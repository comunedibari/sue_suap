package it.wego.cross.dto;

import it.wego.cross.dto.dozer.DatiCatastaliDTO;
import it.wego.cross.dto.dozer.IndirizzoInterventoDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Pratica ver 2.0 unione di PraticaDati, PraticaManuale di cross 2.3
 *
 * @author CS
 */
public class PraticaDTO extends AbstractDTO implements Serializable {

    @NotNull(message = "{error.idPratica}")
    private Integer idPratica;
    @NotNull(message = "{error.codPratica}")
    private Integer codPratica;
    @NotNull(message = "{error.codEnte}")
    private String codEnte;//idEnte nel xml
    @NotNull(message = "{error.codUtente}")
    private Integer codUtente;
    @NotNull(message = "{error.identificativoPratica}")
    @NotEmpty(message = "{error.identificativoPratica}")
    private String identificativoPratica;
    @NotNull(message = "{error.oggettoPratica}")
    @NotEmpty(message = "{error.oggettoPratica}")
    private String oggettoPratica;
    @NotNull(message = "{error.protocolloManualeNUll}")
    @NotEmpty(message = "{error.protocolloManualeNUll}")
    private String protocollo;
    @NotNull(message = "{error.responsabileProcedimento}")
    @NotEmpty(message = "{error.responsabileProcedimento}")
    private String responsabileProcedimento;
    private Date dataApertura;
    private Date dataChiusura;
    private Date dataProtocollo;
    @NotNull(message = "{error.dataRicezioneNull}")
    @Past(message = "{error.dataRicezionePast}")
    private Date dataRicezione;
    @NotNull(message = "{error.idProcesso}")
    private Integer idProcesso;
    @NotNull(message = "{error.codStaging}")
    private Integer codStaging;
    @NotNull(message = "{error.codRecapito}")
    private Integer codRecapito;
    private String stato;
    private String tipoMessaggio;
    private Boolean allegatoPratica;
    @Valid
    private List<ProcedimentoDTO> procedimentiList = new ArrayList<ProcedimentoDTO>();
    @Valid
    private List<AnagraficaDTO> anagraficaList;
    @Valid
    private List<RelPraticaAnagraficheDTO> relPraticaAnagraficheList;
    @Valid
    private List<DatiCatastaliDTO> datiCatastali;
    @Valid
    private List<IndirizzoInterventoDTO> indirizziIntervento;
    @Valid
    private List<AllegatoDTO> allegatiList;
    @Valid
    private AllegatoDTO riepilogoPratica;
    @Valid
    private RecapitoDTO notifica;
    @Valid
    private ComuneDTO comune;
    private Integer IdEnte;
    private Integer IdComune;
    @NotNull(message = "{error.annoRiferimento}")
    private Integer annoRiferimento;
    @NotNull(message = "{error.codRegistro}")
    @NotEmpty(message = "{error.codRegistro}")
    private String registro; //^^CS AGGIUNTA
    @NotNull(message = "{error.fascicolo}")
    @NotEmpty(message = "{error.fascicolo}")
    private String fascicolo; //^^CS AGGUNTA    
    private Integer idPraticaProtocollo;
    private ProcedimentoDTO procedimentoPratica;
    private String esistenzaStradario;
    private String esistenzaRicercaCatasto;
    private String identificativoEsterno;

    public String getEsistenzaRicercaCatasto() {
        return esistenzaRicercaCatasto;
    }

    public void setEsistenzaRicercaCatasto(String esistenzaRicercaCatasto) {
        this.esistenzaRicercaCatasto = esistenzaRicercaCatasto;
    }

    public String getEsistenzaStradario() {
        return esistenzaStradario;
    }

    public void setEsistenzaStradario(String esistenzaStradario) {
        this.esistenzaStradario = esistenzaStradario;
    }

    public List<AnagraficaDTO> getAnagraficaList() {
        return anagraficaList;
    }

    public void setAnagraficaList(List<AnagraficaDTO> AnagraficaList) {
        this.anagraficaList = AnagraficaList;
    }

    public String getCodEnte() {
        return codEnte;
    }

    public void setCodEnte(String codEnte) {
        this.codEnte = codEnte;
    }

    public Integer getCodPratica() {
        return codPratica;
    }

    public void setCodPratica(Integer codPratica) {
        this.codPratica = codPratica;
    }

    public Integer getCodRecapito() {
        return codRecapito;
    }

    public void setCodRecapito(Integer codRecapito) {
        this.codRecapito = codRecapito;
    }

    public Integer getCodStaging() {
        return codStaging;
    }

    public void setCodStaging(Integer codStaging) {
        this.codStaging = codStaging;
    }

    public Integer getCodUtente() {
        return codUtente;
    }

    public void setCodUtente(Integer codUtente) {
        this.codUtente = codUtente;
    }

    public Date getDataApertura() {
        return dataApertura;
    }

    public void setDataApertura(Date dataApertura) {
        this.dataApertura = dataApertura;
    }

    public Date getDataChiusura() {
        return dataChiusura;
    }

    public void setDataChiusura(Date dataChiusura) {
        this.dataChiusura = dataChiusura;
    }

    public Date getDataRicezione() {
        return dataRicezione;
    }

    public void setDataRicezione(Date dataRicezione) {
        this.dataRicezione = dataRicezione;
    }

    public Date getDataProtocollo() {
        return dataProtocollo;
    }

    public void setDataProtocollo(Date dataProtocollo) {
        this.dataProtocollo = dataProtocollo;
    }

    public Integer getIdProcesso() {
        return idProcesso;
    }

    public void setIdProcesso(Integer idProcesso) {
        this.idProcesso = idProcesso;
    }

    public String getIdentificativoPratica() {
        return identificativoPratica;
    }

    public void setIdentificativoPratica(String identificativoPratica) {
        this.identificativoPratica = identificativoPratica;
    }

    public String getOggettoPratica() {
        return oggettoPratica;
    }

    public void setOggettoPratica(String oggettoPratica) {
        this.oggettoPratica = oggettoPratica;
    }

    public List<ProcedimentoDTO> getProcedimentiList() {
        return procedimentiList;
    }

    public void setProcedimentiList(List<ProcedimentoDTO> procedimentiList) {
        this.procedimentiList = procedimentiList;
    }

    public String getProtocollo() {
        return protocollo;
    }

    public void setProtocollo(String protocollo) {
        this.protocollo = protocollo;
    }

    public List<RelPraticaAnagraficheDTO> getRelPraticaAnagraficheList() {
        return relPraticaAnagraficheList;
    }

    public void setRelPraticaAnagraficheList(List<RelPraticaAnagraficheDTO> relPraticaAnagraficheList) {
        this.relPraticaAnagraficheList = relPraticaAnagraficheList;
    }

    public Integer getIdPratica() {
        return idPratica;
    }

    public void setIdPratica(Integer idPratica) {
        this.idPratica = idPratica;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public Boolean getAllegatoPratica() {
        return allegatoPratica;
    }

    public void setAllegatoPratica(Boolean allegatoPratica) {
        this.allegatoPratica = allegatoPratica;
    }

    public List<DatiCatastaliDTO> getDatiCatastali() {
        return datiCatastali;
    }

    public void setDatiCatastali(List<DatiCatastaliDTO> datiCatastali) {
        this.datiCatastali = datiCatastali;
    }

    public List<IndirizzoInterventoDTO> getIndirizziIntervento() {
        return indirizziIntervento;
    }

    public void setIndirizziIntervento(List<IndirizzoInterventoDTO> indirizziIntervento) {
        this.indirizziIntervento = indirizziIntervento;
    }

    public RecapitoDTO getNotifica() {
        return notifica;
    }

    public void setNotifica(RecapitoDTO notifica) {
        this.notifica = notifica;
    }

    public List<AllegatoDTO> getAllegatiList() {
        return allegatiList;
    }

    public void setAllegatiList(List<AllegatoDTO> allegatiList) {
        this.allegatiList = allegatiList;
    }

    public ComuneDTO getComune() {
        return comune;
    }

    public void setComune(ComuneDTO comune) {
        this.comune = comune;
    }

    public AllegatoDTO getRiepilogoPratica() {
        return riepilogoPratica;
    }

    public void setRiepilogoPratica(AllegatoDTO riepilogoPratica) {
        this.riepilogoPratica = riepilogoPratica;
    }

    public String getTipoMessaggio() {
        return tipoMessaggio;
    }

    public void setTipoMessaggio(String tipoMessaggio) {
        this.tipoMessaggio = tipoMessaggio;
    }

    public String getResponsabileProcedimento() {
        return responsabileProcedimento;
    }

    public void setResponsabileProcedimento(String responsabileProcedimento) {
        this.responsabileProcedimento = responsabileProcedimento;
    }

    public Integer getIdEnte() {
        return IdEnte;
    }

    public void setIdEnte(Integer IdEnte) {
        this.IdEnte = IdEnte;
    }

    public Integer getIdComune() {
        return IdComune;
    }

    public void setIdComune(Integer IdComune) {
        this.IdComune = IdComune;
    }

    public Integer getAnnoRiferimento() {
        return annoRiferimento;
    }

    public void setAnnoRiferimento(Integer annoRiferimento) {
        this.annoRiferimento = annoRiferimento;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public String getFascicolo() {
        return fascicolo;
    }

    public void setFascicolo(String fascicolo) {
        this.fascicolo = fascicolo;
    }

    public Integer getIdPraticaProtocollo() {
        return idPraticaProtocollo;
    }

    public void setIdPraticaProtocollo(Integer idPraticaProtocollo) {
        this.idPraticaProtocollo = idPraticaProtocollo;
    }

    public ProcedimentoDTO getProcedimentoPratica() {
        return procedimentoPratica;
    }

    public void setProcedimentoPratica(ProcedimentoDTO procedimentoPratica) {
        this.procedimentoPratica = procedimentoPratica;
    }

    public String getIdentificativoEsterno() {
        return identificativoEsterno;
    }

    public void setIdentificativoEsterno(String identificativoEsterno) {
        this.identificativoEsterno = identificativoEsterno;
    }
}
