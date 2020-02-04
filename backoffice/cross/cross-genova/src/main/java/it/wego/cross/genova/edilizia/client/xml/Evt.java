//
// Questo file � stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andr� persa durante la ricompilazione dello schema di origine. 
// Generato il: 2013.04.17 alle 05:51:30 PM CEST 
//


package it.wego.cross.genova.edilizia.client.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per evt complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="evt">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tipoPraticaEdilizia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroPraticaEdilizia" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="annoPraticaEdilizia" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataProtocolloGenerale" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numeroProtocolloGenerale" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CodiceEvento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CodiceEsito" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataInizio" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="dataFine" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="Note" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AzzeraContatoreGG" type="{}SINO" minOccurs="0"/>
 *         &lt;element name="BloccaContatoreGG" type="{}SINO" minOccurs="0"/>
 *         &lt;element name="CompletaIter" type="{}SINO" minOccurs="0"/>
 *         &lt;element name="allegati" type="{}allegati" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "evt", propOrder = {
    "tipoPraticaEdilizia",
    "numeroPraticaEdilizia",
    "annoPraticaEdilizia",
    "dataProtocolloGenerale",
    "numeroProtocolloGenerale",
    "codiceEvento",
    "codiceEsito",
    "dataInizio",
    "dataFine",
    "note",
    "azzeraContatoreGG",
    "bloccaContatoreGG",
    "completaIter",
    "allegati"
})
public class Evt {

    protected String tipoPraticaEdilizia;
    @XmlElement(required = true)
    protected String numeroPraticaEdilizia;
    @XmlElement(required = true)
    protected String annoPraticaEdilizia;
    @XmlElement(required = true)
    protected String dataProtocolloGenerale;
    @XmlElement(required = true)
    protected String numeroProtocolloGenerale;
    @XmlElement(name = "CodiceEvento", required = true)
    protected String codiceEvento;
    @XmlElement(name = "CodiceEsito")
    protected String codiceEsito;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataInizio;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataFine;
    @XmlElement(name = "Note")
    protected String note;
    @XmlElement(name = "AzzeraContatoreGG")
    protected SINO azzeraContatoreGG;
    @XmlElement(name = "BloccaContatoreGG")
    protected SINO bloccaContatoreGG;
    @XmlElement(name = "CompletaIter")
    protected SINO completaIter;
    protected Allegati allegati;

    /**
     * Recupera il valore della propriet� tipoPraticaEdilizia.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoPraticaEdilizia() {
        return tipoPraticaEdilizia;
    }

    /**
     * Imposta il valore della propriet� tipoPraticaEdilizia.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoPraticaEdilizia(String value) {
        this.tipoPraticaEdilizia = value;
    }

    /**
     * Recupera il valore della propriet� numeroPraticaEdilizia.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroPraticaEdilizia() {
        return numeroPraticaEdilizia;
    }

    /**
     * Imposta il valore della propriet� numeroPraticaEdilizia.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroPraticaEdilizia(String value) {
        this.numeroPraticaEdilizia = value;
    }

    /**
     * Recupera il valore della propriet� annoPraticaEdilizia.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnnoPraticaEdilizia() {
        return annoPraticaEdilizia;
    }

    /**
     * Imposta il valore della propriet� annoPraticaEdilizia.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnnoPraticaEdilizia(String value) {
        this.annoPraticaEdilizia = value;
    }

    /**
     * Recupera il valore della propriet� dataProtocolloGenerale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataProtocolloGenerale() {
        return dataProtocolloGenerale;
    }

    /**
     * Imposta il valore della propriet� dataProtocolloGenerale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataProtocolloGenerale(String value) {
        this.dataProtocolloGenerale = value;
    }

    /**
     * Recupera il valore della propriet� numeroProtocolloGenerale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroProtocolloGenerale() {
        return numeroProtocolloGenerale;
    }

    /**
     * Imposta il valore della propriet� numeroProtocolloGenerale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroProtocolloGenerale(String value) {
        this.numeroProtocolloGenerale = value;
    }

    /**
     * Recupera il valore della propriet� codiceEvento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceEvento() {
        return codiceEvento;
    }

    /**
     * Imposta il valore della propriet� codiceEvento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceEvento(String value) {
        this.codiceEvento = value;
    }

    /**
     * Recupera il valore della propriet� codiceEsito.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceEsito() {
        return codiceEsito;
    }

    /**
     * Imposta il valore della propriet� codiceEsito.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceEsito(String value) {
        this.codiceEsito = value;
    }

    /**
     * Recupera il valore della propriet� dataInizio.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataInizio() {
        return dataInizio;
    }

    /**
     * Imposta il valore della propriet� dataInizio.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataInizio(XMLGregorianCalendar value) {
        this.dataInizio = value;
    }

    /**
     * Recupera il valore della propriet� dataFine.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataFine() {
        return dataFine;
    }

    /**
     * Imposta il valore della propriet� dataFine.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataFine(XMLGregorianCalendar value) {
        this.dataFine = value;
    }

    /**
     * Recupera il valore della propriet� note.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNote() {
        return note;
    }

    /**
     * Imposta il valore della propriet� note.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNote(String value) {
        this.note = value;
    }

    /**
     * Recupera il valore della propriet� azzeraContatoreGG.
     * 
     * @return
     *     possible object is
     *     {@link SINO }
     *     
     */
    public SINO getAzzeraContatoreGG() {
        return azzeraContatoreGG;
    }

    /**
     * Imposta il valore della propriet� azzeraContatoreGG.
     * 
     * @param value
     *     allowed object is
     *     {@link SINO }
     *     
     */
    public void setAzzeraContatoreGG(SINO value) {
        this.azzeraContatoreGG = value;
    }

    /**
     * Recupera il valore della propriet� bloccaContatoreGG.
     * 
     * @return
     *     possible object is
     *     {@link SINO }
     *     
     */
    public SINO getBloccaContatoreGG() {
        return bloccaContatoreGG;
    }

    /**
     * Imposta il valore della propriet� bloccaContatoreGG.
     * 
     * @param value
     *     allowed object is
     *     {@link SINO }
     *     
     */
    public void setBloccaContatoreGG(SINO value) {
        this.bloccaContatoreGG = value;
    }

    /**
     * Recupera il valore della propriet� completaIter.
     * 
     * @return
     *     possible object is
     *     {@link SINO }
     *     
     */
    public SINO getCompletaIter() {
        return completaIter;
    }

    /**
     * Imposta il valore della propriet� completaIter.
     * 
     * @param value
     *     allowed object is
     *     {@link SINO }
     *     
     */
    public void setCompletaIter(SINO value) {
        this.completaIter = value;
    }

    /**
     * Recupera il valore della propriet� allegati.
     * 
     * @return
     *     possible object is
     *     {@link Allegati }
     *     
     */
    public Allegati getAllegati() {
        return allegati;
    }

    /**
     * Imposta il valore della propriet� allegati.
     * 
     * @param value
     *     allowed object is
     *     {@link Allegati }
     *     
     */
    public void setAllegati(Allegati value) {
        this.allegati = value;
    }

}
