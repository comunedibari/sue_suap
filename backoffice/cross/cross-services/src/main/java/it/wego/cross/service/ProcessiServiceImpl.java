package it.wego.cross.service;

import it.wego.cross.dao.LookupDao;
import it.wego.cross.dao.ProcedimentiDao;
import it.wego.cross.dao.ProcessiDao;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.dto.ProcessoDTO;
import it.wego.cross.entity.ProcedimentiEnti;
import it.wego.cross.entity.Processi;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.entity.ProcessiEventiScadenze;
import it.wego.cross.entity.ProcessiSteps;
import it.wego.cross.serializer.ProcessiSerializer;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author CS
 */
@Service
public class ProcessiServiceImpl implements ProcessiService {

    @Autowired
    private ProcessiDao processiDao;

    @Override
    public List<ProcessoDTO> getListaProcessi(Filter filter) {
        List<ProcessoDTO> processiDTO = new ArrayList<ProcessoDTO>();
        List<Processi> processiDB = processiDao.findProcessi(filter);
        for (Processi processoDB : processiDB) {
            ProcessoDTO processo = ProcessiSerializer.serilize(processoDB);
            processiDTO.add(processo);
        }
        return processiDTO;
    }

    @Override
    public Long countListaProcessi() {
        Long count = processiDao.countProcessi();
        return count;
    }

    @Override
    public Processi findProcessoById(Integer idProcesso) {
        return processiDao.findProcessiById(idProcesso);
    }

   @Override
    public ProcessiEventi findProcessiEventiByIdEvento(Integer idEvento) {
        ProcessiEventi pe = processiDao.findByIdEvento(idEvento);
        return pe;
    }

    @Override
    public ProcessiSteps findStepByKey(Integer idEventoTrigger, Integer idEventoResult) {
        ProcessiSteps step = processiDao.findByKey(idEventoTrigger, idEventoResult);
        return step;
    }

    @Override
    public List<ProcessiEventiScadenze> findScadenzaProcessoEventoByIdEvento(Integer idEvento) {
        List<ProcessiEventiScadenze> scadenze = processiDao.findScadenzaProcessoEventoByIdEvento(idEvento);
        return scadenze;
    }

    @Override
    public ProcessiEventiScadenze findScadenzaByIdEventoIdAnaScadenza(Integer idEvento, String idAnaScadenza) {
        ProcessiEventiScadenze scadenza = processiDao.findScadenzaProcessoEvento(idEvento, idAnaScadenza);
        return scadenza;
    }

    @Override
    public ProcessiEventi findProcessiEventiByCodEventoIdProcesso(String codEvento, Processi processo) {
        ProcessiEventi eventoProcesso = processiDao.findProcessiEventiByCodEventoIdProcesso(codEvento, processo.getIdProcesso());
        return eventoProcesso;
    }
    
    @Override
        public List<ProcessoDTO> getListaProcessi(Filter filter, ProcedimentiEnti procedimento) {
        List<ProcessoDTO> processiDTO = getListaProcessi(filter);
        if (procedimento != null && procedimento.getIdProcesso() != null) {
            for (ProcessoDTO processo : processiDTO) {
                if (processo.getIdProcesso().equals(procedimento.getIdProcesso().getIdProcesso())) {
                    processo.setSelezionato(Boolean.TRUE);
                } else {
                    processo.setSelezionato(Boolean.FALSE);
                }
            }
        }
        return processiDTO;
    }

    @Override
    public ProcessiEventi requireEventoDiSistema(String codEvento, String desEvento, Processi processo) {
        ProcessiEventi evento = findProcessiEventiByCodEventoIdProcesso(codEvento, processo);
        if (evento == null){
            evento = new ProcessiEventi();
            evento.setIdProcesso(processo);
            evento.setCodEvento(codEvento);
            evento.setDesEvento(desEvento);
            evento.setScriptScadenzaEvento("N");
            evento.setVerso('I');
            evento.setFlgPortale("N");
            evento.setFlgMail("N");
            evento.setFlgAllMail("N");
            evento.setFlgProtocollazione("N");
            evento.setFlgRicevuta("N");
            evento.setFlgDestinatari("N");
            evento.setFlgFirmato("N");
            evento.setFlgApriSottopratica("N");
            evento.setFlgDestinatariSoloEnti("N");
            evento.setIdScriptEvento("N");
            evento.setIdScriptProtocollo("N");
            evento.setFunzioneApplicativa("system");
            evento.setFlgAutomatico("N");
            evento.setForzaChiusuraScadenze("N");
            processiDao.insert(evento);
            processiDao.flush();
        }
        return evento;
    }
}
