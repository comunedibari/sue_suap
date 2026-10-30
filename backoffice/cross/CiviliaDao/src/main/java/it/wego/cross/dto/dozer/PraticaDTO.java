/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.dozer;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Gabriele
 */
public class PraticaDTO implements Serializable {

    private Integer idPratica;
    private String identificativoPratica;
    private String oggettoPratica;
    private Date dataApertura;
    private Date dataChiusura;
    private Date dataRicezione;
    private String responsabileProcedimento;
    private Integer giorniSospensione;
    private Integer idStaging;
    private ProcessoDTO idProcesso;
    private ProtocolloDTO protocollo;
    private String identificatoreProtocolloIstanza;
    private StatoMailDTO statoEmail;
    private StatoPraticaDTO idStatoPratica;
    private PraticaDTO idPraticaPadre;
    private EnteDTO ente;
    private ProcedimentoDTO procedimento;
    private ComuneDTO idComune;
    private UtenteDTO idUtente;
    private Integer idModello;
    private RecapitoDTO idRecapito;
    private List<PraticaEventoDTO> praticheEventiList;
    private List<ProcedimentoEnteDTO> endoProcedimentiList;
    private List<AllegatoDTO> allegati;
    private List<DatiCatastaliDTO> datiCatastaliList;
    private List<IndirizzoInterventoDTO> indirizziInterventoList;
    private List<NotaDTO> notePratica;
    private List<ScadenzaDTO> scadenzeList;
    private Integer numGiorniPrimaScadenza;

    private String esistenzaStradario;
    private String identificativoEsterno;
    private String esistenzaRicercaCatasto;
    private String integrazione;
    private String prot_suap;
    private String tipoInterventoSuap;
    private String tipoProcedimentoSuap;
    private Date data_prot_suap;

	public String getProt_suap() {
		return prot_suap;
	}

	public void setProt_suap(String prot_suap) {
		this.prot_suap = prot_suap;
	}

	public String getIntegrazione() {
		return integrazione;
	}

	public void setIntegrazione(String integrazione) {
		this.integrazione = integrazione;
	}

	public String getEsistenzaRicercaCatasto() {
        return esistenzaRicercaCatasto;
    }

    public void setEsistenzaRicercaCatasto(String esistenzaRicercaCatasto) {
        this.esistenzaRicercaCatasto = esistenzaRicercaCatasto;
    }

    private List<PraticaDTO> praticheAssociate;

    public String getEsistenzaStradario() {
        return esistenzaStradario;
    }

    public void setEsistenzaStradario(String esistenzaStradario) {
        this.esistenzaStradario = esistenzaStradario;
    }

    public Integer getIdPratica() {
        return idPratica;
    }

    public void setIdPratica(Integer idPratica) {
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

    public Date getDataApertura() {
        return dataApertura;
    }

    public void setDataApertura(Date dataApertura) {
        this.dataApertura = dataApertura;
    }

    public Date getDataChiusura() {
        return dataChiusura;
    }

    public void setDataChiusura(Date dataChiusura) {
        this.dataChiusura = dataChiusura;
    }

    public Date getDataRicezione() {
        return dataRicezione;
    }

    public void setDataRicezione(Date dataRicezione) {
        this.dataRicezione = dataRicezione;
    }

    public String getResponsabileProcedimento() {
        return responsabileProcedimento;
    }

    public void setResponsabileProcedimento(String responsabileProcedimento) {
        this.responsabileProcedimento = responsabileProcedimento;
    }

    public Integer getGiorniSospensione() {
        return giorniSospensione;
    }

    public void setGiorniSospensione(Integer giorniSospensione) {
        this.giorniSospensione = giorniSospensione;
    }

    public Integer getIdStaging() {
        return idStaging;
    }

    public void setIdStaging(Integer idStaging) {
        this.idStaging = idStaging;
    }

    public ProcessoDTO getIdProcesso() {
        return idProcesso;
    }

    public void setIdProcesso(ProcessoDTO idProcesso) {
        this.idProcesso = idProcesso;
    }

    public ProtocolloDTO getProtocollo() {
        return protocollo;
    }

    public void setProtocollo(ProtocolloDTO protocollo) {
        this.protocollo = protocollo;
    }

    public String getIdentificatoreProtocolloIstanza() {
        return identificatoreProtocolloIstanza;
    }

    public void setIdentificatoreProtocolloIstanza(String identificatoreProtocolloIstanza) {
        this.identificatoreProtocolloIstanza = identificatoreProtocolloIstanza;
    }

    public StatoMailDTO getStatoEmail() {
        return statoEmail;
    }

    public void setStatoEmail(StatoMailDTO statoEmail) {
        this.statoEmail = statoEmail;
    }

    public StatoPraticaDTO getIdStatoPratica() {
        return idStatoPratica;
    }

    public void setIdStatoPratica(StatoPraticaDTO idStatoPratica) {
        this.idStatoPratica = idStatoPratica;
    }

    public PraticaDTO getIdPraticaPadre() {
        return idPraticaPadre;
    }

    public void setIdPraticaPadre(PraticaDTO idPraticaPadre) {
        this.idPraticaPadre = idPraticaPadre;
    }

    public EnteDTO getEnte() {
        return ente;
    }

    public void setEnte(EnteDTO ente) {
        this.ente = ente;
    }

    public ProcedimentoDTO getProcedimento() {
        return procedimento;
    }

    public void setProcedimento(ProcedimentoDTO procedimento) {
        this.procedimento = procedimento;
    }

    public ComuneDTO getIdComune() {
        return idComune;
    }

    public void setIdComune(ComuneDTO idComune) {
        this.idComune = idComune;
    }

    public UtenteDTO getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(UtenteDTO idUtente) {
        this.idUtente = idUtente;
    }

    public Integer getIdModello() {
        return idModello;
    }

    public void setIdModello(Integer idModello) {
        this.idModello = idModello;
    }

    public RecapitoDTO getIdRecapito() {
        return idRecapito;
    }

    public void setIdRecapito(RecapitoDTO idRecapito) {
        this.idRecapito = idRecapito;
    }

    public List<PraticaEventoDTO> getPraticheEventiList() {
        return praticheEventiList;
    }

    public void setPraticheEventiList(List<PraticaEventoDTO> praticheEventiList) {
        this.praticheEventiList = praticheEventiList;
    }

    public List<ProcedimentoEnteDTO> getEndoProcedimentiList() {
        return endoProcedimentiList;
    }

    public void setEndoProcedimentiList(List<ProcedimentoEnteDTO> endoProcedimentiList) {
        this.endoProcedimentiList = endoProcedimentiList;
    }

    public String getIdentificativoEsterno() {
        return identificativoEsterno;
    }

    public void setIdentificativoEsterno(String identificativoEsterno) {
        this.identificativoEsterno = identificativoEsterno;
    }

    public List<PraticaDTO> getPraticheAssociate() {
        return praticheAssociate;
    }

    public void setPraticheAssociate(List<PraticaDTO> praticheAssociate) {
        this.praticheAssociate = praticheAssociate;
    }

    public List<AllegatoDTO> getAllegati() {
        return allegati;
    }

    public void setAllegati(List<AllegatoDTO> allegati) {
        this.allegati = allegati;
    }

    public List<DatiCatastaliDTO> getDatiCatastaliList() {
        return datiCatastaliList;
    }

    public void setDatiCatastaliList(List<DatiCatastaliDTO> datiCatastaliList) {
        this.datiCatastaliList = datiCatastaliList;
    }

    public List<IndirizzoInterventoDTO> getIndirizziInterventoList() {
        return indirizziInterventoList;
    }

    public void setIndirizziInterventoList(List<IndirizzoInterventoDTO> indirizziInterventoList) {
        this.indirizziInterventoList = indirizziInterventoList;
    }

    public List<NotaDTO> getNotePratica() {
        return notePratica;
    }

    public void setNotePratica(List<NotaDTO> notePratica) {
        this.notePratica = notePratica;
    }

    public List<ScadenzaDTO> getScadenzeList() {
        return scadenzeList;
    }

    public void setScadenzeList(List<ScadenzaDTO> scadenzeList) {
        this.scadenzeList = scadenzeList;
    }

    public Integer getNumGiorniPrimaScadenza() {
        return numGiorniPrimaScadenza;
    }

    public void setNumGiorniPrimaScadenza(Integer numGiorniPrimaScadenza) {
        this.numGiorniPrimaScadenza = numGiorniPrimaScadenza;
    }

	public String getTipoInterventoSuap() {
		return tipoInterventoSuap;
	}

	public void setTipoInterventoSuap(String tipoInterventoSuap) {
		this.tipoInterventoSuap = tipoInterventoSuap;
	}

	public String getTipoProcedimentoSuap() {
		return tipoProcedimentoSuap;
	}

	public void setTipoProcedimentoSuap(String tipoProcedimentoSuap) {
		this.tipoProcedimentoSuap = tipoProcedimentoSuap;
	}

	public Date getData_prot_suap() {
		return data_prot_suap;
	}

	public void setData_prot_suap(Date data_prot_suap) {
		this.data_prot_suap = data_prot_suap;
	}

}
