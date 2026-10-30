package it.wego.auriga.xml;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * La scrivania virtuale rappresenta un utente non come persona fisica, ma nella funzione che svolge presso una certa UO. Pu� essere indicata univocamente indicando UO e utente; oppure il sistema pu� cercare di identificarla anche o solo a partire dalla sua descrizione
 * 
 * <p>Classe Java per ScrivaniaVirtualeType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ScrivaniaVirtualeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="UO" type="{}UOType" minOccurs="0"/>
 *         &lt;element name="Utente" type="{}UserType" minOccurs="0"/>
 *         &lt;element name="IdScrivaniaVirt" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="DesScrivaniaVirt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScrivaniaVirtualeType", propOrder = {

})
public class ScrivaniaVirtualeType {

    @XmlElement(name = "UO")
    protected UOType uo;
    @XmlElement(name = "Utente")
    protected UserType utente;
    @XmlElement(name = "IdScrivaniaVirt")
    protected BigInteger idScrivaniaVirt;
    @XmlElement(name = "DesScrivaniaVirt")
    protected String desScrivaniaVirt;

    /**
     * Recupera il valore della propriet� uo.
     * 
     * @return
     *     possible object is
     *     {@link UOType }
     *     
     */
    public UOType getUO() {
        return uo;
    }

    /**
     * Imposta il valore della propriet� uo.
     * 
     * @param value
     *     allowed object is
     *     {@link UOType }
     *     
     */
    public void setUO(UOType value) {
        this.uo = value;
    }

    /**
     * Recupera il valore della propriet� utente.
     * 
     * @return
     *     possible object is
     *     {@link UserType }
     *     
     */
    public UserType getUtente() {
        return utente;
    }

    /**
     * Imposta il valore della propriet� utente.
     * 
     * @param value
     *     allowed object is
     *     {@link UserType }
     *     
     */
    public void setUtente(UserType value) {
        this.utente = value;
    }

    /**
     * Recupera il valore della propriet� idScrivaniaVirt.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdScrivaniaVirt() {
        return idScrivaniaVirt;
    }

    /**
     * Imposta il valore della propriet� idScrivaniaVirt.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdScrivaniaVirt(BigInteger value) {
        this.idScrivaniaVirt = value;
    }

    /**
     * Recupera il valore della propriet� desScrivaniaVirt.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesScrivaniaVirt() {
        return desScrivaniaVirt;
    }

    /**
     * Imposta il valore della propriet� desScrivaniaVirt.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesScrivaniaVirt(String value) {
        this.desScrivaniaVirt = value;
    }

}
