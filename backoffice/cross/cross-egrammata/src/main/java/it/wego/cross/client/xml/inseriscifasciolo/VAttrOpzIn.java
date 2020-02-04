//
// Questo filestato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andr� persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.07.24 alle 10:59:37 AM CEST 
//


package it.wego.cross.client.xml.inseriscifasciolo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}ID_ATTR"/>
 *         &lt;element ref="{}VALORE"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "idattr",
    "valore"
})
@XmlRootElement(name = "vAttrOpzIn")
public class VAttrOpzIn {

    @XmlElement(name = "ID_ATTR", required = true)
    protected String idattr;
    @XmlElement(name = "VALORE", required = true)
    protected String valore;

    /**
     * Recupera il valore della propriet� idattr.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDATTR() {
        return idattr;
    }

    /**
     * Imposta il valore della propriet� idattr.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDATTR(String value) {
        this.idattr = value;
    }

    /**
     * Recupera il valore della propriet� valore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVALORE() {
        return valore;
    }

    /**
     * Imposta il valore della propriet� valore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVALORE(String value) {
        this.valore = value;
    }

}
