/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.dozer;

/**
 *
 * @author Gabriele
 */
public class StatoPraticaDTO {

    private Integer idStatoPratica;
    private String descrizione;
    private String codice;
    private String grpStatoPratica;

    public Integer getIdStatoPratica() {
        return idStatoPratica;
    }

    public void setIdStatoPratica(Integer idStatoPratica) {
        this.idStatoPratica = idStatoPratica;
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

    public String getGrpStatoPratica() {
        return grpStatoPratica;
    }

    public void setGrpStatoPratica(String grpStatoPratica) {
        this.grpStatoPratica = grpStatoPratica;
    }
}
