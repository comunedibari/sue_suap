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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author giuseppe
 */
@Entity
@Table(name = "templates")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Templates.findAll", query = "SELECT t FROM Templates t"),
    @NamedQuery(name = "Templates.findByIdTemplate", query = "SELECT t FROM Templates t WHERE t.idTemplate = :idTemplate"),
    @NamedQuery(name = "Templates.findByDescrizione", query = "SELECT t FROM Templates t WHERE t.descrizione = :descrizione"),
    @NamedQuery(name = "Templates.findByNomeFile", query = "SELECT t FROM Templates t WHERE t.nomeFile = :nomeFile"),
    @NamedQuery(name = "Templates.findByMimeType", query = "SELECT t FROM Templates t WHERE t.mimeType = :mimeType"),
    @NamedQuery(name = "Templates.findByFileOutput", query = "SELECT t FROM Templates t WHERE t.fileOutput = :fileOutput"),
    @NamedQuery(name = "Templates.findByNomeFileOriginale", query = "SELECT t FROM Templates t WHERE t.nomeFileOriginale = :nomeFileOriginale")})
public class Templates implements Serializable {
    @OneToMany(mappedBy = "idTemplate")
    private List<OrganiCollegialiTemplate> organiCollegialiTemplateList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_template")
    private Integer idTemplate;
    @Basic(optional = false)
    @Lob
    @Column(name = "template")
    private String template;
    @Column(name = "descrizione")
    private String descrizione;
    @Column(name = "nome_file")
    private String nomeFile;
    @Column(name = "mime_type")
    private String mimeType;
    @Basic(optional = false)
    @Column(name = "file_output")
    private String fileOutput;
    @Column(name = "nome_file_originale")
    private String nomeFileOriginale;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTemplate")
    private List<EventiTemplate> eventiTemplateList;

    public Templates() {
    }

    public Templates(Integer idTemplate) {
        this.idTemplate = idTemplate;
    }

    public Templates(Integer idTemplate, String template, String fileOutput) {
        this.idTemplate = idTemplate;
        this.template = template;
        this.fileOutput = fileOutput;
    }

    public Integer getIdTemplate() {
        return idTemplate;
    }

    public void setIdTemplate(Integer idTemplate) {
        this.idTemplate = idTemplate;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getNomeFile() {
        return nomeFile;
    }

    public void setNomeFile(String nomeFile) {
        this.nomeFile = nomeFile;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getFileOutput() {
        return fileOutput;
    }

    public void setFileOutput(String fileOutput) {
        this.fileOutput = fileOutput;
    }

    public String getNomeFileOriginale() {
        return nomeFileOriginale;
    }

    public void setNomeFileOriginale(String nomeFileOriginale) {
        this.nomeFileOriginale = nomeFileOriginale;
    }

    @XmlTransient
    public List<EventiTemplate> getEventiTemplateList() {
        return eventiTemplateList;
    }

    public void setEventiTemplateList(List<EventiTemplate> eventiTemplateList) {
        this.eventiTemplateList = eventiTemplateList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTemplate != null ? idTemplate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Templates)) {
            return false;
        }
        Templates other = (Templates) object;
        if ((this.idTemplate == null && other.idTemplate != null) || (this.idTemplate != null && !this.idTemplate.equals(other.idTemplate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.Templates[ idTemplate=" + idTemplate + " ]";
    }

    @XmlTransient
    @JsonIgnore
    public List<OrganiCollegialiTemplate> getOrganiCollegialiTemplateList() {
        return organiCollegialiTemplateList;
    }

    public void setOrganiCollegialiTemplateList(List<OrganiCollegialiTemplate> organiCollegialiTemplateList) {
        this.organiCollegialiTemplateList = organiCollegialiTemplateList;
    }
    
}
