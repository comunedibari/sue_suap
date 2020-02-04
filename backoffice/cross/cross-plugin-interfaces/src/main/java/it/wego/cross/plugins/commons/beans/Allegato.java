package it.wego.cross.plugins.commons.beans;

public class Allegato {

    private Integer id;
    private String descrizione;
    private String nomeFile;
    private String tipoFile;
    private String pathFile;
    private String idEsterno;
    private Boolean protocolla;
    private Boolean spedisci = Boolean.TRUE;
    private byte[] file;
    private Boolean fileOrigine = Boolean.FALSE;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getNomeFile() {
        return nomeFile;
    }

    public void setNomeFile(String nomeFile) {
        this.nomeFile = nomeFile;
    }

    public String getTipoFile() {
        return tipoFile;
    }

    public void setTipoFile(String tipoFile) {
        this.tipoFile = tipoFile;
    }

    public String getPathFile() {
        return pathFile;
    }

    public void setPathFile(String pathFile) {
        this.pathFile = pathFile;
    }

    public String getIdEsterno() {
        return idEsterno;
    }

    public void setIdEsterno(String idEsterno) {
        this.idEsterno = idEsterno;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public Boolean getProtocolla() {
        return protocolla;
    }

    public void setProtocolla(Boolean protocolla) {
        this.protocolla = protocolla;
    }

    public Boolean getFileOrigine() {
        return fileOrigine;
    }

    public void setFileOrigine(Boolean fileOrigine) {
        this.fileOrigine = fileOrigine;
    }

    public Boolean getSpedisci() {
        return spedisci;
    }

    public void setSpedisci(Boolean spedisci) {
        this.spedisci = spedisci;
    }
}
