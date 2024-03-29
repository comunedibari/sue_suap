//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.03.02 at 03:51:49 PM CET 
//


package it.gov.impresainungiorno.schema.base;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EnteRicevuta complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EnteRicevuta">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="delegante" type="{http://www.impresainungiorno.gov.it/schema/base}UtentePortale" minOccurs="0"/>
 *         &lt;element name="invio" type="{http://www.impresainungiorno.gov.it/schema/base}InvioAdempimento" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="nome" type="{http://www.impresainungiorno.gov.it/schema/base}StringaBreve" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EnteRicevuta", propOrder = {
    "delegante",
    "invio"
})
public class EnteRicevuta {

    protected UtentePortale delegante;
    @XmlElement(required = true)
    protected List<InvioAdempimento> invio;
    @XmlAttribute
    protected String nome;

    /**
     * Gets the value of the delegante property.
     * 
     * @return
     *     possible object is
     *     {@link UtentePortale }
     *     
     */
    public UtentePortale getDelegante() {
        return delegante;
    }

    /**
     * Sets the value of the delegante property.
     * 
     * @param value
     *     allowed object is
     *     {@link UtentePortale }
     *     
     */
    public void setDelegante(UtentePortale value) {
        this.delegante = value;
    }

    /**
     * Gets the value of the invio property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the invio property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInvio().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InvioAdempimento }
     * 
     * 
     */
    public List<InvioAdempimento> getInvio() {
        if (invio == null) {
            invio = new ArrayList<InvioAdempimento>();
        }
        return this.invio;
    }

    /**
     * Gets the value of the nome property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNome() {
        return nome;
    }

    /**
     * Sets the value of the nome property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNome(String value) {
        this.nome = value;
    }

}
