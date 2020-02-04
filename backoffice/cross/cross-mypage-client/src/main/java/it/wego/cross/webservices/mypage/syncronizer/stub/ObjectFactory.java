
package it.wego.cross.webservices.mypage.syncronizer.stub;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.wego.mypagesynchronizer package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SetPraticaResponse_QNAME = new QName("http://mypagesynchronizer.wego.it/", "setPraticaResponse");
    private final static QName _SetPraticaRequest_QNAME = new QName("http://mypagesynchronizer.wego.it/", "setPraticaRequest");
    private final static QName _Exception_QNAME = new QName("http://mypagesynchronizer.wego.it/", "Exception");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.wego.mypagesynchronizer
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Exception }
     * 
     */
    public Exception createException() {
        return new Exception();
    }

    /**
     * Create an instance of {@link SetPraticaResponse }
     * 
     */
    public SetPraticaResponse createSetPraticaResponse() {
        return new SetPraticaResponse();
    }

    /**
     * Create an instance of {@link SetPraticaRequest }
     * 
     */
    public SetPraticaRequest createSetPraticaRequest() {
        return new SetPraticaRequest();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetPraticaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://mypagesynchronizer.wego.it/", name = "setPraticaResponse")
    public JAXBElement<SetPraticaResponse> createSetPraticaResponse(SetPraticaResponse value) {
        return new JAXBElement<SetPraticaResponse>(_SetPraticaResponse_QNAME, SetPraticaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetPraticaRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://mypagesynchronizer.wego.it/", name = "setPraticaRequest")
    public JAXBElement<SetPraticaRequest> createSetPraticaRequest(SetPraticaRequest value) {
        return new JAXBElement<SetPraticaRequest>(_SetPraticaRequest_QNAME, SetPraticaRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://mypagesynchronizer.wego.it/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

}
