
package it.linksmt.protocollo.ws.server;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getDestinatariProtocollo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getDestinatariProtocollo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="destinatarioProtocolloRequest" minOccurs="0">
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
@XmlType(name = "getDestinatariProtocollo", propOrder = {
    "destinatarioProtocolloRequest"
})
public class GetDestinatariProtocollo {

    protected GetDestinatariProtocollo.DestinatarioProtocolloRequest destinatarioProtocolloRequest;

    /**
     * Gets the value of the destinatarioProtocolloRequest property.
     * 
     * @return
     *     possible object is
     *     {@link GetDestinatariProtocollo.DestinatarioProtocolloRequest }
     *     
     */
    public GetDestinatariProtocollo.DestinatarioProtocolloRequest getDestinatarioProtocolloRequest() {
        return destinatarioProtocolloRequest;
    }

    /**
     * Sets the value of the destinatarioProtocolloRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetDestinatariProtocollo.DestinatarioProtocolloRequest }
     *     
     */
    public void setDestinatarioProtocolloRequest(GetDestinatariProtocollo.DestinatarioProtocolloRequest value) {
        this.destinatarioProtocolloRequest = value;
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
    public static class DestinatarioProtocolloRequest {

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
