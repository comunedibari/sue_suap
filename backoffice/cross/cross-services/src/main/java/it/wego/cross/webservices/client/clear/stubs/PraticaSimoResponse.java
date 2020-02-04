
package it.wego.cross.webservices.client.clear.stubs;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per pratica_simoResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="pratica_simoResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pratica_simoResult" type="{http://www.simo.org}log" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pratica_simoResponse", propOrder = {
    "praticaSimoResult"
})
public class PraticaSimoResponse {

    @XmlElementRef(name = "pratica_simoResult", namespace = "http://www.simo.org", type = JAXBElement.class, required = false)
    protected JAXBElement<Log> praticaSimoResult;

    /**
     * Recupera il valore della proprietà praticaSimoResult.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Log }{@code >}
     *     
     */
    public JAXBElement<Log> getPraticaSimoResult() {
        return praticaSimoResult;
    }

    /**
     * Imposta il valore della proprietà praticaSimoResult.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Log }{@code >}
     *     
     */
    public void setPraticaSimoResult(JAXBElement<Log> value) {
        this.praticaSimoResult = value;
    }

}
