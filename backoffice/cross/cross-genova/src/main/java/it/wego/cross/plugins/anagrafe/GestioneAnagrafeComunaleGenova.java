/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.plugins.anagrafe;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.xml.bind.JAXBElement;
import javax.xml.parsers.DocumentBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.xml.xpath.XPathExpression;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.Pratica;
import it.wego.cross.genova.anagrafe.client.stub.Toponomastica;
import it.wego.cross.genova.anagrafe.client.stub.ToponomasticaService;
import it.wego.cross.genova.anagrafe.client.stub.ToponomasticaService_Impl;
import it.wego.cross.genova.anagrafe.client.xml.ObjectFactory;
import it.wego.cross.genova.anagrafe.client.xml.Tipo;
import it.wego.cross.plugins.commons.beans.Anagrafica;
import it.wego.cross.plugins.commons.beans.Recapito;
import it.wego.cross.service.ConfigurationService;
import it.wego.cross.utils.Utils;

/**
 *
 * @author giuseppe
 */
public class GestioneAnagrafeComunaleGenova implements GestioneAnagrafePersonaFisica {

    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private DocumentBuilder documentBuilder;
    //XPATH EXPRESSIONS
    @Autowired
    private XPathExpression residentiXpr;
    @Autowired
    private XPathExpression residenteXpr;
    @Autowired
    private XPathExpression codiceFiscaleXpr;
    @Autowired
    private XPathExpression cognomeXpr;
    @Autowired
    private XPathExpression nomeXpr;
    @Autowired
    private XPathExpression dataNascitaXpr;
    @Autowired
    private XPathExpression comuneNascitaXpr;
    @Autowired
    private XPathExpression sessoXpr;
    @Autowired
    private XPathExpression indirizzoXpr;
    @Autowired
    private XPathExpression numeroCivicoXpr;
    @Autowired
    private XPathExpression letteraCivicoXpr;
    @Autowired
    private XPathExpression coloreCivicoXpr;
    @Autowired
    private XPathExpression internoNumeroXpr;
    @Autowired
    private XPathExpression internoLetteraXpr;
    @Autowired
    private XPathExpression internoScalaXpr;
    @Autowired
    private XPathExpression capXpr;
    private static final String ANAGRAFE_USERNAME = "anagrafe.user";
    private static final String ANAGRAFE_PASSWORD = "anagrafe.password";
    private static final String ANAGRAFE_ENDPOINT = "anagrafe.endpoint";
    private static Logger log = LoggerFactory.getLogger("plugin");

    @Override
    public Anagrafica search(String codiceFiscale, Enti ente, LkComuni comune) {
        Anagrafica anagrafica = null;
        try {
            log.debug("Eseguo la ricerca per il codice fiscale " + codiceFiscale);
            String endpoint = configurationService.getCachedConfiguration(ANAGRAFE_ENDPOINT, ente.getIdEnte(), comune.getIdComune());
            ToponomasticaService toponomasticaService = new ToponomasticaService_Impl();
            Toponomastica toponomastica = toponomasticaService.getToponomastica();
            ((javax.xml.rpc.Stub) toponomastica)._setProperty("javax.xml.rpc.service.endpoint.address", endpoint);
            String xmlRichiesta = getXmlRichiesta(codiceFiscale, ente, comune);
            log.debug("Richiesta: " + xmlRichiesta);
            String result = toponomastica.wsGetResidentiDaCodiceFiscale(xmlRichiesta);
            anagrafica = parseResult(result);
        } catch (RemoteException ex) {
            log.error("Si è verificato un errore contattando il webservice", ex);
        } catch (Exception ex) {
            log.error("Errore creando l'xml di richiesta", ex);
        }
        return anagrafica;
    }

    @Override
    public Boolean existRicercaAnagraficaPersonaFisica( Pratica pratica) {
        String endpoint = configurationService.getCachedConfiguration(ANAGRAFE_ENDPOINT, pratica.getIdProcEnte().getIdEnte().getIdEnte(), pratica.getIdComune().getIdComune());
        if (endpoint != null) {
            return true;
        }
        return false;
    }

    private String getXmlRichiesta(String codiceFiscale, Enti ente, LkComuni comune) throws Exception {
        String user = configurationService.getCachedConfiguration(ANAGRAFE_USERNAME, ente.getIdEnte(), comune.getIdComune());
        String password = configurationService.getCachedConfiguration(ANAGRAFE_PASSWORD, ente.getIdEnte(), comune.getIdComune());
        Tipo r = new Tipo();
        r.setCODICEFISCALE(codiceFiscale.toUpperCase());
        r.setPASSWORD(password);
        r.setUSER(user);
        ObjectFactory of = new ObjectFactory();
        JAXBElement<Tipo> root = of.createTOPONOMASTICA(r);
        String xml = Utils.marshall(root, Tipo.class);
        return xml;
    }

    private Anagrafica parseResult(String result) {
        Anagrafica a = null;
        try {
            log.debug("Eseguo il parsing della risposta: " + result);
            Document doc = documentBuilder.parse(new ByteArrayInputStream(result.getBytes("UTF-8")));
            Node residenti = residentiXpr.evaluateAsNode(doc);
            if (residenti != null) {
                Node residente = residenteXpr.evaluateAsNode(doc);
                if (residente != null) {
                    a = getResidente(doc);
                }
            }
        } catch (SAXException ex) {
            log.error("Errore nella generazione del documento di risposta", ex);
        } catch (IOException ex) {
            log.error("Errore nella generazione del documento di risposta", ex);
        } catch (ParseException ex) {
            log.error("Errore parsando il risultato della chiamata al webservice", ex);
        } catch (Exception ex) {
            log.error("Errore facendo il dump del nodo residenti", ex);
        }
        return a;
    }

    private Anagrafica getResidente(Document doc) throws ParseException {
        log.debug("Inizio la creazione dell'anagrafica");
        Anagrafica anagrafica = new Anagrafica();
        String codiceFiscale = codiceFiscaleXpr.evaluateAsString(doc);
        anagrafica.setCodiceFiscale(codiceFiscale);
        log.debug("Codice fiscale: " + codiceFiscale);
        String cognome = cognomeXpr.evaluateAsString(doc);
        anagrafica.setCognome(cognome);
        log.debug("Cognome: " + cognome);
        String nome = nomeXpr.evaluateAsString(doc);
        anagrafica.setNome(nome);
        log.debug("Nome: " + nome);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String dataNascita = dataNascitaXpr.evaluateAsString(doc);
        if (dataNascita != null && !"".equalsIgnoreCase(dataNascita.trim())) {
            anagrafica.setDataNascita(df.parse(dataNascita));
            log.debug("Data di nascita: " + dataNascita);
        }
        String comuneNascita = comuneNascitaXpr.evaluateAsString(doc);
        anagrafica.setComuneNascita(comuneNascita);
        log.debug("Comune di nascita: " + comuneNascita);
        String sesso = sessoXpr.evaluateAsString(doc);
        anagrafica.setSesso(sesso);
        log.debug("Sesso: " + sesso);
        Recapito recapito = new Recapito();
        String indirizzo = indirizzoXpr.evaluateAsString(doc);
        recapito.setIndirizzo(indirizzo);
        log.debug("Indirizzo: " + indirizzo);
        String civico = numeroCivicoXpr.evaluateAsString(doc);
        recapito.setnCivico(civico);
        log.debug("Numero civico: " + civico);
        String letteraCivico = letteraCivicoXpr.evaluateAsString(doc);
        recapito.setLetteraCivico(letteraCivico);
        log.debug("Lettera civico: " + letteraCivico);
        String colore = coloreCivicoXpr.evaluateAsString(doc);
        recapito.setColore(colore);
        log.debug("Colore: " + colore);
        String interno = internoNumeroXpr.evaluateAsString(doc);
        recapito.setInterno(interno);
        log.debug("Interno: " + interno);
        String letteraInterno = internoLetteraXpr.evaluateAsString(doc);
        recapito.setLetteraInterno(letteraInterno);
        log.debug("Lettera: " + letteraInterno);
        String scala = internoScalaXpr.evaluateAsString(doc);
        recapito.setScala(scala);
        log.debug("Scala: " + scala);
        String cap = capXpr.evaluateAsString(doc);
        recapito.setCap(cap);
        log.debug("Cap: " + cap);
        anagrafica.setRecapito(recapito);
        return anagrafica;
    }
}
