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
 * <p>Classe Java per enti complex type.
 *
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto
 * in questa classe.
 *
 * <pre>
 * &lt;complexType name="dirittiSegreteriaRate">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dirittiSegreteriaRata" type="{http://www.wego.it/cross}dirittiSegreteriaRata" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dirittiSegreteriaRate", propOrder = {
    "dirittiSegreteriaRata"
})
public class DirittiSegreteriaRate {

    protected List<DirittiSegreteriaRata> dirittiSegreteriaRata;

    /**
     * Gets the value of the evento property.
     *
     * <p> This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the ente property.
     *
     * <p> For example, to add a new item, do as follows:
     * <pre>
     *    getEnte().add(newItem);
     * </pre>
     *
     *
     * <p> Objects of the following type(s) are allowed in the list
     * {@link Ente }
     *
     *
     */
    public List<DirittiSegreteriaRata> getDirittiSegreteriaRata() {
        if (dirittiSegreteriaRata == null) {
            dirittiSegreteriaRata = new ArrayList<DirittiSegreteriaRata>();
        }
        return this.dirittiSegreteriaRata;
    }

    public void setDirittiSegreteriaRata(List<DirittiSegreteriaRata> dirittiSegreteriaRataList) {
        dirittiSegreteriaRata = dirittiSegreteriaRataList;
    }
}
