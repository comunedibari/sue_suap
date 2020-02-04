
package it.wego.cross.beans.grid;

import it.wego.cross.dto.ComuneDTO;
import java.util.List;

public class GridComuneBean extends GridComponentBean{

    
    private List<ComuneDTO> rows;

    public List<ComuneDTO> getRows() {
        return rows;
    }

    public void setRows(List<ComuneDTO> rows) {
        this.rows = rows;
    }
    
    
}
