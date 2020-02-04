//
// Questo file  stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andr persa durante la ricompilazione dello schema di origine. 
// Generato il: 2012.11.27 alle 11:16:28 AM CET 
//


package it.gov.impresainungiorno.schema.suap.pratica;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * 
 *             Dati di protocollo. Corrisponde al tipo Identificatore nella specifica del 
 *             protocollo informatico (si veda documentazione DigitPA
 *         
 * 
 * <p>Classe Java per FormatoProtocollo complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="FormatoProtocollo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="codice-amministrazione" use="required" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}CodiceAmministrazione" />
 *       &lt;attribute name="codice-aoo" use="required" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}CodiceAOO" />
 *       &lt;attribute name="data-registrazione" use="required" type="{http://www.w3.org/2001/XMLSchema}date" />
 *       &lt;attribute name="numero-registrazione" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;pattern value="\d{7}"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FormatoProtocollo")
@XmlSeeAlso({
    ProtocolloSUAP.class
})
public class FormatoProtocollo {

    @XmlAttribute(name = "codice-amministrazione", required = true)
    protected String codiceAmministrazione;
    @XmlAttribute(name = "codice-aoo", required = true)
    protected String codiceAoo;
    @XmlAttribute(name = "data-registrazione", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataRegistrazione;
    @XmlAttribute(name = "numero-registrazione", required = true)
    protected String numeroRegistrazione;

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
     * Recupera il valore della propriet dataRegistrazione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataRegistrazione() {
        return dataRegistrazione;
    }

    /**
     * Imposta il valore della propriet dataRegistrazione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataRegistrazione(XMLGregorianCalendar value) {
        this.dataRegistrazione = value;
    }

    /**
     * Recupera il valore della propriet numeroRegistrazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroRegistrazione() {
        return numeroRegistrazione;
    }

    /**
     * Imposta il valore della propriet numeroRegistrazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroRegistrazione(String value) {
        this.numeroRegistrazione = value;
    }

}
