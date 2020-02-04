/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.beans.search;

import it.wego.cross.dto.ComuneDTO;
import it.wego.cross.dto.ProcedimentoDTO;
import java.util.List;

/**
 *
 * @author giuseppe
 */
public class SearchProcedimentiComuni {

    public List<ProcedimentoDTO> procedimenti;
    public List<ComuneDTO> comuni;

    public List<ProcedimentoDTO> getProcedimenti() {
        return procedimenti;
    }

    public void setProcedimenti(List<ProcedimentoDTO> procedimenti) {
        this.procedimenti = procedimenti;
    }

    public List<ComuneDTO> getComuni() {
        return comuni;
    }

    public void setComuni(List<ComuneDTO> comuni) {
        this.comuni = comuni;
    }
}
