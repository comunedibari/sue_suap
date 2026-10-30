package it.wego.auriga.xml.allegati;

public class AllegatiProtocollati {

	private int idAllegato;
	
	private String nomeFileAllegato;

	private byte[] contentFileBase64;
	
	public int getIdAllegato() {
		return idAllegato;
	}
	public void setIdAllegato(int idAllegato) {
		this.idAllegato = idAllegato;
	}
	public String getNomeFileAllegato() {
		return nomeFileAllegato;
	}
	public void setNomeFileAllegato(String nomeFileAllegato) {
		this.nomeFileAllegato = nomeFileAllegato;
	}
	public byte[] getContentFileBase64() {
		return contentFileBase64;
	}
	public void setContentFileBase64(byte[] contentFileBase64) {
		this.contentFileBase64 = contentFileBase64;
	}
	
}
