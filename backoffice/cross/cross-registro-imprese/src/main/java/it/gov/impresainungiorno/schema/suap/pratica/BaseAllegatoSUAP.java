//
// Questo file  stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andr persa durante la ricompilazione dello schema di origine. 
// Generato il: 2012.11.27 alle 11:16:28 AM CET 
//


package it.gov.impresainungiorno.schema.suap.pratica;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *             Descrive il singolo file allegato. Tipo astratto, non istanziabile
 *         
 * 
 * <p>Classe Java per BaseAllegatoSUAP complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="BaseAllegatoSUAP">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="descrizione" type="{http://www.impresainungiorno.gov.it/schema/base}Stringa" minOccurs="0"/>
 *         &lt;element name="nome-file-originale" type="{http://www.impresainungiorno.gov.it/schema/base}Stringa" minOccurs="0"/>
 *         &lt;element name="mime" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}MimeType"/>
 *         &lt;element name="mime-base" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}MimeType" minOccurs="0"/>
 *         &lt;element name="dimensione" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BaseAllegatoSUAP", propOrder = {
    "descrizione",
    "nomeFileOriginale",
    "mime",
    "mimeBase",
    "dimensione"
})
@XmlSeeAlso({
    it.gov.impresainungiorno.schema.suap.rea.AllegatoGenerico.class,
    it.gov.impresainungiorno.schema.suap.pratica.AllegatoGenerico.class,
    ProcuraSpeciale.TracciatoXml.class,
    ProcuraSpeciale.class,
    ModelloAttivita.TracciatoXml.class,
    ModelloAttivita.class
})
public abstract class BaseAllegatoSUAP {

    protected String descrizione;
    @XmlElement(name = "nome-file-originale")
    protected String nomeFileOriginale;
    @XmlElement(required = true)
    protected String mime;
    @XmlElement(name = "mime-base")
    protected String mimeBase;
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger dimensione;

    /**
     * Recupera il valore della propriet descrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Imposta il valore della propriet descrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizione(String value) {
        this.descrizione = value;
    }

    /**
     * Recupera il valore della propriet nomeFileOriginale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeFileOriginale() {
        return nomeFileOriginale;
    }

    /**
     * Imposta il valore della propriet nomeFileOriginale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeFileOriginale(String value) {
        this.nomeFileOriginale = value;
    }

    /**
     * Recupera il valore della propriet mime.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMime() {
        return mime;
    }

    /**
     * Imposta il valore della propriet mime.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMime(String value) {
        this.mime = value;
    }

    /**
     * Recupera il valore della propriet mimeBase.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMimeBase() {
        return mimeBase;
    }

    /**
     * Imposta il valore della propriet mimeBase.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMimeBase(String value) {
        this.mimeBase = value;
    }

    /**
     * Recupera il valore della propriet dimensione.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDimensione() {
        return dimensione;
    }

    /**
     * Imposta il valore della propriet dimensione.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDimensione(BigInteger value) {
        this.dimensione = value;
    }

}
