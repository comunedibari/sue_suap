//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.05.07 alle 04:53:14 PM CEST 
//


package it.wego.cross.plugins.genova.commercio.xml.avvio.richiesta;

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
 *         &lt;element name="anagrafica" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="tp_anag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ragsoc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="codfisc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="codcomres" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="descomres" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="prov" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="cap" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="codvia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="numciv" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="colciv" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="tipocomm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoproc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idsuap" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dtpres" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="protocollo" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="dtprot" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="numprot" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "anagrafica",
    "tipocomm",
    "tipoproc",
    "idsuap",
    "dtpres",
    "protocollo"
})
@XmlRootElement(name = "avvio")
public class Avvio {

    protected Avvio.Sicurezza sicurezza;
    protected Avvio.Anagrafica anagrafica;
    protected String tipocomm;
    protected String tipoproc;
    protected String idsuap;
    protected String dtpres;
    protected Avvio.Protocollo protocollo;

    /**
     * Recupera il valore della proprietà sicurezza.
     * 
     * @return
     *     possible object is
     *     {@link Avvio.Sicurezza }
     *     
     */
    public Avvio.Sicurezza getSicurezza() {
        return sicurezza;
    }

    /**
     * Imposta il valore della proprietà sicurezza.
     * 
     * @param value
     *     allowed object is
     *     {@link Avvio.Sicurezza }
     *     
     */
    public void setSicurezza(Avvio.Sicurezza value) {
        this.sicurezza = value;
    }

    /**
     * Recupera il valore della proprietà anagrafica.
     * 
     * @return
     *     possible object is
     *     {@link Avvio.Anagrafica }
     *     
     */
    public Avvio.Anagrafica getAnagrafica() {
        return anagrafica;
    }

    /**
     * Imposta il valore della proprietà anagrafica.
     * 
     * @param value
     *     allowed object is
     *     {@link Avvio.Anagrafica }
     *     
     */
    public void setAnagrafica(Avvio.Anagrafica value) {
        this.anagrafica = value;
    }

    /**
     * Recupera il valore della proprietà tipocomm.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipocomm() {
        return tipocomm;
    }

    /**
     * Imposta il valore della proprietà tipocomm.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipocomm(String value) {
        this.tipocomm = value;
    }

    /**
     * Recupera il valore della proprietà tipoproc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoproc() {
        return tipoproc;
    }

    /**
     * Imposta il valore della proprietà tipoproc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoproc(String value) {
        this.tipoproc = value;
    }

    /**
     * Recupera il valore della proprietà idsuap.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdsuap() {
        return idsuap;
    }

    /**
     * Imposta il valore della proprietà idsuap.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdsuap(String value) {
        this.idsuap = value;
    }

    /**
     * Recupera il valore della proprietà dtpres.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDtpres() {
        return dtpres;
    }

    /**
     * Imposta il valore della proprietà dtpres.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDtpres(String value) {
        this.dtpres = value;
    }

    /**
     * Recupera il valore della proprietà protocollo.
     * 
     * @return
     *     possible object is
     *     {@link Avvio.Protocollo }
     *     
     */
    public Avvio.Protocollo getProtocollo() {
        return protocollo;
    }

    /**
     * Imposta il valore della proprietà protocollo.
     * 
     * @param value
     *     allowed object is
     *     {@link Avvio.Protocollo }
     *     
     */
    public void setProtocollo(Avvio.Protocollo value) {
        this.protocollo = value;
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
     *         &lt;element name="tp_anag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ragsoc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="codfisc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="codcomres" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="descomres" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="prov" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="cap" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="codvia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="numciv" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="colciv" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        "tpAnag",
        "ragsoc",
        "codfisc",
        "codcomres",
        "descomres",
        "prov",
        "cap",
        "codvia",
        "numciv",
        "colciv"
    })
    public static class Anagrafica {

        @XmlElement(name = "tp_anag")
        protected String tpAnag;
        protected String ragsoc;
        protected String codfisc;
        protected String codcomres;
        protected String descomres;
        protected String prov;
        protected String cap;
        protected String codvia;
        protected String numciv;
        protected String colciv;

        /**
         * Recupera il valore della proprietà tpAnag.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTpAnag() {
            return tpAnag;
        }

        /**
         * Imposta il valore della proprietà tpAnag.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTpAnag(String value) {
            this.tpAnag = value;
        }

        /**
         * Recupera il valore della proprietà ragsoc.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRagsoc() {
            return ragsoc;
        }

        /**
         * Imposta il valore della proprietà ragsoc.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRagsoc(String value) {
            this.ragsoc = value;
        }

        /**
         * Recupera il valore della proprietà codfisc.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCodfisc() {
            return codfisc;
        }

        /**
         * Imposta il valore della proprietà codfisc.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCodfisc(String value) {
            this.codfisc = value;
        }

        /**
         * Recupera il valore della proprietà codcomres.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCodcomres() {
            return codcomres;
        }

        /**
         * Imposta il valore della proprietà codcomres.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCodcomres(String value) {
            this.codcomres = value;
        }

        /**
         * Recupera il valore della proprietà descomres.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDescomres() {
            return descomres;
        }

        /**
         * Imposta il valore della proprietà descomres.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDescomres(String value) {
            this.descomres = value;
        }

        /**
         * Recupera il valore della proprietà prov.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getProv() {
            return prov;
        }

        /**
         * Imposta il valore della proprietà prov.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setProv(String value) {
            this.prov = value;
        }

        /**
         * Recupera il valore della proprietà cap.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCap() {
            return cap;
        }

        /**
         * Imposta il valore della proprietà cap.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCap(String value) {
            this.cap = value;
        }

        /**
         * Recupera il valore della proprietà codvia.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCodvia() {
            return codvia;
        }

        /**
         * Imposta il valore della proprietà codvia.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCodvia(String value) {
            this.codvia = value;
        }

        /**
         * Recupera il valore della proprietà numciv.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNumciv() {
            return numciv;
        }

        /**
         * Imposta il valore della proprietà numciv.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNumciv(String value) {
            this.numciv = value;
        }

        /**
         * Recupera il valore della proprietà colciv.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getColciv() {
            return colciv;
        }

        /**
         * Imposta il valore della proprietà colciv.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setColciv(String value) {
            this.colciv = value;
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
     *         &lt;element name="dtprot" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="numprot" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        "dtprot",
        "numprot"
    })
    public static class Protocollo {

        protected String dtprot;
        protected String numprot;

        /**
         * Recupera il valore della proprietà dtprot.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDtprot() {
            return dtprot;
        }

        /**
         * Imposta il valore della proprietà dtprot.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDtprot(String value) {
            this.dtprot = value;
        }

        /**
         * Recupera il valore della proprietà numprot.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNumprot() {
            return numprot;
        }

        /**
         * Imposta il valore della proprietà numprot.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNumprot(String value) {
            this.numprot = value;
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
