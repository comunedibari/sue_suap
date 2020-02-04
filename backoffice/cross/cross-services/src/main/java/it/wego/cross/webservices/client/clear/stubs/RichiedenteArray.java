
package it.wego.cross.webservices.client.clear.stubs;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per richiedenteArray complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="richiedenteArray">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="richiedente" type="{http://www.simo.org}richiedente" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "richiedenteArray", propOrder = {
    "richiedente"
})
public class RichiedenteArray {

    @XmlElement(required = true)
    protected List<Richiedente> richiedente;

    /**
     * Gets the value of the richiedente property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the richiedente property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRichiedente().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Richiedente }
     * 
     * 
     */
    public List<Richiedente> getRichiedente() {
        if (richiedente == null) {
            richiedente = new ArrayList<Richiedente>();
        }
        return this.richiedente;
    }

}
