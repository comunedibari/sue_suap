/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.controller;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.wego.cross.actions.ErroriAction;
import it.wego.cross.beans.layout.Menu;
import it.wego.cross.constants.SessionConstants;
import it.wego.cross.constants.Workflow;
import it.wego.cross.dto.ErroreDTO;
import it.wego.cross.dto.PraticaBarDTO;
import it.wego.cross.dto.UtenteDTO;
import it.wego.cross.dto.filters.CrossAuthenticationModel;
import it.wego.cross.entity.Utente;
import it.wego.cross.interceptor.SelectUGInterceptor;
import it.wego.cross.service.ConfigurationService;
import it.wego.cross.service.MessaggiService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.service.UtentiService;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.activiti.engine.TaskService;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSProcessable;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.SignerId;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author CS
 */
@Controller
public abstract class AbstractController {

    public String codEnteSelezionato = null;
    public String codUtenteEnte = null;
    public String selected = null;

    @Autowired
    public UtentiService utentiService;

    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private PraticheService praticheService;
    @Autowired
    private MessaggiService messaggiService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ErroriAction erroriAction;
    protected Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @ModelAttribute("infobar")
    public List<String> infobar(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        UtenteDTO authUser = utentiService.getUtenteConnessoDTO(request);
        List<String> infobar = new ArrayList<String>();
        String path = request.getContextPath();
        infobar.add(path);
//        if (model.containsAttribute("utenteConnesso")) {
        Utente connectedUser = utentiService.getUtenteConnesso(request);
        if (connectedUser != null) {
            try {
                List<PraticaBarDTO> scadenze = getPraticheInScadenza(connectedUser);
                int countPraticheInScadenza = scadenze.size();
                model.addAttribute("scadenzeBar", scadenze);
                Log.APP.info("Pratiche in scadenz   a = " + countPraticheInScadenza);
                model.addAttribute("scadenzeBarTotal", countPraticheInScadenza);
                List<PraticaBarDTO> praticheDaAprire = getPraticheDaAprire(connectedUser);
                int countPraticheDaAprire = praticheDaAprire.size();
                model.addAttribute("inArrivoBar", praticheDaAprire);
                Log.APP.info("Pratiche in arrivo = " + countPraticheDaAprire);
                model.addAttribute("inArrivoBarTotal", countPraticheDaAprire);
                int totaleDaAprire = countPraticheDaAprire;
                int totaleInScadenza = countPraticheInScadenza;
                int countTotal = totaleDaAprire + totaleInScadenza;
                int messaggiDaLeggere = countMessaggiDaLeggere(connectedUser);
                model.addAttribute("messaggiDaLeggere", messaggiDaLeggere);
                model.addAttribute("praticheTotaliBar", countTotal);
            } catch (Exception ex) {
                ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE__ABSTRACT_INFOBAR, "Errore nella creazione dell'attributo INFOBAR nel modello", ex, null, null);
                erroriAction.saveError(errore);
                Log.APP.error("Si è verificato un errore recuperando le pratiche da aprire", ex);
            }
        }
//        }
        model.addAttribute("isSuperuser", request.getSession().getAttribute(SessionConstants.SUPERUSER));
        model.addAttribute("isEstrazioniUser", request.getSession().getAttribute(SessionConstants.ESTRAZIONIUSER));
        model.addAttribute("isEstrazioniCilaTodoUser", request.getSession().getAttribute(SessionConstants.ESTRAZIONICILATODOUSER));
        model.addAttribute("selectedUG", request.getSession().getAttribute(SelectUGInterceptor.CURRENT_SELECTED_UG));
        model.addAttribute("enablePraticheDaProtocollo", request.getSession().getAttribute(SessionConstants.PRATICA_APERTURA_PROTOCOLLO));
        model.addAttribute("enablePraticheManuali", request.getSession().getAttribute(SessionConstants.PRATICA_APERTURA_MANUALE));
        model.addAttribute("enablePraticheComunica", request.getSession().getAttribute(SessionConstants.PRATICA_APERTURA_COMUNICA));
        model.addAttribute("enablePraticheSuapFvg", request.getSession().getAttribute(SessionConstants.PRATICA_APERTURA_SUAP_FVG));
        model.addAttribute("enablePraticheProtocolloRicerca", request.getSession().getAttribute(SessionConstants.PRATICA_APERTURA_PROTOCOLLO_RICERCA));
        model.addAttribute("enableComunicazioniIngresso", request.getSession().getAttribute(SessionConstants.PRATICA_COMUNICAZIONI_INGRESSO));
        model.addAttribute("enablePraticaMessaggi", request.getSession().getAttribute(SessionConstants.PRATICA_ABILITA_MESSAGGI));
        model.addAttribute("enableDirittiSegreteria", request.getSession().getAttribute(SessionConstants.PRATICA_DIRITTI_SEGRETERIA));
        model.addAttribute("enableTasklist", request.getSession().getAttribute(SessionConstants.TASKLIST_ENABLED));
        model.addAttribute("enableCds", request.getSession().getAttribute(SessionConstants.CDS_ENABLED));
        model.addAttribute("enableGestionePratiche", request.getSession().getAttribute(SessionConstants.RUOLO_GESTIONE_PRATICHE));
        request.setAttribute("version", configurationService.getCachedConfiguration("version", null, null));
        request.setAttribute("applicationName", configurationService.getCachedConfiguration("name.application", null, null));

        if (authUser != null) {
            //model.addAttribute("notificationCount", taskService.createTaskQuery().taskInvolvedUser(authUser.getUsername()).processVariableValueEquals(Workflow.TIPO_TASK, Workflow.TIPO_TASK_NOTIFICA).count());
        	/* Commento count della query per evitare timeout di activi nell attesa di risolvere il problema */
        	model.addAttribute("notificationCount", 5);
        }
        return infobar;
    }

    @ModelAttribute("ACL")
    public Boolean controlla(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

        UtenteDTO authUser = utentiService.getUtenteConnessoDTO(request);
        CrossAuthenticationModel utente = (CrossAuthenticationModel) request.getSession().getAttribute(CrossAuthenticationModel.AUTHENTICATIONDATA);
        String path = request.getContextPath();
        Boolean acl;
        if (utente != null && authUser == null) {
            acl = false;
        } else if (authUser == null && !request.getRequestURI().equals(path + "/protected/index.htm")) {
            acl = false;
        } else {
            if (authUser != null && (authUser.getSuperuser().equals("S") || ((authUser.getUtenteRuoloEnte() != null && !authUser.getUtenteRuoloEnte().isEmpty())))) {
                acl = true;
                model.addAttribute("utenteConnesso", authUser);
            } else {
                acl = false;
            }
        }

        return acl;
    }

    @ModelAttribute("path")
    public String path(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (request != null) {
            return request.getContextPath();
        }
        return null;
    }

    @ModelAttribute("referPath")
    public String referPath(Model model, HttpServletRequest request, HttpServletResponse response) {
        if (request != null) {
            return request.getHeader("Referer");
        }
        return null;
    }

    @ModelAttribute("url")
    public String url(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (request != null) {
            String crossApplicationName = "/" + configurationService.getCachedConfiguration("name.application", null, null);
            String pathNoApp = request.getRequestURI().replaceFirst(crossApplicationName, "");
            model.addAttribute("currentUriB64", Utils.encodeB64(pathNoApp));

            return request.getRequestURI();
        }
        return null;
    }

    @ModelAttribute("menu")
    public Menu menu(Model model, HttpServletRequest request, HttpServletResponse response) {
        Menu menu = new Menu();
        try {
            String selectedMenu = request.getParameter("selected_menu");
            String show_menu = request.getParameter("show_menu");
            if (selectedMenu == null) {
                selectedMenu = "";
            }
            selectedMenu = selectedMenu.toUpperCase();
            if (show_menu != null && show_menu.equals("false")) {
                menu.setCss("display:none");
            } else {
                menu.setCss("");
            }
            menu.setSelectedMenu(selectedMenu);
            menu.setShowMenu(show_menu);
        } catch (Exception e) {
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE__ABSTRACT_MENU, "Errore nella creazione dell'attributo MENU nel modello", e, null, null);
            erroriAction.saveError(errore);
        }
        return menu;
    }

    private List<PraticaBarDTO> getPraticheInScadenza(Utente connectedUser) {
        List<PraticaBarDTO> scadenze = praticheService.findScadenzeForBar(connectedUser);
        return scadenze;
    }

    private List<PraticaBarDTO> getPraticheDaAprire(Utente connectedUser) throws Exception {
        List<PraticaBarDTO> praticheDaAprire = praticheService.findDaAprireForBar(connectedUser);
        return praticheDaAprire;
    }

    private int countMessaggiDaLeggere(Utente connectedUser) {
        Long messaggi = messaggiService.countMessaggiDaLeggere(connectedUser);
        return messaggi.intValue();
    }

    public class WegoChecker {

        private final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());
        private Certificate certificate = null;

        private void loadCertificates() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, CertificateException {
            if (certificate == null) {
                CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
                certificate = certificateFactory.generateCertificate(new ByteArrayInputStream(new byte[]{45, 45, 45, 45, 45, 66, 69, 71, 73, 78, 32, 67, 69, 82, 84, 73, 70, 73, 67, 65, 84, 69, 45, 45, 45, 45, 45, 10, 77, 73, 73, 70, 113, 106, 67, 67, 65, 53, 75, 103, 65, 119, 73, 66, 65, 103, 73, 67, 69, 66, 89, 119, 68, 81, 89, 74, 75, 111, 90, 73, 104, 118, 99, 78, 65, 81, 69, 70, 66, 81, 65, 119, 90, 122, 69, 76, 77, 65, 107, 71, 65, 49, 85, 69, 66, 104, 77, 67, 83, 86, 81, 120, 10, 68, 106, 65, 77, 66, 103, 78, 86, 66, 65, 103, 84, 66, 86, 86, 107, 97, 87, 53, 108, 77, 82, 81, 119, 69, 103, 89, 68, 86, 81, 81, 75, 69, 119, 116, 88, 82, 85, 100, 80, 73, 70, 77, 117, 99, 105, 53, 115, 76, 106, 69, 89, 77, 66, 89, 71, 65, 49, 85, 69, 67, 120, 77, 80, 10, 83, 87, 53, 48, 90, 88, 74, 116, 90, 87, 82, 112, 89, 88, 82, 108, 73, 69, 78, 66, 77, 82, 103, 119, 70, 103, 89, 68, 86, 81, 81, 68, 69, 119, 57, 74, 98, 110, 82, 108, 99, 109, 49, 108, 90, 71, 108, 104, 100, 71, 85, 103, 81, 48, 69, 119, 72, 104, 99, 78, 77, 84, 85, 119, 10, 78, 68, 65, 52, 77, 68, 103, 121, 79, 84, 65, 49, 87, 104, 99, 78, 77, 84, 89, 119, 78, 68, 65, 51, 77, 68, 103, 121, 79, 84, 65, 49, 87, 106, 66, 77, 77, 81, 115, 119, 67, 81, 89, 68, 86, 81, 81, 71, 69, 119, 74, 74, 86, 68, 69, 79, 77, 65, 119, 71, 65, 49, 85, 69, 10, 67, 66, 77, 70, 86, 87, 82, 112, 98, 109, 85, 120, 70, 68, 65, 83, 66, 103, 78, 86, 66, 65, 111, 84, 67, 49, 100, 70, 82, 48, 56, 103, 85, 121, 53, 121, 76, 109, 119, 117, 77, 82, 99, 119, 70, 81, 89, 68, 86, 81, 81, 68, 70, 65, 53, 51, 90, 87, 100, 118, 88, 50, 120, 112, 10, 89, 50, 86, 117, 89, 50, 108, 117, 90, 122, 67, 67, 65, 105, 73, 119, 68, 81, 89, 74, 75, 111, 90, 73, 104, 118, 99, 78, 65, 81, 69, 66, 66, 81, 65, 68, 103, 103, 73, 80, 65, 68, 67, 67, 65, 103, 111, 67, 103, 103, 73, 66, 65, 79, 84, 66, 72, 109, 103, 105, 112, 109, 111, 86, 10, 114, 84, 55, 54, 122, 85, 87, 87, 71, 69, 55, 78, 51, 83, 66, 81, 48, 54, 116, 108, 87, 53, 83, 84, 47, 80, 122, 117, 84, 75, 75, 104, 119, 84, 80, 75, 84, 71, 114, 75, 81, 109, 51, 47, 66, 48, 71, 98, 120, 88, 118, 99, 54, 74, 100, 120, 86, 83, 105, 75, 81, 87, 43, 78, 10, 49, 69, 66, 97, 99, 98, 79, 89, 75, 56, 67, 48, 98, 74, 118, 69, 115, 97, 113, 80, 113, 98, 118, 114, 71, 68, 66, 109, 73, 78, 105, 121, 69, 67, 50, 82, 115, 54, 47, 51, 120, 81, 48, 107, 119, 78, 49, 108, 71, 100, 113, 82, 78, 67, 121, 102, 72, 66, 55, 47, 57, 53, 69, 86, 10, 90, 122, 51, 83, 67, 90, 81, 90, 68, 68, 74, 113, 50, 80, 67, 116, 67, 110, 118, 110, 104, 114, 68, 104, 52, 84, 43, 83, 102, 51, 105, 108, 71, 56, 119, 120, 83, 70, 90, 86, 72, 114, 70, 89, 53, 118, 112, 89, 67, 116, 51, 69, 49, 105, 117, 105, 119, 89, 83, 50, 119, 73, 106, 100, 10, 56, 109, 68, 117, 79, 48, 84, 79, 65, 86, 78, 98, 82, 109, 43, 77, 66, 81, 83, 83, 109, 108, 73, 107, 76, 76, 84, 50, 100, 114, 83, 104, 98, 80, 110, 119, 99, 74, 78, 119, 88, 51, 122, 76, 113, 107, 115, 89, 99, 55, 112, 107, 89, 76, 76, 81, 73, 80, 113, 51, 107, 117, 65, 101, 10, 105, 115, 50, 76, 121, 99, 82, 100, 69, 100, 71, 67, 109, 49, 120, 103, 70, 52, 52, 99, 67, 49, 115, 111, 74, 109, 71, 115, 83, 67, 110, 82, 86, 118, 55, 71, 81, 78, 100, 111, 72, 70, 73, 77, 47, 53, 79, 121, 108, 109, 48, 102, 57, 56, 100, 47, 78, 73, 116, 83, 117, 56, 78, 54, 10, 115, 85, 76, 111, 119, 122, 110, 98, 50, 52, 112, 106, 54, 70, 56, 65, 77, 99, 111, 111, 73, 48, 120, 76, 102, 71, 66, 107, 84, 116, 72, 72, 107, 79, 116, 66, 113, 111, 113, 56, 87, 114, 114, 100, 49, 81, 65, 99, 119, 52, 78, 108, 86, 108, 84, 49, 79, 68, 76, 71, 109, 49, 49, 87, 10, 76, 69, 118, 83, 114, 98, 48, 50, 78, 65, 82, 110, 115, 83, 115, 76, 84, 48, 53, 108, 120, 87, 67, 117, 80, 119, 69, 109, 85, 89, 83, 74, 106, 90, 98, 103, 115, 106, 102, 72, 103, 113, 65, 107, 67, 77, 115, 101, 99, 81, 82, 107, 83, 86, 54, 109, 85, 65, 66, 71, 78, 84, 75, 84, 10, 68, 55, 79, 115, 87, 111, 97, 111, 70, 115, 118, 117, 118, 67, 120, 47, 105, 111, 105, 70, 107, 67, 49, 121, 99, 50, 87, 112, 50, 73, 84, 68, 71, 70, 73, 72, 121, 43, 49, 52, 83, 75, 106, 87, 105, 49, 84, 68, 121, 81, 90, 47, 52, 87, 74, 81, 117, 90, 118, 54, 56, 89, 84, 48, 10, 78, 108, 121, 107, 56, 51, 56, 72, 100, 69, 54, 113, 51, 77, 107, 86, 54, 73, 76, 99, 99, 104, 69, 80, 72, 117, 119, 78, 50, 65, 111, 102, 110, 50, 75, 117, 81, 103, 108, 88, 47, 48, 116, 43, 55, 49, 103, 118, 82, 82, 101, 82, 70, 104, 66, 65, 51, 68, 116, 83, 52, 90, 73, 56, 10, 98, 99, 88, 77, 82, 76, 52, 106, 55, 99, 107, 65, 121, 54, 108, 97, 113, 113, 47, 120, 120, 86, 81, 98, 119, 117, 70, 90, 68, 118, 106, 117, 104, 55, 116, 121, 103, 118, 89, 100, 47, 105, 108, 49, 90, 103, 112, 87, 121, 73, 53, 114, 80, 117, 115, 122, 68, 115, 71, 116, 67, 119, 68, 87, 10, 111, 71, 83, 77, 88, 90, 66, 47, 101, 114, 48, 77, 87, 102, 105, 67, 119, 120, 120, 49, 107, 47, 108, 71, 81, 48, 106, 55, 98, 83, 105, 78, 65, 103, 77, 66, 65, 65, 71, 106, 101, 122, 66, 53, 77, 65, 107, 71, 65, 49, 85, 100, 69, 119, 81, 67, 77, 65, 65, 119, 76, 65, 89, 74, 10, 89, 73, 90, 73, 65, 89, 98, 52, 81, 103, 69, 78, 66, 66, 56, 87, 72, 85, 57, 119, 90, 87, 53, 84, 85, 48, 119, 103, 82, 50, 86, 117, 90, 88, 74, 104, 100, 71, 86, 107, 73, 69, 78, 108, 99, 110, 82, 112, 90, 109, 108, 106, 89, 88, 82, 108, 77, 66, 48, 71, 65, 49, 85, 100, 10, 68, 103, 81, 87, 66, 66, 81, 87, 66, 43, 115, 100, 88, 88, 56, 54, 115, 56, 71, 55, 47, 121, 43, 90, 47, 84, 90, 102, 57, 112, 56, 109, 97, 122, 65, 102, 66, 103, 78, 86, 72, 83, 77, 69, 71, 68, 65, 87, 103, 66, 82, 111, 53, 51, 111, 113, 68, 48, 84, 89, 71, 115, 74, 99, 10, 103, 49, 71, 101, 73, 111, 114, 78, 84, 56, 107, 74, 119, 122, 65, 78, 66, 103, 107, 113, 104, 107, 105, 71, 57, 119, 48, 66, 65, 81, 85, 70, 65, 65, 79, 67, 65, 103, 69, 65, 76, 98, 104, 47, 52, 119, 72, 102, 50, 85, 107, 75, 73, 116, 83, 53, 68, 71, 110, 54, 48, 75, 52, 54, 10, 71, 73, 87, 70, 48, 43, 77, 114, 43, 57, 68, 76, 118, 97, 56, 90, 82, 54, 106, 97, 72, 54, 52, 69, 121, 76, 110, 78, 109, 112, 113, 55, 86, 110, 87, 77, 106, 122, 75, 120, 90, 66, 114, 104, 74, 122, 68, 86, 121, 82, 97, 88, 83, 71, 118, 86, 113, 111, 121, 75, 66, 119, 101, 90, 10, 113, 43, 71, 56, 74, 118, 76, 117, 112, 65, 48, 118, 77, 49, 71, 87, 54, 101, 107, 102, 119, 116, 106, 122, 97, 69, 43, 53, 106, 72, 69, 56, 47, 118, 65, 76, 70, 108, 114, 72, 105, 118, 89, 78, 97, 82, 75, 77, 120, 83, 84, 99, 67, 88, 69, 112, 79, 117, 88, 120, 118, 73, 50, 52, 10, 88, 56, 97, 68, 102, 75, 54, 112, 88, 115, 74, 49, 116, 87, 53, 85, 83, 121, 53, 86, 74, 56, 108, 50, 66, 90, 106, 90, 50, 50, 90, 109, 97, 104, 108, 97, 122, 72, 120, 51, 85, 107, 48, 85, 86, 67, 79, 70, 122, 102, 107, 103, 112, 117, 100, 121, 117, 70, 70, 71, 82, 104, 112, 81, 10, 99, 55, 107, 106, 52, 67, 98, 116, 43, 121, 57, 51, 113, 98, 84, 88, 106, 55, 67, 119, 47, 99, 112, 106, 43, 71, 115, 105, 103, 104, 86, 43, 69, 112, 120, 87, 57, 74, 104, 86, 113, 82, 56, 72, 89, 51, 67, 74, 86, 117, 52, 67, 114, 122, 103, 102, 121, 73, 82, 47, 68, 121, 90, 82, 10, 107, 67, 55, 54, 76, 121, 77, 70, 102, 76, 72, 110, 107, 114, 69, 71, 117, 51, 55, 101, 83, 85, 83, 56, 101, 119, 68, 81, 68, 54, 88, 73, 88, 57, 118, 54, 100, 107, 82, 88, 77, 84, 75, 98, 51, 57, 56, 87, 65, 86, 103, 67, 73, 79, 113, 115, 116, 70, 100, 116, 114, 122, 74, 53, 10, 71, 66, 76, 113, 89, 48, 80, 120, 53, 82, 101, 104, 86, 114, 84, 115, 73, 102, 115, 88, 105, 69, 106, 72, 57, 85, 76, 121, 98, 51, 73, 115, 76, 105, 116, 49, 105, 83, 51, 84, 65, 68, 71, 116, 72, 104, 78, 75, 65, 53, 113, 57, 108, 56, 71, 100, 98, 67, 121, 49, 70, 117, 114, 84, 10, 72, 75, 73, 98, 116, 72, 50, 56, 49, 50, 68, 111, 109, 54, 86, 76, 111, 119, 84, 109, 116, 84, 72, 122, 57, 80, 121, 105, 111, 77, 82, 57, 56, 57, 69, 81, 70, 88, 122, 73, 80, 72, 54, 115, 108, 116, 106, 97, 104, 98, 108, 49, 47, 43, 50, 106, 89, 71, 101, 89, 53, 66, 81, 122, 10, 116, 54, 98, 87, 72, 89, 103, 111, 49, 54, 81, 80, 109, 53, 88, 72, 74, 73, 65, 82, 73, 50, 109, 51, 119, 70, 65, 51, 57, 113, 106, 76, 110, 117, 50, 77, 70, 88, 54, 49, 71, 73, 121, 52, 97, 53, 49, 104, 100, 72, 115, 99, 53, 72, 114, 97, 97, 84, 81, 55, 116, 48, 97, 56, 10, 51, 107, 48, 117, 53, 52, 88, 101, 82, 84, 98, 74, 111, 77, 108, 103, 86, 120, 80, 68, 104, 67, 114, 121, 53, 47, 113, 71, 97, 90, 48, 104, 55, 99, 122, 69, 48, 119, 79, 110, 114, 75, 77, 97, 55, 54, 57, 98, 71, 74, 75, 74, 81, 69, 120, 72, 106, 106, 113, 106, 106, 73, 67, 53, 10, 114, 83, 111, 98, 56, 109, 55, 84, 90, 50, 104, 77, 85, 81, 113, 71, 107, 57, 87, 65, 69, 78, 78, 71, 102, 109, 119, 98, 50, 67, 73, 55, 88, 102, 76, 53, 113, 53, 80, 104, 102, 85, 51, 107, 88, 43, 102, 111, 69, 104, 65, 77, 108, 122, 108, 111, 70, 71, 73, 116, 67, 50, 84, 57, 10, 80, 103, 83, 82, 120, 115, 109, 50, 99, 53, 56, 78, 116, 105, 57, 53, 68, 80, 119, 61, 10, 45, 45, 45, 45, 45, 69, 78, 68, 32, 67, 69, 82, 84, 73, 70, 73, 67, 65, 84, 69, 45, 45, 45, 45, 45, 10}));
            }
        }

        public WegoCheck readCheck(InputStream in) throws CMSException, IOException, NoSuchAlgorithmException, InvalidKeySpecException, CertificateException, NoSuchProviderException, ParserConfigurationException, SAXException {
            return readCheck(IOUtils.toByteArray(in));
        }

        public WegoCheck readCheck(byte[] data) throws CMSException, IOException, NoSuchAlgorithmException, InvalidKeySpecException, CertificateException, NoSuchProviderException, ParserConfigurationException, SAXException {
            loadCertificates();
            CMSSignedData signedData = new CMSSignedData(data);
            SignerInformationStore signerInfos = signedData.getSignerInfos();
            SignerInformation signerInformation = (SignerInformation) signerInfos.getSigners().iterator().next();
            Security.addProvider(new BouncyCastleProvider());
            Preconditions.checkArgument(signerInformation.verify((X509Certificate) certificate, "BC"), "signature verification failed");
            SignerId sid = signerInformation.getSID();
            CMSProcessable signedContent = signedData.getSignedContent();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            signedContent.write(out);
            final String xml = out.toString();
            final Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(out.toByteArray()));
            final XPath xPath = XPathFactory.newInstance().newXPath();
            return new WegoCheck() {

                @Override
                public String getProductName() {
                    try {
                        return xPath.evaluate("/" + "l" + "i" + "c" + "e" + "n" + "c" + "e" + "/product/name", document);
                    } catch (XPathExpressionException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                @Override
                public String getCustomerName() {
                    try {
                        return xPath.evaluate("/" + "l" + "i" + "c" + "e" + "n" + "c" + "e" + "/customer/name", document);
                    } catch (XPathExpressionException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                @Override
                public Date getGenerationDate() {
                    try {
                        return sdf.parse(xPath.evaluate("/" + "l" + "i" + "c" + "e" + "n" + "c" + "e" + "/generationDate", document));
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    } catch (XPathExpressionException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                @Override
                public String getModuleName() {
                    try {
                        return xPath.evaluate("/" + "l" + "i" + "c" + "e" + "n" + "c" + "e" + "/modules/module/name", document);
                    } catch (XPathExpressionException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                @Override
                public Date getExprirationDate() {
                    try {
                        return sdf.parse(xPath.evaluate("/" + "l" + "i" + "c" + "e" + "n" + "c" + "e" + "/modules/module/expirationDate", document));
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    } catch (XPathExpressionException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                @Override
                public String getCheckXml() {
                    return xml;
                }

                @Override
                public void validateCheck(String productName, String moduleName) {
                    Preconditions.checkArgument(Objects.equal(getProductName(), productName), "product name mismatch: this check is for product = %s", getProductName());
                    Preconditions.checkArgument(Objects.equal(getModuleName(), moduleName), "module name mismatch: this check is for product = %s", getModuleName());
                    Preconditions.checkArgument(new Date().compareTo(getExprirationDate()) <= 0, "expired check: this check has expired on date = %s", getExprirationDate());
                }
            };
        }

    }

    public interface WegoCheck {

        public String getProductName();

        public String getCustomerName();

        public Date getGenerationDate();

        public String getModuleName();

        public Date getExprirationDate();

        public void validateCheck(String productName, String moduleName);

        public String getCheckXml();
    }

}
