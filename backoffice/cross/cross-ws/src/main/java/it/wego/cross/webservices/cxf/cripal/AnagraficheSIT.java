
package it.wego.cross.webservices.cxf.cripal;

import it.wego.cross.webservices.cxf.*;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anagraficheSIT complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="anagraficheSIT">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="anagraficaSIT" type="{http://www.wego.it/cross}anagraficaSIT" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "anagraficheSIT", propOrder = {
    "anagraficaSIT"
})
public class AnagraficheSIT {

    @XmlElement(required = true)
    protected List<AnagraficaSIT> anagraficaSIT;

    /**
     * Gets the value of the anagraficaSIT property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the anagraficaSIT property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAnagraficaSIT().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AnagraficaSIT }
     * 
     * 
     */
    public List<AnagraficaSIT> getAnagraficaSIT() {
        if (anagraficaSIT == null) {
            anagraficaSIT = new ArrayList<AnagraficaSIT>();
        }
        return this.anagraficaSIT;
    }

}
