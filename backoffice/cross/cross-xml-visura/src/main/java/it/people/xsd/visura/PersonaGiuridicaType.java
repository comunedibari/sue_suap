//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.03.08 at 11:11:55 AM CET 
//


package it.people.xsd.visura;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PersonaGiuridicaType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PersonaGiuridicaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="partitaIva" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codiceFiscale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ragioneSociale" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="naturaGiuridica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sedeLegale" type="{http://myPage.init.it/VisuraTypes}LocalizzazioneType" minOccurs="0"/>
 *         &lt;element name="indirizzoCorrispondenza" type="{http://myPage.init.it/VisuraTypes}LocalizzazioneType" minOccurs="0"/>
 *         &lt;element name="telefono" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fax" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="iscrizioneCCIAA" type="{http://myPage.init.it/VisuraTypes}IscrizioneRegistroType" minOccurs="0"/>
 *         &lt;element name="altriDati" type="{http://myPage.init.it/VisuraTypes}ParametroType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonaGiuridicaType", propOrder = {
    "partitaIva",
    "codiceFiscale",
    "ragioneSociale",
    "naturaGiuridica",
    "sedeLegale",
    "indirizzoCorrispondenza",
    "telefono",
    "fax",
    "iscrizioneCCIAA",
    "altriDati"
})
public class PersonaGiuridicaType {

    @XmlElement(required = true)
    protected String partitaIva;
    protected String codiceFiscale;
    @XmlElement(required = true)
    protected String ragioneSociale;
    protected String naturaGiuridica;
    protected LocalizzazioneType sedeLegale;
    protected LocalizzazioneType indirizzoCorrispondenza;
    protected String telefono;
    protected String fax;
    protected IscrizioneRegistroType iscrizioneCCIAA;
    protected List<ParametroType> altriDati;

    /**
     * Gets the value of the partitaIva property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartitaIva() {
        return partitaIva;
    }

    /**
     * Sets the value of the partitaIva property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartitaIva(String value) {
        this.partitaIva = value;
    }

    /**
     * Gets the value of the codiceFiscale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    /**
     * Sets the value of the codiceFiscale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceFiscale(String value) {
        this.codiceFiscale = value;
    }

    /**
     * Gets the value of the ragioneSociale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRagioneSociale() {
        return ragioneSociale;
    }

    /**
     * Sets the value of the ragioneSociale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRagioneSociale(String value) {
        this.ragioneSociale = value;
    }

    /**
     * Gets the value of the naturaGiuridica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNaturaGiuridica() {
        return naturaGiuridica;
    }

    /**
     * Sets the value of the naturaGiuridica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNaturaGiuridica(String value) {
        this.naturaGiuridica = value;
    }

    /**
     * Gets the value of the sedeLegale property.
     * 
     * @return
     *     possible object is
     *     {@link LocalizzazioneType }
     *     
     */
    public LocalizzazioneType getSedeLegale() {
        return sedeLegale;
    }

    /**
     * Sets the value of the sedeLegale property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocalizzazioneType }
     *     
     */
    public void setSedeLegale(LocalizzazioneType value) {
        this.sedeLegale = value;
    }

    /**
     * Gets the value of the indirizzoCorrispondenza property.
     * 
     * @return
     *     possible object is
     *     {@link LocalizzazioneType }
     *     
     */
    public LocalizzazioneType getIndirizzoCorrispondenza() {
        return indirizzoCorrispondenza;
    }

    /**
     * Sets the value of the indirizzoCorrispondenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocalizzazioneType }
     *     
     */
    public void setIndirizzoCorrispondenza(LocalizzazioneType value) {
        this.indirizzoCorrispondenza = value;
    }

    /**
     * Gets the value of the telefono property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Sets the value of the telefono property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelefono(String value) {
        this.telefono = value;
    }

    /**
     * Gets the value of the fax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFax() {
        return fax;
    }

    /**
     * Sets the value of the fax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFax(String value) {
        this.fax = value;
    }

    /**
     * Gets the value of the iscrizioneCCIAA property.
     * 
     * @return
     *     possible object is
     *     {@link IscrizioneRegistroType }
     *     
     */
    public IscrizioneRegistroType getIscrizioneCCIAA() {
        return iscrizioneCCIAA;
    }

    /**
     * Sets the value of the iscrizioneCCIAA property.
     * 
     * @param value
     *     allowed object is
     *     {@link IscrizioneRegistroType }
     *     
     */
    public void setIscrizioneCCIAA(IscrizioneRegistroType value) {
        this.iscrizioneCCIAA = value;
    }

    /**
     * Gets the value of the altriDati property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the altriDati property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAltriDati().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ParametroType }
     * 
     * 
     */
    public List<ParametroType> getAltriDati() {
        if (altriDati == null) {
            altriDati = new ArrayList<ParametroType>();
        }
        return this.altriDati;
    }

}
