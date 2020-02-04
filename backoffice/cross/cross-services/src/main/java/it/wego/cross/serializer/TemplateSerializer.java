/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.serializer;

import it.wego.cross.dto.AllegatoDTO;
import it.wego.cross.dto.TemplateDTO;
import it.wego.cross.entity.EventiTemplate;
import it.wego.cross.entity.Templates;

/**
 *
 * @author giuseppe
 */
public class TemplateSerializer {

    public static TemplateDTO serialize(EventiTemplate template) {
        TemplateDTO dto = new TemplateDTO();
        dto.setDescrizioneTemplate(template.getIdTemplate().getDescrizione());
        dto.setIdEventoTemplate(template.getIdEventoTemplate());
        dto.setMimeType(template.getIdTemplate().getMimeType());
        dto.setNomeFile(template.getIdTemplate().getNomeFile());
        dto.setTipologiaTemplate(template.getIdTemplate().getFileOutput());
        return dto;
    }
    public static TemplateDTO serialize(Templates template) {
        TemplateDTO dto = new TemplateDTO();
        dto.setIdTemplate(template.getIdTemplate());
        dto.setDescrizioneTemplate(template.getDescrizione());
        dto.setMimeType(template.getMimeType());
        dto.setNomeFile(template.getNomeFile());
        dto.setTipologiaTemplate(template.getFileOutput());
        AllegatoDTO allegato = new AllegatoDTO();
        if(template.getTemplate()!=null)
        {
            allegato.setFileContent(template.getTemplate().getBytes());
        }
        dto.setAllegato(allegato);
        return dto;
    }
}
