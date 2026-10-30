package it.wego.cross.dto.dozer;

public class ProcedimentoEnteDTO {

    private Integer idProcEnte;
    private ProcedimentoDTO procedimento;
    private EnteDTO ente;
    private ProcessoDTO processo;
    private PraticaDTO pratica;
    private EnteDTO ufficio;
    private String provenienza;
    private String praticaProcedimentoDescrizione;
    
    public String getProvenienza() {
		return provenienza;
	}

	public void setProvenienza(String provenienza) {
		this.provenienza = provenienza;
	}
	public Integer getIdProcEnte() {
        return idProcEnte;
    }

    public void setIdProcEnte(Integer idProcEnte) {
        this.idProcEnte = idProcEnte;
    }

    public ProcedimentoDTO getProcedimento() {
        return procedimento;
    }

    public void setProcedimento(ProcedimentoDTO procedimento) {
        this.procedimento = procedimento;
    }

    public EnteDTO getEnte() {
        return ente;
    }

    public void setEnte(EnteDTO ente) {
        this.ente = ente;
    }

    public ProcessoDTO getProcesso() {
        return processo;
    }

    public void setProcesso(ProcessoDTO processo) {
        this.processo = processo;
    }

    public PraticaDTO getPratica() {
        return pratica;
    }

    public void setPratica(PraticaDTO pratica) {
        this.pratica = pratica;
    }

	public EnteDTO getUfficio() {
		return ufficio;
	}

	public void setUfficio(EnteDTO ufficio) {
		this.ufficio = ufficio;
	}

	public String getPraticaProcedimentoDescrizione() {
		return praticaProcedimentoDescrizione;
	}

	public void setPraticaProcedimentoDescrizione(String praticaProcedimentoDescrizione) {
		this.praticaProcedimentoDescrizione = praticaProcedimentoDescrizione;
	}
}
