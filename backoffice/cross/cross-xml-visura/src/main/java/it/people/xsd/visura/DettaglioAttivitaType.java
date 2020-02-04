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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for DettaglioAttivitaType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DettaglioAttivitaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idPratica" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idProcedimento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idAttivita" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataAttivita" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="numeroProtocolloGenerale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataProtocolloGenerale" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="tipoAttivita" type="{http://myPage.init.it/VisuraTypes}TipoAttivitaType"/>
 *         &lt;element name="esito" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="parere" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="note" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="altriDati" type="{http://myPage.init.it/VisuraTypes}ParametroType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="documenti" type="{http://myPage.init.it/VisuraTypes}DocumentiType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DettaglioAttivitaType", propOrder = {
    "idPratica",
    "idProcedimento",
    "idAttivita",
    "dataAttivita",
    "numeroProtocolloGenerale",
    "dataProtocolloGenerale",
    "tipoAttivita",
    "esito",
    "parere",
    "note",
    "altriDati",
    "documenti"
})
public class DettaglioAttivitaType {

    @XmlElement(required = true)
    protected String idPratica;
    protected String idProcedimento;
    @XmlElement(required = true)
    protected String idAttivita;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataAttivita;
    protected String numeroProtocolloGenerale;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataProtocolloGenerale;
    @XmlElement(required = true)
    protected TipoAttivitaType tipoAttivita;
    protected boolean esito;
    protected String parere;
    protected String note;
    protected List<ParametroType> altriDati;
    protected List<DocumentiType> documenti;

    /**
     * Gets the value of the idPratica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdPratica() {
        return idPratica;
    }

    /**
     * Sets the value of the idPratica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdPratica(String value) {
        this.idPratica = value;
    }

    /**
     * Gets the value of the idProcedimento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdProcedimento() {
        return idProcedimento;
    }

    /**
     * Sets the value of the idProcedimento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdProcedimento(String value) {
        this.idProcedimento = value;
    }

    /**
     * Gets the value of the idAttivita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdAttivita() {
        return idAttivita;
    }

    /**
     * Sets the value of the idAttivita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdAttivita(String value) {
        this.idAttivita = value;
    }

    /**
     * Gets the value of the dataAttivita property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataAttivita() {
        return dataAttivita;
    }

    /**
     * Sets the value of the dataAttivita property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataAttivita(XMLGregorianCalendar value) {
        this.dataAttivita = value;
    }

    /**
     * Gets the value of the numeroProtocolloGenerale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroProtocolloGenerale() {
        return numeroProtocolloGenerale;
    }

    /**
     * Sets the value of the numeroProtocolloGenerale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroProtocolloGenerale(String value) {
        this.numeroProtocolloGenerale = value;
    }

    /**
     * Gets the value of the dataProtocolloGenerale property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataProtocolloGenerale() {
        return dataProtocolloGenerale;
    }

    /**
     * Sets the value of the dataProtocolloGenerale property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataProtocolloGenerale(XMLGregorianCalendar value) {
        this.dataProtocolloGenerale = value;
    }

    /**
     * Gets the value of the tipoAttivita property.
     * 
     * @return
     *     possible object is
     *     {@link TipoAttivitaType }
     *     
     */
    public TipoAttivitaType getTipoAttivita() {
        return tipoAttivita;
    }

    /**
     * Sets the value of the tipoAttivita property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoAttivitaType }
     *     
     */
    public void setTipoAttivita(TipoAttivitaType value) {
        this.tipoAttivita = value;
    }

    /**
     * Gets the value of the esito property.
     * 
     */
    public boolean isEsito() {
        return esito;
    }

    /**
     * Sets the value of the esito property.
     * 
     */
    public void setEsito(boolean value) {
        this.esito = value;
    }

    /**
     * Gets the value of the parere property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParere() {
        return parere;
    }

    /**
     * Sets the value of the parere property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParere(String value) {
        this.parere = value;
    }

    /**
     * Gets the value of the note property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNote() {
        return note;
    }

    /**
     * Sets the value of the note property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNote(String value) {
        this.note = value;
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

    /**
     * Gets the value of the documenti property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the documenti property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDocumenti().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DocumentiType }
     * 
     * 
     */
    public List<DocumentiType> getDocumenti() {
        if (documenti == null) {
            documenti = new ArrayList<DocumentiType>();
        }
        return this.documenti;
    }

}
