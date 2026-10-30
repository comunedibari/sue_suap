/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.dozer;

/**
 *
 * @author Giuseppe
 */
public class LkCittadinanzaDTO {

    private Integer idCittadinanza;
    private String descrizione;
    private String codCittadinanza;

    public Integer getIdCittadinanza() {
        return idCittadinanza;
    }

    public void setIdCittadinanza(Integer idCittadinanza) {
        this.idCittadinanza = idCittadinanza;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getCodCittadinanza() {
        return codCittadinanza;
    }

    public void setCodCittadinanza(String codCittadinanza) {
        this.codCittadinanza = codCittadinanza;
    }

}
