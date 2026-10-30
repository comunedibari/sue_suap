package it.wego.auriga.xml.allegati;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

import it.wego.cross.beans.AllegatoBean;
import it.wego.cross.client.xml.extractmulti.OggDiTabDiSistemaType;
import it.wego.cross.client.xml.extractmulti.OutputFilesUD;


@XmlRootElement(name = "Output_FilesUD")
@XmlType(propOrder = { "DatiFileEstratto" })
public class Output_FilesUD {
	
	@XmlElement(name = "DatiFileEstratto")
	private List<DatiFileEstratto> DatiFileEstratto;

	public List<DatiFileEstratto> getDatiFileEstratto() {
		if (DatiFileEstratto == null) {
			DatiFileEstratto = new ArrayList<Output_FilesUD.DatiFileEstratto>();
		}
		return this.DatiFileEstratto;

	}

	public void setDatiFileEstratto(List<DatiFileEstratto> datiFileEstratto) {
		DatiFileEstratto = datiFileEstratto;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "CodId",
        "Decodifica_Nome"        
    })
	public static class RelazioneVsUD { 
		@XmlElement(name = "CodId", required = true)
		private String CodId;
		
		@XmlElement(name = "Decodifica_Nome", required = true)
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

	@XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "CodId",
        "Decodifica_Nome"        
    })
	public static class TipoDoc { 
		@XmlElement(name = "CodId", required = true)
		private Object CodId;
		@XmlElement(name = "Decodifica_Nome", required = true)
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

	@XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "NroAttachment",
        "IdDoc",        
        "NomeFile",
        "NroVersione",
        "NroUltimaVersione",
        "RelazioneVsUD",
        "NroAllegato",
        "TipoDoc",
        "DesOggetto"
    })
	public static class DatiFileEstratto { 
		
		@XmlElement(name = "NroAttachment", required = true)
		private int NroAttachment;

		@XmlElement(name = "IdDoc", required = true)
		private int IdDoc;

	    @XmlElement(name = "NomeFile", required = true)
	    private String NomeFile;

	    @XmlElement(name = "NroVersione", required = true)
		public int NroVersione;

	    @XmlElement(name = "NroUltimaVersione", required = true)
	    private int NroUltimaVersione;

	    @XmlElement(name = "RelazioneVsUD", required = true)
	    private RelazioneVsUD RelazioneVsUD;

	    @XmlElement(name = "NroAllegato")
	    private int NroAllegato;

	    @XmlElement(name = "TipoDoc", required = true)
	    private TipoDoc TipoDoc;

	    @XmlElement(name = "DesOggetto", required = true)
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
