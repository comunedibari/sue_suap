
package it.gov.impresainungiorno.schema.suap.pratica;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * Serve a classificare i documenti per tipologia.
 * 
 * <p>Classe Java per TipoAllegato complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="TipoAllegato"&gt;
 *   &lt;simpleContent&gt;
 *     &lt;extension base="&lt;http://www.impresainungiorno.gov.it/schema/base&gt;Stringa"&gt;
 *       &lt;attribute name="codice" use="required" type="{http://www.impresainungiorno.gov.it/schema/base}Stringa" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/simpleContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TipoAllegato", propOrder = {
    "value"
})
public class TipoAllegato {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "codice", required = true)
    protected String codice;

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
     * Recupera il valore della propriet codice.
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
     * Imposta il valore della propriet codice.
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
