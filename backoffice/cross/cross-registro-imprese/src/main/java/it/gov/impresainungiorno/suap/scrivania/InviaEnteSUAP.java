
package it.gov.impresainungiorno.suap.scrivania;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import it.gov.impresainungiorno.schema.suap.ente.CooperazioneEnteSUAP;


/**
 * <p>Classe Java per inviaEnteSUAP complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="inviaEnteSUAP"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CooperazioneEnteSUAP" type="{http://www.impresainungiorno.gov.it/schema/suap/ente}CooperazioneEnteSUAP"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "inviaEnteSUAP", propOrder = {
    "cooperazioneEnteSUAP"
})
public class InviaEnteSUAP {

    @XmlElement(name = "CooperazioneEnteSUAP", required = true)
    protected CooperazioneEnteSUAP cooperazioneEnteSUAP;

    /**
     * Recupera il valore della proprietà cooperazioneEnteSUAP.
     * 
     * @return
     *     possible object is
     *     {@link CooperazioneEnteSUAP }
     *     
     */
    public CooperazioneEnteSUAP getCooperazioneEnteSUAP() {
        return cooperazioneEnteSUAP;
    }

    /**
     * Imposta il valore della proprietà cooperazioneEnteSUAP.
     * 
     * @param value
     *     allowed object is
     *     {@link CooperazioneEnteSUAP }
     *     
     */
    public void setCooperazioneEnteSUAP(CooperazioneEnteSUAP value) {
        this.cooperazioneEnteSUAP = value;
    }

}
