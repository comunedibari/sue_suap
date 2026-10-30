package it.wego.auriga.xml;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Indica una classificazione di titolario attraverso il suo identificativo o attarverso i suoi livelli o attraverso la sua descrizione. 
 * 			
 * 
 * <p>Classe Java per ClassificazioneType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ClassificazioneType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdClassificazione" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="LivelloClassificazione" type="{}LivelloGerarchiaType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="DescrizioneClassificazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClassificazioneType", propOrder = {
    "idClassificazione",
    "livelloClassificazione",
    "descrizioneClassificazione"
})
public class ClassificazioneType {

    @XmlElement(name = "IdClassificazione")
    protected BigInteger idClassificazione;
    @XmlElement(name = "LivelloClassificazione")
    protected List<LivelloGerarchiaType> livelloClassificazione;

	@XmlElement(name = "DescrizioneClassificazione")
    protected String descrizioneClassificazione;

    /**
     * Recupera il valore della propriet� idClassificazione.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdClassificazione() {
        return idClassificazione;
    }

    /**
     * Imposta il valore della propriet� idClassificazione.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdClassificazione(BigInteger value) {
        this.idClassificazione = value;
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

    public void setLivelloClassificazione(List<LivelloGerarchiaType> livelloClassificazione) {
		this.livelloClassificazione = livelloClassificazione;
	}

    /**
     * Recupera il valore della propriet� descrizioneClassificazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneClassificazione() {
        return descrizioneClassificazione;
    }

    /**
     * Imposta il valore della propriet� descrizioneClassificazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneClassificazione(String value) {
        this.descrizioneClassificazione = value;
    }

}
