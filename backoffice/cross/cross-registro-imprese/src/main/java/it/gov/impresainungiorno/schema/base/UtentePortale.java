
package it.gov.impresainungiorno.schema.base;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 *  Questo tipo contiene tutti i dati relativi al profilo dell'utente,
 *         ossia i dati immessi dall'utente al momento della registrazione: obbligatori sono codice fiscale, 
 *         nome e cognome, cosi' da poter creare l'utente di scrivania (non Archeometra)
 *         nel caso l'utente sia sconosciuto al sistema 
 * 
 * <p>Java class for UtentePortale complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UtentePortale">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.impresainungiorno.gov.it/schema/base}Anagrafica">
 *       &lt;sequence>
 *         &lt;element name="delegato" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.impresainungiorno.gov.it/schema/base>CodiceFiscalePersona">
 *                 &lt;attribute name="ente" use="required" type="{http://www.impresainungiorno.gov.it/schema/base}StringaBreve" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="domicilio" type="{http://www.impresainungiorno.gov.it/schema/base}IndirizzoConRecapiti" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UtentePortale", propOrder = {
    "delegato",
    "domicilio"
})
public class UtentePortale
    extends Anagrafica
{

    protected List<UtentePortale.Delegato> delegato;
    protected IndirizzoConRecapiti domicilio;

    /**
     * Gets the value of the delegato property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the delegato property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDelegato().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UtentePortale.Delegato }
     * 
     * 
     */
    public List<UtentePortale.Delegato> getDelegato() {
        if (delegato == null) {
            delegato = new ArrayList<UtentePortale.Delegato>();
        }
        return this.delegato;
    }

    /**
     * Gets the value of the domicilio property.
     * 
     * @return
     *     possible object is
     *     {@link IndirizzoConRecapiti }
     *     
     */
    public IndirizzoConRecapiti getDomicilio() {
        return domicilio;
    }

    /**
     * Sets the value of the domicilio property.
     * 
     * @param value
     *     allowed object is
     *     {@link IndirizzoConRecapiti }
     *     
     */
    public void setDomicilio(IndirizzoConRecapiti value) {
        this.domicilio = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.impresainungiorno.gov.it/schema/base>CodiceFiscalePersona">
     *       &lt;attribute name="ente" use="required" type="{http://www.impresainungiorno.gov.it/schema/base}StringaBreve" />
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
    public static class Delegato {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "ente", required = true)
        protected String ente;

        /**
         * Codice fiscale, di 16 caratteri, appartenente a persona fisica
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
         * Gets the value of the ente property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEnte() {
            return ente;
        }

        /**
         * Sets the value of the ente property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEnte(String value) {
            this.ente = value;
        }

    }

}
