/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.beans.grid;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.wego.cross.dto.dozer.forms.OrganiCollegialiCommissioneDTO;
import java.util.List;

/**
 *
 * @author Giuseppe
 */
public class GridOrganiCollegialiCommissione extends GridComponentBean {

    private List<OrganiCollegialiCommissioneDTO> rows;

    public List<OrganiCollegialiCommissioneDTO> getRows() {
        return rows;
    }

    public void setRows(List<OrganiCollegialiCommissioneDTO> rows) {
        this.rows = rows;
    }
    
    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create();
        return gson.toJson(this);
    }
}
