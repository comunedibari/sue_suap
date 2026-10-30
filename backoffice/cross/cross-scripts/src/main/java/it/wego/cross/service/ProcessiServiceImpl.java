package it.wego.cross.service;

import org.springframework.stereotype.Service;

import it.wego.cross.dao.ProcessiDao;
import it.wego.cross.entity.Processi;
import it.wego.cross.entity.ProcessiEventi;

/**
 *
 * @author CS
 */
@Service
public class ProcessiServiceImpl  {

	ProcessiDao processiDao;
	
	public ProcessiServiceImpl(ProcessiDao processiDao){
		this.processiDao= processiDao;
	}

    public ProcessiEventi findProcessiEventiByCodEventoIdProcesso(String codEvento, Processi processo) {
        ProcessiEventi eventoProcesso = processiDao.findProcessiEventiByCodEventoIdProcesso(codEvento, processo.getIdProcesso());
        return eventoProcesso;
    }
    
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
