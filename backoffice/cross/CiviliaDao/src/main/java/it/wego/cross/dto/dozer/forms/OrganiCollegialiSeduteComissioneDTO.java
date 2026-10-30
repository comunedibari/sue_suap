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
public class OrganiCollegialiSeduteComissioneDTO {

    private Integer idSeduta;
    private Integer idAnagrafica;
    private Integer idRuoloCommissione;
    private Integer idSedutaPratica;
    private Integer idOrganiCollegiali;
    private Integer idPraticaOrganiCollegiali;
    private Integer idPratica;
    private String protocollo;
    private String oggetto;
    private Date dataRicezione;
    private Integer idEnte;
    private Date dataRichiestaCommissione;
    private String desEnte;
    private String cognome;
    private String nome;
    private String desRuoloCommissione;
    private Integer sequenza;
    private String desOrganoCollegiale;
    private Integer idKey;

    public Integer getIdSeduta() {
        return idSeduta;
    }

    public void setIdSeduta(Integer idSeduta) {
        this.idSeduta = idSeduta;
    }

    public Integer getIdAnagrafica() {
        return idAnagrafica;
    }

    public void setIdAnagrafica(Integer idAnagrafica) {
        this.idAnagrafica = idAnagrafica;
    }

    public Integer getIdRuoloCommissione() {
        return idRuoloCommissione;
    }

    public void setIdRuoloCommissione(Integer idRuoloCommissione) {
        this.idRuoloCommissione = idRuoloCommissione;
    }

    public Integer getIdSedutaPratica() {
        return idSedutaPratica;
    }

    public void setIdSedutaPratica(Integer idSedutaPratica) {
        this.idSedutaPratica = idSedutaPratica;
    }

    public Integer getIdOrganiCollegiali() {
        return idOrganiCollegiali;
    }

    public void setIdOrganiCollegiali(Integer idOrganiCollegiali) {
        this.idOrganiCollegiali = idOrganiCollegiali;
    }

    public Integer getIdPraticaOrganiCollegiali() {
        return idPraticaOrganiCollegiali;
    }

    public void setIdPraticaOrganiCollegiali(Integer idPraticaOrganiCollegiali) {
        this.idPraticaOrganiCollegiali = idPraticaOrganiCollegiali;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDesRuoloCommissione() {
        return desRuoloCommissione;
    }

    public void setDesRuoloCommissione(String desRuoloCommissione) {
        this.desRuoloCommissione = desRuoloCommissione;
    }

    public Integer getIdPratica() {
        return idPratica;
    }

    public void setIdPratica(Integer idPratica) {
        this.idPratica = idPratica;
    }

    public String getProtocollo() {
        return protocollo;
    }

    public void setProtocollo(String protocollo) {
        this.protocollo = protocollo;
    }

    public String getOggetto() {
        return oggetto;
    }

    public void setOggetto(String oggetto) {
        this.oggetto = oggetto;
    }

    public Date getDataRicezione() {
        return dataRicezione;
    }

    public void setDataRicezione(Date dataRicezione) {
        this.dataRicezione = dataRicezione;
    }

    public Integer getIdEnte() {
        return idEnte;
    }

    public void setIdEnte(Integer idEnte) {
        this.idEnte = idEnte;
    }

    public Date getDataRichiestaCommissione() {
        return dataRichiestaCommissione;
    }

    public void setDataRichiestaCommissione(Date dataRichiestaCommissione) {
        this.dataRichiestaCommissione = dataRichiestaCommissione;
    }

    public String getDesEnte() {
        return desEnte;
    }

    public void setDesEnte(String desEnte) {
        this.desEnte = desEnte;
    }

    public Integer getSequenza() {
        return sequenza;
    }

    public void setSequenza(Integer sequenza) {
        this.sequenza = sequenza;
    }

    public String getDesOrganoCollegiale() {
        return desOrganoCollegiale;
    }

    public void setDesOrganoCollegiale(String desOrganoCollegiale) {
        this.desOrganoCollegiale = desOrganoCollegiale;
    }

    public Integer getIdKey() {
        return idKey;
    }

    public void setIdKey(Integer idKey) {
        this.idKey = idKey;
    }
    
}
