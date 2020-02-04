/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.beans.lookup;

import it.wego.cross.dto.ProvinciaDTO;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author giuseppe
 */
public class LookupProvince implements Serializable{

    private List<ProvinciaDTO> rows;

    public List<ProvinciaDTO> getRows() {
        return rows;
    }

    public void setRows(List<ProvinciaDTO> rows) {
        this.rows = rows;
    }
}
