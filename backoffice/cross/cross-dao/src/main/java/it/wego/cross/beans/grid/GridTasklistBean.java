package it.wego.cross.beans.grid;

import it.wego.cross.dto.TaskDTO;
import java.util.ArrayList;
import java.util.List;

public class GridTasklistBean extends GridComponentBean {

    private List<TaskDTO> rows;

    public List<TaskDTO> getRows() {
        if (rows == null) {
            rows = new ArrayList<TaskDTO>();
        }
        return rows;
    }

    public void setRows(List<TaskDTO> rows) {
        this.rows = rows;
    }
}
