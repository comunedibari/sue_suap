/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import com.google.common.base.Strings;
import it.wego.cross.dto.ProcedimentoDTO;
import it.wego.cross.dto.EnteDTO;
import it.wego.cross.dto.TemplateDTO;
import it.wego.cross.dto.EventoTemplateDTO;
import it.wego.cross.dto.ProcessoDTO;
import it.wego.cross.dto.StepDTO;
import it.wego.cross.dto.EventoDTO;
import it.wego.cross.serializer.ProcessiSerializer;
import it.wego.cross.serializer.ProcedimentiSerializer;
import it.wego.cross.serializer.EventiTemplateSerializer;
import it.wego.cross.serializer.TemplateSerializer;
import it.wego.cross.serializer.PraticheSerializer;
import it.wego.cross.serializer.EventiSerializer;
import it.wego.cross.serializer.EntiSerializer;
import it.wego.cross.beans.MailContentBean;
import it.wego.cross.dao.*;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.*;
import it.wego.cross.plugins.documenti.GestioneAllegati;
import it.wego.cross.plugins.protocollo.beans.DocumentoProtocolloResponse;
import it.wego.cross.utils.Utils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author CS
 */
@Service
public class EventiServiceImpl implements EventiService {

    @Autowired
    private ProcessiService processiService;
    @Autowired
    private ProcessiDao processiDao;
    @Autowired
    private EntiDao entiDao;
    @Autowired
    private ProcedimentiDao procedimentiDao;
    @Autowired
    private TemplateDao templateDao;
    @Autowired
    private WorkFlowService workFlowService;
    @Autowired
    private PraticheService praticheService;
    @Autowired
    private PraticaDao praticaDao;
    @Autowired
    private PraticheSerializer praticheSerializer;
    @Autowired
    private EventiSerializer eventiSerializer;
    @Autowired
    protected AllegatiDao allegatiDao;
    @Autowired
    protected PluginService pluginService;

    @Override
    public List<EventoDTO> getTuttiProcessiEventi() {
        List<EventoDTO> eventi = new ArrayList<EventoDTO>();
        List<ProcessiEventi> listaeveni = processiDao.findTuttiProcessiEventi();
        for (ProcessiEventi eventoDB : listaeveni) {
            EventoDTO evento = eventiSerializer.serializeEvento(eventoDB);
            eventi.add(evento);
        }
        return eventi;
    }

    @Override
    public List<ProcessoDTO> findAllProcessi() {
        List<ProcessoDTO> dtos = new ArrayList<ProcessoDTO>();
        List<Processi> processi = processiDao.findAllProcessi();
        for (Processi p : processi) {
            ProcessoDTO dto = ProcessiSerializer.serilize(p);
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public List<EventoDTO> findAllProcessiEventi() {
        List<EventoDTO> eventi = new ArrayList<EventoDTO>();
        List<ProcessiEventi> processiEventi = processiDao.findAllProcessiEventi();
        for (ProcessiEventi eventoDB : processiEventi) {
            EventoDTO evento = eventiSerializer.serializeEvento(eventoDB);
            if (eventoDB.getIdProcesso() != null) {
                evento.setDesProcesso(eventoDB.getIdProcesso().getDesProcesso());
            }
            eventi.add(evento);
        }
        return eventi;
    }

    @Override
    public List<EventoDTO> findEventi(EventoDTO eventodto, Filter filter) {
        List<EventoDTO> eventi = new ArrayList<EventoDTO>();
        List<ProcessiEventi> listaeveni = processiDao.findProcessiEventi(filter);
        for (ProcessiEventi eventoDB : listaeveni) {
            EventoDTO evento = eventiSerializer.serializeEvento(eventoDB);
            eventi.add(evento);
        }
        return eventi;
    }

    @Override
    public List<EnteDTO> getTuttiEnti() {
        List<EnteDTO> eventi = new ArrayList<EnteDTO>();
        List<Enti> lista = entiDao.findAll(new Filter());
        for (Enti dato : lista) {
            EnteDTO evento = EntiSerializer.serializer(dato);
            eventi.add(evento);
        }
        return eventi;
    }

    @Override
    public List<ProcedimentoDTO> getTuttiProcedimenti() {
        List<ProcedimentoDTO> listaDTO = new ArrayList<ProcedimentoDTO>();
        List<Procedimenti> listaDB = procedimentiDao.findTutti();
        for (Procedimenti dato : listaDB) {
            ProcedimentoDTO dto = ProcedimentiSerializer.serialize(dato);
            listaDTO.add(dto);
        }
        return listaDTO;
    }

    @Override
    public List<TemplateDTO> getTemplates(Filter filter) throws Exception {
        List<TemplateDTO> lista = new ArrayList<TemplateDTO>();
        List<Templates> listaTemplate = templateDao.getTuttiTemplates(filter);
        for (Templates temp : listaTemplate) {
            TemplateDTO templ = TemplateSerializer.serialize(temp);
            lista.add(templ);
        }
        return lista;
    }

    @Override
    public void salvaEventiTempate(EventoTemplateDTO eventotemplateDTO) throws Exception {
        EventiTemplate eventoTemplate = null;
        if (eventotemplateDTO.getIdEventoTemplate() != null) {
            eventoTemplate = templateDao.getEventoTemplatesPerID(eventotemplateDTO.getIdEventoTemplate());
        }
        Enti ente = null;
        if (eventotemplateDTO.getIdEnte() != null) {
            ente = entiDao.findByIdEnte(eventotemplateDTO.getIdEnte());
        }
        ProcessiEventi evento = null;
        if (eventotemplateDTO.getIdEvento() != null) {
            evento = processiDao.findByIdEvento(eventotemplateDTO.getIdEvento());
        }
        Procedimenti proc = null;
        if (eventotemplateDTO.getIdProcedimento() != null) {
            proc = procedimentiDao.findProcedimentoByIdProc(eventotemplateDTO.getIdProcedimento());
        }
        Templates template = templateDao.getTemplatesPerID(eventotemplateDTO.getIdTemplate());
        if (eventoTemplate == null) {
            //^^CS NUOVO
            eventoTemplate = new EventiTemplate();
            eventoTemplate.setIdEnte(ente);
            eventoTemplate.setIdEvento(evento);
            eventoTemplate.setIdProcedimento(proc);
            eventoTemplate.setIdTemplate(template);
            templateDao.insert(eventoTemplate);
        } else {
            //^^CS MODIFICA
            eventoTemplate.setIdEnte(ente);
            eventoTemplate.setIdEvento(evento);
            eventoTemplate.setIdProcedimento(proc);
            eventoTemplate.setIdTemplate(template);
            templateDao.update(eventoTemplate);
        }
    }

    @Override
    public void eliminaEventiTempate(EventoTemplateDTO eventotemplateDTO) throws Exception {
        EventiTemplate eventoTemplate = templateDao.getEventoTemplatesPerID(eventotemplateDTO.getIdEventoTemplate());
        templateDao.delete(eventoTemplate);
    }

    @Override
    public List<EventiTemplate> getTemplates(ProcessiEventi eventoProcesso, Enti ente, Procedimenti procedimento) {
        List<EventiTemplate> eventiTemplate = templateDao.getTemplatesPerEnte(eventoProcesso, ente, procedimento);
        return eventiTemplate;
    }

    @Override
    public List<EventoTemplateDTO> findEventiTemplate(Filter filter) throws Exception {
        List<EventiTemplate> eventiTemplate = templateDao.findEventiTemplate(filter);
        return EventiTemplateSerializer.serialize(eventiTemplate);
    }

    @Override
    public List<EventoTemplateDTO> findDescEventiTemplate(Filter filter) throws Exception {
        List<EventiTemplate> eventiTemplate = templateDao.findEventiTemplate(filter);
        return EventiTemplateSerializer.serialize(eventiTemplate);
    }

    @Override
    public EventoTemplateDTO getTemplates(EventoTemplateDTO eventoTemplate) {
        ProcessiEventi evento = processiDao.findByIdEvento(eventoTemplate.getIdEvento());
        Enti ente = entiDao.findByIdEnte(eventoTemplate.getIdEnte());
        Procedimenti proc = procedimentiDao.findProcedimentoByIdProc(eventoTemplate.getIdProcedimento());

        List<EventiTemplate> eventiTemplate = templateDao.getTemplatesPerProcedimento(evento, ente, proc);
        if (eventiTemplate != null && eventiTemplate.size() > 0) {
            return EventiTemplateSerializer.serialize(eventiTemplate.get(0));
        }
        return null;
    }

    @Override
    public TemplateDTO getTemplate(TemplateDTO templateDTO) throws Exception {
        Templates tep = new Templates();
        if (templateDTO.getIdTemplate() != null) {
            tep.setIdTemplate(templateDTO.getIdTemplate());
            tep = templateDao.getTemplatesPerID(tep);
            templateDTO = TemplateSerializer.serialize(tep);
        }
//        //TODO: controlla perché non si capisce a cosa servano
//        List<EnteDTO> enti = new ArrayList<EnteDTO>();
//        List<EventoDTO> eventi = new ArrayList<EventoDTO>();
//        List<ProcedimentoDTO> procedimenti = new ArrayList<ProcedimentoDTO>();
//        for (EventiTemplate eventiTemplate : tep.getEventiTemplateList()) {
//            if (eventiTemplate.getIdEnte() != null) {
//                enti.add(EntiSerializer.serializer(eventiTemplate.getIdEnte()));
//            } else {
//                enti.add(null);
//            }
//
//            eventi.add(EventiSerializer.serializer(eventiTemplate.getIdEvento()));
//            if (eventiTemplate.getIdProcedimento() != null) {
//                procedimenti.add(ProcedimentiSerializer.serialize(eventiTemplate.getIdProcedimento()));
//            } else {
//                procedimenti.add(null);
//            }
//        }

        return templateDTO;
    }

    @Override
    public void salvaTemplates(TemplateDTO templateDTO) throws Exception {
        if (templateDTO.getIdTemplate() == null) {
            it.wego.cross.entity.Templates template = new it.wego.cross.entity.Templates();
            template.setDescrizione(templateDTO.getDescrizioneTemplate());
            template.setFileOutput(templateDTO.getTipologiaTemplate());
            String mime = getMimeType(templateDTO.getTipologiaTemplate());
            template.setMimeType(mime);
            String nomeFile = templateDTO.getNomeFile().split("\\.(?=[^\\.]+$)")[0] + getExtensione(mime);
            template.setNomeFile(nomeFile);
            template.setNomeFileOriginale(templateDTO.getAllegato().getFile().getOriginalFilename());
            Base64 base64 = new Base64();
            template.setTemplate(base64.encodeAsString(templateDTO.getAllegato().getFile().getBytes()));    //base64
            templateDao.insert(template);
        } else {
            it.wego.cross.entity.Templates template = templateDao.getTemplatesPerID(templateDTO.getIdTemplate());
            template.setDescrizione(templateDTO.getDescrizioneTemplate());
            if (templateDTO.getAllegato() != null && templateDTO.getAllegato().getFile() != null && templateDTO.getAllegato().getFile().getSize() > 0) {
                template.setTemplate(Base64.encodeBase64String(templateDTO.getAllegato().getFile().getBytes()));
                template.setNomeFileOriginale(templateDTO.getAllegato().getFile().getOriginalFilename());
            }
            String mime = getMimeType(templateDTO.getTipologiaTemplate());
            template.setMimeType(mime);
            String nomeFile = templateDTO.getNomeFile().split("\\.(?=[^\\.]+$)")[0] + getExtensione(mime);
            template.setNomeFile(nomeFile);
            template.setFileOutput(templateDTO.getTipologiaTemplate());
            templateDao.update(template);
        }
    }

    private String getMimeType(String fileOutput) {
        if (fileOutput.equals("DOC")) {
            //DOC
            return "application/msword";
        } else if (fileOutput.equals("RTF")) {
            //RTF
            return "application/rtf";
        } else {
            //PDF
            return "application/pdf";
        }
    }

    private String getExtensione(String mimeType) {
        if (mimeType.equalsIgnoreCase("application/msword")) {
            return ".doc";
        } else if (mimeType.equalsIgnoreCase("application/rtf")) {
            return ".rtf";
        } else {
            return ".pdf";
        }
    }

    @Override
    public void eliminaTemplates(TemplateDTO templateDTO) throws Exception {
        it.wego.cross.entity.Templates template = templateDao.getTemplatesPerID(templateDTO.getIdTemplate());
        templateDao.delete(template);
    }

    @Override
    public EventoDTO cambiaFlag(EventoDTO eventoDTO) throws Exception {
        it.wego.cross.entity.ProcessiEventi eventoDB = processiDao.findByIdEvento(eventoDTO.getIdEvento());
        if (eventoDB != null) {
            eventoDB.setFlgPortale(Utils.flag(eventoDTO.getPubblicazionePortale()).toString());
            processiDao.update(eventoDB);
        }
        PraticheEventi evento = praticaDao.getDettaglioPraticaEvento(eventoDTO.getIdPraticaEvento());
        evento.setVisibilitaUtente(eventoDTO.getPubblicazionePortale().toString());
        praticaDao.aggiornaPraticheEvento(evento, null);
        return eventoDTO;
    }

    @Override
    public ProcessiEventi findProcessiEventiById(Integer idEvento) throws Exception {
        ProcessiEventi evento = processiDao.findByIdEvento(idEvento);
        return evento;
    }

    @Override
    public List<EventoDTO> findEventiByIdProcesso(Integer idProcesso, Filter filter) {
        Processi processo = processiService.findProcessoById(idProcesso);
        List<ProcessiEventi> processiEventi = procedimentiDao.findByIdProcesso(idProcesso, filter);
        List<EventoDTO> eventi = new ArrayList<EventoDTO>();
        for (ProcessiEventi pe : processiEventi) {
            EventoDTO evento = new EventoDTO();
            evento.setIdEvento(pe.getIdEvento());
            evento.setDescrizione(pe.getDesEvento());
            evento.setDesProcesso(processo.getDesProcesso());
            evento.setCodEvento(pe.getCodEvento());
            eventi.add(evento);
        }
        return eventi;
    }

    @Override
    public Long countEventiByIdProcesso(Integer idProcesso, Filter filter) {
        Long count = procedimentiDao.countProcessiEventiByIdProcesso(idProcesso, filter);
        return count;
    }

    @Override
    public void cancellaEvento(ProcessiEventi processoEvento) throws Exception {
        processiDao.delete(processoEvento);
    }

    @Override
    public List<StepDTO> getSteps(Integer idEvento, Filter filter) {
        List<StepDTO> steps = processiDao.findStepsByIdEvento(idEvento, filter);
        return steps;
    }

    @Override
    public List<EventoDTO> getListaEventi(Integer idPratica) throws Exception {
        return getListaEventi(idPratica, null);
    }

    @Override
    public List<EventoDTO> getListaEventi(Integer idPratica, String verso) throws Exception {
        List<ProcessiEventi> processiEventi = workFlowService.findAvailableEvents(idPratica);
        List<EventoDTO> eventi = new LinkedList<EventoDTO>();
        for (ProcessiEventi pe : processiEventi) {
            if (Strings.isNullOrEmpty(verso)) {
                EventoDTO evento = new EventoDTO();
                evento.setIdEvento(pe.getIdEvento());
                evento.setDescrizione(pe.getDesEvento());
                evento.setStatoPost(pe.getStatoPost());
                evento.setVerso((pe.getVerso() != null && String.valueOf(pe.getVerso()).equals("I")) ? "INPUT" : "OUTPUT");
                eventi.add(evento);
            } else {
                if (verso.equalsIgnoreCase(String.valueOf(pe.getVerso()))) {
                    EventoDTO evento = new EventoDTO();
                    evento.setIdEvento(pe.getIdEvento());
                    evento.setDescrizione(pe.getDesEvento());
                    evento.setStatoPost(pe.getStatoPost());
                    evento.setVerso((pe.getVerso() != null && String.valueOf(pe.getVerso()).equals("I")) ? "INPUT" : "OUTPUT");
                    eventi.add(evento);
                }
            }
        }
        Collections.sort(eventi);
        return eventi;
    }

    @Override
    public EventoDTO getDettaglioEvento(Integer idEvento, Pratica pratica) throws Exception {
        EventoDTO evento = praticheService.getEvento(idEvento);
        ProcessiEventi pe = praticheService.findProcessiEventi(idEvento);
        /**
         * INIZIO : OVERRIDE DELL'OGGETTO e DEL CONTENUTO DELLA MAIL CON IL
         * RISULTATO DEL TEMPALTE*
         */
        it.wego.cross.xml.Pratica praticaXml = praticheSerializer.serialize(pratica, null, null);
        MailContentBean mailBean = workFlowService.getMailContent(praticaXml, pe);
        evento.setOggetto(mailBean.getOggetto());
        evento.setContenuto(mailBean.getContenuto());
        return evento;
    }

    @Override
    public void aggiornaAllegatiConRiferimentiProtocollo(Pratica pratica, DocumentoProtocolloResponse protocollo, List<Allegati> allegatiEntity, Integer idEnte) throws Exception {
        //TODO: gestire il caso Comune su Ente
        GestioneAllegati ga = pluginService.getGestioneAllegati(idEnte, null);
        //attenzione se si sceglie un allegato  
        //con descrizione e nome file identici a uno degli allegati caricati manualmente
        //si ha id_file_esterno doppio.
        //soluzione: picchiare chi carica piu volte, contemporanemanete, allegati con la stessa descrizione e stesso nome file .

        for (Allegati allegato : allegatiEntity) {
            if (protocollo.getAllegatoOriginale() != null 
                    && protocollo.getAllegatoOriginale().getNomeFile().equalsIgnoreCase(allegato.getNomeFile())) {
                //Aggiorno il puntamento solo se idFileEsterno non è valorizzato. Server per fixare il tiket 1495
                if (Strings.isNullOrEmpty(allegato.getIdFileEsterno())) {
                    allegato.setIdFileEsterno(protocollo.getAllegatoOriginale().getIdEsterno());
                    ga.add(allegato, pratica.getIdProcEnte().getIdEnte(), pratica.getIdComune());
                    allegatiDao.mergeAllegato(allegato);
                    continue;
                }
            }
            for (it.wego.cross.plugins.commons.beans.Allegato a : protocollo.getAllegati()) {
                if (a.getNomeFile().equalsIgnoreCase(allegato.getNomeFile())) {
                    //Aggiorno il puntamento solo se idFileEsterno non è valorizzato. Server per fixare il tiket 1495
                    if (Strings.isNullOrEmpty(allegato.getIdFileEsterno())) {
                        allegato.setIdFileEsterno(a.getIdEsterno());
                        ga.add(allegato, pratica.getIdProcEnte().getIdEnte(), pratica.getIdComune());
                        allegatiDao.mergeAllegato(allegato);
                        break;
                    }
                }
            }
        }
    }
}
