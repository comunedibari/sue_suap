
package it.wego.cross.webservices.mypage.syncronizer.stub;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for setPraticaRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="setPraticaRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idPratica" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrizioneProcesso" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="oggettoProcesso" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="contentName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataRicezione" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="codiceNodo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="useId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cognomeRichiedente" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nomeRichiedente" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dominio" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="urlVisura" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idBo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="visibilita" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ruolo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrizioneEvento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "setPraticaRequest", propOrder = {
    "idPratica",
    "descrizioneProcesso",
    "oggettoProcesso",
    "contentName",
    "dataRicezione",
    "codiceNodo",
    "useId",
    "cognomeRichiedente",
    "nomeRichiedente",
    "dominio",
    "urlVisura",
    "idBo",
    "visibilita",
    "ruolo",
    "descrizioneEvento"
})
public class SetPraticaRequest {

    @XmlElement(required = true)
    protected String idPratica;
    @XmlElement(required = true)
    protected String descrizioneProcesso;
    @XmlElement(required = true)
    protected String oggettoProcesso;
    @XmlElement(required = true)
    protected String contentName;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataRicezione;
    @XmlElement(required = true)
    protected String codiceNodo;
    @XmlElement(required = true)
    protected String useId;
    @XmlElement(required = true)
    protected String cognomeRichiedente;
    @XmlElement(required = true)
    protected String nomeRichiedente;
    @XmlElement(required = true)
    protected String dominio;
    @XmlElement(required = true)
    protected String urlVisura;
    @XmlElement(required = true)
    protected String idBo;
    protected boolean visibilita;
    @XmlElement(required = true)
    protected String ruolo;
    @XmlElement(required = true)
    protected String descrizioneEvento;

    /**
     * Gets the value of the idPratica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdPratica() {
        return idPratica;
    }

    /**
     * Sets the value of the idPratica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdPratica(String value) {
        this.idPratica = value;
    }

    /**
     * Gets the value of the descrizioneProcesso property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneProcesso() {
        return descrizioneProcesso;
    }

    /**
     * Sets the value of the descrizioneProcesso property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneProcesso(String value) {
        this.descrizioneProcesso = value;
    }

    /**
     * Gets the value of the oggettoProcesso property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOggettoProcesso() {
        return oggettoProcesso;
    }

    /**
     * Sets the value of the oggettoProcesso property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOggettoProcesso(String value) {
        this.oggettoProcesso = value;
    }

    /**
     * Gets the value of the contentName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContentName() {
        return contentName;
    }

    /**
     * Sets the value of the contentName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContentName(String value) {
        this.contentName = value;
    }

    /**
     * Gets the value of the dataRicezione property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataRicezione() {
        return dataRicezione;
    }

    /**
     * Sets the value of the dataRicezione property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataRicezione(XMLGregorianCalendar value) {
        this.dataRicezione = value;
    }

    /**
     * Gets the value of the codiceNodo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceNodo() {
        return codiceNodo;
    }

    /**
     * Sets the value of the codiceNodo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceNodo(String value) {
        this.codiceNodo = value;
    }

    /**
     * Gets the value of the useId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUseId() {
        return useId;
    }

    /**
     * Sets the value of the useId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUseId(String value) {
        this.useId = value;
    }

    /**
     * Gets the value of the cognomeRichiedente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCognomeRichiedente() {
        return cognomeRichiedente;
    }

    /**
     * Sets the value of the cognomeRichiedente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCognomeRichiedente(String value) {
        this.cognomeRichiedente = value;
    }

    /**
     * Gets the value of the nomeRichiedente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeRichiedente() {
        return nomeRichiedente;
    }

    /**
     * Sets the value of the nomeRichiedente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeRichiedente(String value) {
        this.nomeRichiedente = value;
    }

    /**
     * Gets the value of the dominio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDominio() {
        return dominio;
    }

    /**
     * Sets the value of the dominio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDominio(String value) {
        this.dominio = value;
    }

    /**
     * Gets the value of the urlVisura property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrlVisura() {
        return urlVisura;
    }

    /**
     * Sets the value of the urlVisura property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrlVisura(String value) {
        this.urlVisura = value;
    }

    /**
     * Gets the value of the idBo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdBo() {
        return idBo;
    }

    /**
     * Sets the value of the idBo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdBo(String value) {
        this.idBo = value;
    }

    /**
     * Gets the value of the visibilita property.
     * 
     */
    public boolean isVisibilita() {
        return visibilita;
    }

    /**
     * Sets the value of the visibilita property.
     * 
     */
    public void setVisibilita(boolean value) {
        this.visibilita = value;
    }

    /**
     * Gets the value of the ruolo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRuolo() {
        return ruolo;
    }

    /**
     * Sets the value of the ruolo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRuolo(String value) {
        this.ruolo = value;
    }

    /**
     * Gets the value of the descrizioneEvento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneEvento() {
        return descrizioneEvento;
    }

    /**
     * Sets the value of the descrizioneEvento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneEvento(String value) {
        this.descrizioneEvento = value;
    }

}
