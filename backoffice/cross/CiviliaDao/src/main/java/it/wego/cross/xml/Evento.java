//
// Questo file stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrpersa durante la ricompilazione dello schema di origine. 
// Generato il: 2013.05.27 alle 07:27:17 PM CEST 
//


package it.wego.cross.xml;

import it.wego.cross.xml.adapter.DateAdapter;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="id_pratica_evento" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="id_evento" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="descrizione_evento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="data_evento" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="id_utente" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="nome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cognome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="note" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numero_protocollo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="verso" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="I"/>
 *               &lt;enumeration value="O"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="visibilita_cross" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="S"/>
 *               &lt;enumeration value="N"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="visibilita_utente" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="S"/>
 *               &lt;enumeration value="N"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="allegati" type="{http://www.wego.it/cross}allegati" minOccurs="0"/>
 *         &lt;element name="anagraficheEvento" type="{http://www.w3.org/2001/XMLSchema}anagraficheEvento" minOccurs="0"/>
 *         &lt;element name="entiEvento" type="{http://www.w3.org/2001/XMLSchema}entiEvento" minOccurs="0"/>
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
    "idPraticaEvento",
    "idEvento",
    "descrizioneEvento",
    "dataEvento",
    "idUtente",
    "nome",
    "cognome",
    "note",
    "numeroProtocollo",
    "verso",
    "visibilitaCross",
    "visibilitaUtente",
    "allegati",
    "anagraficheEvento",
    "entiEvento"
})
public class Evento {

    @XmlElement(name = "id_pratica_evento")
    protected BigInteger idPraticaEvento;
    @XmlElement(name = "id_evento")
    protected BigInteger idEvento;
    @XmlElement(name = "descrizione_evento")
    protected String descrizioneEvento;
    @XmlElement(name = "data_evento")
    @XmlSchemaType(name = "date")
    @XmlJavaTypeAdapter(DateAdapter.class)
    protected XMLGregorianCalendar dataEvento;
    @XmlElement(name = "id_utente")
    protected BigInteger idUtente;
    protected String nome;
    protected String cognome;
    protected String note;
    @XmlElement(name = "numero_protocollo")
    protected String numeroProtocollo;
    protected String verso;
    @XmlElement(name = "visibilita_cross")
    protected String visibilitaCross;
    @XmlElement(name = "visibilita_utente")
    protected String visibilitaUtente;
    protected Allegati allegati;
    protected AnagraficheEvento anagraficheEvento;
    protected EntiEvento entiEvento;

    /**
     * Recupera il valore della proprietidPraticaEvento.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdPraticaEvento() {
        return idPraticaEvento;
    }

    /**
     * Imposta il valore della proprietidPraticaEvento.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdPraticaEvento(BigInteger value) {
        this.idPraticaEvento = value;
    }

    /**
     * Recupera il valore della proprietidEvento.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdEvento() {
        return idEvento;
    }

    /**
     * Imposta il valore della proprietidEvento.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdEvento(BigInteger value) {
        this.idEvento = value;
    }

    /**
     * Recupera il valore della proprietdescrizioneEvento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneEvento() {
        return descrizioneEvento;
    }

    /**
     * Imposta il valore della proprietdescrizioneEvento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneEvento(String value) {
        this.descrizioneEvento = value;
    }

    /**
     * Recupera il valore della proprietdataEvento.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataEvento() {
        return dataEvento;
    }

    /**
     * Imposta il valore della proprietdataEvento.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataEvento(XMLGregorianCalendar value) {
        this.dataEvento = value;
    }

    /**
     * Recupera il valore della proprietidUtente.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdUtente() {
        return idUtente;
    }

    /**
     * Imposta il valore della proprietidUtente.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdUtente(BigInteger value) {
        this.idUtente = value;
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
     * Recupera il valore della proprietcognome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Imposta il valore della proprietcognome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCognome(String value) {
        this.cognome = value;
    }

    /**
     * Recupera il valore della proprietnote.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNote() {
        return note;
    }

    /**
     * Imposta il valore della proprietnote.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNote(String value) {
        this.note = value;
    }

    /**
     * Recupera il valore della proprietnumeroProtocollo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroProtocollo() {
        return numeroProtocollo;
    }

    /**
     * Imposta il valore della proprietnumeroProtocollo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroProtocollo(String value) {
        this.numeroProtocollo = value;
    }

    /**
     * Recupera il valore della proprietverso.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVerso() {
        return verso;
    }

    /**
     * Imposta il valore della proprietverso.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVerso(String value) {
        this.verso = value;
    }

    /**
     * Recupera il valore della proprietvisibilitaCross.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVisibilitaCross() {
        return visibilitaCross;
    }

    /**
     * Imposta il valore della proprietvisibilitaCross.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVisibilitaCross(String value) {
        this.visibilitaCross = value;
    }

    /**
     * Recupera il valore della proprietvisibilitaUtente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVisibilitaUtente() {
        return visibilitaUtente;
    }

    /**
     * Imposta il valore della proprietvisibilitaUtente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVisibilitaUtente(String value) {
        this.visibilitaUtente = value;
    }

    /**
     * Recupera il valore della proprietallegati.
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
     * Imposta il valore della proprietallegati.
     * 
     * @param value
     *     allowed object is
     *     {@link Allegati }
     *     
     */
    public void setAllegati(Allegati value) {
        this.allegati = value;
    }

}
