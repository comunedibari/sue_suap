//
// Questo file stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrpersa durante la ricompilazione dello schema di origine. 
// Generato il: 2013.03.28 alle 03:45:54 PM CET 
//


package it.wego.cross.client.xml.addud;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per AssegnatarioEffType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="AssegnatarioEffType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="UO" type="{}UOType"/>
 *         &lt;element name="Utente" type="{}UserType"/>
 *         &lt;element name="ScrivaniaVirtuale" type="{}ScrivaniaVirtualeType"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AssegnatarioEffType", propOrder = {
    "uo",
    "utente",
    "scrivaniaVirtuale"
})
public class AssegnatarioEffType {

    @XmlElement(name = "UO")
    protected UOType uo;
    @XmlElement(name = "Utente")
    protected UserType utente;
    @XmlElement(name = "ScrivaniaVirtuale")
    protected ScrivaniaVirtualeType scrivaniaVirtuale;

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
     * Recupera il valore della proprietscrivaniaVirtuale.
     * 
     * @return
     *     possible object is
     *     {@link ScrivaniaVirtualeType }
     *     
     */
    public ScrivaniaVirtualeType getScrivaniaVirtuale() {
        return scrivaniaVirtuale;
    }

    /**
     * Imposta il valore della proprietscrivaniaVirtuale.
     * 
     * @param value
     *     allowed object is
     *     {@link ScrivaniaVirtualeType }
     *     
     */
    public void setScrivaniaVirtuale(ScrivaniaVirtualeType value) {
        this.scrivaniaVirtuale = value;
    }

}
