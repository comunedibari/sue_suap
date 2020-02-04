
package it.gov.impresainungiorno.schema.base;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Un invio e' un tentativo da parte del portale di inviare un adempimento
 *         ad un ente. Fintanto che l'adempimento non e' accettato o respinto dall'ente, ossia
 *         finche' problemi di connettivita' impediscono all'ente di prendere in carico
 *         l'adempimento, il portale permette all'utente di tentare l'invio dell'adempimento.
 *         Tutti gli invii falliti vengono memorizzati
 * 
 * <p>Java class for InvioAdempimento complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InvioAdempimento">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="esito" type="{http://www.impresainungiorno.gov.it/schema/base}Stringa"/>
 *         &lt;element name="messaggio" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.impresainungiorno.gov.it/schema/base>Stringa">
 *                 &lt;attribute name="titolo" use="required" type="{http://www.impresainungiorno.gov.it/schema/base}Stringa" />
 *                 &lt;attribute name="attributo" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="tempo-invio" use="required" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InvioAdempimento", propOrder = {
    "esito",
    "messaggio"
})
public class InvioAdempimento {

    @XmlElement(required = true)
    protected String esito;
    protected List<InvioAdempimento.Messaggio> messaggio;
    @XmlAttribute(name = "tempo-invio", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar tempoInvio;

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
     * Gets the value of the messaggio property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the messaggio property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMessaggio().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InvioAdempimento.Messaggio }
     * 
     * 
     */
    public List<InvioAdempimento.Messaggio> getMessaggio() {
        if (messaggio == null) {
            messaggio = new ArrayList<InvioAdempimento.Messaggio>();
        }
        return this.messaggio;
    }

    /**
     * Gets the value of the tempoInvio property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTempoInvio() {
        return tempoInvio;
    }

    /**
     * Sets the value of the tempoInvio property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTempoInvio(XMLGregorianCalendar value) {
        this.tempoInvio = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.impresainungiorno.gov.it/schema/base>Stringa">
     *       &lt;attribute name="titolo" use="required" type="{http://www.impresainungiorno.gov.it/schema/base}Stringa" />
     *       &lt;attribute name="attributo" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class Messaggio {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "titolo", required = true)
        protected String titolo;
        @XmlAttribute(name = "attributo")
        protected String attributo;

        /**
         *  Questo elemento non puo' assumere valore stringa vuota, al piu' non c'e' 
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Gets the value of the titolo property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTitolo() {
            return titolo;
        }

        /**
         * Sets the value of the titolo property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTitolo(String value) {
            this.titolo = value;
        }

        /**
         * Gets the value of the attributo property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAttributo() {
            return attributo;
        }

        /**
         * Sets the value of the attributo property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAttributo(String value) {
            this.attributo = value;
        }

    }

}
