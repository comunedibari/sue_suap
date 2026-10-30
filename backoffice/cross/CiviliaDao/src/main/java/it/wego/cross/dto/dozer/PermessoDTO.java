/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.dozer;

/**
 *
 * @author Gabriele
 */
public class PermessoDTO {

    private String codPermesso;
    private String descrizione;

    public String getCodPermesso() {
        return codPermesso;
    }

    public void setCodPermesso(String codPermesso) {
        this.codPermesso = codPermesso;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}
