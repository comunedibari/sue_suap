//
// Questo file stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrpersa durante la ricompilazione dello schema di origine. 
// Generato il: 2013.06.13 alle 04:27:44 PM CEST 
//


package it.wego.cross.xml;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per recapito complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="recapito">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="counter" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="id_recapito" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="presso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id_comune" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="des_comune" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="localita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id_provincia" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="des_provincia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id_stato" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="des_stato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id_dug" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="indirizzo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codice_via" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="n_civico" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codice_civico" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="interno_numero" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="interno_lettera" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="interno_scala" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lettera" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="colore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cap" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="casella_postale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="altre_info_indirizzo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="telefono" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cellulare" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fax" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pec" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id_tipo_indirizzo" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="des_tipo_indirizzo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "recapito", propOrder = {
    "counter",
    "idRecapito",
    "presso",
    "idComune",
    "desComune",
    "localita",
    "idProvincia",
    "desProvincia",
    "idStato",
    "desStato",
    "idDug",
    "indirizzo",
    "codiceVia",
    "nCivico",
    "codiceCivico",
    "internoNumero",
    "internoLettera",
    "internoScala",
    "lettera",
    "colore",
    "cap",
    "casellaPostale",
    "altreInfoIndirizzo",
    "telefono",
    "cellulare",
    "fax",
    "email",
    "pec",
    "idTipoIndirizzo",
    "desTipoIndirizzo"
})
public class Recapito {

    @XmlElement(required = true)
    protected BigInteger counter;
    @XmlElement(name = "id_recapito")
    protected BigInteger idRecapito;
    protected String presso;
    @XmlElement(name = "id_comune")
    protected BigInteger idComune;
    @XmlElement(name = "des_comune")
    protected String desComune;
    protected String localita;
    @XmlElement(name = "id_provincia")
    protected BigInteger idProvincia;
    @XmlElement(name = "des_provincia")
    protected String desProvincia;
    @XmlElement(name = "id_stato")
    protected BigInteger idStato;
    @XmlElement(name = "des_stato")
    protected String desStato;
    @XmlElement(name = "id_dug")
    protected BigInteger idDug;
    protected String indirizzo;
    @XmlElement(name = "codice_via")
    protected String codiceVia;
    @XmlElement(name = "n_civico")
    protected String nCivico;
    @XmlElement(name = "codice_civico")
    protected String codiceCivico;
    @XmlElement(name = "interno_numero")
    protected String internoNumero;
    @XmlElement(name = "interno_lettera")
    protected String internoLettera;
    @XmlElement(name = "interno_scala")
    protected String internoScala;
    protected String lettera;
    protected String colore;
    protected String cap;
    @XmlElement(name = "casella_postale")
    protected String casellaPostale;
    @XmlElement(name = "altre_info_indirizzo")
    protected String altreInfoIndirizzo;
    protected String telefono;
    protected String cellulare;
    protected String fax;
    protected String email;
    protected String pec;
    @XmlElement(name = "id_tipo_indirizzo")
    protected BigInteger idTipoIndirizzo;
    @XmlElement(name = "des_tipo_indirizzo")
    protected String desTipoIndirizzo;

    /**
     * Recupera il valore della proprietcounter.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCounter() {
        return counter;
    }

    /**
     * Imposta il valore della proprietcounter.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCounter(BigInteger value) {
        this.counter = value;
    }

    /**
     * Recupera il valore della proprietidRecapito.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdRecapito() {
        return idRecapito;
    }

    /**
     * Imposta il valore della proprietidRecapito.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdRecapito(BigInteger value) {
        this.idRecapito = value;
    }

    /**
     * Recupera il valore della proprietpresso.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPresso() {
        return presso;
    }

    /**
     * Imposta il valore della proprietpresso.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPresso(String value) {
        this.presso = value;
    }

    /**
     * Recupera il valore della proprietidComune.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdComune() {
        return idComune;
    }

    /**
     * Imposta il valore della proprietidComune.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdComune(BigInteger value) {
        this.idComune = value;
    }

    /**
     * Recupera il valore della proprietdesComune.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesComune() {
        return desComune;
    }

    /**
     * Imposta il valore della proprietdesComune.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesComune(String value) {
        this.desComune = value;
    }

    /**
     * Recupera il valore della proprietlocalita.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocalita() {
        return localita;
    }

    /**
     * Imposta il valore della proprietlocalita.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocalita(String value) {
        this.localita = value;
    }

    /**
     * Recupera il valore della proprietidProvincia.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdProvincia() {
        return idProvincia;
    }

    /**
     * Imposta il valore della proprietidProvincia.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdProvincia(BigInteger value) {
        this.idProvincia = value;
    }

    /**
     * Recupera il valore della proprietdesProvincia.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesProvincia() {
        return desProvincia;
    }

    /**
     * Imposta il valore della proprietdesProvincia.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesProvincia(String value) {
        this.desProvincia = value;
    }

    /**
     * Recupera il valore della proprietidStato.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdStato() {
        return idStato;
    }

    /**
     * Imposta il valore della proprietidStato.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdStato(BigInteger value) {
        this.idStato = value;
    }

    /**
     * Recupera il valore della proprietdesStato.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesStato() {
        return desStato;
    }

    /**
     * Imposta il valore della proprietdesStato.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesStato(String value) {
        this.desStato = value;
    }

    /**
     * Recupera il valore della proprietidDug.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdDug() {
        return idDug;
    }

    /**
     * Imposta il valore della proprietidDug.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdDug(BigInteger value) {
        this.idDug = value;
    }

    /**
     * Recupera il valore della proprietindirizzo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndirizzo() {
        return indirizzo;
    }

    /**
     * Imposta il valore della proprietindirizzo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndirizzo(String value) {
        this.indirizzo = value;
    }

    /**
     * Recupera il valore della proprietcodiceVia.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceVia() {
        return codiceVia;
    }

    /**
     * Imposta il valore della proprietcodiceVia.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceVia(String value) {
        this.codiceVia = value;
    }

    /**
     * Recupera il valore della proprietnCivico.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNCivico() {
        return nCivico;
    }

    /**
     * Imposta il valore della proprietnCivico.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNCivico(String value) {
        this.nCivico = value;
    }

    /**
     * Recupera il valore della proprietcodiceCivico.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceCivico() {
        return codiceCivico;
    }

    /**
     * Imposta il valore della proprietcodiceCivico.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceCivico(String value) {
        this.codiceCivico = value;
    }

    /**
     * Recupera il valore della proprietinternoNumero.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInternoNumero() {
        return internoNumero;
    }

    /**
     * Imposta il valore della proprietinternoNumero.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInternoNumero(String value) {
        this.internoNumero = value;
    }

    /**
     * Recupera il valore della proprietinternoLettera.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInternoLettera() {
        return internoLettera;
    }

    /**
     * Imposta il valore della proprietinternoLettera.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInternoLettera(String value) {
        this.internoLettera = value;
    }

    /**
     * Recupera il valore della proprietinternoScala.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInternoScala() {
        return internoScala;
    }

    /**
     * Imposta il valore della proprietinternoScala.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInternoScala(String value) {
        this.internoScala = value;
    }

    /**
     * Recupera il valore della proprietlettera.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLettera() {
        return lettera;
    }

    /**
     * Imposta il valore della proprietlettera.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLettera(String value) {
        this.lettera = value;
    }

    /**
     * Recupera il valore della proprietcolore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColore() {
        return colore;
    }

    /**
     * Imposta il valore della proprietcolore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColore(String value) {
        this.colore = value;
    }

    /**
     * Recupera il valore della proprietcap.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCap() {
        return cap;
    }

    /**
     * Imposta il valore della proprietcap.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCap(String value) {
        this.cap = value;
    }

    /**
     * Recupera il valore della proprietcasellaPostale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCasellaPostale() {
        return casellaPostale;
    }

    /**
     * Imposta il valore della proprietcasellaPostale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCasellaPostale(String value) {
        this.casellaPostale = value;
    }

    /**
     * Recupera il valore della proprietaltreInfoIndirizzo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAltreInfoIndirizzo() {
        return altreInfoIndirizzo;
    }

    /**
     * Imposta il valore della proprietaltreInfoIndirizzo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAltreInfoIndirizzo(String value) {
        this.altreInfoIndirizzo = value;
    }

    /**
     * Recupera il valore della propriettelefono.
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
     * Imposta il valore della propriettelefono.
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
     * Recupera il valore della proprietcellulare.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCellulare() {
        return cellulare;
    }

    /**
     * Imposta il valore della proprietcellulare.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCellulare(String value) {
        this.cellulare = value;
    }

    /**
     * Recupera il valore della proprietfax.
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
     * Imposta il valore della proprietfax.
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
     * Recupera il valore della proprietemail.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Imposta il valore della proprietemail.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Recupera il valore della proprietpec.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPec() {
        return pec;
    }

    /**
     * Imposta il valore della proprietpec.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPec(String value) {
        this.pec = value;
    }

    /**
     * Recupera il valore della proprietidTipoIndirizzo.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdTipoIndirizzo() {
        return idTipoIndirizzo;
    }

    /**
     * Imposta il valore della proprietidTipoIndirizzo.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdTipoIndirizzo(BigInteger value) {
        this.idTipoIndirizzo = value;
    }

    /**
     * Recupera il valore della proprietdesTipoIndirizzo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesTipoIndirizzo() {
        return desTipoIndirizzo;
    }

    /**
     * Imposta il valore della proprietdesTipoIndirizzo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesTipoIndirizzo(String value) {
        this.desTipoIndirizzo = value;
    }

}
