//
// Questo file � stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andr� persa durante la ricompilazione dello schema di origine. 
// Generato il: 2013.04.04 alle 08:31:17 PM CEST 
//


package it.wego.cross.genova.toponomastica.client.xml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
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
 *       &lt;sequence minOccurs="0">
 *         &lt;element name="Input">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="CampoPrecompilazioneBean" type="{http://gruppoinit.it/b1/ConcessioniEAutorizzazioni/precompilazione}CampoPrecompilazioneBean" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Output">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="CampoPrecompilazioneBean" type="{http://gruppoinit.it/b1/ConcessioniEAutorizzazioni/precompilazione}CampoPrecompilazioneBean" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="CodEnte" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DescrizioneErrore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "input",
    "output",
    "codEnte",
    "descrizioneErrore"
})
@XmlRootElement(name = "PrecompilazioneBean")
public class PrecompilazioneBean {

    @XmlElement(name = "Input")
    protected PrecompilazioneBean.Input input;
    @XmlElement(name = "Output")
    protected PrecompilazioneBean.Output output;
    @XmlElement(name = "CodEnte")
    protected String codEnte;
    @XmlElement(name = "DescrizioneErrore")
    protected String descrizioneErrore;

    /**
     * Recupera il valore della propriet� input.
     * 
     * @return
     *     possible object is
     *     {@link PrecompilazioneBean.Input }
     *     
     */
    public PrecompilazioneBean.Input getInput() {
        return input;
    }

    /**
     * Imposta il valore della propriet� input.
     * 
     * @param value
     *     allowed object is
     *     {@link PrecompilazioneBean.Input }
     *     
     */
    public void setInput(PrecompilazioneBean.Input value) {
        this.input = value;
    }

    /**
     * Recupera il valore della propriet� output.
     * 
     * @return
     *     possible object is
     *     {@link PrecompilazioneBean.Output }
     *     
     */
    public PrecompilazioneBean.Output getOutput() {
        return output;
    }

    /**
     * Imposta il valore della propriet� output.
     * 
     * @param value
     *     allowed object is
     *     {@link PrecompilazioneBean.Output }
     *     
     */
    public void setOutput(PrecompilazioneBean.Output value) {
        this.output = value;
    }

    /**
     * Recupera il valore della propriet� codEnte.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodEnte() {
        return codEnte;
    }

    /**
     * Imposta il valore della propriet� codEnte.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodEnte(String value) {
        this.codEnte = value;
    }

    /**
     * Recupera il valore della propriet� descrizioneErrore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneErrore() {
        return descrizioneErrore;
    }

    /**
     * Imposta il valore della propriet� descrizioneErrore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneErrore(String value) {
        this.descrizioneErrore = value;
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
     *         &lt;element name="CampoPrecompilazioneBean" type="{http://gruppoinit.it/b1/ConcessioniEAutorizzazioni/precompilazione}CampoPrecompilazioneBean" maxOccurs="unbounded" minOccurs="0"/>
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
        "campoPrecompilazioneBean"
    })
    public static class Input {

        @XmlElement(name = "CampoPrecompilazioneBean")
        protected List<CampoPrecompilazioneBean> campoPrecompilazioneBean;

        /**
         * Gets the value of the campoPrecompilazioneBean property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the campoPrecompilazioneBean property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCampoPrecompilazioneBean().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link CampoPrecompilazioneBean }
         * 
         * 
         */
        public List<CampoPrecompilazioneBean> getCampoPrecompilazioneBean() {
            if (campoPrecompilazioneBean == null) {
                campoPrecompilazioneBean = new ArrayList<CampoPrecompilazioneBean>();
            }
            return this.campoPrecompilazioneBean;
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
     *         &lt;element name="CampoPrecompilazioneBean" type="{http://gruppoinit.it/b1/ConcessioniEAutorizzazioni/precompilazione}CampoPrecompilazioneBean" maxOccurs="unbounded" minOccurs="0"/>
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
        "campoPrecompilazioneBean"
    })
    public static class Output {

        @XmlElement(name = "CampoPrecompilazioneBean")
        protected List<CampoPrecompilazioneBean> campoPrecompilazioneBean;

        /**
         * Gets the value of the campoPrecompilazioneBean property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the campoPrecompilazioneBean property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCampoPrecompilazioneBean().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link CampoPrecompilazioneBean }
         * 
         * 
         */
        public List<CampoPrecompilazioneBean> getCampoPrecompilazioneBean() {
            if (campoPrecompilazioneBean == null) {
                campoPrecompilazioneBean = new ArrayList<CampoPrecompilazioneBean>();
            }
            return this.campoPrecompilazioneBean;
        }

    }

}
