package it.wego.auriga.xml;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per UserType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="UserType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="IdInSistemaEsterno" type="{}IdInSistemaEsternoType" minOccurs="0"/>
 *         &lt;element name="IdInterno" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="UsernameInterna" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Descrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NroMatricola" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UserType", propOrder = {
    "idInSistemaEsterno",
    "idInterno",
    "usernameInterna",
    "descrizione",
    "nroMatricola"
})
public class UserType {

    @XmlElement(name = "IdInSistemaEsterno")
    protected String idInSistemaEsterno;
    @XmlElement(name = "IdInterno")
    protected BigInteger idInterno;
    @XmlElement(name = "UsernameInterna")
    protected String usernameInterna;
    @XmlElement(name = "Descrizione")
    protected String descrizione;
    @XmlElement(name = "NroMatricola")
    protected String nroMatricola;

    /**
     * Recupera il valore della propriet� idInSistemaEsterno.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdInSistemaEsterno() {
        return idInSistemaEsterno;
    }

    /**
     * Imposta il valore della propriet� idInSistemaEsterno.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdInSistemaEsterno(String value) {
        this.idInSistemaEsterno = value;
    }

    /**
     * Recupera il valore della propriet� idInterno.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdInterno() {
        return idInterno;
    }

    /**
     * Imposta il valore della propriet� idInterno.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdInterno(BigInteger value) {
        this.idInterno = value;
    }

    /**
     * Recupera il valore della propriet� usernameInterna.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsernameInterna() {
        return usernameInterna;
    }

    /**
     * Imposta il valore della propriet� usernameInterna.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsernameInterna(String value) {
        this.usernameInterna = value;
    }

    /**
     * Recupera il valore della propriet� descrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Imposta il valore della propriet� descrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizione(String value) {
        this.descrizione = value;
    }

    /**
     * Recupera il valore della propriet� nroMatricola.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNroMatricola() {
        return nroMatricola;
    }

    /**
     * Imposta il valore della propriet� nroMatricola.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNroMatricola(String value) {
        this.nroMatricola = value;
    }

}
