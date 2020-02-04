//
// Questo file  stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andr persa durante la ricompilazione dello schema di origine. 
// Generato il: 2012.11.27 alle 11:16:28 AM CET 
//


package it.gov.impresainungiorno.schema.suap.pratica;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per RiepilogoPraticaSUAP complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RiepilogoPraticaSUAP">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="info-schema" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}VersioneSchema"/>
 *         &lt;element name="intestazione" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}Intestazione"/>
 *         &lt;element name="struttura" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}Struttura"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement(name="riepilogo-pratica-suap")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RiepilogoPraticaSUAP", propOrder = {
    "infoSchema",
    "intestazione",
    "struttura"
})
public class RiepilogoPraticaSUAP {

    @XmlElement(name = "info-schema", required = true)
    protected VersioneSchema infoSchema;
    @XmlElement(required = true)
    protected Intestazione intestazione;
    @XmlElement(required = true)
    protected Struttura struttura;

    /**
     * Recupera il valore della propriet infoSchema.
     * 
     * @return
     *     possible object is
     *     {@link VersioneSchema }
     *     
     */
    public VersioneSchema getInfoSchema() {
        return infoSchema;
    }

    /**
     * Imposta il valore della propriet infoSchema.
     * 
     * @param value
     *     allowed object is
     *     {@link VersioneSchema }
     *     
     */
    public void setInfoSchema(VersioneSchema value) {
        this.infoSchema = value;
    }

    /**
     * Recupera il valore della propriet intestazione.
     * 
     * @return
     *     possible object is
     *     {@link Intestazione }
     *     
     */
    public Intestazione getIntestazione() {
        return intestazione;
    }

    /**
     * Imposta il valore della propriet intestazione.
     * 
     * @param value
     *     allowed object is
     *     {@link Intestazione }
     *     
     */
    public void setIntestazione(Intestazione value) {
        this.intestazione = value;
    }

    /**
     * Recupera il valore della propriet struttura.
     * 
     * @return
     *     possible object is
     *     {@link Struttura }
     *     
     */
    public Struttura getStruttura() {
        return struttura;
    }

    /**
     * Imposta il valore della propriet struttura.
     * 
     * @param value
     *     allowed object is
     *     {@link Struttura }
     *     
     */
    public void setStruttura(Struttura value) {
        this.struttura = value;
    }

}
