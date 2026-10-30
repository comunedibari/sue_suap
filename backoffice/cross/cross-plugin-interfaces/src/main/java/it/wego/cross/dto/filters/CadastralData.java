package it.wego.cross.dto.filters;

public class CadastralData {

	private Integer unitTypeId; 
	
	private String map;
	private String subordinate;

	private String section;
	private String sheet;

	
	public Integer getUnitTypeId() {
		return unitTypeId;
	}
	
	public CadastralData setUnitTypeId(String unitTypeId) {
		if(unitTypeId != null)
			this.unitTypeId = Integer.parseInt(unitTypeId);
		return this;
	}
	
	public String getMap() {
		return map;
	}
	
	public CadastralData setMap(String map) {
		this.map = map;
		return this;
	}
	
	public String getSubordinate() {
		return subordinate;
	}
	
	public CadastralData setSubordinate(String subordinate) {
		this.subordinate = subordinate;
		return this;
	}
	
	public String getSection() {
		return section;
	}
	
	public CadastralData setSection(String section) {
		this.section = section;
		return this;
	}
	public String getSheet() {
		return sheet;
	}
	
	public CadastralData setSheet(String sheet) {
		this.sheet = sheet;
		return this;
	}
}
