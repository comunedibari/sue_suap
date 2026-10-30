/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.events.comunicazionerea.entity;

import it.wego.cross.entity.Allegati;
import it.wego.cross.entity.Anagrafica;
import it.wego.cross.entity.Pratica;
import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Gabriele
 */
@Entity
@Table(name = "ri_integrazione_rea")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RiIntegrazioneRea.findAll", query = "SELECT r FROM RiIntegrazioneRea r"),
    @NamedQuery(name = "RiIntegrazioneRea.findByIdRiIntegrazioneRea", query = "SELECT r FROM RiIntegrazioneRea r WHERE r.idRiIntegrazioneRea = :idRiIntegrazioneRea"),
    @NamedQuery(name = "RiIntegrazioneRea.findByTipoProcedimento", query = "SELECT r FROM RiIntegrazioneRea r WHERE r.tipoProcedimento = :tipoProcedimento"),
    @NamedQuery(name = "RiIntegrazioneRea.findByTipoIntervento", query = "SELECT r FROM RiIntegrazioneRea r WHERE r.tipoIntervento = :tipoIntervento"),
    @NamedQuery(name = "RiIntegrazioneRea.findByFormaGiuridicaImpresa", query = "SELECT r FROM RiIntegrazioneRea r WHERE r.formaGiuridicaImpresa = :formaGiuridicaImpresa"),
    @NamedQuery(name = "RiIntegrazioneRea.findByQualificaDichiarantePratica", query = "SELECT r FROM RiIntegrazioneRea r WHERE r.qualificaDichiarantePratica = :qualificaDichiarantePratica"),
    @NamedQuery(name = "RiIntegrazioneRea.findByCaricaLegaleRapp", query = "SELECT r FROM RiIntegrazioneRea r WHERE r.caricaLegaleRapp = :caricaLegaleRapp")})
public class RiIntegrazioneRea implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_ri_integrazione_rea")
    private Integer idRiIntegrazioneRea;
    @Basic(optional = false)
    @Column(name = "tipo_procedimento")
    private String tipoProcedimento;
    @Basic(optional = false)
    @Column(name = "tipo_intervento")
    private String tipoIntervento;
    @Basic(optional = false)
    @Column(name = "forma_giuridica_impresa")
    private String formaGiuridicaImpresa;
    @Basic(optional = false)
    @Column(name = "qualifica_dichiarante_pratica")
    private String qualificaDichiarantePratica;
    @Basic(optional = false)
    @Column(name = "carica_legale_rapp")
    private String caricaLegaleRapp;
    @JoinColumn(name = "id_pratica", referencedColumnName = "id_pratica")
    @ManyToOne(optional = false)
    private Pratica idPratica;
    @JoinColumn(name = "id_azienda_riferimento", referencedColumnName = "id_anagrafica")
    @ManyToOne(optional = false)
    private Anagrafica idAziendaRiferimento;
    @JoinColumn(name = "id_anagrafica_legale_rapp", referencedColumnName = "id_anagrafica")
    @ManyToOne(optional = false)
    private Anagrafica idAnagraficaLegaleRapp;
    @JoinColumn(name = "id_anagrafica_dichiarante", referencedColumnName = "id_anagrafica")
    @ManyToOne(optional = false)
    private Anagrafica idAnagraficaDichiarante;
    @JoinColumn(name = "id_allegato_procura_speciale", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Allegati idAllegatoProcuraSpeciale;

    public RiIntegrazioneRea() {
    }

    public RiIntegrazioneRea(Integer idRiIntegrazioneRea) {
        this.idRiIntegrazioneRea = idRiIntegrazioneRea;
    }

    public RiIntegrazioneRea(Integer idRiIntegrazioneRea, String tipoProcedimento, String tipoIntervento, String formaGiuridicaImpresa, String qualificaDichiarantePratica, String caricaLegaleRapp) {
        this.idRiIntegrazioneRea = idRiIntegrazioneRea;
        this.tipoProcedimento = tipoProcedimento;
        this.tipoIntervento = tipoIntervento;
        this.formaGiuridicaImpresa = formaGiuridicaImpresa;
        this.qualificaDichiarantePratica = qualificaDichiarantePratica;
        this.caricaLegaleRapp = caricaLegaleRapp;
    }

    public Integer getIdRiIntegrazioneRea() {
        return idRiIntegrazioneRea;
    }

    public void setIdRiIntegrazioneRea(Integer idRiIntegrazioneRea) {
        this.idRiIntegrazioneRea = idRiIntegrazioneRea;
    }

    public String getTipoProcedimento() {
        return tipoProcedimento;
    }

    public void setTipoProcedimento(String tipoProcedimento) {
        this.tipoProcedimento = tipoProcedimento;
    }

    public String getTipoIntervento() {
        return tipoIntervento;
    }

    public void setTipoIntervento(String tipoIntervento) {
        this.tipoIntervento = tipoIntervento;
    }

    public String getFormaGiuridicaImpresa() {
        return formaGiuridicaImpresa;
    }

    public void setFormaGiuridicaImpresa(String formaGiuridicaImpresa) {
        this.formaGiuridicaImpresa = formaGiuridicaImpresa;
    }

    public String getQualificaDichiarantePratica() {
        return qualificaDichiarantePratica;
    }

    public void setQualificaDichiarantePratica(String qualificaDichiarantePratica) {
        this.qualificaDichiarantePratica = qualificaDichiarantePratica;
    }

    public String getCaricaLegaleRapp() {
        return caricaLegaleRapp;
    }

    public void setCaricaLegaleRapp(String caricaLegaleRapp) {
        this.caricaLegaleRapp = caricaLegaleRapp;
    }

    public Pratica getIdPratica() {
        return idPratica;
    }

    public void setIdPratica(Pratica idPratica) {
        this.idPratica = idPratica;
    }

    public Anagrafica getIdAziendaRiferimento() {
        return idAziendaRiferimento;
    }

    public void setIdAziendaRiferimento(Anagrafica idAziendaRiferimento) {
        this.idAziendaRiferimento = idAziendaRiferimento;
    }

    public Anagrafica getIdAnagraficaLegaleRapp() {
        return idAnagraficaLegaleRapp;
    }

    public void setIdAnagraficaLegaleRapp(Anagrafica idAnagraficaLegaleRapp) {
        this.idAnagraficaLegaleRapp = idAnagraficaLegaleRapp;
    }

    public Anagrafica getIdAnagraficaDichiarante() {
        return idAnagraficaDichiarante;
    }

    public void setIdAnagraficaDichiarante(Anagrafica idAnagraficaDichiarante) {
        this.idAnagraficaDichiarante = idAnagraficaDichiarante;
    }

    public Allegati getIdAllegatoProcuraSpeciale() {
        return idAllegatoProcuraSpeciale;
    }

    public void setIdAllegatoProcuraSpeciale(Allegati idAllegatoProcuraSpeciale) {
        this.idAllegatoProcuraSpeciale = idAllegatoProcuraSpeciale;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRiIntegrazioneRea != null ? idRiIntegrazioneRea.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RiIntegrazioneRea)) {
            return false;
        }
        RiIntegrazioneRea other = (RiIntegrazioneRea) object;
        if ((this.idRiIntegrazioneRea == null && other.idRiIntegrazioneRea != null) || (this.idRiIntegrazioneRea != null && !this.idRiIntegrazioneRea.equals(other.idRiIntegrazioneRea))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.events.comunicazionerea.entity.RiIntegrazioneRea[ idRiIntegrazioneRea=" + idRiIntegrazioneRea + " ]";
    }
}
