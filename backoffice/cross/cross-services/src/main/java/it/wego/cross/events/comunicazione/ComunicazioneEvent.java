/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.events.comunicazione;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.gson.Gson;
import it.wego.cross.actions.ComunicazioneAction;
import it.wego.cross.actions.ErroriAction;
import it.wego.cross.actions.PraticheAction;
import it.wego.cross.beans.AttoriComunicazione;
import it.wego.cross.beans.EventoBean;
import it.wego.cross.beans.MailContentBean;
import it.wego.cross.constants.AnaTipiEvento;
import it.wego.cross.constants.Constants;
import it.wego.cross.dao.StagingDao;
import it.wego.cross.dto.ErroreDTO;
import it.wego.cross.dto.dozer.PraticaProcedimentiPKDTO;
import it.wego.cross.dto.dozer.converters.IdentificatoreProtocolloIstanzaConverter;
import it.wego.cross.entity.Allegati;
import it.wego.cross.entity.Anagrafica;
import it.wego.cross.entity.AnagraficaRecapiti;
import it.wego.cross.entity.DatiCatastali;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.IndirizziIntervento;
import it.wego.cross.entity.LkStatoPratica;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticaAnagrafica;
import it.wego.cross.entity.PraticaAnagraficaPK;
import it.wego.cross.entity.PraticaProcedimenti;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.PraticheEventiAllegati;
import it.wego.cross.entity.PraticheEventiAnagrafiche;
import it.wego.cross.entity.PraticheEventiAnagrafichePK;
import it.wego.cross.entity.PraticheProtocollo;
import it.wego.cross.entity.Procedimenti;
import it.wego.cross.entity.ProcedimentiEnti;
import it.wego.cross.entity.Processi;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.entity.ProcessiEventiAnagrafica;
import it.wego.cross.entity.ProcessiEventiEnti;
import it.wego.cross.entity.Recapiti;
import it.wego.cross.entity.Staging;
import it.wego.cross.entity.Utente;
import it.wego.cross.entity.view.ProcedimentiLocalizzatiView;
import it.wego.cross.events.AbstractEvent;
import it.wego.cross.events.comunicazione.bean.ComunicazioneBean;
import it.wego.cross.plugins.commons.beans.Allegato;
import it.wego.cross.plugins.documenti.GestioneAllegati;
import it.wego.cross.serializer.AllegatiSerializer;
import it.wego.cross.serializer.DatiCatastaliSerializer;
import it.wego.cross.serializer.PraticheSerializer;
import it.wego.cross.service.AllegatiService;
import it.wego.cross.service.AnagraficheService;
import it.wego.cross.service.EntiService;
import it.wego.cross.service.IndirizziInterventoService;
import it.wego.cross.service.ProcedimentiService;
import it.wego.cross.service.UtentiService;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

/**
 *
 * @author Gabriele
 */
@Repository("comunicazione")
public class ComunicazioneEvent extends AbstractEvent {

    @Autowired
    protected PraticheAction praticheAction;
    @Autowired
    private ProcedimentiService procedimentiService;
    @Autowired
    private StagingDao stagingDao;
    @Autowired
    private ErroriAction erroriAction;
    @Autowired
    private EntiService entiService;
    @Autowired
    private ComunicazioneAction comunicazioneAction;
    @Autowired
    private DatiCatastaliSerializer datiCatastaliSerializer;
    @Autowired
    private PraticheSerializer praticheSerializer;
    @Autowired
    private IndirizziInterventoService indirizziInterventoService;
    @Autowired
    private AnagraficheService anagraficheService;
    @Autowired
    private UtentiService utentiService;
    @Autowired
    private AllegatiService allegatiService;

    @Override
    public void execute(EventoBean eb) throws Exception {
        Pratica pratica = praticheService.getPratica(eb.getIdPratica());
        ComunicazioneBean cb = (ComunicazioneBean) eb;
        ProcessiEventi processoEvento = praticheService.findProcessiEventi(eb.getIdEventoProcesso());
        PraticheEventi praticaEvento = praticheService.getPraticaEvento(cb.getIdEventoPratica());
        if (cb.getEventoAutomatico()) {
            AttoriComunicazione attori = new AttoriComunicazione();
            // gestione eventuale inoltro inizio
            if ((processoEvento.getProcessiEventiAnagraficaList() != null && !processoEvento.getProcessiEventiAnagraficaList().isEmpty()) || (processoEvento.getProcessiEventiEntiList() != null && !processoEvento.getProcessiEventiEntiList().isEmpty())) {
                AnagraficaRecapiti anagraficaRecapitoRiferimentoAnagrafica;
                for (ProcessiEventiAnagrafica pea : processoEvento.getProcessiEventiAnagraficaList()) {
                    Recapiti recapito = anagraficheService.findRecapitoById(pea.getAnagrafica().getIdAnagrafica());
                    if (recapito.getAnagraficaRecapitiList() != null && !recapito.getAnagraficaRecapitiList().isEmpty()) {
                        if (recapito.getAnagraficaRecapitiList().size() > 1) {
                            Log.APP.error("ATTENZIONE! Un recapito è usato da più anagrafiche! NON DOVREBBE SUCCEDERE!");
                        }
                        anagraficaRecapitoRiferimentoAnagrafica = recapito.getAnagraficaRecapitiList().get(0);
                        attori.addAnagrafica(anagraficaRecapitoRiferimentoAnagrafica);
                    }
                }
                for (ProcessiEventiEnti pee : processoEvento.getProcessiEventiEntiList()) {
                    attori.addEnte(pee.getEnti().getIdEnte());
                }
                // gestione eventuale inoltro fine                
            } else {
                attori = praticheAction.getAttoriComunicazione(pratica.getPraticaAnagraficaList());
            }
            if (processoEvento.getVerso() == 'I') {
                cb.setMittenti(attori);
            } else {
                cb.setDestinatari(attori);

            }

            cb.setInviaMail("S".equalsIgnoreCase(processoEvento.getFlgMail()));
            cb.setVisibilitaCross(Boolean.TRUE);
            cb.setVisibilitaUtente("S".equalsIgnoreCase(processoEvento.getFlgPortale()));
            cb.setOggettoProtocollo(pratica.getOggettoPratica());
        }

        Utente user = utentiService.findUtenteByIdUtente(cb.getIdUtente());

        if (Utils.e(cb.getOggettoEmail()) && Utils.e(cb.getCorpoEmail())) {
            MailContentBean mailBean = workFlowService.getMailContent(pratica, processoEvento);
            cb.setOggettoEmail(mailBean.getOggetto());
            cb.setCorpoEmail(mailBean.getContenuto());
        }

        if (cb.getDestinatari() != null) {
            List<Enti> enti = getEntiCoinvolti(cb.getDestinatari().getIdEnti());
            praticaEvento.setEntiList(enti);
            if (cb.getDestinatari().getIdRecapitoNotifica() != null) {
                Recapiti recapito = anagraficheService.findRecapitoById(cb.getDestinatari().getIdRecapitoNotifica());
                praticaEvento.setIdRecapitoNotifica(recapito);
            }
        } else if (cb.getMittenti() != null) {
            List<Enti> enti = getEntiCoinvolti(cb.getMittenti().getIdEnti());
            praticaEvento.setEntiList(enti);
        }
        
        if(!praticaEvento.getDescrizioneEvento().contains("(proc)")) {
	        praticheAction.updateScadenzePratica(pratica, cb);
	        praticheAction.updateStatoPratica(pratica, cb);
        }

        if (processoEvento.getVerso() == 'I') {
            inserisciPraticaEventoAnagrafiche(praticaEvento, cb.getMittenti());
        } else {
            inserisciPraticaEventoAnagrafiche(praticaEvento, cb.getDestinatari());
        }

        praticaEvento.setOggetto(cb.getOggettoProtocollo());

        Integer idComune = null;
        if (pratica.getIdComune() != null) {
            idComune = pratica.getIdComune().getIdComune();
        }
        if (cb.getVisibilitaUtente()) {
            try {
                GestioneAllegati ga = pluginService.getGestioneAllegati(pratica.getIdProcEnte().getIdEnte().getIdEnte(), idComune);
                List<Allegati> allegati = getAllegati(cb.getIdAllegati());
                final List<Allegato> allegatiComunicazione = eb.getAllegati();
                Iterables.removeIf(allegati, new Predicate<Allegati>() {

                    @Override
                    public boolean apply(Allegati input) {
                        for (Allegato a : allegatiComunicazione) {
                            if (a.getNomeFile().equals(input.getNomeFile())) {
                                return !a.getSpedisci();
                            }
                        }
                        return true;
                    }
                });

                ga.uploadAttachmentsOnMyPage(pratica, allegati);
            } catch (Exception ex) {
                //Errore non bloccante
                ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_UPLOAD_MYPAGE, ex.getMessage(), ex, pratica, user);
                erroriAction.saveError(errore);
                Log.APP.error("Non è stato possibile caricare l'allegato su MyPage", ex);
            }
        }

        //Pratiche figlie: le vado a creare dopo che ho protocollato. 
        //TODO: come mi comporto per la protocollazione in ingresso sugli enti terzi?
        //Possibile soluzione: prevedere, nel metodo createPraticaFiglia, una chiamata ad un flusso per gli enti terzi (potrebbe non inviare comunicazioni in ricezione)
        //
        //Portato update pratiche figlie sotto alla protocollazione della comunicazione per risolvere il problema del protocollo in input
        //antecedente a quello di output
        updatePraticheFiglie(pratica, cb);

        //Tutto il discorso della pratica protocollo collegata e dell'evento sorgente ha senso solo per le comunicazioni in ingresso
        if (processoEvento.getVerso() == 'I') {
            PraticheEventi praticaEventoSorgente = null;
            if (eb.getIdPraticaProtocollo() != null) {
                PraticheProtocollo praticaProtocolloCollegata = praticheProtocolloService.findPraticaProtocolloById(eb.getIdPraticaProtocollo());
                praticaEventoSorgente = praticaProtocolloCollegata.getIdPraticaEventoSorgente();

            }

//            processiDao.findProcessiEventiByCodEventoIdProcesso
//            endoProcedimentoEnte.getIdProcess
            if (praticaEventoSorgente == null) {
                if (eb.getPraticaProcedimentiSelected() != null && !eb.getPraticaProcedimentiSelected().isEmpty()) {
                    if (eb.getPraticaProcedimentiSelected().size() > 1) {
                        Log.APP.warn("ATTENZIONE!!! Evento in ricezione con più procedimenti selezionati. Questo non dovrebbe essere possibile, l'interfaccia di gestione dovrebbe impedirlo.");
                        Log.APP.warn("Verrà preso il primo procedimento e ignorati i successivi");
                    }
                    //capisco il procedimento. In teoria dovrebbe essercene al massimo uno. In caso contrario prendo il primo. Loggo nell'istruzione precedente l'anomalia.
                    PraticaProcedimentiPKDTO praticaProcedimento = eb.getPraticaProcedimentiSelected().get(0);
                    Integer idEndoProcedimento = praticaProcedimento.getIdProcedimento();
                    Integer idEnteEndoProcedimento = praticaProcedimento.getIdEnte();
                    ProcedimentiEnti endoProcedimentoEnte = procedimentiService.requireProcedimentoEnte(idEndoProcedimento, idEnteEndoProcedimento, pratica.getIdProcEnte().getIdEnte(), pratica.getIdComune());

                    ProcessiEventi nuovoEvento = processiDao.findProcessiEventiByCodEventoIdProcesso(AnaTipiEvento.RICEZIONE_COMUNICAZIONE_DA_SISTEMA, endoProcedimentoEnte.getIdProcesso().getIdProcesso());
                    if (nuovoEvento != null) {
                        /**
                         * capisco la pratica di riferimento. Quella che ha
                         * endoprocedimentoEnte appena trovato e idPraticaPadre
                         * uguale alla mia pratica corrente visto che ho
                         * chiamato la updatePraticheFiglie prima ho la
                         * sicurezza che la sotto pratica esista. In teoria
                         * l'unico caso in cui nella comunicazione corrente
                         * viene creata la sottopratica e eseguito questo pezzo
                         * di codice è quando l'operatore ha: - aggiunto un endo
                         * procedimento alla pratica - non ha fatto una
                         * comunicazione all'ente destinatario - magicamente
                         * però gli è arrivato una comunicazione (su quella
                         * pratica) dall'ente In teoria non dovrebbe mai
                         * succedere ma per qualche motivo lo sportello comunica
                         * "fuori sistema" e riceve la risposta noi la gestiamo.
                         *
                         */
                        Pratica praticaFigliaDaAggiornare = praticheService.findPraticaWithPraticaPadreAndEnteAndProcedimento(pratica.getIdPratica(), idEnteEndoProcedimento, idEndoProcedimento);
                        //Creo l'evento sulla sottopratica.
                        ComunicazioneBean cbRice = new ComunicazioneBean();
                        cbRice.setIdPratica(praticaFigliaDaAggiornare.getIdPratica());
                        nuovoEvento.setFlgProtocollazione("N");
                        cbRice.setIdEventoProcesso(nuovoEvento.getIdEvento());
                        cb.setIdUtente(cb.getIdUtente());
                        //cbRice.setAllegati(cb.getAllegati());
                        cbRice.setAllegati(cb.getAllegati());
                        cbRice.setVisibilitaCross(Boolean.TRUE);
                        cbRice.setVisibilitaUtente(Boolean.FALSE);
                        cbRice.setNote(cb.getNote());
                        workFlowService.gestisciProcessoEvento(cbRice);
//                        praticaEventoSorgente = cbRice.getEventoPratica();
//                        cb.getMessages().put("EVENTO_SOTTO_PRATICA_" + UUID.randomUUID(), cbRice.getIdEventoPratica());
                        Map<String, Map> eventoSottopraticaMap = (Map<String, Map>) cb.getMessages().get("EVENTO_SOTTO_PRATICA_BEANS");
                        if (eventoSottopraticaMap == null) {
                            eventoSottopraticaMap = new HashMap<String, Map>();
                            cb.getMessages().put("EVENTO_SOTTO_PRATICA_BEANS", eventoSottopraticaMap);
                        }

                        String cbRiceJson = new Gson().toJson(cbRice);
                        String className = cbRice.getClass().getCanonicalName();

                        eventoSottopraticaMap.put(cbRice.getIdEventoPratica().toString(), new ImmutableMap.Builder<String, String>()
                                .put("json", cbRiceJson)
                                .put("classname", className)
                                .build());

                        praticaEventoSorgente = praticheService.getPraticaEvento(cbRice.getIdEventoPratica());
//                        praticheService.startCommunicationProcess(praticaFigliaDaAggiornare, praticheService.getPraticaEvento(cbRice.getIdEventoPratica()), cbRice);
                    }
                }
            }
            if (praticaEvento.getPraticaEventoRef() == null) {
                praticaEvento.setPraticaEventoRef(praticaEventoSorgente);
            }
        }

        //Valorizza l'oggetto comunicazioneBean inserendo i destinatari della comunicazione e creando 0/n record nella tabella Comunicazione
        creaComunicazioni(cb);

        if (cb.getInviaMail() && praticaEvento.getIdEvento().getFlgMail().equalsIgnoreCase("S")) {
            salvaMailInDb(cb);
        }
    }

    private List<Enti> getEntiCoinvolti(List<Integer> entiCoinvolti) {
        List<Enti> enti = new ArrayList<Enti>();
        if (entiCoinvolti != null && !entiCoinvolti.isEmpty()) {
            for (Integer idEnte : entiCoinvolti) {
                Enti ente = entiService.findByIdEnte(idEnte);
                enti.add(ente);
            }
        }
        return enti;
    }

    private List<Allegati> getAllegati(List<Integer> idAllegati) throws Exception {
        List<Allegati> allegati = new ArrayList<Allegati>();
        if (idAllegati != null && !idAllegati.isEmpty()) {
            for (Integer idAllegato : idAllegati) {
                Allegati allegato = allegatiService.findAllegatoById(idAllegato);
                allegati.add(allegato);
            }
        }
        return allegati;
    }

    // <editor-fold defaultstate="collapsed" desc=" GESTIONE PRATICHE FIGLIE ">
    private void updatePraticheFiglie(Pratica pratica, ComunicazioneBean cb) throws Exception {
        ProcessiEventi eventoProcesso = praticheService.findProcessiEventi(cb.getIdEventoProcesso());
        Log.APP.info("FLG SOTTOPRATICA: " + eventoProcesso.getFlgApriSottopratica());
        if (eventoProcesso.getFlgApriSottopratica() != null && eventoProcesso.getFlgApriSottopratica().equalsIgnoreCase("S")) {

//            Procedimenti procedimento = cb.getProcedimento();
            Enti ente;
            Log.APP.info("procedimento null");
            int i = 1;
            Long praticheFiglie = praticaDao.countPraticheFiglie(pratica);
            if (praticheFiglie != null && praticheFiglie.intValue() > 0) {
                i = praticheFiglie.intValue() + 1;
            }
            Procedimenti procedimento;
            Boolean eventoConEndoProcedimentiSelezionabili = !CollectionUtils.isEmpty(cb.getPraticaProcedimentiSelected());
            List<Integer> endoProcedimentiCoinvolti = new ArrayList<Integer>();
            if (eventoConEndoProcedimentiSelezionabili) {
                for (PraticaProcedimentiPKDTO procedimentoCoinvolto : cb.getPraticaProcedimentiSelected()) {
                    endoProcedimentiCoinvolti.add(procedimentoCoinvolto.getIdProcedimento());
                }
            }
            for (PraticaProcedimenti praticaProcedimenti : pratica.getPraticaProcedimentiList()) {
                procedimento = praticaProcedimenti.getProcedimenti();
                ente = praticaProcedimenti.getEnti();

                if (eventoConEndoProcedimentiSelezionabili && !endoProcedimentiCoinvolti.contains(procedimento.getIdProc())) {
                    continue;
                }
                //Apro le sottopratiche solo per gli enti selezionati nella comunicazione corrente
                if (eventoProcesso.getVerso() == 'I') {
                    if (cb.getMittenti().getIdEnti().contains(ente.getIdEnte())) {
                        i = createPraticaFiglia(cb, pratica, procedimento, ente, i);
                    }
                } else {
                    if (cb.getDestinatari().getIdEnti().contains(ente.getIdEnte())) {
                        i = createPraticaFiglia(cb, pratica, procedimento, ente, i);
                    }
                }
            }
        }
    }

    private Integer createPraticaFiglia(ComunicazioneBean cb, Pratica praticaPadre, Procedimenti procedimento, Enti ente, int counter) throws Exception {
        Log.APP.info("Inizio creazione pratica figlia");
        // Pratica praticaEnte = praticheService.getPraticaEnte(praticaPadre, ente);
        Pratica praticaEnte = praticheService.findPraticaWithPraticaPadreAndEnteAndProcedimento(praticaPadre.getIdPratica(), ente.getIdEnte(), procedimento.getIdProc());

        Log.APP.info("Verifico se è disponibile un flusso per l'ente " + ente.getDescrizione() + " e il procedimento " + procedimento.getCodProc());
        Processi processo = workFlowService.getProcessToUse(ente.getIdEnte(), procedimento.getIdProc());
        if (processo != null) {
            Log.APP.info("Trovato il processo " + processo.getDesProcesso());
            if (praticaEnte == null) {
                //Creo l'XML per lo staging
                String oggetto;
                if (cb.getOggetto() != null && !"".equals(cb.getOggetto())) {
                    oggetto = cb.getOggetto();
                } else {
                    ProcedimentiLocalizzatiView procedimentoLocalizzato = procedimentiService.getProcedimentoLocalizzato("it", procedimento.getIdProc());
                    oggetto = procedimentoLocalizzato.getDesProc();

                }
                Log.APP.info("Creo una nuova sottopratica");
                praticaEnte = new Pratica();
                //Di default valorizzo con i dati di protocollazione della pratica padre.
                //se prevista protocollazione, aggiornerò i valori ...
                praticaEnte.setAnnoRiferimento(praticaPadre.getAnnoRiferimento());
                // TODO: protocollo                
                //praticaEnte.setCodFascicolo(praticaPadre.getCodFascicolo());
                praticaEnte.setCodRegistro(praticaPadre.getCodRegistro());
                praticaEnte.setProtocollo(praticaPadre.getProtocollo());
                //La prevalorizz: se prevista protocollazione verrà sovrascritto
                praticaEnte.setDataProtocollazione(praticaPadre.getDataProtocollazione());
                praticaEnte.setDataRicezione(cb.getDataEvento());
                praticaEnte.setDatiCatastaliList(praticaPadre.getDatiCatastaliList());
                praticaEnte.setIdComune(praticaPadre.getIdComune());
                praticaEnte.setIdModello(praticaPadre.getIdModello());
                praticaEnte.setIdPraticaPadre(praticaPadre);
                praticaEnte.setOggettoPratica(oggetto);
                ProcedimentiEnti pe = procedimentiService.findProcedimentiEnti(ente.getIdEnte(), procedimento.getIdProc());
                praticaEnte.setIdProcEnte(pe);
                praticaEnte.setResponsabileProcedimento(pe.getResponsabileProcedimento());
//                praticaEnte.setIdProc(procedimento);
//                praticaEnte.setIdEnte(ente);
                praticaEnte.setIdProcesso(processo);

                /**
                 * CAMPI CORRETTAMENTE VALORIZZATI A NULL
                 * pratica.setIdRecapito(null); pratica.setIdStaging(null);
                 * pratica.setIdUtente(null);
                 * pratica.setPraticaProcedimentiList(null);
                 */
                LkStatoPratica statoNuovaPratica = lookupDao.findStatoPraticaByCodice("A");
                praticaEnte.setIdStatoPratica(statoNuovaPratica);
                String identificativoPratica = praticaPadre.getIdentificativoPratica() + "-" + String.valueOf(counter);
                praticaEnte.setIdentificativoPratica(identificativoPratica);
                praticaDao.insert(praticaEnte);
                //Eseguo il flush per poter avere l'id della pratica
                usefulDao.flush();

                List<PraticaAnagrafica> praticaEnteAnagraficheList = new ArrayList<PraticaAnagrafica>();
                PraticaAnagrafica praticaEnteAnagrafica;
                PraticaAnagraficaPK praticaAnagraficaPK;
                for (PraticaAnagrafica praticaAnagraficaPadre : praticaPadre.getPraticaAnagraficaList()) {
                    praticaAnagraficaPK = new PraticaAnagraficaPK();
                    praticaAnagraficaPK.setIdPratica(praticaEnte.getIdPratica());
                    praticaAnagraficaPK.setIdAnagrafica(praticaAnagraficaPadre.getPraticaAnagraficaPK().getIdAnagrafica());
                    praticaAnagraficaPK.setIdTipoRuolo(praticaAnagraficaPadre.getPraticaAnagraficaPK().getIdTipoRuolo());
                    praticaEnteAnagrafica = new PraticaAnagrafica(praticaAnagraficaPK);
                    praticaEnteAnagrafica.setPratica(praticaEnte);
                    praticaEnteAnagrafica.setAnagrafica(praticaAnagraficaPadre.getAnagrafica());
                    praticaEnteAnagrafica.setLkTipoRuolo(praticaAnagraficaPadre.getLkTipoRuolo());
                    praticaEnteAnagrafica.setAssensoUsoPec(praticaAnagraficaPadre.getAssensoUsoPec());
                    praticaEnteAnagrafica.setDataInizioValidita(praticaAnagraficaPadre.getDataInizioValidita());
                    praticaEnteAnagrafica.setDescrizioneTitolarita(praticaAnagraficaPadre.getDescrizioneTitolarita());
                    praticaEnteAnagrafica.setIdRecapitoNotifica(praticaAnagraficaPadre.getIdRecapitoNotifica());
                    praticaEnteAnagrafica.setIdTipoQualifica(praticaAnagraficaPadre.getIdTipoQualifica());
                    praticaEnteAnagrafica.setPec(praticaAnagraficaPadre.getPec());
                    praticaEnteAnagraficheList.add(praticaEnteAnagrafica);
                }

                praticaEnte.setPraticaAnagraficaList(praticaEnteAnagraficheList);

                List<DatiCatastali> datiCatastaliList = new ArrayList<DatiCatastali>();
                //Porto i dati catastali anche nelle pratiche figlie
                if (praticaPadre.getDatiCatastaliList() != null && !praticaPadre.getDatiCatastaliList().isEmpty()) {
                    for (DatiCatastali datoCatastale : praticaPadre.getDatiCatastaliList()) {
                        DatiCatastali datoCatastalePraticaFiglia = datiCatastaliSerializer.copy(datoCatastale, praticaEnte);
                        //praticaDao.insert(datoCatastalePraticaFiglia);
                        datiCatastaliList.add(datoCatastalePraticaFiglia);
//                        usefulDao.flush();
                    }
                }
                praticaEnte.setDatiCatastaliList(datiCatastaliList);
                //Porto i dati indirizzo  anche nelle pratiche figlie
                List<IndirizziIntervento> indirizziInterventoList = new ArrayList<IndirizziIntervento>();
                if (praticaPadre.getIndirizziInterventoList() != null && !praticaPadre.getIndirizziInterventoList().isEmpty()) {
                    for (IndirizziIntervento indirizzoIntervento : praticaPadre.getIndirizziInterventoList()) {
                        IndirizziIntervento indirizzoInterventoPraticaFiglia = indirizziInterventoService.copy(indirizzoIntervento, praticaEnte);
                        //praticaDao.insert(indirizzoInterventoPraticaFiglia);
                        indirizziInterventoList.add(indirizzoInterventoPraticaFiglia);
//                        usefulDao.flush();
                    }
                }
                praticaEnte.setIndirizziInterventoList(indirizziInterventoList);

                //Popolo l'area di staging
                byte[] xmlRicevuto = praticaPadre.getIdStaging().getXmlRicevuto();
                it.wego.cross.xml.Pratica praticaXml = praticheSerializer.serialize(praticaPadre, null, null);
                String xmlPratica = Utils.marshall(praticaXml);
                Staging staging = new Staging();
                staging.setOggetto(oggetto);
                staging.setDataRicezione(new Date());
                staging.setTipoMessaggio(Constants.WEBSERVICE_AEC);
                staging.setXmlRicevuto(xmlRicevuto);
                staging.setXmlPratica(xmlPratica.getBytes());
                staging.setIdEnte(ente);
                stagingDao.insert(staging);
//                usefulDao.flush();
                praticaEnte.setIdStaging(staging);
//                praticaDao.update(praticaEnte);

                List<Allegato> allegati = new ArrayList<Allegato>();
                PraticheEventi praticaEvento = praticheService.getPraticaEvento(cb.getIdEventoPratica());
                //TODO: gestire il caso Comune su Ente
                GestioneAllegati gestioneAllegati = pluginService.getGestioneAllegati(ente.getIdEnte(), null);
                for (PraticheEventiAllegati pea : praticaEvento.getPraticheEventiAllegatiList()) {
                    Allegati a = pea.getAllegati();
                    Allegato allegato;
                    if (!Utils.e(a.getIdFileEsterno())) {
                        allegato = gestioneAllegati.getFile(a.getIdFileEsterno(), praticaEnte.getIdProcEnte().getIdEnte(), praticaEnte.getIdComune());
                    } else {
                        allegato = AllegatiSerializer.serializeAllegato(a);
                    }
                    allegato.setDescrizione(a.getDescrizione());
                    allegato.setNomeFile(a.getNomeFile());
                    allegato.setProtocolla(Boolean.TRUE);
                    allegato.setTipoFile(a.getTipoFile());
                    allegati.add(allegato);
                }
                ComunicazioneBean cbRice = new ComunicazioneBean();
                cbRice.setIdPratica(praticaEnte.getIdPratica());
                ProcessiEventi nuovoEvento = processiDao.findProcessiEventiByCodEventoIdProcesso(AnaTipiEvento.RICEZIONE_PRATICA, processo.getIdProcesso());
                PraticheEventi eventoRicezionePraticaPadre = IdentificatoreProtocolloIstanzaConverter.getEventoRicezionePratica(praticaPadre);
//        Disabilito la protocollazione, perché già fatta per l'evento
//            nuovoEvento.setFlgProtocollazione("N");
                cbRice.setIdEventoProcesso(nuovoEvento.getIdEvento());
                cb.setIdUtente(cb.getIdUtente());
                //cbRice.setAllegati(cb.getAllegati());

                //cbRice.setDataProtocollo(cb.getDataProtocollo());
                //cbRice.setNumeroProtocollo(cb.getNumeroProtocollo());
                if (!"S".equalsIgnoreCase(nuovoEvento.getFlgProtocollazione())) {
                    cbRice.setNumeroProtocollo(eventoRicezionePraticaPadre.getProtocollo());
                    cbRice.setDataProtocollo(eventoRicezionePraticaPadre.getDataProtocollo());
                }
                cbRice.setAllegati(allegati);
                cbRice.setVisibilitaCross(Boolean.TRUE);
                cbRice.setVisibilitaUtente(Boolean.FALSE);
                cbRice.setNote(cb.getNote());
                cbRice.setDestinatariEmail(Arrays.asList(ente.getPec(), ente.getEmail()));
                cbRice.setOggettoEmail(cb.getOggettoEmail());
                cbRice.setCorpoEmail(cb.getCorpoEmail());
                cbRice.setOggettoProtocollo(cb.getOggettoProtocollo());
                cbRice.setDestinatari(cb.getDestinatari());

                cbRice.setIdEventoPraticaPadre(cb.getIdEventoPratica());
                workFlowService.gestisciProcessoEvento(cbRice);

                Map<String, Map> eventoSottopraticaMap = (Map<String, Map>) cb.getMessages().get("EVENTO_SOTTO_PRATICA_BEANS");
                if (eventoSottopraticaMap == null) {
                    eventoSottopraticaMap = new HashMap<String, Map>();
                    cb.getMessages().put("EVENTO_SOTTO_PRATICA_BEANS", eventoSottopraticaMap);
                }

                String cbRiceJson = new Gson().toJson(cbRice);
                String className = cbRice.getClass().getCanonicalName();

                eventoSottopraticaMap.put(cbRice.getIdEventoPratica().toString(), new ImmutableMap.Builder<String, String>()
                        .put("json", cbRiceJson)
                        .put("classname", className)
                        .build());
                //cb.getMessages().put("EVENTO_SOTTO_PRATICA_" + UUID.randomUUID(), cbRice.getIdEventoPratica());
                return counter + 1;
            } else {
                Log.APP.info("Esiste già una sottopratica per quell/ente/procedimento");
                return counter;
            }
        } else {
            Log.APP.info("Nessun processo trovato, non creo nessuna pratica");
            return counter;
        }
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc=" INVIA MAIL ">    
    private void salvaMailInDb(ComunicazioneBean cb) throws Exception {
        List<String> mailDestinatari;
        if (cb.getDestinatari() != null) {
            AttoriComunicazione destinatari = cb.getDestinatari();
            mailDestinatari = getMailDestinatari(destinatari);
        } else {
            mailDestinatari = cb.getDestinatariEmail();
        }
        PraticheEventi praticaEvento = praticheService.getPraticaEvento(cb.getIdEventoPratica());
        mailService.salvaMailInDb(praticaEvento, mailDestinatari, cb.getOggettoEmail(), cb.getCorpoEmail());
    }

    private List<String> getMailDestinatari(AttoriComunicazione destinatari) {
        List<String> email = new ArrayList<String>();
        if (destinatari != null) {
            if (destinatari.getIdRecapitoNotifica() != null) {
                String indirizzo;
                Recapiti recapito = anagraficheService.findRecapitoById(destinatari.getIdRecapitoNotifica());
                if (recapito.getPec() != null) {
                    indirizzo = recapito.getPec();
                } else {
                    indirizzo = recapito.getEmail();
                }
                email.add(indirizzo);
                return email;
            }
            if (destinatari.getIdAnagraficheRecapiti() != null && destinatari.getIdAnagraficheRecapiti().size() > 0) {
                for (Integer idAnagraficaRecapito : destinatari.getIdAnagraficheRecapiti()) {
                    AnagraficaRecapiti a = anagraficheService.findAnagraficaRecapitiById(idAnagraficaRecapito);
                    if (a.getIdRecapito() != null) {
                        //Se c'è la PEC gli do priorità ...
                        if (!Utils.e(a.getIdRecapito().getPec())) {
                            email.add(a.getIdRecapito().getPec());
                        } else {
                            //... altrimenti prendo l'email
                            if (!Utils.e(a.getIdRecapito().getEmail())) {
                                email.add(a.getIdRecapito().getEmail());
                            } else {
                                //i destinatari senza email vengono gestiti in MailService
                                email.add("");
                            }
                        }
                    }
                }
            }
            if (destinatari.getIdEnti() != null && destinatari.getIdEnti().size() > 0) {
                for (Integer idEnte : destinatari.getIdEnti()) {
                    Enti e = entiService.findByIdEnte(idEnte);
                    if (!Utils.e(e.getPec())) {
                        email.add(e.getPec());
                    } else {
                        if (!Utils.e(e.getEmail())) {
                            email.add(e.getEmail());
                        }
                    }
                }
            }
        }
        return email;
    }
// </editor-fold>

    private void inserisciPraticaEventoAnagrafiche(PraticheEventi evento, AttoriComunicazione attori) throws Exception {
        if (attori == null || (attori.getIdAnagraficheRecapiti().isEmpty() && attori.getIdEnti().isEmpty())) {
            List<Integer> anagraficheEvento = new ArrayList<Integer>();
            List<PraticaAnagrafica> anagrafichePratica = evento.getIdPratica().getPraticaAnagraficaList();
            for (PraticaAnagrafica anagrafica : anagrafichePratica) {
                Integer idAnagrafica = anagrafica.getAnagrafica().getIdAnagrafica();
                AnagraficaRecapiti ar = anagraficheService.getAnagraficaRecapitoRiferimentoAnagrafica(anagrafica.getAnagrafica());
                if (ar != null && ar.getIdAnagrafica() != null && !anagraficheEvento.contains(idAnagrafica)) {
//                        AnagraficaRecapito anagraficaRecapito = new AnagraficaRecapito();
//                        anagraficaRecapito.setAnagrafica(ar.getIdAnagrafica());
//                        anagraficaRecapito.setRecapito(ar.getIdRecapito());
//                        destinatari.addAnagrafica(anagraficaRecapito);
                    PraticheEventiAnagrafiche pea = new PraticheEventiAnagrafiche();
                    PraticheEventiAnagrafichePK key = new PraticheEventiAnagrafichePK();
                    key.setIdAnagrafica(ar.getIdAnagrafica().getIdAnagrafica());
                    key.setIdPraticaEvento(evento.getIdPraticaEvento());
                    pea.setPraticheEventiAnagrafichePK(key);
                    pea.setIdRecapito(ar.getIdRecapito());
                    praticaDao.insert(pea);
                    anagraficheEvento.add(idAnagrafica);
                }
            }
        } else {
            List<Integer> anagraficheRecapiti = attori.getIdAnagraficheRecapiti();
            if (anagraficheRecapiti != null && !anagraficheRecapiti.isEmpty()) {
                for (Integer idAnagraficaRecapito : anagraficheRecapiti) {
                    AnagraficaRecapiti ar = anagraficheService.findAnagraficaRecapitiById(idAnagraficaRecapito);
                    PraticheEventiAnagrafiche pea = new PraticheEventiAnagrafiche();
                    PraticheEventiAnagrafichePK key = new PraticheEventiAnagrafichePK();
                    key.setIdAnagrafica(ar.getIdAnagrafica().getIdAnagrafica());
                    key.setIdPraticaEvento(evento.getIdPraticaEvento());
                    pea.setPraticheEventiAnagrafichePK(key);
                    pea.setIdRecapito(ar.getIdRecapito());
                    praticaDao.insert(pea);
                }
            }
        }
//
//        if (evento.getIdRecapitoNotifica() != null && ) {
//            //Ho inserito un recapito di notifica non i singoli attori. Di default vado a recuperare tutti i richiedenti e beneficiari e li collego all'evento
//            List<PraticaAnagrafica> anagrafichePratica = evento.getIdPratica().getPraticaAnagraficaList();
//            if (anagrafichePratica != null && !anagrafichePratica.isEmpty()) {
//                if (destinatari == null) {
//                    destinatari = new AttoriComunicazione();
//                    destinatari.setIdRecapitoNotifica(evento.getIdRecapitoNotifica().getIdRecapito());
//                }
//
//            }
//        }
//        if (destinatari != null) {
//
//        }
    }

    private void creaComunicazioni(ComunicazioneBean cb) throws Exception {
        if (cb.getDestinatari() != null) {
            List<Integer> entiCoinvolti = cb.getDestinatari().getIdEnti();
            if (entiCoinvolti != null && !entiCoinvolti.isEmpty()) {
                for (Integer idEnte : entiCoinvolti) {
                    Enti ente = entiService.findByIdEnte(idEnte);
                    boolean isCrossUser = entiService.isCrossUser(ente);
                    if (isCrossUser) {
                        //Salva un record nella tabella comunicazione con status NON LETTA, pratica evento, ente coinvolto
                        comunicazioneAction.creaComunicazione(cb, ente);
                    }
                }
            }
        }
    }
}
