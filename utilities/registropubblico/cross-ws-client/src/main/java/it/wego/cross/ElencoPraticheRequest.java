
package it.wego.cross;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ElencoPraticheRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ElencoPraticheRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idEnte" type="{http://www.w3.org/2001/XMLSchema}integer" form="unqualified"/>
 *         &lt;element name="idComune" type="{http://www.w3.org/2001/XMLSchema}integer" form="unqualified"/>
 *         &lt;element name="dataRicezioneDa" type="{http://www.w3.org/2001/XMLSchema}string" form="unqualified"/>
 *         &lt;element name="dataRicezioneA" type="{http://www.w3.org/2001/XMLSchema}string" form="unqualified"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ElencoPraticheRequest", propOrder = {
    "idEnte",
    "idComune",
    "dataRicezioneDa",
    "dataRicezioneA"
})
public class ElencoPraticheRequest {

    @XmlElement(namespace = "", required = true)
    protected BigInteger idEnte;
    @XmlElement(namespace = "", required = true)
    protected BigInteger idComune;
    @XmlElement(namespace = "", required = true)
    protected String dataRicezioneDa;
    @XmlElement(namespace = "", required = true)
    protected String dataRicezioneA;

    /**
     * Gets the value of the idEnte property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdEnte() {
        return idEnte;
    }

    /**
     * Sets the value of the idEnte property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdEnte(BigInteger value) {
        this.idEnte = value;
    }

    /**
     * Gets the value of the idComune property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdComune() {
        return idComune;
    }

    /**
     * Sets the value of the idComune property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdComune(BigInteger value) {
        this.idComune = value;
    }

    /**
     * Gets the value of the dataRicezioneDa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataRicezioneDa() {
        return dataRicezioneDa;
    }

    /**
     * Sets the value of the dataRicezioneDa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataRicezioneDa(String value) {
        this.dataRicezioneDa = value;
    }

    /**
     * Gets the value of the dataRicezioneA property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataRicezioneA() {
        return dataRicezioneA;
    }

    /**
     * Sets the value of the dataRicezioneA property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataRicezioneA(String value) {
        this.dataRicezioneA = value;
    }

}
