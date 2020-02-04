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
@Table(name = "endoprocedimenti_view")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EndoprocedimentiView.findAll", query = "SELECT e FROM EndoprocedimentiView e"),
    @NamedQuery(name = "EndoprocedimentiView.findByIdProc", query = "SELECT e FROM EndoprocedimentiView e WHERE e.idProc = :idProc"),
    @NamedQuery(name = "EndoprocedimentiView.findByCodProc", query = "SELECT e FROM EndoprocedimentiView e WHERE e.codProc = :codProc"),
    @NamedQuery(name = "EndoprocedimentiView.findByTipoProc", query = "SELECT e FROM EndoprocedimentiView e WHERE e.tipoProc = :tipoProc"),
    @NamedQuery(name = "EndoprocedimentiView.findByIdEnte", query = "SELECT e FROM EndoprocedimentiView e WHERE e.idEnte = :idEnte"),
    @NamedQuery(name = "EndoprocedimentiView.findByDesProc", query = "SELECT e FROM EndoprocedimentiView e WHERE e.desProc = :desProc")})
public class EndoprocedimentiView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "id_proc")
    @Id
    private int idProc;
    @Basic(optional = false)
    @Column(name = "cod_proc")
    private String codProc;
    @Column(name = "tipo_proc")
    private String tipoProc;
    @Column(name = "id_ente")
    private Integer idEnte;
    @Basic(optional = false)
    @Column(name = "des_proc")
    private String desProc;

    public EndoprocedimentiView() {
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

    public String getTipoProc() {
        return tipoProc;
    }

    public void setTipoProc(String tipoProc) {
        this.tipoProc = tipoProc;
    }

    public Integer getIdEnte() {
        return idEnte;
    }

    public void setIdEnte(Integer idEnte) {
        this.idEnte = idEnte;
    }

    public String getDesProc() {
        return desProc;
    }

    public void setDesProc(String desProc) {
        this.desProc = desProc;
    }
    
}
