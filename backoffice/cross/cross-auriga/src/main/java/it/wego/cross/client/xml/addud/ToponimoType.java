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
 * <p>Classe Java per ToponimoType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ToponimoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="CodToponomastico" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DescrizioneToponimo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ToponimoType", propOrder = {
    "codToponomastico",
    "descrizioneToponimo"
})
public class ToponimoType {

    @XmlElement(name = "CodToponomastico")
    protected String codToponomastico;
    @XmlElement(name = "DescrizioneToponimo")
    protected String descrizioneToponimo;

    /**
     * Recupera il valore della proprietcodToponomastico.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodToponomastico() {
        return codToponomastico;
    }

    /**
     * Imposta il valore della proprietcodToponomastico.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodToponomastico(String value) {
        this.codToponomastico = value;
    }

    /**
     * Recupera il valore della proprietdescrizioneToponimo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneToponimo() {
        return descrizioneToponimo;
    }

    /**
     * Imposta il valore della proprietdescrizioneToponimo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneToponimo(String value) {
        this.descrizioneToponimo = value;
    }

}
