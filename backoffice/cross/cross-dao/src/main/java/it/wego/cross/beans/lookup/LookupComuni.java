/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.beans.lookup;

import it.wego.cross.beans.grid.GridComponentBean;
import it.wego.cross.dto.ComuneDTO;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author giuseppe
 */
public class LookupComuni extends GridComponentBean implements Serializable {

    private List<ComuneDTO> rows;

    public List<ComuneDTO> getRows() {
        return rows;
    }

    public void setRows(List<ComuneDTO> rows) {
        this.rows = rows;
    }
}
