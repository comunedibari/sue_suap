package it.wego.auriga.xml;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Rappresenta un folder attraverso il suo path (a partire dalla libreria di appartenenza esclusa)+ nome o il suo identificativo
 * 
 * <p>Classe Java per EstremiXIdentificazioneFolderNoLibType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="EstremiXIdentificazioneFolderNoLibType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="IdFolder" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="Path_Nome" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EstremiXIdentificazioneFolderNoLibType", propOrder = {
    "idFolder",
    "pathNome"
})
public class EstremiXIdentificazioneFolderNoLibType {

    @XmlElement(name = "IdFolder")
    protected BigInteger idFolder;
    @XmlElement(name = "Path_Nome")
    protected String pathNome;

    /**
     * Recupera il valore della propriet� idFolder.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdFolder() {
        return idFolder;
    }

    /**
     * Imposta il valore della propriet� idFolder.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdFolder(BigInteger value) {
        this.idFolder = value;
    }

    /**
     * Recupera il valore della propriet� pathNome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPathNome() {
        return pathNome;
    }

    /**
     * Imposta il valore della propriet� pathNome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPathNome(String value) {
        this.pathNome = value;
    }

}
