/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.client;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.FileTypeMap;
import javax.annotation.Nullable;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.rpc.ServiceException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.axis.SimpleTargetedChain;
import org.apache.axis.attachments.AttachmentPart;
import org.apache.axis.configuration.BasicClientConfig;
import org.apache.axis.configuration.SimpleProvider;
import org.apache.axis.transport.http.CommonsHTTPSender;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.digester.Digester;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.google.common.base.Strings;

import it.eng.people.connects.interfaces.envelope.IRichiestaPeopleEnvelope;
import it.eng.people.connects.interfaces.envelope.beans.Allegato;
import it.eng.people.connects.interfaces.envelope.beans.PersonaFisica;
import it.eng.people.connects.interfaces.envelope.beans.PersonaGiuridica;
import it.eng.people.connects.interfaces.envelope.beans.RiepilogoRichiesta;
import it.eng.people.connects.interfaces.envelope.beans.Titolare;
import it.eng.people.connects.interfaces.protocollo.IProtocolEnvelope;
import it.eng.people.connects.interfaces.protocollo.beans.IdentificatoreDiProtocollo;
import it.eng.people.connects.interfaces.protocollo.exceptions.ProtocolloException;
import it.eng.protocollo.ProtoCertIntegrityException;
import it.eng.protocollo.ProtoFileIntegrityException;
import it.eng.protocollo.util.BytesDataSource;
import it.eng.protocollo.webservices.WSProtocollazioneServiceLocator;
import it.eng.protocollo.webservices.WSProtocollazioneSoapBindingStub;
import it.eng.protocollo.webservices.WSRicercaAnagraficaHelper;
import it.eng.protocollo.webservices.WSRicercaAnagraficaServiceLocator;
import it.eng.protocollo.webservices.WSRicercaAnagraficaSoapBindingStub;
import it.eng.protocollo.webservices.WSUtils;
import it.wego.cross.beans.EGrammataFascicoloBean;
import it.wego.cross.beans.EGrammataIdentificatoreDiProtocollo;
import it.wego.cross.client.stub.inseriscifascicolo.WSInserimentoFascService;
import it.wego.cross.client.stub.inseriscifascicolo.WSInserimentoFascService_Impl;
import it.wego.cross.client.stub.inseriscifascicolo.WSInserimentoFasc_Stub;
import it.wego.cross.client.stub.ricercafascicolo.WSRicercaFascicoloServiceLocator;
import it.wego.cross.client.stub.ricercafascicolo.WSRicercaFascicoloSoapBindingStub;
import it.wego.cross.client.stub.ricercaprotocollo.WSRicercaProtocolloService;
import it.wego.cross.client.stub.ricercaprotocollo.WSRicercaProtocolloService_Impl;
import it.wego.cross.client.stub.ricercaprotocollo.WSRicercaProtocollo_Stub;
import it.wego.cross.client.xml.ricercaprotocollo.Documento;
import it.wego.cross.client.xml.ricercaprotocollo.EstremiReg;
import it.wego.cross.client.xml.ricercaprotocollo.Nominativo;
import it.wego.cross.client.xml.ricercaprotocollo.RisultatoRicerca;
import it.wego.cross.constants.EGrammata;
import it.wego.cross.plugins.protocollo.beans.DocumentoProtocolloRequest;
import it.wego.cross.plugins.protocollo.beans.DocumentoProtocolloResponse;
import it.wego.cross.plugins.protocollo.beans.SoggettoProtocollo;
import it.wego.cross.utils.EGrammataUtils;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;

/**
 *
 * @author giuseppe
 */
public class EGrammataProtocol {

    private static final Logger log = LoggerFactory.getLogger("plugin");

    public EGrammataIdentificatoreDiProtocollo doProtocol(IProtocolEnvelope envelope, HashMap protocolParameters)
            throws ProtocolloException {
        String risultato;
        log.debug("EGrammataProtocol:doProtocol:IN");
        risultato = null;
        try {
            HashMap params = protocolParameters;
            String addressWSProt = (String) params.get("INDIRIZZO_WS_PROTOCOLLO");
            log.debug("Indirizzo WS Protocollo: " + addressWSProt);
            String codEnte = (String) params.get("CODICE_ENTE");
            log.debug("Ente: " + codEnte);
            String username = (String) params.get("USERNAME");
            log.debug("Username: " + username);
            String password = (String) params.get("PASSWORD");
            String ip = InetAddress.getLocalHost().getHostAddress();
            log.debug("Indirizzo IP: " + ip);
            String timeoutString = (String) params.get("TIMEOUT");
            log.debug("Timeout: " + timeoutString);
//            String senderID = getSender(envelope, params);
//            log.debug("Sender ID: " + senderID);
            String xml = generateXmlProtocollazione(envelope, protocolParameters);
            log.debug("XML protocollazione: " + xml);

            SimpleProvider config = new SimpleProvider(new BasicClientConfig());
            // create a new simple targeted chain
            SimpleTargetedChain c = new SimpleTargetedChain(new CommonsHTTPSender());
            // and add it to the configuration
            config.deployTransport("http", c);
            WSProtocollazioneServiceLocator locator = new WSProtocollazioneServiceLocator(config);

            locator.setWSProtocollazioneAddress(addressWSProt);
            WSProtocollazioneSoapBindingStub binding = (WSProtocollazioneSoapBindingStub) locator.getWSProtocollazione();
            if (!Strings.isNullOrEmpty(timeoutString)) {
                binding.setTimeout(Integer.valueOf(timeoutString));
            }
            String xmlHash = WSUtils.hashXML(xml, password);
            List attachments = prepareAttachments(envelope);
            log.debug("EGrammataProtocol:doProtocol:XML da inviare al Protocollo: \n" + xml);
            risultato = binding.service(codEnte, username, password, ip, WSUtils.encodeXml(xml), xmlHash, attachments.toArray());
            log.debug("EGrammataProtocol:doProtocol:Chiamata effettuata!");
            risultato = WSUtils.decodeXml(risultato);
            risultato = risultato.replaceAll("\\<Risposta  \\>", "<Risposta>");
            risultato = risultato.replace("<?xml version=\"1.0\" ?>", "<?xml version=\"1.0\" ?><!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
            log.debug("EGrammataProtocol:doProtocol:Risposta dal protocollo \n" + risultato);
        } catch (Exception e) {
            log.error("EGrammataProtocol:doProtocol:ERRORE DURANTE LA PROTOCOLLAZIONE: ", e);
            throw new ProtocolloException("ERRORE DURANTE LA PROTOCOLLAZIONE: \n" + e.getClass() + " " + e.getMessage());
        }
        try {
            if (!verificaRisultato(risultato)) {
            }
            EGrammataIdentificatoreDiProtocollo pn = getIdentificatoreDiProtocollo(risultato);
            log.info("EGrammataProtocol:doProtocol:Protocollazione OK! N. Protocollo: " + pn.toString());
            log.debug("EGrammataProtocol:doProtocol:OUT");
            return pn;
        } catch (Exception ex) {
            log.error("",ex);
        }

        try {
            risultato = risultato.replace("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">", "");
            String messaggio = WSUtils.readMessaggio(risultato);
            if (messaggio == null || messaggio.length() == 0) {
                log.error("EGrammataProtocol:doProtocol:Protocollazione FALLITA! \nMessaggio di errore non"
                        + " reperibile!");
                throw new ProtocolloException("ERRORE NELLA PROTOCOLLAZIONE. MESSAGGIO DI ERRORE NON REPERIBILE.");
            }
            log.error("EGrammataProtocol:doProtocol:Protocollazione FALLITA! \nMessaggio ritornato dal"
                    + " protocollo: \n"
                    + messaggio);
            String codice = WSUtils.readCodice(risultato);
            if (codice != null && (codice.equalsIgnoreCase("11") || codice.equalsIgnoreCase("12"))) {
                if (codice.equalsIgnoreCase("11")) {
                    throw new ProtoFileIntegrityException("PROTOCOLLAZIONE FALLITA: \n" + messaggio);
                } else {
                    throw new ProtoCertIntegrityException("PROTOCOLLAZIONE FALLITA: \n" + messaggio);
                }
            } else {
                throw new ProtocolloException("PROTOCOLLAZIONE FALLITA: \n" + messaggio);
            }
        } catch (Exception e) {
            if (e instanceof ProtocolloException) {
                throw (ProtocolloException) e;
            } else {
                log.error("EGrammataProtocol:doProtocol:Protocollazione FALLITA! \nImpossibile leggere la "
                        + "risposta!", e);
                throw new ProtocolloException("ERRORE NELLA PROTOCOLLAZIONE. IMPOSSIBILE LEGGERE LA RISPOSTA: \n" + e.getMessage());
            }
        }
    }

    public EGrammataIdentificatoreDiProtocollo doProtocolOut(IProtocolEnvelope envelope, HashMap protocolParameters)
            throws ProtocolloException {
        String risultato;
        log.debug("EGrammataProtocol:doProtocol:OUT");
        risultato = null;
        try {
            HashMap params = protocolParameters;
            String addressWSProt = (String) params.get("INDIRIZZO_WS_PROTOCOLLO");
            log.debug("Indirizzo WS Protocollo: " + addressWSProt);
            String codEnte = (String) params.get("CODICE_ENTE");
            log.debug("Ente: " + codEnte);
            String username = (String) params.get("USERNAME");
            log.debug("Username: " + username);
            String password = (String) params.get("PASSWORD");
            String ip = InetAddress.getLocalHost().getHostAddress();
            log.debug("Indirizzo IP: " + ip);
            String senderID = getSender(envelope, params);
            String timeoutString = (String) params.get("TIMEOUT");
            log.debug("Timeout: " + timeoutString);
            log.debug("Sender ID: " + senderID);
            String xml = generateXmlProtocollazioneOut(envelope, protocolParameters);
            log.debug("XML protocollazione: " + xml);
//            WSProtocollazioneServiceLocator locator = new WSProtocollazioneServiceLocator();

            SimpleProvider config = new SimpleProvider(new BasicClientConfig());
            // create a new simple targeted chain
            SimpleTargetedChain c = new SimpleTargetedChain(new CommonsHTTPSender());
            // and add it to the configuration
            config.deployTransport("http", c);
            WSProtocollazioneServiceLocator locator = new WSProtocollazioneServiceLocator(config);

            locator.setWSProtocollazioneAddress(addressWSProt);
            WSProtocollazioneSoapBindingStub binding = (WSProtocollazioneSoapBindingStub) locator.getWSProtocollazione();
            if (!Strings.isNullOrEmpty(timeoutString)) {
                binding.setTimeout(Integer.valueOf(timeoutString));
            }
            String xmlHash = WSUtils.hashXML(xml, password);
            List attachments = prepareAttachments(envelope);
            log.debug("EGrammataProtocol:doProtocol:XML da inviare al Protocollo: \n" + xml);
            risultato = binding.service(codEnte, username, password, ip, WSUtils.encodeXml(xml), xmlHash, attachments.toArray());
            log.debug("EGrammataProtocol:doProtocol:Chiamata effettuata!");
            risultato = WSUtils.decodeXml(risultato);
            risultato = risultato.replaceAll("\\<Risposta  \\>", "<Risposta>");
            risultato = risultato.replace("<?xml version=\"1.0\" ?>", "<?xml version=\"1.0\" ?><!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
            log.debug("EGrammataProtocol:doProtocol:Risposta dal protocollo \n" + risultato);
        } catch (Exception e) {
            log.error("EGrammataProtocol:doProtocol:ERRORE DURANTE LA PROTOCOLLAZIONE: ", e);
            throw new ProtocolloException("ERRORE DURANTE LA PROTOCOLLAZIONE: \n" + e.getClass() + " " + e.getMessage(), e);
        }
        try {
            if (!verificaRisultato(risultato)) {
            }
            EGrammataIdentificatoreDiProtocollo pn = getIdentificatoreDiProtocollo(risultato);
            log.info("EGrammataProtocol:doProtocol:Protocollazione OK! N. Protocollo: " + pn.toString());
            log.debug("EGrammataProtocol:doProtocol:OUT");
            return pn;
        } catch (Exception ex) {
            log.error("",ex);
        }

        try {
            risultato = risultato.replace("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">", "");
            String messaggio = WSUtils.readMessaggio(risultato);
            if (messaggio == null || messaggio.length() == 0) {
                log.error("EGrammataProtocol:doProtocol:Protocollazione FALLITA! \nMessaggio di errore non"
                        + " reperibile!");
                throw new ProtocolloException("ERRORE NELLA PROTOCOLLAZIONE. MESSAGGIO DI ERRORE NON REPERIBILE.");
            }
            log.error("EGrammataProtocol:doProtocol:Protocollazione FALLITA! \nMessaggio ritornato dal"
                    + " protocollo: \n"
                    + messaggio);
            String codice = WSUtils.readCodice(risultato);
            if (codice != null && (codice.equalsIgnoreCase("11") || codice.equalsIgnoreCase("12"))) {
                if (codice.equalsIgnoreCase("11")) {
                    throw new ProtoFileIntegrityException("PROTOCOLLAZIONE FALLITA: \n" + messaggio);
                } else {
                    throw new ProtoCertIntegrityException("PROTOCOLLAZIONE FALLITA: \n" + messaggio);
                }
            } else {
                throw new ProtocolloException("PROTOCOLLAZIONE FALLITA: \n" + messaggio);
            }
        } catch (Exception e) {
            if (e instanceof ProtocolloException) {
                throw (ProtocolloException) e;
            } else {
                log.error("EGrammataProtocol:doProtocol:Protocollazione FALLITA! \nImpossibile leggere la "
                        + "risposta!", e);
                throw new ProtocolloException("ERRORE NELLA PROTOCOLLAZIONE. IMPOSSIBILE LEGGERE LA RISPOSTA: \n" + e.getMessage());
            }
        }
    }

    private String ricercaAnagrafica(
            String nominativo,
            String denominazione,
            String codiceFiscale,
            String partitaIva,
            HashMap<String, String> configurazioneProtocollo) throws ProtocolloException {
        try {
            String addressWS = (String) configurazioneProtocollo.get(EGrammata.INDIRIZZO_WS_RICERCA_ANAGRAFICA);
            String codEnte = (String) configurazioneProtocollo.get("CODICE_ENTE");
            String username = (String) configurazioneProtocollo.get("USERNAME");
            String password = (String) configurazioneProtocollo.get("PASSWORD");
            String ip = InetAddress.getLocalHost().getHostAddress();
            String timeoutString = (String) configurazioneProtocollo.get("TIMEOUT");
            String xml = generateXmlRicercaAnagrafica(codiceFiscale, partitaIva, nominativo, denominazione);
            log.debug("EGrammataProtocol:ricercaAnagrafica:Richiesta ricerca anagrafica \n" + xml);
            String xmlHash = EGrammataUtils.hashXML(xml, password);
            log.debug("EGrammataProtocol:ricercaAnagrafica:Hash della ricerca anagrafica \n" + xmlHash);
            WSRicercaAnagraficaServiceLocator locator = new WSRicercaAnagraficaServiceLocator();

            locator.setWSRicercaAnagraficaAddress(addressWS);
            WSRicercaAnagraficaSoapBindingStub binding = (WSRicercaAnagraficaSoapBindingStub) locator.getWSRicercaAnagrafica();
            if (!Strings.isNullOrEmpty(timeoutString)) {
                binding.setTimeout(Integer.valueOf(timeoutString));
            }
            String risultato = binding.service(codEnte, username, password, ip, WSUtils.encodeXml(xml), xmlHash);
            risultato = WSUtils.decodeXml(risultato);
//            risultato = risultato.replace("<?xml version=\"1.0\" ?>", "<?xml version=\"1.0\" ?><!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
            log.debug("EGrammataProtocol:getFascicolo:Risposta ricerca anagrafe \n" + risultato);
            try {
                if (!verificaRisultato(risultato)) {
                }
                return getCodice(risultato, nominativo, denominazione, codiceFiscale, partitaIva);
            } catch (Exception ex) {
                log.error("",ex);
                try {
                    String msg = WSUtils.readMessaggio(risultato, "RisultatoRicercaAnagrafica");
                    log.error("RICERCA ANAGRAFICA FALLITA:" + msg);
                    throw new ServiceException(msg);
                } catch (RemoteException e) {
                    log.error("SERVIZIO ANAGRAFE PROTOCOLLO NON DISPONIBILE", e);
                    throw new ProtocolloException("SERVIZIO ANAGRAFE PROTOCOLLO NON DISPONIBILE");
                } catch (Throwable t) {
                    log.error("ERRORE DURANTE L'INVOCAZIONE DEL SERVIZIO DI ANAGRAFE PROTOCOLLO", t);
                    throw new ProtocolloException("ERRORE DURANTE L'INVOCAZIONE DEL SERVIZIO DI ANAGRAFE PROTOCOLLO: " + t.getMessage());
                }
            }
        } catch (Exception ex) {
            log.error("EGrammataProtocol:ricercaAnagrafica:ERRORE DURANTE LA RICERCA DELL'ANAGRAFICA: ", ex);
            throw new ProtocolloException("ERRORE RICERCA DELL'ANAGRAFICA: \n" + ex.getClass() + " " + ex.getMessage());
        }
    }

    private String getCodice(String risultato, String nominativo, String denominazione, String codiceFiscale, String partitaIva) throws XPathExpressionException {
        String xpathQuery;
        String codice = null;
        if (!Strings.isNullOrEmpty(codiceFiscale)) {
            InputSource source = new InputSource(new StringReader(risultato));
            xpathQuery = "//Anagrafica[CodiceFiscale = '" + codiceFiscale.toUpperCase() + "'][last()]/@codice";
            XPath xpath = XPathFactory.newInstance().newXPath();
            codice = xpath.evaluate(xpathQuery, source);
        }
        if (Strings.isNullOrEmpty(codice)) {
            if (!Strings.isNullOrEmpty(partitaIva)) {
                InputSource source = new InputSource(new StringReader(risultato));
                xpathQuery = "//Anagrafica[PartitaIva = '" + partitaIva.toUpperCase() + "'][last()]/@codice";
                XPath xpath = XPathFactory.newInstance().newXPath();
                codice = xpath.evaluate(xpathQuery, source);
            }
        }
        if (Strings.isNullOrEmpty(codice)) {
            String ricerca = nominativo;
            if (Strings.isNullOrEmpty(nominativo)) {
                ricerca = denominazione;
            }
            InputSource source = new InputSource(new StringReader(risultato));
            xpathQuery = "//Anagrafica[Nominativo = '" + StringEscapeUtils.escapeXml(ricerca).toUpperCase() + "'][last()]/@codice";
            XPath xpath = XPathFactory.newInstance().newXPath();
            codice = xpath.evaluate(xpathQuery, source);
        }
        return Strings.nullToEmpty(codice);
    }

    private String generateXmlRicercaFascicolo(HashMap<String, Object> protocolParameters) {
        HashMap hmProt = protocolParameters;
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" ?> ");
        sb.append("<!DOCTYPE RicercaFascicolo PUBLIC \"\"  \"http://172.28.20.145:8088/protorerProd/includes/DTD/RicercaFascicoli.dtd\" >");
        sb.append("<RicercaFascicolo versione='2005-01-31' xml:lang='it'>");
        sb.append("<IdUoIn>").append(Utils.emptyIfNull(hmProt.get("ID_UO_IN"))).append("</IdUoIn>");
        sb.append("<Classif_FascApp>");
        sb.append("<CLASSIF_FASC>");
        sb.append("<AnnoFasc>").append(Utils.emptyIfNull(hmProt.get(EGrammata.ANNO_FASCICOLO))).append("</AnnoFasc>");
        sb.append("<Classif>");
        sb.append("<CLASSIFICAZIONE>");
        sb.append("<Titolo/>");
        sb.append("</CLASSIFICAZIONE>");
        sb.append("</Classif>");
        sb.append("<ProgrFasc>").append(Utils.emptyIfNull(hmProt.get(EGrammata.NUMERO_FASCICOLO))).append("</ProgrFasc>");
        sb.append("<NumSottofasc/>");
        sb.append("<IdFascicolo/>");
        sb.append("</CLASSIF_FASC>");
        sb.append("</Classif_FascApp>");
        sb.append("</RicercaFascicolo>");
        return sb.toString();
    }

    public EGrammataFascicoloBean getFascicolo(HashMap<String, Object> configurazioneProtocollo) throws ProtocolloException {
        try {
            String addressWSProt = (String) configurazioneProtocollo.get(EGrammata.INDIRIZZO_WS_RICERCA_FASCICOLO);
            String codEnte = (String) configurazioneProtocollo.get("CODICE_ENTE");
            String username = (String) configurazioneProtocollo.get("USERNAME");
            String password = (String) configurazioneProtocollo.get("PASSWORD");
            String timeoutString = (String) configurazioneProtocollo.get("TIMEOUT");
            String ip = InetAddress.getLocalHost().getHostAddress();
            String xml = generateXmlRicercaFascicolo(configurazioneProtocollo);
            log.debug("EGrammataProtocol:getFascicolo:Richiesta ricerca fascicolo \n" + xml);
            String xmlHash = EGrammataUtils.hashXML(xml, password);
            log.debug("EGrammataProtocol:getFascicolo:Hash della ricerca fascicolo \n" + xmlHash);

            WSRicercaFascicoloServiceLocator ricercaFascicoloService = new WSRicercaFascicoloServiceLocator();
            ricercaFascicoloService.setWSRicercaFascicoloEndpointAddress(addressWSProt);

            WSRicercaFascicoloSoapBindingStub binding = (WSRicercaFascicoloSoapBindingStub) ricercaFascicoloService.getWSRicercaFascicolo();
            if (!Strings.isNullOrEmpty(timeoutString)) {
                binding.setTimeout(Integer.valueOf(timeoutString));
            }
            String risultato = binding.ricerca(codEnte, username, password, ip, WSUtils.encodeXml(xml), xmlHash);

            risultato = WSUtils.decodeXml(risultato);
            risultato = risultato.replaceAll("\\<RisultatoRicerca  \\>", "<RisultatoRicerca>");
            log.debug("EGrammataProtocol:getFascicolo:Risposta ricerca fascicolo \n" + risultato);
            EGrammataFascicoloBean fascicoloBean;
            if (!verificaRisultato(risultato)) {
                log.error("EGrammataProtocol:getFascicolo:ERRORE RITORNATO DAL SERVIZIO: " + risultato);
            }
            fascicoloBean = getFascicoloBean(risultato);
            return fascicoloBean;
        } catch (Exception ex) {
            log.error("EGrammataProtocol:getFascicolo:ERRORE DURANTE LA RICERCA DEL FASCICOLO: ", ex);
            throw new ProtocolloException("ERRORE RICERCA DEL FASCICOLO: \n" + ex.getClass() + " " + ex.getMessage());
        }
    }

    private String generateXmlProtocollazioneOut(IProtocolEnvelope envelope, HashMap protocolParameters) throws ProtocolloException {
        String nomeDocumentoElettronico = envelope.getRiepilogo() == null ? "" : StringEscapeUtils.escapeXml(envelope.getRiepilogo().getNomeFile());
        HashMap hmProt = protocolParameters;
        StringBuilder xml = new StringBuilder();
        String idUoUfficio = (String) hmProt.get("OVERRIDE_ID_UO_IN");
        Log.PLUGIN.info("OVERRIDE_ID_UO_IN: " + idUoUfficio);
        if (StringUtils.isEmpty(idUoUfficio)) {
            idUoUfficio = (String) hmProt.get("ID_UO_IN");
        }
        Log.PLUGIN.info("ID_UO UFFICIO UTILIZZATA: " + idUoUfficio);

        xml.append("<?xml version='1.0' ?><SegnaturaGenerica versione='2003-12-09' xml:lang='it'><Dati RimandaOrigIn='N' TipoProtIN='U'><IdUteIn>")
                .append(Utils.emptyIfNull(hmProt.get("ID_UTE_IN")))
                .append("</IdUteIn><IdUOIn>").append(Utils.emptyIfNull(Utils.emptyIfNull(hmProt.get("ID_UO_IN"))))
                //                .append("</IdUOIn><DtArrivoIn>").append((new SimpleDateFormat("dd/MM/yyyy HH:mm")).format(new Date()))
                .append("</IdUOIn><DtArrivoIn>").append((new SimpleDateFormat("dd/MM/yyyy")).format(new Date()))
                .append("</DtArrivoIn><TxtOggIn>").append(Utils.emptyIfNull(hmProt.get("SUBJECT"))).append(": ").append(envelope.getIdentificatoreRichiesta())
                .append("</TxtOggIn><IdIndice>").append(Utils.emptyIfNull(hmProt.get("ID_INDICE")))
                .append("</IdIndice><IdTitolazione>").append(Utils.emptyIfNull(hmProt.get("ID_TITOLAZIONE")))
                .append("</IdTitolazione><IdFascicolo>").append(Utils.emptyIfNull(hmProt.get("ID_FASCICOLO")))
                .append("</IdFascicolo><NumFasc>").append(Utils.emptyIfNull(hmProt.get("NUMERO_FASCICOLO")))
                .append("</NumFasc><NumSottofasc></NumSottofasc><AnnoFasc>").append(Utils.emptyIfNull(hmProt.get("ANNO_FASCICOLO")))
                .append("</AnnoFasc>");

        DocumentoProtocolloRequest documentoProtocollo = (DocumentoProtocolloRequest) protocolParameters.get("DOCUMENTO_PROTOCOLLO");

        for (SoggettoProtocollo soggettoProtocollo : documentoProtocollo.getSoggetti()) {
            String senderID = getSenderId(soggettoProtocollo, hmProt);
            log.debug("Sender ID: " + senderID);
            if (soggettoProtocollo.getTipoPersona().equals(CrossProtocolEnvelope.FISICA)) {
                log.debug("Aggiungo l'anagrafica: " + soggettoProtocollo.getCognome() + " " + soggettoProtocollo.getNome() + " (" + soggettoProtocollo.getCodiceFiscale() + ")");
                xml.append("<EsibDest flgTpAnag='P' flgDestCopia='N' flgCC='N'><IdAnag>").append(senderID).append("</IdAnag><Cognome>")
                        .append(StringEscapeUtils.escapeXml(soggettoProtocollo.getCognome())).append("</Cognome><Nome>").append(StringEscapeUtils.escapeXml(soggettoProtocollo.getNome())).append("</Nome><CodFis>")
                        .append(StringEscapeUtils.escapeXml(soggettoProtocollo.getCodiceFiscale())).append("</CodFis></EsibDest>");
            } else {
                log.debug("Aggiungo l'anagrafica: " + soggettoProtocollo.getDenominazione() + "(" + soggettoProtocollo.getCodiceFiscale() + ")");
                xml.append("<EsibDest flgTpAnag='D' flgDestCopia='N' flgCC='N'><IdAnag>").append(senderID).append("</IdAnag><RagioneSociale>").append(StringEscapeUtils.escapeXml(soggettoProtocollo.getDenominazione()))
                        .append("</RagioneSociale>");
                //Egrammata per le persone giuridiche sembra considerare la partita iva e non il CF. Per completezza di informazione comunque invio il CF in assenza di partita iva
                if (soggettoProtocollo.getPartitaIva() != null && !soggettoProtocollo.getPartitaIva().equals("")) {
                    xml.append("<ParIva>").append(StringEscapeUtils.escapeXml(soggettoProtocollo.getPartitaIva())).append("</ParIva>");
                } else {
                    xml.append("<CodFis>").append(StringEscapeUtils.escapeXml(soggettoProtocollo.getCodiceFiscale())).append("</CodFis>");
                }
                xml.append("</EsibDest>");
            }
        }
        xml.append("<UOProv><UO><IdUo>").append(Utils.emptyIfNull(idUoUfficio)).append("</IdUo></UO></UOProv>");
//        xml.append("<CopieArrIn flgorig='N' note='' flgCC='N'><UoAss><UO><SettIn>")
//                .append(Utils.emptyIfNull(hmProt.get("COPY_SETT_IN"))).append("</SettIn><ServIn>")
//                .append(Utils.emptyIfNull(hmProt.get("COPY_SERV_IN"))).append("</ServIn><UOCIn>")
//                .append(Utils.emptyIfNull(hmProt.get("COPY_OUC_IN"))).append("</UOCIn><UOSIn>")
//                .append(Utils.emptyIfNull(hmProt.get("COPY_OUS_IN"))).append("</UOSIn><PostIn>")
//                .append(Utils.emptyIfNull(hmProt.get("COPY_POST_IN"))).append("</PostIn></UO></UoAss><IdInd>")
//                .append(Utils.emptyIfNull(hmProt.get("ID_INDICE_COPIE"))).append("</IdInd></CopieArrIn>");
        xml.append("<DocumentoElettronico NumeroAttachment=\"0\" NomeFile=\"").append(nomeDocumentoElettronico).append("\"/>");
        if (!envelope.getAllegati().isEmpty()) {
            Iterator it = envelope.getAllegati().iterator();
            String allegati = "";
            for (int idx = 0; it.hasNext(); idx++) {
                Allegato a = (Allegato) it.next();
                String descrizione = limitString(StringEscapeUtils.escapeXml(a.getDescrizione()), 249);
                allegati = allegati + "<AllegaArrIn Tipo=\"0\" Note=\"" + (Strings.isNullOrEmpty(descrizione) ? "Allegato" : descrizione) + "\" NumeroAttachment=\"" + (idx + 1) + "\" NomeFile=\"" + StringEscapeUtils.escapeXml(a.getNomeFile()) + "\"/>";
            }
            xml.append(allegati);
        }
        xml.append("</Dati></SegnaturaGenerica>");
        return xml.toString();
    }

    private String generateXmlProtocollazione(IProtocolEnvelope envelope, HashMap protocolParameters) throws ProtocolloException {
        String nomeDocumentoElettronico = envelope.getRiepilogo() == null ? "" : StringEscapeUtils.escapeXml(envelope.getRiepilogo().getNomeFile());
        HashMap hmProt = protocolParameters;
//        Titolare tit = envelope.getTitolare();
        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version='1.0' ?><SegnaturaGenerica versione='2003-12-09' xml:lang='it'><Dati RimandaOrigIn='N' TipoProtIN='E'><IdUteIn>")
                .append(Utils.emptyIfNull(hmProt.get("ID_UTE_IN")))
                .append("</IdUteIn><IdUOIn>").append(Utils.emptyIfNull(hmProt.get("ID_UO_IN")))
                .append("</IdUOIn><DtArrivoIn>")
                //                .append((new SimpleDateFormat("dd/MM/yyyy HH:mm")).format(new Date()))
                .append((new SimpleDateFormat("dd/MM/yyyy")).format(new Date()))
                .append("</DtArrivoIn><TxtOggIn>").append(Utils.emptyIfNull(hmProt.get("SUBJECT"))).append(": ").append(envelope.getIdentificatoreRichiesta())
                .append("</TxtOggIn><IdIndice>").append(Utils.emptyIfNull(hmProt.get("ID_INDICE")))
                .append("</IdIndice><IdTitolazione>").append(Utils.emptyIfNull(hmProt.get("ID_TITOLAZIONE")))
                .append("</IdTitolazione><IdFascicolo>").append(Utils.emptyIfNull(hmProt.get("ID_FASCICOLO")))
                .append("</IdFascicolo><NumFasc>").append(Utils.emptyIfNull(hmProt.get("NUMERO_FASCICOLO")))
                .append("</NumFasc><NumSottofasc></NumSottofasc><AnnoFasc>").append(Utils.emptyIfNull(hmProt.get("ANNO_FASCICOLO")))
                .append("</AnnoFasc>");

        DocumentoProtocolloRequest documentoProtocollo = (DocumentoProtocolloRequest) protocolParameters.get("DOCUMENTO_PROTOCOLLO");

        for (SoggettoProtocollo soggettoProtocollo : documentoProtocollo.getSoggetti()) {
            String senderID = getSenderId(soggettoProtocollo, hmProt);
            log.debug("Sender ID: " + senderID);
            if (soggettoProtocollo.getTipoPersona().equals(CrossProtocolEnvelope.FISICA)) {
                log.debug("Aggiungo l'anagrafica: " + soggettoProtocollo.getCognome() + " " + soggettoProtocollo.getNome() + " (" + soggettoProtocollo.getCodiceFiscale() + ")");
                xml.append("<Firm flgTpAnag='P'><IdAnag>").append(senderID).append("</IdAnag><Cognome>")
                        .append(StringEscapeUtils.escapeXml(soggettoProtocollo.getCognome())).append("</Cognome><Nome>").append(StringEscapeUtils.escapeXml(soggettoProtocollo.getNome())).append("</Nome><CodFis>")
                        .append(StringEscapeUtils.escapeXml(soggettoProtocollo.getCodiceFiscale())).append("</CodFis></Firm>");
            } else {
                log.debug("Aggiungo l'anagrafica: " + soggettoProtocollo.getDenominazione() + "(" + soggettoProtocollo.getCodiceFiscale() + ")");
                xml.append("<Firm flgTpAnag='D'><IdAnag>").append(senderID).append("</IdAnag><RagioneSociale>").append(StringEscapeUtils.escapeXml(soggettoProtocollo.getDenominazione()))
                        .append("</RagioneSociale>");
                //Egrammata per le persone giuridiche sembra considerare la partita iva e non il CF. Per completezza di informazione comunque invio il CF in assenza di partita iva
                if (soggettoProtocollo.getPartitaIva() != null && !soggettoProtocollo.getPartitaIva().equals("")) {
                    xml.append("<ParIva>").append(StringEscapeUtils.escapeXml(soggettoProtocollo.getPartitaIva())).append("</ParIva>");
                } else {
                    xml.append("<CodFis>").append(StringEscapeUtils.escapeXml(soggettoProtocollo.getCodiceFiscale())).append("</CodFis>");
                }
                xml.append("</Firm>");
            }
        }

        xml.append("<CopieArrIn flgorig='S' note='' flgCC=''><UoAss><UO><SettIn>")
                .append(Utils.emptyIfNull(hmProt.get("COPY_SETT_IN"))).append("</SettIn><ServIn>")
                .append(Utils.emptyIfNull(hmProt.get("COPY_SERV_IN"))).append("</ServIn><UOCIn>")
                .append(Utils.emptyIfNull(hmProt.get("COPY_OUC_IN"))).append("</UOCIn><UOSIn>")
                .append(Utils.emptyIfNull(hmProt.get("COPY_OUS_IN"))).append("</UOSIn><PostIn>")
                .append(Utils.emptyIfNull(hmProt.get("COPY_POST_IN"))).append("</PostIn></UO></UoAss><IdInd>")
                .append(Utils.emptyIfNull(hmProt.get("ID_INDICE_COPIE"))).append("</IdInd></CopieArrIn><DocumentoElettronico NumeroAttachment=\"0\" NomeFile=\"")
                .append(nomeDocumentoElettronico).append("\"/>");
        if (!envelope.getAllegati().isEmpty()) {
            Iterator it = envelope.getAllegati().iterator();
            String allegati = "";
            for (int idx = 0; it.hasNext(); idx++) {
                Allegato a = (Allegato) it.next();
                String descrizione = limitString(StringEscapeUtils.escapeXml(a.getDescrizione()), 249);
                allegati = allegati + "<AllegaArrIn Tipo=\"0\" Note=\"" + (Strings.isNullOrEmpty(descrizione) ? "Allegato" : descrizione) + "\" NumeroAttachment=\"" + (idx + 1) + "\" NomeFile=\"" + StringEscapeUtils.escapeXml(a.getNomeFile()) + "\"/>";
            }
            xml.append(allegati);
        }
        xml.append("</Dati></SegnaturaGenerica>");
        return xml.toString();
    }

    private String limitString(String stringToCut, int maxlength) {
        if (!Strings.isNullOrEmpty(stringToCut)) {
            return Utils.limitText(stringToCut, maxlength);
        } else {
            return "";
        }
    }

    private List prepareAttachments(IRichiestaPeopleEnvelope envelope)
            throws IOException {
        ArrayList result = new ArrayList();
        FileTypeMap ftm = FileTypeMap.getDefaultFileTypeMap();
        RiepilogoRichiesta riepilogo = envelope.getRiepilogo();
        if (riepilogo != null) {
            result.add(new AttachmentPart(new DataHandler(new BytesDataSource(Base64.decodeBase64(riepilogo.getContenuto()), ftm.getContentType(riepilogo.getNomeFile()), riepilogo.getNomeFile()))));
        }
        Iterator it = envelope.getAllegati().iterator();
        while (it.hasNext()) {
            Allegato a = (Allegato) it.next();
            DataHandler data = new DataHandler(new BytesDataSource(Base64.decodeBase64(a.getContenuto()), ftm.getContentType(a.getNomeFile()), a.getNomeFile()));
            result.add(new AttachmentPart(data));
        }
        return result;
    }

    public EGrammataIdentificatoreDiProtocollo getIdentificatoreDiProtocollo(String risultato) throws IOException, NumberFormatException, SAXException {
        String cleanResult = cleanTagMessaggio(risultato);
        IdentificatoreDiProtocollo pn = (IdentificatoreDiProtocollo) createDigesterProtocollo().parse(new StringReader(cleanResult));
        int anno = Integer.parseInt(pn.getCodiceAmministrazione());
        Date d = new Date();
        if (anno < d.getYear() + 1900) {
            d.setYear(anno);
            d.setMonth(11);
            d.setDate(31);
        } else if (anno > d.getYear() + 1900) {
            d.setYear(anno - 1900);
            d.setMonth(0);
            d.setDate(1);
        }
        pn.setDataDiRegistrazione(d);
        pn.setCodiceAmministrazione(null);
        EGrammataIdentificatoreDiProtocollo egidp = new EGrammataIdentificatoreDiProtocollo();
        egidp.setIdentificatoreDiProtocollo(pn);
        String message = getMessaggio(risultato);
        egidp.setMessage(message);
        return egidp;
    }

    private Digester createDigesterProtocollo() {
        Digester digester = new Digester();
        digester.setValidating(false);
        digester.setEntityResolver(new EGrammataIgnoreEntityValidationResolver());
        digester.addObjectCreate("Risposta/NumeroProtocollo", it.eng.people.connects.interfaces.protocollo.beans.IdentificatoreDiProtocollo.class);
        digester.addSetProperties("Risposta/NumeroProtocollo", "tipo", "codiceAOO");
        digester.addSetProperties("Risposta/NumeroProtocollo", "anno", "codiceAmministrazione");
        digester.addSetProperties("Risposta/NumeroProtocollo", "numero", "numeroDiRegistrazione");
        return digester;
    }

    private boolean verificaRisultato(String xml) throws XPathExpressionException {
        String codice = StringUtils.substringBetween(xml, "<Codice>", "</Codice>");
        return codice != null && codice.equalsIgnoreCase("0");
    }

    private String getSenderId(SoggettoProtocollo soggettoProtocollo, HashMap<String, String> configurazioneProtocollo) throws ProtocolloException {
        String senderID = null;
        log.debug("EGrammataProtocol:doProtocol:Avvio Ricerca in Anagrafe Protocollo");
        String nominativo = "";
        String denominazione = "";
        if (soggettoProtocollo != null) {
            if (!Strings.isNullOrEmpty(soggettoProtocollo.getCognome())) {
                nominativo = soggettoProtocollo.getCognome();
                if (!Strings.isNullOrEmpty(soggettoProtocollo.getNome())) {
                    nominativo = nominativo + " " + soggettoProtocollo.getNome();
                }
            }
            if (!Strings.isNullOrEmpty(soggettoProtocollo.getDenominazione())) {
                denominazione = soggettoProtocollo.getDenominazione();
            }
            senderID = ricercaAnagrafica(StringEscapeUtils.escapeXml(nominativo).toUpperCase(), StringEscapeUtils.escapeXml(denominazione).toUpperCase(), soggettoProtocollo.getCodiceFiscale(), soggettoProtocollo.getPartitaIva(), configurazioneProtocollo);
        }
        return senderID;
    }

    private String getSender(IProtocolEnvelope envelope, HashMap<String, String> configurazioneProtocollo) throws ProtocolloException {
        String senderID = null;
        log.debug("EGrammataProtocol:doProtocol:Avvio Ricerca in Anagrafe Protocollo");
        Titolare tit = envelope.getTitolare();
        if (tit != null) {
            WSRicercaAnagraficaHelper wrah = new WSRicercaAnagraficaHelper();
            wrah.setProtoParams(configurazioneProtocollo);
            if (tit.getTipoTitolare() == 0) {
                PersonaFisica pf = tit.getPersonaFisica();
//                senderID = wrah.ricercaAnagrafica(StringEscapeUtils.escapeXml(pf.getCognome()), StringEscapeUtils.escapeXml(pf.getNome()), StringEscapeUtils.escapeXml(pf.getCodiceFiscale()));
//                senderID = ricercaAnagrafica(StringEscapeUtils.escapeXml(pf.getCodiceFiscale()), configurazioneProtocollo);
                String nominativo = pf.getCognome() + " " + pf.getNome();
                senderID = ricercaAnagrafica(StringEscapeUtils.escapeXml(nominativo).toUpperCase(), null, StringEscapeUtils.escapeXml(pf.getCodiceFiscale()), null, configurazioneProtocollo);
            } else {
                PersonaGiuridica pg = tit.getPersonaGiuridica();
//                senderID = wrah.ricercaAnagrafica(StringEscapeUtils.escapeXml(pg.getDenominazione()), null, StringEscapeUtils.escapeXml(pg.getCodiceFiscale()));
//                senderID = ricercaAnagrafica(StringEscapeUtils.escapeXml(pg.getCodiceFiscale()), configurazioneProtocollo);
                senderID = ricercaAnagrafica(null, StringEscapeUtils.escapeXml(pg.getDenominazione()).toUpperCase(), StringEscapeUtils.escapeXml(pg.getCodiceFiscale()), pg.getPartitaIVA(), configurazioneProtocollo);
            }
            if (senderID != null) {
                log.debug("EGrammataProtocol:doProtocol:Anagrafe Protocollo Trovata. ID: " + senderID);
            } else {
                log.debug("EGrammataProtocol:doProtocol:Anagrafe Protocollo NON Trovata");
                senderID = "";
            }
        }
        return senderID;
    }

    private EGrammataFascicoloBean getFascicoloBean(String xml) throws XPathExpressionException {
        InputSource source = new InputSource(new StringReader(xml));
        XPath xpath = XPathFactory.newInstance().newXPath();
        EGrammataFascicoloBean bean = new EGrammataFascicoloBean();
        String idFascicolo = xpath.evaluate("//Fascicolo/IdFascicolo", source);
        bean.setIdFascicolo(idFascicolo);
        source = new InputSource(new StringReader(xml));
        String annoFascicolo = xpath.evaluate("//Fascicolo/AnnoFasc", source);
        bean.setAnnoFascicolo(annoFascicolo);
        source = new InputSource(new StringReader(xml));
        String numeroFascicolo = xpath.evaluate("//Fascicolo/NumeroFascicolo", source);
        bean.setNumeroFascicolo(numeroFascicolo);
        source = new InputSource(new StringReader(xml));
        String numeroSottoFascicolo = xpath.evaluate("//Fascicolo/NumSottofasc", source);
        bean.setNumeroSottoFascicolo(numeroSottoFascicolo);
        return bean;
    }

    @Nullable
    public DocumentoProtocolloResponse findByEstremiProtocolloGenerale(Integer annoRiferimento, String numeroProtocollo, HashMap<String, Object> configurazioneProtocollo) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("<?xml version=\"1.0\" ?>")
                    .append("<!DOCTYPE RicercaProtocollo PUBLIC \"\" \"http://172.28.20.145:8088/protorerProd/includes/DTD/RicercaProtocollo.dtd\" >")
                    .append("<RicercaProtocollo versione='2005-01-31' xml:lang='it'>")
                    .append("<IdPostazLavoro>").append(configurazioneProtocollo.get("ID_POSTAZ_LAVORO")).append("</IdPostazLavoro>")
                    .append("<RegPrimaria>")
                    .append("<EstremiReg>")
                    .append("<Sigla>").append("PG").append("</Sigla>")
                    .append("<Anno>").append(annoRiferimento).append("</Anno>")
                    .append("<Nro>").append(numeroProtocollo).append("</Nro>")
                    .append("</EstremiReg>")
                    .append("</RegPrimaria>")
                    .append("</RicercaProtocollo>");
            String addressWSProt = (String) configurazioneProtocollo.get(EGrammata.INDIRIZZO_WS_RICERCA_PROTOCOLLO);
            String codEnte = (String) configurazioneProtocollo.get("CODICE_ENTE");
            String username = (String) configurazioneProtocollo.get("USERNAME");
            String password = (String) configurazioneProtocollo.get("PASSWORD");
            String timeoutString = (String) configurazioneProtocollo.get("TIMEOUT");
            Integer timeout = timeoutString != null & !"".equals(timeoutString) ? Integer.valueOf(timeoutString) : null;
            String ip = InetAddress.getLocalHost().getHostAddress();
            String xml = sb.toString();
            log.debug("EGrammataProtocol:findByEstremiProtocolloGenerale: Ricerca protocollo \n" + xml);
            String xmlHash = EGrammataUtils.hashXML(xml, password);
            log.debug("EGrammataProtocol:findByEstremiProtocolloGenerale: Hash della ricerca \n" + xmlHash);
            WSRicercaProtocolloService service = new WSRicercaProtocolloService_Impl();
            WSRicercaProtocollo_Stub stub = (WSRicercaProtocollo_Stub) service.getWSRicercaProtocollo(timeout);
            stub._setProperty(javax.xml.rpc.Stub.ENDPOINT_ADDRESS_PROPERTY, addressWSProt);
            String risultato = stub.ricerca(codEnte, username, password, ip, WSUtils.encodeXml(xml), xmlHash);
            risultato = WSUtils.decodeXml(risultato);
            log.debug("EGrammataProtocol:findByEstremiProtocolloGenerale: Risposta \n" + risultato);
            JAXBContext jaxbContext = JAXBContext.newInstance(RisultatoRicerca.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            RisultatoRicerca risultatoRicerca = (RisultatoRicerca) jaxbUnmarshaller.unmarshal(new StringReader(risultato));
            DocumentoProtocolloResponse response = serializeRisultatoRicerca(risultatoRicerca);
            return response;
        } catch (Exception ex) {
            log.error("GrammataProtocol:findByEstremiProtocolloGenerale. Errore eseguendo la ricerca del protocollo", ex);
            return null;
        }
    }

    private DocumentoProtocolloResponse serializeRisultatoRicerca(RisultatoRicerca risultatoRicerca) throws ParseException {
        DocumentoProtocolloResponse response = null;
        if (risultatoRicerca != null && !risultatoRicerca.getDocumento().isEmpty()) {
            Documento p = risultatoRicerca.getDocumento().get(0);
            response = new DocumentoProtocolloResponse();
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            EstremiReg estremi = p.getRegPrimaria().getEstremiReg();
            response.setFascicolo(p.getProgrFasc());
            if (!Strings.isNullOrEmpty(p.getDtAperturaFasc())) {
                response.setDataCreazioneFascicolo(df.parse(p.getDtAperturaFasc()));
            }
            response.setAnnoProtocollo(estremi.getAnno());
            response.setAnnoFascicolo(p.getAnnoFasc());
            response.setCodRegistro(estremi.getSigla());
            response.setNumeroProtocollo(estremi.getNro());
            String dataArrivo = p.getDtReg();
            response.setDataProtocollo(df.parse(dataArrivo));
            response.setDestinatario(p.getDestinatari());
            response.setMittenti(p.getMittenti());
            response.setOggetto(p.getOggetto());
            List<SoggettoProtocollo> soggetti = null;
            if (p.getNominativo() != null && !p.getNominativo().isEmpty()) {
                soggetti = new ArrayList<SoggettoProtocollo>();
                for (Nominativo n : p.getNominativo()) {
                    SoggettoProtocollo s = new SoggettoProtocollo();
                    s.setCodiceFiscale(n.getCodFis());
                    s.setPartitaIva(n.getParIva());
                    s.setCognome(n.getCognome());
                    s.setNome(n.getNome());
                    s.setDenominazione(n.getRagioneSociale());
                    s.setTipoPersona("P".equalsIgnoreCase(n.getFlgTpAnag()) ? CrossProtocolEnvelope.FISICA : CrossProtocolEnvelope.GIURIDICA);
                    soggetti.add(s);
                }
            }
            response.setSoggetti(soggetti);
            //CAMPI NON GESTITI
            //GLI ALLEGATI RITORNATI DAL PROTOCOLLO SONO SOLO DESCRITTIVI
            response.setAllegati(null);
            response.setAllegatoOriginale(null);
            response.setIdDocumento(null);
            response.setTipoDocumento(null);
        }
        return response;
    }

    private String generateXmlRicercaAnagrafica(String codiceFiscale, String partitaIva, String nominativo, String denominazione) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" ?><RicercaAnagrafica versione=\"2004-01-19\" xml:lang=\"it\"><DatiAnag>");
        if (!Strings.isNullOrEmpty(codiceFiscale)) {
            sb.append("<Codice>");
            sb.append("<CodiceFiscale>").append(codiceFiscale).append("</CodiceFiscale>");
            sb.append("</Codice>");
        } else if (!Strings.isNullOrEmpty(partitaIva)) {
            sb.append("<Codice>");
            sb.append("<PartitaIva>").append(partitaIva).append("</PartitaIva>");
            sb.append("</Codice>");
        } else {
            String ricerca = nominativo;
            if (Strings.isNullOrEmpty(nominativo)) {
                ricerca = denominazione;
            }
            sb.append("<Nominativo>").append(ricerca.toUpperCase()).append("</Nominativo>");
        }
        sb.append("</DatiAnag></RicercaAnagrafica>");
        return sb.toString();
    }

    public EGrammataFascicoloBean createFascicolo(String oggettoFascicolo, HashMap<String, Object> configurazioneProtocollo) {
        try {
            String xml = getXmlCreazioneFascicolo(oggettoFascicolo, configurazioneProtocollo);
            String addressWSProt = (String) configurazioneProtocollo.get(EGrammata.INDIRIZZO_WS_INSERIMENTO_FASCICOLO);
            String codEnte = (String) configurazioneProtocollo.get("CODICE_ENTE");
            String username = (String) configurazioneProtocollo.get("USERNAME");
            String password = (String) configurazioneProtocollo.get("PASSWORD");
            String timeoutString = (String) configurazioneProtocollo.get("TIMEOUT");
            Integer timeout = timeoutString != null & !"".equals(timeoutString) ? Integer.valueOf(timeoutString) : null;
            String ip = InetAddress.getLocalHost().getHostAddress();
            log.debug("EGrammataProtocol:getWSInserimentoFasc: Inserimento fascicolo \n" + xml);
            String xmlHash = EGrammataUtils.hashXML(xml, password);
            log.debug("EGrammataProtocol:getWSInserimentoFasc: Hash dell'inserimento \n" + xmlHash);

            WSInserimentoFascService service = new WSInserimentoFascService_Impl();
            WSInserimentoFasc_Stub stub = (WSInserimentoFasc_Stub) service.getWSInserimentoFasc(timeout);
            stub._setProperty(javax.xml.rpc.Stub.ENDPOINT_ADDRESS_PROPERTY, addressWSProt);
            String risultato = stub.service(codEnte, username, password, ip, WSUtils.encodeXml(xml), xmlHash);
            risultato = WSUtils.decodeXml(risultato);
            log.debug("EGrammataProtocol:getWSInserimentoFasc: Risposta \n" + risultato);
            JAXBContext jaxbContext = JAXBContext.newInstance(it.wego.cross.client.xml.rispostainsfasc.RispostaInsFasc.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            it.wego.cross.client.xml.rispostainsfasc.RispostaInsFasc risultatoRicerca = (it.wego.cross.client.xml.rispostainsfasc.RispostaInsFasc) jaxbUnmarshaller.unmarshal(new StringReader(risultato));
            it.wego.cross.client.xml.rispostainsfasc.Fascicolo fasc = risultatoRicerca.getFascicolo();
            EGrammataFascicoloBean fascicolo = new EGrammataFascicoloBean();
            fascicolo.setAnnoFascicolo(fasc.getAnnoFascicolo());
            fascicolo.setIdFascicolo(fasc.getIdFascicolo());
            fascicolo.setNumeroFascicolo(fasc.getNumeroFascicolo());
            fascicolo.setNumeroSottoFascicolo(fasc.getNumeroSottofascicolo());
            return fascicolo;
        } catch (Exception ex) {
            log.error("EGrammataProtocol:getWSInserimentoFasc. Errore eseguendo la creazione del fascicolo", ex);
            return null;
        }
    }

    private String getXmlCreazioneFascicolo(String oggettoFascicolo, HashMap<String, Object> configurazioneProtocollo) throws JAXBException {
        it.wego.cross.client.xml.inseriscifasciolo.InserimentoFasc addFasc = new it.wego.cross.client.xml.inseriscifasciolo.InserimentoFasc();
        it.wego.cross.client.xml.inseriscifasciolo.Dati dati = new it.wego.cross.client.xml.inseriscifasciolo.Dati();
        dati.setIdUoIn((String) configurazioneProtocollo.get(EGrammata.ID_UO_IN));
        //Fisso a F, ovvero crea un fascicolo. Se volessi un sottofascicolo metterei S
        dati.setFlgFSIn("F");
        //Id. della classificazione del fascicolo da creare. (obblig. se FlgFSIn='F')
        dati.setIdTitolazioneIn((String) configurazioneProtocollo.get(EGrammata.ID_TITOLAZIONE));
        dati.setTxtOggIn(oggettoFascicolo);
        addFasc.setDati(dati);
        JAXBContext jaxbContext = JAXBContext.newInstance(it.wego.cross.client.xml.inseriscifasciolo.InserimentoFasc.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        StringWriter writer = new StringWriter();
        marshaller.marshal(addFasc, writer);
        String xmlRichiesta = writer.toString();
        return xmlRichiesta;
    }

    private String getMessaggio(String xmlToAnalize) {
        return StringUtils.substringBetween(xmlToAnalize, "<Messaggio>", "</Messaggio>");
    }

    private String cleanTagMessaggio(String xmlToClean) {
        String toNormalize = getMessaggio(xmlToClean);
        String normalized = StringEscapeUtils.escapeXml(toNormalize);
        String clean = StringUtils.replace(xmlToClean, toNormalize, normalized);
        return clean;
    }

}
