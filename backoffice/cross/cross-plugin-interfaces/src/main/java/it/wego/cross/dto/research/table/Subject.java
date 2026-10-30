package it.wego.cross.dto.research.table;

import java.io.Serializable;

import it.wego.cross.dto.AbstractDTO;

public class Subject  extends AbstractDTO implements Serializable {
	private String surname;
	private String name;
	
	public Subject(String surname, String name) {
		this.surname = surname;
		this.name = name;
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
}
