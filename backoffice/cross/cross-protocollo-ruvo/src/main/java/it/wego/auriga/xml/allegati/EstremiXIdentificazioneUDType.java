//
// Questo file è stato generato dall'Eclipse Implementation of JAXB, v3.0.0 
// Vedere https://eclipse-ee4j.github.io/jaxb-ri 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.11.16 alle 06:34:12 PM CET 
//


package it.wego.auriga.xml.allegati;

import java.io.Serializable;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Contiene i dati attraverso cui identificare univocamente un'unità documentaria
 * 
 * <p>Classe Java per EstremiXIdentificazioneUDType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="EstremiXIdentificazioneUDType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice&gt;
 *         &lt;element name="IdUD" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="EstremiRegNum" type="{}EstremiRegNumType"/&gt;
 *       &lt;/choice&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EstremiXIdentificazioneUDType", propOrder = {
    "estremiRegNum",
    "idUD"
})
public class EstremiXIdentificazioneUDType
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElement(name = "EstremiRegNum")
    protected EstremiRegNumType estremiRegNum;
    @XmlElement(name = "IdUD")
    protected BigInteger idUD;

    /**
     * Recupera il valore della proprietà estremiRegNum.
     * 
     * @return
     *     possible object is
     *     {@link EstremiRegNumType }
     *     
     */
    public EstremiRegNumType getEstremiRegNum() {
        return estremiRegNum;
    }

    /**
     * Imposta il valore della proprietà estremiRegNum.
     * 
     * @param value
     *     allowed object is
     *     {@link EstremiRegNumType }
     *     
     */
    public void setEstremiRegNum(EstremiRegNumType value) {
        this.estremiRegNum = value;
    }

    /**
     * Recupera il valore della proprietà idUD.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdUD() {
        return idUD;
    }

    /**
     * Imposta il valore della proprietà idUD.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdUD(BigInteger value) {
        this.idUD = value;
    }

}
