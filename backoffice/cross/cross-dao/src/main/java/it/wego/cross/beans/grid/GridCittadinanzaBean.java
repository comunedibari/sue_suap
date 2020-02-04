package it.wego.cross.beans.grid;

import it.wego.cross.dto.CittadinanzaDTO;
import java.util.List;

public class GridCittadinanzaBean extends GridComponentBean{

    
    private List<CittadinanzaDTO> rows;

    public List<CittadinanzaDTO> getRows() {
        return rows;
    }

    public void setRows(List<CittadinanzaDTO> rows) {
        this.rows = rows;
    }
    
    
}
