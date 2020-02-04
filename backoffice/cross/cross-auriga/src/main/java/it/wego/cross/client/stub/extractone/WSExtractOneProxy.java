package it.wego.cross.client.stub.extractone;

public class WSExtractOneProxy implements it.wego.cross.client.stub.extractone.WSExtractOne {

    private String _endpoint = null;
    private it.wego.cross.client.stub.extractone.WSExtractOne wSExtractOne = null;

    public WSExtractOneProxy() {
        _initWSExtractOneProxy();
    }

    public WSExtractOneProxy(String endpoint) {
        _endpoint = endpoint;
        _initWSExtractOneProxy();
    }

    private void _initWSExtractOneProxy() {
        try {
            wSExtractOne = (new it.wego.cross.client.stub.extractone.WSExtractOneServiceLocator()).getWSExtractOne();
            if (wSExtractOne != null) {
                if (_endpoint != null) {
                    ((javax.xml.rpc.Stub) wSExtractOne)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
                } else {
                    _endpoint = (String) ((javax.xml.rpc.Stub) wSExtractOne)._getProperty("javax.xml.rpc.service.endpoint.address");
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
        if (wSExtractOne != null) {
            ((javax.xml.rpc.Stub) wSExtractOne)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        }

    }

    public it.wego.cross.client.stub.extractone.WSExtractOne getWSExtractOne() {
        if (wSExtractOne == null) {
            _initWSExtractOneProxy();
        }
        return wSExtractOne;
    }

    public java.lang.String service(java.lang.String codApplicazione, java.lang.String istanzaApplicazione, java.lang.String userName, java.lang.String password, java.lang.String xml, java.lang.String hash) throws java.rmi.RemoteException {
        if (wSExtractOne == null) {
            _initWSExtractOneProxy();
        }
        return wSExtractOne.service(codApplicazione, istanzaApplicazione, userName, password, xml, hash);
    }
}