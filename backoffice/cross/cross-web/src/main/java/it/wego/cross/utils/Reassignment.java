package it.wego.cross.utils;

import java.util.List;

import it.wego.cross.dto.dozer.UtenteDTO;



public class Reassignment {
	private List<UtenteDTO> users;
	private UtenteDTO defaultUser;
	private Integer defaultDays;
	public List<UtenteDTO> getUsers() {
		return users;
	}
	public void setUsers(List<UtenteDTO> users) {
		this.users = users;
	}
	public UtenteDTO getDefaultUser() {
		return defaultUser;
	}
	public void setDefaultUser(UtenteDTO defaultUser) {
		this.defaultUser = defaultUser;
	}
	public Integer getDefaultDays() {
		return defaultDays;
	}
	public void setDefaultDays(Integer defaultDays) {
		this.defaultDays = defaultDays;
	}
	
	
}
