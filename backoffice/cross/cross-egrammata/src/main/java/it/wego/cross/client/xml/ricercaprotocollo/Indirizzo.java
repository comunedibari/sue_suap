//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.11.28 at 06:31:28 PM CET 
//


package it.wego.cross.client.xml.ricercaprotocollo;

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
 *         &lt;sequence minOccurs="0">
 *           &lt;element ref="{urn:eGrammata:RisultatoRicerca.xsd}IdTopon" minOccurs="0"/>
 *           &lt;element ref="{urn:eGrammata:RisultatoRicerca.xsd}DesInd" minOccurs="0"/>
 *         &lt;/sequence>
 *         &lt;element ref="{urn:eGrammata:RisultatoRicerca.xsd}NumCiv" minOccurs="0"/>
 *         &lt;element ref="{urn:eGrammata:RisultatoRicerca.xsd}Comune"/>
 *         &lt;element ref="{urn:eGrammata:RisultatoRicerca.xsd}CAP" minOccurs="0"/>
 *         &lt;element ref="{urn:eGrammata:RisultatoRicerca.xsd}Provincia"/>
 *         &lt;element ref="{urn:eGrammata:RisultatoRicerca.xsd}Nazione" minOccurs="0"/>
 *         &lt;element ref="{urn:eGrammata:RisultatoRicerca.xsd}Telefono" minOccurs="0"/>
 *         &lt;element ref="{urn:eGrammata:RisultatoRicerca.xsd}Fax" minOccurs="0"/>
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
    "idTopon",
    "desInd",
    "numCiv",
    "comune",
    "cap",
    "provincia",
    "nazione",
    "telefono",
    "fax"
})
public class Indirizzo {

    @XmlElement(name = "IdTopon")
    protected String idTopon;
    @XmlElement(name = "DesInd")
    protected String desInd;
    @XmlElement(name = "NumCiv")
    protected String numCiv;
    @XmlElement(name = "Comune")
    protected Comune comune;
    @XmlElement(name = "CAP")
    protected String cap;
    @XmlElement(name = "Provincia")
    protected String provincia;
    @XmlElement(name = "Nazione")
    protected String nazione;
    @XmlElement(name = "Telefono")
    protected Telefono telefono;
    @XmlElement(name = "Fax")
    protected Fax fax;

    /**
     * Gets the value of the idTopon property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdTopon() {
        return idTopon;
    }

    /**
     * Sets the value of the idTopon property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdTopon(String value) {
        this.idTopon = value;
    }

    /**
     * Gets the value of the desInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesInd() {
        return desInd;
    }

    /**
     * Sets the value of the desInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesInd(String value) {
        this.desInd = value;
    }

    /**
     * Gets the value of the numCiv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumCiv() {
        return numCiv;
    }

    /**
     * Sets the value of the numCiv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumCiv(String value) {
        this.numCiv = value;
    }

    /**
     * Gets the value of the comune property.
     * 
     * @return
     *     possible object is
     *     {@link Comune }
     *     
     */
    public Comune getComune() {
        return comune;
    }

    /**
     * Sets the value of the comune property.
     * 
     * @param value
     *     allowed object is
     *     {@link Comune }
     *     
     */
    public void setComune(Comune value) {
        this.comune = value;
    }

    /**
     * Gets the value of the cap property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCAP() {
        return cap;
    }

    /**
     * Sets the value of the cap property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCAP(String value) {
        this.cap = value;
    }

    /**
     * Gets the value of the provincia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvincia() {
        return provincia;
    }

    /**
     * Sets the value of the provincia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvincia(String value) {
        this.provincia = value;
    }

    /**
     * Gets the value of the nazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNazione() {
        return nazione;
    }

    /**
     * Sets the value of the nazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNazione(String value) {
        this.nazione = value;
    }

    /**
     * Gets the value of the telefono property.
     * 
     * @return
     *     possible object is
     *     {@link Telefono }
     *     
     */
    public Telefono getTelefono() {
        return telefono;
    }

    /**
     * Sets the value of the telefono property.
     * 
     * @param value
     *     allowed object is
     *     {@link Telefono }
     *     
     */
    public void setTelefono(Telefono value) {
        this.telefono = value;
    }

    /**
     * Gets the value of the fax property.
     * 
     * @return
     *     possible object is
     *     {@link Fax }
     *     
     */
    public Fax getFax() {
        return fax;
    }

    /**
     * Sets the value of the fax property.
     * 
     * @param value
     *     allowed object is
     *     {@link Fax }
     *     
     */
    public void setFax(Fax value) {
        this.fax = value;
    }

}
