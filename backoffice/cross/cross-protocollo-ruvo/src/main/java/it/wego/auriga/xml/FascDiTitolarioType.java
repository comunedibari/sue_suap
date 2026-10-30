package it.wego.auriga.xml;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * Rappresenta un fascicolo basato sul titolario di calssificazione e identificato attraverso anno, classificazione, n.ro progressivo (all'interno di anno e classificazione) ed eventuali n.ro di sottofascicolo e di inserto
 * 
 * <p>Classe Java per FascDiTitolarioType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="FascDiTitolarioType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FlagTipo">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="F|SF|I"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Classificazione" type="{}ClassificazioneType"/>
 *         &lt;element name="NroFascicolo" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/>
 *         &lt;element name="NroSottofascicolo" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/>
 *         &lt;element name="NroInserto" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/>
 *         &lt;element name="NroSecondario" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="10"/>
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
@XmlType(name = "FascDiTitolarioType", propOrder = {
    "flagTipo",
    "classificazione",
    "nroFascicolo",
    "nroSottofascicolo",
    "nroInserto",
    "nroSecondario"
})
public class FascDiTitolarioType {

    @XmlElement(name = "FlagTipo", required = true)
    protected String flagTipo;
    @XmlElement(name = "Classificazione", required = true)
    protected ClassificazioneType classificazione;
    @XmlElement(name = "NroFascicolo")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger nroFascicolo;
    @XmlElement(name = "NroSottofascicolo")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger nroSottofascicolo;
    @XmlElement(name = "NroInserto")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger nroInserto;
    @XmlElement(name = "NroSecondario")
    protected String nroSecondario;

    /**
     * Recupera il valore della propriet� flagTipo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagTipo() {
        return flagTipo;
    }

    /**
     * Imposta il valore della propriet� flagTipo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagTipo(String value) {
        this.flagTipo = value;
    }

    /**
     * Recupera il valore della propriet� classificazione.
     * 
     * @return
     *     possible object is
     *     {@link ClassificazioneType }
     *     
     */
    public ClassificazioneType getClassificazione() {
        return classificazione;
    }

    /**
     * Imposta il valore della propriet� classificazione.
     * 
     * @param value
     *     allowed object is
     *     {@link ClassificazioneType }
     *     
     */
    public void setClassificazione(ClassificazioneType value) {
        this.classificazione = value;
    }

    /**
     * Recupera il valore della propriet� nroFascicolo.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNroFascicolo() {
        return nroFascicolo;
    }

    /**
     * Imposta il valore della propriet� nroFascicolo.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNroFascicolo(BigInteger value) {
        this.nroFascicolo = value;
    }

    /**
     * Recupera il valore della propriet� nroSottofascicolo.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNroSottofascicolo() {
        return nroSottofascicolo;
    }

    /**
     * Imposta il valore della propriet� nroSottofascicolo.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNroSottofascicolo(BigInteger value) {
        this.nroSottofascicolo = value;
    }

    /**
     * Recupera il valore della propriet� nroInserto.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNroInserto() {
        return nroInserto;
    }

    /**
     * Imposta il valore della propriet� nroInserto.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNroInserto(BigInteger value) {
        this.nroInserto = value;
    }

    /**
     * Recupera il valore della propriet� nroSecondario.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNroSecondario() {
        return nroSecondario;
    }

    /**
     * Imposta il valore della propriet� nroSecondario.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNroSecondario(String value) {
        this.nroSecondario = value;
    }

}
