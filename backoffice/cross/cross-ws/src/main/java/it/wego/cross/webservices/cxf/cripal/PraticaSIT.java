
package it.wego.cross.webservices.cxf.cripal;

import it.wego.cross.webservices.cxf.*;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per praticaSIT complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
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
     * Recupera il valore della propriet idPratica.
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
     * Imposta il valore della propriet idPratica.
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
     * Recupera il valore della propriet operazione.
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
     * Imposta il valore della propriet operazione.
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
     * Recupera il valore della propriet identificativoPratica.
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
     * Imposta il valore della propriet identificativoPratica.
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
     * Recupera il valore della propriet idSportello.
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
     * Imposta il valore della propriet idSportello.
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
     * Recupera il valore della propriet desSportello.
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
     * Imposta il valore della propriet desSportello.
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
     * Recupera il valore della propriet idProcedimentoSuap.
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
     * Imposta il valore della propriet idProcedimentoSuap.
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
     * Recupera il valore della propriet desProcedimentoSuap.
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
     * Imposta il valore della propriet desProcedimentoSuap.
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
     * Recupera il valore della propriet oggetto.
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
     * Imposta il valore della propriet oggetto.
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
     * Recupera il valore della propriet responsabileProcedimento.
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
     * Imposta il valore della propriet responsabileProcedimento.
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
     * Recupera il valore della propriet istruttore.
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
     * Imposta il valore della propriet istruttore.
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
     * Recupera il valore della propriet codCatastaleComune.
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
     * Imposta il valore della propriet codCatastaleComune.
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
     * Recupera il valore della propriet desComune.
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
     * Imposta il valore della propriet desComune.
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
     * Recupera il valore della propriet segnaturaProtocollo.
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
     * Imposta il valore della propriet segnaturaProtocollo.
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
     * Recupera il valore della propriet dataProtocollo.
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
     * Imposta il valore della propriet dataProtocollo.
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
     * Recupera il valore della propriet dataRicezione.
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
     * Imposta il valore della propriet dataRicezione.
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
     * Recupera il valore della propriet anagraficheSIT.
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
     * Imposta il valore della propriet anagraficheSIT.
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
     * Recupera il valore della propriet datiCatastali.
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
     * Imposta il valore della propriet datiCatastali.
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
     * Recupera il valore della propriet indirizziInterventoSIT.
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
     * Imposta il valore della propriet indirizziInterventoSIT.
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
     * Recupera il valore della propriet procedimentiSIT.
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
     * Imposta il valore della propriet procedimentiSIT.
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
     * Recupera il valore della propriet codStatoPratica.
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
     * Imposta il valore della propriet codStatoPratica.
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
     * Recupera il valore della propriet desStatoPratica.
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
     * Imposta il valore della propriet desStatoPratica.
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
     * Recupera il valore della propriet dataChiusura.
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
     * Imposta il valore della propriet dataChiusura.
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
     * Recupera il valore della propriet dataInizioLavori.
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
     * Imposta il valore della propriet dataInizioLavori.
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
     * Recupera il valore della propriet dataFineLavoriPresunta.
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
     * Imposta il valore della propriet dataFineLavoriPresunta.
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
     * Recupera il valore della propriet dataFineLavori.
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
     * Imposta il valore della propriet dataFineLavori.
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
     * Recupera il valore della propriet flgSanatoria.
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
     * Imposta il valore della propriet flgSanatoria.
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
     * Recupera il valore della propriet flgDeroga.
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
     * Imposta il valore della propriet flgDeroga.
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
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
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
         * Recupera il valore della propriet registro.
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
         * Imposta il valore della propriet registro.
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
         * Recupera il valore della propriet anno.
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
         * Imposta il valore della propriet anno.
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
         * Recupera il valore della propriet protocollo.
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
         * Imposta il valore della propriet protocollo.
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
