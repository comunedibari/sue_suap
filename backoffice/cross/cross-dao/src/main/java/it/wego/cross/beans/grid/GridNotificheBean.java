/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.beans.grid;

import it.wego.cross.dto.TaskDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Giuseppe
 */
public class GridNotificheBean extends GridComponentBean{

    private List<TaskDTO> rows = new ArrayList<TaskDTO>();

    public List<TaskDTO> getRows() {
        return rows;
    }

    public void setRows(List<TaskDTO> rows) {
        this.rows = rows;
    }

}
