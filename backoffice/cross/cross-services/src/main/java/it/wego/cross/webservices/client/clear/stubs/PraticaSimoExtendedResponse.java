
package it.wego.cross.webservices.client.clear.stubs;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per pratica_simo_extendedResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="pratica_simo_extendedResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pratica_simo_extendedResult" type="{http://www.simo.org}log" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pratica_simo_extendedResponse", propOrder = {
    "praticaSimoExtendedResult"
})
public class PraticaSimoExtendedResponse {

    @XmlElementRef(name = "pratica_simo_extendedResult", namespace = "http://www.simo.org", type = JAXBElement.class, required = false)
    protected JAXBElement<Log> praticaSimoExtendedResult;

    /**
     * Recupera il valore della proprietà praticaSimoExtendedResult.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Log }{@code >}
     *     
     */
    public JAXBElement<Log> getPraticaSimoExtendedResult() {
        return praticaSimoExtendedResult;
    }

    /**
     * Imposta il valore della proprietà praticaSimoExtendedResult.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Log }{@code >}
     *     
     */
    public void setPraticaSimoExtendedResult(JAXBElement<Log> value) {
        this.praticaSimoExtendedResult = value;
    }

}
