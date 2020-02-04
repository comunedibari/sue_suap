package it.reporter;

import it.reporter.transformation.ReportGenerator;
import javax.jws.WebService;

@WebService(serviceName="Reporter", portName="ReporterSOAP11port_http", endpointInterface="dynamicodt.reporter.it.ReporterPortType", targetNamespace="http://it.reporter.dynamicodt", wsdlLocation="WEB-INF/wsdl/Reporter/reporter.wsdl")
public class Reporter
{
  public byte[] generateDocument(byte[] templateOdt, byte[] xmlData, byte[] xmlStaticData, byte[] xmlParams, byte[] outputType)
  {
    ReportGenerator rg = new ReportGenerator();
    return rg.generateReport(templateOdt, xmlData, xmlStaticData, xmlParams, outputType);
  }
}
