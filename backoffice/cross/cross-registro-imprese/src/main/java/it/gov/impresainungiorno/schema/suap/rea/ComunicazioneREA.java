//
// Questo file  stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andr persa durante la ricompilazione dello schema di origine. 
// Generato il: 2012.11.27 alle 11:16:28 AM CET 
//


package it.gov.impresainungiorno.schema.suap.rea;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import it.gov.impresainungiorno.schema.base.Indirizzo;
import it.gov.impresainungiorno.schema.suap.pratica.ProtocolloRI;
import it.gov.impresainungiorno.schema.suap.pratica.ProtocolloSUAP;
import it.gov.impresainungiorno.schema.suap.pratica.VersioneSchema;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * <p>Classe Java per ComunicazioneREA complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ComunicazioneREA">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="info-schema" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}VersioneSchema"/>
 *         &lt;element name="protocollo-suap" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}ProtocolloSUAP" minOccurs="0"/>
 *         &lt;element name="suap-mittente">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                 &lt;attribute name="id-suap" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="estremi-pratica-suap">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="protocollo-suap" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}ProtocolloSUAP"/>
 *                   &lt;element name="protocollo-ri" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}ProtocolloRI" minOccurs="0"/>
 *                   &lt;element name="impresa">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="denominazione" type="{http://www.impresainungiorno.gov.it/schema/base}Stringa"/>
 *                             &lt;element name="codice-fiscale" type="{http://www.impresainungiorno.gov.it/schema/base}CodiceFiscale"/>
 *                             &lt;element name="provincia-cciaa-competente" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}SiglaProvincia"/>
 *                             &lt;element name="indirizzo" type="{http://www.impresainungiorno.gov.it/schema/base}Indirizzo" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="oggetto" type="{http://www.impresainungiorno.gov.it/schema/suap/rea}OggettoComunicazioneREA"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="codice-pratica" use="required" type="{http://www.impresainungiorno.gov.it/schema/base}Stringa" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="stato-pratica" type="{http://www.impresainungiorno.gov.it/schema/suap/rea}StatoPratica"/>
 *         &lt;choice>
 *           &lt;element name="comunicazione-scia">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="allegato" type="{http://www.impresainungiorno.gov.it/schema/suap/rea}AllegatoSuap" maxOccurs="unbounded"/>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="comunicazione-esito-scia">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="esito" type="{http://www.impresainungiorno.gov.it/schema/suap/rea}EsitoScia"/>
 *                     &lt;element name="atto-ente" type="{http://www.impresainungiorno.gov.it/schema/suap/rea}AllegatoGenerico" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="lingua" type="{http://www.impresainungiorno.gov.it/schema/suap/rea}Lingua" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement(name="comunicazione-rea")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "comunicazioneREA", propOrder = {
    "infoSchema",
    "protocolloSuap",
    "suapMittente",
    "estremiPraticaSuap",
    "statoPratica",
    "comunicazioneScia",
    "comunicazioneEsitoScia"
})
public class ComunicazioneREA {

    @XmlElement(name = "info-schema", required = true)
    protected VersioneSchema infoSchema;
    @XmlElement(name = "protocollo-suap")
    protected ProtocolloSUAP protocolloSuap;
    @XmlElement(name = "suap-mittente", required = true)
    protected ComunicazioneREA.SuapMittente suapMittente;
    @XmlElement(name = "estremi-pratica-suap", required = true)
    protected ComunicazioneREA.EstremiPraticaSuap estremiPraticaSuap;
    @XmlElement(name = "stato-pratica", required = true)
    protected StatoPratica statoPratica;
    @XmlElement(name = "comunicazione-scia")
    protected ComunicazioneREA.ComunicazioneScia comunicazioneScia;
    @XmlElement(name = "comunicazione-esito-scia")
    protected ComunicazioneREA.ComunicazioneEsitoScia comunicazioneEsitoScia;
    @XmlAttribute(name = "lingua")
    protected String lingua;

    /**
     * Recupera il valore della propriet infoSchema.
     * 
     * @return
     *     possible object is
     *     {@link VersioneSchema }
     *     
     */
    public VersioneSchema getInfoSchema() {
        return infoSchema;
    }

    /**
     * Imposta il valore della propriet infoSchema.
     * 
     * @param value
     *     allowed object is
     *     {@link VersioneSchema }
     *     
     */
    public void setInfoSchema(VersioneSchema value) {
        this.infoSchema = value;
    }

    /**
     * Recupera il valore della propriet protocolloSuap.
     * 
     * @return
     *     possible object is
     *     {@link ProtocolloSUAP }
     *     
     */
    public ProtocolloSUAP getProtocolloSuap() {
        return protocolloSuap;
    }

    /**
     * Imposta il valore della propriet protocolloSuap.
     * 
     * @param value
     *     allowed object is
     *     {@link ProtocolloSUAP }
     *     
     */
    public void setProtocolloSuap(ProtocolloSUAP value) {
        this.protocolloSuap = value;
    }

    /**
     * Recupera il valore della propriet suapMittente.
     * 
     * @return
     *     possible object is
     *     {@link ComunicazioneREA.SuapMittente }
     *     
     */
    public ComunicazioneREA.SuapMittente getSuapMittente() {
        return suapMittente;
    }

    /**
     * Imposta il valore della propriet suapMittente.
     * 
     * @param value
     *     allowed object is
     *     {@link ComunicazioneREA.SuapMittente }
     *     
     */
    public void setSuapMittente(ComunicazioneREA.SuapMittente value) {
        this.suapMittente = value;
    }

    /**
     * Recupera il valore della propriet estremiPraticaSuap.
     * 
     * @return
     *     possible object is
     *     {@link ComunicazioneREA.EstremiPraticaSuap }
     *     
     */
    public ComunicazioneREA.EstremiPraticaSuap getEstremiPraticaSuap() {
        return estremiPraticaSuap;
    }

    /**
     * Imposta il valore della propriet estremiPraticaSuap.
     * 
     * @param value
     *     allowed object is
     *     {@link ComunicazioneREA.EstremiPraticaSuap }
     *     
     */
    public void setEstremiPraticaSuap(ComunicazioneREA.EstremiPraticaSuap value) {
        this.estremiPraticaSuap = value;
    }

    /**
     * Recupera il valore della propriet statoPratica.
     * 
     * @return
     *     possible object is
     *     {@link StatoPratica }
     *     
     */
    public StatoPratica getStatoPratica() {
        return statoPratica;
    }

    /**
     * Imposta il valore della propriet statoPratica.
     * 
     * @param value
     *     allowed object is
     *     {@link StatoPratica }
     *     
     */
    public void setStatoPratica(StatoPratica value) {
        this.statoPratica = value;
    }

    /**
     * Recupera il valore della propriet comunicazioneScia.
     * 
     * @return
     *     possible object is
     *     {@link ComunicazioneREA.ComunicazioneScia }
     *     
     */
    public ComunicazioneREA.ComunicazioneScia getComunicazioneScia() {
        return comunicazioneScia;
    }

    /**
     * Imposta il valore della propriet comunicazioneScia.
     * 
     * @param value
     *     allowed object is
     *     {@link ComunicazioneREA.ComunicazioneScia }
     *     
     */
    public void setComunicazioneScia(ComunicazioneREA.ComunicazioneScia value) {
        this.comunicazioneScia = value;
    }

    /**
     * Recupera il valore della propriet comunicazioneEsitoScia.
     * 
     * @return
     *     possible object is
     *     {@link ComunicazioneREA.ComunicazioneEsitoScia }
     *     
     */
    public ComunicazioneREA.ComunicazioneEsitoScia getComunicazioneEsitoScia() {
        return comunicazioneEsitoScia;
    }

    /**
     * Imposta il valore della propriet comunicazioneEsitoScia.
     * 
     * @param value
     *     allowed object is
     *     {@link ComunicazioneREA.ComunicazioneEsitoScia }
     *     
     */
    public void setComunicazioneEsitoScia(ComunicazioneREA.ComunicazioneEsitoScia value) {
        this.comunicazioneEsitoScia = value;
    }

    /**
     * Recupera il valore della propriet lingua.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLingua() {
        return lingua;
    }

    /**
     * Imposta il valore della propriet lingua.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLingua(String value) {
        this.lingua = value;
    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="esito" type="{http://www.impresainungiorno.gov.it/schema/suap/rea}EsitoScia"/>
     *         &lt;element name="atto-ente" type="{http://www.impresainungiorno.gov.it/schema/suap/rea}AllegatoGenerico" maxOccurs="unbounded" minOccurs="0"/>
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
        "esito",
        "attoEnte"
    })
    public static class ComunicazioneEsitoScia {

        @XmlElement(required = true)
        protected EsitoScia esito;
        @XmlElement(name = "atto-ente")
        protected List<AllegatoGenerico> attoEnte;

        /**
         * Recupera il valore della propriet esito.
         * 
         * @return
         *     possible object is
         *     {@link EsitoScia }
         *     
         */
        public EsitoScia getEsito() {
            return esito;
        }

        /**
         * Imposta il valore della propriet esito.
         * 
         * @param value
         *     allowed object is
         *     {@link EsitoScia }
         *     
         */
        public void setEsito(EsitoScia value) {
            this.esito = value;
        }

        /**
         * Gets the value of the attoEnte property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the attoEnte property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAttoEnte().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link AllegatoGenerico }
         * 
         * 
         */
        public List<AllegatoGenerico> getAttoEnte() {
            if (attoEnte == null) {
                attoEnte = new ArrayList<AllegatoGenerico>();
            }
            return this.attoEnte;
        }

    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="allegato" type="{http://www.impresainungiorno.gov.it/schema/suap/rea}AllegatoSuap" maxOccurs="unbounded"/>
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
        "allegato"
    })
    public static class ComunicazioneScia {

        @XmlElement(required = true)
        protected List<AllegatoSuap> allegato;

        /**
         * Gets the value of the allegato property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the allegato property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAllegato().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link AllegatoSuap }
         * 
         * 
         */
        public List<AllegatoSuap> getAllegato() {
            if (allegato == null) {
                allegato = new ArrayList<AllegatoSuap>();
            }
            return this.allegato;
        }

    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="protocollo-suap" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}ProtocolloSUAP"/>
     *         &lt;element name="protocollo-ri" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}ProtocolloRI" minOccurs="0"/>
     *         &lt;element name="impresa">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="denominazione" type="{http://www.impresainungiorno.gov.it/schema/base}Stringa"/>
     *                   &lt;element name="codice-fiscale" type="{http://www.impresainungiorno.gov.it/schema/base}CodiceFiscale"/>
     *                   &lt;element name="provincia-cciaa-competente" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}SiglaProvincia"/>
     *                   &lt;element name="indirizzo" type="{http://www.impresainungiorno.gov.it/schema/base}Indirizzo" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="oggetto" type="{http://www.impresainungiorno.gov.it/schema/suap/rea}OggettoComunicazioneREA"/>
     *       &lt;/sequence>
     *       &lt;attribute name="codice-pratica" use="required" type="{http://www.impresainungiorno.gov.it/schema/base}Stringa" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "protocolloSuap",
        "protocolloRi",
        "impresa",
        "oggetto"
    })
    public static class EstremiPraticaSuap {

        @XmlElement(name = "protocollo-suap", required = true)
        protected ProtocolloSUAP protocolloSuap;
        @XmlElement(name = "protocollo-ri")
        protected ProtocolloRI protocolloRi;
        @XmlElement(required = true)
        protected ComunicazioneREA.EstremiPraticaSuap.Impresa impresa;
        @XmlElement(required = true)
        protected OggettoComunicazioneREA oggetto;
        @XmlAttribute(name = "codice-pratica", required = true)
        protected String codicePratica;

        /**
         * Recupera il valore della propriet protocolloSuap.
         * 
         * @return
         *     possible object is
         *     {@link ProtocolloSUAP }
         *     
         */
        public ProtocolloSUAP getProtocolloSuap() {
            return protocolloSuap;
        }

        /**
         * Imposta il valore della propriet protocolloSuap.
         * 
         * @param value
         *     allowed object is
         *     {@link ProtocolloSUAP }
         *     
         */
        public void setProtocolloSuap(ProtocolloSUAP value) {
            this.protocolloSuap = value;
        }

        /**
         * Recupera il valore della propriet protocolloRi.
         * 
         * @return
         *     possible object is
         *     {@link ProtocolloRI }
         *     
         */
        public ProtocolloRI getProtocolloRi() {
            return protocolloRi;
        }

        /**
         * Imposta il valore della propriet protocolloRi.
         * 
         * @param value
         *     allowed object is
         *     {@link ProtocolloRI }
         *     
         */
        public void setProtocolloRi(ProtocolloRI value) {
            this.protocolloRi = value;
        }

        /**
         * Recupera il valore della propriet impresa.
         * 
         * @return
         *     possible object is
         *     {@link ComunicazioneREA.EstremiPraticaSuap.Impresa }
         *     
         */
        public ComunicazioneREA.EstremiPraticaSuap.Impresa getImpresa() {
            return impresa;
        }

        /**
         * Imposta il valore della propriet impresa.
         * 
         * @param value
         *     allowed object is
         *     {@link ComunicazioneREA.EstremiPraticaSuap.Impresa }
         *     
         */
        public void setImpresa(ComunicazioneREA.EstremiPraticaSuap.Impresa value) {
            this.impresa = value;
        }

        /**
         * Recupera il valore della propriet oggetto.
         * 
         * @return
         *     possible object is
         *     {@link OggettoComunicazioneREA }
         *     
         */
        public OggettoComunicazioneREA getOggetto() {
            return oggetto;
        }

        /**
         * Imposta il valore della propriet oggetto.
         * 
         * @param value
         *     allowed object is
         *     {@link OggettoComunicazioneREA }
         *     
         */
        public void setOggetto(OggettoComunicazioneREA value) {
            this.oggetto = value;
        }

        /**
         * Recupera il valore della propriet codicePratica.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCodicePratica() {
            return codicePratica;
        }

        /**
         * Imposta il valore della propriet codicePratica.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCodicePratica(String value) {
            this.codicePratica = value;
        }


        /**
         * <p>Classe Java per anonymous complex type.
         * 
         * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="denominazione" type="{http://www.impresainungiorno.gov.it/schema/base}Stringa"/>
         *         &lt;element name="codice-fiscale" type="{http://www.impresainungiorno.gov.it/schema/base}CodiceFiscale"/>
         *         &lt;element name="provincia-cciaa-competente" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}SiglaProvincia"/>
         *         &lt;element name="indirizzo" type="{http://www.impresainungiorno.gov.it/schema/base}Indirizzo" minOccurs="0"/>
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
            "denominazione",
            "codiceFiscale",
            "provinciaCciaaCompetente",
            "indirizzo"
        })
        public static class Impresa {

            @XmlElement(required = true)
            protected String denominazione;
            @XmlElement(name = "codice-fiscale", required = true)
            protected String codiceFiscale;
            @XmlElement(name = "provincia-cciaa-competente", required = true)
            protected String provinciaCciaaCompetente;
            protected Indirizzo indirizzo;

            /**
             * Recupera il valore della propriet denominazione.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDenominazione() {
                return denominazione;
            }

            /**
             * Imposta il valore della propriet denominazione.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDenominazione(String value) {
                this.denominazione = value;
            }

            /**
             * Recupera il valore della propriet codiceFiscale.
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
             * Imposta il valore della propriet codiceFiscale.
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
             * Recupera il valore della propriet provinciaCciaaCompetente.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getProvinciaCciaaCompetente() {
                return provinciaCciaaCompetente;
            }

            /**
             * Imposta il valore della propriet provinciaCciaaCompetente.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setProvinciaCciaaCompetente(String value) {
                this.provinciaCciaaCompetente = value;
            }

            /**
             * Recupera il valore della propriet indirizzo.
             * 
             * @return
             *     possible object is
             *     {@link Indirizzo }
             *     
             */
            public Indirizzo getIndirizzo() {
                return indirizzo;
            }

            /**
             * Imposta il valore della propriet indirizzo.
             * 
             * @param value
             *     allowed object is
             *     {@link Indirizzo }
             *     
             */
            public void setIndirizzo(Indirizzo value) {
                this.indirizzo = value;
            }

        }

    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *       &lt;attribute name="id-suap" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class SuapMittente {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "id-suap")
        @XmlSchemaType(name = "positiveInteger")
        protected BigInteger idSuap;

        /**
         * Recupera il valore della propriet value.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * Imposta il valore della propriet value.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Recupera il valore della propriet idSuap.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getIdSuap() {
            return idSuap;
        }

        /**
         * Imposta il valore della propriet idSuap.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setIdSuap(BigInteger value) {
            this.idSuap = value;
        }

    }

}
