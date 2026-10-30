//
// Questo file stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrpersa durante la ricompilazione dello schema di origine. 
// Generato il: 2013.05.27 alle 07:27:17 PM CEST 
//


package it.wego.cross.xml;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per procedimento complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="procedimento">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id_procedimento" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="cod_procedimento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="termini" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="des_procedimento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cod_lang" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id_ente_destinatario" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="cod_ente_destinatario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="des_ente_destinatario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "procedimento", propOrder = {
    "idProcedimento",
    "codProcedimento",
    "termini",
    "desProcedimento",
    "codLang",
    "idEnteDestinatario",
    "codEnteDestinatario",
    "desEnteDestinatario"
})
public class Procedimento {

    @XmlElement(name = "id_procedimento")
    protected Integer idProcedimento;
    @XmlElement(name = "cod_procedimento")
    protected String codProcedimento;
    protected BigInteger termini;
    @XmlElement(name = "des_procedimento")
    protected String desProcedimento;
    @XmlElement(name = "cod_lang")
    protected String codLang;
    @XmlElement(name = "id_ente_destinatario")
    protected BigInteger idEnteDestinatario;
    @XmlElement(name = "cod_ente_destinatario")
    protected String codEnteDestinatario;
    @XmlElement(name = "des_ente_destinatario")
    protected String desEnteDestinatario;

    /**
     * Recupera il valore della proprietidProcedimento.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdProcedimento() {
        return idProcedimento;
    }

    /**
     * Imposta il valore della proprietidProcedimento.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdProcedimento(Integer value) {
        this.idProcedimento = value;
    }

    /**
     * Recupera il valore della proprietcodProcedimento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodProcedimento() {
        return codProcedimento;
    }

    /**
     * Imposta il valore della proprietcodProcedimento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodProcedimento(String value) {
        this.codProcedimento = value;
    }

    /**
     * Recupera il valore della propriettermini.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTermini() {
        return termini;
    }

    /**
     * Imposta il valore della propriettermini.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTermini(BigInteger value) {
        this.termini = value;
    }

    /**
     * Recupera il valore della proprietdesProcedimento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesProcedimento() {
        return desProcedimento;
    }

    /**
     * Imposta il valore della proprietdesProcedimento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesProcedimento(String value) {
        this.desProcedimento = value;
    }

    /**
     * Recupera il valore della proprietcodLang.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodLang() {
        return codLang;
    }

    /**
     * Imposta il valore della proprietcodLang.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodLang(String value) {
        this.codLang = value;
    }

    /**
     * Recupera il valore della proprietidEnteDestinatario.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdEnteDestinatario() {
        return idEnteDestinatario;
    }

    /**
     * Imposta il valore della proprietidEnteDestinatario.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdEnteDestinatario(BigInteger value) {
        this.idEnteDestinatario = value;
    }

    /**
     * Recupera il valore della proprietcodEnteDestinatario.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodEnteDestinatario() {
        return codEnteDestinatario;
    }

    /**
     * Imposta il valore della proprietcodEnteDestinatario.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodEnteDestinatario(String value) {
        this.codEnteDestinatario = value;
    }

    /**
     * Recupera il valore della proprietdesEnteDestinatario.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesEnteDestinatario() {
        return desEnteDestinatario;
    }

    /**
     * Imposta il valore della proprietdesEnteDestinatario.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesEnteDestinatario(String value) {
        this.desEnteDestinatario = value;
    }

}
