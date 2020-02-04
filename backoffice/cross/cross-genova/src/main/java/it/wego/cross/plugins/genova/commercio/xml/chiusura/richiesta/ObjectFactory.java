//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.05.07 alle 04:47:26 PM CEST 
//


package it.wego.cross.plugins.genova.commercio.xml.chiusura.richiesta;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.wego.cross.plugins.genova.commercio.chiusurarichiesta package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.wego.cross.plugins.genova.commercio.chiusurarichiesta
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Chiusura }
     * 
     */
    public Chiusura createChiusura() {
        return new Chiusura();
    }

    /**
     * Create an instance of {@link Chiusura.Sicurezza }
     * 
     */
    public Chiusura.Sicurezza createChiusuraSicurezza() {
        return new Chiusura.Sicurezza();
    }

    /**
     * Create an instance of {@link Chiusura.Pratica }
     * 
     */
    public Chiusura.Pratica createChiusuraPratica() {
        return new Chiusura.Pratica();
    }

}
