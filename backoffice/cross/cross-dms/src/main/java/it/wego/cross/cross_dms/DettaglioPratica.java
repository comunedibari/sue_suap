package it.wego.cross.cross_dms;

import java.util.List;

import it.wego.cross.dto.AllegatoDTO;
import it.wego.cross.dto.NotaDTO;
import it.wego.cross.dto.dozer.IndirizzoInterventoDTO;
import it.wego.cross.entity.DatiCatastali;
import it.wego.cross.entity.PraticaAnagrafica;
import it.wego.cross.entity.PraticaProcedimenti;

public class DettaglioPratica {
	private List<PraticaAnagrafica> pratica;
	private List<PraticaProcedimenti> procedimenti;
	private List<DatiCatastali> daticatastali;
	private List<IndirizzoInterventoDTO> indIntervento;
	private List<AllegatoDTO> allegati;
	private List<NotaDTO> annotazioni;
	
	public List<NotaDTO> getAnnotazioni() {
		return annotazioni;
	}
	public void setAnnotazioni(List<NotaDTO> annotazioni) {
		this.annotazioni = annotazioni;
	}
	public List<PraticaAnagrafica> getPratica() {
		return pratica;
	}
	public void setPratica(List<PraticaAnagrafica> pratica) {
		this.pratica = pratica;
	}
	public List<PraticaProcedimenti> getProcedimenti() {
		return procedimenti;
	}
	public void setProcedimenti(List<PraticaProcedimenti> procedimenti) {
		this.procedimenti = procedimenti;
	}
	public List<DatiCatastali> getDaticatastali() {
		return daticatastali;
	}
	public void setDaticatastali(List<DatiCatastali> daticatastali) {
		this.daticatastali = daticatastali;
	}
	public List<IndirizzoInterventoDTO> getIndIntervento() {
		return indIntervento;
	}
	public void setIndIntervento(List<IndirizzoInterventoDTO> indIntervento) {
		this.indIntervento = indIntervento;
	}
	public List<AllegatoDTO> getAllegati() {
		return allegati;
	}
	public void setAllegati(List<AllegatoDTO> allegati) {
		this.allegati = allegati;
	}
}
