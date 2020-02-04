
package it.wego.cross.avbari.linksmt.fascicolo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="autore" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="profilo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="idNodoPadre" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idNodoTitolario" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idModelloFascicolo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="riservato" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="startDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="endDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
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
    "autore",
    "profilo",
    "idNodoPadre",
    "idNodoTitolario",
    "idModelloFascicolo",
    "descrizione",
    "riservato",
    "startDate",
    "endDate"
})
@XmlRootElement(name = "nuovoFascicoloRequest")
public class NuovoFascicoloRequest {

    @XmlElement(required = true)
    protected String autore;
    protected int profilo;
    @XmlElement(required = true)
    protected String idNodoPadre;
    @XmlElement(required = true)
    protected String idNodoTitolario;
    protected int idModelloFascicolo;
    @XmlElement(required = true)
    protected String descrizione;
    protected boolean riservato;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startDate;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endDate;

    /**
     * Gets the value of the autore property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAutore() {
        return autore;
    }

    /**
     * Sets the value of the autore property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAutore(String value) {
        this.autore = value;
    }

    /**
     * Gets the value of the profilo property.
     * 
     */
    public int getProfilo() {
        return profilo;
    }

    /**
     * Sets the value of the profilo property.
     * 
     */
    public void setProfilo(int value) {
        this.profilo = value;
    }

    /**
     * Gets the value of the idNodoPadre property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdNodoPadre() {
        return idNodoPadre;
    }

    /**
     * Sets the value of the idNodoPadre property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdNodoPadre(String value) {
        this.idNodoPadre = value;
    }

    /**
     * Gets the value of the idNodoTitolario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdNodoTitolario() {
        return idNodoTitolario;
    }

    /**
     * Sets the value of the idNodoTitolario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdNodoTitolario(String value) {
        this.idNodoTitolario = value;
    }

    /**
     * Gets the value of the idModelloFascicolo property.
     * 
     */
    public int getIdModelloFascicolo() {
        return idModelloFascicolo;
    }

    /**
     * Sets the value of the idModelloFascicolo property.
     * 
     */
    public void setIdModelloFascicolo(int value) {
        this.idModelloFascicolo = value;
    }

    /**
     * Gets the value of the descrizione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Sets the value of the descrizione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizione(String value) {
        this.descrizione = value;
    }

    /**
     * Gets the value of the riservato property.
     * 
     */
    public boolean isRiservato() {
        return riservato;
    }

    /**
     * Sets the value of the riservato property.
     * 
     */
    public void setRiservato(boolean value) {
        this.riservato = value;
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
     * Gets the value of the endDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndDate(XMLGregorianCalendar value) {
        this.endDate = value;
    }

}
