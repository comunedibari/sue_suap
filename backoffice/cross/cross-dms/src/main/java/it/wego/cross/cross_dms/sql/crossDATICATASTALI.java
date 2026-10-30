package it.wego.cross.cross_dms.sql;

public class crossDATICATASTALI {
	private int idImmobile;
	private Pratica pratica;
	private TipoSistemaCatastale tipoSistemaCatastale;
	private String sezione;
	private TipoUnita tipoUnita;
	private String foglio;
	private String mappale;
	private TipoParticella tipoParticella;
	private String estensioneParticella;
	private String subalterno;
	private String comuneCensuario;
	private String codImmobile;
	private String categoria;
	
	public String getIdImmobile() {
		return null;
	}
	public void setIdImmobile(int idImmobile) {
		this.idImmobile = idImmobile;
	}
	public Pratica getPratica() {
		return pratica;
	}
	public void setPratica(Pratica pratica) {
		this.pratica = pratica;
	}
	public TipoSistemaCatastale getTipoSistemaCatastale() {
		return tipoSistemaCatastale;
	}
	public void setTipoSistemaCatastale(TipoSistemaCatastale tipoSistemaCatastale) {
		this.tipoSistemaCatastale = tipoSistemaCatastale;
	}
	public String getSezione() {
		return sezione;
	}
	public void setSezione(String sezione) {
		this.sezione = sezione;
	}
	public TipoUnita getTipoUnita() {
		return tipoUnita;
	}
	public void setTipoUnita(TipoUnita tipoUnita) {
		this.tipoUnita = tipoUnita;
	}
	public String getFoglio() {
		return foglio;
	}
	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}
	public String getMappale() {
		return mappale;
	}
	public void setMappale(String mappale) {
		this.mappale = mappale;
	}
	public TipoParticella getTipoParticella() {
		return tipoParticella;
	}
	public void setTipoParticella(TipoParticella tipoParticella) {
		this.tipoParticella = tipoParticella;
	}
	public String getEstensioneParticella() {
		return estensioneParticella;
	}
	public void setEstensioneParticella(String estensioneParticella) {
		this.estensioneParticella = estensioneParticella;
	}
	public String getSubalterno() {
		return subalterno;
	}
	public void setSubalterno(String subalterno) {
		this.subalterno = subalterno;
	}
	public String getComuneCensuario() {
		return comuneCensuario;
	}
	public void setComuneCensuario(String comuneCensuario) {
		this.comuneCensuario = comuneCensuario;
	}
	public String getCodImmobile() {
		return codImmobile;
	}
	public void setCodImmobile(String codImmobile) {
		this.codImmobile = codImmobile;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}	
	
	public void setDatiImmobile(int idPratica, int idTipoParticella, int idTipoSistemaCatastale, int idTipoUnita)
	{
		Pratica pra = new Pratica();
		pra.setIdPratica(idPratica);
		this.setPratica(pra);
		
		TipoParticella tp = new TipoParticella();
		tp.setIdTipoParticella(idTipoParticella);
		this.setTipoParticella(tp);
		
		TipoSistemaCatastale tsc = new TipoSistemaCatastale();
		tsc.setIdTipoSistemaCatastale(idTipoSistemaCatastale);
		this.setTipoSistemaCatastale(tsc);
		
		TipoUnita tu = new TipoUnita();
		tu.setIdTipoUnita(idTipoUnita);
		this.setTipoUnita(tu);					
	}
}

class Pratica{
	private int idPratica;

	public int getIdPratica() {
		return idPratica;
	}

	public void setIdPratica(int idPratica) {
		this.idPratica = idPratica;
	}
}

class TipoParticella{
	private int idTipoParticella;

	public int getIdTipoParticella() {
		return idTipoParticella;
	}

	public void setIdTipoParticella(int idTipoParticella) {
		this.idTipoParticella = idTipoParticella;
	}
}

class TipoSistemaCatastale{
	private int idTipoSistemaCatastale;

	public int getIdTipoSistemaCatastale() {
		return idTipoSistemaCatastale;
	}

	public void setIdTipoSistemaCatastale(int idTipoSistemaCatastale) {
		this.idTipoSistemaCatastale = idTipoSistemaCatastale;
	}
}

class TipoUnita{
	private int idTipoUnita;

	public int getIdTipoUnita() {
		return idTipoUnita;
	}

	public void setIdTipoUnita(int idTipoUnita) {
		this.idTipoUnita = idTipoUnita;
	}
}