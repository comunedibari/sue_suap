
package it.wego.cross.avbari.linksmt.fascicolo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for DocumentData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DocumentData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="titolo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nomeFile" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="contenuto" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="startDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="autoreUsername" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sunto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dettaglio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="classifica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="improntaMIME" type="{http://server.ws.protocollo.linksmt.it/}ImprontaMIME" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentData", propOrder = {
    "titolo",
    "nomeFile",
    "contenuto",
    "startDate",
    "autoreUsername",
    "sunto",
    "dettaglio",
    "classifica",
    "improntaMIME"
})
public class DocumentData {

    @XmlElement(required = true)
    protected String titolo;
    @XmlElement(required = true)
    protected String nomeFile;
    @XmlElement(required = true)
    protected byte[] contenuto;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startDate;
    @XmlElement(required = true)
    protected String autoreUsername;
    protected String sunto;
    protected String dettaglio;
    protected String classifica;
    protected ImprontaMIME improntaMIME;

    /**
     * Gets the value of the titolo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitolo() {
        return titolo;
    }

    /**
     * Sets the value of the titolo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitolo(String value) {
        this.titolo = value;
    }

    /**
     * Gets the value of the nomeFile property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeFile() {
        return nomeFile;
    }

    /**
     * Sets the value of the nomeFile property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeFile(String value) {
        this.nomeFile = value;
    }

    /**
     * Gets the value of the contenuto property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getContenuto() {
        return contenuto;
    }

    /**
     * Sets the value of the contenuto property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setContenuto(byte[] value) {
        this.contenuto = value;
    }

    /**
     * Gets the value of the startDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartDate() {
        return startDate;
    }

    /**
     * Sets the value of the startDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartDate(XMLGregorianCalendar value) {
        this.startDate = value;
    }

    /**
     * Gets the value of the autoreUsername property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAutoreUsername() {
        return autoreUsername;
    }

    /**
     * Sets the value of the autoreUsername property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAutoreUsername(String value) {
        this.autoreUsername = value;
    }

    /**
     * Gets the value of the sunto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSunto() {
        return sunto;
    }

    /**
     * Sets the value of the sunto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSunto(String value) {
        this.sunto = value;
    }

    /**
     * Gets the value of the dettaglio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDettaglio() {
        return dettaglio;
    }

    /**
     * Sets the value of the dettaglio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDettaglio(String value) {
        this.dettaglio = value;
    }

    /**
     * Gets the value of the classifica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClassifica() {
        return classifica;
    }

    /**
     * Sets the value of the classifica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClassifica(String value) {
        this.classifica = value;
    }

    /**
     * Gets the value of the improntaMIME property.
     * 
     * @return
     *     possible object is
     *     {@link ImprontaMIME }
     *     
     */
    public ImprontaMIME getImprontaMIME() {
        return improntaMIME;
    }

    /**
     * Sets the value of the improntaMIME property.
     * 
     * @param value
     *     allowed object is
     *     {@link ImprontaMIME }
     *     
     */
    public void setImprontaMIME(ImprontaMIME value) {
        this.improntaMIME = value;
    }

}
