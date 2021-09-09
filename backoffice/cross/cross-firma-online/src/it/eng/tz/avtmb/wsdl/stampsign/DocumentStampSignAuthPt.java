package it.eng.tz.avtmb.wsdl.stampsign;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.3.9
 * 2020-11-10T16:12:25.605+01:00
 * Generated source version: 2.3.9
 * 
 */
@WebService(targetNamespace = "http://stampsign.wsdl.avtmb.tz.eng.it", name = "documentStampSignAuthPt")
@XmlSeeAlso({it.eng.tz.avtmb.xsd.stampsign.ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface DocumentStampSignAuthPt {

    @WebMethod(action = "http://stampsign.wsdl.avtmb.tz.eng.it/documentStampSignAuth")
    @WebResult(name = "stampSignAuthResponse", targetNamespace = "http://stampsign.xsd.avtmb.tz.eng.it", partName = "documentStampSignOutPart")
    public it.eng.tz.avtmb.xsd.stampsign.StampSignAuthResponse documentStampSignAuth(
        @WebParam(partName = "documentStampSignInAuthPart", name = "stampSignAuthRequest", targetNamespace = "http://stampsign.xsd.avtmb.tz.eng.it")
        it.eng.tz.avtmb.xsd.stampsign.StampSignAuthRequest documentStampSignInAuthPart
    );
}
