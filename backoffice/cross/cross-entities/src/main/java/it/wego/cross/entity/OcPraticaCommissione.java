/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.entity;

import java.io.Serializable;
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
 * @author piergiorgio
 */
@Entity
@Table(name = "oc_pratica_commissione")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OcPraticaCommissione.findAll", query = "SELECT o FROM OcPraticaCommissione o"),
    @NamedQuery(name = "OcPraticaCommissione.findByIdSedutaPratica", query = "SELECT o FROM OcPraticaCommissione o WHERE o.ocPraticaCommissionePK.idSedutaPratica = :idSedutaPratica"),
    @NamedQuery(name = "OcPraticaCommissione.findByIdAnagrafica", query = "SELECT o FROM OcPraticaCommissione o WHERE o.ocPraticaCommissionePK.idAnagrafica = :idAnagrafica")})
public class OcPraticaCommissione implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected OcPraticaCommissionePK ocPraticaCommissionePK;
    @JoinColumn(name = "id_ruolo_commissione", referencedColumnName = "id_ruolo_commissione")
    @ManyToOne(optional = false)
    private LkRuoliCommissione idRuoloCommissione;
    @JoinColumn(name = "id_anagrafica", referencedColumnName = "id_anagrafica", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Anagrafica anagrafica;
    @JoinColumn(name = "id_seduta_pratica", referencedColumnName = "id_seduta_pratica", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private OcSedutePratiche ocSedutePratiche;

    public OcPraticaCommissione() {
    }

    public OcPraticaCommissione(OcPraticaCommissionePK ocPraticaCommissionePK) {
        this.ocPraticaCommissionePK = ocPraticaCommissionePK;
    }

    public OcPraticaCommissione(int idSedutaPratica, int idAnagrafica) {
        this.ocPraticaCommissionePK = new OcPraticaCommissionePK(idSedutaPratica, idAnagrafica);
    }

    public OcPraticaCommissionePK getOcPraticaCommissionePK() {
        return ocPraticaCommissionePK;
    }

    public void setOcPraticaCommissionePK(OcPraticaCommissionePK ocPraticaCommissionePK) {
        this.ocPraticaCommissionePK = ocPraticaCommissionePK;
    }

    public LkRuoliCommissione getIdRuoloCommissione() {
        return idRuoloCommissione;
    }

    public void setIdRuoloCommissione(LkRuoliCommissione idRuoloCommissione) {
        this.idRuoloCommissione = idRuoloCommissione;
    }

    public Anagrafica getAnagrafica() {
        return anagrafica;
    }

    public void setAnagrafica(Anagrafica anagrafica) {
        this.anagrafica = anagrafica;
    }

    public OcSedutePratiche getOcSedutePratiche() {
        return ocSedutePratiche;
    }

    public void setOcSedutePratiche(OcSedutePratiche ocSedutePratiche) {
        this.ocSedutePratiche = ocSedutePratiche;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ocPraticaCommissionePK != null ? ocPraticaCommissionePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OcPraticaCommissione)) {
            return false;
        }
        OcPraticaCommissione other = (OcPraticaCommissione) object;
        if ((this.ocPraticaCommissionePK == null && other.ocPraticaCommissionePK != null) || (this.ocPraticaCommissionePK != null && !this.ocPraticaCommissionePK.equals(other.ocPraticaCommissionePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.OcPraticaCommissione[ ocPraticaCommissionePK=" + ocPraticaCommissionePK + " ]";
    }
    
}
