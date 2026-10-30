/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author giuseppe
 */
@Entity
@Table(name = "lk_tipo_endoprocedimento_suap")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = ".findAll", query = "SELECT l FROM LkTipoEndoProcedimentoSuap l"),
    @NamedQuery(name = "LkTipoEndoProcedimentoSuap.findByIdTipoEndoProcedimento", query = "SELECT l FROM LkTipoEndoProcedimentoSuap l WHERE l.idTipoEndoProcedimento = :idTipoEndoProcedimento"),
    @NamedQuery(name = "LkTipoEndoProcedimentoSuap.findByCodTipoEndoProcedimento", query = "SELECT l FROM LkTipoEndoProcedimentoSuap l WHERE l.codEndoProcedimento = :codEndoProcedimento"),
    @NamedQuery(name = "LkTipoEndoProcedimentoSuap.findByTipologiaClassificazione", query = "SELECT l FROM LkTipoEndoProcedimentoSuap l WHERE l.tipologia = :tipologia"),
    @NamedQuery(name = "LkTipoEndoProcedimentoSuap.findByDescrizioneTipoEndoProcedimento", query = "SELECT l FROM LkTipoEndoProcedimentoSuap l WHERE l.descrizioneEndoProcedimento = :descrizioneEndoProcedimento")})
public class LkTipoEndoProcedimentoSuap implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_endoprocedimento")
    private Integer idTipoEndoProcedimento;
    @Column(name = "cod_procedimento")
    private String codProcedimento;
    @Column(name = "tipologia")
    private String tipologia;
    @Column(name = "descrizione_procedimento")
    private String descrizioneProcedimento;
    @Column(name = "cod_endoprocedimento")
    private String codEndoProcedimento;
    @Column(name = "descrizione_endoprocedimento")
    private String descrizioneEndoProcedimento;
    @Column(name = "amministrazione_competente")
    private String amministrazioneCompetente;
    @Column(name = "regime_amministrativo")
    private String regimeAmministrativo;
    @Column(name = "invio_contestuale_comunica")
    private String invioContestualeComunica;
    @Column(name = "termini_procedimento")
    private String terminiProcedimento;
    @Column(name = "catalogazione_procedimento")
    private String catalogazioneProcedimento;
	

	public String getCodProcedimento() {
		return codProcedimento;
	}

	public void setCodProcedimento(String codProcedimento) {
		this.codProcedimento = codProcedimento;
	}

	public String getDescrizioneProcedimento() {
		return descrizioneProcedimento;
	}

	public void setDescrizioneProcedimento(String descrizioneProcedimento) {
		this.descrizioneProcedimento = descrizioneProcedimento;
	}

	public String getAmministrazioneCompetente() {
		return amministrazioneCompetente;
	}

	public void setAmministrazioneCompetente(String amministrazioneCompetente) {
		this.amministrazioneCompetente = amministrazioneCompetente;
	}

	public String getRegimeAmministrativo() {
		return regimeAmministrativo;
	}

	public void setRegimeAmministrativo(String regimeAmministrativo) {
		this.regimeAmministrativo = regimeAmministrativo;
	}

	public String getInvioContestualeComunica() {
		return invioContestualeComunica;
	}

	public void setInvioContestualeComunica(String invioContestualeComunica) {
		this.invioContestualeComunica = invioContestualeComunica;
	}

	public String getTerminiProcedimento() {
		return terminiProcedimento;
	}

	public void setTerminiProcedimento(String terminiProcedimento) {
		this.terminiProcedimento = terminiProcedimento;
	}

	public String getCatalogazioneProcedimento() {
		return catalogazioneProcedimento;
	}

	public void setCatalogazioneProcedimento(String catalogazioneProcedimento) {
		this.catalogazioneProcedimento = catalogazioneProcedimento;
	}

	public Integer getIdTipoEndoProcedimento() {
		return idTipoEndoProcedimento;
	}

	public void setIdTipoEndoProcedimento(Integer idTipoEndoProcedimento) {
		this.idTipoEndoProcedimento = idTipoEndoProcedimento;
	}

	public String getCodEndoProcedimento() {
		return codEndoProcedimento;
	}

	public void setCodEndoProcedimento(String codEndoProcedimento) {
		this.codEndoProcedimento = codEndoProcedimento;
	}

	public String getDescrizioneEndoProcedimento() {
		return descrizioneEndoProcedimento;
	}

	public void setDescrizioneEndoProcedimento(String descrizioneEndoProcedimento) {
		this.descrizioneEndoProcedimento = descrizioneEndoProcedimento;
	}



	   
    public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

		@Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LkTipoEndoProcedimentoSuap)) {
            return false;
        }
        LkTipoEndoProcedimentoSuap other = (LkTipoEndoProcedimentoSuap) object;
        if ((this.idTipoEndoProcedimento == null && other.idTipoEndoProcedimento != null) || (this.idTipoEndoProcedimento != null && !this.idTipoEndoProcedimento.equals(other.idTipoEndoProcedimento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.LkTipoEndoProcedimentoSuap[ idTipoEndoProcedimento=" + idTipoEndoProcedimento + " ]";
    }
    
}
