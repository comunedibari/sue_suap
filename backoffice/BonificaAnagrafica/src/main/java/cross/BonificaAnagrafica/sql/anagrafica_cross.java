package cross.BonificaAnagrafica.sql;

public class anagrafica_cross
{
	private int _id_pratica;  	
	private int _id_anagrafica;  
	private int _id_tipo_ruolo; 	
	private String _tipo_anagrafica_cross;
	private String _denominazione;
	private int _nuova_anagrafica;
	private String _cognome_cross;
	private String _nome_cross;
	private String _codice_fiscale_cross;
	private String _ruolo_cross;
	private String _partita_iva_cross;
	private String _flg_individuale_cross;
	
	public anagrafica_cross(
			int id_pratica,  	
			int id_anagrafica,  
			int id_tipo_ruolo, 
			String _tipo_anagrafica_cross, 
			String _denominazione, 
			int _nuova_anagrafica,
			String _cognome_cross, 
			String _nome_cross, 
			String _codice_fiscale_cross, 
			String _ruolo_cross,
			String _partita_iva_cross, 
			String _flg_individuale_cross) {
		
		super();
		this._id_pratica = id_pratica;  	
		this._id_anagrafica = id_anagrafica;  
		this._id_tipo_ruolo = id_tipo_ruolo; 
		
		this._tipo_anagrafica_cross = _tipo_anagrafica_cross;
		this._denominazione = _denominazione;
		this._nuova_anagrafica = _nuova_anagrafica;
		this._cognome_cross = _cognome_cross;
		this._nome_cross = _nome_cross;
		this._codice_fiscale_cross = _codice_fiscale_cross;
		this._ruolo_cross = _ruolo_cross;
		this._partita_iva_cross = _partita_iva_cross;
		this._flg_individuale_cross = _flg_individuale_cross;
	}

	public String to_string() {
		return ( " this._id_pratica                    :" + this._id_pratica 
		+ " this._id_anagrafica                 :" + this._id_anagrafica
		+ " this._id_tipo_ruolo                 :" + this._id_tipo_ruolo
		+ " this._tipo_anagrafica_cross         :" + this._tipo_anagrafica_cross 
		+ " this._denominazione                 :" + this._denominazione 
		+ " this._nuova_anagrafica              :" + this._nuova_anagrafica 
		+ " this._cognome_cross                 :" + this._cognome_cross 
		+ " this._nome_cross                    :" + this._nome_cross 
		+ " this._codice_fiscale_cross          :" + this._codice_fiscale_cross 
		+ " this._ruolo_cross                   :" + this._ruolo_cross 
		+ " this._partita_iva_cross             :" + this._partita_iva_cross 
		+ " this._flg_individuale_cross         :" + this._flg_individuale_cross); 
	}
	
	public int getId_pratica() {
		return _id_pratica;
	}

	public void setId_pratica(int id_pratica) {
		this._id_pratica = id_pratica;
	}

	public int getId_anagrafica() {
		return _id_anagrafica;
	}

	public void setId_anagrafica(int id_anagrafica) {
		this._id_anagrafica = id_anagrafica;
	}

	public int getId_tipo_ruolo() {
		return _id_tipo_ruolo;
	}

	public void setId_tipo_ruolo(int id_tipo_ruolo) {
		this._id_tipo_ruolo = id_tipo_ruolo;
	}
	public String get_tipo_anagrafica_cross() {
		return _tipo_anagrafica_cross;
	}
	public void set_tipo_anagrafica_cross(String _tipo_anagrafica_cross) {
		this._tipo_anagrafica_cross = _tipo_anagrafica_cross;
	}
	public String get_denominazione() {
		return _denominazione;
	}
	public void set_denominazione(String _denominazione) {
		this._denominazione = _denominazione;
	}
	public int get_nuova_anagrafica() {
		return _nuova_anagrafica;
	}
	public void set_nuova_anagrafica(int _nuova_anagrafica) {
		this._nuova_anagrafica = _nuova_anagrafica;
	}
	public String get_cognome_cross() {
		return _cognome_cross;
	}
	public void set_cognome_cross(String _cognome_cross) {
		this._cognome_cross = _cognome_cross;
	}
	public String get_nome_cross() {
		return _nome_cross;
	}
	public void set_nome_cross(String _nome_cross) {
		this._nome_cross = _nome_cross;
	}
	public String get_codice_fiscale_cross() {
		return _codice_fiscale_cross;
	}
	public void set_codice_fiscale_cross(String _codice_fiscale_cross) {
		this._codice_fiscale_cross = _codice_fiscale_cross;
	}
	public String get_ruolo_cross() {
		return _ruolo_cross;
	}
	public void set_ruolo_cross(String _ruolo_cross) {
		this._ruolo_cross = _ruolo_cross;
	}
	public String get_partita_iva_cross() {
		return _partita_iva_cross;
	}
	public void set_partita_iva_cross(String _partita_iva_cross) {
		this._partita_iva_cross = _partita_iva_cross;
	}
	public String get_flg_individuale_cross() {
		return _flg_individuale_cross;
	}
	public void set_flg_individuale_cross(String _flg_individuale_cross) {
		this._flg_individuale_cross = _flg_individuale_cross;
	}	
}