package it.wego.cross.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;

import it.wego.cross.actions.EmailAction;
import it.wego.cross.actions.ErroriAction;
import it.wego.cross.annotations.RequiresGrant;
import it.wego.cross.beans.grid.GridEventoTemplateBean;
import it.wego.cross.dao.PluginConfigurationDao;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dto.ErroreDTO;
import it.wego.cross.entity.Email;
import it.wego.cross.entity.PluginConfiguration;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.Utente;
import it.wego.cross.service.PraticheService;
import it.wego.cross.utils.Log;

@Controller
public class ExtraController extends AbstractController {

	private int idAdmin = 1;

	private final String SEMAPHORE = "semaphore.automatic.registering";
	private static final String REDIRECT_ELENCO_PRATICHE = "redirect:/pratiche/gestisci.htm";

	@Autowired
	private ErroriAction erroriAction;
	@Autowired
	private PluginConfigurationDao pluginConfigurationDao;
	@Autowired
	private PraticheService praticheService;
	@Autowired
	private PraticaDao praticaDao;
	@Autowired
	private EmailAction emailAction;


    @RequiresGrant
	@RequestMapping("/extra/return_to_approval")
	public String returnToApproval(Model model,
			HttpServletRequest request,
			HttpServletResponse response,
			final RedirectAttributes redirectAttributes,
			@ModelAttribute("id_pratica") Integer idPratica) {

		Utente utente = utentiService.getUtenteConnesso(request);
		try {
			praticheService.returnToApproval(idPratica);
		} catch (Exception e) {
			String msg = "Operazione non andata a buon fine";
			GridEventoTemplateBean json = new GridEventoTemplateBean();
			json.setErrors(Arrays.asList(msg));
			ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_RETURN_TO_APPROVAL, "Errore nell'esecuzione del controller extra/return_to_approval", e, null, utente);
			erroriAction.saveError(errore);
			model.addAttribute("json", json.toString());
			Log.APP.error(msg, e);
			throw new RuntimeException(e);
		}

		return REDIRECT_ELENCO_PRATICHE;
	}

	@RequestMapping("/extra/automatic_register")
	public String automaticRegister(Model model,
			HttpServletRequest request,
			HttpServletResponse response) {
		Utente utente = utentiService.getUtenteConnesso(request);
		String status = "running";

		if(utente.getIdUtente() == idAdmin) {
			PluginConfiguration semaphore = pluginConfigurationDao.findByName(SEMAPHORE);
			if(!PluginConfigurationDao.TRUE_VALUE.equalsIgnoreCase(semaphore.getValue())){
				pluginConfigurationDao.changeByName(SEMAPHORE, PluginConfigurationDao.TRUE_VALUE);
				List<Pratica> paperworks =  praticaDao.findPaperworkWithoutProtocol();
				if(!paperworks.isEmpty()) {
					for(Pratica pratica: paperworks) {
						PraticheEventi firstRic = null;
						for(PraticheEventi praticaEvento: pratica.getPraticheEventiList()) {
							if(Pratica.RICEZIONE_PRATICA.equalsIgnoreCase(praticaEvento.getIdEvento().getCodEvento())
									&& (firstRic == null || firstRic.getDataEvento().compareTo(praticaEvento.getDataEvento())>0)){
								firstRic = praticaEvento;
							}
						}
						try {
							praticheService.register(firstRic);
							if("S".equalsIgnoreCase(firstRic.getIdEvento().getFlgMail())) {
								for(Email email: firstRic.getEmailList()) {
									Utente utenteConnesso = utentiService.getUtenteConnesso(request);
									emailAction.rispedisciEmailInviata(email.getIdEmail(), null, email.getEmailDestinatario(), utenteConnesso);
								}
							}
						}
						catch(Exception e) {
							String msg = "Pratica "+ pratica.getIdPratica() + " non protocollata";
							ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_EXTRA_PROTOCOL, "Errore nell'esecuzione del controller extra/automatic_register,"+ msg, e, null, utente);
							erroriAction.saveError(errore);
							Log.APP.error(msg, e);
						}
					}
				}
				status = "executed";
			}
		}
		Gson gson = new Gson();
		model.addAttribute("json", gson.toJson(status));


		pluginConfigurationDao.changeByName(SEMAPHORE, PluginConfigurationDao.FALSE_VALUE);


		return AJAX_PAGE;
	}
}
