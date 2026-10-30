package it.wego.cross.dto;

import java.io.Serializable;

/**
 *
 * @author CS
 */
public class EmailDTO implements Serializable {

    private Integer idEmail;
    private Integer idEvento;
    private Integer idPratica;
    private String taskId;
    private String dataInserimento;
    private String dataAggiornamento;
    private String pathEml;
    private String idMessaggio;
    private String idMessaggioPec;
    private String emailDestinatario;
    private String corpoEmail;
    private String oggettoEmail;
    private String oggettoRisposta;
    private String corpoRisposta;
    private String stato;
    private String statoDescrizione;
    private String oggettoPratica;
    private String descrizioneEvento;

    public Integer getIdEmail() {
        return idEmail;
    }

    public void setIdEmail(Integer idEmail) {
        this.idEmail = idEmail;
    }

    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public String getDataInserimento() {
        return dataInserimento;
    }

    public void setDataInserimento(String dataInserimento) {
        this.dataInserimento = dataInserimento;
    }

    public String getDataAggiornamento() {
        return dataAggiornamento;
    }

    public void setDataAggiornamento(String dataAggiornamento) {
        this.dataAggiornamento = dataAggiornamento;
    }

    public String getPathEml() {
        return pathEml;
    }

    public void setPathEml(String pathEml) {
        this.pathEml = pathEml;
    }

    public String getIdMessaggio() {
        return idMessaggio;
    }

    public void setIdMessaggio(String idMessaggio) {
        this.idMessaggio = idMessaggio;
    }

    public String getIdMessaggioPec() {
        return idMessaggioPec;
    }

    public void setIdMessaggioPec(String idMessaggioPec) {
        this.idMessaggioPec = idMessaggioPec;
    }

    public String getEmailDestinatario() {
        return emailDestinatario;
    }

    public void setEmailDestinatario(String emailDestinatario) {
        this.emailDestinatario = emailDestinatario;
    }

    public String getCorpoEmail() {
        return corpoEmail;
    }

    public void setCorpoEmail(String corpoEmail) {
        this.corpoEmail = corpoEmail;
    }

    public String getOggettoEmail() {
        return oggettoEmail;
    }

    public void setOggettoEmail(String oggettoEmail) {
        this.oggettoEmail = oggettoEmail;
    }

    public String getOggettoRisposta() {
        return oggettoRisposta;
    }

    public void setOggettoRisposta(String oggettoRisposta) {
        this.oggettoRisposta = oggettoRisposta;
    }

    public String getCorpoRisposta() {
        return corpoRisposta;
    }

    public void setCorpoRisposta(String corpoRisposta) {
        this.corpoRisposta = corpoRisposta;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getStatoDescrizione() {
        return statoDescrizione;
    }

    public void setStatoDescrizione(String statoDescrizione) {
        this.statoDescrizione = statoDescrizione;
    }

    public Integer getIdPratica() {
        return idPratica;
    }

    public void setIdPratica(Integer idPratica) {
        this.idPratica = idPratica;
    }

    public String getOggettoPratica() {
        return oggettoPratica;
    }

    public void setOggettoPratica(String oggettoPratica) {
        this.oggettoPratica = oggettoPratica;
    }

    public String getDescrizioneEvento() {
        return descrizioneEvento;
    }

    public void setDescrizioneEvento(String descrizioneEvento) {
        this.descrizioneEvento = descrizioneEvento;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

}
