
package it.eng.tz.avtmb.xsd.stampsign;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for infoDoc complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="infoDoc">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="infoDocKey" type="{http://www.w3.org/2001/XMLSchema}normalizedString"/>
 *         &lt;element name="infoDocValue" type="{http://www.w3.org/2001/XMLSchema}normalizedString"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "infoDoc", propOrder = {
    "infoDocKey",
    "infoDocValue"
})
public class InfoDoc {

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String infoDocKey;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String infoDocValue;

    /**
     * Gets the value of the infoDocKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInfoDocKey() {
        return infoDocKey;
    }

    /**
     * Sets the value of the infoDocKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInfoDocKey(String value) {
        this.infoDocKey = value;
    }

    /**
     * Gets the value of the infoDocValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInfoDocValue() {
        return infoDocValue;
    }

    /**
     * Sets the value of the infoDocValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInfoDocValue(String value) {
        this.infoDocValue = value;
    }

}
