//
// Questo file è stato generato dall'Eclipse Implementation of JAXB, v3.0.0 
// Vedere https://eclipse-ee4j.github.io/jaxb-ri 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.11.16 alle 06:34:12 PM CET 
//


package it.wego.auriga.xml.allegati;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per EstremiRegNumType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="EstremiRegNumType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;all&gt;
 *         &lt;element name="CategoriaReg" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;enumeration value="PG"/&gt;
 *               &lt;enumeration value="PP"/&gt;
 *               &lt;enumeration value="R"/&gt;
 *               &lt;enumeration value="E"/&gt;
 *               &lt;enumeration value="A"/&gt;
 *               &lt;enumeration value="I"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="SiglaReg" type="{}SiglaRegNumType" minOccurs="0"/&gt;
 *         &lt;element name="AnnoReg" type="{}AnnoType"/&gt;
 *         &lt;element name="NumReg" type="{}NumRegType"/&gt;
 *       &lt;/all&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EstremiRegNumType", propOrder = {

})
public class EstremiRegNumType
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElement(name = "CategoriaReg")
    protected String categoriaReg;
    @XmlElement(name = "SiglaReg")
    protected String siglaReg;
    @XmlElement(name = "AnnoReg")
    @XmlSchemaType(name = "integer")
    protected int annoReg;
    @XmlElement(name = "NumReg")
    @XmlSchemaType(name = "integer")
    protected int numReg;

    /**
     * Recupera il valore della proprietà categoriaReg.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCategoriaReg() {
        return categoriaReg;
    }

    /**
     * Imposta il valore della proprietà categoriaReg.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCategoriaReg(String value) {
        this.categoriaReg = value;
    }

    /**
     * Recupera il valore della proprietà siglaReg.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiglaReg() {
        return siglaReg;
    }

    /**
     * Imposta il valore della proprietà siglaReg.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiglaReg(String value) {
        this.siglaReg = value;
    }

    /**
     * Recupera il valore della proprietà annoReg.
     * 
     */
    public int getAnnoReg() {
        return annoReg;
    }

    /**
     * Imposta il valore della proprietà annoReg.
     * 
     */
    public void setAnnoReg(int value) {
        this.annoReg = value;
    }

    /**
     * Recupera il valore della proprietà numReg.
     * 
     */
    public int getNumReg() {
        return numReg;
    }

    /**
     * Imposta il valore della proprietà numReg.
     * 
     */
    public void setNumReg(int value) {
        this.numReg = value;
    }

}
