/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.beans.lookup;

import it.wego.cross.dto.search.FormaGiuridicaDTO;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author giuseppe
 */
public class LookupFormeGiuridiche implements Serializable {

    private List<FormaGiuridicaDTO> rows;

    public List<FormaGiuridicaDTO> getRows() {
        return rows;
    }

    public void setRows(List<FormaGiuridicaDTO> rows) {
        this.rows = rows;
    }
}
