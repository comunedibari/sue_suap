/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto;

import it.wego.cross.entity.LkStatoPratica;
import it.wego.cross.entity.Procedimenti;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.ObjectUtils;

/**
 *
 * @author giuseppe
 */
public class EventoDTO implements Serializable, Comparable<EventoDTO> {

    private Integer idPraticaEvento;
    private Integer idEvento;
    private Integer idPratica;
    private String descrizione;
    private String dataEvento;
    private Integer idUtente;
    private String username;
    private String note;
    private String idTipoMittente;
    private String mittente;
    private String idTipoDestinatario;
    private String destinatario;
    private String verso;
    private String oggetto;
    private String contenuto;
    private String numProtocollo;
    private Date dataProtocollo;
    private List<AllegatoDTO> allegati;
    //TODO: C***O UNA ENTITY IN UN DTO! E LA SERIALIZZAZIONE VA A FARSI BENEDIRE.
    private LkStatoPratica statoPost;
    private String idScript;
    private AttoriComunicazioneDTO destinatari;
    private String pubblicazionePortale = "N";
    private String invioEmail = "N";
    private String allegatoEmail = "N";
    private String protocollo = "N";
    private String scaricaRicevuta = "N";
    private String mostraDestinatari = "N";
    private String caricaDocumentoFirmato = "N";
    private String apriSottopratiche = "N";
    private String destinatariSoloEnti = "N";
    private String visualizzaProcedimentiRiferimento = "N";
    private String visualizzaScadenzeDaChiudere = "N";
    private String visualizzaGiorniScadenzaCustom = "N";
    private Procedimenti procedimentoRiferimento;
    private Integer numeroMassimoDestinatari;
    private String operatore;
    //^^CS AGGIUNTA Serve per la jsp di gestione template
    private Boolean checked = false;
//    private Boolean statoEmail = false;
    private String statoEmail;
    private List<EmailDTO> email;
    private String codEvento;
    private Integer idProcesso;
    private String desProcesso;
    private String funzioneApplicativa;

    public Integer getIdPraticaEvento() {
        return idPraticaEvento;
    }

    public void setIdPraticaEvento(Integer idPraticaEvento) {
        this.idPraticaEvento = idPraticaEvento;
    }

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

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(String dataEvento) {
        this.dataEvento = dataEvento;
    }

    public Integer getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Integer idUtente) {
        this.idUtente = idUtente;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getIdTipoMittente() {
        return idTipoMittente;
    }

    public void setIdTipoMittente(String idTipoMittente) {
        this.idTipoMittente = idTipoMittente;
    }

    public String getMittente() {
        return mittente;
    }

    public void setMittente(String mittente) {
        this.mittente = mittente;
    }

    public String getIdTipoDestinatario() {
        return idTipoDestinatario;
    }

    public void setIdTipoDestinatario(String idTipoDestinatario) {
        this.idTipoDestinatario = idTipoDestinatario;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getVerso() {
        return verso;
    }

    public void setVerso(String verso) {
        this.verso = verso;
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

    public List<AllegatoDTO> getAllegati() {
        return allegati;
    }

    public void setAllegati(List<AllegatoDTO> allegati) {
        this.allegati = allegati;
    }

    public LkStatoPratica getStatoPost() {
        return statoPost;
    }

    public void setStatoPost(LkStatoPratica statoPost) {
        this.statoPost = statoPost;
    }

    public String getIdScript() {
        return idScript;
    }

    public void setIdScript(String idScript) {
        this.idScript = idScript;
    }

    public AttoriComunicazioneDTO getDestinatari() {
        return destinatari;
    }

    public void setDestinatari(AttoriComunicazioneDTO destinatari) {
        this.destinatari = destinatari;
    }

    public Procedimenti getProcedimentoRiferimento() {
        return procedimentoRiferimento;
    }

    public void setProcedimentoRiferimento(Procedimenti procedimentoRiferimento) {
        this.procedimentoRiferimento = procedimentoRiferimento;
    }

    public Integer getNumeroMassimoDestinatari() {
        return numeroMassimoDestinatari;
    }

    public void setNumeroMassimoDestinatari(Integer numeroMassimoDestinatari) {
        this.numeroMassimoDestinatari = numeroMassimoDestinatari;
    }

    public String getOperatore() {
        return operatore;
    }

    public void setOperatore(String operatore) {
        this.operatore = operatore;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public String getStatoEmail() {
        return statoEmail;
    }

    public void setStatoEmail(String statoEmail) {
        this.statoEmail = statoEmail;
    }

    public List<EmailDTO> getEmail() {
        return email;
    }

    public void setEmail(List<EmailDTO> email) {
        this.email = email;
    }

    public String getCodEvento() {
        return codEvento;
    }

    public void setCodEvento(String codEvento) {
        this.codEvento = codEvento;
    }

    public Integer getIdProcesso() {
        return idProcesso;
    }

    public void setIdProcesso(Integer idProcesso) {
        this.idProcesso = idProcesso;
    }

    public String getDesProcesso() {
        return desProcesso;
    }

    public void setDesProcesso(String desProcesso) {
        this.desProcesso = desProcesso;
    }

    public String getNumProtocollo() {
        return numProtocollo;
    }

    public void setNumProtocollo(String numProtocollo) {
        this.numProtocollo = numProtocollo;
    }

    public Date getDataProtocollo() {
        return dataProtocollo;
    }

    public void setDataProtocollo(Date dataProtocollo) {
        this.dataProtocollo = dataProtocollo;
    }

    public String getFunzioneApplicativa() {
        return funzioneApplicativa;
    }

    public void setFunzioneApplicativa(String funzioneApplicativa) {
        this.funzioneApplicativa = funzioneApplicativa;
    }

    public String getPubblicazionePortale() {
        return pubblicazionePortale;
    }

    public void setPubblicazionePortale(String pubblicazionePortale) {
        this.pubblicazionePortale = pubblicazionePortale;
    }

    public String getInvioEmail() {
        return invioEmail;
    }

    public void setInvioEmail(String invioEmail) {
        this.invioEmail = invioEmail;
    }

    public String getAllegatoEmail() {
        return allegatoEmail;
    }

    public void setAllegatoEmail(String allegatoEmail) {
        this.allegatoEmail = allegatoEmail;
    }

    public String getProtocollo() {
        return protocollo;
    }

    public void setProtocollo(String protocollo) {
        this.protocollo = protocollo;
    }

    public String getScaricaRicevuta() {
        return scaricaRicevuta;
    }

    public void setScaricaRicevuta(String scaricaRicevuta) {
        this.scaricaRicevuta = scaricaRicevuta;
    }

    public String getMostraDestinatari() {
        return mostraDestinatari;
    }

    public void setMostraDestinatari(String mostraDestinatari) {
        this.mostraDestinatari = mostraDestinatari;
    }

    public String getCaricaDocumentoFirmato() {
        return caricaDocumentoFirmato;
    }

    public void setCaricaDocumentoFirmato(String caricaDocumentoFirmato) {
        this.caricaDocumentoFirmato = caricaDocumentoFirmato;
    }

    public String getApriSottopratiche() {
        return apriSottopratiche;
    }

    public void setApriSottopratiche(String apriSottopratiche) {
        this.apriSottopratiche = apriSottopratiche;
    }

    public String getDestinatariSoloEnti() {
        return destinatariSoloEnti;
    }

    public void setDestinatariSoloEnti(String destinatariSoloEnti) {
        this.destinatariSoloEnti = destinatariSoloEnti;
    }

    public String getVisualizzaProcedimentiRiferimento() {
        return visualizzaProcedimentiRiferimento;
    }

    public void setVisualizzaProcedimentiRiferimento(String visualizzaProcedimentiRiferimento) {
        this.visualizzaProcedimentiRiferimento = visualizzaProcedimentiRiferimento;
    }

    public String getVisualizzaScadenzeDaChiudere() {
        return visualizzaScadenzeDaChiudere;
    }

    public void setVisualizzaScadenzeDaChiudere(String visualizzaScadenzeDaChiudere) {
        this.visualizzaScadenzeDaChiudere = visualizzaScadenzeDaChiudere;
    }

    public String getVisualizzaGiorniScadenzaCustom() {
        return visualizzaGiorniScadenzaCustom;
    }

    public void setVisualizzaGiorniScadenzaCustom(String visualizzaGiorniScadenzaCustom) {
        this.visualizzaGiorniScadenzaCustom = visualizzaGiorniScadenzaCustom;
    }

    @Override
    public int compareTo(EventoDTO compareObject) {
        return ObjectUtils.compare(this.getDescrizione(), compareObject.getDescrizione());
    }
}
