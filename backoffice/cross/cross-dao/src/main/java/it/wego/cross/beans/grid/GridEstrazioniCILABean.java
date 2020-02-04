package it.wego.cross.beans.grid;

import java.util.List;

import it.wego.cross.dto.EstrazioniCilaDTO;


public class GridEstrazioniCILABean extends GridComponentBean {
	
	private List<EstrazioniCilaDTO> rows;

	public List<EstrazioniCilaDTO> getRows() {
		return rows;
	}

	public void setRows(List<EstrazioniCilaDTO> rows) {
		this.rows = rows;
	}

}
