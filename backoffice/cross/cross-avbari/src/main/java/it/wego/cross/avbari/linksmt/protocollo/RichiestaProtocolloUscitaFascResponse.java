
package it.wego.cross.avbari.linksmt.protocollo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for richiestaProtocolloUscitaFascResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="richiestaProtocolloUscitaFascResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="numeroProtocollo" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *                   &lt;element name="anno" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="dataProtocollo" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *                   &lt;element name="errore" type="{http://protocollo.linksmt.it/RichiestaSOAP}Errore" minOccurs="0"/>
 *                   &lt;element name="esitoProtocollazione" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *                   &lt;element name="esitoCompletamentoProtocollo" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *                   &lt;element name="esitoFascicolazione" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *                   &lt;element name="esitoInvio" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
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
@XmlType(name = "richiestaProtocolloUscitaFascResponse", namespace = "http://server.ws.protocollo.linksmt.it/", propOrder = {
    "_return"
})
public class RichiestaProtocolloUscitaFascResponse {

    @XmlElement(name = "return")
    protected RichiestaProtocolloUscitaFascResponse.Return _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link RichiestaProtocolloUscitaFascResponse.Return }
     *     
     */
    public RichiestaProtocolloUscitaFascResponse.Return getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link RichiestaProtocolloUscitaFascResponse.Return }
     *     
     */
    public void setReturn(RichiestaProtocolloUscitaFascResponse.Return value) {
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
     *         &lt;element name="numeroProtocollo" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
     *         &lt;element name="anno" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="dataProtocollo" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
     *         &lt;element name="errore" type="{http://protocollo.linksmt.it/RichiestaSOAP}Errore" minOccurs="0"/>
     *         &lt;element name="esitoProtocollazione" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
     *         &lt;element name="esitoCompletamentoProtocollo" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
     *         &lt;element name="esitoFascicolazione" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
     *         &lt;element name="esitoInvio" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
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
        "anno",
        "dataProtocollo",
        "errore",
        "esitoProtocollazione",
        "esitoCompletamentoProtocollo",
        "esitoFascicolazione",
        "esitoInvio"
    })
    public static class Return {

        protected Long numeroProtocollo;
        protected int anno;
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar dataProtocollo;
        protected Errore errore;
        protected Boolean esitoProtocollazione;
        protected Boolean esitoCompletamentoProtocollo;
        protected Boolean esitoFascicolazione;
        protected Boolean esitoInvio;

        /**
         * Gets the value of the numeroProtocollo property.
         * 
         * @return
         *     possible object is
         *     {@link Long }
         *     
         */
        public Long getNumeroProtocollo() {
            return numeroProtocollo;
        }

        /**
         * Sets the value of the numeroProtocollo property.
         * 
         * @param value
         *     allowed object is
         *     {@link Long }
         *     
         */
        public void setNumeroProtocollo(Long value) {
            this.numeroProtocollo = value;
        }

        /**
         * Gets the value of the anno property.
         * 
         */
        public int getAnno() {
            return anno;
        }

        /**
         * Sets the value of the anno property.
         * 
         */
        public void setAnno(int value) {
            this.anno = value;
        }

        /**
         * Gets the value of the dataProtocollo property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getDataProtocollo() {
            return dataProtocollo;
        }

        /**
         * Sets the value of the dataProtocollo property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setDataProtocollo(XMLGregorianCalendar value) {
            this.dataProtocollo = value;
        }

        /**
         * Gets the value of the errore property.
         * 
         * @return
         *     possible object is
         *     {@link Errore }
         *     
         */
        public Errore getErrore() {
            return errore;
        }

        /**
         * Sets the value of the errore property.
         * 
         * @param value
         *     allowed object is
         *     {@link Errore }
         *     
         */
        public void setErrore(Errore value) {
            this.errore = value;
        }

        /**
         * Gets the value of the esitoProtocollazione property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isEsitoProtocollazione() {
            return esitoProtocollazione;
        }

        /**
         * Sets the value of the esitoProtocollazione property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setEsitoProtocollazione(Boolean value) {
            this.esitoProtocollazione = value;
        }

        /**
         * Gets the value of the esitoCompletamentoProtocollo property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isEsitoCompletamentoProtocollo() {
            return esitoCompletamentoProtocollo;
        }

        /**
         * Sets the value of the esitoCompletamentoProtocollo property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setEsitoCompletamentoProtocollo(Boolean value) {
            this.esitoCompletamentoProtocollo = value;
        }

        /**
         * Gets the value of the esitoFascicolazione property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isEsitoFascicolazione() {
            return esitoFascicolazione;
        }

        /**
         * Sets the value of the esitoFascicolazione property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setEsitoFascicolazione(Boolean value) {
            this.esitoFascicolazione = value;
        }

        /**
         * Gets the value of the esitoInvio property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isEsitoInvio() {
            return esitoInvio;
        }

        /**
         * Sets the value of the esitoInvio property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setEsitoInvio(Boolean value) {
            this.esitoInvio = value;
        }

    }

}
