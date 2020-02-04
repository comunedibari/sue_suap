/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.dozer.forms;

import it.wego.cross.dto.AllegatoDTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Gabriele
 */
public class CaricamentoManualeDTO extends StandardaFormDTO {

    private Integer idPraticaProtocollo;
    private Integer procedimentoSelezionato;
    private Integer comuneSelezionato;
    private String oggetto;
    private Date ricezioneData;
    private String protocolloSegnatura;
    private Date protocolloData;
    private List<Integer> endoprocedimentiSelezionati = new ArrayList<Integer>();
    private List<AllegatoDTO> allegatiList = new ArrayList<AllegatoDTO>();

    public Integer getIdPraticaProtocollo() {
        return idPraticaProtocollo;
    }

    public void setIdPraticaProtocollo(Integer idPraticaProtocollo) {
        this.idPraticaProtocollo = idPraticaProtocollo;
    }

    public Integer getProcedimentoSelezionato() {
        return procedimentoSelezionato;
    }

    public void setProcedimentoSelezionato(Integer procedimentoSelezionato) {
        this.procedimentoSelezionato = procedimentoSelezionato;
    }

    public Integer getComuneSelezionato() {
        return comuneSelezionato;
    }

    public void setComuneSelezionato(Integer comuneSelezionato) {
        this.comuneSelezionato = comuneSelezionato;
    }

    public String getOggetto() {
        return oggetto;
    }

    public void setOggetto(String oggetto) {
        this.oggetto = oggetto;
    }

    public Date getRicezioneData() {
        return ricezioneData;
    }

    public void setRicezioneData(Date ricezioneData) {
        this.ricezioneData = ricezioneData;
    }

    public String getProtocolloSegnatura() {
        return protocolloSegnatura;
    }

    public void setProtocolloSegnatura(String protocolloSegnatura) {
        this.protocolloSegnatura = protocolloSegnatura;
    }

    public Date getProtocolloData() {
        return protocolloData;
    }

    public void setProtocolloData(Date protocolloData) {
        this.protocolloData = protocolloData;
    }

    public List<Integer> getEndoprocedimentiSelezionati() {
        return endoprocedimentiSelezionati;
    }

    public void setEndoprocedimentiSelezionati(List<Integer> endoprocedimentiSelezionati) {
        this.endoprocedimentiSelezionati = endoprocedimentiSelezionati;
    }

    public List<AllegatoDTO> getAllegatiList() {
        return allegatiList;
    }

    public void setAllegatiList(List<AllegatoDTO> allegatiList) {
        this.allegatiList = allegatiList;
    }

}
