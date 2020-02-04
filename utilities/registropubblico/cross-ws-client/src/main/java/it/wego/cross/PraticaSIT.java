
package it.wego.cross;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for praticaSIT complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="praticaSIT">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id_pratica" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="operazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="identificativo_pratica" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="id_sportello" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="des_sportello" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="id_procedimento_suap" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="des_procedimento_suap" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="oggetto" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="responsabile_procedimento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="istruttore" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cod_catastale_comune" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="des_comune" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="segnatura_protocollo">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="registro" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="anno" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="protocollo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="data_protocollo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="data_ricezione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="anagraficheSIT" type="{http://www.wego.it/cross}anagraficheSIT"/>
 *         &lt;element name="dati_catastali" type="{http://www.wego.it/cross}dati_catastaliSIT"/>
 *         &lt;element name="indirizzi_interventoSIT" type="{http://www.wego.it/cross}indirizzi_interventoSIT"/>
 *         &lt;element name="procedimentiSIT" type="{http://www.wego.it/cross}procedimentiSIT"/>
 *         &lt;element name="cod_stato_pratica" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="des_stato_pratica" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="data_chiusura" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="data_inizio_lavori" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="data_fine_lavori_presunta" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="data_fine_lavori" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="flg_sanatoria" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="flg_deroga" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "praticaSIT", propOrder = {
    "idPratica",
    "operazione",
    "identificativoPratica",
    "idSportello",
    "desSportello",
    "idProcedimentoSuap",
    "desProcedimentoSuap",
    "oggetto",
    "responsabileProcedimento",
    "istruttore",
    "codCatastaleComune",
    "desComune",
    "segnaturaProtocollo",
    "dataProtocollo",
    "dataRicezione",
    "anagraficheSIT",
    "datiCatastali",
    "indirizziInterventoSIT",
    "procedimentiSIT",
    "codStatoPratica",
    "desStatoPratica",
    "dataChiusura",
    "dataInizioLavori",
    "dataFineLavoriPresunta",
    "dataFineLavori",
    "flgSanatoria",
    "flgDeroga"
})
public class PraticaSIT {

    @XmlElement(name = "id_pratica", required = true)
    protected BigInteger idPratica;
    @XmlElement(required = true)
    protected String operazione;
    @XmlElement(name = "identificativo_pratica", required = true)
    protected String identificativoPratica;
    @XmlElement(name = "id_sportello", required = true)
    protected String idSportello;
    @XmlElement(name = "des_sportello", required = true)
    protected String desSportello;
    @XmlElement(name = "id_procedimento_suap", required = true)
    protected String idProcedimentoSuap;
    @XmlElement(name = "des_procedimento_suap", required = true)
    protected String desProcedimentoSuap;
    @XmlElement(required = true)
    protected String oggetto;
    @XmlElement(name = "responsabile_procedimento", required = true)
    protected String responsabileProcedimento;
    @XmlElement(required = true)
    protected String istruttore;
    @XmlElement(name = "cod_catastale_comune", required = true)
    protected String codCatastaleComune;
    @XmlElement(name = "des_comune", required = true)
    protected String desComune;
    @XmlElement(name = "segnatura_protocollo", required = true)
    protected PraticaSIT.SegnaturaProtocollo segnaturaProtocollo;
    @XmlElement(name = "data_protocollo", required = true)
    protected String dataProtocollo;
    @XmlElement(name = "data_ricezione", required = true)
    protected String dataRicezione;
    @XmlElement(required = true)
    protected AnagraficheSIT anagraficheSIT;
    @XmlElement(name = "dati_catastali", required = true)
    protected DatiCatastaliSIT datiCatastali;
    @XmlElement(name = "indirizzi_interventoSIT", required = true)
    protected IndirizziInterventoSIT indirizziInterventoSIT;
    @XmlElement(required = true)
    protected ProcedimentiSIT procedimentiSIT;
    @XmlElement(name = "cod_stato_pratica", required = true)
    protected String codStatoPratica;
    @XmlElement(name = "des_stato_pratica", required = true)
    protected String desStatoPratica;
    @XmlElement(name = "data_chiusura", required = true)
    protected String dataChiusura;
    @XmlElement(name = "data_inizio_lavori", required = true)
    protected String dataInizioLavori;
    @XmlElement(name = "data_fine_lavori_presunta", required = true)
    protected String dataFineLavoriPresunta;
    @XmlElement(name = "data_fine_lavori", required = true)
    protected String dataFineLavori;
    @XmlElement(name = "flg_sanatoria", required = true)
    protected String flgSanatoria;
    @XmlElement(name = "flg_deroga", required = true)
    protected String flgDeroga;

    /**
     * Gets the value of the idPratica property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdPratica() {
        return idPratica;
    }

    /**
     * Sets the value of the idPratica property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdPratica(BigInteger value) {
        this.idPratica = value;
    }

    /**
     * Gets the value of the operazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperazione() {
        return operazione;
    }

    /**
     * Sets the value of the operazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperazione(String value) {
        this.operazione = value;
    }

    /**
     * Gets the value of the identificativoPratica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoPratica() {
        return identificativoPratica;
    }

    /**
     * Sets the value of the identificativoPratica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoPratica(String value) {
        this.identificativoPratica = value;
    }

    /**
     * Gets the value of the idSportello property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdSportello() {
        return idSportello;
    }

    /**
     * Sets the value of the idSportello property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdSportello(String value) {
        this.idSportello = value;
    }

    /**
     * Gets the value of the desSportello property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesSportello() {
        return desSportello;
    }

    /**
     * Sets the value of the desSportello property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesSportello(String value) {
        this.desSportello = value;
    }

    /**
     * Gets the value of the idProcedimentoSuap property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdProcedimentoSuap() {
        return idProcedimentoSuap;
    }

    /**
     * Sets the value of the idProcedimentoSuap property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdProcedimentoSuap(String value) {
        this.idProcedimentoSuap = value;
    }

    /**
     * Gets the value of the desProcedimentoSuap property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesProcedimentoSuap() {
        return desProcedimentoSuap;
    }

    /**
     * Sets the value of the desProcedimentoSuap property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesProcedimentoSuap(String value) {
        this.desProcedimentoSuap = value;
    }

    /**
     * Gets the value of the oggetto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOggetto() {
        return oggetto;
    }

    /**
     * Sets the value of the oggetto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOggetto(String value) {
        this.oggetto = value;
    }

    /**
     * Gets the value of the responsabileProcedimento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResponsabileProcedimento() {
        return responsabileProcedimento;
    }

    /**
     * Sets the value of the responsabileProcedimento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResponsabileProcedimento(String value) {
        this.responsabileProcedimento = value;
    }

    /**
     * Gets the value of the istruttore property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIstruttore() {
        return istruttore;
    }

    /**
     * Sets the value of the istruttore property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIstruttore(String value) {
        this.istruttore = value;
    }

    /**
     * Gets the value of the codCatastaleComune property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodCatastaleComune() {
        return codCatastaleComune;
    }

    /**
     * Sets the value of the codCatastaleComune property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodCatastaleComune(String value) {
        this.codCatastaleComune = value;
    }

    /**
     * Gets the value of the desComune property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesComune() {
        return desComune;
    }

    /**
     * Sets the value of the desComune property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesComune(String value) {
        this.desComune = value;
    }

    /**
     * Gets the value of the segnaturaProtocollo property.
     * 
     * @return
     *     possible object is
     *     {@link PraticaSIT.SegnaturaProtocollo }
     *     
     */
    public PraticaSIT.SegnaturaProtocollo getSegnaturaProtocollo() {
        return segnaturaProtocollo;
    }

    /**
     * Sets the value of the segnaturaProtocollo property.
     * 
     * @param value
     *     allowed object is
     *     {@link PraticaSIT.SegnaturaProtocollo }
     *     
     */
    public void setSegnaturaProtocollo(PraticaSIT.SegnaturaProtocollo value) {
        this.segnaturaProtocollo = value;
    }

    /**
     * Gets the value of the dataProtocollo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataProtocollo() {
        return dataProtocollo;
    }

    /**
     * Sets the value of the dataProtocollo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataProtocollo(String value) {
        this.dataProtocollo = value;
    }

    /**
     * Gets the value of the dataRicezione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataRicezione() {
        return dataRicezione;
    }

    /**
     * Sets the value of the dataRicezione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataRicezione(String value) {
        this.dataRicezione = value;
    }

    /**
     * Gets the value of the anagraficheSIT property.
     * 
     * @return
     *     possible object is
     *     {@link AnagraficheSIT }
     *     
     */
    public AnagraficheSIT getAnagraficheSIT() {
        return anagraficheSIT;
    }

    /**
     * Sets the value of the anagraficheSIT property.
     * 
     * @param value
     *     allowed object is
     *     {@link AnagraficheSIT }
     *     
     */
    public void setAnagraficheSIT(AnagraficheSIT value) {
        this.anagraficheSIT = value;
    }

    /**
     * Gets the value of the datiCatastali property.
     * 
     * @return
     *     possible object is
     *     {@link DatiCatastaliSIT }
     *     
     */
    public DatiCatastaliSIT getDatiCatastali() {
        return datiCatastali;
    }

    /**
     * Sets the value of the datiCatastali property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiCatastaliSIT }
     *     
     */
    public void setDatiCatastali(DatiCatastaliSIT value) {
        this.datiCatastali = value;
    }

    /**
     * Gets the value of the indirizziInterventoSIT property.
     * 
     * @return
     *     possible object is
     *     {@link IndirizziInterventoSIT }
     *     
     */
    public IndirizziInterventoSIT getIndirizziInterventoSIT() {
        return indirizziInterventoSIT;
    }

    /**
     * Sets the value of the indirizziInterventoSIT property.
     * 
     * @param value
     *     allowed object is
     *     {@link IndirizziInterventoSIT }
     *     
     */
    public void setIndirizziInterventoSIT(IndirizziInterventoSIT value) {
        this.indirizziInterventoSIT = value;
    }

    /**
     * Gets the value of the procedimentiSIT property.
     * 
     * @return
     *     possible object is
     *     {@link ProcedimentiSIT }
     *     
     */
    public ProcedimentiSIT getProcedimentiSIT() {
        return procedimentiSIT;
    }

    /**
     * Sets the value of the procedimentiSIT property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProcedimentiSIT }
     *     
     */
    public void setProcedimentiSIT(ProcedimentiSIT value) {
        this.procedimentiSIT = value;
    }

    /**
     * Gets the value of the codStatoPratica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodStatoPratica() {
        return codStatoPratica;
    }

    /**
     * Sets the value of the codStatoPratica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodStatoPratica(String value) {
        this.codStatoPratica = value;
    }

    /**
     * Gets the value of the desStatoPratica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesStatoPratica() {
        return desStatoPratica;
    }

    /**
     * Sets the value of the desStatoPratica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesStatoPratica(String value) {
        this.desStatoPratica = value;
    }

    /**
     * Gets the value of the dataChiusura property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataChiusura() {
        return dataChiusura;
    }

    /**
     * Sets the value of the dataChiusura property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataChiusura(String value) {
        this.dataChiusura = value;
    }

    /**
     * Gets the value of the dataInizioLavori property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataInizioLavori() {
        return dataInizioLavori;
    }

    /**
     * Sets the value of the dataInizioLavori property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataInizioLavori(String value) {
        this.dataInizioLavori = value;
    }

    /**
     * Gets the value of the dataFineLavoriPresunta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataFineLavoriPresunta() {
        return dataFineLavoriPresunta;
    }

    /**
     * Sets the value of the dataFineLavoriPresunta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataFineLavoriPresunta(String value) {
        this.dataFineLavoriPresunta = value;
    }

    /**
     * Gets the value of the dataFineLavori property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataFineLavori() {
        return dataFineLavori;
    }

    /**
     * Sets the value of the dataFineLavori property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataFineLavori(String value) {
        this.dataFineLavori = value;
    }

    /**
     * Gets the value of the flgSanatoria property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlgSanatoria() {
        return flgSanatoria;
    }

    /**
     * Sets the value of the flgSanatoria property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlgSanatoria(String value) {
        this.flgSanatoria = value;
    }

    /**
     * Gets the value of the flgDeroga property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlgDeroga() {
        return flgDeroga;
    }

    /**
     * Sets the value of the flgDeroga property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlgDeroga(String value) {
        this.flgDeroga = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="registro" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="anno" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="protocollo" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "registro",
        "anno",
        "protocollo"
    })
    public static class SegnaturaProtocollo {

        @XmlElement(required = true)
        protected String registro;
        @XmlElement(required = true)
        protected String anno;
        @XmlElement(required = true)
        protected String protocollo;

        /**
         * Gets the value of the registro property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRegistro() {
            return registro;
        }

        /**
         * Sets the value of the registro property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRegistro(String value) {
            this.registro = value;
        }

        /**
         * Gets the value of the anno property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAnno() {
            return anno;
        }

        /**
         * Sets the value of the anno property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAnno(String value) {
            this.anno = value;
        }

        /**
         * Gets the value of the protocollo property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getProtocollo() {
            return protocollo;
        }

        /**
         * Sets the value of the protocollo property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setProtocollo(String value) {
            this.protocollo = value;
        }

    }

}
