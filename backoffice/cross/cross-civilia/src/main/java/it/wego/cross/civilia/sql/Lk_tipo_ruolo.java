package it.wego.cross.civilia.sql;

public class Lk_tipo_ruolo
{
	private int _id_tipo_ruolo;
	private String _descrizione;
	private String _cod_ruolo;
	private int _hidden;

	public Lk_tipo_ruolo(int id_tipo_ruolo_,String descrizione_,String cod_ruolo_, int hidden_)
	{
		this._id_tipo_ruolo = id_tipo_ruolo_;
		this._descrizione = descrizione_;
		this._cod_ruolo = cod_ruolo_;
		this._hidden = hidden_;
	}

	public int getid_tipo_ruolo()
	{
		return this._id_tipo_ruolo;
	}

	public void setid_tipo_ruolo(int value)
	{
		this._id_tipo_ruolo = value;
	}

	public String getdescrizione()
	{
		return this._descrizione;
	}
	
	public void setdescrizione(String value)
	{
		this._descrizione = value;
	}

	public String getcod_ruolo()
	{
		return this._cod_ruolo;
	}
	
	public void setcod_ruolo(String value)
	{
		this._cod_ruolo = value;
	}

	public int gethidden()
	{
		return this._hidden;
	}
	
	public void sethidden(int value)
	{
		this._hidden = value;
	}
}