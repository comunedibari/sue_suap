/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import it.wego.cross.dao.TemplateDao;
import it.wego.cross.entity.EventiTemplate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Giuseppe
 */
@Service
public class TemplateServiceImpl implements TemplateService {

    @Autowired
    private TemplateDao templateDao;
    
    @Override
    public List<EventiTemplate> getEventiTemplate(Integer idEvento, Integer idEnte, Integer idProcedimento, Integer idTemplate) {
        return templateDao.findTemplateSuEvento(idEvento, idEnte, idProcedimento, idTemplate);
    }
}
