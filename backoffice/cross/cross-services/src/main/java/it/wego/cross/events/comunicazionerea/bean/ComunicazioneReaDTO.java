/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.events.comunicazionerea.bean;

/**
 *
 * @author Gabriele
 */
public class ComunicazioneReaDTO {

    private Integer idPratica;
    private Integer idEvento;
    private Integer idEventoPartenza;
    private Integer idAllegatoProcuraSpeciale;
    private String tipologiaProcedimento;
    private String tipologiaIntervento;
    private Integer dichiarantePratica;
    private Integer aziendaRiferimento;
    private String formaGiuridicaAzienda;
    private Integer rappresentanteAzienda;
    private String qualificaDichiarantePratica;
    private String caricaRappresentanteAzienda;
    private String tipologiaComunicazioneRea;

    public Integer getIdPratica() {
        return idPratica;
    }

    public void setIdPratica(Integer idPratica) {
        this.idPratica = idPratica;
    }

    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public Integer getIdEventoPartenza() {
        return idEventoPartenza;
    }

    public void setIdEventoPartenza(Integer idEventoPartenza) {
        this.idEventoPartenza = idEventoPartenza;
    }

    public String getTipologiaProcedimento() {
        return tipologiaProcedimento;
    }

    public void setTipologiaProcedimento(String tipologiaProcedimento) {
        this.tipologiaProcedimento = tipologiaProcedimento;
    }

    public String getTipologiaIntervento() {
        return tipologiaIntervento;
    }

    public void setTipologiaIntervento(String tipologiaIntervento) {
        this.tipologiaIntervento = tipologiaIntervento;
    }

    public Integer getDichiarantePratica() {
        return dichiarantePratica;
    }

    public void setDichiarantePratica(Integer dichiarantePratica) {
        this.dichiarantePratica = dichiarantePratica;
    }

    public Integer getAziendaRiferimento() {
        return aziendaRiferimento;
    }

    public void setAziendaRiferimento(Integer aziendaRiferimento) {
        this.aziendaRiferimento = aziendaRiferimento;
    }

    public String getFormaGiuridicaAzienda() {
        return formaGiuridicaAzienda;
    }

    public void setFormaGiuridicaAzienda(String formaGiuridicaAzienda) {
        this.formaGiuridicaAzienda = formaGiuridicaAzienda;
    }

    public Integer getRappresentanteAzienda() {
        return rappresentanteAzienda;
    }

    public void setRappresentanteAzienda(Integer rappresentanteAzienda) {
        this.rappresentanteAzienda = rappresentanteAzienda;
    }

    public String getQualificaDichiarantePratica() {
        return qualificaDichiarantePratica;
    }

    public void setQualificaDichiarantePratica(String qualificaDichiarantePratica) {
        this.qualificaDichiarantePratica = qualificaDichiarantePratica;
    }

    public String getCaricaRappresentanteAzienda() {
        return caricaRappresentanteAzienda;
    }

    public void setCaricaRappresentanteAzienda(String caricaRappresentanteAzienda) {
        this.caricaRappresentanteAzienda = caricaRappresentanteAzienda;
    }

    public String getTipologiaComunicazioneRea() {
        return tipologiaComunicazioneRea;
    }

    public void setTipologiaComunicazioneRea(String tipologiaComunicazioneRea) {
        this.tipologiaComunicazioneRea = tipologiaComunicazioneRea;
    }

    public Integer getIdAllegatoProcuraSpeciale() {
        return idAllegatoProcuraSpeciale;
    }

    public void setIdAllegatoProcuraSpeciale(Integer idAllegatoProcuraSpeciale) {
        this.idAllegatoProcuraSpeciale = idAllegatoProcuraSpeciale;
    }
}
