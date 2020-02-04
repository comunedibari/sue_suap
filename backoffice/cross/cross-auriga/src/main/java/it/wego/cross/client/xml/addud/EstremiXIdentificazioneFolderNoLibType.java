//
// Questo file stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrpersa durante la ricompilazione dello schema di origine. 
// Generato il: 2013.03.28 alle 03:45:54 PM CET 
//


package it.wego.cross.client.xml.addud;

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
     * Recupera il valore della proprietidFolder.
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
     * Imposta il valore della proprietidFolder.
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
     * Recupera il valore della proprietpathNome.
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
     * Imposta il valore della proprietpathNome.
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
