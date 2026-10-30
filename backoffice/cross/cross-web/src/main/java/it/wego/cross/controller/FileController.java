/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.tika.Tika;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.wego.cross.actions.ErroriAction;
import it.wego.cross.actions.PraticheAction;
import it.wego.cross.constants.Constants;
import it.wego.cross.constants.SessionConstants;
import it.wego.cross.cross_dms.DMSClientDao;
import it.wego.cross.dto.AllegatoDTO;
import it.wego.cross.dto.ErroreDTO;
import it.wego.cross.dto.EventoDTO;
import it.wego.cross.dto.UtenteDTO;
import it.wego.cross.dto.comunicazione.DettaglioPraticaDTO;
import it.wego.cross.dto.dozer.EnteDTO;
import it.wego.cross.dto.dozer.UtenteRuoloEnteDTO;
import it.wego.cross.entity.Allegati;
import it.wego.cross.entity.Configuration;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.EventiTemplate;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.PraticheEventiAllegati;
import it.wego.cross.entity.PraticheProtocollo;
import it.wego.cross.entity.Templates;
import it.wego.cross.entity.Utente;
import it.wego.cross.interceptor.SelectUGInterceptor;
import it.wego.cross.plugins.commons.beans.Allegato;
import it.wego.cross.plugins.documenti.GestioneAllegati;
import it.wego.cross.plugins.documenti.GestioneAllegatiFS;
import it.wego.cross.service.AllegatiService;
import it.wego.cross.service.ConfigurationService;
import it.wego.cross.service.EntiService;
import it.wego.cross.service.PluginService;
import it.wego.cross.service.PraticheProtocolloService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.service.ProtocolloService;
import it.wego.cross.service.WorkFlowService;
import it.wego.cross.session.MultiSubSessionManager;
import it.wego.cross.utils.DocumentConverter;
import it.wego.cross.utils.FileUtilities;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;
import utilities.myUtility;



/**
 * I controller sollevano tutti una eccezione. Mi aspetto infatti di
 * visualizzare l'errore su una nuova pagina
 *
 * @author giuseppe
 */
@Controller
public class FileController extends AbstractController {

	@Autowired
	private ConfigurationService configurationService;
	@Autowired
	private AllegatiService allegatiService;
	@Autowired
	private WorkFlowService workflowService;
	@Autowired
	private PluginService pluginService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private PraticheService praticheService;
	@Autowired
	private PraticheProtocolloService praticheProtocolloService;
	@Autowired
	private EntiService entiService;
	@Autowired
	private PraticheAction praticheAction;
	@Autowired
	private GestioneAllegatiFS documentiFS;
	@Autowired
	private ErroriAction erroriAction;
	@Autowired 
	DocumentConverter documentConverter;
	@Autowired 
	ProtocolloService protocolloService;
	
	private byte[] stampedDocument;
	
	@RequestMapping(value = "/download", method = RequestMethod.POST)
	public void downloadWithPost(HttpServletRequest request, HttpServletResponse response) {
		Utente utente = utentiService.getUtenteConnesso(request);
		try {
			Integer idFile = Integer.valueOf(request.getParameter("id_file"));
			AllegatoDTO allegato = allegatiService.findAllegatoByIdNullSafe(idFile, null);
			byte[] documentBody = allegato.getFileContent();
			response.setContentType(allegato.getTipoFile());
			response.setContentLength(documentBody.length);
			response.addHeader("Content-Disposition", "attachment; filename=\"" + allegato.getNomeFile() + "\"");

			writeOnStream(response, documentBody);
		} catch (Exception ex) {
			try{
				Log.APP.error("Non è stato possibile scaricare il documento", ex);
				String error = messageSource.getMessage("error.file.downloadfileprotocollo", null, Locale.getDefault());
				response.setContentType("text/plain");
				writeOnStream(response, error.getBytes());
				saveAndlogError(null, it.wego.cross.constants.Error.ERRORE_DOWNLOAD_POST, "Errore nell'esecuzione del controller /download metodo POST", ex, null, utente);
			} catch (IOException ex1) {
				//Non può capitare
				saveAndlogError("", it.wego.cross.constants.Error.ERRORE_DOWNLOAD_POST, "Errore nell'esecuzione del controller /download metodo POST", ex1, null, utente);
			} 
		}
	}

	@RequestMapping(value = "/downloadF", method = RequestMethod.GET)
	public void downloadFolderWithPost(HttpServletRequest request, HttpServletResponse response) {
		Utente utente = utentiService.getUtenteConnesso(request);
		try {
			Integer idPratica = Integer.valueOf(request.getParameter("id_pratica"));
			Pratica pratica = praticheService.getPratica(idPratica);
			List<AllegatoDTO> allegatiList = praticheService.getAllegatiPratica(idPratica);
			
			createAndDownloadZip(response, pratica, allegatiList);
		} catch (Exception ex) {
			try{
				Log.APP.error("Non è stato possibile scaricare il documento", ex);
				String error = messageSource.getMessage("error.file.downloadfileprotocollo", null, Locale.getDefault());
				response.setContentType("text/plain");
				writeOnStream(response, error.getBytes());
				saveAndlogError(null, it.wego.cross.constants.Error.ERRORE_DOWNLOAD_POST, "Errore nell'esecuzione del controller /download metodo GET", ex, null, utente);
			} catch (IOException ex1) {
				//Non può capitare
				saveAndlogError("", it.wego.cross.constants.Error.ERRORE_DOWNLOAD_POST, "Errore nell'esecuzione del controller /download metodo GET", ex1, null, utente);
			} 
		}
	}

	@RequestMapping(value = "/downloadS", method = RequestMethod.GET)
	public void downloadSelectedAttachments(HttpServletRequest request, HttpServletResponse response) {
		Utente utente = utentiService.getUtenteConnesso(request);
		try {
			Integer idPratica = Integer.valueOf(request.getParameter("id_pratica"));
			Pratica pratica = praticheService.getPratica(idPratica);
			List<AllegatoDTO> allegatiList = new ArrayList<AllegatoDTO>();
			String[] idFiles = request.getParameterValues("id_files");
		    Set<Integer> set = new HashSet<>();
		    for(int i = 0; i < idFiles.length; i++) {
		    	String id = idFiles[i];
		    	if(id != null)
		    		set.add(Integer.parseInt(id));
		    }
			for(AllegatoDTO allegato: praticheService.getAllegatiPratica(idPratica)) {
				if(set.contains(allegato.getIdAllegato()))
					allegatiList.add(allegato);
			}
			createAndDownloadZip(response, pratica, allegatiList);
		} catch (Exception ex) {
			try{
				Log.APP.error("Non è stato possibile scaricare il documento", ex);
				String error = messageSource.getMessage("error.file.downloadfileprotocollo", null, Locale.getDefault());
				response.setContentType("text/plain");
				writeOnStream(response, error.getBytes());
				saveAndlogError(null, it.wego.cross.constants.Error.ERRORE_DOWNLOAD_POST, "Errore nell'esecuzione del controller /downloadS metodo GET", ex, null, utente);
			} catch (IOException ex1) {
				//Non può capitare
				saveAndlogError("", it.wego.cross.constants.Error.ERRORE_DOWNLOAD_POST, "Errore nell'esecuzione del controller /downloadS metodo GET", ex1, null, utente);
			} 
		}
	}

	@RequestMapping(value = "/downloadSR", method = RequestMethod.GET)
	public void downloadRegisteredAttachments(HttpServletRequest request, HttpServletResponse response) {
		Utente utente = utentiService.getUtenteConnesso(request);
		try {
			Integer idPratica = Integer.valueOf(request.getParameter("id_pratica"));
			Pratica pratica = praticheService.getPratica(idPratica);
			DettaglioPraticaDTO dettaglio = praticheService.getDettaglioPratica(pratica);
			List<AllegatoDTO> allegatiList = new ArrayList<AllegatoDTO>();
			
			for(AllegatoDTO allegato:dettaglio.getAllegati()) {
				if(allegato.getProtocollo() != null && allegato.getProtocollo().length() > 0)
					allegatiList.add(allegato);
			}
			createAndDownloadZip(response, pratica, allegatiList);
		} catch (Exception ex) {
			try{
				Log.APP.error("Non è stato possibile scaricare i document", ex);
				String error = messageSource.getMessage("error.file.downloadfileprotocollo", null, Locale.getDefault());
				response.setContentType("text/plain");
				writeOnStream(response, error.getBytes());
				saveAndlogError(null, it.wego.cross.constants.Error.ERRORE_DOWNLOAD_POST, "Errore nell'esecuzione del controller /downloadSR metodo GET", ex, null, utente);
			} catch (IOException ex1) {
				//Non può capitare
				saveAndlogError("", it.wego.cross.constants.Error.ERRORE_DOWNLOAD_POST, "Errore nell'esecuzione del controller /downloadSR metodo GET", ex1, null, utente);
			} 
		}
	}

	@RequestMapping(value = "/download_attachments", method = RequestMethod.GET)
	public void downloadAllAttachments(HttpServletRequest request, HttpServletResponse response) {
		Utente utente = utentiService.getUtenteConnesso(request);
		try {
			Integer idPratica = Integer.valueOf(request.getParameter("id_pratica"));
			Integer idEvento = Integer.valueOf(request.getParameter("id_pratica_evento"));
			Pratica pratica = praticheService.getPratica(idPratica);
			EventoDTO evento = praticheService.getDettaglioPraticaEvento(idEvento);
			List<AllegatoDTO> allegatiList = evento.getAllegati();
			
			createAndDownloadZip(response, pratica, allegatiList);
		} catch (Exception ex) {
			
			try{
				Log.APP.error("Non è stato possibile scaricare i documenti", ex);
				String error = messageSource.getMessage("error.file.downloadfileattachments", null, Locale.getDefault());
				response.setContentType("text/plain");
				writeOnStream(response, error.getBytes());
				saveAndlogError(null, it.wego.cross.constants.Error.ERRORE_DOWNLOAD_GET, "Errore nell'esecuzione del controller /download_attachments metodo GET", ex, null, utente);
				
			} catch (IOException ex1) {
				//Non può capitare
				saveAndlogError("", it.wego.cross.constants.Error.ERRORE_DOWNLOAD_GET, "Errore nell'esecuzione del controller /download_attachments metodo GET", ex1, null, utente);
			}
		}
	}

	@RequestMapping(value = "/download_from_register", method = RequestMethod.GET)
	public void downloadFromRegister(HttpServletRequest request, HttpServletResponse response) {
		Utente utente = utentiService.getUtenteConnesso(request);
		try {
			Integer idPratica = Integer.valueOf(request.getParameter("id_pratica"));
			Integer idFile = Integer.valueOf(request.getParameter("id_file"));

			Pratica pratica = praticheService.getPratica(idPratica);
			PraticheEventi mainEvent = null;
			Allegati mainAttachment = null;
			for(PraticheEventi pe: pratica.getPraticheEventiList()) {
				for(PraticheEventiAllegati pea: pe.getPraticheEventiAllegatiList()) {
					if("S".equals(pea.getFlgIsPrincipale()) && idFile.equals(pea.getAllegati().getId())) {
						mainEvent = pe;
						mainAttachment = pea.getAllegati();
					}
				}
			}
			Allegato stampedDocument = protocolloService.getStampedDocument(mainEvent.getProtocollo(), mainAttachment.getNomeFile());
			
			response.setContentType(mainAttachment.getTipoFile());
			response.setContentLength(stampedDocument.getFile().length);
			String nomefiledascaricare = mainAttachment.getNomeFile();
			for(int i=0; i<3; i++) {nomefiledascaricare = myUtility.removeExtension(nomefiledascaricare);}

			nomefiledascaricare = nomefiledascaricare + ".pdf";
						
//			response.addHeader("Content-Disposition", "attachment; filename=\"" + mainAttachment.getNomeFile() + "\"");
			response.addHeader("Content-Disposition", "attachment; filename=\"" + nomefiledascaricare + "\"");

			writeOnStream(response, stampedDocument.getFile());
		} catch (Exception ex) {
			try{
				Log.APP.error("Non è stato possibile scaricare i documenti", ex);
				String error = messageSource.getMessage("error.file.downloadfileprotocollo", null, Locale.getDefault());
				response.setContentType("text/plain");
				writeOnStream(response, error.getBytes());
				saveAndlogError(null, it.wego.cross.constants.Error.ERRORE_PROTOCOLLO_SERVICE_DOWNLOAD_GET, "Errore nell'esecuzione del controller /download_from_register metodo GET", ex, null, utente);
				
			} catch (IOException ex1) {
				saveAndlogError("", it.wego.cross.constants.Error.ERRORE_PROTOCOLLO_SERVICE_DOWNLOAD_GET, "Errore nell'esecuzione del controller /download_from_register metodo GET", ex1, null, utente);
			}
		}
	}

	@RequestMapping(value = "/download_from_register_ruvo", method = RequestMethod.GET)
	public void downloadFromRegisterRuvo(HttpServletRequest request, HttpServletResponse response) {
		String nomeFileZippato = "";
		Utente utente = utentiService.getUtenteConnesso(request);
		int timeout = 15000; //Timeout in millisecondi
		
		it.wego.cross.aurigaRuvo.RunnableJobRuvo jobRuvo = null;
		
		/* */
		try {
			Integer idPratica = Integer.valueOf(request.getParameter("id_pratica"));
			Integer idPraticaEvento = Integer.valueOf(request.getParameter("id_pratica_evento"));

			List<Configuration> attachmentsFolderConfig = configurationService.findByName(SessionConstants.TIMEOUT_WS_AURIGA);
			Configuration config = attachmentsFolderConfig.get(0);
			
			if(!config.getValue().isEmpty()) {
				timeout = Integer.valueOf(config.getValue()).intValue();
			}

			Pratica pratica = praticheService.getPratica(idPratica);
			PraticheEventi mainEvent = null;
			Allegati mainAttachment = null;
			
			Log.APP.info("download_from_register_ruvo - pratica:" + idPratica);

			for(PraticheEventi pe: pratica.getPraticheEventiList()) {
				Log.APP.info("download_from_register_ruvo - idEvento:" + pe.getIdEvento());
				Log.APP.info("download_from_register_ruvo - check1 idEvento:" + it.wego.cross.constants.Constants.SUE_RUVO_INVIO_PDC.intValue());
				Log.APP.info("download_from_register_ruvo - check2 idPraticaEvento:" + idPraticaEvento.intValue());
				
				//if(pe.getIdEvento().getIdEvento().intValue() == it.wego.cross.constants.Constants.SUE_RUVO_INVIO_PDC.intValue()) {
				if(pe.getIdPraticaEvento().intValue() == idPraticaEvento.intValue()) {
					Integer idPraticaEventoSession = pe.getIdPraticaEvento();
					Log.APP.info("download_from_register_ruvo - check3 idPraticaEvento selezionato:" + idPraticaEventoSession.intValue());
					Log.APP.info("download_from_register_ruvo - evento protocollo da estrarre:" + pe.getProtocollo());
					
					HttpSession session = request.getSession();
					String session_pratica_evento = idPratica.intValue() + "_" + idPraticaEventoSession.intValue(); 
					Log.APP.info("download_from_register_ruvo - get nomefile da estrarre:" + session_pratica_evento);
					
					Log.APP.info("download_from_register_ruvo - session nomefile da estrarre:" + 
					String.valueOf(session.getAttribute(session_pratica_evento)));
					
					if(session.getAttribute(session_pratica_evento) == null
							|| String.valueOf(session.getAttribute(session_pratica_evento)).isEmpty()) {

						nomeFileZippato = protocolloService.getNomeFileZippato(idPratica, idPraticaEventoSession);							
						Log.APP.info("download_from_register_ruvo - eseguo getNomeFileZippato:" + nomeFileZippato);

						session.setAttribute(session_pratica_evento, nomeFileZippato);
						Log.APP.info("download_from_register_ruvo - eseguo setAttribute:" + nomeFileZippato);
		                
						//byte[] stampedDocument = protocolloService.getDocumentiProtocollatiRuvo(pe.getProtocollo(), nomeFileZippato);
						final String protocollo = pe.getProtocollo();	
						final String filedaEstrarre = nomeFileZippato;
						
						//Thread threadProtocolloRuvo = new Thread(() -> { jobElaboraProtocolloRuvo(protocollo, filedaEstrarre); });
						jobRuvo = new it.wego.cross.aurigaRuvo.RunnableJobRuvo(protocollo, filedaEstrarre);

						Log.APP.info("download_from_register_ruvo - start thread " + 
								" protocollo(" + protocollo + ")" +
								" filedaEstrarre(" + filedaEstrarre + ")"
						);
						
						Thread threadProtocolloRuvo = new Thread(jobRuvo);
						
						threadProtocolloRuvo.start();   
						
						try {
						
							Log.APP.info("download_from_register_ruvo - join thread " + 
									" timeout(" + timeout + ")" 
							);

							long base = System.currentTimeMillis();

							//threadProtocolloRuvo.join(timeout);
							
							threadProtocolloRuvo.join(2000);
							
					        long now = System.currentTimeMillis();
					        
					        long sessionTimeout = now - base;
					        if(sessionTimeout > timeout) {
								Log.APP.info("download_from_register_ruvo - timeout di sessione estrazione file timbrati:" + 
										sessionTimeout);
					        } else {
								Log.APP.info("download_from_register_ruvo - sessione estrazione file timbrati time:" + 
										sessionTimeout);
					        	stampedDocument = jobRuvo.getStampedDocument();
					        }
					        
						} catch (InterruptedException e) {
							Log.APP.info("download_from_register_ruvo - ERRORE:" + e.getMessage());

							e.printStackTrace();
						}
					} else {
						nomeFileZippato = String.valueOf(session.getAttribute(session_pratica_evento));
						nomeFileZippato = nomeFileZippato.trim();
						Log.APP.info("download_from_register_ruvo - AFTER REFRESH PAGE nome file:" + 
								nomeFileZippato + " size:" + nomeFileZippato.length());
						
						stampedDocument = protocolloService.recuperaZipfileTimbrati(nomeFileZippato);
						
						Log.APP.info("download_from_register_ruvo - AFTER REFRESH PAGE dim file:" + 
								(stampedDocument != null ? stampedDocument.length : 0));
						
						
					}
					String end_session_pratica_evento = "end_" + idPratica.intValue() + "_" + idPraticaEventoSession.intValue(); 

					if(session.getAttribute(end_session_pratica_evento) == null
							|| String.valueOf(session.getAttribute(end_session_pratica_evento)).isEmpty()) {

												
						Log.APP.info("download_from_register_ruvo - session_pratica_evento:" + session_pratica_evento);
						if(stampedDocument != null) {
							Log.APP.info("download_from_register_ruvo - session_pratica_evento con download terminato:" + session_pratica_evento);
							session.setAttribute(end_session_pratica_evento, "download terminato");
							PrintWriter outMessage = response.getWriter();  
							response.setContentType("text/html");  
				
							outMessage.println("<html><head></head>"
								+ "<body>"
								+ "<h2></h2>"
								+ "<script>"
								+ " alert(\"Download terminato!\");"
								+ " location.reload();"
								+ "</script>"
								+ "</body></html>");
						} else {
							PrintWriter out = response.getWriter();  
							response.setContentType("text/html");  

							timeout = timeout * 20;
							
							String varHTML = "<html><head>"
								+ "<style>"
								+ " .progress-bar { width: 800px; height: 20px; background-color: #f4f4f7; position: relative; align: \"center\"; }"
								+ " .progress {   height: 100%;  background-color: #1635c0;  position: absolute;  top: 0;  left: 0;  width: 0%;}"
								+ "</style></head>"
								+ "<body>"
								+ "<h2>Recupero allegati protocollati timbrati in corso. Attendere prego.....</h2>"
								+ "<div class=\"progress-bar\">"
								+ "<div class=\"progress\"></div>"
								+ "</div>"
								+ "<span class=\"countdown\"></span>"
								+ "<span class=\"countdown\"></span>"
								+ "<script type=\"text/javascript\">"
								+ "var messageAlert = \"Recupero allegati in corso. Attendere prego... \";"
								+ "const countdownEl = document.querySelector(\".countdown\");"
								+ "const progressBarEl = document.querySelector(\".progress\");"
								+ "let remainingTime = " + (timeout/1000) + ";" 
								+ "const totalTime = remainingTime;"						 
								+ "function countdown() {"
								+ "  if (remainingTime > 0) {"
								+ "	countdownEl.textContent = \" secondi \" + remainingTime;"
								+ "	const progress = ((totalTime - remainingTime) / totalTime) * 100;"
								+ "	progressBarEl.style.width = `${progress}%`;"
								+ "	remainingTime--;"
								+ "	setTimeout(countdown, 1000);"
								+ "  } else {"
								+ "	progressBarEl.style.width = \"100%\";"
								+ "	let text = \"Download in corso! Premere Ok per continuare o Annulla per annullare.\";"
								+ "	if (confirm(text) == true) {"
								+ "		location.reload();"
								+ "	} else{"
								+ "		window.opener = self;"
								+ "		window.close();"
								+ "	}}}"
								+" function firstFunction() {                                      "
							    +"   return new Promise((resolve, reject) => {                     "
							    +"       let y = 0                                                 "
							    +"       setTimeout(() => {                                        "
							    +"         for (i=0; i<4; i++) {				                   "
								+" 			//console.log('Loop valore y:' + y)                    "
							    +"            y++                                                  "
							    +"         }                                                       "							    
								+" 		//document.getElementById(\"escidalpromise\").click();       "
							    +"                                                                 "
								+" 		 console.log('Loop completed.')                            "
							    +"          resolve(y)                                             "
							    +"       }, 2000)                                                  "
							    +"   })                                                            "
							    +" }                                                               "
							    +"                                                                 "
							    +" //2. Create an async function                                   "
							    +" async function secondFunction() {                               "
							    +"     console.log('Before promise call.')                         "
							    +"     //3. Await for the first function to complete               "
							    +"     const result = await firstFunction()                        "
							    +"     console.log('Promise resolved: ' + result)                  "
							    +"     console.log('Next step.')                                   "
							    +" };                                                              "								
								+ " function getPromiseFromEvent(item, event) {	                   "
								+ " 		return new Promise((resolve) => {                      "
								+ " 			const listener = () => {                           "
								+ " 			item.removeEventListener(event, listener);         "
								+ " 			resolve();                                         "
								+ " 		}                                                      "
								+ " 		item.addEventListener(event, listener);                "
								+ " 	})                                                         "
								+ " }                                                              "
								+ "                                                                "
								+ " async function waitForButtonClick() {                          "
								+ " 	const div = document.querySelector(\"div\")                "
								+ " 	const button = document.querySelector(\"button\")          "
								+ " 	//div.innerText = \"Waiting for you to press the button\"  "
								+ " 	await getPromiseFromEvent(button, \"click\")               "
								+ " 	div.innerText = \"The button was pressed!\"                "
								+ " 	alert(\"Download terminato!\");                            "
								+ " 	location.reload();                                         "
								+ " }                                                              "
								+ " //countdown();                                                   "
								+ " secondFunction(); "	
								+ " waitForButtonClick();"
								+ " countdown();"
								+ " </script>";

								nomeFileZippato = String.valueOf(session.getAttribute(session_pratica_evento));
								nomeFileZippato = nomeFileZippato.trim();
								Log.APP.info("download_from_register_ruvo - AFTER REFRESH PAGE nome file:" + 
										nomeFileZippato + " size:" + nomeFileZippato.length());

								//for()......
								
								stampedDocument = protocolloService.recuperaZipfileTimbrati(nomeFileZippato);
								
								Log.APP.info("download_from_register_ruvo - AFTER REFRESH PAGE dim file:" + 
										(stampedDocument != null ? stampedDocument.length : 0));
							
								varHTML = varHTML +
										"<script>document.getElementById(\"escidalpromise\").click();</script></body></html>";
								
								// out.println(varHTML);
								response.sendRedirect("/cross/themes/default/views/progressbar.html?nome_file_zippato=" + nomeFileZippato 
										+ "&id_pratica="+ idPratica.intValue()
										+ "&id_pratica_evento=" + idPraticaEvento.intValue());
							}
						
					} else {
					
						if(stampedDocument != null) {							
							//Eseguire il codice solo se file disponibile 
							Log.APP.info("download_from_register_ruvo - FILE IS AVALABLE!");						
							response.setContentType("application/zip");
							response.setContentLength(stampedDocument.length);
							response.addHeader("Content-Disposition", "attachment; filename=\"pratica_allegati_protocollati_" + idPratica + "\".zip");
							OutputStream out = response.getOutputStream();
							out.write(stampedDocument);
							out.flush();
							out.close();						
						} 
					}
				}
			}
		} catch (Exception ex) {
			try{
				Log.APP.error("Non è stato possibile scaricare i documenti", ex);
				String error = messageSource.getMessage("error.file.downloadfileprotocollo", null, Locale.getDefault());
				response.setContentType("text/plain");
				writeOnStream(response, error.getBytes());
				saveAndlogError(null, it.wego.cross.constants.Error.ERRORE_PROTOCOLLO_SERVICE_DOWNLOAD_GET, "Errore nell'esecuzione del controller /download_from_register_ruvo metodo GET", ex, null, utente);
				
			} catch (IOException ex1) {
				saveAndlogError("", it.wego.cross.constants.Error.ERRORE_PROTOCOLLO_SERVICE_DOWNLOAD_GET, "Errore nell'esecuzione del controller /download_from_register_ruvo metodo GET", ex1, null, utente);
			}
		}
	}

	private void jobElaboraProtocolloRuvo(String protocollo, String nomeFileZippato) {
		try {
			stampedDocument = protocolloService.getDocumentiProtocollatiRuvo(protocollo, nomeFileZippato);
		} catch (Exception e) {
			Log.APP.error("Non è stato possibile scaricare i documenti", e);
		}
	}

	@RequestMapping(value = "/downloadA", method = RequestMethod.GET)
	public void downloadAllegatoWithGet(HttpServletRequest request, HttpServletResponse response) {
		Utente utente = utentiService.getUtenteConnesso(request);
		try {
			List<Configuration> attachmentsFolderConfig = configurationService.findByName(SessionConstants.TEMP_FOLDER);
			Configuration config = attachmentsFolderConfig.get(0);
			String tempFolder = config.getValue();
			String idFile = request.getParameter("id_file");
			File file = new File(tempFolder+File.separator+Constants.UNZIP_FOLDER+idFile);
			byte[] documentBody = FileUtils.readFileToByteArray(file);
			response.setContentType(idFile.split("\\.")[1]);
			response.setContentLength(documentBody.length);
			response.addHeader("Content-Disposition", "attachment; filename=\"" + idFile.split("/")[1] + "\"");
			writeOnStream(response, documentBody);
		} catch (Exception ex) {
			try{
				Log.APP.error("Non è stato possibile scaricare il documento", ex);
				String error = messageSource.getMessage("error.file.downloadfileprotocollo", null, Locale.getDefault());
				response.setContentType("text/plain");
				writeOnStream(response, error.getBytes());
				saveAndlogError(null, it.wego.cross.constants.Error.ERRORE_DOWNLOAD_POST, "Errore nell'esecuzione del controller /download metodo POST", ex, null, utente);
			} catch (IOException ex1) {
				//Non può capitare
				saveAndlogError("", it.wego.cross.constants.Error.ERRORE_DOWNLOAD_POST, "Errore nell'esecuzione del controller /download metodo POST", ex1, null, utente);
			} 
		}
	}

	@RequestMapping(value = "/downloadO", method = RequestMethod.GET)
	public void downloadAllegatoWithGetO(HttpServletRequest request, HttpServletResponse response) {
		Utente utente = utentiService.getUtenteConnesso(request);
		try {
			String idFile = request.getParameter("id_file");
			String pathFileI = allegatiService.getFile(idFile.split("/")[0]);
			File file = new File(pathFileI);

			byte[] documentBody = FileUtils.readFileToByteArray(file);
			response.setContentType(idFile.split("/")[1].split("\\.")[idFile.split("/")[1].split("\\.").length - 1]);
			response.setContentLength(documentBody.length);
			response.addHeader("Content-Disposition", "attachment; filename=\"" + idFile.split("/")[1] + "\"");
			writeOnStream(response, documentBody);
		} catch (Exception ex) {
			try{
				Log.APP.error("Non è stato possibile scaricare il documento", ex);
				String error = messageSource.getMessage("error.file.downloadfileprotocollo", null, Locale.getDefault());
				response.setContentType("text/plain");
				
				writeOnStream(response, error.getBytes());
				saveAndlogError(null, it.wego.cross.constants.Error.ERRORE_DOWNLOAD_POST, "Errore nell'esecuzione del controller /download metodo POST", ex, null, utente);
			} catch (IOException ex1) {
				//Non può capitare
				saveAndlogError("", it.wego.cross.constants.Error.ERRORE_DOWNLOAD_POST, "Errore nell'esecuzione del controller /download metodo POST", ex1, null, utente);
			}
		}
	}

	@RequestMapping(value = "/download/protocollo")
	public void downloadDaProtocollo(HttpServletRequest request, HttpServletResponse response) {
		Utente utente = utentiService.getUtenteConnesso(request);
		try {
			HttpSession session = request.getSession();
			UtenteRuoloEnteDTO ruoloSelezionato = (UtenteRuoloEnteDTO) session.getAttribute(SelectUGInterceptor.CURRENT_SELECTED_UG);
			EnteDTO entePerCuiOpero = ruoloSelezionato.getIdEnte();
			Enti ente = entiService.findByIdEnte(entePerCuiOpero.getIdEnte());
			Integer idProtocollo = Integer.valueOf(request.getParameter("idProtocollo"));
			PraticheProtocollo pp = praticheProtocolloService.findPraticaProtocolloById(idProtocollo);
			//Non ho modo di capire quale plugin utilizzare
			GestioneAllegati ga = pluginService.getGestioneAllegati(ente.getIdEnte(), null);
			Allegato allegato = ga.getFile(pp.getCodDocumento(), ente, null);
			byte[] documentBody = allegato.getFile();
			response.setContentType(allegato.getTipoFile());
			response.setContentLength(documentBody.length);
			response.addHeader("Content-Disposition", "attachment; filename=\"" + allegato.getNomeFile() + "\"");
			writeOnStream(response, documentBody);
		} catch (Exception ex) {
			try{
				Log.APP.error("Non è stato possibile scaricare il documento", ex);
				String error = messageSource.getMessage("error.file.downloadfileprotocollo", null, Locale.getDefault());
				response.setContentType("text/plain");
				writeOnStream(response, error.getBytes());
				saveAndlogError(null, it.wego.cross.constants.Error.ERRORE_DOWNLOAD_PROTOCOLLO_POST, "Errore nell'esecuzione del controller /download/protocollo metodo POST", ex, null, utente);				
			} catch (IOException ex1) {
				//Non può capitare
				saveAndlogError("", it.wego.cross.constants.Error.ERRORE_DOWNLOAD_PROTOCOLLO_POST, "Errore nell'esecuzione del controller /download/protocollo metodo POST", ex1, null, utente);
			} 
		}
	}

	@RequestMapping(value = "/download/protocollo/idfileesterno")
	public void downloadDaProtocolloConIdFileEsterno(HttpServletRequest request, HttpServletResponse response) {
		Utente utente = utentiService.getUtenteConnesso(request);
		try {

			HttpSession session = request.getSession();
			UtenteRuoloEnteDTO ruoloSelezionato = (UtenteRuoloEnteDTO) session.getAttribute(SelectUGInterceptor.CURRENT_SELECTED_UG);
			EnteDTO entePerCuiOpero = ruoloSelezionato.getIdEnte();
			Enti ente = entiService.findByIdEnte(entePerCuiOpero.getIdEnte());
			String idFileEsterno = request.getParameter("idFile");
			GestioneAllegati ga = pluginService.getGestioneAllegati(ente.getIdEnte(), null);
			Allegato allegato = ga.getFile(idFileEsterno, ente, null);
			byte[] documentBody = allegato.getFile();
			response.setContentType(allegato.getTipoFile());
			response.setContentLength(documentBody.length);
			response.addHeader("Content-Disposition", "attachment; filename=\"" + allegato.getNomeFile() + "\"");
			writeOnStream(response, documentBody);
		} catch (Exception ex) {
			
			try{
				Log.APP.error("Non è stato possibile scaricare il documento", ex);
				String error = messageSource.getMessage("error.file.downloadfileprotocollo", null, Locale.getDefault());
				response.setContentType("text/plain");
				writeOnStream(response, error.getBytes());
				saveAndlogError(null, it.wego.cross.constants.Error.ERRORE_DOWNLOAD_PROTOCOLLO_POST, "Errore nell'esecuzione del controller /download/protocollo metodo POST", ex, null, utente);
			} catch (IOException ex1) {
				//Non può capitare
				saveAndlogError("", it.wego.cross.constants.Error.ERRORE_DOWNLOAD_PROTOCOLLO_POST, "Errore nell'esecuzione del controller /download/protocollo metodo POST", ex1, null, utente);
			}
		}
	}

	@RequestMapping(value = "/download/protocollo/path")
	public void downloadDaPraticheProtocolloConPathInB64(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Utente utente = utentiService.getUtenteConnesso(request);
		try {
			String fileName = request.getParameter("fileName");
			String filePath = request.getParameter("filePath");
			fileName = Utils.decodeB64(fileName);
			filePath = Utils.decodeB64(filePath);
			byte[] file = documentiFS.getFile(filePath);

			Tika tika = new Tika();
			String fileType = tika.detect(file, fileName);

			response.setContentType(fileType);
			response.setContentLength(file.length);
			response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			writeOnStream(response, file);
		} catch (Exception ex) {
			Log.APP.error("Non è stato possibile scaricare il documento", ex);
			String error = messageSource.getMessage("error.file.downloadfileprotocollo", null, Locale.getDefault());
			response.setContentType("text/plain");
			writeOnStream(response, error.getBytes());
			saveAndlogError(null, it.wego.cross.constants.Error.ERRORE_DOWNLOAD_PROTOCOLLO_POST, "Errore nell'esecuzione del controller /download/protocollo metodo POST", ex, null, utente);
		} 
	}

	@RequestMapping(value = "/preview", method = RequestMethod.GET)
	public void preview(HttpServletRequest request, HttpServletResponse response) {
		Utente utente = utentiService.getUtenteConnesso(request);
		Pratica pratica = null;


		try {
			Integer idPratica = (Integer) MultiSubSessionManager.getAttribute(request, SessionConstants.ID_PRATICA_SELEZIONATA);
			pratica = praticheService.getPratica(idPratica);
			Integer idFile = Integer.valueOf(request.getParameter("id_file"));



			try {
				AllegatoDTO allegato = allegatiService.findAllegatoByIdNullSafe(idFile, pratica.getIdProcEnte().getIdEnte());
				if (allegato == null || allegato.getFileContent().length == 0){
					throw new Exception("Si sta cercando di scaricare un file vuoto");
				}
				String fileName = allegato.getNomeFile().toLowerCase();
				if (fileName.endsWith(".doc") || fileName.endsWith(".docx") || fileName.endsWith(".odt") || fileName.endsWith(".ods")
						|| fileName.endsWith(".xls") || fileName.endsWith(".xlsx")|| fileName.endsWith(".p7m") ) {
					File inputFile =new File(allegato.getPathFile());
					File outputFile1 = new File(allegato.getPathFile().concat(".pdf"));
					File outputFile = null;
					if(!fileName.endsWith(".p7m")) {
						outputFile = documentConverter.convert(inputFile, outputFile1);
						InputStream stream = new FileInputStream(outputFile);
						allegato.setFileContent( IOUtils.toByteArray(stream));
						allegato.setTipoFile("application/pdf");
						allegato.setNomeFile(fileName.concat(".pdf"));
					}
				}
				byte[] documentBody = null;
				if(fileName.endsWith(".p7m")) {
					String directory = FileUtilities.getParent(allegato.getPathFile());
					documentBody =  FileUtilities.extractByteFromP7M(directory, allegato.getFileContent());
					allegato.setTipoFile("application/pdf");
					allegato.setNomeFile(fileName.replace(".p7m",""));
				}else {
					documentBody = allegato.getFileContent();
				}
				
				response.setContentType(allegato.getTipoFile());
				response.setContentLength(documentBody.length);
				response.addHeader("Content-Disposition", "inline; filename=\"" + allegato.getNomeFile() + "\"");

				writeOnStream(response, documentBody);
			} catch (Exception ex){
				UtenteDTO utenteConnesso = utentiService.getUtenteConnessoDTO(request);
				if (utenteConnesso != null) {
					Log.APP.error("L'utente " + utenteConnesso.getUsername() + " ha cercato di accedere al file con id " + idFile, ex);
				} else {
					Log.APP.error("E' stato tentato un accesso non autorizzato da un utente anonimo al file con id " + idFile, ex);
				}
				response.setContentType("text/plain");
				writeOnStream(response, ex.getMessage().getBytes());
			}
		} catch (Exception ex) {
			try{
				Log.APP.error("Non è stato possibile scaricare il documento", ex);
				String error = messageSource.getMessage("error.file.downloadfileprotocollo", null, Locale.getDefault());
				response.setContentType("text/plain");
				
				writeOnStream(response, error.getBytes());
				saveAndlogError(null, it.wego.cross.constants.Error.ERRORE_DOWNLOAD_GET, "Errore nell'esecuzione del controller /preview metodo GET", ex, pratica, utente);
				
			} catch (IOException ex1) {
				//Non può capitare
				saveAndlogError("", it.wego.cross.constants.Error.ERRORE_DOWNLOAD_GET, "Errore nell'esecuzione del controller /preview metodo GET", ex1, pratica, utente);
			}
		}
	}
	
	@RequestMapping(value = "/previewO", method = RequestMethod.GET)
	public void previewO(HttpServletRequest request, HttpServletResponse response) {
		Utente utente = utentiService.getUtenteConnesso(request);
		Pratica pratica = null;
		try {
			String idFile = request.getParameter("id_file");

			String pathFileI = allegatiService.getFile(idFile.split("/")[0]);
			File tempFile = new File(pathFileI);

			byte[] documentBodyTempFile = FileUtils.readFileToByteArray(tempFile);
			byte[] documentBody = null;
			
			try {
				
				String fileName = idFile.split("/")[1];
				if (fileName.endsWith(".doc") || fileName.endsWith(".docx") || fileName.endsWith(".odt") || fileName.endsWith(".ods")
						|| fileName.endsWith(".xls") || fileName.endsWith(".xlsx")) {
					
					File outputFile1 = new File(tempFile.getAbsolutePath().concat(".pdf"));
					File outputFile = documentConverter.convert(tempFile, outputFile1);
					InputStream stream = new FileInputStream(outputFile);
					documentBody = IOUtils.toByteArray(stream);
					
				}
				else if(fileName.endsWith(".p7m")) {
					documentBody =  FileUtilities.extractByteFromP7M(tempFile.getPath(), documentBodyTempFile);
				}
				else
					documentBody = documentBodyTempFile;
				response.setContentType("application/pdf");
				response.setContentLength(documentBody.length);
				response.addHeader("Content-Disposition", "inline; filename=\"" + tempFile.getName() + "\"");

				writeOnStream(response, documentBody);
			} catch (Exception ex){
				UtenteDTO utenteConnesso = utentiService.getUtenteConnessoDTO(request);
				if (utenteConnesso != null) {
					Log.APP.error("L'utente " + utenteConnesso.getUsername() + " ha cercato di accedere al file con id " + idFile, ex);
				} else {
					Log.APP.error("E' stato tentato un accesso non autorizzato da un utente anonimo al file con id " + idFile, ex);
				}
				response.setContentType("text/plain");
				writeOnStream(response, ex.getMessage().getBytes());  
			}
		} catch (Exception ex) {
			
			try {
				Log.APP.error("Non è stato possibile scaricare il documento", ex);
				String error = messageSource.getMessage("error.file.downloadfileprotocollo", null, Locale.getDefault());
				response.setContentType("text/plain");
				writeOnStream(response, error.getBytes());
				saveAndlogError(null, it.wego.cross.constants.Error.ERRORE_DOWNLOAD_GET, "Errore nell'esecuzione del controller /download metodo GET", ex, pratica, utente);
			} catch (IOException ex1) {
				//Non può capitare
				saveAndlogError("", it.wego.cross.constants.Error.ERRORE_DOWNLOAD_GET, "Errore nell'esecuzione del controller /download metodo GET", ex1, pratica, utente);
			}
		}
	}
	
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void downloadWithGet(HttpServletRequest request, HttpServletResponse response) {
		Utente utente = utentiService.getUtenteConnesso(request);
		Pratica pratica = null;
		Mapper mapper = null;
		 
		try {
			Integer idPratica = (Integer) MultiSubSessionManager.getAttribute(request, SessionConstants.ID_PRATICA_SELEZIONATA);
			pratica = praticheService.getPratica(idPratica);
			Integer idFile = Integer.valueOf(request.getParameter("id_file"));
			byte[] documentBody = null;
			try {
				/* Verifica se si tratta di una pratica DMS */
				String provenienza = it.wego.cross.civilia.Costants.CROSS;
				DettaglioPraticaDTO dettaglio = praticheService.getDettaglioPratica(pratica);
				
                List<it.wego.cross.dto.AllegatoDTO> allegatoDTO = dettaglio.getAllegati();
                AllegatoDTO allegatoDMS = new AllegatoDTO();
                for(it.wego.cross.dto.AllegatoDTO al : allegatoDTO) {
                	if(al.getIdAllegato() != null && al.getIdAllegato().intValue() == idFile.intValue()) {
                		provenienza = al.getProvenienza();
                		allegatoDMS = al;
                		break;
                	}
                }
				if(provenienza == null || it.wego.cross.civilia.Costants.CROSS.equals(provenienza)) {
				
					AllegatoDTO allegato = allegatiService.findAllegatoByIdNullSafe(idFile, pratica.getIdProcEnte().getIdEnte());
					if (allegato == null || allegato.getFileContent().length == 0){
						throw new Exception("Si sta cercando di scaricare un file vuoto");
					}
					documentBody = allegato.getFileContent();
					response.setContentType(allegato.getTipoFile());
					response.setContentLength(documentBody.length);
					response.addHeader("Content-Disposition", "attachment; filename=\"" + allegato.getNomeFile() + "\"");
					writeOnStream(response, documentBody);
				} else if(it.wego.cross.civilia.Costants.DMS.equals(provenienza)){
					DMSClientDao dmsClient = new DMSClientDao();
					String b64 = dmsClient.getAllegatoPratica(request.getParameter("id_file"));
					documentBody  = Base64.decodeBase64(b64);
					if(allegatoDMS.getTipoFile()!=null && !allegatoDMS.getTipoFile().isEmpty() ) {
						response.setContentType(allegatoDMS.getTipoFile());						
					} else {
						response.setContentType("application/pdf");
					}
					
					response.setContentLength(documentBody.length);
					response.addHeader("Content-Disposition", "attachment; filename=\"" + allegatoDMS.getNomeFile() + "\"");
					writeOnStream(response, documentBody);
				}
			} catch (Exception ex){
				UtenteDTO utenteConnesso = utentiService.getUtenteConnessoDTO(request);
				if (utenteConnesso != null) {
					Log.APP.error("L'utente " + utenteConnesso.getUsername() + " ha cercato di accedere al file con id " + idFile, ex);
				} else {
					Log.APP.error("E' stato tentato un accesso non autorizzato da un utente anonimo al file con id " + idFile, ex);
				}
				response.setContentType("text/plain");
				documentBody = ex.getMessage().getBytes();
			}
			writeOnStream(response, documentBody);
		} catch (Exception ex) {
			try{
				Log.APP.error("Non è stato possibile scaricare il documento", ex);
				String error = messageSource.getMessage("error.file.downloadfileprotocollo", null, Locale.getDefault());
				response.setContentType("text/plain");
				writeOnStream(response, error.getBytes());
				saveAndlogError(null, it.wego.cross.constants.Error.ERRORE_DOWNLOAD_GET, "Errore nell'esecuzione del controller /download metodo GET", ex, pratica, utente);
			} catch (IOException ex1) {
				//Non può capitare
				saveAndlogError("", it.wego.cross.constants.Error.ERRORE_DOWNLOAD_GET, "Errore nell'esecuzione del controller /download metodo GET", ex1, pratica, utente);
			} 
		}
	}

	@RequestMapping(value = "/protocollo/download", method = RequestMethod.GET)
	public void downloadFromProtocolloWithGet(HttpServletRequest request, HttpServletResponse response) {
		Utente utente = utentiService.getUtenteConnesso(request);
		Pratica pratica = null;
		try {
			Enti ente = null;
			if (MultiSubSessionManager.getAttribute(request, SessionConstants.ID_PRATICA_SELEZIONATA) != null) {
				Integer idPratica = (Integer) MultiSubSessionManager.getAttribute(request, SessionConstants.ID_PRATICA_SELEZIONATA);
				pratica = praticheService.getPratica(idPratica);
				ente = pratica.getIdProcEnte().getIdEnte();
			} else if (request.getSession().getAttribute(SessionConstants.ENTE_SELEZIONATO) != null) {
				Integer idEnte = (Integer) request.getSession().getAttribute(SessionConstants.ENTE_SELEZIONATO);
				ente = entiService.findByIdEnte(idEnte);
			}

			String idFileEsterno = request.getParameter("id_file");
			Allegato allegatoProtocollo = allegatiService.getAllegatoDaProtocollo(idFileEsterno, ente);
			byte[] documentBody = allegatoProtocollo.getFile();

			response.setContentType(allegatoProtocollo.getTipoFile());
			response.setContentLength(documentBody.length);
			response.addHeader("Content-Disposition", "attachment; filename=\"" + allegatoProtocollo.getNomeFile() + "\"");
			writeOnStream(response, documentBody);
		} catch (Exception ex) {
			try{
				Log.APP.error("Non è stato possibile scaricare il documento", ex);
				String error = messageSource.getMessage("error.file.downloadfileprotocollo", null, Locale.getDefault());
				response.setContentType("text/plain");
				writeOnStream(response, error.getBytes());
				saveAndlogError(null, it.wego.cross.constants.Error.ERRORE_PROTOCOLLO_DOWNLOAD_GET, "Errore nell'esecuzione del controller /download/protocollo metodo GET", ex, pratica, utente);
				
			} catch (IOException ex1) {
				//Non può capitare
				saveAndlogError("", it.wego.cross.constants.Error.ERRORE_PROTOCOLLO_DOWNLOAD_GET, "Errore nell'esecuzione del controller /download/protocollo metodo GET", ex1, pratica, utente);
			} 
		}
	}

	@RequestMapping(value = "/download/pratica")
	public void downloadPraticaOriginale(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Utente utente = utentiService.getUtenteConnesso(request);
		Pratica pratica = null;
		try {
			String idPraticaString = request.getParameter("id_pratica");
			Integer idPratica;
			if (!Utils.e(idPraticaString)) {
				idPratica = Integer.valueOf(idPraticaString);
			} else {
					idPratica = (Integer) MultiSubSessionManager.getAttribute(request, SessionConstants.ID_PRATICA_SELEZIONATA);
			}
			pratica = praticheService.getPratica(idPratica);
			AllegatoDTO allegato = allegatiService.findAllegatoByIdNullSafe(pratica.getIdModello().getId(), pratica.getIdProcEnte().getIdEnte());
			byte[] documentBody = allegato.getFileContent();
			response.setContentType(allegato.getTipoFile());
			response.setContentLength(documentBody.length);
			response.addHeader("Content-Disposition", "attachment; filename=\"" + allegato.getNomeFile() + "\"");
			writeOnStream(response, documentBody);
		} catch (Exception ex) {
			try{
				Log.APP.error("Non è stato possibile scaricare il documento di riepilogo della pratica. Contattare l'amministratore", ex);
				String error = messageSource.getMessage("error.file.downloadpratica", null, Locale.getDefault());
				response.setContentType("text/plain");
				writeOnStream(response, error.getBytes());
				saveAndlogError(null, it.wego.cross.constants.Error.ERRORE_DOWNLOAD_PRATICA, "Errore nell'esecuzione del controller /download/pratica", ex, pratica, utente);
			} catch (IOException ex1) {
				//Non può capitare
				saveAndlogError("", it.wego.cross.constants.Error.ERRORE_DOWNLOAD_PRATICA, "Errore nell'esecuzione del controller /download/pratica", ex1, pratica, utente);
			}
		}
	}

	@RequestMapping(value = "/download/generated", method = RequestMethod.GET)
	public void downloadDocumentoGenerato(Integer idEventoTemplate, HttpServletRequest request, HttpServletResponse response) {
		Utente utente = utentiService.getUtenteConnesso(request);
		Pratica pratica = null;
		try {
			String idPraticaString = request.getParameter("id_pratica");
			Integer idPratica;
			if (!Utils.e(idPraticaString)) {
				idPratica = Integer.valueOf(idPraticaString);
			} else {
					idPratica = (Integer) MultiSubSessionManager.getAttribute(request, SessionConstants.ID_PRATICA_SELEZIONATA);
			}
			EventiTemplate eventoTemplate = workflowService.findEventiTemplateById(idEventoTemplate);
			pratica = praticheService.getPratica(idPratica);
			byte[] documentBody = workflowService.generaDocumento(idEventoTemplate, idPratica, utente);
			if (documentBody != null) {
				response.setContentType(eventoTemplate.getIdTemplate().getMimeType());
				response.setContentLength(documentBody.length);
				response.addHeader("Content-Disposition", "attachment; filename=\"" + eventoTemplate.getIdTemplate().getNomeFile() + "\"");
				documentBody = (documentBody);
			} else {
				Enti ente = eventoTemplate.getIdEnte();
				String error;
				if (ente != null) {
					error = "Nessun template associato all'evento " + eventoTemplate.getIdEvento().getDesEvento() + " per l'ente " + ente.getDescrizione();
					Log.APP.error(error);
				} else {
					error = "Nessun template associato all'evento " + eventoTemplate.getIdEvento().getDesEvento();
					Log.APP.error(error);
				}
				response.setContentType("text/plain");
				documentBody = (error.getBytes());
			}
			writeOnStream(response, documentBody);
		} catch (Exception ex) {
			Log.APP.error("E' avvenuto un errore nella generazione del documento", ex);
			try{
				writeOnStream(response, ex.getMessage().getBytes());
				saveAndlogError(null, it.wego.cross.constants.Error.ERRORE_DOWNLOAD_GENERETED, "Errore nell'esecuzione del controller /download/generated", ex, pratica, utente);
			} catch (Exception exx) {
				saveAndlogError("Si è verificato un errore chiudendo il documento", it.wego.cross.constants.Error.ERRORE_DOWNLOAD_GENERETED, "Errore nell'esecuzione del controller /download/generated", exx, pratica, utente);
				
			}
		}
	}

	@RequestMapping(value = "/download/template", method = RequestMethod.GET)
	public void downloadTemplate(Integer idTemplate, HttpServletRequest request, HttpServletResponse response) {
		Utente utente = utentiService.getUtenteConnesso(request);
		try {
			Templates template = workflowService.generaTemplate(idTemplate);
			byte[] documentBody = null;
			if (template.getTemplate() != null) {
				//IL TEMPLATE E' SEMPRE IN FORMATO ODT!!!!
				HttpHeaders header = new HttpHeaders();
				header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				if (template.getNomeFileOriginale() != null) {
					header.set("Content-Disposition", "attachment; filename=\"" + template.getNomeFileOriginale() + "\"");
				} else {
					header.set("Content-Disposition", "attachment; filename=\"modello.odt\"");
				}
				response.setContentType(MediaType.APPLICATION_OCTET_STREAM.toString());
				response.setContentLength(template.getTemplate().length());
				if (template.getNomeFileOriginale() != null) {
					response.addHeader("Content-Disposition", "attachment; filename=\"" + template.getNomeFileOriginale() + "\"");
				} else {
					response.addHeader("Content-Disposition", "attachment; filename=\"modello.odt\"");
				}
				documentBody = (new Base64().decode(template.getTemplate()));

			} else {
				String error = "Nessun template trovato";
				Log.APP.error(error);
				response.setContentType("text/plain");
				documentBody =(error.getBytes());
			}
			writeOnStream(response, documentBody);
		} catch (Exception ex) {
			Log.APP.error("Si è verificato un errore scaricando il template: " + ex.getMessage(), ex);

			try{
				writeOnStream(response, ex.getMessage().getBytes());
				saveAndlogError(null, it.wego.cross.constants.Error.ERRORE_DOWNLOAD_TEMPLATE_GET, "Errore nell'esecuzione del controller /download/template", ex, null, utente);
				
			} catch (Exception exx) {
				saveAndlogError("Si è verificato un errore scaricando il template: " + exx.getMessage(), it.wego.cross.constants.Error.ERRORE_DOWNLOAD_TEMPLATE_GET, "Errore nell'esecuzione del controller /download/template", exx, null, utente);
			}
		}
	}

	@RequestMapping(value = "/generaXMLSuri")
	public void generaXMLSuri(Integer idTemplate, @ModelAttribute("pratica") EventoDTO evento, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
		Utente utente = utentiService.getUtenteConnesso(request);
		try {
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM.toString());
			String xmlSuri = praticheAction.generaXMLSuri(evento);
			response.setContentLength(xmlSuri.length());
			response.setContentType(MediaType.TEXT_XML_VALUE);
			response.addHeader("Content-Disposition", "attachment; filename=\"" + evento.getDescrizione() + ".xml\"");
			writeOnStream(response, xmlSuri.getBytes());
		} catch (Exception ex) {
			String errore = "errore generazione xml SU.RI.";
			Log.APP.error(errore, ex);
			response.setContentLength(errore.length());
			response.setContentType(MediaType.TEXT_PLAIN_VALUE);
			response.addHeader("Content-Disposition", "attachment; filename=errore.txt");
			Log.APP.error("Si è verificato un errore scaricando il template: " + ex.getMessage(), ex);

			try{
				writeOnStream(response, errore.getBytes());
				saveAndlogError(null, it.wego.cross.constants.Error.ERRORE_GENERAXMLSURI, "Errore nell'esecuzione del controller /generaXMLSuri", ex, null, utente);
			} catch (Exception exx) {
				saveAndlogError(errore, it.wego.cross.constants.Error.ERRORE_GENERAXMLSURI, "Errore nell'esecuzione del controller /generaXMLSuri", exx, null, utente);
			}
		}
	}

	/* *************************
	 * PRIVATE METHODS
	 ****************************/
	
	public void createAndDownloadZip(HttpServletResponse response, Pratica pratica, List<AllegatoDTO> allegatiList) throws Exception {
		Enti ente = pratica.getIdProcEnte().getIdEnte();
		try(FileOutputStream fos = new FileOutputStream("pratica" + pratica.getIdPratica() + ".zip");
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				ZipOutputStream zos = new ZipOutputStream(bos)){
			HashSet<String> names = new HashSet<>();
			for(AllegatoDTO allegato : allegatiList) {
				AllegatoDTO allegatoD = allegatiService.findAllegatoByIdNullSafe(allegato.getIdAllegato(), ente);
				String nomeFile = (names.add(allegato.getNomeFile())?"":UUID.randomUUID())+allegato.getNomeFile();
				
				StringBuilder prefix = new StringBuilder("");
				if(allegato.getDataProtocollo() != null) {
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(allegato.getDataProtocollo());
					prefix.append(calendar.get(Calendar.YEAR)).append("_");
				}
				if(allegato.getProtocollo() != null) {
					String[] temp = allegato.getProtocollo().split("/");
					prefix.append(temp[temp.length-1]).append("_");
				}
				nomeFile = prefix + nomeFile;

				zos.putNextEntry(new ZipEntry(nomeFile));
				zos.write(allegatoD.getFileContent());
				zos.closeEntry();
			}
		}
	
		File file = new File("pratica" + pratica.getIdPratica() + ".zip");
		if(!file.exists())
			Log.APP.warn("file not found");
		
		response.setContentType("application/zip");
		response.setHeader("Content-Disposition","attachment; filename=\"" + "pratica" + pratica.getIdentificativoPratica()  + ".zip\"");

		OutputStream out = response.getOutputStream();
		FileInputStream in = new FileInputStream(file);
		byte[] buffer = new byte[4096];
		int length;
		while ((length = in.read(buffer)) > 0){
			out.write(buffer, 0, length);
		}
		in.close();
		out.flush();    
		out.close();
		if(file.exists())
			file.delete();
	}
	
	private void writeOnStream(HttpServletResponse response, byte[] documentBody) throws IOException {
		OutputStream out = response.getOutputStream();
		out.write(documentBody);
		out.flush();
		out.close();
	}
	
	private void saveAndlogError(String logMessage, String constant, String message, Exception e, Pratica pratica, Utente utente) {
		ErroreDTO err = erroriAction.getError( constant, message, e, pratica, utente);
		erroriAction.saveError(err);
		if(logMessage != null)
			Log.APP.error(logMessage, e);
	}
}
