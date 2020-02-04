/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.comunica;

import java.io.Serializable;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author giuseppe
 */
public class ProtocolloDTO implements Serializable {

    @NotEmpty(message="error.comunica.protocollo.anno")
    private String anno;
    @NotEmpty(message="error.comunica.protocollo.registro")
    private String registro;
    @NotEmpty(message="error.comunica.protocollo.numero")
    private String numero;
    private String fascicolo;

    public String getAnno() {
        return anno;
    }

    public void setAnno(String anno) {
        this.anno = anno;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getFascicolo() {
        return fascicolo;
    }

    public void setFascicolo(String fascicolo) {
        this.fascicolo = fascicolo;
    }
}
