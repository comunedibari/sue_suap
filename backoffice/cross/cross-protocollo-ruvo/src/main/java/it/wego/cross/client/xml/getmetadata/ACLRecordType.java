//
// Questo file stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrpersa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.07.14 alle 03:42:18 PM CEST 
//


package it.wego.cross.client.xml.getmetadata;

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
 *         &lt;element name="VisualizzazioneDati" type="{}FlagConsentiNegaType"/>
 *         &lt;element name="VisualizzazioneFile" type="{}PrivilegioSuFileType"/>
 *         &lt;element name="ModificaDati" type="{}FlagConsentiNegaType"/>
 *         &lt;element name="ModificaFile" type="{}PrivilegioSuFileType"/>
 *         &lt;element name="ModificaPermessi" type="{}FlagConsentiNegaType"/>
 *         &lt;element name="Copia" type="{}FlagConsentiNegaType"/>
 *         &lt;element name="Eliminazione" type="{}FlagConsentiNegaType"/>
 *         &lt;element name="Recupero" type="{}FlagConsentiNegaType"/>
 *         &lt;element name="AccessoLimitatoVerFilePubblicate" type="{}FileUDxPrivilegioType"/>
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

    @XmlElement(name = "VisualizzazioneDati", required = true)
    protected String visualizzazioneDati;
    @XmlElement(name = "VisualizzazioneFile", required = true)
    protected PrivilegioSuFileType visualizzazioneFile;
    @XmlElement(name = "ModificaDati", required = true)
    protected String modificaDati;
    @XmlElement(name = "ModificaFile", required = true)
    protected PrivilegioSuFileType modificaFile;
    @XmlElement(name = "ModificaPermessi", required = true)
    protected String modificaPermessi;
    @XmlElement(name = "Copia", required = true)
    protected String copia;
    @XmlElement(name = "Eliminazione", required = true)
    protected String eliminazione;
    @XmlElement(name = "Recupero", required = true)
    protected String recupero;
    @XmlElement(name = "AccessoLimitatoVerFilePubblicate", required = true)
    protected FileUDxPrivilegioType accessoLimitatoVerFilePubblicate;

    /**
     * Recupera il valore della proprietvisualizzazioneDati.
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
     * Imposta il valore della proprietvisualizzazioneDati.
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
     * Recupera il valore della proprietvisualizzazioneFile.
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
     * Imposta il valore della proprietvisualizzazioneFile.
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
     * Recupera il valore della proprietmodificaDati.
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
     * Imposta il valore della proprietmodificaDati.
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
     * Recupera il valore della proprietmodificaFile.
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
     * Imposta il valore della proprietmodificaFile.
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
     * Recupera il valore della proprietmodificaPermessi.
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
     * Imposta il valore della proprietmodificaPermessi.
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
     * Recupera il valore della proprietcopia.
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
     * Imposta il valore della proprietcopia.
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
     * Recupera il valore della proprieteliminazione.
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
     * Imposta il valore della proprieteliminazione.
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
     * Recupera il valore della proprietrecupero.
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
     * Imposta il valore della proprietrecupero.
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
     * Recupera il valore della proprietaccessoLimitatoVerFilePubblicate.
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
     * Imposta il valore della proprietaccessoLimitatoVerFilePubblicate.
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
