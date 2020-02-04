
package it.wego.cross;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for procedimentiSIT complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="procedimentiSIT">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="procedimentoSIT" type="{http://www.wego.it/cross}procedimentoSIT" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "procedimentiSIT", propOrder = {
    "procedimentoSIT"
})
public class ProcedimentiSIT {

    @XmlElement(required = true)
    protected List<ProcedimentoSIT> procedimentoSIT;

    /**
     * Gets the value of the procedimentoSIT property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the procedimentoSIT property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProcedimentoSIT().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProcedimentoSIT }
     * 
     * 
     */
    public List<ProcedimentoSIT> getProcedimentoSIT() {
        if (procedimentoSIT == null) {
            procedimentoSIT = new ArrayList<ProcedimentoSIT>();
        }
        return this.procedimentoSIT;
    }

}
