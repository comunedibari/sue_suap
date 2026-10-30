/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto;

import it.wego.cross.dto.search.DestinatariDTO;
import it.wego.cross.validator.Protocollo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author giuseppe
 */
public class ComunicazioneDTO implements Serializable {

    private String protocollo;
    private Date dataRicezione;
    private String stato;
    private EventoDTO evento;
    private Boolean downloadAllegatoPratica;
    private List<DestinatariDTO> destinatari;
    private List<String> destinatariIds;
    //^^CS aggiunta
    private List<String> mittentiIds;
    @Protocollo(message = "{error.protocollo}")
    private String numeroDiProtocollo;
    private Date dataDiProtocollo;
    private List<ProcedimentoDTO> procedimentiRiferimento;
    private String inviaEmail;
    private List<AllegatoDTO> allegatiPresenti;
    private List<AllegatoDTO> allegatiDaProtocollo;
    private List<AllegatoDTO> allegatiManuali;
    //^^CS AGGIUNTA
    private AllegatoDTO allegatoOriginale;
    private String visualizzaEventoSuCross;
    private String visualizzaEventoPortale;
    private String note;
    private List<ScadenzaDTO> scadenzeDaChiudere;
    private List<ScadenzaDTO> scadenzeCustom;
    private List<TemplateDTO> template;
    private String visualizzaScadenzeCustom = "N";
    private String visualizzaScadenzeDachiudere = "N";
    //^^CS AGGIUNTA
    private Integer idPratica;
    private UtenteDTO utente;

    public String getProtocollo() {
        return protocollo;
    }

    public void setProtocollo(String protocollo) {
        this.protocollo = protocollo;
    }

    public Date getDataRicezione() {
        return dataRicezione;
    }

    public void setDataRicezione(Date dataRicezione) {
        this.dataRicezione = dataRicezione;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public EventoDTO getEvento() {
        return evento;
    }

    public void setEvento(EventoDTO evento) {
        this.evento = evento;
    }

    public List<ScadenzaDTO> getScadenzeCustom() {
        return scadenzeCustom;
    }

    public void setScadenzeCustom(List<ScadenzaDTO> scadenzeCustom) {
        this.scadenzeCustom = scadenzeCustom;
    }

    public Boolean getDownloadAllegatoPratica() {
        return downloadAllegatoPratica;
    }

    public void setDownloadAllegatoPratica(Boolean downloadAllegatoPratica) {
        this.downloadAllegatoPratica = downloadAllegatoPratica;
    }

    public List<DestinatariDTO> getDestinatari() {
        return destinatari;
    }

    public void setDestinatari(List<DestinatariDTO> destinatari) {
        this.destinatari = destinatari;
    }

    public List<String> getDestinatariIds() {
        return destinatariIds;
    }

    public void setDestinatariIds(List<String> destinatariIds) {
        this.destinatariIds = destinatariIds;
    }

    public String getNumeroDiProtocollo() {
        return numeroDiProtocollo;
    }

    public void setNumeroDiProtocollo(String numeroDiProtocollo) {
        this.numeroDiProtocollo = numeroDiProtocollo;
    }

    public Date getDataDiProtocollo() {
        return dataDiProtocollo;
    }

    public void setDataDiProtocollo(Date dataDiProtocollo) {
        this.dataDiProtocollo = dataDiProtocollo;
    }

    public List<ProcedimentoDTO> getProcedimentiRiferimento() {
        return procedimentiRiferimento;
    }

    public void setProcedimentiRiferimento(List<ProcedimentoDTO> procedimentiRiferimento) {
        this.procedimentiRiferimento = procedimentiRiferimento;
    }

    public String getInviaEmail() {
        return inviaEmail;
    }

    public void setInviaEmail(String inviaEmail) {
        this.inviaEmail = inviaEmail;
    }

    public List<AllegatoDTO> getAllegatiPresenti() {
        if (allegatiPresenti == null) {
            allegatiPresenti = new ArrayList<AllegatoDTO>();
        }
        return allegatiPresenti;
    }

    public void setAllegatiPresenti(List<AllegatoDTO> allegatiPresenti) {
        this.allegatiPresenti = allegatiPresenti;
    }

    public List<AllegatoDTO> getAllegatiDaProtocollo() {
        if (allegatiDaProtocollo == null) {
            allegatiDaProtocollo = new ArrayList<AllegatoDTO>();
        }
        return allegatiDaProtocollo;
    }

    public void setAllegatiDaProtocollo(List<AllegatoDTO> allegatiDaProtocollo) {
        this.allegatiDaProtocollo = allegatiDaProtocollo;
    }

    public List<AllegatoDTO> getAllegatiManuali() {
        if (allegatiManuali == null) {
            allegatiManuali = new ArrayList<AllegatoDTO>();
        }
        return allegatiManuali;
    }

    public void setAllegatiManuali(List<AllegatoDTO> allegatiManuali) {
        this.allegatiManuali = allegatiManuali;
    }

    public String getVisualizzaEventoSuCross() {
        return visualizzaEventoSuCross;
    }

    public void setVisualizzaEventoSuCross(String visualizzaEventoSuCross) {
        this.visualizzaEventoSuCross = visualizzaEventoSuCross;
    }

    public String getVisualizzaEventoPortale() {
        return visualizzaEventoPortale;
    }

    public void setVisualizzaEventoPortale(String visualizzaEventoPortale) {
        this.visualizzaEventoPortale = visualizzaEventoPortale;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<ScadenzaDTO> getScadenzeDaChiudere() {
        return scadenzeDaChiudere;
    }

    public void setScadenzeDaChiudere(List<ScadenzaDTO> scadenzeDaChiudere) {
        this.scadenzeDaChiudere = scadenzeDaChiudere;
    }

    public List<TemplateDTO> getTemplate() {
        return template;
    }

    public void setTemplate(List<TemplateDTO> template) {
        this.template = template;
    }

    public String getVisualizzaScadenzeCustom() {
        return visualizzaScadenzeCustom;
    }

    public void setVisualizzaScadenzeCustom(String visualizzaScadenzeCustom) {
        this.visualizzaScadenzeCustom = visualizzaScadenzeCustom;
    }

    public String getVisualizzaScadenzeDachiudere() {
        return visualizzaScadenzeDachiudere;
    }

    public void setVisualizzaScadenzeDachiudere(String visualizzaScadenzeDachiudere) {
        this.visualizzaScadenzeDachiudere = visualizzaScadenzeDachiudere;
    }

    public List<String> getMittentiIds() {
        return mittentiIds;
    }

    public void setMittentiIds(List<String> mittentiIds) {
        this.mittentiIds = mittentiIds;
    }

    public AllegatoDTO getAllegatoOriginale() {
        return allegatoOriginale;
    }

    public void setAllegatoOriginale(AllegatoDTO allegatoOriginale) {
        this.allegatoOriginale = allegatoOriginale;
    }

    public Integer getIdPratica() {
        return idPratica;
    }

    public void setIdPratica(Integer idPratica) {
        this.idPratica = idPratica;
    }

    public UtenteDTO getUtente() {
        return utente;
    }

    public void setUtente(UtenteDTO utente) {
        this.utente = utente;
    }
}
