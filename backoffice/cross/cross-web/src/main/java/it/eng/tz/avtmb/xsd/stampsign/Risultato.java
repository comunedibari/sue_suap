
package it.eng.tz.avtmb.xsd.stampsign;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for risultato complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="risultato">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="iriDownload" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="downloadFileName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="downloadFileContent" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "risultato", propOrder = {
    "iriDownload",
    "downloadFileName",
    "downloadFileContent"
})
public class Risultato {

    @XmlSchemaType(name = "anyURI")
    protected String iriDownload;
    protected String downloadFileName;
    @XmlMimeType("application/pdf")
    protected DataHandler downloadFileContent;

    /**
     * Gets the value of the iriDownload property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIriDownload() {
        return iriDownload;
    }

    /**
     * Sets the value of the iriDownload property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIriDownload(String value) {
        this.iriDownload = value;
    }

    /**
     * Gets the value of the downloadFileName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDownloadFileName() {
        return downloadFileName;
    }

    /**
     * Sets the value of the downloadFileName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDownloadFileName(String value) {
        this.downloadFileName = value;
    }

    /**
     * Gets the value of the downloadFileContent property.
     * 
     * @return
     *     possible object is
     *     {@link DataHandler }
     *     
     */
    public DataHandler getDownloadFileContent() {
        return downloadFileContent;
    }

    /**
     * Sets the value of the downloadFileContent property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataHandler }
     *     
     */
    public void setDownloadFileContent(DataHandler value) {
        this.downloadFileContent = value;
    }

}
