//
// Questo file  stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andr persa durante la ricompilazione dello schema di origine. 
// Generato il: 2013.03.28 alle 09:48:43 AM CET 
//


package it.wego.cross.genova.anagrafe.client.xml;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.wego.cross.plugins.anagrafe.client.xml package. 
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

    private final static QName _TOPONOMASTICA_QNAME = new QName("", "TOPONOMASTICA");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.wego.cross.plugins.anagrafe.client.xml
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Tipo }
     * 
     */
    public Tipo createTipo() {
        return new Tipo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Tipo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "TOPONOMASTICA")
    public JAXBElement<Tipo> createTOPONOMASTICA(Tipo value) {
        return new JAXBElement<Tipo>(_TOPONOMASTICA_QNAME, Tipo.class, null, value);
    }

}
