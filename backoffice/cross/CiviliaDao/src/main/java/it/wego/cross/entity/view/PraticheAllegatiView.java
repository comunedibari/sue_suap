/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.entity.view;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author giuseppe
 */
@Entity
@Table(name = "pratiche_allegati_view")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PraticheAllegatiView.findAll", query = "SELECT p FROM PraticheAllegatiView p"),
    @NamedQuery(name = "PraticheAllegatiView.findByIdPratica", query = "SELECT p FROM PraticheAllegatiView p WHERE p.idPratica = :idPratica"),
    @NamedQuery(name = "PraticheAllegatiView.findById", query = "SELECT p FROM PraticheAllegatiView p WHERE p.id = :id"),
    @NamedQuery(name = "PraticheAllegatiView.findByDescrizione", query = "SELECT p FROM PraticheAllegatiView p WHERE p.descrizione = :descrizione"),
    @NamedQuery(name = "PraticheAllegatiView.findByNomeFile", query = "SELECT p FROM PraticheAllegatiView p WHERE p.nomeFile = :nomeFile"),
    @NamedQuery(name = "PraticheAllegatiView.findByTipoFile", query = "SELECT p FROM PraticheAllegatiView p WHERE p.tipoFile = :tipoFile"),
    @NamedQuery(name = "PraticheAllegatiView.findByPathFile", query = "SELECT p FROM PraticheAllegatiView p WHERE p.pathFile = :pathFile"),
    @NamedQuery(name = "PraticheAllegatiView.findByIdFileEsterno", query = "SELECT p FROM PraticheAllegatiView p WHERE p.idFileEsterno = :idFileEsterno")})
public class PraticheAllegatiView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "id_pratica")
    @Id
    private int idPratica;
    @Basic(optional = false)
    @Column(name = "id")
    private int id;
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
    @Lob
    @Column(name = "file")
    private byte[] file;

    public PraticheAllegatiView() {
    }

    public int getIdPratica() {
        return idPratica;
    }

    public void setIdPratica(int idPratica) {
        this.idPratica = idPratica;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }
    
}
