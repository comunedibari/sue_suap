//
// Questo file stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrpersa durante la ricompilazione dello schema di origine. 
// Generato il: 2013.03.28 alle 03:45:54 PM CET 
//


package it.wego.cross.client.xml.addud;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per RegistrazioneNumerazioneType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RegistrazioneNumerazioneType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="CategoriaReg">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="PG"/>
 *               &lt;enumeration value="PP"/>
 *               &lt;enumeration value="R"/>
 *               &lt;enumeration value="E"/>
 *               &lt;enumeration value="A"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="SiglaReg" type="{}SiglaRegNumType" minOccurs="0"/>
 *         &lt;element name="AnnoReg" type="{}AnnoType" minOccurs="0"/>
 *         &lt;element name="NumReg" type="{}NumRegType"/>
 *         &lt;element name="DataOraReg" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="UOReg" type="{}UOType" minOccurs="0"/>
 *         &lt;element name="UserReg" type="{}UserType" minOccurs="0"/>
 *         &lt;element name="FlagRegAnnullata" type="{}FlagSiNoType" minOccurs="0"/>
 *       &lt;/all>
 *       &lt;attribute name="IdRegNum" type="{}IdInSistemaEsternoType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RegistrazioneNumerazioneType", propOrder = {

})
public class RegistrazioneNumerazioneType {

    @XmlElement(name = "CategoriaReg", required = true)
    protected String categoriaReg;
    @XmlElement(name = "SiglaReg")
    protected String siglaReg;
    @XmlElement(name = "AnnoReg")
    protected Integer annoReg;
    @XmlElement(name = "NumReg")
    protected int numReg;
    @XmlElement(name = "DataOraReg")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataOraReg;
    @XmlElement(name = "UOReg")
    protected UOType uoReg;
    @XmlElement(name = "UserReg")
    protected UserType userReg;
    @XmlElement(name = "FlagRegAnnullata")
    protected String flagRegAnnullata;
    @XmlAttribute(name = "IdRegNum")
    protected String idRegNum;

    /**
     * Recupera il valore della proprietcategoriaReg.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCategoriaReg() {
        return categoriaReg;
    }

    /**
     * Imposta il valore della proprietcategoriaReg.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCategoriaReg(String value) {
        this.categoriaReg = value;
    }

    /**
     * Recupera il valore della proprietsiglaReg.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiglaReg() {
        return siglaReg;
    }

    /**
     * Imposta il valore della proprietsiglaReg.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiglaReg(String value) {
        this.siglaReg = value;
    }

    /**
     * Recupera il valore della proprietannoReg.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAnnoReg() {
        return annoReg;
    }

    /**
     * Imposta il valore della proprietannoReg.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAnnoReg(Integer value) {
        this.annoReg = value;
    }

    /**
     * Recupera il valore della proprietnumReg.
     * 
     */
    public int getNumReg() {
        return numReg;
    }

    /**
     * Imposta il valore della proprietnumReg.
     * 
     */
    public void setNumReg(int value) {
        this.numReg = value;
    }

    /**
     * Recupera il valore della proprietdataOraReg.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataOraReg() {
        return dataOraReg;
    }

    /**
     * Imposta il valore della proprietdataOraReg.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataOraReg(XMLGregorianCalendar value) {
        this.dataOraReg = value;
    }

    /**
     * Recupera il valore della proprietuoReg.
     * 
     * @return
     *     possible object is
     *     {@link UOType }
     *     
     */
    public UOType getUOReg() {
        return uoReg;
    }

    /**
     * Imposta il valore della proprietuoReg.
     * 
     * @param value
     *     allowed object is
     *     {@link UOType }
     *     
     */
    public void setUOReg(UOType value) {
        this.uoReg = value;
    }

    /**
     * Recupera il valore della proprietuserReg.
     * 
     * @return
     *     possible object is
     *     {@link UserType }
     *     
     */
    public UserType getUserReg() {
        return userReg;
    }

    /**
     * Imposta il valore della proprietuserReg.
     * 
     * @param value
     *     allowed object is
     *     {@link UserType }
     *     
     */
    public void setUserReg(UserType value) {
        this.userReg = value;
    }

    /**
     * Recupera il valore della proprietflagRegAnnullata.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagRegAnnullata() {
        return flagRegAnnullata;
    }

    /**
     * Imposta il valore della proprietflagRegAnnullata.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagRegAnnullata(String value) {
        this.flagRegAnnullata = value;
    }

    /**
     * Recupera il valore della proprietidRegNum.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdRegNum() {
        return idRegNum;
    }

    /**
     * Imposta il valore della proprietidRegNum.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdRegNum(String value) {
        this.idRegNum = value;
    }

}
