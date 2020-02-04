/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author giuseppe
 */
@Entity
@Table(name = "processi_eventi_scadenze")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProcessiEventiScadenze.findAll", query = "SELECT p FROM ProcessiEventiScadenze p"),
    @NamedQuery(name = "ProcessiEventiScadenze.findByIdEvento", query = "SELECT p FROM ProcessiEventiScadenze p WHERE p.processiEventiScadenzePK.idEvento = :idEvento"),
    @NamedQuery(name = "ProcessiEventiScadenze.findByIdAnaScadenza", query = "SELECT p FROM ProcessiEventiScadenze p WHERE p.processiEventiScadenzePK.idAnaScadenza = :idAnaScadenza"),
    @NamedQuery(name = "ProcessiEventiScadenze.findByTerminiScadenza", query = "SELECT p FROM ProcessiEventiScadenze p WHERE p.terminiScadenza = :terminiScadenza"),
    @NamedQuery(name = "ProcessiEventiScadenze.findByScriptScadenza", query = "SELECT p FROM ProcessiEventiScadenze p WHERE p.scriptScadenza = :scriptScadenza"),
    @NamedQuery(name = "ProcessiEventiScadenze.findByFlgVisualizzaScadenza", query = "SELECT p FROM ProcessiEventiScadenze p WHERE p.flgVisualizzaScadenza = :flgVisualizzaScadenza")})
public class ProcessiEventiScadenze implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProcessiEventiScadenzePK processiEventiScadenzePK;
    @Column(name = "termini_scadenza")
    private Integer terminiScadenza;
    @Column(name = "script_scadenza")
    private String scriptScadenza;
    @Basic(optional = false)
    @Column(name = "flg_visualizza_scadenza")
    private String flgVisualizzaScadenza;
    @JoinColumn(name = "id_stato_scadenza", referencedColumnName = "id_stato")
    @ManyToOne(optional = false)
    private LkStatiScadenze idStatoScadenza;
    @JoinColumn(name = "id_ana_scadenza", referencedColumnName = "id_ana_scadenze", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private LkScadenze lkScadenze;
    @JoinColumn(name = "id_evento", referencedColumnName = "id_evento", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ProcessiEventi processiEventi;

    public ProcessiEventiScadenze() {
    }

    public ProcessiEventiScadenze(ProcessiEventiScadenzePK processiEventiScadenzePK) {
        this.processiEventiScadenzePK = processiEventiScadenzePK;
    }

    public ProcessiEventiScadenze(ProcessiEventiScadenzePK processiEventiScadenzePK, String flgVisualizzaScadenza) {
        this.processiEventiScadenzePK = processiEventiScadenzePK;
        this.flgVisualizzaScadenza = flgVisualizzaScadenza;
    }

    public ProcessiEventiScadenze(int idEvento, String idAnaScadenza) {
        this.processiEventiScadenzePK = new ProcessiEventiScadenzePK(idEvento, idAnaScadenza);
    }

    public ProcessiEventiScadenzePK getProcessiEventiScadenzePK() {
        return processiEventiScadenzePK;
    }

    public void setProcessiEventiScadenzePK(ProcessiEventiScadenzePK processiEventiScadenzePK) {
        this.processiEventiScadenzePK = processiEventiScadenzePK;
    }

    public Integer getTerminiScadenza() {
        return terminiScadenza;
    }

    public void setTerminiScadenza(Integer terminiScadenza) {
        this.terminiScadenza = terminiScadenza;
    }

    public String getScriptScadenza() {
        return scriptScadenza;
    }

    public void setScriptScadenza(String scriptScadenza) {
        this.scriptScadenza = scriptScadenza;
    }

    public String getFlgVisualizzaScadenza() {
        return flgVisualizzaScadenza;
    }

    public void setFlgVisualizzaScadenza(String flgVisualizzaScadenza) {
        this.flgVisualizzaScadenza = flgVisualizzaScadenza;
    }

    public LkStatiScadenze getIdStatoScadenza() {
        return idStatoScadenza;
    }

    public void setIdStatoScadenza(LkStatiScadenze idStatoScadenza) {
        this.idStatoScadenza = idStatoScadenza;
    }

    public LkScadenze getLkScadenze() {
        return lkScadenze;
    }

    public void setLkScadenze(LkScadenze lkScadenze) {
        this.lkScadenze = lkScadenze;
    }

    public ProcessiEventi getProcessiEventi() {
        return processiEventi;
    }

    public void setProcessiEventi(ProcessiEventi processiEventi) {
        this.processiEventi = processiEventi;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (processiEventiScadenzePK != null ? processiEventiScadenzePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProcessiEventiScadenze)) {
            return false;
        }
        ProcessiEventiScadenze other = (ProcessiEventiScadenze) object;
        if ((this.processiEventiScadenzePK == null && other.processiEventiScadenzePK != null) || (this.processiEventiScadenzePK != null && !this.processiEventiScadenzePK.equals(other.processiEventiScadenzePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.ProcessiEventiScadenze[ processiEventiScadenzePK=" + processiEventiScadenzePK + " ]";
    }
    
}
