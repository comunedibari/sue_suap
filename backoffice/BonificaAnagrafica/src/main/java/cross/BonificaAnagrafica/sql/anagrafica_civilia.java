package cross.BonificaAnagrafica.sql;

import cross.utilities.myUtility;

public class anagrafica_civilia {

	private int _id_anagrafica;	
	private String _tipoAnagrafica_civ;
	private String _cognome_civ;
	private String _nome_civ;
	private String _codiceFiscale_civ;
	private String _partitaIva_civ;
	private String _ruolo_civ;
	private String _sesso;

	public anagrafica_civilia(int _id_anagrafica, String tipoAnagrafica_civ_,String cognome_civ_,String nome_civ_,
			String codiceFiscale_civ_,String partitaIva_civ_,String ruolo_civ_, String sesso_)
	{
		this._tipoAnagrafica_civ = tipoAnagrafica_civ_;
		this._cognome_civ = cognome_civ_;
		this._nome_civ = nome_civ_;
		this._codiceFiscale_civ = codiceFiscale_civ_;
		this._partitaIva_civ = partitaIva_civ_;
		this._ruolo_civ = ruolo_civ_;
		this._sesso = sesso_;
	}
	public String to_string() {
		return (" this._tipoAnagrafica_civ: 	" + this._tipoAnagrafica_civ
				+ " this._cognome_civ:	        " + this._cognome_civ
				+ " this._nome_civ: 			" + this._nome_civ				
				+ " this._codiceFiscale_civ: 	" + this._codiceFiscale_civ
				+ " this._partitaIva_civ: 		" + this._partitaIva_civ	
				+ " this._ruolo_civ: 			" + this._ruolo_civ			
				+ " this._sesso: 				" + this._sesso);
	}
	public int get_id_anagrafica() {
		return _id_anagrafica;
	}

	public void set_id_anagrafica(int _id_anagrafica) {
		this._id_anagrafica = _id_anagrafica;
	}
	
	public String get_sesso() {
		return _sesso;
	}

	public void set_sesso(String _sesso) {
		this._sesso = _sesso;
	}

	public String gettipoAnagrafica_civ()
	{
		return this._tipoAnagrafica_civ;
	}
	public void settipoAnagrafica_civ(String value)
	{
		this._tipoAnagrafica_civ = value;
	}
	public String getcognome_civ()
	{
		return this._cognome_civ;
	}
	public void setcognome_civ(String value)
	{
		this._cognome_civ = value;
	}
	public String getnome_civ()
	{
		return this._nome_civ;
	}
	public void setnome_civ(String value)
	{
		this._nome_civ = value;
	}
	public String getcodiceFiscale_civ()
	{
		return this._codiceFiscale_civ;
	}
	public void setcodiceFiscale_civ(String value)
	{
		this._codiceFiscale_civ = value;
	}

	public String getpartitaIva_civ()
	{
		return this._partitaIva_civ;
	}
	public void setpartitaIva_civ(String value)
	{
		this._partitaIva_civ = value;
	}

	public String getruolo_civ()
	{
		return myUtility.replaceSingleQuote(this._ruolo_civ);
	}
	public void setruolo_civ(String value)
	{
		this._ruolo_civ = value;
	}
}