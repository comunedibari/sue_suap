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
package it.people.console.web.servlet.mvc;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.BitSet;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.xml.rpc.ServiceException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;

import it.people.console.dto.ProcessActionDataHolder;
import it.people.console.persistence.IPersistenceBroker;
import it.people.console.persistence.beans.support.EditableRowInputData;
import it.people.console.security.AbstractCommand;
import it.people.console.security.SecurityUtils;
import it.people.console.system.AbstractLogger;
import it.people.console.utils.Constants;
import it.people.console.web.client.exceptions.FeServiceReferenceException;
import it.people.core.PplUserData;
import it.people.feservice.FEInterface;
import it.people.feservice.client.FEInterfaceServiceLocator;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 01/dic/2010 18.39.01
 *
 */
public abstract class AbstractController extends AbstractLogger implements IController {

	private static final String PARAM_X_COORD_SUFFIX = ".x";

	private static final String PARAM_Y_COORD_SUFFIX = ".y";
	
	private String commandObjectName;
	
	/**
	 * @param request
	 * @param parameter
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected boolean isPrefixParamInRequest(HttpServletRequest request, String parameter) {
		boolean result = false;
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			if (paramName.startsWith(parameter)) {
				result = true;
				break;
			}
		}
		return result;
	}

	/**
	 * @param request
	 * @param parameter
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected boolean isParamInRequest(HttpServletRequest request, String parameter) {
		boolean result = false;
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			if (paramName.equalsIgnoreCase(parameter) 
					|| paramName.equalsIgnoreCase(parameter + PARAM_X_COORD_SUFFIX) 
					|| paramName.equalsIgnoreCase(parameter + PARAM_Y_COORD_SUFFIX)) {
				result = true;
				break;
			}
		}
		return result;
	}

	/**
	 * @param feReference
	 * @return
	 * @throws FeServiceReferenceException
	 */
	protected FEInterface getFEInterface(String feReference) throws FeServiceReferenceException {
		
		FEInterface result = null;
		
		FEInterfaceServiceLocator feInterfaceServiceLocator = new FEInterfaceServiceLocator();
		try {
			result = feInterfaceServiceLocator.getFEInterface(new URL(feReference));
		} catch (MalformedURLException e) {
        	throw new FeServiceReferenceException(e, "Malfomerd URI for reference '" + feReference + "'.");
		} catch (ServiceException e) {
        	throw new FeServiceReferenceException(e);
		}
		
		return result;
		
	}
	
	/**
	 * @param request
	 * @return
	 */
	protected String getReferer(HttpServletRequest request) {
		
		return request.getHeader("referer");
		
	}

	/**
	 * @param request
	 * @param e
	 */
	protected void putLastExceptioninSession(HttpServletRequest request, Exception e) {
		request.getSession().setAttribute(Constants.System.SESSION_LAST_EXCEPTION_KEY, e);
	}

	/**
	 * @param request
	 * @return
	 */
	protected Exception popLastExceptionfromSession(HttpServletRequest request) {
		Exception result = (Exception)request.getSession().getAttribute(Constants.System.SESSION_LAST_EXCEPTION_KEY);
		request.getSession().removeAttribute(Constants.System.SESSION_LAST_EXCEPTION_KEY);
		return result;
	}

	/**
	 * @param role
	 * @return
	 */
	protected boolean isUserInRole(String role) {
		
		return this.isUserInRole(new String[] {role});
		
	}
	
	/**
	 * @param roles
	 * @return
	 */
	protected boolean isUserInRole(String[] roles) {
		
		boolean result = false;
		
		if (roles != null && roles.length > 0) {
			BitSet rolesBitSet = new BitSet(roles.length);
			BitSet matchBitSet = SecurityUtils.getMatchBitSet(roles.length);
			rolesBitSet.clear();
			Authentication userAuthentication = SecurityContextHolder.getContext().getAuthentication();
			
			Collection<GrantedAuthority> grantedAuthorities = userAuthentication.getAuthorities();
			for(int index = 0; index < roles.length; index++) {
				 rolesBitSet.set(index, 
						 grantedAuthorities.contains(new GrantedAuthorityImpl(roles[index])));
			}
			
			result = rolesBitSet.intersects(matchBitSet);
		}
		
		return result;
		
	}
	
	/**
	 * @param pagedListHolderId
	 * @param action
	 * @param editableRowInputData
	 * @param request
	 */
	protected void holdProcessActionData(final String pagedListHolderId, 
			final AbstractCommand.CommandActions action, 
			final EditableRowInputData editableRowInputData, 
			final HttpServletRequest request) {
		
		request.getSession().setAttribute(Constants.ControllerUtils.PROCESS_ACTION_HOLDER_DATA_KEY , 
				new ProcessActionDataHolder(pagedListHolderId, action, editableRowInputData, request));
		
	}
	
	/**
	 * @param request
	 * @return
	 */
	protected ProcessActionDataHolder popProcessActionData(HttpServletRequest request) {
		
		return (ProcessActionDataHolder)request.getSession().getAttribute(Constants.ControllerUtils.PROCESS_ACTION_HOLDER_DATA_KEY);
		
	}
	
	/**
	 * @param request
	 * @return
	 */
	protected void restoreControllerModel(ModelMap model, HttpServletRequest request) {

		ModelMap backupModel = (ModelMap)request.getSession().getAttribute(Constants.ControllerUtils.LOGOUT_TMP_MODEL_SESSION_KEY);
		model.clear();
		model.addAllAttributes(backupModel);
		request.getSession().removeAttribute(Constants.ControllerUtils.LOGOUT_TMP_MODEL_SESSION_KEY);
		
	}
	
	/* (non-Javadoc)
	 * @see it.people.console.web.servlet.mvc.IController#getCommandObjectName()
	 */
	public String getCommandObjectName() {
		return this.commandObjectName;
	}
	
	/**
	 * @param name
	 */
	protected void setCommandObjectName(String name) {
		this.commandObjectName = name;
	}
	
	/**
	 * @param request
	 * @return
	 */
	protected PplUserData getLoggedUserData(HttpServletRequest request) {
		
		PplUserData result = null;

		if (request.getSession() != null) {
			result = (PplUserData)request.getSession().getAttribute(Constants.Security.AUTHENTICATED_USER_DATA_SESSION_KEY);
		}
		
		return result;
		
	}
	
	/**
	 * @param persistenceBroker
	 * @param communeCodes
	 * @return
	 */
	protected final Map<String, Vector<String>> getFeInterfacesReferencesByCommuneId(IPersistenceBroker persistenceBroker, String[] communeCodes) {
		
		Map<String, Vector<String>> result = new HashMap<String, Vector<String>>();
		
		if (communeCodes != null) { 
			if (communeCodes.length == 1 & communeCodes[0].equalsIgnoreCase(String.valueOf(Constants.UNBOUND_VALUE))) {
				result = persistenceBroker.getAllFEInterfaces();
			} else {
				result = persistenceBroker.getFEInterfaces(communeCodes);
			}
		}
		
		return result;
		
	}
	
}
