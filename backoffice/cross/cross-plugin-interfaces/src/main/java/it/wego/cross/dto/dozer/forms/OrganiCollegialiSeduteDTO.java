/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.dozer.forms;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author piergiorgio
 */
public class OrganiCollegialiSeduteDTO {

    private Integer idSeduta;
    private Integer idOrganniCollegiali;
    private String desOrganoCollegiale;
    private Integer idEnte;
    private String desEnte;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dataPrevista;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dataConvocazione;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dataInizio;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dataConclusione;
    private String oraConvocazione;
    private String oraInizio;
    private String oraConclusione;
    private Integer idStatoSeduta;
    private String desStatoSeduta;
    private String dataConvocazioneGrid;
    private String dataInizioGrid;
    private String dataConclusioneGrid;

    public Integer getIdSeduta() {
        return idSeduta;
    }

    public void setIdSeduta(Integer idSeduta) {
        this.idSeduta = idSeduta;
    }

    public Integer getIdOrganniCollegiali() {
        return idOrganniCollegiali;
    }

    public void setIdOrganniCollegiali(Integer idOrganniCollegiali) {
        this.idOrganniCollegiali = idOrganniCollegiali;
    }

    public String getDesOrganoCollegiale() {
        return desOrganoCollegiale;
    }

    public void setDesOrganoCollegiale(String desOrganoCollegiale) {
        this.desOrganoCollegiale = desOrganoCollegiale;
    }

    public Integer getIdEnte() {
        return idEnte;
    }

    public void setIdEnte(Integer idEnte) {
        this.idEnte = idEnte;
    }

    public String getDesEnte() {
        return desEnte;
    }

    public void setDesEnte(String desEnte) {
        this.desEnte = desEnte;
    }

    public Date getDataPrevista() {
        return dataPrevista;
    }

    public void setDataPrevista(Date dataPrevista) {
        this.dataPrevista = dataPrevista;
    }

    public Date getDataConvocazione() {
        return dataConvocazione;
    }

    public void setDataConvocazione(Date dataConvocazione) {
        this.dataConvocazione = dataConvocazione;
    }

    public Date getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    public Date getDataConclusione() {
        return dataConclusione;
    }

    public void setDataConclusione(Date dataConclusione) {
        this.dataConclusione = dataConclusione;
    }

    public String getOraConvocazione() {
        return oraConvocazione;
    }

    public void setOraConvocazione(String oraConvocazione) {
        this.oraConvocazione = oraConvocazione;
    }

    public String getOraInizio() {
        return oraInizio;
    }

    public void setOraInizio(String oraInizio) {
        this.oraInizio = oraInizio;
    }

    public String getOraConclusione() {
        return oraConclusione;
    }

    public void setOraConclusione(String oraConclusione) {
        this.oraConclusione = oraConclusione;
    }

    public String getDataConvocazioneGrid() {
        return dataConvocazioneGrid;
    }

    public void setDataConvocazioneGrid(String dataConvocazioneGrid) {
        this.dataConvocazioneGrid = dataConvocazioneGrid;
    }

    public String getDataInizioGrid() {
        return dataInizioGrid;
    }

    public void setDataInizioGrid(String dataInizioGrid) {
        this.dataInizioGrid = dataInizioGrid;
    }

    public String getDataConclusioneGrid() {
        return dataConclusioneGrid;
    }

    public void setDataConclusioneGrid(String dataConclusioneGrid) {
        this.dataConclusioneGrid = dataConclusioneGrid;
    }

    public Integer getIdStatoSeduta() {
        return idStatoSeduta;
    }

    public void setIdStatoSeduta(Integer idStatoSeduta) {
        this.idStatoSeduta = idStatoSeduta;
    }

    public String getDesStatoSeduta() {
        return desStatoSeduta;
    }

    public void setDesStatoSeduta(String desStatoSeduta) {
        this.desStatoSeduta = desStatoSeduta;
    }

}
