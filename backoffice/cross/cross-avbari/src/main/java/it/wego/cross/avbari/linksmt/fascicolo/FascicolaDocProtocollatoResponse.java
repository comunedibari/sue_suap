
package it.wego.cross.avbari.linksmt.fascicolo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for fascicolaDocProtocollatoResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="fascicolaDocProtocollatoResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="esitoFascicolazione" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
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
@XmlType(name = "fascicolaDocProtocollatoResponse", propOrder = {
    "_return"
})
public class FascicolaDocProtocollatoResponse {

    @XmlElement(name = "return")
    protected FascicolaDocProtocollatoResponse.Return _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link FascicolaDocProtocollatoResponse.Return }
     *     
     */
    public FascicolaDocProtocollatoResponse.Return getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link FascicolaDocProtocollatoResponse.Return }
     *     
     */
    public void setReturn(FascicolaDocProtocollatoResponse.Return value) {
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
     *         &lt;element name="esitoFascicolazione" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
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
        "esitoFascicolazione",
        "errore"
    })
    public static class Return {

        protected Boolean esitoFascicolazione;
        protected Errore errore;

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
