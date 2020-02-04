
package it.wego.cross;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ElencoPraticheResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ElencoPraticheResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Pratica" maxOccurs="unbounded" minOccurs="0" form="unqualified">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="idPratica" type="{http://www.w3.org/2001/XMLSchema}integer" form="unqualified"/>
 *                   &lt;element name="identificativoPratica" type="{http://www.w3.org/2001/XMLSchema}string" form="unqualified"/>
 *                   &lt;element name="oggetto" type="{http://www.w3.org/2001/XMLSchema}string" form="unqualified"/>
 *                   &lt;element name="dataRicezione" type="{http://www.w3.org/2001/XMLSchema}string" form="unqualified"/>
 *                   &lt;element name="idEnte" type="{http://www.w3.org/2001/XMLSchema}integer" form="unqualified"/>
 *                   &lt;element name="descrizioneEnte" type="{http://www.w3.org/2001/XMLSchema}string" form="unqualified"/>
 *                   &lt;element name="idComune" type="{http://www.w3.org/2001/XMLSchema}integer" form="unqualified"/>
 *                   &lt;element name="descrizioneComune" type="{http://www.w3.org/2001/XMLSchema}string" form="unqualified"/>
 *                   &lt;element name="codiceStatoPratica" type="{http://www.w3.org/2001/XMLSchema}string" form="unqualified"/>
 *                   &lt;element name="descrizioneStatoPratica" type="{http://www.w3.org/2001/XMLSchema}string" form="unqualified"/>
 *                   &lt;element name="ubicazione" type="{http://www.w3.org/2001/XMLSchema}string" form="unqualified"/>
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
@XmlType(name = "ElencoPraticheResponse", propOrder = {
    "pratica"
})
public class ElencoPraticheResponse {

    @XmlElement(name = "Pratica", namespace = "")
    protected List<ElencoPraticheResponse.Pratica> pratica;

    /**
     * Gets the value of the pratica property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pratica property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPratica().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ElencoPraticheResponse.Pratica }
     * 
     * 
     */
    public List<ElencoPraticheResponse.Pratica> getPratica() {
        if (pratica == null) {
            pratica = new ArrayList<ElencoPraticheResponse.Pratica>();
        }
        return this.pratica;
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
     *         &lt;element name="idPratica" type="{http://www.w3.org/2001/XMLSchema}integer" form="unqualified"/>
     *         &lt;element name="identificativoPratica" type="{http://www.w3.org/2001/XMLSchema}string" form="unqualified"/>
     *         &lt;element name="oggetto" type="{http://www.w3.org/2001/XMLSchema}string" form="unqualified"/>
     *         &lt;element name="dataRicezione" type="{http://www.w3.org/2001/XMLSchema}string" form="unqualified"/>
     *         &lt;element name="idEnte" type="{http://www.w3.org/2001/XMLSchema}integer" form="unqualified"/>
     *         &lt;element name="descrizioneEnte" type="{http://www.w3.org/2001/XMLSchema}string" form="unqualified"/>
     *         &lt;element name="idComune" type="{http://www.w3.org/2001/XMLSchema}integer" form="unqualified"/>
     *         &lt;element name="descrizioneComune" type="{http://www.w3.org/2001/XMLSchema}string" form="unqualified"/>
     *         &lt;element name="codiceStatoPratica" type="{http://www.w3.org/2001/XMLSchema}string" form="unqualified"/>
     *         &lt;element name="descrizioneStatoPratica" type="{http://www.w3.org/2001/XMLSchema}string" form="unqualified"/>
     *         &lt;element name="ubicazione" type="{http://www.w3.org/2001/XMLSchema}string" form="unqualified"/>
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
        "idPratica",
        "identificativoPratica",
        "oggetto",
        "dataRicezione",
        "idEnte",
        "descrizioneEnte",
        "idComune",
        "descrizioneComune",
        "codiceStatoPratica",
        "descrizioneStatoPratica",
        "ubicazione"
    })
    public static class Pratica {

        @XmlElement(namespace = "", required = true)
        protected BigInteger idPratica;
        @XmlElement(namespace = "", required = true)
        protected String identificativoPratica;
        @XmlElement(namespace = "", required = true)
        protected String oggetto;
        @XmlElement(namespace = "", required = true)
        protected String dataRicezione;
        @XmlElement(namespace = "", required = true)
        protected BigInteger idEnte;
        @XmlElement(namespace = "", required = true)
        protected String descrizioneEnte;
        @XmlElement(namespace = "", required = true)
        protected BigInteger idComune;
        @XmlElement(namespace = "", required = true)
        protected String descrizioneComune;
        @XmlElement(namespace = "", required = true)
        protected String codiceStatoPratica;
        @XmlElement(namespace = "", required = true)
        protected String descrizioneStatoPratica;
        @XmlElement(namespace = "", required = true)
        protected String ubicazione;

        /**
         * Gets the value of the idPratica property.
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
         * Sets the value of the idPratica property.
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
         * Gets the value of the identificativoPratica property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIdentificativoPratica() {
            return identificativoPratica;
        }

        /**
         * Sets the value of the identificativoPratica property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIdentificativoPratica(String value) {
            this.identificativoPratica = value;
        }

        /**
         * Gets the value of the oggetto property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOggetto() {
            return oggetto;
        }

        /**
         * Sets the value of the oggetto property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOggetto(String value) {
            this.oggetto = value;
        }

        /**
         * Gets the value of the dataRicezione property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDataRicezione() {
            return dataRicezione;
        }

        /**
         * Sets the value of the dataRicezione property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDataRicezione(String value) {
            this.dataRicezione = value;
        }

        /**
         * Gets the value of the idEnte property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getIdEnte() {
            return idEnte;
        }

        /**
         * Sets the value of the idEnte property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setIdEnte(BigInteger value) {
            this.idEnte = value;
        }

        /**
         * Gets the value of the descrizioneEnte property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDescrizioneEnte() {
            return descrizioneEnte;
        }

        /**
         * Sets the value of the descrizioneEnte property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDescrizioneEnte(String value) {
            this.descrizioneEnte = value;
        }

        /**
         * Gets the value of the idComune property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getIdComune() {
            return idComune;
        }

        /**
         * Sets the value of the idComune property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setIdComune(BigInteger value) {
            this.idComune = value;
        }

        /**
         * Gets the value of the descrizioneComune property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDescrizioneComune() {
            return descrizioneComune;
        }

        /**
         * Sets the value of the descrizioneComune property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDescrizioneComune(String value) {
            this.descrizioneComune = value;
        }

        /**
         * Gets the value of the codiceStatoPratica property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCodiceStatoPratica() {
            return codiceStatoPratica;
        }

        /**
         * Sets the value of the codiceStatoPratica property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCodiceStatoPratica(String value) {
            this.codiceStatoPratica = value;
        }

        /**
         * Gets the value of the descrizioneStatoPratica property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDescrizioneStatoPratica() {
            return descrizioneStatoPratica;
        }

        /**
         * Sets the value of the descrizioneStatoPratica property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDescrizioneStatoPratica(String value) {
            this.descrizioneStatoPratica = value;
        }

        /**
         * Gets the value of the ubicazione property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getUbicazione() {
            return ubicazione;
        }

        /**
         * Sets the value of the ubicazione property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setUbicazione(String value) {
            this.ubicazione = value;
        }

    }

}
