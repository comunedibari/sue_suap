/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.events.comunicazione.bean;

import it.wego.cross.beans.AttoriComunicazione;
import it.wego.cross.beans.EventoBean;
import it.wego.cross.dto.ScadenzaDTO;
import java.util.List;

/**
 *
 * @author Gabriele
 */
public class ComunicazioneBean extends EventoBean {

    private Boolean inviaMail = true;
    private Integer idProcedimento;
    private String oggetto;
    private String oggettoEmail;
    private String oggettoProtocollo;
    private String corpoEmail;
    private List<String> destinatariEmail;
    private AttoriComunicazione destinatari;
    private AttoriComunicazione mittenti;
    private List<ScadenzaDTO> scadenzeCustom;

    public ComunicazioneBean(EventoBean eb) {
        this.idPratica = eb.getIdPratica();
        this.dataEvento = eb.getDataEvento();
        this.idEventoProcesso = eb.getIdEventoProcesso();
        this.idEventoPratica = eb.getIdEventoPratica();
        this.idUtente = eb.getIdUtente();
        this.numeroProtocollo = eb.getNumeroProtocollo();
        this.dataProtocollo = eb.getDataProtocollo();
        this.note = eb.getNote();
        this.allegati = eb.getAllegati();
        this.idAllegati = eb.getIdAllegati();
        this.visibilitaCross = eb.getVisibilitaCross();
        this.visibilitaUtente = eb.getVisibilitaUtente();
        this.eventoAutomatico = eb.getEventoAutomatico();
        this.idScadenzeDaChiudere = eb.getIdScadenzeDaChiudere();
        this.praticaProcedimentiSelected = eb.getPraticaProcedimentiSelected();
        this.messages = eb.getMessages();
        this.idPraticaProtocollo = eb.getIdPraticaProtocollo();
        this.idEventoPraticaPadre = eb.getIdEventoPraticaPadre();
    }

    public ComunicazioneBean() {
    }

    public Boolean getInviaMail() {
        return inviaMail;
    }

    public void setInviaMail(Boolean inviaMail) {
        this.inviaMail = inviaMail;
    }

    public Integer getIdProcedimento() {
        return idProcedimento;
    }

    public void setIdProcedimento(Integer idProcedimento) {
        this.idProcedimento = idProcedimento;
    }

    public String getOggetto() {
        return oggetto;
    }

    public void setOggetto(String oggetto) {
        this.oggetto = oggetto;
    }

    public String getOggettoEmail() {
        return oggettoEmail;
    }

    public void setOggettoEmail(String oggettoEmail) {
        this.oggettoEmail = oggettoEmail;
    }

    public String getOggettoProtocollo() {
        return oggettoProtocollo;
    }

    public void setOggettoProtocollo(String oggettoProtocollo) {
        this.oggettoProtocollo = oggettoProtocollo;
    }

    public String getCorpoEmail() {
        return corpoEmail;
    }

    public void setCorpoEmail(String corpoEmail) {
        this.corpoEmail = corpoEmail;
    }

    public List<String> getDestinatariEmail() {
        return destinatariEmail;
    }

    public void setDestinatariEmail(List<String> destinatariEmail) {
        this.destinatariEmail = destinatariEmail;
    }

    public AttoriComunicazione getDestinatari() {
        return destinatari;
    }

    public void setDestinatari(AttoriComunicazione destinatari) {
        this.destinatari = destinatari;
    }

    public AttoriComunicazione getMittenti() {
        return mittenti;
    }

    public void setMittenti(AttoriComunicazione mittenti) {
        this.mittenti = mittenti;
    }

    public List<ScadenzaDTO> getScadenzeCustom() {
        return scadenzeCustom;
    }

    public void setScadenzeCustom(List<ScadenzaDTO> scadenzeCustom) {
        this.scadenzeCustom = scadenzeCustom;
    }
}
