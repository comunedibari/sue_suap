
package it.wego.cross.webservices.cxf.cripal;

import it.wego.cross.webservices.cxf.*;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ElencoPraticheRequest complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
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
     * Recupera il valore della propriet idEnte.
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
     * Imposta il valore della propriet idEnte.
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
     * Recupera il valore della propriet idComune.
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
     * Imposta il valore della propriet idComune.
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
     * Recupera il valore della propriet dataRicezioneDa.
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
     * Imposta il valore della propriet dataRicezioneDa.
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
     * Recupera il valore della propriet dataRicezioneA.
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
     * Imposta il valore della propriet dataRicezioneA.
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
