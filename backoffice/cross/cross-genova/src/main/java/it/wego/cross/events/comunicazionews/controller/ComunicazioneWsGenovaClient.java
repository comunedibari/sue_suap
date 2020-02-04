/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.events.comunicazionews.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.rmi.RemoteException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;

import it.wego.cross.constants.ConfigurationConstants;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.genova.edilizia.client.stub.SueServiceLocator;
import it.wego.cross.genova.edilizia.client.stub.Sue_PortType;
import it.wego.cross.genova.edilizia.client.xml.RispostaBO;
import it.wego.cross.service.ConfigurationService;
import it.wego.cross.utils.Utils;

/**
 *
 * @author giuseppe
 */
@Component
public class ComunicazioneWsGenovaClient {

    private static Logger log = LoggerFactory.getLogger("plugin");
    @Autowired
    private ConfigurationService configurationService;

    public RispostaBO inoltraComunicazione(String richiesta, Enti ente, LkComuni comune) {
        try {
            String endpoint = configurationService.getCachedPluginConfiguration(ConfigurationConstants.ENDPOINT_BO_COMUNICAZIONI_CONFIGURATION, ente.getIdEnte(), comune.getIdComune());
            log.info("Endpoint: " + endpoint);
            log.debug("Richiesta: " + richiesta);
            SueServiceLocator sueService = new SueServiceLocator();
            sueService.setSueEndpointAddress(endpoint);
            Sue_PortType sue = sueService.getSue();
//            ((javax.xml.rpc.Stub) sue)._setProperty("javax.xml.rpc.service.endpoint.address", endpoint);
            String result = sue.wsEvento(richiesta);
            log.info("Risposta: " + result);
            RispostaBO esito = getEsito(result);
            return esito;
        } catch (JAXBException ex) {
            log.error("Errore cercando di analizzare la risposta", ex);
            return null;
        } catch (RemoteException ex) {
            log.error("Si è verificato un errore cercando di contattare il webservice", ex);
            return null;
        } catch (Exception ex) {
            log.error("Errore aggiornando la pratica", ex);
            return null;
        }
    }

    public RispostaBO inviaPratica(String xmlPratica, Enti ente, LkComuni comune) throws RemoteException, JAXBException, Exception {
        String endpoint = configurationService.getCachedPluginConfiguration(ConfigurationConstants.ENDPOINT_BO_CONFIGURATION, ente.getIdEnte(), comune.getIdComune());
        String xmlToSend = applayTransformXsl(xmlPratica, ente);
        log.info("Endpoint: " + endpoint);
        log.debug("XML Pratica: " + xmlToSend);
        SueServiceLocator sueService = new SueServiceLocator();
        sueService.setSueEndpointAddress(endpoint);
        Sue_PortType sue = sueService.getSue();
        String result = sue.wsBackOffice(xmlToSend);
        log.info("Risposta: " + result);
        RispostaBO esito = getEsito(result);
        return esito;
    }

    private RispostaBO getEsito(String result) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(RispostaBO.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        RispostaBO risposta = (RispostaBO) unmarshaller.unmarshal(new StringReader(result));
        return risposta;
    }

    private String applayTransformXsl(String xmlToTransform, Enti ente) throws Exception {
        String xmlOutput = null;
        InputStream bais = null;
        try {
            String templateBase64String = configurationService.getCachedPluginConfiguration(ConfigurationConstants.XSLT_BO_CONFIGURATION, Integer.valueOf(ente.getIdEnte()), null);
            if (!Utils.e(templateBase64String)) {
                Preconditions.checkArgument(templateBase64String != null && !templateBase64String.isEmpty(), "Impossibile trovare il template XSL per trasformare la pratica. Verificare la proprieta '%s' per l'ente '%s'", ConfigurationConstants.XSLT_BO_CONFIGURATION, ente.getIdEnte());

                InputStream template = new ByteArrayInputStream(Base64.decodeBase64(templateBase64String.getBytes("UTF-8")));

//            InputStream template = this.getClass().getClassLoader().getResourceAsStream(templateName);
                TransformerFactory factory = TransformerFactory.newInstance();
                Transformer transformer = factory.newTransformer(new StreamSource(template));
                transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                ByteArrayOutputStream writer = new ByteArrayOutputStream();
                bais = new ByteArrayInputStream(xmlToTransform.getBytes("UTF-8"));
                transformer.transform(new StreamSource(bais), new StreamResult(writer));
                xmlOutput = new String(writer.toByteArray());
            } else {
                xmlOutput = xmlToTransform;
            }
        } catch (TransformerException ex) {
            throw new TransformerException("Errore eseguendo la trasformazione XSL per generare l'anagrafica", ex);
        } catch (Exception ex) {
            throw new Exception("Errore eseguendo la trasformazione XML", ex);
        } finally {
            if (bais != null) {
                bais.close();
            }
        }
        return xmlOutput;
    }
}
