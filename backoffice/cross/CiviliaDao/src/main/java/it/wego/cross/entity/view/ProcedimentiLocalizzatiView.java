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

@Entity
@Table(name = "procedimenti_localizzati_view")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProcedimentiLocalizzatiView.findAll", query = "SELECT p FROM ProcedimentiLocalizzatiView p"),
    @NamedQuery(name = "ProcedimentiLocalizzatiView.findByIdProc", query = "SELECT p FROM ProcedimentiLocalizzatiView p WHERE p.idProc = :idProc"),
    @NamedQuery(name = "ProcedimentiLocalizzatiView.findByCodProc", query = "SELECT p FROM ProcedimentiLocalizzatiView p WHERE p.codProc = :codProc"),
    @NamedQuery(name = "ProcedimentiLocalizzatiView.findByDesProc", query = "SELECT p FROM ProcedimentiLocalizzatiView p WHERE p.desProc = :desProc"),
    @NamedQuery(name = "ProcedimentiLocalizzatiView.findByTermini", query = "SELECT p FROM ProcedimentiLocalizzatiView p WHERE p.termini = :termini"),
    @NamedQuery(name = "ProcedimentiLocalizzatiView.findByIdLang", query = "SELECT p FROM ProcedimentiLocalizzatiView p WHERE p.idLang = :idLang"),
    @NamedQuery(name = "ProcedimentiLocalizzatiView.findByCodLang", query = "SELECT p FROM ProcedimentiLocalizzatiView p WHERE p.codLang = :codLang")})
public class ProcedimentiLocalizzatiView implements Serializable {

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
    @Column(name = "des_proc")
    private String desProc;
    @Column(name = "termini")
    private Integer termini;
    @Column(name = "id_lang")
    private Integer idLang;
    @Column(name = "cod_lang")
    private String codLang;

    public ProcedimentiLocalizzatiView() {
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

    public String getDesProc() {
        return desProc;
    }

    public void setDesProc(String desProc) {
        this.desProc = desProc;
    }

    public Integer getTermini() {
        return termini;
    }

    public void setTermini(Integer termini) {
        this.termini = termini;
    }

    public Integer getIdLang() {
        return idLang;
    }

    public void setIdLang(Integer idLang) {
        this.idLang = idLang;
    }

    public String getCodLang() {
        return codLang;
    }

    public void setCodLang(String codLang) {
        this.codLang = codLang;
    }
}
