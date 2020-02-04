// Questo file  stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andr persa durante la ricompilazione dello schema di origine. 
// Generato il: 2012.11.27 alle 11:16:28 AM CET 
//


package it.gov.impresainungiorno.schema.suap.rea;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.gov.impresainungiorno.schema.suap.rea package. 
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

    private final static QName _ComunicazioneRea_QNAME = new QName("http://www.impresainungiorno.gov.it/schema/suap/rea", "comunicazione-rea");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.gov.impresainungiorno.schema.suap.rea
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
     * Create an instance of {@link ComunicazioneREA.EstremiPraticaSuap }
     * 
     */
    public ComunicazioneREA.EstremiPraticaSuap createComunicazioneREAEstremiPraticaSuap() {
        return new ComunicazioneREA.EstremiPraticaSuap();
    }

    /**
     * Create an instance of {@link AllegatoGenerico }
     * 
     */
    public AllegatoGenerico createAllegatoGenerico() {
        return new AllegatoGenerico();
    }

    /**
     * Create an instance of {@link EsitoScia }
     * 
     */
    public EsitoScia createEsitoScia() {
        return new EsitoScia();
    }

    /**
     * Create an instance of {@link OggettoComunicazioneREA }
     * 
     */
    public OggettoComunicazioneREA createOggettoComunicazioneREA() {
        return new OggettoComunicazioneREA();
    }

    /**
     * Create an instance of {@link AllegatoSuap }
     * 
     */
    public AllegatoSuap createAllegatoSuap() {
        return new AllegatoSuap();
    }

    /**
     * Create an instance of {@link ComunicazioneREA.SuapMittente }
     * 
     */
    public ComunicazioneREA.SuapMittente createComunicazioneREASuapMittente() {
        return new ComunicazioneREA.SuapMittente();
    }

    /**
     * Create an instance of {@link ComunicazioneREA.ComunicazioneScia }
     * 
     */
    public ComunicazioneREA.ComunicazioneScia createComunicazioneREAComunicazioneScia() {
        return new ComunicazioneREA.ComunicazioneScia();
    }

    /**
     * Create an instance of {@link ComunicazioneREA.ComunicazioneEsitoScia }
     * 
     */
    public ComunicazioneREA.ComunicazioneEsitoScia createComunicazioneREAComunicazioneEsitoScia() {
        return new ComunicazioneREA.ComunicazioneEsitoScia();
    }

    /**
     * Create an instance of {@link ComunicazioneREA.EstremiPraticaSuap.Impresa }
     * 
     */
    public ComunicazioneREA.EstremiPraticaSuap.Impresa createComunicazioneREAEstremiPraticaSuapImpresa() {
        return new ComunicazioneREA.EstremiPraticaSuap.Impresa();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ComunicazioneREA }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.impresainungiorno.gov.it/schema/suap/rea", name = "comunicazione-rea")
    public JAXBElement<ComunicazioneREA> createComunicazioneRea(ComunicazioneREA value) {
        return new JAXBElement<ComunicazioneREA>(_ComunicazioneRea_QNAME, ComunicazioneREA.class, null, value);
    }

}
