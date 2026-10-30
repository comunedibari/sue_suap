/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.plugins.protocollo.beans;

import java.util.Date;

/**
 *
 * @author Gabriele
 */
public class Protocollo {

    private String numeroRegistrazione;
    private Date dataRegistrazione;
    private String codiceAoo;
    private String codiceAmministrazione;
    private String registro;
    private String anno;

    public String getNumeroRegistrazione() {
        return numeroRegistrazione;
    }

    public void setNumeroRegistrazione(String numeroRegistrazione) {
        this.numeroRegistrazione = numeroRegistrazione;
    }

    public Date getDataRegistrazione() {
        return dataRegistrazione;
    }

    public void setDataRegistrazione(Date dataRegistrazione) {
        this.dataRegistrazione = dataRegistrazione;
    }

    public String getCodiceAoo() {
        return codiceAoo;
    }

    public void setCodiceAoo(String codiceAoo) {
        this.codiceAoo = codiceAoo;
    }

    public String getCodiceAmministrazione() {
        return codiceAmministrazione;
    }

    public void setCodiceAmministrazione(String codiceAmministrazione) {
        this.codiceAmministrazione = codiceAmministrazione;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public String getAnno() {
        return anno;
    }

    public void setAnno(String anno) {
        this.anno = anno;
    }

}
