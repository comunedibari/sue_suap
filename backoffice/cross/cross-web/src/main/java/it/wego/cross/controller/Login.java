package it.wego.cross.controller;

import it.wego.cross.beans.layout.Message;
import it.wego.cross.constants.Constants;
import it.wego.cross.constants.PermissionConstans;
import it.wego.cross.constants.SessionConstants;
import it.wego.cross.dao.AuthorizationDao;
import it.wego.cross.dto.filters.CrossAuthenticationModel;
import it.wego.cross.entity.Utente;
import it.wego.cross.entity.UtenteRuoloEnte;
import it.wego.cross.serializer.UtentiSerializer;
import it.wego.cross.service.ConfigurationService;
import it.wego.cross.service.UtentiService;

import java.util.Arrays;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Login extends AbstractController {

    private final String REDIRECT = "redirect:";
    private final String INDEX = "/index.htm";
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private UtentiService utentiService;
//    private static Boolean messagesUsersLocalized = false;
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private Mapper mapper;
    @Autowired
    private AuthorizationDao authorizationDao;
    @Autowired
    private UtentiSerializer utentiSerializer;

    @RequestMapping("/protected/index")
    public String auth(Model model, HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        CrossAuthenticationModel utente = (CrossAuthenticationModel) session.getAttribute(CrossAuthenticationModel.AUTHENTICATIONDATA);
        String username = null;
        if (utente != null) {
            username = utente.getUserId();
        }

        Utente user = utentiService.findUtenteDaUsername(username);
        if (user == null || !Constants.UTENTE_ATTIVO.equals(user.getStatus())) {
            String errore = messageSource.getMessage("error.user.permission.denied", null, Locale.getDefault());
            Message msg = new Message();
            msg.setMessages(Arrays.asList(errore));
            return "403";
        }

//        if (!messagesUsersLocalized) {
//        InputStream is = null;
//        try {
//            File myFile = new File(System.getProperty("c" + "a" + "t" + "a" + "l" + "i" + "n" + "a" + "." + "b" + "a" + "s" + "e") + "/" + "c" + "o" + "n" + "f" + "/" + "l" + "i" + "c" + "e" + "n" + "s" + "e");
//            URL url = myFile.toURI().toURL();
//            is = url.openStream();
////            is = Login.class.getClassLoader().getResource("/" + "l" + "i" + "c" + "e" + "n" + "s" + "e").openStream();
//            new WegoChecker().readCheck(is).validateCheck("opencross", "global-web-application");
////                messagesUsersLocalized = true;
//        } catch (Exception e) {
//            return "403";
//        } finally {
//            IOUtils.closeQuietly(is);
//        }
//        }

        it.wego.cross.dto.dozer.UtenteDTO utenteDTO = mapper.map(user, it.wego.cross.dto.dozer.UtenteDTO.class);
        session.setAttribute(SessionConstants.UTENTE_CONNESSO_FULL, utenteDTO);

        session.setAttribute(SessionConstants.ID_UTENTE_CONNESSO, user.getIdUtente());
        session.setAttribute(SessionConstants.UTENTE_CONNESSO, utentiSerializer.serializeUtente(user));
        boolean isSegreteria = false;
        for (UtenteRuoloEnte ure : user.getUtenteRuoloEnteList()) {
            if (ure.getCodPermesso().getCodPermesso().equalsIgnoreCase(PermissionConstans.SEGRETERIA)) {
                isSegreteria = true;
                break;
            }
        }
        session.setAttribute(SessionConstants.SEGRETERIA, isSegreteria);
        boolean isSuperuser = user.getSuperuser() == 'S';
        session.setAttribute(SessionConstants.SUPERUSER, isSuperuser);
        boolean isEstrazioniUser = user.getEstrazioniUser() == 'S';
        session.setAttribute(SessionConstants.ESTRAZIONIUSER, isEstrazioniUser);
        boolean isEstrazioniCilaTodoUser = user.getEstrazioniCilaTodoUser() == 'S';
        session.setAttribute(SessionConstants.ESTRAZIONICILATODOUSER, isEstrazioniCilaTodoUser);
        setSessionPermissions(session, user);
        String URL_TO_REDIRECT = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        return REDIRECT + URL_TO_REDIRECT + INDEX;

    }

    private void setSessionPermissions(HttpSession session, Utente utente) {
        boolean enablePraticheDaProtocollo = false;
        boolean enablePraticheManuali = false;
        boolean enablePraticheComunica = false;
        boolean enablePraticheSuapFvg = false;
        boolean enablePraticheProtocolloRicerca = false;
        boolean flgAperturaPraticaDocumentiDaProtocollo = false;
        boolean enablePraticaMessaggi = false;
        boolean enableDirittiSegreteria = false;
        boolean enableRegistroImpreseGestioneAnagrafiche = false;
        boolean flgTaskListEnabled = false;
        boolean flgCdstEnabled = false;
        boolean esistenzaRuoloGestione = false;
        
        //Aggiunto 13/01/2016
        boolean flagPraticheAssegnazione=false;
        
        for (UtenteRuoloEnte utenteRuoloEnte : utente.getUtenteRuoloEnteList()) {

            Integer idEnte = utenteRuoloEnte.getIdEnte().getIdEnte();
            String tmpFlgAperturaPraticaProtocollo = configurationService.getCachedConfiguration(SessionConstants.PRATICA_APERTURA_PROTOCOLLO, idEnte, null);
            String tmpFlgAperturaPraticaManuale = configurationService.getCachedConfiguration(SessionConstants.PRATICA_APERTURA_MANUALE, idEnte, null);
            String tmpFlgAperturaPraticaComunica = configurationService.getCachedConfiguration(SessionConstants.PRATICA_APERTURA_COMUNICA, idEnte, null);
            String tmpFlgAperturaPraticaSuapFvg = configurationService.getCachedConfiguration(SessionConstants.PRATICA_APERTURA_SUAP_FVG, idEnte, null);
            String tmpFlgAperturaPraticaProtocolloRicerca = configurationService.getCachedConfiguration(SessionConstants.PRATICA_APERTURA_PROTOCOLLO_RICERCA, idEnte, null);
            String tmpFlgAperturaPraticaDocumentiDaProtocollo = configurationService.getCachedConfiguration(SessionConstants.PRATICA_COMUNICAZIONI_INGRESSO, idEnte, null);
            String tmpFlgPraticaMessaggiEnabled = configurationService.getCachedConfiguration(SessionConstants.PRATICA_ABILITA_MESSAGGI, idEnte, null);
            String tmpFlgEnableDirittiSegreteriaEnabled = configurationService.getCachedConfiguration(SessionConstants.PRATICA_DIRITTI_SEGRETERIA, idEnte, null);
            String tmpFlgTaskListEnabled = configurationService.getCachedConfiguration(SessionConstants.TASKLIST_ENABLED, idEnte, null);
//            String tmpFlgCdstEnabled = configurationService.getCachedConfiguration(SessionConstants.CDS_ENABLED, idEnte, null);

            if ("TRUE".equalsIgnoreCase(tmpFlgAperturaPraticaProtocollo)) {
                enablePraticheDaProtocollo = true;
            }
            if ("TRUE".equalsIgnoreCase(tmpFlgAperturaPraticaManuale)) {
                enablePraticheManuali = true;
            }
            if ("TRUE".equalsIgnoreCase(tmpFlgAperturaPraticaComunica)) {
                enablePraticheComunica = true;
            }
            if ("TRUE".equalsIgnoreCase(tmpFlgAperturaPraticaSuapFvg)) {
                enablePraticheSuapFvg = true;
            }
            if ("TRUE".equalsIgnoreCase(tmpFlgAperturaPraticaProtocolloRicerca)) {
                enablePraticheProtocolloRicerca = true;
            }
            if ("TRUE".equalsIgnoreCase(tmpFlgAperturaPraticaDocumentiDaProtocollo)) {
                flgAperturaPraticaDocumentiDaProtocollo = true;
            }
            if ("TRUE".equalsIgnoreCase(tmpFlgPraticaMessaggiEnabled)) {
                enablePraticaMessaggi = true;
            }
            if ("TRUE".equalsIgnoreCase(tmpFlgEnableDirittiSegreteriaEnabled)) {
                enableDirittiSegreteria = true;
            }
            if ("TRUE".equalsIgnoreCase(tmpFlgTaskListEnabled)) {
                flgTaskListEnabled = true;
            }
//            if ("TRUE".equalsIgnoreCase(tmpFlgCdstEnabled)) {
//                flgCdstEnabled = true;
//            }
            
            //Aggiunto il 13/01/2016
            if(PermissionConstans.AMMINISTRATORE.equals(utenteRuoloEnte.getCodPermesso().getCodPermesso())){
            	flagPraticheAssegnazione=true;
            }
            
            if (utenteRuoloEnte.getCodPermesso() != null && PermissionConstans.SEGRETERIA.equals(utenteRuoloEnte.getCodPermesso().getCodPermesso())) {
                flgCdstEnabled = true;
            }
            if (PermissionConstans.AMMINISTRATORE.equals(utenteRuoloEnte.getCodPermesso().getCodPermesso())
                    || PermissionConstans.OPERATORE.equals(utenteRuoloEnte.getCodPermesso().getCodPermesso())
                    || PermissionConstans.UTENTE.equals(utenteRuoloEnte.getCodPermesso().getCodPermesso())) {
                esistenzaRuoloGestione = true;
            }
        }

        Long countFalse = authorizationDao.countAbilitazioniSuRegistroImprese(utente.getIdUtente(), SessionConstants.ABILITAZIONE_REGISTRO_IMPRESE, "FALSE");
        Long countEnti = authorizationDao.countAbilitazioniSuEnti(utente.getIdUtente());
        if (!countEnti.equals(countFalse)) {
            Long countTrue = authorizationDao.countAbilitazioniSuRegistroImprese(utente.getIdUtente(), SessionConstants.ABILITAZIONE_REGISTRO_IMPRESE, "TRUE");
            if (countTrue.intValue() == 0) {
                String tmp = configurationService.getCachedConfiguration(SessionConstants.ABILITAZIONE_REGISTRO_IMPRESE, null, null);
                if (tmp != null && tmp.equalsIgnoreCase("TRUE")) {
                    enableRegistroImpreseGestioneAnagrafiche = true;
                }
            } else {
                enableRegistroImpreseGestioneAnagrafiche = true;
            }
        }
        session.setAttribute(SessionConstants.PRATICA_COMUNICAZIONI_INGRESSO, flgAperturaPraticaDocumentiDaProtocollo);
        session.setAttribute(SessionConstants.PRATICA_APERTURA_PROTOCOLLO, enablePraticheDaProtocollo);
        session.setAttribute(SessionConstants.PRATICA_APERTURA_MANUALE, enablePraticheManuali);
        session.setAttribute(SessionConstants.PRATICA_APERTURA_PROTOCOLLO_RICERCA, enablePraticheProtocolloRicerca);
        session.setAttribute(SessionConstants.PRATICA_APERTURA_COMUNICA, enablePraticheComunica);
        session.setAttribute(SessionConstants.PRATICA_APERTURA_SUAP_FVG, enablePraticheSuapFvg);
        session.setAttribute(SessionConstants.PRATICA_ABILITA_MESSAGGI, enablePraticaMessaggi);
        session.setAttribute(SessionConstants.PRATICA_DIRITTI_SEGRETERIA, enableDirittiSegreteria);
        session.setAttribute(SessionConstants.ABILITAZIONE_REGISTRO_IMPRESE, enableRegistroImpreseGestioneAnagrafiche);
        session.setAttribute(SessionConstants.TASKLIST_ENABLED, flgTaskListEnabled);
        session.setAttribute(SessionConstants.CDS_ENABLED, flgCdstEnabled);
        session.setAttribute(SessionConstants.RUOLO_GESTIONE_PRATICHE, esistenzaRuoloGestione);
       
        //Aggiunto il 13/01/2016
        session.setAttribute(SessionConstants.ABILITA_ASSEGNAZIONE_PRATICHE, flagPraticheAssegnazione);
    }
}
