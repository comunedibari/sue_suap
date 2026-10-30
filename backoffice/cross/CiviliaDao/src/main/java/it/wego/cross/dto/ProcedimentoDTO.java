/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * ProcedimentoCS del veccio cross
 *
 * @author CS
 */
public class ProcedimentoDTO extends AbstractDTO implements Serializable {

    @NotNull(message = "{error.idProcedimento}")
    private Integer idProcedimento;
    @NotNull(message = "{error.codProcedimento}")
    @NotEmpty(message = "{error.codProcedimento}")
    private String codProcedimento;
    @NotNull(message = "{error.termini}")
    private Integer termini;
    private Integer peso;
    private String classifica;
    @NotNull(message = "{error.tipoProc}")
    @NotEmpty(message = "{error.tipoProc}")
    private String tipoProc;
    private String desStato;
    private String desUtente;
    private String giorniScadenza;
    private Integer idProcedimentoEnte;
//    private List<PraticaDTO> praticaList;
//    private List<ProcedimentiEntiDTO> procedimentiEntiList;
//    private List<ProcedimentiTestiDTO> procedimentiTestiList;
    //^^CS da XML
    @NotNull(message = "{error.codEnteDestinatario}")
    @NotEmpty(message = "{error.codEnteDestinatario}")
    private String codEnteDestinatario;
    @NotNull(message = "{error.desEnteDestinatario}")
    @NotEmpty(message = "{error.desEnteDestinatario}")
    private String desEnteDestinatario;
    @NotNull(message = "{error.desProcedimento}")
    @NotEmpty(message = "{error.desProcedimento}")
    private String desProcedimento;
    @NotNull(message = "{error.idEnteDestinatario}")
    private Integer idEnteDestinatario;
    @NotNull(message = "{error.codLang}")
    @NotEmpty(message = "{error.codLang}")
    private String codLang;
    @Valid
    List<ProcedimentiTestiDTO> procedimentiTestiList = new ArrayList<ProcedimentiTestiDTO>();
    //^^CS AGGIUNTA Serve per la jsp di gestione template
    private Boolean checked = false;

    public ProcedimentoDTO() {
    }

    public ProcedimentoDTO(Integer idProcedimento) {
        this.idProcedimento = idProcedimento;
    }

    public String getCodLang() {
        return codLang;
    }

    public void setCodLang(String codLang) {
        this.codLang = codLang;
    }

    public String getCodProcedimento() {
        return codProcedimento;
    }

    public void setCodProcedimento(String codProcedimento) {
        this.codProcedimento = codProcedimento;
    }

    public String getDesEnteDestinatario() {
        return desEnteDestinatario;
    }

    public void setDesEnteDestinatario(String desEnteDestinatario) {
        this.desEnteDestinatario = desEnteDestinatario;
    }

    public String getDesProcedimento() {
        return desProcedimento;
    }

    public void setDesProcedimento(String desProcedimento) {
        this.desProcedimento = desProcedimento;
    }

    public Integer getIdEnteDestinatario() {
        return idEnteDestinatario;
    }

    public void setIdEnteDestinatario(Integer idEnteDestinatario) {
        this.idEnteDestinatario = idEnteDestinatario;
    }

    public Integer getIdProcedimento() {
        return idProcedimento;
    }

    public void setIdProcedimento(Integer idProcedimento) {
        this.idProcedimento = idProcedimento;
    }

    public Integer getTermini() {
        return termini;
    }

    public void setTermini(Integer termini) {
        this.termini = termini;
    }

    public String getCodEnteDestinatario() {
        return codEnteDestinatario;
    }

    public void setCodEnteDestinatario(String codEnteDestinatario) {
        this.codEnteDestinatario = codEnteDestinatario;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Integer getIdProcedimentoEnte() {
        return idProcedimentoEnte;
    }

    public void setIdProcedimentoEnte(Integer idProcedimentoEnte) {
        this.idProcedimentoEnte = idProcedimentoEnte;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public String getClassifica() {
        return classifica;
    }

    public void setClassifica(String classifica) {
        this.classifica = classifica;
    }

    public String getTipoProc() {
        return tipoProc;
    }

    public void setTipoProc(String tipoProc) {
        this.tipoProc = tipoProc;
    }

    public List<ProcedimentiTestiDTO> getProcedimentiTestiList() {
        return procedimentiTestiList;
    }

    public void setProcedimentiTestiList(List<ProcedimentiTestiDTO> procedimentiTestiList) {
        this.procedimentiTestiList = procedimentiTestiList;
    }

    public void fromXML(it.wego.cross.xml.Procedimento procedimento) {

        this.codEnteDestinatario = procedimento.getCodEnteDestinatario();
        this.codLang = procedimento.getCodLang();
        this.codProcedimento = procedimento.getCodProcedimento();
        this.desEnteDestinatario = procedimento.getDesEnteDestinatario();
        this.desProcedimento = procedimento.getDesProcedimento();
        if (procedimento.getIdEnteDestinatario() != null) {
            this.idEnteDestinatario = new Integer(procedimento.getIdEnteDestinatario().intValue());
        }
        this.idProcedimento = procedimento.getIdProcedimento();
        if (procedimento.getTermini() != null) {
            this.termini = new Integer(procedimento.getTermini().intValue());
        }

    }

    public String getDesStato() {
        return desStato;
    }

    public void setDesStato(String desStato) {
        this.desStato = desStato;
    }

    public String getDesUtente() {
        return desUtente;
    }

    public void setDesUtente(String desUtente) {
        this.desUtente = desUtente;
    }

    public String getGiorniScadenza() {
        return giorniScadenza;
    }

    public void setGiorniScadenza(String giorniScadenza) {
        this.giorniScadenza = giorniScadenza;
    }
}
