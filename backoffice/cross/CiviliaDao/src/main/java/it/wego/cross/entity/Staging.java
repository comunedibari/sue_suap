/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author giuseppe
 */
@Entity
@Table(name = "staging")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Staging.findAll", query = "SELECT s FROM Staging s"),
    @NamedQuery(name = "Staging.findByIdStaging", query = "SELECT s FROM Staging s WHERE s.idStaging = :idStaging"),
    @NamedQuery(name = "Staging.findByDataRicezione", query = "SELECT s FROM Staging s WHERE s.dataRicezione = :dataRicezione"),
    @NamedQuery(name = "Staging.findByOggetto", query = "SELECT s FROM Staging s WHERE s.oggetto = :oggetto"),
    @NamedQuery(name = "Staging.findByTipoMessaggio", query = "SELECT s FROM Staging s WHERE s.tipoMessaggio = :tipoMessaggio"),
    @NamedQuery(name = "Staging.findByDataApertura", query = "SELECT s FROM Staging s WHERE s.dataApertura = :dataApertura")})
public class Staging implements Serializable {
    @Lob
    @Column(name = "xml_ricevuto")
    private byte[] xmlRicevuto;
    @Lob
    @Column(name = "xml_pratica")
    private byte[] xmlPratica;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_staging")
    private Integer idStaging;
    @Basic(optional = false)
    @Column(name = "data_ricezione")
    @Temporal(TemporalType.DATE)
    private Date dataRicezione;
    @Column(name = "oggetto")
    private String oggetto;
    @Basic(optional = false)
    @Column(name = "tipo_messaggio")
    private String tipoMessaggio;
    @Column(name = "data_apertura")
    @Temporal(TemporalType.DATE)
    private Date dataApertura;
    @JoinColumn(name = "id_utente_apertura", referencedColumnName = "id_utente")
    @ManyToOne
    private Utente idUtenteApertura;
    @JoinColumn(name = "id_ente", referencedColumnName = "id_ente")
    @ManyToOne(optional = false)
    private Enti idEnte;
    @OneToMany(mappedBy = "idStaging")
    private List<PraticheProtocollo> praticheProtocolloList;
    @OneToMany(mappedBy = "idStaging")
    private List<Pratica> praticaList;
    @Column(name = "identificativo_provenienza")
    private String identificativoProvenienza;    

    public Staging() {
    }

    public Staging(Integer idStaging) {
        this.idStaging = idStaging;
    }

    public Staging(Integer idStaging, Date dataRicezione, String tipoMessaggio) {
        this.idStaging = idStaging;
        this.dataRicezione = dataRicezione;
        this.tipoMessaggio = tipoMessaggio;
    }

    public Integer getIdStaging() {
        return idStaging;
    }

    public void setIdStaging(Integer idStaging) {
        this.idStaging = idStaging;
    }

    public Date getDataRicezione() {
        return dataRicezione;
    }

    public void setDataRicezione(Date dataRicezione) {
        this.dataRicezione = dataRicezione;
    }

    public byte[] getXmlRicevuto() {
        return xmlRicevuto;
    }

    public void setXmlRicevuto(byte[] xmlRicevuto) {
        this.xmlRicevuto = xmlRicevuto;
    }

    public byte[] getXmlPratica() {
        return xmlPratica;
    }

    public void setXmlPratica(byte[] xmlPratica) {
        this.xmlPratica = xmlPratica;
    }

    public String getOggetto() {
        return oggetto;
    }

    public void setOggetto(String oggetto) {
        this.oggetto = oggetto;
    }

    public String getTipoMessaggio() {
        return tipoMessaggio;
    }

    public void setTipoMessaggio(String tipoMessaggio) {
        this.tipoMessaggio = tipoMessaggio;
    }

    public Date getDataApertura() {
        return dataApertura;
    }

    public void setDataApertura(Date dataApertura) {
        this.dataApertura = dataApertura;
    }

    public Utente getIdUtenteApertura() {
        return idUtenteApertura;
    }

    public void setIdUtenteApertura(Utente idUtenteApertura) {
        this.idUtenteApertura = idUtenteApertura;
    }

    public Enti getIdEnte() {
        return idEnte;
    }

    public void setIdEnte(Enti idEnte) {
        this.idEnte = idEnte;
    }

    @XmlTransient
    public List<PraticheProtocollo> getPraticheProtocolloList() {
        return praticheProtocolloList;
    }

    public void setPraticheProtocolloList(List<PraticheProtocollo> praticheProtocolloList) {
        this.praticheProtocolloList = praticheProtocolloList;
    }

    @XmlTransient
    public List<Pratica> getPraticaList() {
        return praticaList;
    }

    public void setPraticaList(List<Pratica> praticaList) {
        this.praticaList = praticaList;
    }

    public String getIdentificativoProvenienza() {
        return identificativoProvenienza;
    }

    public void setIdentificativoProvenienza(String identificativoProvenienza) {
        this.identificativoProvenienza = identificativoProvenienza;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idStaging != null ? idStaging.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Staging)) {
            return false;
        }
        Staging other = (Staging) object;
        if ((this.idStaging == null && other.idStaging != null) || (this.idStaging != null && !this.idStaging.equals(other.idStaging))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.Staging[ idStaging=" + idStaging + " ]";
    }

}
