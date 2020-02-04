package it.wego.cross.plugins.documenti;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.List;

import javax.xml.rpc.ServiceException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.wego.cross.action.ProtocolloDemoAction;
import it.wego.cross.entity.Allegati;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.Pratica;
import it.wego.cross.plugins.commons.beans.Allegato;
import it.wego.cross.utils.Utils;
import it.wego.cross.webservices.mypage.fileclient.FileClient;

/**
 *
 * @author CS
 */
public class GestioneAllegatiDemo implements GestioneAllegati {

    @Autowired
    private ProtocolloDemoAction protocolloAction;
    private static Logger log = LoggerFactory.getLogger("plugin");

    @Override
    public void add(Allegati allegato, Enti ente, LkComuni comune) throws Exception {
        //DO NOTHING
//        if (allegato.getIdFileEsterno() == null || allegato.getIdFileEsterno().trim().equals("")) {
//            demoServices.flush();
////            String id = protocolloAction.salvaAllegato(allegato);
//            allegato.setIdFileEsterno(allegato.getId().toString());
//        }
    }

    @Override
    public byte[] getFileContent(Allegati allegato, Enti ente, LkComuni comune) throws Exception {
        if (!Utils.e(allegato.getIdFileEsterno())) {
            return protocolloAction.getAllegato(allegato.getIdFileEsterno(), ente, comune);
        } else if (!Utils.e(allegato.getFile())) {
            return allegato.getFile();
        } else if (!Utils.e(allegato.getPathFile())) {
            File f = new File(allegato.getPathFile());
            InputStream stream = new FileInputStream(f);
            byte[] file = IOUtils.toByteArray(stream);
            String fileContent = new String(file);
            String[] riferimenti = fileContent.split("\\|\\|");
            if (riferimenti.length != 5
                    || (riferimenti.length == 5 && !riferimenti[riferimenti.length - 1].equals("getFile"))) {
                //è un file binario
                return file;
            } else {
                String codiceComune = riferimenti[0];
                String idPratica = riferimenti[1];
                String codFile = riferimenti[2];
                String url = riferimenti[3];
                FileClient fileClient = new FileClient();
                String fileDaSalvare;
                try {
                    fileDaSalvare = fileClient.getFile(url, codiceComune, idPratica, codFile);
                    file = Base64.decodeBase64(fileDaSalvare);
                    return file;
                } catch (RemoteException ex) {
                    log.error("[Remote Exception] Non sono riuscito a recuperare l'allegato da MyPage", ex);
                    return null;
                } catch (ServiceException ex) {
                    log.error("[ServiceException] Non sono riuscito a recuperare l'allegato da MyPage", ex);
                    return null;
                }
            }
        } else {
            return null;
        }
    }

    @Override
    public Allegato getFile(String idFileEsterno, Enti ente, LkComuni comune) throws Exception {
//        Integer idFile = Integer.valueOf(idFileEsterno);
        byte[] content = protocolloAction.getAllegato(idFileEsterno, ente, comune);
        Allegato all = new Allegato();
        all.setFile(content);
        all.setProtocolla(Boolean.FALSE);
        return all;
    }

    @Override
    public String uploadFile(Pratica pratica, List<Allegati> allegati) throws Exception {
        //NOT IMPLEMENTED
        return "";
    }

    @Override
    public String uploadAttachmentsOnMyPage(Pratica pratica, List<Allegati> allegati) throws Exception {
//        Enti ente = pratica.getIdEnte();
//        LkComuni comune = null;
//        if (pratica.getIdComune() != null) {
//            comune = pratica.getIdComune();
//        }
//        String url = demoServices.getConfiguration(URL_MYPAGE, ente, comune);
//        String codEnte = pratica.getIdEnte().getCodEnte();
//        String idPratica = pratica.getIdentificativoPratica();
//        FileClient client = new FileClient(url);
//        if (allegati != null) {
//            for (it.wego.cross.entity.Allegati allegato : allegati) {
//                try {
//                    byte[] file = getFileContent(allegato);
//                    String content = Base64.encodeBase64String(file);
//                    client.caricaFile(codEnte, idPratica, allegato.getNomeFile(), content);
//                } catch (Exception ex) {
//                    log.error("Errore cercando si caricare gli allegati su MyPage", ex);
//                    throw new Exception();
//                }
//            }
//        }
//        return "OK";
        //NOT ALREADY SUPPORTED
        return "";
    }
}
