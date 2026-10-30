package it.wego.auriga.xml;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per FileUDxPrivilegioType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="FileUDxPrivilegioType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="AllFiles" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *         &lt;element name="SuFile" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" maxOccurs="unbounded"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FileUDxPrivilegioType", propOrder = {
    "allFiles",
    "suFile"
})
public class FileUDxPrivilegioType {

    @XmlElement(name = "AllFiles")
    protected Object allFiles;
    @XmlElement(name = "SuFile")
    @XmlSchemaType(name = "positiveInteger")
    protected List<BigInteger> suFile;

    /**
     * Recupera il valore della propriet� allFiles.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getAllFiles() {
        return allFiles;
    }

    /**
     * Imposta il valore della propriet� allFiles.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setAllFiles(Object value) {
        this.allFiles = value;
    }

    /**
     * Gets the value of the suFile property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the suFile property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSuFile().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BigInteger }
     * 
     * 
     */
    public List<BigInteger> getSuFile() {
        if (suFile == null) {
            suFile = new ArrayList<BigInteger>();
        }
        return this.suFile;
    }

}
