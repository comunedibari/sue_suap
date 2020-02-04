/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.dozer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Gabriele
 */
public class ComuneDTO {

    private Integer idComune;
    private String descrizione;
    private String codCatastale;
    private Date dataFineValidita;
    private StatoDTO idStato;
    private ProvinciaDTO idProvincia;
    private List<EnteDTO> entiAbilitati = new ArrayList<EnteDTO>();

    public Integer getIdComune() {
        return idComune;
    }

    public void setIdComune(Integer idComune) {
        this.idComune = idComune;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getCodCatastale() {
        return codCatastale;
    }

    public void setCodCatastale(String codCatastale) {
        this.codCatastale = codCatastale;
    }

    public Date getDataFineValidita() {
        return dataFineValidita;
    }

    public void setDataFineValidita(Date dataFineValidita) {
        this.dataFineValidita = dataFineValidita;
    }

    public StatoDTO getIdStato() {
        return idStato;
    }

    public void setIdStato(StatoDTO idStato) {
        this.idStato = idStato;
    }

    public ProvinciaDTO getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(ProvinciaDTO idProvincia) {
        this.idProvincia = idProvincia;
    }

    public List<EnteDTO> getEntiAbilitati() {
        return entiAbilitati;
    }

    public void setEntiAbilitati(List<EnteDTO> entiAbilitati) {
        this.entiAbilitati = entiAbilitati;
    }

}
