//
// Questo file stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrpersa durante la ricompilazione dello schema di origine. 
// Generato il: 2013.03.28 alle 03:45:54 PM CET 
//


package it.wego.cross.client.xml.addud;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per LivelloGerarchiaType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="LivelloGerarchiaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="Nro" use="required" type="{}NroLivelloGerarchiaType" />
 *       &lt;attribute name="Codice" use="required" type="{}CodLivelloGerarchiaType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LivelloGerarchiaType")
public class LivelloGerarchiaType {

    @XmlAttribute(name = "Nro", required = true)
    protected int nro;
    @XmlAttribute(name = "Codice", required = true)
    protected String codice;

    /**
     * Recupera il valore della proprietnro.
     * 
     */
    public int getNro() {
        return nro;
    }

    /**
     * Imposta il valore della proprietnro.
     * 
     */
    public void setNro(int value) {
        this.nro = value;
    }

    /**
     * Recupera il valore della proprietcodice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodice() {
        return codice;
    }

    /**
     * Imposta il valore della proprietcodice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodice(String value) {
        this.codice = value;
    }

}
