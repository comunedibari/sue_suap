/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.webservices.mypage.fileclient;

import it.wego.cross.webservices.mypage.fileclient.stubs.AllegatiManager;
import it.wego.cross.webservices.mypage.fileclient.stubs.AllegatiManagerService;
import it.wego.cross.webservices.mypage.fileclient.stubs.AllegatiManagerServiceLocator;
import it.wego.cross.webservices.mypage.fileclient.stubs.BaseBean;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;
import javax.xml.rpc.ServiceException;

/**
 *
 * @author emanuela
 */
public class FileClient {

    public FileClient() {
    }

    public List<BaseBean> getListaAllegati(String url, String codiceComune, String idPratica) throws ServiceException, RemoteException, MalformedURLException {
        AllegatiManagerService manager = new AllegatiManagerServiceLocator();
        AllegatiManager client = manager.getallegatiService(new URL(url));
//        ((AllegatiManager_Stub) client)._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, url);
        BaseBean[] response = client.getListaAllegati(codiceComune, idPratica);
        List<BaseBean> allegati = Arrays.asList(response);
        return allegati;
    }

    public String caricaFile(String url, String nodoFront, String idPratica, String nomeFile, String fileContent) throws RemoteException, ServiceException, MalformedURLException {
        AllegatiManagerService manager = new AllegatiManagerServiceLocator();
        AllegatiManager client = manager.getallegatiService(new URL(url));
        //In people l'id della pratica non considera lo / e tutto quello che segue
        String idPraticaPerFE = idPratica.split("/")[0];
//        ((AllegatiManager_Stub) client)._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, url);
        return client.putFile(nodoFront, idPraticaPerFE, nomeFile, fileContent, true);
    }

    public BaseBean getPraticaFile(BaseBean beans[], String idPratica) {
        String nomeFileFirmato = "riepilogo.pdf.p7m";
        String nomeFileNonFirmato = "riepilogo_" + idPratica.replace("/", "_") + ".pdf";
        boolean found = false;
        BaseBean result = null;
        for (BaseBean bean : beans) {
            if (bean.getNomeFile().equalsIgnoreCase(nomeFileFirmato) && !found) {
                result = bean;
                found = true;
                break;
            }
        }
        if (!found) {
            for (BaseBean bean : beans) {
                if (bean.getNomeFile().equalsIgnoreCase(nomeFileNonFirmato)) {
                    result = bean;
                    break;
                }
            }
        }
        return result;
    }

    public String getFile(String url, String codiceComune, String idPratica, String idFile) throws RemoteException, ServiceException, MalformedURLException {
        AllegatiManagerService manager = new AllegatiManagerServiceLocator();
        AllegatiManager client = manager.getallegatiService(new URL(url));
//        ((AllegatiManager_Stub) client)._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, url);
        String file = client.getFile(codiceComune, idPratica, idFile);
        return file;
    }
}
