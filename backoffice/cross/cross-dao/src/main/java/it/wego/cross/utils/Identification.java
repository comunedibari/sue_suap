package it.wego.cross.utils;

public class Identification {
	private String name;
	private String surname;
	private String fullName;
	private boolean splitted;
	
	
	public Identification(String fullName) {
		this(fullName, null,null);
		splitted = false;
	}
	
	public Identification(String fullName, String surname, String name) {
		super();
		this.name = name;
		this.surname = surname;
		this.fullName = fullName;
		splitted = true;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getFullName() {
		return fullName;
	}
	
	public boolean hasSplitted() {
		return splitted;
	}
	
	
}
