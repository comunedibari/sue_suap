
package it.infocamere.protocollo.ejb;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.infocamere.protocollo.ejb package. 
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

    private final static QName _ProtocolloSUAPException_QNAME = new QName("http://ejb.protocollo.infocamere.it/", "ProtocolloSUAPException");
    private final static QName _ComunicazioneREAResponse_QNAME = new QName("http://ejb.protocollo.infocamere.it/", "comunicazioneREAResponse");
    private final static QName _PraticaSUAPException_QNAME = new QName("http://ejb.protocollo.infocamere.it/", "PraticaSUAPException");
    private final static QName _ComunicazioneREA_QNAME = new QName("http://ejb.protocollo.infocamere.it/", "comunicazioneREA");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.infocamere.protocollo.ejb
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ComunicazioneREA }
     * 
     */
    public ComunicazioneREA createComunicazioneREA() {
        return new ComunicazioneREA();
    }

    /**
     * Create an instance of {@link PraticaSUAPException }
     * 
     */
    public PraticaSUAPException createPraticaSUAPException() {
        return new PraticaSUAPException();
    }

    /**
     * Create an instance of {@link ProtocolloSUAPException }
     * 
     */
    public ProtocolloSUAPException createProtocolloSUAPException() {
        return new ProtocolloSUAPException();
    }

    /**
     * Create an instance of {@link ComunicazioneREAResponse }
     * 
     */
    public ComunicazioneREAResponse createComunicazioneREAResponse() {
        return new ComunicazioneREAResponse();
    }

    /**
     * Create an instance of {@link SoggettoSUAP }
     * 
     */
    public SoggettoSUAP createSoggettoSUAP() {
        return new SoggettoSUAP();
    }

    /**
     * Create an instance of {@link Protocollo }
     * 
     */
    public Protocollo createProtocollo() {
        return new Protocollo();
    }

    /**
     * Create an instance of {@link RispostaREA }
     * 
     */
    public RispostaREA createRispostaREA() {
        return new RispostaREA();
    }

    /**
     * Create an instance of {@link AllegatoSUAPReaXml }
     * 
     */
    public AllegatoSUAPReaXml createAllegatoSUAPReaXml() {
        return new AllegatoSUAPReaXml();
    }

    /**
     * Create an instance of {@link AllegatoSUAPXml }
     * 
     */
    public AllegatoSUAPXml createAllegatoSUAPXml() {
        return new AllegatoSUAPXml();
    }

    /**
     * Create an instance of {@link AllegatoSUAP }
     * 
     */
    public AllegatoSUAP createAllegatoSUAP() {
        return new AllegatoSUAP();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProtocolloSUAPException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ejb.protocollo.infocamere.it/", name = "ProtocolloSUAPException")
    public JAXBElement<ProtocolloSUAPException> createProtocolloSUAPException(ProtocolloSUAPException value) {
        return new JAXBElement<ProtocolloSUAPException>(_ProtocolloSUAPException_QNAME, ProtocolloSUAPException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ComunicazioneREAResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ejb.protocollo.infocamere.it/", name = "comunicazioneREAResponse")
    public JAXBElement<ComunicazioneREAResponse> createComunicazioneREAResponse(ComunicazioneREAResponse value) {
        return new JAXBElement<ComunicazioneREAResponse>(_ComunicazioneREAResponse_QNAME, ComunicazioneREAResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PraticaSUAPException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ejb.protocollo.infocamere.it/", name = "PraticaSUAPException")
    public JAXBElement<PraticaSUAPException> createPraticaSUAPException(PraticaSUAPException value) {
        return new JAXBElement<PraticaSUAPException>(_PraticaSUAPException_QNAME, PraticaSUAPException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ComunicazioneREA }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ejb.protocollo.infocamere.it/", name = "comunicazioneREA")
    public JAXBElement<ComunicazioneREA> createComunicazioneREA(ComunicazioneREA value) {
        return new JAXBElement<ComunicazioneREA>(_ComunicazioneREA_QNAME, ComunicazioneREA.class, null, value);
    }

}
