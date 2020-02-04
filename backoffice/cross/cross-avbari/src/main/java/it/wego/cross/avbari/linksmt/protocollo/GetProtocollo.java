
package it.wego.cross.avbari.linksmt.protocollo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getProtocollo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getProtocollo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="protocolloInformazioniRequest" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="numeroProtocollo" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *                   &lt;element name="anno" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "getProtocollo", namespace = "http://server.ws.protocollo.linksmt.it/", propOrder = {
    "protocolloInformazioniRequest"
})
public class GetProtocollo {

    protected GetProtocollo.ProtocolloInformazioniRequest protocolloInformazioniRequest;

    /**
     * Gets the value of the protocolloInformazioniRequest property.
     * 
     * @return
     *     possible object is
     *     {@link GetProtocollo.ProtocolloInformazioniRequest }
     *     
     */
    public GetProtocollo.ProtocolloInformazioniRequest getProtocolloInformazioniRequest() {
        return protocolloInformazioniRequest;
    }

    /**
     * Sets the value of the protocolloInformazioniRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetProtocollo.ProtocolloInformazioniRequest }
     *     
     */
    public void setProtocolloInformazioniRequest(GetProtocollo.ProtocolloInformazioniRequest value) {
        this.protocolloInformazioniRequest = value;
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
     *         &lt;element name="numeroProtocollo" type="{http://www.w3.org/2001/XMLSchema}long"/>
     *         &lt;element name="anno" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "numeroProtocollo",
        "anno"
    })
    public static class ProtocolloInformazioniRequest {

        protected long numeroProtocollo;
        @XmlElement(required = true)
        protected String anno;

        /**
         * Gets the value of the numeroProtocollo property.
         * 
         */
        public long getNumeroProtocollo() {
            return numeroProtocollo;
        }

        /**
         * Sets the value of the numeroProtocollo property.
         * 
         */
        public void setNumeroProtocollo(long value) {
            this.numeroProtocollo = value;
        }

        /**
         * Gets the value of the anno property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAnno() {
            return anno;
        }

        /**
         * Sets the value of the anno property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAnno(String value) {
            this.anno = value;
        }

    }

}
