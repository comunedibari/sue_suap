package it.wego.auriga.xml.allegati;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.wego.cross.beans.AllegatoBean;
import it.wego.cross.client.xml.extractmulti.OggDiTabDiSistemaType;
import it.wego.cross.client.xml.extractmulti.OutputFilesUD;


public class MyOutputFileUD {
	
	private List<MyDatiFileEstratto> DatiFileEstratto;

	public List<MyDatiFileEstratto> getDatiFileEstratto() {
		if (DatiFileEstratto == null) {
			DatiFileEstratto = new ArrayList<MyDatiFileEstratto>();
		}
		return this.DatiFileEstratto;

	}

	public void setDatiFileEstratto(List<MyDatiFileEstratto> datiFileEstratto) {
		DatiFileEstratto = datiFileEstratto;
	}

	public static class RelazioneVsUD { 
		private String CodId;
		
		private String Decodifica_Nome;

		public String getCodId() {
			return CodId;
		}
		public void setCodId(String codId) {
			CodId = codId;
		}
		public String getDecodifica_Nome() {
			return Decodifica_Nome;
		}
		public void setDecodifica_Nome(String decodifica_Nome) {
			Decodifica_Nome = decodifica_Nome;
		}
	}

	public static class TipoDoc { 
		private Object CodId;
		private Object Decodifica_Nome;

		public Object getCodId() {
			return CodId;
		}
		public void setCodId(Object codId) {
			CodId = codId;
		}
		public Object getDecodifica_Nome() {
			return Decodifica_Nome;
		}
		public void setDecodifica_Nome(Object decodifica_Nome) {
			Decodifica_Nome = decodifica_Nome;
		}
	}

	public static class MyDatiFileEstratto { 
		
		private int NroAttachment;

		private int IdDoc;

	    private String NomeFile;

		public int NroVersione;

	    private int NroUltimaVersione;

	    private RelazioneVsUD RelazioneVsUD;

	    private int NroAllegato;

	    private TipoDoc TipoDoc;

	    private String DesOggetto;				

		public int getNroAttachment() {
			return NroAttachment;
		}
		public void setNroAttachment(int nroAttachment) {
			this.NroAttachment = nroAttachment;
		}
		public int getIdDoc() {
			return this.IdDoc;
		}
		public void setIdDoc(int idDoc) {
			this.IdDoc = idDoc;
		}
		public String getNomeFile() {
			return this.NomeFile;
		}
		public void setNomeFile(String nomeFile) {
			this.NomeFile = nomeFile;
		}
		public int getNroVersione() {
			return this.NroVersione;
		}
		public void setNroVersione(int nroVersione) {
			this.NroVersione = nroVersione;
		}
		public int getNroUltimaVersione() {
			return this.NroUltimaVersione;
		}
		public void setNroUltimaVersione(int nroUltimaVersione) {
			this.NroUltimaVersione = nroUltimaVersione;
		}
		public RelazioneVsUD getRelazioneVsUD() {
			return RelazioneVsUD;
		}
		public void setRelazioneVsUD(RelazioneVsUD relazioneVsUD) {
			this.RelazioneVsUD = relazioneVsUD;
		}
		public TipoDoc getTipoDoc() {
			return TipoDoc;
		}
		public void setTipoDoc(TipoDoc tipoDoc) {
			this.TipoDoc = tipoDoc;
		}
		public String getDesOggetto() {
			return DesOggetto;
		}
		public void setDesOggetto(String desOggetto) {
			this.DesOggetto = desOggetto;
		}
		public int getNroAllegato() {
			return NroAllegato;
		}
		public void setNroAllegato(int nroAllegato) {
			this.NroAllegato = nroAllegato;
		}
	}
}
