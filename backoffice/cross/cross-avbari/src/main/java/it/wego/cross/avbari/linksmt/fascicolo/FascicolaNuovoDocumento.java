
package it.wego.cross.avbari.linksmt.fascicolo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for fascicolaNuovoDocumento complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="fascicolaNuovoDocumento">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fascicolaNuovoDocumentoIN" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="idVoceTitolario" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="idFascicolo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="documento" type="{http://server.ws.protocollo.linksmt.it/}DocumentData"/>
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
@XmlType(name = "fascicolaNuovoDocumento", propOrder = {
    "fascicolaNuovoDocumentoIN"
})
public class FascicolaNuovoDocumento {

    protected FascicolaNuovoDocumento.FascicolaNuovoDocumentoIN fascicolaNuovoDocumentoIN;

    /**
     * Gets the value of the fascicolaNuovoDocumentoIN property.
     * 
     * @return
     *     possible object is
     *     {@link FascicolaNuovoDocumento.FascicolaNuovoDocumentoIN }
     *     
     */
    public FascicolaNuovoDocumento.FascicolaNuovoDocumentoIN getFascicolaNuovoDocumentoIN() {
        return fascicolaNuovoDocumentoIN;
    }

    /**
     * Sets the value of the fascicolaNuovoDocumentoIN property.
     * 
     * @param value
     *     allowed object is
     *     {@link FascicolaNuovoDocumento.FascicolaNuovoDocumentoIN }
     *     
     */
    public void setFascicolaNuovoDocumentoIN(FascicolaNuovoDocumento.FascicolaNuovoDocumentoIN value) {
        this.fascicolaNuovoDocumentoIN = value;
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
     *         &lt;element name="idVoceTitolario" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="idFascicolo" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="documento" type="{http://server.ws.protocollo.linksmt.it/}DocumentData"/>
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
        "idVoceTitolario",
        "idFascicolo",
        "documento"
    })
    public static class FascicolaNuovoDocumentoIN {

        @XmlElement(required = true)
        protected String idVoceTitolario;
        @XmlElement(required = true)
        protected String idFascicolo;
        @XmlElement(required = true)
        protected DocumentData documento;

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
         * Gets the value of the idFascicolo property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIdFascicolo() {
            return idFascicolo;
        }

        /**
         * Sets the value of the idFascicolo property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIdFascicolo(String value) {
            this.idFascicolo = value;
        }

        /**
         * Gets the value of the documento property.
         * 
         * @return
         *     possible object is
         *     {@link DocumentData }
         *     
         */
        public DocumentData getDocumento() {
            return documento;
        }

        /**
         * Sets the value of the documento property.
         * 
         * @param value
         *     allowed object is
         *     {@link DocumentData }
         *     
         */
        public void setDocumento(DocumentData value) {
            this.documento = value;
        }

    }

}
