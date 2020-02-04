//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.03.15 at 06:24:50 PM CET 
//


package it.wego.cross.xsd.documenti;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Azienda complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Azienda">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Nome" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RagSociale" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CodFiscale" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PartitaIva" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="NumRea" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="NaturaGiuridica" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Recapito" type="{}Recapito"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Azienda", propOrder = {
    "nome",
    "ragSociale",
    "codFiscale",
    "partitaIva",
    "numRea",
    "naturaGiuridica",
    "recapito"
})
public class Azienda {

    @XmlElement(name = "Nome", required = true)
    protected String nome;
    @XmlElement(name = "RagSociale", required = true)
    protected String ragSociale;
    @XmlElement(name = "CodFiscale", required = true)
    protected String codFiscale;
    @XmlElement(name = "PartitaIva", required = true)
    protected String partitaIva;
    @XmlElement(name = "NumRea", required = true)
    protected String numRea;
    @XmlElement(name = "NaturaGiuridica", required = true)
    protected String naturaGiuridica;
    @XmlElement(name = "Recapito", required = true)
    protected Recapito recapito;

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
     * Gets the value of the ragSociale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRagSociale() {
        return ragSociale;
    }

    /**
     * Sets the value of the ragSociale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRagSociale(String value) {
        this.ragSociale = value;
    }

    /**
     * Gets the value of the codFiscale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodFiscale() {
        return codFiscale;
    }

    /**
     * Sets the value of the codFiscale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodFiscale(String value) {
        this.codFiscale = value;
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
     * Gets the value of the numRea property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumRea() {
        return numRea;
    }

    /**
     * Sets the value of the numRea property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumRea(String value) {
        this.numRea = value;
    }

    /**
     * Gets the value of the naturaGiuridica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNaturaGiuridica() {
        return naturaGiuridica;
    }

    /**
     * Sets the value of the naturaGiuridica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNaturaGiuridica(String value) {
        this.naturaGiuridica = value;
    }

    /**
     * Gets the value of the recapito property.
     * 
     * @return
     *     possible object is
     *     {@link Recapito }
     *     
     */
    public Recapito getRecapito() {
        return recapito;
    }

    /**
     * Sets the value of the recapito property.
     * 
     * @param value
     *     allowed object is
     *     {@link Recapito }
     *     
     */
    public void setRecapito(Recapito value) {
        this.recapito = value;
    }

}
