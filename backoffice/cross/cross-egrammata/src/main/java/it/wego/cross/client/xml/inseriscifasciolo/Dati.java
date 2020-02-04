//
// Questo filestato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andr� persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.07.24 alle 10:59:37 AM CEST 
//


package it.wego.cross.client.xml.inseriscifasciolo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}IdUoIn"/>
 *         &lt;element ref="{}FlgFSIn"/>
 *         &lt;element ref="{}IdFascicoloIn" minOccurs="0"/>
 *         &lt;element ref="{}IdTitolazioneIn" minOccurs="0"/>
 *         &lt;element ref="{}TxtOggIn"/>
 *         &lt;element ref="{}NoteIn" minOccurs="0"/>
 *         &lt;element ref="{}FlgRiservatezzaIn" minOccurs="0"/>
 *         &lt;element ref="{}IdUOInCaricoIn" minOccurs="0"/>
 *         &lt;element ref="{}IdFascicoloRifIn" minOccurs="0"/>
 *         &lt;element ref="{}AnnoFascRifIn" minOccurs="0"/>
 *         &lt;element ref="{}TitoloFascRifIn" minOccurs="0"/>
 *         &lt;element ref="{}ClasseFascRifIn" minOccurs="0"/>
 *         &lt;element ref="{}SottoClasseFascRifIn" minOccurs="0"/>
 *         &lt;element ref="{}Liv4In" minOccurs="0"/>
 *         &lt;element ref="{}Liv5In" minOccurs="0"/>
 *         &lt;element ref="{}ProgrFascRifIn" minOccurs="0"/>
 *         &lt;element ref="{}NumSottofascRifIn" minOccurs="0"/>
 *         &lt;element ref="{}MotiviRifIn" minOccurs="0"/>
 *         &lt;element ref="{}ParoleChiaveIn" minOccurs="0"/>
 *         &lt;element ref="{}vAttrOpzIn" minOccurs="0"/>
 *         &lt;element ref="{}FlgPropagaAclDoc" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "idUoIn",
    "flgFSIn",
    "idFascicoloIn",
    "idTitolazioneIn",
    "txtOggIn",
    "noteIn",
    "flgRiservatezzaIn",
    "idUOInCaricoIn",
    "idFascicoloRifIn",
    "annoFascRifIn",
    "titoloFascRifIn",
    "classeFascRifIn",
    "sottoClasseFascRifIn",
    "liv4In",
    "liv5In",
    "progrFascRifIn",
    "numSottofascRifIn",
    "motiviRifIn",
    "paroleChiaveIn",
    "vAttrOpzIn",
    "flgPropagaAclDoc"
})
@XmlRootElement(name = "Dati")
public class Dati {

    @XmlElement(name = "IdUoIn", required = true)
    protected String idUoIn;
    @XmlElement(name = "FlgFSIn", required = true)
    protected String flgFSIn;
    @XmlElement(name = "IdFascicoloIn")
    protected String idFascicoloIn;
    @XmlElement(name = "IdTitolazioneIn")
    protected String idTitolazioneIn;
    @XmlElement(name = "TxtOggIn", required = true)
    protected String txtOggIn;
    @XmlElement(name = "NoteIn")
    protected String noteIn;
    @XmlElement(name = "FlgRiservatezzaIn")
    protected String flgRiservatezzaIn;
    @XmlElement(name = "IdUOInCaricoIn")
    protected String idUOInCaricoIn;
    @XmlElement(name = "IdFascicoloRifIn")
    protected String idFascicoloRifIn;
    @XmlElement(name = "AnnoFascRifIn")
    protected String annoFascRifIn;
    @XmlElement(name = "TitoloFascRifIn")
    protected String titoloFascRifIn;
    @XmlElement(name = "ClasseFascRifIn")
    protected String classeFascRifIn;
    @XmlElement(name = "SottoClasseFascRifIn")
    protected String sottoClasseFascRifIn;
    @XmlElement(name = "Liv4In")
    protected String liv4In;
    @XmlElement(name = "Liv5In")
    protected String liv5In;
    @XmlElement(name = "ProgrFascRifIn")
    protected String progrFascRifIn;
    @XmlElement(name = "NumSottofascRifIn")
    protected String numSottofascRifIn;
    @XmlElement(name = "MotiviRifIn")
    protected String motiviRifIn;
    @XmlElement(name = "ParoleChiaveIn")
    protected String paroleChiaveIn;
    protected VAttrOpzIn vAttrOpzIn;
    @XmlElement(name = "FlgPropagaAclDoc")
    protected String flgPropagaAclDoc;

    /**
     * Recupera il valore della propriet� idUoIn.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdUoIn() {
        return idUoIn;
    }

    /**
     * Imposta il valore della propriet� idUoIn.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdUoIn(String value) {
        this.idUoIn = value;
    }

    /**
     * Recupera il valore della propriet� flgFSIn.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlgFSIn() {
        return flgFSIn;
    }

    /**
     * Imposta il valore della propriet� flgFSIn.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlgFSIn(String value) {
        this.flgFSIn = value;
    }

    /**
     * Recupera il valore della propriet� idFascicoloIn.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdFascicoloIn() {
        return idFascicoloIn;
    }

    /**
     * Imposta il valore della propriet� idFascicoloIn.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdFascicoloIn(String value) {
        this.idFascicoloIn = value;
    }

    /**
     * Recupera il valore della propriet� idTitolazioneIn.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdTitolazioneIn() {
        return idTitolazioneIn;
    }

    /**
     * Imposta il valore della propriet� idTitolazioneIn.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdTitolazioneIn(String value) {
        this.idTitolazioneIn = value;
    }

    /**
     * Recupera il valore della propriet� txtOggIn.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTxtOggIn() {
        return txtOggIn;
    }

    /**
     * Imposta il valore della propriet� txtOggIn.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTxtOggIn(String value) {
        this.txtOggIn = value;
    }

    /**
     * Recupera il valore della propriet� noteIn.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNoteIn() {
        return noteIn;
    }

    /**
     * Imposta il valore della propriet� noteIn.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNoteIn(String value) {
        this.noteIn = value;
    }

    /**
     * Recupera il valore della propriet� flgRiservatezzaIn.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlgRiservatezzaIn() {
        return flgRiservatezzaIn;
    }

    /**
     * Imposta il valore della propriet� flgRiservatezzaIn.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlgRiservatezzaIn(String value) {
        this.flgRiservatezzaIn = value;
    }

    /**
     * Recupera il valore della propriet� idUOInCaricoIn.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdUOInCaricoIn() {
        return idUOInCaricoIn;
    }

    /**
     * Imposta il valore della propriet� idUOInCaricoIn.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdUOInCaricoIn(String value) {
        this.idUOInCaricoIn = value;
    }

    /**
     * Recupera il valore della propriet� idFascicoloRifIn.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdFascicoloRifIn() {
        return idFascicoloRifIn;
    }

    /**
     * Imposta il valore della propriet� idFascicoloRifIn.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdFascicoloRifIn(String value) {
        this.idFascicoloRifIn = value;
    }

    /**
     * Recupera il valore della propriet� annoFascRifIn.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnnoFascRifIn() {
        return annoFascRifIn;
    }

    /**
     * Imposta il valore della propriet� annoFascRifIn.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnnoFascRifIn(String value) {
        this.annoFascRifIn = value;
    }

    /**
     * Recupera il valore della propriet� titoloFascRifIn.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitoloFascRifIn() {
        return titoloFascRifIn;
    }

    /**
     * Imposta il valore della propriet� titoloFascRifIn.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitoloFascRifIn(String value) {
        this.titoloFascRifIn = value;
    }

    /**
     * Recupera il valore della propriet� classeFascRifIn.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClasseFascRifIn() {
        return classeFascRifIn;
    }

    /**
     * Imposta il valore della propriet� classeFascRifIn.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClasseFascRifIn(String value) {
        this.classeFascRifIn = value;
    }

    /**
     * Recupera il valore della propriet� sottoClasseFascRifIn.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSottoClasseFascRifIn() {
        return sottoClasseFascRifIn;
    }

    /**
     * Imposta il valore della propriet� sottoClasseFascRifIn.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSottoClasseFascRifIn(String value) {
        this.sottoClasseFascRifIn = value;
    }

    /**
     * Recupera il valore della propriet� liv4In.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLiv4In() {
        return liv4In;
    }

    /**
     * Imposta il valore della propriet� liv4In.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLiv4In(String value) {
        this.liv4In = value;
    }

    /**
     * Recupera il valore della propriet� liv5In.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLiv5In() {
        return liv5In;
    }

    /**
     * Imposta il valore della propriet� liv5In.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLiv5In(String value) {
        this.liv5In = value;
    }

    /**
     * Recupera il valore della propriet� progrFascRifIn.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProgrFascRifIn() {
        return progrFascRifIn;
    }

    /**
     * Imposta il valore della propriet� progrFascRifIn.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProgrFascRifIn(String value) {
        this.progrFascRifIn = value;
    }

    /**
     * Recupera il valore della propriet� numSottofascRifIn.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumSottofascRifIn() {
        return numSottofascRifIn;
    }

    /**
     * Imposta il valore della propriet� numSottofascRifIn.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumSottofascRifIn(String value) {
        this.numSottofascRifIn = value;
    }

    /**
     * Recupera il valore della propriet� motiviRifIn.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMotiviRifIn() {
        return motiviRifIn;
    }

    /**
     * Imposta il valore della propriet� motiviRifIn.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMotiviRifIn(String value) {
        this.motiviRifIn = value;
    }

    /**
     * Recupera il valore della propriet� paroleChiaveIn.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParoleChiaveIn() {
        return paroleChiaveIn;
    }

    /**
     * Imposta il valore della propriet� paroleChiaveIn.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParoleChiaveIn(String value) {
        this.paroleChiaveIn = value;
    }

    /**
     * Recupera il valore della propriet� vAttrOpzIn.
     * 
     * @return
     *     possible object is
     *     {@link VAttrOpzIn }
     *     
     */
    public VAttrOpzIn getVAttrOpzIn() {
        return vAttrOpzIn;
    }

    /**
     * Imposta il valore della propriet� vAttrOpzIn.
     * 
     * @param value
     *     allowed object is
     *     {@link VAttrOpzIn }
     *     
     */
    public void setVAttrOpzIn(VAttrOpzIn value) {
        this.vAttrOpzIn = value;
    }

    /**
     * Recupera il valore della propriet� flgPropagaAclDoc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlgPropagaAclDoc() {
        return flgPropagaAclDoc;
    }

    /**
     * Imposta il valore della propriet� flgPropagaAclDoc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlgPropagaAclDoc(String value) {
        this.flgPropagaAclDoc = value;
    }

}
