
package it.wego.cross.avbari.atti.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="annoDetermina" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="numeroRegistro" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "annoDetermina",
    "numeroRegistro"
})
@XmlRootElement(name = "allegatiDeterminaRequest")
public class AllegatiDeterminaRequest {

    protected int annoDetermina;
    protected int numeroRegistro;

    /**
     * Gets the value of the annoDetermina property.
     * 
     */
    public int getAnnoDetermina() {
        return annoDetermina;
    }

    /**
     * Sets the value of the annoDetermina property.
     * 
     */
    public void setAnnoDetermina(int value) {
        this.annoDetermina = value;
    }

    /**
     * Gets the value of the numeroRegistro property.
     * 
     */
    public int getNumeroRegistro() {
        return numeroRegistro;
    }

    /**
     * Sets the value of the numeroRegistro property.
     * 
     */
    public void setNumeroRegistro(int value) {
        this.numeroRegistro = value;
    }

}
