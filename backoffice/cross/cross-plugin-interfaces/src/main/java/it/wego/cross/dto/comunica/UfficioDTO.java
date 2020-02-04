/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.comunica;

import java.io.Serializable;

/**
 *
 * @author giuseppe
 */
public class UfficioDTO implements Serializable {

    private String codiceAmministrazione;
    private String codiceAoo;
    private String identificativoSuap;
    private String descrizione;

    public String getCodiceAmministrazione() {
        return codiceAmministrazione;
    }

    public void setCodiceAmministrazione(String codiceAmministrazione) {
        this.codiceAmministrazione = codiceAmministrazione;
    }

    public String getCodiceAoo() {
        return codiceAoo;
    }

    public void setCodiceAoo(String codiceAoo) {
        this.codiceAoo = codiceAoo;
    }

    public String getIdentificativoSuap() {
        return identificativoSuap;
    }

    public void setIdentificativoSuap(String identificativoSuap) {
        this.identificativoSuap = identificativoSuap;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}
