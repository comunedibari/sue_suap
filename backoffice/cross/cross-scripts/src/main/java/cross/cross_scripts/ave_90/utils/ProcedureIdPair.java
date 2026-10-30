package cross.cross_scripts.ave_90.utils;

import it.wego.cross.xml.Procedimento;

public class ProcedureIdPair {
	private Procedimento procedimento;
	private String codIntervento;
	
	public ProcedureIdPair(Procedimento procedimento, String codIntervento) {
		super();
		this.procedimento = procedimento;
		this.codIntervento = codIntervento;
	}
	public Procedimento getProcedimento() {
		return procedimento;
	}
	public void setProcedimento(Procedimento procedimento) {
		this.procedimento = procedimento;
	}
	public String getCodIntervento() {
		return codIntervento;
	}
	public void setCodIntervento(String codIntervento) {
		this.codIntervento = codIntervento;
	}
	
	
}
