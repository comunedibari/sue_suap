
package generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import it.wego.cross.ElencoPraticheRequest;
import it.wego.cross.ElencoPraticheResponse;
import it.wego.cross.PraticaDetailRequest;
import it.wego.cross.PraticaDetailResponse;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the generated package. 
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

    private final static QName _PraticaDetailRequest_QNAME = new QName("", "PraticaDetailRequest");
    private final static QName _PraticaDetailResponse_QNAME = new QName("", "PraticaDetailResponse");
    private final static QName _ElencoPraticheRequest_QNAME = new QName("", "ElencoPraticheRequest");
    private final static QName _ElencoPraticheResponse_QNAME = new QName("", "ElencoPraticheResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PraticaDetailRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "PraticaDetailRequest")
    public JAXBElement<PraticaDetailRequest> createPraticaDetailRequest(PraticaDetailRequest value) {
        return new JAXBElement<PraticaDetailRequest>(_PraticaDetailRequest_QNAME, PraticaDetailRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PraticaDetailResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "PraticaDetailResponse")
    public JAXBElement<PraticaDetailResponse> createPraticaDetailResponse(PraticaDetailResponse value) {
        return new JAXBElement<PraticaDetailResponse>(_PraticaDetailResponse_QNAME, PraticaDetailResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ElencoPraticheRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ElencoPraticheRequest")
    public JAXBElement<ElencoPraticheRequest> createElencoPraticheRequest(ElencoPraticheRequest value) {
        return new JAXBElement<ElencoPraticheRequest>(_ElencoPraticheRequest_QNAME, ElencoPraticheRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ElencoPraticheResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ElencoPraticheResponse")
    public JAXBElement<ElencoPraticheResponse> createElencoPraticheResponse(ElencoPraticheResponse value) {
        return new JAXBElement<ElencoPraticheResponse>(_ElencoPraticheResponse_QNAME, ElencoPraticheResponse.class, null, value);
    }

}
