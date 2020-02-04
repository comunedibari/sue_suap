//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrè persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.05.07 alle 04:53:14 PM CEST 
//


package it.wego.cross.plugins.genova.commercio.xml.avvio.richiesta;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.wego.cross.plugins.genova.commercio.xml.avvio.richiesta package. 
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


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.wego.cross.plugins.genova.commercio.xml.avvio.richiesta
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Avvio }
     * 
     */
    public Avvio createAvvio() {
        return new Avvio();
    }

    /**
     * Create an instance of {@link Avvio.Sicurezza }
     * 
     */
    public Avvio.Sicurezza createAvvioSicurezza() {
        return new Avvio.Sicurezza();
    }

    /**
     * Create an instance of {@link Avvio.Anagrafica }
     * 
     */
    public Avvio.Anagrafica createAvvioAnagrafica() {
        return new Avvio.Anagrafica();
    }

    /**
     * Create an instance of {@link Avvio.Protocollo }
     * 
     */
    public Avvio.Protocollo createAvvioProtocollo() {
        return new Avvio.Protocollo();
    }

}
