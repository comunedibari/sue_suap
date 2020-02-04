//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.02.25 alle 05:44:42 PM CET 
//


package it.wego.cross.xml;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per indirizzo_intervento complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="indirizzo_intervento">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="counter" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="id_indirizzo_intervento" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="localita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id_dug" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="indirizzo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="civico" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="altre_informazioni_indirizzo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cap" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codice_via" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codice_civico" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="interno_numero" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="interno_lettera" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="interno_scala" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lettera" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="colore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="latitudine" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="longitudine" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dato_esteso_1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dato_esteso_2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="piano" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="confermato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "indirizzo_intervento", propOrder = {
    "counter",
    "idIndirizzoIntervento",
    "localita",
    "idDug",
    "indirizzo",
    "civico",
    "altreInformazioniIndirizzo",
    "cap",
    "codVia",
    "codCivico",
    "internoNumero",
    "internoLettera",
    "internoScala",
    "lettera",
    "colore",
    "latitudine",
    "longitudine",
    "datoEsteso1",
    "datoEsteso2",
    "piano",
    "confermato"
})
public class IndirizzoIntervento {

    @XmlElement(required = true)
    protected BigInteger counter;
    @XmlElement(name = "id_indirizzo_intervento")
    protected BigInteger idIndirizzoIntervento;
    protected String localita;
    @XmlElement(name = "id_dug")
    protected BigInteger idDug;
    protected String indirizzo;
    protected String civico;
    @XmlElement(name = "altre_informazioni_indirizzo")
    protected String altreInformazioniIndirizzo;
    protected String cap;
    @XmlElement(name = "codice_via")
    protected String codVia;
    @XmlElement(name = "codice_civico")
    protected String codCivico;
    @XmlElement(name = "interno_numero")
    protected String internoNumero;
    @XmlElement(name = "interno_lettera")
    protected String internoLettera;
    @XmlElement(name = "interno_scala")
    protected String internoScala;
    protected String lettera;
    protected String colore;
    protected String latitudine;
    protected String longitudine;
    @XmlElement(name = "dato_esteso_1")
    protected String datoEsteso1;
    @XmlElement(name = "dato_esteso_2")
    protected String datoEsteso2;
    protected String piano;
    protected String confermato;

    /**
     * Recupera il valore della proprietà counter.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCounter() {
        return counter;
    }

    /**
     * Imposta il valore della proprietà counter.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCounter(BigInteger value) {
        this.counter = value;
    }

    /**
     * Recupera il valore della proprietà idIndirizzoIntervento.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdIndirizzoIntervento() {
        return idIndirizzoIntervento;
    }

    /**
     * Imposta il valore della proprietà idIndirizzoIntervento.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdIndirizzoIntervento(BigInteger value) {
        this.idIndirizzoIntervento = value;
    }

    /**
     * Recupera il valore della proprietà localita.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocalita() {
        return localita;
    }

    /**
     * Imposta il valore della proprietà localita.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocalita(String value) {
        this.localita = value;
    }

    /**
     * Recupera il valore della proprietà idDug.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdDug() {
        return idDug;
    }

    /**
     * Imposta il valore della proprietà idDug.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdDug(BigInteger value) {
        this.idDug = value;
    }

    /**
     * Recupera il valore della proprietà indirizzo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndirizzo() {
        return indirizzo;
    }

    /**
     * Imposta il valore della proprietà indirizzo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndirizzo(String value) {
        this.indirizzo = value;
    }

    /**
     * Recupera il valore della proprietà civico.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCivico() {
        return civico;
    }

    /**
     * Imposta il valore della proprietà civico.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCivico(String value) {
        this.civico = value;
    }

    /**
     * Recupera il valore della proprietà altreInformazioniIndirizzo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAltreInformazioniIndirizzo() {
        return altreInformazioniIndirizzo;
    }

    /**
     * Imposta il valore della proprietà altreInformazioniIndirizzo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAltreInformazioniIndirizzo(String value) {
        this.altreInformazioniIndirizzo = value;
    }

    /**
     * Recupera il valore della proprietà cap.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCap() {
        return cap;
    }

    /**
     * Imposta il valore della proprietà cap.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCap(String value) {
        this.cap = value;
    }

    /**
     * Recupera il valore della proprietà codiceVia.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodVia() {
        return codVia;
    }

    /**
     * Imposta il valore della proprietà codiceVia.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodVia(String value) {
        this.codVia = value;
    }

    /**
     * Recupera il valore della proprietà codiceCivico.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodCivico() {
        return codCivico;
    }

    /**
     * Imposta il valore della proprietà codiceCivico.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodCivico(String value) {
        this.codCivico = value;
    }

    /**
     * Recupera il valore della proprietà internoNumero.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInternoNumero() {
        return internoNumero;
    }

    /**
     * Imposta il valore della proprietà internoNumero.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInternoNumero(String value) {
        this.internoNumero = value;
    }

    /**
     * Recupera il valore della proprietà internoLettera.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInternoLettera() {
        return internoLettera;
    }

    /**
     * Imposta il valore della proprietà internoLettera.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInternoLettera(String value) {
        this.internoLettera = value;
    }

    /**
     * Recupera il valore della proprietà internoScala.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInternoScala() {
        return internoScala;
    }

    /**
     * Imposta il valore della proprietà internoScala.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInternoScala(String value) {
        this.internoScala = value;
    }

    /**
     * Recupera il valore della proprietà lettera.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLettera() {
        return lettera;
    }

    /**
     * Imposta il valore della proprietà lettera.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLettera(String value) {
        this.lettera = value;
    }

    /**
     * Recupera il valore della proprietà colore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColore() {
        return colore;
    }

    /**
     * Imposta il valore della proprietà colore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColore(String value) {
        this.colore = value;
    }

    public String getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(String value) {
        this.latitudine = value;
    }

    public String getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(String value) {
        this.longitudine = value;
    }

    public String getDatoEsteso1() {
        return datoEsteso1;
    }

    public void setDatoEsteso1(String value) {
        this.datoEsteso1 = value;
    }

    public String getDatoEsteso2() {
        return datoEsteso2;
    }

    public void setDatoEsteso2(String value) {
        this.datoEsteso2 = value;
    }

    public String getConfermato() {
        return confermato;
    }

    public void setConfermato(String confermato) {
        this.confermato = confermato;
    }
    public String getPiano() {
        return piano;
    }

    public void setPiano(String piano) {
        this.piano = piano;
    }    

}
