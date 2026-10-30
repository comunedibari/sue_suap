/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.dozer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author giuseppe
 */
public class UtenteRuoloEnteDTO implements Serializable {

    private EnteDTO idEnte;
    private PermessoDTO codPermesso;
    private List<ProcedimentoDTO> procedimentiList = new ArrayList<ProcedimentoDTO>();

    public EnteDTO getIdEnte() {
        return idEnte;
    }

    public void setIdEnte(EnteDTO idEnte) {
        this.idEnte = idEnte;
    }

    public PermessoDTO getCodPermesso() {
        return codPermesso;
    }

    public void setCodPermesso(PermessoDTO codPermesso) {
        this.codPermesso = codPermesso;
    }

    public List<ProcedimentoDTO> getProcedimentiList() {
        return procedimentiList;
    }

    public void setProcedimentiList(List<ProcedimentoDTO> procedimentiList) {
        this.procedimentiList = procedimentiList;
    }

}
