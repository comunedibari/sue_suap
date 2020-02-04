/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.beans.grid;

import it.wego.cross.dto.NotaDTO;
import java.util.List;

/**
 *
 * @author giuseppe
 */
public class GridNoteBean extends GridComponentBean {

    private List<NotaDTO> rows;

    public List<NotaDTO> getRows() {
        return rows;
    }

    public void setRows(List<NotaDTO> rows) {
        this.rows = rows;
    }
}
