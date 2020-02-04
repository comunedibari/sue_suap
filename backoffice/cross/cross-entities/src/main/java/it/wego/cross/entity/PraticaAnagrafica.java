/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author giuseppe
 */
@Entity
@Table(name = "pratica_anagrafica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PraticaAnagrafica.findAll", query = "SELECT p FROM PraticaAnagrafica p"),
    @NamedQuery(name = "PraticaAnagrafica.findByIdPratica", query = "SELECT p FROM PraticaAnagrafica p WHERE p.praticaAnagraficaPK.idPratica = :idPratica"),
    @NamedQuery(name = "PraticaAnagrafica.findByIdAnagrafica", query = "SELECT p FROM PraticaAnagrafica p WHERE p.praticaAnagraficaPK.idAnagrafica = :idAnagrafica"),
    @NamedQuery(name = "PraticaAnagrafica.findByIdTipoRuolo", query = "SELECT p FROM PraticaAnagrafica p WHERE p.praticaAnagraficaPK.idTipoRuolo = :idTipoRuolo"),
    @NamedQuery(name = "PraticaAnagrafica.findByDataInizioValidita", query = "SELECT p FROM PraticaAnagrafica p WHERE p.dataInizioValidita = :dataInizioValidita"),
    @NamedQuery(name = "PraticaAnagrafica.findByDescrizioneTitolarita", query = "SELECT p FROM PraticaAnagrafica p WHERE p.descrizioneTitolarita = :descrizioneTitolarita"),
    @NamedQuery(name = "PraticaAnagrafica.findByAssensoUsoPec", query = "SELECT p FROM PraticaAnagrafica p WHERE p.assensoUsoPec = :assensoUsoPec"),
    @NamedQuery(name = "PraticaAnagrafica.findByPec", query = "SELECT p FROM PraticaAnagrafica p WHERE p.pec = :pec"),
    @NamedQuery(name = "PraticaAnagrafica.findByFlgDittaIndividuale", query = "SELECT p FROM PraticaAnagrafica p WHERE p.flgDittaIndividuale = :flgDittaIndividuale")})
public class PraticaAnagrafica implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PraticaAnagraficaPK praticaAnagraficaPK;
    @Column(name = "data_inizio_validita")
    @Temporal(TemporalType.DATE)
    private Date dataInizioValidita;
    @Column(name = "descrizione_titolarita")
    private String descrizioneTitolarita;
    @Column(name = "assenso_uso_pec")
    private Character assensoUsoPec;
    @Column(name = "pec")
    private String pec;
    @Column(name = "flg_ditta_individuale")
    private String flgDittaIndividuale;
    @JoinColumn(name = "id_recapito_notifica", referencedColumnName = "id_recapito")
    @ManyToOne
    private Recapiti idRecapitoNotifica;
    @JoinColumn(name = "id_tipo_ruolo", referencedColumnName = "id_tipo_ruolo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private LkTipoRuolo lkTipoRuolo;
    @JoinColumn(name = "id_tipo_qualifica", referencedColumnName = "id_tipo_qualifica")
    @ManyToOne
    private LkTipoQualifica idTipoQualifica;
    @JoinColumn(name = "id_pratica", referencedColumnName = "id_pratica", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Pratica pratica;
    @JoinColumn(name = "id_anagrafica", referencedColumnName = "id_anagrafica", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Anagrafica anagrafica;

    public PraticaAnagrafica() {
    }

    public PraticaAnagrafica(PraticaAnagraficaPK praticaAnagraficaPK) {
        this.praticaAnagraficaPK = praticaAnagraficaPK;
    }

    public PraticaAnagrafica(int idPratica, int idAnagrafica, int idTipoRuolo) {
        this.praticaAnagraficaPK = new PraticaAnagraficaPK(idPratica, idAnagrafica, idTipoRuolo);
    }

    public PraticaAnagraficaPK getPraticaAnagraficaPK() {
        return praticaAnagraficaPK;
    }

    public void setPraticaAnagraficaPK(PraticaAnagraficaPK praticaAnagraficaPK) {
        this.praticaAnagraficaPK = praticaAnagraficaPK;
    }

    public Date getDataInizioValidita() {
        return dataInizioValidita;
    }

    public void setDataInizioValidita(Date dataInizioValidita) {
        this.dataInizioValidita = dataInizioValidita;
    }

    public String getDescrizioneTitolarita() {
        return descrizioneTitolarita;
    }

    public void setDescrizioneTitolarita(String descrizioneTitolarita) {
        this.descrizioneTitolarita = descrizioneTitolarita;
    }

    public Character getAssensoUsoPec() {
        return assensoUsoPec;
    }

    public void setAssensoUsoPec(Character assensoUsoPec) {
        this.assensoUsoPec = assensoUsoPec;
    }

    public String getPec() {
        return pec;
    }

    public void setPec(String pec) {
        this.pec = pec;
    }

    public String getFlgDittaIndividuale() {
        return flgDittaIndividuale;
    }

    public void setFlgDittaIndividuale(String flgDittaIndividuale) {
        this.flgDittaIndividuale = flgDittaIndividuale;
    }

    public Recapiti getIdRecapitoNotifica() {
        return idRecapitoNotifica;
    }

    public void setIdRecapitoNotifica(Recapiti idRecapitoNotifica) {
        this.idRecapitoNotifica = idRecapitoNotifica;
    }

    public LkTipoRuolo getLkTipoRuolo() {
        return lkTipoRuolo;
    }

    public void setLkTipoRuolo(LkTipoRuolo lkTipoRuolo) {
        this.lkTipoRuolo = lkTipoRuolo;
    }

    public LkTipoQualifica getIdTipoQualifica() {
        return idTipoQualifica;
    }

    public void setIdTipoQualifica(LkTipoQualifica idTipoQualifica) {
        this.idTipoQualifica = idTipoQualifica;
    }

    public Pratica getPratica() {
        return pratica;
    }

    public void setPratica(Pratica pratica) {
        this.pratica = pratica;
    }

    public Anagrafica getAnagrafica() {
        return anagrafica;
    }

    public void setAnagrafica(Anagrafica anagrafica) {
        this.anagrafica = anagrafica;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (praticaAnagraficaPK != null ? praticaAnagraficaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PraticaAnagrafica)) {
            return false;
        }
        PraticaAnagrafica other = (PraticaAnagrafica) object;
        if ((this.praticaAnagraficaPK == null && other.praticaAnagraficaPK != null) || (this.praticaAnagraficaPK != null && !this.praticaAnagraficaPK.equals(other.praticaAnagraficaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.PraticaAnagrafica[ praticaAnagraficaPK=" + praticaAnagraficaPK + " ]";
    }
    
}
