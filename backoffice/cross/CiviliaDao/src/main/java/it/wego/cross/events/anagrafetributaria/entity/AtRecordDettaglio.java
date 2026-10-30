/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.events.anagrafetributaria.entity;

import it.wego.cross.entity.Pratica;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author giuseppe
 */
@Entity
@Table(name = "at_record_dettaglio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AtRecordDettaglio.findAll", query = "SELECT a FROM AtRecordDettaglio a"),
    @NamedQuery(name = "AtRecordDettaglio.findByIdAtRecordDettaglio", query = "SELECT a FROM AtRecordDettaglio a WHERE a.idAtRecordDettaglio = :idAtRecordDettaglio"),
    @NamedQuery(name = "AtRecordDettaglio.findByCodFornitura", query = "SELECT a FROM AtRecordDettaglio a WHERE a.codFornitura = :codFornitura"),
    @NamedQuery(name = "AtRecordDettaglio.findByDataInserimento", query = "SELECT a FROM AtRecordDettaglio a WHERE a.dataInserimento = :dataInserimento"),
    @NamedQuery(name = "AtRecordDettaglio.findByAnnoRiferimento", query = "SELECT a FROM AtRecordDettaglio a WHERE a.annoRiferimento = :annoRiferimento")})
public class AtRecordDettaglio implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "record_dettaglio")
    private byte[] recordDettaglio;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_at_record_dettaglio")
    private Integer idAtRecordDettaglio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "cod_fornitura")
    private String codFornitura;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_inserimento")
    @Temporal(TemporalType.DATE)
    private Date dataInserimento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "anno_riferimento")
    private int annoRiferimento;
    @JoinColumn(name = "id_pratica", referencedColumnName = "id_pratica")
    @OneToOne(optional = false)
    private Pratica idPratica;

    public AtRecordDettaglio() {
    }

    public AtRecordDettaglio(Integer idAtRecordDettaglio) {
        this.idAtRecordDettaglio = idAtRecordDettaglio;
    }

    public AtRecordDettaglio(Integer idAtRecordDettaglio, String codFornitura, byte[] recordDettaglio, Date dataInserimento, int annoRiferimento) {
        this.idAtRecordDettaglio = idAtRecordDettaglio;
        this.codFornitura = codFornitura;
        this.recordDettaglio = recordDettaglio;
        this.dataInserimento = dataInserimento;
        this.annoRiferimento = annoRiferimento;
    }

    public Integer getIdAtRecordDettaglio() {
        return idAtRecordDettaglio;
    }

    public void setIdAtRecordDettaglio(Integer idAtRecordDettaglio) {
        this.idAtRecordDettaglio = idAtRecordDettaglio;
    }

    public String getCodFornitura() {
        return codFornitura;
    }

    public void setCodFornitura(String codFornitura) {
        this.codFornitura = codFornitura;
    }

    public byte[] getRecordDettaglio() {
        return recordDettaglio;
    }

    public void setRecordDettaglio(byte[] recordDettaglio) {
        this.recordDettaglio = recordDettaglio;
    }

    public Date getDataInserimento() {
        return dataInserimento;
    }

    public void setDataInserimento(Date dataInserimento) {
        this.dataInserimento = dataInserimento;
    }

    public int getAnnoRiferimento() {
        return annoRiferimento;
    }

    public void setAnnoRiferimento(int annoRiferimento) {
        this.annoRiferimento = annoRiferimento;
    }

    public Pratica getIdPratica() {
        return idPratica;
    }

    public void setIdPratica(Pratica idPratica) {
        this.idPratica = idPratica;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAtRecordDettaglio != null ? idAtRecordDettaglio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AtRecordDettaglio)) {
            return false;
        }
        AtRecordDettaglio other = (AtRecordDettaglio) object;
        if ((this.idAtRecordDettaglio == null && other.idAtRecordDettaglio != null) || (this.idAtRecordDettaglio != null && !this.idAtRecordDettaglio.equals(other.idAtRecordDettaglio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.events.anagrafetributaria.entity.AtRecordDettaglio[ idAtRecordDettaglio=" + idAtRecordDettaglio + " ]";
    }

}
