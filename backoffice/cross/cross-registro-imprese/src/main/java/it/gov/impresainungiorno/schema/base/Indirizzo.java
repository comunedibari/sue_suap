
package it.gov.impresainungiorno.schema.base;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * Identificazione di indirizzo, italiano o straniera
 * 
 * <p>Classe Java per Indirizzo complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Indirizzo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;group ref="{http://www.impresainungiorno.gov.it/schema/base}Citta"/&gt;
 *         &lt;group ref="{http://www.impresainungiorno.gov.it/schema/base}DatiIndirizzoItaliano" minOccurs="0"/&gt;
 *         &lt;group ref="{http://www.impresainungiorno.gov.it/schema/base}DatiIndirizzo"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Indirizzo", propOrder = {
    "stato",
    "provincia",
    "comune",
    "cittaStraniera",
    "cap",
    "toponimo",
    "denominazioneStradale",
    "numeroCivico",
    "frazione"
})
@XmlSeeAlso({
    IndirizzoConRecapiti.class,
    IndirizzoConRecapitiObbligatori.class
})
public class Indirizzo {

    @XmlElement(required = true)
    protected Stato stato;
    protected Provincia provincia;
    protected Comune comune;
    @XmlElement(name = "citta-straniera")
    protected String cittaStraniera;
    protected String cap;
    protected String toponimo;
    @XmlElement(name = "denominazione-stradale", required = true)
    protected String denominazioneStradale;
    @XmlElement(name = "numero-civico", required = true)
    protected String numeroCivico;
    protected String frazione;

    /**
     * Recupera il valore della propriet stato.
     * 
     * @return
     *     possible object is
     *     {@link Stato }
     *     
     */
    public Stato getStato() {
        return stato;
    }

    /**
     * Imposta il valore della propriet stato.
     * 
     * @param value
     *     allowed object is
     *     {@link Stato }
     *     
     */
    public void setStato(Stato value) {
        this.stato = value;
    }

    /**
     * Recupera il valore della propriet provincia.
     * 
     * @return
     *     possible object is
     *     {@link Provincia }
     *     
     */
    public Provincia getProvincia() {
        return provincia;
    }

    /**
     * Imposta il valore della propriet provincia.
     * 
     * @param value
     *     allowed object is
     *     {@link Provincia }
     *     
     */
    public void setProvincia(Provincia value) {
        this.provincia = value;
    }

    /**
     * Recupera il valore della propriet comune.
     * 
     * @return
     *     possible object is
     *     {@link Comune }
     *     
     */
    public Comune getComune() {
        return comune;
    }

    /**
     * Imposta il valore della propriet comune.
     * 
     * @param value
     *     allowed object is
     *     {@link Comune }
     *     
     */
    public void setComune(Comune value) {
        this.comune = value;
    }

    /**
     * Recupera il valore della propriet cittaStraniera.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCittaStraniera() {
        return cittaStraniera;
    }

    /**
     * Imposta il valore della propriet cittaStraniera.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCittaStraniera(String value) {
        this.cittaStraniera = value;
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
     * Recupera il valore della propriet toponimo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToponimo() {
        return toponimo;
    }

    /**
     * Imposta il valore della propriet toponimo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToponimo(String value) {
        this.toponimo = value;
    }

    /**
     * Recupera il valore della propriet denominazioneStradale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDenominazioneStradale() {
        return denominazioneStradale;
    }

    /**
     * Imposta il valore della propriet denominazioneStradale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDenominazioneStradale(String value) {
        this.denominazioneStradale = value;
    }

    /**
     * Recupera il valore della propriet numeroCivico.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroCivico() {
        return numeroCivico;
    }

    /**
     * Imposta il valore della propriet numeroCivico.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroCivico(String value) {
        this.numeroCivico = value;
    }

    /**
     * Recupera il valore della propriet frazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFrazione() {
        return frazione;
    }

    /**
     * Imposta il valore della propriet frazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFrazione(String value) {
        this.frazione = value;
    }

}
