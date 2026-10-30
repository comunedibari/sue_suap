//
// Questo file stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrpersa durante la ricompilazione dello schema di origine. 
// Generato il: 2013.03.28 alle 03:45:54 PM CET 
//


package it.wego.cross.client.xml.addud;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per DestinatarioEsternoType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="DestinatarioEsternoType">
 *   &lt;complexContent>
 *     &lt;extension base="{}SoggettoEsternoType">
 *       &lt;sequence>
 *         &lt;element name="PerConoscenza" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="IndirizzoDest" type="{}IndirizzoType" minOccurs="0"/>
 *         &lt;element name="IndirizzoEmailDest" type="{}EmailType" minOccurs="0"/>
 *         &lt;element name="DataOraSpedAlDest" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="MezzoTrasmissioneAlDest" type="{}OggDiTabDiSistemaType" minOccurs="0"/>
 *         &lt;element name="DataRaccomandataAlDest" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="NroRaccomandataAlDest" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DestinatarioEsternoType", propOrder = {
    "perConoscenza",
    "indirizzoDest",
    "indirizzoEmailDest",
    "dataOraSpedAlDest",
    "mezzoTrasmissioneAlDest",
    "dataRaccomandataAlDest",
    "nroRaccomandataAlDest"
})
public class DestinatarioEsternoType
    extends SoggettoEsternoType
{

    @XmlElement(name = "PerConoscenza")
    protected Object perConoscenza;
    @XmlElement(name = "IndirizzoDest")
    protected IndirizzoType indirizzoDest;
    @XmlElement(name = "IndirizzoEmailDest")
    protected EmailType indirizzoEmailDest;
    @XmlElement(name = "DataOraSpedAlDest")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataOraSpedAlDest;
    @XmlElement(name = "MezzoTrasmissioneAlDest")
    protected OggDiTabDiSistemaType mezzoTrasmissioneAlDest;
    @XmlElement(name = "DataRaccomandataAlDest")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataRaccomandataAlDest;
    @XmlElement(name = "NroRaccomandataAlDest")
    protected String nroRaccomandataAlDest;

    /**
     * Recupera il valore della proprietperConoscenza.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getPerConoscenza() {
        return perConoscenza;
    }

    /**
     * Imposta il valore della proprietperConoscenza.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setPerConoscenza(Object value) {
        this.perConoscenza = value;
    }

    /**
     * Recupera il valore della proprietindirizzoDest.
     * 
     * @return
     *     possible object is
     *     {@link IndirizzoType }
     *     
     */
    public IndirizzoType getIndirizzoDest() {
        return indirizzoDest;
    }

    /**
     * Imposta il valore della proprietindirizzoDest.
     * 
     * @param value
     *     allowed object is
     *     {@link IndirizzoType }
     *     
     */
    public void setIndirizzoDest(IndirizzoType value) {
        this.indirizzoDest = value;
    }

    /**
     * Recupera il valore della proprietindirizzoEmailDest.
     * 
     * @return
     *     possible object is
     *     {@link EmailType }
     *     
     */
    public EmailType getIndirizzoEmailDest() {
        return indirizzoEmailDest;
    }

    /**
     * Imposta il valore della proprietindirizzoEmailDest.
     * 
     * @param value
     *     allowed object is
     *     {@link EmailType }
     *     
     */
    public void setIndirizzoEmailDest(EmailType value) {
        this.indirizzoEmailDest = value;
    }

    /**
     * Recupera il valore della proprietdataOraSpedAlDest.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataOraSpedAlDest() {
        return dataOraSpedAlDest;
    }

    /**
     * Imposta il valore della proprietdataOraSpedAlDest.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataOraSpedAlDest(XMLGregorianCalendar value) {
        this.dataOraSpedAlDest = value;
    }

    /**
     * Recupera il valore della proprietmezzoTrasmissioneAlDest.
     * 
     * @return
     *     possible object is
     *     {@link OggDiTabDiSistemaType }
     *     
     */
    public OggDiTabDiSistemaType getMezzoTrasmissioneAlDest() {
        return mezzoTrasmissioneAlDest;
    }

    /**
     * Imposta il valore della proprietmezzoTrasmissioneAlDest.
     * 
     * @param value
     *     allowed object is
     *     {@link OggDiTabDiSistemaType }
     *     
     */
    public void setMezzoTrasmissioneAlDest(OggDiTabDiSistemaType value) {
        this.mezzoTrasmissioneAlDest = value;
    }

    /**
     * Recupera il valore della proprietdataRaccomandataAlDest.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataRaccomandataAlDest() {
        return dataRaccomandataAlDest;
    }

    /**
     * Imposta il valore della proprietdataRaccomandataAlDest.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataRaccomandataAlDest(XMLGregorianCalendar value) {
        this.dataRaccomandataAlDest = value;
    }

    /**
     * Recupera il valore della proprietnroRaccomandataAlDest.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNroRaccomandataAlDest() {
        return nroRaccomandataAlDest;
    }

    /**
     * Imposta il valore della proprietnroRaccomandataAlDest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNroRaccomandataAlDest(String value) {
        this.nroRaccomandataAlDest = value;
    }

}
