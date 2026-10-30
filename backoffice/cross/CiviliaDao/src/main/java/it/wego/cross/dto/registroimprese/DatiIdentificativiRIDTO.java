/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.registroimprese;

import it.wego.cross.dto.RecapitoDTO;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author giuseppe
 */
public class DatiIdentificativiRIDTO implements Serializable {

    private String statoImpresa;
    private String denominazione;
    private String codiceFiscale;
    private String cciaa;
    private String desCciaa;
    private Integer idCciaa;
    private String nrea;
    private String partitaIva;
    //^^CS MODIFICA private RecapitoRIDTO sedeLegale;
    private RecapitoDTO sedeLegale;
    private List<AttivitaIstatRIDTO> attivita;
    private ErroreRIDTO errore;
    private List<WarningRIDTO> warning;

    public String getStatoImpresa() {
        return statoImpresa;
    }

    public void setStatoImpresa(String statoImpresa) {
        this.statoImpresa = statoImpresa;
    }

    public String getDenominazione() {
        return denominazione;
    }

    public void setDenominazione(String denominazione) {
        this.denominazione = denominazione;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getDesCciaa() {
        return desCciaa;
    }

    public void setDesCciaa(String desCciaa) {
        this.desCciaa = desCciaa;
    }

    public Integer getIdCciaa() {
        return idCciaa;
    }

    public void setIdCciaa(Integer idCciaa) {
        this.idCciaa = idCciaa;
    }

    public String getNrea() {
        return nrea;
    }

    public void setNrea(String nrea) {
        this.nrea = nrea;
    }

    public String getPartitaIva() {
        return partitaIva;
    }

    public void setPartitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
    }
/* ^^CS MODIFICA
    public RecapitoRIDTO getSedeLegale() {
        return sedeLegale;
    }

    public void setSedeLegale(RecapitoRIDTO sedeLegale) {
        this.sedeLegale = sedeLegale;
    }
*/

    public RecapitoDTO getSedeLegale() {
        return sedeLegale;
    }

    public void setSedeLegale(RecapitoDTO sedeLegale) {
        this.sedeLegale = sedeLegale;
    }
    
    public List<AttivitaIstatRIDTO> getAttivita() {
        return attivita;
    }

    public void setAttivita(List<AttivitaIstatRIDTO> attivita) {
        this.attivita = attivita;
    }

    public ErroreRIDTO getErrore() {
        return errore;
    }

    public void setErrore(ErroreRIDTO errore) {
        this.errore = errore;
    }

    public List<WarningRIDTO> getWarning() {
        return warning;
    }

    public void setWarning(List<WarningRIDTO> warning) {
        this.warning = warning;
    }

    public String getCciaa() {
        return cciaa;
    }

    public void setCciaa(String cciaa) {
        this.cciaa = cciaa;
    }
}
