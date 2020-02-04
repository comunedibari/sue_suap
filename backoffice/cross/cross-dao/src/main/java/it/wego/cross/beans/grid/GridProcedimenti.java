/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.beans.grid;

import it.wego.cross.dto.ProcedimentoDTO;
import java.util.List;

/**
 *
 * @author giuseppe
 */
public class GridProcedimenti extends GridComponentBean {

    private List<ProcedimentoDTO> rows;

    public List<ProcedimentoDTO> getRows() {
        return rows;
    }

    public void setRows(List<ProcedimentoDTO> rows) {
        this.rows = rows;
    }
}
