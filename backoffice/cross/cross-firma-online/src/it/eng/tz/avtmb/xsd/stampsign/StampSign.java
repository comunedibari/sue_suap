
package it.eng.tz.avtmb.xsd.stampsign;

import java.util.ArrayList;
import java.util.List;
import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for stampSign complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="stampSign">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="clientId" type="{http://www.w3.org/2001/XMLSchema}normalizedString"/>
 *         &lt;element name="authority" type="{http://www.w3.org/2001/XMLSchema}normalizedString"/>
 *         &lt;element name="identificativoDocumento" type="{http://www.w3.org/2001/XMLSchema}normalizedString"/>
 *         &lt;element name="composizioneDocumento" type="{http://stampsign.xsd.avtmb.tz.eng.it}composizioneDocumento"/>
 *         &lt;element name="dataDocumento" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="iriAmministrazione" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *         &lt;element name="oggettoDocumento" type="{http://www.w3.org/2001/XMLSchema}normalizedString"/>
 *         &lt;element name="tipoDocumento" type="{http://stampsign.xsd.avtmb.tz.eng.it}tipoDocumento"/>
 *         &lt;choice>
 *           &lt;element name="fileDocumento" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *           &lt;element name="iriDocumento" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *         &lt;/choice>
 *         &lt;element name="infoDoc" type="{http://stampsign.xsd.avtmb.tz.eng.it}infoDoc" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="extraMetaDoc" type="{http://stampsign.xsd.avtmb.tz.eng.it}extraMeta"/>
 *         &lt;element name="documentInResponse" type="{http://stampsign.xsd.avtmb.tz.eng.it}documentInResponseType" minOccurs="0"/>
 *         &lt;element name="transactionId" type="{http://www.w3.org/2001/XMLSchema}normalizedString"/>
 *         &lt;element name="signer" type="{http://www.w3.org/2001/XMLSchema}normalizedString"/>
 *         &lt;element name="otp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoFirma" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="singleStampSize" type="{http://stampsign.xsd.avtmb.tz.eng.it}singleStampSizeType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "stampSign", propOrder = {
    "clientId",
    "authority",
    "identificativoDocumento",
    "composizioneDocumento",
    "dataDocumento",
    "iriAmministrazione",
    "oggettoDocumento",
    "tipoDocumento",
    "fileDocumento",
    "iriDocumento",
    "infoDoc",
    "extraMetaDoc",
    "documentInResponse",
    "transactionId",
    "signer",
    "otp",
    "tipoFirma",
    "singleStampSize"
})
public class StampSign {

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String clientId;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String authority;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String identificativoDocumento;
    @XmlElement(required = true)
    protected ComposizioneDocumento composizioneDocumento;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataDocumento;
    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
    protected String iriAmministrazione;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String oggettoDocumento;
    @XmlElement(required = true)
    protected TipoDocumento tipoDocumento;
    @XmlMimeType("application/pdf")
    protected DataHandler fileDocumento;
    @XmlSchemaType(name = "anyURI")
    protected String iriDocumento;
    protected List<InfoDoc> infoDoc;
    @XmlElement(required = true)
    protected ExtraMeta extraMetaDoc;
    @XmlElement(defaultValue = "NO")
    protected DocumentInResponseType documentInResponse;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String transactionId;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String signer;
    protected String otp;
    protected String tipoFirma;
    @XmlElement(defaultValue = "QUARANTA")
    protected SingleStampSizeType singleStampSize;

    /**
     * Gets the value of the clientId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Sets the value of the clientId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientId(String value) {
        this.clientId = value;
    }

    /**
     * Gets the value of the authority property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthority() {
        return authority;
    }

    /**
     * Sets the value of the authority property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthority(String value) {
        this.authority = value;
    }

    /**
     * Gets the value of the identificativoDocumento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoDocumento() {
        return identificativoDocumento;
    }

    /**
     * Sets the value of the identificativoDocumento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoDocumento(String value) {
        this.identificativoDocumento = value;
    }

    /**
     * Gets the value of the composizioneDocumento property.
     * 
     * @return
     *     possible object is
     *     {@link ComposizioneDocumento }
     *     
     */
    public ComposizioneDocumento getComposizioneDocumento() {
        return composizioneDocumento;
    }

    /**
     * Sets the value of the composizioneDocumento property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComposizioneDocumento }
     *     
     */
    public void setComposizioneDocumento(ComposizioneDocumento value) {
        this.composizioneDocumento = value;
    }

    /**
     * Gets the value of the dataDocumento property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataDocumento() {
        return dataDocumento;
    }

    /**
     * Sets the value of the dataDocumento property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataDocumento(XMLGregorianCalendar value) {
        this.dataDocumento = value;
    }

    /**
     * Gets the value of the iriAmministrazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIriAmministrazione() {
        return iriAmministrazione;
    }

    /**
     * Sets the value of the iriAmministrazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIriAmministrazione(String value) {
        this.iriAmministrazione = value;
    }

    /**
     * Gets the value of the oggettoDocumento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOggettoDocumento() {
        return oggettoDocumento;
    }

    /**
     * Sets the value of the oggettoDocumento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOggettoDocumento(String value) {
        this.oggettoDocumento = value;
    }

    /**
     * Gets the value of the tipoDocumento property.
     * 
     * @return
     *     possible object is
     *     {@link TipoDocumento }
     *     
     */
    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * Sets the value of the tipoDocumento property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoDocumento }
     *     
     */
    public void setTipoDocumento(TipoDocumento value) {
        this.tipoDocumento = value;
    }

    /**
     * Gets the value of the fileDocumento property.
     * 
     * @return
     *     possible object is
     *     {@link DataHandler }
     *     
     */
    public DataHandler getFileDocumento() {
        return fileDocumento;
    }

    /**
     * Sets the value of the fileDocumento property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataHandler }
     *     
     */
    public void setFileDocumento(DataHandler value) {
        this.fileDocumento = value;
    }

    /**
     * Gets the value of the iriDocumento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIriDocumento() {
        return iriDocumento;
    }

    /**
     * Sets the value of the iriDocumento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIriDocumento(String value) {
        this.iriDocumento = value;
    }

    /**
     * Gets the value of the infoDoc property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the infoDoc property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInfoDoc().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InfoDoc }
     * 
     * 
     */
    public List<InfoDoc> getInfoDoc() {
        if (infoDoc == null) {
            infoDoc = new ArrayList<InfoDoc>();
        }
        return this.infoDoc;
    }

    /**
     * Gets the value of the extraMetaDoc property.
     * 
     * @return
     *     possible object is
     *     {@link ExtraMeta }
     *     
     */
    public ExtraMeta getExtraMetaDoc() {
        return extraMetaDoc;
    }

    /**
     * Sets the value of the extraMetaDoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExtraMeta }
     *     
     */
    public void setExtraMetaDoc(ExtraMeta value) {
        this.extraMetaDoc = value;
    }

    /**
     * Gets the value of the documentInResponse property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentInResponseType }
     *     
     */
    public DocumentInResponseType getDocumentInResponse() {
        return documentInResponse;
    }

    /**
     * Sets the value of the documentInResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentInResponseType }
     *     
     */
    public void setDocumentInResponse(DocumentInResponseType value) {
        this.documentInResponse = value;
    }

    /**
     * Gets the value of the transactionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * Sets the value of the transactionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransactionId(String value) {
        this.transactionId = value;
    }

    /**
     * Gets the value of the signer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSigner() {
        return signer;
    }

    /**
     * Sets the value of the signer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSigner(String value) {
        this.signer = value;
    }

    /**
     * Gets the value of the otp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOtp() {
        return otp;
    }

    /**
     * Sets the value of the otp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOtp(String value) {
        this.otp = value;
    }

    /**
     * Gets the value of the tipoFirma property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoFirma() {
        return tipoFirma;
    }

    /**
     * Sets the value of the tipoFirma property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoFirma(String value) {
        this.tipoFirma = value;
    }

    /**
     * Gets the value of the singleStampSize property.
     * 
     * @return
     *     possible object is
     *     {@link SingleStampSizeType }
     *     
     */
    public SingleStampSizeType getSingleStampSize() {
        return singleStampSize;
    }

    /**
     * Sets the value of the singleStampSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link SingleStampSizeType }
     *     
     */
    public void setSingleStampSize(SingleStampSizeType value) {
        this.singleStampSize = value;
    }

}
