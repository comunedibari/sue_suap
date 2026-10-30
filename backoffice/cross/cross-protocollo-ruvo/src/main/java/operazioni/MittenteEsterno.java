package operazioni;


public class MittenteEsterno {
	private String FlagFisica;
	private String Denominazione_Cognome;
	private String CodiceFiscale;


	// Getter Methods 

	public String getFlagFisica() {
		return FlagFisica;
	}

	public String getDenominazione_Cognome() {
		return Denominazione_Cognome;
	}

	public String getCodiceFiscale() {
		return CodiceFiscale;
	}

	// Setter Methods 

	public void setFlagFisica(String FlagFisica) {
		this.FlagFisica = FlagFisica;
	}

	public void setDenominazione_Cognome(String Denominazione_Cognome) {
		this.Denominazione_Cognome = Denominazione_Cognome;
	}

	public void setCodiceFiscale(String CodiceFiscale) {
		this.CodiceFiscale = CodiceFiscale;
	}
}