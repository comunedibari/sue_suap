
package it.eng.tz.avtmb.xsd.stampsign;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for extraMeta complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="extraMeta">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="titoloAmministrazioneProdotto" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="iriAmministrazioneConservato" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="titoloAmministrazioneConservato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modalitaVerifica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataFineValidita" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dataFineVerifica" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dataProtocolloRicevuto" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="numeroProtocolloRicevuto" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="posizioneTimbro" type="{http://stampsign.xsd.avtmb.tz.eng.it}posizioneTimbro" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "extraMeta", propOrder = {
    "titoloAmministrazioneProdotto",
    "iriAmministrazioneConservato",
    "titoloAmministrazioneConservato",
    "modalitaVerifica",
    "dataFineValidita",
    "dataFineVerifica",
    "dataProtocolloRicevuto",
    "numeroProtocolloRicevuto",
    "posizioneTimbro"
})
public class ExtraMeta {

    @XmlElement(required = true)
    protected String titoloAmministrazioneProdotto;
    @XmlSchemaType(name = "anyURI")
    protected String iriAmministrazioneConservato;
    protected String titoloAmministrazioneConservato;
    protected String modalitaVerifica;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataFineValidita;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataFineVerifica;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataProtocolloRicevuto;
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String numeroProtocolloRicevuto;
    protected PosizioneTimbro posizioneTimbro;

    /**
     * Gets the value of the titoloAmministrazioneProdotto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitoloAmministrazioneProdotto() {
        return titoloAmministrazioneProdotto;
    }

    /**
     * Sets the value of the titoloAmministrazioneProdotto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitoloAmministrazioneProdotto(String value) {
        this.titoloAmministrazioneProdotto = value;
    }

    /**
     * Gets the value of the iriAmministrazioneConservato property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIriAmministrazioneConservato() {
        return iriAmministrazioneConservato;
    }

    /**
     * Sets the value of the iriAmministrazioneConservato property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIriAmministrazioneConservato(String value) {
        this.iriAmministrazioneConservato = value;
    }

    /**
     * Gets the value of the titoloAmministrazioneConservato property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitoloAmministrazioneConservato() {
        return titoloAmministrazioneConservato;
    }

    /**
     * Sets the value of the titoloAmministrazioneConservato property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitoloAmministrazioneConservato(String value) {
        this.titoloAmministrazioneConservato = value;
    }

    /**
     * Gets the value of the modalitaVerifica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModalitaVerifica() {
        return modalitaVerifica;
    }

    /**
     * Sets the value of the modalitaVerifica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModalitaVerifica(String value) {
        this.modalitaVerifica = value;
    }

    /**
     * Gets the value of the dataFineValidita property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataFineValidita() {
        return dataFineValidita;
    }

    /**
     * Sets the value of the dataFineValidita property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataFineValidita(XMLGregorianCalendar value) {
        this.dataFineValidita = value;
    }

    /**
     * Gets the value of the dataFineVerifica property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataFineVerifica() {
        return dataFineVerifica;
    }

    /**
     * Sets the value of the dataFineVerifica property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataFineVerifica(XMLGregorianCalendar value) {
        this.dataFineVerifica = value;
    }

    /**
     * Gets the value of the dataProtocolloRicevuto property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataProtocolloRicevuto() {
        return dataProtocolloRicevuto;
    }

    /**
     * Sets the value of the dataProtocolloRicevuto property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataProtocolloRicevuto(XMLGregorianCalendar value) {
        this.dataProtocolloRicevuto = value;
    }

    /**
     * Gets the value of the numeroProtocolloRicevuto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroProtocolloRicevuto() {
        return numeroProtocolloRicevuto;
    }

    /**
     * Sets the value of the numeroProtocolloRicevuto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroProtocolloRicevuto(String value) {
        this.numeroProtocolloRicevuto = value;
    }

    /**
     * Gets the value of the posizioneTimbro property.
     * 
     * @return
     *     possible object is
     *     {@link PosizioneTimbro }
     *     
     */
    public PosizioneTimbro getPosizioneTimbro() {
        return posizioneTimbro;
    }

    /**
     * Sets the value of the posizioneTimbro property.
     * 
     * @param value
     *     allowed object is
     *     {@link PosizioneTimbro }
     *     
     */
    public void setPosizioneTimbro(PosizioneTimbro value) {
        this.posizioneTimbro = value;
    }

}
