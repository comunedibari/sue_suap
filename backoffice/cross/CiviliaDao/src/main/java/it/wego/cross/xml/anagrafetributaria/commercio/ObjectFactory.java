//
// Questo file e' stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andra' persa durante la ricompilazione dello schema di origine. 
// Generato il: 2013.02.05 alle 11:21:53 AM CET 
//


package it.wego.cross.xml.anagrafetributaria.commercio;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.wego.cross.xml.anagrafetributaria.commercio package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.wego.cross.xml.anagrafetributaria.commercio
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AnagrafeTributariaCommercio }
     * 
     */
    public AnagrafeTributariaCommercio createAnagrafeTributariaCommercio() {
        return new AnagrafeTributariaCommercio();
    }

    /**
     * Create an instance of {@link RecordControllo }
     * 
     */
    public RecordControllo createRecordControllo() {
        return new RecordControllo();
    }

    /**
     * Create an instance of {@link AnagrafeTributariaCommercio.RecordsDettaglio }
     * 
     */
    public AnagrafeTributariaCommercio.RecordsDettaglio createAnagrafeTributariaCommercioRecordsDettaglio() {
        return new AnagrafeTributariaCommercio.RecordsDettaglio();
    }

    /**
     * Create an instance of {@link RecordDettaglio }
     * 
     */
    public RecordDettaglio createRecordDettaglio() {
        return new RecordDettaglio();
    }

}
