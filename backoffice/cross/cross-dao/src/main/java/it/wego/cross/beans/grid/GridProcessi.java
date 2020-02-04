/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.beans.grid;

import it.wego.cross.dto.ProcessoDTO;
import java.util.List;

/**
 *
 * @author giuseppe
 */
public class GridProcessi extends GridComponentBean {

    private List<ProcessoDTO> rows;

    public List<ProcessoDTO> getRows() {
        return rows;
    }

    public void setRows(List<ProcessoDTO> rows) {
        this.rows = rows;
    }
}
