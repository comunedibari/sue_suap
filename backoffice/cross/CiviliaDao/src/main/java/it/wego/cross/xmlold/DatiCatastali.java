//
// Questo file stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andr persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.02.27 alle 01:48:58 PM CET 
//
package it.wego.cross.xmlold;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Classe Java per dati_catastali complex type.
 *
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto
 * in questa classe.
 *
 * <pre>
 * &lt;complexType name="dati_catastali">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="immobile" type="{http://www.wego.it/cross}immobile" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dati_catastali", propOrder = {
    "immobile"
})
public class DatiCatastali {

    @XmlElement(required = true)
    protected List<Immobile> immobile;

    /**
     * Gets the value of the immobile property.
     *
     * <p> This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the immobile property.
     *
     * <p> For example, to add a new item, do as follows:
     * <pre>
     *    getImmobile().add(newItem);
     * </pre>
     *
     *
     * <p> Objects of the following type(s) are allowed in the list
     * {@link Immobile }
     *
     *
     */
    public List<Immobile> getImmobile() {
        if (immobile == null) {
            immobile = new ArrayList<Immobile>();
        }
        return this.immobile;
    }

    public void setImmobile(List<it.wego.cross.xmlold.Immobile> immobileList) {
        immobile = immobileList;
    }
}
