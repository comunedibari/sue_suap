
package it.wego.cross.webservices.cxf.cripal;

import it.wego.cross.webservices.cxf.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anagraficaSIT complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
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
     * Recupera il valore della propriet idAnagrafica.
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
     * Imposta il valore della propriet idAnagrafica.
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
     * Recupera il valore della propriet codTipoRuolo.
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
     * Imposta il valore della propriet codTipoRuolo.
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
     * Recupera il valore della propriet desTipoRuolo.
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
     * Imposta il valore della propriet desTipoRuolo.
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
     * Recupera il valore della propriet cognome.
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
     * Imposta il valore della propriet cognome.
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
     * Recupera il valore della propriet nome.
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
     * Imposta il valore della propriet nome.
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
     * Recupera il valore della propriet denominazione.
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
     * Imposta il valore della propriet denominazione.
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
     * Recupera il valore della propriet codiceFiscale.
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
     * Imposta il valore della propriet codiceFiscale.
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
     * Recupera il valore della propriet partitaIva.
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
     * Imposta il valore della propriet partitaIva.
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
     * Recupera il valore della propriet tipoAnagrafica.
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
     * Imposta il valore della propriet tipoAnagrafica.
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
