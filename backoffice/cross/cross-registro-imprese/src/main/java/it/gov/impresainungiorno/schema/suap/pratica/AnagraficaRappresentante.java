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
import javax.xml.bind.annotation.XmlType;
import it.gov.impresainungiorno.schema.base.Anagrafica;


/**
 * <p>Classe Java per AnagraficaRappresentante complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="AnagraficaRappresentante">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.impresainungiorno.gov.it/schema/base}Anagrafica">
 *       &lt;sequence>
 *         &lt;element name="carica" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}Carica"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AnagraficaRappresentante", propOrder = {
    "carica"
})
public class AnagraficaRappresentante
    extends Anagrafica
{

    @XmlElement(required = true)
    protected Carica carica;

    /**
     * Recupera il valore della propriet carica.
     * 
     * @return
     *     possible object is
     *     {@link Carica }
     *     
     */
    public Carica getCarica() {
        return carica;
    }

    /**
     * Imposta il valore della propriet carica.
     * 
     * @param value
     *     allowed object is
     *     {@link Carica }
     *     
     */
    public void setCarica(Carica value) {
        this.carica = value;
    }

}
