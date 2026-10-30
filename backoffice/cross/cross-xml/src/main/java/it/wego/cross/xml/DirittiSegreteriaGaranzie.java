//
// Questo file � stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andr� persa durante la ricompilazione dello schema di origine. 
// Generato il: 2024.04.16 alle 09:52:47 AM CEST 
//


package it.wego.cross.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per dirittiSegreteriaGaranzie complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="dirittiSegreteriaGaranzie">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="banca" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="data" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="importo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dirittiSegreteriaGaranzie", propOrder = {
    "banca",
    "data",
    "importo"
})
public class DirittiSegreteriaGaranzie {

    @XmlElement(name = "banca")
    protected String banca;
    @XmlElement(name = "data")
    protected String data;
    @XmlElement(name = "importo")
    protected String importo;

    /**
     * Recupera il valore della propriet� banca.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBanca() {
        return banca;
    }

    /**
     * Imposta il valore della propriet� banca.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBanca(String value) {
        this.banca = value;
    }

    /**
     * Recupera il valore della propriet� data.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getData() {
        return data;
    }

    /**
     * Imposta il valore della propriet� data.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setData(String value) {
        this.data = value;
    }

    /**
     * Recupera il valore della propriet� importo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImporto() {
        return importo;
    }

    /**
     * Imposta il valore della propriet� importo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImporto(String value) {
        this.importo = value;
    }

}
