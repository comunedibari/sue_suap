
package it.infocamere.protocollo.ejb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.transform.Source;


/**
 * <p>Java class for allegatoSUAPXml complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="allegatoSUAPXml">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dataHandler" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "allegatoSUAPXml", propOrder = {
    "dataHandler"
})
public class AllegatoSUAPXml {

    @XmlMimeType("text/xml")
    protected Source dataHandler;

    /**
     * Gets the value of the dataHandler property.
     * 
     * @return
     *     possible object is
     *     {@link Source }
     *     
     */
    public Source getDataHandler() {
        return dataHandler;
    }

    /**
     * Sets the value of the dataHandler property.
     * 
     * @param value
     *     allowed object is
     *     {@link Source }
     *     
     */
    public void setDataHandler(Source value) {
        this.dataHandler = value;
    }

}
