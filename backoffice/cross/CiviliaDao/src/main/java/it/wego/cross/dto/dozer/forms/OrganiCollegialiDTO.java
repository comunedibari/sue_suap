/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.dozer.forms;

/**
 *
 * @author piergiorgio
 */
public class OrganiCollegialiDTO {

    private Integer idOrganiCollegiali;
    private Integer idEnte;
    private String desOrganoCollegiale;
    private String desEnte;

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

}
