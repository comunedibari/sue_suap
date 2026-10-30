
package it.eng.tz.avtmb.xsd.stampsign;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="stamp" type="{http://stampsign.xsd.avtmb.tz.eng.it}stamp" minOccurs="0"/>
 *         &lt;element name="sign" type="{http://stampsign.xsd.avtmb.tz.eng.it}sign" minOccurs="0"/>
 *         &lt;element name="stampSign" type="{http://stampsign.xsd.avtmb.tz.eng.it}stampSign" minOccurs="0"/>
 *       &lt;/choice>
 *       &lt;attribute name="requestType" use="required" type="{http://stampsign.xsd.avtmb.tz.eng.it}requestType" />
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "stamp",
    "sign",
    "stampSign"
})
@XmlRootElement(name = "stampSignRequest")
public class StampSignRequest {

    protected Stamp stamp;
    protected Sign sign;
    protected StampSign stampSign;
    @XmlAttribute(name = "requestType", required = true)
    protected RequestType requestType;
    @XmlAttribute(name = "id", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;

    /**
     * Gets the value of the stamp property.
     * 
     * @return
     *     possible object is
     *     {@link Stamp }
     *     
     */
    public Stamp getStamp() {
        return stamp;
    }

    /**
     * Sets the value of the stamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link Stamp }
     *     
     */
    public void setStamp(Stamp value) {
        this.stamp = value;
    }

    /**
     * Gets the value of the sign property.
     * 
     * @return
     *     possible object is
     *     {@link Sign }
     *     
     */
    public Sign getSign() {
        return sign;
    }

    /**
     * Sets the value of the sign property.
     * 
     * @param value
     *     allowed object is
     *     {@link Sign }
     *     
     */
    public void setSign(Sign value) {
        this.sign = value;
    }

    /**
     * Gets the value of the stampSign property.
     * 
     * @return
     *     possible object is
     *     {@link StampSign }
     *     
     */
    public StampSign getStampSign() {
        return stampSign;
    }

    /**
     * Sets the value of the stampSign property.
     * 
     * @param value
     *     allowed object is
     *     {@link StampSign }
     *     
     */
    public void setStampSign(StampSign value) {
        this.stampSign = value;
    }

    /**
     * Gets the value of the requestType property.
     * 
     * @return
     *     possible object is
     *     {@link RequestType }
     *     
     */
    public RequestType getRequestType() {
        return requestType;
    }

    /**
     * Sets the value of the requestType property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestType }
     *     
     */
    public void setRequestType(RequestType value) {
        this.requestType = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

}
