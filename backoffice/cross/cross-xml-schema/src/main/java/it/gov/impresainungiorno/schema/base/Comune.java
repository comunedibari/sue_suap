//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.03.02 at 03:51:49 PM CET 
//


package it.gov.impresainungiorno.schema.base;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for Comune complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Comune">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.impresainungiorno.gov.it/schema/base>Stringa">
 *       &lt;attribute name="codice-catastale" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.impresainungiorno.gov.it/schema/base}Stringa">
 *             &lt;pattern value="[A-Z]{1}\d{3}"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="codice-istat">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.impresainungiorno.gov.it/schema/base}Stringa">
 *             &lt;pattern value="\d{6}"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Comune", propOrder = {
    "value"
})
public class Comune {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "codice-catastale", required = true)
    protected String codiceCatastale;
    @XmlAttribute(name = "codice-istat")
    protected String codiceIstat;

    /**
     *  Questo elemento non puo' assumere valore stringa vuota, al piu' non c'e' 
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the codiceCatastale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceCatastale() {
        return codiceCatastale;
    }

    /**
     * Sets the value of the codiceCatastale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceCatastale(String value) {
        this.codiceCatastale = value;
    }

    /**
     * Gets the value of the codiceIstat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceIstat() {
        return codiceIstat;
    }

    /**
     * Sets the value of the codiceIstat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceIstat(String value) {
        this.codiceIstat = value;
    }

}