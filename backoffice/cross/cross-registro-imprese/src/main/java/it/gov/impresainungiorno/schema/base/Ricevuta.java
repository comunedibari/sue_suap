
package it.gov.impresainungiorno.schema.base;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Ricevuta complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Ricevuta">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="operazione" type="{http://www.impresainungiorno.gov.it/schema/base}Stringa"/>
 *         &lt;element name="codice-fiscale-impresa" type="{http://www.impresainungiorno.gov.it/schema/base}CodiceFiscale" minOccurs="0"/>
 *         &lt;element name="ragione-sociale-impresa" type="{http://www.impresainungiorno.gov.it/schema/base}Stringa" minOccurs="0"/>
 *         &lt;element name="operatore" type="{http://www.impresainungiorno.gov.it/schema/base}UtentePortale" form="qualified"/>
 *         &lt;choice>
 *           &lt;element name="ente" type="{http://www.impresainungiorno.gov.it/schema/base}EnteRicevuta" maxOccurs="unbounded"/>
 *           &lt;element name="errore" type="{http://www.impresainungiorno.gov.it/schema/base}Errore" maxOccurs="unbounded"/>
 *         &lt;/choice>
 *         &lt;element name="lista-adempimenti" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ente" type="{http://www.impresainungiorno.gov.it/schema/base}StringaBreve"/>
 *                   &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="riferimento-portale" type="{http://www.impresainungiorno.gov.it/schema/base}RiferimentoPortale" />
 *       &lt;attribute name="tempo-invio" use="required" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Ricevuta", propOrder = {
    "operazione",
    "codiceFiscaleImpresa",
    "ragioneSocialeImpresa",
    "operatore",
    "ente",
    "errore",
    "listaAdempimenti"
})
public class Ricevuta {

    @XmlElement(required = true)
    protected String operazione;
    @XmlElement(name = "codice-fiscale-impresa")
    protected String codiceFiscaleImpresa;
    @XmlElement(name = "ragione-sociale-impresa")
    protected String ragioneSocialeImpresa;
    @XmlElement(namespace = "http://www.impresainungiorno.gov.it/schema/base", required = true)
    protected UtentePortale operatore;
    protected List<EnteRicevuta> ente;
    protected List<Errore> errore;
    @XmlElement(name = "lista-adempimenti")
    protected List<Ricevuta.ListaAdempimenti> listaAdempimenti;
    @XmlAttribute(name = "riferimento-portale")
    protected String riferimentoPortale;
    @XmlAttribute(name = "tempo-invio", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar tempoInvio;

    /**
     * Gets the value of the operazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperazione() {
        return operazione;
    }

    /**
     * Sets the value of the operazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperazione(String value) {
        this.operazione = value;
    }

    /**
     * Gets the value of the codiceFiscaleImpresa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceFiscaleImpresa() {
        return codiceFiscaleImpresa;
    }

    /**
     * Sets the value of the codiceFiscaleImpresa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceFiscaleImpresa(String value) {
        this.codiceFiscaleImpresa = value;
    }

    /**
     * Gets the value of the ragioneSocialeImpresa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRagioneSocialeImpresa() {
        return ragioneSocialeImpresa;
    }

    /**
     * Sets the value of the ragioneSocialeImpresa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRagioneSocialeImpresa(String value) {
        this.ragioneSocialeImpresa = value;
    }

    /**
     * Gets the value of the operatore property.
     * 
     * @return
     *     possible object is
     *     {@link UtentePortale }
     *     
     */
    public UtentePortale getOperatore() {
        return operatore;
    }

    /**
     * Sets the value of the operatore property.
     * 
     * @param value
     *     allowed object is
     *     {@link UtentePortale }
     *     
     */
    public void setOperatore(UtentePortale value) {
        this.operatore = value;
    }

    /**
     * Gets the value of the ente property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ente property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEnte().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EnteRicevuta }
     * 
     * 
     */
    public List<EnteRicevuta> getEnte() {
        if (ente == null) {
            ente = new ArrayList<EnteRicevuta>();
        }
        return this.ente;
    }

    /**
     * Gets the value of the errore property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the errore property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getErrore().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Errore }
     * 
     * 
     */
    public List<Errore> getErrore() {
        if (errore == null) {
            errore = new ArrayList<Errore>();
        }
        return this.errore;
    }

    /**
     * Gets the value of the listaAdempimenti property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listaAdempimenti property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListaAdempimenti().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Ricevuta.ListaAdempimenti }
     * 
     * 
     */
    public List<Ricevuta.ListaAdempimenti> getListaAdempimenti() {
        if (listaAdempimenti == null) {
            listaAdempimenti = new ArrayList<Ricevuta.ListaAdempimenti>();
        }
        return this.listaAdempimenti;
    }

    /**
     * Gets the value of the riferimentoPortale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiferimentoPortale() {
        return riferimentoPortale;
    }

    /**
     * Sets the value of the riferimentoPortale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiferimentoPortale(String value) {
        this.riferimentoPortale = value;
    }

    /**
     * Gets the value of the tempoInvio property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTempoInvio() {
        return tempoInvio;
    }

    /**
     * Sets the value of the tempoInvio property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTempoInvio(XMLGregorianCalendar value) {
        this.tempoInvio = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="ente" type="{http://www.impresainungiorno.gov.it/schema/base}StringaBreve"/>
     *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "ente",
        "id"
    })
    public static class ListaAdempimenti {

        @XmlElement(required = true)
        protected String ente;
        @XmlElement(required = true)
        protected String id;

        /**
         * Gets the value of the ente property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEnte() {
            return ente;
        }

        /**
         * Sets the value of the ente property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEnte(String value) {
            this.ente = value;
        }

        /**
         * Gets the value of the id property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getId() {
            return id;
        }

        /**
         * Sets the value of the id property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setId(String value) {
            this.id = value;
        }

    }

}
