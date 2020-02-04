
package it.wego.cross.webservices.client.clear.stubs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per altro_evento complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="altro_evento">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="descrizione_altro_evento" type="{http://www.simo.org}altro_evento_descrizione_altro_eventoType"/>
 *         &lt;element name="stato_termine_evento" type="{http://www.simo.org}TipiStatoPost"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "altro_evento", propOrder = {
    "descrizioneAltroEvento",
    "statoTermineEvento"
})
public class AltroEvento {

    @XmlElement(name = "descrizione_altro_evento", required = true)
    protected String descrizioneAltroEvento;
    @XmlElement(name = "stato_termine_evento", required = true)
    protected TipiStatoPost statoTermineEvento;

    /**
     * Recupera il valore della proprietà descrizioneAltroEvento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneAltroEvento() {
        return descrizioneAltroEvento;
    }

    /**
     * Imposta il valore della proprietà descrizioneAltroEvento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneAltroEvento(String value) {
        this.descrizioneAltroEvento = value;
    }

    /**
     * Recupera il valore della proprietà statoTermineEvento.
     * 
     * @return
     *     possible object is
     *     {@link TipiStatoPost }
     *     
     */
    public TipiStatoPost getStatoTermineEvento() {
        return statoTermineEvento;
    }

    /**
     * Imposta il valore della proprietà statoTermineEvento.
     * 
     * @param value
     *     allowed object is
     *     {@link TipiStatoPost }
     *     
     */
    public void setStatoTermineEvento(TipiStatoPost value) {
        this.statoTermineEvento = value;
    }

}
