package operazioni;

public class DatiEntrata {
	private String DataOraArrivo;
	MittenteEsterno MittenteEsternoObject;


	// Getter Methods 

	public String getDataOraArrivo() {
		return DataOraArrivo;
	}

	public MittenteEsterno getMittenteEsterno() {
		return MittenteEsternoObject;
	}

	// Setter Methods 

	public void setDataOraArrivo(String DataOraArrivo) {
		this.DataOraArrivo = DataOraArrivo;
	}

	public void setMittenteEsterno(MittenteEsterno MittenteEsternoObject) {
		this.MittenteEsternoObject = MittenteEsternoObject;
	}
}