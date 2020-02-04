/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.beans.grid;

import it.wego.cross.dto.PermessiEnteProcedimentoDTO;
import java.util.List;

/**
 *
 * @author giuseppe
 */
public class GridPermessiEnteProcedimento extends GridComponentBean {

    private List<PermessiEnteProcedimentoDTO> rows;

    public List<PermessiEnteProcedimentoDTO> getRows() {
        return rows;
    }

    public void setRows(List<PermessiEnteProcedimentoDTO> rows) {
        this.rows = rows;
    }
}
