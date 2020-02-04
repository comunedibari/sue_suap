
package it.wego.cross.webservices.cxf.cripal;

import it.wego.cross.webservices.cxf.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per procedimentoSIT complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
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
     * Recupera il valore della propriet idProcedimento.
     * 
     */
    public int getIdProcedimento() {
        return idProcedimento;
    }

    /**
     * Imposta il valore della propriet idProcedimento.
     * 
     */
    public void setIdProcedimento(int value) {
        this.idProcedimento = value;
    }

    /**
     * Recupera il valore della propriet codProcedimento.
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
     * Imposta il valore della propriet codProcedimento.
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
     * Recupera il valore della propriet desProcedimento.
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
     * Imposta il valore della propriet desProcedimento.
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
