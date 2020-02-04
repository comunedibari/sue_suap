//
// Questo file  stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andr persa durante la ricompilazione dello schema di origine. 
// Generato il: 2012.11.27 alle 11:16:28 AM CET 
//


package it.gov.impresainungiorno.schema.suap.pratica;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import it.gov.impresainungiorno.schema.base.Indirizzo;


/**
 * <p>Classe Java per ImpiantoProduttivo complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ImpiantoProduttivo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="indirizzo" type="{http://www.impresainungiorno.gov.it/schema/base}Indirizzo"/>
 *         &lt;element name="dati-catastali" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="sezione" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;simpleContent>
 *                         &lt;extension base="&lt;http://www.impresainungiorno.gov.it/schema/base>Stringa">
 *                           &lt;attribute name="tipo">
 *                             &lt;simpleType>
 *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                 &lt;enumeration value="amministrativa"/>
 *                                 &lt;enumeration value="censuaria"/>
 *                                 &lt;enumeration value="urbana"/>
 *                               &lt;/restriction>
 *                             &lt;/simpleType>
 *                           &lt;/attribute>
 *                         &lt;/extension>
 *                       &lt;/simpleContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="foglio" type="{http://www.impresainungiorno.gov.it/schema/base}Stringa"/>
 *                   &lt;element name="mappale" type="{http://www.impresainungiorno.gov.it/schema/base}Stringa" maxOccurs="unbounded"/>
 *                   &lt;element name="subalterno" type="{http://www.impresainungiorno.gov.it/schema/base}Stringa" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="tipo">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                       &lt;enumeration value="fabbricati"/>
 *                       &lt;enumeration value="terreni"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *                 &lt;attribute name="comune-catastale">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.impresainungiorno.gov.it/schema/base}Stringa">
 *                       &lt;pattern value="[A-Z]{1}\d{3}"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="disponibilita-immobile" type="{http://www.impresainungiorno.gov.it/schema/base}Stringa" minOccurs="0"/>
 *         &lt;element name="destinazione-uso" type="{http://www.impresainungiorno.gov.it/schema/base}Stringa" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ImpiantoProduttivo", propOrder = {
    "indirizzo",
    "datiCatastali",
    "disponibilitaImmobile",
    "destinazioneUso"
})
public class ImpiantoProduttivo {

    @XmlElement(required = true)
    protected Indirizzo indirizzo;
    @XmlElement(name = "dati-catastali")
    protected List<ImpiantoProduttivo.DatiCatastali> datiCatastali;
    @XmlElement(name = "disponibilita-immobile")
    protected String disponibilitaImmobile;
    @XmlElement(name = "destinazione-uso")
    protected String destinazioneUso;

    /**
     * Recupera il valore della propriet indirizzo.
     * 
     * @return
     *     possible object is
     *     {@link Indirizzo }
     *     
     */
    public Indirizzo getIndirizzo() {
        return indirizzo;
    }

    /**
     * Imposta il valore della propriet indirizzo.
     * 
     * @param value
     *     allowed object is
     *     {@link Indirizzo }
     *     
     */
    public void setIndirizzo(Indirizzo value) {
        this.indirizzo = value;
    }

    /**
     * Gets the value of the datiCatastali property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the datiCatastali property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDatiCatastali().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ImpiantoProduttivo.DatiCatastali }
     * 
     * 
     */
    public List<ImpiantoProduttivo.DatiCatastali> getDatiCatastali() {
        if (datiCatastali == null) {
            datiCatastali = new ArrayList<ImpiantoProduttivo.DatiCatastali>();
        }
        return this.datiCatastali;
    }

    /**
     * Recupera il valore della propriet disponibilitaImmobile.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisponibilitaImmobile() {
        return disponibilitaImmobile;
    }

    /**
     * Imposta il valore della propriet disponibilitaImmobile.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisponibilitaImmobile(String value) {
        this.disponibilitaImmobile = value;
    }

    /**
     * Recupera il valore della propriet destinazioneUso.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestinazioneUso() {
        return destinazioneUso;
    }

    /**
     * Imposta il valore della propriet destinazioneUso.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestinazioneUso(String value) {
        this.destinazioneUso = value;
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
     *         &lt;element name="sezione" minOccurs="0">
     *           &lt;complexType>
     *             &lt;simpleContent>
     *               &lt;extension base="&lt;http://www.impresainungiorno.gov.it/schema/base>Stringa">
     *                 &lt;attribute name="tipo">
     *                   &lt;simpleType>
     *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                       &lt;enumeration value="amministrativa"/>
     *                       &lt;enumeration value="censuaria"/>
     *                       &lt;enumeration value="urbana"/>
     *                     &lt;/restriction>
     *                   &lt;/simpleType>
     *                 &lt;/attribute>
     *               &lt;/extension>
     *             &lt;/simpleContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="foglio" type="{http://www.impresainungiorno.gov.it/schema/base}Stringa"/>
     *         &lt;element name="mappale" type="{http://www.impresainungiorno.gov.it/schema/base}Stringa" maxOccurs="unbounded"/>
     *         &lt;element name="subalterno" type="{http://www.impresainungiorno.gov.it/schema/base}Stringa" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *       &lt;attribute name="tipo">
     *         &lt;simpleType>
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *             &lt;enumeration value="fabbricati"/>
     *             &lt;enumeration value="terreni"/>
     *           &lt;/restriction>
     *         &lt;/simpleType>
     *       &lt;/attribute>
     *       &lt;attribute name="comune-catastale">
     *         &lt;simpleType>
     *           &lt;restriction base="{http://www.impresainungiorno.gov.it/schema/base}Stringa">
     *             &lt;pattern value="[A-Z]{1}\d{3}"/>
     *           &lt;/restriction>
     *         &lt;/simpleType>
     *       &lt;/attribute>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "sezione",
        "foglio",
        "mappale",
        "subalterno"
    })
    public static class DatiCatastali {

        protected ImpiantoProduttivo.DatiCatastali.Sezione sezione;
        @XmlElement(required = true)
        protected String foglio;
        @XmlElement(required = true)
        protected List<String> mappale;
        protected List<String> subalterno;
        @XmlAttribute(name = "tipo")
        protected String tipo;
        @XmlAttribute(name = "comune-catastale")
        protected String comuneCatastale;

        /**
         * Recupera il valore della propriet sezione.
         * 
         * @return
         *     possible object is
         *     {@link ImpiantoProduttivo.DatiCatastali.Sezione }
         *     
         */
        public ImpiantoProduttivo.DatiCatastali.Sezione getSezione() {
            return sezione;
        }

        /**
         * Imposta il valore della propriet sezione.
         * 
         * @param value
         *     allowed object is
         *     {@link ImpiantoProduttivo.DatiCatastali.Sezione }
         *     
         */
        public void setSezione(ImpiantoProduttivo.DatiCatastali.Sezione value) {
            this.sezione = value;
        }

        /**
         * Recupera il valore della propriet foglio.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFoglio() {
            return foglio;
        }

        /**
         * Imposta il valore della propriet foglio.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFoglio(String value) {
            this.foglio = value;
        }

        /**
         * Gets the value of the mappale property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the mappale property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getMappale().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getMappale() {
            if (mappale == null) {
                mappale = new ArrayList<String>();
            }
            return this.mappale;
        }

        /**
         * Gets the value of the subalterno property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the subalterno property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSubalterno().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getSubalterno() {
            if (subalterno == null) {
                subalterno = new ArrayList<String>();
            }
            return this.subalterno;
        }

        /**
         * Recupera il valore della propriet tipo.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTipo() {
            return tipo;
        }

        /**
         * Imposta il valore della propriet tipo.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTipo(String value) {
            this.tipo = value;
        }

        /**
         * Recupera il valore della propriet comuneCatastale.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getComuneCatastale() {
            return comuneCatastale;
        }

        /**
         * Imposta il valore della propriet comuneCatastale.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setComuneCatastale(String value) {
            this.comuneCatastale = value;
        }


        /**
         * <p>Classe Java per anonymous complex type.
         * 
         * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;simpleContent>
         *     &lt;extension base="&lt;http://www.impresainungiorno.gov.it/schema/base>Stringa">
         *       &lt;attribute name="tipo">
         *         &lt;simpleType>
         *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *             &lt;enumeration value="amministrativa"/>
         *             &lt;enumeration value="censuaria"/>
         *             &lt;enumeration value="urbana"/>
         *           &lt;/restriction>
         *         &lt;/simpleType>
         *       &lt;/attribute>
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
        public static class Sezione {

            @XmlValue
            protected String value;
            @XmlAttribute(name = "tipo")
            protected String tipo;

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
             * Imposta il valore della propriet value.
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
             * Recupera il valore della propriet tipo.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTipo() {
                return tipo;
            }

            /**
             * Imposta il valore della propriet tipo.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTipo(String value) {
                this.tipo = value;
            }

        }

    }

}
