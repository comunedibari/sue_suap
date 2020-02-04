/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.beans.grid;

import it.wego.cross.dto.PraticaProtocolloDTO;
import java.util.List;

/**
 *
 * @author giuseppe
 */
public class GridPraticheProtocolloBean extends GridComponentBean {

    private List<PraticaProtocolloDTO> rows;

    public List<PraticaProtocolloDTO> getRows() {
        return rows;
    }

    public void setRows(List<PraticaProtocolloDTO> rows) {
        this.rows = rows;
    }
}
