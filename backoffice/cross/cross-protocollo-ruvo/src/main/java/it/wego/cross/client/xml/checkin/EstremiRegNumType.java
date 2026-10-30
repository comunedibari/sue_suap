//
// Questo file stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrpersa durante la ricompilazione dello schema di origine. 
// Generato il: PM.03.05 alle 07:37:39 PM CET 
//


package it.wego.cross.client.xml.checkin;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per EstremiRegNumType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="EstremiRegNumType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="CategoriaReg" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="PG"/>
 *               &lt;enumeration value="PP"/>
 *               &lt;enumeration value="R"/>
 *               &lt;enumeration value="E"/>
 *               &lt;enumeration value="A"/>
 *               &lt;enumeration value="I"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="SiglaReg" type="{}SiglaRegNumType" minOccurs="0"/>
 *         &lt;element name="AnnoReg" type="{}AnnoType"/>
 *         &lt;element name="NumReg" type="{}NumRegType"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EstremiRegNumType", propOrder = {

})
public class EstremiRegNumType {

    @XmlElement(name = "CategoriaReg")
    protected String categoriaReg;
    @XmlElement(name = "SiglaReg")
    protected String siglaReg;
    @XmlElement(name = "AnnoReg")
    protected int annoReg;
    @XmlElement(name = "NumReg")
    protected int numReg;

    /**
     * Recupera il valore della proprietcategoriaReg.
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
     * Imposta il valore della proprietcategoriaReg.
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
     * Recupera il valore della proprietsiglaReg.
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
     * Imposta il valore della proprietsiglaReg.
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
     * Recupera il valore della proprietannoReg.
     * 
     */
    public int getAnnoReg() {
        return annoReg;
    }

    /**
     * Imposta il valore della proprietannoReg.
     * 
     */
    public void setAnnoReg(int value) {
        this.annoReg = value;
    }

    /**
     * Recupera il valore della proprietnumReg.
     * 
     */
    public int getNumReg() {
        return numReg;
    }

    /**
     * Imposta il valore della proprietnumReg.
     * 
     */
    public void setNumReg(int value) {
        this.numReg = value;
    }

}
