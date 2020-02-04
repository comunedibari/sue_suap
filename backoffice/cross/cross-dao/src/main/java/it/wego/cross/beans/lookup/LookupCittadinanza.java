/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.beans.lookup;

import it.wego.cross.dto.CittadinanzaDTO;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author giuseppe
 */
public class LookupCittadinanza implements Serializable {

    private List<CittadinanzaDTO> rows;

    public List<CittadinanzaDTO> getRows() {
        return rows;
    }

    public void setRows(List<CittadinanzaDTO> rows) {
        this.rows = rows;
    }
}
