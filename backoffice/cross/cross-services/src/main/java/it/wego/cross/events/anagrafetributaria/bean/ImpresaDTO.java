/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.events.anagrafetributaria.bean;

/**
 *
 * @author Giuseppe
 */
public class ImpresaDTO {

    private Integer idAnagrafica;
    private String descrizione;
    private Integer idRecapito;
    private String descrizioneRecapito;

    public Integer getIdAnagrafica() {
        return idAnagrafica;
    }

    public void setIdAnagrafica(Integer idAnagrafica) {
        this.idAnagrafica = idAnagrafica;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Integer getIdRecapito() {
        return idRecapito;
    }

    public void setIdRecapito(Integer idRecapito) {
        this.idRecapito = idRecapito;
    }

    public String getDescrizioneRecapito() {
        return descrizioneRecapito;
    }

    public void setDescrizioneRecapito(String descrizioneRecapito) {
        this.descrizioneRecapito = descrizioneRecapito;
    }
    
    
}
