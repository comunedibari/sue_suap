package it.wego.auriga.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="ClassifUA" type="{}ClassifUAType"/>
 *         &lt;element name="FolderCustom" type="{}EstremiXIdentificazioneFolderNoLibType"/>
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
    "classifUA",
    "folderCustom"
})
public class ClassifFascicoloType {

    @XmlElement(name = "ClassifUA")
    protected ClassifUAType classifUA;
    @XmlElement(name = "FolderCustom")
    protected EstremiXIdentificazioneFolderNoLibType folderCustom;

    /**
     * Recupera il valore della propriet� classifUA.
     * 
     * @return
     *     possible object is
     *     {@link ClassifUAType }
     *     
     */
    public ClassifUAType getClassifUA() {
        return classifUA;
    }

    /**
     * Imposta il valore della propriet� classifUA.
     * 
     * @param value
     *     allowed object is
     *     {@link ClassifUAType }
     *     
     */
    public void setClassifUA(ClassifUAType value) {
        this.classifUA = value;
    }

    /**
     * Recupera il valore della propriet� folderCustom.
     * 
     * @return
     *     possible object is
     *     {@link EstremiXIdentificazioneFolderNoLibType }
     *     
     */
    public EstremiXIdentificazioneFolderNoLibType getFolderCustom() {
        return folderCustom;
    }

    /**
     * Imposta il valore della propriet� folderCustom.
     * 
     * @param value
     *     allowed object is
     *     {@link EstremiXIdentificazioneFolderNoLibType }
     *     
     */
    public void setFolderCustom(EstremiXIdentificazioneFolderNoLibType value) {
        this.folderCustom = value;
    }

}
