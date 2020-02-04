//
// Questo file stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrpersa durante la ricompilazione dello schema di origine. 
// Generato il: PM.03.05 alle 07:37:39 PM CET 
//


package it.wego.cross.client.xml.checkin;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EstremiXIdentificazioneDoc" type="{}EstremiXIdentificazioneDocType"/>
 *         &lt;element name="NroVersioneEstratta" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/>
 *         &lt;element name="NuovaVersioneElettronica">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="NomeFile" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="AttivaVerificaFirma" type="{}FlagSiNoType" minOccurs="0"/>
 *                   &lt;element name="Formato" type="{}OggDiTabDiSistemaType" minOccurs="0"/>
 *                   &lt;element name="CodVersione" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="30"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="FlagPubblicata" type="{}FlagSiNoType"/>
 *                   &lt;element name="DerivaDaScansione" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *                   &lt;element name="DataScansione" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                   &lt;element name="UtenteScansione" type="{}UserType" minOccurs="0"/>
 *                   &lt;element name="SpecificheScansione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="Note" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="AttributoAdd" type="{}AttributoAddizionaleType" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
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
@XmlType(name = "", propOrder = {
    "estremiXIdentificazioneDoc",
    "nroVersioneEstratta",
    "nuovaVersioneElettronica"
})
@XmlRootElement(name = "DatiXCheckIn")
public class DatiXCheckIn {

    @XmlElement(name = "EstremiXIdentificazioneDoc", required = true)
    protected EstremiXIdentificazioneDocType estremiXIdentificazioneDoc;
    @XmlElement(name = "NroVersioneEstratta")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger nroVersioneEstratta;
    @XmlElement(name = "NuovaVersioneElettronica", required = true)
    protected DatiXCheckIn.NuovaVersioneElettronica nuovaVersioneElettronica;

    /**
     * Recupera il valore della proprietestremiXIdentificazioneDoc.
     * 
     * @return
     *     possible object is
     *     {@link EstremiXIdentificazioneDocType }
     *     
     */
    public EstremiXIdentificazioneDocType getEstremiXIdentificazioneDoc() {
        return estremiXIdentificazioneDoc;
    }

    /**
     * Imposta il valore della proprietestremiXIdentificazioneDoc.
     * 
     * @param value
     *     allowed object is
     *     {@link EstremiXIdentificazioneDocType }
     *     
     */
    public void setEstremiXIdentificazioneDoc(EstremiXIdentificazioneDocType value) {
        this.estremiXIdentificazioneDoc = value;
    }

    /**
     * Recupera il valore della proprietnroVersioneEstratta.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNroVersioneEstratta() {
        return nroVersioneEstratta;
    }

    /**
     * Imposta il valore della proprietnroVersioneEstratta.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNroVersioneEstratta(BigInteger value) {
        this.nroVersioneEstratta = value;
    }

    /**
     * Recupera il valore della proprietnuovaVersioneElettronica.
     * 
     * @return
     *     possible object is
     *     {@link DatiXCheckIn.NuovaVersioneElettronica }
     *     
     */
    public DatiXCheckIn.NuovaVersioneElettronica getNuovaVersioneElettronica() {
        return nuovaVersioneElettronica;
    }

    /**
     * Imposta il valore della proprietnuovaVersioneElettronica.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiXCheckIn.NuovaVersioneElettronica }
     *     
     */
    public void setNuovaVersioneElettronica(DatiXCheckIn.NuovaVersioneElettronica value) {
        this.nuovaVersioneElettronica = value;
    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="NomeFile" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="AttivaVerificaFirma" type="{}FlagSiNoType" minOccurs="0"/>
     *         &lt;element name="Formato" type="{}OggDiTabDiSistemaType" minOccurs="0"/>
     *         &lt;element name="CodVersione" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="30"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="FlagPubblicata" type="{}FlagSiNoType"/>
     *         &lt;element name="DerivaDaScansione" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
     *         &lt;element name="DataScansione" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *         &lt;element name="UtenteScansione" type="{}UserType" minOccurs="0"/>
     *         &lt;element name="SpecificheScansione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="Note" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="AttributoAdd" type="{}AttributoAddizionaleType" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "nomeFile",
        "attivaVerificaFirma",
        "formato",
        "codVersione",
        "flagPubblicata",
        "derivaDaScansione",
        "dataScansione",
        "utenteScansione",
        "specificheScansione",
        "note",
        "attributoAdd"
    })
    public static class NuovaVersioneElettronica {

        @XmlElement(name = "NomeFile", required = true)
        protected String nomeFile;
        @XmlElement(name = "AttivaVerificaFirma", defaultValue = "0")
        protected String attivaVerificaFirma;
        @XmlElement(name = "Formato")
        protected OggDiTabDiSistemaType formato;
        @XmlElement(name = "CodVersione")
        protected String codVersione;
        @XmlElement(name = "FlagPubblicata", required = true)
        protected String flagPubblicata;
        @XmlElement(name = "DerivaDaScansione")
        protected Object derivaDaScansione;
        @XmlElement(name = "DataScansione")
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar dataScansione;
        @XmlElement(name = "UtenteScansione")
        protected UserType utenteScansione;
        @XmlElement(name = "SpecificheScansione")
        protected String specificheScansione;
        @XmlElement(name = "Note", required = true)
        protected String note;
        @XmlElement(name = "AttributoAdd")
        protected List<AttributoAddizionaleType> attributoAdd;

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
         * Recupera il valore della proprietattivaVerificaFirma.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAttivaVerificaFirma() {
            return attivaVerificaFirma;
        }

        /**
         * Imposta il valore della proprietattivaVerificaFirma.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAttivaVerificaFirma(String value) {
            this.attivaVerificaFirma = value;
        }

        /**
         * Recupera il valore della proprietformato.
         * 
         * @return
         *     possible object is
         *     {@link OggDiTabDiSistemaType }
         *     
         */
        public OggDiTabDiSistemaType getFormato() {
            return formato;
        }

        /**
         * Imposta il valore della proprietformato.
         * 
         * @param value
         *     allowed object is
         *     {@link OggDiTabDiSistemaType }
         *     
         */
        public void setFormato(OggDiTabDiSistemaType value) {
            this.formato = value;
        }

        /**
         * Recupera il valore della proprietcodVersione.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCodVersione() {
            return codVersione;
        }

        /**
         * Imposta il valore della proprietcodVersione.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCodVersione(String value) {
            this.codVersione = value;
        }

        /**
         * Recupera il valore della proprietflagPubblicata.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFlagPubblicata() {
            return flagPubblicata;
        }

        /**
         * Imposta il valore della proprietflagPubblicata.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFlagPubblicata(String value) {
            this.flagPubblicata = value;
        }

        /**
         * Recupera il valore della proprietderivaDaScansione.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getDerivaDaScansione() {
            return derivaDaScansione;
        }

        /**
         * Imposta il valore della proprietderivaDaScansione.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setDerivaDaScansione(Object value) {
            this.derivaDaScansione = value;
        }

        /**
         * Recupera il valore della proprietdataScansione.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getDataScansione() {
            return dataScansione;
        }

        /**
         * Imposta il valore della proprietdataScansione.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setDataScansione(XMLGregorianCalendar value) {
            this.dataScansione = value;
        }

        /**
         * Recupera il valore della proprietutenteScansione.
         * 
         * @return
         *     possible object is
         *     {@link UserType }
         *     
         */
        public UserType getUtenteScansione() {
            return utenteScansione;
        }

        /**
         * Imposta il valore della proprietutenteScansione.
         * 
         * @param value
         *     allowed object is
         *     {@link UserType }
         *     
         */
        public void setUtenteScansione(UserType value) {
            this.utenteScansione = value;
        }

        /**
         * Recupera il valore della proprietspecificheScansione.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSpecificheScansione() {
            return specificheScansione;
        }

        /**
         * Imposta il valore della proprietspecificheScansione.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSpecificheScansione(String value) {
            this.specificheScansione = value;
        }

        /**
         * Recupera il valore della proprietnote.
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
         * Imposta il valore della proprietnote.
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
         * Gets the value of the attributoAdd property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the attributoAdd property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAttributoAdd().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link AttributoAddizionaleType }
         * 
         * 
         */
        public List<AttributoAddizionaleType> getAttributoAdd() {
            if (attributoAdd == null) {
                attributoAdd = new ArrayList<AttributoAddizionaleType>();
            }
            return this.attributoAdd;
        }

    }

}
