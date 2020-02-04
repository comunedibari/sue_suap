package it.wego.cross.beans.grid;

import it.wego.cross.dto.EventoTemplateDTO;
import it.wego.cross.dto.TemplateDTO;
import java.util.List;

public class GridEventoTemplateBean extends GridComponentBean{

    
    private List<EventoTemplateDTO> rows;

    public List<EventoTemplateDTO> getRows() {
        return rows;
    }

    public void setRows(List<EventoTemplateDTO> rows) {
        this.rows = rows;
    }
    
    
}
