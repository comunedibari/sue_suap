//
// Questo file e' stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andra' persa durante la ricompilazione dello schema di origine. 
// Generato il: 2013.02.01 alle 04:09:31 PM CET 
//


package it.wego.cross.xml.anagrafetributaria.edilizia;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per beneficiario complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="beneficiario">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tipoRecord" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="concatenazione" type="{http://www.wego.it/cross}concatenazione"/>
 *         &lt;element name="soggettoBeneficiario" type="{http://www.wego.it/cross}soggetto"/>
 *         &lt;element name="qualificaBeneficiario">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="1"/>
 *               &lt;enumeration value="2"/>
 *               &lt;enumeration value="3"/>
 *               &lt;enumeration value="4"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="controllo" type="{http://www.wego.it/cross}controllo"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "beneficiario", propOrder = {
    "tipoRecord",
    "concatenazione",
    "soggettoBeneficiario",
    "qualificaBeneficiario",
    "controllo"
})
public class Beneficiario {

    @XmlElement(required = true)
    protected String tipoRecord;
    @XmlElement(required = true)
    protected Concatenazione concatenazione;
    @XmlElement(required = true)
    protected Soggetto soggettoBeneficiario;
    @XmlElement(required = true)
    protected String qualificaBeneficiario;
    @XmlElement(required = true)
    protected Controllo controllo;

    /**
     * Recupera il valore della proprieta' tipoRecord.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoRecord() {
        return tipoRecord;
    }

    /**
     * Imposta il valore della proprieta' tipoRecord.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoRecord(String value) {
        this.tipoRecord = value;
    }

    /**
     * Recupera il valore della proprieta' concatenazione.
     * 
     * @return
     *     possible object is
     *     {@link Concatenazione }
     *     
     */
    public Concatenazione getConcatenazione() {
        return concatenazione;
    }

    /**
     * Imposta il valore della proprieta' concatenazione.
     * 
     * @param value
     *     allowed object is
     *     {@link Concatenazione }
     *     
     */
    public void setConcatenazione(Concatenazione value) {
        this.concatenazione = value;
    }

    /**
     * Recupera il valore della proprieta' soggettoBeneficiario.
     * 
     * @return
     *     possible object is
     *     {@link Soggetto }
     *     
     */
    public Soggetto getSoggettoBeneficiario() {
        return soggettoBeneficiario;
    }

    /**
     * Imposta il valore della proprieta' soggettoBeneficiario.
     * 
     * @param value
     *     allowed object is
     *     {@link Soggetto }
     *     
     */
    public void setSoggettoBeneficiario(Soggetto value) {
        this.soggettoBeneficiario = value;
    }

    /**
     * Recupera il valore della proprieta' qualificaBeneficiario.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQualificaBeneficiario() {
        return qualificaBeneficiario;
    }

    /**
     * Imposta il valore della proprieta' qualificaBeneficiario.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQualificaBeneficiario(String value) {
        this.qualificaBeneficiario = value;
    }

    /**
     * Recupera il valore della proprieta' controllo.
     * 
     * @return
     *     possible object is
     *     {@link Controllo }
     *     
     */
    public Controllo getControllo() {
        return controllo;
    }

    /**
     * Imposta il valore della proprieta' controllo.
     * 
     * @param value
     *     allowed object is
     *     {@link Controllo }
     *     
     */
    public void setControllo(Controllo value) {
        this.controllo = value;
    }

}
