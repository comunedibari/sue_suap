package it.wego.cross.beans.grid;

import it.wego.cross.dto.PraticaNuova;
import java.util.List;

public class GridPraticaNuovaBean extends GridComponentBean {

    private List<PraticaNuova> rows;

    public List<PraticaNuova> getRows() {
        return rows;
    }

    public void setRows(List<PraticaNuova> rows) {
        this.rows = rows;
    }
}
