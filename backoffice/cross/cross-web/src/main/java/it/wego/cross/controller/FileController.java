/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.controller;

import it.wego.cross.actions.ErroriAction;
import it.wego.cross.actions.PraticheAction;
import it.wego.cross.constants.SessionConstants;
import it.wego.cross.dto.AllegatoDTO;
import it.wego.cross.dto.ErroreDTO;
import it.wego.cross.dto.EventoDTO;
import it.wego.cross.dto.UtenteDTO;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.EventiTemplate;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticheProtocollo;
import it.wego.cross.entity.Templates;
import it.wego.cross.entity.Utente;
import it.wego.cross.interceptor.SelectUGInterceptor;
import it.wego.cross.plugins.commons.beans.Allegato;
import it.wego.cross.plugins.documenti.GestioneAllegati;
import it.wego.cross.plugins.documenti.GestioneAllegatiFS;
import it.wego.cross.service.AllegatiService;
import it.wego.cross.service.EntiService;
import it.wego.cross.service.PluginService;
import it.wego.cross.service.PraticheProtocolloService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.service.WorkFlowService;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.binary.Base64;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * I controller sollevano tutti una eccezione. Mi aspetto infatti di
 * visualizzare l'errore su una nuova pagina
 *
 * @author giuseppe
 */
@Controller
public class FileController extends AbstractController {

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
            OutputStream out = response.getOutputStream();
            out.write(documentBody);
            out.flush();
            out.close();
        } catch (Exception ex) {
            OutputStream out = null;
            try {
                Log.APP.error("Non è stato possibile scaricare il documento", ex);
                String error = messageSource.getMessage("error.file.downloadfileprotocollo", null, Locale.getDefault());
                response.setContentType("text/plain");
                out = response.getOutputStream();
                out.write(error.getBytes());
                out.flush();
                out.close();
                ErroreDTO err = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_DOWNLOAD_POST, "Errore nell'esecuzione del controller /download metodo POST", ex, null, utente);
                erroriAction.saveError(err);
            } catch (IOException ex1) {
                //Non può capitare
                ErroreDTO err = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_DOWNLOAD_POST, "Errore nell'esecuzione del controller /download metodo POST", ex1, null, utente);
                erroriAction.saveError(err);
                Log.APP.error("",ex1);
            } finally {
                try {

                    out.close();
                } catch (IOException ex1) {
                    //Non può capitare
                    ErroreDTO err = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_DOWNLOAD_POST, "Errore nell'esecuzione del controller /download metodo POST", ex1, null, utente);
                    erroriAction.saveError(err);
                    Log.APP.error("", ex1);
                }
            }
        }
    }

    //@RequestMapping(value = "/download/protocollo", method = RequestMethod.POST)
    @RequestMapping(value = "/download/protocollo")
    public void downloadDaProtocollo(HttpServletRequest request, HttpServletResponse response) {
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            HttpSession session = request.getSession();
            it.wego.cross.dto.dozer.UtenteRuoloEnteDTO ruoloSelezionato = (it.wego.cross.dto.dozer.UtenteRuoloEnteDTO) session.getAttribute(SelectUGInterceptor.CURRENT_SELECTED_UG);
            it.wego.cross.dto.dozer.EnteDTO entePerCuiOpero = ruoloSelezionato.getIdEnte();
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
            OutputStream out = response.getOutputStream();
            out.write(documentBody);
            out.flush();
            out.close();
        } catch (Exception ex) {
            OutputStream out = null;
            try {
                Log.APP.error("Non è stato possibile scaricare il documento", ex);
                String error = messageSource.getMessage("error.file.downloadfileprotocollo", null, Locale.getDefault());
                response.setContentType("text/plain");
                out = response.getOutputStream();
                out.write(error.getBytes());
                out.flush();
                out.close();
                ErroreDTO err = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_DOWNLOAD_PROTOCOLLO_POST, "Errore nell'esecuzione del controller /download/protocollo metodo POST", ex, null, utente);
                erroriAction.saveError(err);
            } catch (IOException ex1) {
                //Non può capitare
                ErroreDTO err = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_DOWNLOAD_PROTOCOLLO_POST, "Errore nell'esecuzione del controller /download/protocollo metodo POST", ex1, null, utente);
                erroriAction.saveError(err);
                Log.APP.error("", ex1);
            } finally {
                try {
                    out.close();
                } catch (IOException ex1) {
                    //Non può capitare
                    ErroreDTO err = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_DOWNLOAD_PROTOCOLLO_POST, "Errore nell'esecuzione del controller /download/protocollo metodo POST", ex1, null, utente);
                    erroriAction.saveError(err);
                    Log.APP.error("", ex1);
                }
            }
        }
    }

    @RequestMapping(value = "/download/protocollo/idfileesterno")
    public void downloadDaProtocolloConIdFileEsterno(HttpServletRequest request, HttpServletResponse response) {
        Utente utente = utentiService.getUtenteConnesso(request);
        try {

            HttpSession session = request.getSession();
            it.wego.cross.dto.dozer.UtenteRuoloEnteDTO ruoloSelezionato = (it.wego.cross.dto.dozer.UtenteRuoloEnteDTO) session.getAttribute(SelectUGInterceptor.CURRENT_SELECTED_UG);
            it.wego.cross.dto.dozer.EnteDTO entePerCuiOpero = ruoloSelezionato.getIdEnte();
            Enti ente = entiService.findByIdEnte(entePerCuiOpero.getIdEnte());
            String idFileEsterno = request.getParameter("idFile");
//            Integer idProtocollo = Integer.valueOf(request.getParameter("idProtocollo"));
//            PraticheProtocollo pp = praticheProtocolloService.findPraticaProtocolloById(idProtocollo);
            //Non ho modo di capire quale plugin utilizzare
            GestioneAllegati ga = pluginService.getGestioneAllegati(ente.getIdEnte(), null);
            Allegato allegato = ga.getFile(idFileEsterno, ente, null);
            byte[] documentBody = allegato.getFile();
            response.setContentType(allegato.getTipoFile());
            response.setContentLength(documentBody.length);
            response.addHeader("Content-Disposition", "attachment; filename=\"" + allegato.getNomeFile() + "\"");
            OutputStream out = response.getOutputStream();
            out.write(documentBody);
            out.flush();
            out.close();
        } catch (Exception ex) {
            OutputStream out = null;
            try {
                Log.APP.error("Non è stato possibile scaricare il documento", ex);
                String error = messageSource.getMessage("error.file.downloadfileprotocollo", null, Locale.getDefault());
                response.setContentType("text/plain");
                out = response.getOutputStream();
                out.write(error.getBytes());
                out.flush();
                out.close();
                ErroreDTO err = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_DOWNLOAD_PROTOCOLLO_POST, "Errore nell'esecuzione del controller /download/protocollo metodo POST", ex, null, utente);
                erroriAction.saveError(err);
            } catch (IOException ex1) {
                //Non può capitare
                ErroreDTO err = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_DOWNLOAD_PROTOCOLLO_POST, "Errore nell'esecuzione del controller /download/protocollo metodo POST", ex1, null, utente);
                erroriAction.saveError(err);
                Log.APP.error("", ex1);
            } finally {
                try {
                    out.close();
                } catch (IOException ex1) {
                    //Non può capitare
                    ErroreDTO err = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_DOWNLOAD_PROTOCOLLO_POST, "Errore nell'esecuzione del controller /download/protocollo metodo POST", ex1, null, utente);
                    erroriAction.saveError(err);
                    Log.APP.error("", ex1);
                }
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
            response.getOutputStream().write(file);
        } catch (Exception ex) {
            Log.APP.error("Non è stato possibile scaricare il documento", ex);
            String error = messageSource.getMessage("error.file.downloadfileprotocollo", null, Locale.getDefault());
            response.setContentType("text/plain");
            response.getOutputStream().write(error.getBytes());
            ErroreDTO err = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_DOWNLOAD_PROTOCOLLO_POST, "Errore nell'esecuzione del controller /download/protocollo metodo POST", ex, null, utente);
            erroriAction.saveError(err);
        } finally {
            response.getOutputStream().flush();
            response.getOutputStream().close();
        }
    }

//    @-Transactional
//    private AllegatoDTO findAllegatoById(Integer idAllegato, Enti ente) throws Exception {
//        AllegatoDTO allegato;
//        if (ente != null) {
//            allegato = allegatiService.getAllegato(idAllegato, ente.getIdEnte());
//        } else {
//            allegato = allegatiService.getAllegato(idAllegato, null);
//        }
//        return allegato;
//    }
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void downloadWithGet(HttpServletRequest request, HttpServletResponse response) {
        Utente utente = utentiService.getUtenteConnesso(request);
        Pratica pratica = null;
        try {
            Integer idPratica = (Integer) request.getSession().getAttribute(SessionConstants.ID_PRATICA_SELEZIONATA);
            pratica = praticheService.getPratica(idPratica);
            Integer idFile = Integer.valueOf(request.getParameter("id_file"));
//            boolean isDownloadPermitted = allegatiService.checkAllegato(pratica, idFile);
//            if (isDownloadPermitted) {
            try {
                AllegatoDTO allegato = allegatiService.findAllegatoByIdNullSafe(idFile, pratica.getIdProcEnte().getIdEnte());
                if (allegato == null || allegato.getFileContent().length == 0){
                    throw new Exception("Si sta cercando di scaricare un file vuoto");
                }
                byte[] documentBody = allegato.getFileContent();
                response.setContentType(allegato.getTipoFile());
                response.setContentLength(documentBody.length);
                response.addHeader("Content-Disposition", "attachment; filename=\"" + allegato.getNomeFile() + "\"");
                OutputStream out = response.getOutputStream();
                out.write(documentBody);
                out.flush();
                out.close();
            } catch (Exception ex){
                UtenteDTO utenteConnesso = utentiService.getUtenteConnessoDTO(request);
                if (utenteConnesso != null) {
                    Log.APP.error("L'utente " + utenteConnesso.getUsername() + " ha cercato di accedere al file con id " + idFile, ex);
                } else {
                    Log.APP.error("E' stato tentato un accesso non autorizzato da un utente anonimo al file con id " + idFile, ex);
                }
//                String error = messageSource.getMessage("error.file.permessonegato", null, Locale.getDefault());
                response.setContentType("text/plain");
                OutputStream out = response.getOutputStream();
                out.write(ex.getMessage().getBytes());
                out.flush();
                out.close();   
            }
        } catch (Exception ex) {
            OutputStream out = null;
            try {
                Log.APP.error("Non è stato possibile scaricare il documento", ex);
                String error = messageSource.getMessage("error.file.downloadfileprotocollo", null, Locale.getDefault());
                response.setContentType("text/plain");
                out = response.getOutputStream();
                out.write(error.getBytes());
                out.flush();
                out.close();
                ErroreDTO err = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_DOWNLOAD_GET, "Errore nell'esecuzione del controller /download metodo GET", ex, pratica, utente);
                erroriAction.saveError(err);
            } catch (IOException ex1) {
                //Non può capitare
                ErroreDTO err = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_DOWNLOAD_GET, "Errore nell'esecuzione del controller /download metodo GET", ex1, pratica, utente);
                erroriAction.saveError(err);
                Log.APP.error("", ex1);
            } finally {
                try {
                    out.close();
                } catch (IOException ex1) {
                    //Non può capitare
                    ErroreDTO err = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_DOWNLOAD_GET, "Errore nell'esecuzione del controller /download metodo GET", ex1, pratica, utente);
                    erroriAction.saveError(err);
                    Log.APP.error("", ex1);
                }
            }
        }
    }

    @RequestMapping(value = "/protocollo/download", method = RequestMethod.GET)
    public void downloadFromProtocolloWithGet(HttpServletRequest request, HttpServletResponse response) {
        Utente utente = utentiService.getUtenteConnesso(request);
        Pratica pratica = null;
        try {
            Enti ente = null;
            if (request.getSession().getAttribute(SessionConstants.ID_PRATICA_SELEZIONATA) != null) {
                Integer idPratica = (Integer) request.getSession().getAttribute(SessionConstants.ID_PRATICA_SELEZIONATA);
                pratica = praticheService.getPratica(idPratica);
                ente = pratica.getIdProcEnte().getIdEnte();
            } else if (request.getSession().getAttribute(SessionConstants.ENTE_SELEZIONATO) != null) {
                Integer idEnte = (Integer) request.getSession().getAttribute(SessionConstants.ENTE_SELEZIONATO);
                ente = entiService.findByIdEnte(idEnte);
            }

            String idFileEsterno = request.getParameter("id_file");
            it.wego.cross.plugins.commons.beans.Allegato allegatoProtocollo = allegatiService.getAllegatoDaProtocollo(idFileEsterno, ente);
            byte[] documentBody = allegatoProtocollo.getFile();

            response.setContentType(allegatoProtocollo.getTipoFile());
            response.setContentLength(documentBody.length);
            response.addHeader("Content-Disposition", "attachment; filename=\"" + allegatoProtocollo.getNomeFile() + "\"");
            OutputStream out = response.getOutputStream();
            out.write(documentBody);
            out.flush();
            out.close();
        } catch (Exception ex) {
            OutputStream out = null;
            try {
                Log.APP.error("Non è stato possibile scaricare il documento", ex);
                String error = messageSource.getMessage("error.file.downloadfileprotocollo", null, Locale.getDefault());
                response.setContentType("text/plain");
                out = response.getOutputStream();
                out.write(error.getBytes());
                out.flush();
                out.close();
                ErroreDTO err = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PROTOCOLLO_DOWNLOAD_GET, "Errore nell'esecuzione del controller /download/protocollo metodo GET", ex, pratica, utente);
                erroriAction.saveError(err);
            } catch (IOException ex1) {
                //Non può capitare
                ErroreDTO err = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PROTOCOLLO_DOWNLOAD_GET, "Errore nell'esecuzione del controller /download/protocollo metodo GET", ex1, pratica, utente);
                erroriAction.saveError(err);
                Log.APP.error("", ex1);
            } finally {
                try {

                    out.close();
                } catch (IOException ex1) {
                    //Non può capitare           
                    ErroreDTO err = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PROTOCOLLO_DOWNLOAD_GET, "Errore nell'esecuzione del controller /download/protocollo metodo GET", ex1, pratica, utente);
                    erroriAction.saveError(err);
                    Log.APP.error("", ex1);
                }
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
                idPratica = (Integer) request.getSession().getAttribute(SessionConstants.ID_PRATICA_SELEZIONATA);
            }
            pratica = praticheService.getPratica(idPratica);
            AllegatoDTO allegato = allegatiService.findAllegatoByIdNullSafe(pratica.getIdModello().getId(), pratica.getIdProcEnte().getIdEnte());
            byte[] documentBody = allegato.getFileContent();
            response.setContentType(allegato.getTipoFile());
            response.setContentLength(documentBody.length);
            response.addHeader("Content-Disposition", "attachment; filename=\"" + allegato.getNomeFile() + "\"");
            OutputStream out = response.getOutputStream();
            out.write(documentBody);
            out.flush();
            out.close();
        } catch (Exception ex) {
            OutputStream out = null;
            try {
                Log.APP.error("Non è stato possibile scaricare il documento di riepilogo della pratica. Contattare l'amministratore", ex);
                String error = messageSource.getMessage("error.file.downloadpratica", null, Locale.getDefault());
                response.setContentType("text/plain");
                out = response.getOutputStream();
                out.write(error.getBytes());
                out.flush();
                ErroreDTO err = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_DOWNLOAD_PRATICA, "Errore nell'esecuzione del controller /download/pratica", ex, pratica, utente);
                erroriAction.saveError(err);
            } catch (IOException ex1) {
                //Non può capitare
                ErroreDTO err = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_DOWNLOAD_PRATICA, "Errore nell'esecuzione del controller /download/pratica", ex1, pratica, utente);
                erroriAction.saveError(err);
                Log.APP.error("", ex1);
            } finally {
                try {
                    out.close();
                } catch (IOException ex1) {
                    //Non può capitare
                    ErroreDTO err = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_DOWNLOAD_PRATICA, "Errore nell'esecuzione del controller /download/pratica", ex1, pratica, utente);
                    erroriAction.saveError(err);
                    Log.APP.error("", ex1);
                }
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
                idPratica = (Integer) request.getSession().getAttribute(SessionConstants.ID_PRATICA_SELEZIONATA);
            }
            EventiTemplate eventoTemplate = workflowService.findEventiTemplateById(idEventoTemplate);
            pratica = praticheService.getPratica(idPratica);
            byte[] documentBody = workflowService.generaDocumento(idEventoTemplate, idPratica, utente);
            if (documentBody != null) {
                response.setContentType(eventoTemplate.getIdTemplate().getMimeType());
                response.setContentLength(documentBody.length);
                response.addHeader("Content-Disposition", "attachment; filename=\"" + eventoTemplate.getIdTemplate().getNomeFile() + "\"");
                OutputStream out = response.getOutputStream();
                out.write(documentBody);
                out.flush();
                out.close();
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
                OutputStream out = response.getOutputStream();
                out.write(error.getBytes());
                out.flush();
            }
        } catch (Exception ex) {
            Log.APP.error("E' avvenuto un errore nella generazione del documento", ex);
            try {
                OutputStream out = response.getOutputStream();
                out.write(ex.getMessage().getBytes());
                out.flush();
                ErroreDTO err = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_DOWNLOAD_GENERETED, "Errore nell'esecuzione del controller /download/generated", ex, pratica, utente);
                erroriAction.saveError(err);
            } catch (Exception exx) {
                Log.APP.error("Si è verificato un errore chiudendo il documento", exx);
                ErroreDTO err = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_DOWNLOAD_GENERETED, "Errore nell'esecuzione del controller /download/generated", exx, pratica, utente);
                erroriAction.saveError(err);
            }
        }
    }

////    @-Transactional
//    private EventiTemplate findEventoTemplate(Integer idEventoTemplate) {
//        EventiTemplate eventoTemplate = workflowService.findEventiTemplateById(idEventoTemplate);
//        return eventoTemplate;
//    }
////    @-Transactional
//    private byte[] generaDocumento(Integer idEventoTemplate, Integer idPratica, Utente utente) throws Exception {
//        EventiTemplate eventoTemplate = workflowService.findEventiTemplateById(idEventoTemplate);
//        Pratica pratica = praticheService.findPratica(idPratica);
//        byte[] documentBody = workflowService.generaDocumento(eventoTemplate, pratica, utente);
//        return documentBody;
//    }
    @RequestMapping(value = "/download/template", method = RequestMethod.GET)
    public void downloadTemplate(Integer idTemplate, HttpServletRequest request, HttpServletResponse response) {
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            Templates template = workflowService.generaTemplate(idTemplate);
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
                OutputStream out = response.getOutputStream();
                out.write(new Base64().decode(template.getTemplate()));
                out.flush();
                out.close();
            } else {
                String error = "Nessun template trovato";
                Log.APP.error(error);
                response.setContentType("text/plain");
                OutputStream out = response.getOutputStream();
                out.write(error.getBytes());
                out.flush();
            }
        } catch (Exception ex) {
            Log.APP.error("Si è verificato un errore scaricando il template: " + ex.getMessage(), ex);

            try {
                OutputStream out = response.getOutputStream();
                out.write(ex.getMessage().getBytes());
                out.flush();
                ErroreDTO err = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_DOWNLOAD_TEMPLATE_GET, "Errore nell'esecuzione del controller /download/template", ex, null, utente);
                erroriAction.saveError(err);
            } catch (Exception exx) {
                Log.APP.error("Si è verificato un errore scaricando il template: " + exx.getMessage(), exx);
                ErroreDTO err = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_DOWNLOAD_TEMPLATE_GET, "Errore nell'esecuzione del controller /download/template", exx, null, utente);
                erroriAction.saveError(err);
            }
        }
    }

////    @-Transactional
//    private Templates findTemplate(Integer idTemplate) throws Exception {
//        Templates template = workflowService.generaTemplate(idTemplate);
//        return template;
//    }
    //^^CS AGGIUNTA download XML SURI
    @RequestMapping(value = "/generaXMLSuri")
    public void generaXMLSuri(Integer idTemplate, @ModelAttribute("pratica") EventoDTO evento, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM.toString());
            String xmlSuri = praticheAction.generaXMLSuri(evento);
            response.setContentLength(xmlSuri.length());
            response.setContentType(MediaType.TEXT_XML_VALUE);
            response.addHeader("Content-Disposition", "attachment; filename=\"" + evento.getDescrizione() + ".xml\"");
            OutputStream out = response.getOutputStream();
            out.write(xmlSuri.getBytes());
            out.flush();
            out.close();
        } catch (Exception ex) {
            String errore = "errore generazione xml SU.RI.";
            Log.APP.error(errore, ex);
            response.setContentLength(errore.length());
            response.setContentType(MediaType.TEXT_PLAIN_VALUE);
            response.addHeader("Content-Disposition", "attachment; filename=errore.txt");
            Log.APP.error("Si è verificato un errore scaricando il template: " + ex.getMessage(), ex);

            try {
                OutputStream out = response.getOutputStream();
                out.write(errore.getBytes());
                out.flush();
                out.close();
                ErroreDTO err = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_GENERAXMLSURI, "Errore nell'esecuzione del controller /generaXMLSuri", ex, null, utente);
                erroriAction.saveError(err);
            } catch (Exception exx) {
                Log.APP.error(errore, exx);
                ErroreDTO err = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_DOWNLOAD_TEMPLATE_GET, "Errore nell'esecuzione del controller /generaXMLSuri", exx, null, utente);
                erroriAction.saveError(err);
            }

        }
    }
}
