//
// Questo file stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrpersa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.07.14 alle 03:42:18 PM CEST 
//


package it.wego.cross.client.xml.getmetadata;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * La scrivania virtuale rappresenta un utente non come persona fisica, ma nella funzione che svolge presso una certa UO. Puessere indicata univocamente indicando UO e utente; oppure il sistema pucercare di identificarla anche o solo a partire dalla sua descrizione
 * 
 * <p>Classe Java per ScrivaniaVirtualeType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ScrivaniaVirtualeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="UO" type="{}UOType"/>
 *         &lt;element name="Utente" type="{}UserType"/>
 *         &lt;element name="DesScrivaniaVirt" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScrivaniaVirtualeType", propOrder = {

})
public class ScrivaniaVirtualeType {

    @XmlElement(name = "UO", required = true)
    protected UOType uo;
    @XmlElement(name = "Utente", required = true)
    protected UserType utente;
    @XmlElement(name = "DesScrivaniaVirt", required = true)
    protected String desScrivaniaVirt;

    /**
     * Recupera il valore della proprietuo.
     * 
     * @return
     *     possible object is
     *     {@link UOType }
     *     
     */
    public UOType getUO() {
        return uo;
    }

    /**
     * Imposta il valore della proprietuo.
     * 
     * @param value
     *     allowed object is
     *     {@link UOType }
     *     
     */
    public void setUO(UOType value) {
        this.uo = value;
    }

    /**
     * Recupera il valore della proprietutente.
     * 
     * @return
     *     possible object is
     *     {@link UserType }
     *     
     */
    public UserType getUtente() {
        return utente;
    }

    /**
     * Imposta il valore della proprietutente.
     * 
     * @param value
     *     allowed object is
     *     {@link UserType }
     *     
     */
    public void setUtente(UserType value) {
        this.utente = value;
    }

    /**
     * Recupera il valore della proprietdesScrivaniaVirt.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesScrivaniaVirt() {
        return desScrivaniaVirt;
    }

    /**
     * Imposta il valore della proprietdesScrivaniaVirt.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesScrivaniaVirt(String value) {
        this.desScrivaniaVirt = value;
    }

}
