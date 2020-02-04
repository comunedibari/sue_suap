/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.dozer;

import java.util.Date;

/**
 *
 * @author Gabriele
 */
public class ScadenzaDTO {

    private Integer idScadenza;
    private Date dataScadenza;
    private Date dataFineScadenza;
    private Date dataFineScadenzaCalcolata;
    private Date dataInizioScadenza;
    private int giorniTeoriciScadenza;
    private String descrizioneScadenza;
    private StatoScadenzaDTO idStato;
    private String grpStatoScadenza;
    private String idAnaScadenza;
    private String desAnaScadenze;

    public Integer getIdScadenza() {
        return idScadenza;
    }

    public void setIdScadenza(Integer idScadenza) {
        this.idScadenza = idScadenza;
    }

    public Date getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(Date dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public Date getDataFineScadenza() {
        return dataFineScadenza;
    }

    public void setDataFineScadenza(Date dataFineScadenza) {
        this.dataFineScadenza = dataFineScadenza;
    }

    public Date getDataFineScadenzaCalcolata() {
        return dataFineScadenzaCalcolata;
    }

    public void setDataFineScadenzaCalcolata(Date dataFineScadenzaCalcolata) {
        this.dataFineScadenzaCalcolata = dataFineScadenzaCalcolata;
    }

    public Date getDataInizioScadenza() {
        return dataInizioScadenza;
    }

    public void setDataInizioScadenza(Date dataInizioScadenza) {
        this.dataInizioScadenza = dataInizioScadenza;
    }

    public int getGiorniTeoriciScadenza() {
        return giorniTeoriciScadenza;
    }

    public void setGiorniTeoriciScadenza(int giorniTeoriciScadenza) {
        this.giorniTeoriciScadenza = giorniTeoriciScadenza;
    }

    public String getDescrizioneScadenza() {
        return descrizioneScadenza;
    }

    public void setDescrizioneScadenza(String descrizioneScadenza) {
        this.descrizioneScadenza = descrizioneScadenza;
    }

    public String getGrpStatoScadenza() {
        return grpStatoScadenza;
    }

    public void setGrpStatoScadenza(String grpStatoScadenza) {
        this.grpStatoScadenza = grpStatoScadenza;
    }

    public String getDesAnaScadenze() {
        return desAnaScadenze;
    }

    public void setDesAnaScadenze(String desAnaScadenze) {
        this.desAnaScadenze = desAnaScadenze;
    }

    public String getIdAnaScadenza() {
        return idAnaScadenza;
    }

    public void setIdAnaScadenza(String idAnaScadenza) {
        this.idAnaScadenza = idAnaScadenza;
    }

    public StatoScadenzaDTO getIdStato() {
        return idStato;
    }

    public void setIdStato(StatoScadenzaDTO idStato) {
        this.idStato = idStato;
    }

}
