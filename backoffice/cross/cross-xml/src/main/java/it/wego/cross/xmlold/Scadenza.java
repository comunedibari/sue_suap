//
// Questo file stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andr persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.02.27 alle 01:48:58 PM CET 
//


package it.wego.cross.xmlold;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per scadenza complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="scadenza">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id_scadenza" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="id_pratica" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="data_scadenza" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="id_ana_scadenza" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="des_ana_scadenza" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flg_ana_scadenza" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id_stato_scadenza" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="des_stato_scadenza" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="grp_stato_scadenza" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="data_fine_scadenza" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="data_inizio_scadenza" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="id_pratica_evento" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="giorni_teorici_scadenza" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "scadenza", propOrder = {
    "idScadenza",
    "idPratica",
    "dataScadenza",
    "idAnaScadenza",
    "desAnaScadenza",
    "flgAnaScadenza",
    "idStatoScadenza",
    "desStatoScadenza",
    "grpStatoScadenza",
    "dataFineScadenza",
    "dataInizioScadenza",
    "idPraticaEvento",
    "giorniTeoriciScadenza",
    "descrizione"
})
public class Scadenza {

    @XmlElement(name = "id_scadenza")
    protected BigInteger idScadenza;
    @XmlElement(name = "id_pratica")
    protected BigInteger idPratica;
    @XmlElement(name = "data_scadenza")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataScadenza;
    @XmlElement(name = "id_ana_scadenza")
    protected String idAnaScadenza;
    @XmlElement(name = "des_ana_scadenza")
    protected String desAnaScadenza;
    @XmlElement(name = "flg_ana_scadenza")
    protected String flgAnaScadenza;
    @XmlElement(name = "id_stato_scadenza")
    protected String idStatoScadenza;
    @XmlElement(name = "des_stato_scadenza")
    protected String desStatoScadenza;
    @XmlElement(name = "grp_stato_scadenza")
    protected String grpStatoScadenza;
    @XmlElement(name = "data_fine_scadenza")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataFineScadenza;
    @XmlElement(name = "data_inizio_scadenza")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataInizioScadenza;
    @XmlElement(name = "id_pratica_evento")
    protected BigInteger idPraticaEvento;
    @XmlElement(name = "giorni_teorici_scadenza")
    protected BigInteger giorniTeoriciScadenza;
    protected String descrizione;

    /**
     * Recupera il valore della propriet� idScadenza.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdScadenza() {
        return idScadenza;
    }

    /**
     * Imposta il valore della propriet� idScadenza.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdScadenza(BigInteger value) {
        this.idScadenza = value;
    }

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
     * Recupera il valore della propriet� dataScadenza.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataScadenza() {
        return dataScadenza;
    }

    /**
     * Imposta il valore della propriet� dataScadenza.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataScadenza(XMLGregorianCalendar value) {
        this.dataScadenza = value;
    }

    /**
     * Recupera il valore della propriet� idAnaScadenza.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdAnaScadenza() {
        return idAnaScadenza;
    }

    /**
     * Imposta il valore della propriet� idAnaScadenza.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdAnaScadenza(String value) {
        this.idAnaScadenza = value;
    }

    /**
     * Recupera il valore della propriet� desAnaScadenza.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesAnaScadenza() {
        return desAnaScadenza;
    }

    /**
     * Imposta il valore della propriet� desAnaScadenza.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesAnaScadenza(String value) {
        this.desAnaScadenza = value;
    }

    /**
     * Recupera il valore della propriet� flgAnaScadenza.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlgAnaScadenza() {
        return flgAnaScadenza;
    }

    /**
     * Imposta il valore della propriet� flgAnaScadenza.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlgAnaScadenza(String value) {
        this.flgAnaScadenza = value;
    }

    /**
     * Recupera il valore della propriet� idStatoScadenza.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdStatoScadenza() {
        return idStatoScadenza;
    }

    /**
     * Imposta il valore della propriet� idStatoScadenza.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdStatoScadenza(String value) {
        this.idStatoScadenza = value;
    }

    /**
     * Recupera il valore della propriet� desStatoScadenza.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesStatoScadenza() {
        return desStatoScadenza;
    }

    /**
     * Imposta il valore della propriet� desStatoScadenza.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesStatoScadenza(String value) {
        this.desStatoScadenza = value;
    }

    /**
     * Recupera il valore della propriet� grpStatoScadenza.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGrpStatoScadenza() {
        return grpStatoScadenza;
    }

    /**
     * Imposta il valore della propriet� grpStatoScadenza.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGrpStatoScadenza(String value) {
        this.grpStatoScadenza = value;
    }

    /**
     * Recupera il valore della propriet� dataFineScadenza.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataFineScadenza() {
        return dataFineScadenza;
    }

    /**
     * Imposta il valore della propriet� dataFineScadenza.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataFineScadenza(XMLGregorianCalendar value) {
        this.dataFineScadenza = value;
    }

    /**
     * Recupera il valore della propriet� dataInizioScadenza.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataInizioScadenza() {
        return dataInizioScadenza;
    }

    /**
     * Imposta il valore della propriet� dataInizioScadenza.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataInizioScadenza(XMLGregorianCalendar value) {
        this.dataInizioScadenza = value;
    }

    /**
     * Recupera il valore della propriet� idPraticaEvento.
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
     * Imposta il valore della propriet� idPraticaEvento.
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
     * Recupera il valore della propriet� giorniTeoriciScadenza.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getGiorniTeoriciScadenza() {
        return giorniTeoriciScadenza;
    }

    /**
     * Imposta il valore della propriet� giorniTeoriciScadenza.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setGiorniTeoriciScadenza(BigInteger value) {
        this.giorniTeoriciScadenza = value;
    }

    /**
     * Recupera il valore della propriet� descrizione.
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
     * Imposta il valore della propriet� descrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizione(String value) {
        this.descrizione = value;
    }

}
