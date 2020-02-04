/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.dozer;

import java.util.List;

/**
 *
 * @author Gabriele
 */
public class ProcessoEventoDTO {

    private Integer idEvento;
    private String codEvento;
    private String desEvento;
    private String scriptScadenzaEvento;
    private String verso;
    private String flgPortale;
    private String flgMail;
    private String flgAllMail;
    private String flgProtocollazione;
    private String flgRicevuta;
    private String flgDestinatari;
    private String flgFirmato;
    private String flgApriSottopratica;
    private String flgDestinatariSoloEnti;
    private String flgVisualizzaProcedimenti;
    private String idScriptEvento;
    private String idScriptProtocollo;
    private Integer maxDestinatari;
    private String oggettoEmail;
    private String corpoEmail;
    private String funzioneApplicativa;
    private String flgAutomatico;
    private List<ProcessoEventoScadenzaDTO> processiEventiScadenzeList;
    private ProcessoDTO idProcesso;
    private ProcedimentoDTO idProcedimentoRiferimento;
    private TipoAttoreDTO idTipoMittente;
    private TipoAttoreDTO idTipoDestinatario;
    private StatoPraticaDTO statoPost;
    private String flgScadenzeCustom;

    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public String getCodEvento() {
        return codEvento;
    }

    public void setCodEvento(String codEvento) {
        this.codEvento = codEvento;
    }

    public String getDesEvento() {
        return desEvento;
    }

    public void setDesEvento(String desEvento) {
        this.desEvento = desEvento;
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

    public String getFlgAllMail() {
        return flgAllMail;
    }

    public void setFlgAllMail(String flgAllMail) {
        this.flgAllMail = flgAllMail;
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

    public String getFlgApriSottopratica() {
        return flgApriSottopratica;
    }

    public void setFlgApriSottopratica(String flgApriSottopratica) {
        this.flgApriSottopratica = flgApriSottopratica;
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

    public String getIdScriptEvento() {
        return idScriptEvento;
    }

    public void setIdScriptEvento(String idScriptEvento) {
        this.idScriptEvento = idScriptEvento;
    }

    public String getIdScriptProtocollo() {
        return idScriptProtocollo;
    }

    public void setIdScriptProtocollo(String idScriptProtocollo) {
        this.idScriptProtocollo = idScriptProtocollo;
    }

    public Integer getMaxDestinatari() {
        return maxDestinatari;
    }

    public void setMaxDestinatari(Integer maxDestinatari) {
        this.maxDestinatari = maxDestinatari;
    }

    public String getOggettoEmail() {
        return oggettoEmail;
    }

    public void setOggettoEmail(String oggettoEmail) {
        this.oggettoEmail = oggettoEmail;
    }

    public String getCorpoEmail() {
        return corpoEmail;
    }

    public void setCorpoEmail(String corpoEmail) {
        this.corpoEmail = corpoEmail;
    }

    public String getFunzioneApplicativa() {
        return funzioneApplicativa;
    }

    public void setFunzioneApplicativa(String funzioneApplicativa) {
        this.funzioneApplicativa = funzioneApplicativa;
    }

    public String getFlgAutomatico() {
        return flgAutomatico;
    }

    public void setFlgAutomatico(String flgAutomatico) {
        this.flgAutomatico = flgAutomatico;
    }

    public List<ProcessoEventoScadenzaDTO> getProcessiEventiScadenzeList() {
        return processiEventiScadenzeList;
    }

    public void setProcessiEventiScadenzeList(List<ProcessoEventoScadenzaDTO> processiEventiScadenzeList) {
        this.processiEventiScadenzeList = processiEventiScadenzeList;
    }

    public ProcessoDTO getIdProcesso() {
        return idProcesso;
    }

    public void setIdProcesso(ProcessoDTO idProcesso) {
        this.idProcesso = idProcesso;
    }

    public ProcedimentoDTO getIdProcedimentoRiferimento() {
        return idProcedimentoRiferimento;
    }

    public void setIdProcedimentoRiferimento(ProcedimentoDTO idProcedimentoRiferimento) {
        this.idProcedimentoRiferimento = idProcedimentoRiferimento;
    }

    public TipoAttoreDTO getIdTipoMittente() {
        return idTipoMittente;
    }

    public void setIdTipoMittente(TipoAttoreDTO idTipoMittente) {
        this.idTipoMittente = idTipoMittente;
    }

    public TipoAttoreDTO getIdTipoDestinatario() {
        return idTipoDestinatario;
    }

    public void setIdTipoDestinatario(TipoAttoreDTO idTipoDestinatario) {
        this.idTipoDestinatario = idTipoDestinatario;
    }

    public StatoPraticaDTO getStatoPost() {
        return statoPost;
    }

    public void setStatoPost(StatoPraticaDTO statoPost) {
        this.statoPost = statoPost;
    }

    public String getFlgScadenzeCustom() {
        return flgScadenzeCustom;
    }

    public void setFlgScadenzeCustom(String flgScadenzeCustom) {
        this.flgScadenzeCustom = flgScadenzeCustom;
    }

    public static class ProcessoEventoScadenzaDTO {

        private Integer terminiScadenza;
        private String scriptScadenza;
        private String flgVisualizzaScadenza;
        private StatoScadenzaDTO idStatoScadenza;
        private ScadenzaDTO scadenza;

        public Integer getTerminiScadenza() {
            return terminiScadenza;
        }

        public void setTerminiScadenza(Integer terminiScadenza) {
            this.terminiScadenza = terminiScadenza;
        }

        public String getScriptScadenza() {
            return scriptScadenza;
        }

        public void setScriptScadenza(String scriptScadenza) {
            this.scriptScadenza = scriptScadenza;
        }

        public String getFlgVisualizzaScadenza() {
            return flgVisualizzaScadenza;
        }

        public void setFlgVisualizzaScadenza(String flgVisualizzaScadenza) {
            this.flgVisualizzaScadenza = flgVisualizzaScadenza;
        }

        public StatoScadenzaDTO getIdStatoScadenza() {
            return idStatoScadenza;
        }

        public void setIdStatoScadenza(StatoScadenzaDTO idStatoScadenza) {
            this.idStatoScadenza = idStatoScadenza;
        }

        public ScadenzaDTO getScadenza() {
            return scadenza;
        }

        public void setScadenza(ScadenzaDTO scadenza) {
            this.scadenza = scadenza;
        }
    }
}
