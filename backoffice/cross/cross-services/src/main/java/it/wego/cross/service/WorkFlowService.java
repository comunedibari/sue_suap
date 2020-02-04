/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import it.wego.cross.beans.AttoriComunicazione;
import it.wego.cross.beans.EventoBean;
import it.wego.cross.beans.MailContentBean;
import it.wego.cross.entity.Email;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.EventiTemplate;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.Processi;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.entity.Templates;
import it.wego.cross.entity.Utente;
import it.wego.cross.events.comunicazione.bean.ComunicazioneBean;

import java.util.List;
import java.util.Map;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;

/**
 *
 * @author Gabriele
 */
@Service
public interface WorkFlowService {

    public List<ProcessiEventi> findAvailableEvents(Integer idPratica) throws Exception;

    public Processi getProcessToUse(Integer codEnte, Integer idProcedimento) throws Exception;

//    public PraticheEventi gestisciProcessoEvento(EventoBean eb) throws Exception;
    public void gestisciProcessoEvento(EventoBean eb) throws Exception;

    public AttoriComunicazione getDestinatariDefaultEvento(Pratica pratica, ProcessiEventi eventoProcesso, Enti enteSelezionato) throws Exception;

    public EventiTemplate findEventiTemplateById(Integer idEventoTemplate);

//    public byte[] generaDocumento(WorkFlowBean wfb) throws Exception;
    public byte[] generaDocumento(EventiTemplate eventoTemplate, Pratica pratica, Utente utente) throws Exception;

    public byte[] generaDocumento(it.wego.cross.xml.Pratica praticaCross, EventiTemplate et) throws Exception;

    public MailContentBean getMailContent(Pratica pratica, ProcessiEventi pe) throws Exception;

    public MailContentBean getMailContent(Pratica pratica, PraticheEventi praticaEvento) throws Exception;

    public MailContentBean getMailContent(it.wego.cross.xml.Pratica praticaCross, ProcessiEventi pe) throws Exception;

//    public String getDefaultMailObject(WorkFlowBean wfb) throws Exception;
    public Templates generaTemplate(Integer id) throws Exception;
//    public String getDefaultMailBody(WorkFlowBean wfb) throws Exception;

    public ProcessiEventi findProcessiEventiById(Integer id);

    public ProcessiEventi findProcessiEventiByIdProcessoCodEvento(Processi idProcesso, String codEvento);

    public byte[] generaDocumento(Integer idEventoTemplate, Integer idPratica, Utente utente) throws Exception;

    public boolean isInManualProtocolStatus(Task task);

    public Map<String, Object> getVariablesForProtocolTask(Email email);

	public void aggiornaUtentePraticaPerChiusura(Pratica pratica, PraticheEventi praticaEvento);
	
	public void aggiornaUtentePraticaIntegrazione(Pratica pratica, EventoBean eb) throws Exception;
}
