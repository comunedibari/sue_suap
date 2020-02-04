package it.wego.cross.beans.grid;

import java.util.List;

import it.wego.cross.dto.EstrazioniCilaDTO;
import it.wego.cross.dto.EstrazioniSciaDTO;


public class GridEstrazioniSCIABean extends GridComponentBean {
	
	private List<EstrazioniSciaDTO> rows;

	public List<EstrazioniSciaDTO> getRows() {
		return rows;
	}

	public void setRows(List<EstrazioniSciaDTO> pratiche) {
		this.rows = pratiche;
	}

}