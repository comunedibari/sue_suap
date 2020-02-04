//
// Questo file e' stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andra' persa durante la ricompilazione dello schema di origine. 
// Generato il: 2013.02.01 alle 04:09:31 PM CET 
//


package it.wego.cross.xml.anagrafetributaria.edilizia;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per recordDettaglio complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="recordDettaglio">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="identificazioneRichiesta" type="{http://www.wego.it/cross}identificazioneRichiesta"/>
 *         &lt;element name="beneficiari">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="beneficiario" type="{http://www.wego.it/cross}beneficiario" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="datiCatastali">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="datoCatastale" type="{http://www.wego.it/cross}datoCatastale" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="professionisti">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="professionista" type="{http://www.wego.it/cross}professionista" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="imprese">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="impresa" type="{http://www.wego.it/cross}impresa" maxOccurs="unbounded"/>
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
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "recordDettaglio", propOrder = {
    "identificazioneRichiesta",
    "beneficiari",
    "datiCatastali",
    "professionisti",
    "imprese"
})
public class RecordDettaglio {

    @XmlElement(required = true)
    protected IdentificazioneRichiesta identificazioneRichiesta;
    @XmlElement(required = true)
    protected RecordDettaglio.Beneficiari beneficiari;
    @XmlElement(required = true)
    protected RecordDettaglio.DatiCatastali datiCatastali;
    @XmlElement(required = true)
    protected RecordDettaglio.Professionisti professionisti;
    @XmlElement(required = true)
    protected RecordDettaglio.Imprese imprese;

    /**
     * Recupera il valore della proprieta' identificazioneRichiesta.
     * 
     * @return
     *     possible object is
     *     {@link IdentificazioneRichiesta }
     *     
     */
    public IdentificazioneRichiesta getIdentificazioneRichiesta() {
        return identificazioneRichiesta;
    }

    /**
     * Imposta il valore della proprieta' identificazioneRichiesta.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentificazioneRichiesta }
     *     
     */
    public void setIdentificazioneRichiesta(IdentificazioneRichiesta value) {
        this.identificazioneRichiesta = value;
    }

    /**
     * Recupera il valore della proprieta' beneficiari.
     * 
     * @return
     *     possible object is
     *     {@link RecordDettaglio.Beneficiari }
     *     
     */
    public RecordDettaglio.Beneficiari getBeneficiari() {
        return beneficiari;
    }

    /**
     * Imposta il valore della proprieta' beneficiari.
     * 
     * @param value
     *     allowed object is
     *     {@link RecordDettaglio.Beneficiari }
     *     
     */
    public void setBeneficiari(RecordDettaglio.Beneficiari value) {
        this.beneficiari = value;
    }

    /**
     * Recupera il valore della proprieta' datiCatastali.
     * 
     * @return
     *     possible object is
     *     {@link RecordDettaglio.DatiCatastali }
     *     
     */
    public RecordDettaglio.DatiCatastali getDatiCatastali() {
        return datiCatastali;
    }

    /**
     * Imposta il valore della proprieta' datiCatastali.
     * 
     * @param value
     *     allowed object is
     *     {@link RecordDettaglio.DatiCatastali }
     *     
     */
    public void setDatiCatastali(RecordDettaglio.DatiCatastali value) {
        this.datiCatastali = value;
    }

    /**
     * Recupera il valore della proprieta' professionisti.
     * 
     * @return
     *     possible object is
     *     {@link RecordDettaglio.Professionisti }
     *     
     */
    public RecordDettaglio.Professionisti getProfessionisti() {
        return professionisti;
    }

    /**
     * Imposta il valore della proprieta' professionisti.
     * 
     * @param value
     *     allowed object is
     *     {@link RecordDettaglio.Professionisti }
     *     
     */
    public void setProfessionisti(RecordDettaglio.Professionisti value) {
        this.professionisti = value;
    }

    /**
     * Recupera il valore della proprieta' imprese.
     * 
     * @return
     *     possible object is
     *     {@link RecordDettaglio.Imprese }
     *     
     */
    public RecordDettaglio.Imprese getImprese() {
        return imprese;
    }

    /**
     * Imposta il valore della proprieta' imprese.
     * 
     * @param value
     *     allowed object is
     *     {@link RecordDettaglio.Imprese }
     *     
     */
    public void setImprese(RecordDettaglio.Imprese value) {
        this.imprese = value;
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
     *         &lt;element name="beneficiario" type="{http://www.wego.it/cross}beneficiario" maxOccurs="unbounded"/>
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
        "beneficiario"
    })
    public static class Beneficiari {

        @XmlElement(required = true)
        protected List<Beneficiario> beneficiario;

        /**
         * Gets the value of the beneficiario property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the beneficiario property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getBeneficiario().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Beneficiario }
         * 
         * 
         */
        public List<Beneficiario> getBeneficiario() {
            if (beneficiario == null) {
                beneficiario = new ArrayList<Beneficiario>();
            }
            return this.beneficiario;
        }

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
     *         &lt;element name="datoCatastale" type="{http://www.wego.it/cross}datoCatastale" maxOccurs="unbounded"/>
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
        "datoCatastale"
    })
    public static class DatiCatastali {

        @XmlElement(required = true)
        protected List<DatoCatastale> datoCatastale;

        /**
         * Gets the value of the datoCatastale property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the datoCatastale property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDatoCatastale().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link DatoCatastale }
         * 
         * 
         */
        public List<DatoCatastale> getDatoCatastale() {
            if (datoCatastale == null) {
                datoCatastale = new ArrayList<DatoCatastale>();
            }
            return this.datoCatastale;
        }

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
     *         &lt;element name="impresa" type="{http://www.wego.it/cross}impresa" maxOccurs="unbounded"/>
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
        "impresa"
    })
    public static class Imprese {

        @XmlElement(required = true)
        protected List<Impresa> impresa;

        /**
         * Gets the value of the impresa property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the impresa property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getImpresa().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Impresa }
         * 
         * 
         */
        public List<Impresa> getImpresa() {
            if (impresa == null) {
                impresa = new ArrayList<Impresa>();
            }
            return this.impresa;
        }

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
     *         &lt;element name="professionista" type="{http://www.wego.it/cross}professionista" maxOccurs="unbounded"/>
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
        "professionista"
    })
    public static class Professionisti {

        @XmlElement(required = true)
        protected List<Professionista> professionista;

        /**
         * Gets the value of the professionista property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the professionista property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getProfessionista().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Professionista }
         * 
         * 
         */
        public List<Professionista> getProfessionista() {
            if (professionista == null) {
                professionista = new ArrayList<Professionista>();
            }
            return this.professionista;
        }

    }

}
