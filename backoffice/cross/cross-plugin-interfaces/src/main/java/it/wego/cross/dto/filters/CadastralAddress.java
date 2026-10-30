package it.wego.cross.dto.filters;

import java.util.Objects;

public class CadastralAddress {

	private String streetName;
	
	private String streetNumber;
	
	private String townName;
	
	private Integer townId;

	public String getStreetName() {
		return streetName;
	}

	public CadastralAddress setStreetName(String streetName) {
		this.streetName = streetName;
		return this;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public CadastralAddress setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
		return this;
	}

	public String getTownName() {
		return townName;
	}

	public CadastralAddress setTownName(String townName) {
		this.townName = townName;
		return this;
	}

	public Integer getTownId() {
		return townId;
	}

	public CadastralAddress setTownId(String townId) {
		this.townId = townId != null? Integer.parseInt(townId): null;
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hash(streetName, streetNumber, townId, townName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CadastralAddress other = (CadastralAddress) obj;
		return Objects.equals(streetName, other.streetName) && Objects.equals(streetNumber, other.streetNumber)
				&& Objects.equals(townId, other.townId) && Objects.equals(townName, other.townName);
	}
	
	
}
