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
public class OrganiCollegialiCommissioneDTO {

    private Integer idOrganiCollegiali;
    private String desOrganoCollegiale;
    private Integer idEnte;
    private String desEnte;
    private Integer idAnagrafica;
    private String cognome;
    private String nome;
    private Integer idRuoloComissione;
    private String desRuoloCommissione;

    public Integer getIdOrganiCollegiali() {
        return idOrganiCollegiali;
    }

    public void setIdOrganiCollegiali(Integer idOrganiCollegiali) {
        this.idOrganiCollegiali = idOrganiCollegiali;
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

    public Integer getIdAnagrafica() {
        return idAnagrafica;
    }

    public void setIdAnagrafica(Integer idAnagrafica) {
        this.idAnagrafica = idAnagrafica;
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

    public Integer getIdRuoloComissione() {
        return idRuoloComissione;
    }

    public void setIdRuoloComissione(Integer idRuoloComissione) {
        this.idRuoloComissione = idRuoloComissione;
    }

    public String getDesRuoloCommissione() {
        return desRuoloCommissione;
    }

    public void setDesRuoloCommissione(String desRuoloCommissione) {
        this.desRuoloCommissione = desRuoloCommissione;
    }

}
