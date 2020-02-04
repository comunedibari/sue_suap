
package it.wego.cross.webservices.client.clear.stubs;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per localizzazione complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="localizzazione">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="localizzazione_provincia" type="{http://www.simo.org}localizzazione_localizzazione_provinciaType"/>
 *         &lt;element name="localizzazione_comune" type="{http://www.simo.org}localizzazione_localizzazione_comuneType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "localizzazione", propOrder = {
    "localizzazioneProvincia",
    "localizzazioneComune"
})
public class Localizzazione {

    @XmlElement(name = "localizzazione_provincia", required = true)
    protected String localizzazioneProvincia;
    @XmlElementRef(name = "localizzazione_comune", namespace = "http://www.simo.org", type = JAXBElement.class, required = false)
    protected JAXBElement<String> localizzazioneComune;

    /**
     * Recupera il valore della proprietà localizzazioneProvincia.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocalizzazioneProvincia() {
        return localizzazioneProvincia;
    }

    /**
     * Imposta il valore della proprietà localizzazioneProvincia.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocalizzazioneProvincia(String value) {
        this.localizzazioneProvincia = value;
    }

    /**
     * Recupera il valore della proprietà localizzazioneComune.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getLocalizzazioneComune() {
        return localizzazioneComune;
    }

    /**
     * Imposta il valore della proprietà localizzazioneComune.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setLocalizzazioneComune(JAXBElement<String> value) {
        this.localizzazioneComune = value;
    }

}
