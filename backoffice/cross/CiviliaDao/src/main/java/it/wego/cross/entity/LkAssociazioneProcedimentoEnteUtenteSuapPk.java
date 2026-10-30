package it.wego.cross.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
@Embeddable
public class LkAssociazioneProcedimentoEnteUtenteSuapPk implements Serializable{
	@Column(name = "cod_procedimento_suap")
	private String codProcedimentoSuap;
    @Column(name = "id_ente")
    private Integer idEnte;
	@Column(name = "id_utente")
    private Integer idUtente;
	
	public LkAssociazioneProcedimentoEnteUtenteSuapPk() {
		
	}
	public LkAssociazioneProcedimentoEnteUtenteSuapPk(String codProcedimentoSuap,Integer idEnte, Integer idUtente) {
        this.codProcedimentoSuap = codProcedimentoSuap;
        this.idEnte = idEnte;
        this.idUtente = idUtente;
    }

	public String getCodProcedimentoSuap() {
		return codProcedimentoSuap;
	}
	public void setCodProcedimentoSuap(String codProcedimentoSuap) {
		this.codProcedimentoSuap = codProcedimentoSuap;
	}
	public Integer getIdEnte() {
		return idEnte;
	}
	public void setIdEnte(Integer idEnte) {
		this.idEnte = idEnte;
	}
	public Integer getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(Integer idUtente) {
		this.idUtente = idUtente;
	}
	
	
	
    
}
