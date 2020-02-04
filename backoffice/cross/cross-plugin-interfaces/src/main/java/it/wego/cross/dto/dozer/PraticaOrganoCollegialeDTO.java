/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.dozer;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author piergiorgio
 */
public class PraticaOrganoCollegialeDTO {

    private Integer idPratica;
    private Integer idEvento;
    private Integer idOrganiCollegiali;
    private String desOrganoCollegiale;
    @DateTimeFormat(pattern="dd/MM/yyyy")
    private Date dataRichiesta;
    private Integer idStatoPraticaOrganiCollegiali;
    private String codiceStatoPraticaOrganiCollegiali;

    public Integer getIdPratica() {
        return idPratica;
    }

    public void setIdPratica(Integer idPratica) {
        this.idPratica = idPratica;
    }

    public String getDesOrganoCollegiale() {
        return desOrganoCollegiale;
    }

    public void setDesOrganoCollegiale(String desOrganoCollegiale) {
        this.desOrganoCollegiale = desOrganoCollegiale;
    }

    public Date getDataRichiesta() {
        return dataRichiesta;
    }

    public void setDataRichiesta(Date dataRichiesta) {
        this.dataRichiesta = dataRichiesta;
    }

    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public Integer getIdStatoPraticaOrganiCollegiali() {
        return idStatoPraticaOrganiCollegiali;
    }

    public void setIdStatoPraticaOrganiCollegiali(Integer idStatoPraticaOrganiCollegiali) {
        this.idStatoPraticaOrganiCollegiali = idStatoPraticaOrganiCollegiali;
    }

    public String getCodiceStatoPraticaOrganiCollegiali() {
        return codiceStatoPraticaOrganiCollegiali;
    }

    public void setCodiceStatoPraticaOrganiCollegiali(String codiceStatoPraticaOrganiCollegiali) {
        this.codiceStatoPraticaOrganiCollegiali = codiceStatoPraticaOrganiCollegiali;
    }

    public Integer getIdOrganiCollegiali() {
        return idOrganiCollegiali;
    }

    public void setIdOrganiCollegiali(Integer idOrganiCollegiali) {
        this.idOrganiCollegiali = idOrganiCollegiali;
    }

}
