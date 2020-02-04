package dynamicodt.reporter.it;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;

@WebServiceClient(name="Reporter", targetNamespace="http://it.reporter.dynamicodt", wsdlLocation="file:/C:/Documenti/NetBeansProjects/Reporter/src/conf/xml-resources/web-services/Reporter/wsdl/reporter.wsdl")
public class Reporter
  extends Service
{
  private static final URL REPORTER_WSDL_LOCATION;
  private static final WebServiceException REPORTER_EXCEPTION;
  private static final QName REPORTER_QNAME = new QName("http://it.reporter.dynamicodt", "Reporter");
  
  static
  {
    URL url = null;
    WebServiceException e = null;
    try
    {
      url = new URL("file:/C:/Documenti/NetBeansProjects/Reporter/src/conf/xml-resources/web-services/Reporter/wsdl/reporter.wsdl");
    }
    catch (MalformedURLException ex)
    {
      e = new WebServiceException(ex);
    }
    REPORTER_WSDL_LOCATION = url;
    REPORTER_EXCEPTION = e;
  }
  
  public Reporter()
  {
    super(__getWsdlLocation(), REPORTER_QNAME);
  }
  
  public Reporter(WebServiceFeature... features)
  {
    super(__getWsdlLocation(), REPORTER_QNAME, features);
  }
  
  public Reporter(URL wsdlLocation)
  {
    super(wsdlLocation, REPORTER_QNAME);
  }
  
  public Reporter(URL wsdlLocation, WebServiceFeature... features)
  {
    super(wsdlLocation, REPORTER_QNAME, features);
  }
  
  public Reporter(URL wsdlLocation, QName serviceName)
  {
    super(wsdlLocation, serviceName);
  }
  
  public Reporter(URL wsdlLocation, QName serviceName, WebServiceFeature... features)
  {
    super(wsdlLocation, serviceName, features);
  }
  
  @WebEndpoint(name="ReporterSOAP11port_http")
  public ReporterPortType getReporterSOAP11PortHttp()
  {
    return (ReporterPortType)super.getPort(new QName("http://it.reporter.dynamicodt", "ReporterSOAP11port_http"), ReporterPortType.class);
  }
  
  @WebEndpoint(name="ReporterSOAP11port_http")
  public ReporterPortType getReporterSOAP11PortHttp(WebServiceFeature... features)
  {
    return (ReporterPortType)super.getPort(new QName("http://it.reporter.dynamicodt", "ReporterSOAP11port_http"), ReporterPortType.class, features);
  }
  
  @WebEndpoint(name="ReporterSOAP12port_http")
  public ReporterPortType getReporterSOAP12PortHttp()
  {
    return (ReporterPortType)super.getPort(new QName("http://it.reporter.dynamicodt", "ReporterSOAP12port_http"), ReporterPortType.class);
  }
  
  @WebEndpoint(name="ReporterSOAP12port_http")
  public ReporterPortType getReporterSOAP12PortHttp(WebServiceFeature... features)
  {
    return (ReporterPortType)super.getPort(new QName("http://it.reporter.dynamicodt", "ReporterSOAP12port_http"), ReporterPortType.class, features);
  }
  
  @WebEndpoint(name="ReporterHttpport1")
  public ReporterPortType getReporterHttpport1()
  {
    return (ReporterPortType)super.getPort(new QName("http://it.reporter.dynamicodt", "ReporterHttpport1"), ReporterPortType.class);
  }
  
  @WebEndpoint(name="ReporterHttpport1")
  public ReporterPortType getReporterHttpport1(WebServiceFeature... features)
  {
    return (ReporterPortType)super.getPort(new QName("http://it.reporter.dynamicodt", "ReporterHttpport1"), ReporterPortType.class, features);
  }
  
  private static URL __getWsdlLocation()
  {
    if (REPORTER_EXCEPTION != null) {
      throw REPORTER_EXCEPTION;
    }
    return REPORTER_WSDL_LOCATION;
  }
}
