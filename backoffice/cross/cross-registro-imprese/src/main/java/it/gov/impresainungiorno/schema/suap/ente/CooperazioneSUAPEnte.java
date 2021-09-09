//
// Questo file � stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andr� persa durante la ricompilazione dello schema di origine. 
// Generato il: 2020.05.18 alle 05:15:06 PM CEST 
//


package it.gov.impresainungiorno.schema.suap.ente;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import it.gov.impresainungiorno.schema.suap.pratica.AnagraficaImpresa;
import it.gov.impresainungiorno.schema.suap.pratica.EstremiEnte;
import it.gov.impresainungiorno.schema.suap.pratica.EstremiSuap;
import it.gov.impresainungiorno.schema.suap.pratica.OggettoComunicazione;
import it.gov.impresainungiorno.schema.suap.pratica.ProtocolloRI;
import it.gov.impresainungiorno.schema.suap.pratica.ProtocolloSUAP;
import org.w3._2001.xmlschema.Adapter1;


/**
 * <p>Classe Java per CooperazioneSUAPEnte complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="CooperazioneSUAPEnte">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="info-schema">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="versione" use="required">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                       &lt;pattern value="(\d+.)?\d+.\d+(-beta)?"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *                 &lt;attribute name="data" use="required" type="{http://www.w3.org/2001/XMLSchema}date" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="intestazione">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="suap-competente" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}EstremiSuap"/>
 *                   &lt;element name="ente-destinatario" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}EstremiEnte" maxOccurs="unbounded"/>
 *                   &lt;group ref="{http://www.impresainungiorno.gov.it/schema/suap/ente}RiferimentoPratica"/>
 *                   &lt;group ref="{http://www.impresainungiorno.gov.it/schema/suap/ente}ComunicazioneEnte"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="progressivo" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                 &lt;attribute name="totale" type="{http://www.w3.org/2001/XMLSchema}int" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="allegato" type="{http://www.impresainungiorno.gov.it/schema/suap/ente}AllegatoCooperazione" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CooperazioneSUAPEnte", propOrder = {
    "infoSchema",
    "intestazione",
    "allegato"
})
@XmlRootElement(name = "CooperazioneSUAPEnte")
public class CooperazioneSUAPEnte {

    @XmlElement(name = "info-schema", required = true)
    protected CooperazioneSUAPEnte.InfoSchema infoSchema;
    @XmlElement(required = true)
    protected CooperazioneSUAPEnte.Intestazione intestazione;
    protected List<AllegatoCooperazione> allegato;

    /**
     * Recupera il valore della propriet� infoSchema.
     * 
     * @return
     *     possible object is
     *     {@link CooperazioneSUAPEnte.InfoSchema }
     *     
     */
    public CooperazioneSUAPEnte.InfoSchema getInfoSchema() {
        return infoSchema;
    }

    /**
     * Imposta il valore della propriet� infoSchema.
     * 
     * @param value
     *     allowed object is
     *     {@link CooperazioneSUAPEnte.InfoSchema }
     *     
     */
    public void setInfoSchema(CooperazioneSUAPEnte.InfoSchema value) {
        this.infoSchema = value;
    }

    /**
     * Recupera il valore della propriet� intestazione.
     * 
     * @return
     *     possible object is
     *     {@link CooperazioneSUAPEnte.Intestazione }
     *     
     */
    public CooperazioneSUAPEnte.Intestazione getIntestazione() {
        return intestazione;
    }

    /**
     * Imposta il valore della propriet� intestazione.
     * 
     * @param value
     *     allowed object is
     *     {@link CooperazioneSUAPEnte.Intestazione }
     *     
     */
    public void setIntestazione(CooperazioneSUAPEnte.Intestazione value) {
        this.intestazione = value;
    }

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
     * {@link AllegatoCooperazione }
     * 
     * 
     */
    public List<AllegatoCooperazione> getAllegato() {
        if (allegato == null) {
            allegato = new ArrayList<AllegatoCooperazione>();
        }
        return this.allegato;
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
     *       &lt;attribute name="versione" use="required">
     *         &lt;simpleType>
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *             &lt;pattern value="(\d+.)?\d+.\d+(-beta)?"/>
     *           &lt;/restriction>
     *         &lt;/simpleType>
     *       &lt;/attribute>
     *       &lt;attribute name="data" use="required" type="{http://www.w3.org/2001/XMLSchema}date" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class InfoSchema {

        @XmlAttribute(name = "versione", required = true)
        protected String versione;
        @XmlAttribute(name = "data", required = true)
        @XmlJavaTypeAdapter(Adapter1 .class)
        @XmlSchemaType(name = "date")
        protected Date data;

        /**
         * Recupera il valore della propriet� versione.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getVersione() {
            return versione;
        }

        /**
         * Imposta il valore della propriet� versione.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setVersione(String value) {
            this.versione = value;
        }

        /**
         * Recupera il valore della propriet� data.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public Date getData() {
            return data;
        }

        /**
         * Imposta il valore della propriet� data.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setData(Date value) {
            this.data = value;
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
     *         &lt;element name="suap-competente" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}EstremiSuap"/>
     *         &lt;element name="ente-destinatario" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}EstremiEnte" maxOccurs="unbounded"/>
     *         &lt;group ref="{http://www.impresainungiorno.gov.it/schema/suap/ente}RiferimentoPratica"/>
     *         &lt;group ref="{http://www.impresainungiorno.gov.it/schema/suap/ente}ComunicazioneEnte"/>
     *       &lt;/sequence>
     *       &lt;attribute name="progressivo" type="{http://www.w3.org/2001/XMLSchema}int" />
     *       &lt;attribute name="totale" type="{http://www.w3.org/2001/XMLSchema}int" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "suapCompetente",
        "enteDestinatario",
        "codicePratica",
        "impresa",
        "oggettoPratica",
        "protocolloPraticaSuap",
        "protocolloRi",
        "oggettoComunicazione",
        "testoComunicazione",
        "protocollo"
    })
    public static class Intestazione {

        @XmlElement(name = "suap-competente", required = true)
        protected EstremiSuap suapCompetente;
        @XmlElement(name = "ente-destinatario", required = true)
        protected List<EstremiEnte> enteDestinatario;
        @XmlElement(name = "codice-pratica", required = true)
        protected String codicePratica;
        @XmlElement(required = true)
        protected AnagraficaImpresa impresa;
        @XmlElement(name = "oggetto-pratica", required = true)
        protected OggettoComunicazione oggettoPratica;
        @XmlElement(name = "protocollo-pratica-suap", required = true)
        protected ProtocolloSUAP protocolloPraticaSuap;
        @XmlElement(name = "protocollo-ri")
        protected ProtocolloRI protocolloRi;
        @XmlElement(name = "oggetto-comunicazione", required = true)
        protected OggettoCooperazione oggettoComunicazione;
        @XmlElement(name = "testo-comunicazione")
        protected String testoComunicazione;
        @XmlElement(required = true)
        protected ProtocolloSUAP protocollo;
        @XmlAttribute(name = "progressivo")
        protected Integer progressivo;
        @XmlAttribute(name = "totale")
        protected Integer totale;

        /**
         * Recupera il valore della propriet� suapCompetente.
         * 
         * @return
         *     possible object is
         *     {@link EstremiSuap }
         *     
         */
        public EstremiSuap getSuapCompetente() {
            return suapCompetente;
        }

        /**
         * Imposta il valore della propriet� suapCompetente.
         * 
         * @param value
         *     allowed object is
         *     {@link EstremiSuap }
         *     
         */
        public void setSuapCompetente(EstremiSuap value) {
            this.suapCompetente = value;
        }

        /**
         * Gets the value of the enteDestinatario property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the enteDestinatario property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getEnteDestinatario().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link EstremiEnte }
         * 
         * 
         */
        public List<EstremiEnte> getEnteDestinatario() {
            if (enteDestinatario == null) {
                enteDestinatario = new ArrayList<EstremiEnte>();
            }
            return this.enteDestinatario;
        }

        /**
         * Recupera il valore della propriet� codicePratica.
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
         * Imposta il valore della propriet� codicePratica.
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
         * Recupera il valore della propriet� impresa.
         * 
         * @return
         *     possible object is
         *     {@link AnagraficaImpresa }
         *     
         */
        public AnagraficaImpresa getImpresa() {
            return impresa;
        }

        /**
         * Imposta il valore della propriet� impresa.
         * 
         * @param value
         *     allowed object is
         *     {@link AnagraficaImpresa }
         *     
         */
        public void setImpresa(AnagraficaImpresa value) {
            this.impresa = value;
        }

        /**
         * Recupera il valore della propriet� oggettoPratica.
         * 
         * @return
         *     possible object is
         *     {@link OggettoComunicazione }
         *     
         */
        public OggettoComunicazione getOggettoPratica() {
            return oggettoPratica;
        }

        /**
         * Imposta il valore della propriet� oggettoPratica.
         * 
         * @param value
         *     allowed object is
         *     {@link OggettoComunicazione }
         *     
         */
        public void setOggettoPratica(OggettoComunicazione value) {
            this.oggettoPratica = value;
        }

        /**
         * Recupera il valore della propriet� protocolloPraticaSuap.
         * 
         * @return
         *     possible object is
         *     {@link ProtocolloSUAP }
         *     
         */
        public ProtocolloSUAP getProtocolloPraticaSuap() {
            return protocolloPraticaSuap;
        }

        /**
         * Imposta il valore della propriet� protocolloPraticaSuap.
         * 
         * @param value
         *     allowed object is
         *     {@link ProtocolloSUAP }
         *     
         */
        public void setProtocolloPraticaSuap(ProtocolloSUAP value) {
            this.protocolloPraticaSuap = value;
        }

        /**
         * Recupera il valore della propriet� protocolloRi.
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
         * Imposta il valore della propriet� protocolloRi.
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
         * Recupera il valore della propriet� oggettoComunicazione.
         * 
         * @return
         *     possible object is
         *     {@link OggettoCooperazione }
         *     
         */
        public OggettoCooperazione getOggettoComunicazione() {
            return oggettoComunicazione;
        }

        /**
         * Imposta il valore della propriet� oggettoComunicazione.
         * 
         * @param value
         *     allowed object is
         *     {@link OggettoCooperazione }
         *     
         */
        public void setOggettoComunicazione(OggettoCooperazione value) {
            this.oggettoComunicazione = value;
        }

        /**
         * Recupera il valore della propriet� testoComunicazione.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTestoComunicazione() {
            return testoComunicazione;
        }

        /**
         * Imposta il valore della propriet� testoComunicazione.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTestoComunicazione(String value) {
            this.testoComunicazione = value;
        }

        /**
         * Recupera il valore della propriet� protocollo.
         * 
         * @return
         *     possible object is
         *     {@link ProtocolloSUAP }
         *     
         */
        public ProtocolloSUAP getProtocollo() {
            return protocollo;
        }

        /**
         * Imposta il valore della propriet� protocollo.
         * 
         * @param value
         *     allowed object is
         *     {@link ProtocolloSUAP }
         *     
         */
        public void setProtocollo(ProtocolloSUAP value) {
            this.protocollo = value;
        }

        /**
         * Recupera il valore della propriet� progressivo.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getProgressivo() {
            return progressivo;
        }

        /**
         * Imposta il valore della propriet� progressivo.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setProgressivo(Integer value) {
            this.progressivo = value;
        }

        /**
         * Recupera il valore della propriet� totale.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getTotale() {
            return totale;
        }

        /**
         * Imposta il valore della propriet� totale.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setTotale(Integer value) {
            this.totale = value;
        }

    }

}
