//
// Questo file stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrpersa durante la ricompilazione dello schema di origine. 
// Generato il: 2013.06.13 alle 04:27:44 PM CEST 
//
package it.wego.cross.xml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Classe Java per recapiti complex type.
 *
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto
 * in questa classe.
 *
 * <pre>
 * &lt;complexType name="recapiti">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="recapito" type="{http://www.wego.it/cross}recapito" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "recapiti", propOrder = {
    "recapito"
})
public class Recapiti {

    protected List<Recapito> recapito;

    /**
     * Gets the value of the recapito property.
     *
     * <p> This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the recapito property.
     *
     * <p> For example, to add a new item, do as follows:
     * <pre>
     *    getRecapito().add(newItem);
     * </pre>
     *
     *
     * <p> Objects of the following type(s) are allowed in the list
     * {@link Recapito }
     *
     *
     */
    public List<Recapito> getRecapito() {
        if (recapito == null) {
            recapito = new ArrayList<Recapito>();
        }
        return this.recapito;
    }

    public void setRecapito(List<it.wego.cross.xml.Recapito> recapitoList) {
        recapito = recapitoList;
    }
}
