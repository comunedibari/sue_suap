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
@Table(name = "procedimenti_collegati_view")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProcedimentiCollegatiView.findAll", query = "SELECT p FROM ProcedimentiCollegatiView p"),
    @NamedQuery(name = "ProcedimentiCollegatiView.findByIdProc", query = "SELECT p FROM ProcedimentiCollegatiView p WHERE p.idProc = :idProc"),
    @NamedQuery(name = "ProcedimentiCollegatiView.findByCodProc", query = "SELECT p FROM ProcedimentiCollegatiView p WHERE p.codProc = :codProc"),
    @NamedQuery(name = "ProcedimentiCollegatiView.findByDesProc", query = "SELECT p FROM ProcedimentiCollegatiView p WHERE p.desProc = :desProc"),
    @NamedQuery(name = "ProcedimentiCollegatiView.findByIdEnte", query = "SELECT p FROM ProcedimentiCollegatiView p WHERE p.idEnte = :idEnte"),
    @NamedQuery(name = "ProcedimentiCollegatiView.findByDesEnte", query = "SELECT p FROM ProcedimentiCollegatiView p WHERE p.desEnte = :desEnte"),
    @NamedQuery(name = "ProcedimentiCollegatiView.findByIdProcedimentoUnico", query = "SELECT p FROM ProcedimentiCollegatiView p WHERE p.idProcedimentoUnico = :idProcedimentoUnico"),
    @NamedQuery(name = "ProcedimentiCollegatiView.findByDesProcedimentoUnico", query = "SELECT p FROM ProcedimentiCollegatiView p WHERE p.desProcedimentoUnico = :desProcedimentoUnico"),
    @NamedQuery(name = "ProcedimentiCollegatiView.findByTipoProc", query = "SELECT p FROM ProcedimentiCollegatiView p WHERE p.tipoProc = :tipoProc")})
public class ProcedimentiCollegatiView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "id_proc")
    @Id
    private int idProc;
    @Basic(optional = false)
    @Column(name = "cod_proc")
    private String codProc;
    @Basic(optional = false)
    @Column(name = "des_proc")
    private String desProc;
    @Basic(optional = false)
    @Column(name = "id_ente")
    private int idEnte;
    @Basic(optional = false)
    @Column(name = "des_ente")
    private String desEnte;
    @Basic(optional = false)
    @Column(name = "id_procedimento_unico")
    private int idProcedimentoUnico;
    @Basic(optional = false)
    @Column(name = "des_procedimento_unico")
    private String desProcedimentoUnico;
    @Column(name = "tipo_proc")
    private String tipoProc;

    public ProcedimentiCollegatiView() {
    }

    public int getIdProc() {
        return idProc;
    }

    public void setIdProc(int idProc) {
        this.idProc = idProc;
    }

    public String getCodProc() {
        return codProc;
    }

    public void setCodProc(String codProc) {
        this.codProc = codProc;
    }

    public String getDesProc() {
        return desProc;
    }

    public void setDesProc(String desProc) {
        this.desProc = desProc;
    }

    public int getIdEnte() {
        return idEnte;
    }

    public void setIdEnte(int idEnte) {
        this.idEnte = idEnte;
    }

    public String getDesEnte() {
        return desEnte;
    }

    public void setDesEnte(String desEnte) {
        this.desEnte = desEnte;
    }

    public int getIdProcedimentoUnico() {
        return idProcedimentoUnico;
    }

    public void setIdProcedimentoUnico(int idProcedimentoUnico) {
        this.idProcedimentoUnico = idProcedimentoUnico;
    }

    public String getDesProcedimentoUnico() {
        return desProcedimentoUnico;
    }

    public void setDesProcedimentoUnico(String desProcedimentoUnico) {
        this.desProcedimentoUnico = desProcedimentoUnico;
    }

    public String getTipoProc() {
        return tipoProc;
    }

    public void setTipoProc(String tipoProc) {
        this.tipoProc = tipoProc;
    }
    
}
