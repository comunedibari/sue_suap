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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *             Descrive un file di tipo modello-attivita
 *         
 * 
 * <p>Classe Java per ModelloAttivita complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ModelloAttivita">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.impresainungiorno.gov.it/schema/suap/pratica}BaseAllegatoSUAP">
 *       &lt;sequence>
 *         &lt;element name="tracciato-xml" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{http://www.impresainungiorno.gov.it/schema/suap/pratica}BaseAllegatoSUAP">
 *                 &lt;attribute name="nome-file" use="required" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}TipoFileAllegatoModelloAttivita" />
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="nome-file" use="required" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}TipoFileAllegatoDistintaModelloAttivita" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ModelloAttivita", propOrder = {
    "tracciatoXml"
})
public class ModelloAttivita
    extends BaseAllegatoSUAP
{

    @XmlElement(name = "tracciato-xml")
    protected ModelloAttivita.TracciatoXml tracciatoXml;
    @XmlAttribute(name = "nome-file", required = true)
    protected String nomeFile;

    /**
     * Recupera il valore della propriet tracciatoXml.
     * 
     * @return
     *     possible object is
     *     {@link ModelloAttivita.TracciatoXml }
     *     
     */
    public ModelloAttivita.TracciatoXml getTracciatoXml() {
        return tracciatoXml;
    }

    /**
     * Imposta il valore della propriet tracciatoXml.
     * 
     * @param value
     *     allowed object is
     *     {@link ModelloAttivita.TracciatoXml }
     *     
     */
    public void setTracciatoXml(ModelloAttivita.TracciatoXml value) {
        this.tracciatoXml = value;
    }

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
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{http://www.impresainungiorno.gov.it/schema/suap/pratica}BaseAllegatoSUAP">
     *       &lt;attribute name="nome-file" use="required" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}TipoFileAllegatoModelloAttivita" />
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class TracciatoXml
        extends BaseAllegatoSUAP
    {

        @XmlAttribute(name = "nome-file", required = true)
        protected String nomeFile;

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

    }

}
