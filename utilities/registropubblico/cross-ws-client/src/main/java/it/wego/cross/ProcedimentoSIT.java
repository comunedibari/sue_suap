
package it.wego.cross;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for procedimentoSIT complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="procedimentoSIT">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id_procedimento" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="cod_procedimento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="des_procedimento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "procedimentoSIT", propOrder = {
    "idProcedimento",
    "codProcedimento",
    "desProcedimento"
})
public class ProcedimentoSIT {

    @XmlElement(name = "id_procedimento")
    protected int idProcedimento;
    @XmlElement(name = "cod_procedimento", required = true)
    protected String codProcedimento;
    @XmlElement(name = "des_procedimento", required = true)
    protected String desProcedimento;

    /**
     * Gets the value of the idProcedimento property.
     * 
     */
    public int getIdProcedimento() {
        return idProcedimento;
    }

    /**
     * Sets the value of the idProcedimento property.
     * 
     */
    public void setIdProcedimento(int value) {
        this.idProcedimento = value;
    }

    /**
     * Gets the value of the codProcedimento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodProcedimento() {
        return codProcedimento;
    }

    /**
     * Sets the value of the codProcedimento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodProcedimento(String value) {
        this.codProcedimento = value;
    }

    /**
     * Gets the value of the desProcedimento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesProcedimento() {
        return desProcedimento;
    }

    /**
     * Sets the value of the desProcedimento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesProcedimento(String value) {
        this.desProcedimento = value;
    }

}
