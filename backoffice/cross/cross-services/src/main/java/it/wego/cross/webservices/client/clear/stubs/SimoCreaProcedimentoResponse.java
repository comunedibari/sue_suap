
package it.wego.cross.webservices.client.clear.stubs;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per simo_crea_procedimentoResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="simo_crea_procedimentoResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="simo_crea_procedimentoResult" type="{http://www.simo.org}log" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "simo_crea_procedimentoResponse", propOrder = {
    "simoCreaProcedimentoResult"
})
public class SimoCreaProcedimentoResponse {

    @XmlElementRef(name = "simo_crea_procedimentoResult", namespace = "http://www.simo.org", type = JAXBElement.class, required = false)
    protected JAXBElement<Log> simoCreaProcedimentoResult;

    /**
     * Recupera il valore della proprietà simoCreaProcedimentoResult.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Log }{@code >}
     *     
     */
    public JAXBElement<Log> getSimoCreaProcedimentoResult() {
        return simoCreaProcedimentoResult;
    }

    /**
     * Imposta il valore della proprietà simoCreaProcedimentoResult.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Log }{@code >}
     *     
     */
    public void setSimoCreaProcedimentoResult(JAXBElement<Log> value) {
        this.simoCreaProcedimentoResult = value;
    }

}
