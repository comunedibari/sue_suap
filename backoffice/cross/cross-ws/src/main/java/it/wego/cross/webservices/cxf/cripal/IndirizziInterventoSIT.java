
package it.wego.cross.webservices.cxf.cripal;

import it.wego.cross.webservices.cxf.*;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per indirizzi_interventoSIT complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="indirizzi_interventoSIT">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="indirizzo_interventoSIT" type="{http://www.wego.it/cross}indirizzo_interventoSIT" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "indirizzi_interventoSIT", propOrder = {
    "indirizzoInterventoSIT"
})
public class IndirizziInterventoSIT {

    @XmlElement(name = "indirizzo_interventoSIT", required = true)
    protected List<IndirizzoInterventoSIT> indirizzoInterventoSIT;

    /**
     * Gets the value of the indirizzoInterventoSIT property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the indirizzoInterventoSIT property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIndirizzoInterventoSIT().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IndirizzoInterventoSIT }
     * 
     * 
     */
    public List<IndirizzoInterventoSIT> getIndirizzoInterventoSIT() {
        if (indirizzoInterventoSIT == null) {
            indirizzoInterventoSIT = new ArrayList<IndirizzoInterventoSIT>();
        }
        return this.indirizzoInterventoSIT;
    }

}
