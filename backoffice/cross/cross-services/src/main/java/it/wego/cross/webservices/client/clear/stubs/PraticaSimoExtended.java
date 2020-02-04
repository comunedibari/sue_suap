
package it.wego.cross.webservices.client.clear.stubs;

import java.math.BigDecimal;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per pratica_simo_extended complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="pratica_simo_extended">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codice_protocollo" type="{http://www.simo.org}codice_protocollo"/>
 *         &lt;element name="codice_procedimento_rur" type="{http://www.simo.org}pratica_simo_extended_codice_procedimento_rurType"/>
 *         &lt;element name="identificazione_pratica" type="{http://www.simo.org}pratica_simo_extended_identificazione_praticaType" minOccurs="0"/>
 *         &lt;element name="responsabile_procedimento" type="{http://www.simo.org}pratica_simo_extended_responsabile_procedimentoType" minOccurs="0"/>
 *         &lt;element name="responsabile_istruttoria" type="{http://www.simo.org}pratica_simo_extended_responsabile_istruttoriaType" minOccurs="0"/>
 *         &lt;element name="importo_richiesto" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="oggetto" type="{http://www.simo.org}pratica_simo_extended_oggettoType"/>
 *         &lt;element name="richiedenti" type="{http://www.simo.org}richiedenteArray" minOccurs="0"/>
 *         &lt;element name="localizzazioni" type="{http://www.simo.org}localizzazioneArray" minOccurs="0"/>
 *         &lt;element name="data_ora_ricevimento" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="data_ora_protocollazione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="data_avvio_monitoraggio" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="verso" type="{http://www.simo.org}TipiVerso"/>
 *         &lt;element name="codice_ufficio" type="{http://www.simo.org}pratica_simo_extended_codice_ufficioType" minOccurs="0"/>
 *         &lt;element name="codice_protocollo_precedente" type="{http://www.simo.org}codice_protocollo_precedente" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pratica_simo_extended", propOrder = {
    "codiceProtocollo",
    "codiceProcedimentoRur",
    "identificazionePratica",
    "responsabileProcedimento",
    "responsabileIstruttoria",
    "importoRichiesto",
    "oggetto",
    "richiedenti",
    "localizzazioni",
    "dataOraRicevimento",
    "dataOraProtocollazione",
    "dataAvvioMonitoraggio",
    "verso",
    "codiceUfficio",
    "codiceProtocolloPrecedente"
})
public class PraticaSimoExtended {

    @XmlElement(name = "codice_protocollo", required = true)
    protected CodiceProtocollo codiceProtocollo;
    @XmlElement(name = "codice_procedimento_rur", required = true)
    protected String codiceProcedimentoRur;
    @XmlElementRef(name = "identificazione_pratica", namespace = "http://www.simo.org", type = JAXBElement.class, required = false)
    protected JAXBElement<String> identificazionePratica;
    @XmlElementRef(name = "responsabile_procedimento", namespace = "http://www.simo.org", type = JAXBElement.class, required = false)
    protected JAXBElement<String> responsabileProcedimento;
    @XmlElementRef(name = "responsabile_istruttoria", namespace = "http://www.simo.org", type = JAXBElement.class, required = false)
    protected JAXBElement<String> responsabileIstruttoria;
    @XmlElementRef(name = "importo_richiesto", namespace = "http://www.simo.org", type = JAXBElement.class, required = false)
    protected JAXBElement<BigDecimal> importoRichiesto;
    @XmlElement(required = true, nillable = true)
    protected String oggetto;
    @XmlElementRef(name = "richiedenti", namespace = "http://www.simo.org", type = JAXBElement.class, required = false)
    protected JAXBElement<RichiedenteArray> richiedenti;
    @XmlElementRef(name = "localizzazioni", namespace = "http://www.simo.org", type = JAXBElement.class, required = false)
    protected JAXBElement<LocalizzazioneArray> localizzazioni;
    @XmlElementRef(name = "data_ora_ricevimento", namespace = "http://www.simo.org", type = JAXBElement.class, required = false)
    protected JAXBElement<XMLGregorianCalendar> dataOraRicevimento;
    @XmlElementRef(name = "data_ora_protocollazione", namespace = "http://www.simo.org", type = JAXBElement.class, required = false)
    protected JAXBElement<XMLGregorianCalendar> dataOraProtocollazione;
    @XmlElementRef(name = "data_avvio_monitoraggio", namespace = "http://www.simo.org", type = JAXBElement.class, required = false)
    protected JAXBElement<XMLGregorianCalendar> dataAvvioMonitoraggio;
    @XmlElement(required = true)
    protected TipiVerso verso;
    @XmlElementRef(name = "codice_ufficio", namespace = "http://www.simo.org", type = JAXBElement.class, required = false)
    protected JAXBElement<String> codiceUfficio;
    @XmlElementRef(name = "codice_protocollo_precedente", namespace = "http://www.simo.org", type = JAXBElement.class, required = false)
    protected JAXBElement<CodiceProtocolloPrecedente> codiceProtocolloPrecedente;

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
     * Recupera il valore della proprietà codiceProcedimentoRur.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceProcedimentoRur() {
        return codiceProcedimentoRur;
    }

    /**
     * Imposta il valore della proprietà codiceProcedimentoRur.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceProcedimentoRur(String value) {
        this.codiceProcedimentoRur = value;
    }

    /**
     * Recupera il valore della proprietà identificazionePratica.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getIdentificazionePratica() {
        return identificazionePratica;
    }

    /**
     * Imposta il valore della proprietà identificazionePratica.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setIdentificazionePratica(JAXBElement<String> value) {
        this.identificazionePratica = value;
    }

    /**
     * Recupera il valore della proprietà responsabileProcedimento.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getResponsabileProcedimento() {
        return responsabileProcedimento;
    }

    /**
     * Imposta il valore della proprietà responsabileProcedimento.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setResponsabileProcedimento(JAXBElement<String> value) {
        this.responsabileProcedimento = value;
    }

    /**
     * Recupera il valore della proprietà responsabileIstruttoria.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getResponsabileIstruttoria() {
        return responsabileIstruttoria;
    }

    /**
     * Imposta il valore della proprietà responsabileIstruttoria.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setResponsabileIstruttoria(JAXBElement<String> value) {
        this.responsabileIstruttoria = value;
    }

    /**
     * Recupera il valore della proprietà importoRichiesto.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     *     
     */
    public JAXBElement<BigDecimal> getImportoRichiesto() {
        return importoRichiesto;
    }

    /**
     * Imposta il valore della proprietà importoRichiesto.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     *     
     */
    public void setImportoRichiesto(JAXBElement<BigDecimal> value) {
        this.importoRichiesto = value;
    }

    /**
     * Recupera il valore della proprietà oggetto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOggetto() {
        return oggetto;
    }

    /**
     * Imposta il valore della proprietà oggetto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOggetto(String value) {
        this.oggetto = value;
    }

    /**
     * Recupera il valore della proprietà richiedenti.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link RichiedenteArray }{@code >}
     *     
     */
    public JAXBElement<RichiedenteArray> getRichiedenti() {
        return richiedenti;
    }

    /**
     * Imposta il valore della proprietà richiedenti.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link RichiedenteArray }{@code >}
     *     
     */
    public void setRichiedenti(JAXBElement<RichiedenteArray> value) {
        this.richiedenti = value;
    }

    /**
     * Recupera il valore della proprietà localizzazioni.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link LocalizzazioneArray }{@code >}
     *     
     */
    public JAXBElement<LocalizzazioneArray> getLocalizzazioni() {
        return localizzazioni;
    }

    /**
     * Imposta il valore della proprietà localizzazioni.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link LocalizzazioneArray }{@code >}
     *     
     */
    public void setLocalizzazioni(JAXBElement<LocalizzazioneArray> value) {
        this.localizzazioni = value;
    }

    /**
     * Recupera il valore della proprietà dataOraRicevimento.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getDataOraRicevimento() {
        return dataOraRicevimento;
    }

    /**
     * Imposta il valore della proprietà dataOraRicevimento.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setDataOraRicevimento(JAXBElement<XMLGregorianCalendar> value) {
        this.dataOraRicevimento = value;
    }

    /**
     * Recupera il valore della proprietà dataOraProtocollazione.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getDataOraProtocollazione() {
        return dataOraProtocollazione;
    }

    /**
     * Imposta il valore della proprietà dataOraProtocollazione.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setDataOraProtocollazione(JAXBElement<XMLGregorianCalendar> value) {
        this.dataOraProtocollazione = value;
    }

    /**
     * Recupera il valore della proprietà dataAvvioMonitoraggio.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getDataAvvioMonitoraggio() {
        return dataAvvioMonitoraggio;
    }

    /**
     * Imposta il valore della proprietà dataAvvioMonitoraggio.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setDataAvvioMonitoraggio(JAXBElement<XMLGregorianCalendar> value) {
        this.dataAvvioMonitoraggio = value;
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

    /**
     * Recupera il valore della proprietà codiceUfficio.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCodiceUfficio() {
        return codiceUfficio;
    }

    /**
     * Imposta il valore della proprietà codiceUfficio.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCodiceUfficio(JAXBElement<String> value) {
        this.codiceUfficio = value;
    }

    /**
     * Recupera il valore della proprietà codiceProtocolloPrecedente.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link CodiceProtocolloPrecedente }{@code >}
     *     
     */
    public JAXBElement<CodiceProtocolloPrecedente> getCodiceProtocolloPrecedente() {
        return codiceProtocolloPrecedente;
    }

    /**
     * Imposta il valore della proprietà codiceProtocolloPrecedente.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link CodiceProtocolloPrecedente }{@code >}
     *     
     */
    public void setCodiceProtocolloPrecedente(JAXBElement<CodiceProtocolloPrecedente> value) {
        this.codiceProtocolloPrecedente = value;
    }

}
