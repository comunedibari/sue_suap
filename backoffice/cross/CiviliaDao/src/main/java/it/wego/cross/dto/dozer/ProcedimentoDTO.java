/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.dozer;

/**
 *
 * @author Gabriele
 */
public class ProcedimentoDTO {

    private Integer idProc;
    private String codProc;
    private Integer termini;
    private String tipoProc;
    private String classifica;
    private Integer peso;
    private String descrizione;

    public Integer getIdProc() {
        return idProc;
    }

    public void setIdProc(Integer idProc) {
        this.idProc = idProc;
    }

    public String getCodProc() {
        return codProc;
    }

    public void setCodProc(String codProc) {
        this.codProc = codProc;
    }

    public Integer getTermini() {
        return termini;
    }

    public void setTermini(Integer termini) {
        this.termini = termini;
    }

    public String getTipoProc() {
        return tipoProc;
    }

    public void setTipoProc(String tipoProc) {
        this.tipoProc = tipoProc;
    }

    public String getClassifica() {
        return classifica;
    }

    public void setClassifica(String classifica) {
        this.classifica = classifica;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}
