/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.registroimprese;

/**
 *
 * @author giuseppe
 */
public class AttivitaIstatRIDTO {

    protected String value;
    protected String codiceIstat;
    protected String catalogo;
    protected Boolean principale;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCodiceIstat() {
        return codiceIstat;
    }

    public void setCodiceIstat(String codiceIstat) {
        this.codiceIstat = codiceIstat;
    }

    public String getCatalogo() {
        return catalogo;
    }

    public void setCatalogo(String catalogo) {
        this.catalogo = catalogo;
    }

    public Boolean getPrincipale() {
        return principale;
    }

    public void setPrincipale(Boolean principale) {
        this.principale = principale;
    }
}
