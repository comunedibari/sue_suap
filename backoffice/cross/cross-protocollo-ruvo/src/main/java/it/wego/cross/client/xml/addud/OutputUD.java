//
// Questo file stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrpersa durante la ricompilazione dello schema di origine. 
// Generato il: 2013.03.28 alle 03:47:42 PM CET 
//


package it.wego.cross.client.xml.addud;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdUD" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="RegistrazioneDataUD" type="{}EstremiRegNumType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="VersioneElettronicaNonCaricata" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="NroAttachmentAssociato" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/>
 *                   &lt;element name="NomeFile" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "", propOrder = {
    "idUD",
    "registrazioneDataUD",
    "versioneElettronicaNonCaricata"
})
@XmlRootElement(name = "Output_UD")
public class OutputUD {

    @XmlElement(name = "IdUD", required = true)
    protected BigInteger idUD;
    @XmlElement(name = "RegistrazioneDataUD")
    protected List<EstremiRegNumType> registrazioneDataUD;
    @XmlElement(name = "VersioneElettronicaNonCaricata")
    protected List<OutputUD.VersioneElettronicaNonCaricata> versioneElettronicaNonCaricata;

    /**
     * Recupera il valore della proprietidUD.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdUD() {
        return idUD;
    }

    /**
     * Imposta il valore della proprietidUD.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdUD(BigInteger value) {
        this.idUD = value;
    }

    /**
     * Gets the value of the registrazioneDataUD property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the registrazioneDataUD property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRegistrazioneDataUD().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EstremiRegNumType }
     * 
     * 
     */
    public List<EstremiRegNumType> getRegistrazioneDataUD() {
        if (registrazioneDataUD == null) {
            registrazioneDataUD = new ArrayList<EstremiRegNumType>();
        }
        return this.registrazioneDataUD;
    }

    /**
     * Gets the value of the versioneElettronicaNonCaricata property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the versioneElettronicaNonCaricata property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVersioneElettronicaNonCaricata().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OutputUD.VersioneElettronicaNonCaricata }
     * 
     * 
     */
    public List<OutputUD.VersioneElettronicaNonCaricata> getVersioneElettronicaNonCaricata() {
        if (versioneElettronicaNonCaricata == null) {
            versioneElettronicaNonCaricata = new ArrayList<OutputUD.VersioneElettronicaNonCaricata>();
        }
        return this.versioneElettronicaNonCaricata;
    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="NroAttachmentAssociato" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/>
     *         &lt;element name="NomeFile" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "nroAttachmentAssociato",
        "nomeFile"
    })
    public static class VersioneElettronicaNonCaricata {

        @XmlElement(name = "NroAttachmentAssociato", required = true)
        @XmlSchemaType(name = "positiveInteger")
        protected BigInteger nroAttachmentAssociato;
        @XmlElement(name = "NomeFile", required = true)
        protected String nomeFile;

        /**
         * Recupera il valore della proprietnroAttachmentAssociato.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getNroAttachmentAssociato() {
            return nroAttachmentAssociato;
        }

        /**
         * Imposta il valore della proprietnroAttachmentAssociato.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setNroAttachmentAssociato(BigInteger value) {
            this.nroAttachmentAssociato = value;
        }

        /**
         * Recupera il valore della proprietnomeFile.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNomeFile() {
            return nomeFile;
        }

        /**
         * Imposta il valore della proprietnomeFile.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNomeFile(String value) {
            this.nomeFile = value;
        }

    }

}
