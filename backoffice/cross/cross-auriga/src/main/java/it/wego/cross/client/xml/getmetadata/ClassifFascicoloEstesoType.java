//
// Questo file stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrpersa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.07.14 alle 03:42:18 PM CEST 
//


package it.wego.cross.client.xml.getmetadata;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ClassifFascicoloEstesoType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ClassifFascicoloEstesoType">
 *   &lt;complexContent>
 *     &lt;extension base="{}ClassifFascicoloType">
 *       &lt;sequence>
 *         &lt;element name="OggettoFasc" type="{}OggettoFascType" minOccurs="0"/>
 *         &lt;element name="TipoFasc" type="{}OggDiTabDiSistemaType" minOccurs="0"/>
 *         &lt;element name="InCaricoA" type="{}AssegnatarioEffType" minOccurs="0"/>
 *         &lt;element name="ApertoDa" type="{}UOUserType" minOccurs="0"/>
 *         &lt;element name="AttributoAddFasc" type="{}AttributoAddizionaleType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClassifFascicoloEstesoType", propOrder = {
    "oggettoFasc",
    "tipoFasc",
    "inCaricoA",
    "apertoDa",
    "attributoAddFasc"
})
public class ClassifFascicoloEstesoType
    extends ClassifFascicoloType
{

    @XmlElement(name = "OggettoFasc")
    protected String oggettoFasc;
    @XmlElement(name = "TipoFasc")
    protected OggDiTabDiSistemaType tipoFasc;
    @XmlElement(name = "InCaricoA")
    protected AssegnatarioEffType inCaricoA;
    @XmlElement(name = "ApertoDa")
    protected UOUserType apertoDa;
    @XmlElement(name = "AttributoAddFasc")
    protected List<AttributoAddizionaleType> attributoAddFasc;

    /**
     * Recupera il valore della proprietoggettoFasc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOggettoFasc() {
        return oggettoFasc;
    }

    /**
     * Imposta il valore della proprietoggettoFasc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOggettoFasc(String value) {
        this.oggettoFasc = value;
    }

    /**
     * Recupera il valore della propriettipoFasc.
     * 
     * @return
     *     possible object is
     *     {@link OggDiTabDiSistemaType }
     *     
     */
    public OggDiTabDiSistemaType getTipoFasc() {
        return tipoFasc;
    }

    /**
     * Imposta il valore della propriettipoFasc.
     * 
     * @param value
     *     allowed object is
     *     {@link OggDiTabDiSistemaType }
     *     
     */
    public void setTipoFasc(OggDiTabDiSistemaType value) {
        this.tipoFasc = value;
    }

    /**
     * Recupera il valore della proprietinCaricoA.
     * 
     * @return
     *     possible object is
     *     {@link AssegnatarioEffType }
     *     
     */
    public AssegnatarioEffType getInCaricoA() {
        return inCaricoA;
    }

    /**
     * Imposta il valore della proprietinCaricoA.
     * 
     * @param value
     *     allowed object is
     *     {@link AssegnatarioEffType }
     *     
     */
    public void setInCaricoA(AssegnatarioEffType value) {
        this.inCaricoA = value;
    }

    /**
     * Recupera il valore della proprietapertoDa.
     * 
     * @return
     *     possible object is
     *     {@link UOUserType }
     *     
     */
    public UOUserType getApertoDa() {
        return apertoDa;
    }

    /**
     * Imposta il valore della proprietapertoDa.
     * 
     * @param value
     *     allowed object is
     *     {@link UOUserType }
     *     
     */
    public void setApertoDa(UOUserType value) {
        this.apertoDa = value;
    }

    /**
     * Gets the value of the attributoAddFasc property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attributoAddFasc property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttributoAddFasc().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AttributoAddizionaleType }
     * 
     * 
     */
    public List<AttributoAddizionaleType> getAttributoAddFasc() {
        if (attributoAddFasc == null) {
            attributoAddFasc = new ArrayList<AttributoAddizionaleType>();
        }
        return this.attributoAddFasc;
    }

}
