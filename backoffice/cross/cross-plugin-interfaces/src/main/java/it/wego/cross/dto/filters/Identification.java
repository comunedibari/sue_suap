package it.wego.cross.dto.filters;

public class Identification {
	
	private String code;
	private String surname;
	private String name;
	private String companyName;
	
	
	public Identification(String code, String surname, String name,  String companyName) {
		super();
		this.code = code;
		this.name = name;
		this.surname = surname;
		this.companyName = companyName;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Override
	public String toString() {
		return "Identification [code=" + code + ", surname=" + surname + ", name=" + name + ", companyName="
				+ companyName + "]";
	}
	
	
}
