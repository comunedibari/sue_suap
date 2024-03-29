
package it.wego.cross.genova.toponomastica.client.stub;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6-1b01 
 * Generated source version: 2.2
 * 
 */
@WebService(name = "elencoVieSoap", targetNamespace = "http://webservice.backend.people.it/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface ElencoVieSoap {


    /**
     * 
     * @param data
     * @return
     *     returns java.lang.String
     */
    @WebMethod(action = "process")
    @WebResult(name = "processResult", targetNamespace = "http://webservice.backend.people.it/")
    @RequestWrapper(localName = "process", targetNamespace = "http://webservice.backend.people.it/", className = "it.wego.cross.plugins.ricercadaticatastali.Process")
    @ResponseWrapper(localName = "processResponse", targetNamespace = "http://webservice.backend.people.it/", className = "it.wego.cross.plugins.ricercadaticatastali.ProcessResponse")
    public String process(
        @WebParam(name = "data", targetNamespace = "http://webservice.backend.people.it/")
        String data);

}
