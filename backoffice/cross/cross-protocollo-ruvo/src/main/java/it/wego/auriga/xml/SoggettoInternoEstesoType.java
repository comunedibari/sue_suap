package it.wego.auriga.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per SoggettoInternoEstesoType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="SoggettoInternoEstesoType">
 *   &lt;complexContent>
 *     &lt;extension base="{}SoggettoInternoType">
 *       &lt;sequence>
 *         &lt;element name="NaturaRelazioneConUD" type="{}OggDiTabDiSistemaType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SoggettoInternoEstesoType", propOrder = {
    "naturaRelazioneConUD"
})
public class SoggettoInternoEstesoType
    extends SoggettoInternoType
{

    @XmlElement(name = "NaturaRelazioneConUD", required = true)
    protected OggDiTabDiSistemaType naturaRelazioneConUD;

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

}
