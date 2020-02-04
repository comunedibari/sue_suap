
package it.gov.impresainungiorno.schema.base;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Identificazione di indirizzo straniero, per i casi in cui un indirizzo italiano non e' valido 
 * 
 * <p>Java class for IndirizzoStraniero complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IndirizzoStraniero">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{http://www.impresainungiorno.gov.it/schema/base}CittaStraniera"/>
 *         &lt;group ref="{http://www.impresainungiorno.gov.it/schema/base}DatiIndirizzo"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IndirizzoStraniero", propOrder = {
    "cittaStraniera",
    "denominazioneStradale",
    "numeroCivico",
    "frazione"
})
public class IndirizzoStraniero {

    @XmlElement(name = "citta-straniera", required = true)
    protected String cittaStraniera;
    @XmlElement(name = "denominazione-stradale", required = true)
    protected String denominazioneStradale;
    @XmlElement(name = "numero-civico", required = true)
    protected String numeroCivico;
    protected String frazione;

    /**
     * Gets the value of the cittaStraniera property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCittaStraniera() {
        return cittaStraniera;
    }

    /**
     * Sets the value of the cittaStraniera property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCittaStraniera(String value) {
        this.cittaStraniera = value;
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
