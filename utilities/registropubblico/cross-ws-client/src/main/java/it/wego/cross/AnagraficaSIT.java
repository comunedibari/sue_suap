
package it.wego.cross;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anagraficaSIT complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="anagraficaSIT">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id_anagrafica" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cod_tipo_ruolo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="des_tipo_ruolo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cognome" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nome" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="denominazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codice_fiscale" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="partita_iva" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tipo_anagrafica" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "anagraficaSIT", propOrder = {
    "idAnagrafica",
    "codTipoRuolo",
    "desTipoRuolo",
    "cognome",
    "nome",
    "denominazione",
    "codiceFiscale",
    "partitaIva",
    "tipoAnagrafica"
})
public class AnagraficaSIT {

    @XmlElement(name = "id_anagrafica", required = true)
    protected String idAnagrafica;
    @XmlElement(name = "cod_tipo_ruolo", required = true)
    protected String codTipoRuolo;
    @XmlElement(name = "des_tipo_ruolo", required = true)
    protected String desTipoRuolo;
    @XmlElement(required = true)
    protected String cognome;
    @XmlElement(required = true)
    protected String nome;
    @XmlElement(required = true)
    protected String denominazione;
    @XmlElement(name = "codice_fiscale", required = true)
    protected String codiceFiscale;
    @XmlElement(name = "partita_iva", required = true)
    protected String partitaIva;
    @XmlElement(name = "tipo_anagrafica", required = true)
    protected String tipoAnagrafica;

    /**
     * Gets the value of the idAnagrafica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdAnagrafica() {
        return idAnagrafica;
    }

    /**
     * Sets the value of the idAnagrafica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdAnagrafica(String value) {
        this.idAnagrafica = value;
    }

    /**
     * Gets the value of the codTipoRuolo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodTipoRuolo() {
        return codTipoRuolo;
    }

    /**
     * Sets the value of the codTipoRuolo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodTipoRuolo(String value) {
        this.codTipoRuolo = value;
    }

    /**
     * Gets the value of the desTipoRuolo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesTipoRuolo() {
        return desTipoRuolo;
    }

    /**
     * Sets the value of the desTipoRuolo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesTipoRuolo(String value) {
        this.desTipoRuolo = value;
    }

    /**
     * Gets the value of the cognome property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Sets the value of the cognome property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCognome(String value) {
        this.cognome = value;
    }

    /**
     * Gets the value of the nome property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNome() {
        return nome;
    }

    /**
     * Sets the value of the nome property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNome(String value) {
        this.nome = value;
    }

    /**
     * Gets the value of the denominazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDenominazione() {
        return denominazione;
    }

    /**
     * Sets the value of the denominazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDenominazione(String value) {
        this.denominazione = value;
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
     * Gets the value of the tipoAnagrafica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoAnagrafica() {
        return tipoAnagrafica;
    }

    /**
     * Sets the value of the tipoAnagrafica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoAnagrafica(String value) {
        this.tipoAnagrafica = value;
    }

}
