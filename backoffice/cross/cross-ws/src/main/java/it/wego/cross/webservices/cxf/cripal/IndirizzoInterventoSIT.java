
package it.wego.cross.webservices.cxf.cripal;

import it.wego.cross.webservices.cxf.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per indirizzo_interventoSIT complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="indirizzo_interventoSIT">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id_indirizzo_intervento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codice_sit" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="localita" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="indirizzo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="civico" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cap" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="interno_numero" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="interno_lettera" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="interno_scala" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="piano" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "indirizzo_interventoSIT", propOrder = {
    "idIndirizzoIntervento",
    "codiceSit",
    "localita",
    "indirizzo",
    "civico",
    "cap",
    "internoNumero",
    "internoLettera",
    "internoScala",
    "piano"
})
public class IndirizzoInterventoSIT {

    @XmlElement(name = "id_indirizzo_intervento", required = true)
    protected String idIndirizzoIntervento;
    @XmlElement(name = "codice_sit", required = true)
    protected String codiceSit;
    @XmlElement(required = true)
    protected String localita;
    @XmlElement(required = true)
    protected String indirizzo;
    @XmlElement(required = true)
    protected String civico;
    @XmlElement(required = true)
    protected String cap;
    @XmlElement(name = "interno_numero", required = true)
    protected String internoNumero;
    @XmlElement(name = "interno_lettera", required = true)
    protected String internoLettera;
    @XmlElement(name = "interno_scala", required = true)
    protected String internoScala;
    @XmlElement(required = true)
    protected String piano;

    /**
     * Recupera il valore della propriet idIndirizzoIntervento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdIndirizzoIntervento() {
        return idIndirizzoIntervento;
    }

    /**
     * Imposta il valore della propriet idIndirizzoIntervento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdIndirizzoIntervento(String value) {
        this.idIndirizzoIntervento = value;
    }

    /**
     * Recupera il valore della propriet codiceSit.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceSit() {
        return codiceSit;
    }

    /**
     * Imposta il valore della propriet codiceSit.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceSit(String value) {
        this.codiceSit = value;
    }

    /**
     * Recupera il valore della propriet localita.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocalita() {
        return localita;
    }

    /**
     * Imposta il valore della propriet localita.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocalita(String value) {
        this.localita = value;
    }

    /**
     * Recupera il valore della propriet indirizzo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndirizzo() {
        return indirizzo;
    }

    /**
     * Imposta il valore della propriet indirizzo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndirizzo(String value) {
        this.indirizzo = value;
    }

    /**
     * Recupera il valore della propriet civico.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCivico() {
        return civico;
    }

    /**
     * Imposta il valore della propriet civico.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCivico(String value) {
        this.civico = value;
    }

    /**
     * Recupera il valore della propriet cap.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCap() {
        return cap;
    }

    /**
     * Imposta il valore della propriet cap.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCap(String value) {
        this.cap = value;
    }

    /**
     * Recupera il valore della propriet internoNumero.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInternoNumero() {
        return internoNumero;
    }

    /**
     * Imposta il valore della propriet internoNumero.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInternoNumero(String value) {
        this.internoNumero = value;
    }

    /**
     * Recupera il valore della propriet internoLettera.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInternoLettera() {
        return internoLettera;
    }

    /**
     * Imposta il valore della propriet internoLettera.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInternoLettera(String value) {
        this.internoLettera = value;
    }

    /**
     * Recupera il valore della propriet internoScala.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInternoScala() {
        return internoScala;
    }

    /**
     * Imposta il valore della propriet internoScala.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInternoScala(String value) {
        this.internoScala = value;
    }

    /**
     * Recupera il valore della propriet piano.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPiano() {
        return piano;
    }

    /**
     * Imposta il valore della propriet piano.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPiano(String value) {
        this.piano = value;
    }

}
