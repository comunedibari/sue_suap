
package it.gov.impresainungiorno.schema.base;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatiPersona complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiPersona">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.impresainungiorno.gov.it/schema/base}AnagraficaCompleta">
 *       &lt;sequence>
 *         &lt;element name="residenza" type="{http://www.impresainungiorno.gov.it/schema/base}Indirizzo"/>
 *         &lt;group ref="{http://www.impresainungiorno.gov.it/schema/base}Recapiti"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiPersona", propOrder = {
    "residenza",
    "eMail",
    "telefono",
    "sitoWeb"
})
@XmlSeeAlso({
    LegaleRappresentante.class
})
public class DatiPersona
    extends AnagraficaCompleta
{

    @XmlElement(required = true)
    protected Indirizzo residenza;
    @XmlElement(name = "e-mail")
    protected List<EMail> eMail;
    protected List<Telefono> telefono;
    @XmlElement(name = "sito-web")
    @XmlSchemaType(name = "anyURI")
    protected List<String> sitoWeb;

    /**
     * Gets the value of the residenza property.
     * 
     * @return
     *     possible object is
     *     {@link Indirizzo }
     *     
     */
    public Indirizzo getResidenza() {
        return residenza;
    }

    /**
     * Sets the value of the residenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link Indirizzo }
     *     
     */
    public void setResidenza(Indirizzo value) {
        this.residenza = value;
    }

    /**
     * Gets the value of the eMail property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the eMail property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEMail().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EMail }
     * 
     * 
     */
    public List<EMail> getEMail() {
        if (eMail == null) {
            eMail = new ArrayList<EMail>();
        }
        return this.eMail;
    }

    /**
     * Gets the value of the telefono property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the telefono property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTelefono().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Telefono }
     * 
     * 
     */
    public List<Telefono> getTelefono() {
        if (telefono == null) {
            telefono = new ArrayList<Telefono>();
        }
        return this.telefono;
    }

    /**
     * Gets the value of the sitoWeb property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sitoWeb property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSitoWeb().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getSitoWeb() {
        if (sitoWeb == null) {
            sitoWeb = new ArrayList<String>();
        }
        return this.sitoWeb;
    }

}
