package it.wego.auriga.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per EmailType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="EmailType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="IndirizzoEmail" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="FlagPEC" type="{}FlagSiNoType" minOccurs="0"/>
 *         &lt;element name="FlagCasellaIstituzionaleAOO" type="{}FlagSiNoType" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EmailType", propOrder = {

})
public class EmailType {

    @XmlElement(name = "IndirizzoEmail", required = true)
    protected String indirizzoEmail;
    @XmlElement(name = "FlagPEC")
    protected String flagPEC;
    @XmlElement(name = "FlagCasellaIstituzionaleAOO")
    protected String flagCasellaIstituzionaleAOO;

    /**
     * Recupera il valore della propriet� indirizzoEmail.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndirizzoEmail() {
        return indirizzoEmail;
    }

    /**
     * Imposta il valore della propriet� indirizzoEmail.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndirizzoEmail(String value) {
        this.indirizzoEmail = value;
    }

    /**
     * Recupera il valore della propriet� flagPEC.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagPEC() {
        return flagPEC;
    }

    /**
     * Imposta il valore della propriet� flagPEC.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagPEC(String value) {
        this.flagPEC = value;
    }

    /**
     * Recupera il valore della propriet� flagCasellaIstituzionaleAOO.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagCasellaIstituzionaleAOO() {
        return flagCasellaIstituzionaleAOO;
    }

    /**
     * Imposta il valore della propriet� flagCasellaIstituzionaleAOO.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagCasellaIstituzionaleAOO(String value) {
        this.flagCasellaIstituzionaleAOO = value;
    }

}
