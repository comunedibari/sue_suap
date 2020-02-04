//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.05.07 alle 04:50:53 PM CEST 
//


package it.wego.cross.plugins.genova.commercio.xml.cancellazione.richiesta;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *       &lt;sequence>
 *         &lt;element name="sicurezza" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="user" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="pwd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="pratica" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="numprat" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
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
    "sicurezza",
    "pratica"
})
@XmlRootElement(name = "cancella")
public class Cancella {

    protected Cancella.Sicurezza sicurezza;
    protected Cancella.Pratica pratica;

    /**
     * Recupera il valore della proprietà sicurezza.
     * 
     * @return
     *     possible object is
     *     {@link Cancella.Sicurezza }
     *     
     */
    public Cancella.Sicurezza getSicurezza() {
        return sicurezza;
    }

    /**
     * Imposta il valore della proprietà sicurezza.
     * 
     * @param value
     *     allowed object is
     *     {@link Cancella.Sicurezza }
     *     
     */
    public void setSicurezza(Cancella.Sicurezza value) {
        this.sicurezza = value;
    }

    /**
     * Recupera il valore della proprietà pratica.
     * 
     * @return
     *     possible object is
     *     {@link Cancella.Pratica }
     *     
     */
    public Cancella.Pratica getPratica() {
        return pratica;
    }

    /**
     * Imposta il valore della proprietà pratica.
     * 
     * @param value
     *     allowed object is
     *     {@link Cancella.Pratica }
     *     
     */
    public void setPratica(Cancella.Pratica value) {
        this.pratica = value;
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
     *         &lt;element name="numprat" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
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
        "numprat"
    })
    public static class Pratica {

        protected String numprat;

        /**
         * Recupera il valore della proprietà numprat.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public String getNumprat() {
            return numprat;
        }

        /**
         * Imposta il valore della proprietà numprat.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setNumprat(String value) {
            this.numprat = value;
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
     *         &lt;element name="user" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="pwd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        "user",
        "pwd"
    })
    public static class Sicurezza {

        protected String user;
        protected String pwd;

        /**
         * Recupera il valore della proprietà user.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getUser() {
            return user;
        }

        /**
         * Imposta il valore della proprietà user.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setUser(String value) {
            this.user = value;
        }

        /**
         * Recupera il valore della proprietà pwd.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPwd() {
            return pwd;
        }

        /**
         * Imposta il valore della proprietà pwd.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPwd(String value) {
            this.pwd = value;
        }

    }

}
