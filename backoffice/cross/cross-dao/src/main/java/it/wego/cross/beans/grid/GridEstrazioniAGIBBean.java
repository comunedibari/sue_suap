package it.wego.cross.beans.grid;

import java.util.List;

import it.wego.cross.dto.EstrazioniAgibDTO;


public class GridEstrazioniAGIBBean extends GridComponentBean {
	
	private List<EstrazioniAgibDTO> rows;

	public List<EstrazioniAgibDTO> getRows() {
		return rows;
	}

	public void setRows(List<EstrazioniAgibDTO> pratiche) {
		this.rows = pratiche;
	}

}