/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.beans.lookup;

import it.wego.cross.dto.NazionalitaDTO;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author giuseppe
 */
public class LookupNazionalita implements Serializable {

    private List<NazionalitaDTO> rows;

    public List<NazionalitaDTO> getRows() {
        return rows;
    }

    public void setRows(List<NazionalitaDTO> rows) {
        this.rows = rows;
    }
}
