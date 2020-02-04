//
// Questo file stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrpersa durante la ricompilazione dello schema di origine. 
// Generato il: PM.03.05 alle 07:27:19 PM CET 
//


package it.wego.cross.client.xml.checkout;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.wego.cross.documenti.client.auriga package. 
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

    private final static QName _EstremiXIdentificazioneDoc_QNAME = new QName("", "EstremiXIdentificazioneDoc");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.wego.cross.documenti.client.auriga
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link EstremiXIdentificazioneDocType }
     * 
     */
    public EstremiXIdentificazioneDocType createEstremiXIdentificazioneDocType() {
        return new EstremiXIdentificazioneDocType();
    }

    /**
     * Create an instance of {@link EstremiRegNumType }
     * 
     */
    public EstremiRegNumType createEstremiRegNumType() {
        return new EstremiRegNumType();
    }

    /**
     * Create an instance of {@link EstremiXIdentificazioneUDType }
     * 
     */
    public EstremiXIdentificazioneUDType createEstremiXIdentificazioneUDType() {
        return new EstremiXIdentificazioneUDType();
    }

    /**
     * Create an instance of {@link OggDiTabDiSistemaType }
     * 
     */
    public OggDiTabDiSistemaType createOggDiTabDiSistemaType() {
        return new OggDiTabDiSistemaType();
    }

    /**
     * Create an instance of {@link EstremiXIdentificazioneDocType.DocVsUD }
     * 
     */
    public EstremiXIdentificazioneDocType.DocVsUD createEstremiXIdentificazioneDocTypeDocVsUD() {
        return new EstremiXIdentificazioneDocType.DocVsUD();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EstremiXIdentificazioneDocType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "EstremiXIdentificazioneDoc")
    public JAXBElement<EstremiXIdentificazioneDocType> createEstremiXIdentificazioneDoc(EstremiXIdentificazioneDocType value) {
        return new JAXBElement<EstremiXIdentificazioneDocType>(_EstremiXIdentificazioneDoc_QNAME, EstremiXIdentificazioneDocType.class, null, value);
    }

}
