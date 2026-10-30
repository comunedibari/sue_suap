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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * Contiene le informazioni di come trattare/salvare i file in attach in input al Web Service
 * 
 * <p>Classe Java per VersioneElettronicaType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="VersioneElettronicaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NroAttachmentAssociato" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/>
 *         &lt;element name="NomeFile" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AttivaVerificaFirma" type="{}FlagSiNoType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VersioneElettronicaType", propOrder = {
    "nroAttachmentAssociato",
    "nomeFile",
    "attivaVerificaFirma"
})
public class VersioneElettronicaType {

    @XmlElement(name = "NroAttachmentAssociato", required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger nroAttachmentAssociato;
    @XmlElement(name = "NomeFile", required = true)
    protected String nomeFile;
    @XmlElement(name = "AttivaVerificaFirma", defaultValue = "0")
    protected String attivaVerificaFirma;

    /**
     * Recupera il valore della proprietnroAttachmentAssociato.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNroAttachmentAssociato() {
        return nroAttachmentAssociato;
    }

    /**
     * Imposta il valore della proprietnroAttachmentAssociato.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNroAttachmentAssociato(BigInteger value) {
        this.nroAttachmentAssociato = value;
    }

    /**
     * Recupera il valore della proprietnomeFile.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeFile() {
        return nomeFile;
    }

    /**
     * Imposta il valore della proprietnomeFile.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeFile(String value) {
        this.nomeFile = value;
    }

    /**
     * Recupera il valore della proprietattivaVerificaFirma.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttivaVerificaFirma() {
        return attivaVerificaFirma;
    }

    /**
     * Imposta il valore della proprietattivaVerificaFirma.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttivaVerificaFirma(String value) {
        this.attivaVerificaFirma = value;
    }

}
