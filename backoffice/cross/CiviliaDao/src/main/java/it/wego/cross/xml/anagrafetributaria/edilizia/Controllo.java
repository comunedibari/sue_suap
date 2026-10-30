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
 * <p>Classe Java per controllo complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="controllo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="filler" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="carattereControllo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "controllo", propOrder = {
    "filler",
    "carattereControllo"
})
public class Controllo {

    @XmlElement(required = true)
    protected String filler;
    @XmlElement(required = true)
    protected String carattereControllo;

    /**
     * Recupera il valore della proprieta' filler.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFiller() {
        return filler;
    }

    /**
     * Imposta il valore della proprieta' filler.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFiller(String value) {
        this.filler = value;
    }

    /**
     * Recupera il valore della proprieta' carattereControllo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCarattereControllo() {
        return carattereControllo;
    }

    /**
     * Imposta il valore della proprieta' carattereControllo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCarattereControllo(String value) {
        this.carattereControllo = value;
    }

}
