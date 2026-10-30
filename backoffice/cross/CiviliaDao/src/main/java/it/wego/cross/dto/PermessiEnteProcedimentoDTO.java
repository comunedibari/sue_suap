package it.wego.cross.dto;

import java.io.Serializable;

public class PermessiEnteProcedimentoDTO implements Serializable {

    private Integer idProc;
    private String codProc;
    private String desProc;
    private Integer termini;
    private String processo;
    private Boolean abilitato;
    private String responsabileProcedimento;

    public Boolean getAbilitato() {
        return abilitato;
    }

    public void setAbilitato(Boolean abilitato) {
        this.abilitato = abilitato;
    }

    public String getCodProc() {
        return codProc;
    }

    public void setCodProc(String codProc) {
        this.codProc = codProc;
    }

    public String getDesProc() {
        return desProc;
    }

    public void setDesProc(String desProc) {
        this.desProc = desProc;
    }

    public Integer getIdProc() {
        return idProc;
    }

    public void setIdProc(Integer idProc) {
        this.idProc = idProc;
    }

    public Integer getTermini() {
        return termini;
    }

    public void setTermini(Integer termini) {
        this.termini = termini;
    }

    public String getProcesso() {
        return processo;
    }

    public void setProcesso(String processo) {
        this.processo = processo;
    }

    public String getResponsabileProcedimento() {
        return responsabileProcedimento;
    }

    public void setResponsabileProcedimento(String responsabileProcedimento) {
        this.responsabileProcedimento = responsabileProcedimento;
    }
}
