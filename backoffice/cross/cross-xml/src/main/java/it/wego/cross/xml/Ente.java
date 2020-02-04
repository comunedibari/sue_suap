//
// Questo file stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrpersa durante la ricompilazione dello schema di origine. 
// Generato il: 2013.05.27 alle 07:27:17 PM CEST 
//
package it.wego.cross.xml;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Classe Java per ente complex type.
 *
 * <p>
 * Il seguente frammento di schema specifica il contenuto previsto contenuto in
 * questa classe.
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
    private String codiceAoo;
    @XmlElement(name = "tipo_ente")
    private String tipoEnte;
    @XmlElement(name = "cod_ente_esterno")
    private String codEnteEsterno;
    @XmlElement(name = "unita_organizzativa")
    private String unitaOrganizzativa;
    @XmlElement(name = "codice_amministrazione")
    private String codiceAmministrazione;
    @XmlElement(name = "identificativo_suap")
    private String identificativoSuap;

    /**
     * Recupera il valore della proprietidEnte.
     *
     * @return possible object is {@link BigInteger }
     *
     */
    public BigInteger getIdEnte() {
        return idEnte;
    }

    /**
     * Imposta il valore della proprietidEnte.
     *
     * @param value allowed object is {@link BigInteger }
     *
     */
    public void setIdEnte(BigInteger value) {
        this.idEnte = value;
    }

    /**
     * Recupera il valore della proprietcodEnte.
     *
     * @return possible object is {@link String }
     *
     */
    public String getCodEnteo() {
        return codEnte;
    }

    /**
     * Imposta il valore della proprietcodEnte.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setCodEnte(String value) {
        this.codEnte = value;
    }

    /**
     * Recupera il valore della proprietDescrizione.
     *
     * @return possible object is {@link String }
     *
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Imposta il valore della proprietDescrizione.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setDescrizione(String value) {
        this.descrizione = value;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getPartitaIva() {
        return partitaIva;
    }

    public void setPartitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPec() {
        return pec;
    }

    public void setPec(String pec) {
        this.pec = pec;
    }

    public String getCodiceIstat() {
        return codiceIstat;
    }

    public void setCodiceIstat(String codiceIstat) {
        this.codiceIstat = codiceIstat;
    }

    public String getCodiceCatastale() {
        return codiceCatastale;
    }

    public void setCodiceCatastale(String codiceCatastale) {
        this.codiceCatastale = codiceCatastale;
    }

    public String getCodiceAoo() {
        return codiceAoo;
    }

    public void setCodiceAoo(String codiceAoo) {
        this.codiceAoo = codiceAoo;
    }

    public String getTipoEnte() {
        return tipoEnte;
    }

    public void setTipoEnte(String tipoEnte) {
        this.tipoEnte = tipoEnte;
    }

    public String getCodEnteEsterno() {
        return codEnteEsterno;
    }

    public void setCodEnteEsterno(String codEnteEsterno) {
        this.codEnteEsterno = codEnteEsterno;
    }

    public String getUnitaOrganizzativa() {
        return unitaOrganizzativa;
    }

    public void setUnitaOrganizzativa(String unitaOrganizzativa) {
        this.unitaOrganizzativa = unitaOrganizzativa;
    }

    public String getCodiceAmministrazione() {
        return codiceAmministrazione;
    }

    public void setCodiceAmministrazione(String codiceAmministrazione) {
        this.codiceAmministrazione = codiceAmministrazione;
    }

    public String getIdentificativoSuap() {
        return identificativoSuap;
    }

    public void setIdentificativoSuap(String identificativoSuap) {
        this.identificativoSuap = identificativoSuap;
    }

}
