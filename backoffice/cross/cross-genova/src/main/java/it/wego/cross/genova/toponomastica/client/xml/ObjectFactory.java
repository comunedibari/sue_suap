//
// Questo file � stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andr� persa durante la ricompilazione dello schema di origine. 
// Generato il: 2013.04.04 alle 08:31:17 PM CEST 
//


package it.wego.cross.genova.toponomastica.client.xml;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.wego.cross.plugins.precompilazione package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.wego.cross.plugins.precompilazione
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PrecompilazioneBean }
     * 
     */
    public PrecompilazioneBean createPrecompilazioneBean() {
        return new PrecompilazioneBean();
    }

    /**
     * Create an instance of {@link PrecompilazioneBean.Input }
     * 
     */
    public PrecompilazioneBean.Input createPrecompilazioneBeanInput() {
        return new PrecompilazioneBean.Input();
    }

    /**
     * Create an instance of {@link PrecompilazioneBean.Output }
     * 
     */
    public PrecompilazioneBean.Output createPrecompilazioneBeanOutput() {
        return new PrecompilazioneBean.Output();
    }

    /**
     * Create an instance of {@link CampoPrecompilazioneBean }
     * 
     */
    public CampoPrecompilazioneBean createCampoPrecompilazioneBean() {
        return new CampoPrecompilazioneBean();
    }

}
