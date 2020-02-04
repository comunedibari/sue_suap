/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.events.anagrafetributaria.bean;

import java.io.Serializable;

/**
 *
 * @author giuseppe
 */
public class AnagrafeTributariaCommercioDTO implements Serializable {

    private Integer idDettaglio;
    private Integer idSoggetto;
    private String codFornitura;
    private String codProvvedimento;
    private String inizioProvvedimento;
    private String fineProvvedimento;

    public Integer getIdDettaglio() {
        return idDettaglio;
    }

    public void setIdDettaglio(Integer idDettaglio) {
        this.idDettaglio = idDettaglio;
    }

    public Integer getIdSoggetto() {
        return idSoggetto;
    }

    public void setIdSoggetto(Integer idSoggetto) {
        this.idSoggetto = idSoggetto;
    }

    public String getCodFornitura() {
        return codFornitura;
    }

    public void setCodFornitura(String codFornitura) {
        this.codFornitura = codFornitura;
    }

    public String getCodProvvedimento() {
        return codProvvedimento;
    }

    public void setCodProvvedimento(String codProvvedimento) {
        this.codProvvedimento = codProvvedimento;
    }

    public String getInizioProvvedimento() {
        return inizioProvvedimento;
    }

    public void setInizioProvvedimento(String inizioProvvedimento) {
        this.inizioProvvedimento = inizioProvvedimento;
    }

    public String getFineProvvedimento() {
        return fineProvvedimento;
    }

    public void setFineProvvedimento(String fineProvvedimento) {
        this.fineProvvedimento = fineProvvedimento;
    }
}
