package it.wego.cross.beans.grid;

import it.wego.cross.dto.AnagraficaDTO;
import java.util.List;

public class GridAnagraficaBean extends GridComponentBean{

    
    private List<AnagraficaDTO> rows;

    public List<AnagraficaDTO> getRows() {
        return rows;
    }

    public void setRows(List<AnagraficaDTO> rows) {
        this.rows = rows;
    }
    
    
}
