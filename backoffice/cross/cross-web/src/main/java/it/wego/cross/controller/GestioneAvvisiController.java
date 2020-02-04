package it.wego.cross.controller;

import it.wego.cross.beans.layout.Message;
import it.wego.cross.dto.AvvisoDTO;
import it.wego.cross.entity.Avviso;
import it.wego.cross.service.AvvisoService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class GestioneAvvisiController extends AbstractController{

	@Autowired
	private AvvisoService avvisoService;

	private final String PAGE_CREA_AVVISO = "crea_avviso";
	private final String CREA_AVVISO_ACTION="creaAvviso";
	private final String MODIFICA_AVVISO_ACTION="modificaAvviso";
	
	private final String REDIRECT_LISTA_AVVISI = "redirect:/avvisi/listaAvvisi.htm";
	private final String PAGE_LISTA_AVVISI = "lista_avvisi";
	
	private final String TESTO_OBBIGATORIO = "Testo obbligatorio";
	private final String SCADENZA_OBBLIGATORIA = "Scadenza obbligatoria";
	
	private final String SUCCESS_CREA = "Nuovo avviso creato con successo";
	private final String SUCCESS_MODIFICA = "Avviso modificato con successo";
	private final String SUCCESS_DELETE = "Avviso eliminato con successo";

	@RequestMapping("/avvisi/creaAvviso")
	public String creaAvviso(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("avvisoDTO") AvvisoDTO avvisoDTO ,BindingResult result, RedirectAttributes redirectAttributes) throws Exception {

		//	ModelMap modelMap=new ModelMap();
		String action=avvisoDTO.getAction();		

		List<String> messages = new ArrayList<String>();

		if(action!=null && action.equalsIgnoreCase(CREA_AVVISO_ACTION) && (null==avvisoDTO.getTesto() || avvisoDTO.getTesto().isEmpty() || null==avvisoDTO.getStringScadenza() || avvisoDTO.getStringScadenza().isEmpty())){
			Message error = new Message();

			if(null==avvisoDTO.getTesto() || avvisoDTO.getTesto().isEmpty()){				
				messages.add(TESTO_OBBIGATORIO);				
			}
			if(null==avvisoDTO.getStringScadenza() || avvisoDTO.getStringScadenza().isEmpty()){				
				messages.add(SCADENZA_OBBLIGATORIA);
			}
			error.setMessages(messages);
			request.setAttribute("message", error);
			avvisoDTO.setAction(CREA_AVVISO_ACTION);
			//modelMap.addAttribute("avvisoDTO" , avvisoDTO);
			return 	PAGE_CREA_AVVISO;
		}


		if(action!=null && action.equalsIgnoreCase(CREA_AVVISO_ACTION)){
			Avviso avviso=new Avviso();
			avviso.setTesto(avvisoDTO.getTesto());

			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Date date = df.parse(avvisoDTO.getStringScadenza());

			avviso.setScadenza(date);

			avvisoService.creaAvviso(avviso);
			Message success = new Message();
			success.setError(false);
			success.setMessages(messages);
			messages.add(SUCCESS_CREA);	
			redirectAttributes.addFlashAttribute("message", success );	
			return 	REDIRECT_LISTA_AVVISI;
		}else{
			avvisoDTO.setAction(CREA_AVVISO_ACTION);
			//	modelMap.addAttribute("avvisoDTO" , avvisoDTO);
			return 	PAGE_CREA_AVVISO;
		}

	}

	@RequestMapping("/avvisi/listaAvvisi")
	public String listaAvvisi( HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Avviso> avvisiListTot=new ArrayList<Avviso>();
		avvisiListTot=avvisoService.findAllAvvisi();
		request.setAttribute("avvisiListTot", avvisiListTot);		
		return PAGE_LISTA_AVVISI;
	}

	@RequestMapping(value="/avvisi/deleteAvviso",  method = RequestMethod.GET)
	public String deleteAvviso( HttpServletRequest request, HttpServletResponse response, @RequestParam("idAvviso") int idAvviso, RedirectAttributes redirectAttributes) throws Exception {
		Avviso avviso=avvisoService.findbyIdAvvisi(idAvviso);
		avvisoService.deleteAvviso(avviso);
		List<Avviso> avvisiListTot=new ArrayList<Avviso>();
		avvisiListTot=avvisoService.findAllAvvisi();
		request.setAttribute("avvisiListTot", avvisiListTot);
		List<String> messages = new ArrayList<String>();
		Message success = new Message();
		success.setError(false);
		success.setMessages(messages);
		messages.add(SUCCESS_DELETE);	
		redirectAttributes.addFlashAttribute("message", success );	
		return PAGE_LISTA_AVVISI;
	}

	@RequestMapping(value="/avvisi/apriDettaglioAvviso",  method = RequestMethod.GET)
	public String apriDettaglioAvviso( HttpServletRequest request, HttpServletResponse response, @RequestParam("idAvviso") int idAvviso, @ModelAttribute("avvisoDTO") AvvisoDTO avvisoDTO ,BindingResult result, RedirectAttributes redirectAttributes) throws Exception {
		Avviso avviso=avvisoService.findbyIdAvvisi(idAvviso);
		avvisoDTO.setIdAvviso(avviso.getIdAvviso());
		avvisoDTO.setTesto(avviso.getTesto());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		avvisoDTO.setStringScadenza(simpleDateFormat.format(avviso.getScadenza()));				
		avvisoDTO.setAction(MODIFICA_AVVISO_ACTION);
		List<String> messages = new ArrayList<String>();
		Message success = new Message();
		success.setError(false);
		success.setMessages(messages);
		messages.add(SUCCESS_DELETE);	
		redirectAttributes.addFlashAttribute("message", success );	

		return PAGE_CREA_AVVISO;
	}


	@RequestMapping("/avvisi/modificaAvviso")
	public String modificaAvviso(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("avvisoDTO") AvvisoDTO avvisoDTO ,BindingResult result, RedirectAttributes redirectAttributes) throws Exception {

		List<String> messages = new ArrayList<String>();

		if (null==avvisoDTO.getTesto() || avvisoDTO.getTesto().isEmpty() || null==avvisoDTO.getStringScadenza() || avvisoDTO.getStringScadenza().isEmpty()){
			Message error = new Message();

			if(null==avvisoDTO.getTesto() || avvisoDTO.getTesto().isEmpty()){				
				messages.add(TESTO_OBBIGATORIO);				
			}
			if(null==avvisoDTO.getStringScadenza() || avvisoDTO.getStringScadenza().isEmpty()){				
				messages.add(SCADENZA_OBBLIGATORIA);
			}
			error.setMessages(messages);
			request.setAttribute("message", error);
			avvisoDTO.setAction(MODIFICA_AVVISO_ACTION);
			return 	PAGE_CREA_AVVISO;
		}

		Avviso avviso=avvisoService.findbyIdAvvisi(avvisoDTO.getIdAvviso());
		
		avviso.setTesto(avvisoDTO.getTesto());
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date date = df.parse(avvisoDTO.getStringScadenza());
		avviso.setScadenza(date);
		avvisoService.updateAvviso(avviso);
		
		Message success = new Message();
		success.setError(false);
		success.setMessages(messages);
		messages.add(SUCCESS_MODIFICA);	
		redirectAttributes.addFlashAttribute("message", success );	
		return 	REDIRECT_LISTA_AVVISI;

	}



}
