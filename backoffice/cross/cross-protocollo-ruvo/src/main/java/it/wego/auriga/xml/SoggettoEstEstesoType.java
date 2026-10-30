package it.wego.auriga.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Soggetto esterno che ha una relazione con un documento, fascicolo, proced. ecc. diversa da quella di mittente/destinatario
 * 
 * <p>Classe Java per SoggettoEstEstesoType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="SoggettoEstEstesoType">
 *   &lt;complexContent>
 *     &lt;extension base="{}SoggettoEsternoType">
 *       &lt;sequence>
 *         &lt;element name="NaturaRelazioneConUD" type="{}OggDiTabDiSistemaType"/>
 *         &lt;element name="DettNaturaRelazioneConUD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SoggettoEstEstesoType", propOrder = {
    "naturaRelazioneConUD",
    "dettNaturaRelazioneConUD"
})
public class SoggettoEstEstesoType
    extends SoggettoEsternoType
{

    @XmlElement(name = "NaturaRelazioneConUD", required = true)
    protected OggDiTabDiSistemaType naturaRelazioneConUD;
    @XmlElement(name = "DettNaturaRelazioneConUD")
    protected String dettNaturaRelazioneConUD;

    /**
     * Recupera il valore della propriet� naturaRelazioneConUD.
     * 
     * @return
     *     possible object is
     *     {@link OggDiTabDiSistemaType }
     *     
     */
    public OggDiTabDiSistemaType getNaturaRelazioneConUD() {
        return naturaRelazioneConUD;
    }

    /**
     * Imposta il valore della propriet� naturaRelazioneConUD.
     * 
     * @param value
     *     allowed object is
     *     {@link OggDiTabDiSistemaType }
     *     
     */
    public void setNaturaRelazioneConUD(OggDiTabDiSistemaType value) {
        this.naturaRelazioneConUD = value;
    }

    /**
     * Recupera il valore della propriet� dettNaturaRelazioneConUD.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDettNaturaRelazioneConUD() {
        return dettNaturaRelazioneConUD;
    }

    /**
     * Imposta il valore della propriet� dettNaturaRelazioneConUD.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDettNaturaRelazioneConUD(String value) {
        this.dettNaturaRelazioneConUD = value;
    }

}
