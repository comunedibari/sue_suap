
package it.wego.cross.beans.grid;

import it.wego.cross.dto.dozer.LkTipoParticellaDTO;
import java.util.List;

public class GridDatiLkTipoParticellaBean extends GridComponentBean{

    
    private List<LkTipoParticellaDTO> rows;

    public List<LkTipoParticellaDTO> getRows() {
        return rows;
    }

    public void setRows(List<LkTipoParticellaDTO> rows) {
        this.rows = rows;
    }
    
    
}
