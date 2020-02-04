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
 * <p>Classe Java per professionista complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="professionista">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tipoRecord" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="concatenazione" type="{http://www.wego.it/cross}concatenazione"/>
 *         &lt;element name="dettaglioProfessionista">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="codiceFiscale">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="16"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="alboProfessionale">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;enumeration value="1"/>
 *                         &lt;enumeration value="2"/>
 *                         &lt;enumeration value="3"/>
 *                         &lt;enumeration value="4"/>
 *                         &lt;enumeration value="5"/>
 *                         &lt;enumeration value="6"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="provinciaAlbo">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="2"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="numeroIscrizioneAlbo">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="10"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="qualificaProfessionale">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;enumeration value="0"/>
 *                         &lt;enumeration value="1"/>
 *                         &lt;enumeration value="2"/>
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
@XmlType(name = "professionista", propOrder = {
    "tipoRecord",
    "concatenazione",
    "dettaglioProfessionista",
    "controllo"
})
public class Professionista {

    @XmlElement(required = true)
    protected String tipoRecord;
    @XmlElement(required = true)
    protected Concatenazione concatenazione;
    @XmlElement(required = true)
    protected Professionista.DettaglioProfessionista dettaglioProfessionista;
    @XmlElement(required = true)
    protected Controllo controllo;

    /**
     * Recupera il valore della proprieta' tipoRecord.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoRecord() {
        return tipoRecord;
    }

    /**
     * Imposta il valore della proprieta' tipoRecord.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoRecord(String value) {
        this.tipoRecord = value;
    }

    /**
     * Recupera il valore della proprieta' concatenazione.
     * 
     * @return
     *     possible object is
     *     {@link Concatenazione }
     *     
     */
    public Concatenazione getConcatenazione() {
        return concatenazione;
    }

    /**
     * Imposta il valore della proprieta' concatenazione.
     * 
     * @param value
     *     allowed object is
     *     {@link Concatenazione }
     *     
     */
    public void setConcatenazione(Concatenazione value) {
        this.concatenazione = value;
    }

    /**
     * Recupera il valore della proprieta' dettaglioProfessionista.
     * 
     * @return
     *     possible object is
     *     {@link Professionista.DettaglioProfessionista }
     *     
     */
    public Professionista.DettaglioProfessionista getDettaglioProfessionista() {
        return dettaglioProfessionista;
    }

    /**
     * Imposta il valore della proprieta' dettaglioProfessionista.
     * 
     * @param value
     *     allowed object is
     *     {@link Professionista.DettaglioProfessionista }
     *     
     */
    public void setDettaglioProfessionista(Professionista.DettaglioProfessionista value) {
        this.dettaglioProfessionista = value;
    }

    /**
     * Recupera il valore della proprieta' controllo.
     * 
     * @return
     *     possible object is
     *     {@link Controllo }
     *     
     */
    public Controllo getControllo() {
        return controllo;
    }

    /**
     * Imposta il valore della proprieta' controllo.
     * 
     * @param value
     *     allowed object is
     *     {@link Controllo }
     *     
     */
    public void setControllo(Controllo value) {
        this.controllo = value;
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
     *         &lt;element name="codiceFiscale">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="16"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="alboProfessionale">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;enumeration value="1"/>
     *               &lt;enumeration value="2"/>
     *               &lt;enumeration value="3"/>
     *               &lt;enumeration value="4"/>
     *               &lt;enumeration value="5"/>
     *               &lt;enumeration value="6"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="provinciaAlbo">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="2"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="numeroIscrizioneAlbo">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="10"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="qualificaProfessionale">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;enumeration value="0"/>
     *               &lt;enumeration value="1"/>
     *               &lt;enumeration value="2"/>
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
        "codiceFiscale",
        "alboProfessionale",
        "provinciaAlbo",
        "numeroIscrizioneAlbo",
        "qualificaProfessionale"
    })
    public static class DettaglioProfessionista {

        @XmlElement(required = true)
        protected String codiceFiscale;
        @XmlElement(required = true)
        protected String alboProfessionale;
        @XmlElement(required = true)
        protected String provinciaAlbo;
        @XmlElement(required = true)
        protected String numeroIscrizioneAlbo;
        @XmlElement(required = true)
        protected String qualificaProfessionale;

        /**
         * Recupera il valore della proprieta' codiceFiscale.
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
         * Imposta il valore della proprieta' codiceFiscale.
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
         * Recupera il valore della proprieta' alboProfessionale.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAlboProfessionale() {
            return alboProfessionale;
        }

        /**
         * Imposta il valore della proprieta' alboProfessionale.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAlboProfessionale(String value) {
            this.alboProfessionale = value;
        }

        /**
         * Recupera il valore della proprieta' provinciaAlbo.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getProvinciaAlbo() {
            return provinciaAlbo;
        }

        /**
         * Imposta il valore della proprieta' provinciaAlbo.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setProvinciaAlbo(String value) {
            this.provinciaAlbo = value;
        }

        /**
         * Recupera il valore della proprieta' numeroIscrizioneAlbo.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNumeroIscrizioneAlbo() {
            return numeroIscrizioneAlbo;
        }

        /**
         * Imposta il valore della proprieta' numeroIscrizioneAlbo.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNumeroIscrizioneAlbo(String value) {
            this.numeroIscrizioneAlbo = value;
        }

        /**
         * Recupera il valore della proprieta' qualificaProfessionale.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getQualificaProfessionale() {
            return qualificaProfessionale;
        }

        /**
         * Imposta il valore della proprieta' qualificaProfessionale.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setQualificaProfessionale(String value) {
            this.qualificaProfessionale = value;
        }

    }

}
