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
import it.gov.impresainungiorno.schema.suap.pratica.BaseAllegatoSUAP;


/**
 * <p>Classe Java per AllegatoCooperazione complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="AllegatoCooperazione">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.impresainungiorno.gov.it/schema/suap/pratica}BaseAllegatoSUAP">
 *       &lt;attribute name="nome-file" use="required" type="{http://www.impresainungiorno.gov.it/schema/suap/ente}TipoFileAllegatoCooperazione" />
 *       &lt;attribute name="cod" type="{http://www.impresainungiorno.gov.it/schema/base}Stringa" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AllegatoCooperazione")
public class AllegatoCooperazione
    extends BaseAllegatoSUAP
{

    @XmlAttribute(name = "nome-file", required = true)
    protected String nomeFile;
    @XmlAttribute(name = "cod")
    protected String cod;

    /**
     * Recupera il valore della propriet nomeFile.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeFile() {
        return nomeFile;
    }

    /**
     * Imposta il valore della propriet nomeFile.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeFile(String value) {
        this.nomeFile = value;
    }

    /**
     * Recupera il valore della propriet cod.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCod() {
        return cod;
    }

    /**
     * Imposta il valore della propriet cod.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCod(String value) {
        this.cod = value;
    }

}
