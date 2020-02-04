//
// Questo filestato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andr� persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.07.24 alle 02:23:40 PM CEST 
//


package it.wego.cross.client.xml.rispostainsfasc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


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
 *         &lt;element name="IdFascicolo" type="{http://www.w3.org/2001/XMLSchema}NMTOKEN"/>
 *         &lt;element name="AnnoFascicolo" type="{http://www.w3.org/2001/XMLSchema}NMTOKEN"/>
 *         &lt;element name="NumeroFascicolo" type="{http://www.w3.org/2001/XMLSchema}NMTOKEN"/>
 *         &lt;element name="NumeroSottofascicolo" type="{http://www.w3.org/2001/XMLSchema}NMTOKEN"/>
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
    "idFascicolo",
    "annoFascicolo",
    "numeroFascicolo",
    "numeroSottofascicolo"
})
@XmlRootElement(name = "Fascicolo")
public class Fascicolo {

    @XmlElement(name = "IdFascicolo", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NMTOKEN")
    protected String idFascicolo;
    @XmlElement(name = "AnnoFascicolo", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NMTOKEN")
    protected String annoFascicolo;
    @XmlElement(name = "NumeroFascicolo", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NMTOKEN")
    protected String numeroFascicolo;
    @XmlElement(name = "NumeroSottofascicolo", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NMTOKEN")
    protected String numeroSottofascicolo;

    /**
     * Recupera il valore della propriet� idFascicolo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdFascicolo() {
        return idFascicolo;
    }

    /**
     * Imposta il valore della propriet� idFascicolo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdFascicolo(String value) {
        this.idFascicolo = value;
    }

    /**
     * Recupera il valore della propriet� annoFascicolo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnnoFascicolo() {
        return annoFascicolo;
    }

    /**
     * Imposta il valore della propriet� annoFascicolo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnnoFascicolo(String value) {
        this.annoFascicolo = value;
    }

    /**
     * Recupera il valore della propriet� numeroFascicolo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroFascicolo() {
        return numeroFascicolo;
    }

    /**
     * Imposta il valore della propriet� numeroFascicolo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroFascicolo(String value) {
        this.numeroFascicolo = value;
    }

    /**
     * Recupera il valore della propriet� numeroSottofascicolo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroSottofascicolo() {
        return numeroSottofascicolo;
    }

    /**
     * Imposta il valore della propriet� numeroSottofascicolo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroSottofascicolo(String value) {
        this.numeroSottofascicolo = value;
    }

}
