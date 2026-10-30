//
// Questo file stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrpersa durante la ricompilazione dello schema di origine. 
// Generato il: 2013.03.01 alle 08:35:08 AM CET 
//


package it.wego.cross.client.xml.extractone;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per WSErrorType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="WSErrorType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ErrorContext" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ErrorNumber" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="ErrorMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WSErrorType", propOrder = {
    "errorContext",
    "errorNumber",
    "errorMessage"
})
@XmlSeeAlso({
    it.wego.cross.client.xml.extractone.BaseOutputWS.WSError.class
})
public class WSErrorType {

    @XmlElement(name = "ErrorContext")
    protected String errorContext;
    @XmlElement(name = "ErrorNumber")
    protected BigInteger errorNumber;
    @XmlElement(name = "ErrorMessage")
    protected String errorMessage;

    /**
     * Recupera il valore della proprieterrorContext.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorContext() {
        return errorContext;
    }

    /**
     * Imposta il valore della proprieterrorContext.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorContext(String value) {
        this.errorContext = value;
    }

    /**
     * Recupera il valore della proprieterrorNumber.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getErrorNumber() {
        return errorNumber;
    }

    /**
     * Imposta il valore della proprieterrorNumber.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setErrorNumber(BigInteger value) {
        this.errorNumber = value;
    }

    /**
     * Recupera il valore della proprieterrorMessage.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Imposta il valore della proprieterrorMessage.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorMessage(String value) {
        this.errorMessage = value;
    }

}
