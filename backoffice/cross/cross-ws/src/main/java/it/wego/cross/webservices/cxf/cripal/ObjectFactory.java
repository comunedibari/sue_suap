
package it.wego.cross.webservices.cxf.cripal;

import it.wego.cross.webservices.cxf.*;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.wego.cross package. 
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

    private final static QName _PraticaDetailRequest_QNAME = new QName("http://www.wego.it/cross", "PraticaDetailRequest");
    private final static QName _ElencoPraticheRequest_QNAME = new QName("http://www.wego.it/cross", "ElencoPraticheRequest");
    private final static QName _ElencoPraticheResponse_QNAME = new QName("http://www.wego.it/cross", "ElencoPraticheResponse");
    private final static QName _PraticaDetailResponse_QNAME = new QName("http://www.wego.it/cross", "PraticaDetailResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.wego.cross
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PraticaSIT }
     * 
     */
    public PraticaSIT createPraticaSIT() {
        return new PraticaSIT();
    }

    /**
     * Create an instance of {@link ElencoPraticheResponse }
     * 
     */
    public ElencoPraticheResponse createElencoPraticheResponse() {
        return new ElencoPraticheResponse();
    }

    /**
     * Create an instance of {@link PraticaDetailRequest }
     * 
     */
    public PraticaDetailRequest createPraticaDetailRequest() {
        return new PraticaDetailRequest();
    }

    /**
     * Create an instance of {@link PraticaDetailResponse }
     * 
     */
    public PraticaDetailResponse createPraticaDetailResponse() {
        return new PraticaDetailResponse();
    }

    /**
     * Create an instance of {@link ElencoPraticheRequest }
     * 
     */
    public ElencoPraticheRequest createElencoPraticheRequest() {
        return new ElencoPraticheRequest();
    }

    /**
     * Create an instance of {@link IndirizziInterventoSIT }
     * 
     */
    public IndirizziInterventoSIT createIndirizziInterventoSIT() {
        return new IndirizziInterventoSIT();
    }

    /**
     * Create an instance of {@link IndirizzoInterventoSIT }
     * 
     */
    public IndirizzoInterventoSIT createIndirizzoInterventoSIT() {
        return new IndirizzoInterventoSIT();
    }

    /**
     * Create an instance of {@link ProcedimentoSIT }
     * 
     */
    public ProcedimentoSIT createProcedimentoSIT() {
        return new ProcedimentoSIT();
    }

    /**
     * Create an instance of {@link ProcedimentiSIT }
     * 
     */
    public ProcedimentiSIT createProcedimentiSIT() {
        return new ProcedimentiSIT();
    }

    /**
     * Create an instance of {@link AnagraficaSIT }
     * 
     */
    public AnagraficaSIT createAnagraficaSIT() {
        return new AnagraficaSIT();
    }

    /**
     * Create an instance of {@link DatiCatastaliSIT }
     * 
     */
    public DatiCatastaliSIT createDatiCatastaliSIT() {
        return new DatiCatastaliSIT();
    }

    /**
     * Create an instance of {@link DatoCatastaleSIT }
     * 
     */
    public DatoCatastaleSIT createDatoCatastaleSIT() {
        return new DatoCatastaleSIT();
    }

    /**
     * Create an instance of {@link AnagraficheSIT }
     * 
     */
    public AnagraficheSIT createAnagraficheSIT() {
        return new AnagraficheSIT();
    }

    /**
     * Create an instance of {@link PraticaSIT.SegnaturaProtocollo }
     * 
     */
    public PraticaSIT.SegnaturaProtocollo createPraticaSITSegnaturaProtocollo() {
        return new PraticaSIT.SegnaturaProtocollo();
    }

    /**
     * Create an instance of {@link ElencoPraticheResponse.Pratica }
     * 
     */
    public ElencoPraticheResponse.Pratica createElencoPraticheResponsePratica() {
        return new ElencoPraticheResponse.Pratica();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PraticaDetailRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.wego.it/cross", name = "PraticaDetailRequest")
    public JAXBElement<PraticaDetailRequest> createPraticaDetailRequest(PraticaDetailRequest value) {
        return new JAXBElement<PraticaDetailRequest>(_PraticaDetailRequest_QNAME, PraticaDetailRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ElencoPraticheRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.wego.it/cross", name = "ElencoPraticheRequest")
    public JAXBElement<ElencoPraticheRequest> createElencoPraticheRequest(ElencoPraticheRequest value) {
        return new JAXBElement<ElencoPraticheRequest>(_ElencoPraticheRequest_QNAME, ElencoPraticheRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ElencoPraticheResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.wego.it/cross", name = "ElencoPraticheResponse")
    public JAXBElement<ElencoPraticheResponse> createElencoPraticheResponse(ElencoPraticheResponse value) {
        return new JAXBElement<ElencoPraticheResponse>(_ElencoPraticheResponse_QNAME, ElencoPraticheResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PraticaDetailResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.wego.it/cross", name = "PraticaDetailResponse")
    public JAXBElement<PraticaDetailResponse> createPraticaDetailResponse(PraticaDetailResponse value) {
        return new JAXBElement<PraticaDetailResponse>(_PraticaDetailResponse_QNAME, PraticaDetailResponse.class, null, value);
    }

}
