package dynamicodt.reporter.it;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import it.dynamicodt.reporter.it.xsd.ObjectFactory;

@WebService(name="ReporterPortType", targetNamespace="http://it.reporter.dynamicodt")
@XmlSeeAlso({ObjectFactory.class})
public abstract interface ReporterPortType
{
  @WebMethod(action="urn:generateDocument")
  @WebResult(targetNamespace="http://it.reporter.dynamicodt/xsd")
  @RequestWrapper(localName="generateDocument", targetNamespace="http://it.reporter.dynamicodt/xsd", className="dynamicodt.reporter.it.xsd.GenerateDocument")
  @ResponseWrapper(localName="generateDocumentResponse", targetNamespace="http://it.reporter.dynamicodt/xsd", className="dynamicodt.reporter.it.xsd.GenerateDocumentResponse")
  public abstract byte[] generateDocument(
		  @WebParam(name="odtTemplate",  targetNamespace="http://it.reporter.dynamicodt/xsd") byte[] paramArrayOfByte1, 
		  @WebParam(name="xmlData", targetNamespace="http://it.reporter.dynamicodt/xsd") byte[] paramArrayOfByte2, 
		  @WebParam(name="xmlStaticData", targetNamespace="http://it.reporter.dynamicodt/xsd") byte[] paramArrayOfByte3, 
		  @WebParam(name="xmlParams", targetNamespace="http://it.reporter.dynamicodt/xsd") byte[] paramArrayOfByte4, 
		  @WebParam(name="docOutputType", targetNamespace="http://it.reporter.dynamicodt/xsd") byte[] paramArrayOfByte5);
}
