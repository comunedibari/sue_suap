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

/**
 *
 * @author giuseppe
 */
@Entity
@Table(name = "allegati")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Allegati.findAll", query = "SELECT a FROM Allegati a"),
    @NamedQuery(name = "Allegati.findById", query = "SELECT a FROM Allegati a WHERE a.id = :id"),
    @NamedQuery(name = "Allegati.findByDescrizione", query = "SELECT a FROM Allegati a WHERE a.descrizione = :descrizione"),
    @NamedQuery(name = "Allegati.findByNomeFile", query = "SELECT a FROM Allegati a WHERE a.nomeFile = :nomeFile"),
    @NamedQuery(name = "Allegati.findByTipoFile", query = "SELECT a FROM Allegati a WHERE a.tipoFile = :tipoFile"),
    @NamedQuery(name = "Allegati.findByPathFile", query = "SELECT a FROM Allegati a WHERE a.pathFile = :pathFile"),
    @NamedQuery(name = "Allegati.findByIdFileEsterno", query = "SELECT a FROM Allegati a WHERE a.idFileEsterno = :idFileEsterno")})
public class Allegati implements Serializable {

    @Lob
    @Column(name = "file")
    private byte[] file;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "descrizione")
    private String descrizione;
    @Basic(optional = false)
    @Column(name = "nome_file")
    private String nomeFile;
    @Basic(optional = false)
    @Column(name = "tipo_file")
    private String tipoFile;
    @Column(name = "path_file")
    private String pathFile;
    @Column(name = "id_file_esterno")
    private String idFileEsterno;
//    @JoinTable(name = "pratiche_eventi_allegati", joinColumns = {
//        @JoinColumn(name = "id_allegato", referencedColumnName = "id")}, inverseJoinColumns = {
//        @JoinColumn(name = "id_pratica_evento", referencedColumnName = "id_pratica_evento")})
//    @ManyToMany
//    private List<PraticheEventi> praticheEventiList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "allegati")
    private List<PraticheEventiAllegati> praticheEventiAllegatiList;

    @OneToMany(mappedBy = "idModello")
    private List<Pratica> praticaList;

    public Allegati() {
    }

    public Allegati(Integer id) {
        this.id = id;
    }

    public Allegati(Integer id, String nomeFile, String tipoFile) {
        this.id = id;
        this.nomeFile = nomeFile;
        this.tipoFile = tipoFile;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getTipoFile() {
        return tipoFile;
    }

    public void setTipoFile(String tipoFile) {
        this.tipoFile = tipoFile;
    }

    public String getPathFile() {
        return pathFile;
    }

    public void setPathFile(String pathFile) {
        this.pathFile = pathFile;
    }

    public String getIdFileEsterno() {
        return idFileEsterno;
    }

    public void setIdFileEsterno(String idFileEsterno) {
        this.idFileEsterno = idFileEsterno;
    }

//    @XmlTransient
//    public List<PraticheEventi> getPraticheEventiList() {
//        return praticheEventiList;
//    }
//
//    public void setPraticheEventiList(List<PraticheEventi> praticheEventiList) {
//        this.praticheEventiList = praticheEventiList;
//    }
    @XmlTransient
    public List<Pratica> getPraticaList() {
        return praticaList;
    }

    public void setPraticaList(List<Pratica> praticaList) {
        this.praticaList = praticaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Allegati)) {
            return false;
        }
        Allegati other = (Allegati) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.Allegati[ id=" + id + " ]";
    }


    public List<PraticheEventiAllegati> getPraticheEventiAllegatiList() {
        return praticheEventiAllegatiList;
    }

    public void setPraticheEventiAllegatiList(List<PraticheEventiAllegati> praticheEventiAllegatiList) {
        this.praticheEventiAllegatiList = praticheEventiAllegatiList;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

}
