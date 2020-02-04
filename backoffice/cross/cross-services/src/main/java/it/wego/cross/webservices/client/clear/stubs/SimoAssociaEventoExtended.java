
package it.wego.cross.webservices.client.clear.stubs;

import java.math.BigDecimal;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per simo_associa_evento_extended complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="simo_associa_evento_extended">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codice_protocollo" type="{http://www.simo.org}codice_protocollo"/>
 *         &lt;element name="codice_pratica_simo" type="{http://www.simo.org}codice_pratica_simo"/>
 *         &lt;element name="codice_evento" type="{http://www.simo.org}simo_associa_evento_extended_codice_eventoType"/>
 *         &lt;element name="annotazioni_evento" type="{http://www.simo.org}simo_associa_evento_extended_annotazioni_eventoType"/>
 *         &lt;element name="esito" type="{http://www.simo.org}esito"/>
 *         &lt;element name="annotazioni_esito" type="{http://www.simo.org}simo_associa_evento_extended_annotazioni_esitoType" minOccurs="0"/>
 *         &lt;element name="importo_finanziato" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="data_ora_ricevimento" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="data_ora_protocollazione" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="verso" type="{http://www.simo.org}TipiVerso"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "simo_associa_evento_extended", propOrder = {
    "codiceProtocollo",
    "codicePraticaSimo",
    "codiceEvento",
    "annotazioniEvento",
    "esito",
    "annotazioniEsito",
    "importoFinanziato",
    "dataOraRicevimento",
    "dataOraProtocollazione",
    "verso"
})
public class SimoAssociaEventoExtended {

    @XmlElement(name = "codice_protocollo", required = true)
    protected CodiceProtocollo codiceProtocollo;
    @XmlElement(name = "codice_pratica_simo", required = true)
    protected CodicePraticaSimo codicePraticaSimo;
    @XmlElement(name = "codice_evento", required = true)
    protected String codiceEvento;
    @XmlElement(name = "annotazioni_evento", required = true, nillable = true)
    protected String annotazioniEvento;
    @XmlElement(required = true)
    protected Esito esito;
    @XmlElementRef(name = "annotazioni_esito", namespace = "http://www.simo.org", type = JAXBElement.class, required = false)
    protected JAXBElement<String> annotazioniEsito;
    @XmlElementRef(name = "importo_finanziato", namespace = "http://www.simo.org", type = JAXBElement.class, required = false)
    protected JAXBElement<BigDecimal> importoFinanziato;
    @XmlElement(name = "data_ora_ricevimento", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataOraRicevimento;
    @XmlElement(name = "data_ora_protocollazione", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataOraProtocollazione;
    @XmlElement(required = true)
    protected TipiVerso verso;

    /**
     * Recupera il valore della proprietà codiceProtocollo.
     * 
     * @return
     *     possible object is
     *     {@link CodiceProtocollo }
     *     
     */
    public CodiceProtocollo getCodiceProtocollo() {
        return codiceProtocollo;
    }

    /**
     * Imposta il valore della proprietà codiceProtocollo.
     * 
     * @param value
     *     allowed object is
     *     {@link CodiceProtocollo }
     *     
     */
    public void setCodiceProtocollo(CodiceProtocollo value) {
        this.codiceProtocollo = value;
    }

    /**
     * Recupera il valore della proprietà codicePraticaSimo.
     * 
     * @return
     *     possible object is
     *     {@link CodicePraticaSimo }
     *     
     */
    public CodicePraticaSimo getCodicePraticaSimo() {
        return codicePraticaSimo;
    }

    /**
     * Imposta il valore della proprietà codicePraticaSimo.
     * 
     * @param value
     *     allowed object is
     *     {@link CodicePraticaSimo }
     *     
     */
    public void setCodicePraticaSimo(CodicePraticaSimo value) {
        this.codicePraticaSimo = value;
    }

    /**
     * Recupera il valore della proprietà codiceEvento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceEvento() {
        return codiceEvento;
    }

    /**
     * Imposta il valore della proprietà codiceEvento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceEvento(String value) {
        this.codiceEvento = value;
    }

    /**
     * Recupera il valore della proprietà annotazioniEvento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnnotazioniEvento() {
        return annotazioniEvento;
    }

    /**
     * Imposta il valore della proprietà annotazioniEvento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnnotazioniEvento(String value) {
        this.annotazioniEvento = value;
    }

    /**
     * Recupera il valore della proprietà esito.
     * 
     * @return
     *     possible object is
     *     {@link Esito }
     *     
     */
    public Esito getEsito() {
        return esito;
    }

    /**
     * Imposta il valore della proprietà esito.
     * 
     * @param value
     *     allowed object is
     *     {@link Esito }
     *     
     */
    public void setEsito(Esito value) {
        this.esito = value;
    }

    /**
     * Recupera il valore della proprietà annotazioniEsito.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAnnotazioniEsito() {
        return annotazioniEsito;
    }

    /**
     * Imposta il valore della proprietà annotazioniEsito.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAnnotazioniEsito(JAXBElement<String> value) {
        this.annotazioniEsito = value;
    }

    /**
     * Recupera il valore della proprietà importoFinanziato.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     *     
     */
    public JAXBElement<BigDecimal> getImportoFinanziato() {
        return importoFinanziato;
    }

    /**
     * Imposta il valore della proprietà importoFinanziato.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     *     
     */
    public void setImportoFinanziato(JAXBElement<BigDecimal> value) {
        this.importoFinanziato = value;
    }

    /**
     * Recupera il valore della proprietà dataOraRicevimento.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataOraRicevimento() {
        return dataOraRicevimento;
    }

    /**
     * Imposta il valore della proprietà dataOraRicevimento.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataOraRicevimento(XMLGregorianCalendar value) {
        this.dataOraRicevimento = value;
    }

    /**
     * Recupera il valore della proprietà dataOraProtocollazione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataOraProtocollazione() {
        return dataOraProtocollazione;
    }

    /**
     * Imposta il valore della proprietà dataOraProtocollazione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataOraProtocollazione(XMLGregorianCalendar value) {
        this.dataOraProtocollazione = value;
    }

    /**
     * Recupera il valore della proprietà verso.
     * 
     * @return
     *     possible object is
     *     {@link TipiVerso }
     *     
     */
    public TipiVerso getVerso() {
        return verso;
    }

    /**
     * Imposta il valore della proprietà verso.
     * 
     * @param value
     *     allowed object is
     *     {@link TipiVerso }
     *     
     */
    public void setVerso(TipiVerso value) {
        this.verso = value;
    }

}
