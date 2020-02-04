//
// Questo file stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrpersa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.07.14 alle 03:42:18 PM CEST 
//


package it.wego.cross.client.xml.getmetadata;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * Rappresenta una classificazione e/o un'unitarchivistica (UA), vale a dire un fascicolo basato sul titolario di calssificazione e identificato attraverso anno, classificazione, n.ro progressivo (all'interno di anno e classificazione) ed eventuale n.ro di sottofascicolo
 * 
 * <p>Classe Java per ClassifUAType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ClassifUAType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AnnoAperturaUA" type="{}AnnoType" minOccurs="0"/>
 *         &lt;element name="LivelloClassificazione" type="{}LivelloGerarchiaType" maxOccurs="unbounded"/>
 *         &lt;element name="NroProgrUA" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/>
 *         &lt;element name="NroSottofasc" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClassifUAType", propOrder = {
    "annoAperturaUA",
    "livelloClassificazione",
    "nroProgrUA",
    "nroSottofasc"
})
public class ClassifUAType {

    @XmlElement(name = "AnnoAperturaUA")
    protected Integer annoAperturaUA;
    @XmlElement(name = "LivelloClassificazione", required = true)
    protected List<LivelloGerarchiaType> livelloClassificazione;
    @XmlElement(name = "NroProgrUA")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger nroProgrUA;
    @XmlElement(name = "NroSottofasc")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger nroSottofasc;

    /**
     * Recupera il valore della proprietannoAperturaUA.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAnnoAperturaUA() {
        return annoAperturaUA;
    }

    /**
     * Imposta il valore della proprietannoAperturaUA.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAnnoAperturaUA(Integer value) {
        this.annoAperturaUA = value;
    }

    /**
     * Gets the value of the livelloClassificazione property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the livelloClassificazione property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLivelloClassificazione().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LivelloGerarchiaType }
     * 
     * 
     */
    public List<LivelloGerarchiaType> getLivelloClassificazione() {
        if (livelloClassificazione == null) {
            livelloClassificazione = new ArrayList<LivelloGerarchiaType>();
        }
        return this.livelloClassificazione;
    }

    /**
     * Recupera il valore della proprietnroProgrUA.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNroProgrUA() {
        return nroProgrUA;
    }

    /**
     * Imposta il valore della proprietnroProgrUA.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNroProgrUA(BigInteger value) {
        this.nroProgrUA = value;
    }

    /**
     * Recupera il valore della proprietnroSottofasc.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNroSottofasc() {
        return nroSottofasc;
    }

    /**
     * Imposta il valore della proprietnroSottofasc.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNroSottofasc(BigInteger value) {
        this.nroSottofasc = value;
    }

}
