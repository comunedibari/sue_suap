//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.02.25 alle 01:11:35 PM CET 
//
package it.wego.cross.xml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Classe Java per indirizzi_intervento complex type.
 *
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto
 * in questa classe.
 *
 * <pre>
 * &lt;complexType name="indirizzi_intervento">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="indirizzo_intervento" type="{http://www.wego.it/cross}indirizzo_intervento" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "indirizzi_intervento", propOrder = {
    "indirizzoIntervento"
})
public class IndirizziIntervento {

    @XmlElement(name = "indirizzo_intervento", required = true)
    protected List<IndirizzoIntervento> indirizzoIntervento;

    /**
     * Gets the value of the indirizzoIntervento property.
     *
     * <p> This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the indirizzoIntervento property.
     *
     * <p> For example, to add a new item, do as follows:
     * <pre>
     *    getIndirizzoIntervento().add(newItem);
     * </pre>
     *
     *
     * <p> Objects of the following type(s) are allowed in the list
     * {@link IndirizzoIntervento }
     *
     *
     */
    public List<IndirizzoIntervento> getIndirizzoIntervento() {
        if (indirizzoIntervento == null) {
            indirizzoIntervento = new ArrayList<IndirizzoIntervento>();
        }
        return this.indirizzoIntervento;
    }

    public void setIndirizzoIntervento(List<it.wego.cross.xml.IndirizzoIntervento> indirizzoInterventoList) {
        indirizzoIntervento = indirizzoInterventoList;
    }
}
