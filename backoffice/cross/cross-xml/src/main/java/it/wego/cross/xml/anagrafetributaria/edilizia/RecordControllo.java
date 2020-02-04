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
 * <p>Classe Java per recordControllo complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="recordControllo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tipoRecord">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="0"/>
 *               &lt;enumeration value="9"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="codiceIdentificativoFornitura" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codiceNumericoFornitura" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="soggettoObbligato" type="{http://www.wego.it/cross}soggetto"/>
 *         &lt;element name="annoRiferimento">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="(19|20)(\d{2})"/>
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
@XmlType(name = "recordControllo", propOrder = {
    "tipoRecord",
    "codiceIdentificativoFornitura",
    "codiceNumericoFornitura",
    "soggettoObbligato",
    "annoRiferimento",
    "controllo"
})
public class RecordControllo {

    @XmlElement(required = true)
    protected String tipoRecord;
    @XmlElement(required = true)
    protected String codiceIdentificativoFornitura;
    @XmlElement(required = true)
    protected String codiceNumericoFornitura;
    @XmlElement(required = true)
    protected Soggetto soggettoObbligato;
    @XmlElement(required = true)
    protected String annoRiferimento;
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
     * Recupera il valore della proprieta' codiceIdentificativoFornitura.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceIdentificativoFornitura() {
        return codiceIdentificativoFornitura;
    }

    /**
     * Imposta il valore della proprieta' codiceIdentificativoFornitura.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceIdentificativoFornitura(String value) {
        this.codiceIdentificativoFornitura = value;
    }

    /**
     * Recupera il valore della proprieta' codiceNumericoFornitura.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceNumericoFornitura() {
        return codiceNumericoFornitura;
    }

    /**
     * Imposta il valore della proprieta' codiceNumericoFornitura.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceNumericoFornitura(String value) {
        this.codiceNumericoFornitura = value;
    }

    /**
     * Recupera il valore della proprieta' soggettoObbligato.
     * 
     * @return
     *     possible object is
     *     {@link Soggetto }
     *     
     */
    public Soggetto getSoggettoObbligato() {
        return soggettoObbligato;
    }

    /**
     * Imposta il valore della proprieta' soggettoObbligato.
     * 
     * @param value
     *     allowed object is
     *     {@link Soggetto }
     *     
     */
    public void setSoggettoObbligato(Soggetto value) {
        this.soggettoObbligato = value;
    }

    /**
     * Recupera il valore della proprieta' annoRiferimento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnnoRiferimento() {
        return annoRiferimento;
    }

    /**
     * Imposta il valore della proprieta' annoRiferimento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnnoRiferimento(String value) {
        this.annoRiferimento = value;
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
