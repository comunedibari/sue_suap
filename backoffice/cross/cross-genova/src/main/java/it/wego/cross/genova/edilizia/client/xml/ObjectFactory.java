//
// Questo file � stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andr� persa durante la ricompilazione dello schema di origine. 
// Generato il: 2013.04.17 alle 05:51:30 PM CEST 
//
package it.wego.cross.genova.edilizia.client.xml;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each Java content interface and Java
 * element interface generated in the it.wego.cross.events package. <p>An
 * ObjectFactory allows you to programatically construct new instances of the
 * Java representation for XML content. The Java representation of XML content
 * can consist of schema derived interfaces and classes representing the binding
 * of schema type definitions, element declarations and model groups. Factory
 * methods for each of these are provided in this class.
 *
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _EVENTO_QNAME = new QName("", "EVENTO");
    private final static QName _RispostaBO_QNAME = new QName("", "RispostaBO");

    /**
     * Create a new ObjectFactory that can be used to create new instances of
     * schema derived classes for package: it.wego.cross.events
     *
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Evt }
     *
     */
    public Evt createEvt() {
        return new Evt();
    }

    /**
     * Create an instance of {@link Allegato }
     *
     */
    public Allegato createAllegato() {
        return new Allegato();
    }

    /**
     * Create an instance of {@link Allegati }
     *
     */
    public Allegati createAllegati() {
        return new Allegati();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Evt }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "", name = "EVENTO")
    public JAXBElement<Evt> createEVENTO(Evt value) {
        return new JAXBElement<Evt>(_EVENTO_QNAME, Evt.class, null, value);
    }

    /**
     * Create an instance of {@link RispostaBO }
     * 
     */
    public RispostaBO createRispostaBO() {
        return new RispostaBO();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RispostaBO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "RispostaBO")
    public JAXBElement<RispostaBO> createRispostaBO(RispostaBO value) {
        return new JAXBElement<RispostaBO>(_RispostaBO_QNAME, RispostaBO.class, null, value);
    }

}
