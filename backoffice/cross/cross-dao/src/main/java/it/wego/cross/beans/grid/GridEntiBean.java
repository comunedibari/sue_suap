package it.wego.cross.beans.grid;

import it.wego.cross.dto.EnteDTO;
import java.util.List;

public class GridEntiBean extends GridComponentBean {

    private List<EnteDTO> rows;

    public List<EnteDTO> getRows() {
        return rows;
    }

    public void setRows(List<EnteDTO> rows) {
        this.rows = rows;
    }
}
