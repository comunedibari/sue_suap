/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.beans.lookup;

import it.wego.cross.dto.search.TipoCollegioDTO;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author giuseppe
 */
public class LookupTipoCollegio implements Serializable {

    private List<TipoCollegioDTO> rows;

    public List<TipoCollegioDTO> getRows() {
        return rows;
    }

    public void setRows(List<TipoCollegioDTO> rows) {
        this.rows = rows;
    }
}
