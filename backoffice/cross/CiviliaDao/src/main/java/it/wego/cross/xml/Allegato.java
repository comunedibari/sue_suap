//
// Questo file stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrpersa durante la ricompilazione dello schema di origine. 
// Generato il: 2013.05.27 alle 07:27:17 PM CEST 
//


package it.wego.cross.xml;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per allegato complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="allegato">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="riepilogoPratica" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="S"/>
 *               &lt;enumeration value="N"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="id_allegato" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nome_file" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipo_file" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="path_file" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id_file_esterno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "allegato", propOrder = {
    "riepilogoPratica",
    "idAllegato",
    "descrizione",
    "nomeFile",
    "tipoFile",
    "pathFile",
    "idFileEsterno"
})
public class Allegato {

    protected String riepilogoPratica;
    @XmlElement(name = "id_allegato")
    protected BigInteger idAllegato;
    protected String descrizione;
    @XmlElement(name = "nome_file")
    protected String nomeFile;
    @XmlElement(name = "tipo_file")
    protected String tipoFile;
    @XmlElement(name = "path_file")
    protected String pathFile;
    @XmlElement(name = "id_file_esterno")
    protected String idFileEsterno;

    /**
     * Recupera il valore della proprietriepilogoPratica.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiepilogoPratica() {
        return riepilogoPratica;
    }

    /**
     * Imposta il valore della proprietriepilogoPratica.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiepilogoPratica(String value) {
        this.riepilogoPratica = value;
    }

    /**
     * Recupera il valore della proprietidAllegato.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdAllegato() {
        return idAllegato;
    }

    /**
     * Imposta il valore della proprietidAllegato.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdAllegato(BigInteger value) {
        this.idAllegato = value;
    }

    /**
     * Recupera il valore della proprietdescrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Imposta il valore della proprietdescrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizione(String value) {
        this.descrizione = value;
    }

    /**
     * Recupera il valore della proprietnomeFile.
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
     * Imposta il valore della proprietnomeFile.
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
     * Recupera il valore della propriettipoFile.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoFile() {
        return tipoFile;
    }

    /**
     * Imposta il valore della propriettipoFile.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoFile(String value) {
        this.tipoFile = value;
    }

    /**
     * Recupera il valore della proprietpathFile.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPathFile() {
        return pathFile;
    }

    /**
     * Imposta il valore della proprietpathFile.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPathFile(String value) {
        this.pathFile = value;
    }

    /**
     * Recupera il valore della proprietidFileEsterno.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdFileEsterno() {
        return idFileEsterno;
    }

    /**
     * Imposta il valore della proprietidFileEsterno.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdFileEsterno(String value) {
        this.idFileEsterno = value;
    }

}
