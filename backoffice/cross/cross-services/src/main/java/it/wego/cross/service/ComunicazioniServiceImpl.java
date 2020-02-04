/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import it.wego.cross.beans.AnagraficaRecapito;
import it.wego.cross.beans.AttoriComunicazione;
import it.wego.cross.beans.MailContentBean;
import it.wego.cross.dao.ComunicazioniDao;
import it.wego.cross.dao.EntiDao;
import it.wego.cross.dao.ProcedimentiDao;
import it.wego.cross.dao.UtentiDao;
import it.wego.cross.dto.AllegatoDTO;
import it.wego.cross.dto.ComunicazioneDTO;
import it.wego.cross.dto.EventoDTO;
import it.wego.cross.dto.ProcedimentiTestiDTO;
import it.wego.cross.dto.ProcedimentoDTO;
import it.wego.cross.dto.ScadenzaDTO;
import it.wego.cross.dto.TemplateDTO;
import it.wego.cross.dto.UtenteDTO;
import it.wego.cross.dto.UtenteRuoloEnteDTO;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.dto.search.DestinatariDTO;
import it.wego.cross.entity.AnagraficaRecapiti;
import it.wego.cross.entity.Comunicazione;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.EventiTemplate;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.Procedimenti;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.entity.ProcessiEventiScadenze;
import it.wego.cross.entity.Recapiti;
import it.wego.cross.entity.Utente;
import it.wego.cross.entity.view.ScadenzeDaChiudereView;
import it.wego.cross.serializer.PraticheSerializer;
import it.wego.cross.serializer.ProcedimentiSerializer;
import it.wego.cross.serializer.ScadenzeSerializer;
import it.wego.cross.serializer.TemplateSerializer;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 *
 * @author giuseppe
 */
@Service
public class ComunicazioniServiceImpl implements ComunicazioniService {

    @Autowired
    private ComunicazioniDao comunicazioniDao;
    @Autowired
    private EntiDao entiDao;
    @Autowired
    private ProcedimentiDao procedimentiDao;
    @Autowired
    private UtentiDao utentiDao;
    @Autowired
    private EventiService eventiService;
    @Autowired
    private PraticheService praticheService;
    @Autowired
    private WorkFlowService workFlowService;
    @Autowired
    private SearchService searchService;
    @Autowired
    private AnagraficheService anagraficheService;
    @Autowired
    private EntiService entiService;
    @Autowired
    private PraticheSerializer praticheSerializer;

    @Override
    public List<Comunicazione> getComunicazioniUtente(Filter filter) {
        List<Comunicazione> comunicazioni = comunicazioniDao.findComunicazioniAttiveByUtente(filter);
        return comunicazioni;
    }

    @Override
    public Long countComunicazioniUtente(Filter filter) {
        Long count = comunicazioniDao.countComunicazioniAttiveByUtente(filter);
        return count;
    }

    @Override
    public Comunicazione findComunicazioneById(Integer idComunicazione) {
        Comunicazione comunicazione = comunicazioniDao.findByIdComunicazione(idComunicazione);
        return comunicazione;
    }

    @Override
    public void salva(Comunicazione comunicazione) throws Exception {
        comunicazioniDao.insert(comunicazione);
    }

    @Override
    public ComunicazioneDTO getDettaglioComunicazione(Pratica pratica, Enti ente, EventoDTO evento, UtenteDTO user) throws Exception {
        List<ProcedimentoDTO> procedimenti = new ArrayList<ProcedimentoDTO>();
        if (evento.getVisualizzaProcedimentiRiferimento().equals("S")) {
            if (evento.getProcedimentoRiferimento() != null) {
                Procedimenti p = evento.getProcedimentoRiferimento();
                ProcedimentoDTO procedimento = ProcedimentiSerializer.serializeForEvent(p, null, "it");
                procedimenti.add(procedimento);
            } else {
                for (UtenteRuoloEnteDTO utenteRuoloEnteDTO : user.getUtenteRuoloEnte()) {
                    Enti e = entiDao.findByIdEnte(utenteRuoloEnteDTO.getIdEnte());
                    if (utenteRuoloEnteDTO.getProcedimentiList()!=null && !utenteRuoloEnteDTO.getProcedimentiList().isEmpty()){
                        for (ProcedimentiTestiDTO proc : utenteRuoloEnteDTO.getProcedimentiList()){
                            Procedimenti p = procedimentiDao.findProcedimentoByIdProc(proc.getIdProcedimento());
                            ProcedimentoDTO procedimento = ProcedimentiSerializer.serializeForEvent(p, e, "it");
                            procedimenti.add(procedimento);
                        }
                    }
                }
            }
        }
        List<DestinatariDTO> destinatari = getListDestinatari(evento.getIdEvento(), pratica, ente);
        List<AllegatoDTO> allegati = praticheService.getAllegatiPratica(pratica);

        List<ScadenzaDTO> scadenzeDaChiudere = new ArrayList<ScadenzaDTO>();
        List<ScadenzeDaChiudereView> scadenze = praticheService.getScadenzeDaChiudere(evento.getIdEvento(), pratica.getIdPratica());
        ComunicazioneDTO comunicazione = new ComunicazioneDTO();
        comunicazione.setVisualizzaScadenzeDachiudere("N");
        if (scadenze != null && !scadenze.isEmpty()) {
            for (ScadenzeDaChiudereView scadenza : scadenze) {
                ScadenzaDTO s = ScadenzeSerializer.serialize(scadenza);
                scadenzeDaChiudere.add(s);
                comunicazione.setVisualizzaScadenzeDachiudere("S");
            }
        }
        ProcessiEventi eventoProcesso = praticheService.findProcessiEventi(evento.getIdEvento());
        List<ScadenzaDTO> scadenzeCustom = new ArrayList<ScadenzaDTO>();
        List<ProcessiEventiScadenze> processiEventiScadenze = eventoProcesso.getProcessiEventiScadenzeList();
        comunicazione.setVisualizzaScadenzeCustom("N");
        for (ProcessiEventiScadenze pes : processiEventiScadenze) {
            //Verifico se vado ad aprire una scadenza
            if (pes.getFlgVisualizzaScadenza().equalsIgnoreCase("S")
                    && pes.getIdStatoScadenza().getGrpStatoScadenza().equals("A")) {
                ScadenzaDTO dto = ScadenzeSerializer.serialize(pes);
                scadenzeCustom.add(dto);
                comunicazione.setVisualizzaScadenzeCustom("S");
            }
        }
        List<TemplateDTO> templatesDaVisualizzare = new ArrayList<TemplateDTO>();
        List<EventiTemplate> templates = eventiService.getTemplates(eventoProcesso, pratica.getIdProcEnte().getIdEnte(), pratica.getIdProcEnte().getIdProc());
        if (templates != null && !templates.isEmpty()) {
            for (EventiTemplate template : templates) {
                TemplateDTO dto = TemplateSerializer.serialize(template);
                templatesDaVisualizzare.add(dto);
            }
        }
        Utente utente = utentiDao.findUtenteByIdUtente(user.getIdUtente());
        it.wego.cross.xml.Pratica praticaCross = praticheSerializer.serialize(pratica, null, utente);
        MailContentBean mailBean = workFlowService.getMailContent(praticaCross, eventoProcesso);
        evento.setOggetto(mailBean.getOggetto());
        evento.setContenuto(mailBean.getContenuto());
        String protocollo = pratica.getCodRegistro() + "/" + pratica.getAnnoRiferimento() + "/" + pratica.getProtocollo();
        comunicazione.setProtocollo(protocollo);
        comunicazione.setTemplate(templatesDaVisualizzare);
        comunicazione.setDataRicezione(pratica.getDataRicezione());
        comunicazione.setStato(pratica.getIdStatoPratica().getDescrizione());
        comunicazione.setEvento(evento);
        comunicazione.setDownloadAllegatoPratica(pratica.getIdModello() != null);
        comunicazione.setDestinatari(destinatari);
        comunicazione.setProcedimentiRiferimento(procedimenti);
        comunicazione.setAllegatiPresenti(allegati);
        comunicazione.setScadenzeCustom(scadenzeCustom);
        comunicazione.setScadenzeDaChiudere(scadenzeDaChiudere);
        comunicazione.setIdPratica(pratica.getIdPratica());

        return comunicazione;
    }

    @Override
    public void initializeComunicazione(it.wego.cross.dto.dozer.forms.ComunicazioneDTO comunicazione, Pratica pratica, ProcessiEventi processoEvento, Utente utente) throws Exception {
        it.wego.cross.xml.Pratica praticaCross = praticheSerializer.serialize(pratica, null, utente);
        MailContentBean mailBean = workFlowService.getMailContent(praticaCross, processoEvento);

        comunicazione.setContenuto(mailBean.getContenuto());
        comunicazione.setOggetto(mailBean.getOggetto());

        comunicazione.setIdPratica(pratica.getIdPratica());
        comunicazione.setIdEvento(processoEvento.getIdEvento());

        if (comunicazione.getScadenzeCustom() == null) {
            comunicazione.setScadenzeCustom(new ArrayList<ScadenzaDTO>());
        }
        for (ProcessiEventiScadenze pes : processoEvento.getProcessiEventiScadenzeList()) {
            comunicazione.getScadenzeCustom().add(new ScadenzaDTO(pes.getLkScadenze().getIdAnaScadenze(), pes.getLkScadenze().getDesAnaScadenze(), pes.getTerminiScadenza()));
        }
        if ("I".equalsIgnoreCase(processoEvento.getVerso().toString())) {
            //Tab Attivo: Mittenti
            comunicazione.setActiveTab("#frame4");
            comunicazione.setInviaEmail(Boolean.FALSE);
        } else {
            //Tab Attivo: Comuniczione
            comunicazione.setActiveTab("#frame1");
        }
    }

    @Override
    public List<DestinatariDTO> getListDestinatari(Integer idEvento, Integer idPratica, List<String> previousDestinatariIdList) throws Exception {
        Pratica pratica = praticheService.getPratica(idPratica);
        ProcessiEventi processoEvento = praticheService.findProcessiEventi(idEvento);
//        Enti ente = pratica.getIdProcEnte().getIdEnte();

        List<DestinatariDTO> destinatari = new ArrayList<DestinatariDTO>();
        AttoriComunicazione attoriComunicazione;

        if (previousDestinatariIdList != null && !previousDestinatariIdList.isEmpty()) {
            attoriComunicazione = new AttoriComunicazione();
//            Anagrafica anagraficaLoop;
            AnagraficaRecapiti anagraficaRecapitoRiferimentoAnagrafica;
            for (String id : previousDestinatariIdList) {
                String[] split = StringUtils.split(id, "|");
                if (split != null && split.length == 2) {
                    if (split[0].equalsIgnoreCase("E")) {
                        attoriComunicazione.addEnte(Integer.valueOf(split[1]));
                    } else {
                        Recapiti recapito = anagraficheService.findRecapitoById(Integer.valueOf(split[1]));
                        if (recapito.getAnagraficaRecapitiList() != null && !recapito.getAnagraficaRecapitiList().isEmpty()) {
                            if (recapito.getAnagraficaRecapitiList().size() > 1) {
                                Log.APP.error("ATTENZIONE! Un recapito è usato da più anagrafiche! NON DOVREBBE SUCCEDERE!");
                            }
                            anagraficaRecapitoRiferimentoAnagrafica = recapito.getAnagraficaRecapitiList().get(0);
                            attoriComunicazione.addAnagrafica(anagraficaRecapitoRiferimentoAnagrafica);
                        }
                    }
                }
            }
        } else {
            attoriComunicazione = workFlowService.getDestinatariDefaultEvento(pratica, processoEvento, null);
        }
        List<DestinatariDTO> dest = searchService.serializeDestinatari(pratica, attoriComunicazione);
        for (DestinatariDTO d : dest) {
            if (!Utils.e(d.getEmail())) {
                destinatari.add(d);
            }
        }
        return destinatari;
    }

    @Override
    public List<DestinatariDTO> getListDestinatari(Integer idEvento, Pratica pratica, Enti ente) throws Exception {
        ProcessiEventi pe = praticheService.findProcessiEventi(idEvento);
        AttoriComunicazione destinatari = workFlowService.getDestinatariDefaultEvento(pratica, pe, ente);
        List<DestinatariDTO> dest = searchService.serializeDestinatari(pratica, destinatari);
        List<DestinatariDTO> destfin = new ArrayList<DestinatariDTO>();
        for (DestinatariDTO d : dest) {
            if (!Utils.e(d.getEmail())) {
                destfin.add(d);
            }
        }
        return destfin;
    }

    @Override
    public List<DestinatariDTO> getListMittenti(Integer idEvento, Integer idPratica, List<String> previousMittentiIdList) throws Exception {
        Pratica pratica = praticheService.getPratica(idPratica);
        ProcessiEventi processoEvento = praticheService.findProcessiEventi(idEvento);
        Enti ente = pratica.getIdProcEnte().getIdEnte();

        List<DestinatariDTO> mittenti = new ArrayList<DestinatariDTO>();
        AttoriComunicazione attoriComunicazione;

        if (previousMittentiIdList != null && !previousMittentiIdList.isEmpty()) {
            attoriComunicazione = new AttoriComunicazione();
//            Anagrafica anagraficaLoop;
            AnagraficaRecapiti anagraficaRecapitoRiferimentoAnagrafica;
            for (String id : previousMittentiIdList) {
                String[] split = StringUtils.split(id, "|");
                if (split != null && split.length == 2) {
                    if (split[0].equalsIgnoreCase("E")) {
                        attoriComunicazione.addEnte(Integer.valueOf(split[1]));
                    } else {
                        Recapiti recapito = anagraficheService.findRecapitoById(Integer.valueOf(split[1]));
                        if (recapito.getAnagraficaRecapitiList() != null && !recapito.getAnagraficaRecapitiList().isEmpty()) {
                            if (recapito.getAnagraficaRecapitiList().size() > 1) {
                                Log.APP.error("ATTENZIONE! Un recapito è usato da più anagrafiche! NON DOVREBBE SUCCEDERE!");
                            }
                            anagraficaRecapitoRiferimentoAnagrafica = recapito.getAnagraficaRecapitiList().get(0);
                            attoriComunicazione.addAnagrafica(anagraficaRecapitoRiferimentoAnagrafica);
                        }
                    }
                }
            }
            List<DestinatariDTO> mitt = searchService.serializeDestinatari(pratica, attoriComunicazione);
            for (DestinatariDTO d : mitt) {
                if (!Utils.e(d.getEmail())) {
                    mittenti.add(d);
                }
            }
        }

        return mittenti;
    }

    public List<TemplateDTO> getTemplates(Integer idEvento, Integer idEnte, Integer idProcedimento) throws Exception {
        ProcessiEventi processoEvento = getProcessoEvento(idEvento);
        Enti ente = getEnte(idEnte);
        Procedimenti procedimento = procedimentiDao.findProcedimentoByIdProc(idProcedimento);

        List<TemplateDTO> templatesDaVisualizzare = new ArrayList<TemplateDTO>();
        List<EventiTemplate> templates = eventiService.getTemplates(processoEvento, ente, procedimento);
        for (EventiTemplate template : templates) {
            TemplateDTO dto = TemplateSerializer.serialize(template);
            templatesDaVisualizzare.add(dto);
        }
        return templatesDaVisualizzare;
    }

    public ProcessiEventi getProcessoEvento(Integer idEvento) {
        ProcessiEventi eventoProcesso = praticheService.findProcessiEventi(idEvento);
        return eventoProcesso;
    }

    public Enti getEnte(Integer idEnte) {
        return entiService.findByIdEnte(idEnte);
    }
}
