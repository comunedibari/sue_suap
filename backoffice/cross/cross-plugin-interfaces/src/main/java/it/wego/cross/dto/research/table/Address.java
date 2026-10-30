package it.wego.cross.dto.research.table;

import java.io.Serializable;

import it.wego.cross.dto.AbstractDTO;

public class Address extends AbstractDTO implements Serializable {
	private String streetName;
	private String streetNumber;
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	public String getStreetNumber() {
		return streetNumber;
	}
	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}
	
}
