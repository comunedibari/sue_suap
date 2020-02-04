/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import it.wego.cross.dto.ProcedimentoDTO;
import it.wego.cross.dto.EnteDTO;
import it.wego.cross.dto.TemplateDTO;
import it.wego.cross.dto.EventoTemplateDTO;
import it.wego.cross.dto.ProcessoDTO;
import it.wego.cross.dto.StepDTO;
import it.wego.cross.dto.EventoDTO;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.Allegati;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.EventiTemplate;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.Procedimenti;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.plugins.protocollo.beans.DocumentoProtocolloResponse;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Service per la gestione della pratiche
 *
 * @author CS
 */
@Service
public interface EventiService {

    public List<EventoDTO> getTuttiProcessiEventi();

    public List<EventoDTO> findAllProcessiEventi();

    public List<EnteDTO> getTuttiEnti();

    public List<ProcedimentoDTO> getTuttiProcedimenti();

    public List<TemplateDTO> getTemplates(Filter filter) throws Exception;

    public void salvaEventiTempate(EventoTemplateDTO EventotemplateDTO) throws Exception;

    public void eliminaEventiTempate(EventoTemplateDTO EventotemplateDTO) throws Exception;

    public EventoTemplateDTO getTemplates(EventoTemplateDTO eventoTemplate);

    public List<EventoTemplateDTO> findEventiTemplate(Filter filter) throws Exception;

    public List<EventoTemplateDTO> findDescEventiTemplate(Filter filter) throws Exception;

    public void salvaTemplates(TemplateDTO template) throws Exception;

    public void eliminaTemplates(TemplateDTO template) throws Exception;

    public List<EventiTemplate> getTemplates(ProcessiEventi eventoProcesso, Enti ente, Procedimenti procedimento);

    public TemplateDTO getTemplate(TemplateDTO templateDTO) throws Exception;

    public EventoDTO cambiaFlag(EventoDTO evento) throws Exception;

    public List<EventoDTO> findEventi(EventoDTO evento, Filter filter);

    public ProcessiEventi findProcessiEventiById(Integer idEvento) throws Exception;

    public List<EventoDTO> findEventiByIdProcesso(Integer idProcesso, Filter filter);

    public Long countEventiByIdProcesso(Integer idProcesso, Filter filter);

    public void cancellaEvento(ProcessiEventi processoEvento) throws Exception;

    public List<StepDTO> getSteps(Integer idEvento, Filter filter);

    public List<ProcessoDTO> findAllProcessi();
    
    public List<EventoDTO> getListaEventi(Integer idPratica) throws Exception;
    
    public List<EventoDTO> getListaEventi(Integer idPratica, String verso) throws Exception;
    
    public EventoDTO getDettaglioEvento(Integer idEvento, Pratica pratica) throws Exception;
    
    public void aggiornaAllegatiConRiferimentiProtocollo(Pratica pratica, DocumentoProtocolloResponse protocollo, List<Allegati> allegatiEntity, Integer idEnte) throws Exception;

}
