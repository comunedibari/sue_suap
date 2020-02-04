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
 *         &lt;element ref="{}ID_FASCICOLO"/>
 *         &lt;element ref="{}NUM_SOTTOFASC"/>
 *         &lt;element ref="{}CODICE"/>
 *         &lt;element ref="{}DES_TITOLAZIONE"/>
 *         &lt;element ref="{}DT_APERTURA"/>
 *         &lt;element ref="{}DT_CHIUSURA"/>
 *         &lt;element ref="{}DT_ARCH"/>
 *         &lt;element ref="{}TXT_OGG"/>
 *         &lt;element ref="{}FLG_ANN"/>
 *         &lt;element ref="{}DT_VERSAMENTO"/>
 *         &lt;element ref="{}ID_FASCICOLO_RIF"/>
 *         &lt;element ref="{}NUM_SOTTOFASC_RIF"/>
 *         &lt;element ref="{}DEC_FASC_RIF"/>
 *         &lt;element ref="{}MOTIVI_RIF"/>
 *         &lt;element ref="{}PAROLE_CHIAVE"/>
 *         &lt;element ref="{}ANNO_FASC"/>
 *         &lt;element ref="{}NUM_FASC"/>
 *         &lt;element ref="{}TITOLO_CLASS"/>
 *         &lt;element ref="{}CLASSE_CLASS"/>
 *         &lt;element ref="{}SOTTOCLASSE_CLASS"/>
 *         &lt;element ref="{}LIVELLO4_CLASS"/>
 *         &lt;element ref="{}LIVELLO5_CLASS"/>
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
    "idfascicolo",
    "numsottofasc",
    "codice",
    "destitolazione",
    "dtapertura",
    "dtchiusura",
    "dtarch",
    "txtogg",
    "flgann",
    "dtversamento",
    "idfascicolorif",
    "numsottofascrif",
    "decfascrif",
    "motivirif",
    "parolechiave",
    "annofasc",
    "numfasc",
    "titoloclass",
    "classeclass",
    "sottoclasseclass",
    "livello4CLASS",
    "livello5CLASS"
})
@XmlRootElement(name = "Fascicolo")
public class Fascicolo {

    @XmlElement(name = "ID_FASCICOLO", required = true)
    protected String idfascicolo;
    @XmlElement(name = "NUM_SOTTOFASC", required = true)
    protected String numsottofasc;
    @XmlElement(name = "CODICE", required = true)
    protected String codice;
    @XmlElement(name = "DES_TITOLAZIONE", required = true)
    protected String destitolazione;
    @XmlElement(name = "DT_APERTURA", required = true)
    protected String dtapertura;
    @XmlElement(name = "DT_CHIUSURA", required = true)
    protected String dtchiusura;
    @XmlElement(name = "DT_ARCH", required = true)
    protected String dtarch;
    @XmlElement(name = "TXT_OGG", required = true)
    protected String txtogg;
    @XmlElement(name = "FLG_ANN", required = true)
    protected String flgann;
    @XmlElement(name = "DT_VERSAMENTO", required = true)
    protected String dtversamento;
    @XmlElement(name = "ID_FASCICOLO_RIF", required = true)
    protected String idfascicolorif;
    @XmlElement(name = "NUM_SOTTOFASC_RIF", required = true)
    protected String numsottofascrif;
    @XmlElement(name = "DEC_FASC_RIF", required = true)
    protected String decfascrif;
    @XmlElement(name = "MOTIVI_RIF", required = true)
    protected String motivirif;
    @XmlElement(name = "PAROLE_CHIAVE", required = true)
    protected String parolechiave;
    @XmlElement(name = "ANNO_FASC", required = true)
    protected String annofasc;
    @XmlElement(name = "NUM_FASC", required = true)
    protected String numfasc;
    @XmlElement(name = "TITOLO_CLASS", required = true)
    protected String titoloclass;
    @XmlElement(name = "CLASSE_CLASS", required = true)
    protected String classeclass;
    @XmlElement(name = "SOTTOCLASSE_CLASS", required = true)
    protected String sottoclasseclass;
    @XmlElement(name = "LIVELLO4_CLASS", required = true)
    protected String livello4CLASS;
    @XmlElement(name = "LIVELLO5_CLASS", required = true)
    protected String livello5CLASS;

    /**
     * Recupera il valore della propriet� idfascicolo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDFASCICOLO() {
        return idfascicolo;
    }

    /**
     * Imposta il valore della propriet� idfascicolo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDFASCICOLO(String value) {
        this.idfascicolo = value;
    }

    /**
     * Recupera il valore della propriet� numsottofasc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNUMSOTTOFASC() {
        return numsottofasc;
    }

    /**
     * Imposta il valore della propriet� numsottofasc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNUMSOTTOFASC(String value) {
        this.numsottofasc = value;
    }

    /**
     * Recupera il valore della propriet� codice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODICE() {
        return codice;
    }

    /**
     * Imposta il valore della propriet� codice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODICE(String value) {
        this.codice = value;
    }

    /**
     * Recupera il valore della propriet� destitolazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESTITOLAZIONE() {
        return destitolazione;
    }

    /**
     * Imposta il valore della propriet� destitolazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESTITOLAZIONE(String value) {
        this.destitolazione = value;
    }

    /**
     * Recupera il valore della propriet� dtapertura.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDTAPERTURA() {
        return dtapertura;
    }

    /**
     * Imposta il valore della propriet� dtapertura.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDTAPERTURA(String value) {
        this.dtapertura = value;
    }

    /**
     * Recupera il valore della propriet� dtchiusura.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDTCHIUSURA() {
        return dtchiusura;
    }

    /**
     * Imposta il valore della propriet� dtchiusura.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDTCHIUSURA(String value) {
        this.dtchiusura = value;
    }

    /**
     * Recupera il valore della propriet� dtarch.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDTARCH() {
        return dtarch;
    }

    /**
     * Imposta il valore della propriet� dtarch.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDTARCH(String value) {
        this.dtarch = value;
    }

    /**
     * Recupera il valore della propriet� txtogg.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTXTOGG() {
        return txtogg;
    }

    /**
     * Imposta il valore della propriet� txtogg.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTXTOGG(String value) {
        this.txtogg = value;
    }

    /**
     * Recupera il valore della propriet� flgann.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFLGANN() {
        return flgann;
    }

    /**
     * Imposta il valore della propriet� flgann.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFLGANN(String value) {
        this.flgann = value;
    }

    /**
     * Recupera il valore della propriet� dtversamento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDTVERSAMENTO() {
        return dtversamento;
    }

    /**
     * Imposta il valore della propriet� dtversamento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDTVERSAMENTO(String value) {
        this.dtversamento = value;
    }

    /**
     * Recupera il valore della propriet� idfascicolorif.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDFASCICOLORIF() {
        return idfascicolorif;
    }

    /**
     * Imposta il valore della propriet� idfascicolorif.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDFASCICOLORIF(String value) {
        this.idfascicolorif = value;
    }

    /**
     * Recupera il valore della propriet� numsottofascrif.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNUMSOTTOFASCRIF() {
        return numsottofascrif;
    }

    /**
     * Imposta il valore della propriet� numsottofascrif.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNUMSOTTOFASCRIF(String value) {
        this.numsottofascrif = value;
    }

    /**
     * Recupera il valore della propriet� decfascrif.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDECFASCRIF() {
        return decfascrif;
    }

    /**
     * Imposta il valore della propriet� decfascrif.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDECFASCRIF(String value) {
        this.decfascrif = value;
    }

    /**
     * Recupera il valore della propriet� motivirif.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMOTIVIRIF() {
        return motivirif;
    }

    /**
     * Imposta il valore della propriet� motivirif.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMOTIVIRIF(String value) {
        this.motivirif = value;
    }

    /**
     * Recupera il valore della propriet� parolechiave.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPAROLECHIAVE() {
        return parolechiave;
    }

    /**
     * Imposta il valore della propriet� parolechiave.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPAROLECHIAVE(String value) {
        this.parolechiave = value;
    }

    /**
     * Recupera il valore della propriet� annofasc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getANNOFASC() {
        return annofasc;
    }

    /**
     * Imposta il valore della propriet� annofasc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setANNOFASC(String value) {
        this.annofasc = value;
    }

    /**
     * Recupera il valore della propriet� numfasc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNUMFASC() {
        return numfasc;
    }

    /**
     * Imposta il valore della propriet� numfasc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNUMFASC(String value) {
        this.numfasc = value;
    }

    /**
     * Recupera il valore della propriet� titoloclass.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTITOLOCLASS() {
        return titoloclass;
    }

    /**
     * Imposta il valore della propriet� titoloclass.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTITOLOCLASS(String value) {
        this.titoloclass = value;
    }

    /**
     * Recupera il valore della propriet� classeclass.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCLASSECLASS() {
        return classeclass;
    }

    /**
     * Imposta il valore della propriet� classeclass.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCLASSECLASS(String value) {
        this.classeclass = value;
    }

    /**
     * Recupera il valore della propriet� sottoclasseclass.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSOTTOCLASSECLASS() {
        return sottoclasseclass;
    }

    /**
     * Imposta il valore della propriet� sottoclasseclass.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSOTTOCLASSECLASS(String value) {
        this.sottoclasseclass = value;
    }

    /**
     * Recupera il valore della propriet� livello4CLASS.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLIVELLO4CLASS() {
        return livello4CLASS;
    }

    /**
     * Imposta il valore della propriet� livello4CLASS.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLIVELLO4CLASS(String value) {
        this.livello4CLASS = value;
    }

    /**
     * Recupera il valore della propriet� livello5CLASS.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLIVELLO5CLASS() {
        return livello5CLASS;
    }

    /**
     * Imposta il valore della propriet� livello5CLASS.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLIVELLO5CLASS(String value) {
        this.livello5CLASS = value;
    }

}
