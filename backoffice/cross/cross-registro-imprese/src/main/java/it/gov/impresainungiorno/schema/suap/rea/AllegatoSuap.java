//
// Questo file  stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andr persa durante la ricompilazione dello schema di origine. 
// Generato il: 2012.11.27 alle 11:16:28 AM CET 
//


package it.gov.impresainungiorno.schema.suap.rea;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per AllegatoSuap complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="AllegatoSuap">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.impresainungiorno.gov.it/schema/suap/rea}AllegatoGenerico">
 *       &lt;attribute name="descrittore" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="SUAP-ZIP"/>
 *             &lt;enumeration value="SUAP-PDF"/>
 *             &lt;enumeration value="SUAP-XML"/>
 *             &lt;enumeration value="MDA-PDF"/>
 *             &lt;enumeration value="MDA-XML"/>
 *             &lt;enumeration value="ALTRO"/>
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
@XmlType(name = "AllegatoSuap")
public class AllegatoSuap
    extends AllegatoGenerico
{

    @XmlAttribute(name = "descrittore", required = true)
    protected String descrittore;

    /**
     * Recupera il valore della propriet descrittore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrittore() {
        return descrittore;
    }

    /**
     * Imposta il valore della propriet descrittore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrittore(String value) {
        this.descrittore = value;
    }

}
