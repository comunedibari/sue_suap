//
// Questo file stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrpersa durante la ricompilazione dello schema di origine. 
// Generato il: 2013.03.22 alle 04:25:41 PM CET 
//


package it.wego.cross.client.xml.trovadocfolder;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per PaginazioneType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="PaginazioneType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NroPagina" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/>
 *         &lt;element name="NroRecordInPagina" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaginazioneType", propOrder = {
    "nroPagina",
    "nroRecordInPagina"
})
public class PaginazioneType {

    @XmlElement(name = "NroPagina", defaultValue = "1")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger nroPagina;
    @XmlElement(name = "NroRecordInPagina")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger nroRecordInPagina;

    /**
     * Recupera il valore della proprietnroPagina.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNroPagina() {
        return nroPagina;
    }

    /**
     * Imposta il valore della proprietnroPagina.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNroPagina(BigInteger value) {
        this.nroPagina = value;
    }

    /**
     * Recupera il valore della proprietnroRecordInPagina.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNroRecordInPagina() {
        return nroRecordInPagina;
    }

    /**
     * Imposta il valore della proprietnroRecordInPagina.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNroRecordInPagina(BigInteger value) {
        this.nroRecordInPagina = value;
    }

}
