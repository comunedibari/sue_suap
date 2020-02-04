/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import it.wego.cross.entity.Enti;
import it.wego.cross.entity.EventiTemplate;
import it.wego.cross.entity.Procedimenti;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.entity.Templates;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Giuseppe
 */
@Service
public interface TemplateService {

    public List<EventiTemplate> getEventiTemplate(Integer idEvento, Integer idEnte, Integer idProcedimento, Integer idTemplate);
}
