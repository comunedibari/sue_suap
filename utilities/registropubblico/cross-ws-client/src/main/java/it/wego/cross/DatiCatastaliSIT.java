
package it.wego.cross;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dati_catastaliSIT complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dati_catastaliSIT">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dato_catastaleSIT" type="{http://www.wego.it/cross}dato_catastaleSIT" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dati_catastaliSIT", propOrder = {
    "datoCatastaleSIT"
})
public class DatiCatastaliSIT {

    @XmlElement(name = "dato_catastaleSIT", required = true)
    protected List<DatoCatastaleSIT> datoCatastaleSIT;

    /**
     * Gets the value of the datoCatastaleSIT property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the datoCatastaleSIT property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDatoCatastaleSIT().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DatoCatastaleSIT }
     * 
     * 
     */
    public List<DatoCatastaleSIT> getDatoCatastaleSIT() {
        if (datoCatastaleSIT == null) {
            datoCatastaleSIT = new ArrayList<DatoCatastaleSIT>();
        }
        return this.datoCatastaleSIT;
    }

}
