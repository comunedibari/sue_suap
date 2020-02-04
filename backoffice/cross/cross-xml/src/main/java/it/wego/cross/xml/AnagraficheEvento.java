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
 * <p>Classe Java per anagraficheEvento complex type.
 *
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto
 * in questa classe.
 *
 * <pre>
 * &lt;complexType name="anagraficheEvento">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="anagraficaEvento" type="{http://www.wego.it/cross}anagraficaEvento" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "anagraficheEvento", propOrder = {
    "anagraficaEvento"
})
public class AnagraficheEvento {

    protected List<AnagraficaEvento> anagraficaEvento;

    /**
     * Gets the value of the anagraficaEvento property.
     *
     * <p> This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the ente property.
     *
     * <p> For example, to add a new item, do as follows:
     * <pre>
     *    getAnagraficaEvento().add(newItem);
     * </pre>
     *
     *
     * <p> Objects of the following type(s) are allowed in the list
     * {@link AnagraficaEvento }
     *
     *
     */
    public List<AnagraficaEvento> getAnagraficaEvento() {
        if (anagraficaEvento == null) {
            anagraficaEvento = new ArrayList<AnagraficaEvento>();
        }
        return this.anagraficaEvento;
    }

    public void setAnagraficaEvento(List<it.wego.cross.xml.AnagraficaEvento> anagraficaEventoList) {
        anagraficaEvento = anagraficaEventoList;
    }
}
