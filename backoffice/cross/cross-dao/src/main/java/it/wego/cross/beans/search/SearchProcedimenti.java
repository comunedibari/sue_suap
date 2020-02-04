/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.beans.search;

import it.wego.cross.beans.grid.GridComponentBean;
import it.wego.cross.dto.ProcedimentoDTO;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author giuseppe
 */
public class SearchProcedimenti extends GridComponentBean implements Serializable {

    private List<ProcedimentoDTO> rows;

    public List<ProcedimentoDTO> getRows() {
        return rows;
    }

    public void setRows(List<ProcedimentoDTO> rows) {
        this.rows = rows;
    }
}
