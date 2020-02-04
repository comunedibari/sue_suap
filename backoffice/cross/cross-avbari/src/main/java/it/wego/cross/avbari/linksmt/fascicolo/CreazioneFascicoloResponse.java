
package it.wego.cross.avbari.linksmt.fascicolo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for creazioneFascicoloResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="creazioneFascicoloResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="idFascicolo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "creazioneFascicoloResponse", propOrder = {
    "_return"
})
public class CreazioneFascicoloResponse {

    @XmlElement(name = "return")
    protected CreazioneFascicoloResponse.Return _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link CreazioneFascicoloResponse.Return }
     *     
     */
    public CreazioneFascicoloResponse.Return getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreazioneFascicoloResponse.Return }
     *     
     */
    public void setReturn(CreazioneFascicoloResponse.Return value) {
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
     *         &lt;element name="idFascicolo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        "idFascicolo",
        "errore"
    })
    public static class Return {

        protected String idFascicolo;
        protected Errore errore;

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
