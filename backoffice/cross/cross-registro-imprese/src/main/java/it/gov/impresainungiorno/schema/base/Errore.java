
package it.gov.impresainungiorno.schema.base;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Errore complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Errore">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="causa" type="{http://www.impresainungiorno.gov.it/schema/base}Errore" minOccurs="0"/>
 *         &lt;element name="parametro" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="codice" use="required" type="{http://www.impresainungiorno.gov.it/schema/base}StringaBreve" />
 *       &lt;attribute name="gravita" type="{http://www.impresainungiorno.gov.it/schema/base}StringaBreve" default="GRAVE" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Errore", propOrder = {
    "causa",
    "parametro"
})
public class Errore {

    protected Errore causa;
    protected List<String> parametro;
    @XmlAttribute(name = "codice", required = true)
    protected String codice;
    @XmlAttribute(name = "gravita")
    protected String gravita;

    /**
     * Gets the value of the causa property.
     * 
     * @return
     *     possible object is
     *     {@link Errore }
     *     
     */
    public Errore getCausa() {
        return causa;
    }

    /**
     * Sets the value of the causa property.
     * 
     * @param value
     *     allowed object is
     *     {@link Errore }
     *     
     */
    public void setCausa(Errore value) {
        this.causa = value;
    }

    /**
     * Gets the value of the parametro property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the parametro property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParametro().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getParametro() {
        if (parametro == null) {
            parametro = new ArrayList<String>();
        }
        return this.parametro;
    }

    /**
     * Gets the value of the codice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodice() {
        return codice;
    }

    /**
     * Sets the value of the codice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodice(String value) {
        this.codice = value;
    }

    /**
     * Gets the value of the gravita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGravita() {
        if (gravita == null) {
            return "GRAVE";
        } else {
            return gravita;
        }
    }

    /**
     * Sets the value of the gravita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGravita(String value) {
        this.gravita = value;
    }

}
