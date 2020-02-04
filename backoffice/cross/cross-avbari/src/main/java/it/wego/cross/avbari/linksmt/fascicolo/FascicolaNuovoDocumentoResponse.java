
package it.wego.cross.avbari.linksmt.fascicolo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for fascicolaNuovoDocumentoResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="fascicolaNuovoDocumentoResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="esito" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                   &lt;element name="errore" type="{http://server.ws.protocollo.linksmt.it/}Errore" minOccurs="0"/>
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
@XmlType(name = "fascicolaNuovoDocumentoResponse", propOrder = {
    "_return"
})
public class FascicolaNuovoDocumentoResponse {

    @XmlElement(name = "return")
    protected FascicolaNuovoDocumentoResponse.Return _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link FascicolaNuovoDocumentoResponse.Return }
     *     
     */
    public FascicolaNuovoDocumentoResponse.Return getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link FascicolaNuovoDocumentoResponse.Return }
     *     
     */
    public void setReturn(FascicolaNuovoDocumentoResponse.Return value) {
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
     *         &lt;element name="esito" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *         &lt;element name="errore" type="{http://server.ws.protocollo.linksmt.it/}Errore" minOccurs="0"/>
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
        "esito",
        "errore"
    })
    public static class Return {

        protected boolean esito;
        protected Errore errore;

        /**
         * Gets the value of the esito property.
         * 
         */
        public boolean isEsito() {
            return esito;
        }

        /**
         * Sets the value of the esito property.
         * 
         */
        public void setEsito(boolean value) {
            this.esito = value;
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

    }

}
