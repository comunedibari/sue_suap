//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.04.30 at 02:32:10 PM CEST 
//


package it.wego.cross.avbari.sit.client.request.xsd;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anagraficheSIT complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
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