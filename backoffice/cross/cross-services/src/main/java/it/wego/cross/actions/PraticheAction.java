/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.actions;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import it.gov.impresainungiorno.schema.suap.ente.CooperazioneSUAPEnte;
import it.gov.impresainungiorno.schema.suap.pratica.RiepilogoPraticaSUAP;
import it.wego.cross.beans.AnagraficaRecapito;
import it.wego.cross.beans.AttoriComunicazione;
import it.wego.cross.beans.grid.GridAssociaUtentiBean;
import it.wego.cross.beans.grid.GridDatiCatastaliBean;
import it.wego.cross.beans.grid.GridDatoCatastaleBean;
import it.wego.cross.beans.grid.GridEstrazioniAGIBBean;
import it.wego.cross.beans.grid.GridEstrazioniCILABean;
import it.wego.cross.beans.grid.GridEstrazioniPDCBean;
import it.wego.cross.beans.grid.GridEstrazioniSCIABean;
import it.wego.cross.beans.grid.GridIndirizziInterventoBean;
import it.wego.cross.beans.grid.GridIndirizzoInterventoBean;
import it.wego.cross.beans.grid.GridPraticaEventoBean;
import it.wego.cross.beans.grid.GridPraticaNuovaBean;
import it.wego.cross.beans.grid.GridRisultatoCaricamentoPraticaBean;
import it.wego.cross.beans.layout.JqgridPaginator;
import it.wego.cross.constants.Constants;
import it.wego.cross.constants.SessionConstants;
import it.wego.cross.constants.StatoPratica;
import it.wego.cross.dao.AnagraficheDao;
import it.wego.cross.dao.EntiDao;
import it.wego.cross.dao.ErroriDao;
import it.wego.cross.dao.LookupDao;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dao.ProcedimentiDao;
import it.wego.cross.dao.ProcessiDao;
import it.wego.cross.dao.StagingDao;
import it.wego.cross.dao.UtentiDao;
import it.wego.cross.dto.AllegatoRicezioneDTO;
import it.wego.cross.dto.dozer.DatiCatastaliDTO;
import it.wego.cross.dto.ErroreDTO;
import it.wego.cross.dto.EstrazioniAgibDTO;
import it.wego.cross.dto.EstrazioniCilaDTO;
import it.wego.cross.dto.EstrazioniPdcDTO;
import it.wego.cross.dto.EstrazioniSciaDTO;
import it.wego.cross.dto.EventoDTO;
import it.wego.cross.dto.PermessiEnteProcedimentoDTO;
import it.wego.cross.dto.PraticaDTO;
import it.wego.cross.dto.PraticaNuova;
import it.wego.cross.dto.PraticheEventiRidDTO;
import it.wego.cross.dto.ScadenzaDTO;
import it.wego.cross.dto.UtenteDTO;
import it.wego.cross.dto.dozer.IndirizzoInterventoDTO;
import it.wego.cross.dto.dozer.PraticaEventoDTO;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.Allegati;
import it.wego.cross.entity.Anagrafica;
import it.wego.cross.entity.AnagraficaRecapiti;
import it.wego.cross.entity.DatiCatastali;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.IndirizziIntervento;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.LkDug;
import it.wego.cross.entity.LkStatiPraticaOrganiCollegiali;
import it.wego.cross.entity.LkStatiScadenze;
import it.wego.cross.entity.LkStatoPratica;
import it.wego.cross.entity.LkTipoQualifica;
import it.wego.cross.entity.LkTipoRuolo;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticaAnagrafica;
import it.wego.cross.entity.PraticaProcedimenti;
import it.wego.cross.entity.PraticaProcedimentiPK;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.Procedimenti;
import it.wego.cross.entity.ProcedimentiEnti;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.entity.ProcessiEventiScadenze;
import it.wego.cross.entity.RisultatoCaricamentoPratiche;
import it.wego.cross.entity.Scadenze;
import it.wego.cross.entity.Staging;
import it.wego.cross.entity.Utente;
import it.wego.cross.events.comunicazione.bean.ComunicazioneBean;
import it.wego.cross.events.comunicazionerea.serializer.ComunicazioneReaSerializer;
import it.wego.cross.exception.CrossException;
import it.wego.cross.plugins.commons.beans.Allegato;
import it.wego.cross.plugins.pratica.GestionePratica;
import it.wego.cross.serializer.AllegatiSerializer;
import it.wego.cross.serializer.AnagraficheSerializer;
import it.wego.cross.serializer.DatiCatastaliSerializer;
import it.wego.cross.serializer.EventiSerializer;
import it.wego.cross.serializer.FilterSerializer;
import it.wego.cross.service.AllegatiService;
import it.wego.cross.service.AnagraficheService;
import it.wego.cross.service.ConfigurationService;
import it.wego.cross.service.DatiCatastaliService;
import it.wego.cross.service.IndirizziInterventoService;
import it.wego.cross.service.IndirizziInterventoServiceImpl;
import it.wego.cross.service.LookupService;
import it.wego.cross.service.PluginService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.service.ProcedimentiService;
import it.wego.cross.service.RisultatoCaricamentoPraticheService;
import it.wego.cross.service.UsefulService;
import it.wego.cross.service.UtentiService;
import it.wego.cross.service.WorkFlowService;
import it.wego.cross.utils.DozerUtils;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.PraticaUtils;
import it.wego.cross.utils.RecapitoUtils;
import it.wego.cross.utils.Utils;
import it.wego.cross.xml.Anagrafiche;
import it.wego.cross.xml.Immobile;

import java.io.StringWriter;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import org.activiti.engine.delegate.DelegateExecution;
import org.apache.commons.collections.CollectionUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigInteger;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import org.activiti.engine.delegate.DelegateExecution;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author giuseppe
 */
@Component
public class PraticheAction {

    @Autowired
    private PraticheService praticheService;
    @Autowired
    private PraticaDao praticaDao;
    @Autowired
    private AnagraficheDao anagraficheDao;
    @Autowired
    private ComunicazioneReaSerializer comunicazioneReaSerializer;
    @Autowired
    private UsefulService usefulService;
    @Autowired
    private LookupDao lookupDao;
    @Autowired
    private LookupService lookupService;
    @Autowired
    private FilterSerializer filterSerializer;
    @Autowired
    private ProcedimentiDao procedimentiDao;
    @Autowired
    private UtentiService utentiService;
    @Autowired
    private AnagraficheService anagraficheService;
    @Autowired
    private StagingDao stagingDao;
    @Autowired
    private UtentiDao utenteDao;
    @Autowired
    private ErroriAction erroriAction;
    @Autowired
    private ProcedimentiService procedimentiService;
    @Autowired
    private WorkFlowService workFlowService;
    @Autowired
    private EntiDao entiDao;
    @Autowired
    protected ProcessiDao processiDao;
    @Autowired
    private Mapper mapper;
    @Autowired
    private PluginService pluginService;
    @Autowired
    private DatiCatastaliSerializer datiCatastaliSerializer;
    @Autowired
    private AnagraficheSerializer anagraficheSerializer;
    @Autowired
    private ConfigurationService configuration;
    @Autowired
    private DatiCatastaliService datiCatastaliService;
    @Autowired
    private IndirizziInterventoService indirizziInterventoService;
    @Autowired
    private SystemEventAction systemEventAction;
    @Autowired
    private WorkflowAction workflowAction;
    @Autowired
    private AllegatiService allegatiService;

    @Transactional
    public String generaXMLSuri(EventoDTO evento) throws Exception {
        Pratica pratica = praticaDao.findPratica(evento.getIdPratica());
        PraticheEventi praticaEvento = praticaDao.getDettaglioPraticaEvento(evento.getIdPraticaEvento());
        RiepilogoPraticaSUAP pratucaSUAP = comunicazioneReaSerializer.serializeRiepilogoPraticaSUAP(pratica, praticaEvento);
        JAXBContext jc = JAXBContext.newInstance(it.gov.impresainungiorno.schema.suap.pratica.RiepilogoPraticaSUAP.class);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        StringWriter sw = new StringWriter();
        marshaller.marshal(pratucaSUAP, sw);
        return sw.toString();
    }

    public GridPraticaNuovaBean getPraticheDaGestire(HttpServletRequest request, JqgridPaginator paginator) throws Exception {
        GridPraticaNuovaBean json = new GridPraticaNuovaBean();
        Integer maxResult = Integer.parseInt(paginator.getRows());
        Integer page = Integer.parseInt(paginator.getPage());
        String column = paginator.getSidx();
        String order = paginator.getSord();
        Integer firstRecord = (page * maxResult) - maxResult;
        Filter filter = filterSerializer.getSearchFilter(request);
        filter.setSearchByUtenteConnesso(false);
        filter.setPage(page);
        filter.setLimit(maxResult);
        filter.setOffset(firstRecord);
        filter.setOrderColumn(column);
        filter.setOrderDirection(order);
        //Il defautl è ATTIVE 
        if ((filter.getDesStatoPratica() != null && filter.getDesStatoPratica().equals("ATTIVE"))) {
            //Escludo le pratiche ricevute e quelle chiuse
            List<LkStatoPratica> daEscludere = new ArrayList<LkStatoPratica>();
            //SAL3
            LkStatoPratica ricevuta = lookupService.findStatoPraticaByCodice("R");
            daEscludere.add(ricevuta);
            List<LkStatoPratica> chiuse = lookupService.findStatiPraticaByGruppo("C");
            daEscludere.addAll(chiuse);
            filter.setPraticheDaEscludere(daEscludere);
        } else if (filter.getDesStatoPratica() != null && filter.getDesStatoPratica().equals("CHIUSE")) {
            //Escludo tutte le pratiche diverse da chiusa
            List<LkStatoPratica> daEscludere = new ArrayList<LkStatoPratica>();
            List<LkStatoPratica> toExclude = lookupService.findStatoPraticaByGruppoDiversoDa("C");
            daEscludere.addAll(toExclude);
            filter.setPraticheDaEscludere(daEscludere);
        } else if (filter.getDesStatoPratica() != null && filter.getDesStatoPratica().equals("ALL")) {
            //Escludo sempre le pratiche ricevute per 2 motivi:
            //1. performance: devono andarsi a leggere l'xml pratica cross per trovare il richiedente.
            //2. c'è la sezione dedicata.
        	//SAL3
//            List<LkStatoPratica> daEscludere = new ArrayList<LkStatoPratica>();
//            LkStatoPratica ricevuta = lookupService.findStatoPraticaByCodice("R");
//            daEscludere.add(ricevuta);
//            filter.setPraticheDaEscludere(daEscludere);
        }else if (filter.getDesStatoPratica() == null && filter.getIdStatoPratica() == null) {
        	filter.setSearchByUtenteConnesso(true);
        }
        request.getSession().setAttribute("praticheGestisci", filter);
        Long countRighe = praticheService.countFiltrate(filter);
        List<PraticaNuova> pratiche = praticheService.findFiltrate(filter);
        Integer totalRecords = countRighe.intValue();
        totalRecords = (totalRecords / maxResult == 0) ? 1 : ((totalRecords % maxResult == 0) ? (totalRecords / maxResult) : (totalRecords / maxResult + 1));
        json.setPage(page);
        json.setRecords(countRighe.intValue());
        json.setTotal(totalRecords);
        json.setRows(pratiche);
        return json;
    }

    @Deprecated
    @Transactional
    public void setProtocolloResponsabileProcedimento(Integer idPratica, String protocollo, String responsabileProcedimento) throws Exception {
        if (idPratica != null) {
            Pratica pratica = praticheService.getPratica(idPratica);
            pratica.setResponsabileProcedimento(responsabileProcedimento);
            if (!Utils.e(protocollo)) {
                String[] protoSplit = protocollo.split("/");
                if (protoSplit != null && protoSplit.length == 3) {
                    if (Utils.isInteger(protoSplit[1])) {
                        pratica.setCodRegistro(protoSplit[0]);
                        pratica.setAnnoRiferimento(Integer.valueOf(protoSplit[1]));
                        pratica.setProtocollo(protoSplit[2]);
                    }
                }
            }
            aggiornaPratica(pratica);
        }
    }

    @Transactional
    public it.wego.cross.entity.Pratica aggiornaPratica(PraticaDTO praticaDTO, LkStatoPratica lkStatoPratica) throws Exception {
        Pratica praticaEntity = praticaDao.findPratica(praticaDTO.getIdPratica());
        String xmlPratica = new String(praticaEntity.getIdStaging().getXmlPratica());
        it.wego.cross.xml.Pratica praticaXML = PraticaUtils.getPraticaFromXML(xmlPratica);

        Integer idPratica = praticaDTO.getIdPratica();
        //e' gia salvata
        //DATICATASTALI 
        List<DatiCatastali> datiCatastali = praticaEntity.getDatiCatastaliList();
        usefulService.flush();
        if (praticaXML.getDatiCatastali() != null && praticaXML.getDatiCatastali().getImmobile() != null) {
            for (it.wego.cross.xml.Immobile immobile : praticaXML.getDatiCatastali().getImmobile()) {
                if (immobile.getIdImmobile() == null) {
                    DatiCatastali immobileEntity = new DatiCatastali();
                    datiCatastaliSerializer.serialize(immobile, immobileEntity);
                    immobileEntity.setIdPratica(praticaEntity);
                    immobileEntity.setIdTipoSistemaCatastale(lookupDao.findIdTipoCatastaleById(Utils.ib(immobile.getIdTipoSistemaCatastale())));
                    if (immobile.getIdTipoUnita() != null) {
                        immobileEntity.setIdTipoUnita(lookupDao.findTipoUnitaById(Utils.ib(immobile.getIdTipoUnita())));
                    }
                    if (immobile.getIdTipoParticella() != null) {
                        immobileEntity.setIdTipoParticella(lookupDao.findTipoParticellaById(Utils.ib(immobile.getIdTipoParticella())));
                    }
                    datiCatastali.add(immobileEntity);
                    usefulService.flush();
                    immobile.setIdImmobile(BigInteger.valueOf(immobileEntity.getIdImmobile().intValue()));
                }
            }
        }
        praticaEntity.setDatiCatastaliList(datiCatastali);
        List<IndirizziIntervento> datiIndirizziIntervento = praticaEntity.getIndirizziInterventoList();
        if (praticaXML.getIndirizziIntervento() != null && praticaXML.getIndirizziIntervento().getIndirizzoIntervento() != null) {
            for (it.wego.cross.xml.IndirizzoIntervento indirizzoIntervento : praticaXML.getIndirizziIntervento().getIndirizzoIntervento()) {
                if (indirizzoIntervento.getIdIndirizzoIntervento() == null) {
                    if (indirizzoIntervento.getIndirizzo() != null) {
                        IndirizziIntervento indirizzoInterventoEntity = mapper.map(indirizzoIntervento, it.wego.cross.entity.IndirizziIntervento.class);
                        indirizzoInterventoEntity.setIdPratica(praticaEntity);

                        if (indirizzoIntervento.getIdDug() != null) {
                            indirizzoInterventoEntity.setIdDug(lookupDao.findDugById(Utils.ib(indirizzoIntervento.getIdDug())));
                        }
                        datiIndirizziIntervento.add(indirizzoInterventoEntity);
                        usefulService.flush();
                        indirizzoIntervento.setIdIndirizzoIntervento(BigInteger.valueOf(indirizzoInterventoEntity.getIdIndirizzoIntervento().intValue()));
                    }
                }
            }
        }
        praticaEntity.setIndirizziInterventoList(datiIndirizziIntervento);
        usefulService.flush();
        if (praticaEntity.getPraticaAnagraficaList() != null && praticaEntity.getPraticaAnagraficaList().size() > 0) {
            /**
             * Nella logica attuale non ce da aggiornare nessuna anagrafica. in
             * quanto non si puo procedere senza aver precedentemente confermato
             * tutte le anagrafiche e quindi i recapiti.
             */
        } else {
            praticaEntity.setPraticaAnagraficaList(new ArrayList<it.wego.cross.entity.PraticaAnagrafica>());
            for (it.wego.cross.xml.Anagrafiche anagraficheXML : praticaXML.getAnagrafiche()) {
                it.wego.cross.entity.PraticaAnagrafica praAngraficaEntity = new it.wego.cross.entity.PraticaAnagrafica();
                it.wego.cross.entity.Anagrafica angraficaEntity = anagraficheDao.findById(anagraficheXML.getAnagrafica().getIdAnagrafica().intValue());
                praAngraficaEntity.setPratica(praticaEntity);
                praAngraficaEntity.setAnagrafica(angraficaEntity);
                praAngraficaEntity.setPratica(praticaEntity);
                if (!anagraficheXML.getDesTipoRuolo().equalsIgnoreCase("RICHIEDENTE")
                        && anagraficheXML.getAnagrafica().getFlgIndividuale() != null && anagraficheXML.getAnagrafica().getFlgIndividuale().equals("S")) {
                    praAngraficaEntity.setFlgDittaIndividuale("S");
                } else {
                    praAngraficaEntity.setFlgDittaIndividuale("N");
                }
                if (angraficaEntity.getAnagraficaRecapitiList() != null) {
                    Log.XML.info("Ci sono " + angraficaEntity.getAnagraficaRecapitiList().size() + " recapiti");
                    for (AnagraficaRecapiti recapito : angraficaEntity.getAnagraficaRecapitiList()) {
                        if (recapito.getIdTipoIndirizzo() != null && recapito.getIdTipoIndirizzo().getDescrizione().equals(Constants.INDIRIZZO_NOTIFICA)) {
                            praAngraficaEntity.setIdRecapitoNotifica(recapito.getIdRecapito());
                            break;
                        }
                    }
                }
                if (anagraficheXML.getIdTipoQualifica() != null) {
                    praAngraficaEntity.setIdTipoQualifica(lookupDao.findTipoQualificaById(Utils.ib(anagraficheXML.getIdTipoQualifica())));
                }
                praAngraficaEntity.setDescrizioneTitolarita(anagraficheXML.getDescrizioneTitolarita());
                praAngraficaEntity.setAssensoUsoPec(Utils.flag(anagraficheXML.isAssensoUsoPec()));

                if (anagraficheXML.getIdTipoQualifica() != null) {
                    LkTipoQualifica tipoQualifica = lookupDao.findTipoQualificaById(anagraficheXML.getIdTipoQualifica().intValue());
                    praAngraficaEntity.setIdTipoQualifica(tipoQualifica);
                }
                LkTipoRuolo tipoRuolo = lookupDao.findTipoRuoloById(anagraficheXML.getIdTipoRuolo().intValue());
                Log.XML.debug("Tipo ruolo " + tipoRuolo.getDescrizione());
                praAngraficaEntity.setLkTipoRuolo(tipoRuolo);

                praAngraficaEntity.setDataInizioValidita(new Date());
                praAngraficaEntity.setPec(anagraficheXML.getPec());

                it.wego.cross.entity.PraticaAnagraficaPK pk = new it.wego.cross.entity.PraticaAnagraficaPK();
                pk.setIdAnagrafica(angraficaEntity.getIdAnagrafica());
                pk.setIdPratica(praticaEntity.getIdPratica());
                pk.setIdTipoRuolo(tipoRuolo.getIdTipoRuolo());
                praAngraficaEntity.setPraticaAnagraficaPK(pk);
                Log.APP.info("Inserisco l'anagrafica " + pk.getIdAnagrafica() + " per la pratica " + pk.getIdPratica() + " con ruolo " + pk.getIdTipoRuolo());
                praticaEntity.getPraticaAnagraficaList().add(praAngraficaEntity);
                lookupDao.insert(praAngraficaEntity);
                usefulService.flush();
            }
        }

        List<it.wego.cross.entity.PraticaProcedimenti> procedimentiEntity = praticaEntity.getPraticaProcedimentiList();
        if (procedimentiEntity == null || procedimentiEntity.isEmpty()) {
            procedimentiEntity = new ArrayList<it.wego.cross.entity.PraticaProcedimenti>();

            for (it.wego.cross.xml.Procedimento procedimento : praticaXML.getProcedimenti().getProcedimento()) {
                it.wego.cross.entity.Procedimenti procedimentoEntity = procedimentiDao.findProcedimentoByCodProc(procedimento.getCodProcedimento());
                it.wego.cross.entity.PraticaProcedimenti praproce = praticaDao.findPraticaProcedimenti(idPratica.intValue(), procedimentoEntity.getIdProc());
                if (praproce == null) {
                    praproce = new it.wego.cross.entity.PraticaProcedimenti();
                    praproce.setProcedimenti(procedimentoEntity);
                    it.wego.cross.entity.PraticaProcedimentiPK propk = new it.wego.cross.entity.PraticaProcedimentiPK();
                    propk.setIdEnte(praproce.getEnti().getIdEnte());
                    propk.setIdPratica(idPratica);
                    propk.setIdProcedimento(procedimentoEntity.getIdProc());
                    praticaDao.insert(praproce);
                    usefulService.flush();
                    usefulService.refresh(praproce);
                    praproce.setPraticaProcedimentiPK(propk);
                }
                procedimentiEntity.add(praproce);
            }
            praticaEntity.setPraticaProcedimentiList(procedimentiEntity);
        }
        Log.APP.info("Eseguo l'aggiornamento della pratica " + praticaEntity.getIdentificativoPratica());
        xmlPratica = PraticaUtils.getXmlFromPratica(praticaXML);
        praticaEntity.getIdStaging().setXmlPratica(xmlPratica.getBytes());
        // aggiunto a causa del cambio stato dopo l'ultimo step del processo
        if (lkStatoPratica != null) {
            praticaEntity.setIdStatoPratica(lkStatoPratica);
        }
        praticaDao.update(praticaEntity);

        return praticaEntity;
    }

    @Transactional(rollbackFor = Exception.class)
    public void assegnaUtenteAPratica(Integer idUtente, Integer idPratica, Utente utenteConnesso) throws Exception {
        Pratica pratica = praticaDao.findPratica(idPratica);
        if (pratica != null) {
            Utente utente = utenteDao.findUtenteByIdUtente(idUtente);
            pratica.setIdUtente(utente);

            if (pratica.getIdPraticaPadre() != null) {
                LkStatoPratica statoPratica = lookupDao.findStatoPraticaByCodice(StatoPratica.APERTURA);
                pratica.setIdStatoPratica(statoPratica);
            }
            praticaDao.update(pratica);
            //Crea avento assegnazione pratica
            systemEventAction.assegnaPratica(pratica, utente, utenteConnesso);
            //Riassegna task collegati alla pratica
            //workflowAction.riassegnaTaskAssociatiAllaPratica(idPratica, utente);
        } else {
            throw new Exception("Non è stato possibile associare l'utente alla pratica");
        }
    }

    public GridPraticaNuovaBean getPraticheDaAssegnare(HttpServletRequest request, JqgridPaginator paginator) throws Exception {
        GridPraticaNuovaBean json = new GridPraticaNuovaBean();
        Utente connectedUser = utentiService.getUtenteConnesso(request);
        Integer maxResult = Integer.parseInt(paginator.getRows());
        Integer page = Integer.parseInt(paginator.getPage());
        String column = paginator.getSidx();
        String order = paginator.getSord();
        Integer firstRecord = (page * maxResult) - maxResult;
        Filter filter = filterSerializer.getSearchFilter(request);
        filter.setPage(page);
        filter.setLimit(maxResult);
        filter.setOffset(firstRecord);
        filter.setOrderColumn(column);
        filter.setOrderDirection(order);
        filter.setConnectedUser(connectedUser);
        filter.setSearchByUtenteConnesso(false);
        request.getSession().setAttribute("praticheNuoveDaAssegnare", filter);
        //Filtra solo pratiche RICEVUTE
        LkStatoPratica lookup = praticheService.findLookupStatoPratica(StatoPratica.RICEVUTA);
        filter.setIdStatoPratica(lookup);
        Long countRighe = praticheService.countDaAssegnare(filter);
        List<PraticaNuova> pratiche = praticheService.findDaAssegnare(filter);
        Integer totalRecords = countRighe.intValue();
        totalRecords = (totalRecords / maxResult == 0) ? 1 : ((totalRecords % maxResult == 0) ? (totalRecords / maxResult) : (totalRecords / maxResult + 1));
        json.setPage(page);
        json.setRecords(countRighe.intValue());
        json.setTotal(totalRecords);
        json.setRows(pratiche);
        return json;
    }

    public GridAssociaUtentiBean getUtentiDaAssociareAllaPratica(HttpServletRequest request, JqgridPaginator paginator) throws Exception {
        GridAssociaUtentiBean json = new GridAssociaUtentiBean();
        Integer idPratica = (Integer) request.getSession().getAttribute("id_pratica");
        Integer maxResult = Integer.parseInt(paginator.getRows());
        Integer page = Integer.parseInt(paginator.getPage());
        String column = paginator.getSidx();
        String order = paginator.getSord();
        Integer firstRecord = (page * maxResult) - maxResult;
        Filter filter = filterSerializer.getSearchFilter(request);
        filter.setLimit(maxResult);
        filter.setOffset(firstRecord);
        filter.setOrderColumn(column);
        filter.setOrderDirection(order);
        Long countRighe = utentiService.countDaAssegnarePratica(idPratica, filter);
        List<UtenteDTO> utenti = utentiService.findDaAssegnarePratica(idPratica, filter);
        Integer totalRecords = countRighe.intValue();
        totalRecords = (totalRecords / maxResult == 0) ? 1 : ((totalRecords % maxResult == 0) ? (totalRecords / maxResult) : (totalRecords / maxResult + 1));
        json.setPage(page);
        json.setRecords(countRighe.intValue());
        json.setTotal(totalRecords);
        json.setRows(utenti);
        return json;
    }

    public GridPraticaNuovaBean getElencoPraticheDaAprire(HttpServletRequest request, JqgridPaginator paginator) throws Exception {
        GridPraticaNuovaBean json = new GridPraticaNuovaBean();
        Utente connectedUser = utentiService.getUtenteConnesso(request);
        Integer maxResult = Integer.parseInt(paginator.getRows());
        Integer page = Integer.parseInt(paginator.getPage());
        String column = paginator.getSidx();
        String order = paginator.getSord();
        Integer firstRecord = (page * maxResult) - maxResult;
        Filter filter = filterSerializer.getSearchFilter(request);
        filter.setPage(page);
        filter.setLimit(maxResult);
        filter.setOffset(firstRecord);
        filter.setOrderColumn(column);
        filter.setOrderDirection(order);
        filter.setConnectedUser(connectedUser);
//        filter.setSearchByUtenteConnesso(Boolean.TRUE);
        request.getSession().setAttribute("praticheNuoveApertura", filter);
        //Filtra solo pratiche RICEVUTE
        LkStatoPratica lookup = praticheService.findLookupStatoPratica(StatoPratica.RICEVUTA);
        filter.setIdStatoPratica(lookup);
        Long countRighe = praticheService.countDaAprire(filter);
        List<PraticaNuova> pratiche = praticheService.findDaAprire(filter);
        Integer totalRecords = countRighe.intValue();
        totalRecords = (totalRecords / maxResult == 0) ? 1 : ((totalRecords % maxResult == 0) ? (totalRecords / maxResult) : (totalRecords / maxResult + 1));
        json.setPage(page);
        json.setRecords(countRighe.intValue());
        json.setTotal(totalRecords);
        json.setRows(pratiche);
        return json;
    }

    @Transactional(rollbackFor = Exception.class)
    public void salvaNotificaPratica(PraticaDTO praticaDTO) throws Exception {
        Log.APP.info("Salva Notifica Pratica: idPratica (" + praticaDTO.getIdPratica() + ")");
        it.wego.cross.entity.Pratica pratica = praticaDao.findPratica(praticaDTO.getIdPratica());
        String xmlPratica = new String(pratica.getIdStaging().getXmlPratica());
        it.wego.cross.xml.Pratica praticaXML = PraticaUtils.getPraticaFromXML(xmlPratica);
        if (pratica.getIdRecapito() == null) {
            pratica.setIdRecapito(new it.wego.cross.entity.Recapiti());
        }
        RecapitoUtils.copyRecapito2Entity(praticaDTO.getNotifica(), pratica.getIdRecapito());
        if (pratica.getIdRecapito().getIdRecapito() == null) {
            lookupDao.insert(pratica.getIdRecapito());
            usefulService.flush();
        }
        LkComuni comune = null;
        if (praticaDTO.getNotifica().getIdComune() != null) {
            comune = lookupDao.findComuneById(praticaDTO.getNotifica().getIdComune());
        } else if (praticaDTO.getNotifica().getDescComune() != null && !praticaDTO.getNotifica().getDescComune().equals("")) {
            comune = lookupDao.findComuneByDescrizione(praticaDTO.getNotifica().getDescComune(), new Date());
        }
        pratica.getIdRecapito().setIdComune(comune);
        RecapitoUtils.copyEntity2Recapito(pratica.getIdRecapito(), praticaDTO.getNotifica());
        RecapitoUtils.copyRecapito2XML(praticaDTO.getNotifica(), praticaXML.getNotifica(), null);

        Staging stg = pratica.getIdStaging();
        JAXBContext contextObj = JAXBContext.newInstance(it.wego.cross.xml.Pratica.class);
        Marshaller marshallerObj = contextObj.createMarshaller();
        marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter praticaSTR = new StringWriter();
        marshallerObj.marshal(praticaXML, praticaSTR);
        stg.setXmlPratica(praticaSTR.toString().getBytes());
        stagingDao.merge(stg);
        usefulService.flush();
        praticaDao.update(pratica);
        usefulService.flush();
    }

    public GridDatoCatastaleBean findDatoCatastaleDaXML(DatiCatastaliDTO datiCatastaliDTO) throws Exception {
        Pratica praticaEnitity = praticaDao.findPratica(Integer.valueOf(datiCatastaliDTO.getIdPratica()));
        String xmlPratica = new String(praticaEnitity.getIdStaging().getXmlPratica());
        it.wego.cross.xml.Pratica praticaXML = PraticaUtils.getPraticaFromXML(xmlPratica);
        it.wego.cross.xml.Immobile datiXML = datiCatastaliService.trovaStrutturaXmlPerIdImmobile(datiCatastaliDTO.getIdImmobile(), praticaXML.getDatiCatastali());
        if (datiXML != null) {
            Log.APP.info("Modifico l'immobile per idImmobile" + datiXML.getIdImmobile());
        } else {
            // non trovato per idImmobile, lo cerco per counter
            datiXML = datiCatastaliService.trovaStrutturaXmlPerCounter(datiCatastaliDTO.getCounter(), praticaXML.getDatiCatastali());
            if (datiXML != null) {
                Log.APP.info("Modifico l'immobile per counter" + datiXML.getIdImmobile());
            }
        }
        if (datiXML != null) {
            datiCatastaliSerializer.serialize(praticaEnitity, datiXML, datiCatastaliDTO);
        }
        GridDatoCatastaleBean json = new GridDatoCatastaleBean();
        json.setDatoCatastale(datiCatastaliDTO);
        return json;
    }

    public GridDatoCatastaleBean findDatoCatastaleDaBancaDati(Integer idImmobile) throws Exception {
        DatiCatastali dato = praticaDao.findDatiCatastali(idImmobile);
        DatiCatastaliDTO datiCatastaliDTO = mapper.map(dato, DatiCatastaliDTO.class);
        datiCatastaliDTO.setIdPratica(dato.getIdPratica().getIdPratica());
        if (dato.getIdTipoSistemaCatastale() != null) {
            datiCatastaliDTO.setIdTipoSistemaCatastale(dato.getIdTipoSistemaCatastale().getIdTipoSistemaCatastale());
            datiCatastaliDTO.setDesSistemaCatastale(dato.getIdTipoSistemaCatastale().getDescrizione());
        }
        if (dato.getIdTipoUnita() != null) {
            datiCatastaliDTO.setIdTipoUnita(dato.getIdTipoUnita().getIdTipoUnita());
            datiCatastaliDTO.setDesTipoUnita(dato.getIdTipoUnita().getDescrizione());
        }
        if (dato.getIdTipoParticella() != null) {
            datiCatastaliDTO.setIdTipoParticella(dato.getIdTipoParticella().getIdTipoParticella());
            datiCatastaliDTO.setDesTipoParticella(dato.getIdTipoParticella().getDescrizione());
        }
        GestionePratica gp = pluginService.getGestionePratica(dato.getIdPratica().getIdProcEnte().getIdEnte().getIdEnte());
        datiCatastaliDTO.setUrlCatasto(gp.getUrlCatasto(dato, dato.getIdPratica()));
        GridDatoCatastaleBean json = new GridDatoCatastaleBean();
        json.setDatoCatastale(datiCatastaliDTO);
        return json;
    }

    @Transactional(rollbackFor = Exception.class)
    public GridDatoCatastaleBean eliminaDatoCatastale(Integer idImmobile) throws Exception {
        GridDatoCatastaleBean json = new GridDatoCatastaleBean();
        it.wego.cross.entity.DatiCatastali datiDB = praticaDao.findDatiCatastali(idImmobile);
        praticaDao.eliminaDatiCatastali(datiDB);
        //Logica di salvataggio non eliminare
        json.setMessages(Arrays.asList("Dato Catastale eliminato correttamente."));
        return json;
    }

    @Transactional(rollbackFor = Exception.class)
    public void salvaStaging(Staging staging) throws Exception {
        try {
            stagingDao.insert(staging);
        } catch (Exception ex) {
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_POPOLAMENTO_STAGING, "Errore in fase di popolamento dell'area di staging", ex, null, null);
            erroriAction.saveError(errore);
            throw new Exception(ex);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateStaging(Staging staging, Pratica pratica) throws Exception {
        try {
            stagingDao.merge(staging);
        } catch (Exception ex) {
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_POPOLAMENTO_STAGING, "Errore in fase di aggiornamento dell'area di staging", ex, pratica, null);
            erroriAction.saveError(errore);
            throw new Exception(ex);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void salvaPratica(Pratica pratica) throws Exception {
        praticaDao.insert(pratica);
        usefulService.flush();
    }

    @Transactional(rollbackFor = Exception.class)
    public void popolaProcedimenti(Pratica pratica, it.wego.cross.xml.Pratica praticaCross) throws Exception {
        List<it.wego.cross.xml.Procedimento> procedimentiPratica = praticaCross.getProcedimenti().getProcedimento();
        List<PraticaProcedimenti> praticaProcedimentiList = new ArrayList<PraticaProcedimenti>();
        for (it.wego.cross.xml.Procedimento procedimento : procedimentiPratica) {
            PraticaProcedimenti praticaProcedimenti = new PraticaProcedimenti();
            //Presuppongo che coincida con l'ID inserito nell'anagrafica enti di CROSS
            Enti ente = entiDao.findByCodEnte(procedimento.getCodEnteDestinatario());
            if(ente == null)
            	ente = entiDao.findByIdEnte(procedimento.getIdEnteDestinatario().intValue());
            PraticaProcedimentiPK key = new PraticaProcedimentiPK();
            key.setIdEnte(ente.getIdEnte());
            key.setIdPratica(pratica.getIdPratica());
            Procedimenti proc = procedimentiService.getProcedimento(procedimento.getCodProcedimento());
            if (proc == null) {
                Log.WS.warn("ATTENZIONE! Procedimento con codice '" + procedimento.getCodProcedimento() + "' non trovato.");
            }
            key.setIdProcedimento(proc.getIdProc());
            praticaProcedimenti.setPraticaProcedimentiPK(key);
            praticaDao.insert(praticaProcedimenti);
            usefulService.flush();
            usefulService.refresh(praticaProcedimenti);
            praticaProcedimentiList.add(praticaProcedimenti);
        }
        pratica.setPraticaProcedimentiList(praticaProcedimentiList);
        praticaDao.update(pratica);
    }
    
   

    @Transactional(rollbackFor = Exception.class)
    public ComunicazioneBean inserisciEventoRicezione(it.wego.cross.xml.Pratica praticaCross, Pratica pratica, ProcessiEventi eventoProcesso, List<String> destinatariEmail, List<AllegatoRicezioneDTO> allegati) throws Exception {
        try {
            String ricTuttiAllegati = configuration.getCachedConfiguration(SessionConstants.EVENTO_RICEZIONE_ALLEGA_TUTTO, pratica.getIdProcEnte().getIdEnte().getIdEnte(), pratica.getIdComune().getIdComune());

            ComunicazioneBean cb = new ComunicazioneBean();
            Log.WS.info("Cerco evento di ricezione");
            Log.WS.info("Setto la pratica");
            cb.setIdPratica(pratica.getIdPratica());
            Log.WS.info("Setto l'evento");
            cb.setIdEventoProcesso(eventoProcesso.getIdEvento());
            if (eventoProcesso.getFlgMail().equalsIgnoreCase("S")) {
                Log.WS.info("Deve essere inviata la comunicazione");
                //Non avendo il controllo come da front forzo l'invio della email
                cb.setInviaMail(Boolean.TRUE);
                cb.setNumeroProtocollo(pratica.getProtocollo());
                cb.setDataProtocollo(pratica.getDataProtocollazione());
                Log.WS.info("Setto i destinatari: " + destinatariEmail);
                cb.setDestinatariEmail(destinatariEmail);
            }
            AttoriComunicazione attori = getAttoriComunicazione(pratica.getPraticaAnagraficaList());
            cb.setMittenti(attori);
            cb.setVisibilitaCross(Boolean.TRUE);
            cb.setVisibilitaUtente(Boolean.TRUE);
            AllegatoRicezioneDTO allegatoPratica = new AllegatoRicezioneDTO();
            if (allegati != null && allegati.size() > 0) {
                for (AllegatoRicezioneDTO allegato : allegati) {
                    Log.WS.info("Aggiungo il file " + allegato.getAllegato().getNomeFile() + " all'evento");
                    Allegato a = AllegatiSerializer.serializeAllegato(allegato);
                    if (eventoProcesso.getFlgProtocollazione().equalsIgnoreCase("S")) {
                        a.setProtocolla(Boolean.TRUE);
                    } else {
                        a.setProtocolla(Boolean.FALSE);
                    }

                    if (allegato.isModelloDomanda()) {
                        allegatoPratica = allegato;
                        a.setFileOrigine(Boolean.TRUE);
                        a.setSpedisci(Boolean.TRUE);
                    } else {
                        if ("TRUE".equalsIgnoreCase(ricTuttiAllegati)) {
                            a.setSpedisci(Boolean.TRUE);
                        } else {
                            a.setSpedisci(Boolean.FALSE);
                        }
                    }

                    cb.addAllegato(a);
                }
            }
            cb.setOggettoProtocollo(pratica.getOggettoPratica());
            aggiornaPratica(pratica);
            Log.WS.info("Gestisco l'evento");
            workFlowService.gestisciProcessoEvento(cb);
            Log.WS.info("Salvo il modello della pratica");
            for (Integer idAllegato : cb.getIdAllegati()) {
                Allegati allegato = allegatiService.findAllegatoById(idAllegato);
                if (allegato != null && allegatoPratica.getAllegato() != null && allegato.getNomeFile().equals(allegatoPratica.getAllegato().getNomeFile())) {
                    pratica.setIdModello(allegato);
                    praticaDao.update(pratica);
                    usefulService.flush();
                    break;
                }
            }

            return cb;

        } catch (Exception ex) {
            Log.APP.error("Si è verificato un errore cercando di inserire l'evento", ex);
            throw ex;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void aggiornaPratica(Pratica pratica) throws Exception {
        Log.WS.info("Aggiorno la pratica");
        praticaDao.update(pratica);
    }

    public GridIndirizziInterventoBean ricercaToponomastica(IndirizzoInterventoDTO indirizzoInterventoDTO) throws Exception {
        GridIndirizziInterventoBean ret = new GridIndirizziInterventoBean();
        Pratica pratica = praticheService.getPratica(indirizzoInterventoDTO.getIdPratica());
        GestionePratica gp = pluginService.getGestionePratica(pratica.getIdProcEnte().getIdEnte().getIdEnte());
        List<IndirizzoInterventoDTO> datiToponomastici = gp.getDatiToponomastica(indirizzoInterventoDTO, pratica);
        ret.setRows(datiToponomastici);
        ret.setTotal(datiToponomastici.size());
        return ret;
    }

    public GridDatiCatastaliBean ricercaCatasto(DatiCatastaliDTO datiCatastaliDTO) throws Exception {
        GridDatiCatastaliBean ret = new GridDatiCatastaliBean();
        Pratica pratica = praticheService.getPratica(datiCatastaliDTO.getIdPratica());
        GestionePratica gp = pluginService.getGestionePratica(pratica.getIdProcEnte().getIdEnte().getIdEnte());
        List<DatiCatastaliDTO> datiCatastali = gp.getDatiCatastali(datiCatastaliDTO, pratica);
        ret.setRows(datiCatastali);
        ret.setTotal(datiCatastali.size());
        return ret;
    }

    public GridIndirizzoInterventoBean findIndirizzoIntervento(Integer idIndirizzoIntervento) throws Exception {
        IndirizziIntervento indirizzo = praticaDao.findIndirizziInterventoById(idIndirizzoIntervento);
        IndirizzoInterventoDTO indirizzoInterventoDTO = mapper.map(indirizzo, IndirizzoInterventoDTO.class);
        indirizzoInterventoDTO.setIdPratica(indirizzo.getIdPratica().getIdPratica());
        if (indirizzo.getIdDug() != null) {
            indirizzoInterventoDTO.setIdDug(indirizzo.getIdDug().getIdDug());
            indirizzoInterventoDTO.setDesDug(indirizzo.getIdDug().getDescrizione());
        }
        GestionePratica gp = pluginService.getGestionePratica(indirizzo.getIdPratica().getIdProcEnte().getIdEnte().getIdEnte());
        indirizzoInterventoDTO.setUrlCatasto(gp.getUrlCatastoIndirizzo(indirizzo, indirizzo.getIdPratica()));
        GridIndirizzoInterventoBean json = new GridIndirizzoInterventoBean();
        json.setIndirizzo(indirizzoInterventoDTO);
        return json;
    }

    public GridIndirizzoInterventoBean findIndirizzoInterventoDaXML(IndirizzoInterventoDTO indirizzoInterventoDTO) throws Exception {
        Pratica praticaEnitity = praticaDao.findPratica(indirizzoInterventoDTO.getIdPratica());
        String xmlPratica = new String(praticaEnitity.getIdStaging().getXmlPratica());
        it.wego.cross.xml.Pratica praticaXML = PraticaUtils.getPraticaFromXML(xmlPratica);
        it.wego.cross.xml.IndirizzoIntervento datiXML = indirizziInterventoService.trovaStrutturaXmlPerIdIndirizzoIntervento(indirizzoInterventoDTO.getIdIndirizzoIntervento(), praticaXML.getIndirizziIntervento());
        if (datiXML != null) {
            Log.APP.info("Modifico l'immobile per idImmobile" + datiXML.getIdIndirizzoIntervento());
        } else {
            // non trovato per idImmobile, lo cerco per counter
            datiXML = indirizziInterventoService.trovaStrutturaXmlPerCounter(indirizzoInterventoDTO.getCounter(), praticaXML.getIndirizziIntervento());
            if (datiXML != null) {
                Log.APP.info("Modifico l'immobile per counter" + datiXML.getIdIndirizzoIntervento());
            }
        }
        if (datiXML != null) {
            datiCatastaliSerializer.serialize(praticaEnitity, datiXML, indirizzoInterventoDTO);
        }
        GridIndirizzoInterventoBean json = new GridIndirizzoInterventoBean();
        json.setIndirizzo(indirizzoInterventoDTO);
        return json;
    }

    @Transactional(rollbackFor = Exception.class)
    public void insertAnagrafiche(List<Anagrafiche> anagrafiche, Pratica pratica) throws Exception {
        List<PraticaAnagrafica> anagraficheInserite = new ArrayList<PraticaAnagrafica>();
        for (Anagrafiche anagrafica : anagrafiche) {
            PraticaAnagrafica pa = anagraficheSerializer.serialize(anagrafica, pratica);
            if (!anagraficheInserite.contains(pa)) {
                praticaDao.insert(pa);
                anagrafica.getAnagrafica().setIdAnagrafica(Utils.bi(pa.getAnagrafica().getIdAnagrafica()));
                pratica.getPraticaAnagraficaList().add(pa);
                anagraficheInserite.add(pa);
            }
        }
    }

    public AttoriComunicazione getAttoriComunicazione(List<PraticaAnagrafica> praticaAnagraficaList) throws Exception {

        List<Anagrafica> anagraficaList = new ArrayList<Anagrafica>();
        for (PraticaAnagrafica praticaAnagrafica : praticaAnagraficaList) {
            Anagrafica anagrafica = anagraficheDao.findById(praticaAnagrafica.getPraticaAnagraficaPK().getIdAnagrafica());
            if (!anagraficaList.contains(anagrafica)) {
                anagraficaList.add(anagrafica);
            }
        }

        AttoriComunicazione attoriComunicazione = new AttoriComunicazione();

        for (Anagrafica anagrafica : anagraficaList) {
        	if(anagrafica!=null) {
	            AnagraficaRecapiti anagraficaRecapitoRiferimentoAnagrafica = anagraficheService.getAnagraficaRecapitoRiferimentoAnagrafica(anagrafica);
	//            AnagraficaRecapito attoreComunicazione = new AnagraficaRecapito(anagraficaRecapitoRiferimentoAnagrafica.getIdAnagrafica(), anagraficaRecapitoRiferimentoAnagrafica.getIdRecapito());
	            if(anagraficaRecapitoRiferimentoAnagrafica!=null)
	            	attoriComunicazione.addAnagrafica(anagraficaRecapitoRiferimentoAnagrafica.getIdAnagraficaRecapito());
        	}
//            AnagraficaRecapiti anagraficaRecapitoRiferimentoAnagrafica = anagraficheService.getAnagraficaRecapitoRiferimentoAnagrafica(anagrafica);
////            AnagraficaRecapito attoreComunicazione = new AnagraficaRecapito(anagraficaRecapitoRiferimentoAnagrafica.getIdAnagrafica(), anagraficaRecapitoRiferimentoAnagrafica.getIdRecapito());
//            attoriComunicazione.addAnagrafica(anagraficaRecapitoRiferimentoAnagrafica.getIdAnagraficaRecapito());
        }

        if (CollectionUtils.isNotEmpty(praticaAnagraficaList) && praticaAnagraficaList.get(0).getPraticaAnagraficaPK() != null) {
            Integer idPratica = praticaAnagraficaList.get(0).getPraticaAnagraficaPK().getIdPratica();
            Pratica pratica = praticheService.getPratica(idPratica);
            if (pratica.getIdRecapito() != null) {
                attoriComunicazione.setIdRecapitoNotifica(pratica.getIdRecapito().getIdRecapito());
            }
        }

        return attoriComunicazione;
    }

    @Transactional(rollbackFor = Exception.class)
    public void scollegaPratica(Integer idPratica, Integer idPraticaCollegata, Utente utenteConnesso) throws Exception {
        praticaDao.scollegaPratica(idPratica, idPraticaCollegata);
        praticaDao.scollegaPratica(idPraticaCollegata, idPratica);
        Pratica praticaOrigine = praticaDao.findPratica(idPratica);
        Pratica praticaCollegata = praticaDao.findPratica(idPraticaCollegata);
        systemEventAction.scollegaPratica(praticaOrigine, praticaCollegata, utenteConnesso);
        systemEventAction.scollegaPratica(praticaCollegata, praticaOrigine, utenteConnesso);
    }

    @Transactional(rollbackFor = Exception.class)
    public void collegaPratica(Integer idPratica, Integer idPraticaCollegata, Utente utenteConnesso) throws Exception {
        praticaDao.collegaPratica(idPratica, idPraticaCollegata);
        praticaDao.collegaPratica(idPraticaCollegata, idPratica);
        Pratica praticaOrigine = praticaDao.findPratica(idPratica);
        Pratica praticaCollegata = praticaDao.findPratica(idPraticaCollegata);
        systemEventAction.collegaPratica(praticaOrigine, praticaCollegata, utenteConnesso);
        systemEventAction.collegaPratica(praticaCollegata, praticaOrigine, utenteConnesso);
    }

    public static enum AttributiPratica {

        PRATICA_IDENTIFICATIVO, PRATICA_OGGETTO, PRATICA_PROCEDIMENTO, PRATICA_RICEZIONE_DATA, PRATICA_PROTOCOLLO_SEGNATURA, PRATICA_PROTOCOLLO_DATA,
        PRATICA_STATO, PRATICA_CHIUSURA_DATA, PRATICA_RESPONSABILE_ISTRUTTORIA, PRATICA_RESPONSABILE_PROCEDIMENTO
    }

    public static enum AttributiPraticaEvento {

        EVENTO_PROTOCOLLO, EVENTO_DATA_PROTOCOLLO, EVENTO_NOTE
    }

    @Transactional(rollbackFor = Exception.class)
    public void aggiornaAttributiPratica(Integer idPratica, AttributiPratica attributo, String value) throws Exception {
        Pratica pratica = praticheService.getPratica(idPratica);
        switch (attributo) {
            case PRATICA_IDENTIFICATIVO:
                pratica.setIdentificativoPratica(value);
                break;
            case PRATICA_OGGETTO:
                pratica.setOggettoPratica(value);
                break;
            case PRATICA_PROCEDIMENTO:
                //pratica.setOggettoPratica(value);
                break;
            case PRATICA_RICEZIONE_DATA:
                pratica.setDataRicezione(Utils.italianFormatToDate(value));
                break;
            case PRATICA_PROTOCOLLO_SEGNATURA:
                String[] protoSplit = value.split("/");
                if (protoSplit != null && protoSplit.length == 3) {
                    if (Utils.isInteger(protoSplit[1])) {
                        pratica.setCodRegistro(protoSplit[0]);
                        pratica.setAnnoRiferimento(Integer.valueOf(protoSplit[1]));
                        pratica.setProtocollo(protoSplit[2]);
                    }
                } else {
                    throw new CrossException("Segnatura di protocollo non valida");
                }
                //pratica.setOggettoPratica(value);
                break;
            case PRATICA_PROTOCOLLO_DATA:
                pratica.setDataProtocollazione(Utils.italianFormatToDate(value));
                break;
            case PRATICA_STATO:
                LkStatoPratica statoPratica = lookupDao.findLkStatoPraticaByIdStatoPratica(Integer.valueOf(value));
                pratica.setIdStatoPratica(statoPratica);
                if (!statoPratica.getGrpStatoPratica().equals("C")) {
                    pratica.setDataChiusura(null);
                }
                break;
            case PRATICA_CHIUSURA_DATA:
                pratica.setDataChiusura(Utils.italianFormatToDate(value));
                break;
            case PRATICA_RESPONSABILE_ISTRUTTORIA:
                //pratica.setOggettoPratica(value);
                break;
            case PRATICA_RESPONSABILE_PROCEDIMENTO:
                pratica.setResponsabileProcedimento(value);
                break;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void aggiornaAttributiPraticaEvento(Integer idPraticaEvento, AttributiPraticaEvento attributo, String value) throws Exception {
        PraticheEventi evento = praticheService.getPraticaEvento(idPraticaEvento);
        switch (attributo) {
            case EVENTO_PROTOCOLLO:
                if (Strings.isNullOrEmpty(value)) {
                    evento.setProtocollo(value);
                } else {
                    String[] protoSplit = value.split("/");
                    if (protoSplit != null && protoSplit.length == 3) {
                        if (Utils.isInteger(protoSplit[1])) {
                            evento.setProtocollo(value);
                        } else {
                            throw new CrossException("Non è stato inserito un anno valido");
                        }
                    } else {
                        throw new CrossException("Segnatura di protocollo non valida");
                    }
                }
                break;
            case EVENTO_DATA_PROTOCOLLO:
                evento.setDataProtocollo(!Strings.isNullOrEmpty(value) ? Utils.italianFormatToDate(value) : null);
                break;
            case EVENTO_NOTE:
                evento.setNote(value);
                break;
        }
    }

    public GridPraticaEventoBean getPraticaEventoGrid(HttpServletRequest request,
            JqgridPaginator paginator) throws Exception {
        String sord = paginator.getSord();
        Boolean order = true;
        if ("asc".equals(sord)) {
            order = false;
        }
        Integer maxResult = Integer.parseInt(paginator.getRows());
        Integer page = Integer.parseInt(paginator.getPage());
        Integer firstRecord = (page * maxResult) - maxResult;
        GridPraticaEventoBean json = new GridPraticaEventoBean();
        HttpSession session = request.getSession();
    	Integer idPratica = (Integer) session.getAttribute(SessionConstants.ID_PRATICA_SELEZIONATA);
        //Integer idPratica = Integer.parseInt(request.getParameter("idPratica"));
        Pratica pratica = praticheService.getPratica(idPratica);
//        it.wego.cross.dto.dozer.PraticaDTO praticaDTO = mapper.map(pratica, it.wego.cross.dto.dozer.PraticaDTO.class);
//        List<PraticaEventoDTO> praticheEventi = praticaDTO.getPraticheEventiList();

        List<PraticaEventoDTO> praticheEventi = DozerUtils.map(mapper, pratica.getPraticheEventiList(), it.wego.cross.dto.dozer.PraticaEventoDTO.class);
        praticheEventi = orderPraticaEventoByDate(praticheEventi, order);
        Integer countRighe = praticheEventi.size();
        Integer totalRecords = countRighe;
        totalRecords = (totalRecords / maxResult == 0) ? 1 : ((totalRecords % maxResult == 0) ? (totalRecords / maxResult) : (totalRecords / maxResult + 1));
        json.setPage(page);
        json.setRecords(countRighe);
        json.setTotal(totalRecords);
        List<PraticheEventiRidDTO> eventiDaVedere = new ArrayList();
        Integer lastRecord = (firstRecord + maxResult < countRighe) ? firstRecord + maxResult : countRighe;
        for (Integer i = firstRecord; i < lastRecord; i++) {
            eventiDaVedere.add(EventiSerializer.getPraticheEventiRidDaPraticheEventi(praticheEventi.get(i)));
        }

        json.setRows(eventiDaVedere);
        return json;
    }

    private List<PraticaEventoDTO> orderPraticaEventoByDate(List<PraticaEventoDTO> input, Boolean reverse) {
        List<PraticaEventoDTO> listcopy = new ArrayList();
        for (PraticaEventoDTO pe : input) {
            listcopy.add(pe);
        }
        Collections.sort(listcopy, new Comparator<PraticaEventoDTO>() {
            @Override
            public int compare(PraticaEventoDTO pe1, PraticaEventoDTO pe2) {
                Date d1 = pe1.getDataEvento();
                Date d2 = pe2.getDataEvento();
                if (d1.getTime() < d2.getTime()) {
                    return -1;
                } else if (d1.getTime() == d2.getTime()) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });
        if (reverse) {
            return Lists.reverse(listcopy);
        } else {
            return listcopy;
        }
    }

    @Transactional
    public void modifyXmlPratica(String xml, Integer idPratica) throws Exception {
        Pratica pratica = praticaDao.findPratica(idPratica);
        if (pratica.getIdStaging() != null) {
            Staging stg = stagingDao.findByCodStaging(pratica.getIdStaging().getIdStaging());
            it.wego.cross.xml.Pratica praticaXml = PraticaUtils.getPraticaFromXML(xml);
            JAXBContext contextObj = JAXBContext.newInstance(it.wego.cross.xml.Pratica.class);
            Marshaller marshallerObj = contextObj.createMarshaller();
            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter praticaSTR = new StringWriter();
            marshallerObj.marshal(praticaXml, praticaSTR);
            stg.setXmlPratica(praticaSTR.toString().getBytes());
            Log.APP.info("stgdao.merge:");
            stagingDao.merge(stg);
        }
    }

    private void serializzaEntityXmlindirizzoIntervento(it.wego.cross.entity.IndirizziIntervento datiDB, it.wego.cross.xml.IndirizzoIntervento datiXML) {
        mapper.map(datiDB, datiXML);
        if (datiDB.getIdDug() != null) {
            datiXML.setIdDug(Utils.bi(datiDB.getIdDug().getIdDug()));
        }
    }

    @Transactional
    public void protocollaPraticaEvento(DelegateExecution execution) throws Exception {
        Map<String, Object> variables = execution.getVariables();
        Integer idPraticaEvento = (Integer) variables.get("idPraticaEvento");
        PraticheEventi praticaEvento = praticaDao.getDettaglioPraticaEvento(idPraticaEvento);

        if (Utils.e(praticaEvento.getProtocollo())) {
            execution.setVariable("require_manual_protocol", true);
        } else {
            execution.setVariable("require_manual_protocol", false);
        }

        Log.WORKFLOW.debug("Metodo protocollaPraticaEvento chiamato. Evento da protocollare: " + praticaEvento.getDescrizioneEvento() + "(" + praticaEvento.getIdPraticaEvento() + ")");
    }

    @Transactional
    public void aggiornaPraticaEvento(DelegateExecution execution) throws Exception {
        Map<String, Object> variables = execution.getVariables();
        Integer idPraticaEvento = (Integer) variables.get("idPraticaEvento");
        String protocolloSegnatura = (String) variables.get("evento_protocollo_segnatura");
        String protocolloDataString = (String) variables.get("evento_protocollo_data");
        Date protocolloData = Utils.italianFormatToDate(protocolloDataString);
        PraticheEventi praticaEvento = praticaDao.getDettaglioPraticaEvento(idPraticaEvento);
        praticaEvento.setProtocollo(protocolloSegnatura);
        praticaEvento.setDataProtocollo(protocolloData);

        Log.WORKFLOW.debug("Metodo aggiornaPraticaEvento chiamato. Evento da aggiornare: " + praticaEvento.getDescrizioneEvento() + "(" + praticaEvento.getIdPraticaEvento() + ")");
    }

    @Transactional
    public void aggiornaResponsabileProcedimento(PermessiEnteProcedimentoDTO permessiEnteProcedimentoDTO, Enti ente, String azione) throws Exception {
        ProcedimentiEnti procedimentoEnte = procedimentiService.findProcedimentiEnti(ente.getIdEnte(), permessiEnteProcedimentoDTO.getIdProc());
        if (procedimentoEnte != null) {
            procedimentoEnte.setResponsabileProcedimento(permessiEnteProcedimentoDTO.getResponsabileProcedimento());
            procedimentiDao.salvaProcedimentoEnte(procedimentoEnte);
        }
        if ("ancheLePratiche".equals(azione)) {
            praticheService.aggiornaResponsabileProcedimentoSuPratiche(procedimentoEnte);
        }

    }

    public void updateStatoPratica(Pratica pratica, ComunicazioneBean cb) throws Exception {
//        //necessario per aggiornare lo stato della pratica quando passo da attiva a interrotta
        LkStatoPratica praticaInterrotta = lookupDao.findStatoPraticaByCodice(StatoPratica.INTERROTTA);
        List<Scadenze> scadenzeInterruzioneAttive = getScadenzeAttiveByStato(pratica, praticaInterrotta);
        if (scadenzeInterruzioneAttive != null && !scadenzeInterruzioneAttive.isEmpty()) {
            //La pratica deve rimanere interrotta
            pratica.setIdStatoPratica(praticaInterrotta);
        } else {
            LkStatoPratica praticaSospesa = lookupDao.findStatoPraticaByCodice(StatoPratica.SOSPESA);
            List<Scadenze> scadenzeSospensioneAttive = getScadenzeAttiveByStato(pratica, praticaSospesa);
            if (scadenzeSospensioneAttive != null && !scadenzeSospensioneAttive.isEmpty()) {
                //La pratica deve rimanere sospesa
                pratica.setIdStatoPratica(praticaSospesa);
            } else {
                ProcessiEventi evento = praticheService.findProcessiEventi(cb.getIdEventoProcesso());
                LkStatoPratica nuovoStatoPratica = evento.getStatoPost();
                if (nuovoStatoPratica != null) {
                    if (nuovoStatoPratica.getGrpStatoPratica().equalsIgnoreCase(StatoPratica.NON_MODIFICABILE)) {
                        //Non modifico lo stato della pratica
                    } else {
                        pratica.setIdStatoPratica(nuovoStatoPratica);
                        //Se è un evento di chiusura, metto la data di chiusura
                        if (nuovoStatoPratica.getGrpStatoPratica().equalsIgnoreCase(StatoPratica.CHIUSA)) {
                            pratica.setDataChiusura(cb.getDataEvento());
                        }
                    }

                }
            }
        }
        usefulService.flush();
        Utente utente = utentiService.findUtenteByIdUtente(cb.getIdUtente());
        //Se l'utente 
        
        List<ProcessiEventi> processiEventiChiusura = praticheService.getProcessiEventiChiusura(pratica.getIdProcesso());
        ProcessiEventi evento = praticheService.findProcessiEventi(cb.getIdEventoProcesso());
		if(!processiEventiChiusura.isEmpty() && processiEventiChiusura.contains(evento)) {
			Utente utenteArchivio = utentiService.findUtenteDaUsername("cilanongestite");
			if(utenteArchivio != null)
				pratica.setIdUtente(utenteArchivio);
		}
		else {
	        if (utente != null) {
	            pratica.setIdUtente(utente);
	            if (pratica.getIdUtente() != null && !pratica.getIdUtente().equals(utente)) {
	                //L'evento è stato creato da un utente diverso dall'istruttore della pratica
	                workflowAction.riassegnaTaskAssociatiAllaPratica(pratica.getIdPratica(), utente);
	            }
	        }
		}
        praticaDao.update(pratica);
    }

    private List<Scadenze> getScadenzeAttiveByStato(Pratica pratica, LkStatoPratica statoPratica) {
        List<Scadenze> scadenzePratica = pratica.getScadenzeList();
        List<Scadenze> scadenzeAttiveConEventoSospensione = new ArrayList<Scadenze>();
        if (scadenzePratica != null && !scadenzePratica.isEmpty()) {
            for (Scadenze s : scadenzePratica) {
                if (s.getIdStato().getIdStato().equalsIgnoreCase("A")
                        && s.getIdPraticaEvento().getIdEvento().getStatoPost().equals(statoPratica)) {
                    scadenzeAttiveConEventoSospensione.add(s);
                }
            }
        }

        return scadenzeAttiveConEventoSospensione;
    }

    public void updateScadenzePratica(Pratica pratica, ComunicazioneBean cb) throws Exception {
        ProcessiEventi eventoProcesso = praticheService.findProcessiEventi(cb.getIdEventoProcesso());
        // inserito nuovo flg per forzare la chiusura di tutte le scadenze attive (forza_chiusura_scadenze) 
        /*
         * Apertura/chiusura scadenze
         */
        if (!"S".equals(eventoProcesso.getForzaChiusuraScadenze())) {
            if (cb.getIdScadenzeDaChiudere() != null && !cb.getIdScadenzeDaChiudere().isEmpty()) {
                for (Integer idScadenza : cb.getIdScadenzeDaChiudere()) {
                    Scadenze scadenza = praticheService.findScadenzaById(idScadenza);
                    for (Scadenze s : pratica.getScadenzeList()) {
                        if (s.equals(scadenza)) {
                            eseguiChiusuraScadenza(scadenza, pratica, cb);
                        }
                    }
                }
            }
            List<ProcessiEventiScadenze> scadenzeEvento = eventoProcesso.getProcessiEventiScadenzeList();
            if (scadenzeEvento != null && !scadenzeEvento.isEmpty()) {
                for (ProcessiEventiScadenze scadenza : scadenzeEvento) {
                    gestisciScadenza(cb, pratica, scadenza);
                }
            }
            //Esegui idscript su evento
            gestisciIdScriptScadenzaSuEvento(cb, pratica);
        } else {
            List<Scadenze> lista = praticheService.findScadenzaPraticaAttive(pratica);
            LkStatiScadenze chiusura = lookupDao.findStatoScadenzaById("C");
            if (lista != null && !lista.isEmpty()) {
                for (Scadenze scadenza : lista) {
                    scadenza.setDataFineScadenza(cb.getDataEvento());
                    scadenza.setIdStato(chiusura);
                    processiDao.aggiornaScadenza(scadenza);
                    if (isScadenzaDiSospensionePratica(scadenza)) {
                        Log.APP.info("Aggiorno i termini di scadenza della pratica - scadenza di sospensione");
                        aggiornaTerminiScadenzaPraticaPerChiusura(scadenza, pratica);
                    } else if (isScadenzaDiInterruzionePratica(scadenza)) {
                        Log.APP.info("Aggiorno i termini di scadenza della pratica - scadenza di interruzione");
                        aggiornaTerminiScadenzaPraticaPerChiusura(scadenza, pratica);
                    }
                }
            }
        }

    }

    private void eseguiChiusuraScadenza(Scadenze scadenza, Pratica pratica, ComunicazioneBean cb) throws Exception {

        Log.APP.info("Aggiorno i dati relativi alla scadenza con id " + scadenza.getIdScadenza());
        Log.APP.info("Eseguo l'eventuale idscript della scadenza");
        gestisciIdScriptScadenza(cb, scadenza, pratica);

        LkStatiScadenze chiusura = lookupDao.findStatoScadenzaById("C");
        Scadenze daChiudere = null;
        for (Scadenze s : pratica.getScadenzeList()) {
            if (scadenza.equals(s)) {
                daChiudere = s;
                break;
            }
        }
        if (daChiudere != null) {
            Log.APP.info("Setto lo stato a chiusa");
            daChiudere.setIdStato(chiusura);
            Log.APP.info("Imposto come data fine scadenza la data dell'evento");
            daChiudere.setDataFineScadenza(cb.getDataEvento());
            //Non modifico mai la scadenza calcolata in modo da tenere traccia di quale fosse la data di chiusura prevista
            processiDao.aggiornaScadenza(scadenza);
            if (isScadenzaDiSospensionePratica(daChiudere)) {
                Log.APP.info("Aggiorno i termini di scadenza della pratica - scadenza di sospensione");
                aggiornaTerminiScadenzaPraticaPerChiusura(daChiudere, pratica);
            } else if (isScadenzaDiInterruzionePratica(daChiudere)) {
                Log.APP.info("Aggiorno i termini di scadenza della pratica - scadenza di interruzione");
                aggiornaTerminiScadenzaPraticaPerChiusura(daChiudere, pratica);
            }
        }
    }

    private boolean isScadenzaDiSospensionePratica(Scadenze scadenzaPratica) {
        ProcessiEventi evento = scadenzaPratica.getIdPraticaEvento().getIdEvento();
        LkStatoPratica statoPraticaAttivatoDaEvento = evento.getStatoPost();
        return statoPraticaAttivatoDaEvento.getCodice().equalsIgnoreCase("S");
    }

    private void aggiornaTerminiScadenzaPraticaPerChiusura(Scadenze scadenza, Pratica pratica) throws Exception {
        if (pratica.getIdStatoPratica().getCodice().equalsIgnoreCase("I")) {
            aggiornaTerminiSuPraticaSospesa(pratica, scadenza);
        } else if (pratica.getIdStatoPratica().getCodice().equalsIgnoreCase("S")) {
            aggiornaTerminiSuPraticaSospesa(pratica, scadenza);
        }
    }

    private void aggiornaTerminiSuPraticaSospesa(Pratica pratica, Scadenze scadenza) throws Exception {
        Log.APP.info("Calcolo l'intervallo delle scadenze multiple attive sulla pratica");
        PraticaDao.IntervalloScadenzeMultiple intervalloScadenzeMultipleSospensione = praticheService.findIntervalloScadenzeMultipleSospensione(pratica.getIdPratica());
        if (intervalloScadenzeMultipleSospensione != null
                && intervalloScadenzeMultipleSospensione.getDataInizio() != null
                && intervalloScadenzeMultipleSospensione.getDataFine() != null) {
            Date dataInizioScadenzeMultiple = intervalloScadenzeMultipleSospensione.getDataInizio();
            Log.APP.info("Data inizio delle scadenze: " + Utils.dateItalianFormat(dataInizioScadenzeMultiple));
            Date dataFineScadenza = scadenza.getDataFineScadenza();
            Log.APP.info("Data fine della scadenza da chiudere: " + Utils.dateItalianFormat(dataFineScadenza));
            Date dataInizioScadenza = scadenza.getDataInizioScadenza();
            Log.APP.info("Data inizio della scadenza da chiudere: " + Utils.dateItalianFormat(dataInizioScadenza));
            long deltaGiorniScadenza;
            Log.APP.info("Calcolo il delta dei giorni di scadenza");
            if (dataFineScadenza.compareTo(dataInizioScadenzeMultiple) > 0) {
                Log.APP.info("La data fine scadenza è posteriore alla data inizio scadenza minimo nell'intervallo calcolato");
                deltaGiorniScadenza = Utils.getDaysBetweenDates(dataInizioScadenza, dataInizioScadenzeMultiple);
                if (deltaGiorniScadenza < 0) {
                    Log.APP.info("Il delta è negativo, forzo a 0");
                    deltaGiorniScadenza = 0;
                }
                Log.APP.info("Giorni scadenza: " + deltaGiorniScadenza);
            } else {
                deltaGiorniScadenza = Utils.getDaysBetweenDates(dataInizioScadenza, dataFineScadenza);
                if (deltaGiorniScadenza < 0) {
                    Log.APP.info("Il delta è negativo, forzo a 0");
                    deltaGiorniScadenza = 0;
                }
                Log.APP.info("Giorni scadenza: " + deltaGiorniScadenza);
            }
            Log.APP.info("Recupero i giorni di sospensione salvati sulla pratica e calcolo il nuovo valore");
            int giorniSospensionePratica = pratica.getGiorniSospensione() + (int) deltaGiorniScadenza;
            Log.APP.info("Giorni sospensione della pratica: " + giorniSospensionePratica);
            pratica.setGiorniSospensione(giorniSospensionePratica);
            Log.APP.info("Aggiorno la pratica");
            praticaDao.update(pratica);
            Date dataFineScadenzeMultiple = intervalloScadenzeMultipleSospensione.getDataFine();
            Log.APP.info("Calcolo i giorni per calcolare la nuova scadenza della pratica");
            long giorniDifferenza = Utils.getDaysBetweenDates(dataInizioScadenzeMultiple, dataFineScadenzeMultiple);
            Log.APP.info("Giorni scadenza " + giorniDifferenza);
            Scadenze scadenzaPratica = getScadenzaPratica(pratica);
            if (scadenzaPratica != null) {
                Date nuovaDataFine = Utils.addDaysToDate(scadenzaPratica.getDataInizioScadenza(), (int) giorniDifferenza + (int) giorniSospensionePratica + scadenzaPratica.getGiorniTeoriciScadenza());
                Log.APP.info("Nuova data fine della scadenza: " + Utils.dateItalianFormat(nuovaDataFine));
                scadenzaPratica.setDataFineScadenza(nuovaDataFine);
                scadenzaPratica.setDataFineScadenzaCalcolata(nuovaDataFine);
                Log.APP.info("Aggiorno la scadenza");
                processiDao.aggiornaScadenza(scadenzaPratica);
                Log.APP.info("Scadenza aggiornata");
            } else {
                Log.APP.warn("Nessuna scadenza trovata");
            }
        } else {
            Scadenze scadenzaPratica = getScadenzaPratica(pratica);
            if (scadenzaPratica != null) {
                Date dataInizioScadenza = scadenzaPratica.getDataInizioScadenza();
                long deltaGiorniScadenza = Utils.getDaysBetweenDates(scadenza.getDataInizioScadenza(), scadenza.getDataFineScadenza());
                int giorniSospensionePratica = pratica.getGiorniSospensione() + (int) deltaGiorniScadenza + scadenzaPratica.getGiorniTeoriciScadenza();
                Date dataFineScadenzaPratica = Utils.addDaysToDate(dataInizioScadenza, giorniSospensionePratica);
                scadenzaPratica.setDataFineScadenza(dataFineScadenzaPratica);
                scadenzaPratica.setDataFineScadenzaCalcolata(dataFineScadenzaPratica);
                processiDao.aggiornaScadenza(scadenzaPratica);
            } else {
                Log.APP.warn("Nessuna scadenza trovata");
            }
        }
    }

    private boolean isScadenzaDiInterruzionePratica(Scadenze scadenzaPratica) {
        ProcessiEventi evento = scadenzaPratica.getIdPraticaEvento().getIdEvento();
        LkStatoPratica statoPraticaAttivatoDaEvento = evento.getStatoPost();
        return statoPraticaAttivatoDaEvento.getCodice().equalsIgnoreCase("I");
    }

    private void gestisciIdScriptScadenza(ComunicazioneBean cb, Scadenze scadenza, Pratica pratica) throws Exception {
        //TODO: esegui plugin per gestione scadenza
    }

    private Scadenze getScadenzaPratica(Pratica pratica) {
        List<Scadenze> scadenze = pratica.getScadenzeList();
        Scadenze scadenzaPratica = null;
        if (scadenze != null && !scadenze.isEmpty()) {
            for (Scadenze scadenza : scadenze) {
                if (scadenza.getIdAnaScadenza().getFlgScadenzaPratica().equalsIgnoreCase("S")) {
                    scadenzaPratica = scadenza;
                    break;
                }
            }
        }
        return scadenzaPratica;
    }

    private void gestisciScadenza(ComunicazioneBean cb, Pratica pratica, ProcessiEventiScadenze scadenza) throws Exception {
        //Apro la scadenza
        if (scadenza.getIdStatoScadenza().getIdStato().equals("A")) {
            inserisciScadenza(cb, pratica, scadenza);
        }
        if (scadenza.getIdStatoScadenza().getIdStato().equals("C")) {
            chiudiScadenza(scadenza, pratica, cb);
        }
    }

    private void inserisciScadenza(ComunicazioneBean cb, Pratica pratica, ProcessiEventiScadenze scadenza) throws Exception {
        if (cb.getScadenzeCustom() != null && !cb.getScadenzeCustom().isEmpty()) {
            for (ScadenzaDTO dto : cb.getScadenzeCustom()) {
                Scadenze s = getScadenza(cb, pratica, scadenza, dto);
                processiDao.insertScadenza(s);
                if (isScadenzaDiSospensionePratica(s)) {
                    aggiornaTerminiScadenzaPratica(pratica);
                } else if (isScadenzaDiInterruzionePratica(s)) {
                    riavviaTerminiPratica(pratica);
                }
            }
        } else {
            Scadenze s = getScadenza(cb, pratica, scadenza, null);
            processiDao.insertScadenza(s);
            if (isScadenzaDiSospensionePratica(s)) {
                aggiornaTerminiScadenzaPratica(pratica);
            } else if (isScadenzaDiInterruzionePratica(s)) {
                riavviaTerminiPratica(pratica);
            }
        }
    }

    private Scadenze getScadenza(ComunicazioneBean cb, Pratica pratica, ProcessiEventiScadenze scadenza, ScadenzaDTO dto) {
        Scadenze s = new Scadenze();
        s.setIdPratica(pratica);
        s.setDataScadenza(cb.getDataEvento());
        s.setIdAnaScadenza(scadenza.getLkScadenze());
        Integer terminiScadenza = scadenza.getTerminiScadenza();
        if (dto != null) {
            Integer termini = dto.getTermini();
            if (termini != null) {
                s.setDataFineScadenza(Utils.add(cb.getDataEvento(), termini));
            } else {
                s.setDataFineScadenza(Utils.add(cb.getDataEvento(), 0));
            }
            s.setGiorniTeoriciScadenza(termini);
            if (!Utils.e(dto.getDescrizione())) {
                s.setDescrizioneScadenza(dto.getDescrizione());
            } else {
                s.setDescrizioneScadenza(scadenza.getLkScadenze().getDesAnaScadenze());
            }
        } else {
            if (scadenza.getTerminiScadenza() != null) {
                s.setDataFineScadenza(Utils.add(cb.getDataEvento(), scadenza.getTerminiScadenza()));
            } else {
                //se i termini sono a null, lo considero 0 giorni
                s.setDataFineScadenza(Utils.add(cb.getDataEvento(), 0));
                terminiScadenza = 0;
            }
            s.setGiorniTeoriciScadenza(terminiScadenza);
            s.setDescrizioneScadenza(scadenza.getLkScadenze().getDesAnaScadenze());
        }
        s.setIdStato(scadenza.getIdStatoScadenza());
        s.setDataInizioScadenza(cb.getDataEvento());
        //La scadenza calcolata coincide con la data di scadenza
        s.setDataFineScadenzaCalcolata(s.getDataFineScadenza());
        PraticheEventi evento = praticheService.getPraticaEvento(cb.getIdEventoPratica());
        s.setIdPraticaEvento(evento);
        return s;
    }

    private void chiudiScadenza(ProcessiEventiScadenze processiEventiScadenze, Pratica pratica, ComunicazioneBean cb) throws Exception {
        LkStatiScadenze chiusura = lookupDao.findStatoScadenzaById("C");
        Log.APP.info("Prendo tutte le scadenze indicate che devono essere chiuse automaticamente");
        Log.APP.info("Per quelle che devono essere chiuse manualmente delego il funzionamento all'operatore");
        if (processiEventiScadenze.getIdStatoScadenza().equals(chiusura) && processiEventiScadenze.getFlgVisualizzaScadenza().equalsIgnoreCase("N")) {
            Log.APP.info("Prendo tutte le scadenze della pratica");
            List<Scadenze> scadenzePratica = pratica.getScadenzeList();
            if (scadenzePratica != null && !scadenzePratica.isEmpty()) {
                for (Scadenze scadenza : scadenzePratica) {
                    Log.APP.info("Per ogni scadenza prendo solo quelle che sono aperte");
                    LkStatiScadenze aperta = lookupDao.findStatoScadenzaById("A");
                    Log.APP.info("Seleziono le pratiche dello stesso tipo della scadenza che sto gestendo e che sono ancora aperte");
                    if (scadenza.getIdStato().equals(aperta)
                            && scadenza.getIdAnaScadenza().equals(processiEventiScadenze.getLkScadenze())) {
                        eseguiChiusuraScadenza(scadenza, pratica, cb);
                    }
                }
            }
        }
    }

    private void riavviaTerminiPratica(Pratica pratica) throws Exception {
        Log.APP.info("Devo ricalcolare i termini della pratica");
        if (pratica.getScadenzeList() != null && !pratica.getScadenzeList().isEmpty()) {
            Log.APP.info("Per ogni scadenza attiva ...");
            for (Scadenze scadenza : pratica.getScadenzeList()) {
                if (scadenza.getIdStato().getIdStato().equalsIgnoreCase("A")) {
                    Log.APP.info("Ricalcolo la nuova data di fine scadenza");
                    Date now = new Date();
                    Log.APP.info("Per la scadenza " + scadenza.getIdScadenza() + " recupero i giorni teorici di scadenza");
                    int giorniTeoriciScadenza = scadenza.getGiorniTeoriciScadenza();
                    Log.APP.info("I giorni teorici di scadenza " + giorniTeoriciScadenza);
                    Date nuovaDataFine = Utils.addDaysToDate(now, giorniTeoriciScadenza);
                    Log.APP.info("La data di inizio scadenza è " + Utils.dateItalianFormat(now));
                    scadenza.setDataInizioScadenza(now);
                    Log.APP.info("La data di fine scadenza è " + Utils.dateItalianFormat(nuovaDataFine));
                    scadenza.setDataFineScadenza(nuovaDataFine);
                    scadenza.setDataFineScadenzaCalcolata(nuovaDataFine);
                    processiDao.aggiornaScadenza(scadenza);
                    aggiornaTerminiScadenzaPraticaPerChiusura(scadenza, pratica);
                }
            }
        }
    }

    private void aggiornaTerminiScadenzaPratica(Pratica pratica) throws Exception {
        Log.APP.info("Calcolo l'intervallo delle scadenze multiple attive sulla pratica");
        PraticaDao.IntervalloScadenzeMultiple intervalloScadenzeMultipleSospensione = praticheService.findIntervalloScadenzeMultipleSospensione(pratica.getIdPratica());
        if (intervalloScadenzeMultipleSospensione != null) {
            Date dataInizio = intervalloScadenzeMultipleSospensione.getDataInizio();
            Log.APP.info("Data inizio scadenze sospese " + Utils.dateItalianFormat(dataInizio));
            Date dataFine = intervalloScadenzeMultipleSospensione.getDataFine();
            Log.APP.info("Data fine scadenze sospese " + Utils.dateItalianFormat(dataFine));
            long giorniDifferenza = Utils.getDaysBetweenDates(dataInizio, dataFine);
            Log.APP.info("Giorni di sospensione: " + giorniDifferenza);
            Log.APP.info("Ricerco la scadenza della pratica");
            Scadenze scadenzaPratica = getScadenzaPratica(pratica);
            if (scadenzaPratica != null) {
                Log.APP.info("La scadenza della pratica ha id " + scadenzaPratica.getIdScadenza());
                Log.APP.info("Calcolo la nuova data di fine scadenza della pratica");
                Date nuovaDataFine = Utils.addDaysToDate(scadenzaPratica.getDataInizioScadenza(), (int) giorniDifferenza + scadenzaPratica.getGiorniTeoriciScadenza());
                Log.APP.info("Nuova data fine scadenza: " + Utils.dateItalianFormat(nuovaDataFine));
                scadenzaPratica.setDataFineScadenza(nuovaDataFine);
                scadenzaPratica.setDataFineScadenzaCalcolata(nuovaDataFine);
                Log.APP.info("Aggiorno la scadenza della pratica");
                processiDao.aggiornaScadenza(scadenzaPratica);
                Log.APP.info("Scadenza aggiornata");
            } else {
                Log.APP.warn("Non ho trovato scadenze di sospensione sulla pratica");
            }
        } else {
            Log.APP.info("L'intervallo è vuoto!");
        }
    }

    private void gestisciIdScriptScadenzaSuEvento(ComunicazioneBean cb, Pratica pratica) throws Exception {
        //TODO: esegui plugin per gestione scadenza su evento
    }

	public GridEstrazioniCILABean getEstrazioneCILA(HttpServletRequest request, JqgridPaginator paginator) throws Exception {
		
		GridEstrazioniCILABean json = new GridEstrazioniCILABean();
        Integer maxResult = Integer.parseInt(paginator.getRows());
        Integer page = Integer.parseInt(paginator.getPage());
        String column = paginator.getSidx();
        String order = paginator.getSord();
        Integer firstRecord = (page * maxResult) - maxResult;
        Filter filter = filterSerializer.getSearchFilter(request);
        filter.setPage(page);
        filter.setLimit(maxResult);
        filter.setOffset(firstRecord);
        filter.setOrderColumn(column);
        filter.setOrderDirection(order);
        
        request.getSession().setAttribute("estrazioniCilaFilter", filter);
        
        List<EstrazioniCilaDTO> pratiche = praticheService.findPraticheCILA(filter);
        List<EstrazioniCilaDTO> countPraticheCILA = praticheService.listPraticheCILA(filter);
        Integer totalRecords = countPraticheCILA.size(); 
        totalRecords = (totalRecords / maxResult == 0) ? 1 : ((totalRecords % maxResult == 0) ? (totalRecords / maxResult) : (totalRecords / maxResult + 1));
        json.setPage(page);
        json.setRecords(countPraticheCILA.size());
        json.setTotal(totalRecords);
        json.setRows(pratiche);
        return json;

	}

	public GridEstrazioniSCIABean getEstrazioneSCIA(HttpServletRequest request, JqgridPaginator paginator) throws Exception {
		GridEstrazioniSCIABean json = new GridEstrazioniSCIABean();
        Integer maxResult = Integer.parseInt(paginator.getRows());
        Integer page = Integer.parseInt(paginator.getPage());
        String column = paginator.getSidx();
        String order = paginator.getSord();
        Integer firstRecord = (page * maxResult) - maxResult;
        Filter filter = filterSerializer.getSearchFilter(request);
        filter.setPage(page);
        filter.setLimit(maxResult);
        filter.setOffset(firstRecord);
        filter.setOrderColumn(column);
        filter.setOrderDirection(order);
        
        request.getSession().setAttribute("estrazioniSciaFilter", filter);
        
        List<EstrazioniSciaDTO> pratiche = praticheService.findPraticheSCIA(filter);
        List<EstrazioniSciaDTO> countPraticheSCIA = praticheService.listPraticheSCIA(filter);
        Integer totalRecords = countPraticheSCIA.size(); 
        totalRecords = (totalRecords / maxResult == 0) ? 1 : ((totalRecords % maxResult == 0) ? (totalRecords / maxResult) : (totalRecords / maxResult + 1));
        json.setPage(page);
        json.setRecords(countPraticheSCIA.size());
        json.setTotal(totalRecords);
        json.setRows(pratiche);
        return json;
	}

	public it.wego.cross.beans.grid.GridEstrazioniPDCBean getEstrazionePDC(HttpServletRequest request, JqgridPaginator paginator) throws Exception {
		it.wego.cross.beans.grid.GridEstrazioniPDCBean json = new it.wego.cross.beans.grid.GridEstrazioniPDCBean();
        Integer maxResult = Integer.parseInt(paginator.getRows());
        Integer page = Integer.parseInt(paginator.getPage());
        String column = paginator.getSidx();
        String order = paginator.getSord();
        Integer firstRecord = (page * maxResult) - maxResult;
        Filter filter = filterSerializer.getSearchFilter(request);
        filter.setPage(page);
        filter.setLimit(maxResult);
        filter.setOffset(firstRecord);
        filter.setOrderColumn(column);
        filter.setOrderDirection(order);
        
        request.getSession().setAttribute("estrazioniPdcFilter", filter);
        
        List<EstrazioniPdcDTO> pratiche = praticheService.findPratichePDC(filter);
        List<EstrazioniPdcDTO> countPratichePDC = praticheService.listPratichePDC(filter);
        Integer totalRecords = countPratichePDC.size(); 
        totalRecords = (totalRecords / maxResult == 0) ? 1 : ((totalRecords % maxResult == 0) ? (totalRecords / maxResult) : (totalRecords / maxResult + 1));
        json.setPage(page);
        json.setRecords(countPratichePDC.size());
        json.setTotal(totalRecords);
        json.setRows(pratiche);
        return json;
	}

	public GridEstrazioniAGIBBean getEstrazioneAGIB(HttpServletRequest request, JqgridPaginator paginator) throws Exception {
		GridEstrazioniAGIBBean json = new GridEstrazioniAGIBBean();
        Integer maxResult = Integer.parseInt(paginator.getRows());
        Integer page = Integer.parseInt(paginator.getPage());
        String column = paginator.getSidx();
        String order = paginator.getSord();
        Integer firstRecord = (page * maxResult) - maxResult;
        Filter filter = filterSerializer.getSearchFilter(request);
        filter.setPage(page);
        filter.setLimit(maxResult);
        filter.setOffset(firstRecord);
        filter.setOrderColumn(column);
        filter.setOrderDirection(order);
        
        request.getSession().setAttribute("estrazioniAgibFilter", filter);
        
        List<EstrazioniAgibDTO> pratiche = praticheService.findPraticheAGIB(filter);
        List<EstrazioniAgibDTO> countPraticheAGIB = praticheService.listPraticheAGIB(filter);
        Integer totalRecords = countPraticheAGIB.size(); 
        totalRecords = (totalRecords / maxResult == 0) ? 1 : ((totalRecords % maxResult == 0) ? (totalRecords / maxResult) : (totalRecords / maxResult + 1));
        json.setPage(page);
        json.setRecords(countPraticheAGIB.size());
        json.setTotal(totalRecords);
        json.setRows(pratiche);
        return json;
	}

	public List<AllegatoRicezioneDTO> gestioneAllegatiFuoriXml(it.wego.cross.xml.Pratica praticaCross,String path) throws IOException {
		//leggi nella cartella di ogni pratica in formato pdf,rtf e doc
		
		File[] arrayFile = Utils.getFileAllegatiStampe(path);
		byte[] documentBody = null;
		List<AllegatoRicezioneDTO> allegati  = new ArrayList<AllegatoRicezioneDTO>();
		//i file letti saranno trasformati in list di AllegatoRicezioneDTO
		//MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
		for(File file:arrayFile) {
			documentBody = Utils.fileToByteArray(file);
			
			AllegatoRicezioneDTO allegato = new AllegatoRicezioneDTO();
			Allegati a = new Allegati();
			String fileNameWithOutExt = FilenameUtils.removeExtension(file.getName());
			a.setDescrizione(file.getName());
			
			a.setNomeFile(fileNameWithOutExt);
			a.setFile(documentBody);
			String mimeType = Files.probeContentType(Paths.get(file.toURI()));
			a.setTipoFile(mimeType);

			allegato.setAllegato(a);
			allegato.setModelloDomanda("");
			// Lo imposto come da inviare, in quanto è una ricevuta dell'evento

			allegato.setSend(Boolean.TRUE);

			allegati.add(allegato);

		}
		
		return allegati;
	}

	@Transactional(rollbackFor = Exception.class)
    public void popolaDatiCatastali(Pratica pratica, it.wego.cross.xml.Pratica praticaCross) throws Exception {

        it.wego.cross.xml.DatiCatastali datiCatastaliList =   praticaCross.getDatiCatastali();
        List<Immobile> listaImmobili = datiCatastaliList.getImmobile();
        
    	List<it.wego.cross.entity.DatiCatastali> listaDatiCatastali = new ArrayList<it.wego.cross.entity.DatiCatastali>();
        for(Immobile immobile: listaImmobili) {
        	it.wego.cross.entity.DatiCatastali datiCatastali = new it.wego.cross.entity.DatiCatastali();
        	datiCatastali.setCategoria(immobile.getCategoria()  );
        	datiCatastali.setCodImmobile(immobile.getCategoria());
        	datiCatastali.setIdTipoParticella(lookupDao.findTipoParticellaById(immobile.getIdTipoParticella()!=null ?immobile.getIdTipoParticella().intValue():0));
        	
        	datiCatastali.setIdTipoSistemaCatastale(lookupDao.findIdTipoCatastaleById(immobile.getIdTipoSistemaCatastale()!=null ? immobile.getIdTipoSistemaCatastale().intValue():1));
        	
        	
        	Integer idTipoUnita = 0;
        	if(immobile.getIdTipoUnita()!=null) {
        		idTipoUnita = immobile.getIdTipoUnita().intValue();
        	}
			datiCatastali.setIdTipoUnita(lookupDao.findTipoUnitaById(idTipoUnita));
			datiCatastali.setMappale(immobile.getMappale());
			datiCatastali.setSezione(immobile.getSezione());
			datiCatastali.setSubalterno(immobile.getSubalterno());
			datiCatastali.setFoglio(immobile.getFoglio());
			datiCatastali.setIdPratica(pratica);
			praticaDao.insert(datiCatastali);
			usefulService.flush();
            usefulService.refresh(datiCatastali);
            
        	listaDatiCatastali.add(datiCatastali); 
        }
        
        pratica.setDatiCatastaliList(listaDatiCatastali);
        praticaDao.update(pratica);
    }

	public void popolaIndirizziIntervento(Pratica pratica, it.wego.cross.xml.Pratica praticaCross) throws Exception {
		it.wego.cross.xml.IndirizziIntervento indirizziIntervento = praticaCross.getIndirizziIntervento();
		List<it.wego.cross.xml.IndirizzoIntervento> listXmlIndirizziIntervento = indirizziIntervento.getIndirizzoIntervento();
		List<IndirizziIntervento> indirizziInterventoList = new ArrayList<IndirizziIntervento>();
		
		for(it.wego.cross.xml.IndirizzoIntervento indirizzo: listXmlIndirizziIntervento) {
			
			IndirizziIntervento indirizziInterventoEntity = new IndirizziIntervento();
			indirizziInterventoEntity.setAltreInformazioniIndirizzo(indirizzo.getAltreInformazioniIndirizzo());
			indirizziInterventoEntity.setDatoEsteso1(indirizzo.getDatoEsteso1());
			indirizziInterventoEntity.setDatoEsteso2(indirizzo.getDatoEsteso2());
			indirizziInterventoEntity.setCap(indirizzo.getCap());
			indirizziInterventoEntity.setCivico(indirizzo.getCivico());
			indirizziInterventoEntity.setCodCivico(indirizzo.getCodCivico());
			indirizziInterventoEntity.setCodVia(indirizzo.getCodVia());
			indirizziInterventoEntity.setColore(indirizzo.getColore());
			if(indirizzo.getIdIndirizzoIntervento()!=null) {
				indirizziInterventoEntity.setIdIndirizzoIntervento(indirizzo.getIdIndirizzoIntervento().intValue());
			}
			indirizziInterventoEntity.setIdPratica(pratica);
			indirizziInterventoEntity.setIndirizzo(indirizzo.getIndirizzo());
			indirizziInterventoEntity.setInternoLettera(indirizzo.getInternoLettera());
			indirizziInterventoEntity.setInternoNumero(indirizzo.getInternoLettera());
			indirizziInterventoEntity.setInternoScala(indirizzo.getInternoScala());
			indirizziInterventoEntity.setLatitudine(indirizzo.getLatitudine());
			indirizziInterventoEntity.setLettera(indirizzo.getLettera());
			indirizziInterventoEntity.setLocalita(indirizzo.getLocalita());
			indirizziInterventoEntity.setLongitudine(indirizzo.getLongitudine());
			indirizziInterventoEntity.setPiano(indirizzo.getPiano());
			if(indirizzo.getIdDug()!=null) {
				LkDug idDug = null;//lkDug.indirizzo.getIdDug().intValue()
				indirizziInterventoEntity.setIdDug(idDug);
			}
			
		//indirizziInterventoList = indirizziInterventoService.serializeIndirizziInterventoXml(praticaCross);
			indirizziInterventoList.add(indirizziInterventoEntity);
		}
		
		pratica.setIndirizziInterventoList(indirizziInterventoList);
		praticaDao.update(pratica);
		
	}

	@Transactional(rollbackFor = Exception.class)
    public ComunicazioneBean popolaEventoRicezione(it.wego.cross.xml.Pratica praticaCross, Pratica pratica, ProcessiEventi eventoProcesso, List<String> destinatariEmail, List<AllegatoRicezioneDTO> allegati) throws Exception {
        try {
            String ricTuttiAllegati = configuration.getCachedConfiguration(SessionConstants.EVENTO_RICEZIONE_ALLEGA_TUTTO, pratica.getIdProcEnte().getIdEnte().getIdEnte(), pratica.getIdComune().getIdComune());

            ComunicazioneBean cb = new ComunicazioneBean();
            Log.WS.info("Cerco evento di ricezione");
            Log.WS.info("Setto la pratica");
            cb.setIdPratica(pratica.getIdPratica());
            Log.WS.info("Setto l'evento");
            cb.setIdEventoProcesso(eventoProcesso.getIdEvento());
           /*if (eventoProcesso.getFlgMail().equalsIgnoreCase("S")) {
                Log.WS.info("Deve essere inviata la comunicazione");
                //Non avendo il controllo come da front forzo l'invio della email
                cb.setInviaMail(Boolean.TRUE);
                cb.setNumeroProtocollo(pratica.getProtocollo());
                cb.setDataProtocollo(pratica.getDataProtocollazione());
                Log.WS.info("Setto i destinatari: " + destinatariEmail);
                cb.setDestinatariEmail(destinatariEmail);
            }*/
            AttoriComunicazione attori = getAttoriComunicazione(pratica.getPraticaAnagraficaList());
            cb.setMittenti(attori);
            cb.setVisibilitaCross(Boolean.TRUE);
            cb.setVisibilitaUtente(Boolean.TRUE);
            AllegatoRicezioneDTO allegatoPratica = new AllegatoRicezioneDTO();
            if (allegati != null && allegati.size() > 0) {
                for (AllegatoRicezioneDTO allegato : allegati) {
                    Log.WS.info("Aggiungo il file " + allegato.getAllegato().getNomeFile() + " all'evento");
                    Allegato a = AllegatiSerializer.serializeAllegato(allegato);
/*                    if (eventoProcesso.getFlgProtocollazione().equalsIgnoreCase("S")) {
                        a.setProtocolla(Boolean.TRUE);
                    } else {
                        a.setProtocolla(Boolean.FALSE);
                    }*/

                    if (allegato.isModelloDomanda()) {
                        allegatoPratica = allegato;
                        a.setFileOrigine(Boolean.TRUE);
                        a.setSpedisci(Boolean.TRUE);
                    } else {
                        if ("TRUE".equalsIgnoreCase(ricTuttiAllegati)) {
                            a.setSpedisci(Boolean.TRUE);
                        } else {
                            a.setSpedisci(Boolean.FALSE);
                        }
                    }

                    cb.addAllegato(a);
                }
            }
            cb.setOggettoProtocollo(pratica.getOggettoPratica());
            aggiornaPratica(pratica);
            Log.WS.info("Gestisco l'evento");
            workFlowService.gestisciProcessoEvento(cb);
            Log.WS.info("Salvo il modello della pratica");
            for (Integer idAllegato : cb.getIdAllegati()) {
                Allegati allegato = allegatiService.findAllegatoById(idAllegato);
                if (allegato != null && allegatoPratica.getAllegato() != null && allegato.getNomeFile().equals(allegatoPratica.getAllegato().getNomeFile())) {
                    pratica.setIdModello(allegato);
                    praticaDao.update(pratica);
                    usefulService.flush();
                    break;
                }
            }

            return cb;

        } catch (Exception ex) {
            Log.APP.error("Si è verificato un errore cercando di inserire l'evento", ex);
            throw ex;
        }
    }
	
	@Transactional(rollbackFor = Exception.class)
    public void popolaProcedimentiUpload(Pratica pratica, it.wego.cross.xml.Pratica praticaCross) throws Exception {
        List<it.wego.cross.xml.Procedimento> procedimentiPratica = praticaCross.getProcedimenti().getProcedimento();
        List<PraticaProcedimenti> praticaProcedimentiList = new ArrayList<PraticaProcedimenti>();
        for (it.wego.cross.xml.Procedimento procedimento : procedimentiPratica) {
            PraticaProcedimenti praticaProcedimenti = new PraticaProcedimenti();
            //Presuppongo che coincida con l'ID inserito nell'anagrafica enti di CROSS
            String enteDest = "";
            if(procedimento.getCodEnteDestinatario()==null)
            	enteDest = "2001";
            Enti ente = entiDao.findByCodEnte(enteDest);
            PraticaProcedimentiPK key = new PraticaProcedimentiPK();
            if(ente!=null) {
            	key.setIdEnte(ente.getIdEnte()!=null ? ente.getIdEnte() : Integer.parseInt(ente.getCodEnte()) );
            }else {
            	Log.WS.warn("ATTENZIONE! Codice procedimento non esistente nella pratica con identificativo '" + praticaCross.getIdentificativoPratica());
            }
            key.setIdPratica(pratica.getIdPratica());
            String codiceProcedimento = "";
            Procedimenti proc = null;
            //rivedere questo passaggio cioè non solo quando cod procedimento è vuoto controllare con id procedimento suap
            if(procedimento.getCodProcedimento().isEmpty()) {
            	codiceProcedimento = praticaCross.getIdProcedimentoSuap();
            	proc = procedimentiService.findProcedimentoByIdProc(new Integer(codiceProcedimento));
            }else {
             proc = procedimentiService.getProcedimento(procedimento.getCodProcedimento());
            }
             if (proc == null) {
                Log.WS.warn("ATTENZIONE! Procedimento con codice '" + procedimento.getCodProcedimento() + "' non trovato.");
            }else {
            	if(ente!=null) {
	            key.setIdProcedimento(proc.getIdProc());
	            praticaProcedimenti.setPraticaProcedimentiPK(key);
	            praticaDao.insert(praticaProcedimenti);
	            usefulService.flush();
	            usefulService.refresh(praticaProcedimenti);
	            praticaProcedimentiList.add(praticaProcedimenti);
            	}
	           }
            //TODO: INSERIRE ERRORE PER MANCANZA DI PRATICA PROCEDIMENTI capire se lanciare exception o no
           
        }
        pratica.setPraticaProcedimentiList(praticaProcedimentiList);
        praticaDao.update(pratica);
    }

	@Transactional(rollbackFor = Exception.class)
	public ComunicazioneBean inserisciEventoAggiornamentoSuap(it.wego.cross.xml.Pratica praticaCross,
			Pratica pratica, ProcessiEventi eventoProcesso, List<String> destinatariEmail,
			List<AllegatoRicezioneDTO> allegati, CooperazioneSUAPEnte cooperazioneSUAPEnte) throws Exception {
		 try {
//	            String ricTuttiAllegati = configuration.getCachedConfiguration(SessionConstants.EVENTO_RICEZIONE_ALLEGA_TUTTO, pratica.getIdProcEnte().getIdEnte().getIdEnte(), pratica.getIdComune().getIdComune());

	            ComunicazioneBean cb = new ComunicazioneBean();
	            Log.WS.info("Cerco evento di ricezione");
	            Log.WS.info("Setto la pratica");
	            cb.setIdPratica(pratica.getIdPratica());
	            Log.WS.info("Setto l'evento");
	            cb.setIdEventoProcesso(eventoProcesso.getIdEvento());
	            if (eventoProcesso.getFlgMail().equalsIgnoreCase("S")) {
	                Log.WS.info("Deve essere inviata la comunicazione");
	                cb.setInviaMail(Boolean.TRUE);
	                cb.setNumeroProtocollo(pratica.getProtocollo());
	                cb.setDataProtocollo(pratica.getDataProtocollazione());
	                Log.WS.info("Setto i destinatari: " + destinatariEmail);
	                cb.setDestinatariEmail(destinatariEmail);
	            }
	            AttoriComunicazione attori = getAttoriComunicazione(pratica.getPraticaAnagraficaList());
	            cb.setMittenti(attori);
	            cb.setVisibilitaCross(Boolean.TRUE);
	            cb.setVisibilitaUtente(Boolean.TRUE);
	            AllegatoRicezioneDTO allegatoPratica = new AllegatoRicezioneDTO();
	            if (allegati != null && allegati.size() > 0) {
	                for (AllegatoRicezioneDTO allegato : allegati) {
	                    Log.WS.info("Aggiungo il file " + allegato.getAllegato().getNomeFile() + " all'evento");
	                    Allegato a = AllegatiSerializer.serializeAllegato(allegato);
	                    if (eventoProcesso.getFlgProtocollazione().equalsIgnoreCase("S")) {
	                        a.setProtocolla(Boolean.TRUE);
	                    } else {
	                        a.setProtocolla(Boolean.FALSE);
	                    }

	                    if (allegato.isModelloDomanda()) {
	                        allegatoPratica = allegato;
	                        a.setFileOrigine(Boolean.TRUE);
	                        a.setSpedisci(Boolean.TRUE);
	                    } else {
//	                        if ("TRUE".equalsIgnoreCase(ricTuttiAllegati)) {
	                            a.setSpedisci(Boolean.TRUE);
//	                        } else {
//	                            a.setSpedisci(Boolean.FALSE);
//	                        }
	                    }

	                    cb.addAllegato(a);
	                }
	            }
//	            cb.setOggettoProtocollo(pratica.getOggettoPratica());
	            cb.setOggettoProtocollo(cooperazioneSUAPEnte.getIntestazione().getOggettoComunicazione().getValue());
	            cb.setNote(cooperazioneSUAPEnte.getIntestazione().getTestoComunicazione());
	            aggiornaPratica(pratica);
	            Log.WS.info("Gestisco l'evento");
	            workFlowService.gestisciProcessoEvento(cb);
	            Log.WS.info("Salvo il modello della pratica");
	            for (Integer idAllegato : cb.getIdAllegati()) {
	                Allegati allegato = allegatiService.findAllegatoById(idAllegato);
	                if (allegato != null && allegatoPratica.getAllegato() != null && allegato.getNomeFile().equals(allegatoPratica.getAllegato().getNomeFile())) {
	                    pratica.setIdModello(allegato);
	                    praticaDao.update(pratica);
	                    usefulService.flush();
	                    break;
	                }
	            }

	            return cb;

	        } catch (Exception ex) {
	            Log.APP.error("Si è verificato un errore cercando di inserire l'evento", ex);
	            throw ex;
	        }
	}
	
	
}
