//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.12.23 at 11:38:30 AM CET 
//


package it.wego.cross.client.xml.extractmulti;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EstremiRegNumType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EstremiRegNumType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="CategoriaReg" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="PG"/>
 *               &lt;enumeration value="PP"/>
 *               &lt;enumeration value="R"/>
 *               &lt;enumeration value="E"/>
 *               &lt;enumeration value="A"/>
 *               &lt;enumeration value="I"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="SiglaReg" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{}SiglaRegNumType">
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="AnnoReg" type="{}AnnoType"/>
 *         &lt;element name="NumReg" type="{}NumRegType"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EstremiRegNumType", propOrder = {

})
public class EstremiRegNumType {

    @XmlElement(name = "CategoriaReg")
    protected String categoriaReg;
    @XmlElement(name = "SiglaReg")
    protected String siglaReg;
    @XmlElement(name = "AnnoReg")
    protected int annoReg;
    @XmlElement(name = "NumReg")
    protected int numReg;

    /**
     * Gets the value of the categoriaReg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCategoriaReg() {
        return categoriaReg;
    }

    /**
     * Sets the value of the categoriaReg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCategoriaReg(String value) {
        this.categoriaReg = value;
    }

    /**
     * Gets the value of the siglaReg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiglaReg() {
        return siglaReg;
    }

    /**
     * Sets the value of the siglaReg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiglaReg(String value) {
        this.siglaReg = value;
    }

    /**
     * Gets the value of the annoReg property.
     * 
     */
    public int getAnnoReg() {
        return annoReg;
    }

    /**
     * Sets the value of the annoReg property.
     * 
     */
    public void setAnnoReg(int value) {
        this.annoReg = value;
    }

    /**
     * Gets the value of the numReg property.
     * 
     */
    public int getNumReg() {
        return numReg;
    }

    /**
     * Sets the value of the numReg property.
     * 
     */
    public void setNumReg(int value) {
        this.numReg = value;
    }

}
