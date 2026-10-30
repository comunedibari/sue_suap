//
// Questo file stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrpersa durante la ricompilazione dello schema di origine. 
// Generato il: 2013.05.27 alle 07:27:17 PM CEST 
//
package it.wego.cross.xml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Classe Java per entiEvento complex type.
 *
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto
 * in questa classe.
 *
 * <pre>
 * &lt;complexType name="entiEvento">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="enteEvento" type="{http://www.wego.it/cross}enteEvento" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "entiEvento", propOrder = {
    "enteEvento"
})
public class EntiEvento {

    protected List<EnteEvento> enteEvento;

    /**
     * Gets the value of the enteEvento property.
     *
     * <p> This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the ente property.
     *
     * <p> For example, to add a new item, do as follows:
     * <pre>
     *    getEnteEvento().add(newItem);
     * </pre>
     *
     *
     * <p> Objects of the following type(s) are allowed in the list
     * {@link EnteEvento }
     *
     *
     */
    public List<EnteEvento> getEnteEvento() {
        if (enteEvento == null) {
            enteEvento = new ArrayList<EnteEvento>();
        }
        return this.enteEvento;
    }

    public void setEnteEvento(List<it.wego.cross.xml.EnteEvento> enteEventoList) {
        enteEvento = enteEventoList;
    }
}
