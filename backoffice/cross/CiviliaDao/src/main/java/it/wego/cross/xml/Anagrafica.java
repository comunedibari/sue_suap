//
// Questo file stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrpersa durante la ricompilazione dello schema di origine. 
// Generato il: 2013.05.27 alle 07:27:17 PM CEST 
//
package it.wego.cross.xml;

import it.wego.cross.xml.adapter.DateAdapter;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * Classe Java per anagrafica complex type.
 *
 * <p>
 * Il seguente frammento di schema specifica il contenuto previsto contenuto in
 * questa classe.
 *
 * <pre>
 * &lt;complexType name="anagrafica">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="counter" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="confermata" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id_anagrafica" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="tipo_anagrafica" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="variante_anagrafica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cognome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="denominazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codice_fiscale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="partita_iva" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flg_individuale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="data_nascita" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="id_cittadinanza" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="cod_cittadinanza" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="des_cittadinanza" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id_nazionalita" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="cod_nazionalita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="des_nazionalita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id_comune_nascita" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="des_comune_nascita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id_provincia_nascita" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="des_provincia_nascita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id_stato_nascita" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="des_stato_nascita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="localita_nascita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sesso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id_tipo_collegio" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="des_tipo_collegio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numero_iscrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="data_iscrizione" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="id_provincia_iscrizione" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="des_provincia_iscrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id_provincia_cciaa" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="cod_provincia_cciaa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="des_provincia_cciaa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flg_attesa_iscrizione_ri" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="flg_obbligo_iscrizione_ri" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="data_iscrizione_ri" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="n_iscrizione_ri" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flg_attesa_iscrizione_rea" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="data_iscrizione_rea" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="n_iscrizione_rea" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id_forma_giuridica" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="des_forma_giuridica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="recapiti" type="{http://www.wego.it/cross}recapiti" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "anagrafica", propOrder = {
    "counter",
    "confermata",
    "daRubrica",
    "idAnagrafica",
    "tipoAnagrafica",
    "varianteAnagrafica",
    "cognome",
    "denominazione",
    "nome",
    "codiceFiscale",
    "partitaIva",
    "flgIndividuale",
    "dataNascita",
    "idCittadinanza",
    "codCittadinanza",
    "desCittadinanza",
    "idNazionalita",
    "codNazionalita",
    "desNazionalita",
    "idComuneNascita",
    "desComuneNascita",
    "idProvinciaNascita",
    "desProvinciaNascita",
    "idStatoNascita",
    "desStatoNascita",
    "localitaNascita",
    "sesso",
    "idTipoCollegio",
    "desTipoCollegio",
    "numeroIscrizione",
    "dataIscrizione",
    "idProvinciaIscrizione",
    "codProvinciaIscrizione",
    "desProvinciaIscrizione",
    "idProvinciaCciaa",
    "codProvinciaCciaa",
    "desProvinciaCciaa",
    "flgAttesaIscrizioneRi",
    "flgObbligoIscrizioneRi",
    "dataIscrizioneRi",
    "nIscrizioneRi",
    "flgAttesaIscrizioneRea",
    "dataIscrizioneRea",
    "nIscrizioneRea",
    "idFormaGiuridica",
    "desFormaGiuridica",
    "recapiti"
})
public class Anagrafica {

    @XmlElement(required = true)
    protected BigInteger counter;
    protected String confermata;
    @XmlElement(name = "da_rubrica")
    protected String daRubrica = "N";
    @XmlElement(name = "id_anagrafica")
    protected BigInteger idAnagrafica;
    @XmlElement(name = "tipo_anagrafica", required = true)
    protected String tipoAnagrafica;
    @XmlElement(name = "variante_anagrafica")
    protected String varianteAnagrafica;
    protected String cognome;
    protected String denominazione;
    protected String nome;
    @XmlElement(name = "codice_fiscale")
    protected String codiceFiscale;
    @XmlElement(name = "partita_iva")
    protected String partitaIva;
    @XmlElement(name = "flg_individuale")
    protected String flgIndividuale;
    @XmlElement(name = "data_nascita")
    @XmlSchemaType(name = "date")
    @XmlJavaTypeAdapter(DateAdapter.class)
    protected XMLGregorianCalendar dataNascita;
    @XmlElement(name = "id_cittadinanza")
    protected BigInteger idCittadinanza;
    @XmlElement(name = "cod_cittadinanza")
    protected String codCittadinanza;
    @XmlElement(name = "des_cittadinanza")
    protected String desCittadinanza;
    @XmlElement(name = "id_nazionalita")
    protected BigInteger idNazionalita;
    @XmlElement(name = "cod_nazionalita")
    protected String codNazionalita;
    @XmlElement(name = "des_nazionalita")
    protected String desNazionalita;
    @XmlElement(name = "id_comune_nascita")
    protected BigInteger idComuneNascita;
    @XmlElement(name = "des_comune_nascita")
    protected String desComuneNascita;
    @XmlElement(name = "id_provincia_nascita")
    protected BigInteger idProvinciaNascita;
    @XmlElement(name = "des_provincia_nascita")
    protected String desProvinciaNascita;
    @XmlElement(name = "id_stato_nascita")
    protected BigInteger idStatoNascita;
    @XmlElement(name = "des_stato_nascita")
    protected String desStatoNascita;
    @XmlElement(name = "localita_nascita")
    protected String localitaNascita;
    protected String sesso;
    @XmlElement(name = "id_tipo_collegio")
    protected BigInteger idTipoCollegio;
    @XmlElement(name = "des_tipo_collegio")
    protected String desTipoCollegio;
    @XmlElement(name = "numero_iscrizione")
    protected String numeroIscrizione;
    @XmlElement(name = "data_iscrizione")
    @XmlSchemaType(name = "date")
    @XmlJavaTypeAdapter(DateAdapter.class)
    protected XMLGregorianCalendar dataIscrizione;
    @XmlElement(name = "id_provincia_iscrizione")
    protected BigInteger idProvinciaIscrizione;
    @XmlElement(name = "cod_provincia_iscrizione")
    protected String codProvinciaIscrizione;
    @XmlElement(name = "des_provincia_iscrizione")
    protected String desProvinciaIscrizione;
    @XmlElement(name = "id_provincia_cciaa")
    protected BigInteger idProvinciaCciaa;
    @XmlElement(name = "cod_provincia_cciaa")
    protected String codProvinciaCciaa;
    @XmlElement(name = "des_provincia_cciaa")
    protected String desProvinciaCciaa;
    @XmlElement(name = "flg_attesa_iscrizione_ri")
    protected Boolean flgAttesaIscrizioneRi;
    @XmlElement(name = "flg_obbligo_iscrizione_ri")
    protected Boolean flgObbligoIscrizioneRi;
    @XmlElement(name = "data_iscrizione_ri")
    @XmlSchemaType(name = "date")
    @XmlJavaTypeAdapter(DateAdapter.class)
    protected XMLGregorianCalendar dataIscrizioneRi;
    @XmlElement(name = "n_iscrizione_ri")
    protected String nIscrizioneRi;
    @XmlElement(name = "flg_attesa_iscrizione_rea")
    protected Boolean flgAttesaIscrizioneRea;
    @XmlElement(name = "data_iscrizione_rea")
    @XmlSchemaType(name = "date")
    @XmlJavaTypeAdapter(DateAdapter.class)
    protected XMLGregorianCalendar dataIscrizioneRea;
    @XmlElement(name = "n_iscrizione_rea")
    protected String nIscrizioneRea;
    @XmlElement(name = "id_forma_giuridica")
    protected BigInteger idFormaGiuridica;
    @XmlElement(name = "des_forma_giuridica")
    protected String desFormaGiuridica;
    protected Recapiti recapiti;

    /**
     * Recupera il valore della proprietcounter.
     *
     * @return possible object is {@link BigInteger }
     *
     */
    public BigInteger getCounter() {
        return counter;
    }

    /**
     * Imposta il valore della proprietcounter.
     *
     * @param value allowed object is {@link BigInteger }
     *
     */
    public void setCounter(BigInteger value) {
        this.counter = value;
    }

    /**
     * Recupera il valore della proprietconfermata.
     *
     * @return possible object is {@link String }
     *
     */
    public String getConfermata() {
        return confermata;
    }

    /**
     * Imposta il valore della proprietconfermata.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setConfermata(String value) {
        this.confermata = value;
    }

    public String getDaRubrica() {
        return daRubrica;
    }

    public void setDaRubrica(String daRubrica) {
        this.daRubrica = daRubrica;
    }

    /**
     * Recupera il valore della proprietidAnagrafica.
     *
     * @return possible object is {@link BigInteger }
     *
     */
    public BigInteger getIdAnagrafica() {
        return idAnagrafica;
    }

    /**
     * Imposta il valore della proprietidAnagrafica.
     *
     * @param value allowed object is {@link BigInteger }
     *
     */
    public void setIdAnagrafica(BigInteger value) {
        this.idAnagrafica = value;
    }

    /**
     * Recupera il valore della propriettipoAnagrafica.
     *
     * @return possible object is {@link String }
     *
     */
    public String getTipoAnagrafica() {
        return tipoAnagrafica;
    }

    /**
     * Imposta il valore della propriettipoAnagrafica.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setTipoAnagrafica(String value) {
        this.tipoAnagrafica = value;
    }

    /**
     * Recupera il valore della proprietvarianteAnagrafica.
     *
     * @return possible object is {@link String }
     *
     */
    public String getVarianteAnagrafica() {
        return varianteAnagrafica;
    }

    /**
     * Imposta il valore della proprietvarianteAnagrafica.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setVarianteAnagrafica(String value) {
        this.varianteAnagrafica = value;
    }

    /**
     * Recupera il valore della proprietcognome.
     *
     * @return possible object is {@link String }
     *
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Imposta il valore della proprietcognome.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setCognome(String value) {
        this.cognome = value;
    }

    /**
     * Recupera il valore della proprietdenominazione.
     *
     * @return possible object is {@link String }
     *
     */
    public String getDenominazione() {
        return denominazione;
    }

    /**
     * Imposta il valore della proprietdenominazione.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setDenominazione(String value) {
        this.denominazione = value;
    }

    /**
     * Recupera il valore della proprietnome.
     *
     * @return possible object is {@link String }
     *
     */
    public String getNome() {
        return nome;
    }

    /**
     * Imposta il valore della proprietnome.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setNome(String value) {
        this.nome = value;
    }

    /**
     * Recupera il valore della proprietcodiceFiscale.
     *
     * @return possible object is {@link String }
     *
     */
    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    /**
     * Imposta il valore della proprietcodiceFiscale.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setCodiceFiscale(String value) {
        this.codiceFiscale = value;
    }

    /**
     * Recupera il valore della proprietpartitaIva.
     *
     * @return possible object is {@link String }
     *
     */
    public String getPartitaIva() {
        return partitaIva;
    }

    /**
     * Imposta il valore della proprietpartitaIva.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setPartitaIva(String value) {
        this.partitaIva = value;
    }

    /**
     * Recupera il valore della proprietflgIndividuale.
     *
     * @return possible object is {@link String }
     *
     */
    public String getFlgIndividuale() {
        return flgIndividuale;
    }

    /**
     * Imposta il valore della proprietflgIndividuale.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setFlgIndividuale(String value) {
        this.flgIndividuale = value;
    }

    /**
     * Recupera il valore della proprietdataNascita.
     *
     * @return possible object is {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getDataNascita() {
        return dataNascita;
    }

    /**
     * Imposta il valore della proprietdataNascita.
     *
     * @param value allowed object is {@link XMLGregorianCalendar }
     *
     */
    public void setDataNascita(XMLGregorianCalendar value) {
        this.dataNascita = value;
    }

    /**
     * Recupera il valore della proprietidCittadinanza.
     *
     * @return possible object is {@link BigInteger }
     *
     */
    public BigInteger getIdCittadinanza() {
        return idCittadinanza;
    }

    /**
     * Imposta il valore della proprietidCittadinanza.
     *
     * @param value allowed object is {@link BigInteger }
     *
     */
    public void setIdCittadinanza(BigInteger value) {
        this.idCittadinanza = value;
    }

    /**
     * Recupera il valore della proprietcodCittadinanza.
     *
     * @return possible object is {@link String }
     *
     */
    public String getCodCittadinanza() {
        return codCittadinanza;
    }

    /**
     * Imposta il valore della proprietcodCittadinanza.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setCodCittadinanza(String value) {
        this.codCittadinanza = value;
    }

    /**
     * Recupera il valore della proprietdesCittadinanza.
     *
     * @return possible object is {@link String }
     *
     */
    public String getDesCittadinanza() {
        return desCittadinanza;
    }

    /**
     * Imposta il valore della proprietdesCittadinanza.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setDesCittadinanza(String value) {
        this.desCittadinanza = value;
    }

    /**
     * Recupera il valore della proprietidNazionalita.
     *
     * @return possible object is {@link BigInteger }
     *
     */
    public BigInteger getIdNazionalita() {
        return idNazionalita;
    }

    /**
     * Imposta il valore della proprietidNazionalita.
     *
     * @param value allowed object is {@link BigInteger }
     *
     */
    public void setIdNazionalita(BigInteger value) {
        this.idNazionalita = value;
    }

    /**
     * Recupera il valore della proprietcodNazionalita.
     *
     * @return possible object is {@link String }
     *
     */
    public String getCodNazionalita() {
        return codNazionalita;
    }

    /**
     * Imposta il valore della proprietcodNazionalita.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setCodNazionalita(String value) {
        this.codNazionalita = value;
    }

    /**
     * Recupera il valore della proprietdesNazionalita.
     *
     * @return possible object is {@link String }
     *
     */
    public String getDesNazionalita() {
        return desNazionalita;
    }

    /**
     * Imposta il valore della proprietdesNazionalita.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setDesNazionalita(String value) {
        this.desNazionalita = value;
    }

    /**
     * Recupera il valore della proprietidComuneNascita.
     *
     * @return possible object is {@link BigInteger }
     *
     */
    public BigInteger getIdComuneNascita() {
        return idComuneNascita;
    }

    /**
     * Imposta il valore della proprietidComuneNascita.
     *
     * @param value allowed object is {@link BigInteger }
     *
     */
    public void setIdComuneNascita(BigInteger value) {
        this.idComuneNascita = value;
    }

    /**
     * Recupera il valore della proprietdesComuneNascita.
     *
     * @return possible object is {@link String }
     *
     */
    public String getDesComuneNascita() {
        return desComuneNascita;
    }

    /**
     * Imposta il valore della proprietdesComuneNascita.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setDesComuneNascita(String value) {
        this.desComuneNascita = value;
    }

    /**
     * Recupera il valore della proprietidProvinciaNascita.
     *
     * @return possible object is {@link BigInteger }
     *
     */
    public BigInteger getIdProvinciaNascita() {
        return idProvinciaNascita;
    }

    /**
     * Imposta il valore della proprietidProvinciaNascita.
     *
     * @param value allowed object is {@link BigInteger }
     *
     */
    public void setIdProvinciaNascita(BigInteger value) {
        this.idProvinciaNascita = value;
    }

    /**
     * Recupera il valore della proprietdesProvinciaNascita.
     *
     * @return possible object is {@link String }
     *
     */
    public String getDesProvinciaNascita() {
        return desProvinciaNascita;
    }

    /**
     * Imposta il valore della proprietdesProvinciaNascita.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setDesProvinciaNascita(String value) {
        this.desProvinciaNascita = value;
    }

    /**
     * Recupera il valore della proprietidStatoNascita.
     *
     * @return possible object is {@link BigInteger }
     *
     */
    public BigInteger getIdStatoNascita() {
        return idStatoNascita;
    }

    /**
     * Imposta il valore della proprietidStatoNascita.
     *
     * @param value allowed object is {@link BigInteger }
     *
     */
    public void setIdStatoNascita(BigInteger value) {
        this.idStatoNascita = value;
    }

    /**
     * Recupera il valore della proprietdesStatoNascita.
     *
     * @return possible object is {@link String }
     *
     */
    public String getDesStatoNascita() {
        return desStatoNascita;
    }

    /**
     * Imposta il valore della proprietdesStatoNascita.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setDesStatoNascita(String value) {
        this.desStatoNascita = value;
    }

    /**
     * Recupera il valore della proprietlocalitaNascita.
     *
     * @return possible object is {@link String }
     *
     */
    public String getLocalitaNascita() {
        return localitaNascita;
    }

    /**
     * Imposta il valore della proprietlocalitaNascita.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setLocalitaNascita(String value) {
        this.localitaNascita = value;
    }

    /**
     * Recupera il valore della proprietsesso.
     *
     * @return possible object is {@link String }
     *
     */
    public String getSesso() {
        return sesso;
    }

    /**
     * Imposta il valore della proprietsesso.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setSesso(String value) {
        this.sesso = value;
    }

    /**
     * Recupera il valore della proprietidTipoCollegio.
     *
     * @return possible object is {@link BigInteger }
     *
     */
    public BigInteger getIdTipoCollegio() {
        return idTipoCollegio;
    }

    /**
     * Imposta il valore della proprietidTipoCollegio.
     *
     * @param value allowed object is {@link BigInteger }
     *
     */
    public void setIdTipoCollegio(BigInteger value) {
        this.idTipoCollegio = value;
    }

    /**
     * Recupera il valore della proprietdesTipoCollegio.
     *
     * @return possible object is {@link String }
     *
     */
    public String getDesTipoCollegio() {
        return desTipoCollegio;
    }

    /**
     * Imposta il valore della proprietdesTipoCollegio.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setDesTipoCollegio(String value) {
        this.desTipoCollegio = value;
    }

    /**
     * Recupera il valore della proprietnumeroIscrizione.
     *
     * @return possible object is {@link String }
     *
     */
    public String getNumeroIscrizione() {
        return numeroIscrizione;
    }

    /**
     * Imposta il valore della proprietnumeroIscrizione.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setNumeroIscrizione(String value) {
        this.numeroIscrizione = value;
    }

    /**
     * Recupera il valore della proprietdataIscrizione.
     *
     * @return possible object is {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getDataIscrizione() {
        return dataIscrizione;
    }

    /**
     * Imposta il valore della proprietdataIscrizione.
     *
     * @param value allowed object is {@link XMLGregorianCalendar }
     *
     */
    public void setDataIscrizione(XMLGregorianCalendar value) {
        this.dataIscrizione = value;
    }

    /**
     * Recupera il valore della proprietidProvinciaIscrizione.
     *
     * @return possible object is {@link BigInteger }
     *
     */
    public BigInteger getIdProvinciaIscrizione() {
        return idProvinciaIscrizione;
    }

    /**
     * Imposta il valore della proprietidProvinciaIscrizione.
     *
     * @param value allowed object is {@link BigInteger }
     *
     */
    public void setIdProvinciaIscrizione(BigInteger value) {
        this.idProvinciaIscrizione = value;
    }

    /**
     * Recupera il valore della proprietdesProvinciaIscrizione.
     *
     * @return possible object is {@link String }
     *
     */
    public String getDesProvinciaIscrizione() {
        return desProvinciaIscrizione;
    }

    /**
     * Imposta il valore della proprietdesProvinciaIscrizione.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setDesProvinciaIscrizione(String value) {
        this.desProvinciaIscrizione = value;
    }

    public String getCodProvinciaIscrizione() {
        return codProvinciaIscrizione;
    }

    public void setCodProvinciaIscrizione(String codProvinciaIscrizione) {
        this.codProvinciaIscrizione = codProvinciaIscrizione;
    }

    /**
     * Recupera il valore della proprietidProvinciaCciaa.
     *
     * @return possible object is {@link BigInteger }
     *
     */
    public BigInteger getIdProvinciaCciaa() {
        return idProvinciaCciaa;
    }

    /**
     * Imposta il valore della proprietidProvinciaCciaa.
     *
     * @param value allowed object is {@link BigInteger }
     *
     */
    public void setIdProvinciaCciaa(BigInteger value) {
        this.idProvinciaCciaa = value;
    }

    /**
     * Recupera il valore della proprietdesProvinciaCciaa.
     *
     * @return possible object is {@link String }
     *
     */
    public String getDesProvinciaCciaa() {
        return desProvinciaCciaa;
    }

    /**
     * Imposta il valore della proprietdesProvinciaCciaa.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setDesProvinciaCciaa(String value) {
        this.desProvinciaCciaa = value;
    }

    public String getCodProvinciaCciaa() {
        return codProvinciaCciaa;
    }

    public void setCodProvinciaCciaa(String codProvinciaCciaa) {
        this.codProvinciaCciaa = codProvinciaCciaa;
    }

    /**
     * Recupera il valore della proprietflgAttesaIscrizioneRi.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public Boolean isFlgAttesaIscrizioneRi() {
        return flgAttesaIscrizioneRi;
    }

    /**
     * Imposta il valore della proprietflgAttesaIscrizioneRi.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setFlgAttesaIscrizioneRi(Boolean value) {
        this.flgAttesaIscrizioneRi = value;
    }

    /**
     * Recupera il valore della proprietflgObbligoIscrizioneRi.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public Boolean isFlgObbligoIscrizioneRi() {
        return flgObbligoIscrizioneRi;
    }

    /**
     * Imposta il valore della proprietflgObbligoIscrizioneRi.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setFlgObbligoIscrizioneRi(Boolean value) {
        this.flgObbligoIscrizioneRi = value;
    }

    /**
     * Recupera il valore della proprietdataIscrizioneRi.
     *
     * @return possible object is {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getDataIscrizioneRi() {
        return dataIscrizioneRi;
    }

    /**
     * Imposta il valore della proprietdataIscrizioneRi.
     *
     * @param value allowed object is {@link XMLGregorianCalendar }
     *
     */
    public void setDataIscrizioneRi(XMLGregorianCalendar value) {
        this.dataIscrizioneRi = value;
    }

    /**
     * Recupera il valore della proprietnIscrizioneRi.
     *
     * @return possible object is {@link String }
     *
     */
    public String getNIscrizioneRi() {
        return nIscrizioneRi;
    }

    /**
     * Imposta il valore della proprietnIscrizioneRi.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setNIscrizioneRi(String value) {
        this.nIscrizioneRi = value;
    }

    /**
     * Recupera il valore della proprietflgAttesaIscrizioneRea.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public Boolean isFlgAttesaIscrizioneRea() {
        return flgAttesaIscrizioneRea;
    }

    /**
     * Imposta il valore della proprietflgAttesaIscrizioneRea.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setFlgAttesaIscrizioneRea(Boolean value) {
        this.flgAttesaIscrizioneRea = value;
    }

    /**
     * Recupera il valore della proprietdataIscrizioneRea.
     *
     * @return possible object is {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getDataIscrizioneRea() {
        return dataIscrizioneRea;
    }

    /**
     * Imposta il valore della proprietdataIscrizioneRea.
     *
     * @param value allowed object is {@link XMLGregorianCalendar }
     *
     */
    public void setDataIscrizioneRea(XMLGregorianCalendar value) {
        this.dataIscrizioneRea = value;
    }

    /**
     * Recupera il valore della proprietnIscrizioneRea.
     *
     * @return possible object is {@link String }
     *
     */
    public String getNIscrizioneRea() {
        return nIscrizioneRea;
    }

    /**
     * Imposta il valore della proprietnIscrizioneRea.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setNIscrizioneRea(String value) {
        this.nIscrizioneRea = value;
    }

    /**
     * Recupera il valore della proprietidFormaGiuridica.
     *
     * @return possible object is {@link BigInteger }
     *
     */
    public BigInteger getIdFormaGiuridica() {
        return idFormaGiuridica;
    }

    /**
     * Imposta il valore della proprietidFormaGiuridica.
     *
     * @param value allowed object is {@link BigInteger }
     *
     */
    public void setIdFormaGiuridica(BigInteger value) {
        this.idFormaGiuridica = value;
    }

    /**
     * Recupera il valore della proprietdesFormaGiuridica.
     *
     * @return possible object is {@link String }
     *
     */
    public String getDesFormaGiuridica() {
        return desFormaGiuridica;
    }

    /**
     * Imposta il valore della proprietdesFormaGiuridica.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setDesFormaGiuridica(String value) {
        this.desFormaGiuridica = value;
    }

    /**
     * Recupera il valore della proprietrecapiti.
     *
     * @return possible object is {@link Recapiti }
     *
     */
    public Recapiti getRecapiti() {
        return recapiti;
    }

    /**
     * Imposta il valore della proprietrecapiti.
     *
     * @param value allowed object is {@link Recapiti }
     *
     */
    public void setRecapiti(Recapiti value) {
        this.recapiti = value;
    }
}
