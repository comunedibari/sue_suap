//
// Questo file stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrpersa durante la ricompilazione dello schema di origine. 
// Generato il: PM.03.05 alle 07:27:19 PM CET 
//


package it.wego.cross.client.xml.checkout;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Contiene gli estremi per individuare un documento
 * 
 * <p>Classe Java per EstremiXIdentificazioneDocType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="EstremiXIdentificazioneDocType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="IdDoc" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;sequence>
 *           &lt;element name="EstremiXIdentificazioneUD" type="{}EstremiXIdentificazioneUDType"/>
 *           &lt;element name="DocVsUD">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;choice>
 *                     &lt;element name="FlagPrimario" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                     &lt;element name="NroAllegato">
 *                       &lt;simpleType>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
 *                           &lt;minInclusive value="1"/>
 *                         &lt;/restriction>
 *                       &lt;/simpleType>
 *                     &lt;/element>
 *                     &lt;sequence>
 *                       &lt;element name="NaturaRelVsUd" type="{}OggDiTabDiSistemaType" minOccurs="0"/>
 *                       &lt;element name="TipoDoc" type="{}OggDiTabDiSistemaType" minOccurs="0"/>
 *                       &lt;element name="DesOgg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                     &lt;/sequence>
 *                   &lt;/choice>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *         &lt;/sequence>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EstremiXIdentificazioneDocType", propOrder = {
    "idDoc",
    "estremiXIdentificazioneUD",
    "docVsUD"
})
public class EstremiXIdentificazioneDocType {

    @XmlElement(name = "IdDoc")
    protected BigInteger idDoc;
    @XmlElement(name = "EstremiXIdentificazioneUD")
    protected EstremiXIdentificazioneUDType estremiXIdentificazioneUD;
    @XmlElement(name = "DocVsUD")
    protected EstremiXIdentificazioneDocType.DocVsUD docVsUD;

    /**
     * Recupera il valore della proprietidDoc.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdDoc() {
        return idDoc;
    }

    /**
     * Imposta il valore della proprietidDoc.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdDoc(BigInteger value) {
        this.idDoc = value;
    }

    /**
     * Recupera il valore della proprietestremiXIdentificazioneUD.
     * 
     * @return
     *     possible object is
     *     {@link EstremiXIdentificazioneUDType }
     *     
     */
    public EstremiXIdentificazioneUDType getEstremiXIdentificazioneUD() {
        return estremiXIdentificazioneUD;
    }

    /**
     * Imposta il valore della proprietestremiXIdentificazioneUD.
     * 
     * @param value
     *     allowed object is
     *     {@link EstremiXIdentificazioneUDType }
     *     
     */
    public void setEstremiXIdentificazioneUD(EstremiXIdentificazioneUDType value) {
        this.estremiXIdentificazioneUD = value;
    }

    /**
     * Recupera il valore della proprietdocVsUD.
     * 
     * @return
     *     possible object is
     *     {@link EstremiXIdentificazioneDocType.DocVsUD }
     *     
     */
    public EstremiXIdentificazioneDocType.DocVsUD getDocVsUD() {
        return docVsUD;
    }

    /**
     * Imposta il valore della proprietdocVsUD.
     * 
     * @param value
     *     allowed object is
     *     {@link EstremiXIdentificazioneDocType.DocVsUD }
     *     
     */
    public void setDocVsUD(EstremiXIdentificazioneDocType.DocVsUD value) {
        this.docVsUD = value;
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
     *       &lt;choice>
     *         &lt;element name="FlagPrimario" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *         &lt;element name="NroAllegato">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
     *               &lt;minInclusive value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;sequence>
     *           &lt;element name="NaturaRelVsUd" type="{}OggDiTabDiSistemaType" minOccurs="0"/>
     *           &lt;element name="TipoDoc" type="{}OggDiTabDiSistemaType" minOccurs="0"/>
     *           &lt;element name="DesOgg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;/sequence>
     *       &lt;/choice>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "flagPrimario",
        "nroAllegato",
        "naturaRelVsUd",
        "tipoDoc",
        "desOgg"
    })
    public static class DocVsUD {

        @XmlElement(name = "FlagPrimario")
        protected Object flagPrimario;
        @XmlElement(name = "NroAllegato")
        protected BigInteger nroAllegato;
        @XmlElement(name = "NaturaRelVsUd")
        protected OggDiTabDiSistemaType naturaRelVsUd;
        @XmlElement(name = "TipoDoc")
        protected OggDiTabDiSistemaType tipoDoc;
        @XmlElement(name = "DesOgg")
        protected String desOgg;

        /**
         * Recupera il valore della proprietflagPrimario.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getFlagPrimario() {
            return flagPrimario;
        }

        /**
         * Imposta il valore della proprietflagPrimario.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setFlagPrimario(Object value) {
            this.flagPrimario = value;
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
         * Recupera il valore della proprietnaturaRelVsUd.
         * 
         * @return
         *     possible object is
         *     {@link OggDiTabDiSistemaType }
         *     
         */
        public OggDiTabDiSistemaType getNaturaRelVsUd() {
            return naturaRelVsUd;
        }

        /**
         * Imposta il valore della proprietnaturaRelVsUd.
         * 
         * @param value
         *     allowed object is
         *     {@link OggDiTabDiSistemaType }
         *     
         */
        public void setNaturaRelVsUd(OggDiTabDiSistemaType value) {
            this.naturaRelVsUd = value;
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
         * Recupera il valore della proprietdesOgg.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDesOgg() {
            return desOgg;
        }

        /**
         * Imposta il valore della proprietdesOgg.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDesOgg(String value) {
            this.desOgg = value;
        }

    }

}
