//
// Questo file  stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andr persa durante la ricompilazione dello schema di origine. 
// Generato il: 2020.02.19 alle 06:34:54 PM CET 
//


package it.gov.impresainungiorno.schema.suap.ente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Classe Java per OggettoCooperazione complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="OggettoCooperazione">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.impresainungiorno.gov.it/schema/base>Stringa">
 *       &lt;attribute name="tipo-cooperazione" type="{http://www.impresainungiorno.gov.it/schema/base}Stringa" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OggettoCooperazione", propOrder = {
    "value"
})
public class OggettoCooperazione {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "tipo-cooperazione")
    protected String tipoCooperazione;

    /**
     *  Questo elemento non puo' assumere valore stringa vuota, al piu' non c'e' 
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Imposta il valore della propriet value.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Recupera il valore della propriet tipoCooperazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoCooperazione() {
        return tipoCooperazione;
    }

    /**
     * Imposta il valore della propriet tipoCooperazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoCooperazione(String value) {
        this.tipoCooperazione = value;
    }

}
