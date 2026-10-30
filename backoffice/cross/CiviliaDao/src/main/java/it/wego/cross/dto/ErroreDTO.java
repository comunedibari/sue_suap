package it.wego.cross.dto;

import java.util.Date;

/**
 *
 * @author CS
 */
public class ErroreDTO {

    private Integer idErrore;
    private Integer idPratica;
    private Integer idUtente;
    private String codErrore;
    private String status;
    private Date data;
    private String descrizione;
    private String trace;
    private Exception exception;

    public Integer getIdErrore() {
        return idErrore;
    }

    public void setIdErrore(Integer idErrore) {
        this.idErrore = idErrore;
    }

    public Integer getIdPratica() {
        return idPratica;
    }

    public void setIdPratica(Integer idPratica) {
        this.idPratica = idPratica;
    }

    public Integer getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Integer idUtente) {
        this.idUtente = idUtente;
    }

    public String getCodErrore() {
        return codErrore;
    }

    public void setCodErrore(String codErrore) {
        this.codErrore = codErrore;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

}
