//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.02.25 alle 05:44:42 PM CET 
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
    "confermato"
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

    /**
     * Recupera il valore della proprietà counter.
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
     * Imposta il valore della proprietà counter.
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
     * Recupera il valore della proprietà idImmobile.
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
     * Imposta il valore della proprietà idImmobile.
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
     * Recupera il valore della proprietà codImmobile.
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
     * Imposta il valore della proprietà codImmobile.
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
     * Recupera il valore della proprietà idTipoSistemaCatastale.
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
     * Imposta il valore della proprietà idTipoSistemaCatastale.
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
     * Recupera il valore della proprietà desTipoSistemaCatastale.
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
     * Imposta il valore della proprietà desTipoSistemaCatastale.
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
     * Recupera il valore della proprietà sezione.
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
     * Imposta il valore della proprietà sezione.
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
     * Recupera il valore della proprietà idTipoUnita.
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
     * Imposta il valore della proprietà idTipoUnita.
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
     * Recupera il valore della proprietà desTipoUnita.
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
     * Imposta il valore della proprietà desTipoUnita.
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
     * Recupera il valore della proprietà foglio.
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
     * Imposta il valore della proprietà foglio.
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
     * Recupera il valore della proprietà mappale.
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
     * Imposta il valore della proprietà mappale.
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
     * Recupera il valore della proprietà idTipoParticella.
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
     * Imposta il valore della proprietà idTipoParticella.
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
     * Recupera il valore della proprietà desTipoParticella.
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
     * Imposta il valore della proprietà desTipoParticella.
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
     * Recupera il valore della proprietà estensioneParticella.
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
     * Imposta il valore della proprietà estensioneParticella.
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
     * Recupera il valore della proprietà subalterno.
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
     * Imposta il valore della proprietà subalterno.
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
     * Recupera il valore della proprietà idComune.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public String getComuneCensuario() {
        return comuneCensuario;
    }

    /**
     * Imposta il valore della proprietà idComune.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setComuneCensuario(String value) {
        this.comuneCensuario = value;
    }

    public String getConfermato() {
        return confermato;
    }

    public void setConfermato(String confermato) {
        this.confermato = confermato;
    }

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
    
    
}
