
package it.wego.cross.webservices.mypage.syncronizer;

import it.wego.cross.webservices.mypage.syncronizer.Exception;
import it.wego.cross.webservices.mypage.syncronizer.stub.ObjectFactory;
import it.wego.cross.webservices.mypage.syncronizer.stub.SetPraticaRequest;
import it.wego.cross.webservices.mypage.syncronizer.stub.SetPraticaResponse;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6-1b01 
 * Generated source version: 2.1
 * 
 */
@WebService(name = "MyPageSynchronizerService", targetNamespace = "http://mypagesynchronizer.wego.it/")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface MyPageSynchronizerService {


    /**
     * 
     * @param parameters
     * @return
     *     returns it.wego.cross.webservices.mypage.syncronizer.SetPraticaResponse
     * @throws Exception
     */
    @WebMethod
    @WebResult(name = "setPraticaResponse", targetNamespace = "http://mypagesynchronizer.wego.it/", partName = "setPraticaResponse")
    public SetPraticaResponse setPratica(
        @WebParam(name = "setPraticaRequest", targetNamespace = "http://mypagesynchronizer.wego.it/", partName = "parameters")
        SetPraticaRequest parameters)
        throws Exception
    ;

}
