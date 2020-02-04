
package it.wego.cross.avbari.linksmt.protocollo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getAllegatoResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getAllegatoResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="allegato" type="{http://protocollo.linksmt.it/RichiestaSOAP}Allegato" minOccurs="0"/>
 *                   &lt;element name="errore" type="{http://protocollo.linksmt.it/RichiestaSOAP}Errore" minOccurs="0"/>
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
@XmlType(name = "getAllegatoResponse", namespace = "http://server.ws.protocollo.linksmt.it/", propOrder = {
    "_return"
})
public class GetAllegatoResponse {

    @XmlElement(name = "return")
    protected GetAllegatoResponse.Return _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link GetAllegatoResponse.Return }
     *     
     */
    public GetAllegatoResponse.Return getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetAllegatoResponse.Return }
     *     
     */
    public void setReturn(GetAllegatoResponse.Return value) {
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
     *         &lt;element name="allegato" type="{http://protocollo.linksmt.it/RichiestaSOAP}Allegato" minOccurs="0"/>
     *         &lt;element name="errore" type="{http://protocollo.linksmt.it/RichiestaSOAP}Errore" minOccurs="0"/>
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
        "errore"
    })
    public static class Return {

        protected Allegato allegato;
        protected Errore errore;

        /**
         * Gets the value of the allegato property.
         * 
         * @return
         *     possible object is
         *     {@link Allegato }
         *     
         */
        public Allegato getAllegato() {
            return allegato;
        }

        /**
         * Sets the value of the allegato property.
         * 
         * @param value
         *     allowed object is
         *     {@link Allegato }
         *     
         */
        public void setAllegato(Allegato value) {
            this.allegato = value;
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
