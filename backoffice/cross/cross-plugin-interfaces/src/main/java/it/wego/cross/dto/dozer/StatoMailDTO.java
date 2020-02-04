/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.dozer;

/**
 *
 * @author Gabriele
 */
public class StatoMailDTO {

    private Integer idStatiMail;
    private String descrizione;
    private String codice;

    public Integer getIdStatiMail() {
        return idStatiMail;
    }

    public void setIdStatiMail(Integer idStatiMail) {
        this.idStatiMail = idStatiMail;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }
}
