//
// Questo file stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrpersa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.07.14 alle 03:42:18 PM CEST 
//


package it.wego.cross.client.xml.getmetadata;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ClassifFascicoloType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ClassifFascicoloType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="ClassifUAType" type="{}ClassifUAType"/>
 *         &lt;element name="FolderCustomType" type="{}FolderCustomType"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClassifFascicoloType", propOrder = {
    "classifUAType",
    "folderCustomType"
})
@XmlSeeAlso({
    ClassifFascicoloEstesoType.class
})
public class ClassifFascicoloType {

    @XmlElement(name = "ClassifUAType")
    protected ClassifUAType classifUAType;
    @XmlElement(name = "FolderCustomType")
    protected String folderCustomType;

    /**
     * Recupera il valore della proprietclassifUAType.
     * 
     * @return
     *     possible object is
     *     {@link ClassifUAType }
     *     
     */
    public ClassifUAType getClassifUAType() {
        return classifUAType;
    }

    /**
     * Imposta il valore della proprietclassifUAType.
     * 
     * @param value
     *     allowed object is
     *     {@link ClassifUAType }
     *     
     */
    public void setClassifUAType(ClassifUAType value) {
        this.classifUAType = value;
    }

    /**
     * Recupera il valore della proprietfolderCustomType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFolderCustomType() {
        return folderCustomType;
    }

    /**
     * Imposta il valore della proprietfolderCustomType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFolderCustomType(String value) {
        this.folderCustomType = value;
    }

}
