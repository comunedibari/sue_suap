//
// Questo filestato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andr� persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.07.24 alle 10:59:37 AM CEST 
//


package it.wego.cross.client.xml.inseriscifasciolo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per DatiComplex complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="DatiComplex">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}Dati"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiComplex", propOrder = {
    "dati"
})
@XmlSeeAlso({
    InserimentoFasc.class
})
public class DatiComplex {

    @XmlElement(name = "Dati", required = true)
    protected Dati dati;

    /**
     * Recupera il valore della propriet� dati.
     * 
     * @return
     *     possible object is
     *     {@link Dati }
     *     
     */
    public Dati getDati() {
        return dati;
    }

    /**
     * Imposta il valore della propriet� dati.
     * 
     * @param value
     *     allowed object is
     *     {@link Dati }
     *     
     */
    public void setDati(Dati value) {
        this.dati = value;
    }

}
