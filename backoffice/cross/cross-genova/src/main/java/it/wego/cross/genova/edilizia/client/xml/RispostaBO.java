//
// Questo file � stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andr� persa durante la ricompilazione dello schema di origine. 
// Generato il: 2013.05.03 alle 09:53:22 AM CEST 
//
package it.wego.cross.genova.edilizia.client.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per RispostaBO complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RispostaBO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="errorMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="esito">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="OK"/>
 *               &lt;enumeration value="KO"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="tipoRegistroProtocolloBackOffice" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numeroRegistroProtocolloBackOffice" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="annoRegistroProtocolloBackOffice" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="fascicoloProtocollo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idFascicoloProtocollo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="resposabileProcedimento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ufficioRiferimento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement(name = "RispostaBO")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RispostaBO", propOrder = {
    "errorMessage",
    "esito",
    "tipoRegistroProtocolloBackOffice",
    "numeroRegistroProtocolloBackOffice",
    "annoRegistroProtocolloBackOffice",
    "dataRegistroProtocolloBackOffice",
    "fascicoloProtocollo",
    "idFascicoloProtocollo",
    "resposabileProcedimento",
    "ufficioRiferimento"
})
public class RispostaBO {

    protected String errorMessage;
    @XmlElement(required = true)
    protected String esito;
    protected String tipoRegistroProtocolloBackOffice;
    protected int numeroRegistroProtocolloBackOffice;
    protected int annoRegistroProtocolloBackOffice;
    protected String dataRegistroProtocolloBackOffice;
    protected String fascicoloProtocollo;
    protected String idFascicoloProtocollo;
    protected String resposabileProcedimento;
    protected String ufficioRiferimento;

    /**
     * Recupera il valore della proprieta' errorMessage.
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
     * Imposta il valore della proprieta' errorMessage.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorMessage(String value) {
        this.errorMessage = value;
    }

    /**
     * Recupera il valore della proprieta' esito.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsito() {
        return esito;
    }

    /**
     * Imposta il valore della proprieta' esito.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsito(String value) {
        this.esito = value;
    }

    /**
     * Recupera il valore della proprieta' tipoRegistroProtocolloBackOffice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoRegistroProtocolloBackOffice() {
        return tipoRegistroProtocolloBackOffice;
    }

    /**
     * Imposta il valore della proprieta' tipoRegistroProtocolloBackOffice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoRegistroProtocolloBackOffice(String value) {
        this.tipoRegistroProtocolloBackOffice = value;
    }

    /**
     * Recupera il valore della proprieta' numeroRegistroProtocolloBackOffice.
     * 
     */
    public int getNumeroRegistroProtocolloBackOffice() {
        return numeroRegistroProtocolloBackOffice;
    }

    /**
     * Imposta il valore della proprieta' numeroRegistroProtocolloBackOffice.
     * 
     */
    public void setNumeroRegistroProtocolloBackOffice(int value) {
        this.numeroRegistroProtocolloBackOffice = value;
    }

    /**
     * Recupera il valore della proprieta' annoRegistroProtocolloBackOffice.
     * 
     */
    public int getAnnoRegistroProtocolloBackOffice() {
        return annoRegistroProtocolloBackOffice;
    }

    /**
     * Imposta il valore della proprieta' annoRegistroProtocolloBackOffice.
     * 
     */
    public void setAnnoRegistroProtocolloBackOffice(int value) {
        this.annoRegistroProtocolloBackOffice = value;
    }

    /**
     * Recupera il valore della proprieta' fascicoloProtocollo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFascicoloProtocollo() {
        return fascicoloProtocollo;
    }

    /**
     * Imposta il valore della proprieta' fascicoloProtocollo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFascicoloProtocollo(String value) {
        this.fascicoloProtocollo = value;
    }

    /**
     * Recupera il valore della proprieta' idFascicoloProtocollo.
     * 
     */
    public String getIdFascicoloProtocollo() {
        return idFascicoloProtocollo;
    }

    /**
     * Imposta il valore della proprieta' idFascicoloProtocollo.
     * 
     */
    public void setIdFascicoloProtocollo(String value) {
        this.idFascicoloProtocollo = value;
    }

    /**
     * Recupera il valore della proprieta' resposabileProcedimento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResposabileProcedimento() {
        return resposabileProcedimento;
    }

    /**
     * Imposta il valore della proprieta' resposabileProcedimento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResposabileProcedimento(String value) {
        this.resposabileProcedimento = value;
    }

    /**
     * Recupera il valore della proprieta' ufficioRiferimento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUfficioRiferimento() {
        return ufficioRiferimento;
    }

    /**
     * Imposta il valore della proprieta' ufficioRiferimento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUfficioRiferimento(String value) {
        this.ufficioRiferimento = value;
    }

    
    public String getDataRegistroProtocolloBackOffice() {
        return dataRegistroProtocolloBackOffice;
    }

    public void setDataRegistroProtocolloBackOffice(String dataRegistroProtocolloBackOffice) {
        this.dataRegistroProtocolloBackOffice = dataRegistroProtocolloBackOffice;
    }

}