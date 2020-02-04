
package it.wego.cross.webservices.client.Reporter.stubs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="odtTemplate" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="xmlData" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="xmlStaticData" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="xmlParams" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="docOutputType" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "odtTemplate",
    "xmlData",
    "xmlStaticData",
    "xmlParams",
    "docOutputType"
})
@XmlRootElement(name = "generateDocument")
public class GenerateDocument {

    @XmlElement(required = true)
    protected byte[] odtTemplate;
    @XmlElement(required = true)
    protected byte[] xmlData;
    @XmlElement(required = true)
    protected byte[] xmlStaticData;
    @XmlElement(required = true)
    protected byte[] xmlParams;
    @XmlElement(required = true)
    protected byte[] docOutputType;

    /**
     * Gets the value of the odtTemplate property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getOdtTemplate() {
        return odtTemplate;
    }

    /**
     * Sets the value of the odtTemplate property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setOdtTemplate(byte[] value) {
        this.odtTemplate = value;
    }

    /**
     * Gets the value of the xmlData property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getXmlData() {
        return xmlData;
    }

    /**
     * Sets the value of the xmlData property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setXmlData(byte[] value) {
        this.xmlData = value;
    }

    /**
     * Gets the value of the xmlStaticData property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getXmlStaticData() {
        return xmlStaticData;
    }

    /**
     * Sets the value of the xmlStaticData property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setXmlStaticData(byte[] value) {
        this.xmlStaticData = value;
    }

    /**
     * Gets the value of the xmlParams property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getXmlParams() {
        return xmlParams;
    }

    /**
     * Sets the value of the xmlParams property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setXmlParams(byte[] value) {
        this.xmlParams = value;
    }

    /**
     * Gets the value of the docOutputType property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getDocOutputType() {
        return docOutputType;
    }

    /**
     * Sets the value of the docOutputType property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setDocOutputType(byte[] value) {
        this.docOutputType = value;
    }

}
