//
// Questo file stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrpersa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.07.14 alle 03:42:18 PM CEST 
//


package it.wego.cross.client.xml.getmetadata;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per IndirizzoType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="IndirizzoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Toponimo" type="{}ToponimoType" minOccurs="0"/>
 *         &lt;element name="Civico" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Interno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Scala" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Piano" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="Localita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CAP" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="[0-9]{5}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ComuneIndirizzo" type="{}ComuneItalianoType" minOccurs="0"/>
 *         &lt;element name="NomeCittaEstera" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="StatoIndirizzo" type="{}StatoNazionaleType" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IndirizzoType", propOrder = {

})
public class IndirizzoType {

    @XmlElement(name = "Toponimo")
    protected ToponimoType toponimo;
    @XmlElement(name = "Civico")
    protected String civico;
    @XmlElement(name = "Interno")
    protected String interno;
    @XmlElement(name = "Scala")
    protected String scala;
    @XmlElement(name = "Piano")
    protected BigInteger piano;
    @XmlElement(name = "Localita")
    protected String localita;
    @XmlElement(name = "CAP")
    protected String cap;
    @XmlElement(name = "ComuneIndirizzo")
    protected ComuneItalianoType comuneIndirizzo;
    @XmlElement(name = "NomeCittaEstera")
    protected String nomeCittaEstera;
    @XmlElement(name = "StatoIndirizzo")
    protected StatoNazionaleType statoIndirizzo;

    /**
     * Recupera il valore della propriettoponimo.
     * 
     * @return
     *     possible object is
     *     {@link ToponimoType }
     *     
     */
    public ToponimoType getToponimo() {
        return toponimo;
    }

    /**
     * Imposta il valore della propriettoponimo.
     * 
     * @param value
     *     allowed object is
     *     {@link ToponimoType }
     *     
     */
    public void setToponimo(ToponimoType value) {
        this.toponimo = value;
    }

    /**
     * Recupera il valore della proprietcivico.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCivico() {
        return civico;
    }

    /**
     * Imposta il valore della proprietcivico.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCivico(String value) {
        this.civico = value;
    }

    /**
     * Recupera il valore della proprietinterno.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInterno() {
        return interno;
    }

    /**
     * Imposta il valore della proprietinterno.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInterno(String value) {
        this.interno = value;
    }

    /**
     * Recupera il valore della proprietscala.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScala() {
        return scala;
    }

    /**
     * Imposta il valore della proprietscala.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScala(String value) {
        this.scala = value;
    }

    /**
     * Recupera il valore della proprietpiano.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPiano() {
        return piano;
    }

    /**
     * Imposta il valore della proprietpiano.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPiano(BigInteger value) {
        this.piano = value;
    }

    /**
     * Recupera il valore della proprietlocalita.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocalita() {
        return localita;
    }

    /**
     * Imposta il valore della proprietlocalita.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocalita(String value) {
        this.localita = value;
    }

    /**
     * Recupera il valore della proprietcap.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCAP() {
        return cap;
    }

    /**
     * Imposta il valore della proprietcap.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCAP(String value) {
        this.cap = value;
    }

    /**
     * Recupera il valore della proprietcomuneIndirizzo.
     * 
     * @return
     *     possible object is
     *     {@link ComuneItalianoType }
     *     
     */
    public ComuneItalianoType getComuneIndirizzo() {
        return comuneIndirizzo;
    }

    /**
     * Imposta il valore della proprietcomuneIndirizzo.
     * 
     * @param value
     *     allowed object is
     *     {@link ComuneItalianoType }
     *     
     */
    public void setComuneIndirizzo(ComuneItalianoType value) {
        this.comuneIndirizzo = value;
    }

    /**
     * Recupera il valore della proprietnomeCittaEstera.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeCittaEstera() {
        return nomeCittaEstera;
    }

    /**
     * Imposta il valore della proprietnomeCittaEstera.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeCittaEstera(String value) {
        this.nomeCittaEstera = value;
    }

    /**
     * Recupera il valore della proprietstatoIndirizzo.
     * 
     * @return
     *     possible object is
     *     {@link StatoNazionaleType }
     *     
     */
    public StatoNazionaleType getStatoIndirizzo() {
        return statoIndirizzo;
    }

    /**
     * Imposta il valore della proprietstatoIndirizzo.
     * 
     * @param value
     *     allowed object is
     *     {@link StatoNazionaleType }
     *     
     */
    public void setStatoIndirizzo(StatoNazionaleType value) {
        this.statoIndirizzo = value;
    }

}
