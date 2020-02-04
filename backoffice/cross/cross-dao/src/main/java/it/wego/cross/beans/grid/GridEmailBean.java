/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.beans.grid;

import it.wego.cross.dto.EmailDTO;
import java.util.List;

/**
 *
 * @author Giuseppe
 */
public class GridEmailBean extends GridComponentBean {

    private List<EmailDTO> rows;

    public List<EmailDTO> getRows() {
        return rows;
    }

    public void setRows(List<EmailDTO> rows) {
        this.rows = rows;
    }
}
