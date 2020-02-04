//
// Questo file e' stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andra' persa durante la ricompilazione dello schema di origine. 
// Generato il: 2013.02.05 alle 11:21:53 AM CET 
//


package it.wego.cross.xml.anagrafetributaria.commercio;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per recordControllo complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="recordControllo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tipoRecord">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="0"/>
 *               &lt;enumeration value="9"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="codiceFiscaleUfficio">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="11"/>
 *               &lt;minLength value="11"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="denominazioneUfficio">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="60"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="domicilioFiscaleUfficio">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="35"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="provinciaDomicilioUfficio">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="indirizzoUfficio">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="35"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="capUfficio">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="5"/>
 *               &lt;maxLength value="5"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="naturaUfficio">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="annoRiferimento">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="4"/>
 *               &lt;minLength value="4"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="codiceFornitura" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="progressivoInvio">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="7"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="dataInvio">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="8"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="flagRiciclo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="filler">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="27"/>
 *               &lt;minLength value="27"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="totaleRecordInviati">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               &lt;maxInclusive value="9999999"/>
 *               &lt;minInclusive value="0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "recordControllo", propOrder = {
    "tipoRecord",
    "codiceFiscaleUfficio",
    "denominazioneUfficio",
    "domicilioFiscaleUfficio",
    "provinciaDomicilioUfficio",
    "indirizzoUfficio",
    "capUfficio",
    "naturaUfficio",
    "annoRiferimento",
    "codiceFornitura",
    "progressivoInvio",
    "dataInvio",
    "flagRiciclo",
    "filler",
    "totaleRecordInviati"
})
public class RecordControllo {

    @XmlElement(required = true)
    protected String tipoRecord;
    @XmlElement(required = true)
    protected String codiceFiscaleUfficio;
    @XmlElement(required = true)
    protected String denominazioneUfficio;
    @XmlElement(required = true)
    protected String domicilioFiscaleUfficio;
    @XmlElement(required = true)
    protected String provinciaDomicilioUfficio;
    @XmlElement(required = true)
    protected String indirizzoUfficio;
    @XmlElement(required = true)
    protected String capUfficio;
    @XmlElement(required = true)
    protected String naturaUfficio;
    @XmlElement(required = true)
    protected String annoRiferimento;
    @XmlElement(required = true)
    protected String codiceFornitura;
    @XmlElement(required = true)
    protected String progressivoInvio;
    @XmlElement(required = true)
    protected String dataInvio;
    @XmlElement(required = true)
    protected String flagRiciclo;
    @XmlElement(required = true)
    protected String filler;
    protected int totaleRecordInviati;

    /**
     * Recupera il valore della proprieta' tipoRecord.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoRecord() {
        return tipoRecord;
    }

    /**
     * Imposta il valore della proprieta' tipoRecord.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoRecord(String value) {
        this.tipoRecord = value;
    }

    /**
     * Recupera il valore della proprieta' codiceFiscaleUfficio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceFiscaleUfficio() {
        return codiceFiscaleUfficio;
    }

    /**
     * Imposta il valore della proprieta' codiceFiscaleUfficio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceFiscaleUfficio(String value) {
        this.codiceFiscaleUfficio = value;
    }

    /**
     * Recupera il valore della proprieta' denominazioneUfficio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDenominazioneUfficio() {
        return denominazioneUfficio;
    }

    /**
     * Imposta il valore della proprieta' denominazioneUfficio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDenominazioneUfficio(String value) {
        this.denominazioneUfficio = value;
    }

    /**
     * Recupera il valore della proprieta' domicilioFiscaleUfficio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDomicilioFiscaleUfficio() {
        return domicilioFiscaleUfficio;
    }

    /**
     * Imposta il valore della proprieta' domicilioFiscaleUfficio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDomicilioFiscaleUfficio(String value) {
        this.domicilioFiscaleUfficio = value;
    }

    /**
     * Recupera il valore della proprieta' provinciaDomicilioUfficio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvinciaDomicilioUfficio() {
        return provinciaDomicilioUfficio;
    }

    /**
     * Imposta il valore della proprieta' provinciaDomicilioUfficio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvinciaDomicilioUfficio(String value) {
        this.provinciaDomicilioUfficio = value;
    }

    /**
     * Recupera il valore della proprieta' indirizzoUfficio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndirizzoUfficio() {
        return indirizzoUfficio;
    }

    /**
     * Imposta il valore della proprieta' indirizzoUfficio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndirizzoUfficio(String value) {
        this.indirizzoUfficio = value;
    }

    /**
     * Recupera il valore della proprieta' capUfficio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCapUfficio() {
        return capUfficio;
    }

    /**
     * Imposta il valore della proprieta' capUfficio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCapUfficio(String value) {
        this.capUfficio = value;
    }

    /**
     * Recupera il valore della proprieta' naturaUfficio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNaturaUfficio() {
        return naturaUfficio;
    }

    /**
     * Imposta il valore della proprieta' naturaUfficio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNaturaUfficio(String value) {
        this.naturaUfficio = value;
    }

    /**
     * Recupera il valore della proprieta' annoRiferimento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnnoRiferimento() {
        return annoRiferimento;
    }

    /**
     * Imposta il valore della proprieta' annoRiferimento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnnoRiferimento(String value) {
        this.annoRiferimento = value;
    }

    /**
     * Recupera il valore della proprieta' codiceFornitura.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceFornitura() {
        return codiceFornitura;
    }

    /**
     * Imposta il valore della proprieta' codiceFornitura.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceFornitura(String value) {
        this.codiceFornitura = value;
    }

    /**
     * Recupera il valore della proprieta' progressivoInvio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProgressivoInvio() {
        return progressivoInvio;
    }

    /**
     * Imposta il valore della proprieta' progressivoInvio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProgressivoInvio(String value) {
        this.progressivoInvio = value;
    }

    /**
     * Recupera il valore della proprieta' dataInvio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataInvio() {
        return dataInvio;
    }

    /**
     * Imposta il valore della proprieta' dataInvio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataInvio(String value) {
        this.dataInvio = value;
    }

    /**
     * Recupera il valore della proprieta' flagRiciclo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagRiciclo() {
        return flagRiciclo;
    }

    /**
     * Imposta il valore della proprieta' flagRiciclo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagRiciclo(String value) {
        this.flagRiciclo = value;
    }

    /**
     * Recupera il valore della proprieta' filler.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFiller() {
        return filler;
    }

    /**
     * Imposta il valore della proprieta' filler.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFiller(String value) {
        this.filler = value;
    }

    /**
     * Recupera il valore della proprieta' totaleRecordInviati.
     * 
     */
    public int getTotaleRecordInviati() {
        return totaleRecordInviati;
    }

    /**
     * Imposta il valore della proprieta' totaleRecordInviati.
     * 
     */
    public void setTotaleRecordInviati(int value) {
        this.totaleRecordInviati = value;
    }

}
