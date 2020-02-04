/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.plugins.documenti;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.rpc.ServiceException;
import javax.xml.soap.AttachmentPart;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Strings;

import it.wego.cross.beans.AllegatoBean;
import it.wego.cross.client.stub.addud.WSAddUd;
import it.wego.cross.client.stub.addud.WSAddUdServiceLocator;
import it.wego.cross.client.stub.addud.WSAddUdSoapBindingStub;
import it.wego.cross.client.stub.checkin.WSCheckIn;
import it.wego.cross.client.stub.checkin.WSCheckInServiceLocator;
import it.wego.cross.client.stub.extractmulti.WSExtractMultiServiceLocator;
import it.wego.cross.client.stub.extractmulti.WSExtractMultiSoapBindingStub;
import it.wego.cross.client.stub.extractone.WSExtractOne;
import it.wego.cross.client.stub.extractone.WSExtractOneServiceLocator;
import it.wego.cross.client.stub.extractone.WSExtractOneSoapBindingStub;
import it.wego.cross.client.stub.trovadocfolder.WSTrovaDocFolder;
import it.wego.cross.client.stub.trovadocfolder.WSTrovaDocFolderServiceLocator;
import it.wego.cross.client.stub.trovadocfolder.WSTrovaDocFolderSoapBindingStub;
import it.wego.cross.client.xml.addud.AllegatoUDType;
import it.wego.cross.client.xml.addud.NewUD;
import it.wego.cross.client.xml.addud.OutputUD;
import it.wego.cross.client.xml.addud.VersioneElettronicaType;
import it.wego.cross.client.xml.extractone.BaseOutputWS;
import it.wego.cross.client.xml.extractone.EstremiRegNumType;
import it.wego.cross.client.xml.extractone.EstremiXIdentificazioneFileUDType;
import it.wego.cross.client.xml.extractone.EstremiXIdentificazioneUDType;
import it.wego.cross.client.xml.extractone.EstremiXidentificazioneVerDocType;
import it.wego.cross.client.xml.extractone.ObjectFactory;
import it.wego.cross.client.xml.trovadocfolder.Lista;
import it.wego.cross.client.xml.trovadocfolder.Lista.Riga;
import it.wego.cross.client.xml.trovadocfolder.Lista.Riga.Colonna;
import it.wego.cross.client.xml.trovadocfolder.TrovaDocFolder;
import it.wego.cross.constants.Auriga;
import it.wego.cross.constants.GenovaConstants;
import it.wego.cross.entity.Allegati;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticheEventiAllegati;
import it.wego.cross.genova.actions.PluginGenovaAction;
import it.wego.cross.plugins.commons.beans.Allegato;
import it.wego.cross.plugins.protocollo.beans.DocumentoProtocolloResponse;
import it.wego.cross.service.ConfigurationService;
import it.wego.cross.service.PraticheServiceImpl;
import it.wego.cross.service.UsefulService;
import it.wego.cross.utils.AurigaUtils;
import it.wego.cross.utils.ByteArrayDataSource;
import it.wego.cross.utils.Utils;
import it.wego.cross.webservices.mypage.fileclient.FileClient;

/**
 *
 * @author giuseppe
 */
public class GestioneAllegatiGenova implements GestioneAllegati {

    private static final Logger log = LoggerFactory.getLogger("plugin");
    @Autowired
    private UsefulService usefulService;
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private PluginGenovaAction pluginGenovaAction;
    @Autowired
    private PraticheServiceImpl praticheServiceImpl;

    @Override
    public void add(Allegati allegato, Enti ente, LkComuni comune) throws Exception {
        //Non faccio niente
    }

    @Override
    public byte[] getFileContent(Allegati allegato, Enti ente, LkComuni comune) throws Exception {
        byte[] content = null;
        if (!Utils.e(allegato.getFile())) {
            content = allegato.getFile();
        }

        if (content == null && !Utils.e(allegato.getPathFile())) {
            File f = new File(allegato.getPathFile());
            InputStream stream = new FileInputStream(f);
            content = IOUtils.toByteArray(stream);
        }

        if (content == null && !Utils.e(allegato.getIdFileEsterno())) {
            //Se ho un ID numerico, allora ho caricato il file tramite WsCheckIn
            //ID è l'identificativo della unità documentale
            if (AurigaUtils.isUploaded(allegato.getIdFileEsterno())) {
                Allegato a = estraiDaDocumentale(allegato.getIdFileEsterno(), ente, comune);
                content = a.getFile();
            } else {
                //Il file è stato caricato passando dal protocollo. Ho già pronto l'XML di richiesta ...
                Allegato a = estraiDaProtocollo(allegato.getIdFileEsterno(), ente, comune);
                content = a.getFile();
            }
        }
        //TODO: rimuovere perché spostato in batch schedulato
        if (content == null) {
            content = getAttachmentContent(allegato, ente, comune);
        }
        return content;
    }

    @Override
    public Allegato getFile(String idFileEsterno, Enti ente, LkComuni comune) throws Exception {
        Allegato result = null;
        try {
            //verifico se posso usare la WSCheckOut...
            if (AurigaUtils.isUploaded(idFileEsterno)) {
                result = estraiDaDocumentale(idFileEsterno, ente, comune);
            } else {
                //... altrimenti uso la WsExtractOne
                result = estraiDaProtocollo(idFileEsterno, ente, comune);
            }
        } catch (Exception ex) {
            log.error("Auriga:errore interrogando il webservice", ex);
            throw new Exception("Errore contattando il servizio documentale", ex);
        }
        return result;
    }

    @Override
    public String uploadFile(Pratica pratica, List<Allegati> allegati) throws Exception {
        try {
//            String endpointRicerca = configurationService.getCachedConfiguration(Auriga.ENDPOINT_CREA_UNITA_DOCUMENTALE, idEnte, idComune);
            String idUd = ricercaUnitaDocumentale(pratica);
            if (!Strings.isNullOrEmpty(idUd)) {
                caricaAllegatiSuUd(idUd, pratica, allegati);
            } else {
                idUd = creazioneUnitaDocumentaria(pratica, allegati);
            }
            return idUd;
        } catch (JAXBException ex) {
            log.error("Errore cercando di convertire il file XML.", ex);
            throw new Exception("Errore cercando di convertire il file XML.", ex);
        } catch (RemoteException ex) {
            log.error("Errore cercando di ccontattare il servizio documentale.", ex);
            throw new Exception("Errore cercando di ccontattare il servizio documentale.", ex);
        } catch (ServiceException ex) {
            log.error("Errore cercando di contattare il servizio documentale.", ex);
            throw new Exception("Errore cercando di ccontattare il servizio documentale.", ex);
        }

    }

    @Override
    public String uploadAttachmentsOnMyPage(Pratica pratica, List<Allegati> allegati) throws Exception {
        Enti ente = pratica.getIdProcEnte().getIdEnte();
        LkComuni comune = pratica.getIdComune();
        Integer idComune = comune == null ? null : comune.getIdComune();
        Integer idEnte = ente == null ? null : ente.getIdEnte();

        String url = configurationService.getCachedConfiguration(GenovaConstants.URL_MYPAGE, idEnte, idComune);
        log.debug("URL MyPage: " + url);
        String nodoFront = pratica.getIdStaging().getIdentificativoProvenienza();
        log.debug("Nodo front: " + nodoFront);
        String idPratica = pratica.getIdentificativoPratica();
        log.debug("Pratica: " + idPratica);
        if (allegati != null && !Utils.e(nodoFront)) {
            for (it.wego.cross.entity.Allegati allegato : allegati) {
                try {
                    byte[] file = getFileContent(allegato, ente, comune);
                    String content = Base64.encodeBase64String(file);
                    log.debug("Carico il file " + allegato.getNomeFile());
                    FileClient client = new FileClient();
                    client.caricaFile(url, nodoFront, idPratica, allegato.getNomeFile(), content);
                } catch (Exception ex) {
                    log.error("Errore cercando si caricare gli allegati su MyPage", ex);
                    throw new Exception("Errore cercando si caricare gli allegati su MyPage", ex);
                }
            }
        }
        return "OK";
    }

    public List<Allegato> getAllAttachments(Integer anno, String numero, Enti ente, LkComuni comune) throws Exception {
        Integer idEnte = ente == null ? null : ente.getIdEnte();
        Integer idComune = comune == null ? null : comune.getIdComune();

        WSExtractMultiServiceLocator wsLocator = new WSExtractMultiServiceLocator();
        String addressWsDoc = configurationService.getCachedConfiguration(Auriga.ENDPOINT_RICERCA_DOCUMENTALE_MULTIPLA, idEnte, idComune);
        wsLocator.setWSExtractMultiEndpointAddress(addressWsDoc);
        WSExtractMultiSoapBindingStub call = (WSExtractMultiSoapBindingStub) wsLocator.getWSExtractMulti();
        String xmlRichiesta = getXmlRecuperoAllegatiDaProtocollo(numero, anno);
        String codiceApplicazione = configurationService.getCachedConfiguration(Auriga.COD_APPLICAZIONE, idEnte, idComune);
        String codiceIstanza = configurationService.getCachedConfiguration(Auriga.COD_ISTANZA, idEnte, idComune);
        String username = configurationService.getCachedConfiguration(Auriga.DOCUMENTALE_USERNAME, idEnte, idComune);
        String password = configurationService.getCachedConfiguration(Auriga.DOCUMENTALE_PASSWORD, idEnte, idComune);
        log.debug("Auriga:getAllAttachments:XML da inviare al Documentale: \n" + xmlRichiesta);
        String xmlHash = AurigaUtils.hashXML(xmlRichiesta, "");
        log.debug("Auriga:getAllAttachments:Hash dell'XML da inviare al Documentale: \n" + xmlHash);
        String risultato = call.service(codiceApplicazione, codiceIstanza, username, password, xmlRichiesta, xmlHash);
        risultato = AurigaUtils.decodeXml(risultato);
        log.debug("Auriga:getAllAttachments:Risposta del documentale: \n" + risultato);
        checkResult(risultato);
        List<Allegato> allegati = new ArrayList<Allegato>();
        Object[] allegatiResponse = ((WSExtractMultiSoapBindingStub) call).getAttachments();
        if (allegatiResponse == null || allegatiResponse.length == 0) {
            log.info("Auriga:getAllAttachments:Nessun allegato trovato associato alla pratica");
            return null;
        } else {
            log.debug("Auriga:getAllAttachments: Numero di allegati trovato: " + allegatiResponse.length);
            //Il secondo attachment è l'esito della chiamata
            DataHandler elencoDeiFileEstratti = ((AttachmentPart) allegatiResponse[0]).getDataHandler();
            Map<Integer, AllegatoBean> allegatiIndividuati = new HashMap<Integer, AllegatoBean>();
            if (elencoDeiFileEstratti != null) {
                log.debug("Auriga:getAllAttachments: Esito della creazione della unita documentale");
                InputStream inputStream = elencoDeiFileEstratti.getInputStream();
                byte[] content = IOUtils.toByteArray(inputStream);
                String esitoXml = new String(content);
                log.debug(esitoXml);
                JAXBContext context = JAXBContext.newInstance(it.wego.cross.client.xml.extractmulti.OutputFilesUD.class);
                Unmarshaller um = context.createUnmarshaller();
                it.wego.cross.client.xml.extractmulti.OutputFilesUD elencoAllegati = (it.wego.cross.client.xml.extractmulti.OutputFilesUD) um.unmarshal(new StringReader(esitoXml));
                List<it.wego.cross.client.xml.extractmulti.OutputFilesUD.DatiFileEstratto> datiFile = elencoAllegati.getDatiFileEstratto();
                for (it.wego.cross.client.xml.extractmulti.OutputFilesUD.DatiFileEstratto datiSingoloFile : datiFile) {
                    AllegatoBean allegato = new AllegatoBean();
                    allegato.setIdAllegato(String.valueOf(datiSingoloFile.getNroAttachment().intValue()));
                    allegato.setDescrizioneOggetto(datiSingoloFile.getDesOggetto());
                    allegato.setNomeFile(String.valueOf(datiSingoloFile.getNomeFile()));
                    allegato.setVersione(String.valueOf(datiSingoloFile.getNroVersione()));
                    allegato.setPrimario(datiSingoloFile.getRelazioneVsUD().getCodId().equalsIgnoreCase("P"));
                    allegatiIndividuati.put(datiSingoloFile.getNroAttachment().intValue(), allegato);
                }
            } else {
                log.error("Auriga:non è stato possibile recuperare il file XML con l'elenco dei file allegati");
                throw new Exception("Non è stato possibile recuperare l'elenco dei file allegati");
            }
            //Il valore 1 coincide con il file di riepilogo, quindi inizio a recuperare gli allegati dal 2 in poi
            for (int i = 1; i < allegatiResponse.length; i++) {
                Allegato allegato = new Allegato();
                DataHandler attach = ((AttachmentPart) allegatiResponse[i]).getDataHandler();
                //devo prendere l'ellegato con id da 2 in poi
                AllegatoBean bean = allegatiIndividuati.get(i + 1);
                allegato.setDescrizione(bean.getDescrizioneOggetto());
                allegato.setNomeFile(bean.getNomeFile());
                byte[] file = AurigaUtils.toBytes(attach);
                allegato.setFile(file);
                allegato.setFileOrigine(bean.isPrimario());
                allegato.setIdEsterno(bean.getIdAllegato());
                allegati.add(allegato);
            }
        }
        return allegati;
    }

    public DocumentoProtocolloResponse valorizzaAllegati(DocumentoProtocolloResponse documentoProtocollo, Enti ente, LkComuni comune) throws Exception {
        List<Allegato> allegatiAllaPratica = getAllAttachments(Integer.valueOf(documentoProtocollo.getAnnoProtocollo()),
                documentoProtocollo.getNumeroProtocollo(), ente, comune);
        List<Allegato> allegati = new ArrayList<Allegato>();
        Allegato principale = null;
        if (allegatiAllaPratica != null && !allegatiAllaPratica.isEmpty()) {
            for (Allegato allegato : allegatiAllaPratica) {
                String stringaIdentificativa = documentoProtocollo.getNumeroProtocollo() + "-" + documentoProtocollo.getAnnoProtocollo() + "-" + allegato.getIdEsterno();
                allegato.setIdEsterno(stringaIdentificativa);
                if (allegato.getFileOrigine()) {
                    principale = allegato;
                } else {
                    allegati.add(allegato);
                }
            }
        }
        documentoProtocollo.setAllegati(allegati);
        documentoProtocollo.setAllegatoOriginale(principale);
        documentoProtocollo.setIdDocumento(null);
        return documentoProtocollo;
    }

    private byte[] getAttachmentContent(Allegati allegato, Enti ente, LkComuni comune) throws Exception {
        byte[] content = null;
        try {
            Integer idComune = comune == null ? null : comune.getIdComune();
            Integer idEnte = ente == null ? null : ente.getIdEnte();

            WSExtractOneServiceLocator wsLocator = new WSExtractOneServiceLocator();
            String addressWsDoc = configurationService.getCachedConfiguration(Auriga.ENDPOINT_RICERCA_DOCUMENTALE_SINGOLA, idEnte, idComune);
            wsLocator.setWSExtractOneEndpointAddress(addressWsDoc);
            WSExtractOne wsExtractOne = wsLocator.getWSExtractOne();
            String codiceApplicazione = configurationService.getCachedConfiguration(Auriga.COD_APPLICAZIONE, idEnte, idComune);
            String codiceIstanza = configurationService.getCachedConfiguration(Auriga.COD_ISTANZA, idEnte, idComune);
            String username = configurationService.getCachedConfiguration(Auriga.DOCUMENTALE_USERNAME, idEnte, idComune);
            String password = configurationService.getCachedConfiguration(Auriga.DOCUMENTALE_PASSWORD, idEnte, idComune);
            String xml = getXmlRecuperoDaProtocollo(allegato);
            log.debug("Auriga:getFileContent:XML da inviare al Documentale: \n" + xml);
            String xmlHash = AurigaUtils.hashXML(xml, "");
            log.debug("Auriga:getFileContent:Hash dell'XML da inviare al Documentale: \n" + xmlHash);
            String risultato = wsExtractOne.service(codiceApplicazione, codiceIstanza, username, password, xml, xmlHash);
            risultato = AurigaUtils.decodeXml(risultato);

            log.debug("Auriga:getFileContent:Risposta del documentale: \n" + risultato);
            Object[] allegati = ((WSExtractOneSoapBindingStub) wsExtractOne).getAttachments();
            log.debug("Auriga:getFileContent:Numero di allegati: " + allegati.length);
            if (allegati.length == 0) {
                log.debug("Auriga:getFileContent:Numero di allegati: " + allegati.length);
            } else {
                //Il secondo attachment è il file fisico da restituire
                DataHandler allegatoFisico = ((AttachmentPart) allegati[1]).getDataHandler();
                if (allegatoFisico != null) {
                    log.debug("Auriga:getFileContent: Trovato un allegato fisico");
                    InputStream inputStream = allegatoFisico.getInputStream();
                    content = IOUtils.toByteArray(inputStream);
                    //Aggiorno i riferimenti dell'allegato
                    aggiornaRiferimentiAllegato(allegato, xml);
                } else {
                    log.error("Auriga:non è stato trovato nessun allegato");
                }
            }
        } catch (Exception ex) {
            log.error("Auriga:errore interrogando il webservice", ex);
            throw new Exception("Errore contattando il servizio documentale", ex);
        }
        return content;
    }

    private Pratica getPratica(Allegati allegato) {
        List<PraticheEventiAllegati> praticheEventiAllegati = allegato.getPraticheEventiAllegatiList();
//        List<PraticheEventi> praticheEventi = allegato.getPraticheEventiList();
        Pratica pratica = null;
        if (praticheEventiAllegati != null && !praticheEventiAllegati.isEmpty()) {
            pratica = praticheEventiAllegati.get(0).getPraticheEventi().getIdPratica();
        }
        return pratica;
    }

    private String getXmlRecuperoDaProtocollo(Allegati allegato) throws Exception {
        Pratica pratica = getPratica(allegato);
        EstremiXidentificazioneVerDocType estremiIdentificazioneVerDoc = new EstremiXidentificazioneVerDocType();
        //Estremi identificazione Unita documentale
        EstremiXIdentificazioneUDType estremiIdentificazioneUD = new EstremiXIdentificazioneUDType();
        EstremiRegNumType estremiProtocollo = new EstremiRegNumType();
        estremiProtocollo.setAnnoReg(pratica.getAnnoRiferimento());
        estremiProtocollo.setCategoriaReg(pratica.getCodRegistro());
        estremiProtocollo.setNumReg(Integer.valueOf(pratica.getProtocollo()));
        estremiIdentificazioneUD.setEstremiRegNum(estremiProtocollo);
        //Estremi identificazione file
        EstremiXIdentificazioneFileUDType estremiIdentificazioneFile = new EstremiXIdentificazioneFileUDType();
        estremiIdentificazioneFile.setNomeFile(allegato.getNomeFile());
        estremiIdentificazioneVerDoc.setEstremiXIdentificazioneUD(estremiIdentificazioneUD);
        estremiIdentificazioneVerDoc.setEstremixIdentificazioneFileUD(estremiIdentificazioneFile);
        ObjectFactory of = new ObjectFactory();
        JAXBElement<EstremiXidentificazioneVerDocType> root = of.createFileUDToExtract(estremiIdentificazioneVerDoc);
        String xml = Utils.marshall(root, EstremiXidentificazioneVerDocType.class);
        return xml;
    }

    private void aggiornaRiferimentiAllegato(Allegati allegato, String xmlRichiesta) throws Exception {
        allegato.setIdFileEsterno("P|" + xmlRichiesta);
        allegato.setFile(null);
        allegato.setPathFile(null);
        log.debug("Aggiorno i riferimenti per l'allegato " + allegato.getId());
        log.debug("Descrizione: " + allegato.getDescrizione());
        log.debug("Id file esterno: " + allegato.getIdFileEsterno());
        log.debug("Nome file: " + allegato.getNomeFile());
        log.debug("Path file: " + allegato.getPathFile());
        log.debug("Tipo file: " + allegato.getTipoFile());
        usefulService.update(allegato);
    }

    /**
     * Nella sezione idfileesterno trovo l'xml per la chiamata al documentale
     *
     * @param xml la richiesta da fare
     * @return allegato da gestire
     */
    private Allegato estraiDaProtocollo(String idFileEsterno, Enti ente, LkComuni comune) throws Exception {
        Allegato result = null;
        try {
            Integer idComune = comune == null ? null : comune.getIdComune();
            Integer idEnte = ente == null ? null : ente.getIdEnte();

            if (idFileEsterno.contains("|")) {
                String xmlRichiesta = idFileEsterno.split("\\|")[1];
                WSExtractOneServiceLocator wsLocator = new WSExtractOneServiceLocator();
                String addressWsDoc = configurationService.getCachedConfiguration(Auriga.ENDPOINT_RICERCA_DOCUMENTALE_SINGOLA, idEnte, idComune);
                wsLocator.setWSExtractOneEndpointAddress(addressWsDoc);
                WSExtractOne wsExtractOne = wsLocator.getWSExtractOne();
                String codiceApplicazione = configurationService.getCachedConfiguration(Auriga.COD_APPLICAZIONE, idEnte, idComune);
                String codiceIstanza = configurationService.getCachedConfiguration(Auriga.COD_ISTANZA, idEnte, idComune);
                String username = configurationService.getCachedConfiguration(Auriga.DOCUMENTALE_USERNAME, idEnte, idComune);
                String password = configurationService.getCachedConfiguration(Auriga.DOCUMENTALE_PASSWORD, idEnte, idComune);
                log.debug("Auriga:getFileContent:XML da inviare al Documentale: \n" + xmlRichiesta);
                String xmlHash = AurigaUtils.hashXML(xmlRichiesta, "");
                log.debug("Auriga:getFileContent:Hash dell'XML da inviare al Documentale: \n" + xmlHash);
                String risultato = wsExtractOne.service(codiceApplicazione, codiceIstanza, username, password, xmlRichiesta, xmlHash);
                risultato = AurigaUtils.decodeXml(risultato);
                log.debug("Auriga:getFileContent:Risposta del documentale: \n" + risultato);
                Object[] allegati = ((WSExtractOneSoapBindingStub) wsExtractOne).getAttachments();
                log.debug("Auriga:getFileContent:Numero di allegati: " + allegati.length);
                if (allegati.length == 0) {
                    log.debug("Auriga:getFileContent:Numero di allegati: " + allegati.length);
                } else {
                    //Il secondo attachment è il file fisico da restituire
                    DataHandler allegatoFisico = ((AttachmentPart) allegati[1]).getDataHandler();
                    if (allegatoFisico != null) {
                        log.debug("Auriga:getFileContent: Trovato un allegato fisico");
                        InputStream inputStream = allegatoFisico.getInputStream();
                        byte[] content = IOUtils.toByteArray(inputStream);
                        result = new Allegato();
                        result.setFile(content);
                        result.setNomeFile(allegatoFisico.getName());
                        result.setTipoFile(allegatoFisico.getContentType());
                    } else {
                        log.error("Auriga:non è stato trovato nessun allegato");
                    }
                }
            } else {
                String[] s = idFileEsterno.split("-");
                String numero = s[0];
                String anno = s[1];
                String index = s[2];
                List<Allegato> allegati = getAllAttachments(Integer.valueOf(anno), numero, ente, comune);
                if (allegati != null && !allegati.isEmpty()) {
                    for (Allegato allegato : allegati) {
                        if (allegato.getIdEsterno().equals(index)) {
                            result = allegato;
                            break;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            log.error("Auriga:errore interrogando il webservice", ex);
            throw new Exception("Errore contattando il servizio documentale", ex);
        }
        return result;
    }

    private Allegato estraiDaDocumentale(String idFileEsterno, Enti ente, LkComuni comune) throws Exception {
        Allegato result = null;
        try {
            Integer idComune = comune == null ? null : comune.getIdComune();
            Integer idEnte = ente == null ? null : ente.getIdEnte();
            String xmlRichiesta = idFileEsterno.split("\\|")[1];
            WSExtractOneServiceLocator wsLocator = new WSExtractOneServiceLocator();
            String addressWsDoc = configurationService.getCachedConfiguration(Auriga.ENDPOINT_RICERCA_DOCUMENTALE_SINGOLA, idEnte, idComune);
            wsLocator.setWSExtractOneEndpointAddress(addressWsDoc);
            WSExtractOne wsExtractOne = wsLocator.getWSExtractOne();
            String codiceApplicazione = configurationService.getCachedConfiguration(Auriga.COD_APPLICAZIONE, idEnte, idComune);
            String codiceIstanza = configurationService.getCachedConfiguration(Auriga.COD_ISTANZA, idEnte, idComune);
            String username = configurationService.getCachedConfiguration(Auriga.DOCUMENTALE_USERNAME, idEnte, idComune);
            String password = configurationService.getCachedConfiguration(Auriga.DOCUMENTALE_PASSWORD, idEnte, idComune);
            log.debug("Auriga:getFileContent:XML da inviare al Documentale: \n" + xmlRichiesta);
            String xmlHash = AurigaUtils.hashXML(xmlRichiesta, "");
            log.debug("Auriga:getFileContent:Hash dell'XML da inviare al Documentale: \n" + xmlHash);
            String risultato = wsExtractOne.service(codiceApplicazione, codiceIstanza, username, password, xmlRichiesta, xmlHash);
            risultato = AurigaUtils.decodeXml(risultato);
            log.debug("Auriga:getFileContent:Risposta del documentale: \n" + risultato);
            Object[] allegati = ((WSExtractOneSoapBindingStub) wsExtractOne).getAttachments();
            log.debug("Auriga:getFileContent:Numero di allegati: " + allegati.length);
            if (allegati.length == 0) {
                log.debug("Auriga:getFileContent:Numero di allegati: " + allegati.length);
            } else {
                //Il secondo attachment è il file fisico da restituire
                DataHandler allegatoFisico = ((AttachmentPart) allegati[1]).getDataHandler();
                if (allegatoFisico != null) {
                    log.debug("Auriga:getFileContent: Trovato un allegato fisico");
                    InputStream inputStream = allegatoFisico.getInputStream();
                    byte[] content = IOUtils.toByteArray(inputStream);
                    result = new Allegato();
                    result.setFile(content);
                    result.setNomeFile(allegatoFisico.getName());
                    result.setTipoFile(allegatoFisico.getContentType());
                } else {
                    log.error("Auriga:non è stato trovato nessun allegato");
                }
            }
        } catch (Exception ex) {
            log.error("Auriga:errore interrogando il webservice", ex);
            throw new Exception("Errore contattando il servizio documentale", ex);
        }
        return result;
    }

    private String ricercaUnitaDocumentale(Pratica pratica) throws ServiceException, NoSuchAlgorithmException, RemoteException, Exception {
        Enti ente = pratica.getIdProcEnte().getIdEnte();
        LkComuni comune = null;
        if (pratica.getIdComune() != null) {
            comune = pratica.getIdComune();
        }
        Integer idComune = comune == null ? null : comune.getIdComune();
        Integer idEnte = ente == null ? null : ente.getIdEnte();
        String endpointRicercaUd = configurationService.getCachedConfiguration(Auriga.ENDPOINT_RICERCA_UNITA_DOCUMENTALE, idEnte, idComune);
        WSTrovaDocFolderServiceLocator wsLocator = new WSTrovaDocFolderServiceLocator();
        wsLocator.setWSTrovaDocFolderEndpointAddress(endpointRicercaUd);
        WSTrovaDocFolder trovaDoc = wsLocator.getWSTrovaDocFolder();
        String xmlRichiesta = getXmlRicercaUd(pratica);
        String codiceApplicazione = configurationService.getCachedConfiguration(Auriga.COD_APPLICAZIONE, idEnte, idComune);
        String codiceIstanza = configurationService.getCachedConfiguration(Auriga.COD_ISTANZA, idEnte, idComune);
        String username = configurationService.getCachedConfiguration(Auriga.DOCUMENTALE_USERNAME, idEnte, idComune);
        String password = configurationService.getCachedConfiguration(Auriga.DOCUMENTALE_PASSWORD, idEnte, idComune);
        log.debug("Auriga:ricercaUnitaDocumentale:XML da inviare al Documentale: \n" + xmlRichiesta);
        String xmlHash = AurigaUtils.hashXML(xmlRichiesta, "");
        log.debug("Auriga:ricercaUnitaDocumentale:Hash dell'XML da inviare al Documentale: \n" + xmlHash);
        String risultato = trovaDoc.service(codiceApplicazione, codiceIstanza, username, password, xmlRichiesta, xmlHash);
        risultato = AurigaUtils.decodeXml(risultato);
        log.debug("Auriga:ricercaUnitaDocumentale:Risposta del documentale: \n" + risultato);
        checkResult(risultato);
        Object[] allegatiResponse = ((WSTrovaDocFolderSoapBindingStub) trovaDoc).getAttachments();
        log.debug("Auriga:ricercaUnitaDocumentale:Numero di allegati: " + allegatiResponse.length);
        if (allegatiResponse.length == 0) {
            log.debug("Auriga:ricercaUnitaDocumentale:Numero di allegati: " + allegatiResponse.length);
            throw new Exception("Non è stato trovata nessuna unita documentale");
        } else {
            //Il secondo attachment è l'esito della chiamata
            DataHandler esitoCreazioneUnitaDocumentale = ((AttachmentPart) allegatiResponse[0]).getDataHandler();
            if (esitoCreazioneUnitaDocumentale != null) {
                log.debug("Auriga:ricercaUnitaDocumentale: Esito della ricerca della unita documentale");
                InputStream inputStream = esitoCreazioneUnitaDocumentale.getInputStream();
                byte[] content = IOUtils.toByteArray(inputStream);
                String esitoXml = new String(content);
                log.debug(esitoXml);
                JAXBContext context = JAXBContext.newInstance(Lista.class);
                Unmarshaller um = context.createUnmarshaller();
                Lista esitoRicercaUd = (Lista) um.unmarshal(new StringReader(esitoXml));
                String idUd = null;
                if (esitoRicercaUd.getRiga() != null && !esitoRicercaUd.getRiga().isEmpty()) {
                    //Mi aspetto un solo risultato dalla ricerca
                    Riga riga = esitoRicercaUd.getRiga().get(0);
                    if (riga.getColonna() != null && !riga.getColonna().isEmpty()) {
                        for (Colonna colonna : riga.getColonna()) {
                            //La colonna con id = 2 è "Identificativo dell'unità documentaria o folder (ID_UD o ID_FOLDER)"
                            if (colonna.getNro().intValue() == 2) {
                                return colonna.getContent();
                            }
                        }
                    }
                }
                return idUd;
            } else {
                log.error("Auriga:non è stato trovato nessun allegato");
                throw new Exception("Non è stata trovata nessuna unita documentale");
            }
        }
    }

    private void caricaAllegatiSuUd(String idUd, Pratica pratica, List<Allegati> allegati) throws Exception {
        Enti ente = pratica.getIdProcEnte().getIdEnte();
        LkComuni comune = null;
        if (pratica.getIdComune() != null) {
            comune = pratica.getIdComune();
        }
        Integer idComune = comune == null ? null : comune.getIdComune();
        Integer idEnte = ente == null ? null : ente.getIdEnte();
        String endpointCaricamentoFile = configurationService.getCachedConfiguration(Auriga.ENDPOINT_CARICA_ALLEGATO, idEnte, idComune);

        int index = 1;
        for (Allegati allegato : allegati) {
            WSCheckInServiceLocator wsLocator = new WSCheckInServiceLocator();
            wsLocator.setWSCheckInEndpointAddress(endpointCaricamentoFile);
            WSCheckIn wsCheckin = wsLocator.getWSCheckIn();
            String xmlRichiesta = getXmlCaricamentoFile(idUd, allegato, index);
            String codiceApplicazione = configurationService.getCachedConfiguration(Auriga.COD_APPLICAZIONE, idEnte, idComune);
            String codiceIstanza = configurationService.getCachedConfiguration(Auriga.COD_ISTANZA, idEnte, idComune);
            String username = configurationService.getCachedConfiguration(Auriga.DOCUMENTALE_USERNAME, idEnte, idComune);
            String password = configurationService.getCachedConfiguration(Auriga.DOCUMENTALE_PASSWORD, idEnte, idComune);
            log.debug("Auriga:caricaAllegatiSuUd:XML da inviare al Documentale: \n" + xmlRichiesta);
            String xmlHash = AurigaUtils.hashXML(xmlRichiesta, "");
            log.debug("Auriga:caricaAllegatiSuUd:Hash dell'XML da inviare al Documentale: \n" + xmlHash);
            Object allegatoDaCaricare = getAttachment(allegato, ente, comune);
            Object[] attachment = new Object[1];
            attachment[0] = allegatoDaCaricare;
            String risultato = wsCheckin.service(codiceApplicazione, codiceIstanza, username, password, xmlRichiesta, xmlHash, attachment);
            risultato = AurigaUtils.decodeXml(risultato);
            log.debug("Auriga:caricaAllegatiSuUd:Risposta del documentale: \n" + risultato);
            checkResult(risultato);
            index++;
        }
    }

    private String creazioneUnitaDocumentaria(Pratica pratica, List<Allegati> allegati) throws ServiceException, RemoteException, NoSuchAlgorithmException, JAXBException, Exception {
        Enti ente = pratica.getIdProcEnte().getIdEnte();
        LkComuni comune = null;
        if (pratica.getIdComune() != null) {
            comune = pratica.getIdComune();
        }
        Integer idComune = comune == null ? null : comune.getIdComune();
        Integer idEnte = ente == null ? null : ente.getIdEnte();
        String endpointCreazioneUd = configurationService.getCachedConfiguration(Auriga.ENDPOINT_CREA_UNITA_DOCUMENTALE, idEnte, idComune);
        WSAddUdServiceLocator wsLocator = new WSAddUdServiceLocator();

        wsLocator.setWSAddUdEndpointAddress(endpointCreazioneUd);
        WSAddUd wsAddUd = wsLocator.getWSAddUd();
        String xmlRichiesta = getXmlCreazioneUd(pratica, allegati);
        String codiceApplicazione = configurationService.getCachedConfiguration(Auriga.COD_APPLICAZIONE, idEnte, idComune);
        String codiceIstanza = configurationService.getCachedConfiguration(Auriga.COD_ISTANZA, idEnte, idComune);
        String username = configurationService.getCachedConfiguration(Auriga.DOCUMENTALE_USERNAME, idEnte, idComune);
        String password = configurationService.getCachedConfiguration(Auriga.DOCUMENTALE_PASSWORD, idEnte, idComune);
        log.debug("Auriga:creazioneUnitaDocumentaria:XML da inviare al Documentale: \n" + xmlRichiesta);
        String xmlHash = AurigaUtils.hashXML(xmlRichiesta, "");
        log.debug("Auriga:creazioneUnitaDocumentaria:Hash dell'XML da inviare al Documentale: \n" + xmlHash);
        Object[] attachment = null;
        if (allegati != null && !allegati.isEmpty()) {
            attachment = new Object[allegati.size()];
            int i = 0;
            for (Allegati allegato : allegati) {
                attachment[i] = getAttachment(allegato, ente, comune);
                i++;
            }
        }

        String risultato = wsAddUd.service(codiceApplicazione, codiceIstanza, username, password, xmlRichiesta, xmlHash, attachment);
        risultato = AurigaUtils.decodeXml(risultato);
        log.debug("Auriga:creazioneUnitaDocumentaria:Risposta del documentale: \n" + risultato);
        checkResult(risultato);
        Object[] allegatiResponse = ((WSAddUdSoapBindingStub) wsAddUd).getAttachments();
        log.debug("Auriga:creazioneUnitaDocumentaria:Numero di allegati: " + allegatiResponse.length);
        if (allegatiResponse.length == 0) {
            log.debug("Auriga:creazioneUnitaDocumentaria:Numero di allegati: " + allegatiResponse.length);
            throw new Exception("Non è stato creata nessuna unita documentale");
        } else {
            //Il secondo attachment è l'esito della chiamata
            DataHandler esitoCreazioneUnitaDocumentale = ((AttachmentPart) allegatiResponse[0]).getDataHandler();
            if (esitoCreazioneUnitaDocumentale != null) {
                log.debug("Auriga:creazioneUnitaDocumentaria: Esito della creazione della unita documentale");
                InputStream inputStream = esitoCreazioneUnitaDocumentale.getInputStream();
                byte[] content = IOUtils.toByteArray(inputStream);
                String esitoXml = new String(content);
                log.debug(esitoXml);
                JAXBContext context = JAXBContext.newInstance(OutputUD.class);
                Unmarshaller um = context.createUnmarshaller();
                OutputUD esitoCreazioneUD = (OutputUD) um.unmarshal(new StringReader(esitoXml));
                String idUD = esitoCreazioneUD.getIdUD().toString();
                pluginGenovaAction.aggiornaAllegatiCaricatiManualmente(idUD, allegati);
                return idUD;
            } else {
                log.error("Auriga:non è stato trovato nessun allegato");
                throw new Exception("Non è stato creata nessuna unita documentale");
            }
        }
    }

    private void checkResult(String risultato) throws Exception, JAXBException {
        JAXBContext context = JAXBContext.newInstance(BaseOutputWS.class);
        Unmarshaller um = context.createUnmarshaller();
        BaseOutputWS output = (BaseOutputWS) um.unmarshal(new StringReader(risultato));
        String result = output.getWSResult();
        if (result.equals("1")) {
            //Tutto OK
            if (output.getWarningMessage() != null) {
                log.warn("Il sistema documentale ha ritornato il seguente messaggio di warning");
                log.warn(output.getWarningMessage());
            }
        } else {
            //Si è verificato un errore
            if (output.getWSError() != null) {
                log.error("Si è verificato un errore caricando il file sul sistema documentale. Di seguito i dettagli");
                log.error("Contesto: " + output.getWSError().getErrorContext());
                log.error("Codice numerico Auriga: " + output.getWSError().getErrorNumber());
                log.error("Messaggio: " + output.getWSError().getErrorMessage());
                throw new Exception(output.getWSError().getErrorMessage());
            }
        }
    }

    private String getXmlRicercaUd(Pratica pratica) throws Exception {
        TrovaDocFolder trovaDoc = new TrovaDocFolder();
        TrovaDocFolder.FiltriPrincipali filtriPrincipali = new TrovaDocFolder.FiltriPrincipali();
        TrovaDocFolder.FiltriPrincipali.FiltroFullText fullText = new TrovaDocFolder.FiltriPrincipali.FiltroFullText();
        fullText.setListaParole(pratica.getIdentificativoPratica());
        //Non è chiara la documentazione. Ipotizzo che No=0, Si=1
        fullText.setFlagTutteLeParole("0");
        filtriPrincipali.setFiltroFullText(fullText);
        trovaDoc.setFiltriPrincipali(filtriPrincipali);
        String xml = Utils.marshall(trovaDoc);
        return xml;
    }

    private String getXmlCaricamentoFile(String idUd, Allegati allegato, int index) throws Exception {
        it.wego.cross.client.xml.checkin.DatiXCheckIn checkin = new it.wego.cross.client.xml.checkin.DatiXCheckIn();
        it.wego.cross.client.xml.checkin.EstremiXIdentificazioneDocType estremiDoc = new it.wego.cross.client.xml.checkin.EstremiXIdentificazioneDocType();
        it.wego.cross.client.xml.checkin.EstremiXIdentificazioneUDType estremiUD = new it.wego.cross.client.xml.checkin.EstremiXIdentificazioneUDType();
        estremiUD.setIdUD(new BigInteger(idUd));
        estremiDoc.setEstremiXIdentificazioneUD(estremiUD);
        it.wego.cross.client.xml.checkin.EstremiXIdentificazioneDocType.DocVsUD doc = new it.wego.cross.client.xml.checkin.EstremiXIdentificazioneDocType.DocVsUD();
        doc.setNroAllegato(BigInteger.valueOf(index));
        //Non valorizzo FlagPrimario in quanto tuti i documenti sono allegati in una UD
        estremiDoc.setDocVsUD(doc);
        checkin.setEstremiXIdentificazioneDoc(estremiDoc);

        it.wego.cross.client.xml.checkin.DatiXCheckIn.NuovaVersioneElettronica documento = new it.wego.cross.client.xml.checkin.DatiXCheckIn.NuovaVersioneElettronica();
        documento.setNomeFile(allegato.getNomeFile());
        documento.setNote(allegato.getDescrizione());
        checkin.setNuovaVersioneElettronica(documento);
        String xml = Utils.marshall(checkin);
        return xml;
    }

    private String getXmlCreazioneUd(Pratica pratica, List<Allegati> allegati) throws Exception {
        NewUD newUD = new NewUD();
        String nome = pratica.getIdentificativoPratica();
        newUD.setNomeUD(nome);
        newUD.setOggettoUD(pratica.getOggettoPratica());
        newUD.setNoteUD("Allegato caricato manualmente");
        if (allegati != null && !allegati.isEmpty()) {
            int i = 1;
            for (Allegati a : allegati) {
                AllegatoUDType allegato = new AllegatoUDType();
                allegato.setDesAllegato(a.getDescrizione());
                VersioneElettronicaType ve = new VersioneElettronicaType();
                ve.setNomeFile(a.getNomeFile());
                ve.setNroAttachmentAssociato(BigInteger.valueOf(i));
                allegato.setVersioneElettronica(ve);
                newUD.getAllegatoUD().add(allegato);
                i++;
            }
            newUD.setNroAllegati(BigInteger.valueOf(i - 1));
        }

        String xml = Utils.marshall(newUD);
        return xml;
    }

    private DataHandler getAttachment(Allegati allegato, Enti ente, LkComuni comune) throws Exception {
        ByteArrayDataSource ds = new ByteArrayDataSource();
        ds.setContentType(allegato.getTipoFile());
        ds.setName(allegato.getNomeFile());
        if (allegato.getFile() != null) {
            ds.setBytes(allegato.getFile());
        } else if (allegato.getIdFileEsterno() != null) {
            byte[] file = getFileContent(allegato, ente, comune);
            ds.setBytes(file);
        } else if (allegato.getPathFile() != null) {
            File f = new File(allegato.getPathFile());
            InputStream stream = new FileInputStream(f);
            byte[] file = IOUtils.toByteArray(stream);
            ds.setBytes(file);
        }
        DataHandler dh = new DataHandler(ds);
        return dh;
    }

    private String getXmlRecuperoAllegatiDaProtocollo(String numero, Integer anno) throws Exception {
        it.wego.cross.client.xml.extractmulti.EstremiXIdentificazioneUDType estremiIdentificazioneUD = new it.wego.cross.client.xml.extractmulti.EstremiXIdentificazioneUDType();
        it.wego.cross.client.xml.extractmulti.EstremiRegNumType estremiReg = new it.wego.cross.client.xml.extractmulti.EstremiRegNumType();
        estremiReg.setAnnoReg(anno);
        estremiReg.setNumReg(Integer.valueOf(numero));
        estremiIdentificazioneUD.setEstremiRegNum(estremiReg);
        it.wego.cross.client.xml.extractmulti.ObjectFactory of = new it.wego.cross.client.xml.extractmulti.ObjectFactory();
        JAXBElement<it.wego.cross.client.xml.extractmulti.EstremiXIdentificazioneUDType> root = of.createEstremiXIdentificazioneUD(estremiIdentificazioneUD);
        String xml = Utils.marshall(root, it.wego.cross.client.xml.extractmulti.EstremiXIdentificazioneUDType.class);
        return xml;
    }

}
