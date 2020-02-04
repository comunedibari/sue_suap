//
// Questo file stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrpersa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.07.14 alle 03:42:18 PM CEST 
//


package it.wego.cross.client.xml.getmetadata;

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
 *         &lt;element name="DataOraAssegnazione" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="FlagPerConoscenza" type="{}FlagSiNoType"/>
 *         &lt;element name="LivelloPriorita" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="ALTA"/>
 *               &lt;enumeration value="MEDIA"/>
 *               &lt;enumeration value="BASSA"/>
 *               &lt;enumeration value=""/>
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
 *                         &lt;enumeration value="PresaInCarico"/>
 *                         &lt;enumeration value="PresaVisione"/>
 *                         &lt;enumeration value="Entrambe"/>
 *                         &lt;enumeration value=""/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="FlagPrimaUltima">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;enumeration value="P"/>
 *                         &lt;enumeration value="U"/>
 *                         &lt;enumeration value=""/>
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
    "dataOraAssegnazione",
    "flagPerConoscenza",
    "livelloPriorita",
    "motivoAssegnazione",
    "messaggioAssegnazione",
    "decorrenzaAssegnazione",
    "richiestaPresaInCaricoEntroGiorniNro",
    "richiestaRispostaEntroGiorniNro",
    "richiestaNotificaMail"
})
public class AssegnazioneInternaType
    extends SoggettoInternoType
{

    @XmlElement(name = "DataOraAssegnazione", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataOraAssegnazione;
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
     * Recupera il valore della proprietdataOraAssegnazione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataOraAssegnazione() {
        return dataOraAssegnazione;
    }

    /**
     * Imposta il valore della proprietdataOraAssegnazione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataOraAssegnazione(XMLGregorianCalendar value) {
        this.dataOraAssegnazione = value;
    }

    /**
     * Recupera il valore della proprietflagPerConoscenza.
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
     * Imposta il valore della proprietflagPerConoscenza.
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
     * Recupera il valore della proprietlivelloPriorita.
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
     * Imposta il valore della proprietlivelloPriorita.
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
     * Recupera il valore della proprietmotivoAssegnazione.
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
     * Imposta il valore della proprietmotivoAssegnazione.
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
     * Recupera il valore della proprietmessaggioAssegnazione.
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
     * Imposta il valore della proprietmessaggioAssegnazione.
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
     * Recupera il valore della proprietdecorrenzaAssegnazione.
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
     * Imposta il valore della proprietdecorrenzaAssegnazione.
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
     * Recupera il valore della proprietrichiestaPresaInCaricoEntroGiorniNro.
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
     * Imposta il valore della proprietrichiestaPresaInCaricoEntroGiorniNro.
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
     * Recupera il valore della proprietrichiestaRispostaEntroGiorniNro.
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
     * Imposta il valore della proprietrichiestaRispostaEntroGiorniNro.
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
     * Recupera il valore della proprietrichiestaNotificaMail.
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
     * Imposta il valore della proprietrichiestaNotificaMail.
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
     *               &lt;enumeration value="PresaInCarico"/>
     *               &lt;enumeration value="PresaVisione"/>
     *               &lt;enumeration value="Entrambe"/>
     *               &lt;enumeration value=""/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="FlagPrimaUltima">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;enumeration value="P"/>
     *               &lt;enumeration value="U"/>
     *               &lt;enumeration value=""/>
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
         * Recupera il valore della proprietnotificaPer.
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
         * Imposta il valore della proprietnotificaPer.
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
         * Recupera il valore della proprietflagPrimaUltima.
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
         * Imposta il valore della proprietflagPrimaUltima.
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
