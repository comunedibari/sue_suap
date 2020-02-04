
package it.wego.cross.webservices.cxf.cripal;

import it.wego.cross.webservices.cxf.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per PraticaDetailResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="PraticaDetailResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Pratica" type="{http://www.wego.it/cross}praticaSIT"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PraticaDetailResponse", propOrder = {
    "pratica"
})
public class PraticaDetailResponse {

    @XmlElement(name = "Pratica", required = true)
    protected PraticaSIT pratica;

    /**
     * Recupera il valore della propriet pratica.
     * 
     * @return
     *     possible object is
     *     {@link PraticaSIT }
     *     
     */
    public PraticaSIT getPratica() {
        return pratica;
    }

    /**
     * Imposta il valore della propriet pratica.
     * 
     * @param value
     *     allowed object is
     *     {@link PraticaSIT }
     *     
     */
    public void setPratica(PraticaSIT value) {
        this.pratica = value;
    }

}
