//
// Questo file e' stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andra' persa durante la ricompilazione dello schema di origine. 
// Generato il: 2013.02.01 alle 04:09:31 PM CET 
//
package it.wego.cross.xml.anagrafetributaria.edilizia;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Classe Java per identificazioneRichiesta complex type.
 *
 * <p>
 * Il seguente frammento di schema specifica il contenuto previsto contenuto in
 * questa classe.
 *
 * <pre>
 * &lt;complexType name="identificazioneRichiesta">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tipoRecord" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="soggettoRichiedente" type="{http://www.wego.it/cross}soggetto"/>
 *         &lt;element name="qualificaRichiedente">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="1"/>
 *               &lt;enumeration value="2"/>
 *               &lt;enumeration value="3"/>
 *               &lt;enumeration value="4"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="richiesta">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="tipoRichiesta">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;enumeration value="0"/>
 *                         &lt;enumeration value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="tipologiaIntervento">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;enumeration value="1"/>
 *                         &lt;enumeration value="2"/>
 *                         &lt;enumeration value="3"/>
 *                         &lt;enumeration value="4"/>
 *                         &lt;enumeration value="5"/>
 *                         &lt;enumeration value="6"/>
 *                         &lt;enumeration value="7"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="numeroProtocollo">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="20"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="tipologiaRichiesta">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;enumeration value="0"/>
 *                         &lt;enumeration value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="dataPresentazioneRichiesta">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.wego.it/cross}data">
 *                         &lt;pattern value="(0[1-9]|[12][0-9]|3[01])(0[1-9]|1[012])(19|20)(\d{2})"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="dataInizioLavori" type="{http://www.wego.it/cross}data"/>
 *                   &lt;element name="dataFineLavori" type="{http://www.wego.it/cross}data"/>
 *                   &lt;element name="indirizzoOggettoIstanza">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="35"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="controllo" type="{http://www.wego.it/cross}controllo"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "identificazioneRichiesta", propOrder = {
    "tipoRecord",
    "soggettoRichiedente",
    "qualificaRichiedente",
    "richiesta",
    "controllo"
})
public class IdentificazioneRichiesta {

    @XmlElement(required = true)
    protected String tipoRecord;
    @XmlElement(required = true)
    protected Soggetto soggettoRichiedente;
    @XmlElement(required = true)
    protected String qualificaRichiedente;
    @XmlElement(required = true)
    protected IdentificazioneRichiesta.Richiesta richiesta;
    @XmlElement(required = true)
    protected Controllo controllo;

    /**
     * Recupera il valore della proprieta' tipoRecord.
     *
     * @return possible object is {@link String }
     *
     */
    public String getTipoRecord() {
        return tipoRecord;
    }

    /**
     * Imposta il valore della proprieta' tipoRecord.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setTipoRecord(String value) {
        this.tipoRecord = value;
    }

    /**
     * Recupera il valore della proprieta' soggettoRichiedente.
     *
     * @return possible object is {@link Soggetto }
     *
     */
    public Soggetto getSoggettoRichiedente() {
        return soggettoRichiedente;
    }

    /**
     * Imposta il valore della proprieta' soggettoRichiedente.
     *
     * @param value allowed object is {@link Soggetto }
     *
     */
    public void setSoggettoRichiedente(Soggetto value) {
        this.soggettoRichiedente = value;
    }

    /**
     * Recupera il valore della proprieta' qualificaRichiedente.
     *
     * @return possible object is {@link String }
     *
     */
    public String getQualificaRichiedente() {
        return qualificaRichiedente;
    }

    /**
     * Imposta il valore della proprieta' qualificaRichiedente.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setQualificaRichiedente(String value) {
        this.qualificaRichiedente = value;
    }

    /**
     * Recupera il valore della proprieta' richiesta.
     *
     * @return possible object is {@link IdentificazioneRichiesta.Richiesta }
     *
     */
    public IdentificazioneRichiesta.Richiesta getRichiesta() {
        return richiesta;
    }

    /**
     * Imposta il valore della proprieta' richiesta.
     *
     * @param value allowed object is
     *     {@link IdentificazioneRichiesta.Richiesta }
     *
     */
    public void setRichiesta(IdentificazioneRichiesta.Richiesta value) {
        this.richiesta = value;
    }

    /**
     * Recupera il valore della proprieta' controllo.
     *
     * @return possible object is {@link Controllo }
     *
     */
    public Controllo getControllo() {
        return controllo;
    }

    /**
     * Imposta il valore della proprieta' controllo.
     *
     * @param value allowed object is {@link Controllo }
     *
     */
    public void setControllo(Controllo value) {
        this.controllo = value;
    }

    /**
     * <p>
     * Classe Java per anonymous complex type.
     *
     * <p>
     * Il seguente frammento di schema specifica il contenuto previsto contenuto
     * in questa classe.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="tipoRichiesta">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;enumeration value="0"/>
     *               &lt;enumeration value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="tipologiaIntervento">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;enumeration value="1"/>
     *               &lt;enumeration value="2"/>
     *               &lt;enumeration value="3"/>
     *               &lt;enumeration value="4"/>
     *               &lt;enumeration value="5"/>
     *               &lt;enumeration value="6"/>
     *               &lt;enumeration value="7"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="numeroProtocollo">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="20"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="tipologiaRichiesta">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;enumeration value="0"/>
     *               &lt;enumeration value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="dataPresentazioneRichiesta">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.wego.it/cross}data">
     *               &lt;pattern value="(0[1-9]|[12][0-9]|3[01])(0[1-9]|1[012])(19|20)(\d{2})"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="dataInizioLavori" type="{http://www.wego.it/cross}data"/>
     *         &lt;element name="dataFineLavori" type="{http://www.wego.it/cross}data"/>
     *         &lt;element name="indirizzoOggettoIstanza">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="35"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
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
        "tipoRichiesta",
        "tipologiaIntervento",
        "numeroProtocollo",
        "tipologiaRichiesta",
        "dataPresentazioneRichiesta",
        "dataInizioLavori",
        "dataFineLavori",
        "idIndirizzoOggettoIstanza",
        "indirizzoOggettoIstanza"
    })
    public static class Richiesta {

        @XmlElement(required = true)
        protected String tipoRichiesta;
        @XmlElement(required = true)
        protected String tipologiaIntervento;
        @XmlElement(required = true)
        protected String numeroProtocollo;
        @XmlElement(required = true)
        protected String tipologiaRichiesta;
        @XmlElement(required = true, nillable = true)
        protected String dataPresentazioneRichiesta;
        @XmlElement(required = true, nillable = true)
        protected String dataInizioLavori;
        @XmlElement(required = true, nillable = true)
        protected String dataFineLavori;
        @XmlElement(required = true)
        protected String idIndirizzoOggettoIstanza;
        @XmlElement(required = true)
        protected String indirizzoOggettoIstanza;

        /**
         * Recupera il valore della proprieta' tipoRichiesta.
         *
         * @return possible object is {@link String }
         *
         */
        public String getTipoRichiesta() {
            return tipoRichiesta;
        }

        /**
         * Imposta il valore della proprieta' tipoRichiesta.
         *
         * @param value allowed object is {@link String }
         *
         */
        public void setTipoRichiesta(String value) {
            this.tipoRichiesta = value;
        }

        /**
         * Recupera il valore della proprieta' tipologiaIntervento.
         *
         * @return possible object is {@link String }
         *
         */
        public String getTipologiaIntervento() {
            return tipologiaIntervento;
        }

        /**
         * Imposta il valore della proprieta' tipologiaIntervento.
         *
         * @param value allowed object is {@link String }
         *
         */
        public void setTipologiaIntervento(String value) {
            this.tipologiaIntervento = value;
        }

        /**
         * Recupera il valore della proprieta' numeroProtocollo.
         *
         * @return possible object is {@link String }
         *
         */
        public String getNumeroProtocollo() {
            return numeroProtocollo;
        }

        /**
         * Imposta il valore della proprieta' numeroProtocollo.
         *
         * @param value allowed object is {@link String }
         *
         */
        public void setNumeroProtocollo(String value) {
            this.numeroProtocollo = value;
        }

        /**
         * Recupera il valore della proprieta' tipologiaRichiesta.
         *
         * @return possible object is {@link String }
         *
         */
        public String getTipologiaRichiesta() {
            return tipologiaRichiesta;
        }

        /**
         * Imposta il valore della proprieta' tipologiaRichiesta.
         *
         * @param value allowed object is {@link String }
         *
         */
        public void setTipologiaRichiesta(String value) {
            this.tipologiaRichiesta = value;
        }

        /**
         * Recupera il valore della proprieta' dataPresentazioneRichiesta.
         *
         * @return possible object is {@link String }
         *
         */
        public String getDataPresentazioneRichiesta() {
            return dataPresentazioneRichiesta;
        }

        /**
         * Imposta il valore della proprieta' dataPresentazioneRichiesta.
         *
         * @param value allowed object is {@link String }
         *
         */
        public void setDataPresentazioneRichiesta(String value) {
            this.dataPresentazioneRichiesta = value;
        }

        /**
         * Recupera il valore della proprieta' dataInizioLavori.
         *
         * @return possible object is {@link String }
         *
         */
        public String getDataInizioLavori() {
            return dataInizioLavori;
        }

        /**
         * Imposta il valore della proprieta' dataInizioLavori.
         *
         * @param value allowed object is {@link String }
         *
         */
        public void setDataInizioLavori(String value) {
            this.dataInizioLavori = value;
        }

        /**
         * Recupera il valore della proprieta' dataFineLavori.
         *
         * @return possible object is {@link String }
         *
         */
        public String getDataFineLavori() {
            return dataFineLavori;
        }

        /**
         * Imposta il valore della proprieta' dataFineLavori.
         *
         * @param value allowed object is {@link String }
         *
         */
        public void setDataFineLavori(String value) {
            this.dataFineLavori = value;
        }

        public String getIdIndirizzoOggettoIstanza() {
            return idIndirizzoOggettoIstanza;
        }

        public void setIdIndirizzoOggettoIstanza(String idIndirizzoOggettoIstanza) {
            this.idIndirizzoOggettoIstanza = idIndirizzoOggettoIstanza;
        }

        /**
         * Recupera il valore della proprieta' indirizzoOggettoIstanza.
         *
         * @return possible object is {@link String }
         *
         */
        public String getIndirizzoOggettoIstanza() {
            return indirizzoOggettoIstanza;
        }

        /**
         * Imposta il valore della proprieta' indirizzoOggettoIstanza.
         *
         * @param value allowed object is {@link String }
         *
         */
        public void setIndirizzoOggettoIstanza(String value) {
            this.indirizzoOggettoIstanza = value;
        }

    }

}
