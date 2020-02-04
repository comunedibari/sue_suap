/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.beans.grid;

import it.wego.cross.dto.PraticheEventiRidDTO;
import it.wego.cross.dto.dozer.PraticaEventoDTO;
import java.util.List;

/**
 *
 * @author GabrieleM
 */
public class GridPraticaEventoBean extends GridComponentBean {

    private List<PraticheEventiRidDTO> rows;

    public List<PraticheEventiRidDTO> getRows() {
        return rows;
    }

    public void setRows(List<PraticheEventiRidDTO> rows) {
        this.rows = rows;
    }
}
