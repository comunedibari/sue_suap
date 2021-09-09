
package it.linksmt.protocollo.ws.server;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import it.linksmt.protocollo.richiestasoap.Allegato;
import it.linksmt.protocollo.richiestasoap.Destinatario;
import it.linksmt.protocollo.richiestasoap.Documento;
import it.linksmt.protocollo.richiestasoap.Mittente;


/**
 * <p>Java class for richiestaProtocollo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="richiestaProtocollo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="protocolloRequest" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="mittente" type="{http://protocollo.linksmt.it/RichiestaSOAP}Mittente"/>
 *                   &lt;element name="destinatari" type="{http://protocollo.linksmt.it/RichiestaSOAP}Destinatario" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="documento" type="{http://protocollo.linksmt.it/RichiestaSOAP}Documento"/>
 *                   &lt;element name="allegati" type="{http://protocollo.linksmt.it/RichiestaSOAP}Allegato" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="areaOrganizzativaOmogenea" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="amministrazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="oggetto" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="idUtente" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
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
@XmlType(name = "richiestaProtocollo", propOrder = {
    "protocolloRequest"
})
public class RichiestaProtocollo {

    protected RichiestaProtocollo.ProtocolloRequest protocolloRequest;

    /**
     * Gets the value of the protocolloRequest property.
     * 
     * @return
     *     possible object is
     *     {@link RichiestaProtocollo.ProtocolloRequest }
     *     
     */
    public RichiestaProtocollo.ProtocolloRequest getProtocolloRequest() {
        return protocolloRequest;
    }

    /**
     * Sets the value of the protocolloRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link RichiestaProtocollo.ProtocolloRequest }
     *     
     */
    public void setProtocolloRequest(RichiestaProtocollo.ProtocolloRequest value) {
        this.protocolloRequest = value;
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
     *         &lt;element name="mittente" type="{http://protocollo.linksmt.it/RichiestaSOAP}Mittente"/>
     *         &lt;element name="destinatari" type="{http://protocollo.linksmt.it/RichiestaSOAP}Destinatario" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="documento" type="{http://protocollo.linksmt.it/RichiestaSOAP}Documento"/>
     *         &lt;element name="allegati" type="{http://protocollo.linksmt.it/RichiestaSOAP}Allegato" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="areaOrganizzativaOmogenea" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="amministrazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="oggetto" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="idUtente" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
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
        "mittente",
        "destinatari",
        "documento",
        "allegati",
        "areaOrganizzativaOmogenea",
        "amministrazione",
        "oggetto",
        "idUtente"
    })
    public static class ProtocolloRequest {

        @XmlElement(required = true)
        protected Mittente mittente;
        @XmlElement(nillable = true)
        protected List<Destinatario> destinatari;
        @XmlElement(required = true)
        protected Documento documento;
        @XmlElement(nillable = true)
        protected List<Allegato> allegati;
        @XmlElement(required = true)
        protected String areaOrganizzativaOmogenea;
        @XmlElement(required = true)
        protected String amministrazione;
        @XmlElement(required = true)
        protected String oggetto;
        protected Integer idUtente;

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

        /**
         * Gets the value of the documento property.
         * 
         * @return
         *     possible object is
         *     {@link Documento }
         *     
         */
        public Documento getDocumento() {
            return documento;
        }

        /**
         * Sets the value of the documento property.
         * 
         * @param value
         *     allowed object is
         *     {@link Documento }
         *     
         */
        public void setDocumento(Documento value) {
            this.documento = value;
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
         * Gets the value of the oggetto property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOggetto() {
            return oggetto;
        }

        /**
         * Sets the value of the oggetto property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOggetto(String value) {
            this.oggetto = value;
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

    }

}
