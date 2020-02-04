
package it.wego.cross.avbari.atti.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getDocumentoDeterminaResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getDocumentoDeterminaResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="allegato" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="contenuto" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *                             &lt;element name="mimeType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="nomeFile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="esito" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "getDocumentoDeterminaResponse", propOrder = {
    "_return"
})
public class GetDocumentoDeterminaResponse {

    @XmlElement(name = "return")
    protected GetDocumentoDeterminaResponse.Return _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link GetDocumentoDeterminaResponse.Return }
     *     
     */
    public GetDocumentoDeterminaResponse.Return getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetDocumentoDeterminaResponse.Return }
     *     
     */
    public void setReturn(GetDocumentoDeterminaResponse.Return value) {
        this._return = value;
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
     *         &lt;element name="allegato" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="contenuto" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
     *                   &lt;element name="mimeType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="nomeFile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="esito" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        "allegato",
        "esito"
    })
    public static class Return {

        protected GetDocumentoDeterminaResponse.Return.Allegato allegato;
        protected String esito;

        /**
         * Gets the value of the allegato property.
         * 
         * @return
         *     possible object is
         *     {@link GetDocumentoDeterminaResponse.Return.Allegato }
         *     
         */
        public GetDocumentoDeterminaResponse.Return.Allegato getAllegato() {
            return allegato;
        }

        /**
         * Sets the value of the allegato property.
         * 
         * @param value
         *     allowed object is
         *     {@link GetDocumentoDeterminaResponse.Return.Allegato }
         *     
         */
        public void setAllegato(GetDocumentoDeterminaResponse.Return.Allegato value) {
            this.allegato = value;
        }

        /**
         * Gets the value of the esito property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEsito() {
            return esito;
        }

        /**
         * Sets the value of the esito property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEsito(String value) {
            this.esito = value;
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
         *         &lt;element name="contenuto" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
         *         &lt;element name="mimeType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="nomeFile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
            "contenuto",
            "mimeType",
            "nomeFile"
        })
        public static class Allegato {

            protected byte[] contenuto;
            protected String mimeType;
            protected String nomeFile;

            /**
             * Gets the value of the contenuto property.
             * 
             * @return
             *     possible object is
             *     byte[]
             */
            public byte[] getContenuto() {
                return contenuto;
            }

            /**
             * Sets the value of the contenuto property.
             * 
             * @param value
             *     allowed object is
             *     byte[]
             */
            public void setContenuto(byte[] value) {
                this.contenuto = value;
            }

            /**
             * Gets the value of the mimeType property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getMimeType() {
                return mimeType;
            }

            /**
             * Sets the value of the mimeType property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setMimeType(String value) {
                this.mimeType = value;
            }

            /**
             * Gets the value of the nomeFile property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNomeFile() {
                return nomeFile;
            }

            /**
             * Sets the value of the nomeFile property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNomeFile(String value) {
                this.nomeFile = value;
            }

        }

    }

}
