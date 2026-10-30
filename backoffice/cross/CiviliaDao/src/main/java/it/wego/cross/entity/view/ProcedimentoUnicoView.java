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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author giuseppe
 */
@Entity
@Table(name = "procedimento_unico_view")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProcedimentoUnicoView.findAll", query = "SELECT p FROM ProcedimentoUnicoView p"),
    @NamedQuery(name = "ProcedimentoUnicoView.findByIdProc", query = "SELECT p FROM ProcedimentoUnicoView p WHERE p.idProc = :idProc"),
    @NamedQuery(name = "ProcedimentoUnicoView.findByIdEnte", query = "SELECT p FROM ProcedimentoUnicoView p WHERE p.idEnte = :idEnte"),
    @NamedQuery(name = "ProcedimentoUnicoView.findByTipoProc", query = "SELECT p FROM ProcedimentoUnicoView p WHERE p.tipoProc = :tipoProc"),
    @NamedQuery(name = "ProcedimentoUnicoView.findByDescrizione", query = "SELECT p FROM ProcedimentoUnicoView p WHERE p.descrizione = :descrizione"),
    @NamedQuery(name = "ProcedimentoUnicoView.findByDesProc", query = "SELECT p FROM ProcedimentoUnicoView p WHERE p.desProc = :desProc")})
public class ProcedimentoUnicoView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "id_proc")
    @Id
    private int idProc;
    @Basic(optional = false)
    @Column(name = "id_ente")
    private int idEnte;
    @Column(name = "tipo_proc")
    private String tipoProc;
    @Basic(optional = false)
    @Column(name = "descrizione")
    private String descrizione;
    @Basic(optional = false)
    @Column(name = "des_proc")
    private String desProc;

    public ProcedimentoUnicoView() {
    }

    public int getIdProc() {
        return idProc;
    }

    public void setIdProc(int idProc) {
        this.idProc = idProc;
    }

    public int getIdEnte() {
        return idEnte;
    }

    public void setIdEnte(int idEnte) {
        this.idEnte = idEnte;
    }

    public String getTipoProc() {
        return tipoProc;
    }

    public void setTipoProc(String tipoProc) {
        this.tipoProc = tipoProc;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getDesProc() {
        return desProc;
    }

    public void setDesProc(String desProc) {
        this.desProc = desProc;
    }
    
}
