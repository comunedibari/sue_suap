//
// Questo file e' stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andra' persa durante la ricompilazione dello schema di origine. 
// Generato il: 2013.02.05 alle 06:52:58 PM CET 
//


package it.wego.cross.xml.anagrafetributaria.edilizia;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per datoCatastale complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="datoCatastale">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tipoRecord" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="concatenazione" type="{http://www.wego.it/cross}concatenazione"/>
 *         &lt;element name="tipoUnita">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="T"/>
 *               &lt;enumeration value="F"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="sezione">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="3"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="foglio">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="5"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="particella">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="5"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="estensioneParticella">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="4"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="tipoParticella">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="F"/>
 *               &lt;enumeration value="E"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="subalterno">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="4"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="controllo" type="{http://www.wego.it/cross}controllo"/>
 *         &lt;element name="tipoCatasto">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="ORDINARIO"/>
 *               &lt;enumeration value="TAVOLARE"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "datoCatastale", propOrder = {
    "tipoRecord",
    "concatenazione",
    "tipoUnita",
    "sezione",
    "foglio",
    "particella",
    "estensioneParticella",
    "tipoParticella",
    "subalterno",
    "controllo",
    "tipoCatasto"
})
public class DatoCatastale {

    @XmlElement(required = true)
    protected String tipoRecord;
    @XmlElement(required = true)
    protected Concatenazione concatenazione;
    @XmlElement(required = true)
    protected String tipoUnita;
    @XmlElement(required = true)
    protected String sezione;
    @XmlElement(required = true)
    protected String foglio;
    @XmlElement(required = true)
    protected String particella;
    @XmlElement(required = true)
    protected String estensioneParticella;
    @XmlElement(required = true)
    protected String tipoParticella;
    @XmlElement(required = true)
    protected String subalterno;
    @XmlElement(required = true)
    protected Controllo controllo;
    @XmlElement(required = true)
    protected String tipoCatasto;

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
     * Recupera il valore della proprieta' tipoUnita.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoUnita() {
        return tipoUnita;
    }

    /**
     * Imposta il valore della proprieta' tipoUnita.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoUnita(String value) {
        this.tipoUnita = value;
    }

    /**
     * Recupera il valore della proprieta' sezione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSezione() {
        return sezione;
    }

    /**
     * Imposta il valore della proprieta' sezione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSezione(String value) {
        this.sezione = value;
    }

    /**
     * Recupera il valore della proprieta' foglio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFoglio() {
        return foglio;
    }

    /**
     * Imposta il valore della proprieta' foglio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFoglio(String value) {
        this.foglio = value;
    }

    /**
     * Recupera il valore della proprieta' particella.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParticella() {
        return particella;
    }

    /**
     * Imposta il valore della proprieta' particella.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParticella(String value) {
        this.particella = value;
    }

    /**
     * Recupera il valore della proprieta' estensioneParticella.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEstensioneParticella() {
        return estensioneParticella;
    }

    /**
     * Imposta il valore della proprieta' estensioneParticella.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEstensioneParticella(String value) {
        this.estensioneParticella = value;
    }

    /**
     * Recupera il valore della proprieta' tipoParticella.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoParticella() {
        return tipoParticella;
    }

    /**
     * Imposta il valore della proprieta' tipoParticella.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoParticella(String value) {
        this.tipoParticella = value;
    }

    /**
     * Recupera il valore della proprieta' subalterno.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubalterno() {
        return subalterno;
    }

    /**
     * Imposta il valore della proprieta' subalterno.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubalterno(String value) {
        this.subalterno = value;
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

    /**
     * Recupera il valore della proprieta' tipoCatasto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoCatasto() {
        return tipoCatasto;
    }

    /**
     * Imposta il valore della proprieta' tipoCatasto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoCatasto(String value) {
        this.tipoCatasto = value;
    }

}
