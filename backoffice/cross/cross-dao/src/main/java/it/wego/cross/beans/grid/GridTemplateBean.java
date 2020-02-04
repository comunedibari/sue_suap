package it.wego.cross.beans.grid;

import it.wego.cross.dto.TemplateDTO;
import java.util.List;

public class GridTemplateBean extends GridComponentBean{

    
    private List<TemplateDTO> rows;

    public List<TemplateDTO> getRows() {
        return rows;
    }

    public void setRows(List<TemplateDTO> rows) {
        this.rows = rows;
    }
    
    
}
