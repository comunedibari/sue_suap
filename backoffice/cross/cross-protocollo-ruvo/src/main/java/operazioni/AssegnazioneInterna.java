package operazioni;

public class AssegnazioneInterna {
	UO UOObject;
	private String FlagPerConoscenza;


	// Getter Methods 

	public UO getUO() {
		return UOObject;
	}

	public String getFlagPerConoscenza() {
		return FlagPerConoscenza;
	}

	// Setter Methods 

	public void setUO(UO UOObject) {
		this.UOObject = UOObject;
	}

	public void setFlagPerConoscenza(String FlagPerConoscenza) {
		this.FlagPerConoscenza = FlagPerConoscenza;
	}
}