package it.wego.cross.entity;
import javax.persistence.NamedQueries;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import it.wego.cross.entity.Utente;


/**
 * The persistent class for the lk_associazione_procedimento_ente_utente_suap database table.
 * 
 */
@Entity
@Table(name="lk_associazione_procedimento_ente_utente_suap")
@NamedQueries({
    @NamedQuery(name = "LkAssociazioneProcedimentoEnteUtenteSuap.findAll", query = "SELECT l FROM LkAssociazioneProcedimentoEnteUtenteSuap l"),
    @NamedQuery(name = "LkAssociazioneProcedimentoEnteUtenteSuap.findByCodProcedimentoSuap", query = "SELECT l FROM LkAssociazioneProcedimentoEnteUtenteSuap l WHERE l.codProcedimentoSuap = :codProcedimentoSuap"),
    @NamedQuery(name = "LkAssociazioneProcedimentoEnteUtenteSuap.findByIdEnte", query = "SELECT l FROM LkAssociazioneProcedimentoEnteUtenteSuap l WHERE l.id.idEnte = :idEnte"),
    @NamedQuery(name = "LkAssociazioneProcedimentoEnteUtenteSuap.findByCodProcedimentoSuapAndIdEnte", query = "SELECT l FROM LkAssociazioneProcedimentoEnteUtenteSuap l WHERE l.codProcedimentoSuap = :codProcedimentoSuap AND l.id.idEnte =:idEnte"),
    @NamedQuery(name = "LkAssociazioneProcedimentoEnteUtenteSuap.findByIdUtente", query = "SELECT l FROM LkAssociazioneProcedimentoEnteUtenteSuap l WHERE l.idUtente = :idUtente")
})
public class LkAssociazioneProcedimentoEnteUtenteSuap implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private LkAssociazioneProcedimentoEnteUtenteSuapPK id;

	@Column(name="cod_endoprocedimento_suap")
	private String codProcedimentoSuap;

	@Column(name="priorita_utente")
	private int prioritaUtente;

	@Column(name="priorita_responsabile")
	private int prioritaResponsabile;

	@ManyToOne
	@JoinColumn(name="id_responsabile_procedimento")
	private Utente idResponsabileProcedimento;

	@ManyToOne
	@JoinColumn(name="id_utente")
	private Utente idUtente;

	public LkAssociazioneProcedimentoEnteUtenteSuapPK getId() {
		return this.id;
	}

	public void setId(LkAssociazioneProcedimentoEnteUtenteSuapPK id) {
		this.id = id;
	}

	public String getCodProcedimentoSuap() {
		return this.codProcedimentoSuap;
	}

	public void setCodProcedimentoSuap(String codProcedimentoSuap) {
		this.codProcedimentoSuap = codProcedimentoSuap;
	}

	public Utente getIdResponsabileProcedimento() {
		return idResponsabileProcedimento;
	}

	public void setIdResponsabileProcedimento(Utente idResponsabileProcedimento) {
		this.idResponsabileProcedimento = idResponsabileProcedimento;
	}

	public Utente getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(Utente idUtente) {
		this.idUtente = idUtente;
	}

	public int getPrioritaUtente() {
		return prioritaUtente;
	}

	public void setPrioritaUtente(int prioritaUtente) {
		this.prioritaUtente = prioritaUtente;
	}

	public int getPrioritaResponsabile() {
		return prioritaResponsabile;
	}

	public void setPrioritaResponsabile(int prioritaResponsabile) {
		this.prioritaResponsabile = prioritaResponsabile;
	}
}