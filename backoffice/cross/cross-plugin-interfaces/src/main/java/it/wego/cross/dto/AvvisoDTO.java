package it.wego.cross.dto;

public class AvvisoDTO {
	
	private int idAvviso;
	private String testo;
	private String stringScadenza;
	private String action;
	

	public int getIdAvviso() {
		return idAvviso;
	}
	public void setIdAvviso(int idAvviso) {
		this.idAvviso = idAvviso;
	}
	public String getTesto() {
		return testo;
	}
	public void setTesto(String testo) {
		this.testo = testo;
	}
	public String getStringScadenza() {
		return stringScadenza;
	}
	public void setStringScadenza(String stringScadenza) {
		this.stringScadenza = stringScadenza;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}

	
	

}
