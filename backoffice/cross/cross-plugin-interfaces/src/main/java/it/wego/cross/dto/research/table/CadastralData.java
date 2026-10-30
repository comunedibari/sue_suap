package it.wego.cross.dto.research.table;

import java.io.Serializable;

import it.wego.cross.dto.AbstractDTO;

public class CadastralData extends AbstractDTO implements Serializable {
	
	private String section;
	private String sheet;
	private String map;
	private String subordinate;
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public String getSheet() {
		return sheet;
	}
	public void setSheet(String sheet) {
		this.sheet = sheet;
	}
	public String getMap() {
		return map;
	}
	public void setMap(String map) {
		this.map = map;
	}
	public String getSubordinate() {
		return subordinate;
	}
	public void setSubordinate(String subordinate) {
		this.subordinate = subordinate;
	}

	
	
}
