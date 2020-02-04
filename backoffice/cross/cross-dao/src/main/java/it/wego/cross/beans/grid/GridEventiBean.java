/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.beans.grid;

import it.wego.cross.dto.EventoDTO;
import java.util.List;

/**
 *
 * @author giuseppe
 */
public class GridEventiBean extends GridComponentBean {

    private List<EventoDTO> rows;

    public List<EventoDTO> getRows() {
        return rows;
    }

    public void setRows(List<EventoDTO> rows) {
        this.rows = rows;
    }
}
