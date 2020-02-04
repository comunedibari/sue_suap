
package it.gov.impresainungiorno.schema.base;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Anagrafica complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Anagrafica">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cognome" type="{http://www.impresainungiorno.gov.it/schema/base}StringaLunga"/>
 *         &lt;element name="nome" type="{http://www.impresainungiorno.gov.it/schema/base}StringaLunga"/>
 *         &lt;element name="codice-fiscale" type="{http://www.impresainungiorno.gov.it/schema/base}CodiceFiscalePersona"/>
 *         &lt;element name="nazionalita" type="{http://www.impresainungiorno.gov.it/schema/base}Stato" minOccurs="0"/>
 *         &lt;element name="partita-iva" type="{http://www.impresainungiorno.gov.it/schema/base}PartitaIVA" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Anagrafica", propOrder = {
    "cognome",
    "nome",
    "codiceFiscale",
    "nazionalita",
    "partitaIva"
})
@XmlSeeAlso({
    UtentePortale.class,
    AnagraficaCompleta.class,
    AnagraficaRappresentante.class
})
public class Anagrafica {

    @XmlElement(required = true)
    protected String cognome;
    @XmlElement(required = true)
    protected String nome;
    @XmlElement(name = "codice-fiscale", required = true)
    protected String codiceFiscale;
    protected Stato nazionalita;
    @XmlElement(name = "partita-iva")
    protected String partitaIva;

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
     * Gets the value of the nazionalita property.
     * 
     * @return
     *     possible object is
     *     {@link Stato }
     *     
     */
    public Stato getNazionalita() {
        return nazionalita;
    }

    /**
     * Sets the value of the nazionalita property.
     * 
     * @param value
     *     allowed object is
     *     {@link Stato }
     *     
     */
    public void setNazionalita(Stato value) {
        this.nazionalita = value;
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

}
