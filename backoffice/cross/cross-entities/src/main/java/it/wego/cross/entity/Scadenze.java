/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author giuseppe
 */
@Entity
@Table(name = "scadenze")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Scadenze.findAll", query = "SELECT s FROM Scadenze s"),
    @NamedQuery(name = "Scadenze.findByIdScadenza", query = "SELECT s FROM Scadenze s WHERE s.idScadenza = :idScadenza"),
    @NamedQuery(name = "Scadenze.findByDataScadenza", query = "SELECT s FROM Scadenze s WHERE s.dataScadenza = :dataScadenza"),
    @NamedQuery(name = "Scadenze.findByDataFineScadenza", query = "SELECT s FROM Scadenze s WHERE s.dataFineScadenza = :dataFineScadenza"),
    @NamedQuery(name = "Scadenze.findByDataInizioScadenza", query = "SELECT s FROM Scadenze s WHERE s.dataInizioScadenza = :dataInizioScadenza"),
    @NamedQuery(name = "Scadenze.findByGiorniTeoriciScadenza", query = "SELECT s FROM Scadenze s WHERE s.giorniTeoriciScadenza = :giorniTeoriciScadenza"),
    @NamedQuery(name = "Scadenze.findByDescrizioneScadenza", query = "SELECT s FROM Scadenze s WHERE s.descrizioneScadenza = :descrizioneScadenza")})
public class Scadenze implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_scadenza")
    private Integer idScadenza;
    @Basic(optional = false)
    @Column(name = "data_scadenza")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataScadenza;
    @Column(name = "data_fine_scadenza")
    @Temporal(TemporalType.DATE)
    private Date dataFineScadenza;
    @Column(name = "data_fine_scadenza_calcolata")
    @Temporal(TemporalType.DATE)
    private Date dataFineScadenzaCalcolata;
    @Basic(optional = false)
    @Column(name = "data_inizio_scadenza")
    @Temporal(TemporalType.DATE)
    private Date dataInizioScadenza;
    @Basic(optional = false)
    @Column(name = "giorni_teorici_scadenza")
    private int giorniTeoriciScadenza;
    @Column(name = "descrizione_scadenza")
    private String descrizioneScadenza;
    @JoinColumn(name = "id_pratica_evento", referencedColumnName = "id_pratica_evento")
    @ManyToOne(optional = false)
    private PraticheEventi idPraticaEvento;
    @JoinColumn(name = "id_stato", referencedColumnName = "id_stato")
    @ManyToOne(optional = false)
    private LkStatiScadenze idStato;
    @JoinColumn(name = "id_pratica", referencedColumnName = "id_pratica")
    @ManyToOne(optional = false)
    private Pratica idPratica;
    @JoinColumn(name = "id_ana_scadenza", referencedColumnName = "id_ana_scadenze")
    @ManyToOne(optional = false)
    private LkScadenze idAnaScadenza;

    public Scadenze() {
    }

    public Scadenze(Integer idScadenza) {
        this.idScadenza = idScadenza;
    }

    public Scadenze(Integer idScadenza, Date dataScadenza, Date dataInizioScadenza, int giorniTeoriciScadenza) {
        this.idScadenza = idScadenza;
        this.dataScadenza = dataScadenza;
        this.dataInizioScadenza = dataInizioScadenza;
        this.giorniTeoriciScadenza = giorniTeoriciScadenza;
    }

    public Integer getIdScadenza() {
        return idScadenza;
    }

    public void setIdScadenza(Integer idScadenza) {
        this.idScadenza = idScadenza;
    }

    public Date getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(Date dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public Date getDataFineScadenza() {
        return dataFineScadenza;
    }

    public void setDataFineScadenza(Date dataFineScadenza) {
        this.dataFineScadenza = dataFineScadenza;
    }

    public Date getDataFineScadenzaCalcolata() {
        return dataFineScadenzaCalcolata;
    }

    public void setDataFineScadenzaCalcolata(Date dataFineScadenzaCalcolata) {
        this.dataFineScadenzaCalcolata = dataFineScadenzaCalcolata;
    }

    public Date getDataInizioScadenza() {
        return dataInizioScadenza;
    }

    public void setDataInizioScadenza(Date dataInizioScadenza) {
        this.dataInizioScadenza = dataInizioScadenza;
    }

    public int getGiorniTeoriciScadenza() {
        return giorniTeoriciScadenza;
    }

    public void setGiorniTeoriciScadenza(int giorniTeoriciScadenza) {
        this.giorniTeoriciScadenza = giorniTeoriciScadenza;
    }

    public String getDescrizioneScadenza() {
        return descrizioneScadenza;
    }

    public void setDescrizioneScadenza(String descrizioneScadenza) {
        this.descrizioneScadenza = descrizioneScadenza;
    }

    public PraticheEventi getIdPraticaEvento() {
        return idPraticaEvento;
    }

    public void setIdPraticaEvento(PraticheEventi idPraticaEvento) {
        this.idPraticaEvento = idPraticaEvento;
    }

    public LkStatiScadenze getIdStato() {
        return idStato;
    }

    public void setIdStato(LkStatiScadenze idStato) {
        this.idStato = idStato;
    }

    public Pratica getIdPratica() {
        return idPratica;
    }

    public void setIdPratica(Pratica idPratica) {
        this.idPratica = idPratica;
    }

    public LkScadenze getIdAnaScadenza() {
        return idAnaScadenza;
    }

    public void setIdAnaScadenza(LkScadenze idAnaScadenza) {
        this.idAnaScadenza = idAnaScadenza;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idScadenza != null ? idScadenza.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Scadenze)) {
            return false;
        }
        Scadenze other = (Scadenze) object;
        if ((this.idScadenza == null && other.idScadenza != null) || (this.idScadenza != null && !this.idScadenza.equals(other.idScadenza))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.Scadenze[ idScadenza=" + idScadenza + " ]";
    }

}
