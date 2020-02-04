//
// Questo file  stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andr persa durante la ricompilazione dello schema di origine. 
// Generato il: 2012.11.27 alle 11:16:28 AM CET 
//


package it.gov.impresainungiorno.schema.suap.pratica;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Classe Java per EstremiSuap complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="EstremiSuap">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *       &lt;attribute name="codice-amministrazione" use="required" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}CodiceAmministrazione" />
 *       &lt;attribute name="codice-aoo" use="required" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}CodiceAOO" />
 *       &lt;attribute name="identificativo-suap" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EstremiSuap", propOrder = {
    "value"
})
public class EstremiSuap {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "codice-amministrazione", required = true)
    protected String codiceAmministrazione;
    @XmlAttribute(name = "codice-aoo", required = true)
    protected String codiceAoo;
    @XmlAttribute(name = "identificativo-suap", required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger identificativoSuap;

    /**
     * Recupera il valore della propriet value.
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
     * Recupera il valore della propriet codiceAmministrazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceAmministrazione() {
        return codiceAmministrazione;
    }

    /**
     * Imposta il valore della propriet codiceAmministrazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceAmministrazione(String value) {
        this.codiceAmministrazione = value;
    }

    /**
     * Recupera il valore della propriet codiceAoo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceAoo() {
        return codiceAoo;
    }

    /**
     * Imposta il valore della propriet codiceAoo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceAoo(String value) {
        this.codiceAoo = value;
    }

    /**
     * Recupera il valore della propriet identificativoSuap.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdentificativoSuap() {
        return identificativoSuap;
    }

    /**
     * Imposta il valore della propriet identificativoSuap.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdentificativoSuap(BigInteger value) {
        this.identificativoSuap = value;
    }

}
