//
// Questo file  stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
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
 * <p>Classe Java per scadenze complex type.
 *
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto
 * in questa classe.
 *
 * <pre>
 * &lt;complexType name="scadenze">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="scadenza" type="{http://www.wego.it/cross}scadenza" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "scadenze", propOrder = {
    "scadenza"
})
public class Scadenze {

    @XmlElement(required = true)
    protected List<Scadenza> scadenza;

    /**
     * Gets the value of the scadenza property.
     *
     * <p> This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the scadenza property.
     *
     * <p> For example, to add a new item, do as follows:
     * <pre>
     *    getScadenza().add(newItem);
     * </pre>
     *
     *
     * <p> Objects of the following type(s) are allowed in the list
     * {@link Scadenza }
     *
     *
     */
    public List<Scadenza> getScadenza() {
        if (scadenza == null) {
            scadenza = new ArrayList<Scadenza>();
        }
        return this.scadenza;
    }

    public void setScadenza(List<it.wego.cross.xmlold.Scadenza> scadenzaList) {
        scadenza = scadenzaList;
    }
}
