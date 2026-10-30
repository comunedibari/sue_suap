//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.11.14 alle 06:57:45 PM CET 
//


package it.wego.auriga.xml;

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
 * &lt;complexType name="EstremiXIdentificazioneUDType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="IdUD" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="EstremiRegNum" type="{}EstremiRegNumType"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EstremiXIdentificazioneUDType", propOrder = {
    "idUD",
    "estremiRegNum"
})
public class EstremiXIdentificazioneUDType {

    @XmlElement(name = "IdUD")
    protected BigInteger idUD;
    @XmlElement(name = "EstremiRegNum")
    protected EstremiRegNumType estremiRegNum;

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

}
