
package it.wego.cross.webservices.client.clear.stubs;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per richiedente complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="richiedente">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codicefiscale_partitaiva" type="{http://www.simo.org}richiedente_codicefiscale_partitaivaType"/>
 *         &lt;element name="tipologia_soggetto" type="{http://www.simo.org}TipiSoggetto"/>
 *         &lt;element name="comune_richiedente" type="{http://www.simo.org}richiedente_comune_richiedenteType" minOccurs="0"/>
 *         &lt;element name="localita_richiedente" type="{http://www.simo.org}richiedente_localita_richiedenteType" minOccurs="0"/>
 *         &lt;element name="denominazione" type="{http://www.simo.org}richiedente_denominazioneType"/>
 *         &lt;element name="indirizzo_richiedente" type="{http://www.simo.org}richiedente_indirizzo_richiedenteType" minOccurs="0"/>
 *         &lt;element name="provincia_richiedente" type="{http://www.simo.org}richiedente_provincia_richiedenteType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "richiedente", propOrder = {
    "codicefiscalePartitaiva",
    "tipologiaSoggetto",
    "comuneRichiedente",
    "localitaRichiedente",
    "denominazione",
    "indirizzoRichiedente",
    "provinciaRichiedente"
})
public class Richiedente {

    @XmlElement(name = "codicefiscale_partitaiva", required = true)
    protected String codicefiscalePartitaiva;
    @XmlElement(name = "tipologia_soggetto", required = true)
    protected TipiSoggetto tipologiaSoggetto;
    @XmlElementRef(name = "comune_richiedente", namespace = "http://www.simo.org", type = JAXBElement.class, required = false)
    protected JAXBElement<String> comuneRichiedente;
    @XmlElementRef(name = "localita_richiedente", namespace = "http://www.simo.org", type = JAXBElement.class, required = false)
    protected JAXBElement<String> localitaRichiedente;
    @XmlElement(required = true)
    protected String denominazione;
    @XmlElementRef(name = "indirizzo_richiedente", namespace = "http://www.simo.org", type = JAXBElement.class, required = false)
    protected JAXBElement<String> indirizzoRichiedente;
    @XmlElement(name = "provincia_richiedente", required = true)
    protected String provinciaRichiedente;

    /**
     * Recupera il valore della proprietà codicefiscalePartitaiva.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodicefiscalePartitaiva() {
        return codicefiscalePartitaiva;
    }

    /**
     * Imposta il valore della proprietà codicefiscalePartitaiva.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodicefiscalePartitaiva(String value) {
        this.codicefiscalePartitaiva = value;
    }

    /**
     * Recupera il valore della proprietà tipologiaSoggetto.
     * 
     * @return
     *     possible object is
     *     {@link TipiSoggetto }
     *     
     */
    public TipiSoggetto getTipologiaSoggetto() {
        return tipologiaSoggetto;
    }

    /**
     * Imposta il valore della proprietà tipologiaSoggetto.
     * 
     * @param value
     *     allowed object is
     *     {@link TipiSoggetto }
     *     
     */
    public void setTipologiaSoggetto(TipiSoggetto value) {
        this.tipologiaSoggetto = value;
    }

    /**
     * Recupera il valore della proprietà comuneRichiedente.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getComuneRichiedente() {
        return comuneRichiedente;
    }

    /**
     * Imposta il valore della proprietà comuneRichiedente.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setComuneRichiedente(JAXBElement<String> value) {
        this.comuneRichiedente = value;
    }

    /**
     * Recupera il valore della proprietà localitaRichiedente.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getLocalitaRichiedente() {
        return localitaRichiedente;
    }

    /**
     * Imposta il valore della proprietà localitaRichiedente.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setLocalitaRichiedente(JAXBElement<String> value) {
        this.localitaRichiedente = value;
    }

    /**
     * Recupera il valore della proprietà denominazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDenominazione() {
        return denominazione;
    }

    /**
     * Imposta il valore della proprietà denominazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDenominazione(String value) {
        this.denominazione = value;
    }

    /**
     * Recupera il valore della proprietà indirizzoRichiedente.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getIndirizzoRichiedente() {
        return indirizzoRichiedente;
    }

    /**
     * Imposta il valore della proprietà indirizzoRichiedente.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setIndirizzoRichiedente(JAXBElement<String> value) {
        this.indirizzoRichiedente = value;
    }

    /**
     * Recupera il valore della proprietà provinciaRichiedente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvinciaRichiedente() {
        return provinciaRichiedente;
    }

    /**
     * Imposta il valore della proprietà provinciaRichiedente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvinciaRichiedente(String value) {
        this.provinciaRichiedente = value;
    }

}
