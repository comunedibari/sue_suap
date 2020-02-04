//
// Questo file stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrpersa durante la ricompilazione dello schema di origine. 
// Generato il: 2013.03.01 alle 08:36:33 AM CET 
//


package it.wego.cross.client.xml.extractone;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per EstremiXIdentificazioneFileUDType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="EstremiXIdentificazioneFileUDType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element name="FlagPrimario" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *           &lt;choice>
 *             &lt;element name="NroAllegato" minOccurs="0">
 *               &lt;simpleType>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
 *                   &lt;minInclusive value="1"/>
 *                 &lt;/restriction>
 *               &lt;/simpleType>
 *             &lt;/element>
 *             &lt;element name="TipoDocAllegato" type="{}OggDiTabDiSistemaType" minOccurs="0"/>
 *             &lt;element name="DesAllegato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *           &lt;/choice>
 *           &lt;element name="NomeFile" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;/choice>
 *         &lt;element name="NroVersione" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
 *               &lt;minInclusive value="1"/>
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
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EstremiXIdentificazioneFileUDType", propOrder = {
    "flagPrimario",
    "nroAllegato",
    "tipoDocAllegato",
    "desAllegato",
    "nomeFile",
    "nroVersione"
})
public class EstremiXIdentificazioneFileUDType {

    @XmlElement(name = "FlagPrimario")
    protected Object flagPrimario;
    @XmlElement(name = "NroAllegato")
    protected BigInteger nroAllegato;
    @XmlElement(name = "TipoDocAllegato")
    protected OggDiTabDiSistemaType tipoDocAllegato;
    @XmlElement(name = "DesAllegato")
    protected String desAllegato;
    @XmlElement(name = "NomeFile")
    protected String nomeFile;
    @XmlElement(name = "NroVersione")
    protected BigInteger nroVersione;

    /**
     * Recupera il valore della proprietflagPrimario.
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
     * Imposta il valore della proprietflagPrimario.
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
     * Recupera il valore della proprietnroAllegato.
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
     * Imposta il valore della proprietnroAllegato.
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
     * Recupera il valore della propriettipoDocAllegato.
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
     * Imposta il valore della propriettipoDocAllegato.
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
     * Recupera il valore della proprietdesAllegato.
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
     * Imposta il valore della proprietdesAllegato.
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
     * Recupera il valore della proprietnomeFile.
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
     * Imposta il valore della proprietnomeFile.
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
     * Recupera il valore della proprietnroVersione.
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
     * Imposta il valore della proprietnroVersione.
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
