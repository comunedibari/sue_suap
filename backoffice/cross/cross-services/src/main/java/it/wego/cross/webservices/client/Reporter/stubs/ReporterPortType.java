
package it.wego.cross.webservices.client.Reporter.stubs;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "ReporterPortType", targetNamespace = "http://it.reporter.dynamicodt")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface ReporterPortType {


    /**
     * 
     * @param xmlParams
     * @param xmlStaticData
     * @param xmlData
     * @param docOutputType
     * @param odtTemplate
     * @return
     *     returns byte[]
     */
    @WebMethod(action = "urn:generateDocument")
    @WebResult(targetNamespace = "http://it.reporter.dynamicodt/xsd")
    @RequestWrapper(localName = "generateDocument", targetNamespace = "http://it.reporter.dynamicodt/xsd", className = "it.wego.cross.webservices.client.reporter.GenerateDocument")
    @ResponseWrapper(localName = "generateDocumentResponse", targetNamespace = "http://it.reporter.dynamicodt/xsd", className = "it.wego.cross.webservices.client.reporter.GenerateDocumentResponse")
    public byte[] generateDocument(
        @WebParam(name = "odtTemplate", targetNamespace = "http://it.reporter.dynamicodt/xsd")
        byte[] odtTemplate,
        @WebParam(name = "xmlData", targetNamespace = "http://it.reporter.dynamicodt/xsd")
        byte[] xmlData,
        @WebParam(name = "xmlStaticData", targetNamespace = "http://it.reporter.dynamicodt/xsd")
        byte[] xmlStaticData,
        @WebParam(name = "xmlParams", targetNamespace = "http://it.reporter.dynamicodt/xsd")
        byte[] xmlParams,
        @WebParam(name = "docOutputType", targetNamespace = "http://it.reporter.dynamicodt/xsd")
        byte[] docOutputType);

}
