
package it.wego.cross.webservices.client.clear.stubs;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for simo_crea_procedimento complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="simo_crea_procedimento">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codice" type="{http://www.simo.org}simo_crea_procedimento_codiceType"/>
 *         &lt;element name="descrizione" type="{http://www.simo.org}simo_crea_procedimento_descrizioneType"/>
 *         &lt;element name="eventi" type="{http://www.simo.org}eventoArray" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "simo_crea_procedimento", propOrder = {
    "codice",
    "descrizione",
    "eventi"
})
public class SimoCreaProcedimento {

    @XmlElement(required = true)
    protected String codice;
    @XmlElement(required = true)
    protected String descrizione;
    @XmlElementRef(name = "eventi", namespace = "http://www.simo.org", type = JAXBElement.class)
    protected JAXBElement<EventoArray> eventi;

    /**
     * Gets the value of the codice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodice() {
        return codice;
    }

    /**
     * Sets the value of the codice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodice(String value) {
        this.codice = value;
    }

    /**
     * Gets the value of the descrizione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Sets the value of the descrizione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizione(String value) {
        this.descrizione = value;
    }

    /**
     * Gets the value of the eventi property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link EventoArray }{@code >}
     *     
     */
    public JAXBElement<EventoArray> getEventi() {
        return eventi;
    }

    /**
     * Sets the value of the eventi property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link EventoArray }{@code >}
     *     
     */
    public void setEventi(JAXBElement<EventoArray> value) {
        this.eventi = value;
    }
}
