package it.wego.cross.cross_dms.sql;

public class PraticaCross{
	public int idPratica;
	public String identificativoPratica;
	public String oggettoPratica;
	public String protocollo;
	public String vecchioProtocollo;
	public String codRegistro;
	public int annoRiferimento;
	public int idPraticaPadre;
	public crossSTATOPRATICA statoPratica;
	public String dataApertura;
	public String dataChiusura;
	public crossRECAPITO recapito;
	public crossPROCESSO processo;
	public crossUTENTE utente;
	public crossCOMUNE comune;
	public String dataRicezione;
	public int idStaging;
	public int idModello;
	public String responsabileProcedimento;
	public String dataProtocollazione;
	public String dataVecchiaProtocollazione;
	public int giorniSospensione;
	public int statoEmail;
	public crossPROCEDIMENTOENTE procedimentoEnte;
	public String ufficioRiferimento;
	public String identificativoEsterno;
	public String integrazione;
	public String dataProtSuap;
	public String protSuap;
	public int idTipoProcedimentoSuap;
	public int idTipoInterventoSuap;
	
	public int getIdPratica() {
		return idPratica;
	}
	public void setIdPratica(int idPratica) {
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
	public crossSTATOPRATICA getStatoPratica() {
		return statoPratica;
	}
	public void setStatoPratica(crossSTATOPRATICA statoPratica) {
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
	public crossRECAPITO getRecapito() {
		return recapito;
	}
	public void setRecapito(crossRECAPITO recapito) {
		this.recapito = recapito;
	}
	public crossPROCESSO getProcesso() {
		return processo;
	}
	public void setProcesso(crossPROCESSO processo) {
		this.processo = processo;
	}
	public crossUTENTE getUtente() {
		return utente;
	}
	public void setUtente(crossUTENTE utente) {
		this.utente = utente;
	}
	public crossCOMUNE getComune() {
		return comune;
	}
	public void setComune(crossCOMUNE comune) {
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
	public crossPROCEDIMENTOENTE getProcedimentoEnte() {
		return procedimentoEnte;
	}
	public void setProcedimentoEnte(crossPROCEDIMENTOENTE procedimentoEnte) {
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



