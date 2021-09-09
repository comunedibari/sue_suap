
package it.linksmt.protocollo.ws.server;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import it.linksmt.protocollo.richiestasoap.Allegato;


/**
 * <p>Java class for addAllegatura complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="addAllegatura">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="allegaturaRequest" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="amministrazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="areaOrganizzativaOmogenea" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="numeroProtocollo" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *                   &lt;element name="anno" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="idUtente" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *                   &lt;element name="allegati" type="{http://protocollo.linksmt.it/RichiestaSOAP}Allegato" maxOccurs="unbounded"/>
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
@XmlType(name = "addAllegatura", propOrder = {
    "allegaturaRequest"
})
public class AddAllegatura {

    protected AddAllegatura.AllegaturaRequest allegaturaRequest;

    /**
     * Gets the value of the allegaturaRequest property.
     * 
     * @return
     *     possible object is
     *     {@link AddAllegatura.AllegaturaRequest }
     *     
     */
    public AddAllegatura.AllegaturaRequest getAllegaturaRequest() {
        return allegaturaRequest;
    }

    /**
     * Sets the value of the allegaturaRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddAllegatura.AllegaturaRequest }
     *     
     */
    public void setAllegaturaRequest(AddAllegatura.AllegaturaRequest value) {
        this.allegaturaRequest = value;
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
     *         &lt;element name="amministrazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="areaOrganizzativaOmogenea" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="numeroProtocollo" type="{http://www.w3.org/2001/XMLSchema}long"/>
     *         &lt;element name="anno" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="idUtente" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
     *         &lt;element name="allegati" type="{http://protocollo.linksmt.it/RichiestaSOAP}Allegato" maxOccurs="unbounded"/>
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
        "amministrazione",
        "areaOrganizzativaOmogenea",
        "numeroProtocollo",
        "anno",
        "idUtente",
        "allegati"
    })
    public static class AllegaturaRequest {

        @XmlElement(required = true)
        protected String amministrazione;
        @XmlElement(required = true)
        protected String areaOrganizzativaOmogenea;
        protected long numeroProtocollo;
        @XmlElement(required = true)
        protected String anno;
        protected Integer idUtente;
        @XmlElement(required = true)
        protected List<Allegato> allegati;

        /**
         * Gets the value of the amministrazione property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAmministrazione() {
            return amministrazione;
        }

        /**
         * Sets the value of the amministrazione property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAmministrazione(String value) {
            this.amministrazione = value;
        }

        /**
         * Gets the value of the areaOrganizzativaOmogenea property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAreaOrganizzativaOmogenea() {
            return areaOrganizzativaOmogenea;
        }

        /**
         * Sets the value of the areaOrganizzativaOmogenea property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAreaOrganizzativaOmogenea(String value) {
            this.areaOrganizzativaOmogenea = value;
        }

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

        /**
         * Gets the value of the idUtente property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getIdUtente() {
            return idUtente;
        }

        /**
         * Sets the value of the idUtente property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setIdUtente(Integer value) {
            this.idUtente = value;
        }

        /**
         * Gets the value of the allegati property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the allegati property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAllegati().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Allegato }
         * 
         * 
         */
        public List<Allegato> getAllegati() {
            if (allegati == null) {
                allegati = new ArrayList<Allegato>();
            }
            return this.allegati;
        }

    }

}
