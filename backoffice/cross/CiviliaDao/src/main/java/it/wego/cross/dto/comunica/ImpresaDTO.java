/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.comunica;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author giuseppe
 */
public class ImpresaDTO implements Serializable {

    private String formaGiuridicaOrigine;
    @NotNull(message = "error.comunica.impresa.formagiuridica")
    @Size(min = 1, message = "error.comunica.impresa.formagiuridica")
    private String codFormaGiuridica;
    private String desFormaGiuridica;
    private String ragioneSociale;
    private String codiceFiscale;
    private String partitaIva;
    private String numeroRea;
    private String provinciaReaOrigine;
    @NotNull(message = "error.comunica.impresa.provincia")
    @Size(min = 1, message = "error.comunica.impresa.provincia")
    private String provinciaRea;
    private String dataIscrizione;
    private String desProvinciaSede;
    private String codCatastaleProvinciaSede;
    private String codCatastaleComuneSede;
    private String desComuneSede;
    private String capSede;
    private String indirizzoSede;
    private String civicoSede;
    private String telefono;
    private String pec;
    private String desProvincia;
    private String codCatastaleProvincia;
    private String codCatastaleComune;
    private String desComune;
    private String cap;
    private String toponimo;
    private String indirizzo;
    private String civico;

    public String getFormaGiuridicaOrigine() {
        return formaGiuridicaOrigine;
    }

    public void setFormaGiuridicaOrigine(String formaGiuridicaOrigine) {
        this.formaGiuridicaOrigine = formaGiuridicaOrigine;
    }

    public String getCodFormaGiuridica() {
        return codFormaGiuridica;
    }

    public void setCodFormaGiuridica(String codFormaGiuridica) {
        this.codFormaGiuridica = codFormaGiuridica;
    }

    public String getDesFormaGiuridica() {
        return desFormaGiuridica;
    }

    public void setDesFormaGiuridica(String desFormaGiuridica) {
        this.desFormaGiuridica = desFormaGiuridica;
    }

    public String getRagioneSociale() {
        return ragioneSociale;
    }

    public void setRagioneSociale(String ragioneSociale) {
        this.ragioneSociale = ragioneSociale;
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

    public String getNumeroRea() {
        return numeroRea;
    }

    public void setNumeroRea(String numeroRea) {
        this.numeroRea = numeroRea;
    }

    public String getProvinciaReaOrigine() {
        return provinciaReaOrigine;
    }

    public void setProvinciaReaOrigine(String provinciaReaOrigine) {
        this.provinciaReaOrigine = provinciaReaOrigine;
    }

    public String getProvinciaRea() {
        return provinciaRea;
    }

    public void setProvinciaRea(String provinciaRea) {
        this.provinciaRea = provinciaRea;
    }

    public String getDataIscrizione() {
        return dataIscrizione;
    }

    public void setDataIscrizione(String dataIscrizione) {
        this.dataIscrizione = dataIscrizione;
    }

    public String getDesProvinciaSede() {
        return desProvinciaSede;
    }

    public void setDesProvinciaSede(String desProvinciaSede) {
        this.desProvinciaSede = desProvinciaSede;
    }

    public String getCodCatastaleProvinciaSede() {
        return codCatastaleProvinciaSede;
    }

    public void setCodCatastaleProvinciaSede(String codCatastaleProvinciaSede) {
        this.codCatastaleProvinciaSede = codCatastaleProvinciaSede;
    }

    public String getCodCatastaleComuneSede() {
        return codCatastaleComuneSede;
    }

    public void setCodCatastaleComuneSede(String codCatastaleComuneSede) {
        this.codCatastaleComuneSede = codCatastaleComuneSede;
    }

    public String getDesComuneSede() {
        return desComuneSede;
    }

    public void setDesComuneSede(String desComuneSede) {
        this.desComuneSede = desComuneSede;
    }

    public String getCapSede() {
        return capSede;
    }

    public void setCapSede(String capSede) {
        this.capSede = capSede;
    }

    public String getIndirizzoSede() {
        return indirizzoSede;
    }

    public void setIndirizzoSede(String indirizzoSede) {
        this.indirizzoSede = indirizzoSede;
    }

    public String getCivicoSede() {
        return civicoSede;
    }

    public void setCivicoSede(String civicoSede) {
        this.civicoSede = civicoSede;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPec() {
        return pec;
    }

    public void setPec(String pec) {
        this.pec = pec;
    }

    public String getDesProvincia() {
        return desProvincia;
    }

    public void setDesProvincia(String desProvincia) {
        this.desProvincia = desProvincia;
    }

    public String getCodCatastaleProvincia() {
        return codCatastaleProvincia;
    }

    public void setCodCatastaleProvincia(String codCatastaleProvincia) {
        this.codCatastaleProvincia = codCatastaleProvincia;
    }

    public String getCodCatastaleComune() {
        return codCatastaleComune;
    }

    public void setCodCatastaleComune(String codCatastaleComune) {
        this.codCatastaleComune = codCatastaleComune;
    }

    public String getDesComune() {
        return desComune;
    }

    public void setDesComune(String desComune) {
        this.desComune = desComune;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getToponimo() {
        return toponimo;
    }

    public void setToponimo(String toponimo) {
        this.toponimo = toponimo;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getCivico() {
        return civico;
    }

    public void setCivico(String civico) {
        this.civico = civico;
    }
}
