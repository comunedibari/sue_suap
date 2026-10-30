package it.wego.cross.cross_beans;

import java.util.ArrayList;
import java.util.Date;


public class MATRIMONIO{
	public ArrayList<String> riferimento_atto_matrimonio;
	public ArrayList<String> comune_celebrazione;
	public ArrayList<String> comune_trascrizione;
	public ArrayList<Date> data_matrimonio;
	
	public ArrayList<String> getRiferimento_atto_matrimonio() {
		return riferimento_atto_matrimonio;
	}
	public void setRiferimento_atto_matrimonio(ArrayList<String> riferimento_atto_matrimonio) {
		this.riferimento_atto_matrimonio = riferimento_atto_matrimonio;
	}
	public ArrayList<String> getComune_celebrazione() {
		return comune_celebrazione;
	}
	public void setComune_celebrazione(ArrayList<String> comune_celebrazione) {
		this.comune_celebrazione = comune_celebrazione;
	}
	public ArrayList<String> getComune_trascrizione() {
		return comune_trascrizione;
	}
	public void setComune_trascrizione(ArrayList<String> comune_trascrizione) {
		this.comune_trascrizione = comune_trascrizione;
	}
	public ArrayList<Date> getData_matrimonio() {
		return data_matrimonio;
	}
	public void setData_matrimonio(ArrayList<Date> data_matrimonio) {
		this.data_matrimonio = data_matrimonio;
	}
	
}