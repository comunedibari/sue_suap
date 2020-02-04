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
 * <p>Classe Java per InfoConservazioneType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="InfoConservazioneType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element name="Permanente" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *           &lt;element name="PerAnni" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/>
 *         &lt;/choice>
 *         &lt;element name="Supporto" type="{}OggDiTabDiSistemaType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InfoConservazioneType", propOrder = {
    "permanente",
    "perAnni",
    "supporto"
})
public class InfoConservazioneType {

    @XmlElement(name = "Permanente")
    protected Object permanente;
    @XmlElement(name = "PerAnni")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger perAnni;
    @XmlElement(name = "Supporto")
    protected OggDiTabDiSistemaType supporto;

    /**
     * Recupera il valore della proprietpermanente.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getPermanente() {
        return permanente;
    }

    /**
     * Imposta il valore della proprietpermanente.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setPermanente(Object value) {
        this.permanente = value;
    }

    /**
     * Recupera il valore della proprietperAnni.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPerAnni() {
        return perAnni;
    }

    /**
     * Imposta il valore della proprietperAnni.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPerAnni(BigInteger value) {
        this.perAnni = value;
    }

    /**
     * Recupera il valore della proprietsupporto.
     * 
     * @return
     *     possible object is
     *     {@link OggDiTabDiSistemaType }
     *     
     */
    public OggDiTabDiSistemaType getSupporto() {
        return supporto;
    }

    /**
     * Imposta il valore della proprietsupporto.
     * 
     * @param value
     *     allowed object is
     *     {@link OggDiTabDiSistemaType }
     *     
     */
    public void setSupporto(OggDiTabDiSistemaType value) {
        this.supporto = value;
    }

}
