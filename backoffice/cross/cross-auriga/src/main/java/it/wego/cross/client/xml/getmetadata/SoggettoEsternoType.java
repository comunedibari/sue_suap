//
// Questo file stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrpersa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.07.14 alle 03:42:18 PM CEST 
//


package it.wego.cross.client.xml.getmetadata;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Soggetto (persona fisica o giuridica) esterno all'AOO
 * 
 * <p>Classe Java per SoggettoEsternoType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="SoggettoEsternoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CodIdInAnagrafe" type="{}IdInSistemaEsternoType" minOccurs="0"/>
 *         &lt;element name="FlagFisica" type="{}FlagSiNoType"/>
 *         &lt;element name="Denominazione_Cognome" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Nome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CodiceFiscale" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="16"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="PartitaIva" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="11"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Sesso" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="M|F|-"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="DataNascitaIstituzione" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="ComuneNascita" type="{}ComuneItalianoType" minOccurs="0"/>
 *         &lt;element name="StatoNascita" type="{}StatoNazionaleType" minOccurs="0"/>
 *         &lt;element name="StatoCittadinanza" type="{}StatoNazionaleType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SoggettoEsternoType", propOrder = {
    "codIdInAnagrafe",
    "flagFisica",
    "denominazioneCognome",
    "nome",
    "codiceFiscale",
    "partitaIva",
    "sesso",
    "dataNascitaIstituzione",
    "comuneNascita",
    "statoNascita",
    "statoCittadinanza"
})
@XmlSeeAlso({
    SoggettoEstEstesoType.class,
    DestinatarioEsternoType.class
})
public class SoggettoEsternoType {

    @XmlElement(name = "CodIdInAnagrafe")
    protected String codIdInAnagrafe;
    @XmlElement(name = "FlagFisica", required = true)
    protected String flagFisica;
    @XmlElement(name = "Denominazione_Cognome", required = true)
    protected String denominazioneCognome;
    @XmlElement(name = "Nome")
    protected String nome;
    @XmlElement(name = "CodiceFiscale")
    protected String codiceFiscale;
    @XmlElement(name = "PartitaIva")
    protected String partitaIva;
    @XmlElement(name = "Sesso")
    protected String sesso;
    @XmlElement(name = "DataNascitaIstituzione")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataNascitaIstituzione;
    @XmlElement(name = "ComuneNascita")
    protected ComuneItalianoType comuneNascita;
    @XmlElement(name = "StatoNascita")
    protected StatoNazionaleType statoNascita;
    @XmlElement(name = "StatoCittadinanza")
    protected StatoNazionaleType statoCittadinanza;

    /**
     * Recupera il valore della proprietcodIdInAnagrafe.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodIdInAnagrafe() {
        return codIdInAnagrafe;
    }

    /**
     * Imposta il valore della proprietcodIdInAnagrafe.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodIdInAnagrafe(String value) {
        this.codIdInAnagrafe = value;
    }

    /**
     * Recupera il valore della proprietflagFisica.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagFisica() {
        return flagFisica;
    }

    /**
     * Imposta il valore della proprietflagFisica.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagFisica(String value) {
        this.flagFisica = value;
    }

    /**
     * Recupera il valore della proprietdenominazioneCognome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDenominazioneCognome() {
        return denominazioneCognome;
    }

    /**
     * Imposta il valore della proprietdenominazioneCognome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDenominazioneCognome(String value) {
        this.denominazioneCognome = value;
    }

    /**
     * Recupera il valore della proprietnome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNome() {
        return nome;
    }

    /**
     * Imposta il valore della proprietnome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNome(String value) {
        this.nome = value;
    }

    /**
     * Recupera il valore della proprietcodiceFiscale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    /**
     * Imposta il valore della proprietcodiceFiscale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceFiscale(String value) {
        this.codiceFiscale = value;
    }

    /**
     * Recupera il valore della proprietpartitaIva.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartitaIva() {
        return partitaIva;
    }

    /**
     * Imposta il valore della proprietpartitaIva.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartitaIva(String value) {
        this.partitaIva = value;
    }

    /**
     * Recupera il valore della proprietsesso.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSesso() {
        return sesso;
    }

    /**
     * Imposta il valore della proprietsesso.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSesso(String value) {
        this.sesso = value;
    }

    /**
     * Recupera il valore della proprietdataNascitaIstituzione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataNascitaIstituzione() {
        return dataNascitaIstituzione;
    }

    /**
     * Imposta il valore della proprietdataNascitaIstituzione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataNascitaIstituzione(XMLGregorianCalendar value) {
        this.dataNascitaIstituzione = value;
    }

    /**
     * Recupera il valore della proprietcomuneNascita.
     * 
     * @return
     *     possible object is
     *     {@link ComuneItalianoType }
     *     
     */
    public ComuneItalianoType getComuneNascita() {
        return comuneNascita;
    }

    /**
     * Imposta il valore della proprietcomuneNascita.
     * 
     * @param value
     *     allowed object is
     *     {@link ComuneItalianoType }
     *     
     */
    public void setComuneNascita(ComuneItalianoType value) {
        this.comuneNascita = value;
    }

    /**
     * Recupera il valore della proprietstatoNascita.
     * 
     * @return
     *     possible object is
     *     {@link StatoNazionaleType }
     *     
     */
    public StatoNazionaleType getStatoNascita() {
        return statoNascita;
    }

    /**
     * Imposta il valore della proprietstatoNascita.
     * 
     * @param value
     *     allowed object is
     *     {@link StatoNazionaleType }
     *     
     */
    public void setStatoNascita(StatoNazionaleType value) {
        this.statoNascita = value;
    }

    /**
     * Recupera il valore della proprietstatoCittadinanza.
     * 
     * @return
     *     possible object is
     *     {@link StatoNazionaleType }
     *     
     */
    public StatoNazionaleType getStatoCittadinanza() {
        return statoCittadinanza;
    }

    /**
     * Imposta il valore della proprietstatoCittadinanza.
     * 
     * @param value
     *     allowed object is
     *     {@link StatoNazionaleType }
     *     
     */
    public void setStatoCittadinanza(StatoNazionaleType value) {
        this.statoCittadinanza = value;
    }

}
