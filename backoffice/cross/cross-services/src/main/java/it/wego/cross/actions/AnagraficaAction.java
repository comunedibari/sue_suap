/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.actions;

import com.google.common.base.Strings;
import it.wego.cross.beans.grid.GridAnagraficaBean;
import it.wego.cross.beans.layout.JqgridPaginator;
import it.wego.cross.constants.Constants;
import it.wego.cross.dao.AnagraficheDao;
import it.wego.cross.dao.LookupDao;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dao.StagingDao;
import it.wego.cross.dto.AnagraficaDTO;
import it.wego.cross.dto.AnagraficaDaCollegareDTO;
import it.wego.cross.dto.CittadinanzaDTO;
import it.wego.cross.dto.ComuneDTO;
import it.wego.cross.dto.NazionalitaDTO;
import it.wego.cross.dto.RecapitoDTO;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.Anagrafica;
import it.wego.cross.entity.AnagraficaRecapiti;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.LkProvincie;
import it.wego.cross.entity.LkTipoCollegio;
import it.wego.cross.entity.LkTipoIndirizzo;
import it.wego.cross.entity.LkTipoRuolo;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticaAnagrafica;
import it.wego.cross.entity.PraticaAnagraficaPK;
import it.wego.cross.entity.Recapiti;
import it.wego.cross.entity.Staging;
import it.wego.cross.entity.Utente;
import it.wego.cross.serializer.AnagraficheSerializer;
import it.wego.cross.serializer.FilterSerializer;
import it.wego.cross.service.AnagraficheService;
import it.wego.cross.service.LookupService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.service.ProcessiService;
import it.wego.cross.service.UsefulService;
import it.wego.cross.utils.AnagraficaUtils;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.PraticaUtils;
import it.wego.cross.utils.RecapitoUtils;
import it.wego.cross.utils.Utils;
import it.wego.cross.xml.Anagrafiche;
import java.io.StringWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author giuseppe
 */
@Component
public class AnagraficaAction {

    @Autowired
    private FilterSerializer filterSerializer;
    @Autowired
    private AnagraficheService anagraficheService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private AnagraficheDao anagraficheDao;
    @Autowired
    private UsefulService usefulService;
    @Autowired
    private LookupDao lookupDao;
    @Autowired
    private AnagraficheSerializer anagraficheSerializer;
    @Autowired
    private LookupService lookupService;
    @Autowired
    private AnagraficaUtils anagraficaUtils;
    @Autowired
    private PraticaDao praticaDao;
    @Autowired
    private StagingDao stagingDao;
    @Autowired
    private PraticheService praticheService;
    @Autowired
    private ProcessiService processiService;
    @Autowired
    private SystemEventAction systemEventAction;

    @Transactional
    public void eliminaRecapito(Integer idTipoRecapito, Integer idAnagrafica) throws Exception {

        Anagrafica anagrafica = anagraficheDao.findById(idAnagrafica);
        for (AnagraficaRecapiti recapito : anagrafica.getAnagraficaRecapitiList()) {
            if (recapito.getIdTipoIndirizzo().getIdTipoIndirizzo() == idTipoRecapito) {
                anagraficheDao.remove(recapito);
                anagrafica.getAnagraficaRecapitiList().remove(recapito);
                break;
            }
        }
        usefulService.flush();
        anagraficheDao.update(anagrafica);
    }

    @Transactional
    public void aggiungiRecapito(RecapitoDTO recapito) throws Exception {
        it.wego.cross.entity.Recapiti recapitoDB = new it.wego.cross.entity.Recapiti();
        RecapitoUtils.copyRecapito2Entity(recapito, recapitoDB);
        Anagrafica anagraficaDB = anagraficheDao.findById(recapito.getIdAnagrafica());
        LkTipoIndirizzo tipoIndirizzo = lookupDao.findTipoIndirizzoByDesc(recapito.getDescTipoIndirizzo());
        LkComuni comune = lookupDao.findComuneById(recapito.getIdComune());
        recapitoDB.setIdComune(comune);
        lookupDao.insert(recapitoDB);
        usefulService.flush();
        AnagraficaRecapiti nuovoRecapito = new AnagraficaRecapiti();
        nuovoRecapito.setIdAnagrafica(anagraficaDB);
        nuovoRecapito.setIdRecapito(recapitoDB);
        nuovoRecapito.setIdTipoIndirizzo(tipoIndirizzo);
        anagraficaDB.getAnagraficaRecapitiList().add(nuovoRecapito);
    }

    @Transactional
    public void aggiornaAnagrafica(Anagrafica anagrafica) throws Exception {
        anagraficheDao.update(anagrafica);
    }

    @Transactional(rollbackFor = Exception.class)
    public void salvaAnagrafica(AnagraficaDTO input, Anagrafica result) throws Exception {
        Anagrafica anagrafica = anagraficheSerializer.serialize(input, result);
        salvaAnagrafica(anagrafica);
        usefulService.flush();
        salvaNuovoRecapito(input, anagrafica);
    }

    private void salvaAnagrafica(Anagrafica anagrafica) throws Exception {
        anagraficheDao.insert(anagrafica);
    }

    private void salvaNuovoRecapito(AnagraficaDTO input, Anagrafica anagrafica) throws Exception {
        if (input.getRecapiti() != null && input.getRecapiti().size() > 0) {
            List<AnagraficaRecapiti> anagraficaRecapitiList = new ArrayList<AnagraficaRecapiti>();
            for (RecapitoDTO dto : input.getRecapiti()) {
                Recapiti recapito;
                if (dto.getIdRecapito() != null) {
                    recapito = anagraficheService.findRecapitoById(dto.getIdRecapito());
                } else {
                    recapito = new Recapiti();
                }
                if (dto.getIdComune() != null) {
                    LkComuni comune = lookupService.findComuneById(dto.getIdComune());
                    recapito.setIdComune(comune);
                }
                recapito.setLocalita(dto.getLocalita());
                recapito.setIndirizzo(dto.getIndirizzo());
                recapito.setNCivico(dto.getnCivico());
                recapito.setCap(dto.getCap());
                recapito.setCasellaPostale(dto.getCasellaPostale());
                recapito.setTelefono(dto.getTelefono());
                recapito.setCellulare(dto.getCellulare());
                recapito.setFax(dto.getFax());
                recapito.setEmail(dto.getEmail());
                recapito.setPec(dto.getPec());
                recapito.setAltreInfoIndirizzo(dto.getAltreInfoIndirizzo());
                lookupDao.insert(recapito);
                usefulService.flush();
                AnagraficaRecapiti anagraficaRecapiti = new AnagraficaRecapiti();
                anagraficaRecapiti.setIdAnagrafica(anagrafica);
                anagraficaRecapiti.setIdRecapito(recapito);
                LkTipoIndirizzo tipoIndirizzo = lookupDao.findTipoIndirizzoByDesc(dto.getDescTipoIndirizzo());
                anagraficaRecapiti.setIdTipoIndirizzo(tipoIndirizzo);
                anagraficaRecapitiList.add(anagraficaRecapiti);
                anagraficheDao.insert(anagraficaRecapiti);
                usefulService.flush();
            }
            anagrafica.setAnagraficaRecapitiList(anagraficaRecapitiList);
            anagraficheDao.update(anagrafica);
            usefulService.flush();
        }
    }

    private void aggiornaAnagraficaAction(Anagrafica a) throws Exception {
        anagraficheDao.update(a);
    }

    @Transactional(rollbackFor = Exception.class)
    public void aggiornaAnagrafica(AnagraficaDTO anagrafica, Anagrafica result) throws Exception {
        Anagrafica a = anagraficheSerializer.serialize(anagrafica, result);
        if (anagrafica.getRecapiti() != null && anagrafica.getRecapiti().size() > 0) {
            for (RecapitoDTO dto : anagrafica.getRecapiti()) {
                Recapiti recapito;
                if (dto.getIdRecapito() != null) {
                    recapito = anagraficheService.findRecapitoById(dto.getIdRecapito());
                } else {
                    recapito = new Recapiti();
                }
                //IE BUG
                //se e' gia stato eliminato non fare niente
                if (recapito == null) {
                    continue;
                }
                if (dto.getIdComune() != null) {
                    LkComuni comune = lookupService.findComuneById(dto.getIdComune());
                    recapito.setIdComune(comune);
                }
                recapito.setLocalita(dto.getLocalita());
                recapito.setIndirizzo(dto.getIndirizzo());
                recapito.setNCivico(dto.getnCivico());
                recapito.setCap(dto.getCap());
                recapito.setCasellaPostale(dto.getCasellaPostale());
                recapito.setTelefono(dto.getTelefono());
                recapito.setCellulare(dto.getCellulare());
                recapito.setFax(dto.getFax());
                recapito.setEmail(dto.getEmail());
                recapito.setPec(dto.getPec());
                recapito.setAltreInfoIndirizzo(dto.getAltreInfoIndirizzo());
                usefulService.flush();
            }
        }
        aggiornaAnagraficaAction(a);
    }

    public AnagraficaDTO aggiornaAnagraficaStg(AnagraficaDTO anagraficaDTO, Utente utenteConnesso) throws Exception {

        //Anagrafica anagrafica = anagraficheDao.findByCodiceFiscale(anagraficaDTO.getCodiceFiscale());
        Anagrafica anagrafica = null;
        if (anagraficaDTO.getIdAnagrafica() != null) {
            anagrafica = anagraficheDao.findById(anagraficaDTO.getIdAnagrafica());
        }
        if (anagrafica == null) {
            anagrafica = anagraficheDao.findByCodiceFiscale(anagraficaDTO.getCodiceFiscale());
        }
        if (anagrafica == null && !Strings.isNullOrEmpty(anagraficaDTO.getPartitaIva())) {
            anagrafica = anagraficheDao.findByPartitaIva(anagraficaDTO.getPartitaIva());
        }
        if (anagrafica == null) {
            anagrafica = new Anagrafica();
            Log.APP.info("Calcolo la variante anagrafica");
            String varianteAnagrafica = anagraficaDTO.getIdTipoPersona();
            if (Constants.PERSONA_FISICA.equals(anagraficaDTO.getIdTipoPersona()) && anagraficaDTO.getFlgIndividuale().equalsIgnoreCase("S")) {
                varianteAnagrafica = "I";
            }
            anagraficaDTO.setVarianteAnagrafica(varianteAnagrafica);
            anagraficaUtils.copyAnagrafica2Entity(anagraficaDTO, anagrafica);
            Log.APP.info("Insert Anagrafica:");
            anagraficheDao.insert(anagrafica);
            usefulService.flush();
            List<AnagraficaRecapiti> anagraficaRecapitiList = new ArrayList<AnagraficaRecapiti>();
            Log.APP.info("Trovati " + anagraficaDTO.getRecapiti() + " recapiti");
            for (RecapitoDTO recapitoDTO : anagraficaDTO.getRecapiti()) {
                AnagraficaRecapiti anagraficaRecapito = new AnagraficaRecapiti();
                it.wego.cross.entity.Recapiti recapitoDB = new it.wego.cross.entity.Recapiti();
                if (recapitoDTO.getIdTipoIndirizzo() != null) {
                    LkTipoIndirizzo tipind = lookupDao.findTipoIndirizzoById(recapitoDTO.getIdTipoIndirizzo());
                    if (tipind != null) {
                        recapitoDTO.setIdTipoIndirizzo(tipind.getIdTipoIndirizzo());
                        recapitoDTO.setDescTipoIndirizzo(tipind.getDescrizione());
                        anagraficaRecapito.setIdTipoIndirizzo(tipind);
                    }
                }
                if (recapitoDTO.getIdComune() != null) {
                    LkComuni comune = null;
                    if (recapitoDTO.getIdComune() != null) {
                        comune = lookupDao.findComuneById(recapitoDTO.getIdComune());
                    } else {
                        List<LkComuni> comuni = lookupDao.findComuniByDescrizione(recapitoDTO.getDescComune(), new Date());
                        if (comuni != null && comuni.size() > 0) {
                            comune = comuni.get(0);
                        }
                    }
                    recapitoDB.setIdComune(comune);
                }
                if (recapitoDTO.getIdDug() != null) {
                    recapitoDB.setIdDug(lookupDao.findDugById(recapitoDTO.getIdDug()));
                }
                RecapitoUtils.copyRecapito2Entity(recapitoDTO, recapitoDB);
                lookupDao.insert(recapitoDB);
                usefulService.flush();
                anagraficaRecapito.setIdRecapito(recapitoDB);
                anagraficaRecapito.setIdAnagrafica(anagrafica);
                recapitoDTO.setIdRecapito(recapitoDB.getIdRecapito());
                recapitoDTO.setIdAnagrafica(anagrafica.getIdAnagrafica());
                anagraficheDao.insert(anagraficaRecapito);
                usefulService.flush();
                anagraficaRecapitiList.add(anagraficaRecapito);

            }
            anagrafica.setAnagraficaRecapitiList(anagraficaRecapitiList);
            //Aggiorno l'XML dell'anagrafica
            aggiornaAnagraficaXML(anagraficaDTO);
        } else {
            // caso cambio codice fiscale su anagraficaDTO e angrafica già collegata, scollego la vecchia angrafica e la sostituisco con quella corrispondente
            // per codice fiscale - salvo il vecchio id per scollegarlo
            Integer oldIdAnagrafica = anagraficaDTO.getIdAnagrafica();
            anagraficaDTO.setIdAnagrafica(anagrafica.getIdAnagrafica());
            Log.APP.info("AGGIORNA Anagrafica id:" + anagrafica.getIdAnagrafica());
            for (RecapitoDTO recapitoDTO : anagraficaDTO.getRecapiti()) {
                boolean trovato = false;
                if (recapitoDTO.getIdTipoIndirizzo() == null) {
                    LkTipoIndirizzo tipind = lookupDao.findTipoIndirizzoByDesc(recapitoDTO.getDescTipoIndirizzo());
                    if (tipind != null) {
                        recapitoDTO.setIdTipoIndirizzo(tipind.getIdTipoIndirizzo());
                    }
                }
                LkTipoIndirizzo tipoIndirizzo = lookupDao.findTipoIndirizzoById(recapitoDTO.getIdTipoIndirizzo());
                for (AnagraficaRecapiti anagraficaRecapito : anagrafica.getAnagraficaRecapitiList()) {
//                    if (RecapitoUtils.equals(recapitoDTO, anagraficaRecapito.getIdRecapito())) {
                    if (anagraficaRecapito.getIdRecapito() != null && anagraficaRecapito.getIdRecapito().getIdRecapito().equals(recapitoDTO.getIdRecapito())) {
                        Recapiti recapitoDB = anagraficaRecapito.getIdRecapito();
                        RecapitoUtils.copyRecapito2Entity(recapitoDTO, recapitoDB);
                        if (recapitoDTO.getIdDug() != null) {
                            recapitoDB.setIdDug(lookupDao.findDugById(recapitoDTO.getIdDug()));
                        }
                        LkComuni comune = null;
                        if (recapitoDTO.getIdComune() != null) {
                            comune = lookupDao.findComuneById(recapitoDTO.getIdComune());
                        } else if (!Strings.isNullOrEmpty(recapitoDTO.getDescComune())) {
                            List<LkComuni> comuni = lookupDao.findComuniByDescrizione(recapitoDTO.getDescComune(), new Date());
                            if (comuni != null && comuni.size() > 0) {
                                comune = comuni.get(0);
                            }
                        }
                        recapitoDB.setIdComune(comune);
                        trovato = true;
                        recapitoDTO.setIdRecapito(recapitoDB.getIdRecapito());
                        recapitoDTO.setIdAnagrafica(anagrafica.getIdAnagrafica());
                        anagraficaRecapito.setIdTipoIndirizzo(tipoIndirizzo);
                        break;
                    }
                }
                if (!trovato) {
                    Recapiti recapitoDB = new Recapiti();
                    LkComuni comune = null;
                    if (recapitoDTO.getIdComune() != null) {
                        comune = lookupDao.findComuneById(recapitoDTO.getIdComune());
                    } else {
                        List<LkComuni> comuni = lookupDao.findComuniByDescrizione(recapitoDTO.getDescComune(), new Date());
                        if (comuni != null && comuni.size() > 0) {
                            comune = comuni.get(0);
                        }
                    }
                    recapitoDB.setIdComune(comune);
                    RecapitoUtils.copyRecapito2Entity(recapitoDTO, recapitoDB);
                    lookupDao.insert(recapitoDB);
                    usefulService.flush();
                    AnagraficaRecapiti anagraficaRecapiti = new AnagraficaRecapiti();
                    anagraficaRecapiti.setIdAnagrafica(anagrafica);
                    anagraficaRecapiti.setIdRecapito(recapitoDB);
                    anagraficaRecapiti.setIdTipoIndirizzo(tipoIndirizzo);
                    Log.APP.info("Aggiungo il recapito collegato con l'anagrafica con id recapito " + recapitoDB.getIdRecapito() + " e id anagrafica " + anagrafica.getIdAnagrafica());
                    recapitoDTO.setIdRecapito(recapitoDB.getIdRecapito());
                    recapitoDTO.setIdAnagrafica(anagrafica.getIdAnagrafica());
                    if (anagrafica.getAnagraficaRecapitiList() == null) {
                        anagrafica.setAnagraficaRecapitiList(new ArrayList<AnagraficaRecapiti>());
                    }
                    anagrafica.getAnagraficaRecapitiList().add(anagraficaRecapiti);
                }
            }
            //idTipoCollegio
            if (anagraficaDTO.getIdTipoCollegio() != null) {
                LkTipoCollegio tipocollegio = lookupDao.findLookupTipoCollegioById(anagraficaDTO.getIdTipoCollegio());
                if (tipocollegio != null) {
                    anagrafica.setIdTipoCollegio(tipocollegio);
                }
            }
            if (anagraficaDTO.getProvinciaCciaa() != null) {
                LkProvincie provincia = lookupDao.findLkProvinciaById(anagraficaDTO.getProvinciaCciaa().getIdProvincie());
                if (provincia != null && provincia.getIdProvincie() != null) {
                    anagrafica.setIdProvinciaCciaa(provincia);
                }
            }
            if (anagraficaDTO.getProvinciaIscrizione() != null) {
                LkProvincie provincia = lookupDao.findLkProvinciaById(anagraficaDTO.getProvinciaIscrizione().getIdProvincie());
                if (provincia != null && provincia.getIdProvincie() != null) {
                    anagrafica.setIdProvinciaIscrizione(provincia);
                }
            }
            String varianteAnagrafica = anagraficaDTO.getIdTipoPersona();
            String flgIndividuale = anagraficaDTO.getFlgIndividuale();
            if (flgIndividuale.equalsIgnoreCase("S")) {
                varianteAnagrafica = "I";
            }
            //L'anagrafica è marcata come persona fisica ma in banca dati è ditta individuale, allora devo mantenere la varianteAnagrafica ad I
            if (anagrafica.getVarianteAnagrafica() != null && anagrafica.getVarianteAnagrafica().equals("I")) {
                varianteAnagrafica = "I";
            }
            anagraficaDTO.setVarianteAnagrafica(varianteAnagrafica);
            //^^CS sembra sbagliato ma non lo e'
            anagraficaUtils.copyAnagrafica2Entity(anagraficaDTO, anagrafica);
            anagraficaUtils.copyEntity2Anagrafica(anagrafica, anagraficaDTO);
            Log.APP.info("DB : angraficaID " + anagrafica.getIdAnagrafica() + ", CF " + anagrafica.getCodiceFiscale() + ", PIVA " + anagrafica.getPartitaIva() + ", variabileAnagrafica " + anagrafica.getVarianteAnagrafica() + ", tipoAnagrafica " + anagrafica.getTipoAnagrafica());
            anagraficheDao.update(anagrafica);
            usefulService.flush();
            anagraficaDTO.setFlgIndividuale(flgIndividuale);
            // butto via il collegamento alla vecchia anagrafica
            if (!oldIdAnagrafica.equals(anagraficaDTO.getIdAnagrafica())) {
                PraticaAnagrafica pa = praticaDao.findPraticaAnagraficaByKey(anagraficaDTO.getIdPratica(), oldIdAnagrafica, anagraficaDTO.getIdTipoRuolo());
                systemEventAction.scollegaAnagrafica(pa, utenteConnesso);
                Log.APP.info("Elimino Pratica Anagrafica " + oldIdAnagrafica);
            }
        }
        Log.APP.info("XML : angraficaID " + anagrafica.getIdAnagrafica() + " CF " + anagrafica.getCodiceFiscale() + " PIVA " + anagrafica.getPartitaIva() + " variabileAnagrafica " + anagrafica.getVarianteAnagrafica() + " tipoAnagrafica " + anagrafica.getTipoAnagrafica());
        anagraficaDTO.setIdAnagrafica(anagrafica.getIdAnagrafica());
        aggiornaAnagraficaXML(anagraficaDTO);
        Log.APP.info("return : fine salvataggio anagrafica");
        return anagraficaDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    public AnagraficaDTO aggiornaAnagraficaStgTransactional(AnagraficaDTO anagraficaDTO, Utente utenteConnesso) throws Exception {
        AnagraficaDTO anagrafica = aggiornaAnagraficaStg(anagraficaDTO, utenteConnesso);
        PraticaAnagrafica collegamentoNuovo = praticheService.findPraticaAnagraficaByKey(anagrafica.getIdPratica(), anagrafica.getIdAnagrafica(), anagrafica.getIdTipoRuolo());
        AnagraficaDaCollegareDTO dto = AnagraficheSerializer.serializeAnagraficaDaCollegare(anagraficheService.findAnagraficaById(anagrafica.getIdAnagrafica()));
        dto.setRuolo(anagrafica.getIdTipoRuolo());
        if (anagrafica.getVarianteAnagrafica().equals("I")) {
            dto.setDittaIndividuale("S");
        } else if (anagrafica.getVarianteAnagrafica().equals("F")) {
            dto.setDittaIndividuale("N");
        }
        if (collegamentoNuovo == null && anagrafica.getIdTipoRuoloOriginale() == null) {
            //(AnagraficaDaCollegareDTO anagrafica, Integer idPratica, Utente utenteConnesso)
            systemEventAction.inserisciCollegamentoPraticaAnagraficaNoTransactional(dto, anagrafica.getIdPratica(), utenteConnesso, false);
        } else {
            PraticaAnagrafica collegamentoOriginale = praticheService.findPraticaAnagraficaByKey(anagrafica.getIdPratica(), anagrafica.getIdAnagrafica(), anagrafica.getIdTipoRuoloOriginale());
            if (collegamentoOriginale != null) {
                praticheService.deletePraticaAnagrafica(collegamentoOriginale);
            }
            if (collegamentoNuovo == null) {
                collegamentoNuovo = new PraticaAnagrafica();
                PraticaAnagraficaPK key = new PraticaAnagraficaPK();
                key.setIdAnagrafica(anagrafica.getIdAnagrafica());
                key.setIdPratica(anagrafica.getIdPratica());
                key.setIdTipoRuolo(anagrafica.getIdTipoRuolo());
                collegamentoNuovo.setPraticaAnagraficaPK(key);
            }
            collegamentoNuovo.setDataInizioValidita(new Date());
            collegamentoNuovo.setFlgDittaIndividuale(anagrafica.getFlgIndividuale());
            praticheService.savePraticaAnagrafica(collegamentoNuovo);
        }
        return anagrafica;
    }

    @Transactional
    public void salvaCFAnagraficaXML(AnagraficaDTO anagrafica) throws Exception {
        String cf = anagrafica.getCodiceFiscale();
        String tipoAnagrafica = anagrafica.getTipoAnagrafica();
        String varianteAnagrafica = anagrafica.getVarianteAnagrafica();
        LkTipoRuolo ruolo = lookupDao.findTipoRuoloByDesc(anagrafica.getDesTipoRuolo());
        String desRuolo = ruolo.getDescrizione();
        Integer idRuolo = ruolo.getIdTipoRuolo();
        String codRuolo = ruolo.getCodRuolo();
        AnagraficaDTO anagraficaXML = anagraficheService.getAnagraficaXML(anagrafica);
        anagraficaXML.setIdPratica(anagrafica.getIdPratica());
        anagraficaXML.setCodiceFiscale(cf);
        anagraficaXML.setTipoAnagrafica(tipoAnagrafica);
        anagraficaXML.setVarianteAnagrafica(varianteAnagrafica);
        anagraficaXML.setDesTipoRuolo(desRuolo);
        anagraficaXML.setIdTipoRuolo(idRuolo);
        anagraficaXML.setCodTipoRuolo(codRuolo);
        aggiornaAnagraficaXML(anagraficaXML);
    }

    private void aggiornaXmlPraticaAggiornaNuovaStrutturaAnagrafica(AnagraficaDTO anagraficaDTO) throws Exception {
        Pratica pratica = praticaDao.findPratica(anagraficaDTO.getIdPratica());
        Staging stg = stagingDao.findByCodStaging(pratica.getIdStaging().getIdStaging());
        it.wego.cross.xml.Pratica praticaXml = PraticaUtils.getPraticaFromXML(new String(stg.getXmlPratica()));
        Log.APP.info("aggiornaAnagraficaXML: idPratica:" + anagraficaDTO.getIdPratica() + " idStaging:" + pratica.getIdStaging().getIdStaging());
        for (it.wego.cross.xml.Anagrafiche anaXML : praticaXml.getAnagrafiche()) {
            if (anagraficaUtils.equals(anagraficaDTO, anaXML.getAnagrafica())) {
                Log.APP.info("copyAnagrafica2XML: counter:" + anagraficaDTO.getCounter());
                anagraficaUtils.copyAnagrafica2XML(anagraficaDTO, anaXML);
            }
        }
        JAXBContext contextObj = JAXBContext.newInstance(it.wego.cross.xml.Pratica.class);
        Marshaller marshallerObj = contextObj.createMarshaller();
        marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter praticaSTR = new StringWriter();
        marshallerObj.marshal(praticaXml, praticaSTR);
        stg.setXmlPratica(praticaSTR.toString().getBytes());
        Log.APP.info("stgdao.merge:");
        stagingDao.merge(stg);
    }

    private void aggiornaAnagraficaXML(AnagraficaDTO anagraficaDTO) throws Exception {
        Pratica pratica = praticaDao.findPratica(anagraficaDTO.getIdPratica());
        Staging stg = stagingDao.findByCodStaging(pratica.getIdStaging().getIdStaging());
        it.wego.cross.xml.Pratica praticaXml = PraticaUtils.getPraticaFromXML(new String(stg.getXmlPratica()));
        Log.APP.info("aggiornaAnagraficaXML: idPratica:" + anagraficaDTO.getIdPratica() + " idStaging:" + pratica.getIdStaging().getIdStaging());
        for (it.wego.cross.xml.Anagrafiche anaXML : praticaXml.getAnagrafiche()) {
            if (anagraficaUtils.equals(anagraficaDTO, anaXML.getAnagrafica())) {
                Log.APP.info("copyAnagrafica2XML: counter:" + anagraficaDTO.getCounter());
                anagraficaUtils.copyAnagrafica2XML(anagraficaDTO, anaXML);
                break;
            }
        }
        JAXBContext contextObj = JAXBContext.newInstance(it.wego.cross.xml.Pratica.class);
        Marshaller marshallerObj = contextObj.createMarshaller();
        marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter praticaSTR = new StringWriter();
        marshallerObj.marshal(praticaXml, praticaSTR);
        stg.setXmlPratica(praticaSTR.toString().getBytes());
        Log.APP.info("stgdao.merge:");
        stagingDao.merge(stg);
    }

    @Transactional
    public void inserisciRecapito(Recapiti recapito) throws Exception {
        anagraficheDao.insert(recapito);
    }

    public GridAnagraficaBean getAnagrafiche(HttpServletRequest request, JqgridPaginator paginator, String filterName) throws Exception {
        GridAnagraficaBean json = new GridAnagraficaBean();
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
        request.getSession().setAttribute(filterName, filter);
        Long countRighe = anagraficheService.countAnagrafiche(filter);
        List<AnagraficaDTO> anagrafiche = cambiaDenominazione(anagraficheService.searchAnagrafiche(filter));
        int totalRecords = countRighe.intValue();
        totalRecords = (totalRecords / maxResult == 0) ? 1 : ((totalRecords % maxResult == 0) ? (totalRecords / maxResult) : (totalRecords / maxResult + 1));
        json.setPage(page);
        json.setRecords(countRighe.intValue());
        json.setTotal(totalRecords);
        json.setRows(anagrafiche);
        return json;
    }

    private List<AnagraficaDTO> cambiaDenominazione(List<AnagraficaDTO> searchAnagrafiche) {
        List<AnagraficaDTO> anagrafiche = new ArrayList<AnagraficaDTO>();
        for (AnagraficaDTO a : searchAnagrafiche) {
            if (a.getNome() != null && a.getCognome() != null && (a.getDenominazione() == null || "".equals(a.getDenominazione()))) {
                a.setDenominazione(a.getCognome() + " " + a.getNome());
            }
            if (anagraficaUtils.getTipoAnagrafica(a).equals(Constants.PERSONA_FISICA)) {
                a.setTipoAnagrafica(messageSource.getMessage("utenti.personaFisica", null, Locale.getDefault()));
                if (a.getVarianteAnagrafica() != null) {
                    if (a.getVarianteAnagrafica() != null && a.getVarianteAnagrafica().equals(Constants.PERSONA_DITTAINDIVIDUALE)) {
                        a.setTipoAnagrafica(messageSource.getMessage("utenti.personaIndividuale", null, Locale.getDefault()));
                    }
                }
            }
            if (anagraficaUtils.getTipoAnagrafica(a).equals(Constants.PERSONA_GIURIDICA)) {
                a.setTipoAnagrafica(messageSource.getMessage("utenti.personaGiuridica", null, Locale.getDefault()));
            }

            anagrafiche.add(a);
        }
        return anagrafiche;
    }

    public GridAnagraficaBean getListaAnagraficheDaConfrontare(HttpServletRequest request, AnagraficaDTO anagrafica, AnagraficaDTO anagraficaDB) throws Exception, NumberFormatException {
        GridAnagraficaBean json = new GridAnagraficaBean();
        List<AnagraficaDTO> anagrafiche = new ArrayList<AnagraficaDTO>();
        AnagraficaDTO anagraficaDto = anagraficheService.getAnagraficaXML(anagrafica);
        String desComuneNascita = "default";
        Integer idComuneNascita = -1;
        if (anagraficaDto.getComuneNascita() != null) {
            desComuneNascita = anagraficaDto.getComuneNascita().getDescrizione();
            idComuneNascita = anagraficaDto.getComuneNascita().getIdComune();
        }
        String desCittadinanza = "default";
        Integer idCittadinanza;
        if (anagraficaDto.getDesCittadinanza() != null) {
            desCittadinanza = anagraficaDto.getDesCittadinanza();
        }
        idCittadinanza = anagraficaDto.getIdCittadinanza();
        String desNazionalita = "default";
        Integer idNazionalita;
        if (anagraficaDto.getDesNazionalita() != null) {
            desNazionalita = anagraficaDto.getDesNazionalita();
        }
        idNazionalita = anagraficaDto.getIdNazionalita();
        ComuneDTO comuneDB;
        if (idComuneNascita != null) {
            comuneDB = lookupService.getComuneDTOFromId(idComuneNascita);
        } else {
            comuneDB = lookupService.getComuneDTOFromNomePreciso(desComuneNascita);
        }
        CittadinanzaDTO cittadinanzaDB;
        if (idCittadinanza != null) {
            cittadinanzaDB = lookupService.getCittadinanzaFromId(idCittadinanza);
        } else {
            cittadinanzaDB = lookupService.getCittadinanzaFromDescrizionePrecisa(desCittadinanza);
        }
        NazionalitaDTO nazionalitaDB;
        if (idNazionalita != null) {
            nazionalitaDB = lookupService.getNazionalitaFromId(idNazionalita);
        } else {
            nazionalitaDB = lookupService.getNazionalitaFromDescrizionePrecisa(desNazionalita);
        }
        if (comuneDB != null) {
            anagraficaDto.setComuneNascita(comuneDB);
        }

        if (anagraficaDto.getProvinciaCciaa() != null) {
            if (anagraficaDto.getProvinciaCciaa().getIdProvincie() == null) {
                if (!Strings.isNullOrEmpty(anagraficaDto.getProvinciaCciaa().getCodCatastale())) {
                    LkProvincie provincia = lookupDao.findLkProvinciaByCod(anagraficaDto.getProvinciaCciaa().getCodCatastale(), Boolean.TRUE);
                    if (provincia != null) {
                        anagraficaDto.getProvinciaCciaa().setIdProvincie(provincia.getIdProvincie());
                        anagraficaDto.getProvinciaCciaa().setDescrizione(provincia.getDescrizione());
                    } else {
                        provincia = lookupDao.findLkProvinciaByDescPrecisa(anagraficaDto.getProvinciaCciaa().getCodCatastale(), Boolean.TRUE);
                        if (provincia != null) {
                            anagraficaDto.getProvinciaCciaa().setIdProvincie(provincia.getIdProvincie());
                            anagraficaDto.getProvinciaCciaa().setDescrizione(provincia.getDescrizione());
                        }
                    }
                } else if (Strings.isNullOrEmpty(anagraficaDto.getProvinciaCciaa().getCodCatastale())
                        && !Strings.isNullOrEmpty(anagraficaDto.getProvinciaCciaa().getDescrizione())) {
                    LkProvincie provincia = lookupDao.findLkProvinciaByDescPrecisa(anagraficaDto.getProvinciaCciaa().getDescrizione(), Boolean.TRUE);
                    if (provincia != null) {
                        anagraficaDto.getProvinciaCciaa().setIdProvincie(provincia.getIdProvincie());
                        anagraficaDto.getProvinciaCciaa().setDescrizione(provincia.getDescrizione());
                    }
                }
            }
        }

        if (cittadinanzaDB != null) {
            anagraficaDto.setDesCittadinanza(cittadinanzaDB.getDescrizione());
            anagraficaDto.setIdCittadinanza(cittadinanzaDB.getIdCittadinanza());
        }
        if (nazionalitaDB != null) {
            anagraficaDto.setDesNazionalita(nazionalitaDB.getDescrizione());
            anagraficaDto.setIdNazionalita(nazionalitaDB.getIdNazionalita());
        }
        if (anagraficaDto.getRecapiti() != null) {
            for (RecapitoDTO recapito : anagraficaDto.getRecapiti()) {
                String nomeComune = recapito.getDescComune();
                Integer idComune = recapito.getIdComune();
                ComuneDTO comuneRecapitoDB = null;
                if (recapito.getIdComune() != null) {
                    comuneRecapitoDB = lookupService.getComuneDTOFromId(idComune);
                }
                if (comuneRecapitoDB == null && !Strings.isNullOrEmpty(nomeComune)) {
                    comuneRecapitoDB = lookupService.getComuneDTOFromNomePreciso(nomeComune);
                }
                if (comuneRecapitoDB != null) {
                    recapito.setDescComune(comuneRecapitoDB.getDescrizione());
                    recapito.setDescProvincia(comuneRecapitoDB.getProvincia().getDescrizione());
                    recapito.setDescStato(comuneRecapitoDB.getStato().getDescrizione());
                    recapito.setIdComune(comuneRecapitoDB.getIdComune());
                    recapito.setIdProvincia(comuneRecapitoDB.getProvincia().getIdProvincie());
                    recapito.setIdStato(comuneRecapitoDB.getStato().getIdStato());
                }
                if (recapito.getIdTipoIndirizzo() != null && recapito.getDescTipoIndirizzo() == null) {
                    LkTipoIndirizzo tipoIndirizzo = lookupDao.findTipoIndirizzoById(recapito.getIdTipoIndirizzo());
                    recapito.setDescTipoIndirizzo(tipoIndirizzo.getDescrizione());
                }
            }
            mergeRecapiti(anagraficaDto, anagraficaDB);
        }

        PraticaAnagrafica collegamentoEsistente = praticheService.findPraticaAnagraficaByKey(anagrafica.getIdPratica(), anagraficaDB.getIdAnagrafica(), anagraficaDto.getIdTipoRuolo());
        if (anagraficaDB.getVarianteAnagrafica() != null && anagraficaDB.getVarianteAnagrafica() != "G") {
            if (collegamentoEsistente != null && !Utils.e(collegamentoEsistente.getFlgDittaIndividuale())) {
                String varianteAnagrafica = collegamentoEsistente.getFlgDittaIndividuale().equalsIgnoreCase("S") ? "I" : "F";
                anagraficaDto.setVarianteAnagrafica(varianteAnagrafica);
                anagraficaDB.setVarianteAnagrafica(varianteAnagrafica);
            }
        }
        if (collegamentoEsistente != null) {
            anagraficaDB.setIdTipoRuoloOriginale(collegamentoEsistente.getLkTipoRuolo().getIdTipoRuolo());
        }
        //Allinea ruoli tra le 2 anangrafiche (in anagrafica da db non ho il ruolo)
        anagraficaDB.setIdTipoRuolo(anagraficaDto.getIdTipoRuolo());
        anagraficaDB.setDesTipoRuolo(anagraficaDto.getDesTipoRuolo());
        anagraficaDB.setCounter(anagraficaDto.getCounter());
        if (Utils.e(anagraficaDB.getTipoAnagrafica())) {
            anagraficaDB.setTipoAnagrafica(anagraficaDto.getTipoAnagrafica());
        }
        if (Utils.e(anagraficaDB.getVarianteAnagrafica())) {
            anagraficaDB.setVarianteAnagrafica(anagraficaDto.getVarianteAnagrafica());
        }
        anagraficaDB.setFlgIndividuale(anagraficaDto.getFlgIndividuale());
        anagrafiche.add(anagraficaDto);//^^CS anagraficaDB DEVE!!! essere sempre 0
        anagrafiche.add(anagraficaDB);//^^CS anagraficaXML DEVE!!! essere sempre 1
        json.setRows(anagrafiche);
        return json;
    }

    private void mergeRecapiti(AnagraficaDTO anagraficaXML, AnagraficaDTO anagraficaDB) {
        List<RecapitoDTO> recapitiXML = new ArrayList<RecapitoDTO>();
        List<RecapitoDTO> recapitiDB = new ArrayList<RecapitoDTO>();
        for (String tipo : Constants.ORDINE_RECAPITI) {
            LkTipoIndirizzo tipoIndirizzo = lookupDao.findTipoIndirizzoByDesc(tipo);
            if (tipoIndirizzo != null) {
                Integer idTipoIndirizzo = tipoIndirizzo.getIdTipoIndirizzo();
                Boolean trovatoXML = false;
                Boolean trovatoDB = false;
                int indexXML = 0;
                if (anagraficaXML.getRecapiti() != null) {
                    for (RecapitoDTO recapito : anagraficaXML.getRecapiti()) {
                        if ((recapito.getIdTipoIndirizzo() != null && recapito.getIdTipoIndirizzo().equals(idTipoIndirizzo))
                                || (recapito.getDescTipoIndirizzo() != null && recapito.getDescTipoIndirizzo().equalsIgnoreCase(tipo))) {
                            trovatoXML = true;
                            recapitiXML.add(recapito);
                            break;
                        }
                        indexXML++;
                    }
                }
                int indexDB = 0;
                if (anagraficaDB.getRecapiti() != null) {
                    for (RecapitoDTO recapito : anagraficaDB.getRecapiti()) {
                        if ((recapito.getIdTipoIndirizzo() != null && recapito.getIdTipoIndirizzo().equals(idTipoIndirizzo))
                                || (recapito.getDescTipoIndirizzo() != null && recapito.getDescTipoIndirizzo().equalsIgnoreCase(tipo))) {
                            trovatoDB = true;
                            recapitiDB.add(recapito);
                            break;
                        }
                        indexDB++;
                    }
                }
                if (trovatoDB || trovatoXML) {
                    if (!trovatoDB) {
                        RecapitoDTO recapito = new RecapitoDTO();
                        recapitiDB.add(recapito);
                    }
                    if (!trovatoXML) {
                        RecapitoDTO recapito = new RecapitoDTO();
                        recapitiXML.add(recapito);
                    }
                }
            }
        }
        anagraficaXML.setRecapiti(recapitiXML);
        anagraficaDB.setRecapiti(recapitiDB);
    }

    @Transactional
    public Integer aggiungiAnagraficadXMLTransactional(AnagraficaDTO anagrafica) throws Exception {
        return aggiungiAnagraficadaXML(anagrafica);
    }

    public Integer aggiungiAnagraficadaXML(AnagraficaDTO anagrafica) throws Exception {
        Pratica pratica = praticaDao.findPratica(anagrafica.getIdPratica());
        Staging stg = stagingDao.findByCodStaging(pratica.getIdStaging().getIdStaging());
        it.wego.cross.xml.Pratica praticaXml = PraticaUtils.getPraticaFromXML(new String(stg.getXmlPratica()));
        Integer counter = praticaXml.getAnagrafiche().size() + 1;
        anagrafica.setCounter(counter);
        it.wego.cross.xml.Anagrafiche anagraficheXML = new it.wego.cross.xml.Anagrafiche();
        LkTipoRuolo ruolo = lookupDao.findTipoRuoloById(anagrafica.getIdTipoRuolo());
        anagrafica.setDaRubrica(anagrafica.getDaRubrica());
        anagrafica.setCodTipoRuolo(ruolo.getCodRuolo());
        anagrafica.setIdTipoRuolo(ruolo.getIdTipoRuolo());
        anagrafica.setDesTipoRuolo(ruolo.getDescrizione());
        anagrafica.setVarianteAnagrafica(anagrafica.getVarianteAnagrafica());
        anagraficaUtils.copyAnagrafica2XML(anagrafica, anagraficheXML);
        praticaXml.getAnagrafiche().add(anagraficheXML);
        int cnt = 0;
        for (Anagrafiche a : praticaXml.getAnagrafiche()) {
            a.getAnagrafica().setCounter(Utils.bi(cnt));
            cnt++;
        }
        JAXBContext contextObj = JAXBContext.newInstance(it.wego.cross.xml.Pratica.class);
        Marshaller marshallerObj = contextObj.createMarshaller();
        marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter praticaSTR = new StringWriter();
        marshallerObj.marshal(praticaXml, praticaSTR);
        stg.setXmlPratica(praticaSTR.toString().getBytes());
        Log.APP.info("stgdao.merge:");
        stagingDao.merge(stg);
        return counter;
    }

    @Transactional
    public void eliminaAnagraficadXML(AnagraficaDTO anagrafica) throws Exception {
        Pratica pratica = praticaDao.findPratica(anagrafica.getIdPratica());
        Staging stg = stagingDao.findByCodStaging(pratica.getIdStaging().getIdStaging());
        it.wego.cross.xml.Pratica praticaXml = PraticaUtils.getPraticaFromXML(new String(stg.getXmlPratica()));
        int i = 0;
        for (it.wego.cross.xml.Anagrafiche anagraficaxml : praticaXml.getAnagrafiche()) {
            if (anagraficaUtils.equals(anagrafica, anagraficaxml.getAnagrafica())) {
                praticaXml.getAnagrafiche().remove(i);
                break;
            }
            i++;
        }
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
