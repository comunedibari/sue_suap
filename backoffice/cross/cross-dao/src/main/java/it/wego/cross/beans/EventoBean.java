/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.beans;

import it.wego.cross.dto.dozer.PraticaProcedimentiPKDTO;
import it.wego.cross.plugins.commons.beans.Allegato;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Gabriele
 */
public class EventoBean implements Serializable {

    protected Integer idPratica;
    protected Date dataEvento;
    protected Integer idEventoProcesso;
    protected Integer idEventoPratica;
    protected Integer idUtente;
    protected String numeroProtocollo;
    protected Date dataProtocollo;
    protected String note;
    protected List<Allegato> allegati = new ArrayList<Allegato>();
    protected List<Integer> idAllegati = new ArrayList<Integer>();
    protected Boolean visibilitaCross = true;
    protected Boolean visibilitaUtente = true;
    protected Boolean eventoAutomatico = false;
    protected List<Integer> idScadenzeDaChiudere = new ArrayList<Integer>();
    protected List<PraticaProcedimentiPKDTO> praticaProcedimentiSelected = new ArrayList<PraticaProcedimentiPKDTO>();
    protected Map<String, Object> messages;
    protected Integer idPraticaProtocollo;
    protected Integer idEventoPraticaPadre = null;

//    protected List<Integer> allegatiDaNonInviare;
    public Integer getIdPratica() {
        return idPratica;
    }

    public void setIdPratica(Integer idPratica) {
        this.idPratica = idPratica;
    }

    public Date getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(Date dataEvento) {
        this.dataEvento = dataEvento;
    }

    public Integer getIdEventoProcesso() {
        return idEventoProcesso;
    }

    public void setIdEventoProcesso(Integer idEventoProcesso) {
        this.idEventoProcesso = idEventoProcesso;
    }

    public Integer getIdEventoPratica() {
        return idEventoPratica;
    }

    public void setIdEventoPratica(Integer idEventoPratica) {
        this.idEventoPratica = idEventoPratica;
    }

    public Integer getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Integer idUtente) {
        this.idUtente = idUtente;
    }

    public String getNumeroProtocollo() {
        return numeroProtocollo;
    }

    public void setNumeroProtocollo(String numeroProtocollo) {
        this.numeroProtocollo = numeroProtocollo;
    }

    public Date getDataProtocollo() {
        return dataProtocollo;
    }

    public void setDataProtocollo(Date dataProtocollo) {
        this.dataProtocollo = dataProtocollo;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<Allegato> getAllegati() {
        return allegati;
    }

    public void setAllegati(List<Allegato> allegati) {
        this.allegati = allegati;
    }

    public void addAllegato(Allegato allegato) {
        this.allegati.add(allegato);
    }

    public List<Integer> getIdAllegati() {
        return idAllegati;
    }

    public void setIdAllegati(List<Integer> idAllegati) {
        this.idAllegati = idAllegati;
    }

    public void addIdAllegato(Integer idAllegato) {
        this.idAllegati.add(idAllegato);
    }

    public Boolean getVisibilitaCross() {
        return visibilitaCross;
    }

    public void setVisibilitaCross(Boolean visibilitaCross) {
        this.visibilitaCross = visibilitaCross;
    }

    public Boolean getVisibilitaUtente() {
        return visibilitaUtente;
    }

    public void setVisibilitaUtente(Boolean visibilitaUtente) {
        this.visibilitaUtente = visibilitaUtente;
    }

    public Boolean getEventoAutomatico() {
        return eventoAutomatico;
    }

    public void setEventoAutomatico(Boolean eventoAutomatico) {
        this.eventoAutomatico = eventoAutomatico;
    }

    public List<Integer> getIdScadenzeDaChiudere() {
        return idScadenzeDaChiudere;
    }

    public void setIdScadenzeDaChiudere(List<Integer> idScadenzeDaChiudere) {
        this.idScadenzeDaChiudere = idScadenzeDaChiudere;
    }

    public void addIdScadenzaDaChiudere(Integer idScadenza) {
        this.idScadenzeDaChiudere.add(idScadenza);
    }

    public List<PraticaProcedimentiPKDTO> getPraticaProcedimentiSelected() {
        return praticaProcedimentiSelected;
    }

    public void setPraticaProcedimentiSelected(List<PraticaProcedimentiPKDTO> praticaProcedimentiSelected) {
        this.praticaProcedimentiSelected = praticaProcedimentiSelected;
    }

    public void addPraticaProcedimentiSelected(PraticaProcedimentiPKDTO praticaProcedimento) {
        this.praticaProcedimentiSelected.add(praticaProcedimento);
    }

    public Map<String, Object> getMessages() {
        if (this.messages == null) {
            this.messages = new HashMap<String, Object>();
        }
        return messages;
    }

    public Integer getIdPraticaProtocollo() {
        return idPraticaProtocollo;
    }

    public void setIdPraticaProtocollo(Integer idPraticaProtocollo) {
        this.idPraticaProtocollo = idPraticaProtocollo;
    }

    public Integer getIdEventoPraticaPadre() {
        return idEventoPraticaPadre;
    }

    public void setIdEventoPraticaPadre(Integer idEventoPraticaPadre) {
        this.idEventoPraticaPadre = idEventoPraticaPadre;
    }

}
