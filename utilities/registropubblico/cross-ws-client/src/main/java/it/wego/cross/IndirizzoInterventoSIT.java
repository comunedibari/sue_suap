
package it.wego.cross;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for indirizzo_interventoSIT complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="indirizzo_interventoSIT">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id_indirizzo_intervento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codice_sit" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="localita" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="indirizzo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="civico" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cap" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="interno_numero" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="interno_lettera" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="interno_scala" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="piano" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "indirizzo_interventoSIT", propOrder = {
    "idIndirizzoIntervento",
    "codiceSit",
    "localita",
    "indirizzo",
    "civico",
    "cap",
    "internoNumero",
    "internoLettera",
    "internoScala",
    "piano"
})
public class IndirizzoInterventoSIT {

    @XmlElement(name = "id_indirizzo_intervento", required = true)
    protected String idIndirizzoIntervento;
    @XmlElement(name = "codice_sit", required = true)
    protected String codiceSit;
    @XmlElement(required = true)
    protected String localita;
    @XmlElement(required = true)
    protected String indirizzo;
    @XmlElement(required = true)
    protected String civico;
    @XmlElement(required = true)
    protected String cap;
    @XmlElement(name = "interno_numero", required = true)
    protected String internoNumero;
    @XmlElement(name = "interno_lettera", required = true)
    protected String internoLettera;
    @XmlElement(name = "interno_scala", required = true)
    protected String internoScala;
    @XmlElement(required = true)
    protected String piano;

    /**
     * Gets the value of the idIndirizzoIntervento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdIndirizzoIntervento() {
        return idIndirizzoIntervento;
    }

    /**
     * Sets the value of the idIndirizzoIntervento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdIndirizzoIntervento(String value) {
        this.idIndirizzoIntervento = value;
    }

    /**
     * Gets the value of the codiceSit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceSit() {
        return codiceSit;
    }

    /**
     * Sets the value of the codiceSit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceSit(String value) {
        this.codiceSit = value;
    }

    /**
     * Gets the value of the localita property.
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
     * Sets the value of the localita property.
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
     * Gets the value of the indirizzo property.
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
     * Sets the value of the indirizzo property.
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
     * Gets the value of the civico property.
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
     * Sets the value of the civico property.
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
     * Gets the value of the cap property.
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
     * Sets the value of the cap property.
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
     * Gets the value of the internoNumero property.
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
     * Sets the value of the internoNumero property.
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
     * Gets the value of the internoLettera property.
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
     * Sets the value of the internoLettera property.
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
     * Gets the value of the internoScala property.
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
     * Sets the value of the internoScala property.
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
     * Gets the value of the piano property.
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
     * Sets the value of the piano property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPiano(String value) {
        this.piano = value;
    }

}
