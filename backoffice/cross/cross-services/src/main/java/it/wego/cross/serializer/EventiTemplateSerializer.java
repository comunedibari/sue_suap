/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.serializer;

import it.wego.cross.beans.file.MultipartFileUploadBean;
import it.wego.cross.dao.EntiDao;
import it.wego.cross.dao.LookupDao;
import it.wego.cross.dao.ProcedimentiDao;
import it.wego.cross.dao.ProcessiDao;
import it.wego.cross.dao.TemplateDao;
import it.wego.cross.dto.EventoTemplateDTO;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.EventiTemplate;
import it.wego.cross.entity.Procedimenti;
import it.wego.cross.entity.ProcessiEventi;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 *
 * @author Gabriele
 */
@Component
public class EventiTemplateSerializer {

    @Autowired
    private LookupDao lookupDao;
    @Autowired
    private ProcessiDao processiDao;
    @Autowired
    private EntiDao entiDao;
    @Autowired
    private ProcedimentiDao procedimentiDao;
    @Autowired
    private TemplateDao templateDao;

    /**
     * ^^CS Manca la serializzazione del file
     *
     * @param eventoTemplate
     * @return
     */
    public static EventoTemplateDTO serialize(EventiTemplate eventoTemplate) {
        EventoTemplateDTO eventoTemplateDTO = new EventoTemplateDTO();
        eventoTemplateDTO.setIdEventoTemplate(eventoTemplate.getIdEventoTemplate());
        if (eventoTemplate.getIdEnte() != null) {
            eventoTemplateDTO.setDescEnte(eventoTemplate.getIdEnte().getDescrizione());
            eventoTemplateDTO.setIdEnte(eventoTemplate.getIdEnte().getIdEnte());
        }
        if (eventoTemplate.getIdEvento() != null) {
            eventoTemplateDTO.setDescEvento(eventoTemplate.getIdEvento().getDesEvento());
            eventoTemplateDTO.setIdEvento(eventoTemplate.getIdEvento().getIdEvento());
            eventoTemplateDTO.setDescProcesso(eventoTemplate.getIdEvento().getIdProcesso().getDesProcesso());
            eventoTemplateDTO.setIdProcesso(eventoTemplate.getIdEvento().getIdProcesso().getIdProcesso());
        }
        if (eventoTemplate.getIdProcedimento() != null) {
            //^^CS ATTENZIONE NON VALE SEMPRE!!
            eventoTemplateDTO.setDescProcedimento(eventoTemplate.getIdProcedimento().getProcedimentiTestiList().get(0).getDesProc());
            eventoTemplateDTO.setIdProcedimento(eventoTemplate.getIdProcedimento().getIdProc());
        }
        if (eventoTemplate.getIdTemplate() != null) {
            eventoTemplateDTO.setIdTemplate(eventoTemplate.getIdTemplate().getIdTemplate());
            eventoTemplateDTO.setNomefile(eventoTemplate.getIdTemplate().getNomeFile());
            eventoTemplateDTO.setDescTemplate(eventoTemplate.getIdTemplate().getDescrizione());
        }
        return eventoTemplateDTO;
    }

    public void serialize(EventoTemplateDTO eventoTemplateDTO, EventiTemplate eventoTemplateDB) throws Exception {

        if (eventoTemplateDB.getIdEventoTemplate() == null && eventoTemplateDTO.getIdEventoTemplate() != null) {
            eventoTemplateDB = templateDao.getEventoTemplatesPerID(eventoTemplateDTO.getIdEventoTemplate());
        }

        if (eventoTemplateDTO.getIdEvento() != null) {
            ProcessiEventi processiEvento = processiDao.findByIdEvento(eventoTemplateDTO.getIdEvento());
            eventoTemplateDB.setIdEvento(processiEvento);
        }

        if (eventoTemplateDTO.getIdEnte() != null) {
            Enti ente = entiDao.findByIdEnte(eventoTemplateDTO.getIdEnte());
            eventoTemplateDB.setIdEnte(ente);
        }

        if (eventoTemplateDTO.getIdProcedimento() != null) {
            Procedimenti procedimento = procedimentiDao.findProcedimentoByIdProc(eventoTemplateDTO.getIdProcedimento());
            eventoTemplateDB.setIdProcedimento(procedimento);
        }

        if (eventoTemplateDTO.getIdTemplate() != null) {
            it.wego.cross.entity.Templates template = templateDao.getTemplatesPerID(eventoTemplateDTO.getIdTemplate());
            if (eventoTemplateDTO.getFile() != null) {
                template.setDescrizione(eventoTemplateDTO.getDescTemplate());
                template.setMimeType(eventoTemplateDTO.getFile().getContentType());
                template.setNomeFile(eventoTemplateDTO.getFile().getOriginalFilename());
                Base64 base64 = new Base64();
                template.setTemplate(base64.encodeAsString(eventoTemplateDTO.getFile().getBytes()));
            }
            eventoTemplateDB.setIdTemplate(template);
        }
    }

    public static List<EventoTemplateDTO> serialize(List<EventiTemplate> eventoTemplate) {
        List<EventoTemplateDTO> listEventi = new ArrayList<EventoTemplateDTO>();

        for (EventiTemplate evento : eventoTemplate) {
            EventoTemplateDTO eventoDTO = new EventoTemplateDTO();
            if (evento != null) {
                eventoDTO = serialize(evento);
            }
            
            listEventi.add(eventoDTO);
        }
        return listEventi;
    }
}
