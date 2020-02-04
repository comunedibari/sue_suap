/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.cxf.interceptor.security.AuthenticationException;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.gson.Gson;

import it.wego.cross.conferenzaservizi.xsd.Seduta.Ente;
import it.wego.cross.constants.PermissionConstans;
import it.wego.cross.constants.SessionConstants;
import it.wego.cross.dao.AuthorizationDao;
import it.wego.cross.dao.PermessiDao;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dao.UtentiDao;
import it.wego.cross.dto.PermessiEnteProcedimentoDTO;
import it.wego.cross.dto.UtenteDTO;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.Permessi;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.ProcedimentiEnti;
import it.wego.cross.entity.Utente;
import it.wego.cross.entity.UtenteRuoloEnte;
import it.wego.cross.entity.view.ProcedimentiLocalizzatiView;
import it.wego.cross.serializer.UtentiSerializer;
import it.wego.cross.utils.Log;
import it.wego.utils.wegoforms.FormEngine;

/**
 *
 * @author CS
 */
@Service
public class UtentiServiceImpl implements UtentiService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UtentiDao utentiDao;
    @Autowired
    private PraticaDao praticaDao;
    @Autowired
    private PermessiDao permessiDao;
    @Autowired
    private AuthorizationDao authorizationDao;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private EntiService entiService;
    @Autowired
    private Mapper mapper;
    @Autowired
    private UtentiSerializer utentiSerializer;
    @Autowired
    private ConfigurationService configurationService;
   

    @Override
    public Long countDaAssegnarePratica(Integer idPratica, Filter filter) {
        Pratica pratica = praticaDao.findPratica(idPratica);
        Utente connectedUser = filter.getConnectedUser();
        if (isAdministrator(pratica, connectedUser)) {
            return utentiDao.countUtentiAbilitati(pratica);
        } else {
            return 1L;
        }
    }

    private boolean isAdministrator(Pratica pratica, Utente utente) {
        boolean isAdmin = praticaDao.isAdminSuPratica(pratica, utente);
        return isAdmin;
    }

    @Override
    public List<UtenteDTO> findDaAssegnarePratica(Integer idPratica, Filter filter) {
        Pratica pratica = praticaDao.findPratica(idPratica);
        Utente connectedUser = filter.getConnectedUser();
        List<UtenteDTO> utentiDto = new ArrayList<UtenteDTO>();
        if (isAdministrator(pratica, connectedUser)) {
            String orderColumn = filter.getOrderColumn();
            if (filter.getOrderColumn().equals("nominativo")) {
                orderColumn = "cognome";
            }
            filter.setOrderColumn(orderColumn);
            List<Utente> utenti = utentiDao.findUtentiAbilitati(pratica, filter);
            for (Utente utente : utenti) {
                UtenteDTO utenteDto = UtentiSerializer.serializeUtenteSenzaRuoli(utente);
                utentiDto.add(utenteDto);
            }
        } else {
            UtenteDTO user = UtentiSerializer.serializeUtenteSenzaRuoli(connectedUser);
            utentiDto.add(user);
        }
        return utentiDto;
    }

    @Override
    public List<UtenteDTO> findAdminSuPratica(Integer idPratica, Filter filter) {
        Pratica pratica = praticaDao.findPratica(idPratica);
        List<UtenteDTO> utentiDto = new ArrayList<UtenteDTO>();
        List<Utente> utenti = utentiDao.findUtentiAbilitatiAdmin(pratica, filter);
        for (Utente utente : utenti) {
            UtenteDTO utenteDto = UtentiSerializer.serializeUtenteSenzaRuoli(utente);
            utentiDto.add(utenteDto);
        }
        return utentiDto;
    }

    @Override
    public Utente findUtenteDaUsername(String username) {
        Utente user = utentiDao.findUtenteByUsername(username);
        return user;
    }

    @Override
    public Utente findUtenteByIdUtente(Integer idUtente) {
        if (idUtente == null) {
            return null;
        } else {
            Utente user = utentiDao.findUtenteByIdUtente(idUtente);
            return user;
        }
    }

    @Override
    public List<Utente> findAll(Filter filter) {
        List<Utente> utenti = utentiDao.findAll(filter);
        return utenti;
    }

    @Override
    public Long countAll(Filter filter) {
        Long count = utentiDao.countAll(filter);
        return count;
    }

    @Override
    public List<Permessi> findAllPermessi() {
        List<Permessi> permessi = permessiDao.findAll();
        return permessi;
    }

    @Override
    public Permessi findByCodPermesso(String codPermesso) {
        Permessi permesso = permessiDao.findByCodPermesso(codPermesso);
        return permesso;
    }

    @Override
    public UtenteRuoloEnte getRuoloByKey(Integer idUtente, Integer idEnte, String codPermesso) {
        UtenteRuoloEnte utenteRuoloEnte = authorizationDao.getRuoloByKey(idUtente, idEnte, codPermesso);
        return utenteRuoloEnte;
    }

    @Override
    public PermessiEnteProcedimentoDTO getPermesso(ProcedimentiLocalizzatiView procedimento, int codEnte) {
        return permessiDao.getPermesso(procedimento, codEnte);
    }

    @Override
    public Utente getUtenteConnesso(HttpServletRequest request) {
        UtenteDTO authUser = getUtenteConnessoDTO(request);
        Utente utente=null;
        if (authUser != null) {
            utente = utentiDao.findUtenteByIdUtente(authUser.getIdUtente());
            //return utente;
        } else {               
             	Principal principal = request.getUserPrincipal();               
                utente = utentiDao.findUtenteByUsername((principal.getName())); 
                it.wego.cross.dto.dozer.UtenteDTO utenteDTO = mapper.map(utente, it.wego.cross.dto.dozer.UtenteDTO.class);
                
                HttpSession session = request.getSession();
                session.setAttribute(SessionConstants.UTENTE_CONNESSO_FULL, utenteDTO);

                session.setAttribute(SessionConstants.ID_UTENTE_CONNESSO, utente.getIdUtente());
                session.setAttribute(SessionConstants.UTENTE_CONNESSO, utentiSerializer.serializeUtente(utente));
                boolean isSegreteria = false;
                for (UtenteRuoloEnte ure : utente.getUtenteRuoloEnteList()) {
                    if (ure.getCodPermesso().getCodPermesso().equalsIgnoreCase(PermissionConstans.SEGRETERIA)) {
                        isSegreteria = true;
                        break;
                    }
                }
                session.setAttribute(SessionConstants.SEGRETERIA, isSegreteria);
                boolean isSuperuser = utente.getSuperuser() == 'S';
                session.setAttribute(SessionConstants.SUPERUSER, isSuperuser);
                setSessionPermissions(session, utente);
            //return null;
        }
        return utente;
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

    @Override
    public Utente getUtente(Integer idUtente) {
        return utentiDao.findUtenteByIdUtente(idUtente);
    }

    @Override
    public UtenteDTO getUtenteConnessoDTO(HttpServletRequest request) {
        UtenteDTO authUser = (UtenteDTO) request.getSession().getAttribute(SessionConstants.UTENTE_CONNESSO);
        if (authUser != null) {
            return authUser;
        } else {
            String errore = messageSource.getMessage("error.user.permission.denied", null, Locale.getDefault());
            Log.APP.error(errore);
            return null;
        }
    }

    @Override
    public Boolean couldCreateNewEvent(Utente utente, Pratica pratica) {
        List<UtenteRuoloEnte> utenteRuoliEnte = authorizationDao.getUtenteRuoloEnte(utente.getIdUtente(), pratica.getIdProcEnte().getIdProc().getIdProc(), pratica.getIdProcEnte().getIdEnte().getIdEnte());
        boolean couldCreateEvent = false;
        if (utenteRuoliEnte != null && !utenteRuoliEnte.isEmpty()) {
            for (UtenteRuoloEnte u : utenteRuoliEnte) {
                if (u.getCodPermesso().getCodPermesso().equals(PermissionConstans.OPERATORE) || u.getCodPermesso().getCodPermesso().equals(PermissionConstans.AMMINISTRATORE)) {
                    couldCreateEvent = true;
                    break;
                }
            }
        }
        return couldCreateEvent;
    }

    @Override
    public boolean isAdminOnProcEnte(Utente utente, ProcedimentiEnti idProcEnte) throws Exception {
        List<UtenteRuoloEnte> utenteRuoliEnte = authorizationDao.getUtenteRuoloEnte(utente.getIdUtente(), idProcEnte.getIdProc().getIdProc(), idProcEnte.getIdEnte().getIdEnte());
        if (utenteRuoliEnte == null || utenteRuoliEnte.isEmpty()) {
            throw new Exception("L'utente non può operare sulla pratica");
        }
        boolean isAdmin = false;
        for (UtenteRuoloEnte u : utenteRuoliEnte) {
            if (u.getCodPermesso().getCodPermesso().equals(PermissionConstans.AMMINISTRATORE)) {
                isAdmin = true;
                break;
            }
        }
        return isAdmin;
    }

    @Override
    public List<Utente> findAllSystemSuperusers() {
        return utentiDao.findAllSystemSuperusers();
    }

    @Override
    public List<String> findAllSystemSuperusersUsername() {
        return utentiDao.findAllSystemSuperusersUsername();
    }

    @Override
    public String encodePasswordWithSsha(String cleartextPassword) {
        final String salt = UUID.randomUUID().toString();
        return "{SSHA}" + org.apache.commons.codec.binary.Base64.encodeBase64String(ArrayUtils.addAll(DigestUtils.sha(cleartextPassword + salt), salt.getBytes()));
    }

    @Override
    public String getAlberoEntiRuoli(Integer idUtente) {
        Gson gson = new Gson();
        List<Enti> enti = entiService.findAll(new Filter());
        List listaEnti = Lists.newArrayList();
        List<Permessi> permessi = permessiDao.findAll();
        List<UtenteRuoloEnte> listaRuoliDb = getRuoliEnti(idUtente);
        Map<String, Integer> mappaRuoli = new HashMap<String, Integer>();
        for (UtenteRuoloEnte ure : listaRuoliDb) {
            mappaRuoli.put(ure.getIdEnte().getIdEnte() + "#" + ure.getCodPermesso().getCodPermesso(), ure.getIdUtenteRuoloEnte());
        }
        for (Enti ente : enti) {
            List listaPermessi = Lists.newArrayList();
            for (Permessi p : permessi) {
                String key = ente.getIdEnte() + "#" + p.getCodPermesso();
                boolean esisteRuolo = mappaRuoli.containsKey(key);
                String classe = "";
                if (!esisteRuolo) {
                    classe = "hidden";
                }
                listaPermessi.add(ImmutableMap.of(
                        "key", p.getCodPermesso(),
                        "title", p.getDescrizione() + "&nbsp;<span class=\"" + classe + " attivaBottone" + ente.getIdEnte() + "#" + p.getCodPermesso() + "\"><input type=\"button\" onClick=\"gestisciProcedimentiPopUp('" + mappaRuoli.get(key) + "')\" value=\"Procedimenti\"></span>",
                        "select", esisteRuolo)); //procedimentiEntiId.contains(procedimento.getIdProcEnte())));
            }
            listaEnti.add(ImmutableMap.of(
                    "key", ente.getIdEnte().toString(),
                    "title", ente.getDescrizione(),
                    "children", listaPermessi,
                    "unselectable", true,
                    "hideCheckbox", listaPermessi.isEmpty()));
        }
        List listaRadiceAlbero = Lists.newArrayList(ImmutableMap.of(
                "title", "enti",
                "key", "enti",
                "isFolder", true,
                "expand", true,
                "children", listaEnti));
        return gson.toJson(listaRadiceAlbero);
    }

    @Override
    public List<UtenteRuoloEnte> getRuoliEnti(Integer idUtente) {
        return utentiDao.findRuoliEnti(idUtente);
    }

    @Override
    public void initRicercaProcedimentiRuoli(HttpServletRequest request, Model model, FormEngine formEngine, Map<String, Object> formParameters) {
        Filter filter = new Filter();
        formEngine.putInstanceDataValue("filtroRicerca", filter);
        formEngine.putInstanceDataValue("idUtenteRuoloEnte", request.getParameter("idUtenteRuoloEnte"));
    }
    
    private final static String SSHA_PREFIX = "{SSHA}", CLEARTEXT_PREFIX = "{CLEARTEXT}";
    private final static int SHA1_BYTE_SIZE = 20, MAX_CACHE_SIZE = 128;
    
    @Override
    public boolean testPsw(String password, String passwordFromDb) throws AuthenticationException {
        if (passwordFromDb.startsWith(SSHA_PREFIX)) {
            byte[] data = Base64.decodeBase64(passwordFromDb.substring(SSHA_PREFIX.length()).getBytes()),
                    shaData = Arrays.copyOfRange(data, 0, SHA1_BYTE_SIZE),
                    saltData = Arrays.copyOfRange(data, SHA1_BYTE_SIZE, data.length);
            byte[] shaNew = DigestUtils.sha(ArrayUtils.addAll(password.getBytes(), saltData));
            if (Arrays.equals(shaData, shaNew)) {
                return true;
            }
        } else if (passwordFromDb.startsWith(CLEARTEXT_PREFIX) || !passwordFromDb.matches("^[{].*[}].*")) {
            passwordFromDb = passwordFromDb.replaceFirst("^[{]CLEARTEXT[}]", "");
            if (passwordFromDb.equals(password)) {
                return true;
            }
        } else {
            logger.warn("unsupported password format from db = " + passwordFromDb.replaceFirst("^([{].*[}]).*", "$1"));
        }
        return false;
    }
    
    @Override
    public List<Utente> findUtentiByEnte(List<Enti> listaEnti) {
    	List<Utente> utentiPerEnte = new ArrayList<Utente>();
    	for (Enti ente : listaEnti) {
    		List<Utente> findUtentiByEnte = utentiDao.findUtentiByEnte(ente);
    		for (Utente utente : findUtentiByEnte) {
				if(!utentiPerEnte.contains(utente))
					utentiPerEnte.add(utente);
			}
		}
    	Collections.sort(utentiPerEnte, new Comparator<Utente>() {
    		@Override
    		public int compare(final Utente object1, final Utente object2) {
    		return object1.getCognome().trim().toUpperCase().compareTo(object2.getCognome().trim().toUpperCase());
    		}
    		});

    	return utentiPerEnte;
    }
}
