package it.wego.auriga.xml;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * Contiene le informazioni di come trattare/salvare i file in attach in input al Web Service
 * 
 * <p>Classe Java per VersioneElettronicaType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="VersioneElettronicaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NroAttachmentAssociato" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/>
 *         &lt;element name="NomeFile" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="NroVers" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FlagVersPubblicata" type="{}FlagSiNoType" minOccurs="0"/>
 *         &lt;element name="FlagDaScansione" type="{}FlagSiNoType" minOccurs="0"/>
 *         &lt;element name="Note" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FlagOCR" type="{}FlagSiNoType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VersioneElettronicaType", propOrder = {
    "nroAttachmentAssociato",
    "nomeFile",
    "nroVers",
    "flagVersPubblicata",
    "flagDaScansione",
    "note",
    "flagOCR"
})
public class VersioneElettronicaType {

    @XmlElement(name = "NroAttachmentAssociato", required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger nroAttachmentAssociato;
    @XmlElement(name = "NomeFile", required = true)
    protected String nomeFile;
    @XmlElement(name = "NroVers")
    protected String nroVers;
    @XmlElement(name = "FlagVersPubblicata", defaultValue = "1")
    protected String flagVersPubblicata;
    @XmlElement(name = "FlagDaScansione", defaultValue = "1")
    protected String flagDaScansione;
    @XmlElement(name = "Note")
    protected String note;
    @XmlElement(name = "FlagOCR", defaultValue = "1")
    protected String flagOCR;

    /**
     * Recupera il valore della propriet� nroAttachmentAssociato.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNroAttachmentAssociato() {
        return nroAttachmentAssociato;
    }

    /**
     * Imposta il valore della propriet� nroAttachmentAssociato.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNroAttachmentAssociato(BigInteger value) {
        this.nroAttachmentAssociato = value;
    }

    /**
     * Recupera il valore della propriet� nomeFile.
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
     * Imposta il valore della propriet� nomeFile.
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
     * Recupera il valore della propriet� nroVers.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNroVers() {
        return nroVers;
    }

    /**
     * Imposta il valore della propriet� nroVers.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNroVers(String value) {
        this.nroVers = value;
    }

    /**
     * Recupera il valore della propriet� flagVersPubblicata.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagVersPubblicata() {
        return flagVersPubblicata;
    }

    /**
     * Imposta il valore della propriet� flagVersPubblicata.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagVersPubblicata(String value) {
        this.flagVersPubblicata = value;
    }

    /**
     * Recupera il valore della propriet� flagDaScansione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagDaScansione() {
        return flagDaScansione;
    }

    /**
     * Imposta il valore della propriet� flagDaScansione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagDaScansione(String value) {
        this.flagDaScansione = value;
    }

    /**
     * Recupera il valore della propriet� note.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNote() {
        return note;
    }

    /**
     * Imposta il valore della propriet� note.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNote(String value) {
        this.note = value;
    }

    /**
     * Recupera il valore della propriet� flagOCR.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagOCR() {
        return flagOCR;
    }

    /**
     * Imposta il valore della propriet� flagOCR.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagOCR(String value) {
        this.flagOCR = value;
    }

}
