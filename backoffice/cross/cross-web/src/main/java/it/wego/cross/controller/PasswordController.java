package it.wego.cross.controller;

import it.wego.cross.actions.UtentiAction;
import it.wego.cross.beans.layout.Message;
import it.wego.cross.dao.AuthorizationDao;
import it.wego.cross.dto.UtenteDTO;
import it.wego.cross.entity.Utente;
import it.wego.cross.serializer.UtentiSerializer;
import it.wego.cross.service.UtentiService;
import it.wego.cross.validator.IsValid;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PasswordController extends AbstractController{
	
	

	//variabili relative alle comunicazione utente sulle operazioni effettuate

	private final String OPERAZIONE_MODIFICA_PASSWORD = "modificaPassword";
	private final String OPERAZIONE_RECUPERO_PASSWORD = "recuperaPassword";
	private final String PASSWORD_DIVERSE = "La nuova password e la nuova password ridigitata non corrispondono";
	private final String PAGE_MODIFY_PASSWORD = "utente_modificaPassword";
	private final String PASSWORD = "Password richiesta";
	private final String USERNAME = "Username richiesto";
	private final String EMAIL = "Email richiesta";
	private final String NEW_PASSWORD = "Nuova password richiesta";
	private final String CONFIRM_PASSWORD_REQUIRED = "Devi ridigitare la nuova password";
	private final String SUCCESS = "Modifica effettuata con successo";
	private final String USERNAME_PASSWORD_ERRATE="Password errata";
	private final String USERNAME_EMAIL_ERRATE="Username o email errate";
	private final String HOME_PAGE = "redirect:/index.htm";
	private final String PAGE_PASSWORD_RECOVERY = "/recuperaPassword/recuperaPassword";
	private final String OGGETTO_EMAIL="Reimpostazione Password";
	private final String SUCCESS_PASSWORD_RECOVERY = "La nuova password è stata inviata all'indirizzo ";
	private final String ERROR_SAND_EMAIL = "Errore durante l'invio della nuova password all'indirizzo email : ";
	
	
	
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private UtentiService utentiService;
	@Autowired
	private UtentiAction utentiAction;
	@Autowired
	protected Validator validator;
	@Autowired
	private IsValid isValid;
	@Autowired
	private UtentiSerializer utentiSerializer;
	@Autowired
	private AuthorizationDao authorizationDao;


	@RequestMapping("/utenti/modificaPassword")
	public String modifyPassword(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("utente") UtenteDTO utente,BindingResult result,  RedirectAttributes redirectAttributes) {
		String action = request.getParameter("action");
		
		UtenteDTO utenteConnesso=utentiService.getUtenteConnessoDTO(request);
		
		String redirectPage=PAGE_MODIFY_PASSWORD;
		
		if (action != null && OPERAZIONE_MODIFICA_PASSWORD.equals(action)) {
			
			List<String> messages = new ArrayList<String>();
			boolean isCorrect = false;

			
			if(null==utente.getPassword()  || utente.getPassword()==""){
				isCorrect = false;
				messages.add(PASSWORD);
			}
			
			if(null==utente.getNewPassword()  || utente.getNewPassword()==""){
				isCorrect = false;
				messages.add(NEW_PASSWORD);
			}
			
			if(null==utente.getConfirmNewPassword()  || utente.getConfirmNewPassword()==""){
				isCorrect = false;
				messages.add(CONFIRM_PASSWORD_REQUIRED);
			}


			if( utente.getNewPassword()!=null && utente.getNewPassword()!="" && utente.getConfirmNewPassword()!=null && utente.getConfirmNewPassword()!="" && !utente.getNewPassword().equals(utente.getConfirmNewPassword())){
				isCorrect = false;
				messages.add(PASSWORD_DIVERSE);

			}
			
			if( utente.getNewPassword()!=null && utente.getNewPassword()!="" && utente.getConfirmNewPassword()!=null && utente.getConfirmNewPassword()!="" &&  utente.getNewPassword().equals(utente.getConfirmNewPassword())){
				isCorrect = true;	
			}
			
		

			if(isCorrect){
				UtenteDTO utenteRicercato = utentiSerializer.serializeUtente(utentiService.findUtenteDaUsername(utenteConnesso.getUsername()));
				String password=utente.getPassword();
				String passwordFromDb=utenteRicercato.getPassword();
                
				boolean match=utentiService.testPsw(password, passwordFromDb);
                 
				if(match){
				
					utenteRicercato.setPassword(utente.getNewPassword());
					try {
						utentiAction.modificaUtente(utenteRicercato);
						Message success = new Message();
						success.setError(false);
						success.setMessages(messages);
						messages.add(SUCCESS);	
						redirectAttributes.addFlashAttribute("message", success );									
						redirectPage=HOME_PAGE;
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}else{
					Message error = new Message();
					messages.add(USERNAME_PASSWORD_ERRATE);
					error.setMessages(messages);
					request.setAttribute("message", error);
					utente.setPassword("");
					utente.setNewPassword("");
					utente.setConfirmNewPassword("");
					model.addAttribute("utente", utente);
				}
			}else {
				Message error = new Message();
				error.setMessages(messages);
				request.setAttribute("message", error);
				utente.setPassword("");
				utente.setNewPassword("");
				utente.setConfirmNewPassword("");
				model.addAttribute("utente", utente);
			}
			
		}
		request.setAttribute("username", utenteConnesso.getUsername());
		return redirectPage;
	}
	
	@RequestMapping(value="/utenti/recuperaPassword" ,method=RequestMethod.GET)
	public String initRecuperaPassword() {
		
		return PAGE_PASSWORD_RECOVERY;
		
	}

	
	
	@RequestMapping(value="/utenti/recuperaPassword" ,method=RequestMethod.POST)
	public String recuperaPassword(Model model, @RequestParam("action") String action, @ModelAttribute("utente") UtenteDTO utente)  {
		
		List<String> messages = new ArrayList<String>();
		
		
		UtenteDTO utenteRicercato=null;
		
		if (action != null && OPERAZIONE_RECUPERO_PASSWORD.equals(action)) {
			
			
			
			//StringBuilder stringBuilder = new StringBuilder();
			boolean isCorrect = true;
			
			if(null==utente.getUsername()  || utente.getUsername()==""){
				isCorrect = false;
				messages.add(USERNAME);
				//stringBuilder.append(USERNAME);
			}
			if(null==utente.getEmail()  || utente.getEmail()==""){
				isCorrect = false;
				messages.add(EMAIL);
				//stringBuilder.append(EMAIL);
			}
			
			if(isCorrect){
				Utente ut=utentiService.findUtenteDaUsername(utente.getUsername());
				if(ut!=null){
					utenteRicercato = utentiSerializer.serializeUtente(ut);
					if(utenteRicercato.getEmail().equals(utente.getEmail())){
						try {
							utentiAction.recuperaPassword(utenteRicercato, OGGETTO_EMAIL );
							Message success = new Message();
							success.setError(false);
							messages.add(SUCCESS_PASSWORD_RECOVERY+" "+utenteRicercato.getEmail());	
							success.setMessages(messages);					
							model.addAttribute("success", success);
						} catch (Throwable e) {
							Message error = new Message();
							messages.add(ERROR_SAND_EMAIL+" "+utenteRicercato.getEmail());
							error.setMessages(messages);
							error.setError(true);
							model.addAttribute("error", error);
						}
						
					}else{
						Message error = new Message();
						messages.add(USERNAME_EMAIL_ERRATE);
						error.setMessages(messages);
						error.setError(true);
						model.addAttribute("error", error);
					}		
				}else{
					Message error = new Message();
					messages.add(USERNAME_EMAIL_ERRATE);
					error.setMessages(messages);
					error.setError(true);
					model.addAttribute("error", error);
				}		
										
			}else{
				Message error = new Message();
				error.setMessages(messages);
				error.setError(true);
				model.addAttribute("error", error);
			}
		}
	
		return PAGE_PASSWORD_RECOVERY;
		
	}


	

}








