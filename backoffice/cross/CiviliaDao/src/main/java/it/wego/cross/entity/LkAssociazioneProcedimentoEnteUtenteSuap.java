/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "lk_associazione_procedimento_ente_utente_suap")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LkAssociazioneProcedimentoEnteUtenteSuap.findAll", query = "SELECT l FROM LkAssociazioneProcedimentoEnteUtenteSuap l"),
    @NamedQuery(name = "LkAssociazioneProcedimentoEnteUtenteSuap.findByCodProcedimentoSuap", query = "SELECT l FROM LkAssociazioneProcedimentoEnteUtenteSuap l WHERE l.codProcedimentoSuap = :codProcedimentoSuap"),
    @NamedQuery(name = "LkAssociazioneProcedimentoEnteUtenteSuap.findByIdEnte", query = "SELECT l FROM LkAssociazioneProcedimentoEnteUtenteSuap l WHERE l.idEnte = :idEnte"),
    @NamedQuery(name = "LkAssociazioneProcedimentoEnteUtenteSuap.findByCodProcedimentoSuapAndIdEnte", query = "SELECT l FROM LkAssociazioneProcedimentoEnteUtenteSuap l WHERE l.codProcedimentoSuap = :codProcedimentoSuap AND l.idEnte =:idEnte"),
    @NamedQuery(name = "LkAssociazioneProcedimentoEnteUtenteSuap.findByIdUtente", query = "SELECT l FROM LkAssociazioneProcedimentoEnteUtenteSuap l WHERE l.idUtente = :idUtente")})
public class LkAssociazioneProcedimentoEnteUtenteSuap implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private LkAssociazioneProcedimentoEnteUtenteSuapPk lkAssociazioneProcedimentoEnteUtenteSuapPk;
    @Column(name = "cod_procedimento_suap")
    private String codProcedimentoSuap;
    @Column(name = "id_ente")
    private Integer idEnte;
	@Column(name = "id_utente")
    private Integer idUtente;
    
	public LkAssociazioneProcedimentoEnteUtenteSuap(LkAssociazioneProcedimentoEnteUtenteSuapPk lkAssociazioneProcedimentoEnteUtenteSuapPk) {
        this.lkAssociazioneProcedimentoEnteUtenteSuapPk = lkAssociazioneProcedimentoEnteUtenteSuapPk;
    }

    

    public LkAssociazioneProcedimentoEnteUtenteSuap(String codProcedimentoSuap,Integer idEnte, Integer idUtente) {
        this.lkAssociazioneProcedimentoEnteUtenteSuapPk = new LkAssociazioneProcedimentoEnteUtenteSuapPk(codProcedimentoSuap, idEnte,idUtente);
    }

    public LkAssociazioneProcedimentoEnteUtenteSuapPk getLkAssociazioneProcedimentoEnteUtentePK() {
        return lkAssociazioneProcedimentoEnteUtenteSuapPk;
    }

    public void setLkAssociazioneProcedimentoEnteUtentePK(LkAssociazioneProcedimentoEnteUtenteSuapPk lkAssociazioneProcedimentoEnteUtenteSuapPk) {
        this.lkAssociazioneProcedimentoEnteUtenteSuapPk = lkAssociazioneProcedimentoEnteUtenteSuapPk;
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


	
	
	public LkAssociazioneProcedimentoEnteUtenteSuap() {
    }

   
	

    
}
