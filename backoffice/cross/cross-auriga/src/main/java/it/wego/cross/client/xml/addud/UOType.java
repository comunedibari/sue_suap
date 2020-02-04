//
// Questo file stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrpersa durante la ricompilazione dello schema di origine. 
// Generato il: 2013.03.28 alle 03:45:54 PM CET 
//


package it.wego.cross.client.xml.addud;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * Indica una UO attraverso il suo identificativo o attarverso i suoi livelli o attraverso la sua denominazione. 
 * 			
 * 
 * <p>Classe Java per UOType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="UOType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdUO" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="LivelloUO" type="{}LivelloGerarchiaType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="DenominazioneUO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UOType", propOrder = {
    "idUO",
    "livelloUO",
    "denominazioneUO"
})
@XmlSeeAlso({
    UOEstesaType.class
})
public class UOType {

    @XmlElement(name = "IdUO")
    protected BigInteger idUO;
    @XmlElement(name = "LivelloUO")
    protected List<LivelloGerarchiaType> livelloUO;
    @XmlElement(name = "DenominazioneUO")
    protected String denominazioneUO;

    /**
     * Recupera il valore della proprietidUO.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdUO() {
        return idUO;
    }

    /**
     * Imposta il valore della proprietidUO.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdUO(BigInteger value) {
        this.idUO = value;
    }

    /**
     * Gets the value of the livelloUO property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the livelloUO property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLivelloUO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LivelloGerarchiaType }
     * 
     * 
     */
    public List<LivelloGerarchiaType> getLivelloUO() {
        if (livelloUO == null) {
            livelloUO = new ArrayList<LivelloGerarchiaType>();
        }
        return this.livelloUO;
    }

    /**
     * Recupera il valore della proprietdenominazioneUO.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDenominazioneUO() {
        return denominazioneUO;
    }

    /**
     * Imposta il valore della proprietdenominazioneUO.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDenominazioneUO(String value) {
        this.denominazioneUO = value;
    }

}
