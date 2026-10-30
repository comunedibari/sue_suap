package cross.BonificaAnagrafica.sql;

public class Confronta_ruoli_cross {
	private int id_tipo_ruolo_attuale;  	
	private String descrizione_attuale;
	private int id_tipo_ruolo_civilia;
	private String descrizione_civilia;
	
	public int getId_tipo_ruolo_attuale() {
		return id_tipo_ruolo_attuale;
	}
	public void setId_tipo_ruolo_attuale(int id_tipo_ruolo_attuale) {
		this.id_tipo_ruolo_attuale = id_tipo_ruolo_attuale;
	}
	public String getDescrizione_attuale() {
		return descrizione_attuale;
	}
	public void setDescrizione_attuale(String descrizione_attuale) {
		this.descrizione_attuale = descrizione_attuale;
	}
	public int getId_tipo_ruolo_civilia() {
		return id_tipo_ruolo_civilia;
	}
	public void setId_tipo_ruolo_civilia(int id_tipo_ruolo_civilia) {
		this.id_tipo_ruolo_civilia = id_tipo_ruolo_civilia;
	}
	public String getDescrizione_civilia() {
		return descrizione_civilia;
	}
	public void setDescrizione_civilia(String descrizione_civilia) {
		this.descrizione_civilia = descrizione_civilia;
	}
	
}
