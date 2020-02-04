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
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * 
 *                     La descrizione dell'elemeto forma giridica e' quella corrispondente all'annotation del valore possibile dell'atributo. Ai fini dei controlli fa fede il valore dell'attributo codice
 *                 
 * 
 * <p>Classe Java per FormaGiuridica complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="FormaGiuridica">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *       &lt;attribute name="codice" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="XX"/>
 *             &lt;enumeration value="SZ"/>
 *             &lt;enumeration value="SV"/>
 *             &lt;enumeration value="SU"/>
 *             &lt;enumeration value="ST"/>
 *             &lt;enumeration value="SS"/>
 *             &lt;enumeration value="SR"/>
 *             &lt;enumeration value="SP"/>
 *             &lt;enumeration value="SO"/>
 *             &lt;enumeration value="SN"/>
 *             &lt;enumeration value="SM"/>
 *             &lt;enumeration value="SL"/>
 *             &lt;enumeration value="SI"/>
 *             &lt;enumeration value="SG"/>
 *             &lt;enumeration value="SF"/>
 *             &lt;enumeration value="SE"/>
 *             &lt;enumeration value="SD"/>
 *             &lt;enumeration value="SC"/>
 *             &lt;enumeration value="SA"/>
 *             &lt;enumeration value="PS"/>
 *             &lt;enumeration value="PC"/>
 *             &lt;enumeration value="PA"/>
 *             &lt;enumeration value="OS"/>
 *             &lt;enumeration value="OO"/>
 *             &lt;enumeration value="OC"/>
 *             &lt;enumeration value="MA"/>
 *             &lt;enumeration value="LL"/>
 *             &lt;enumeration value="IR"/>
 *             &lt;enumeration value="IF"/>
 *             &lt;enumeration value="ID"/>
 *             &lt;enumeration value="IC"/>
 *             &lt;enumeration value="GE"/>
 *             &lt;enumeration value="FO"/>
 *             &lt;enumeration value="FI"/>
 *             &lt;enumeration value="ES"/>
 *             &lt;enumeration value="ER"/>
 *             &lt;enumeration value="EP"/>
 *             &lt;enumeration value="EN"/>
 *             &lt;enumeration value="EM"/>
 *             &lt;enumeration value="EL"/>
 *             &lt;enumeration value="EI"/>
 *             &lt;enumeration value="EE"/>
 *             &lt;enumeration value="ED"/>
 *             &lt;enumeration value="EC"/>
 *             &lt;enumeration value="DI"/>
 *             &lt;enumeration value="CZ"/>
 *             &lt;enumeration value="CS"/>
 *             &lt;enumeration value="CR"/>
 *             &lt;enumeration value="CO"/>
 *             &lt;enumeration value="CN"/>
 *             &lt;enumeration value="CM"/>
 *             &lt;enumeration value="CL"/>
 *             &lt;enumeration value="CI"/>
 *             &lt;enumeration value="CF"/>
 *             &lt;enumeration value="CE"/>
 *             &lt;enumeration value="CC"/>
 *             &lt;enumeration value="AZ"/>
 *             &lt;enumeration value="AU"/>
 *             &lt;enumeration value="AT"/>
 *             &lt;enumeration value="AS"/>
 *             &lt;enumeration value="AR"/>
 *             &lt;enumeration value="AP"/>
 *             &lt;enumeration value="AN"/>
 *             &lt;enumeration value="AM"/>
 *             &lt;enumeration value="AL"/>
 *             &lt;enumeration value="AI"/>
 *             &lt;enumeration value="AF"/>
 *             &lt;enumeration value="AE"/>
 *             &lt;enumeration value="AC"/>
 *             &lt;enumeration value="AA"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="categoria" type="{http://www.impresainungiorno.gov.it/schema/base}StringaLunga" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FormaGiuridica", propOrder = {
    "value"
})
public class FormaGiuridica {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "codice", required = true)
    protected String codice;
    @XmlAttribute(name = "categoria")
    protected String categoria;

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

    /**
     * Recupera il valore della propriet categoria.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Imposta il valore della propriet categoria.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCategoria(String value) {
        this.categoria = value;
    }

}