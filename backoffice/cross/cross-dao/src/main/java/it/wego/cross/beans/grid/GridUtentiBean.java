/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.beans.grid;

import it.wego.cross.dto.UtenteDTO;
import java.util.List;

/**
 *
 * @author giuseppe
 */
public class GridUtentiBean extends GridComponentBean {

    private List<UtenteDTO> rows;

    public List<UtenteDTO> getRows() {
        return rows;
    }

    public void setRows(List<UtenteDTO> rows) {
        this.rows = rows;
    }
}
