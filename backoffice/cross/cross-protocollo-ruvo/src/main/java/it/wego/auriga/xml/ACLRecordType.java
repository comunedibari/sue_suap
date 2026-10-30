package it.wego.auriga.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ACLRecordType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ACLRecordType">
 *   &lt;complexContent>
 *     &lt;extension base="{}SoggettoInternoInACLType">
 *       &lt;sequence>
 *         &lt;element name="VisualizzazioneDati" type="{}FlagConsentiNegaType" minOccurs="0"/>
 *         &lt;element name="VisualizzazioneFile" type="{}PrivilegioSuFileType" minOccurs="0"/>
 *         &lt;element name="ModificaDati" type="{}FlagConsentiNegaType" minOccurs="0"/>
 *         &lt;element name="ModificaFile" type="{}PrivilegioSuFileType" minOccurs="0"/>
 *         &lt;element name="ModificaPermessi" type="{}FlagConsentiNegaType" minOccurs="0"/>
 *         &lt;element name="Copia" type="{}FlagConsentiNegaType" minOccurs="0"/>
 *         &lt;element name="Eliminazione" type="{}FlagConsentiNegaType" minOccurs="0"/>
 *         &lt;element name="Recupero" type="{}FlagConsentiNegaType" minOccurs="0"/>
 *         &lt;element name="AccessoLimitatoVerFilePubblicate" type="{}FileUDxPrivilegioType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ACLRecordType", propOrder = {
    "visualizzazioneDati",
    "visualizzazioneFile",
    "modificaDati",
    "modificaFile",
    "modificaPermessi",
    "copia",
    "eliminazione",
    "recupero",
    "accessoLimitatoVerFilePubblicate"
})
public class ACLRecordType
    extends SoggettoInternoInACLType
{

    @XmlElement(name = "VisualizzazioneDati")
    protected String visualizzazioneDati;
    @XmlElement(name = "VisualizzazioneFile")
    protected PrivilegioSuFileType visualizzazioneFile;
    @XmlElement(name = "ModificaDati")
    protected String modificaDati;
    @XmlElement(name = "ModificaFile")
    protected PrivilegioSuFileType modificaFile;
    @XmlElement(name = "ModificaPermessi")
    protected String modificaPermessi;
    @XmlElement(name = "Copia")
    protected String copia;
    @XmlElement(name = "Eliminazione")
    protected String eliminazione;
    @XmlElement(name = "Recupero")
    protected String recupero;
    @XmlElement(name = "AccessoLimitatoVerFilePubblicate")
    protected FileUDxPrivilegioType accessoLimitatoVerFilePubblicate;

    /**
     * Recupera il valore della propriet� visualizzazioneDati.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVisualizzazioneDati() {
        return visualizzazioneDati;
    }

    /**
     * Imposta il valore della propriet� visualizzazioneDati.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVisualizzazioneDati(String value) {
        this.visualizzazioneDati = value;
    }

    /**
     * Recupera il valore della propriet� visualizzazioneFile.
     * 
     * @return
     *     possible object is
     *     {@link PrivilegioSuFileType }
     *     
     */
    public PrivilegioSuFileType getVisualizzazioneFile() {
        return visualizzazioneFile;
    }

    /**
     * Imposta il valore della propriet� visualizzazioneFile.
     * 
     * @param value
     *     allowed object is
     *     {@link PrivilegioSuFileType }
     *     
     */
    public void setVisualizzazioneFile(PrivilegioSuFileType value) {
        this.visualizzazioneFile = value;
    }

    /**
     * Recupera il valore della propriet� modificaDati.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModificaDati() {
        return modificaDati;
    }

    /**
     * Imposta il valore della propriet� modificaDati.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModificaDati(String value) {
        this.modificaDati = value;
    }

    /**
     * Recupera il valore della propriet� modificaFile.
     * 
     * @return
     *     possible object is
     *     {@link PrivilegioSuFileType }
     *     
     */
    public PrivilegioSuFileType getModificaFile() {
        return modificaFile;
    }

    /**
     * Imposta il valore della propriet� modificaFile.
     * 
     * @param value
     *     allowed object is
     *     {@link PrivilegioSuFileType }
     *     
     */
    public void setModificaFile(PrivilegioSuFileType value) {
        this.modificaFile = value;
    }

    /**
     * Recupera il valore della propriet� modificaPermessi.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModificaPermessi() {
        return modificaPermessi;
    }

    /**
     * Imposta il valore della propriet� modificaPermessi.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModificaPermessi(String value) {
        this.modificaPermessi = value;
    }

    /**
     * Recupera il valore della propriet� copia.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCopia() {
        return copia;
    }

    /**
     * Imposta il valore della propriet� copia.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCopia(String value) {
        this.copia = value;
    }

    /**
     * Recupera il valore della propriet� eliminazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEliminazione() {
        return eliminazione;
    }

    /**
     * Imposta il valore della propriet� eliminazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEliminazione(String value) {
        this.eliminazione = value;
    }

    /**
     * Recupera il valore della propriet� recupero.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecupero() {
        return recupero;
    }

    /**
     * Imposta il valore della propriet� recupero.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecupero(String value) {
        this.recupero = value;
    }

    /**
     * Recupera il valore della propriet� accessoLimitatoVerFilePubblicate.
     * 
     * @return
     *     possible object is
     *     {@link FileUDxPrivilegioType }
     *     
     */
    public FileUDxPrivilegioType getAccessoLimitatoVerFilePubblicate() {
        return accessoLimitatoVerFilePubblicate;
    }

    /**
     * Imposta il valore della propriet� accessoLimitatoVerFilePubblicate.
     * 
     * @param value
     *     allowed object is
     *     {@link FileUDxPrivilegioType }
     *     
     */
    public void setAccessoLimitatoVerFilePubblicate(FileUDxPrivilegioType value) {
        this.accessoLimitatoVerFilePubblicate = value;
    }

}
