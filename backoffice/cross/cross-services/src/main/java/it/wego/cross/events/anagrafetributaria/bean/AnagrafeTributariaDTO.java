/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.events.anagrafetributaria.bean;

import java.util.List;

/**
 *
 * @author Gabriele
 */
public class AnagrafeTributariaDTO {

    private Integer idPratica;
    private Integer idEvento;
    private Integer idDettaglio;
    private Integer idSoggettoObbligato;
    private Integer idEnte;
    private Integer idNaturaUfficio;
    private String codiceFiscaleSoggettoObbligato;
    private Integer annoRiferimento;
    private String codFornitura;
    //Sezione Richiesta
    private String tipoRichiesta;
    private String tipoIntervento;
    private String tipologiaRichiesta;
    private String dataInizioLavori;
    private String dataFineLavori;
    private IndirizzoRichiestaDTO indirizzo;
    //Beneficiari
    private List<AnagraficaDTO> richiedenti;
    //Beneficiari
    private List<AnagraficaDTO> beneficiari;
    //Dati catastali
    private List<DatiCatastaliDTO> datiCatastali;
    //Professionisti
    private List<AnagraficaDTO> professionisti;
    //Imprese
    private List<ImpresaDTO> imprese;

    public Integer getIdPratica() {
        return idPratica;
    }

    public void setIdPratica(Integer idPratica) {
        this.idPratica = idPratica;
    }

    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public Integer getIdDettaglio() {
        return idDettaglio;
    }

    public void setIdDettaglio(Integer idDettaglio) {
        this.idDettaglio = idDettaglio;
    }

    public Integer getIdSoggettoObbligato() {
        return idSoggettoObbligato;
    }

    public void setIdSoggettoObbligato(Integer idSoggettoObbligato) {
        this.idSoggettoObbligato = idSoggettoObbligato;
    }

    public Integer getIdEnte() {
        return idEnte;
    }

    public void setIdEnte(Integer idEnte) {
        this.idEnte = idEnte;
    }

    public Integer getIdNaturaUfficio() {
        return idNaturaUfficio;
    }

    public void setIdNaturaUfficio(Integer idNaturaUfficio) {
        this.idNaturaUfficio = idNaturaUfficio;
    }

    public String getCodiceFiscaleSoggettoObbligato() {
        return codiceFiscaleSoggettoObbligato;
    }

    public void setCodiceFiscaleSoggettoObbligato(String codiceFiscaleSoggettoObbligato) {
        this.codiceFiscaleSoggettoObbligato = codiceFiscaleSoggettoObbligato;
    }

    public Integer getAnnoRiferimento() {
        return annoRiferimento;
    }

    public void setAnnoRiferimento(Integer annoRiferimento) {
        this.annoRiferimento = annoRiferimento;
    }

    public String getCodFornitura() {
        return codFornitura;
    }

    public void setCodFornitura(String codFornitura) {
        this.codFornitura = codFornitura;
    }

    public String getTipoRichiesta() {
        return tipoRichiesta;
    }

    public void setTipoRichiesta(String tipoRichiesta) {
        this.tipoRichiesta = tipoRichiesta;
    }

    public String getTipoIntervento() {
        return tipoIntervento;
    }

    public void setTipoIntervento(String tipoIntervento) {
        this.tipoIntervento = tipoIntervento;
    }

    public String getTipologiaRichiesta() {
        return tipologiaRichiesta;
    }

    public void setTipologiaRichiesta(String tipologiaRichiesta) {
        this.tipologiaRichiesta = tipologiaRichiesta;
    }

    public String getDataInizioLavori() {
        return dataInizioLavori;
    }

    public void setDataInizioLavori(String dataInizioLavori) {
        this.dataInizioLavori = dataInizioLavori;
    }

    public String getDataFineLavori() {
        return dataFineLavori;
    }

    public void setDataFineLavori(String dataFineLavori) {
        this.dataFineLavori = dataFineLavori;
    }

    public IndirizzoRichiestaDTO getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(IndirizzoRichiestaDTO indirizzo) {
        this.indirizzo = indirizzo;
    }

    public List<AnagraficaDTO> getRichiedenti() {
        return richiedenti;
    }

    public void setRichiedenti(List<AnagraficaDTO> richiedenti) {
        this.richiedenti = richiedenti;
    }

    public List<AnagraficaDTO> getBeneficiari() {
        return beneficiari;
    }

    public void setBeneficiari(List<AnagraficaDTO> beneficiari) {
        this.beneficiari = beneficiari;
    }

    public List<DatiCatastaliDTO> getDatiCatastali() {
        return datiCatastali;
    }

    public void setDatiCatastali(List<DatiCatastaliDTO> datiCatastali) {
        this.datiCatastali = datiCatastali;
    }

    public List<AnagraficaDTO> getProfessionisti() {
        return professionisti;
    }

    public void setProfessionisti(List<AnagraficaDTO> professionisti) {
        this.professionisti = professionisti;
    }

    public List<ImpresaDTO> getImprese() {
        return imprese;
    }

    public void setImprese(List<ImpresaDTO> imprese) {
        this.imprese = imprese;
    }

}
