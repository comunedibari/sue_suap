/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.webservices.cxf.ricercapratiche;

import it.init.myPage.visuraTypes.DettaglioAttivitaType;
import it.init.myPage.visuraTypes.TipoAttivitaType;
import it.init.myPage.visuraTypes.VisuraDettaglioPraticaDocument;
import it.init.myPage.visuraTypes.VisuraDettaglioPraticaType;
import it.wego.cross.constants.AnaTipiEvento;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.service.PraticheService;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.ws.Provider;
import javax.xml.ws.Service;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.WebServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.xml.xpath.XPathExpression;

/**
 *
 * @author giuseppe
 */
@Component("ricercaPraticheService")
@WebServiceProvider()
@ServiceMode(value = Service.Mode.MESSAGE)
public class RicercaPratiche implements Provider<SOAPMessage> {

    @Autowired
    private XPathExpression requestProcessInput;
    @Autowired
    private PraticheService praticheService;

    @Override
    public SOAPMessage invoke(SOAPMessage request) {
        try {
            String idPratica = requestProcessInput.evaluateAsString(request.getSOAPBody());
            VisuraDettaglioPraticaType visura = populateVisura(idPratica);
            VisuraDettaglioPraticaDocument documento = VisuraDettaglioPraticaDocument.Factory.newInstance();
            documento.setVisuraDettaglioPratica(visura);
            SOAPMessage response = MessageFactory.newInstance().createMessage();
            SOAPPart sp = response.getSOAPPart();
            SOAPEnvelope env = sp.getEnvelope();
            SOAPBody body = response.getSOAPBody();
            Name processResponse = env.createName("processResponse", "ppl", "http://webservice.backend.people.it/");
            SOAPElement processResponseElement = body.addChildElement(processResponse);
            Name processReturn = env.createName("processReturn", "ppl", "http://webservice.backend.people.it/");
            SOAPElement processReturnElement = processResponseElement.addChildElement(processReturn);
            processReturnElement.addTextNode(documento.toString());
            return response;
        } catch (Exception ex) {
            Log.WS.error("Si è verificato un errore cercando di reperire lo storico delle pratiche", ex);
            throw new RuntimeException(ex);
        }
    }

//    @-Transactional
    private VisuraDettaglioPraticaType populateVisura(String idPratica) throws DatatypeConfigurationException {
        Log.WS.info("Genero i dati della visura");
        VisuraDettaglioPraticaType visura = VisuraDettaglioPraticaType.Factory.newInstance();
        Log.WS.info("Recupero pratica con id " + idPratica);
        Pratica pratica = praticheService.getPraticaByIdentificativo(idPratica);
        List<DettaglioAttivitaType> dettaglioAttivita = new ArrayList<DettaglioAttivitaType>();
        for (PraticheEventi evento : pratica.getPraticheEventiList()) {
            Log.WS.info("l'evento ha visibilità per l'utente? " + evento.getVisibilitaUtente());
            //Modifica aggiunta il 22/03/2016
            Log.WS.info("l'evento ha visibilità su People? " + evento.getIdEvento().getFlgPortale());
           // if (evento.getVisibilitaUtente().equalsIgnoreCase("S")) {
            if (evento.getVisibilitaUtente().equalsIgnoreCase("S") && evento.getIdEvento().getFlgPortale().equalsIgnoreCase("S")) {
                DettaglioAttivitaType dettaglio = serializeDettaglioAttivita(pratica, evento);
                Log.WS.info("Aggiungo l'evento a quelli da visualizzare");
                dettaglioAttivita.add(dettaglio);
            }
        }
        DettaglioAttivitaType[] dettaglioAttivitaArray = dettaglioAttivita.toArray(new DettaglioAttivitaType[]{});
        visura.setDettaglioArray(dettaglioAttivitaArray);
        return visura;
    }

    private DettaglioAttivitaType serializeDettaglioAttivita(Pratica pratica, PraticheEventi evento) throws DatatypeConfigurationException {
        DettaglioAttivitaType dettaglio = DettaglioAttivitaType.Factory.newInstance();
        Log.WS.info("Id pratica: " + pratica.getIdentificativoPratica());
        dettaglio.setIdPratica(pratica.getIdentificativoPratica());
        Log.WS.info("Numero di protocollo: " + pratica.getProtocollo());
        dettaglio.setNumeroProtocolloGenerale(pratica.getProtocollo());
        if (pratica.getIdStatoPratica() != null) {
            Log.WS.info("Stato della pratica: " + pratica.getIdStatoPratica().getDescrizione());
            dettaglio.setIdAttivita(pratica.getIdStatoPratica().getDescrizione());
        }
        if (pratica.getDataRicezione() != null) {
            Log.WS.info("Data protocollo: " + Utils.dateToXmlGregorianCalendar(pratica.getDataRicezione()));
            Calendar dataProtocollo = Calendar.getInstance();
            dataProtocollo.setTime(pratica.getDataRicezione());
            dettaglio.setDataProtocolloGenerale(dataProtocollo);
        }
        //Skip
        //dettaglio.setIdProcedimento(procedimento.getBoProcedimentiPK().getCodProc());
        TipoAttivitaType tipoAttivita = TipoAttivitaType.Factory.newInstance();
        if (evento.getDataEvento() != null) {
            Log.WS.info("Data attività: " + Utils.dateToXmlGregorianCalendar(evento.getDataEvento()));
            Calendar dataAttivita = Calendar.getInstance();
            dataAttivita.setTime(evento.getDataEvento());
            dettaglio.setDataAttivita(dataAttivita);
            if (evento.getIdEvento() != null) {
                Log.WS.info("Codice evento: " + evento.getIdEvento().getCodEvento());
                tipoAttivita.setCodice(evento.getIdEvento().getCodEvento());
                Log.WS.info("Descrizione evento: " + evento.getIdEvento().getDesEvento());
                tipoAttivita.setDescrizione(evento.getIdEvento().getDesEvento());
            }
        }

        if (tipoAttivita != null) {
            dettaglio.setTipoAttivita(tipoAttivita);
        }
        if (pratica.getIdStatoPratica() != null && pratica.getIdStatoPratica().getGrpStatoPratica().equals(AnaTipiEvento.GRUPPO_EVENTO_CHIUSURA)) {
            Log.WS.info("Esito evento: " + false);
            dettaglio.setEsito(false);
        } else {
            Log.WS.info("Esito evento: " + true);
            dettaglio.setEsito(true);
        }
        return dettaglio;
    }
}
