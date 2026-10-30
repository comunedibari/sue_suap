//
// Questo file è stato generato dall'Eclipse Implementation of JAXB, v3.0.0 
// Vedere https://eclipse-ee4j.github.io/jaxb-ri 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.11.16 alle 06:34:12 PM CET 
//


package it.wego.auriga.xml.allegati;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * <p>Classe Java per EstremiXidentificazioneVerDocType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="EstremiXidentificazioneVerDocType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="EstremiXIdentificazioneUD" type="{}EstremiXIdentificazioneUDType"/&gt;
 *         &lt;element name="EstremixIdentificazioneFileUD" type="{}EstremiXIdentificazioneFileUDType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EstremiXidentificazioneVerDocType", propOrder = {
    "estremiXIdentificazioneUD",
    "estremixIdentificazioneFileUD"
})
@XmlRootElement(name = "FileUDToExtract")
public class FileUDToExtract
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElement(name = "EstremiXIdentificazioneUD", required = true)
    protected EstremiXIdentificazioneUDType estremiXIdentificazioneUD;
    @XmlElement(name = "EstremixIdentificazioneFileUD", required = true)
    protected EstremiXIdentificazioneFileUDType estremixIdentificazioneFileUD;

    /**
     * Recupera il valore della proprietà estremiXIdentificazioneUD.
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
     * Imposta il valore della proprietà estremiXIdentificazioneUD.
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
     * Recupera il valore della proprietà estremixIdentificazioneFileUD.
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
     * Imposta il valore della proprietà estremixIdentificazioneFileUD.
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
