/**
 * Copyright (c) 2011, Regione Emilia-Romagna, Italy
 *  
 * Licensed under the EUPL, Version 1.1 or - as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the
 * Licence.
 * 
 * For convenience a plain text copy of the English version
 * of the Licence can be found in the file LICENCE.txt in
 * the top-level directory of this software distribution.
 * 
 * You may obtain a copy of the Licence in any of 22 European
 * Languages at:
 * 
 * http://joinup.ec.europa.eu/software/page/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * 
 * This product includes software developed by Yale University
 * 
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
**/
package it.people.console.web.controllers;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Collection;
import java.util.Vector;

import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import it.people.console.beans.Root;
import it.people.console.domain.RootLogin;
import it.people.console.security.ICryptoUtils;
import it.people.console.security.auth.SIRACLoginAuthenticationToken;
import it.people.console.security.exceptions.CryptoUtilsException;
import it.people.console.security.ru.IRUManager;
import it.people.console.utils.Constants;
import it.people.console.web.controllers.exceptions.RootObjectException;
import it.people.console.web.controllers.validator.RootLoginValidator;
import it.people.console.web.servlet.mvc.MessageSourceAwareController;
import it.people.core.PplUserData;
import it.people.sirac.core.SiracConstants;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 24/giu/2011 18.17.32
 *
 */
@Controller
@RequestMapping("/RootLogin")
@SessionAttributes("RootLogin")
public class RootLoginController extends MessageSourceAwareController {

	boolean needUpdate = false;
	
	private RootLoginValidator validator;
	
	@Autowired
	private ICryptoUtils cryptoUtils;

	@Autowired
	private SecurityContextRepository httpSessionSecurityContextRepository;
	
	@Autowired
	private IRUManager ruManager;
	
	@Autowired
	public RootLoginController(RootLoginValidator validator) {
		this.validator = validator;
		this.setCommandObjectName("rootLogin");
	}
	
    /**
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/login.rdo", method = RequestMethod.GET)
    public String setupForm(ModelMap model, HttpServletRequest request) {

    	RootLogin rootLogin = new RootLogin();
    	
    	model.put("sidebar", "/WEB-INF/jsp/mainSidebar.jsp");
    	
    	model.put("includeTopbarLinks", false);
    	
    	try {
			if (isDefaultRoot()) {
				rootLogin.setDefaultRoot(true);
			} 
    	} catch (RootObjectException e) {
    		return "redirect:errore.do";
    	}

    	model.put("rootLogin", rootLogin);
    	
    	return getStaticProperty("root.login.view");
    	
    }

    /**
     * @param model
     * @param rootLogin
     * @param result
     * @param request
     * @return
     */
    @RequestMapping(value = "/login.rdo", method = RequestMethod.POST)
    public String doLogin(ModelMap model, @ModelAttribute("rootLogin") RootLogin rootLogin, 
    		BindingResult result, HttpServletRequest request) {
    	
    	validator.validate(rootLogin, result);
    	    	
    	if (result.hasErrors()) {
    		try {
				if (isDefaultRoot()) {
					rootLogin.setDefaultRoot(true);
				} 
	    	} catch (RootObjectException e) {
	    		return "redirect:errore.do";
	    	}
	    	
	    	model.put("sidebar", "/WEB-INF/jsp/mainSidebar.jsp");
	    	
	    	model.put("includeTopbarLinks", false);
        	return getStaticProperty("root.login.view");
    	} else {
			try {
				updateRootLoginData(rootLogin);
			} catch (RootObjectException e) {
	    		return "redirect:errore.do";
			}
    		
    		if (rootLogin.isNeedUpdate()) {
    	    	model.put("sidebar", "/WEB-INF/jsp/mainSidebar.jsp");
//    	    	rootLogin.setNeedUpdate(false);
    	    	model.put("includeTopbarLinks", false);
            	return getStaticProperty("root.login.view");
    		} else {
    			try {
        			if (rootLogin.isNeedLogin()) {
        				updateRootData(rootLogin);
        		    	model.put("sidebar", "/WEB-INF/jsp/mainSidebar.jsp");
        		    	
        		    	model.put("includeTopbarLinks", false);
                    	return getStaticProperty("root.login.view");
        			} else {
    					setRootSession(request);
            			return "redirect:/paginaPrincipale.mdo";
        			}
    			} catch (RootObjectException e) {
		    		return "redirect:errore.do";
				}
    		}
    	}
    	
    }
    
    /**
     * @param rootLogin
     * @throws RootObjectException
     */
    private void updateRootLoginData(RootLogin rootLogin) throws RootObjectException {
    	
    	rootLogin.setNeedUpdate(false);
    	try {
			Root root = ruManager.getRootUser("zukowski");
			if (root != null && cryptoUtils.validRootPassword(root.getPassword(), rootLogin.getPassword())
				&& Constants.System.DEFAULT_ROOT_PASSWORD.toLowerCase()
					.equalsIgnoreCase(rootLogin.getPassword().trim().toLowerCase())) {
				rootLogin.setNeedUpdate(true);
				rootLogin.setNeedLogin(true);
			}
		} catch (StreamCorruptedException e) {
			throw new RootObjectException(e);
		} catch (InvalidKeyException e) {
			throw new RootObjectException(e);
		} catch (NoSuchAlgorithmException e) {
			throw new RootObjectException(e);
		} catch (InvalidKeySpecException e) {
			throw new RootObjectException(e);
		} catch (NoSuchPaddingException e) {
			throw new RootObjectException(e);
		} catch (CryptoUtilsException e) {
			throw new RootObjectException(e);
		} catch (IOException e) {
			throw new RootObjectException(e);
		} catch (ClassNotFoundException e) {
			throw new RootObjectException(e);
		}
    	
    }
    
    /**
     * @param rootLogin
     * @throws RootObjectException
     */
    private void updateRootData(RootLogin rootLogin) throws RootObjectException {
    	
    	try {
			Root root = new Root(rootLogin.getFirstName(), rootLogin.getLastName(), "root user", 
					rootLogin.geteMail(), cryptoUtils.getNewCryptedPassword(rootLogin.getPassword()));
			ruManager.writeRootUser("zukowski", root);
			//rootLogin.setNeedLogin(false);
		} catch (StreamCorruptedException e) {
			throw new RootObjectException(e);
		} catch (InvalidKeyException e) {
			throw new RootObjectException(e);
		} catch (NoSuchAlgorithmException e) {
			throw new RootObjectException(e);
		} catch (InvalidKeySpecException e) {
			throw new RootObjectException(e);
		} catch (NoSuchPaddingException e) {
			throw new RootObjectException(e);
		} catch (CryptoUtilsException e) {
			throw new RootObjectException(e);
		} catch (IOException e) {
			throw new RootObjectException(e);
		} catch (IllegalBlockSizeException e) {
			throw new RootObjectException(e);
		}
    	
    }
    
    /**
     * @param rootLogin
     * @param request
     * @throws RootObjectException 
     */
    private void setRootSession(HttpServletRequest request) throws RootObjectException {

		Root root = null;
		try {
			root = ruManager.getRootUser("zukowski");
		} catch (StreamCorruptedException e) {
			throw new RootObjectException(e);
		} catch (InvalidKeyException e) {
			throw new RootObjectException(e);
		} catch (NoSuchAlgorithmException e) {
			throw new RootObjectException(e);
		} catch (InvalidKeySpecException e) {
			throw new RootObjectException(e);
		} catch (NoSuchPaddingException e) {
			throw new RootObjectException(e);
		} catch (CryptoUtilsException e) {
			throw new RootObjectException(e);
		} catch (IOException e) {
			throw new RootObjectException(e);
		} catch (ClassNotFoundException e) {
			throw new RootObjectException(e);
		}
    	
    	HttpSession session = request.getSession();
    	
    	PplUserData pplUserData = new PplUserData();
    	pplUserData.setNome(root.getFirstName());
    	pplUserData.setCognome(root.getLastName());
    	pplUserData.setCodiceFiscale(Constants.Security.ROOT_TAX_CODE);
    	pplUserData.setEmailaddress(root.geteMail());

		session.setAttribute(SiracConstants.SIRAC_AUTHENTICATED_USERDATA, pplUserData);
		session.setAttribute(SiracConstants.SIRAC_AUTHENTICATED_USER, Constants.Security.ROOT_USER_LOGIN_IDP);

		Collection<GrantedAuthority> rootAuthorities = new Vector<GrantedAuthority>();
		
		rootAuthorities.add(new GrantedAuthorityImpl(Constants.Security.ROOT_ROLE));
		rootAuthorities.add(new GrantedAuthorityImpl(Constants.Security.CONSOLE_ADMIN_ROLE));
		
		SIRACLoginAuthenticationToken authenticationToken = 
			new SIRACLoginAuthenticationToken(Constants.Security.ROOT_USER_LOGIN_IDP, 
					pplUserData.getCodiceFiscale(), rootAuthorities);
		authenticationToken.setDetails(pplUserData);
		
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        
        SecurityContextImpl securityContext = new SecurityContextImpl();
        securityContext.setAuthentication(authenticationToken);
        
        if (logger.isDebugEnabled()) {
        	logger.debug("Thread ID = " + Thread.currentThread().getId());
        	logger.debug("Session ID = " + request.getSession().getId());
        }
    	
    }
    
    /**
     * @return isDefaultRoot
     * @throws RootObjectException
     */
    private boolean isDefaultRoot() throws RootObjectException {
    	boolean isDefaultRoot = false;
		try {
			Root root = ruManager.getRootUser("zukowski");
			if (root != null && cryptoUtils.validRootPassword(root.getPassword(), Constants.System.DEFAULT_ROOT_PASSWORD.toLowerCase())) {
				isDefaultRoot = true;
			}
		} catch (StreamCorruptedException e) {
			throw new RootObjectException(e);
		} catch (InvalidKeyException e) {
			throw new RootObjectException(e);
		} catch (NoSuchAlgorithmException e) {
			throw new RootObjectException(e);
		} catch (InvalidKeySpecException e) {
			throw new RootObjectException(e);
		} catch (NoSuchPaddingException e) {
			throw new RootObjectException(e);
		} catch (CryptoUtilsException e) {
			throw new RootObjectException(e);
		} catch (IOException e) {
			throw new RootObjectException(e);
		} catch (ClassNotFoundException e) {
			throw new RootObjectException(e);
		}
    	
		return isDefaultRoot;
    }
    
}
