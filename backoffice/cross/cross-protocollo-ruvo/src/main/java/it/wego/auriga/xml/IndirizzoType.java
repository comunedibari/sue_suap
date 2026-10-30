package it.wego.auriga.xml;

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
     * Recupera il valore della propriet� toponimo.
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
     * Imposta il valore della propriet� toponimo.
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
     * Recupera il valore della propriet� civico.
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
     * Imposta il valore della propriet� civico.
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
     * Recupera il valore della propriet� interno.
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
     * Imposta il valore della propriet� interno.
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
     * Recupera il valore della propriet� scala.
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
     * Imposta il valore della propriet� scala.
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
     * Recupera il valore della propriet� piano.
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
     * Imposta il valore della propriet� piano.
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
     * Recupera il valore della propriet� localita.
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
     * Imposta il valore della propriet� localita.
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
     * Recupera il valore della propriet� cap.
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
     * Imposta il valore della propriet� cap.
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
     * Recupera il valore della propriet� comuneIndirizzo.
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
     * Imposta il valore della propriet� comuneIndirizzo.
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
     * Recupera il valore della propriet� nomeCittaEstera.
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
     * Imposta il valore della propriet� nomeCittaEstera.
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
     * Recupera il valore della propriet� statoIndirizzo.
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
     * Imposta il valore della propriet� statoIndirizzo.
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
