/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.beans.grid;

import it.wego.cross.dto.dozer.DatiCatastaliDTO;
import java.util.List;

/**
 *
 * @author Giuseppe
 */
public class GridDatiCatastaliBean extends GridComponentBean {

    private List<DatiCatastaliDTO> rows;
    private String error;

    public List<DatiCatastaliDTO> getRows() {
        return rows;
    }

    public void setRows(List<DatiCatastaliDTO> rows) {
        this.rows = rows;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
