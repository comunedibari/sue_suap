package it.wego.auriga.xml;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per AssegnazioneInternaType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="AssegnazioneInternaType">
 *   &lt;complexContent>
 *     &lt;extension base="{}SoggettoInternoType">
 *       &lt;sequence>
 *         &lt;element name="FlagPerConoscenza" type="{}FlagSiNoType"/>
 *         &lt;element name="LivelloPriorita" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="ALTA|MEDIA|BASSA"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="MotivoAssegnazione" type="{}OggDiTabDiSistemaType" minOccurs="0"/>
 *         &lt;element name="MessaggioAssegnazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DecorrenzaAssegnazione" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="RichiestaPresaInCaricoEntroGiorniNro" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/>
 *         &lt;element name="RichiestaRispostaEntroGiorniNro" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/>
 *         &lt;element name="RichiestaNotificaMail" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="IndirizzoEmailNotifica" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *                   &lt;element name="NotificaPer">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;pattern value="PresaInCarico|PresaVisione|Entrambe"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="FlagPrimaUltima">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;pattern value="P|U"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AssegnazioneInternaType", propOrder = {
    "flagPerConoscenza",
    "livelloPriorita",
    "motivoAssegnazione",
    "messaggioAssegnazione",
    "decorrenzaAssegnazione",
    "richiestaPresaInCaricoEntroGiorniNro",
    "richiestaRispostaEntroGiorniNro",
    "richiestaNotificaMail"
})

public class AssegnazioneInternaType extends SoggettoInternoType
{

    @XmlElement(name = "FlagPerConoscenza", required = true, defaultValue = "0")
    protected String flagPerConoscenza;
    @XmlElement(name = "LivelloPriorita")
    protected String livelloPriorita;
    @XmlElement(name = "MotivoAssegnazione")
    protected OggDiTabDiSistemaType motivoAssegnazione;
    @XmlElement(name = "MessaggioAssegnazione")
    protected String messaggioAssegnazione;
    @XmlElement(name = "DecorrenzaAssegnazione")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar decorrenzaAssegnazione;
    @XmlElement(name = "RichiestaPresaInCaricoEntroGiorniNro")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger richiestaPresaInCaricoEntroGiorniNro;
    @XmlElement(name = "RichiestaRispostaEntroGiorniNro")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger richiestaRispostaEntroGiorniNro;
    @XmlElement(name = "RichiestaNotificaMail")
    protected AssegnazioneInternaType.RichiestaNotificaMail richiestaNotificaMail;

    /**
     * Recupera il valore della propriet� flagPerConoscenza.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagPerConoscenza() {
        return flagPerConoscenza;
    }

    /**
     * Imposta il valore della propriet� flagPerConoscenza.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagPerConoscenza(String value) {
        this.flagPerConoscenza = value;
    }

    /**
     * Recupera il valore della propriet� livelloPriorita.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLivelloPriorita() {
        return livelloPriorita;
    }

    /**
     * Imposta il valore della propriet� livelloPriorita.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLivelloPriorita(String value) {
        this.livelloPriorita = value;
    }

    /**
     * Recupera il valore della propriet� motivoAssegnazione.
     * 
     * @return
     *     possible object is
     *     {@link OggDiTabDiSistemaType }
     *     
     */
    public OggDiTabDiSistemaType getMotivoAssegnazione() {
        return motivoAssegnazione;
    }

    /**
     * Imposta il valore della propriet� motivoAssegnazione.
     * 
     * @param value
     *     allowed object is
     *     {@link OggDiTabDiSistemaType }
     *     
     */
    public void setMotivoAssegnazione(OggDiTabDiSistemaType value) {
        this.motivoAssegnazione = value;
    }

    /**
     * Recupera il valore della propriet� messaggioAssegnazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessaggioAssegnazione() {
        return messaggioAssegnazione;
    }

    /**
     * Imposta il valore della propriet� messaggioAssegnazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessaggioAssegnazione(String value) {
        this.messaggioAssegnazione = value;
    }

    /**
     * Recupera il valore della propriet� decorrenzaAssegnazione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDecorrenzaAssegnazione() {
        return decorrenzaAssegnazione;
    }

    /**
     * Imposta il valore della propriet� decorrenzaAssegnazione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDecorrenzaAssegnazione(XMLGregorianCalendar value) {
        this.decorrenzaAssegnazione = value;
    }

    /**
     * Recupera il valore della propriet� richiestaPresaInCaricoEntroGiorniNro.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRichiestaPresaInCaricoEntroGiorniNro() {
        return richiestaPresaInCaricoEntroGiorniNro;
    }

    /**
     * Imposta il valore della propriet� richiestaPresaInCaricoEntroGiorniNro.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRichiestaPresaInCaricoEntroGiorniNro(BigInteger value) {
        this.richiestaPresaInCaricoEntroGiorniNro = value;
    }

    /**
     * Recupera il valore della propriet� richiestaRispostaEntroGiorniNro.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRichiestaRispostaEntroGiorniNro() {
        return richiestaRispostaEntroGiorniNro;
    }

    /**
     * Imposta il valore della propriet� richiestaRispostaEntroGiorniNro.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRichiestaRispostaEntroGiorniNro(BigInteger value) {
        this.richiestaRispostaEntroGiorniNro = value;
    }

    /**
     * Recupera il valore della propriet� richiestaNotificaMail.
     * 
     * @return
     *     possible object is
     *     {@link AssegnazioneInternaType.RichiestaNotificaMail }
     *     
     */
    public AssegnazioneInternaType.RichiestaNotificaMail getRichiestaNotificaMail() {
        return richiestaNotificaMail;
    }

    /**
     * Imposta il valore della propriet� richiestaNotificaMail.
     * 
     * @param value
     *     allowed object is
     *     {@link AssegnazioneInternaType.RichiestaNotificaMail }
     *     
     */
    public void setRichiestaNotificaMail(AssegnazioneInternaType.RichiestaNotificaMail value) {
        this.richiestaNotificaMail = value;
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
     *         &lt;element name="IndirizzoEmailNotifica" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
     *         &lt;element name="NotificaPer">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;pattern value="PresaInCarico|PresaVisione|Entrambe"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="FlagPrimaUltima">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;pattern value="P|U"/>
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
    @XmlType(name = "", propOrder = {
        "indirizzoEmailNotifica",
        "notificaPer",
        "flagPrimaUltima"
    })
    public static class RichiestaNotificaMail {

        @XmlElement(name = "IndirizzoEmailNotifica", required = true)
        protected List<String> indirizzoEmailNotifica;
        @XmlElement(name = "NotificaPer", required = true)
        protected String notificaPer;
        @XmlElement(name = "FlagPrimaUltima", required = true, defaultValue = "P")
        protected String flagPrimaUltima;

        /**
         * Gets the value of the indirizzoEmailNotifica property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the indirizzoEmailNotifica property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getIndirizzoEmailNotifica().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getIndirizzoEmailNotifica() {
            if (indirizzoEmailNotifica == null) {
                indirizzoEmailNotifica = new ArrayList<String>();
            }
            return this.indirizzoEmailNotifica;
        }

        /**
         * Recupera il valore della propriet� notificaPer.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNotificaPer() {
            return notificaPer;
        }

        /**
         * Imposta il valore della propriet� notificaPer.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNotificaPer(String value) {
            this.notificaPer = value;
        }

        /**
         * Recupera il valore della propriet� flagPrimaUltima.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFlagPrimaUltima() {
            return flagPrimaUltima;
        }

        /**
         * Imposta il valore della propriet� flagPrimaUltima.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFlagPrimaUltima(String value) {
            this.flagPrimaUltima = value;
        }

    }

}
