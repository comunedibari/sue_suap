/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.actions;

import it.wego.cross.dto.EventoTemplateDTO;
import it.wego.cross.dto.TemplateDTO;
import it.wego.cross.service.EventiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TemplateAction {

    @Autowired
    private EventiService eventiService;

    @Transactional
    public void salvaTemplate(TemplateDTO template) throws Exception {
        eventiService.salvaTemplates(template);
    }

    @Transactional
    public void eliminaTemplate(TemplateDTO template) throws Exception {
        eventiService.eliminaTemplates(template);
    }

    @Transactional
    public void salvaEventoTemplate(EventoTemplateDTO eventoTemplate) throws Exception {
        eventiService.salvaEventiTempate(eventoTemplate);
    }

    @Transactional
    public void eliminaEventoTemplate(EventoTemplateDTO eventoTemplate) throws Exception {
        eventiService.eliminaEventiTempate(eventoTemplate);
    }
}
