//
// Questo file stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrpersa durante la ricompilazione dello schema di origine. 
// Generato il: 2013.03.01 alle 08:36:33 AM CET 
//


package it.wego.cross.client.xml.extractone;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per EstremiXidentificazioneVerDocType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="EstremiXidentificazioneVerDocType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EstremiXIdentificazioneUD" type="{}EstremiXIdentificazioneUDType"/>
 *         &lt;element name="EstremixIdentificazioneFileUD" type="{}EstremiXIdentificazioneFileUDType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EstremiXidentificazioneVerDocType", propOrder = {
    "estremiXIdentificazioneUD",
    "estremixIdentificazioneFileUD"
})
public class EstremiXidentificazioneVerDocType {

    @XmlElement(name = "EstremiXIdentificazioneUD", required = true)
    protected EstremiXIdentificazioneUDType estremiXIdentificazioneUD;
    @XmlElement(name = "EstremixIdentificazioneFileUD", required = true)
    protected EstremiXIdentificazioneFileUDType estremixIdentificazioneFileUD;

    /**
     * Recupera il valore della proprietestremiXIdentificazioneUD.
     * 
     * @return
     *     possible object is
     *     {@link EstremiXIdentificazioneUDType }
     *     
     */
    public EstremiXIdentificazioneUDType getEstremiXIdentificazioneUD() {
        return estremiXIdentificazioneUD;
    }

    /**
     * Imposta il valore della proprietestremiXIdentificazioneUD.
     * 
     * @param value
     *     allowed object is
     *     {@link EstremiXIdentificazioneUDType }
     *     
     */
    public void setEstremiXIdentificazioneUD(EstremiXIdentificazioneUDType value) {
        this.estremiXIdentificazioneUD = value;
    }

    /**
     * Recupera il valore della proprietestremixIdentificazioneFileUD.
     * 
     * @return
     *     possible object is
     *     {@link EstremiXIdentificazioneFileUDType }
     *     
     */
    public EstremiXIdentificazioneFileUDType getEstremixIdentificazioneFileUD() {
        return estremixIdentificazioneFileUD;
    }

    /**
     * Imposta il valore della proprietestremixIdentificazioneFileUD.
     * 
     * @param value
     *     allowed object is
     *     {@link EstremiXIdentificazioneFileUDType }
     *     
     */
    public void setEstremixIdentificazioneFileUD(EstremiXIdentificazioneFileUDType value) {
        this.estremixIdentificazioneFileUD = value;
    }

}
