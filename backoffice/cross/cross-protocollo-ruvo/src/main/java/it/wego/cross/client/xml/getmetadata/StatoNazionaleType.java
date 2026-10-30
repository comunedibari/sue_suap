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
 * <p>Classe Java per StatoNazionaleType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="StatoNazionaleType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CodISTATStato" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="[0-9]{3}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="NomeStato" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StatoNazionaleType", propOrder = {
    "codISTATStato",
    "nomeStato"
})
public class StatoNazionaleType {

    @XmlElement(name = "CodISTATStato")
    protected String codISTATStato;
    @XmlElement(name = "NomeStato", required = true)
    protected String nomeStato;

    /**
     * Recupera il valore della proprietcodISTATStato.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodISTATStato() {
        return codISTATStato;
    }

    /**
     * Imposta il valore della proprietcodISTATStato.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodISTATStato(String value) {
        this.codISTATStato = value;
    }

    /**
     * Recupera il valore della proprietnomeStato.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeStato() {
        return nomeStato;
    }

    /**
     * Imposta il valore della proprietnomeStato.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeStato(String value) {
        this.nomeStato = value;
    }

}
