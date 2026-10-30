/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

import CiviliaDao.Costants;

public class Anagrafica implements Serializable {
    private List<OcPraticaCommissione> ocPraticaCommissioneList;
    private List<OrganiCollegialiCommissione> organiCollegialiCommissioneList;
    private static final long serialVersionUID = 1L;
    private Integer idAnagrafica;
    private Character tipoAnagrafica; 
    private Character flgIndividuale;    
    private String cognome;
    private String nome;
    private String codiceFiscale;
    private String denominazione;
    private String partitaIva;
    private Date dataNascita;
    private String localitaNascita;
    private Character sesso;
    private String numeroIscrizione;
    private Date dataIscrizione;
    private Character flgAttesaIscrizioneRi;
    private Character flgObbligoIscrizioneRi;
    private Date dataIscrizioneRi;
    private String nIscrizioneRi;
    private Character flgAttesaIscrizioneRea;
    private Date dataIscrizioneRea;
    private String nIscrizioneRea;
    private String varianteAnagrafica;
    private List<RelazioniAnagrafiche> relazioniAnagraficheList;
    private List<RelazioniAnagrafiche> relazioniAnagraficheList1;
    private LkFormeGiuridiche idFormaGiuridica;
    private LkNazionalita idNazionalita;
    private LkProvincie idProvinciaIscrizione;
    private LkProvincie idProvinciaCciaa;
    private LkTipoCollegio idTipoCollegio;
    private LkComuni idComuneNascita;
    private LkCittadinanza idCittadinanza;
    private List<PraticheProtocollo> praticheProtocolloList;
    private List<PraticaAnagrafica> praticaAnagraficaList;
    private List<AnagraficaRecapiti> anagraficaRecapitiList;
    private List<PraticheEventiAnagrafiche> praticheEventiAnagraficheList;
    private List<ProcessiEventiAnagrafica> processiEventiAnagraficaList;    

    private boolean anagraficaDaEnte = false;
    
    public Anagrafica() {
    }

    public Anagrafica(Integer idAnagrafica) {
        this.idAnagrafica = idAnagrafica;
    }

    public Anagrafica(Integer idAnagrafica, char tipoAnagrafica, char flgIndividuale) {
        this.idAnagrafica = idAnagrafica;
        this.tipoAnagrafica = tipoAnagrafica;
        this.flgIndividuale = flgIndividuale;
    }

    public Integer getIdAnagrafica() {
        return idAnagrafica;
    }

    public void setIdAnagrafica(Integer idAnagrafica) {
        this.idAnagrafica = idAnagrafica;
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

    public Character getSesso() {
        return sesso;
    }

    public void setSesso(Character sesso) {
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

    public Character getFlgAttesaIscrizioneRi() {
        return flgAttesaIscrizioneRi;
    }

    public void setFlgAttesaIscrizioneRi(Character flgAttesaIscrizioneRi) {
        this.flgAttesaIscrizioneRi = flgAttesaIscrizioneRi;
    }

    public Character getFlgObbligoIscrizioneRi() {
        return flgObbligoIscrizioneRi;
    }

    public void setFlgObbligoIscrizioneRi(Character flgObbligoIscrizioneRi) {
        this.flgObbligoIscrizioneRi = flgObbligoIscrizioneRi;
    }

    public Date getDataIscrizioneRi() {
        return dataIscrizioneRi;
    }

    public void setDataIscrizioneRi(Date dataIscrizioneRi) {
        this.dataIscrizioneRi = dataIscrizioneRi;
    }

    public String getNIscrizioneRi() {
        return nIscrizioneRi;
    }

    public void setNIscrizioneRi(String nIscrizioneRi) {
        this.nIscrizioneRi = nIscrizioneRi;
    }

    public Character getFlgAttesaIscrizioneRea() {
        return flgAttesaIscrizioneRea;
    }

    public void setFlgAttesaIscrizioneRea(Character flgAttesaIscrizioneRea) {
        this.flgAttesaIscrizioneRea = flgAttesaIscrizioneRea;
    }

    public Date getDataIscrizioneRea() {
        return dataIscrizioneRea;
    }

    public void setDataIscrizioneRea(Date dataIscrizioneRea) {
        this.dataIscrizioneRea = dataIscrizioneRea;
    }

    public String getNIscrizioneRea() {
        return nIscrizioneRea;
    }

    public void setNIscrizioneRea(String nIscrizioneRea) {
        this.nIscrizioneRea = nIscrizioneRea;
    }

    public String getVarianteAnagrafica() {
        return varianteAnagrafica;
    }

    public void setVarianteAnagrafica(String varianteAnagrafica) {
        this.varianteAnagrafica = varianteAnagrafica;
    }


    public List<RelazioniAnagrafiche> getRelazioniAnagraficheList() {
        return relazioniAnagraficheList;
    }

    public void setRelazioniAnagraficheList(List<RelazioniAnagrafiche> relazioniAnagraficheList) {
        this.relazioniAnagraficheList = relazioniAnagraficheList;
    }


    public List<RelazioniAnagrafiche> getRelazioniAnagraficheList1() {
        return relazioniAnagraficheList1;
    }

    public void setRelazioniAnagraficheList1(List<RelazioniAnagrafiche> relazioniAnagraficheList1) {
        this.relazioniAnagraficheList1 = relazioniAnagraficheList1;
    }

    public LkFormeGiuridiche getIdFormaGiuridica() {
        return idFormaGiuridica;
    }

    public void setIdFormaGiuridica(LkFormeGiuridiche idFormaGiuridica) {
        this.idFormaGiuridica = idFormaGiuridica;
    }

    public LkNazionalita getIdNazionalita() {
        return idNazionalita;
    }

    public void setIdNazionalita(LkNazionalita idNazionalita) {
        this.idNazionalita = idNazionalita;
    }

    public LkProvincie getIdProvinciaIscrizione() {
        return idProvinciaIscrizione;
    }

    public void setIdProvinciaIscrizione(LkProvincie idProvinciaIscrizione) {
        this.idProvinciaIscrizione = idProvinciaIscrizione;
    }

    public LkProvincie getIdProvinciaCciaa() {
        return idProvinciaCciaa;
    }

    public void setIdProvinciaCciaa(LkProvincie idProvinciaCciaa) {
        this.idProvinciaCciaa = idProvinciaCciaa;
    }

    public LkTipoCollegio getIdTipoCollegio() {
        return idTipoCollegio;
    }

    public void setIdTipoCollegio(LkTipoCollegio idTipoCollegio) {
        this.idTipoCollegio = idTipoCollegio;
    }

    public LkComuni getIdComuneNascita() {
        return idComuneNascita;
    }

    public void setIdComuneNascita(LkComuni idComuneNascita) {
        this.idComuneNascita = idComuneNascita;
    }

    public LkCittadinanza getIdCittadinanza() {
        return idCittadinanza;
    }

    public void setIdCittadinanza(LkCittadinanza idCittadinanza) {
        this.idCittadinanza = idCittadinanza;
    }

    public List<PraticheProtocollo> getPraticheProtocolloList() {
        return praticheProtocolloList;
    }

    public void setPraticheProtocolloList(List<PraticheProtocollo> praticheProtocolloList) {
        this.praticheProtocolloList = praticheProtocolloList;
    }

    public List<PraticaAnagrafica> getPraticaAnagraficaList() {
        return praticaAnagraficaList;
    }

    public void setPraticaAnagraficaList(List<PraticaAnagrafica> praticaAnagraficaList) {
        this.praticaAnagraficaList = praticaAnagraficaList;
    }

    public List<AnagraficaRecapiti> getAnagraficaRecapitiList() {
        return anagraficaRecapitiList;
    }

    public void setAnagraficaRecapitiList(List<AnagraficaRecapiti> anagraficaRecapitiList) {
        this.anagraficaRecapitiList = anagraficaRecapitiList;
    }


    public List<PraticheEventiAnagrafiche> getPraticheEventiAnagraficheList() {
        return praticheEventiAnagraficheList;
    }

    public void setPraticheEventiAnagraficheList(List<PraticheEventiAnagrafiche> praticheEventiAnagraficheList) {
        this.praticheEventiAnagraficheList = praticheEventiAnagraficheList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAnagrafica != null ? idAnagrafica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Anagrafica)) {
            return false;
        }
        Anagrafica other = (Anagrafica) object;
        if ((this.idAnagrafica == null && other.idAnagrafica != null) || (this.idAnagrafica != null && !this.idAnagrafica.equals(other.idAnagrafica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.Anagrafica[ idAnagrafica=" + idAnagrafica + " ]";
    }

    public Character getTipoAnagrafica() {
        return tipoAnagrafica;
    }

    public void setTipoAnagrafica(Character tipoAnagrafica) {
        this.tipoAnagrafica = tipoAnagrafica;
    }

    public Character getFlgIndividuale() {
        return flgIndividuale;
    }

    public void setFlgIndividuale(Character flgIndividuale) {
        this.flgIndividuale = flgIndividuale;
    }

    public List<ProcessiEventiAnagrafica> getProcessiEventiAnagraficaList() {
        return processiEventiAnagraficaList;
    }

    public void setProcessiEventiAnagraficaList(List<ProcessiEventiAnagrafica> processiEventiAnagraficaList) {
        this.processiEventiAnagraficaList = processiEventiAnagraficaList;
    }

    public String getnIscrizioneRi() {
        return nIscrizioneRi;
    }

    public void setnIscrizioneRi(String nIscrizioneRi) {
        this.nIscrizioneRi = nIscrizioneRi;
    }

    public String getnIscrizioneRea() {
        return nIscrizioneRea;
    }

    public void setnIscrizioneRea(String nIscrizioneRea) {
        this.nIscrizioneRea = nIscrizioneRea;
    }

    public boolean isAnagraficaDaEnte() {
        return anagraficaDaEnte;
    }

    public void setAnagraficaDaEnte(boolean anagraficaDaEnte) {
        this.anagraficaDaEnte = anagraficaDaEnte;
    }

    public List<OrganiCollegialiCommissione> getOrganiCollegialiCommissioneList() {
        return organiCollegialiCommissioneList;
    }

    public void setOrganiCollegialiCommissioneList(List<OrganiCollegialiCommissione> organiCollegialiCommissioneList) {
        this.organiCollegialiCommissioneList = organiCollegialiCommissioneList;
    }

    public List<OcPraticaCommissione> getOcPraticaCommissioneList() {
        return ocPraticaCommissioneList;
    }

    public void setOcPraticaCommissioneList(List<OcPraticaCommissione> ocPraticaCommissioneList) {
        this.ocPraticaCommissioneList = ocPraticaCommissioneList;
    }
    public String getProvenienza() {
		return Costants.CIVILIA;
	}
    
}
