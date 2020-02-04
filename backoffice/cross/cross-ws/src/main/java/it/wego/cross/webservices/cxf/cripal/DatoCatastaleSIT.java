
package it.wego.cross.webservices.cxf.cripal;

import it.wego.cross.webservices.cxf.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per dato_catastaleSIT complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
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
     * Recupera il valore della propriet idImmobile.
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
     * Imposta il valore della propriet idImmobile.
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
     * Recupera il valore della propriet codiceSit.
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
     * Imposta il valore della propriet codiceSit.
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
     * Recupera il valore della propriet codTipoUnita.
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
     * Imposta il valore della propriet codTipoUnita.
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
     * Recupera il valore della propriet desTipoUnita.
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
     * Imposta il valore della propriet desTipoUnita.
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
     * Recupera il valore della propriet sezione.
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
     * Imposta il valore della propriet sezione.
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
     * Recupera il valore della propriet foglio.
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
     * Imposta il valore della propriet foglio.
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
     * Recupera il valore della propriet mappale.
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
     * Imposta il valore della propriet mappale.
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
     * Recupera il valore della propriet subalterno.
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
     * Imposta il valore della propriet subalterno.
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
