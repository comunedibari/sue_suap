/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto;

import it.wego.cross.dto.dozer.ProcessoEventoAnagraficaDTO;
import it.wego.cross.dto.dozer.ProcessoEventoEnteDTO;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author giuseppe
 */
public class ProcessoEventoDTO implements Serializable {

    private Integer idEvento;
    private Integer idProcesso;
    private String codiceEvento;
    private String descrizioneEvento;
    private Integer statoPost;
    private String tipoMittente;
    private String tipoDestinatario;
    private String scriptScadenzaEvento;
    private String verso;
    private String flgPortale;
    private String flgMail;
    private String flgAllegatiEmail;
    private String flgProtocollazione;
    private String flgRicevuta;
    private String flgDestinatari;
    private String flgFirmato;
    private String flgApriSottoPratica;
    private String flgDestinatariSoloEnti;
    private String flgVisualizzaProcedimenti;
    private String flgAutomatico;
    private Integer idProcedimentoRiferimento;
    private String scriptEvento;
    private String scriptProtocollo;
    private Integer maxDestinatari;
    private String oggettoMail;
    private String corpoMail;
    private String funzioneApplicativa;
    private String forzaChiusuraScadenze;
    private List<ScadenzaEventoDTO> scadenze;
    private List<ProcessoEventoAnagraficaDTO> anagraficaDestinatari;
    private List<ProcessoEventoEnteDTO> enteDestinatari;

    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public Integer getIdProcesso() {
        return idProcesso;
    }

    public void setIdProcesso(Integer idProcesso) {
        this.idProcesso = idProcesso;
    }

    public String getCodiceEvento() {
        return codiceEvento;
    }

    public void setCodiceEvento(String codiceEvento) {
        this.codiceEvento = codiceEvento;
    }

    public String getDescrizioneEvento() {
        return descrizioneEvento;
    }

    public void setDescrizioneEvento(String descrizioneEvento) {
        this.descrizioneEvento = descrizioneEvento;
    }

    public Integer getStatoPost() {
        return statoPost;
    }

    public void setStatoPost(Integer statoPost) {
        this.statoPost = statoPost;
    }

    public String getTipoMittente() {
        return tipoMittente;
    }

    public void setTipoMittente(String tipoMittente) {
        this.tipoMittente = tipoMittente;
    }

    public String getTipoDestinatario() {
        return tipoDestinatario;
    }

    public void setTipoDestinatario(String tipoDestinatario) {
        this.tipoDestinatario = tipoDestinatario;
    }

    public String getScriptScadenzaEvento() {
        return scriptScadenzaEvento;
    }

    public void setScriptScadenzaEvento(String scriptScadenzaEvento) {
        this.scriptScadenzaEvento = scriptScadenzaEvento;
    }

    public String getVerso() {
        return verso;
    }

    public void setVerso(String verso) {
        this.verso = verso;
    }

    public String getFlgPortale() {
        return flgPortale;
    }

    public void setFlgPortale(String flgPortale) {
        this.flgPortale = flgPortale;
    }

    public String getFlgMail() {
        return flgMail;
    }

    public void setFlgMail(String flgMail) {
        this.flgMail = flgMail;
    }

    public String getFlgAllegatiEmail() {
        return flgAllegatiEmail;
    }

    public void setFlgAllegatiEmail(String flgAllegatiEmail) {
        this.flgAllegatiEmail = flgAllegatiEmail;
    }

    public String getFlgProtocollazione() {
        return flgProtocollazione;
    }

    public void setFlgProtocollazione(String flgProtocollazione) {
        this.flgProtocollazione = flgProtocollazione;
    }

    public String getFlgRicevuta() {
        return flgRicevuta;
    }

    public void setFlgRicevuta(String flgRicevuta) {
        this.flgRicevuta = flgRicevuta;
    }

    public String getFlgDestinatari() {
        return flgDestinatari;
    }

    public void setFlgDestinatari(String flgDestinatari) {
        this.flgDestinatari = flgDestinatari;
    }

    public String getFlgFirmato() {
        return flgFirmato;
    }

    public void setFlgFirmato(String flgFirmato) {
        this.flgFirmato = flgFirmato;
    }

    public String getFlgApriSottoPratica() {
        return flgApriSottoPratica;
    }

    public void setFlgApriSottoPratica(String flgApriSottoPratica) {
        this.flgApriSottoPratica = flgApriSottoPratica;
    }

    public String getFlgDestinatariSoloEnti() {
        return flgDestinatariSoloEnti;
    }

    public void setFlgDestinatariSoloEnti(String flgDestinatariSoloEnti) {
        this.flgDestinatariSoloEnti = flgDestinatariSoloEnti;
    }

    public String getFlgVisualizzaProcedimenti() {
        return flgVisualizzaProcedimenti;
    }

    public void setFlgVisualizzaProcedimenti(String flgVisualizzaProcedimenti) {
        this.flgVisualizzaProcedimenti = flgVisualizzaProcedimenti;
    }

    public String getFlgAutomatico() {
        return flgAutomatico;
    }

    public void setFlgAutomatico(String flgAutomatico) {
        this.flgAutomatico = flgAutomatico;
    }

    public Integer getIdProcedimentoRiferimento() {
        return idProcedimentoRiferimento;
    }

    public void setIdProcedimentoRiferimento(Integer idProcedimentoRiferimento) {
        this.idProcedimentoRiferimento = idProcedimentoRiferimento;
    }

    public String getScriptEvento() {
        return scriptEvento;
    }

    public void setScriptEvento(String scriptEvento) {
        this.scriptEvento = scriptEvento;
    }

    public String getScriptProtocollo() {
        return scriptProtocollo;
    }

    public void setScriptProtocollo(String scriptProtocollo) {
        this.scriptProtocollo = scriptProtocollo;
    }

    public Integer getMaxDestinatari() {
        return maxDestinatari;
    }

    public void setMaxDestinatari(Integer maxDestinatari) {
        this.maxDestinatari = maxDestinatari;
    }

    public String getOggettoMail() {
        return oggettoMail;
    }

    public void setOggettoMail(String oggettoMail) {
        this.oggettoMail = oggettoMail;
    }

    public String getCorpoMail() {
        return corpoMail;
    }

    public void setCorpoMail(String corpoMail) {
        this.corpoMail = corpoMail;
    }

    public String getFunzioneApplicativa() {
        return funzioneApplicativa;
    }

    public void setFunzioneApplicativa(String funzioneApplicativa) {
        this.funzioneApplicativa = funzioneApplicativa;
    }

    public List<ScadenzaEventoDTO> getScadenze() {
        return scadenze;
    }

    public void setScadenze(List<ScadenzaEventoDTO> scadenze) {
        this.scadenze = scadenze;
    }

    public List<ProcessoEventoAnagraficaDTO> getAnagraficaDestinatari() {
        return anagraficaDestinatari;
    }

    public void setAnagraficaDestinatari(List<ProcessoEventoAnagraficaDTO> anagraficaDestinatari) {
        this.anagraficaDestinatari = anagraficaDestinatari;
    }

    public List<ProcessoEventoEnteDTO> getEnteDestinatari() {
        return enteDestinatari;
    }

    public void setEnteDestinatari(List<ProcessoEventoEnteDTO> enteDestinatari) {
        this.enteDestinatari = enteDestinatari;
    }

    public String getForzaChiusuraScadenze() {
        return forzaChiusuraScadenze;
    }

    public void setForzaChiusuraScadenze(String forzaChiusuraScadenze) {
        this.forzaChiusuraScadenze = forzaChiusuraScadenze;
    }

}
