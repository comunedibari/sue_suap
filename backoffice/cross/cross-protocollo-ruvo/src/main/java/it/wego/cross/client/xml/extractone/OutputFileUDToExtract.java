//
// Questo file stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrpersa durante la ricompilazione dello schema di origine. 
// Generato il: 2013.03.01 alle 08:37:08 AM CET 
//


package it.wego.cross.client.xml.extractone;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


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
 *         &lt;element name="NomeFile" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *         &lt;element name="NroVersione" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *         &lt;element name="NroUltimaVersione" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *         &lt;element name="NroAllegato" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
 *               &lt;minInclusive value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="TipoDocAllegato" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="CodiceIdentificativo" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="DesAllegato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "nomeFile",
    "nroVersione",
    "nroUltimaVersione",
    "nroAllegato",
    "tipoDocAllegato",
    "desAllegato"
})
@XmlRootElement(name = "Output_FileUDToExtract")
public class OutputFileUDToExtract {

    @XmlElement(name = "NomeFile", required = true)
    protected Object nomeFile;
    @XmlElement(name = "NroVersione", required = true)
    protected Object nroVersione;
    @XmlElement(name = "NroUltimaVersione", required = true)
    protected Object nroUltimaVersione;
    @XmlElement(name = "NroAllegato")
    protected BigInteger nroAllegato;
    @XmlElement(name = "TipoDocAllegato")
    protected OutputFileUDToExtract.TipoDocAllegato tipoDocAllegato;
    @XmlElement(name = "DesAllegato")
    protected String desAllegato;

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
     * Recupera il valore della propriettipoDocAllegato.
     * 
     * @return
     *     possible object is
     *     {@link OutputFileUDToExtract.TipoDocAllegato }
     *     
     */
    public OutputFileUDToExtract.TipoDocAllegato getTipoDocAllegato() {
        return tipoDocAllegato;
    }

    /**
     * Imposta il valore della propriettipoDocAllegato.
     * 
     * @param value
     *     allowed object is
     *     {@link OutputFileUDToExtract.TipoDocAllegato }
     *     
     */
    public void setTipoDocAllegato(OutputFileUDToExtract.TipoDocAllegato value) {
        this.tipoDocAllegato = value;
    }

    /**
     * Recupera il valore della proprietdesAllegato.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesAllegato() {
        return desAllegato;
    }

    /**
     * Imposta il valore della proprietdesAllegato.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesAllegato(String value) {
        this.desAllegato = value;
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
     *       &lt;attribute name="CodiceIdentificativo" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "content"
    })
    public static class TipoDocAllegato {

        @XmlValue
        protected String content;
        @XmlAttribute(name = "CodiceIdentificativo", required = true)
        protected String codiceIdentificativo;

        /**
         * Recupera il valore della proprietcontent.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getContent() {
            return content;
        }

        /**
         * Imposta il valore della proprietcontent.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setContent(String value) {
            this.content = value;
        }

        /**
         * Recupera il valore della proprietcodiceIdentificativo.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCodiceIdentificativo() {
            return codiceIdentificativo;
        }

        /**
         * Imposta il valore della proprietcodiceIdentificativo.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCodiceIdentificativo(String value) {
            this.codiceIdentificativo = value;
        }

    }

}
