package cross.cross_scripts.external_events;

public class Pratica {
	Integer idPratica;
	String codPratica;
	String data;

	public Pratica(Integer idPratica, String data, String codPratica) {
		super();
		this.idPratica = idPratica;
		this.codPratica = codPratica;
		this.data = data;
	}
	public Integer getIdPratica() {
		return idPratica;
	}
	public void setIdPratica(Integer idPratica) {
		this.idPratica = idPratica;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		return "Pratica [idPratica=" + idPratica + ", codPratica=" + codPratica + ", data=" + data + "]";
	}
	
	
	
}
