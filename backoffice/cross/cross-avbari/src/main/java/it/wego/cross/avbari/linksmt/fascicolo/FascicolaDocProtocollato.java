
package it.wego.cross.avbari.linksmt.fascicolo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for fascicolaDocProtocollato complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="fascicolaDocProtocollato">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fascicolaDocumentoRequest" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="idProtocollo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="idVoceTitolario" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="idVoceFascicolo" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "fascicolaDocProtocollato", propOrder = {
    "fascicolaDocumentoRequest"
})
public class FascicolaDocProtocollato {

    protected FascicolaDocProtocollato.FascicolaDocumentoRequest fascicolaDocumentoRequest;

    /**
     * Gets the value of the fascicolaDocumentoRequest property.
     * 
     * @return
     *     possible object is
     *     {@link FascicolaDocProtocollato.FascicolaDocumentoRequest }
     *     
     */
    public FascicolaDocProtocollato.FascicolaDocumentoRequest getFascicolaDocumentoRequest() {
        return fascicolaDocumentoRequest;
    }

    /**
     * Sets the value of the fascicolaDocumentoRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link FascicolaDocProtocollato.FascicolaDocumentoRequest }
     *     
     */
    public void setFascicolaDocumentoRequest(FascicolaDocProtocollato.FascicolaDocumentoRequest value) {
        this.fascicolaDocumentoRequest = value;
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
     *         &lt;element name="idProtocollo" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="idVoceTitolario" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="idVoceFascicolo" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "idProtocollo",
        "idVoceTitolario",
        "idVoceFascicolo"
    })
    public static class FascicolaDocumentoRequest {

        @XmlElement(required = true)
        protected String idProtocollo;
        @XmlElement(required = true)
        protected String idVoceTitolario;
        @XmlElement(required = true)
        protected String idVoceFascicolo;

        /**
         * Gets the value of the idProtocollo property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIdProtocollo() {
            return idProtocollo;
        }

        /**
         * Sets the value of the idProtocollo property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIdProtocollo(String value) {
            this.idProtocollo = value;
        }

        /**
         * Gets the value of the idVoceTitolario property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIdVoceTitolario() {
            return idVoceTitolario;
        }

        /**
         * Sets the value of the idVoceTitolario property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIdVoceTitolario(String value) {
            this.idVoceTitolario = value;
        }

        /**
         * Gets the value of the idVoceFascicolo property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIdVoceFascicolo() {
            return idVoceFascicolo;
        }

        /**
         * Sets the value of the idVoceFascicolo property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIdVoceFascicolo(String value) {
            this.idVoceFascicolo = value;
        }

    }

}
