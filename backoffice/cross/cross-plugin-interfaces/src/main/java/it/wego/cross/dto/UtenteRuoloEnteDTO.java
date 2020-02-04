package it.wego.cross.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UtenteRuoloEnteDTO implements Serializable {

    private Integer idUtente;
    private String codPermesso;
    private Integer idEnte;
    private String desPermesso;
    private String desEnte;
    private List<ProcedimentiTestiDTO> procedimentiList = new ArrayList<ProcedimentiTestiDTO>();

    public Integer getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Integer idUtente) {
        this.idUtente = idUtente;
    }

    public String getCodPermesso() {
        return codPermesso;
    }

    public void setCodPermesso(String codPermesso) {
        this.codPermesso = codPermesso;
    }

    public Integer getIdEnte() {
        return idEnte;
    }

    public void setIdEnte(Integer idEnte) {
        this.idEnte = idEnte;
    }

    public String getDesPermesso() {
        return desPermesso;
    }

    public void setDesPermesso(String desPermesso) {
        this.desPermesso = desPermesso;
    }

    public String getDesEnte() {
        return desEnte;
    }

    public void setDesEnte(String desEnte) {
        this.desEnte = desEnte;
    }

    public List<ProcedimentiTestiDTO> getProcedimentiList() {
        return procedimentiList;
    }

    public void setProcedimentiList(List<ProcedimentiTestiDTO> procedimentiList) {
        this.procedimentiList = procedimentiList;
    }

}
