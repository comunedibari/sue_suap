
package it.wego.cross.webservices.client.clear.stubs;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per codice_protocollo complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="codice_protocollo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="prot_anno" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="prot_num" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="prot_reg" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="prot_aoo" type="{http://www.simo.org}codice_protocollo_prot_aooType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "codice_protocollo", propOrder = {
    "protAnno",
    "protNum",
    "protReg",
    "protAoo"
})
public class CodiceProtocollo {

    @XmlElement(name = "prot_anno", required = true)
    protected String protAnno;
    @XmlElement(name = "prot_num", required = true)
    protected BigInteger protNum;
    @XmlElement(name = "prot_reg", required = true)
    protected String protReg;
    @XmlElement(name = "prot_aoo", required = true)
    protected String protAoo;

    /**
     * Recupera il valore della proprietà protAnno.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProtAnno() {
        return protAnno;
    }

    /**
     * Imposta il valore della proprietà protAnno.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProtAnno(String value) {
        this.protAnno = value;
    }

    /**
     * Recupera il valore della proprietà protNum.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getProtNum() {
        return protNum;
    }

    /**
     * Imposta il valore della proprietà protNum.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setProtNum(BigInteger value) {
        this.protNum = value;
    }

    /**
     * Recupera il valore della proprietà protReg.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProtReg() {
        return protReg;
    }

    /**
     * Imposta il valore della proprietà protReg.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProtReg(String value) {
        this.protReg = value;
    }

    /**
     * Recupera il valore della proprietà protAoo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProtAoo() {
        return protAoo;
    }

    /**
     * Imposta il valore della proprietà protAoo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProtAoo(String value) {
        this.protAoo = value;
    }

}
