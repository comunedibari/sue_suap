package it.wego.cross.beans;

public class ProcedimentoPermessiBean {

    private String nome;
    private Integer termini;
    private String abilitazione;
    private String codPermesso;
    private String descrizionePermesso;
    private Integer codUtente;
    private Integer codEnte;
    private Integer codProcedimento;
    private Integer idProcEnte;
    private Integer codRuolo;

    public Integer getCodRuolo() {
        return codRuolo;
    }

    public void setCodRuolo(Integer codRuolo) {
        this.codRuolo = codRuolo;
    }

    public Integer getIdProcEnte() {
        return idProcEnte;
    }

    public void setIdProcEnte(Integer idProcEnte) {
        this.idProcEnte = idProcEnte;
    }

    public Integer getCodEnte() {
        return codEnte;
    }

    public void setCodEnte(Integer codEnte) {
        this.codEnte = codEnte;
    }

    public Integer getCodProcedimento() {
        return codProcedimento;
    }

    public void setCodProcedimento(Integer codProcedimento) {
        this.codProcedimento = codProcedimento;
    }

    public Integer getCodUtente() {
        return codUtente;
    }

    public void setCodUtente(Integer codUtente) {
        this.codUtente = codUtente;
    }

    public String getAbilitazione() {
        return abilitazione;
    }

    public void setAbilitazione(String abilitazione) {
        this.abilitazione = abilitazione;
    }

    public String getCodPermesso() {
        return codPermesso;
    }

    public void setCodPermesso(String codPermesso) {
        this.codPermesso = codPermesso;
    }

    public String getDescrizionePermesso() {
        return descrizionePermesso;
    }

    public void setDescrizionePermesso(String descrizionePermesso) {
        this.descrizionePermesso = descrizionePermesso;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getTermini() {
        return termini;
    }

    public void setTermini(Integer termini) {
        this.termini = termini;
    }
}
