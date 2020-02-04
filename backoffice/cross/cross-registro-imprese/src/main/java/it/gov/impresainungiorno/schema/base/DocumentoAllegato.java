
package it.gov.impresainungiorno.schema.base;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DocumentoAllegato complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DocumentoAllegato">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nome" type="{http://www.impresainungiorno.gov.it/schema/base}Stringa"/>
 *         &lt;element name="dimensione" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/>
 *         &lt;element name="uri" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="tipologia" type="{http://www.impresainungiorno.gov.it/schema/base}Stringa" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="mime" type="{http://www.impresainungiorno.gov.it/schema/base}Stringa" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentoAllegato", propOrder = {
    "nome",
    "dimensione",
    "uri",
    "tipologia"
})
public class DocumentoAllegato {

    @XmlElement(required = true)
    protected String nome;
    @XmlElement(required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger dimensione;
    @XmlSchemaType(name = "anyURI")
    protected String uri;
    protected String tipologia;
    @XmlAttribute(name = "mime")
    protected String mime;

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
     * Gets the value of the dimensione property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDimensione() {
        return dimensione;
    }

    /**
     * Sets the value of the dimensione property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDimensione(BigInteger value) {
        this.dimensione = value;
    }

    /**
     * Gets the value of the uri property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUri() {
        return uri;
    }

    /**
     * Sets the value of the uri property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUri(String value) {
        this.uri = value;
    }

    /**
     * Gets the value of the tipologia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipologia() {
        return tipologia;
    }

    /**
     * Sets the value of the tipologia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipologia(String value) {
        this.tipologia = value;
    }

    /**
     * Gets the value of the mime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMime() {
        return mime;
    }

    /**
     * Sets the value of the mime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMime(String value) {
        this.mime = value;
    }

}
