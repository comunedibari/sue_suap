package it.wego.auriga.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per UOEstesaType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="UOEstesaType">
 *   &lt;complexContent>
 *     &lt;extension base="{}UOType">
 *       &lt;sequence>
 *         &lt;element name="FlagIncluseSottoUO" type="{}FlagSiNoType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UOEstesaType", propOrder = {
    "flagIncluseSottoUO"
})
public class UOEstesaType
    extends UOType
{

    @XmlElement(name = "FlagIncluseSottoUO", required = true, defaultValue = "0")
    protected String flagIncluseSottoUO;

    /**
     * Recupera il valore della propriet� flagIncluseSottoUO.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagIncluseSottoUO() {
        return flagIncluseSottoUO;
    }

    /**
     * Imposta il valore della propriet� flagIncluseSottoUO.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagIncluseSottoUO(String value) {
        this.flagIncluseSottoUO = value;
    }

}
