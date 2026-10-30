package it.wego.auriga.xml.fileallegati;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
* <p>Classe Java per anonymous complex type.
* 
* <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
* 
* <pre>
* &lt;complexType&gt;
*   &lt;complexContent&gt;
*     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
*       &lt;sequence&gt;
*         &lt;element name="NomeFile" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
*         &lt;element name="NroVersione" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
*         &lt;element name="NroUltimaVersione" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
*         &lt;element name="NroAllegato" minOccurs="0"&gt;
*           &lt;simpleType&gt;
*             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger"&gt;
*               &lt;minInclusive value="1"/&gt;
*             &lt;/restriction&gt;
*           &lt;/simpleType&gt;
*         &lt;/element&gt;
*         &lt;element name="TipoDocAllegato" minOccurs="0"&gt;
*           &lt;complexType&gt;
*             &lt;complexContent&gt;
*               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
*                 &lt;attribute name="CodiceIdentificativo" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
*               &lt;/restriction&gt;
*             &lt;/complexContent&gt;
*           &lt;/complexType&gt;
*         &lt;/element&gt;
*         &lt;element name="DesAllegato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
*       &lt;/sequence&gt;
*     &lt;/restriction&gt;
*   &lt;/complexContent&gt;
* &lt;/complexType&gt;
* </pre>
* 
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
 "nomeFile",
 "nroVersione",
 "nroUltimaVersione",
 "nroAllegato",
 "tipoDocAllegato",
 "desAllegato"
})
@XmlRootElement(name = "Output_FileUDToExtract")
public class MyOutputFileUDToExtract {

 @XmlElement(name = "NomeFile", required = true)
 protected String nomeFile;
 @XmlElement(name = "NroVersione", required = true)
 protected int nroVersione;
 @XmlElement(name = "NroUltimaVersione", required = true)
 protected int nroUltimaVersione;
 @XmlElement(name = "NroAllegato")
 protected int nroAllegato;
 @XmlElement(name = "TipoDocAllegato")
 protected OutputFileUDToExtract.TipoDocAllegato tipoDocAllegato;
 @XmlElement(name = "DesAllegato")
 protected String desAllegato;

 /**
  * Recupera il valore della proprietà nomeFile.
  * 
  * @return
  *     possible object is
  *     {@link Object }
  *     
  */
 public String getNomeFile() {
     return nomeFile;
 }

 /**
  * Imposta il valore della proprietà nomeFile.
  * 
  * @param value
  *     allowed object is
  *     {@link Object }
  *     
  */
 public void setNomeFile(String value) {
     this.nomeFile = value;
 }

 /**
  * Recupera il valore della proprietà nroVersione.
  * 
  * @return
  *     possible object is
  *     {@link Object }
  *     
  */
 public int getNroVersione() {
     return nroVersione;
 }

 /**
  * Imposta il valore della proprietà nroVersione.
  * 
  * @param value
  *     allowed object is
  *     {@link Object }
  *     
  */
 public void setNroVersione(int value) {
     this.nroVersione = value;
 }

 /**
  * Recupera il valore della proprietà nroUltimaVersione.
  * 
  * @return
  *     possible object is
  *     {@link Object }
  *     
  */
 public int getNroUltimaVersione() {
     return nroUltimaVersione;
 }

 /**
  * Imposta il valore della proprietà nroUltimaVersione.
  * 
  * @param value
  *     allowed object is
  *     {@link Object }
  *     
  */
 public void setNroUltimaVersione(int value) {
     this.nroUltimaVersione = value;
 }

 /**
  * Recupera il valore della proprietà nroAllegato.
  * 
  * @return
  *     possible object is
  *     {@link BigInteger }
  *     
  */
 public int getNroAllegato() {
     return nroAllegato;
 }

 /**
  * Imposta il valore della proprietà nroAllegato.
  * 
  * @param value
  *     allowed object is
  *     {@link BigInteger }
  *     
  */
 public void setNroAllegato(int value) {
     this.nroAllegato = value;
 }

 /**
  * Recupera il valore della proprietà tipoDocAllegato.
  * 
  * @return
  *     possible object is
  *     {@link OutputFileUDToExtract.TipoDocAllegato }
  *     
  */
 public OutputFileUDToExtract.TipoDocAllegato getTipoDocAllegato() {
     return tipoDocAllegato;
 }

 /**
  * Imposta il valore della proprietà tipoDocAllegato.
  * 
  * @param value
  *     allowed object is
  *     {@link OutputFileUDToExtract.TipoDocAllegato }
  *     
  */
 public void setTipoDocAllegato(OutputFileUDToExtract.TipoDocAllegato value) {
     this.tipoDocAllegato = value;
 }

 /**
  * Recupera il valore della proprietà desAllegato.
  * 
  * @return
  *     possible object is
  *     {@link String }
  *     
  */
 public String getDesAllegato() {
     return desAllegato;
 }

 /**
  * Imposta il valore della proprietà desAllegato.
  * 
  * @param value
  *     allowed object is
  *     {@link String }
  *     
  */
 public void setDesAllegato(String value) {
     this.desAllegato = value;
 }


 /**
  * <p>Classe Java per anonymous complex type.
  * 
  * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
  * 
  * <pre>
  * &lt;complexType&gt;
  *   &lt;complexContent&gt;
  *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
  *       &lt;attribute name="CodiceIdentificativo" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
  *     &lt;/restriction&gt;
  *   &lt;/complexContent&gt;
  * &lt;/complexType&gt;
  * </pre>
  * 
  * 
  */
 @XmlAccessorType(XmlAccessType.FIELD)
 @XmlType(name = "", propOrder = {
     "content"
 })
 public static class TipoDocAllegato {

     @XmlValue
     protected String content;
     @XmlAttribute(name = "CodiceIdentificativo", required = true)
     protected String codiceIdentificativo;

     /**
      * Recupera il valore della proprietà content.
      * 
      * @return
      *     possible object is
      *     {@link String }
      *     
      */
     public String getContent() {
         return content;
     }

     /**
      * Imposta il valore della proprietà content.
      * 
      * @param value
      *     allowed object is
      *     {@link String }
      *     
      */
     public void setContent(String value) {
         this.content = value;
     }

     /**
      * Recupera il valore della proprietà codiceIdentificativo.
      * 
      * @return
      *     possible object is
      *     {@link String }
      *     
      */
     public String getCodiceIdentificativo() {
         return codiceIdentificativo;
     }

     /**
      * Imposta il valore della proprietà codiceIdentificativo.
      * 
      * @param value
      *     allowed object is
      *     {@link String }
      *     
      */
     public void setCodiceIdentificativo(String value) {
         this.codiceIdentificativo = value;
     }

 }

}
