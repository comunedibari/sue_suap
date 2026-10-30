package it.wego.cross.dto.filters;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkClassificazioneProcedimento;
import it.wego.cross.entity.LkStatiScadenze;
import it.wego.cross.entity.LkStatoPratica;
import it.wego.cross.entity.LkTipoInterventoSuap;
import it.wego.cross.entity.LkTipoPratica;
import it.wego.cross.entity.LkTipoProcedimentoSuap;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.Utente;

public class Filter {

	private boolean changed;
	
	private Integer limit;
    private Integer offset;
    private Integer totale;
    private String orderDirection;
    private String orderColumn;
    private Integer page;
    private Utente connectedUser;
    //Solo per ricerca pratica
    private Integer annoRiferimento;
    private String numeroProtocollo;
    private String registerNumberByEvent;
    private String desComune;
    private Integer idComune;
    private Enti enteSelezionato;
    private Date dataInizio;
    private Date dataFine;
    private LkStatoPratica idStatoPratica;
    private String desStatoPratica;
    private List<LkStatoPratica> praticheDaEscludere;
    private Boolean searchByUtenteConnesso = Boolean.FALSE;
    private String foglio;
    private String mappale;
    private String subalterno;
    //Solo per scadenze
    private LkStatiScadenze idStatoScadenza;
    //Solo per utenti
    private String userStatus;
    //Solo per ricerca comune
    private String descrizioneComune;
    private String codiceCatastale;
    //Solo per ricerca pratiche protocollo
    private String numeroFascicolo;
    private String oggetto;
    private String enteRiferimento;
    private String statoPratica;
    private String identificativoPratica;
    private Pratica praticaCrossRiferimento;
    private List<String> tipiDocumentiProtocollo = new ArrayList<String>();
    //Solo per ricerca anagrafiche
    private String nome;
    private String cognome;
    private String denominazione;
    private String partitaIva;
    private String codiceFiscale;
    private String tipoAnagrafica;
    //Solo per ricerca su template
    private Integer idTemplate;
    private String descrizioneTemplate;
    private Integer idEventoTemplate;
    private Integer idEnte;
    private Integer idEvento;
    private String descEvento;
    private Integer idProcedimento;
    //Solo per ricerca anagrafiche
    private String ricercaAnagraficaCF;
    private String ricercaAnagraficaNome;
    private String procedimento;
    private String tipoProcedimento;
    private String processo;
    private String tipoFiltro;
    private String descrizione;
    //Solo per stato procedimenti in gestione enti
    private String statoProcedimento;
    //Solo per gli utenti
    private String username;
    private String superuser;
    private String statoProcedimentoUtente;
    private Integer idUtente;
    //Solo per gli indirizzi
    private String indirizzo;
    private String civico;
    private Integer idTipoUnita;
    private Integer idTipoSistemaCatastale;
    private Integer idTipoParticella;
    private String estensioneParticella;
    private String sezione;
    private String comuneCensuario;
    private List<Integer> listaEnti = new ArrayList<Integer>();
    private Integer idOrganiCollegiali;
    private Integer idSeduta;
    private Integer idSedutaPratica;
    private Integer idPraticaOrganiCollegiali;
    private Integer idPratica;
    private Integer idOperatoreSelezionato;
    private String protocolloSuap;
	private LkTipoInterventoSuap idTipoInterventoSuap;
	private LkTipoProcedimentoSuap idTipoProcedimentoSuap;
	private LkClassificazioneProcedimento idClassificazioneProcedimento;
    private String desProcedimentoSuap;
    private String desInterventoSuap;
    private Date dataInizioProtSuap;
    private Date dataFineProtSuap;
    private String desClassificazioneProcedimento;
    private List<Pratica> listaPraticheIn;
    private List<String> listTree;
    private Identifications identifications;
    private CadastralDataList cadastralDataList;
    private CadastralAddresses cadastralAddresses;
    private LkTipoPratica idTipoPratica;
    private List<LkTipoPratica> idTipoPratiche;
    private Integer annoPratica;
    private Integer numeroPratica;
    private String interventionDescription;
    
    public boolean isChanged() {
		return changed;
	}

	private void setChanged(Object object) {
		this.changed = changed || object != null;
	}
	
    public String getProtocolloSuap() {
		return protocolloSuap;
	}

	public void setProtocolloSuap(String protocolloSuap) {
		setChanged(protocolloSuap);
		this.protocolloSuap = protocolloSuap;
	}

	public Integer getIdOperatoreSelezionato() {
		return idOperatoreSelezionato;
	}

	public void setIdOperatoreSelezionato(Integer idOperatoreSelezionato) {
		setChanged(idOperatoreSelezionato);
		this.idOperatoreSelezionato = idOperatoreSelezionato;
	}

	public String getComuneCensuario() {
        return comuneCensuario;
    }

    public void setComuneCensuario(String comuneCensuario) {
		setChanged(comuneCensuario);
        this.comuneCensuario = comuneCensuario;
    }

    public Integer getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Integer idUtente) {
		setChanged(idUtente);
        this.idUtente = idUtente;
    }

    
    public String getStatoProcedimentoUtente() {
        return statoProcedimentoUtente;
    }

    public void setStatoProcedimentoUtente(String statoProcedimentoUtente) {
		setChanged(statoProcedimentoUtente);
        this.statoProcedimentoUtente = statoProcedimentoUtente;
    }

    public Integer getAnnoRiferimento() {
        return annoRiferimento;
    }

    public void setAnnoRiferimento(Integer annoRiferimento) {
		setChanged(annoRiferimento);
        this.annoRiferimento = annoRiferimento;
    }

    public Integer getIdTipoUnita() {
        return idTipoUnita;
    }

    public void setIdTipoUnita(Integer idTipoUnita) {
		setChanged(idTipoUnita);
        this.idTipoUnita = idTipoUnita;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
		setChanged(indirizzo);
        this.indirizzo = indirizzo;
    }

    public String getCivico() {
        return civico;
    }

    public void setCivico(String civico) {
		setChanged(civico);
        this.civico = civico;
    }
    

    public String getSuperuser() {
        return superuser;
    }

    public void setSuperuser(String superuser) {
		setChanged(superuser);
        this.superuser = superuser;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
		setChanged(username);
        this.username = username;
    }
 
    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getOrderDirection() {
        return orderDirection;
    }

    public void setOrderDirection(String orderDirection) {
        this.orderDirection = orderDirection;
    }

    public String getOrderColumn() {
        return orderColumn;
    }

    public void setOrderColumn(String orderColumn) {
        this.orderColumn = orderColumn;
    }

    public Utente getConnectedUser() {
        return connectedUser;
    }

    public void setConnectedUser(Utente connectedUser) {
        this.connectedUser = connectedUser;
    }

    /**
     * Da utilizzare solo per la ricerca di pratiche
     *
     * @return
     */
    public String getNumeroProtocollo() {
        return numeroProtocollo;
    }

    /**
     * Da utilizzare solo per la ricerca di pratiche
     *
     * @param numeroProtocollo
     */
    public void setNumeroProtocollo(String numeroProtocollo) {
		setChanged(numeroProtocollo);
        this.numeroProtocollo = numeroProtocollo;
    }

    /**
     * Da utilizzare solo per la ricerca di pratiche
     */
//    public List<ProcedimentiEnti> getProcedimentiEnti() {
//        return procedimentiEnti;
//    }
    /**
     * Da utilizzare solo per la ricerca di pratiche
     */
//    public void setProcedimentiEnti(List<ProcedimentiEnti> procedimentiEnti) {
//		  setChanged(procedimentiEnti);
//        this.procedimentiEnti = procedimentiEnti;
//    }
    /**
     * Da utilizzare solo per la ricerca di pratiche
     *
     * @return
     */
    public Enti getEnteSelezionato() {
        return enteSelezionato;
    }

    /**
     * Da utilizzare solo per la ricerca di pratiche
     *
     * @param enteSelezionato
     */
    public void setEnteSelezionato(Enti enteSelezionato) {
		setChanged(enteSelezionato);
        this.enteSelezionato = enteSelezionato;
    }

    /**
     * Da utilizzare solo per la ricerca di pratiche
     *
     * @return
     */
    public Date getDataInizio() {
        return dataInizio;
    }

    /**
     * Da utilizzare solo per la ricerca di pratiche
     *
     * @param dataInizio
     */
    public void setDataInizio(Date dataInizio) {
		setChanged(dataInizio);
        this.dataInizio = dataInizio;
    }

    /**
     * Da utilizzare solo per la ricerca di pratiche
     *
     * @return
     */
    public Date getDataFine() {
        return dataFine;
    }

    /**
     * Da utilizzare solo per la ricerca di pratiche
     *
     * @param dataFine
     */
    public void setDataFine(Date dataFine) {
		setChanged(dataFine);
        this.dataFine = dataFine;
    }

    /**
     * Da utilizzare solo per la ricerca di pratiche
     *
     * @return
     */
    public LkStatoPratica getIdStatoPratica() {
        return idStatoPratica;
    }

    /**
     * Da utilizzare solo per la ricerca di pratiche
     *
     * @param idStatoPratica
     */
    public void setIdStatoPratica(LkStatoPratica idStatoPratica) {
		setChanged(idStatoPratica);
        this.idStatoPratica = idStatoPratica;
    }

    /**
     * Da utilizzare solo per la ricerca di pratiche
     *
     * @return
     */
    public String getDesStatoPratica() {
        return desStatoPratica;
    }

    /**
     * Da utilizzare solo per la ricerca di pratiche
     *
     * @param desStatoPratica
     */
    public void setDesStatoPratica(String desStatoPratica) {
		setChanged("ALL".equals(desStatoPratica)? null: desStatoPratica);
        this.desStatoPratica = desStatoPratica;
    }

    /**
     * Da utilizzare solo per la ricerca di pratiche
     *
     * @return
     */
    public Boolean getSearchByUtenteConnesso() {
        return searchByUtenteConnesso;
    }

    /**
     * Da utilizzare solo per la ricerca di pratiche
     *
     * @param searchByUtenteConnesso
     */
    public void setSearchByUtenteConnesso(Boolean searchByUtenteConnesso) {
        this.searchByUtenteConnesso = searchByUtenteConnesso;
    }

    /**
     * Da utilizzare solo per la ricerca di pratiche
     *
     * @return
     */
    public String getFoglio() {
        return foglio;
    }

    /**
     * Da utilizzare solo per la ricerca di pratiche
     *
     * @param foglio
     */
    public void setFoglio(String foglio) {
		setChanged(foglio);
        this.foglio = foglio;
    }

    /**
     * Da utilizzare solo per la ricerca di pratiche
     *
     * @return
     */
    public Integer getIdComune() {
        return idComune;
    }

    /**
     * Da utilizzare solo per la ricerca di pratiche
     *
     * @param idComune
     */
    public void setIdComune(Integer idComune) {
		setChanged(idComune);
        this.idComune = idComune;
    }

    /**
     * Da utilizzare solo per la ricerca di pratiche
     *
     * @return
     */
    public String getDesComune() {
        return desComune;
    }

    /**
     * Da utilizzare solo per la ricerca di pratiche
     *
     * @param desComune
     */
    public void setDesComune(String desComune) {
		setChanged(desComune);
        this.desComune = desComune;
    }

    /**
     * Da utilizzare solo per la ricerca di pratiche
     *
     * @return
     */
    public String getMappale() {
        return mappale;
    }

    /**
     * Da utilizzare solo per la ricerca di pratiche
     *
     * @param mappale
     */
    public void setMappale(String mappale) {
		setChanged(mappale);
        this.mappale = mappale;
    }

    /**
     * Da utilizzare solo per la ricerca di pratiche
     *
     * @return
     */
    public String getSubalterno() {
        return subalterno;
    }

    /**
     * Da utilizzare solo per la ricerca di pratiche
     *
     * @param subalterno
     */
    public void setSubalterno(String subalterno) {
		setChanged(subalterno);
        this.subalterno = subalterno;
    }

    /**
     * Da utilizzare solo per la ricerca delle scadenze
     *
     * @return
     */
    public LkStatiScadenze getIdStatoScadenza() {
        return idStatoScadenza;
    }

    /**
     * Da utilizzare solo per la ricerca delle scadenze
     *
     * @param idStatoScadenza
     */
    public void setIdStatoScadenza(LkStatiScadenze idStatoScadenza) {
		setChanged(idStatoScadenza);
        this.idStatoScadenza = idStatoScadenza;
    }

    /**
     * Da utilizzare solo per la ricerca degli utenti
     *
     * @return
     */
    public String getUserStatus() {
        return userStatus;
    }

    /**
     * Da utilizzare solo per la ricerca degli utenti
     *
     * @param userStatus
     */
    public void setUserStatus(String userStatus) {
		setChanged(userStatus);
        this.userStatus = userStatus;
    }

    /**
     * Da utilizzare solo per la ricerca dei comuni
     *
     * @return
     */
    public String getDescrizioneComune() {
        return descrizioneComune;
    }

    /**
     * Da utilizzare solo per la ricerca dei comuni
     *
     * @param descrizioneComune
     */
    public void setDescrizioneComune(String descrizioneComune) {
		setChanged(descrizioneComune);
        this.descrizioneComune = descrizioneComune;
    }

    /**
     * Da utilizzare solo per la ricerca dei comuni
     *
     * @return
     */
    public String getCodiceCatastale() {
        return codiceCatastale;
    }

    /**
     * Da utilizzare solo per la ricerca dei comuni
     *
     * @param codiceCatastale
     */
    public void setCodiceCatastale(String codiceCatastale) {
		setChanged(codiceCatastale);
        this.codiceCatastale = codiceCatastale;
    }

    /**
     * Da utilizzare solo per la ricerca delle pratiche
     *
     * @return
     */
    public List<LkStatoPratica> getPraticheDaEscludere() {
        return praticheDaEscludere;
    }

    /**
     * Da utilizzare solo per la ricerca delle pratiche
     *
     * @param praticheDaEscludere
     */
    public void setPraticheDaEscludere(List<LkStatoPratica> praticheDaEscludere) {
		setChanged(praticheDaEscludere);
        this.praticheDaEscludere = praticheDaEscludere;
    }

    /**
     * Da utilizzare solo per la ricerca delle pratiche dal protocollo
     *
     * @return
     */
    public String getNumeroFascicolo() {
        return numeroFascicolo;
    }

    /**
     * Da utilizzare solo per la ricerca delle pratiche dal protocollo
     *
     * @param numeroFascicolo
     */
    public void setNumeroFascicolo(String numeroFascicolo) {
		setChanged(numeroFascicolo);
        this.numeroFascicolo = numeroFascicolo;
    }

    /**
     * Da utilizzare solo per la ricerca delle pratiche dal protocollo
     *
     * @return
     */
    public String getOggetto() {
        return oggetto;
    }

    /**
     * Da utilizzare solo per la ricerca delle pratiche dal protocollo
     *
     * @param oggetto
     */
    public void setOggetto(String oggetto) {
		setChanged(oggetto);
        this.oggetto = oggetto;
    }

    /**
     * Da utilizzare solo per la ricerca delle pratiche dal protocollo
     *
     * @return
     */
    public String getEnteRiferimento() {
        return enteRiferimento;
    }

    /**
     * Da utilizzare solo per la ricerca delle pratiche dal protocollo
     *
     * @param enteRiferimento
     */
    public void setEnteRiferimento(String enteRiferimento) {
		setChanged(enteRiferimento);
        this.enteRiferimento = enteRiferimento;
    }

    /**
     * Da utilizzare solo per la ricerca delle pratiche dal protocollo
     *
     * @return
     */
    public String getStatoPratica() {
        return statoPratica;
    }

    /**
     * Da utilizzare solo per la ricerca delle pratiche dal protocollo
     *
     * @param statoPratica
     */
    public void setStatoPratica(String statoPratica) {
		setChanged(statoPratica);
        this.statoPratica = statoPratica;
    }

    /**
     * Da utilizzare solo per la ricerca delle pratiche dal protocollo
     *
     * @return
     */
    public Pratica getPraticaCrossRiferimento() {
        return praticaCrossRiferimento;
    }

    /**
     * Da utilizzare solo per la ricerca delle pratiche dal protocollo
     *
     * @param praticaCrossRiferimento
     */
    public void setPraticaCrossRiferimento(Pratica praticaCrossRiferimento) {
		setChanged(praticaCrossRiferimento);
        this.praticaCrossRiferimento = praticaCrossRiferimento;
    }

    /**
     * Da utilizzare solo per la ricerca delle pratiche dal protocollo
     *
     * @return
     */
    public String getIdentificativoPratica() {
        return identificativoPratica;
    }

    /**
     * Da utilizzare solo per la ricerca delle pratiche dal protocollo
     *
     * @param identificativoPratica
     */
    public void setIdentificativoPratica(String identificativoPratica) {
		setChanged(identificativoPratica);
        this.identificativoPratica = identificativoPratica;
    }

    public List<String> getTipiDocumentiProtocollo() {
        return tipiDocumentiProtocollo;
    }

    public void setTipiDocumentiProtocollo(List<String> tipiDocumentiProtocollo) {
		setChanged(tipiDocumentiProtocollo);
        this.tipiDocumentiProtocollo = tipiDocumentiProtocollo;
    }

    /**
     * Da utilizzare solo per la ricerca delle anagrafiche
     *
     * @return
     */
    public String getNome() {
        return nome;
    }

    /**
     * Da utilizzare solo per la ricerca delle anagrafiche
     *
     * @param nome
     */
    public void setNome(String nome) {
		setChanged(nome);
        this.nome = nome;
    }

    /**
     * Da utilizzare solo per la ricerca delle anagrafiche
     *
     * @return
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Da utilizzare solo per la ricerca delle anagrafiche
     *
     * @param cognome
     */
    public void setCognome(String cognome) {
		setChanged(cognome);
        this.cognome = cognome;
    }

    /**
     * Da utilizzare solo per la ricerca delle anagrafiche
     *
     * @return
     */
    public String getDenominazione() {
        return denominazione;
    }

    /**
     * Da utilizzare solo per la ricerca delle anagrafiche
     *
     * @param denominazione
     */
    public void setDenominazione(String denominazione) {
		setChanged(denominazione);
        this.denominazione = denominazione;
    }

    /**
     * Da utilizzare solo per la ricerca delle anagrafiche
     *
     * @return
     */
    public String getPartitaIva() {
        return partitaIva;
    }

    /**
     * Da utilizzare solo per la ricerca delle anagrafiche
     *
     * @param partitaIva
     */
    public void setPartitaIva(String partitaIva) {
		setChanged(partitaIva);
        this.partitaIva = partitaIva;
    }

    /**
     * Da utilizzare solo per la ricerca delle anagrafiche
     *
     * @return
     */
    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    /**
     * Da utilizzare solo per la ricerca delle anagrafiche
     *
     * @param codiceFiscale
     */
    public void setCodiceFiscale(String codiceFiscale) {
		setChanged(codiceFiscale);
        this.codiceFiscale = codiceFiscale;
    }

    /**
     * Da utilizzare solo per la ricerca delle anagrafiche
     *
     * @return
     */
    public String getTipoAnagrafica() {
        return tipoAnagrafica;
    }

    /**
     * Da utilizzare solo per la ricerca delle anagrafiche
     *
     * @param tipoAnagrafica
     */
    public void setTipoAnagrafica(String tipoAnagrafica) {
		setChanged(tipoAnagrafica);
        this.tipoAnagrafica = tipoAnagrafica;
    }

    /**
     * ^^CS da utilizzare su lista template
     *
     * @return
     */
    public Integer getIdTemplate() {
        return idTemplate;
    }

    /**
     * ^^CS da utilizzare su lista template
     *
     * @param idTemplate
     */
    public void setIdTemplate(Integer idTemplate) {
		setChanged(idTemplate);
        this.idTemplate = idTemplate;
    }

    /**
     * ^^CS da utilizzare su lista template
     *
     * @return
     */
    public Integer getIdEventoTemplate() {
        return idEventoTemplate;
    }

    /**
     * ^^CS da utilizzare su lista template
     *
     * @param idEventoTemplate
     */
    public void setIdEventoTemplate(Integer idEventoTemplate) {
		setChanged(idEventoTemplate);
        this.idEventoTemplate = idEventoTemplate;
    }

    public Integer getIdEnte() {
        return idEnte;
    }

    public void setIdEnte(Integer idEnte) {
		setChanged(idEnte);
        this.idEnte = idEnte;
    }

    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
		setChanged(idEvento);
        this.idEvento = idEvento;
    }

    public Integer getIdProcedimento() {
        return idProcedimento;
    }

    public void setIdProcedimento(Integer idProcedimento) {
		setChanged(idProcedimento);
        this.idProcedimento = idProcedimento;
    }

    public String getDescrizioneTemplate() {
        return descrizioneTemplate;
    }

    public void setDescrizioneTemplate(String descrizioneTemplate) {
		setChanged(descrizioneTemplate);
        this.descrizioneTemplate = descrizioneTemplate;
    }

    public Integer getTotale() {
        return totale;
    }

    public void setTotale(Integer totale) {
		setChanged(totale);
        this.totale = totale;
    }

    public String getDescEvento() {
        return descEvento;
    }

    public void setDescEvento(String descEvento) {
		setChanged(descEvento);
        this.descEvento = descEvento;
    }

    public String getRicercaAnagraficaCF() {
        return ricercaAnagraficaCF;
    }

    public void setRicercaAnagraficaCF(String ricercaAnagraficaCF) {
		setChanged(ricercaAnagraficaCF);
        this.ricercaAnagraficaCF = ricercaAnagraficaCF;
    }

    public String getRicercaAnagraficaNome() {
        return ricercaAnagraficaNome;
    }

    public void setRicercaAnagraficaNome(String ricercaAnagraficaNome) {
		setChanged(ricercaAnagraficaNome);
        this.ricercaAnagraficaNome = ricercaAnagraficaNome;
    }

    public String getProcedimento() {
        return procedimento;
    }

    public void setProcedimento(String procedimento) {
		setChanged(procedimento);
        this.procedimento = procedimento;
    }

    public String getProcesso() {
        return processo;
    }

    /**
     * Ricerca per tipologia di procedimento
     *
     * @return
     */
    public String getTipoProcedimento() {
        return tipoProcedimento;
    }

    /**
     * Ricerca per tipologia di procedimento
     *
     * @param tipoProcedimento
     */
    public void setTipoProcedimento(String tipoProcedimento) {
		setChanged(tipoProcedimento);
        this.tipoProcedimento = tipoProcedimento;
    }

    public void setProcesso(String processo) {
		setChanged(processo);
        this.processo = processo;
    }

    public String getTipoFiltro() {
        return tipoFiltro;
    }

    public void setTipoFiltro(String tipoFiltro) {
		setChanged(tipoFiltro);
        this.tipoFiltro = tipoFiltro;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
		this.page = page;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
		setChanged(descrizione);
        this.descrizione = descrizione;
    }

    /**
     * Da utilizzare solo per stato procedimenti in gestione enti
     *
     * @return
     */
    public String getStatoProcedimento() {
        return statoProcedimento;
    }

    /**
     * Da utilizzare solo per stato procedimenti in gestione enti
     *
     * @param statoProcedimento
     */
    public void setStatoProcedimento(String statoProcedimento) {
		setChanged(statoProcedimento);
        this.statoProcedimento = statoProcedimento;
    }

    public Integer getIdTipoSistemaCatastale() {
        return idTipoSistemaCatastale;
    }

    public void setIdTipoSistemaCatastale(Integer idTipoSistemaCatastale) {
		setChanged(idTipoSistemaCatastale);
        this.idTipoSistemaCatastale = idTipoSistemaCatastale;
    }

    public Integer getIdTipoParticella() {
        return idTipoParticella;
    }

    public void setIdTipoParticella(Integer idTipoParticella) {
		setChanged(idTipoParticella);
        this.idTipoParticella = idTipoParticella;
    }

    public String getEstensioneParticella() {
        return estensioneParticella;
    }

    public void setEstensioneParticella(String estensioneParticella) {
		setChanged(estensioneParticella);
        this.estensioneParticella = estensioneParticella;
    }

    public String getSezione() {
        return sezione;
    }

    public void setSezione(String sezione) {
		setChanged(sezione);
        this.sezione = sezione;
    }

    public List<Integer> getListaEnti() {
        return listaEnti;
    }

    public void setListaEnti(List<Integer> listaEnti) {
		setChanged(listaEnti);
        this.listaEnti = listaEnti;
    }

    public Integer getIdOrganiCollegiali() {
        return idOrganiCollegiali;
    }

    public void setIdOrganiCollegiali(Integer idOrganiCollegiali) {
		setChanged(idOrganiCollegiali);
        this.idOrganiCollegiali = idOrganiCollegiali;
    }

    public Integer getIdSeduta() {
        return idSeduta;
    }

    public void setIdSeduta(Integer idSeduta) {
		setChanged(idSeduta);
        this.idSeduta = idSeduta;
    }

    public Integer getIdSedutaPratica() {
        return idSedutaPratica;
    }

    public void setIdSedutaPratica(Integer idSedutaPratica) {
		setChanged(idSedutaPratica);
        this.idSedutaPratica = idSedutaPratica;
    }

    public Integer getIdPraticaOrganiCollegiali() {
        return idPraticaOrganiCollegiali;
    }

    public void setIdPraticaOrganiCollegiali(Integer idPraticaOrganiCollegiali) {
		setChanged(idPraticaOrganiCollegiali);
        this.idPraticaOrganiCollegiali = idPraticaOrganiCollegiali;
    }

	public Integer getIdPratica() {
		return idPratica;
	}

	public void setIdPratica(Integer idPratica) {
		setChanged(idPratica);
		this.idPratica = idPratica;
	}

	public String getDesProcedimentoSuap() {
		return desProcedimentoSuap;
	}

	public void setDesProcedimentoSuap(String desProcedimentoSuap) {
		setChanged(desProcedimentoSuap);
		this.desProcedimentoSuap = desProcedimentoSuap;
	}

	public String getDesInterventoSuap() {
		return desInterventoSuap;
	}

	public void setDesInterventoSuap(String desInterventoSuap) {
		setChanged(desInterventoSuap);
		this.desInterventoSuap = desInterventoSuap;
	}
	public LkTipoInterventoSuap getIdTipoInterventoSuap() {
		return idTipoInterventoSuap;
	}

	public void setIdTipoInterventoSuap(LkTipoInterventoSuap idTipoInterventoSuap) {
		setChanged(idTipoInterventoSuap);
		this.idTipoInterventoSuap = idTipoInterventoSuap;
	}

	public LkTipoProcedimentoSuap getIdTipoProcedimentoSuap() {
		return idTipoProcedimentoSuap;
	}

	public void setIdTipoProcedimentoSuap(LkTipoProcedimentoSuap idTipoProcedimentoSuap) {
		setChanged(idTipoProcedimentoSuap);
		this.idTipoProcedimentoSuap = idTipoProcedimentoSuap;
	}


	public Date getDataInizioProtSuap() {
		return dataInizioProtSuap;
	}

	public void setDataInizioProtSuap(Date dataInizioProtSuap) {
		setChanged(dataInizioProtSuap);
		this.dataInizioProtSuap = dataInizioProtSuap;
	}

	public Date getDataFineProtSuap() {
		return dataFineProtSuap;
	}

	public void setDataFineProtSuap(Date dataFineProtSuap) {
		setChanged(dataFineProtSuap);
		this.dataFineProtSuap = dataFineProtSuap;
	}

	public LkClassificazioneProcedimento getIdClassificazioneProcedimento() {
		return idClassificazioneProcedimento;
	}

	public void setIdClassificazioneProcedimento(LkClassificazioneProcedimento idClassificazioneProcedimento) {
		setChanged(idClassificazioneProcedimento);
		this.idClassificazioneProcedimento = idClassificazioneProcedimento;
	}

	public String getDesClassificazioneProcedimento() {
		return desClassificazioneProcedimento;
	}

	public void setDesClassificazioneProcedimento(String desClassificazioneProcedimento) {
		setChanged(desClassificazioneProcedimento);
		this.desClassificazioneProcedimento = desClassificazioneProcedimento;
	}

	public List<Pratica> getListaPraticheIn() {
		return listaPraticheIn;
	}

	public void setListaPraticheIn(List<Pratica> listaPraticheIn) {
		setChanged(listaPraticheIn);
		this.listaPraticheIn = listaPraticheIn;
	}

	public List<String> getListTree() {
		return listTree;
	}

	public void setListTree(List<String> listTree) {
		setChanged(listTree);
		this.listTree = listTree;
	}

	public Identifications getIdentifications() {
		return identifications;
	}

	public void setIdentifications(Identifications identifications) {
		setChanged(identifications);
		this.identifications = identifications;
	}

	public CadastralDataList getCadastralDataList() {
		return cadastralDataList;
	}

	public void setCadastralDataList(CadastralDataList cadastralDataList) {
		setChanged(cadastralDataList);
		this.cadastralDataList = cadastralDataList;
	}

	public CadastralAddresses getCadastralAddresses() {
		return cadastralAddresses;
	}

	public void setCadastralAddresses(CadastralAddresses cadastralAddresses) {
		setChanged(cadastralAddresses);
		this.cadastralAddresses = cadastralAddresses;
	}

	public LkTipoPratica getIdTipoPratica() {
		return idTipoPratica;
	}

	public void setIdTipoPratica(LkTipoPratica idTipoPratica) {
		setChanged(idTipoPratica);
		this.idTipoPratica = idTipoPratica;
	}

	public Integer getAnnoPratica() {
		return annoPratica;
	}

	public void setAnnoPratica(Integer annoPratica) {
		setChanged(annoPratica);
		this.annoPratica = annoPratica;
	}

	public Integer getNumeroPratica() {
		return numeroPratica;
	}

	public void setNumeroPratica(Integer numeroPratica) {
		setChanged(numeroPratica);
		this.numeroPratica = numeroPratica;
	}

	public String getRegisterNumberByEvent() {
		return registerNumberByEvent;
	}

	public void setRegisterNumberByEvent(String registerNumberByEvent) {
		setChanged(registerNumberByEvent);
		this.registerNumberByEvent = registerNumberByEvent;
	}

	public String getInterventionDescription() {
		return interventionDescription;
	}

	public void setInterventionDescription(String interventionDescription) {
		setChanged(interventionDescription);
		this.interventionDescription = interventionDescription;
	}

	public List<LkTipoPratica> getIdTipoPratiche() {
		return idTipoPratiche;
	}

	public void setIdTipoPratiche(List<LkTipoPratica> idTipoPratiche) {
		this.idTipoPratiche = idTipoPratiche;
	}
	
	
}
