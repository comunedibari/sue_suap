
package it.wego.cross;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PraticaDetailResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PraticaDetailResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Pratica" type="{http://www.wego.it/cross}praticaSIT"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PraticaDetailResponse", propOrder = {
    "pratica"
})
public class PraticaDetailResponse {

    @XmlElement(name = "Pratica", required = true)
    protected PraticaSIT pratica;

    /**
     * Gets the value of the pratica property.
     * 
     * @return
     *     possible object is
     *     {@link PraticaSIT }
     *     
     */
    public PraticaSIT getPratica() {
        return pratica;
    }

    /**
     * Sets the value of the pratica property.
     * 
     * @param value
     *     allowed object is
     *     {@link PraticaSIT }
     *     
     */
    public void setPratica(PraticaSIT value) {
        this.pratica = value;
    }

}
