//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.03.08 at 11:11:55 AM CET 
//


package it.people.xsd.visura;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AnagrafeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AnagrafeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="personaFisica" type="{http://myPage.init.it/VisuraTypes}PersonaFisicaType"/>
 *         &lt;element name="personaGiuridica" type="{http://myPage.init.it/VisuraTypes}PersonaGiuridicaType"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AnagrafeType", propOrder = {
    "personaFisica",
    "personaGiuridica"
})
public class AnagrafeType {

    protected PersonaFisicaType personaFisica;
    protected PersonaGiuridicaType personaGiuridica;

    /**
     * Gets the value of the personaFisica property.
     * 
     * @return
     *     possible object is
     *     {@link PersonaFisicaType }
     *     
     */
    public PersonaFisicaType getPersonaFisica() {
        return personaFisica;
    }

    /**
     * Sets the value of the personaFisica property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonaFisicaType }
     *     
     */
    public void setPersonaFisica(PersonaFisicaType value) {
        this.personaFisica = value;
    }

    /**
     * Gets the value of the personaGiuridica property.
     * 
     * @return
     *     possible object is
     *     {@link PersonaGiuridicaType }
     *     
     */
    public PersonaGiuridicaType getPersonaGiuridica() {
        return personaGiuridica;
    }

    /**
     * Sets the value of the personaGiuridica property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonaGiuridicaType }
     *     
     */
    public void setPersonaGiuridica(PersonaGiuridicaType value) {
        this.personaGiuridica = value;
    }

}
