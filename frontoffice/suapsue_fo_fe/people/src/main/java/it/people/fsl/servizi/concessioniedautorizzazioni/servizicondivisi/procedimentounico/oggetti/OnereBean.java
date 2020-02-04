package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti;

import java.io.Serializable;

public class OnereBean implements Serializable {

	public OnereBean() {
		setCodice("");
		this.setAeCodiceUtente("");
		setAeCodiceEnte("");
		setAeCodiceUfficio("");
		setAeTipoUfficio("");
		setCodiceDestinatario("");
		setDescrizioneDestinatario("");
		setDescrizione("");
		setImporto(0.0D);
		setRiversamentoAutomatico(false);
		setAccettaValoreZero(true);
// PC - Oneri MIP - inizio                
                setIdentificativoContabile("");
// PC - Oneri MIP - fine  
	}

	public OnereBean(String codice, String aeCodiceUtente, String aeCodiceEnte, String aeCodiceUfficio,
			String aeTipoUfficio, String codDestinatario, String desOnere,
			String desDestinatario, double importoOnere,
			boolean riversamentoAutomaticoOnere, 
			boolean accettaValoreZero
// PC - Oneri MIP - inizio    
                        ,String identificativoContabile
// PC - Oneri MIP - fine
                        ) {
		setCodice(codice);
		this.setAeCodiceUtente(aeCodiceUtente);
		setAeCodiceEnte(aeCodiceEnte);
		setAeCodiceUfficio(aeCodiceUfficio);
		setAeTipoUfficio(aeTipoUfficio);
		setCodiceDestinatario(codDestinatario);
		setDescrizioneDestinatario(desDestinatario);
		setDescrizione(desOnere);
		setImporto(importoOnere);
		setRiversamentoAutomatico(riversamentoAutomaticoOnere);
		setAccettaValoreZero(accettaValoreZero);
// PC - Oneri MIP - inizio    
                setIdentificativoContabile(identificativoContabile);
// PC - Oneri MIP - fine   
	}

	public final String getCodice() {
		return codice;
	}

	public final void setCodice(String codice) {
		this.codice = codice;
	}

	
	/**
	 * @return the aeCodiceUtente
	 */
	public final String getAeCodiceUtente() {
		return this.aeCodiceUtente;
	}

	/**
	 * @param aeCodiceUtente the aeCodiceUtente to set
	 */
	public final void setAeCodiceUtente(String aeCodiceUtente) {
		this.aeCodiceUtente = aeCodiceUtente;
	}

	public final String getAeCodiceEnte() {
		return aeCodiceEnte;
	}

	public final void setAeCodiceEnte(String aeCodiceEnte) {
		this.aeCodiceEnte = aeCodiceEnte;
	}

	public final String getAeCodiceUfficio() {
		return aeCodiceUfficio;
	}

	public final void setAeCodiceUfficio(String aeCodiceUfficio) {
		this.aeCodiceUfficio = aeCodiceUfficio;
	}

	public final String getAeTipoUfficio() {
		return aeTipoUfficio;
	}

	public final void setAeTipoUfficio(String aeTipoUfficio) {
		this.aeTipoUfficio = aeTipoUfficio;
	}

	public final String getCodiceDestinatario() {
		return codiceDestinatario;
	}

	public final void setCodiceDestinatario(String codiceDestinatario) {
		this.codiceDestinatario = codiceDestinatario;
	}

	public final String getDescrizione() {
		return descrizione;
	}

	public final void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public final String getDescrizioneDestinatario() {
		return descrizioneDestinatario;
	}

	public final void setDescrizioneDestinatario(String descrizioneDestinatario) {
		this.descrizioneDestinatario = descrizioneDestinatario;
	}

	public final double getImporto() {
		return importo;
	}

	public final void setImporto(double importo) {
		this.importo = importo;
	}

	public final boolean isRiversamentoAutomatico() {
		return riversamentoAutomatico;
	}

	public final void setRiversamentoAutomatico(boolean riversamentoAutomatico) {
		this.riversamentoAutomatico = riversamentoAutomatico;
	}
	
	public final boolean isAccettaValoreZero() {
		return accettaValoreZero;
	}

	public final void setAccettaValoreZero(boolean accettaValoreZero) {
		this.accettaValoreZero = accettaValoreZero;
	}

// PC - Oneri MIP - inizio 
        public final String getIdentificativoContabile() {
		return identificativoContabile;
	}    
        
	public final void setIdentificativoContabile(String identificativoContabile) {
		this.identificativoContabile = identificativoContabile;
	}        
// PC - Oneri MIP - fine

	private static final long serialVersionUID = 0x4998403eL;
	private String codice;
	private String aeCodiceUtente;
	private String aeCodiceEnte;
	private String aeCodiceUfficio;
	private String aeTipoUfficio;
	private String codiceDestinatario;
	private String descrizione;
	private String descrizioneDestinatario;
	private double importo;
	private boolean riversamentoAutomatico;
	private boolean accettaValoreZero;
// PC - Oneri MIP - inizio    
        private String identificativoContabile;
// PC - Oneri MIP - fine  
}
