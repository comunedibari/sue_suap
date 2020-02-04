package it.wego.cross.dto;

import java.io.Serializable;

/**
 *
 * @author giuseppe
 */
public class TemplateDTO implements Serializable {

    private Integer idEventoTemplate;
    private String descrizioneTemplate;
    private String nomeFile;
    private String mimeType;
    private String tipologiaTemplate;
    private AllegatoDTO allegato;
    //^^CS AGGIUNTa
    private Integer idEvento;
    private Integer idEnte;
    private Integer idProcedimento;
    private Integer idTemplate;

    public Integer getIdEventoTemplate() {
        return idEventoTemplate;
    }

    public void setIdEventoTemplate(Integer idEventoTemplate) {
        this.idEventoTemplate = idEventoTemplate;
    }

    public String getDescrizioneTemplate() {
        return descrizioneTemplate;
    }

    public void setDescrizioneTemplate(String descrizioneTemplate) {
        this.descrizioneTemplate = descrizioneTemplate;
    }

    public String getNomeFile() {
        return nomeFile;
    }

    public void setNomeFile(String nomeFile) {
        this.nomeFile = nomeFile;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Integer getIdTemplate() {
        return idTemplate;
    }

    public void setIdTemplate(Integer idTemplate) {
        this.idTemplate = idTemplate;
    }

    public AllegatoDTO getAllegato() {
        return allegato;
    }

    public void setAllegato(AllegatoDTO allegato) {
        this.allegato = allegato;
    }

    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public Integer getIdEnte() {
        return idEnte;
    }

    public void setIdEnte(Integer idEnte) {
        this.idEnte = idEnte;
    }

    public Integer getIdProcedimento() {
        return idProcedimento;
    }

    public void setIdProcedimento(Integer idProcedimento) {
        this.idProcedimento = idProcedimento;
    }

    public String getTipologiaTemplate() {
        return tipologiaTemplate;
    }

    public void setTipologiaTemplate(String tipologiaTemplate) {
        this.tipologiaTemplate = tipologiaTemplate;
    }
}
