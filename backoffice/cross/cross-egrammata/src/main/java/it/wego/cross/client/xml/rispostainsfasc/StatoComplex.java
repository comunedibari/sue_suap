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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per StatoComplex complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="StatoComplex">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}Stato"/>
 *         &lt;element ref="{}Fascicolo"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StatoComplex", propOrder = {
    "stato",
    "fascicolo"
})
@XmlSeeAlso({
    RispostaInsFasc.class
})
public class StatoComplex {

    @XmlElement(name = "Stato", required = true)
    protected Stato stato;
    @XmlElement(name = "Fascicolo", required = true)
    protected Fascicolo fascicolo;

    /**
     * Recupera il valore della propriet� stato.
     * 
     * @return
     *     possible object is
     *     {@link Stato }
     *     
     */
    public Stato getStato() {
        return stato;
    }

    /**
     * Imposta il valore della propriet� stato.
     * 
     * @param value
     *     allowed object is
     *     {@link Stato }
     *     
     */
    public void setStato(Stato value) {
        this.stato = value;
    }

    /**
     * Recupera il valore della propriet� fascicolo.
     * 
     * @return
     *     possible object is
     *     {@link Fascicolo }
     *     
     */
    public Fascicolo getFascicolo() {
        return fascicolo;
    }

    /**
     * Imposta il valore della propriet� fascicolo.
     * 
     * @param value
     *     allowed object is
     *     {@link Fascicolo }
     *     
     */
    public void setFascicolo(Fascicolo value) {
        this.fascicolo = value;
    }

}
