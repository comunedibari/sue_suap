package it.wego.cross.restws;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import it.wego.cross.actions.EventiAction;
import it.wego.cross.beans.AttoriComunicazione;
import it.wego.cross.constants.Constants;
import it.wego.cross.constants.TipoRuolo;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.entity.Anagrafica;
import it.wego.cross.entity.AnagraficaRecapiti;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticaAnagrafica;
import it.wego.cross.entity.PraticaProcedimenti;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.entity.UtenteRuoloEnte;
import it.wego.cross.events.comunicazione.bean.ComunicazioneBean;
import it.wego.cross.events.notification.NotificationEngine;
import it.wego.cross.plugins.commons.beans.Allegato;
import it.wego.cross.service.AnagraficheService;
import it.wego.cross.service.ConfigurationService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.service.WorkFlowService;
import it.wego.utils.json.JsonBuilder;
import it.wego.utils.json.JsonMapTransformer;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 */
@Controller
public class RestWsController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PraticaDao praticaDao;
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private WorkFlowService workFlowService;
    @Autowired
    private EventiAction eventiAction;
    @Autowired
    private PraticheService praticheService;
    @Autowired
    private AnagraficheService anagraficheService;
    @Autowired
    private NotificationEngine notificationEngine;
    @Autowired
    private MessageSource messageSource;

    @PostConstruct
    public void init() {
        logger.info("ready");
    }

    private final Function<Pratica, Map> praticaTransformer = new JsonMapTransformer<Pratica>() {

        @Override
        public void transformToMap(final Pratica pratica) {
            put("idPratica", pratica.getIdPratica());
            put("dataInvio", pratica.getDataRicezione());
            put("codicePratica", pratica.getIdentificativoPratica());
            put("protocollo", Strings.nullToEmpty(pratica.getProtocolloDaEventoRicezione()));
            put("oggetto", pratica.getOggettoPratica());
            put("comune", pratica.getIdComune().getDescrizione());
            final Set<String> ruoli = Sets.newHashSet(Objects.firstNonNull(configurationService.getCachedConfiguration("FRONTCROSS.ROLES.ENABLED", pratica.getIdProcEnte().getIdEnte().getIdEnte(), pratica.getIdComune().getIdComune()), TipoRuolo.RICHIEDENTE).split(" *, *"));
            put("anagrafiche", Joiner.on(", ").join(Iterables.transform(Iterables.filter(pratica.getPraticaAnagraficaList(), new Predicate<PraticaAnagrafica>() {

                @Override
                public boolean apply(PraticaAnagrafica praticaAnagrafica) {
                    return ruoli.contains(praticaAnagrafica.getLkTipoRuolo().getCodRuolo());
                }
            }), new Function<PraticaAnagrafica, String>() {

                @Override
                public String apply(PraticaAnagrafica praticaAnagrafica) {
                    return Objects.equal(praticaAnagrafica.getAnagrafica().getTipoAnagrafica(), Constants.FLAG_ANAGRAFICA_FISICA)
                            ? StringUtils.trimToEmpty(Strings.nullToEmpty(praticaAnagrafica.getAnagrafica().getCognome()) + " " + Strings.nullToEmpty(praticaAnagrafica.getAnagrafica().getNome()))
                            : Strings.nullToEmpty(praticaAnagrafica.getAnagrafica().getDenominazione());
                }
            })));
            put("stato", pratica.getIdStatoPratica().getDescrizione());
            put("codiceStato", pratica.getIdStatoPratica().getCodice());
            put("codiceGruppoStato", pratica.getIdStatoPratica().getGrpStatoPratica());
            put("procedimento", pratica.getIdProcEnte().getIdProc().getProcedimentiTestiByLang(Constants.DEFAULT_LANGAUGE)
                    + (Objects.equal(pratica.getIdProcEnte().getIdEnte().getTipoEnte(), Constants.TIPO_ENTE_SUAP) ? (" : "
                            + Joiner.on(", ").join(Iterables.transform(pratica.getPraticaProcedimentiList(), new Function<PraticaProcedimenti, String>() {

                        @Override
                        public String apply(PraticaProcedimenti praticaProcedimenti) {
                            return praticaProcedimenti.getProcedimenti().getProcedimentiTestiByLang(Constants.DEFAULT_LANGAUGE);
                        }
                    }))) : ""));
            put("dataProtocollo", pratica.getDataProtocolloDaEventoRicezione());
            put("responsabileProcedimento", pratica.getResponsabileProcedimento());
        }
    };

    @RequestMapping("/findPraticheListByUserIdAndSearch")
    public @ResponseBody
    Object findPraticheListByUserIdAndSearch(@RequestParam String userId, @RequestParam(required = false) String search) {
        return JsonBuilder.newInstance().withData(praticaDao.findPratichePrimoLivelloByUtenteAndSearch(userId, search)).withTransformer(praticaTransformer).buildStoreResponse();
    }

    @RequestMapping("/getPraticaByIdPratica")
    public @ResponseBody
    Object getPraticaByIdPratica(@RequestParam Integer idPratica) {
        return JsonBuilder.newInstance().withData(praticaDao.requirePraticaByIdPratica(idPratica)).withTransformer(praticaTransformer).buildResponse();
    }

    @RequestMapping("/getPraticaByCodicePratica")
    public @ResponseBody
    Object getPraticaByCodicePratica(@RequestParam String codicePratica) {
        return JsonBuilder.newInstance().withData(praticaDao.requirePraticaByCodicePratica(codicePratica)).withTransformer(praticaTransformer).buildResponse();
    }

    @RequestMapping("/getEventiPraticaByIdPratica")
    public @ResponseBody
    Object getEventiPraticaByIdPratica(@RequestParam Integer idPratica) {
        return JsonBuilder.newInstance().withData(praticaDao.requirePraticaByIdPratica(idPratica).getPraticheEventiList()).withTransformer(new JsonMapTransformer<PraticheEventi>() {

            @Override
            public void transformToMap(PraticheEventi praticheEventi) {
                put("idEvento", praticheEventi.getIdPraticaEvento());
                put("protocollo", praticheEventi.getProtocollo());
                //TODO add more data
            }
        }).buildStoreResponse();
    }

    @RequestMapping("/inviaIntegrazione")
    public @ResponseBody
    Object inviaIntegrazione(@RequestBody RichiestaIntegrazioneBean richiestaIntegrazioneBean) throws Exception {
        logger.info("ricevuta richiesta integrazione = {} con {} allegati", richiestaIntegrazioneBean, richiestaIntegrazioneBean.getAllegati().size());
        Pratica pratica = praticaDao.findPratica(richiestaIntegrazioneBean.getIdPratica());
        Preconditions.checkNotNull(pratica);
        String codEvento = Objects.firstNonNull(configurationService.getCachedConfiguration("FRONTCROSS.EVENT.INTEGRAZIONE", pratica.getIdEnteProcEnteInteger(), pratica.getIdComuneInteger()), "RICEZ_INTEG");
        ProcessiEventi processoEvento = workFlowService.findProcessiEventiByIdProcessoCodEvento(pratica.getIdProcesso(), codEvento);
        Preconditions.checkNotNull(processoEvento, "processo evento non trovato per codEvento = %s", codEvento);

        boolean bloccaPraticheChiuse = Boolean.parseBoolean(Objects.firstNonNull(Strings.emptyToNull(configurationService.getCachedConfiguration("FRONTCROSS.BLOCCAPRATICHECHIUSE", pratica.getIdEnteProcEnteInteger(), pratica.getIdComuneInteger())), Boolean.TRUE.toString()));
        if (bloccaPraticheChiuse) {
            Preconditions.checkArgument(!Objects.equal(pratica.getIdStatoPratica().getGrpStatoPratica(), Constants.GRUPPO_STATO_PRATICA_C), "impossibile inviare integrazione, la pratica e' chiusa");
        }

        Anagrafica anagrafica = anagraficheService.findAnagraficaByCodFiscale(richiestaIntegrazioneBean.getUserId());
        Preconditions.checkNotNull(anagrafica, "anagrafica non trovata per codiceFiscale = %s", richiestaIntegrazioneBean.getUserId());

        AttoriComunicazione attoriComunicazione = new AttoriComunicazione();
        AnagraficaRecapiti anagraficaRecapiti = anagraficheService.getAnagraficaRecapitoRiferimentoAnagrafica(anagrafica);
        attoriComunicazione.addAnagrafica(anagraficaRecapiti);

        ComunicazioneBean eventoBean = new ComunicazioneBean();
        eventoBean.setDestinatari(attoriComunicazione);
        eventoBean.setMittenti(attoriComunicazione);
        eventoBean.setIdPratica(richiestaIntegrazioneBean.getIdPratica());
        eventoBean.setIdEventoProcesso(processoEvento.getIdEvento());
        eventoBean.setIdUtente(null);
        eventoBean.setOggettoProtocollo(richiestaIntegrazioneBean.getIntegrazione().getOggetto());
        eventoBean.setNote(richiestaIntegrazioneBean.getIntegrazione().getOggetto());

        eventoBean.setAllegati(Lists.newArrayList(Lists.transform(richiestaIntegrazioneBean.getAllegati(), new Function<AllegatoBean, Allegato>() {

            @Override
            public Allegato apply(AllegatoBean allegatoBean) {
                Allegato allegato = new Allegato();
                allegato.setDescrizione(allegatoBean.getDescrizioneFile());
                allegato.setNomeFile(allegatoBean.getNomeFile());
                allegato.setFile(Base64.decodeBase64(allegatoBean.getB64FileData()));
                allegato.setTipoFile(allegatoBean.getMimeType());
                allegato.setFileOrigine(allegatoBean.getAllegatoPrincipale());
                return allegato;
            }
        })));
        eventiAction.gestisciProcessoEvento(eventoBean);
        PraticheEventi eventoPratica = praticheService.getPraticaEvento(eventoBean.getIdEventoPratica());
        praticheService.startCommunicationProcess(eventoPratica.getIdPratica(), eventoPratica, eventoBean);

        notificationEngine.createNotification(eventoPratica, pratica.getIdUtente() != null ? Arrays.asList(pratica.getIdUtente().getUsername()) : Lists.newArrayList(Lists.transform(pratica.getIdProcEnte().getUtenteRuoloEnteList(), new Function<UtenteRuoloEnte, String>() {

            @Override
            public String apply(UtenteRuoloEnte ruolo) {
                return ruolo.getIdUtente().getUsername();
            }
        })),
                messageSource.getMessage("nuovaIntegrazione.title", null, Locale.getDefault()),
                messageSource.getMessage("nuovaIntegrazione.message", new Object[]{
                    anagrafica.getCognome(),
                    anagrafica.getNome(),
                    Strings.isNullOrEmpty(pratica.getProtocollo()) ? pratica.getIdentificativoPratica() : (pratica.getCodRegistro() + "/" + pratica.getAnnoRiferimento() + "/" + pratica.getProtocollo())}, Locale.getDefault()));

        return JsonBuilder.newInstance().withRecord("idEventoProtocollazione", eventoPratica.getIdPraticaEvento()).buildResponse();
    }

    @RequestMapping("/test")
    public @ResponseBody
    Object test() {
        return JsonBuilder.newInstance().buildResponse();
    }

    @ExceptionHandler
    public @ResponseBody
    Object handleException(Exception exception) {
        logger.error("error", exception);
        return JsonBuilder.newInstance().withError(exception).buildResponse();
    }

    public static class RichiestaIntegrazioneBean {

        private Integer idPratica;
        private String codicePratica, userId;

        private IntegrazioneBean integrazione;
        private List<AllegatoBean> allegati;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public Integer getIdPratica() {
            return idPratica;
        }

        public void setIdPratica(Integer idPratica) {
            this.idPratica = idPratica;
        }

        public String getCodicePratica() {
            return codicePratica;
        }

        public void setCodicePratica(String codicePratica) {
            this.codicePratica = codicePratica;
        }

        public IntegrazioneBean getIntegrazione() {
            return integrazione;
        }

        public void setIntegrazione(IntegrazioneBean integrazione) {
            this.integrazione = integrazione;
        }

        public List<AllegatoBean> getAllegati() {
            return allegati;
        }

        public void setAllegati(List<AllegatoBean> allegati) {
            this.allegati = allegati;
        }

    }

    public static class IntegrazioneBean {

        private String oggetto;
        private Date dataCreazione;

        public String getOggetto() {
            return oggetto;
        }

        public void setOggetto(String oggetto) {
            this.oggetto = oggetto;
        }

        public Date getDataCreazione() {
            return dataCreazione;
        }

        public void setDataCreazione(Date dataCreazione) {
            this.dataCreazione = dataCreazione;
        }

    }

    public static class AllegatoBean {

        private Integer idAllegato;
        private Boolean allegatoPrincipale;
        private String nomeFile, descrizioneFile, b64FileData, mimeType;

        public Integer getIdAllegato() {
            return idAllegato;
        }

        public Boolean getAllegatoPrincipale() {
            return allegatoPrincipale;
        }

        public void setAllegatoPrincipale(Boolean allegatoPrincipale) {
            this.allegatoPrincipale = allegatoPrincipale;
        }

        public String getMimeType() {
            return mimeType;
        }

        public void setMimeType(String mimeType) {
            this.mimeType = mimeType;
        }

        public void setIdAllegato(Integer idAllegato) {
            this.idAllegato = idAllegato;
        }

        public String getNomeFile() {
            return nomeFile;
        }

        public void setNomeFile(String nomeFile) {
            this.nomeFile = nomeFile;
        }

        public String getDescrizioneFile() {
            return descrizioneFile;
        }

        public void setDescrizioneFile(String descrizioneFile) {
            this.descrizioneFile = descrizioneFile;
        }

        public String getB64FileData() {
            return b64FileData;
        }

        public void setB64FileData(String b64FileData) {
            this.b64FileData = b64FileData;
        }

    }
}
