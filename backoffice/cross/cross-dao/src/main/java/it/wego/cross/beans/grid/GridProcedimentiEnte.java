/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.beans.grid;

import it.wego.cross.beans.ProcedimentoPermessiBean;
import java.util.List;

/**
 *
 * @author giuseppe
 */
public class GridProcedimentiEnte extends GridComponentBean {

    private List<ProcedimentoPermessiBean> rows;

    public List<ProcedimentoPermessiBean> getRows() {
        return rows;
    }

    public void setRows(List<ProcedimentoPermessiBean> rows) {
        this.rows = rows;
    }
}
