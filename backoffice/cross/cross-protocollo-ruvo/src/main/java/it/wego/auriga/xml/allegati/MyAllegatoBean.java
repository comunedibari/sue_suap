package it.wego.auriga.xml.allegati;

public class MyAllegatoBean {

    private int idAllegato;
    private String nomeFile;
    private String descrizioneOggetto;
    private String versione;
    private Boolean primario = Boolean.FALSE;

    public int getIdAllegato() {
        return idAllegato;
    }

    public void setIdAllegato(int idAllegato) {
        this.idAllegato = idAllegato;
    }

    public String getNomeFile() {
        return nomeFile;
    }

    public void setNomeFile(String nomeFile) {
        this.nomeFile = nomeFile;
    }

    public String getDescrizioneOggetto() {
        return descrizioneOggetto;
    }

    public void setDescrizioneOggetto(String descrizioneOggetto) {
        this.descrizioneOggetto = descrizioneOggetto;
    }

    public String getVersione() {
        return versione;
    }

    public void setVersione(String versione) {
        this.versione = versione;
    }

    public Boolean isPrimario() {
        return primario;
    }

    public void setPrimario(Boolean primario) {
        this.primario = primario;
    }

}
