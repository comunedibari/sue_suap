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
public class LkFormeGiuridicheDTO {

    private Integer idFormeGiuridiche;
    private String codFormaGiuridica;

    private String descrizione;

    public Integer getIdFormeGiuridiche() {
        return idFormeGiuridiche;
    }

    public void setIdFormeGiuridiche(Integer idFormeGiuridiche) {
        this.idFormeGiuridiche = idFormeGiuridiche;
    }

    public String getCodFormaGiuridica() {
        return codFormaGiuridica;
    }

    public void setCodFormaGiuridica(String codFormaGiuridica) {
        this.codFormaGiuridica = codFormaGiuridica;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

}
