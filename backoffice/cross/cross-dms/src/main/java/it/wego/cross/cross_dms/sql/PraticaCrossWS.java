package it.wego.cross.cross_dms.sql;
/*
{
    "idPratica": null,
    "identificativoPratica": "12345678AAAAA9CH",
    "oggettoPratica": "Richiesta Mutuo 24",
    "protocollo": "No idea",
    "vecchioProtocollo": "Sto aggiornando",
    "codRegistro": "Codice 102",
    "annoRiferimento": 1990,
    "idPraticaPadre": 1,
    "statoPratica": {"idStatoPratica": 1},
    "dataApertura": "2002-10-19",
    "dataChiusura": "2019-10-19",
    "recapito": {"idRecapito": 6728},
    "processo": {"idProcesso": 22},
    "utente": {"idUtente": 1},
    "comune": {"idComune": 893},
    "dataRicezione": "2002-10-18",
    "idStaging": 1,
    "idModello": 1,
    "responsabileProcedimento": "Marco",
    "dataProtocollazione": "2002-10-25",
    "dataVecchiaProtocollazione": "2022-10-23",
    "giorniSospensione": 10,
    "statoEmail": 1,
    "procedimentoEnte": {"idProcedimentoEnte": 1509},
    "ufficioRiferimento": "Ufficio bari",
    "identificativoEsterno":"boh 2.0",
    "integrazione": "boh",
    "dataProtSuap": "2002-11-12",
    "protSuap": "boh",
    "idTipoProcedimentoSuap": 1,
    "idTipoInterventoSuap": 1

}
*/
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.annotation.JsonProperty;
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString, Root.class); 
*/

import java.text.ParseException;
import java.text.SimpleDateFormat;

import it.wego.cross.cross_dms.utilities.myUtility;

public class PraticaCrossWS {
	private Object idPratica;
	private String identificativoPratica;
	private String oggettoPratica;
	private String protocollo;
	private String vecchioProtocollo;
	private String codRegistro;
	private int annoRiferimento;
	private int idPraticaPadre;
	private StatoPratica statoPratica;
	private String dataApertura;
	private String dataChiusura;
	private Recapito recapito;
	private Processo processo;
	private Utente utente;
	private Comune comune;
	private String dataRicezione;
	private int idStaging;
	private int idModello;
	private String responsabileProcedimento;
	private String dataProtocollazione;
	private String dataVecchiaProtocollazione;
	private int giorniSospensione;
	private int statoEmail;
	private ProcedimentoEnte procedimentoEnte;
	private String ufficioRiferimento;
	private String identificativoEsterno;
	private String integrazione;
	private String dataProtSuap;
	private String protSuap;
	private int idTipoProcedimentoSuap;
	private int idTipoInterventoSuap;

	public PraticaCrossWS(Object idPratica, String identificativoPratica, String oggettoPratica, String protocollo,
			String vecchioProtocollo, String codRegistro, int annoRiferimento, int idPraticaPadre,
			int idStatoPratica, String dataApertura, String dataChiusura, int idrecapito, int idProcesso,
			int idUtente, int idComune, String dataRicezione, int idStaging, int idModello,
			String responsabileProcedimento, String dataProtocollazione, String dataVecchiaProtocollazione,
			int giorniSospensione, int statoEmail, int idProcedimentoEnte, String ufficioRiferimento,
			String identificativoEsterno, String integrazione, String dataProtSuap, String protSuap,
			int idTipoProcedimentoSuap, int idTipoInterventoSuap) {
		
		super();
		
		this.idPratica = idPratica;
		this.identificativoPratica = identificativoPratica;
		this.oggettoPratica = oggettoPratica;
		this.protocollo = protocollo;
		this.vecchioProtocollo = vecchioProtocollo;
		this.codRegistro = codRegistro;
		this.annoRiferimento = annoRiferimento;
		this.idPraticaPadre = idPraticaPadre;
		
		StatoPratica stp = new StatoPratica();
		stp.setIdStatoPratica(idStatoPratica);
		this.setStatoPratica(stp);
		
		java.util.Date dataTemp = null;
		try {
			String tmpData = (dataApertura != null && !dataApertura.isEmpty()) ? dataApertura.toString() : "31/05/2023";
			String formato_data = myUtility.get_format_date(tmpData);
			dataTemp = new SimpleDateFormat(formato_data).parse(tmpData);
			java.sql.Date sqlDate = new java.sql.Date(dataTemp.getTime());
			this.dataApertura = sqlDate.toString();
		} catch (ParseException e) {
			this.dataApertura = null;
		}
		
		try {
			String tmpData = (dataChiusura != null && !dataChiusura.isEmpty()) ? dataChiusura.toString() : "31/05/2023";
			String formato_data = myUtility.get_format_date(tmpData);
			dataTemp = new SimpleDateFormat(formato_data).parse(tmpData.toString());
			java.sql.Date sqlDate = new java.sql.Date(dataTemp.getTime());
			this.dataChiusura = sqlDate.toString();
		} catch (ParseException e) {
			this.dataChiusura = null;
		}
		
		Recapito rp = new Recapito();
		rp.setIdRecapito(idrecapito);		
		this.setRecapito(rp);;
		
		Processo prc = new Processo();
		prc.setIdProcesso(idProcesso);
		this.setProcesso(prc);

		Utente ute = new Utente();
		ute.setIdUtente(idUtente);
		this.setUtente(ute);
		
		Comune cm = new Comune();
		cm.setIdComune(idComune);
		this.setComune(cm);
		
		try {
			String tmpData = (dataRicezione != null && !dataRicezione.isEmpty()) ? dataRicezione.toString() : "31/05/2023";
			String formato_data = myUtility.get_format_date(tmpData);
			dataTemp = new SimpleDateFormat(formato_data).parse(tmpData.toString());
			java.sql.Date sqlDate = new java.sql.Date(dataTemp.getTime());
			this.dataRicezione = sqlDate.toString();
		} catch (ParseException e) {
			this.dataRicezione = null;
		}

		this.idStaging = idStaging;
		this.idModello = idModello;
		this.responsabileProcedimento = responsabileProcedimento;
		
		try {
			String tmpData = (dataProtocollazione != null && !dataProtocollazione.isEmpty()) ? dataProtocollazione.toString() : "31/05/2023";
			String formato_data = myUtility.get_format_date(tmpData);
			dataTemp = new SimpleDateFormat(formato_data).parse(tmpData.toString());
			java.sql.Date sqlDate = new java.sql.Date(dataTemp.getTime());
			this.dataProtocollazione = sqlDate.toString();
		} catch (ParseException e) {
			this.dataProtocollazione = null;
		}
		
		try {
			String tmpData = (dataVecchiaProtocollazione != null && !dataVecchiaProtocollazione.isEmpty()) ? dataVecchiaProtocollazione.toString() : "31/05/2023";
			String formato_data = myUtility.get_format_date(tmpData);
			dataTemp = new SimpleDateFormat(formato_data).parse(tmpData.toString());
			java.sql.Date sqlDate = new java.sql.Date(dataTemp.getTime());
			this.dataVecchiaProtocollazione = sqlDate.toString();
		} catch (ParseException e) {
			this.dataVecchiaProtocollazione = null;
		}
		
		this.giorniSospensione = giorniSospensione;
		this.statoEmail = statoEmail;
		
		ProcedimentoEnte proc = new ProcedimentoEnte();
		proc.setIdProcedimentoEnte(idProcedimentoEnte);
		this.setProcedimentoEnte(proc);
		
		this.ufficioRiferimento = ufficioRiferimento;
		this.identificativoEsterno = identificativoEsterno;
		this.integrazione = integrazione;
		
		try {
			String tmpData = (dataProtSuap != null && !dataProtSuap.isEmpty()) ? dataProtSuap.toString() : "31/05/2023";
			String formato_data = myUtility.get_format_date(tmpData);
			dataTemp = new SimpleDateFormat(formato_data).parse(tmpData.toString());
			java.sql.Date sqlDate = new java.sql.Date(dataTemp.getTime());
			this.dataProtSuap = sqlDate.toString();
		} catch (ParseException e) {
			this.dataProtSuap = null;
		}
		
		this.protSuap = protSuap;
		this.idTipoProcedimentoSuap = idTipoProcedimentoSuap;
		this.idTipoInterventoSuap = idTipoInterventoSuap;
	}
	
	public Object getIdPratica() {
		return idPratica;
	}
	public void setIdPratica(Object idPratica) {
		this.idPratica = idPratica;
	}
	public String getIdentificativoPratica() {
		return identificativoPratica;
	}
	public void setIdentificativoPratica(String identificativoPratica) {
		this.identificativoPratica = identificativoPratica;
	}
	public String getOggettoPratica() {
		return oggettoPratica;
	}
	public void setOggettoPratica(String oggettoPratica) {
		this.oggettoPratica = oggettoPratica;
	}
	public String getProtocollo() {
		return protocollo;
	}
	public void setProtocollo(String protocollo) {
		this.protocollo = protocollo;
	}
	public String getVecchioProtocollo() {
		return vecchioProtocollo;
	}
	public void setVecchioProtocollo(String vecchioProtocollo) {
		this.vecchioProtocollo = vecchioProtocollo;
	}
	public String getCodRegistro() {
		return codRegistro;
	}
	public void setCodRegistro(String codRegistro) {
		this.codRegistro = codRegistro;
	}
	public int getAnnoRiferimento() {
		return annoRiferimento;
	}
	public void setAnnoRiferimento(int annoRiferimento) {
		this.annoRiferimento = annoRiferimento;
	}
	public int getIdPraticaPadre() {
		return idPraticaPadre;
	}
	public void setIdPraticaPadre(int idPraticaPadre) {
		this.idPraticaPadre = idPraticaPadre;
	}
	public StatoPratica getStatoPratica() {
		return statoPratica;
	}
	public void setStatoPratica(StatoPratica statoPratica) {
		this.statoPratica = statoPratica;
	}
	public String getDataApertura() {
		return dataApertura;
	}
	public void setDataApertura(String dataApertura) {
		this.dataApertura = dataApertura;
	}
	public String getDataChiusura() {
		return dataChiusura;
	}
	public void setDataChiusura(String dataChiusura) {
		this.dataChiusura = dataChiusura;
	}
	public Recapito getRecapito() {
		return recapito;
	}
	public void setRecapito(Recapito recapito) {
		this.recapito = recapito;
	}
	public Processo getProcesso() {
		return processo;
	}
	public void setProcesso(Processo processo) {
		this.processo = processo;
	}
	public Utente getUtente() {
		return utente;
	}
	public void setUtente(Utente utente) {
		this.utente = utente;
	}
	public Comune getComune() {
		return comune;
	}
	public void setComune(Comune comune) {
		this.comune = comune;
	}
	public String getDataRicezione() {
		return dataRicezione;
	}
	public void setDataRicezione(String dataRicezione) {
		this.dataRicezione = dataRicezione;
	}
	public int getIdStaging() {
		return idStaging;
	}
	public void setIdStaging(int idStaging) {
		this.idStaging = idStaging;
	}
	public int getIdModello() {
		return idModello;
	}
	public void setIdModello(int idModello) {
		this.idModello = idModello;
	}
	public String getResponsabileProcedimento() {
		return responsabileProcedimento;
	}
	public void setResponsabileProcedimento(String responsabileProcedimento) {
		this.responsabileProcedimento = responsabileProcedimento;
	}
	public String getDataProtocollazione() {
		return dataProtocollazione;
	}
	public void setDataProtocollazione(String dataProtocollazione) {
		this.dataProtocollazione = dataProtocollazione;
	}
	public String getDataVecchiaProtocollazione() {
		return dataVecchiaProtocollazione;
	}
	public void setDataVecchiaProtocollazione(String dataVecchiaProtocollazione) {
		this.dataVecchiaProtocollazione = dataVecchiaProtocollazione;
	}
	public int getGiorniSospensione() {
		return giorniSospensione;
	}
	public void setGiorniSospensione(int giorniSospensione) {
		this.giorniSospensione = giorniSospensione;
	}
	public int getStatoEmail() {
		return statoEmail;
	}
	public void setStatoEmail(int statoEmail) {
		this.statoEmail = statoEmail;
	}
	public ProcedimentoEnte getProcedimentoEnte() {
		return procedimentoEnte;
	}
	public void setProcedimentoEnte(ProcedimentoEnte procedimentoEnte) {
		this.procedimentoEnte = procedimentoEnte;
	}
	public String getUfficioRiferimento() {
		return ufficioRiferimento;
	}
	public void setUfficioRiferimento(String ufficioRiferimento) {
		this.ufficioRiferimento = ufficioRiferimento;
	}
	public String getIdentificativoEsterno() {
		return identificativoEsterno;
	}
	public void setIdentificativoEsterno(String identificativoEsterno) {
		this.identificativoEsterno = identificativoEsterno;
	}
	public String getIntegrazione() {
		return integrazione;
	}
	public void setIntegrazione(String integrazione) {
		this.integrazione = integrazione;
	}
	public String getDataProtSuap() {
		return dataProtSuap;
	}
	public void setDataProtSuap(String dataProtSuap) {
		this.dataProtSuap = dataProtSuap;
	}
	public String getProtSuap() {
		return protSuap;
	}
	public void setProtSuap(String protSuap) {
		this.protSuap = protSuap;
	}
	public int getIdTipoProcedimentoSuap() {
		return idTipoProcedimentoSuap;
	}
	public void setIdTipoProcedimentoSuap(int idTipoProcedimentoSuap) {
		this.idTipoProcedimentoSuap = idTipoProcedimentoSuap;
	}
	public int getIdTipoInterventoSuap() {
		return idTipoInterventoSuap;
	}
	public void setIdTipoInterventoSuap(int idTipoInterventoSuap) {
		this.idTipoInterventoSuap = idTipoInterventoSuap;
	}
}

class Comune{
	private int idComune;

	public int getIdComune() {
		return idComune;
	}

	public void setIdComune(int idComune) {
		this.idComune = idComune;
	}
}

class ProcedimentoEnte{
	private int idProcedimentoEnte;
	
	public int getIdProcedimentoEnte() {
		return idProcedimentoEnte;
	}

	public void setIdProcedimentoEnte(int idProcedimentoEnte) {
		this.idProcedimentoEnte = idProcedimentoEnte;
	}
}

class Processo{
	private int idProcesso;

	public int getIdProcesso() {
		return idProcesso;
	}

	public void setIdProcesso(int idProcesso) {
		this.idProcesso = idProcesso;
	}
	
}

class Recapito{
	private int idRecapito;

	public int getIdRecapito() {
		return idRecapito;
	}

	public void setIdRecapito(int idRecapito) {
		this.idRecapito = idRecapito;
	}
}

class StatoPratica{
	private int idStatoPratica;

	public int getIdStatoPratica() {
		return idStatoPratica;
	}

	public void setIdStatoPratica(int idStatoPratica) {
		this.idStatoPratica = idStatoPratica;
	}
}

class Utente{
	private int idUtente;

	public int getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}
}

