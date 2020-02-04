/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.aurigasync.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.AttachmentPart;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import it.wego.cross.aurigasync.action.AurigaSyncAction;
import it.wego.cross.client.stub.getmetadata.WSGetMetadataUdServiceLocator;
import it.wego.cross.client.stub.getmetadata.WSGetMetadataUdSoapBindingStub;
import it.wego.cross.client.stub.getmetadata.WSGetMetadataUd_PortType;
import it.wego.cross.client.xml.getmetadata.AllegatoUDType;
import it.wego.cross.client.xml.getmetadata.DatiUD;
import it.wego.cross.client.xml.getmetadata.EstremiRegNumType;
import it.wego.cross.client.xml.getmetadata.EstremiXIdentificazioneUDType;
import it.wego.cross.constants.Auriga;
import it.wego.cross.dao.AllegatiDaAggiornareDao;
import it.wego.cross.dao.ConfigurationDao;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.entity.Allegati;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.PraticheEventiAllegati;
import it.wego.cross.utils.AurigaUtils;
import it.wego.cross.utils.Utils;

/**
 *
 * @author Giuseppe
 */
@Service
public class AurigaSyncService {

    private static final Logger general = LoggerFactory.getLogger("aurigasync");
    private static final Logger notupdated = LoggerFactory.getLogger("auriganotupdated");
    private static final Logger updated = LoggerFactory.getLogger("aurigaupdated");

    private int fileNonTrovati = 0;
    private int fileTrovati = 0;
    private int fileAnalizzati = 0;

    @Autowired
    private ConfigurationDao configurationDao;
    @Autowired
    private AllegatiDaAggiornareDao allegatiDaAggiornareDao;
    @Autowired
    private AurigaSyncAction aurigaSyncAction;
    @Autowired
    private PraticaDao praticaDao;

    public String getConfigurationValue(String key, Enti ente, LkComuni comune) {
        String value = configurationDao.getConfigurationValue(key, ente, comune);
        if (Strings.isNullOrEmpty(value)) {
            value = configurationDao.getConfigurationValue(key, ente, null);
            if (Strings.isNullOrEmpty(value)) {
                value = configurationDao.getConfigurationValue(key, null, null);
            }
        }
        return value;
    }

    public void startSynchronization(String startDateString, String endDateString, boolean deleteFile, boolean simulate) throws ParseException, IOException {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate = df.parse(startDateString);
        Date endDate = df.parse(endDateString);
        syncronize(startDate, endDate, deleteFile, simulate);
        general.info("File analizzati: " + fileAnalizzati);
        general.info("File trovati: " + fileTrovati);
        general.info("File non trovati: " + fileNonTrovati);
    }

    private void syncronize(Date startDate, Date endDate, boolean deleteFile, boolean simulate) throws IOException {
        Long eventiDaAggiornare = countEventiConAllegatiDaAggiornare(startDate, endDate);
        general.info("Trovati " + eventiDaAggiornare + " eventi con allegati da aggiornare");
        List<Integer> praticheEventiConAllegatiDaAggiornare = getEventiConAllegatiDaAggiornare(startDate, endDate);
        String pathFileToAppend = getPathFileToAppend();
        for (Integer idEvento : praticheEventiConAllegatiDaAggiornare) {
            PraticheEventi evento = praticaDao.getDettaglioPraticaEvento(idEvento);
            Integer idPraticaEvento = evento.getIdPraticaEvento();
            general.info("###############################################");
            general.info("Aggiorno gli allegati associati al protocollo " + evento.getProtocollo() + " [ID_PRATICA_EVENTO " + idPraticaEvento + "]");
            List<PraticheEventiAllegati> allegatiEvento = evento.getPraticheEventiAllegatiList();
            try {
                DatiUD metadati = getMetadatiPerEvento(evento);
                for (PraticheEventiAllegati allegato : allegatiEvento) {
                    //Inserisco il controllo ma non dovrebbero esserci casi in cui un prticaEventoAllegato sia senza allegato collegato
                    Allegati attachment = allegato.getAllegati();
                    if (attachment != null && (!Strings.isNullOrEmpty(attachment.getPathFile()) || attachment.getFile() != null)) {
                        String fileName = attachment.getNomeFile();
                        fileAnalizzati++;
                        try {
//                                for (String extension : supportedExtensions) {
//                                    if (fileName.endsWith(extension)) {
//                                        String newExtension = metadataMap.get(extension);
//                                        fileName = fileName.replace(extension, newExtension);
//                                    }
//                                }
//                                log.debug("Aggiorno il file " + fileName + "...");
                            String allegatoPrincipale = metadati.getVersioneElettronica().getNomeFile();
                            if (fileName.equalsIgnoreCase(allegatoPrincipale)) {
                                //L'allegato è l'allegato principale della comunicazione
                                aggiornaFile(evento, pathFileToAppend, fileName, allegato, deleteFile, simulate);
                                fileTrovati++;
                            } else {
                                List<AllegatoUDType> allegatiMetadata = metadati.getAllegatoUD();
                                if (allegatiMetadata != null && !allegatiMetadata.isEmpty()) {
                                    boolean trovato = findInAurigaAttachmentMetadata(allegatiMetadata, fileName);
                                    if (!trovato) {
//                                        String fileNameVariante = getPossibileVariante(fileName);
//                                        boolean trovataVariante = findInAurigaAttachmentMetadata(allegatiMetadata, fileNameVariante);
//                                        if (trovataVariante) {
//                                            aggiornaFile(evento, fileNameVariante, allegato, deleteFile, simulate);
//                                            fileTrovati++;
//                                        } else {
                                        fileNonTrovati++;
                                        general.info(fileName + " non trovato [ID PRATICA EVENTO " + idPraticaEvento + "]");
                                        notupdated.info(fileName + " non trovato [ID PRATICA EVENTO " + idPraticaEvento + "]");
//                                        }
                                    } else {
                                        aggiornaFile(evento, pathFileToAppend, fileName, allegato, deleteFile, simulate);
                                        fileTrovati++;
                                    }
                                } else {
                                    general.info("Nessun allegato su AURIGA per [PROTOCOLLO: " + evento.getProtocollo() + "] - [ID PRATICA EVENTO: " + idPraticaEvento + "]");
                                    notupdated.info("Nessun allegato su AURIGA per [PROTOCOLLO: " + evento.getProtocollo() + "] - [ID PRATICA EVENTO: " + idPraticaEvento + "]");
                                }
                            }
                        } catch (Exception ex) {
                            general.error(fileName + " [ID PRATICA EVENTO: " + idPraticaEvento + "] non è stato aggiornato a seguito di un errore. Continuo con il prossimo allegato", ex);
                            notupdated.error(fileName + " [ID PRATICA EVENTO: " + idPraticaEvento + "] non è stato aggiornato a seguito di un errore.");
                        }
                    }
                }
            } catch (Exception ex1) {
                general.error("Non è stato possibile recuperare i metadati per l'evento [ID PRATICA EVENTO: " + idPraticaEvento + ". Continuo con il prossimo evento", ex1);
            }
        }
    }

    private boolean findInAurigaAttachmentMetadata(List<AllegatoUDType> allegatiMetadata, String fileName) throws UnsupportedEncodingException {
        boolean trovato = false;
        for (AllegatoUDType allegatoMetadata : allegatiMetadata) {
            general.debug("Cerco tra gli allegati di Auriga ritornati dalla chiamata al servizio Metadati");
            if (allegatoMetadata.getVersioneElettronica() != null) {
                String allegatoAurigaFileName = allegatoMetadata.getVersioneElettronica().getNomeFile();
                general.debug(allegatoAurigaFileName + " [AURIGA] = " + fileName + " [CROSS]");
                if (allegatoAurigaFileName.equalsIgnoreCase(fileName)) {
                    trovato = true;
                    break;
                }
            }
        }
        return trovato;
    }

    private List<Integer> getEventiConAllegatiDaAggiornare(Date startDate, Date endDate) {
        return allegatiDaAggiornareDao.findPraticheEventiConAllegatiDaAggiornare(startDate, endDate);
    }

    private Long countEventiConAllegatiDaAggiornare(Date startDate, Date endDate) {
        return allegatiDaAggiornareDao.countPraticheEventiConAllegatiDaAggiornare(startDate, endDate);
    }

    private DatiUD getMetadatiPerEvento(PraticheEventi evento) throws Exception {
        Pratica pratica = evento.getIdPratica();
        LkComuni comune = pratica.getIdComune() != null ? pratica.getIdComune() : null;
        Enti ente = pratica.getIdProcEnte().getIdEnte();
        WSGetMetadataUdServiceLocator locator = new WSGetMetadataUdServiceLocator();
        String addressWsDoc = getConfigurationValue(Auriga.ENDPOINT_RICERCA_METADATA, ente, comune);
        locator.setWSGetMetadataUdEndpointAddress(addressWsDoc);
        WSGetMetadataUd_PortType port = locator.getWSGetMetadataUd();
        String codiceApplicazione = getConfigurationValue(Auriga.COD_APPLICAZIONE, ente, comune);
        String codiceIstanza = getConfigurationValue(Auriga.COD_ISTANZA, ente, comune);
        String username = getConfigurationValue(Auriga.DOCUMENTALE_USERNAME, ente, comune);
        String password = getConfigurationValue(Auriga.DOCUMENTALE_PASSWORD, ente, comune);
        String[] protocolloSplitted = evento.getProtocollo().split("/");
        String registro = protocolloSplitted[0];
        String anno = protocolloSplitted[1];
        String numero = protocolloSplitted[2];
        String xml = getXmlRecuperoMetadati(registro, anno, numero);
        general.debug("Auriga:getMetadati: XML da inviare al documentale: \n" + xml);
        String xmlHash = AurigaUtils.hashXML(xml, "");
        general.debug("Auriga:getMetadati:Hash dell'XML da inviare al Documentale: \n" + xmlHash);
        String risultato = port.service(codiceApplicazione, codiceIstanza, username, password, xml, xmlHash);
        risultato = AurigaUtils.decodeXml(risultato);
        general.debug("Auriga:getMetadati:Risposta del documentale: \n" + risultato);
        Object[] allegati = ((WSGetMetadataUdSoapBindingStub) port).getAttachments();
        general.debug("Auriga:getMetadati:Numero di allegati: " + allegati.length);
        if (allegati.length < 1) {
            general.error("Errore: non è stata trovata l'informazione relativa ai metadati per l'evento [ID PRATICA EVENTO " + evento.getIdPraticaEvento() + "] [PROTOCOLLO " + evento.getProtocollo() + "]");
            return null;
        } else {
            //Il secondo attachment contiene le informazioni sui metadati
            DataHandler allegatoFisico = ((AttachmentPart) allegati[0]).getDataHandler();
            if (allegatoFisico != null) {
                general.debug("Auriga:getMetadati: Torvati i metadati");
                InputStream inputStream = allegatoFisico.getInputStream();
                byte[] content = IOUtils.toByteArray(inputStream);
                String metadatiXml = new String(content, Charset.forName("UTF-8"));
                general.debug("XML con i metadati:\n" + metadatiXml);
                JAXBContext context = JAXBContext.newInstance(it.wego.cross.client.xml.getmetadata.DatiUD.class
                );
                Unmarshaller um = context.createUnmarshaller();
                DatiUD metadati = (DatiUD) um.unmarshal(new StringReader(metadatiXml));
                return metadati;
            } else {
                general.error("Errore leggendo il file fisico con i metadati per l'evento [ID PRATICA EVENTO " + evento.getIdPraticaEvento() + "] [PROTOCOLLO " + evento.getProtocollo() + "]");
                return null;
            }
        }
    }

    private String getXmlRecuperoMetadati(String registro, String anno, String numero) throws Exception {
        EstremiXIdentificazioneUDType estremiIdentificazioneUD = new EstremiXIdentificazioneUDType();
        EstremiRegNumType estremiReg = new EstremiRegNumType();
        estremiReg.setAnnoReg(Integer.valueOf(anno));
        estremiReg.setNumReg(Integer.valueOf(numero));
        estremiIdentificazioneUD.setEstremiRegNum(estremiReg);
        it.wego.cross.client.xml.getmetadata.ObjectFactory of = new it.wego.cross.client.xml.getmetadata.ObjectFactory();
        JAXBElement<EstremiXIdentificazioneUDType> root = of.createEstremiXIdentificazioneUD(estremiIdentificazioneUD);
        String xml = Utils.marshall(root, EstremiXIdentificazioneUDType.class);
        return xml;

    }

    private Map<String, String> getAurigaMetadataMap() throws IOException {
        InputStream is = getClass().getResourceAsStream("/auriga_mime_type.properties");
        Properties props = new Properties();
        props.load(is);
        ImmutableMap<String, String> map = Maps.fromProperties(props);
        return map;
    }

    private void aggiornaFile(PraticheEventi evento, String path, String fileName, PraticheEventiAllegati allegato, boolean deleteFile, boolean simulate) throws Exception {
        Integer idPraticaEvento = evento.getIdPraticaEvento();
        try {
            if (!simulate) {
                String protocollo = allegato.getPraticheEventi().getProtocollo();
                String[] protocolloSplitted = protocollo.split("/");
                Integer annoProtocollo = Integer.valueOf(protocolloSplitted[1]);
                Integer numeroProtocollo = Integer.valueOf(protocolloSplitted[2]);
                String xmlFileEsterno = getXmlPerExtractOne(annoProtocollo, numeroProtocollo, fileName);
                Allegati allegatoDaAggiornare = allegato.getAllegati();
                aurigaSyncAction.aggiornaRiferimentiAllegato(evento, path, allegatoDaAggiornare, xmlFileEsterno, deleteFile);
            }
            updated.info(fileName + " aggiornato [ID PRATICA EVENTO " + idPraticaEvento + "]");
            general.info(fileName + " aggiornato [ID PRATICA EVENTO " + idPraticaEvento + "]");
        } catch (Exception ex) {
            general.error("Impossibile aggiornare il file " + fileName + " [ID PRATICA EVENTO " + idPraticaEvento + "]", ex);
            notupdated.info(fileName + " non aggiornato [ID PRATICA EVENTO " + idPraticaEvento + "]");
            //Procedo con gli altri file
        }
    }

//    private String getPossibileVariante(String fileName) throws UnsupportedEncodingException {
//        if (fileName.contains(".dxf")) {
//            fileName = fileName.replace(".dxf", ".dxf.txt");
//        }
//        if (fileName.contains("à")) {
//            fileName = fileName.replace("à", new String("à".getBytes("UTF-8")));
//        }
//        return fileName;
//    }
    private String getPathFileToAppend() throws IOException {
        InputStream io = null;
        String path = "";
        try {
            Properties prop = new Properties();
            String propFileName = "config.properties";
            io = getClass().getClassLoader().getResourceAsStream(propFileName);
            prop.load(io);
            path = prop.getProperty("absolute.path");
        } catch (IOException ex) {

        } finally {
            if (io != null) {
                io.close();
            }
        }
        return path;
    }

    private String getXmlPerExtractOne(Integer annoProtocollo, Integer numeroProtocollo, String fileName) throws Exception {
        it.wego.cross.client.xml.extractone.EstremiXidentificazioneVerDocType estremi = new it.wego.cross.client.xml.extractone.EstremiXidentificazioneVerDocType();
        it.wego.cross.client.xml.extractone.EstremiXIdentificazioneUDType estremiUnitaDocumentaria = new it.wego.cross.client.xml.extractone.EstremiXIdentificazioneUDType();
        it.wego.cross.client.xml.extractone.EstremiRegNumType estremiReg = new it.wego.cross.client.xml.extractone.EstremiRegNumType();
        estremiReg.setAnnoReg(annoProtocollo);
        estremiReg.setNumReg(numeroProtocollo);
        estremiUnitaDocumentaria.setEstremiRegNum(estremiReg);
        estremi.setEstremiXIdentificazioneUD(estremiUnitaDocumentaria);
        it.wego.cross.client.xml.extractone.EstremiXIdentificazioneFileUDType estremiPerFile = new it.wego.cross.client.xml.extractone.EstremiXIdentificazioneFileUDType();
        estremiPerFile.setNomeFile(fileName);
        estremi.setEstremixIdentificazioneFileUD(estremiPerFile);
        it.wego.cross.client.xml.extractone.ObjectFactory of = new it.wego.cross.client.xml.extractone.ObjectFactory();
        JAXBElement<it.wego.cross.client.xml.extractone.EstremiXidentificazioneVerDocType> root = of.createFileUDToExtract(estremi);
        String xml = Utils.marshall(root, it.wego.cross.client.xml.extractone.EstremiXidentificazioneVerDocType.class);
        return xml;
    }
}
