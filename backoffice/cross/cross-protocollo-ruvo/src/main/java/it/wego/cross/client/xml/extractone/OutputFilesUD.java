//
// Questo file stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrpersa durante la ricompilazione dello schema di origine. 
// Generato il: 2013.03.01 alle 08:38:09 AM CET 
//


package it.wego.cross.client.xml.extractone;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="DatiFileEstratto" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="NroAttachment" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/>
 *                   &lt;element name="NomeFile" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                   &lt;element name="NroVersione" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                   &lt;element name="NroUltimaVersione" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                   &lt;element name="RelazioneVsUD" type="{}OggDiTabDiSistemaType"/>
 *                   &lt;element name="NroAllegato" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
 *                         &lt;minInclusive value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="TipoDoc" type="{}OggDiTabDiSistemaType"/>
 *                   &lt;element name="DesOggetto" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
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
    "datiFileEstratto"
})
@XmlRootElement(name = "Output_FilesUD")
public class OutputFilesUD {

    @XmlElement(name = "DatiFileEstratto")
    protected List<OutputFilesUD.DatiFileEstratto> datiFileEstratto;

    /**
     * Gets the value of the datiFileEstratto property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the datiFileEstratto property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDatiFileEstratto().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OutputFilesUD.DatiFileEstratto }
     * 
     * 
     */
    public List<OutputFilesUD.DatiFileEstratto> getDatiFileEstratto() {
        if (datiFileEstratto == null) {
            datiFileEstratto = new ArrayList<OutputFilesUD.DatiFileEstratto>();
        }
        return this.datiFileEstratto;
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
     *         &lt;element name="NroAttachment" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/>
     *         &lt;element name="NomeFile" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *         &lt;element name="NroVersione" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *         &lt;element name="NroUltimaVersione" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *         &lt;element name="RelazioneVsUD" type="{}OggDiTabDiSistemaType"/>
     *         &lt;element name="NroAllegato" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
     *               &lt;minInclusive value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="TipoDoc" type="{}OggDiTabDiSistemaType"/>
     *         &lt;element name="DesOggetto" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "nroAttachment",
        "nomeFile",
        "nroVersione",
        "nroUltimaVersione",
        "relazioneVsUD",
        "nroAllegato",
        "tipoDoc",
        "desOggetto"
    })
    public static class DatiFileEstratto {

        @XmlElement(name = "NroAttachment", required = true)
        @XmlSchemaType(name = "positiveInteger")
        protected BigInteger nroAttachment;
        @XmlElement(name = "NomeFile", required = true)
        protected Object nomeFile;
        @XmlElement(name = "NroVersione", required = true)
        protected Object nroVersione;
        @XmlElement(name = "NroUltimaVersione", required = true)
        protected Object nroUltimaVersione;
        @XmlElement(name = "RelazioneVsUD", required = true)
        protected OggDiTabDiSistemaType relazioneVsUD;
        @XmlElement(name = "NroAllegato")
        protected BigInteger nroAllegato;
        @XmlElement(name = "TipoDoc", required = true)
        protected OggDiTabDiSistemaType tipoDoc;
        @XmlElement(name = "DesOggetto", required = true)
        protected String desOggetto;

        /**
         * Recupera il valore della proprietnroAttachment.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getNroAttachment() {
            return nroAttachment;
        }

        /**
         * Imposta il valore della proprietnroAttachment.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setNroAttachment(BigInteger value) {
            this.nroAttachment = value;
        }

        /**
         * Recupera il valore della proprietnomeFile.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getNomeFile() {
            return nomeFile;
        }

        /**
         * Imposta il valore della proprietnomeFile.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setNomeFile(Object value) {
            this.nomeFile = value;
        }

        /**
         * Recupera il valore della proprietnroVersione.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getNroVersione() {
            return nroVersione;
        }

        /**
         * Imposta il valore della proprietnroVersione.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setNroVersione(Object value) {
            this.nroVersione = value;
        }

        /**
         * Recupera il valore della proprietnroUltimaVersione.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getNroUltimaVersione() {
            return nroUltimaVersione;
        }

        /**
         * Imposta il valore della proprietnroUltimaVersione.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setNroUltimaVersione(Object value) {
            this.nroUltimaVersione = value;
        }

        /**
         * Recupera il valore della proprietrelazioneVsUD.
         * 
         * @return
         *     possible object is
         *     {@link OggDiTabDiSistemaType }
         *     
         */
        public OggDiTabDiSistemaType getRelazioneVsUD() {
            return relazioneVsUD;
        }

        /**
         * Imposta il valore della proprietrelazioneVsUD.
         * 
         * @param value
         *     allowed object is
         *     {@link OggDiTabDiSistemaType }
         *     
         */
        public void setRelazioneVsUD(OggDiTabDiSistemaType value) {
            this.relazioneVsUD = value;
        }

        /**
         * Recupera il valore della proprietnroAllegato.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getNroAllegato() {
            return nroAllegato;
        }

        /**
         * Imposta il valore della proprietnroAllegato.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setNroAllegato(BigInteger value) {
            this.nroAllegato = value;
        }

        /**
         * Recupera il valore della propriettipoDoc.
         * 
         * @return
         *     possible object is
         *     {@link OggDiTabDiSistemaType }
         *     
         */
        public OggDiTabDiSistemaType getTipoDoc() {
            return tipoDoc;
        }

        /**
         * Imposta il valore della propriettipoDoc.
         * 
         * @param value
         *     allowed object is
         *     {@link OggDiTabDiSistemaType }
         *     
         */
        public void setTipoDoc(OggDiTabDiSistemaType value) {
            this.tipoDoc = value;
        }

        /**
         * Recupera il valore della proprietdesOggetto.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDesOggetto() {
            return desOggetto;
        }

        /**
         * Imposta il valore della proprietdesOggetto.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDesOggetto(String value) {
            this.desOggetto = value;
        }

    }

}
