//
// Questo file stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrpersa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.07.14 alle 03:42:18 PM CEST 
//


package it.wego.cross.client.xml.getmetadata;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per PrivilegioSuFileType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="PrivilegioSuFileType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Files" type="{}FileUDxPrivilegioType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="ConsentiNega" use="required" type="{}FlagConsentiNegaType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PrivilegioSuFileType", propOrder = {
    "files"
})
public class PrivilegioSuFileType {

    @XmlElement(name = "Files", required = true)
    protected FileUDxPrivilegioType files;
    @XmlAttribute(name = "ConsentiNega", required = true)
    protected String consentiNega;

    /**
     * Recupera il valore della proprietfiles.
     * 
     * @return
     *     possible object is
     *     {@link FileUDxPrivilegioType }
     *     
     */
    public FileUDxPrivilegioType getFiles() {
        return files;
    }

    /**
     * Imposta il valore della proprietfiles.
     * 
     * @param value
     *     allowed object is
     *     {@link FileUDxPrivilegioType }
     *     
     */
    public void setFiles(FileUDxPrivilegioType value) {
        this.files = value;
    }

    /**
     * Recupera il valore della proprietconsentiNega.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsentiNega() {
        return consentiNega;
    }

    /**
     * Imposta il valore della proprietconsentiNega.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsentiNega(String value) {
        this.consentiNega = value;
    }

}
