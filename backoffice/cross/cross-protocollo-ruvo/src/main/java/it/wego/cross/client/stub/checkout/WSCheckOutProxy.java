package it.wego.cross.client.stub.checkout;

public class WSCheckOutProxy implements it.wego.cross.client.stub.checkout.WSCheckOut {
  private String _endpoint = null;
  private it.wego.cross.client.stub.checkout.WSCheckOut wSCheckOut = null;
  
  public WSCheckOutProxy() {
    _initWSCheckOutProxy();
  }
  
  public WSCheckOutProxy(String endpoint) {
    _endpoint = endpoint;
    _initWSCheckOutProxy();
  }
  
  private void _initWSCheckOutProxy() {
    try {
      wSCheckOut = (new it.wego.cross.client.stub.checkout.WSCheckOutServiceLocator()).getWSCheckOut();
      if (wSCheckOut != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)wSCheckOut)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)wSCheckOut)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (wSCheckOut != null)
      ((javax.xml.rpc.Stub)wSCheckOut)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public it.wego.cross.client.stub.checkout.WSCheckOut getWSCheckOut() {
    if (wSCheckOut == null)
      _initWSCheckOutProxy();
    return wSCheckOut;
  }
  
  public java.lang.String service(java.lang.String codApplicazione, java.lang.String istanzaApplicazione, java.lang.String userName, java.lang.String password, java.lang.String xml, java.lang.String hash) throws java.rmi.RemoteException{
    if (wSCheckOut == null)
      _initWSCheckOutProxy();
    return wSCheckOut.service(codApplicazione, istanzaApplicazione, userName, password, xml, hash);
  }
  
  
}