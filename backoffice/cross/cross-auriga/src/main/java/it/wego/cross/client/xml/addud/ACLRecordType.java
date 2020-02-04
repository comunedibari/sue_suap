//
// Questo file stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrpersa durante la ricompilazione dello schema di origine. 
// Generato il: 2013.03.28 alle 03:45:54 PM CET 
//


package it.wego.cross.client.xml.addud;

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
