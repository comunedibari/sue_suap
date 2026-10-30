/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.dozer;

import java.util.Date;

/**
 *
 * @author Giuseppe
 */
public class AnagraficaDTO {

    private Integer idAnagrafica;
    private String tipoAnagrafica;
    private String cognome;
    private String nome;
    private String codiceFiscale;
    private String denominazione;
    private String partitaIva;
    private String flgIndividuale;
    private Date dataNascita;
    private String localitaNascita;
    private String sesso;
    private String numeroIscrizione;
    private Date dataIscrizione;
    private String flgAttesaIscrizioneRi;
    private String flgObbligoIscrizioneRi;
    private Date dataIscrizioneRi;
    private String nIscrizioneRi;
    private String flgAttesaIscrizioneRea;
    private Date dataIscrizioneRea;
    private String nIscrizioneRea;
    private String varianteAnagrafica;
    private LkFormeGiuridicheDTO idFormaGiuridica;
    private LkNazionalitaDTO idNazionalita;
    private ProvinciaDTO idProvinciaIscrizione;
    private ProvinciaDTO idProvinciaCciaa;
    private LkTipoCollegioDTO idTipoCollegio;
    private ComuneDTO idComuneNascita;
    private LkCittadinanzaDTO idCittadinanza;

//    private List<AnagraficaRecapiti> anagraficaRecapitiList;
    public Integer getIdAnagrafica() {
        return idAnagrafica;
    }

    public void setIdAnagrafica(Integer idAnagrafica) {
        this.idAnagrafica = idAnagrafica;
    }

    public String getTipoAnagrafica() {
        return tipoAnagrafica;
    }

    public void setTipoAnagrafica(String tipoAnagrafica) {
        this.tipoAnagrafica = tipoAnagrafica;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getDenominazione() {
        return denominazione;
    }

    public void setDenominazione(String denominazione) {
        this.denominazione = denominazione;
    }

    public String getPartitaIva() {
        return partitaIva;
    }

    public void setPartitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
    }

    public String getFlgIndividuale() {
        return flgIndividuale;
    }

    public void setFlgIndividuale(String flgIndividuale) {
        this.flgIndividuale = flgIndividuale;
    }

    public Date getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getLocalitaNascita() {
        return localitaNascita;
    }

    public void setLocalitaNascita(String localitaNascita) {
        this.localitaNascita = localitaNascita;
    }

    public String getSesso() {
        return sesso;
    }

    public void setSesso(String sesso) {
        this.sesso = sesso;
    }

    public String getNumeroIscrizione() {
        return numeroIscrizione;
    }

    public void setNumeroIscrizione(String numeroIscrizione) {
        this.numeroIscrizione = numeroIscrizione;
    }

    public Date getDataIscrizione() {
        return dataIscrizione;
    }

    public void setDataIscrizione(Date dataIscrizione) {
        this.dataIscrizione = dataIscrizione;
    }

    public String getFlgAttesaIscrizioneRi() {
        return flgAttesaIscrizioneRi;
    }

    public void setFlgAttesaIscrizioneRi(String flgAttesaIscrizioneRi) {
        this.flgAttesaIscrizioneRi = flgAttesaIscrizioneRi;
    }

    public String getFlgObbligoIscrizioneRi() {
        return flgObbligoIscrizioneRi;
    }

    public void setFlgObbligoIscrizioneRi(String flgObbligoIscrizioneRi) {
        this.flgObbligoIscrizioneRi = flgObbligoIscrizioneRi;
    }

    public Date getDataIscrizioneRi() {
        return dataIscrizioneRi;
    }

    public void setDataIscrizioneRi(Date dataIscrizioneRi) {
        this.dataIscrizioneRi = dataIscrizioneRi;
    }

    public String getnIscrizioneRi() {
        return nIscrizioneRi;
    }

    public void setnIscrizioneRi(String nIscrizioneRi) {
        this.nIscrizioneRi = nIscrizioneRi;
    }

    public String getFlgAttesaIscrizioneRea() {
        return flgAttesaIscrizioneRea;
    }

    public void setFlgAttesaIscrizioneRea(String flgAttesaIscrizioneRea) {
        this.flgAttesaIscrizioneRea = flgAttesaIscrizioneRea;
    }

    public Date getDataIscrizioneRea() {
        return dataIscrizioneRea;
    }

    public void setDataIscrizioneRea(Date dataIscrizioneRea) {
        this.dataIscrizioneRea = dataIscrizioneRea;
    }

    public String getnIscrizioneRea() {
        return nIscrizioneRea;
    }

    public void setnIscrizioneRea(String nIscrizioneRea) {
        this.nIscrizioneRea = nIscrizioneRea;
    }

    public String getVarianteAnagrafica() {
        return varianteAnagrafica;
    }

    public void setVarianteAnagrafica(String varianteAnagrafica) {
        this.varianteAnagrafica = varianteAnagrafica;
    }

    public LkFormeGiuridicheDTO getIdFormaGiuridica() {
        return idFormaGiuridica;
    }

    public void setIdFormaGiuridica(LkFormeGiuridicheDTO idFormaGiuridica) {
        this.idFormaGiuridica = idFormaGiuridica;
    }

    public LkNazionalitaDTO getIdNazionalita() {
        return idNazionalita;
    }

    public void setIdNazionalita(LkNazionalitaDTO idNazionalita) {
        this.idNazionalita = idNazionalita;
    }

    public ProvinciaDTO getIdProvinciaIscrizione() {
        return idProvinciaIscrizione;
    }

    public void setIdProvinciaIscrizione(ProvinciaDTO idProvinciaIscrizione) {
        this.idProvinciaIscrizione = idProvinciaIscrizione;
    }

    public ProvinciaDTO getIdProvinciaCciaa() {
        return idProvinciaCciaa;
    }

    public void setIdProvinciaCciaa(ProvinciaDTO idProvinciaCciaa) {
        this.idProvinciaCciaa = idProvinciaCciaa;
    }

    public LkTipoCollegioDTO getIdTipoCollegio() {
        return idTipoCollegio;
    }

    public void setIdTipoCollegio(LkTipoCollegioDTO idTipoCollegio) {
        this.idTipoCollegio = idTipoCollegio;
    }

    public ComuneDTO getIdComuneNascita() {
        return idComuneNascita;
    }

    public void setIdComuneNascita(ComuneDTO idComuneNascita) {
        this.idComuneNascita = idComuneNascita;
    }

    public LkCittadinanzaDTO getIdCittadinanza() {
        return idCittadinanza;
    }

    public void setIdCittadinanza(LkCittadinanzaDTO idCittadinanza) {
        this.idCittadinanza = idCittadinanza;
    }

}
