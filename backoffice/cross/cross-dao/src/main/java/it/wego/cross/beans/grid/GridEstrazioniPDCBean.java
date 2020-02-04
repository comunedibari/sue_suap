package it.wego.cross.beans.grid;

import java.util.List;

import it.wego.cross.dto.EstrazioniCilaDTO;
import it.wego.cross.dto.EstrazioniPdcDTO;
import it.wego.cross.dto.EstrazioniSciaDTO;


public class GridEstrazioniPDCBean extends GridComponentBean {
	
	private List<EstrazioniPdcDTO> rows;

	public List<EstrazioniPdcDTO> getRows() {
		return rows;
	}

	public void setRows(List<EstrazioniPdcDTO> pratiche) {
		this.rows = pratiche;
	}

}