
package it.wego.cross.webservices.client.clear.stubs;

import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per codice_protocollo_precedente complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="codice_protocollo_precedente">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="prot_anno" type="{http://www.simo.org}codice_protocollo_precedente_prot_annoType" minOccurs="0"/>
 *         &lt;element name="prot_num" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="prot_reg" type="{http://www.simo.org}codice_protocollo_precedente_prot_regType" minOccurs="0"/>
 *         &lt;element name="prot_aoo" type="{http://www.simo.org}codice_protocollo_precedente_prot_aooType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "codice_protocollo_precedente", propOrder = {
    "protAnno",
    "protNum",
    "protReg",
    "protAoo"
})
public class CodiceProtocolloPrecedente {

    @XmlElementRef(name = "prot_anno", namespace = "http://www.simo.org", type = JAXBElement.class, required = false)
    protected JAXBElement<String> protAnno;
    @XmlElement(name = "prot_num", required = true, nillable = true)
    protected BigInteger protNum;
    @XmlElementRef(name = "prot_reg", namespace = "http://www.simo.org", type = JAXBElement.class, required = false)
    protected JAXBElement<String> protReg;
    @XmlElementRef(name = "prot_aoo", namespace = "http://www.simo.org", type = JAXBElement.class, required = false)
    protected JAXBElement<String> protAoo;

    /**
     * Recupera il valore della proprietà protAnno.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getProtAnno() {
        return protAnno;
    }

    /**
     * Imposta il valore della proprietà protAnno.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setProtAnno(JAXBElement<String> value) {
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
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getProtReg() {
        return protReg;
    }

    /**
     * Imposta il valore della proprietà protReg.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setProtReg(JAXBElement<String> value) {
        this.protReg = value;
    }

    /**
     * Recupera il valore della proprietà protAoo.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getProtAoo() {
        return protAoo;
    }

    /**
     * Imposta il valore della proprietà protAoo.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setProtAoo(JAXBElement<String> value) {
        this.protAoo = value;
    }

}
