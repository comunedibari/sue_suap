/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import it.wego.cross.dto.filters.Filter;
import it.wego.cross.dto.ProcessoDTO;
import it.wego.cross.entity.ProcedimentiEnti;
import it.wego.cross.entity.Processi;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.entity.ProcessiEventiScadenze;
import it.wego.cross.entity.ProcessiSteps;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author giuseppe
 */
@Service
public interface ProcessiService {

    public Long countListaProcessi();

    public List<ProcessoDTO> getListaProcessi(Filter filter);

    public Processi findProcessoById(Integer idProcesso);

    public ProcessiEventi findProcessiEventiByIdEvento(Integer idEvento);

    public ProcessiSteps findStepByKey(Integer idEventoTrigger, Integer idEventoResult);

    public List<ProcessiEventiScadenze> findScadenzaProcessoEventoByIdEvento(Integer idEvento);

    public ProcessiEventiScadenze findScadenzaByIdEventoIdAnaScadenza(Integer idEvento, String idAnaScadenza);

    public ProcessiEventi findProcessiEventiByCodEventoIdProcesso(String codEvento, Processi processo);
    
    public ProcessiEventi requireEventoDiSistema(String codEvento, String desEvento, Processi processo);
    
    public List<ProcessoDTO> getListaProcessi(Filter filter, ProcedimentiEnti procedimento);
}
