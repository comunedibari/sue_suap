package it.wego.cross.dto;

import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author CS
 */
public class EventoTemplateDTO extends AbstractDTO {

    private Integer idEventoTemplate;
    private Integer idEvento;
    private Integer idEnte;
    private Integer idProcedimento;
    private Integer idProcesso;
    private Integer idTemplate;
    private String descEventoTemplate;
    private String descProcesso;
    private String descEvento;
    private String descEnte;
    private String descProcedimento;
    private String descTemplate;
    private String nomefile;
    private MultipartFile file;

    public Integer getIdEventoTemplate() {
        return idEventoTemplate;
    }

    public void setIdEventoTemplate(Integer idEventoTemplate) {
        this.idEventoTemplate = idEventoTemplate;
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

    public Integer getIdProcesso() {
        return idProcesso;
    }

    public void setIdProcesso(Integer idProcesso) {
        this.idProcesso = idProcesso;
    }

    public Integer getIdTemplate() {
        return idTemplate;
    }

    public void setIdTemplate(Integer idTemplate) {
        this.idTemplate = idTemplate;
    }

    public String getDescEventoTemplate() {
        return descEventoTemplate;
    }

    public void setDescEventoTemplate(String descEventoTemplate) {
        this.descEventoTemplate = descEventoTemplate;
    }

    public String getDescEvento() {
        return descEvento;
    }

    public void setDescEvento(String descEvento) {
        this.descEvento = descEvento;
    }

    public String getDescEnte() {
        return descEnte;
    }

    public void setDescEnte(String descEnte) {
        this.descEnte = descEnte;
    }

    public String getDescProcedimento() {
        return descProcedimento;
    }

    public void setDescProcedimento(String descProcedimento) {
        this.descProcedimento = descProcedimento;
    }

    public String getDescTemplate() {
        return descTemplate;
    }

    public void setDescTemplate(String descTemplate) {
        this.descTemplate = descTemplate;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getNomefile() {
        return nomefile;
    }

    public void setNomefile(String nomefile) {
        this.nomefile = nomefile;
    }

    public String getDescProcesso() {
        return descProcesso;
    }

    public void setDescProcesso(String descProcesso) {
        this.descProcesso = descProcesso;
    }
}
