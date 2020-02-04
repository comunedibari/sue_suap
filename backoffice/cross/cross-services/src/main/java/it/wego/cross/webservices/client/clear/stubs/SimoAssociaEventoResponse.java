
package it.wego.cross.webservices.client.clear.stubs;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per simo_associa_eventoResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="simo_associa_eventoResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="simo_associa_eventoResult" type="{http://www.simo.org}log" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "simo_associa_eventoResponse", propOrder = {
    "simoAssociaEventoResult"
})
public class SimoAssociaEventoResponse {

    @XmlElementRef(name = "simo_associa_eventoResult", namespace = "http://www.simo.org", type = JAXBElement.class, required = false)
    protected JAXBElement<Log> simoAssociaEventoResult;

    /**
     * Recupera il valore della proprietà simoAssociaEventoResult.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Log }{@code >}
     *     
     */
    public JAXBElement<Log> getSimoAssociaEventoResult() {
        return simoAssociaEventoResult;
    }

    /**
     * Imposta il valore della proprietà simoAssociaEventoResult.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Log }{@code >}
     *     
     */
    public void setSimoAssociaEventoResult(JAXBElement<Log> value) {
        this.simoAssociaEventoResult = value;
    }

}
