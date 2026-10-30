package it.wego.auriga.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ComuneItalianoType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ComuneItalianoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="CodISTATComune">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="[0-9]{6}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="NomeComune" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ComuneItalianoType", propOrder = {
    "codISTATComune",
    "nomeComune"
})
public class ComuneItalianoType {

    @XmlElement(name = "CodISTATComune")
    protected String codISTATComune;
    @XmlElement(name = "NomeComune")
    protected String nomeComune;

    /**
     * Recupera il valore della propriet� codISTATComune.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodISTATComune() {
        return codISTATComune;
    }

    /**
     * Imposta il valore della propriet� codISTATComune.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodISTATComune(String value) {
        this.codISTATComune = value;
    }

    /**
     * Recupera il valore della propriet� nomeComune.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeComune() {
        return nomeComune;
    }

    /**
     * Imposta il valore della propriet� nomeComune.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeComune(String value) {
        this.nomeComune = value;
    }

}
