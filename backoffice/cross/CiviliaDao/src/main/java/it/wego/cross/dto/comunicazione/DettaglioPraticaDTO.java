/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.comunicazione;

import it.wego.cross.dto.AllegatoDTO;
import it.wego.cross.dto.EventoDTO;
import it.wego.cross.dto.dozer.IndirizzoInterventoDTO;
import it.wego.cross.dto.NotaDTO;
import it.wego.cross.dto.ProcedimentoDTO;
import it.wego.cross.dto.ScadenzaDTO;
import it.wego.cross.dto.dozer.DatiCatastaliDTO;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author giuseppe
 */
public class DettaglioPraticaDTO implements Serializable {

    private String identificativoPratica;
    private Date dataRicezione;
    private String codiceStatoPratica;
    private String statoPratica;
    private String numeroProtocollo;
    private String oggetto;
    private Integer idPratica;
    private List<AnagraficaMinifiedDTO> anagrafiche;
    private List<ProcedimentoDTO> procedimenti;
    private List<EventoDTO> eventi;
    private List<AllegatoDTO> allegati;
    private List<ScadenzaDTO> scadenze;
    private List<DatiCatastaliDTO> datiCatastali;
    private List<IndirizzoInterventoDTO> indirizziIntervento;
    private List<NotaDTO> notePratica;
    private String esistenzaStradario;
    private String esistenzaRicercaCatasto;

    public String getEsistenzaRicercaCatasto() {
        return esistenzaRicercaCatasto;
    }

    public void setEsistenzaRicercaCatasto(String esistenzaRicercaCatasto) {
        this.esistenzaRicercaCatasto = esistenzaRicercaCatasto;
    }

    public String getIdentificativoPratica() {
        return identificativoPratica;
    }

    public void setIdentificativoPratica(String identificativoPratica) {
        this.identificativoPratica = identificativoPratica;
    }

    public Date getDataRicezione() {
        return dataRicezione;
    }

    public void setDataRicezione(Date dataRicezione) {
        this.dataRicezione = dataRicezione;
    }

    public String getStatoPratica() {
        return statoPratica;
    }

    public void setStatoPratica(String statoPratica) {
        this.statoPratica = statoPratica;
    }

    public List<AnagraficaMinifiedDTO> getAnagrafiche() {
        return anagrafiche;
    }

    public void setAnagrafiche(List<AnagraficaMinifiedDTO> anagrafiche) {
        this.anagrafiche = anagrafiche;
    }

    public List<ProcedimentoDTO> getProcedimenti() {
        return procedimenti;
    }

    public void setProcedimenti(List<ProcedimentoDTO> procedimenti) {
        this.procedimenti = procedimenti;
    }

    public List<EventoDTO> getEventi() {
        return eventi;
    }

    public void setEventi(List<EventoDTO> eventi) {
        this.eventi = eventi;
    }

    public List<AllegatoDTO> getAllegati() {
        return allegati;
    }

    public void setAllegati(List<AllegatoDTO> allegati) {
        this.allegati = allegati;
    }

    public List<ScadenzaDTO> getScadenze() {
        return scadenze;
    }

    public void setScadenze(List<ScadenzaDTO> scadenze) {
        this.scadenze = scadenze;
    }

    public String getNumeroProtocollo() {
        return numeroProtocollo;
    }

    public void setNumeroProtocollo(String numeroProtocollo) {
        this.numeroProtocollo = numeroProtocollo;
    }

    public String getOggetto() {
        return oggetto;
    }

    public void setOggetto(String oggetto) {
        this.oggetto = oggetto;
    }

    public List<DatiCatastaliDTO> getDatiCatastali() {
        return datiCatastali;
    }

    public void setDatiCatastali(List<DatiCatastaliDTO> datiCatastali) {
        this.datiCatastali = datiCatastali;
    }

    public Integer getIdPratica() {
        return idPratica;
    }

    public void setIdPratica(Integer idPratica) {
        this.idPratica = idPratica;
    }

    public List<NotaDTO> getNotePratica() {
        return notePratica;
    }

    public void setNotePratica(List<NotaDTO> notePratica) {
        this.notePratica = notePratica;
    }

    public List<IndirizzoInterventoDTO> getIndirizziIntervento() {
        return indirizziIntervento;
    }

    public void setIndirizziIntervento(List<IndirizzoInterventoDTO> indirizziIntervento) {
        this.indirizziIntervento = indirizziIntervento;
    }

    public String getEsistenzaStradario() {
        return esistenzaStradario;
    }

    public void setEsistenzaStradario(String esistenzaStradario) {
        this.esistenzaStradario = esistenzaStradario;
    }

    public String getCodiceStatoPratica() {
        return codiceStatoPratica;
    }

    public void setCodiceStatoPratica(String codiceStatoPratica) {
        this.codiceStatoPratica = codiceStatoPratica;
    }

}
