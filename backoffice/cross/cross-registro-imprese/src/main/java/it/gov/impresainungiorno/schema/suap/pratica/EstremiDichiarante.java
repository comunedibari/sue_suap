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
import it.gov.impresainungiorno.schema.base.Anagrafica;


/**
 * <p>Classe Java per EstremiDichiarante complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="EstremiDichiarante">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.impresainungiorno.gov.it/schema/base}Anagrafica">
 *       &lt;sequence>
 *         &lt;element name="pec" type="{http://www.impresainungiorno.gov.it/schema/base}EMailIndirizzo" minOccurs="0"/>
 *         &lt;element name="telefono" type="{http://www.impresainungiorno.gov.it/schema/base}NumeroTelefono" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="qualifica" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="ALTRO PREVISTO DALLA VIGENTE NORMATIVA"/>
 *             &lt;enumeration value="AMMINISTRATORE"/>
 *             &lt;enumeration value="ASSOCIAZIONE DI CATEGORIA"/>
 *             &lt;enumeration value="CENTRO ELABORAZIONE DATI"/>
 *             &lt;enumeration value="COMMISSARIO GIUDIZIARIO"/>
 *             &lt;enumeration value="CONSULENTE"/>
 *             &lt;enumeration value="CURATORE FALLIMENTARE"/>
 *             &lt;enumeration value="DELEGATO"/>
 *             &lt;enumeration value="LEGALE RAPPRESENTANTE"/>
 *             &lt;enumeration value="LIQUIDATORE"/>
 *             &lt;enumeration value="NOTAIO"/>
 *             &lt;enumeration value="PROFESSIONISTA INCARICATO"/>
 *             &lt;enumeration value="SOCIO"/>
 *             &lt;enumeration value="STUDIO ASSOCIATO"/>
 *             &lt;enumeration value="TITOLARE"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EstremiDichiarante", propOrder = {
    "pec",
    "telefono"
})
public class EstremiDichiarante
    extends Anagrafica
{

    protected String pec;
    protected String telefono;
    @XmlAttribute(name = "qualifica", required = true)
    protected String qualifica;

    /**
     * Recupera il valore della propriet pec.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPec() {
        return pec;
    }

    /**
     * Imposta il valore della propriet pec.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPec(String value) {
        this.pec = value;
    }

    /**
     * Recupera il valore della propriet telefono.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Imposta il valore della propriet telefono.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelefono(String value) {
        this.telefono = value;
    }

    /**
     * Recupera il valore della propriet qualifica.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQualifica() {
        return qualifica;
    }

    /**
     * Imposta il valore della propriet qualifica.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQualifica(String value) {
        this.qualifica = value;
    }

}
