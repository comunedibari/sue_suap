/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.aurigasync.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import javax.activation.DataHandler;
import javax.xml.soap.AttachmentPart;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;

import it.wego.cross.aurigasync.service.AurigaSyncService;
import it.wego.cross.client.stub.extractone.WSExtractOne;
import it.wego.cross.client.stub.extractone.WSExtractOneServiceLocator;
import it.wego.cross.client.stub.extractone.WSExtractOneSoapBindingStub;
import it.wego.cross.constants.Auriga;
import it.wego.cross.dao.AllegatiDao;
import it.wego.cross.entity.Allegati;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.utils.AurigaUtils;

/**
 *
 * @author Giuseppe
 */
@Component
public class AurigaSyncAction {

    private static final Logger general = LoggerFactory.getLogger("aurigasync");
    private static final Logger notupdated = LoggerFactory.getLogger("auriganotupdated");

    @Autowired
    private AllegatiDao allegatiDao;
    @Autowired
    private AurigaSyncService aurigaSyncService;

    @Transactional(rollbackFor = Exception.class)
    public void aggiornaRiferimentiAllegato(PraticheEventi evento, String path, Allegati allegatoDaAggiornare, String xmlRichiestaAuriga, boolean deleteFile) throws Exception {
        boolean isEmpty = checkFileIsEmpty(xmlRichiestaAuriga, evento);
        if (!isEmpty) {
            String filePath = allegatoDaAggiornare.getPathFile();
            allegatoDaAggiornare.setPathFile(null);
            if (deleteFile) {
                allegatoDaAggiornare.setFile(null);
            }
            allegatoDaAggiornare.setIdFileEsterno("P|" + xmlRichiestaAuriga);
            allegatiDao.mergeAllegato(allegatoDaAggiornare);
            if (!Strings.isNullOrEmpty(filePath) && deleteFile) {
                try {
                    //Creo il path assoluto
                    filePath = path + filePath;
                    File f = new File(filePath);
                    general.info("Cancello il file = " + filePath);
                    File directory = f.getParentFile();
                    f.delete();
                    general.info("Verifico se la directory " + directory.getAbsolutePath() + " è vuota...");
                    if (directory.isDirectory() && directory.list().length == 0) {
                        general.info("La directory è vuota. La cancello...");
                        directory.delete();
                        general.info("... cancellata!");
                    }
                    if (directory.isDirectory() && containsRiepilogoToDelete(directory)) {
                        general.info("La directory " + directory.getAbsolutePath() + " non è vuota e contiene il file riepilogo.pdf.");
                        String pathToRiepilogo = directory.getAbsolutePath() + File.pathSeparator + "riepilogo.pdf";
                        general.info("Cancello il file " + pathToRiepilogo + " ...");
                        File riepilogo = new File(pathToRiepilogo);
                        riepilogo.delete();
                        general.info("... cancellato!");
                        if (directory.isDirectory() && directory.list().length == 0) {
                            general.info("La directory " + directory.getAbsolutePath() + " ora è vuota. La cancello...");
                            directory.delete();
                            general.info("... cancellata!");
                        }
                    } else {
                        general.info("RIGA DI DEBUG - pre if");
                        if (directory != null && directory.list() != null){
                            general.info("RIGA DI DEBUG");
                            general.info("Directory non vuota. Ci sono ancora " + directory.list().length + " files");    
                        }
                    }
                } catch (Exception ex) {
                    general.error("[FILE DA RIMUOVERE MANUALMENTE] Non è stato possibile cancellare il file " + filePath, ex);
                    notupdated.error("[FILE DA RIMUOVERE MANUALMENTE] Non è stato possibile cancellare il file " + filePath);
                }
            }
        }
    }

    private boolean checkFileIsEmpty(String xmlRichiestaPerCheckout, PraticheEventi evento) throws Exception {
        Pratica pratica = evento.getIdPratica();
        LkComuni idComune = pratica.getIdComune() != null ? pratica.getIdComune() : null;
        Enti idEnte = pratica.getIdProcEnte().getIdEnte();
        DataHandler file = checkOutFile(xmlRichiestaPerCheckout, idEnte, idComune);
        if (file != null) {
            InputStream inputStream = file.getInputStream();
            byte[] content = IOUtils.toByteArray(inputStream);
            return content.length == 0;
        } else {
            return true;
        }
    }

    private DataHandler checkOutFile(String xmlRichiesta, Enti idEnte, LkComuni idComune) throws Exception {
        try {
            WSExtractOneServiceLocator wsLocator = new WSExtractOneServiceLocator();
            String addressWsDoc = aurigaSyncService.getConfigurationValue(Auriga.ENDPOINT_RICERCA_DOCUMENTALE_SINGOLA, idEnte, idComune);
            wsLocator.setWSExtractOneEndpointAddress(addressWsDoc);
            WSExtractOne wsExtractOne = wsLocator.getWSExtractOne();
            String codiceApplicazione = aurigaSyncService.getConfigurationValue(Auriga.COD_APPLICAZIONE, idEnte, idComune);
            String codiceIstanza = aurigaSyncService.getConfigurationValue(Auriga.COD_ISTANZA, idEnte, idComune);
            String username = aurigaSyncService.getConfigurationValue(Auriga.DOCUMENTALE_USERNAME, idEnte, idComune);
            String password = aurigaSyncService.getConfigurationValue(Auriga.DOCUMENTALE_PASSWORD, idEnte, idComune);
            general.debug("Auriga:getFileContent:XML da inviare al Documentale: \n" + xmlRichiesta);
            String xmlHash = AurigaUtils.hashXML(xmlRichiesta, "");
            general.debug("Auriga:getFileContent:Hash dell'XML da inviare al Documentale: \n" + xmlHash);
            String risultato = wsExtractOne.service(codiceApplicazione, codiceIstanza, username, password, xmlRichiesta, xmlHash);
            risultato = AurigaUtils.decodeXml(risultato);
            general.debug("Auriga:getFileContent:Risposta del documentale: \n" + risultato);
            Object[] allegati = ((WSExtractOneSoapBindingStub) wsExtractOne).getAttachments();
            general.debug("Auriga:getFileContent:Numero di allegati: " + allegati.length);
            if (allegati.length == 0) {
                general.debug("Auriga:getFileContent:Numero di allegati: " + allegati.length);
                return null;
            } else {
                //Il secondo attachment è il file fisico da restituire
                DataHandler allegatoFisico = ((AttachmentPart) allegati[1]).getDataHandler();
                return allegatoFisico;
            }
        } catch (Exception ex) {
            general.error("Auriga:errore interrogando il webservice", ex);
            throw new Exception("Errore contattando il servizio documentale", ex);
        }
    }

    private static boolean containsRiepilogoToDelete(File directory) throws FileNotFoundException, IOException {
        if (directory.list().length == 1 && directory.listFiles(new FileFilter() {

            @Override
            public boolean accept(File pathname) {
                return pathname.getName().equals("riepilogo.pdf");
            }
        }).length == 1) {
            String pathToRiepilogo = directory.getAbsolutePath() + File.separator + "riepilogo.pdf";
            general.info("Cerco il file riepilogo.pdf nella cartella");
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(pathToRiepilogo));
            try {
                String line = br.readLine();

                while (line != null) {
                    sb.append(line);
                    line = br.readLine();
                }
            } finally {
                br.close();
            }

            return sb.toString().endsWith("getFile");
        } else {
            return false;
        }
    }
}
