//
// Questo file  stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andr persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.02.27 alle 01:48:58 PM CET 
//


package it.wego.cross.xmlold;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per pratica complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="pratica">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id_pratica" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="id_procedimento_suap" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="identificativo_pratica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="oggetto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="responsabile_procedimento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="istruttore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="termini_evasione_pratica" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="notifica" type="{http://www.wego.it/cross}recapito" minOccurs="0"/>
 *         &lt;element name="cod_catastale_comune" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="des_comune" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id_ente" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="cod_ente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="des_ente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="des_comune_ente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="indirizzo_ente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cap_ente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fax_ente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="email_ente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pec_ente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="telefono_ente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id_protocollo_manuale" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="registro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="protocollo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fascicolo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="anno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="data_protocollo" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="data_ricezione" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="anagrafiche" type="{http://www.wego.it/cross}anagrafiche" maxOccurs="unbounded"/>
 *         &lt;element name="dati_catastali" type="{http://www.wego.it/cross}dati_catastali" minOccurs="0"/>
 *         &lt;element name="procedimenti" type="{http://www.wego.it/cross}procedimenti"/>
 *         &lt;element name="scadenze" type="{http://www.wego.it/cross}scadenze" minOccurs="0"/>
 *         &lt;element name="allegati" type="{http://www.wego.it/cross}allegati" minOccurs="0"/>
 *         &lt;element name="eventi" type="{http://www.wego.it/cross}eventi" minOccurs="0"/>
 *         &lt;element name="evento_corrente" type="{http://www.wego.it/cross}evento" minOccurs="0"/>
 *         &lt;element name="dati_da_front" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pratica", propOrder = {
    "idPratica",
    "idProcedimentoSuap",
    "identificativoPratica",
    "oggetto",
    "responsabileProcedimento",
    "istruttore",
    "terminiEvasionePratica",
    "notifica",
    "codCatastaleComune",
    "desComune",
    "idEnte",
    "codEnte",
    "desEnte",
    "desComuneEnte",
    "indirizzoEnte",
    "capEnte",
    "faxEnte",
    "emailEnte",
    "pecEnte",
    "telefonoEnte",
    "idProtocolloManuale",
    "registro",
    "protocollo",
    "fascicolo",
    "anno",
    "dataProtocollo",
    "dataRicezione",
    "anagrafiche",
    "datiCatastali",
    "procedimenti",
    "scadenze",
    "allegati",
    "eventi",
    "eventoCorrente",
    "datiDaFront"
})
public class Pratica {

    @XmlElement(name = "id_pratica")
    protected BigInteger idPratica;
    @XmlElement(name = "id_procedimento_suap", required = true)
    protected BigInteger idProcedimentoSuap;
    @XmlElement(name = "identificativo_pratica")
    protected String identificativoPratica;
    protected String oggetto;
    @XmlElement(name = "responsabile_procedimento")
    protected String responsabileProcedimento;
    protected String istruttore;
    @XmlElement(name = "termini_evasione_pratica")
    protected BigInteger terminiEvasionePratica;
    protected Recapito notifica;
    @XmlElement(name = "cod_catastale_comune")
    protected String codCatastaleComune;
    @XmlElement(name = "des_comune")
    protected String desComune;
    @XmlElement(name = "id_ente")
    protected BigInteger idEnte;
    @XmlElement(name = "cod_ente")
    protected String codEnte;
    @XmlElement(name = "des_ente")
    protected String desEnte;
    @XmlElement(name = "des_comune_ente")
    protected String desComuneEnte;
    @XmlElement(name = "indirizzo_ente")
    protected String indirizzoEnte;
    @XmlElement(name = "cap_ente")
    protected String capEnte;
    @XmlElement(name = "fax_ente")
    protected String faxEnte;
    @XmlElement(name = "email_ente")
    protected String emailEnte;
    @XmlElement(name = "pec_ente")
    protected String pecEnte;
    @XmlElement(name = "telefono_ente")
    protected String telefonoEnte;
    @XmlElement(name = "id_protocollo_manuale")
    protected BigInteger idProtocolloManuale;
    protected String registro;
    protected String protocollo;
    protected String fascicolo;
    protected String anno;
    @XmlElement(name = "data_protocollo")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataProtocollo;
    @XmlElement(name = "data_ricezione")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataRicezione;
    @XmlElement(required = true)
    protected List<Anagrafiche> anagrafiche;
    @XmlElement(name = "dati_catastali")
    protected DatiCatastali datiCatastali;
    @XmlElement(required = true)
    protected Procedimenti procedimenti;
    protected Scadenze scadenze;
    protected Allegati allegati;
    protected Eventi eventi;
    @XmlElement(name = "evento_corrente")
    protected Evento eventoCorrente;
    @XmlElement(name = "dati_da_front")
    protected String datiDaFront;

    /**
     * Recupera il valore della propriet� idPratica.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdPratica() {
        return idPratica;
    }

    /**
     * Imposta il valore della propriet� idPratica.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdPratica(BigInteger value) {
        this.idPratica = value;
    }

    /**
     * Recupera il valore della propriet� idProcedimentoSuap.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdProcedimentoSuap() {
        return idProcedimentoSuap;
    }

    /**
     * Imposta il valore della propriet� idProcedimentoSuap.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdProcedimentoSuap(BigInteger value) {
        this.idProcedimentoSuap = value;
    }

    /**
     * Recupera il valore della propriet� identificativoPratica.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoPratica() {
        return identificativoPratica;
    }

    /**
     * Imposta il valore della propriet� identificativoPratica.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoPratica(String value) {
        this.identificativoPratica = value;
    }

    /**
     * Recupera il valore della propriet� oggetto.
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
     * Imposta il valore della propriet� oggetto.
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
     * Recupera il valore della propriet� responsabileProcedimento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResponsabileProcedimento() {
        return responsabileProcedimento;
    }

    /**
     * Imposta il valore della propriet� responsabileProcedimento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResponsabileProcedimento(String value) {
        this.responsabileProcedimento = value;
    }

    /**
     * Recupera il valore della propriet� istruttore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIstruttore() {
        return istruttore;
    }

    /**
     * Imposta il valore della propriet� istruttore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIstruttore(String value) {
        this.istruttore = value;
    }

    /**
     * Recupera il valore della propriet� terminiEvasionePratica.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTerminiEvasionePratica() {
        return terminiEvasionePratica;
    }

    /**
     * Imposta il valore della propriet� terminiEvasionePratica.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTerminiEvasionePratica(BigInteger value) {
        this.terminiEvasionePratica = value;
    }

    /**
     * Recupera il valore della propriet� notifica.
     * 
     * @return
     *     possible object is
     *     {@link Recapito }
     *     
     */
    public Recapito getNotifica() {
        return notifica;
    }

    /**
     * Imposta il valore della propriet� notifica.
     * 
     * @param value
     *     allowed object is
     *     {@link Recapito }
     *     
     */
    public void setNotifica(Recapito value) {
        this.notifica = value;
    }

    /**
     * Recupera il valore della propriet� codCatastaleComune.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodCatastaleComune() {
        return codCatastaleComune;
    }

    /**
     * Imposta il valore della propriet� codCatastaleComune.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodCatastaleComune(String value) {
        this.codCatastaleComune = value;
    }

    /**
     * Recupera il valore della propriet� desComune.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesComune() {
        return desComune;
    }

    /**
     * Imposta il valore della propriet� desComune.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesComune(String value) {
        this.desComune = value;
    }

    /**
     * Recupera il valore della propriet� idEnte.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdEnte() {
        return idEnte;
    }

    /**
     * Imposta il valore della propriet� idEnte.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdEnte(BigInteger value) {
        this.idEnte = value;
    }

    /**
     * Recupera il valore della propriet� codEnte.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public String getCodEnte() {
        return codEnte;
    }

    /**
     * Imposta il valore della propriet� codEnte.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCodEnte(String value) {
        this.codEnte = value;
    }

    /**
     * Recupera il valore della propriet� desEnte.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesEnte() {
        return desEnte;
    }

    /**
     * Imposta il valore della propriet� desEnte.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesEnte(String value) {
        this.desEnte = value;
    }

    /**
     * Recupera il valore della propriet� desComuneEnte.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesComuneEnte() {
        return desComuneEnte;
    }

    /**
     * Imposta il valore della propriet� desComuneEnte.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesComuneEnte(String value) {
        this.desComuneEnte = value;
    }

    /**
     * Recupera il valore della propriet� indirizzoEnte.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndirizzoEnte() {
        return indirizzoEnte;
    }

    /**
     * Imposta il valore della propriet� indirizzoEnte.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndirizzoEnte(String value) {
        this.indirizzoEnte = value;
    }

    /**
     * Recupera il valore della propriet� capEnte.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCapEnte() {
        return capEnte;
    }

    /**
     * Imposta il valore della propriet� capEnte.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCapEnte(String value) {
        this.capEnte = value;
    }

    /**
     * Recupera il valore della propriet� faxEnte.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFaxEnte() {
        return faxEnte;
    }

    /**
     * Imposta il valore della propriet� faxEnte.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFaxEnte(String value) {
        this.faxEnte = value;
    }

    /**
     * Recupera il valore della propriet� emailEnte.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailEnte() {
        return emailEnte;
    }

    /**
     * Imposta il valore della propriet� emailEnte.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailEnte(String value) {
        this.emailEnte = value;
    }

    /**
     * Recupera il valore della propriet� pecEnte.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPecEnte() {
        return pecEnte;
    }

    /**
     * Imposta il valore della propriet� pecEnte.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPecEnte(String value) {
        this.pecEnte = value;
    }

    /**
     * Recupera il valore della propriet� telefonoEnte.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelefonoEnte() {
        return telefonoEnte;
    }

    /**
     * Imposta il valore della propriet� telefonoEnte.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelefonoEnte(String value) {
        this.telefonoEnte = value;
    }

    /**
     * Recupera il valore della propriet� idProtocolloManuale.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdProtocolloManuale() {
        return idProtocolloManuale;
    }

    /**
     * Imposta il valore della propriet� idProtocolloManuale.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdProtocolloManuale(BigInteger value) {
        this.idProtocolloManuale = value;
    }

    /**
     * Recupera il valore della propriet� registro.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegistro() {
        return registro;
    }

    /**
     * Imposta il valore della propriet� registro.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegistro(String value) {
        this.registro = value;
    }

    /**
     * Recupera il valore della propriet� protocollo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProtocollo() {
        return protocollo;
    }

    /**
     * Imposta il valore della propriet� protocollo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProtocollo(String value) {
        this.protocollo = value;
    }

    /**
     * Recupera il valore della propriet� fascicolo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFascicolo() {
        return fascicolo;
    }

    /**
     * Imposta il valore della propriet� fascicolo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFascicolo(String value) {
        this.fascicolo = value;
    }

    /**
     * Recupera il valore della propriet� anno.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnno() {
        return anno;
    }

    /**
     * Imposta il valore della propriet� anno.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnno(String value) {
        this.anno = value;
    }

    /**
     * Recupera il valore della propriet� dataProtocollo.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataProtocollo() {
        return dataProtocollo;
    }

    /**
     * Imposta il valore della propriet� dataProtocollo.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataProtocollo(XMLGregorianCalendar value) {
        this.dataProtocollo = value;
    }

    /**
     * Recupera il valore della propriet� dataRicezione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataRicezione() {
        return dataRicezione;
    }

    /**
     * Imposta il valore della propriet� dataRicezione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataRicezione(XMLGregorianCalendar value) {
        this.dataRicezione = value;
    }

    /**
     * Gets the value of the anagrafiche property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the anagrafiche property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAnagrafiche().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Anagrafiche }
     * 
     * 
     */
    public List<Anagrafiche> getAnagrafiche() {
        if (anagrafiche == null) {
            anagrafiche = new ArrayList<Anagrafiche>();
        }
        return this.anagrafiche;
    }

    /**
     * Recupera il valore della propriet� datiCatastali.
     * 
     * @return
     *     possible object is
     *     {@link DatiCatastali }
     *     
     */
    public DatiCatastali getDatiCatastali() {
        return datiCatastali;
    }

    /**
     * Imposta il valore della propriet� datiCatastali.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiCatastali }
     *     
     */
    public void setDatiCatastali(DatiCatastali value) {
        this.datiCatastali = value;
    }

    /**
     * Recupera il valore della propriet� procedimenti.
     * 
     * @return
     *     possible object is
     *     {@link Procedimenti }
     *     
     */
    public Procedimenti getProcedimenti() {
        return procedimenti;
    }

    /**
     * Imposta il valore della propriet� procedimenti.
     * 
     * @param value
     *     allowed object is
     *     {@link Procedimenti }
     *     
     */
    public void setProcedimenti(Procedimenti value) {
        this.procedimenti = value;
    }

    /**
     * Recupera il valore della propriet� scadenze.
     * 
     * @return
     *     possible object is
     *     {@link Scadenze }
     *     
     */
    public Scadenze getScadenze() {
        return scadenze;
    }

    /**
     * Imposta il valore della propriet� scadenze.
     * 
     * @param value
     *     allowed object is
     *     {@link Scadenze }
     *     
     */
    public void setScadenze(Scadenze value) {
        this.scadenze = value;
    }

    /**
     * Recupera il valore della propriet� allegati.
     * 
     * @return
     *     possible object is
     *     {@link Allegati }
     *     
     */
    public Allegati getAllegati() {
        return allegati;
    }

    /**
     * Imposta il valore della propriet� allegati.
     * 
     * @param value
     *     allowed object is
     *     {@link Allegati }
     *     
     */
    public void setAllegati(Allegati value) {
        this.allegati = value;
    }

    /**
     * Recupera il valore della propriet� eventi.
     * 
     * @return
     *     possible object is
     *     {@link Eventi }
     *     
     */
    public Eventi getEventi() {
        return eventi;
    }

    /**
     * Imposta il valore della propriet� eventi.
     * 
     * @param value
     *     allowed object is
     *     {@link Eventi }
     *     
     */
    public void setEventi(Eventi value) {
        this.eventi = value;
    }

    /**
     * Recupera il valore della propriet� eventoCorrente.
     * 
     * @return
     *     possible object is
     *     {@link Evento }
     *     
     */
    public Evento getEventoCorrente() {
        return eventoCorrente;
    }

    /**
     * Imposta il valore della propriet� eventoCorrente.
     * 
     * @param value
     *     allowed object is
     *     {@link Evento }
     *     
     */
    public void setEventoCorrente(Evento value) {
        this.eventoCorrente = value;
    }

    /**
     * Recupera il valore della propriet� datiDaFront.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatiDaFront() {
        return datiDaFront;
    }

    /**
     * Imposta il valore della propriet� datiDaFront.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatiDaFront(String value) {
        this.datiDaFront = value;
    }
    /**
     * Nella sezione di caricamento da protocollo c'e' necessita di cancellare
     * tutte le anagrafiche dall'xml, poichÃ© da questa sezione si lavora
     * direttamente sull'XML di CROSS e non sulle tabelle in banca dati
     *
     * @return
     */
    public void setAnagrafiche(List<Anagrafiche> anagraficheList) {
        anagrafiche = anagraficheList;
    }
}
