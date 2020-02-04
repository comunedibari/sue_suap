
package it.wego.cross.webservices.client.aosta.protocollo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per DettaglioProtocollo complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="DettaglioProtocollo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="uo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="direzione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codregistro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="annoProt" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="numeroProt" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="dataProt" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="titolario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="classifica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="oggetto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataDoc" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="riservato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="emittente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fileOrig" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="carico" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="source" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="aoo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numFascicolo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="annoFascicolo" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="tipoDoc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pubblicato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DettaglioProtocollo", propOrder = {
    "id",
    "uo",
    "direzione",
    "codregistro",
    "annoProt",
    "numeroProt",
    "dataProt",
    "titolario",
    "classifica",
    "oggetto",
    "dataDoc",
    "riservato",
    "emittente",
    "fileOrig",
    "carico",
    "source",
    "aoo",
    "numFascicolo",
    "annoFascicolo",
    "tipoDoc",
    "pubblicato"
})
public class DettaglioProtocollo {

    protected Integer id;
    protected String uo;
    protected String direzione;
    protected String codregistro;
    protected Short annoProt;
    protected Integer numeroProt;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataProt;
    protected String titolario;
    protected String classifica;
    protected String oggetto;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataDoc;
    protected String riservato;
    protected String emittente;
    protected Integer fileOrig;
    protected String carico;
    protected String source;
    protected String aoo;
    protected String numFascicolo;
    protected Short annoFascicolo;
    protected String tipoDoc;
    protected String pubblicato;

    /**
     * Recupera il valore della propriet id.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getId() {
        return id;
    }

    /**
     * Imposta il valore della propriet id.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setId(Integer value) {
        this.id = value;
    }

    /**
     * Recupera il valore della propriet uo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUo() {
        return uo;
    }

    /**
     * Imposta il valore della propriet uo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUo(String value) {
        this.uo = value;
    }

    /**
     * Recupera il valore della propriet direzione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDirezione() {
        return direzione;
    }

    /**
     * Imposta il valore della propriet direzione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDirezione(String value) {
        this.direzione = value;
    }

    /**
     * Recupera il valore della propriet codregistro.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodregistro() {
        return codregistro;
    }

    /**
     * Imposta il valore della propriet codregistro.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodregistro(String value) {
        this.codregistro = value;
    }

    /**
     * Recupera il valore della propriet annoProt.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getAnnoProt() {
        return annoProt;
    }

    /**
     * Imposta il valore della propriet annoProt.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setAnnoProt(Short value) {
        this.annoProt = value;
    }

    /**
     * Recupera il valore della propriet numeroProt.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumeroProt() {
        return numeroProt;
    }

    /**
     * Imposta il valore della propriet numeroProt.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumeroProt(Integer value) {
        this.numeroProt = value;
    }

    /**
     * Recupera il valore della propriet dataProt.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataProt() {
        return dataProt;
    }

    /**
     * Imposta il valore della propriet dataProt.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataProt(XMLGregorianCalendar value) {
        this.dataProt = value;
    }

    /**
     * Recupera il valore della propriet titolario.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitolario() {
        return titolario;
    }

    /**
     * Imposta il valore della propriet titolario.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitolario(String value) {
        this.titolario = value;
    }

    /**
     * Recupera il valore della propriet classifica.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClassifica() {
        return classifica;
    }

    /**
     * Imposta il valore della propriet classifica.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClassifica(String value) {
        this.classifica = value;
    }

    /**
     * Recupera il valore della propriet oggetto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOggetto() {
        return oggetto;
    }

    /**
     * Imposta il valore della propriet oggetto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOggetto(String value) {
        this.oggetto = value;
    }

    /**
     * Recupera il valore della propriet dataDoc.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataDoc() {
        return dataDoc;
    }

    /**
     * Imposta il valore della propriet dataDoc.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataDoc(XMLGregorianCalendar value) {
        this.dataDoc = value;
    }

    /**
     * Recupera il valore della propriet riservato.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiservato() {
        return riservato;
    }

    /**
     * Imposta il valore della propriet riservato.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiservato(String value) {
        this.riservato = value;
    }

    /**
     * Recupera il valore della propriet emittente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmittente() {
        return emittente;
    }

    /**
     * Imposta il valore della propriet emittente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmittente(String value) {
        this.emittente = value;
    }

    /**
     * Recupera il valore della propriet fileOrig.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFileOrig() {
        return fileOrig;
    }

    /**
     * Imposta il valore della propriet fileOrig.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFileOrig(Integer value) {
        this.fileOrig = value;
    }

    /**
     * Recupera il valore della propriet carico.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCarico() {
        return carico;
    }

    /**
     * Imposta il valore della propriet carico.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCarico(String value) {
        this.carico = value;
    }

    /**
     * Recupera il valore della propriet source.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSource() {
        return source;
    }

    /**
     * Imposta il valore della propriet source.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSource(String value) {
        this.source = value;
    }

    /**
     * Recupera il valore della propriet aoo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAoo() {
        return aoo;
    }

    /**
     * Imposta il valore della propriet aoo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAoo(String value) {
        this.aoo = value;
    }

    /**
     * Recupera il valore della propriet numFascicolo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumFascicolo() {
        return numFascicolo;
    }

    /**
     * Imposta il valore della propriet numFascicolo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumFascicolo(String value) {
        this.numFascicolo = value;
    }

    /**
     * Recupera il valore della propriet annoFascicolo.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getAnnoFascicolo() {
        return annoFascicolo;
    }

    /**
     * Imposta il valore della propriet annoFascicolo.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setAnnoFascicolo(Short value) {
        this.annoFascicolo = value;
    }

    /**
     * Recupera il valore della propriet tipoDoc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoDoc() {
        return tipoDoc;
    }

    /**
     * Imposta il valore della propriet tipoDoc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoDoc(String value) {
        this.tipoDoc = value;
    }

    /**
     * Recupera il valore della propriet pubblicato.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPubblicato() {
        return pubblicato;
    }

    /**
     * Imposta il valore della propriet pubblicato.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPubblicato(String value) {
        this.pubblicato = value;
    }

}
