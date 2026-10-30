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
 * <p>Classe Java per EstremiXIdentificazioneFileUDType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="EstremiXIdentificazioneFileUDType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;choice&gt;
 *           &lt;element name="FlagPrimario" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
 *           &lt;choice&gt;
 *             &lt;element name="NroAllegato" minOccurs="0"&gt;
 *               &lt;simpleType&gt;
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger"&gt;
 *                   &lt;minInclusive value="1"/&gt;
 *                 &lt;/restriction&gt;
 *               &lt;/simpleType&gt;
 *             &lt;/element&gt;
 *             &lt;element name="TipoDocAllegato" type="{}OggDiTabDiSistemaType" minOccurs="0"/&gt;
 *             &lt;element name="DesAllegato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *           &lt;/choice&gt;
 *           &lt;element name="NomeFile" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;/choice&gt;
 *         &lt;element name="NroVersione" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger"&gt;
 *               &lt;minInclusive value="1"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EstremiXIdentificazioneFileUDType", propOrder = {
    "nomeFile",
    "desAllegato",
    "tipoDocAllegato",
    "nroAllegato",
    "flagPrimario",
    "nroVersione"
})
public class EstremiXIdentificazioneFileUDType
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElement(name = "NomeFile")
    protected String nomeFile;
    @XmlElement(name = "DesAllegato")
    protected String desAllegato;
    @XmlElement(name = "TipoDocAllegato")
    protected OggDiTabDiSistemaType tipoDocAllegato;
    @XmlElement(name = "NroAllegato")
    protected BigInteger nroAllegato;
    @XmlElement(name = "FlagPrimario")
    protected Object flagPrimario;
    @XmlElement(name = "NroVersione")
    protected BigInteger nroVersione;

    /**
     * Recupera il valore della proprietà nomeFile.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeFile() {
        return nomeFile;
    }

    /**
     * Imposta il valore della proprietà nomeFile.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeFile(String value) {
        this.nomeFile = value;
    }

    /**
     * Recupera il valore della proprietà desAllegato.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesAllegato() {
        return desAllegato;
    }

    /**
     * Imposta il valore della proprietà desAllegato.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesAllegato(String value) {
        this.desAllegato = value;
    }

    /**
     * Recupera il valore della proprietà tipoDocAllegato.
     * 
     * @return
     *     possible object is
     *     {@link OggDiTabDiSistemaType }
     *     
     */
    public OggDiTabDiSistemaType getTipoDocAllegato() {
        return tipoDocAllegato;
    }

    /**
     * Imposta il valore della proprietà tipoDocAllegato.
     * 
     * @param value
     *     allowed object is
     *     {@link OggDiTabDiSistemaType }
     *     
     */
    public void setTipoDocAllegato(OggDiTabDiSistemaType value) {
        this.tipoDocAllegato = value;
    }

    /**
     * Recupera il valore della proprietà nroAllegato.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNroAllegato() {
        return nroAllegato;
    }

    /**
     * Imposta il valore della proprietà nroAllegato.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNroAllegato(BigInteger value) {
        this.nroAllegato = value;
    }

    /**
     * Recupera il valore della proprietà flagPrimario.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getFlagPrimario() {
        return flagPrimario;
    }

    /**
     * Imposta il valore della proprietà flagPrimario.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setFlagPrimario(Object value) {
        this.flagPrimario = value;
    }

    /**
     * Recupera il valore della proprietà nroVersione.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNroVersione() {
        return nroVersione;
    }

    /**
     * Imposta il valore della proprietà nroVersione.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNroVersione(BigInteger value) {
        this.nroVersione = value;
    }

}
