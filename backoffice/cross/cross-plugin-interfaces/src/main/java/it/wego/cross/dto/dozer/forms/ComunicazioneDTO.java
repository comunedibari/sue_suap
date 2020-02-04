/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.dozer.forms;

import it.wego.cross.dto.AllegatoDTO;
import it.wego.cross.dto.ScadenzaDTO;
import it.wego.cross.validator.Protocollo;
import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Gabriele
 */
public class ComunicazioneDTO extends StandardaFormDTO {

    private Integer idEvento;
    private Integer idPratica;
    private String submit;
    private String activeTab;
    private Boolean visualizzaEventoPortale = Boolean.FALSE;
    private List<String> destinatariIds;
    private List<String> mittentiIds;
    private List<String> procedimentiIds;
    private String oggetto;
    private String contenuto;
    private String note;
    @Protocollo(message = "{error.protocollo}")
    private String numeroDiProtocollo;
    @DateTimeFormat(pattern="dd/MM/yyyy")
    private Date dataDiProtocollo;
//    private String idProcedimentoRiferimento;
    private Boolean inviaEmail = Boolean.TRUE;
    private List<ScadenzaDTO> scadenzeCustom;
    private List<ScadenzaDTO> scadenzeDaChiudere;
    private List<AllegatoDTO> allegatiManuali;
    private List<AllegatoDTO> allegatiDaProtocollo;
    private List<AllegatoDTO> allegatiPresenti;
    private AllegatoDTO allegatoOriginale;
    private Integer idPraticaProtocollo;

    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public Integer getIdPratica() {
        return idPratica;
    }

    public void setIdPratica(Integer idPratica) {
        this.idPratica = idPratica;
    }

    public String getSubmit() {
        return submit;
    }

    public void setSubmit(String submit) {
        this.submit = submit;
    }

    public String getActiveTab() {
        return activeTab;
    }

    public void setActiveTab(String activeTab) {
        this.activeTab = activeTab;
    }

    public List<String> getDestinatariIds() {
        return destinatariIds;
    }

    public Boolean getVisualizzaEventoPortale() {
        return visualizzaEventoPortale;
    }

    public void setVisualizzaEventoPortale(Boolean visualizzaEventoPortale) {
        this.visualizzaEventoPortale = visualizzaEventoPortale;
    }

    public void setDestinatariIds(List<String> destinatariIds) {
        this.destinatariIds = destinatariIds;
    }

    public List<String> getMittentiIds() {
        return mittentiIds;
    }

    public void setMittentiIds(List<String> mittentiIds) {
        this.mittentiIds = mittentiIds;
    }

    public String getOggetto() {
        return oggetto;
    }

    public void setOggetto(String oggetto) {
        this.oggetto = oggetto;
    }

    public String getContenuto() {
        return contenuto;
    }

    public void setContenuto(String contenuto) {
        this.contenuto = contenuto;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    //    public String getIdProcedimentoRiferimento() {
    //        return idProcedimentoRiferimento;
    //    }
    //
    //    public void setIdProcedimentoRiferimento(String idProcedimentoRiferimento) {
    //        this.idProcedimentoRiferimento = idProcedimentoRiferimento;
    //    }
    public Boolean getInviaEmail() {
        return inviaEmail;
    }

    public void setInviaEmail(Boolean inviaEmail) {
        this.inviaEmail = inviaEmail;
    }

    public List<ScadenzaDTO> getScadenzeCustom() {
        return scadenzeCustom;
    }

    public void setScadenzeCustom(List<ScadenzaDTO> scadenzeCustom) {
        this.scadenzeCustom = scadenzeCustom;
    }

    public List<ScadenzaDTO> getScadenzeDaChiudere() {
        return scadenzeDaChiudere;
    }

    public void setScadenzeDaChiudere(List<ScadenzaDTO> scadenzeDaChiudere) {
        this.scadenzeDaChiudere = scadenzeDaChiudere;
    }

    public List<AllegatoDTO> getAllegatiManuali() {
        return allegatiManuali;
    }

    public void setAllegatiManuali(List<AllegatoDTO> allegatiManuali) {
        this.allegatiManuali = allegatiManuali;
    }

    public List<AllegatoDTO> getAllegatiDaProtocollo() {
        return allegatiDaProtocollo;
    }

    public void setAllegatiDaProtocollo(List<AllegatoDTO> allegatiDaProtocollo) {
        this.allegatiDaProtocollo = allegatiDaProtocollo;
    }

    public List<AllegatoDTO> getAllegatiPresenti() {
        return allegatiPresenti;
    }

    public void setAllegatiPresenti(List<AllegatoDTO> allegatiPresenti) {
        this.allegatiPresenti = allegatiPresenti;
    }

    public AllegatoDTO getAllegatoOriginale() {
        return allegatoOriginale;
    }

    public void setAllegatoOriginale(AllegatoDTO allegatoOriginale) {
        this.allegatoOriginale = allegatoOriginale;
    }

    public List<String> getProcedimentiIds() {
        return procedimentiIds;
    }

    public void setProcedimentiIds(List<String> procedimentiIds) {
        this.procedimentiIds = procedimentiIds;
    }

    public Integer getIdPraticaProtocollo() {
        return idPraticaProtocollo;
    }

    public void setIdPraticaProtocollo(Integer idPraticaProtocollo) {
        this.idPraticaProtocollo = idPraticaProtocollo;
    }
    
    
}
