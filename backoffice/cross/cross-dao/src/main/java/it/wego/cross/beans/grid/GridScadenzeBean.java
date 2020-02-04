/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.beans.grid;

import it.wego.cross.dto.ScadenzaDTO;
import java.util.List;

/**
 *
 * @author giuseppe
 */
public class GridScadenzeBean extends GridComponentBean {

    private List<ScadenzaDTO> rows;

    public List<ScadenzaDTO> getRows() {
        return rows;
    }

    public void setRows(List<ScadenzaDTO> rows) {
        this.rows = rows;
    }
}
