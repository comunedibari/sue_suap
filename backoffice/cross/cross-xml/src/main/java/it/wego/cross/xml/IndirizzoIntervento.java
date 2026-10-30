//
// Questo file � stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andr� persa durante la ricompilazione dello schema di origine. 
// Generato il: 2024.04.16 alle 09:52:47 AM CEST 
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
 *         &lt;element name="id_comune" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="des_comune" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="localita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id_provincia" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="des_provincia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "idComune",
    "desComune",
    "localita",
    "idProvincia",
    "desProvincia",
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
    @XmlElement(name = "id_comune")
    protected BigInteger idComune;
    @XmlElement(name = "des_comune")
    protected String desComune;
    protected String localita;
    @XmlElement(name = "id_provincia")
    protected BigInteger idProvincia;
    @XmlElement(name = "des_provincia")
    protected String desProvincia;
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
     * Recupera il valore della propriet� counter.
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
     * Imposta il valore della propriet� counter.
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
     * Recupera il valore della propriet� idIndirizzoIntervento.
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
     * Imposta il valore della propriet� idIndirizzoIntervento.
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
     * Recupera il valore della propriet� idComune.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdComune() {
        return idComune;
    }

    /**
     * Imposta il valore della propriet� idComune.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdComune(BigInteger value) {
        this.idComune = value;
    }

    /**
     * Recupera il valore della propriet� desComune.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesComune() {
        return desComune;
    }

    /**
     * Imposta il valore della propriet� desComune.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesComune(String value) {
        this.desComune = value;
    }

    /**
     * Recupera il valore della propriet� localita.
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
     * Imposta il valore della propriet� localita.
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
     * Recupera il valore della propriet� idProvincia.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdProvincia() {
        return idProvincia;
    }

    /**
     * Imposta il valore della propriet� idProvincia.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdProvincia(BigInteger value) {
        this.idProvincia = value;
    }

    /**
     * Recupera il valore della propriet� desProvincia.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesProvincia() {
        return desProvincia;
    }

    /**
     * Imposta il valore della propriet� desProvincia.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesProvincia(String value) {
        this.desProvincia = value;
    }

    /**
     * Recupera il valore della propriet� idDug.
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
     * Imposta il valore della propriet� idDug.
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
     * Recupera il valore della propriet� indirizzo.
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
     * Imposta il valore della propriet� indirizzo.
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
     * Recupera il valore della propriet� civico.
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
     * Imposta il valore della propriet� civico.
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
     * Recupera il valore della propriet� altreInformazioniIndirizzo.
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
     * Imposta il valore della propriet� altreInformazioniIndirizzo.
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
     * Recupera il valore della propriet� cap.
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
     * Imposta il valore della propriet� cap.
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
     * Recupera il valore della propriet� codiceVia.
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
     * Imposta il valore della propriet� codiceVia.
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
     * Recupera il valore della propriet� codiceCivico.
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
     * Imposta il valore della propriet� codiceCivico.
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
     * Recupera il valore della propriet� internoNumero.
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
     * Imposta il valore della propriet� internoNumero.
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
     * Recupera il valore della propriet� internoLettera.
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
     * Imposta il valore della propriet� internoLettera.
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
     * Recupera il valore della propriet� internoScala.
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
     * Imposta il valore della propriet� internoScala.
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
     * Recupera il valore della propriet� lettera.
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
     * Imposta il valore della propriet� lettera.
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
     * Recupera il valore della propriet� colore.
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
     * Imposta il valore della propriet� colore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColore(String value) {
        this.colore = value;
    }

    /**
     * Recupera il valore della propriet� latitudine.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLatitudine() {
        return latitudine;
    }

    /**
     * Imposta il valore della propriet� latitudine.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLatitudine(String value) {
        this.latitudine = value;
    }

    /**
     * Recupera il valore della propriet� longitudine.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLongitudine() {
        return longitudine;
    }

    /**
     * Imposta il valore della propriet� longitudine.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLongitudine(String value) {
        this.longitudine = value;
    }

    /**
     * Recupera il valore della propriet� datoEsteso1.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatoEsteso1() {
        return datoEsteso1;
    }

    /**
     * Imposta il valore della propriet� datoEsteso1.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatoEsteso1(String value) {
        this.datoEsteso1 = value;
    }

    /**
     * Recupera il valore della propriet� datoEsteso2.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatoEsteso2() {
        return datoEsteso2;
    }

    /**
     * Imposta il valore della propriet� datoEsteso2.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatoEsteso2(String value) {
        this.datoEsteso2 = value;
    }

    /**
     * Recupera il valore della propriet� piano.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPiano() {
        return piano;
    }

    /**
     * Imposta il valore della propriet� piano.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPiano(String value) {
        this.piano = value;
    }

    /**
     * Recupera il valore della propriet� confermato.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfermato() {
        return confermato;
    }

    /**
     * Imposta il valore della propriet� confermato.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfermato(String value) {
        this.confermato = value;
    }

}
