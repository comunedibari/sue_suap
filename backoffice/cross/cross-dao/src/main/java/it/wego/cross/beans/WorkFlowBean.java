/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.beans;

import it.wego.cross.dto.ScadenzaDTO;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.Procedimenti;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.entity.Scadenze;
import it.wego.cross.entity.Utente;
import it.wego.cross.plugins.commons.beans.Allegato;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Gabri
 */
public class WorkFlowBean {

    Pratica pratica;
    Date dataEvento;
    ProcessiEventi eventoProcesso;
    PraticheEventi eventoPratica;
    Utente utenteEvento;
    String note;
    List<Allegato> allegati = new ArrayList<Allegato>();
    /**Visibilita abstract**/
    Boolean visibilitaCross = true;
    Boolean visibilitaUtente = true;
    List<Scadenze> scadenzeDaChiudere;
    
    String protocollo;
    Boolean inviaMail = true;
    Procedimenti procedimento;
    String oggetto;
    String oggettoEmail;
    String corpoEmail;
    List<String> destinatariEmail;
    AttoriComunicazione destinatari;
    AttoriComunicazione mittenti;    
    List<ScadenzaDTO> scadenzeCustom;

    public Pratica getPratica() {
        return pratica;
    }

    public void setPratica(Pratica pratica) {
        this.pratica = pratica;
    }

    public ProcessiEventi getEventoProcesso() {
        return eventoProcesso;
    }

    public void setEventoProcesso(ProcessiEventi eventoProcesso) {
        this.eventoProcesso = eventoProcesso;
    }

    public Utente getUtenteEvento() {
        return utenteEvento;
    }

    public void setUtenteEvento(Utente utenteEvento) {
        this.utenteEvento = utenteEvento;
    }

    public String getProtocollo() {
        return protocollo;
    }

    public void setProtocollo(String protocollo) {
        this.protocollo = protocollo;
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

    public String getOggettoEmail() {
        return oggettoEmail;
    }

    public void setOggettoEmail(String oggettoEmail) {
        this.oggettoEmail = oggettoEmail;
    }

    public String getCorpoEmail() {
        return corpoEmail;
    }

    public void setCorpoEmail(String corpoEmail) {
        this.corpoEmail = corpoEmail;
    }

    public Boolean getInviaMail() {
        return inviaMail;
    }

    public void setInviaMail(Boolean inviaMail) {
        this.inviaMail = inviaMail;
    }

    public AttoriComunicazione getDestinatari() {
        return destinatari;
    }

    public void setDestinatari(AttoriComunicazione destinatari) {
        this.destinatari = destinatari;
    }

    public List<Allegato> getAllegati() {
        return allegati;
    }

    public void setAllegati(List<Allegato> allegati) {
        this.allegati = allegati;
    }

    public void addAllegato(Allegato allegato) {
        if (this.allegati == null) {
            this.allegati = new ArrayList<Allegato>();
        }
        this.allegati.add(allegato);
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(Date dataEvento) {
        this.dataEvento = dataEvento;
    }

    public PraticheEventi getEventoPratica() {
        return eventoPratica;
    }

    public void setEventoPratica(PraticheEventi eventoPratica) {
        this.eventoPratica = eventoPratica;
    }

    public Procedimenti getProcedimento() {
        return procedimento;
    }

    public void setProcedimento(Procedimenti procedimento) {
        this.procedimento = procedimento;
    }

    public String getOggetto() {
        return oggetto;
    }

    public void setOggetto(String oggetto) {
        this.oggetto = oggetto;
    }

    public List<ScadenzaDTO> getScadenzeCustom() {
        return scadenzeCustom;
    }

    public void setScadenzeCustom(List<ScadenzaDTO> scadenzeCustom) {
        this.scadenzeCustom = scadenzeCustom;
    }

    public List<Scadenze> getScadenzeDaChiudere() {
        return scadenzeDaChiudere;
    }

    public void setScadenzeDaChiudere(List<Scadenze> scadenzeDaChiudere) {
        this.scadenzeDaChiudere = scadenzeDaChiudere;
    }

    /**
     * Da utilizzare solo per le pratiche ricevute da WS
     *
     * @return
     */
    public List<String> getDestinatariEmail() {
        return destinatariEmail;
    }

    /**
     * Da utilizzare solo per le pratiche ricevute da WS
     */
    public void setDestinatariEmail(List<String> destinatariEmail) {
        this.destinatariEmail = destinatariEmail;
    }

    public AttoriComunicazione getMittenti() {
        return mittenti;
    }

    public void setMittenti(AttoriComunicazione mittenti) {
        this.mittenti = mittenti;
    }
}
