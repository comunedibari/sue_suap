
package it.wego.cross.webservices.client.clear.stubs;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per codice_pratica_simo complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="codice_pratica_simo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="prot_num_simo" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="prot_anno_simo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="prot_reg_simo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="prot_aoo_simo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "codice_pratica_simo", propOrder = {
    "protNumSimo",
    "protAnnoSimo",
    "protRegSimo",
    "protAooSimo"
})
public class CodicePraticaSimo {

    @XmlElement(name = "prot_num_simo", required = true)
    protected BigInteger protNumSimo;
    @XmlElement(name = "prot_anno_simo", required = true)
    protected String protAnnoSimo;
    @XmlElement(name = "prot_reg_simo", required = true)
    protected String protRegSimo;
    @XmlElement(name = "prot_aoo_simo", required = true)
    protected String protAooSimo;

    /**
     * Recupera il valore della proprietà protNumSimo.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getProtNumSimo() {
        return protNumSimo;
    }

    /**
     * Imposta il valore della proprietà protNumSimo.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setProtNumSimo(BigInteger value) {
        this.protNumSimo = value;
    }

    /**
     * Recupera il valore della proprietà protAnnoSimo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProtAnnoSimo() {
        return protAnnoSimo;
    }

    /**
     * Imposta il valore della proprietà protAnnoSimo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProtAnnoSimo(String value) {
        this.protAnnoSimo = value;
    }

    /**
     * Recupera il valore della proprietà protRegSimo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProtRegSimo() {
        return protRegSimo;
    }

    /**
     * Imposta il valore della proprietà protRegSimo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProtRegSimo(String value) {
        this.protRegSimo = value;
    }

    /**
     * Recupera il valore della proprietà protAooSimo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProtAooSimo() {
        return protAooSimo;
    }

    /**
     * Imposta il valore della proprietà protAooSimo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProtAooSimo(String value) {
        this.protAooSimo = value;
    }

}
