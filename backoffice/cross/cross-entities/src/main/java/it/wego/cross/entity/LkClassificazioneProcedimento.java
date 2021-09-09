/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "lk_classificazione_procedimento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LkClassificazioneProcedimento.findAll", query = "SELECT l FROM LkClassificazioneProcedimento l"),
    @NamedQuery(name = "LkClassificazioneProcedimento.findByIdClassificazioneProcedimento", query = "SELECT l FROM LkClassificazioneProcedimento l WHERE l.idClassificazioneProcedimento = :idClassificazioneProcedimento"),
    @NamedQuery(name = "LkClassificazioneProcedimento.findByCodiceClassificazioneProcedimento", query = "SELECT l FROM LkClassificazioneProcedimento l WHERE l.codiceClassificazioneProcedimento = :codiceClassificazioneProcedimento"),
    @NamedQuery(name = "LkClassificazioneProcedimento.findByDescrizioneClassificazioneProcedimento", query = "SELECT l FROM LkClassificazioneProcedimento l WHERE l.descrizioneClassificazioneProcedimento = :descrizioneClassificazioneProcedimento")})
public class LkClassificazioneProcedimento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_classificazione_procedimento")
    private Integer idClassificazioneProcedimento;
    @Column(name = "codice_classificazione_procedimento")
    private String codiceClassificazioneProcedimento;
    @Column(name = "descrizione_classificazione_procedimento")
    private String descrizioneClassificazioneProcedimento;
    

	
	
	public LkClassificazioneProcedimento() {
    }

    public LkClassificazioneProcedimento(Integer idClassificazioneProcedimento) {
        this.idClassificazioneProcedimento = idClassificazioneProcedimento;
    }

    public Integer getIdClassificazioneProcedimento() {
        return idClassificazioneProcedimento;
    }

    public void setIdClassificazioneProcedimento(Integer idClassificazioneProcedimento) {
        this.idClassificazioneProcedimento = idClassificazioneProcedimento;
    }

   
           public String getCodiceClassificazioneProcedimento() {
		return codiceClassificazioneProcedimento;
	}

	public void setCodiceClassificazioneProcedimento(String codiceClassificazioneProcedimento) {
		this.codiceClassificazioneProcedimento = codiceClassificazioneProcedimento;
	}

	public String getDescrizioneClassificazioneProcedimento() {
		return descrizioneClassificazioneProcedimento;
	}

	public void setDescrizioneClassificazioneProcedimento(String descrizioneClassificazioneProcedimento) {
		this.descrizioneClassificazioneProcedimento = descrizioneClassificazioneProcedimento;
	}

		@Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LkClassificazioneProcedimento)) {
            return false;
        }
        LkClassificazioneProcedimento other = (LkClassificazioneProcedimento) object;
        if ((this.idClassificazioneProcedimento == null && other.idClassificazioneProcedimento != null) || (this.idClassificazioneProcedimento != null && !this.idClassificazioneProcedimento.equals(other.idClassificazioneProcedimento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.LkClassificazioneProcedimento[ idClassificazioneProcedimento=" + idClassificazioneProcedimento + " ]";
    }
    
}
