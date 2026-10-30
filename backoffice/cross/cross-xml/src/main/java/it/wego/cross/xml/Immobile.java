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
 *         &lt;element name="cod_immobile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
 *         &lt;element name="categoria" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="comune_censuario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="confermato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="latitudine" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="longitudine" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id_comune" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="des_comune" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id_provincia" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="des_provincia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "codImmobile",
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
    "categoria",
    "comuneCensuario",
    "confermato",
    "latitudine",
    "longitudine",
    "idComune",
    "desComune",
    "idProvincia",
    "desProvincia"
})
public class Immobile {

    @XmlElement(required = true)
    protected BigInteger counter;
    @XmlElement(name = "id_immobile")
    protected BigInteger idImmobile;
    @XmlElement(name = "cod_immobile")
    protected String codImmobile;
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
    protected String categoria;
    @XmlElement(name = "comune_censuario")
    protected String comuneCensuario;
    protected String confermato;
    protected String latitudine;
    protected String longitudine;
    @XmlElement(name = "id_comune")
    protected BigInteger idComune;
    @XmlElement(name = "des_comune")
    protected String desComune;
    @XmlElement(name = "id_provincia")
    protected BigInteger idProvincia;
    @XmlElement(name = "des_provincia")
    protected String desProvincia;

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
     * Recupera il valore della propriet� codImmobile.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodImmobile() {
        return codImmobile;
    }

    /**
     * Imposta il valore della propriet� codImmobile.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodImmobile(String value) {
        this.codImmobile = value;
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
     * Recupera il valore della propriet� categoria.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Imposta il valore della propriet� categoria.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCategoria(String value) {
        this.categoria = value;
    }

    /**
     * Recupera il valore della propriet� comuneCensuario.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComuneCensuario() {
        return comuneCensuario;
    }

    /**
     * Imposta il valore della propriet� comuneCensuario.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComuneCensuario(String value) {
        this.comuneCensuario = value;
    }

    /**
     * Recupera il valore della propriet� confermato.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfermato() {
        return confermato;
    }

    /**
     * Imposta il valore della propriet� confermato.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfermato(String value) {
        this.confermato = value;
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

}
