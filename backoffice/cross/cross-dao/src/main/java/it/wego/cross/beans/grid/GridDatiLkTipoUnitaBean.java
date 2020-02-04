
package it.wego.cross.beans.grid;

import it.wego.cross.dto.dozer.LkTipoUnitaDTO;
import java.util.List;

public class GridDatiLkTipoUnitaBean extends GridComponentBean{

    
    private List<LkTipoUnitaDTO> rows;

    public List<LkTipoUnitaDTO> getRows() {
        return rows;
    }

    public void setRows(List<LkTipoUnitaDTO> rows) {
        this.rows = rows;
    }
    
    
}
