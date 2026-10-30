package it.wego.cross.entity;

public class LkTipoProcesso {

	private int _id_tipo_pratica;
	private int _id_processo;

	public LkTipoProcesso(int id_tipo_pratica_,int id_processo_)
	{
		this._id_tipo_pratica = id_tipo_pratica_;
		this._id_processo = id_processo_;
	}
	public int getid_tipo_pratica()
	{
		return this._id_tipo_pratica;
	}
	public void setid_tipo_pratica(int value)
	{
		this._id_tipo_pratica = value;
	}

	public int getid_processo()
	{
		return this._id_processo;
	}
	public void setid_processo(int value)
	{
		this._id_processo = value;
	}
	
}