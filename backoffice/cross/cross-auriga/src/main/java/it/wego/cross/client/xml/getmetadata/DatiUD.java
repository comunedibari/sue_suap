//
// Questo file stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrpersa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.07.14 alle 03:42:18 PM CEST 
//


package it.wego.cross.client.xml.getmetadata;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdUD" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="NomeUD" type="{}NomeUDType"/>
 *         &lt;element name="RegistrazioneData" type="{}RegistrazioneNumerazioneType" maxOccurs="unbounded"/>
 *         &lt;element name="OggettoUD" type="{}OggettoUDType"/>
 *         &lt;element name="TipoDoc" type="{}OggDiTabDiSistemaType"/>
 *         &lt;element name="OriginaleCartaceo" type="{}FlagSiNoType" minOccurs="0"/>
 *         &lt;element name="TipoCartaceo" type="{}TipoCartaceoType" minOccurs="0"/>
 *         &lt;element name="VersioneElettronica" type="{}VersioneElettronicaType"/>
 *         &lt;element name="TipoProvenienza" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="E"/>
 *               &lt;enumeration value="U"/>
 *               &lt;enumeration value="I"/>
 *               &lt;enumeration value=""/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="NroAllegati" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="DatiProduzione" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="DataStesura" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                   &lt;element name="LuogoStesura" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="Estensore" type="{}UserType" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="UffProduttore" type="{}UOType" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="DatiEntrata" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="DataOraArrivo" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *                   &lt;element name="DataDocRicevuto" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                   &lt;element name="EstremiRegistrazioneDocRicevuto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="RiferimentiDocRicevuto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="MezzoTrasmissione" type="{}OggDiTabDiSistemaType" minOccurs="0"/>
 *                   &lt;element name="DataRaccomandata" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                   &lt;element name="NroRaccomandata" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="MittenteEsterno" type="{}SoggettoEsternoType" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="FirmatarioEsterno" type="{}SoggettoEsternoType" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="IndirizzoProv" type="{}IndirizzoType" minOccurs="0"/>
 *                   &lt;element name="IndirizzoEmailProv" type="{}EmailType" minOccurs="0"/>
 *                   &lt;element name="UtenteRicezione" type="{}UserType" minOccurs="0"/>
 *                   &lt;element name="UffRicezione" type="{}UOType" minOccurs="0"/>
 *                   &lt;element name="UtenteCtrlAmmissib" type="{}UserType" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="DatiUscita" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="DataOraSped" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *                   &lt;element name="MezzoTrasmissione" type="{}OggDiTabDiSistemaType" minOccurs="0"/>
 *                   &lt;element name="DataRaccomandata" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                   &lt;element name="NroRaccomandata" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="DestinatarioEsterno" type="{}DestinatarioEsternoType" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="UtenteSpedizione" type="{}UserType" minOccurs="0"/>
 *                   &lt;element name="UffSpedizione" type="{}UOType" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="AltroSoggEsterno" type="{}SoggettoEstEstesoType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="AssegnazioneInterna" type="{}AssegnazioneInternaType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="CopiaDaTenereA" type="{}SoggettoInternoType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="AltroSoggettoInterno" type="{}SoggettoInternoEstesoType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="CollocazioneClassificazioneUD">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="LibreriaUD" type="{}LibreriaType" minOccurs="0"/>
 *                   &lt;element name="ClassifFascicolo" type="{}ClassifFascicoloEstesoType" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="Workspace" type="{}OggDiTabDiSistemaType" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="AllegatoUD" type="{}AllegatoUDType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="InRispostaAUD" type="{}EstremiRegNumType" minOccurs="0"/>
 *         &lt;element name="LivelloRiservatezzaInterno" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/>
 *         &lt;element name="FlagVietatoAccessoEsterno" type="{}FlagSiNoType" minOccurs="0"/>
 *         &lt;element name="VietatoAccessoEsternoFinoAl" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="PermessoVsSoggInterno" type="{}ACLRecordType" maxOccurs="unbounded"/>
 *         &lt;element name="LivelloEvidenza" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/>
 *         &lt;element name="NoteUD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AttributoAddUD" type="{}AttributoAddizionaleType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "idUD",
    "nomeUD",
    "registrazioneData",
    "oggettoUD",
    "tipoDoc",
    "originaleCartaceo",
    "tipoCartaceo",
    "versioneElettronica",
    "tipoProvenienza",
    "nroAllegati",
    "datiProduzione",
    "datiEntrata",
    "datiUscita",
    "altroSoggEsterno",
    "assegnazioneInterna",
    "copiaDaTenereA",
    "altroSoggettoInterno",
    "collocazioneClassificazioneUD",
    "allegatoUD",
    "inRispostaAUD",
    "livelloRiservatezzaInterno",
    "flagVietatoAccessoEsterno",
    "vietatoAccessoEsternoFinoAl",
    "permessoVsSoggInterno",
    "livelloEvidenza",
    "noteUD",
    "attributoAddUD"
})
@XmlRootElement(name = "DatiUD")
public class DatiUD {

    @XmlElement(name = "IdUD", required = true)
    protected BigInteger idUD;
    @XmlElement(name = "NomeUD", required = true)
    protected String nomeUD;
    @XmlElement(name = "RegistrazioneData", required = true)
    protected List<RegistrazioneNumerazioneType> registrazioneData;
    @XmlElement(name = "OggettoUD", required = true)
    protected String oggettoUD;
    @XmlElement(name = "TipoDoc", required = true)
    protected OggDiTabDiSistemaType tipoDoc;
    @XmlElement(name = "OriginaleCartaceo")
    protected String originaleCartaceo;
    @XmlElement(name = "TipoCartaceo")
    protected String tipoCartaceo;
    @XmlElement(name = "VersioneElettronica", required = true)
    protected VersioneElettronicaType versioneElettronica;
    @XmlElement(name = "TipoProvenienza", defaultValue = "I")
    protected String tipoProvenienza;
    @XmlElement(name = "NroAllegati", required = true)
    protected BigInteger nroAllegati;
    @XmlElement(name = "DatiProduzione")
    protected DatiUD.DatiProduzione datiProduzione;
    @XmlElement(name = "DatiEntrata")
    protected DatiUD.DatiEntrata datiEntrata;
    @XmlElement(name = "DatiUscita")
    protected DatiUD.DatiUscita datiUscita;
    @XmlElement(name = "AltroSoggEsterno")
    protected List<SoggettoEstEstesoType> altroSoggEsterno;
    @XmlElement(name = "AssegnazioneInterna")
    protected List<AssegnazioneInternaType> assegnazioneInterna;
    @XmlElement(name = "CopiaDaTenereA")
    protected List<SoggettoInternoType> copiaDaTenereA;
    @XmlElement(name = "AltroSoggettoInterno")
    protected List<SoggettoInternoEstesoType> altroSoggettoInterno;
    @XmlElement(name = "CollocazioneClassificazioneUD", required = true)
    protected DatiUD.CollocazioneClassificazioneUD collocazioneClassificazioneUD;
    @XmlElement(name = "AllegatoUD")
    protected List<AllegatoUDType> allegatoUD;
    @XmlElement(name = "InRispostaAUD")
    protected EstremiRegNumType inRispostaAUD;
    @XmlElement(name = "LivelloRiservatezzaInterno")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger livelloRiservatezzaInterno;
    @XmlElement(name = "FlagVietatoAccessoEsterno")
    protected String flagVietatoAccessoEsterno;
    @XmlElement(name = "VietatoAccessoEsternoFinoAl")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar vietatoAccessoEsternoFinoAl;
    @XmlElement(name = "PermessoVsSoggInterno", required = true)
    protected List<ACLRecordType> permessoVsSoggInterno;
    @XmlElement(name = "LivelloEvidenza")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger livelloEvidenza;
    @XmlElement(name = "NoteUD")
    protected String noteUD;
    @XmlElement(name = "AttributoAddUD")
    protected List<AttributoAddizionaleType> attributoAddUD;

    /**
     * Recupera il valore della proprietidUD.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdUD() {
        return idUD;
    }

    /**
     * Imposta il valore della proprietidUD.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdUD(BigInteger value) {
        this.idUD = value;
    }

    /**
     * Recupera il valore della proprietnomeUD.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeUD() {
        return nomeUD;
    }

    /**
     * Imposta il valore della proprietnomeUD.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeUD(String value) {
        this.nomeUD = value;
    }

    /**
     * Gets the value of the registrazioneData property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the registrazioneData property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRegistrazioneData().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RegistrazioneNumerazioneType }
     * 
     * 
     */
    public List<RegistrazioneNumerazioneType> getRegistrazioneData() {
        if (registrazioneData == null) {
            registrazioneData = new ArrayList<RegistrazioneNumerazioneType>();
        }
        return this.registrazioneData;
    }

    /**
     * Recupera il valore della proprietoggettoUD.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOggettoUD() {
        return oggettoUD;
    }

    /**
     * Imposta il valore della proprietoggettoUD.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOggettoUD(String value) {
        this.oggettoUD = value;
    }

    /**
     * Recupera il valore della propriettipoDoc.
     * 
     * @return
     *     possible object is
     *     {@link OggDiTabDiSistemaType }
     *     
     */
    public OggDiTabDiSistemaType getTipoDoc() {
        return tipoDoc;
    }

    /**
     * Imposta il valore della propriettipoDoc.
     * 
     * @param value
     *     allowed object is
     *     {@link OggDiTabDiSistemaType }
     *     
     */
    public void setTipoDoc(OggDiTabDiSistemaType value) {
        this.tipoDoc = value;
    }

    /**
     * Recupera il valore della proprietoriginaleCartaceo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOriginaleCartaceo() {
        return originaleCartaceo;
    }

    /**
     * Imposta il valore della proprietoriginaleCartaceo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOriginaleCartaceo(String value) {
        this.originaleCartaceo = value;
    }

    /**
     * Recupera il valore della propriettipoCartaceo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoCartaceo() {
        return tipoCartaceo;
    }

    /**
     * Imposta il valore della propriettipoCartaceo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoCartaceo(String value) {
        this.tipoCartaceo = value;
    }

    /**
     * Recupera il valore della proprietversioneElettronica.
     * 
     * @return
     *     possible object is
     *     {@link VersioneElettronicaType }
     *     
     */
    public VersioneElettronicaType getVersioneElettronica() {
        return versioneElettronica;
    }

    /**
     * Imposta il valore della proprietversioneElettronica.
     * 
     * @param value
     *     allowed object is
     *     {@link VersioneElettronicaType }
     *     
     */
    public void setVersioneElettronica(VersioneElettronicaType value) {
        this.versioneElettronica = value;
    }

    /**
     * Recupera il valore della propriettipoProvenienza.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoProvenienza() {
        return tipoProvenienza;
    }

    /**
     * Imposta il valore della propriettipoProvenienza.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoProvenienza(String value) {
        this.tipoProvenienza = value;
    }

    /**
     * Recupera il valore della proprietnroAllegati.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNroAllegati() {
        return nroAllegati;
    }

    /**
     * Imposta il valore della proprietnroAllegati.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNroAllegati(BigInteger value) {
        this.nroAllegati = value;
    }

    /**
     * Recupera il valore della proprietdatiProduzione.
     * 
     * @return
     *     possible object is
     *     {@link DatiUD.DatiProduzione }
     *     
     */
    public DatiUD.DatiProduzione getDatiProduzione() {
        return datiProduzione;
    }

    /**
     * Imposta il valore della proprietdatiProduzione.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiUD.DatiProduzione }
     *     
     */
    public void setDatiProduzione(DatiUD.DatiProduzione value) {
        this.datiProduzione = value;
    }

    /**
     * Recupera il valore della proprietdatiEntrata.
     * 
     * @return
     *     possible object is
     *     {@link DatiUD.DatiEntrata }
     *     
     */
    public DatiUD.DatiEntrata getDatiEntrata() {
        return datiEntrata;
    }

    /**
     * Imposta il valore della proprietdatiEntrata.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiUD.DatiEntrata }
     *     
     */
    public void setDatiEntrata(DatiUD.DatiEntrata value) {
        this.datiEntrata = value;
    }

    /**
     * Recupera il valore della proprietdatiUscita.
     * 
     * @return
     *     possible object is
     *     {@link DatiUD.DatiUscita }
     *     
     */
    public DatiUD.DatiUscita getDatiUscita() {
        return datiUscita;
    }

    /**
     * Imposta il valore della proprietdatiUscita.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiUD.DatiUscita }
     *     
     */
    public void setDatiUscita(DatiUD.DatiUscita value) {
        this.datiUscita = value;
    }

    /**
     * Gets the value of the altroSoggEsterno property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the altroSoggEsterno property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAltroSoggEsterno().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SoggettoEstEstesoType }
     * 
     * 
     */
    public List<SoggettoEstEstesoType> getAltroSoggEsterno() {
        if (altroSoggEsterno == null) {
            altroSoggEsterno = new ArrayList<SoggettoEstEstesoType>();
        }
        return this.altroSoggEsterno;
    }

    /**
     * Gets the value of the assegnazioneInterna property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the assegnazioneInterna property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAssegnazioneInterna().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AssegnazioneInternaType }
     * 
     * 
     */
    public List<AssegnazioneInternaType> getAssegnazioneInterna() {
        if (assegnazioneInterna == null) {
            assegnazioneInterna = new ArrayList<AssegnazioneInternaType>();
        }
        return this.assegnazioneInterna;
    }

    /**
     * Gets the value of the copiaDaTenereA property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the copiaDaTenereA property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCopiaDaTenereA().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SoggettoInternoType }
     * 
     * 
     */
    public List<SoggettoInternoType> getCopiaDaTenereA() {
        if (copiaDaTenereA == null) {
            copiaDaTenereA = new ArrayList<SoggettoInternoType>();
        }
        return this.copiaDaTenereA;
    }

    /**
     * Gets the value of the altroSoggettoInterno property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the altroSoggettoInterno property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAltroSoggettoInterno().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SoggettoInternoEstesoType }
     * 
     * 
     */
    public List<SoggettoInternoEstesoType> getAltroSoggettoInterno() {
        if (altroSoggettoInterno == null) {
            altroSoggettoInterno = new ArrayList<SoggettoInternoEstesoType>();
        }
        return this.altroSoggettoInterno;
    }

    /**
     * Recupera il valore della proprietcollocazioneClassificazioneUD.
     * 
     * @return
     *     possible object is
     *     {@link DatiUD.CollocazioneClassificazioneUD }
     *     
     */
    public DatiUD.CollocazioneClassificazioneUD getCollocazioneClassificazioneUD() {
        return collocazioneClassificazioneUD;
    }

    /**
     * Imposta il valore della proprietcollocazioneClassificazioneUD.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiUD.CollocazioneClassificazioneUD }
     *     
     */
    public void setCollocazioneClassificazioneUD(DatiUD.CollocazioneClassificazioneUD value) {
        this.collocazioneClassificazioneUD = value;
    }

    /**
     * Gets the value of the allegatoUD property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the allegatoUD property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAllegatoUD().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AllegatoUDType }
     * 
     * 
     */
    public List<AllegatoUDType> getAllegatoUD() {
        if (allegatoUD == null) {
            allegatoUD = new ArrayList<AllegatoUDType>();
        }
        return this.allegatoUD;
    }

    /**
     * Recupera il valore della proprietinRispostaAUD.
     * 
     * @return
     *     possible object is
     *     {@link EstremiRegNumType }
     *     
     */
    public EstremiRegNumType getInRispostaAUD() {
        return inRispostaAUD;
    }

    /**
     * Imposta il valore della proprietinRispostaAUD.
     * 
     * @param value
     *     allowed object is
     *     {@link EstremiRegNumType }
     *     
     */
    public void setInRispostaAUD(EstremiRegNumType value) {
        this.inRispostaAUD = value;
    }

    /**
     * Recupera il valore della proprietlivelloRiservatezzaInterno.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getLivelloRiservatezzaInterno() {
        return livelloRiservatezzaInterno;
    }

    /**
     * Imposta il valore della proprietlivelloRiservatezzaInterno.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setLivelloRiservatezzaInterno(BigInteger value) {
        this.livelloRiservatezzaInterno = value;
    }

    /**
     * Recupera il valore della proprietflagVietatoAccessoEsterno.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagVietatoAccessoEsterno() {
        return flagVietatoAccessoEsterno;
    }

    /**
     * Imposta il valore della proprietflagVietatoAccessoEsterno.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagVietatoAccessoEsterno(String value) {
        this.flagVietatoAccessoEsterno = value;
    }

    /**
     * Recupera il valore della proprietvietatoAccessoEsternoFinoAl.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getVietatoAccessoEsternoFinoAl() {
        return vietatoAccessoEsternoFinoAl;
    }

    /**
     * Imposta il valore della proprietvietatoAccessoEsternoFinoAl.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setVietatoAccessoEsternoFinoAl(XMLGregorianCalendar value) {
        this.vietatoAccessoEsternoFinoAl = value;
    }

    /**
     * Gets the value of the permessoVsSoggInterno property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the permessoVsSoggInterno property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPermessoVsSoggInterno().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ACLRecordType }
     * 
     * 
     */
    public List<ACLRecordType> getPermessoVsSoggInterno() {
        if (permessoVsSoggInterno == null) {
            permessoVsSoggInterno = new ArrayList<ACLRecordType>();
        }
        return this.permessoVsSoggInterno;
    }

    /**
     * Recupera il valore della proprietlivelloEvidenza.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getLivelloEvidenza() {
        return livelloEvidenza;
    }

    /**
     * Imposta il valore della proprietlivelloEvidenza.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setLivelloEvidenza(BigInteger value) {
        this.livelloEvidenza = value;
    }

    /**
     * Recupera il valore della proprietnoteUD.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNoteUD() {
        return noteUD;
    }

    /**
     * Imposta il valore della proprietnoteUD.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNoteUD(String value) {
        this.noteUD = value;
    }

    /**
     * Gets the value of the attributoAddUD property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attributoAddUD property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttributoAddUD().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AttributoAddizionaleType }
     * 
     * 
     */
    public List<AttributoAddizionaleType> getAttributoAddUD() {
        if (attributoAddUD == null) {
            attributoAddUD = new ArrayList<AttributoAddizionaleType>();
        }
        return this.attributoAddUD;
    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="LibreriaUD" type="{}LibreriaType" minOccurs="0"/>
     *         &lt;element name="ClassifFascicolo" type="{}ClassifFascicoloEstesoType" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="Workspace" type="{}OggDiTabDiSistemaType" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "libreriaUD",
        "classifFascicolo",
        "workspace"
    })
    public static class CollocazioneClassificazioneUD {

        @XmlElement(name = "LibreriaUD")
        protected String libreriaUD;
        @XmlElement(name = "ClassifFascicolo")
        protected List<ClassifFascicoloEstesoType> classifFascicolo;
        @XmlElement(name = "Workspace")
        protected List<OggDiTabDiSistemaType> workspace;

        /**
         * Recupera il valore della proprietlibreriaUD.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLibreriaUD() {
            return libreriaUD;
        }

        /**
         * Imposta il valore della proprietlibreriaUD.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLibreriaUD(String value) {
            this.libreriaUD = value;
        }

        /**
         * Gets the value of the classifFascicolo property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the classifFascicolo property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getClassifFascicolo().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ClassifFascicoloEstesoType }
         * 
         * 
         */
        public List<ClassifFascicoloEstesoType> getClassifFascicolo() {
            if (classifFascicolo == null) {
                classifFascicolo = new ArrayList<ClassifFascicoloEstesoType>();
            }
            return this.classifFascicolo;
        }

        /**
         * Gets the value of the workspace property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the workspace property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getWorkspace().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link OggDiTabDiSistemaType }
         * 
         * 
         */
        public List<OggDiTabDiSistemaType> getWorkspace() {
            if (workspace == null) {
                workspace = new ArrayList<OggDiTabDiSistemaType>();
            }
            return this.workspace;
        }

    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="DataOraArrivo" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
     *         &lt;element name="DataDocRicevuto" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *         &lt;element name="EstremiRegistrazioneDocRicevuto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="RiferimentiDocRicevuto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="MezzoTrasmissione" type="{}OggDiTabDiSistemaType" minOccurs="0"/>
     *         &lt;element name="DataRaccomandata" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *         &lt;element name="NroRaccomandata" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="MittenteEsterno" type="{}SoggettoEsternoType" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="FirmatarioEsterno" type="{}SoggettoEsternoType" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="IndirizzoProv" type="{}IndirizzoType" minOccurs="0"/>
     *         &lt;element name="IndirizzoEmailProv" type="{}EmailType" minOccurs="0"/>
     *         &lt;element name="UtenteRicezione" type="{}UserType" minOccurs="0"/>
     *         &lt;element name="UffRicezione" type="{}UOType" minOccurs="0"/>
     *         &lt;element name="UtenteCtrlAmmissib" type="{}UserType" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "dataOraArrivo",
        "dataDocRicevuto",
        "estremiRegistrazioneDocRicevuto",
        "riferimentiDocRicevuto",
        "mezzoTrasmissione",
        "dataRaccomandata",
        "nroRaccomandata",
        "mittenteEsterno",
        "firmatarioEsterno",
        "indirizzoProv",
        "indirizzoEmailProv",
        "utenteRicezione",
        "uffRicezione",
        "utenteCtrlAmmissib"
    })
    public static class DatiEntrata {

        @XmlElement(name = "DataOraArrivo")
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar dataOraArrivo;
        @XmlElement(name = "DataDocRicevuto")
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar dataDocRicevuto;
        @XmlElement(name = "EstremiRegistrazioneDocRicevuto")
        protected String estremiRegistrazioneDocRicevuto;
        @XmlElement(name = "RiferimentiDocRicevuto")
        protected String riferimentiDocRicevuto;
        @XmlElement(name = "MezzoTrasmissione")
        protected OggDiTabDiSistemaType mezzoTrasmissione;
        @XmlElement(name = "DataRaccomandata")
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar dataRaccomandata;
        @XmlElement(name = "NroRaccomandata")
        protected String nroRaccomandata;
        @XmlElement(name = "MittenteEsterno")
        protected List<SoggettoEsternoType> mittenteEsterno;
        @XmlElement(name = "FirmatarioEsterno")
        protected List<SoggettoEsternoType> firmatarioEsterno;
        @XmlElement(name = "IndirizzoProv")
        protected IndirizzoType indirizzoProv;
        @XmlElement(name = "IndirizzoEmailProv")
        protected EmailType indirizzoEmailProv;
        @XmlElement(name = "UtenteRicezione")
        protected UserType utenteRicezione;
        @XmlElement(name = "UffRicezione")
        protected UOType uffRicezione;
        @XmlElement(name = "UtenteCtrlAmmissib")
        protected UserType utenteCtrlAmmissib;

        /**
         * Recupera il valore della proprietdataOraArrivo.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getDataOraArrivo() {
            return dataOraArrivo;
        }

        /**
         * Imposta il valore della proprietdataOraArrivo.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setDataOraArrivo(XMLGregorianCalendar value) {
            this.dataOraArrivo = value;
        }

        /**
         * Recupera il valore della proprietdataDocRicevuto.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getDataDocRicevuto() {
            return dataDocRicevuto;
        }

        /**
         * Imposta il valore della proprietdataDocRicevuto.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setDataDocRicevuto(XMLGregorianCalendar value) {
            this.dataDocRicevuto = value;
        }

        /**
         * Recupera il valore della proprietestremiRegistrazioneDocRicevuto.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEstremiRegistrazioneDocRicevuto() {
            return estremiRegistrazioneDocRicevuto;
        }

        /**
         * Imposta il valore della proprietestremiRegistrazioneDocRicevuto.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEstremiRegistrazioneDocRicevuto(String value) {
            this.estremiRegistrazioneDocRicevuto = value;
        }

        /**
         * Recupera il valore della proprietriferimentiDocRicevuto.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRiferimentiDocRicevuto() {
            return riferimentiDocRicevuto;
        }

        /**
         * Imposta il valore della proprietriferimentiDocRicevuto.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRiferimentiDocRicevuto(String value) {
            this.riferimentiDocRicevuto = value;
        }

        /**
         * Recupera il valore della proprietmezzoTrasmissione.
         * 
         * @return
         *     possible object is
         *     {@link OggDiTabDiSistemaType }
         *     
         */
        public OggDiTabDiSistemaType getMezzoTrasmissione() {
            return mezzoTrasmissione;
        }

        /**
         * Imposta il valore della proprietmezzoTrasmissione.
         * 
         * @param value
         *     allowed object is
         *     {@link OggDiTabDiSistemaType }
         *     
         */
        public void setMezzoTrasmissione(OggDiTabDiSistemaType value) {
            this.mezzoTrasmissione = value;
        }

        /**
         * Recupera il valore della proprietdataRaccomandata.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getDataRaccomandata() {
            return dataRaccomandata;
        }

        /**
         * Imposta il valore della proprietdataRaccomandata.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setDataRaccomandata(XMLGregorianCalendar value) {
            this.dataRaccomandata = value;
        }

        /**
         * Recupera il valore della proprietnroRaccomandata.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNroRaccomandata() {
            return nroRaccomandata;
        }

        /**
         * Imposta il valore della proprietnroRaccomandata.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNroRaccomandata(String value) {
            this.nroRaccomandata = value;
        }

        /**
         * Gets the value of the mittenteEsterno property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the mittenteEsterno property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getMittenteEsterno().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link SoggettoEsternoType }
         * 
         * 
         */
        public List<SoggettoEsternoType> getMittenteEsterno() {
            if (mittenteEsterno == null) {
                mittenteEsterno = new ArrayList<SoggettoEsternoType>();
            }
            return this.mittenteEsterno;
        }

        /**
         * Gets the value of the firmatarioEsterno property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the firmatarioEsterno property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getFirmatarioEsterno().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link SoggettoEsternoType }
         * 
         * 
         */
        public List<SoggettoEsternoType> getFirmatarioEsterno() {
            if (firmatarioEsterno == null) {
                firmatarioEsterno = new ArrayList<SoggettoEsternoType>();
            }
            return this.firmatarioEsterno;
        }

        /**
         * Recupera il valore della proprietindirizzoProv.
         * 
         * @return
         *     possible object is
         *     {@link IndirizzoType }
         *     
         */
        public IndirizzoType getIndirizzoProv() {
            return indirizzoProv;
        }

        /**
         * Imposta il valore della proprietindirizzoProv.
         * 
         * @param value
         *     allowed object is
         *     {@link IndirizzoType }
         *     
         */
        public void setIndirizzoProv(IndirizzoType value) {
            this.indirizzoProv = value;
        }

        /**
         * Recupera il valore della proprietindirizzoEmailProv.
         * 
         * @return
         *     possible object is
         *     {@link EmailType }
         *     
         */
        public EmailType getIndirizzoEmailProv() {
            return indirizzoEmailProv;
        }

        /**
         * Imposta il valore della proprietindirizzoEmailProv.
         * 
         * @param value
         *     allowed object is
         *     {@link EmailType }
         *     
         */
        public void setIndirizzoEmailProv(EmailType value) {
            this.indirizzoEmailProv = value;
        }

        /**
         * Recupera il valore della proprietutenteRicezione.
         * 
         * @return
         *     possible object is
         *     {@link UserType }
         *     
         */
        public UserType getUtenteRicezione() {
            return utenteRicezione;
        }

        /**
         * Imposta il valore della proprietutenteRicezione.
         * 
         * @param value
         *     allowed object is
         *     {@link UserType }
         *     
         */
        public void setUtenteRicezione(UserType value) {
            this.utenteRicezione = value;
        }

        /**
         * Recupera il valore della proprietuffRicezione.
         * 
         * @return
         *     possible object is
         *     {@link UOType }
         *     
         */
        public UOType getUffRicezione() {
            return uffRicezione;
        }

        /**
         * Imposta il valore della proprietuffRicezione.
         * 
         * @param value
         *     allowed object is
         *     {@link UOType }
         *     
         */
        public void setUffRicezione(UOType value) {
            this.uffRicezione = value;
        }

        /**
         * Recupera il valore della proprietutenteCtrlAmmissib.
         * 
         * @return
         *     possible object is
         *     {@link UserType }
         *     
         */
        public UserType getUtenteCtrlAmmissib() {
            return utenteCtrlAmmissib;
        }

        /**
         * Imposta il valore della proprietutenteCtrlAmmissib.
         * 
         * @param value
         *     allowed object is
         *     {@link UserType }
         *     
         */
        public void setUtenteCtrlAmmissib(UserType value) {
            this.utenteCtrlAmmissib = value;
        }

    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="DataStesura" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *         &lt;element name="LuogoStesura" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="Estensore" type="{}UserType" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="UffProduttore" type="{}UOType" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "dataStesura",
        "luogoStesura",
        "estensore",
        "uffProduttore"
    })
    public static class DatiProduzione {

        @XmlElement(name = "DataStesura")
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar dataStesura;
        @XmlElement(name = "LuogoStesura")
        protected String luogoStesura;
        @XmlElement(name = "Estensore")
        protected List<UserType> estensore;
        @XmlElement(name = "UffProduttore")
        protected List<UOType> uffProduttore;

        /**
         * Recupera il valore della proprietdataStesura.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getDataStesura() {
            return dataStesura;
        }

        /**
         * Imposta il valore della proprietdataStesura.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setDataStesura(XMLGregorianCalendar value) {
            this.dataStesura = value;
        }

        /**
         * Recupera il valore della proprietluogoStesura.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLuogoStesura() {
            return luogoStesura;
        }

        /**
         * Imposta il valore della proprietluogoStesura.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLuogoStesura(String value) {
            this.luogoStesura = value;
        }

        /**
         * Gets the value of the estensore property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the estensore property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getEstensore().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link UserType }
         * 
         * 
         */
        public List<UserType> getEstensore() {
            if (estensore == null) {
                estensore = new ArrayList<UserType>();
            }
            return this.estensore;
        }

        /**
         * Gets the value of the uffProduttore property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the uffProduttore property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getUffProduttore().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link UOType }
         * 
         * 
         */
        public List<UOType> getUffProduttore() {
            if (uffProduttore == null) {
                uffProduttore = new ArrayList<UOType>();
            }
            return this.uffProduttore;
        }

    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="DataOraSped" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
     *         &lt;element name="MezzoTrasmissione" type="{}OggDiTabDiSistemaType" minOccurs="0"/>
     *         &lt;element name="DataRaccomandata" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *         &lt;element name="NroRaccomandata" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="DestinatarioEsterno" type="{}DestinatarioEsternoType" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="UtenteSpedizione" type="{}UserType" minOccurs="0"/>
     *         &lt;element name="UffSpedizione" type="{}UOType" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "dataOraSped",
        "mezzoTrasmissione",
        "dataRaccomandata",
        "nroRaccomandata",
        "destinatarioEsterno",
        "utenteSpedizione",
        "uffSpedizione"
    })
    public static class DatiUscita {

        @XmlElement(name = "DataOraSped")
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar dataOraSped;
        @XmlElement(name = "MezzoTrasmissione")
        protected OggDiTabDiSistemaType mezzoTrasmissione;
        @XmlElement(name = "DataRaccomandata")
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar dataRaccomandata;
        @XmlElement(name = "NroRaccomandata")
        protected String nroRaccomandata;
        @XmlElement(name = "DestinatarioEsterno")
        protected List<DestinatarioEsternoType> destinatarioEsterno;
        @XmlElement(name = "UtenteSpedizione")
        protected UserType utenteSpedizione;
        @XmlElement(name = "UffSpedizione")
        protected UOType uffSpedizione;

        /**
         * Recupera il valore della proprietdataOraSped.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getDataOraSped() {
            return dataOraSped;
        }

        /**
         * Imposta il valore della proprietdataOraSped.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setDataOraSped(XMLGregorianCalendar value) {
            this.dataOraSped = value;
        }

        /**
         * Recupera il valore della proprietmezzoTrasmissione.
         * 
         * @return
         *     possible object is
         *     {@link OggDiTabDiSistemaType }
         *     
         */
        public OggDiTabDiSistemaType getMezzoTrasmissione() {
            return mezzoTrasmissione;
        }

        /**
         * Imposta il valore della proprietmezzoTrasmissione.
         * 
         * @param value
         *     allowed object is
         *     {@link OggDiTabDiSistemaType }
         *     
         */
        public void setMezzoTrasmissione(OggDiTabDiSistemaType value) {
            this.mezzoTrasmissione = value;
        }

        /**
         * Recupera il valore della proprietdataRaccomandata.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getDataRaccomandata() {
            return dataRaccomandata;
        }

        /**
         * Imposta il valore della proprietdataRaccomandata.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setDataRaccomandata(XMLGregorianCalendar value) {
            this.dataRaccomandata = value;
        }

        /**
         * Recupera il valore della proprietnroRaccomandata.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNroRaccomandata() {
            return nroRaccomandata;
        }

        /**
         * Imposta il valore della proprietnroRaccomandata.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNroRaccomandata(String value) {
            this.nroRaccomandata = value;
        }

        /**
         * Gets the value of the destinatarioEsterno property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the destinatarioEsterno property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDestinatarioEsterno().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link DestinatarioEsternoType }
         * 
         * 
         */
        public List<DestinatarioEsternoType> getDestinatarioEsterno() {
            if (destinatarioEsterno == null) {
                destinatarioEsterno = new ArrayList<DestinatarioEsternoType>();
            }
            return this.destinatarioEsterno;
        }

        /**
         * Recupera il valore della proprietutenteSpedizione.
         * 
         * @return
         *     possible object is
         *     {@link UserType }
         *     
         */
        public UserType getUtenteSpedizione() {
            return utenteSpedizione;
        }

        /**
         * Imposta il valore della proprietutenteSpedizione.
         * 
         * @param value
         *     allowed object is
         *     {@link UserType }
         *     
         */
        public void setUtenteSpedizione(UserType value) {
            this.utenteSpedizione = value;
        }

        /**
         * Recupera il valore della proprietuffSpedizione.
         * 
         * @return
         *     possible object is
         *     {@link UOType }
         *     
         */
        public UOType getUffSpedizione() {
            return uffSpedizione;
        }

        /**
         * Imposta il valore della proprietuffSpedizione.
         * 
         * @param value
         *     allowed object is
         *     {@link UOType }
         *     
         */
        public void setUffSpedizione(UOType value) {
            this.uffSpedizione = value;
        }

    }

}
