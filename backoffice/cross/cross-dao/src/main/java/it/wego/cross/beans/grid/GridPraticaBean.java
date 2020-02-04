/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.beans.grid;

import it.wego.cross.dto.PraticaDTO;
import java.util.List;

/**
 *
 * @author Giuseppe
 */
public class GridPraticaBean extends GridComponentBean {

    private List<PraticaDTO> rows;

    public List<PraticaDTO> getRows() {
        return rows;
    }

    public void setRows(List<PraticaDTO> rows) {
        this.rows = rows;
    }
}
