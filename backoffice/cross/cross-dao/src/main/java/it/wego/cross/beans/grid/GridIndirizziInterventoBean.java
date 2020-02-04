/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.beans.grid;

import it.wego.cross.dto.dozer.IndirizzoInterventoDTO;
import java.util.List;

/**
 *
 * @author Giuseppe
 */
public class GridIndirizziInterventoBean extends GridComponentBean {

    private List<IndirizzoInterventoDTO> rows;
    private String error;

    public List<IndirizzoInterventoDTO> getRows() {
        return rows;
    }

    public void setRows(List<IndirizzoInterventoDTO> rows) {
        this.rows = rows;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
