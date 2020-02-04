//
// Questo file stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrpersa durante la ricompilazione dello schema di origine. 
// Generato il: 2013.03.28 alle 03:45:54 PM CET 
//


package it.wego.cross.client.xml.addud;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Contiene i dati di un documento allegato di un''unitdocumentaria
 * 
 * <p>Classe Java per AllegatoUDType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="AllegatoUDType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TipoDocAllegato" type="{}OggDiTabDiSistemaType" minOccurs="0"/>
 *         &lt;element name="DesAllegato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OriginaleCartaceo" type="{}FlagSiNoType" minOccurs="0"/>
 *         &lt;element name="TipoCartaceo" type="{}TipoCartaceoType" minOccurs="0"/>
 *         &lt;element name="VersioneElettronica" type="{}VersioneElettronicaType" minOccurs="0"/>
 *         &lt;element name="AttributoAddAlleg" type="{}AttributoAddizionaleType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AllegatoUDType", propOrder = {
    "tipoDocAllegato",
    "desAllegato",
    "originaleCartaceo",
    "tipoCartaceo",
    "versioneElettronica",
    "attributoAddAlleg"
})
public class AllegatoUDType {

    @XmlElement(name = "TipoDocAllegato")
    protected OggDiTabDiSistemaType tipoDocAllegato;
    @XmlElement(name = "DesAllegato")
    protected String desAllegato;
    @XmlElement(name = "OriginaleCartaceo")
    protected String originaleCartaceo;
    @XmlElement(name = "TipoCartaceo")
    protected String tipoCartaceo;
    @XmlElement(name = "VersioneElettronica")
    protected VersioneElettronicaType versioneElettronica;
    @XmlElement(name = "AttributoAddAlleg")
    protected List<AttributoAddizionaleType> attributoAddAlleg;

    /**
     * Recupera il valore della propriettipoDocAllegato.
     * 
     * @return
     *     possible object is
     *     {@link OggDiTabDiSistemaType }
     *     
     */
    public OggDiTabDiSistemaType getTipoDocAllegato() {
        return tipoDocAllegato;
    }

    /**
     * Imposta il valore della propriettipoDocAllegato.
     * 
     * @param value
     *     allowed object is
     *     {@link OggDiTabDiSistemaType }
     *     
     */
    public void setTipoDocAllegato(OggDiTabDiSistemaType value) {
        this.tipoDocAllegato = value;
    }

    /**
     * Recupera il valore della proprietdesAllegato.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesAllegato() {
        return desAllegato;
    }

    /**
     * Imposta il valore della proprietdesAllegato.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesAllegato(String value) {
        this.desAllegato = value;
    }

    /**
     * Recupera il valore della proprietoriginaleCartaceo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOriginaleCartaceo() {
        return originaleCartaceo;
    }

    /**
     * Imposta il valore della proprietoriginaleCartaceo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOriginaleCartaceo(String value) {
        this.originaleCartaceo = value;
    }

    /**
     * Recupera il valore della propriettipoCartaceo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoCartaceo() {
        return tipoCartaceo;
    }

    /**
     * Imposta il valore della propriettipoCartaceo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoCartaceo(String value) {
        this.tipoCartaceo = value;
    }

    /**
     * Recupera il valore della proprietversioneElettronica.
     * 
     * @return
     *     possible object is
     *     {@link VersioneElettronicaType }
     *     
     */
    public VersioneElettronicaType getVersioneElettronica() {
        return versioneElettronica;
    }

    /**
     * Imposta il valore della proprietversioneElettronica.
     * 
     * @param value
     *     allowed object is
     *     {@link VersioneElettronicaType }
     *     
     */
    public void setVersioneElettronica(VersioneElettronicaType value) {
        this.versioneElettronica = value;
    }

    /**
     * Gets the value of the attributoAddAlleg property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attributoAddAlleg property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttributoAddAlleg().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AttributoAddizionaleType }
     * 
     * 
     */
    public List<AttributoAddizionaleType> getAttributoAddAlleg() {
        if (attributoAddAlleg == null) {
            attributoAddAlleg = new ArrayList<AttributoAddizionaleType>();
        }
        return this.attributoAddAlleg;
    }

}
