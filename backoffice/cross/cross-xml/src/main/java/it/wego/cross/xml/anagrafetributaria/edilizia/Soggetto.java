//
// Questo file e' stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andra' persa durante la ricompilazione dello schema di origine. 
// Generato il: 2013.02.01 alle 04:09:31 PM CET 
//


package it.wego.cross.xml.anagrafetributaria.edilizia;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per soggetto complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="soggetto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codiceFiscale">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="16"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;choice>
 *           &lt;element name="soggettoFisico" type="{http://www.wego.it/cross}soggettoFisico"/>
 *           &lt;element name="soggettoGiuridico" type="{http://www.wego.it/cross}soggettoGiuridico"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "soggetto", propOrder = {
    "codiceFiscale",
    "soggettoFisico",
    "soggettoGiuridico"
})
public class Soggetto {

    @XmlElement(required = true)
    protected String codiceFiscale;
    protected SoggettoFisico soggettoFisico;
    protected SoggettoGiuridico soggettoGiuridico;

    /**
     * Recupera il valore della proprieta' codiceFiscale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    /**
     * Imposta il valore della proprieta' codiceFiscale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceFiscale(String value) {
        this.codiceFiscale = value;
    }

    /**
     * Recupera il valore della proprieta' soggettoFisico.
     * 
     * @return
     *     possible object is
     *     {@link SoggettoFisico }
     *     
     */
    public SoggettoFisico getSoggettoFisico() {
        return soggettoFisico;
    }

    /**
     * Imposta il valore della proprieta' soggettoFisico.
     * 
     * @param value
     *     allowed object is
     *     {@link SoggettoFisico }
     *     
     */
    public void setSoggettoFisico(SoggettoFisico value) {
        this.soggettoFisico = value;
    }

    /**
     * Recupera il valore della proprieta' soggettoGiuridico.
     * 
     * @return
     *     possible object is
     *     {@link SoggettoGiuridico }
     *     
     */
    public SoggettoGiuridico getSoggettoGiuridico() {
        return soggettoGiuridico;
    }

    /**
     * Imposta il valore della proprieta' soggettoGiuridico.
     * 
     * @param value
     *     allowed object is
     *     {@link SoggettoGiuridico }
     *     
     */
    public void setSoggettoGiuridico(SoggettoGiuridico value) {
        this.soggettoGiuridico = value;
    }

}
