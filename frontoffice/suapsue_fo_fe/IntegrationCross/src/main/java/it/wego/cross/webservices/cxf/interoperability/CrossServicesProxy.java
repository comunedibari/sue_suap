package it.wego.cross.webservices.cxf.interoperability;

public class CrossServicesProxy implements it.wego.cross.webservices.cxf.interoperability.CrossServices {
  private String _endpoint = null;
  private it.wego.cross.webservices.cxf.interoperability.CrossServices crossServices = null;
  
  public CrossServicesProxy() {
    _initCrossServicesProxy();
  }
  
  public CrossServicesProxy(String endpoint) {
    _endpoint = endpoint;
    _initCrossServicesProxy();
  }
  
  private void _initCrossServicesProxy() {
    try {
      crossServices = (new it.wego.cross.webservices.cxf.interoperability.CrossServicesImplServiceLocator()).getCrossServicesImplPort();
      if (crossServices != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)crossServices)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)crossServices)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (crossServices != null)
      ((javax.xml.rpc.Stub)crossServices)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public it.wego.cross.webservices.cxf.interoperability.CrossServices getCrossServices() {
    if (crossServices == null)
      _initCrossServicesProxy();
    return crossServices;
  }
  
  public it.wego.cross.webservices.cxf.interoperability.Evento inserisciEvento(it.wego.cross.webservices.cxf.interoperability.Evento evento) throws java.rmi.RemoteException, it.wego.cross.webservices.cxf.interoperability.CrossServicesException{
    if (crossServices == null)
      _initCrossServicesProxy();
    return crossServices.inserisciEvento(evento);
  }
  
  public it.wego.cross.webservices.cxf.interoperability.Evento[] getListaEventi(java.lang.Integer idPratica, java.lang.String identificativoPratica, java.lang.String codiceEnte) throws java.rmi.RemoteException, it.wego.cross.webservices.cxf.interoperability.CrossServicesException{
    if (crossServices == null)
      _initCrossServicesProxy();
    return crossServices.getListaEventi(idPratica, identificativoPratica, codiceEnte);
  }
  
  
}