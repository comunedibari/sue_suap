/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.dozer;

import java.util.List;

/**
 *
 * @author Gabriele
 */
public class EnteDTO {

    private Integer idEnte;
    private String codEnte;
    private String codiceFiscale;
    private String partitaIva;
    private String descrizione;
    private String indirizzo;
    private Integer cap;
    private String citta;
    private String provincia;
    private String telefono;
    private String fax;
    private String email;
    private String pec;
    private String codiceIstat;
    private String codiceCatastale;
    private String codiceAoo;
    private String tipoEnte;
    private String codEnteEsterno;
    private String unitaOrganizzativa;
    private String codiceAmministrazione;
    private String identificativoSuap;
    private EnteDTO idEntePadre;
    private List<ComuneDTO> comuniAbilitati;
    private List<ProcedimentoDTO> procedimentiAttivi;
    private List<ProcedimentoEnteDTO> endoProcedimentiEntiAttivi;

    public Integer getIdEnte() {
        return idEnte;
    }

    public void setIdEnte(Integer idEnte) {
        this.idEnte = idEnte;
    }

    public String getCodEnte() {
        return codEnte;
    }

    public void setCodEnte(String codEnte) {
        this.codEnte = codEnte;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getPartitaIva() {
        return partitaIva;
    }

    public void setPartitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public Integer getCap() {
        return cap;
    }

    public void setCap(Integer cap) {
        this.cap = cap;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPec() {
        return pec;
    }

    public void setPec(String pec) {
        this.pec = pec;
    }

    public String getCodiceIstat() {
        return codiceIstat;
    }

    public void setCodiceIstat(String codiceIstat) {
        this.codiceIstat = codiceIstat;
    }

    public String getCodiceCatastale() {
        return codiceCatastale;
    }

    public void setCodiceCatastale(String codiceCatastale) {
        this.codiceCatastale = codiceCatastale;
    }

    public String getCodiceAoo() {
        return codiceAoo;
    }

    public void setCodiceAoo(String codiceAoo) {
        this.codiceAoo = codiceAoo;
    }

    public String getTipoEnte() {
        return tipoEnte;
    }

    public void setTipoEnte(String tipoEnte) {
        this.tipoEnte = tipoEnte;
    }

    public String getCodEnteEsterno() {
        return codEnteEsterno;
    }

    public void setCodEnteEsterno(String codEnteEsterno) {
        this.codEnteEsterno = codEnteEsterno;
    }

    public String getUnitaOrganizzativa() {
        return unitaOrganizzativa;
    }

    public void setUnitaOrganizzativa(String unitaOrganizzativa) {
        this.unitaOrganizzativa = unitaOrganizzativa;
    }

    public String getCodiceAmministrazione() {
        return codiceAmministrazione;
    }

    public void setCodiceAmministrazione(String codiceAmministrazione) {
        this.codiceAmministrazione = codiceAmministrazione;
    }

    public String getIdentificativoSuap() {
        return identificativoSuap;
    }

    public void setIdentificativoSuap(String identificativoSuap) {
        this.identificativoSuap = identificativoSuap;
    }

    public EnteDTO getIdEntePadre() {
        return idEntePadre;
    }

    public void setIdEntePadre(EnteDTO idEntePadre) {
        this.idEntePadre = idEntePadre;
    }

    public List<ComuneDTO> getComuniAbilitati() {
        return comuniAbilitati;
    }

    public void setComuniAbilitati(List<ComuneDTO> comuniAbilitati) {
        this.comuniAbilitati = comuniAbilitati;
    }

    public List<ProcedimentoDTO> getProcedimentiAttivi() {
        return procedimentiAttivi;
    }

    public void setProcedimentiAttivi(List<ProcedimentoDTO> procedimentiAttivi) {
        this.procedimentiAttivi = procedimentiAttivi;
    }

    public List<ProcedimentoEnteDTO> getEndoProcedimentiEntiAttivi() {
        return endoProcedimentiEntiAttivi;
    }

    public void setEndoProcedimentiEntiAttivi(List<ProcedimentoEnteDTO> endoProcedimentiEntiAttivi) {
        this.endoProcedimentiEntiAttivi = endoProcedimentiEntiAttivi;
    }

}
