package operazioni;

public class VersioneElettronica {
	private String NroAttachmentAssociato;
	private String NomeFile;
	private String FlagVersPubblicata;


	// Getter Methods 

	public String getNroAttachmentAssociato() {
		return NroAttachmentAssociato;
	}

	public String getNomeFile() {
		return NomeFile;
	}

	public String getFlagVersPubblicata() {
		return FlagVersPubblicata;
	}

	// Setter Methods 

	public void setNroAttachmentAssociato(String NroAttachmentAssociato) {
		this.NroAttachmentAssociato = NroAttachmentAssociato;
	}

	public void setNomeFile(String NomeFile) {
		this.NomeFile = NomeFile;
	}

	public void setFlagVersPubblicata(String FlagVersPubblicata) {
		this.FlagVersPubblicata = FlagVersPubblicata;
	}
}