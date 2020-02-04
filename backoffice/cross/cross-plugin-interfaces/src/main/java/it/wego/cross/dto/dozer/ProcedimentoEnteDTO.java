/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.dozer;

/**
 *
 * @author Gabriele
 */
public class ProcedimentoEnteDTO {

    private Integer idProcEnte;
    private ProcedimentoDTO procedimento;
    private EnteDTO ente;
    private ProcessoDTO processo;
    private PraticaDTO pratica;

    public Integer getIdProcEnte() {
        return idProcEnte;
    }

    public void setIdProcEnte(Integer idProcEnte) {
        this.idProcEnte = idProcEnte;
    }

    public ProcedimentoDTO getProcedimento() {
        return procedimento;
    }

    public void setProcedimento(ProcedimentoDTO procedimento) {
        this.procedimento = procedimento;
    }

    public EnteDTO getEnte() {
        return ente;
    }

    public void setEnte(EnteDTO ente) {
        this.ente = ente;
    }

    public ProcessoDTO getProcesso() {
        return processo;
    }

    public void setProcesso(ProcessoDTO processo) {
        this.processo = processo;
    }

    public PraticaDTO getPratica() {
        return pratica;
    }

    public void setPratica(PraticaDTO pratica) {
        this.pratica = pratica;
    }

}
