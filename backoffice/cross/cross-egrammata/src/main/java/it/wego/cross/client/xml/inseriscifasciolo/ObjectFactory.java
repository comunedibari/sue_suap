//
// Questo filestato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andr� persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.07.24 alle 10:59:37 AM CEST 
//


package it.wego.cross.client.xml.inseriscifasciolo;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.wego.cross.client.xml.inseriscifascicolo package. 
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

    private final static QName _TXTOGG_QNAME = new QName("", "TXT_OGG");
    private final static QName _Codice_QNAME = new QName("", "Codice");
    private final static QName _Liv4In_QNAME = new QName("", "Liv4In");
    private final static QName _CODICE_QNAME = new QName("", "CODICE");
    private final static QName _ProgrFascRifIn_QNAME = new QName("", "ProgrFascRifIn");
    private final static QName _LIVELLO4CLASS_QNAME = new QName("", "LIVELLO4_CLASS");
    private final static QName _MotiviRifIn_QNAME = new QName("", "MotiviRifIn");
    private final static QName _FlgFSIn_QNAME = new QName("", "FlgFSIn");
    private final static QName _VALORE_QNAME = new QName("", "VALORE");
    private final static QName _IDFASCICOLO_QNAME = new QName("", "ID_FASCICOLO");
    private final static QName _SOTTOCLASSECLASS_QNAME = new QName("", "SOTTOCLASSE_CLASS");
    private final static QName _NUMFASC_QNAME = new QName("", "NUM_FASC");
    private final static QName _Liv5In_QNAME = new QName("", "Liv5In");
    private final static QName _IDATTR_QNAME = new QName("", "ID_ATTR");
    private final static QName _NoteIn_QNAME = new QName("", "NoteIn");
    private final static QName _DESTITOLAZIONE_QNAME = new QName("", "DES_TITOLAZIONE");
    private final static QName _IdUoIn_QNAME = new QName("", "IdUoIn");
    private final static QName _FlgPropagaAclDoc_QNAME = new QName("", "FlgPropagaAclDoc");
    private final static QName _ANNOFASC_QNAME = new QName("", "ANNO_FASC");
    private final static QName _ClasseFascRifIn_QNAME = new QName("", "ClasseFascRifIn");
    private final static QName _IdFascicoloRifIn_QNAME = new QName("", "IdFascicoloRifIn");
    private final static QName _TxtOggIn_QNAME = new QName("", "TxtOggIn");
    private final static QName _FlgRiservatezzaIn_QNAME = new QName("", "FlgRiservatezzaIn");
    private final static QName _TitoloFascRifIn_QNAME = new QName("", "TitoloFascRifIn");
    private final static QName _DTARCH_QNAME = new QName("", "DT_ARCH");
    private final static QName _MOTIVIRIF_QNAME = new QName("", "MOTIVI_RIF");
    private final static QName _LIVELLO5CLASS_QNAME = new QName("", "LIVELLO5_CLASS");
    private final static QName _DTVERSAMENTO_QNAME = new QName("", "DT_VERSAMENTO");
    private final static QName _IdFascicoloIn_QNAME = new QName("", "IdFascicoloIn");
    private final static QName _DTCHIUSURA_QNAME = new QName("", "DT_CHIUSURA");
    private final static QName _IdTitolazioneIn_QNAME = new QName("", "IdTitolazioneIn");
    private final static QName _DECFASCRIF_QNAME = new QName("", "DEC_FASC_RIF");
    private final static QName _NUMSOTTOFASCRIF_QNAME = new QName("", "NUM_SOTTOFASC_RIF");
    private final static QName _IdUOInCaricoIn_QNAME = new QName("", "IdUOInCaricoIn");
    private final static QName _FLGANN_QNAME = new QName("", "FLG_ANN");
    private final static QName _CLASSECLASS_QNAME = new QName("", "CLASSE_CLASS");
    private final static QName _Messaggio_QNAME = new QName("", "Messaggio");
    private final static QName _NumSottofascRifIn_QNAME = new QName("", "NumSottofascRifIn");
    private final static QName _SottoClasseFascRifIn_QNAME = new QName("", "SottoClasseFascRifIn");
    private final static QName _IDFASCICOLORIF_QNAME = new QName("", "ID_FASCICOLO_RIF");
    private final static QName _NUMSOTTOFASC_QNAME = new QName("", "NUM_SOTTOFASC");
    private final static QName _AnnoFascRifIn_QNAME = new QName("", "AnnoFascRifIn");
    private final static QName _ParoleChiaveIn_QNAME = new QName("", "ParoleChiaveIn");
    private final static QName _PAROLECHIAVE_QNAME = new QName("", "PAROLE_CHIAVE");
    private final static QName _DTAPERTURA_QNAME = new QName("", "DT_APERTURA");
    private final static QName _TITOLOCLASS_QNAME = new QName("", "TITOLO_CLASS");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.wego.cross.client.xml.inseriscifascicolo
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Dati }
     * 
     */
    public Dati createDati() {
        return new Dati();
    }

    /**
     * Create an instance of {@link VAttrOpzIn }
     * 
     */
    public VAttrOpzIn createVAttrOpzIn() {
        return new VAttrOpzIn();
    }

    /**
     * Create an instance of {@link InserimentoFasc }
     * 
     */
    public InserimentoFasc createInserimentoFasc() {
        return new InserimentoFasc();
    }

    /**
     * Create an instance of {@link DatiComplex }
     * 
     */
    public DatiComplex createDatiComplex() {
        return new DatiComplex();
    }

    /**
     * Create an instance of {@link RisultatoRicerca }
     * 
     */
    public RisultatoRicerca createRisultatoRicerca() {
        return new RisultatoRicerca();
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
    @XmlElementDecl(namespace = "", name = "TXT_OGG")
    public JAXBElement<String> createTXTOGG(String value) {
        return new JAXBElement<String>(_TXTOGG_QNAME, String.class, null, value);
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
    @XmlElementDecl(namespace = "", name = "Liv4In")
    public JAXBElement<String> createLiv4In(String value) {
        return new JAXBElement<String>(_Liv4In_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "CODICE")
    public JAXBElement<String> createCODICE(String value) {
        return new JAXBElement<String>(_CODICE_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ProgrFascRifIn")
    public JAXBElement<String> createProgrFascRifIn(String value) {
        return new JAXBElement<String>(_ProgrFascRifIn_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "LIVELLO4_CLASS")
    public JAXBElement<String> createLIVELLO4CLASS(String value) {
        return new JAXBElement<String>(_LIVELLO4CLASS_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "MotiviRifIn")
    public JAXBElement<String> createMotiviRifIn(String value) {
        return new JAXBElement<String>(_MotiviRifIn_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "FlgFSIn")
    public JAXBElement<String> createFlgFSIn(String value) {
        return new JAXBElement<String>(_FlgFSIn_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "VALORE")
    public JAXBElement<String> createVALORE(String value) {
        return new JAXBElement<String>(_VALORE_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ID_FASCICOLO")
    public JAXBElement<String> createIDFASCICOLO(String value) {
        return new JAXBElement<String>(_IDFASCICOLO_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "SOTTOCLASSE_CLASS")
    public JAXBElement<String> createSOTTOCLASSECLASS(String value) {
        return new JAXBElement<String>(_SOTTOCLASSECLASS_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "NUM_FASC")
    public JAXBElement<String> createNUMFASC(String value) {
        return new JAXBElement<String>(_NUMFASC_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Liv5In")
    public JAXBElement<String> createLiv5In(String value) {
        return new JAXBElement<String>(_Liv5In_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ID_ATTR")
    public JAXBElement<String> createIDATTR(String value) {
        return new JAXBElement<String>(_IDATTR_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "NoteIn")
    public JAXBElement<String> createNoteIn(String value) {
        return new JAXBElement<String>(_NoteIn_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "DES_TITOLAZIONE")
    public JAXBElement<String> createDESTITOLAZIONE(String value) {
        return new JAXBElement<String>(_DESTITOLAZIONE_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "IdUoIn")
    public JAXBElement<String> createIdUoIn(String value) {
        return new JAXBElement<String>(_IdUoIn_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "FlgPropagaAclDoc")
    public JAXBElement<String> createFlgPropagaAclDoc(String value) {
        return new JAXBElement<String>(_FlgPropagaAclDoc_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ANNO_FASC")
    public JAXBElement<String> createANNOFASC(String value) {
        return new JAXBElement<String>(_ANNOFASC_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ClasseFascRifIn")
    public JAXBElement<String> createClasseFascRifIn(String value) {
        return new JAXBElement<String>(_ClasseFascRifIn_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "IdFascicoloRifIn")
    public JAXBElement<String> createIdFascicoloRifIn(String value) {
        return new JAXBElement<String>(_IdFascicoloRifIn_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "TxtOggIn")
    public JAXBElement<String> createTxtOggIn(String value) {
        return new JAXBElement<String>(_TxtOggIn_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "FlgRiservatezzaIn")
    public JAXBElement<String> createFlgRiservatezzaIn(String value) {
        return new JAXBElement<String>(_FlgRiservatezzaIn_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "TitoloFascRifIn")
    public JAXBElement<String> createTitoloFascRifIn(String value) {
        return new JAXBElement<String>(_TitoloFascRifIn_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "DT_ARCH")
    public JAXBElement<String> createDTARCH(String value) {
        return new JAXBElement<String>(_DTARCH_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "MOTIVI_RIF")
    public JAXBElement<String> createMOTIVIRIF(String value) {
        return new JAXBElement<String>(_MOTIVIRIF_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "LIVELLO5_CLASS")
    public JAXBElement<String> createLIVELLO5CLASS(String value) {
        return new JAXBElement<String>(_LIVELLO5CLASS_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "DT_VERSAMENTO")
    public JAXBElement<String> createDTVERSAMENTO(String value) {
        return new JAXBElement<String>(_DTVERSAMENTO_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "IdFascicoloIn")
    public JAXBElement<String> createIdFascicoloIn(String value) {
        return new JAXBElement<String>(_IdFascicoloIn_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "DT_CHIUSURA")
    public JAXBElement<String> createDTCHIUSURA(String value) {
        return new JAXBElement<String>(_DTCHIUSURA_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "IdTitolazioneIn")
    public JAXBElement<String> createIdTitolazioneIn(String value) {
        return new JAXBElement<String>(_IdTitolazioneIn_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "DEC_FASC_RIF")
    public JAXBElement<String> createDECFASCRIF(String value) {
        return new JAXBElement<String>(_DECFASCRIF_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "NUM_SOTTOFASC_RIF")
    public JAXBElement<String> createNUMSOTTOFASCRIF(String value) {
        return new JAXBElement<String>(_NUMSOTTOFASCRIF_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "IdUOInCaricoIn")
    public JAXBElement<String> createIdUOInCaricoIn(String value) {
        return new JAXBElement<String>(_IdUOInCaricoIn_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "FLG_ANN")
    public JAXBElement<String> createFLGANN(String value) {
        return new JAXBElement<String>(_FLGANN_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "CLASSE_CLASS")
    public JAXBElement<String> createCLASSECLASS(String value) {
        return new JAXBElement<String>(_CLASSECLASS_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Messaggio")
    public JAXBElement<String> createMessaggio(String value) {
        return new JAXBElement<String>(_Messaggio_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "NumSottofascRifIn")
    public JAXBElement<String> createNumSottofascRifIn(String value) {
        return new JAXBElement<String>(_NumSottofascRifIn_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "SottoClasseFascRifIn")
    public JAXBElement<String> createSottoClasseFascRifIn(String value) {
        return new JAXBElement<String>(_SottoClasseFascRifIn_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ID_FASCICOLO_RIF")
    public JAXBElement<String> createIDFASCICOLORIF(String value) {
        return new JAXBElement<String>(_IDFASCICOLORIF_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "NUM_SOTTOFASC")
    public JAXBElement<String> createNUMSOTTOFASC(String value) {
        return new JAXBElement<String>(_NUMSOTTOFASC_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "AnnoFascRifIn")
    public JAXBElement<String> createAnnoFascRifIn(String value) {
        return new JAXBElement<String>(_AnnoFascRifIn_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ParoleChiaveIn")
    public JAXBElement<String> createParoleChiaveIn(String value) {
        return new JAXBElement<String>(_ParoleChiaveIn_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "PAROLE_CHIAVE")
    public JAXBElement<String> createPAROLECHIAVE(String value) {
        return new JAXBElement<String>(_PAROLECHIAVE_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "DT_APERTURA")
    public JAXBElement<String> createDTAPERTURA(String value) {
        return new JAXBElement<String>(_DTAPERTURA_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "TITOLO_CLASS")
    public JAXBElement<String> createTITOLOCLASS(String value) {
        return new JAXBElement<String>(_TITOLOCLASS_QNAME, String.class, null, value);
    }

}
