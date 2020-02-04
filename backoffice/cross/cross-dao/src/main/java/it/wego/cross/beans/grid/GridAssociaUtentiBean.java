package it.wego.cross.beans.grid;

import it.wego.cross.dto.UtenteDTO;
import java.util.List;

public class GridAssociaUtentiBean extends GridComponentBean {

    private List<UtenteDTO> rows;

    public List<UtenteDTO> getRows() {
        return rows;
    }

    public void setRows(List<UtenteDTO> rows) {
        this.rows = rows;
    }
}
