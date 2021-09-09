
package it.linksmt.protocollo.ws.server;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import it.linksmt.protocollo.richiestasoap.Destinatario;
import it.linksmt.protocollo.richiestasoap.Errore;
import it.linksmt.protocollo.richiestasoap.Mittente;


/**
 * <p>Java class for getDestinatariProtocolloResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getDestinatariProtocolloResponse">
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
 *                   &lt;element name="mittente" type="{http://protocollo.linksmt.it/RichiestaSOAP}Mittente" minOccurs="0"/>
 *                   &lt;element name="destinatari" type="{http://protocollo.linksmt.it/RichiestaSOAP}Destinatario" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "getDestinatariProtocolloResponse", propOrder = {
    "_return"
})
public class GetDestinatariProtocolloResponse {

    @XmlElement(name = "return")
    protected GetDestinatariProtocolloResponse.Return _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link GetDestinatariProtocolloResponse.Return }
     *     
     */
    public GetDestinatariProtocolloResponse.Return getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetDestinatariProtocolloResponse.Return }
     *     
     */
    public void setReturn(GetDestinatariProtocolloResponse.Return value) {
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
     *         &lt;element name="mittente" type="{http://protocollo.linksmt.it/RichiestaSOAP}Mittente" minOccurs="0"/>
     *         &lt;element name="destinatari" type="{http://protocollo.linksmt.it/RichiestaSOAP}Destinatario" maxOccurs="unbounded" minOccurs="0"/>
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
        "mittente",
        "destinatari"
    })
    public static class Return {

        protected Long numeroProtocollo;
        protected int anno;
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar dataProtocollo;
        protected Errore errore;
        protected Mittente mittente;
        @XmlElement(nillable = true)
        protected List<Destinatario> destinatari;

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
         * Gets the value of the mittente property.
         * 
         * @return
         *     possible object is
         *     {@link Mittente }
         *     
         */
        public Mittente getMittente() {
            return mittente;
        }

        /**
         * Sets the value of the mittente property.
         * 
         * @param value
         *     allowed object is
         *     {@link Mittente }
         *     
         */
        public void setMittente(Mittente value) {
            this.mittente = value;
        }

        /**
         * Gets the value of the destinatari property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the destinatari property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDestinatari().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Destinatario }
         * 
         * 
         */
        public List<Destinatario> getDestinatari() {
            if (destinatari == null) {
                destinatari = new ArrayList<Destinatario>();
            }
            return this.destinatari;
        }

    }

}
