//
// Questo file � stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andr� persa durante la ricompilazione dello schema di origine. 
// Generato il: 2024.04.16 alle 09:52:47 AM CEST 
//


package it.wego.cross.xml;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per contributiCosto complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="contributiCosto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dirittiSegreteriaGenerali" type="{http://www.wego.it/cross}dirittiSegreteriaGenerali" minOccurs="0"/>
 *         &lt;element name="dirittiSegreteriaGaranzie" type="{http://www.wego.it/cross}dirittiSegreteriaGaranzie" minOccurs="0"/>
 *         &lt;element name="dirittiSegreteriaRate" type="{http://www.wego.it/cross}dirittiSegreteriaRate" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "contributiCosto", propOrder = {
    "dirittiSegreteriaGenerali",
    "dirittiSegreteriaGaranzie",
    "dirittiSegreteriaRate"
})
public class ContributiCosto {

    @XmlElement(name = "dirittiSegreteriaGenerali")
    protected DirittiSegreteriaGenerali dirittiSegreteriaGenerali;
    @XmlElement(name = "dirittiSegreteriaGaranzie")
    protected DirittiSegreteriaGaranzie dirittiSegreteriaGaranzie;
    @XmlElement(name = "dirittiSegreteriaRate")
    protected DirittiSegreteriaRate dirittiSegreteriaRate;

    /**
     * Recupera il valore della propriet� dirittiSegreteriaGenerali.
     * 
     * @return
     *     possible object is
     *     {@link DirittiSegreteriaGenerali }
     *     
     */
    public DirittiSegreteriaGenerali getDirittiSegreteriaGenerali() {
        return dirittiSegreteriaGenerali;
    }

    /**
     * Imposta il valore della propriet� dirittiSegreteriaGenerali.
     * 
     * @param value
     *     allowed object is
     *     {@link DirittiSegreteriaGenerali }
     *     
     */
    public void setDirittiSegreteriaGenerali(DirittiSegreteriaGenerali value) {
        this.dirittiSegreteriaGenerali = value;
    }

    /**
     * Recupera il valore della propriet� dirittiSegreteriaGaranzie.
     * 
     * @return
     *     possible object is
     *     {@link DirittiSegreteriaGaranzie }
     *     
     */
    public DirittiSegreteriaGaranzie getDirittiSegreteriaGaranzie() {
        return dirittiSegreteriaGaranzie;
    }

    /**
     * Imposta il valore della propriet� dirittiSegreteriaGaranzie.
     * 
     * @param value
     *     allowed object is
     *     {@link DirittiSegreteriaGaranzie }
     *     
     */
    public void setDirittiSegreteriaGaranzie(DirittiSegreteriaGaranzie value) {
        this.dirittiSegreteriaGaranzie = value;
    }

    /**
     * Recupera il valore della propriet� dirittiSegreteriaRate.
     * 
     * @return
     *     possible object is
     *     {@link DirittiSegreteriaRate }
     *     
     */
    public DirittiSegreteriaRate getDirittiSegreteriaRate() {
        return dirittiSegreteriaRate;
    }

    /**
     * Imposta il valore della propriet� dirittiSegreteriaRate.
     * 
     * @param value
     *     allowed object is
     *     {@link DirittiSegreteriaRate }
     *     
     */
    public void setDirittiSegreteriaRate(DirittiSegreteriaRate value) {
        this.dirittiSegreteriaRate = value;
    }

}
