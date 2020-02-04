/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.beans.grid;

import it.wego.cross.dto.StepDTO;
import java.util.List;

/**
 *
 * @author giuseppe
 */
public class GridStepsBean extends GridComponentBean {

    private List<StepDTO> rows;

    public List<StepDTO> getRows() {
        return rows;
    }

    public void setRows(List<StepDTO> rows) {
        this.rows = rows;
    }
}
