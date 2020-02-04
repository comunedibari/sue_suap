
package it.wego.cross;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dato_catastaleSIT complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dato_catastaleSIT">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id_immobile" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codice_sit" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cod_tipo_unita" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="des_tipo_unita" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sezione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="foglio" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="mappale" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="subalterno" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dato_catastaleSIT", propOrder = {
    "idImmobile",
    "codiceSit",
    "codTipoUnita",
    "desTipoUnita",
    "sezione",
    "foglio",
    "mappale",
    "subalterno"
})
public class DatoCatastaleSIT {

    @XmlElement(name = "id_immobile", required = true)
    protected String idImmobile;
    @XmlElement(name = "codice_sit", required = true)
    protected String codiceSit;
    @XmlElement(name = "cod_tipo_unita", required = true)
    protected String codTipoUnita;
    @XmlElement(name = "des_tipo_unita", required = true)
    protected String desTipoUnita;
    @XmlElement(required = true)
    protected String sezione;
    @XmlElement(required = true)
    protected String foglio;
    @XmlElement(required = true)
    protected String mappale;
    @XmlElement(required = true)
    protected String subalterno;

    /**
     * Gets the value of the idImmobile property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdImmobile() {
        return idImmobile;
    }

    /**
     * Sets the value of the idImmobile property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdImmobile(String value) {
        this.idImmobile = value;
    }

    /**
     * Gets the value of the codiceSit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceSit() {
        return codiceSit;
    }

    /**
     * Sets the value of the codiceSit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceSit(String value) {
        this.codiceSit = value;
    }

    /**
     * Gets the value of the codTipoUnita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodTipoUnita() {
        return codTipoUnita;
    }

    /**
     * Sets the value of the codTipoUnita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodTipoUnita(String value) {
        this.codTipoUnita = value;
    }

    /**
     * Gets the value of the desTipoUnita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesTipoUnita() {
        return desTipoUnita;
    }

    /**
     * Sets the value of the desTipoUnita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesTipoUnita(String value) {
        this.desTipoUnita = value;
    }

    /**
     * Gets the value of the sezione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSezione() {
        return sezione;
    }

    /**
     * Sets the value of the sezione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSezione(String value) {
        this.sezione = value;
    }

    /**
     * Gets the value of the foglio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFoglio() {
        return foglio;
    }

    /**
     * Sets the value of the foglio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFoglio(String value) {
        this.foglio = value;
    }

    /**
     * Gets the value of the mappale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMappale() {
        return mappale;
    }

    /**
     * Sets the value of the mappale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMappale(String value) {
        this.mappale = value;
    }

    /**
     * Gets the value of the subalterno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubalterno() {
        return subalterno;
    }

    /**
     * Sets the value of the subalterno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubalterno(String value) {
        this.subalterno = value;
    }

}
