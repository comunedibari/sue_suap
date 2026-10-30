//
// Questo file  stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andr persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.02.27 alle 01:48:58 PM CET 
//


package it.wego.cross.xmlold;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.wego.cross.xmlold package. 
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

    private final static QName _Pratica_QNAME = new QName("http://www.wego.it/cross", "pratica");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.wego.cross.xmlold
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Pratica }
     * 
     */
    public Pratica createPratica() {
        return new Pratica();
    }

    /**
     * Create an instance of {@link Immobile }
     * 
     */
    public Immobile createImmobile() {
        return new Immobile();
    }

    /**
     * Create an instance of {@link Recapito }
     * 
     */
    public Recapito createRecapito() {
        return new Recapito();
    }

    /**
     * Create an instance of {@link Evento }
     * 
     */
    public Evento createEvento() {
        return new Evento();
    }

    /**
     * Create an instance of {@link Scadenze }
     * 
     */
    public Scadenze createScadenze() {
        return new Scadenze();
    }

    /**
     * Create an instance of {@link Scadenza }
     * 
     */
    public Scadenza createScadenza() {
        return new Scadenza();
    }

    /**
     * Create an instance of {@link Eventi }
     * 
     */
    public Eventi createEventi() {
        return new Eventi();
    }

    /**
     * Create an instance of {@link Allegato }
     * 
     */
    public Allegato createAllegato() {
        return new Allegato();
    }

    /**
     * Create an instance of {@link Procedimenti }
     * 
     */
    public Procedimenti createProcedimenti() {
        return new Procedimenti();
    }

    /**
     * Create an instance of {@link Anagrafiche }
     * 
     */
    public Anagrafiche createAnagrafiche() {
        return new Anagrafiche();
    }

    /**
     * Create an instance of {@link Anagrafica }
     * 
     */
    public Anagrafica createAnagrafica() {
        return new Anagrafica();
    }

    /**
     * Create an instance of {@link Recapiti }
     * 
     */
    public Recapiti createRecapiti() {
        return new Recapiti();
    }

    /**
     * Create an instance of {@link Procedimento }
     * 
     */
    public Procedimento createProcedimento() {
        return new Procedimento();
    }

    /**
     * Create an instance of {@link DatiCatastali }
     * 
     */
    public DatiCatastali createDatiCatastali() {
        return new DatiCatastali();
    }

    /**
     * Create an instance of {@link Allegati }
     * 
     */
    public Allegati createAllegati() {
        return new Allegati();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Pratica }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.wego.it/cross", name = "pratica")
    public JAXBElement<Pratica> createPratica(Pratica value) {
        return new JAXBElement<Pratica>(_Pratica_QNAME, Pratica.class, null, value);
    }

}
