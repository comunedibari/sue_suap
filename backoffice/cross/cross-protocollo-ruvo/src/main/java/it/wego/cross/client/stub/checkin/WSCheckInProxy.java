package it.wego.cross.client.stub.checkin;

public class WSCheckInProxy implements it.wego.cross.client.stub.checkin.WSCheckIn {
  private String _endpoint = null;
  private it.wego.cross.client.stub.checkin.WSCheckIn wSCheckIn = null;
  
  public WSCheckInProxy() {
    _initWSCheckInProxy();
  }
  
  public WSCheckInProxy(String endpoint) {
    _endpoint = endpoint;
    _initWSCheckInProxy();
  }
  
  private void _initWSCheckInProxy() {
    try {
      wSCheckIn = (new it.wego.cross.client.stub.checkin.WSCheckInServiceLocator()).getWSCheckIn();
      if (wSCheckIn != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)wSCheckIn)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)wSCheckIn)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (wSCheckIn != null)
      ((javax.xml.rpc.Stub)wSCheckIn)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public it.wego.cross.client.stub.checkin.WSCheckIn getWSCheckIn() {
    if (wSCheckIn == null)
      _initWSCheckInProxy();
    return wSCheckIn;
  }
  
  public java.lang.String service(java.lang.String codApplicazione, java.lang.String istanzaApplicazione, java.lang.String userName, java.lang.String password, java.lang.String xml, java.lang.String hash, Object[] attachments) throws java.rmi.RemoteException{
    if (wSCheckIn == null)
      _initWSCheckInProxy();
    return wSCheckIn.service(codApplicazione, istanzaApplicazione, userName, password, xml, hash, attachments);
  }
  
  
}