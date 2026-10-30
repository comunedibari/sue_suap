/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.dozer;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Gabriele
 */
public class PraticaEventoDTO implements Serializable {

    private Integer idPraticaEvento;
    private Date dataEvento;
    private String oggetto;
    private String comunicazione;
    private String note;
    private String protocollo;
    private Date dataProtocollo;
    private String verso;
    private String visibilitaCross;
    private String visibilitaUtente;
//    private List<Allegati> allegatiList;
    private List<EnteDTO> entiList;
//    private List<Comunicazione> comunicazioneList;
//    private List<Email> emailList;
//    private List<Scadenze> scadenzeList;
    private List<RecapitoAnagraficaDTO> recapitoAnagraficaList;
    private UtenteDTO idUtente;
    private ProcessoEventoDTO idEvento;
    private String descrizioneEvento;
    private StatoMailDTO statoMail;

    public StatoMailDTO getStatoMail() {
        return statoMail;
    }

    public void setStatoMail(StatoMailDTO statoMail) {
        this.statoMail = statoMail;
    }

    public Integer getIdPraticaEvento() {
        return idPraticaEvento;
    }

    public void setIdPraticaEvento(Integer idPraticaEvento) {
        this.idPraticaEvento = idPraticaEvento;
    }

    public Date getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(Date dataEvento) {
        this.dataEvento = dataEvento;
    }

    public String getOggetto() {
        return oggetto;
    }

    public void setOggetto(String oggetto) {
        this.oggetto = oggetto;
    }

    public String getComunicazione() {
        return comunicazione;
    }

    public void setComunicazione(String comunicazione) {
        this.comunicazione = comunicazione;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getProtocollo() {
        return protocollo;
    }

    public void setProtocollo(String protocollo) {
        this.protocollo = protocollo;
    }

    public Date getDataProtocollo() {
        return dataProtocollo;
    }

    public void setDataProtocollo(Date dataProtocollo) {
        this.dataProtocollo = dataProtocollo;
    }

    public String getVerso() {
        return verso;
    }

    public void setVerso(String verso) {
        this.verso = verso;
    }

    public String getVisibilitaCross() {
        return visibilitaCross;
    }

    public void setVisibilitaCross(String visibilitaCross) {
        this.visibilitaCross = visibilitaCross;
    }

    public String getVisibilitaUtente() {
        return visibilitaUtente;
    }

    public void setVisibilitaUtente(String visibilitaUtente) {
        this.visibilitaUtente = visibilitaUtente;
    }

    public List<EnteDTO> getEntiList() {
        return entiList;
    }

    public void setEntiList(List<EnteDTO> entiList) {
        this.entiList = entiList;
    }

    public UtenteDTO getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(UtenteDTO idUtente) {
        this.idUtente = idUtente;
    }

    public ProcessoEventoDTO getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(ProcessoEventoDTO idEvento) {
        this.idEvento = idEvento;
    }

    public String getDescrizioneEvento() {
        return descrizioneEvento;
    }

    public void setDescrizioneEvento(String descrizioneEvento) {
        this.descrizioneEvento = descrizioneEvento;
    }

    public List<RecapitoAnagraficaDTO> getRecapitoAnagraficaList() {
        return recapitoAnagraficaList;
    }

    public void setRecapitoAnagraficaList(List<RecapitoAnagraficaDTO> recapitoAnagraficaList) {
        this.recapitoAnagraficaList = recapitoAnagraficaList;
    }

}
