//
// Questo file  stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andr persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.02.27 alle 01:48:58 PM CET 
//


package it.wego.cross.xmlold;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per immobile complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="immobile">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="counter" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="id_immobile" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="id_tipo_sistema_catastale" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="des_tipo_sistema_catastale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sezione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id_tipo_unita" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="des_tipo_unita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="foglio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mappale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id_tipo_particella" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="des_tipo_particella" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="estensione_particella" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="subalterno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="latitudine" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="longitudine" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id_comune" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="des_comune" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="localita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id_provincia" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="des_provincia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id_dug" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="indirizzo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="civico" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="altre_informazioni_indirizzo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cap" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codice_via" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codice_civico" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="interno_numero" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="interno_lettera" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="interno_scala" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lettera" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="colore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "immobile", propOrder = {
    "counter",
    "idImmobile",
    "idTipoSistemaCatastale",
    "desTipoSistemaCatastale",
    "sezione",
    "idTipoUnita",
    "desTipoUnita",
    "foglio",
    "mappale",
    "idTipoParticella",
    "desTipoParticella",
    "estensioneParticella",
    "subalterno",
    "latitudine",
    "longitudine",
    "idComune",
    "desComune",
    "localita",
    "idProvincia",
    "desProvincia",
    "idDug",
    "indirizzo",
    "civico",
    "altreInformazioniIndirizzo",
    "cap",
    "codiceVia",
    "codiceCivico",
    "internoNumero",
    "internoLettera",
    "internoScala",
    "lettera",
    "colore"
})
public class Immobile {

    @XmlElement(required = true)
    protected BigInteger counter;
    @XmlElement(name = "id_immobile")
    protected BigInteger idImmobile;
    @XmlElement(name = "id_tipo_sistema_catastale")
    protected BigInteger idTipoSistemaCatastale;
    @XmlElement(name = "des_tipo_sistema_catastale")
    protected String desTipoSistemaCatastale;
    protected String sezione;
    @XmlElement(name = "id_tipo_unita")
    protected BigInteger idTipoUnita;
    @XmlElement(name = "des_tipo_unita")
    protected String desTipoUnita;
    protected String foglio;
    protected String mappale;
    @XmlElement(name = "id_tipo_particella")
    protected BigInteger idTipoParticella;
    @XmlElement(name = "des_tipo_particella")
    protected String desTipoParticella;
    @XmlElement(name = "estensione_particella")
    protected String estensioneParticella;
    protected String subalterno;
    protected String latitudine;
    protected String longitudine;
    @XmlElement(name = "id_comune")
    protected BigInteger idComune;
    @XmlElement(name = "des_comune")
    protected String desComune;
    protected String localita;
    @XmlElement(name = "id_provincia")
    protected BigInteger idProvincia;
    @XmlElement(name = "des_provincia")
    protected String desProvincia;
    @XmlElement(name = "id_dug")
    protected BigInteger idDug;
    protected String indirizzo;
    protected String civico;
    @XmlElement(name = "altre_informazioni_indirizzo")
    protected String altreInformazioniIndirizzo;
    protected String cap;
    @XmlElement(name = "codice_via")
    protected String codiceVia;
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

    /**
     * Recupera il valore della propriet� counter.
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
     * Imposta il valore della propriet� counter.
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
     * Recupera il valore della propriet� idImmobile.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdImmobile() {
        return idImmobile;
    }

    /**
     * Imposta il valore della propriet� idImmobile.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdImmobile(BigInteger value) {
        this.idImmobile = value;
    }

    /**
     * Recupera il valore della propriet� idTipoSistemaCatastale.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdTipoSistemaCatastale() {
        return idTipoSistemaCatastale;
    }

    /**
     * Imposta il valore della propriet� idTipoSistemaCatastale.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdTipoSistemaCatastale(BigInteger value) {
        this.idTipoSistemaCatastale = value;
    }

    /**
     * Recupera il valore della propriet� desTipoSistemaCatastale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesTipoSistemaCatastale() {
        return desTipoSistemaCatastale;
    }

    /**
     * Imposta il valore della propriet� desTipoSistemaCatastale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesTipoSistemaCatastale(String value) {
        this.desTipoSistemaCatastale = value;
    }

    /**
     * Recupera il valore della propriet� sezione.
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
     * Imposta il valore della propriet� sezione.
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
     * Recupera il valore della propriet� idTipoUnita.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdTipoUnita() {
        return idTipoUnita;
    }

    /**
     * Imposta il valore della propriet� idTipoUnita.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdTipoUnita(BigInteger value) {
        this.idTipoUnita = value;
    }

    /**
     * Recupera il valore della propriet� desTipoUnita.
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
     * Imposta il valore della propriet� desTipoUnita.
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
     * Recupera il valore della propriet� foglio.
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
     * Imposta il valore della propriet� foglio.
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
     * Recupera il valore della propriet� mappale.
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
     * Imposta il valore della propriet� mappale.
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
     * Recupera il valore della propriet� idTipoParticella.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdTipoParticella() {
        return idTipoParticella;
    }

    /**
     * Imposta il valore della propriet� idTipoParticella.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdTipoParticella(BigInteger value) {
        this.idTipoParticella = value;
    }

    /**
     * Recupera il valore della propriet� desTipoParticella.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesTipoParticella() {
        return desTipoParticella;
    }

    /**
     * Imposta il valore della propriet� desTipoParticella.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesTipoParticella(String value) {
        this.desTipoParticella = value;
    }

    /**
     * Recupera il valore della propriet� estensioneParticella.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEstensioneParticella() {
        return estensioneParticella;
    }

    /**
     * Imposta il valore della propriet� estensioneParticella.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEstensioneParticella(String value) {
        this.estensioneParticella = value;
    }

    /**
     * Recupera il valore della propriet� subalterno.
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
     * Imposta il valore della propriet� subalterno.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubalterno(String value) {
        this.subalterno = value;
    }

    /**
     * Recupera il valore della propriet� latitudine.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLatitudine() {
        return latitudine;
    }

    /**
     * Imposta il valore della propriet� latitudine.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLatitudine(String value) {
        this.latitudine = value;
    }

    /**
     * Recupera il valore della propriet� longitudine.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLongitudine() {
        return longitudine;
    }

    /**
     * Imposta il valore della propriet� longitudine.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLongitudine(String value) {
        this.longitudine = value;
    }

    /**
     * Recupera il valore della propriet� idComune.
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
     * Imposta il valore della propriet� idComune.
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
     * Recupera il valore della propriet� desComune.
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
     * Imposta il valore della propriet� desComune.
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
     * Recupera il valore della propriet� localita.
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
     * Imposta il valore della propriet� localita.
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
     * Recupera il valore della propriet� idProvincia.
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
     * Imposta il valore della propriet� idProvincia.
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
     * Recupera il valore della propriet� desProvincia.
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
     * Imposta il valore della propriet� desProvincia.
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
     * Recupera il valore della propriet� idDug.
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
     * Imposta il valore della propriet� idDug.
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
     * Recupera il valore della propriet� civico.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCivico() {
        return civico;
    }

    /**
     * Imposta il valore della propriet� civico.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCivico(String value) {
        this.civico = value;
    }

    /**
     * Recupera il valore della propriet� altreInformazioniIndirizzo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAltreInformazioniIndirizzo() {
        return altreInformazioniIndirizzo;
    }

    /**
     * Imposta il valore della propriet� altreInformazioniIndirizzo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAltreInformazioniIndirizzo(String value) {
        this.altreInformazioniIndirizzo = value;
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
     * Recupera il valore della propriet� codiceVia.
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
     * Imposta il valore della propriet� codiceVia.
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
     * Recupera il valore della propriet� codiceCivico.
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
     * Imposta il valore della propriet� codiceCivico.
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
     * Recupera il valore della propriet� internoNumero.
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
     * Imposta il valore della propriet� internoNumero.
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
     * Recupera il valore della propriet� internoLettera.
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
     * Imposta il valore della propriet� internoLettera.
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
     * Recupera il valore della propriet� internoScala.
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
     * Imposta il valore della propriet� internoScala.
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
     * Recupera il valore della propriet� lettera.
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
     * Imposta il valore della propriet� lettera.
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
     * Recupera il valore della propriet� colore.
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
     * Imposta il valore della propriet� colore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColore(String value) {
        this.colore = value;
    }

}
