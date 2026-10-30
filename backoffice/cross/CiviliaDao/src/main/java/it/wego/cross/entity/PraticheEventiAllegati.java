/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
 * @author Gabriele
 */
@Entity
@Table(name = "pratiche_eventi_allegati")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PraticheEventiAllegati.findAll", query = "SELECT p FROM PraticheEventiAllegati p"),
    @NamedQuery(name = "PraticheEventiAllegati.findByIdPraticaEvento", query = "SELECT p FROM PraticheEventiAllegati p WHERE p.praticheEventiAllegatiPK.idPraticaEvento = :idPraticaEvento"),
    @NamedQuery(name = "PraticheEventiAllegati.findByIdAllegato", query = "SELECT p FROM PraticheEventiAllegati p WHERE p.praticheEventiAllegatiPK.idAllegato = :idAllegato"),
    @NamedQuery(name = "PraticheEventiAllegati.findByFlgIsPrincipale", query = "SELECT p FROM PraticheEventiAllegati p WHERE p.flgIsPrincipale = :flgIsPrincipale"),
    @NamedQuery(name = "PraticheEventiAllegati.findByFlgIsToSend", query = "SELECT p FROM PraticheEventiAllegati p WHERE p.flgIsToSend = :flgIsToSend")})
public class PraticheEventiAllegati implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PraticheEventiAllegatiPK praticheEventiAllegatiPK;
    @Basic(optional = false)
    @Column(name = "flg_is_principale")
    private String flgIsPrincipale;
    @Column(name = "flg_is_to_send")
    private String flgIsToSend;
    @JoinColumn(name = "id_pratica_evento", referencedColumnName = "id_pratica_evento", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private PraticheEventi praticheEventi;
    @JoinColumn(name = "id_allegato", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Allegati allegati;

    public PraticheEventiAllegati() {
    }

    public PraticheEventiAllegati(PraticheEventiAllegatiPK praticheEventiAllegatiPK) {
        this.praticheEventiAllegatiPK = praticheEventiAllegatiPK;
    }

    public PraticheEventiAllegati(PraticheEventiAllegatiPK praticheEventiAllegatiPK, String flgIsPrincipale) {
        this.praticheEventiAllegatiPK = praticheEventiAllegatiPK;
        this.flgIsPrincipale = flgIsPrincipale;
    }

    public PraticheEventiAllegati(int idPraticaEvento, int idAllegato) {
        this.praticheEventiAllegatiPK = new PraticheEventiAllegatiPK(idPraticaEvento, idAllegato);
    }

    public PraticheEventiAllegatiPK getPraticheEventiAllegatiPK() {
        return praticheEventiAllegatiPK;
    }

    public void setPraticheEventiAllegatiPK(PraticheEventiAllegatiPK praticheEventiAllegatiPK) {
        this.praticheEventiAllegatiPK = praticheEventiAllegatiPK;
    }

    public String getFlgIsPrincipale() {
        return flgIsPrincipale;
    }

    public void setFlgIsPrincipale(String flgIsPrincipale) {
        this.flgIsPrincipale = flgIsPrincipale;
    }

    public String getFlgIsToSend() {
        return flgIsToSend;
    }

    public void setFlgIsToSend(String flgIsToSend) {
        this.flgIsToSend = flgIsToSend;
    }

    public PraticheEventi getPraticheEventi() {
        return praticheEventi;
    }

    public void setPraticheEventi(PraticheEventi praticheEventi) {
        this.praticheEventi = praticheEventi;
    }

    public Allegati getAllegati() {
        return allegati;
    }

    public void setAllegati(Allegati allegati) {
        this.allegati = allegati;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (praticheEventiAllegatiPK != null ? praticheEventiAllegatiPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PraticheEventiAllegati)) {
            return false;
        }
        PraticheEventiAllegati other = (PraticheEventiAllegati) object;
        if ((this.praticheEventiAllegatiPK == null && other.praticheEventiAllegatiPK != null) || (this.praticheEventiAllegatiPK != null && !this.praticheEventiAllegatiPK.equals(other.praticheEventiAllegatiPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.PraticheEventiAllegati[ praticheEventiAllegatiPK=" + praticheEventiAllegatiPK + " ]";
    }

}
