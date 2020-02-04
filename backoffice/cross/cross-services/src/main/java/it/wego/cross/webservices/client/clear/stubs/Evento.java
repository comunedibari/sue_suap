
package it.wego.cross.webservices.client.clear.stubs;

import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per evento complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="evento">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codice" type="{http://www.simo.org}evento_codiceType"/>
 *         &lt;element name="flg_riavvio" type="{http://www.simo.org}SiNo"/>
 *         &lt;element name="descrizione" type="{http://www.simo.org}evento_descrizioneType"/>
 *         &lt;element name="flg_chiusura" type="{http://www.simo.org}SiNo"/>
 *         &lt;element name="termini_evasione" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="stato_post_evento" type="{http://www.simo.org}TipiStatoPost"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "evento", propOrder = {
    "codice",
    "flgRiavvio",
    "descrizione",
    "flgChiusura",
    "terminiEvasione",
    "statoPostEvento"
})
public class Evento {

    @XmlElement(required = true)
    protected String codice;
    @XmlElement(name = "flg_riavvio", required = true)
    protected SiNo flgRiavvio;
    @XmlElement(required = true)
    protected String descrizione;
    @XmlElement(name = "flg_chiusura", required = true)
    protected SiNo flgChiusura;
    @XmlElementRef(name = "termini_evasione", namespace = "http://www.simo.org", type = JAXBElement.class, required = false)
    protected JAXBElement<BigInteger> terminiEvasione;
    @XmlElement(name = "stato_post_evento", required = true)
    protected TipiStatoPost statoPostEvento;

    /**
     * Recupera il valore della proprietà codice.
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
     * Imposta il valore della proprietà codice.
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
     * Recupera il valore della proprietà flgRiavvio.
     * 
     * @return
     *     possible object is
     *     {@link SiNo }
     *     
     */
    public SiNo getFlgRiavvio() {
        return flgRiavvio;
    }

    /**
     * Imposta il valore della proprietà flgRiavvio.
     * 
     * @param value
     *     allowed object is
     *     {@link SiNo }
     *     
     */
    public void setFlgRiavvio(SiNo value) {
        this.flgRiavvio = value;
    }

    /**
     * Recupera il valore della proprietà descrizione.
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
     * Imposta il valore della proprietà descrizione.
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
     * Recupera il valore della proprietà flgChiusura.
     * 
     * @return
     *     possible object is
     *     {@link SiNo }
     *     
     */
    public SiNo getFlgChiusura() {
        return flgChiusura;
    }

    /**
     * Imposta il valore della proprietà flgChiusura.
     * 
     * @param value
     *     allowed object is
     *     {@link SiNo }
     *     
     */
    public void setFlgChiusura(SiNo value) {
        this.flgChiusura = value;
    }

    /**
     * Recupera il valore della proprietà terminiEvasione.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     *     
     */
    public JAXBElement<BigInteger> getTerminiEvasione() {
        return terminiEvasione;
    }

    /**
     * Imposta il valore della proprietà terminiEvasione.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     *     
     */
    public void setTerminiEvasione(JAXBElement<BigInteger> value) {
        this.terminiEvasione = value;
    }

    /**
     * Recupera il valore della proprietà statoPostEvento.
     * 
     * @return
     *     possible object is
     *     {@link TipiStatoPost }
     *     
     */
    public TipiStatoPost getStatoPostEvento() {
        return statoPostEvento;
    }

    /**
     * Imposta il valore della proprietà statoPostEvento.
     * 
     * @param value
     *     allowed object is
     *     {@link TipiStatoPost }
     *     
     */
    public void setStatoPostEvento(TipiStatoPost value) {
        this.statoPostEvento = value;
    }

}
