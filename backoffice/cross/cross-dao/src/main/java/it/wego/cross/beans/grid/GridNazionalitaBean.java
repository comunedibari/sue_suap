/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.beans.grid;

import it.wego.cross.dto.NazionalitaDTO;
import java.util.List;

/**
 *
 * @author giuseppe
 */
public class GridNazionalitaBean extends GridComponentBean {

    private List<NazionalitaDTO> rows;

    public List<NazionalitaDTO> getRows() {
        return rows;
    }

    public void setRows(List<NazionalitaDTO> rows) {
        this.rows = rows;
    }
}
