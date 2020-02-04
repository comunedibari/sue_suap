/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto;

import it.wego.cross.constants.Constants;
import it.wego.cross.utils.Utils;
import it.wego.cross.validator.AlphaNumeric;
import it.wego.cross.validator.Alphabetic;
import it.wego.cross.validator.CodiceFiscale;
import it.wego.cross.validator.Numeric;
import it.wego.cross.validator.NumeroRegistroImprese;
import it.wego.cross.validator.PartitaIva;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * AnagraficaCS ver 2.0 unione di Destinatari, Azieenda e anagrafica
 *
 * //
 *
 * @author CS
 */
public class AnagraficaDTO extends AbstractDTO implements Serializable {

    String collegata = "";
    Boolean manuale = false;
    String daRubrica = "N";
    private String tipoAnagrafica;
    @NotEmpty(message = "{error.tipoAnagraficaEmpty}")
    @Length(max = 1, message = "{error.tipoAnagraficaLength}")
    private String idTipoPersona;
    @NotNull(message = "{error.tipoAnagraficaAlphabetic}")
    private Integer counter;
    private Integer codAnagrafica;
    private Integer idAnagrafica;
    private Integer idPratica;
    @CodiceFiscale(message = "{error.codiceFiscale}")
    private String codiceFiscale;
    @PartitaIva(message = "{error.partitaIva}")
    private String partitaIva;
    private Integer idTipoRuolo;
    private Integer idTipoRuoloOriginale;
    private String desTipoRuolo;
    private String codTipoRuolo;
    private String nome;
    private String cognome;
    private String denominazione;
    //Fisica Professionista
    @Past(message = "{error.dataNascita}")
    private Date dataNascita;
    private String desCittadinanza;
    private Integer idCittadinanza;
    private String desNazionalita;
    private Integer idNazionalita;
    private String numeroIscrizione;
    private Integer idProvinciaIscrizione;
    @Past(message = "{error.dataIscrizione}")
    private Date dataIscrizione;
    //Forma Giuridica
    private Integer idFormaGiuridica;
    private String desFormaGiuridica;
    private String assensoUsoPec;
    @Length(max = 1, message = "{error.sesso}")
    private String sesso;
    private RecapitoDTO notifica;
    private ComuneDTO comuneNascita;
    private ProvinciaDTO provinciaIscrizione;
    private ProvinciaDTO provinciaCciaa;
    private List<RecapitoDTO> recapiti;
    @Past(message = "{error.dataInizioValidita}")
    private Date dataInizioValidita;
    private Date dataIscrizioneRea;
    private Date dataIscrizioneRi;
    private Integer codTipoCollegio;
    private String desTipoCollegio;
    private String descrizioneTitolarita;
    @Max(value = 1, message = "{error.flgAttesaIscrizioneRea}")
    private String flgAttesaIscrizioneRea;
    @Max(value = 1, message = "{error.flgAttesaIscrizioneRi}")
    private String flgAttesaIscrizioneRi;
    @Max(value = 1, message = "{error.flgIndividuale}")
    private String flgIndividuale;
    @Max(value = 1, message = "{error.flgObbligoIscrizioneRi}")
    private String flgObbligoIscrizioneRi;
    private String localitaNascita;
    private Integer idTipoCollegio;
    @Numeric(message = "{error.nIscrizioneRea}")
    private String nIscrizioneRea;
    @NumeroRegistroImprese
    @Size(max = 20, message = "{error.iscrizioneri.size}")
    private String nIscrizioneRi;
    private String confermata;
    private Integer idTipoQualifica;
    private String desTipoQualifica;
    private String varianteAnagrafica;

    public String getCollegata() {
        return collegata;
    }

    public void setCollegata(String yes) {
        this.collegata = yes;
    }

    public String getDaRubrica() {
        return daRubrica;
    }

    public void setDaRubrica(String daRubrica) {
        this.daRubrica = daRubrica;
    }

    public Boolean getManuale() {
        if (manuale == null) {
            return false;
        }
        return manuale;
    }
    /*^^CS NON ELIMINAE */

    public void setManuale(Boolean manuale) {
        if (manuale == null) {
            this.manuale = false;
        } else {
            this.manuale = manuale;
        }
    }

    /*^^CS NON ELIMINAE */
    public void setTipoAnagrafica(String tipoAnagrafica) {
        this.tipoAnagrafica = tipoAnagrafica;
        // AnagraficaUtils.getTipoAnagrafica(this);
    }

    /*^^CS NON ELIMINAE */
    public String getIdTipoPersona() {
        return idTipoPersona;
    }

    public void setIdTipoPersona(String idTipoPersona) {
        this.idTipoPersona = idTipoPersona;
    }

    public String getTipoAnagrafica() {
        return tipoAnagrafica;
    }

    public String getAssensoUsoPec() {
        return assensoUsoPec;
    }

    public void setAssensoUsoPec(String assensoUsoPec) {
        this.assensoUsoPec = assensoUsoPec;
    }

    public Integer getCodAnagrafica() {
        return codAnagrafica;
    }

    public void setCodAnagrafica(Integer codAnagrafica) {
        this.codAnagrafica = codAnagrafica;
    }

    public Integer getCodTipoCollegio() {
        return codTipoCollegio;
    }

    public void setCodTipoCollegio(Integer codTipoCollegio) {
        this.codTipoCollegio = codTipoCollegio;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public ComuneDTO getComuneNascita() {
        return comuneNascita;
    }

    public void setComuneNascita(ComuneDTO comuneNascita) {
        this.comuneNascita = comuneNascita;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public Date getDataInizioValidita() {
        return dataInizioValidita;
    }

    public void setDataInizioValidita(Date dataInizioValidita) {
        this.dataInizioValidita = dataInizioValidita;
    }

    public Date getDataIscrizione() {
        return dataIscrizione;
    }

    public void setDataIscrizione(Date dataIscrizione) {
        this.dataIscrizione = dataIscrizione;
    }

    public Date getDataIscrizioneRea() {
        return dataIscrizioneRea;
    }

    public void setDataIscrizioneRea(Date dataIscrizioneRea) {
        this.dataIscrizioneRea = dataIscrizioneRea;
    }

    public Date getDataIscrizioneRi() {
        return dataIscrizioneRi;
    }

    public void setDataIscrizioneRi(Date dataIscrizioneRi) {
        this.dataIscrizioneRi = dataIscrizioneRi;
    }

    public Date getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getDenominazione() {
        return denominazione;
    }

    public void setDenominazione(String denominazione) {
        this.denominazione = denominazione;
    }

    public String getDesCittadinanza() {
        return desCittadinanza;
    }

    public void setDesCittadinanza(String desCittadinanza) {
        this.desCittadinanza = desCittadinanza;
    }

    public String getDesNazionalita() {
        return desNazionalita;
    }

    public void setDesNazionalita(String desNazionalita) {
        this.desNazionalita = desNazionalita;
    }

    public Integer getIdNazionalita() {
        return idNazionalita;
    }

    public void setIdNazionalita(Integer idNazionalita) {
        this.idNazionalita = idNazionalita;
    }

    public String getDesFormaGiuridica() {
        return desFormaGiuridica;
    }

    public void setDesFormaGiuridica(String desFormaGiuridica) {
        this.desFormaGiuridica = desFormaGiuridica;
    }

    public String getDesTipoCollegio() {
        return desTipoCollegio;
    }

    public void setDesTipoCollegio(String desTipoCollegio) {
        this.desTipoCollegio = desTipoCollegio;
    }

    public String getDesTipoRuolo() {
        return desTipoRuolo;
    }

    public void setDesTipoRuolo(String desTipoRuolo) {
        this.desTipoRuolo = desTipoRuolo;
    }

    public String getCodTipoRuolo() {
        return codTipoRuolo;
    }

    public void setCodTipoRuolo(String codTipoRuolo) {
        this.codTipoRuolo = codTipoRuolo;
    }

    public String getDescrizioneTitolarita() {
        return descrizioneTitolarita;
    }

    public void setDescrizioneTitolarita(String descrizioneTitolarita) {
        this.descrizioneTitolarita = descrizioneTitolarita;
    }

    public String getFlgAttesaIscrizioneRea() {
        return flgAttesaIscrizioneRea;
    }

    public void setFlgAttesaIscrizioneRea(String flgAttesaIscrizioneRea) {
        this.flgAttesaIscrizioneRea = flgAttesaIscrizioneRea;
    }

    public String getFlgAttesaIscrizioneRi() {
        return flgAttesaIscrizioneRi;
    }

    public void setFlgAttesaIscrizioneRi(String flgAttesaIscrizioneRi) {
        this.flgAttesaIscrizioneRi = flgAttesaIscrizioneRi;
    }

    public String getFlgIndividuale() {
        return flgIndividuale;
    }

    public void setFlgIndividuale(String flgIndividuale) {
        this.flgIndividuale = flgIndividuale;
    }

    public String getFlgObbligoIscrizioneRi() {
        return flgObbligoIscrizioneRi;
    }

    public void setFlgObbligoIscrizioneRi(String flgObbligoIscrizioneRi) {
        this.flgObbligoIscrizioneRi = flgObbligoIscrizioneRi;
    }

    public Integer getIdAnagrafica() {
        return idAnagrafica;
    }

    public void setIdAnagrafica(Integer idAnagrafica) {
        this.idAnagrafica = idAnagrafica;
    }

    public Integer getIdCittadinanza() {
        return idCittadinanza;
    }

    public void setIdCittadinanza(Integer idCittadinanza) {
        this.idCittadinanza = idCittadinanza;
    }

    public Integer getIdFormaGiuridica() {
        return idFormaGiuridica;
    }

    public void setIdFormaGiuridica(Integer idFormaGiuridica) {
        this.idFormaGiuridica = idFormaGiuridica;
    }

    public Integer getIdPratica() {
        return idPratica;
    }

    public void setIdPratica(Integer idPratica) {
        this.idPratica = idPratica;
    }

    public Integer getIdProvinciaIscrizione() {
        return idProvinciaIscrizione;
    }

    public void setIdProvinciaIscrizione(Integer idProvinciaIscrizione) {
        this.idProvinciaIscrizione = idProvinciaIscrizione;
    }

    public Integer getIdTipoCollegio() {
        return idTipoCollegio;
    }

    public void setIdTipoCollegio(Integer idTipoCollegio) {
        this.idTipoCollegio = idTipoCollegio;
    }

    public Integer getIdTipoRuolo() {
        return idTipoRuolo;
    }

    public void setIdTipoRuolo(Integer idTipoRuolo) {
        this.idTipoRuolo = idTipoRuolo;
    }

    public Integer getIdTipoRuoloOriginale() {
        return idTipoRuoloOriginale;
    }

    public void setIdTipoRuoloOriginale(Integer idTipoRuoloOriginale) {
        this.idTipoRuoloOriginale = idTipoRuoloOriginale;
    }

    public String getLocalitaNascita() {
        return localitaNascita;
    }

    public void setLocalitaNascita(String localitaNascita) {
        this.localitaNascita = localitaNascita;
    }

    public String getnIscrizioneRea() {
        return nIscrizioneRea;
    }

    public void setnIscrizioneRea(String nIscrizioneRea) {
        this.nIscrizioneRea = nIscrizioneRea;
    }

    public String getnIscrizioneRi() {
        return nIscrizioneRi;
    }

    public void setnIscrizioneRi(String nIscrizioneRi) {
        this.nIscrizioneRi = nIscrizioneRi;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public RecapitoDTO getNotifica() {
        return notifica;
    }

    public void setNotifica(RecapitoDTO notifica) {
        this.notifica = notifica;
    }

    public String getNumeroIscrizione() {
        return numeroIscrizione;
    }

    public void setNumeroIscrizione(String numeroIscrizione) {
        this.numeroIscrizione = numeroIscrizione;
    }

    public String getPartitaIva() {
        return partitaIva;
    }

    public void setPartitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
    }

    public ProvinciaDTO getProvinciaIscrizione() {
        return provinciaIscrizione;
    }

    public void setProvinciaIscrizione(ProvinciaDTO provinciaIscrizione) {
        this.provinciaIscrizione = provinciaIscrizione;
    }

    public List<RecapitoDTO> getRecapiti() {
        return recapiti;
    }

    public void setRecapiti(List<RecapitoDTO> recapiti) {
        this.recapiti = recapiti;
    }

    public String getSesso() {
        return sesso;
    }

    public void setSesso(String sesso) {
        this.sesso = sesso;
    }

    public ProvinciaDTO getProvinciaCciaa() {
        return provinciaCciaa;
    }

    public void setProvinciaCciaa(ProvinciaDTO ProvinciaCciaa) {
        this.provinciaCciaa = ProvinciaCciaa;
    }

    public String getConfermata() {
        return confermata;
    }

    public void setConfermata(String confermata) {
        this.confermata = confermata;
    }

    public Integer getIdTipoQualifica() {
        return idTipoQualifica;
    }

    public void setIdTipoQualifica(Integer idTipoQualifica) {
        this.idTipoQualifica = idTipoQualifica;
    }

    public String getDesTipoQualifica() {
        return desTipoQualifica;
    }

    public void setDesTipoQualifica(String desTipoQualifica) {
        this.desTipoQualifica = desTipoQualifica;
    }

    public String getVarianteAnagrafica() {
        return varianteAnagrafica;
    }

    /**
     * ^^CS NON MODIFICARE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     *
     * @param varianteAnagrafica
     */
    public void setVarianteAnagrafica(String varianteAnagrafica) {
        if (varianteAnagrafica != null && !"".equals(varianteAnagrafica)) {
            this.varianteAnagrafica = varianteAnagrafica;
        } else {
            this.varianteAnagrafica = null;

        }
    }

    public Boolean equals(AnagraficaDTO anagrafica) {
        Boolean ok = false;
        if (this.tipoAnagrafica.equals(Constants.PERSONA_FISICA)) {
            if (this.getNome() != null && this.getNome().equals(anagrafica.getNome())
                    && this.getCognome() != null && this.getCognome().equals(anagrafica.getCognome())
                    && this.getCodiceFiscale() != null && this.getCodiceFiscale().equals(anagrafica.getCodiceFiscale())
                    && this.getDataNascita() != null && this.getDataNascita().equals(anagrafica.getDataNascita())
                    && this.getDesCittadinanza() != null && this.getDesCittadinanza().equals(anagrafica.getDesCittadinanza())
                    && this.getIdCittadinanza() != null && this.getIdCittadinanza().equals(anagrafica.getIdCittadinanza())
                    && this.getDesNazionalita() != null && this.getDesNazionalita().equals(anagrafica.getDesNazionalita())
                    && this.getIdNazionalita() != null && this.getIdNazionalita().equals(anagrafica.getIdNazionalita())
                    && this.getLocalitaNascita() != null && this.getLocalitaNascita().equals(anagrafica.getLocalitaNascita())
                    && this.getSesso() != null && this.getSesso().equals(anagrafica.getSesso())
                    && this.getComuneNascita() != null && this.getComuneNascita().equals(anagrafica.getComuneNascita())) {

                if (Constants.PERSONA_DITTAINDIVIDUALE.equalsIgnoreCase(this.varianteAnagrafica)) {
                    ok = this.getPartitaIva() != null && this.getPartitaIva().equals(anagrafica.getPartitaIva());
                } else {
                    ok = true;
                }
            } else {
                ok = false;
            }
        }

        if (this.tipoAnagrafica.equals(Constants.PERSONA_GIURIDICA)) {
            ok = this.getPartitaIva() != null && this.getPartitaIva().equals(anagrafica.getPartitaIva())
                    && this.getDenominazione() != null && this.getDenominazione().equals(anagrafica.getDenominazione())
                    //                    && this.getCodTipoCollegio() != null && this.getCodTipoCollegio().equals(anagrafica.getCodTipoCollegio())
                    && this.getDesFormaGiuridica() != null && this.getDesFormaGiuridica().equals(anagrafica.getDesFormaGiuridica())
                    && this.getIdFormaGiuridica() != null && this.getIdFormaGiuridica().equals(anagrafica.getIdFormaGiuridica());
        }
        if (ok) {
            // Boolean trovato = false;
            //recaapiti
            if (this.getRecapiti() != null && anagrafica.getRecapiti() != null && this.getRecapiti().size() == anagrafica.getRecapiti().size()) {
                for (RecapitoDTO recapito : this.getRecapiti()) {
                    ok = false;
                    for (RecapitoDTO recapitoDB : anagrafica.getRecapiti()) {

                        if (recapito.getIdRecapito() == null) {
                            ok = false;
                            break;
                        }
                        /**
                         * ^^CS logica di recapito notifica vuoto se
                         * l'anagrafica non ha recapiti di notifica
                         */
                        if (recapitoDB.getIdRecapito() == null && recapitoDB.getDescTipoIndirizzo().equals(Constants.INDIRIZZO_NOTIFICA)) {
                            continue;
                        }
                        /**
                         * fine
                         */
                        if (recapito.getIdRecapito() == recapitoDB.getIdRecapito()) {
                            ok = Utils.equals(recapito.getIdRecapito(), recapitoDB.getIdRecapito())
                                    && Utils.equals(recapito.getCasellaPostale(), recapitoDB.getCasellaPostale())
                                    && //TODO: ^^CS gestione altre info recaapito
                                    //Utils.equals(recapito.getAltreInfoIndirizzo(),recapitoDB.getAltreInfoIndirizzo()) &&
                                    Utils.equals(recapito.getCellulare(), recapitoDB.getCellulare())
                                    && Utils.equals(recapito.getDescComune(), recapitoDB.getDescComune())
                                    && Utils.equals(recapito.getDescDug(), recapitoDB.getDescDug())
                                    && Utils.equals(recapito.getDescProvincia(), recapitoDB.getDescProvincia())
                                    && Utils.equals(recapito.getDescStato(), recapitoDB.getDescStato())
                                    && Utils.equals(recapito.getDescTipoIndirizzo(), recapitoDB.getDescTipoIndirizzo())
                                    && Utils.equals(recapito.getEmail(), recapitoDB.getEmail())
                                    && Utils.equals(recapito.getFax(), recapitoDB.getFax())
                                    && Utils.equals(recapito.getIdComune(), recapitoDB.getIdComune())
                                    && Utils.equals(recapito.getIdTipoIndirizzo(), recapitoDB.getIdTipoIndirizzo())
                                    && Utils.equals(recapito.getIndirizzo(), recapitoDB.getIndirizzo())
                                    && Utils.equals(recapito.getTelefono(), recapitoDB.getTelefono())
                                    && Utils.equals(recapito.getnCivico(), recapitoDB.getnCivico());
                        }

                    }
                    if (!ok) {
                        break;
                    }

                }
            } else {
                ok = false;
            }
            /*if (!trovato) {
             ok = false;
             }*/
        }
        return ok;
    }
}
