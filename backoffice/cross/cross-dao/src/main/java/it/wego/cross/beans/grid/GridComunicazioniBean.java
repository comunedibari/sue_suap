/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.beans.grid;

import it.wego.cross.dto.ComunicazioneMinifiedDTO;
import java.util.List;

/**
 *
 * @author giuseppe
 */
public class GridComunicazioniBean extends GridComponentBean {

    private List<ComunicazioneMinifiedDTO> rows;

    public List<ComunicazioneMinifiedDTO> getRows() {
        return rows;
    }

    public void setRows(List<ComunicazioneMinifiedDTO> rows) {
        this.rows = rows;
    }
}
