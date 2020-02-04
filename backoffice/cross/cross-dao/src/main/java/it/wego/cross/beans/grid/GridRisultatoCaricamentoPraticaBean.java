package it.wego.cross.beans.grid;

import java.util.List;

import it.wego.cross.dto.RisultatoCaricamentoPraticheDTO;

public class GridRisultatoCaricamentoPraticaBean extends GridComponentBean {

    private List<RisultatoCaricamentoPraticheDTO> rows;

    public List<RisultatoCaricamentoPraticheDTO> getRows() {
        return rows;
    }

    public void setRows(List<RisultatoCaricamentoPraticheDTO> rows) {
        this.rows = rows;
    }
}
