package it.wego.auriga.xml;

import java.util.List;

public class GetIdMetadati {
	private int idUd;
	
	private int idDoc;

	private String nomeFile;
	
	private String nomeFileOriginale;
	
	private List<MetadatiAllegati> listMetadatiAllegati; 
	
	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getNomeFileOriginale() {
		return nomeFileOriginale;
	}

	public void setNomeFileOriginale(String nomeFileOriginale) {
		this.nomeFileOriginale = nomeFileOriginale;
	}

	public int getIdUd() {
		return idUd;
	}

	public void setIdUd(int idUd) {
		this.idUd = idUd;
	}

	public int getIdDoc() {
		return idDoc;
	}

	public void setIdDoc(int idDoc) {
		this.idDoc = idDoc;
	}

	/**
	 * @return the listMetadatiAllegati
	 */
	public List<MetadatiAllegati> getListMetadatiAllegati() {
		return listMetadatiAllegati;
	}

	/**
	 * @param listMetadatiAllegati the listMetadatiAllegati to set
	 */
	public void setListMetadatiAllegati(List<MetadatiAllegati> listMetadatiAllegati) {
		this.listMetadatiAllegati = listMetadatiAllegati;
	}

}
