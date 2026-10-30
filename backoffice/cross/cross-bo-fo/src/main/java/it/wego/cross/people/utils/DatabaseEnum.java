package it.wego.cross.people.utils;

public enum DatabaseEnum {

	PEOPLE_BARI("it.wego.cross-bo-fo-bari"),
	PEOPLE_G("it.wego.cross-bo-fo-g"),
	PEOPLE_M("it.wego.cross-bo-fo-m");
	
	private String value;
	
	DatabaseEnum(String value){
		this.value = value;
	}
	
	public String get() {
		return this.value;
	}
	
}
