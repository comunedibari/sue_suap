//
// Questo filestato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andr� persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.07.24 alle 02:23:40 PM CEST 
//


package it.wego.cross.client.xml.rispostainsfasc;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.wego.cross.client.xml.rispostainsfasc package. 
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

    private final static QName _Codice_QNAME = new QName("", "Codice");
    private final static QName _Messaggio_QNAME = new QName("", "Messaggio");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.wego.cross.client.xml.rispostainsfasc
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RispostaInsFasc }
     * 
     */
    public RispostaInsFasc createRispostaInsFasc() {
        return new RispostaInsFasc();
    }

    /**
     * Create an instance of {@link StatoComplex }
     * 
     */
    public StatoComplex createStatoComplex() {
        return new StatoComplex();
    }

    /**
     * Create an instance of {@link Stato }
     * 
     */
    public Stato createStato() {
        return new Stato();
    }

    /**
     * Create an instance of {@link Fascicolo }
     * 
     */
    public Fascicolo createFascicolo() {
        return new Fascicolo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Codice")
    public JAXBElement<String> createCodice(String value) {
        return new JAXBElement<String>(_Codice_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Messaggio")
    public JAXBElement<String> createMessaggio(String value) {
        return new JAXBElement<String>(_Messaggio_QNAME, String.class, null, value);
    }

}
