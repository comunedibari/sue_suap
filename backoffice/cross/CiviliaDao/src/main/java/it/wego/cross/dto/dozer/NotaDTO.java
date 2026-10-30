/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.dozer;

import java.util.Date;

/**
 *
 * @author Giuseppe
 */
public class NotaDTO {

    private Integer idNotePratica;
    private Date dataInserimento;
    private String testo;
    private UtenteDTO idUtente;

    public Integer getIdNotePratica() {
        return idNotePratica;
    }

    public void setIdNotePratica(Integer idNotePratica) {
        this.idNotePratica = idNotePratica;
    }

    public Date getDataInserimento() {
        return dataInserimento;
    }

    public void setDataInserimento(Date dataInserimento) {
        this.dataInserimento = dataInserimento;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public UtenteDTO getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(UtenteDTO idUtente) {
        this.idUtente = idUtente;
    }

}
