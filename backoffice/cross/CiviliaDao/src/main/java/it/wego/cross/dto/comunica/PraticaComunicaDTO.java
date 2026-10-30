/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.comunica;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author giuseppe
 */
public class PraticaComunicaDTO implements Serializable {

    @Valid
    private UfficioDTO ufficio;
    @Valid
    private ImpresaDTO impresa;
    @Valid
    private ComuneRiferimentoDTO comuneRiferimento;
    @Valid
    private LegaleRappresentanteDTO legaleRappresentante;
    @Valid
    private DichiaranteDTO dichiarante;
    @Valid
    @NotNull(message = "error.comunica.allegati.dimensione")
    @Size(min = 1, message = "error.comunica.allegati.dimensione")
    private List<AllegatoComunicaDTO> allegati;
    @NotNull(message = "error.comunica.oggetto")
    private String oggetto;
    private String tipoProcedimento;
    private Integer idProcedimentoSuap;
    @NotNull(message = "error.comunica.allegati.modello")
    private Integer idAllegatoPratica;
    @Valid
    @NotNull(message = "error.comunica.interventi.dimensione")
    @Size(min = 1, message = "error.comunica.interventi.dimensione")
    private List<InterventoDTO> interventi;
    private String interventoOrigine;
    @NotNull(message = "error.comunica.dataricezione")
    private Date dataRicezionePratica;
    @Valid
    private ProtocolloDTO protocollo;
    @Valid
    private SportelloDTO sportello;

    public UfficioDTO getUfficio() {
        return ufficio;
    }

    public void setUfficio(UfficioDTO ufficio) {
        this.ufficio = ufficio;
    }

    public ImpresaDTO getImpresa() {
        return impresa;
    }

    public void setImpresa(ImpresaDTO impresa) {
        this.impresa = impresa;
    }

    public LegaleRappresentanteDTO getLegaleRappresentante() {
        return legaleRappresentante;
    }

    public void setLegaleRappresentante(LegaleRappresentanteDTO legaleRappresentante) {
        this.legaleRappresentante = legaleRappresentante;
    }

    public DichiaranteDTO getDichiarante() {
        return dichiarante;
    }

    public void setDichiarante(DichiaranteDTO dichiarante) {
        this.dichiarante = dichiarante;
    }

    public String getOggetto() {
        return oggetto;
    }

    public void setOggetto(String oggetto) {
        this.oggetto = oggetto;
    }

    public String getTipoProcedimento() {
        return tipoProcedimento;
    }

    public void setTipoProcedimento(String tipoProcedimento) {
        this.tipoProcedimento = tipoProcedimento;
    }

    public List<InterventoDTO> getInterventi() {
        return interventi;
    }

    public void setInterventi(List<InterventoDTO> interventi) {
        this.interventi = interventi;
    }

    public List<AllegatoComunicaDTO> getAllegati() {
        return allegati;
    }

    public void setAllegati(List<AllegatoComunicaDTO> allegati) {
        this.allegati = allegati;
    }

    public ProtocolloDTO getProtocollo() {
        return protocollo;
    }

    public void setProtocollo(ProtocolloDTO protocollo) {
        this.protocollo = protocollo;
    }

    public String getInterventoOrigine() {
        return interventoOrigine;
    }

    public void setInterventoOrigine(String interventoOrigine) {
        this.interventoOrigine = interventoOrigine;
    }

    public ComuneRiferimentoDTO getComuneRiferimento() {
        return comuneRiferimento;
    }

    public void setComuneRiferimento(ComuneRiferimentoDTO comuneRiferimento) {
        this.comuneRiferimento = comuneRiferimento;
    }

    public Date getDataRicezionePratica() {
        return dataRicezionePratica;
    }

    public void setDataRicezionePratica(Date dataRicezionePratica) {
        this.dataRicezionePratica = dataRicezionePratica;
    }

    public SportelloDTO getSportello() {
        return sportello;
    }

    public void setSportello(SportelloDTO sportello) {
        this.sportello = sportello;
    }

    public Integer getIdProcedimentoSuap() {
        return idProcedimentoSuap;
    }

    public void setIdProcedimentoSuap(Integer idProcedimentoSuap) {
        this.idProcedimentoSuap = idProcedimentoSuap;
    }

    public Integer getIdAllegatoPratica() {
        return idAllegatoPratica;
    }

    public void setIdAllegatoPratica(Integer idAllegatoPratica) {
        this.idAllegatoPratica = idAllegatoPratica;
    }
}
