
package it.gov.impresainungiorno.schema.base;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * E' la versione con tutti i dati opzionali cosi' come possono arrivare dal R.I.
 * 
 * <p>Java class for IndirizzoItalianoRI complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IndirizzoItalianoRI">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{http://www.impresainungiorno.gov.it/schema/base}CittaItalianaRI"/>
 *         &lt;group ref="{http://www.impresainungiorno.gov.it/schema/base}DatiIndirizzoItalianoRI"/>
 *         &lt;group ref="{http://www.impresainungiorno.gov.it/schema/base}DatiIndirizzoRI"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IndirizzoItalianoRI", propOrder = {
    "provincia",
    "comune",
    "cap",
    "toponimo",
    "denominazioneStradale",
    "numeroCivico",
    "frazione"
})
public class IndirizzoItalianoRI {

    protected ProvinciaRI provincia;
    protected ComuneRI comune;
    protected String cap;
    protected String toponimo;
    @XmlElement(name = "denominazione-stradale")
    protected String denominazioneStradale;
    @XmlElement(name = "numero-civico")
    protected String numeroCivico;
    protected String frazione;

    /**
     * Gets the value of the provincia property.
     * 
     * @return
     *     possible object is
     *     {@link ProvinciaRI }
     *     
     */
    public ProvinciaRI getProvincia() {
        return provincia;
    }

    /**
     * Sets the value of the provincia property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProvinciaRI }
     *     
     */
    public void setProvincia(ProvinciaRI value) {
        this.provincia = value;
    }

    /**
     * Gets the value of the comune property.
     * 
     * @return
     *     possible object is
     *     {@link ComuneRI }
     *     
     */
    public ComuneRI getComune() {
        return comune;
    }

    /**
     * Sets the value of the comune property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComuneRI }
     *     
     */
    public void setComune(ComuneRI value) {
        this.comune = value;
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
     * Gets the value of the toponimo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToponimo() {
        return toponimo;
    }

    /**
     * Sets the value of the toponimo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToponimo(String value) {
        this.toponimo = value;
    }

    /**
     * Gets the value of the denominazioneStradale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDenominazioneStradale() {
        return denominazioneStradale;
    }

    /**
     * Sets the value of the denominazioneStradale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDenominazioneStradale(String value) {
        this.denominazioneStradale = value;
    }

    /**
     * Gets the value of the numeroCivico property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroCivico() {
        return numeroCivico;
    }

    /**
     * Sets the value of the numeroCivico property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroCivico(String value) {
        this.numeroCivico = value;
    }

    /**
     * Gets the value of the frazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFrazione() {
        return frazione;
    }

    /**
     * Sets the value of the frazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFrazione(String value) {
        this.frazione = value;
    }

}
