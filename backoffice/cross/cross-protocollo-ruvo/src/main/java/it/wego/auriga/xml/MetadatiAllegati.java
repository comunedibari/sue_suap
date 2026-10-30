package it.wego.auriga.xml;

public class MetadatiAllegati{
	private int idDoc;
	private boolean isPrincipale;
	private String nomeFile;	
	private String nomeFileOriginale;

	/**
	 * @return the isPrincipale
	 */
	public boolean isPrincipale() {
		return isPrincipale;
	}

	/**
	 * @param isPrincipale the isPrincipale to set
	 */
	public void setPrincipale(boolean isPrincipale) {
		this.isPrincipale = isPrincipale;
	}

	/**
	 * @return the idDoc
	 */
	public int getIdDoc() {
		return idDoc;
	}

	/**
	 * @param idDoc the idDoc to set
	 */
	public void setIdDoc(int idDoc) {
		this.idDoc = idDoc;
	}

	/**
	 * @return the nomeFile
	 */
	public String getNomeFile() {
		return nomeFile;
	}

	/**
	 * @param nomeFile the nomeFile to set
	 */
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	/**
	 * @return the nomeFileOriginale
	 */
	public String getNomeFileOriginale() {
		return nomeFileOriginale;
	}

	/**
	 * @param nomeFileOriginale the nomeFileOriginale to set
	 */
	public void setNomeFileOriginale(String nomeFileOriginale) {
		this.nomeFileOriginale = nomeFileOriginale;
	}
}