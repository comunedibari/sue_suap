
package it.wego.cross.avbari.linksmt.fascicolo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idVoceTitolario" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idFascicolo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="documento" type="{http://server.ws.protocollo.linksmt.it/}DocumentData"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "idVoceTitolario",
    "idFascicolo",
    "documento"
})
@XmlRootElement(name = "fascicolaNuovoDocumentoIN")
public class FascicolaNuovoDocumentoIN {

    @XmlElement(required = true)
    protected String idVoceTitolario;
    @XmlElement(required = true)
    protected String idFascicolo;
    @XmlElement(required = true)
    protected DocumentData documento;

    /**
     * Gets the value of the idVoceTitolario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdVoceTitolario() {
        return idVoceTitolario;
    }

    /**
     * Sets the value of the idVoceTitolario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdVoceTitolario(String value) {
        this.idVoceTitolario = value;
    }

    /**
     * Gets the value of the idFascicolo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdFascicolo() {
        return idFascicolo;
    }

    /**
     * Sets the value of the idFascicolo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdFascicolo(String value) {
        this.idFascicolo = value;
    }

    /**
     * Gets the value of the documento property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentData }
     *     
     */
    public DocumentData getDocumento() {
        return documento;
    }

    /**
     * Sets the value of the documento property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentData }
     *     
     */
    public void setDocumento(DocumentData value) {
        this.documento = value;
    }

}
