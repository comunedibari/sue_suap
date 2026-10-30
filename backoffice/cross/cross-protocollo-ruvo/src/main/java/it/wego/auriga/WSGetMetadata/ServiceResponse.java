
package it.wego.auriga.WSGetMetadata;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for serviceResponse element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="serviceResponse">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element name="serviceReturn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;/sequence>
 *       &lt;/restriction>
 *     &lt;/complexContent>
 *   &lt;/complexType>
 * &lt;/element>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "serviceReturn"
})
@XmlRootElement(name = "serviceResponse")
public class ServiceResponse {

    @XmlElement(required = true)
    protected String serviceReturn;

    /**
     * Gets the value of the serviceReturn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceReturn() {
        return serviceReturn;
    }

    /**
     * Sets the value of the serviceReturn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceReturn(String value) {
        this.serviceReturn = value;
    }

}
