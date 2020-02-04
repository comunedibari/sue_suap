package it.wego.cross.client.stub.trovadocfolder;


public class WSTrovaDocFolderProxy implements it.wego.cross.client.stub.trovadocfolder.WSTrovaDocFolder {

    private String _endpoint = null;
    private it.wego.cross.client.stub.trovadocfolder.WSTrovaDocFolder wSTrovaDocFolder = null;

    public WSTrovaDocFolderProxy() {
        _initWSTrovaDocFolderProxy();
    }

    public WSTrovaDocFolderProxy(String endpoint) {
        _endpoint = endpoint;
        _initWSTrovaDocFolderProxy();
    }

    private void _initWSTrovaDocFolderProxy() {
        try {
            wSTrovaDocFolder = (new it.wego.cross.client.stub.trovadocfolder.WSTrovaDocFolderServiceLocator()).getWSTrovaDocFolder();
            if (wSTrovaDocFolder != null) {
                if (_endpoint != null) {
                    ((javax.xml.rpc.Stub) wSTrovaDocFolder)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
                } else {
                    _endpoint = (String) ((javax.xml.rpc.Stub) wSTrovaDocFolder)._getProperty("javax.xml.rpc.service.endpoint.address");
                }
            }

        } catch (javax.xml.rpc.ServiceException serviceException) {
        }
    }

    public String getEndpoint() {
        return _endpoint;
    }

    public void setEndpoint(String endpoint) {
        _endpoint = endpoint;
        if (wSTrovaDocFolder != null) {
            ((javax.xml.rpc.Stub) wSTrovaDocFolder)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        }

    }

    public it.wego.cross.client.stub.trovadocfolder.WSTrovaDocFolder getWSTrovaDocFolder() {
        if (wSTrovaDocFolder == null) {
            _initWSTrovaDocFolderProxy();
        }
        return wSTrovaDocFolder;
    }

    public java.lang.String service(java.lang.String codApplicazione, java.lang.String istanzaApplicazione, java.lang.String userName, java.lang.String password, java.lang.String xml, java.lang.String hash) throws java.rmi.RemoteException {
        if (wSTrovaDocFolder == null) {
            _initWSTrovaDocFolderProxy();
        }
        return wSTrovaDocFolder.service(codApplicazione, istanzaApplicazione, userName, password, xml, hash);
    }
}