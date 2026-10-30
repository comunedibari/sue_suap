package it.wego.auriga.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * Rappresenta un ruolo amministrativo eventualmente circoscritto ad un certo livello della struttura organizzativa piuttosto che ad una specifica UO (es: Dirigente, Dirigente di Settore, Dirigente del settore X, Dirigenti dei Servizi del Settore X, Segretarie del Settore dell'Ufficio X)
 * 
 * <p>Classe Java per RuoloAmmContestualizzatoType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RuoloAmmContestualizzatoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RuoloAmm" type="{}OggDiTabDiSistemaType"/>
 *         &lt;choice>
 *           &lt;element name="VsLivelloUO" type="{}NroLivelloGerarchiaType" minOccurs="0"/>
 *           &lt;element name="VsTipoUO" type="{}OggDiTabDiSistemaType" minOccurs="0"/>
 *         &lt;/choice>
 *         &lt;element name="VsUO" type="{}UOEstesaType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RuoloAmmContestualizzatoType", propOrder = {
    "ruoloAmm",
    "vsLivelloUO",
    "vsTipoUO",
    "vsUO"
})
public class RuoloAmmContestualizzatoType {

    @XmlElement(name = "RuoloAmm", required = true)
    protected OggDiTabDiSistemaType ruoloAmm;
    @XmlElement(name = "VsLivelloUO")
    @XmlSchemaType(name = "integer")
    protected Integer vsLivelloUO;
    @XmlElement(name = "VsTipoUO")
    protected OggDiTabDiSistemaType vsTipoUO;
    @XmlElement(name = "VsUO")
    protected UOEstesaType vsUO;

    /**
     * Recupera il valore della propriet� ruoloAmm.
     * 
     * @return
     *     possible object is
     *     {@link OggDiTabDiSistemaType }
     *     
     */
    public OggDiTabDiSistemaType getRuoloAmm() {
        return ruoloAmm;
    }

    /**
     * Imposta il valore della propriet� ruoloAmm.
     * 
     * @param value
     *     allowed object is
     *     {@link OggDiTabDiSistemaType }
     *     
     */
    public void setRuoloAmm(OggDiTabDiSistemaType value) {
        this.ruoloAmm = value;
    }

    /**
     * Recupera il valore della propriet� vsLivelloUO.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getVsLivelloUO() {
        return vsLivelloUO;
    }

    /**
     * Imposta il valore della propriet� vsLivelloUO.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setVsLivelloUO(Integer value) {
        this.vsLivelloUO = value;
    }

    /**
     * Recupera il valore della propriet� vsTipoUO.
     * 
     * @return
     *     possible object is
     *     {@link OggDiTabDiSistemaType }
     *     
     */
    public OggDiTabDiSistemaType getVsTipoUO() {
        return vsTipoUO;
    }

    /**
     * Imposta il valore della propriet� vsTipoUO.
     * 
     * @param value
     *     allowed object is
     *     {@link OggDiTabDiSistemaType }
     *     
     */
    public void setVsTipoUO(OggDiTabDiSistemaType value) {
        this.vsTipoUO = value;
    }

    /**
     * Recupera il valore della propriet� vsUO.
     * 
     * @return
     *     possible object is
     *     {@link UOEstesaType }
     *     
     */
    public UOEstesaType getVsUO() {
        return vsUO;
    }

    /**
     * Imposta il valore della propriet� vsUO.
     * 
     * @param value
     *     allowed object is
     *     {@link UOEstesaType }
     *     
     */
    public void setVsUO(UOEstesaType value) {
        this.vsUO = value;
    }

}
