//
// Questo file � stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andr� persa durante la ricompilazione dello schema di origine. 
// Generato il: 2024.04.16 alle 09:52:47 AM CEST 
//


package it.wego.cross.xml;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ente complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ente">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id_ente" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="cod_ente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="des_ente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codice_fiscale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="partita_iva" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="indirizzo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cap" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="citta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="provincia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="telefono" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fax" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pec" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codice_istat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codice_catastale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codice_aoo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipo_ente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cod_ente_esterno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="unita_organizzativa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codice_amministrazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="identificativo_suap" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ente", propOrder = {
    "idEnte",
    "codEnte",
    "descrizione",
    "codiceFiscale",
    "partitaIva",
    "indirizzo",
    "cap",
    "citta",
    "provincia",
    "telefono",
    "fax",
    "email",
    "pec",
    "codiceIstat",
    "codiceCatastale",
    "codiceAoo",
    "tipoEnte",
    "codEnteEsterno",
    "unitaOrganizzativa",
    "codiceAmministrazione",
    "identificativoSuap"
})
public class Ente {

    @XmlElement(name = "id_ente")
    protected BigInteger idEnte;
    @XmlElement(name = "cod_ente")
    protected String codEnte;
    @XmlElement(name = "des_ente")
    protected String descrizione;
    @XmlElement(name = "codice_fiscale")
    protected String codiceFiscale;
    @XmlElement(name = "partita_iva")
    protected String partitaIva;
    @XmlElement(name = "indirizzo")
    protected String indirizzo;
    @XmlElement(name = "cap")
    protected String cap;
    @XmlElement(name = "citta")
    protected String citta;
    @XmlElement(name = "provincia")
    protected String provincia;
    @XmlElement(name = "telefono")
    protected String telefono;
    @XmlElement(name = "fax")
    protected String fax;
    @XmlElement(name = "email")
    protected String email;
    @XmlElement(name = "pec")
    protected String pec;
    @XmlElement(name = "codice_istat")
    protected String codiceIstat;
    @XmlElement(name = "codice_catastale")
    protected String codiceCatastale;
    @XmlElement(name = "codice_aoo")
    protected String codiceAoo;
    @XmlElement(name = "tipo_ente")
    protected String tipoEnte;
    @XmlElement(name = "cod_ente_esterno")
    protected String codEnteEsterno;
    @XmlElement(name = "unita_organizzativa")
    protected String unitaOrganizzativa;
    @XmlElement(name = "codice_amministrazione")
    protected String codiceAmministrazione;
    @XmlElement(name = "identificativo_suap")
    protected String identificativoSuap;

    /**
     * Recupera il valore della propriet� idEnte.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdEnte() {
        return idEnte;
    }

    /**
     * Imposta il valore della propriet� idEnte.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdEnte(BigInteger value) {
        this.idEnte = value;
    }

    /**
     * Recupera il valore della propriet� codEnte.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodEnte() {
        return codEnte;
    }

    /**
     * Imposta il valore della propriet� codEnte.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodEnte(String value) {
        this.codEnte = value;
    }

    /**
     * Recupera il valore della propriet� desEnte.
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
     * Imposta il valore della propriet� desEnte.
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
     * Recupera il valore della propriet� codiceFiscale.
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
     * Imposta il valore della propriet� codiceFiscale.
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
     * Recupera il valore della propriet� partitaIva.
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
     * Imposta il valore della propriet� partitaIva.
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
     * Recupera il valore della propriet� indirizzo.
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
     * Imposta il valore della propriet� indirizzo.
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
     * Recupera il valore della propriet� cap.
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
     * Imposta il valore della propriet� cap.
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
     * Recupera il valore della propriet� citta.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCitta() {
        return citta;
    }

    /**
     * Imposta il valore della propriet� citta.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCitta(String value) {
        this.citta = value;
    }

    /**
     * Recupera il valore della propriet� provincia.
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
     * Imposta il valore della propriet� provincia.
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
     * Recupera il valore della propriet� telefono.
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
     * Imposta il valore della propriet� telefono.
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
     * Recupera il valore della propriet� fax.
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
     * Imposta il valore della propriet� fax.
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
     * Recupera il valore della propriet� email.
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
     * Imposta il valore della propriet� email.
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
     * Recupera il valore della propriet� pec.
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
     * Imposta il valore della propriet� pec.
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
     * Recupera il valore della propriet� codiceIstat.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceIstat() {
        return codiceIstat;
    }

    /**
     * Imposta il valore della propriet� codiceIstat.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceIstat(String value) {
        this.codiceIstat = value;
    }

    /**
     * Recupera il valore della propriet� codiceCatastale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceCatastale() {
        return codiceCatastale;
    }

    /**
     * Imposta il valore della propriet� codiceCatastale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceCatastale(String value) {
        this.codiceCatastale = value;
    }

    /**
     * Recupera il valore della propriet� codiceAoo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceAoo() {
        return codiceAoo;
    }

    /**
     * Imposta il valore della propriet� codiceAoo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceAoo(String value) {
        this.codiceAoo = value;
    }

    /**
     * Recupera il valore della propriet� tipoEnte.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoEnte() {
        return tipoEnte;
    }

    /**
     * Imposta il valore della propriet� tipoEnte.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoEnte(String value) {
        this.tipoEnte = value;
    }

    /**
     * Recupera il valore della propriet� codEnteEsterno.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodEnteEsterno() {
        return codEnteEsterno;
    }

    /**
     * Imposta il valore della propriet� codEnteEsterno.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodEnteEsterno(String value) {
        this.codEnteEsterno = value;
    }

    /**
     * Recupera il valore della propriet� unitaOrganizzativa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnitaOrganizzativa() {
        return unitaOrganizzativa;
    }

    /**
     * Imposta il valore della propriet� unitaOrganizzativa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnitaOrganizzativa(String value) {
        this.unitaOrganizzativa = value;
    }

    /**
     * Recupera il valore della propriet� codiceAmministrazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceAmministrazione() {
        return codiceAmministrazione;
    }

    /**
     * Imposta il valore della propriet� codiceAmministrazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceAmministrazione(String value) {
        this.codiceAmministrazione = value;
    }

    /**
     * Recupera il valore della propriet� identificativoSuap.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoSuap() {
        return identificativoSuap;
    }

    /**
     * Imposta il valore della propriet� identificativoSuap.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoSuap(String value) {
        this.identificativoSuap = value;
    }

}
