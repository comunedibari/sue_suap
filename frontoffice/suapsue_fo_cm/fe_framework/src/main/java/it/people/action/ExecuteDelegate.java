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
package it.people.action;

import it.people.core.PeopleContext;
import it.people.core.PplDelegateFactory;
import it.people.core.PplProcessDelegate;
import it.people.core.PplProcessDelegateManager;
import it.people.core.persistence.exception.peopleException;
import it.people.error.errorMessage;
import it.people.parser.DateValidator;
import it.people.parser.FieldValidator;
import it.people.parser.FiscalCodeValidator;
import it.people.parser.OptionalAttribute;
import it.people.parser.exception.ParserException;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Category;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 
 * User: Luigi Corollo Date: 12-gen-2004 Time: 16.25.09 <br>
 * <br>
 * Esegue le operazioni sui delegati (inserimento/cancellazione)
 */
public class ExecuteDelegate extends Action {
    private static String ERROR_JSP = "/framework/genericErrors/ProcessError.jsp";
    private errorMessage m_error;
    private Category cat = Category
	    .getInstance(ExecuteDelegate.class.getName());

    protected HashMap m_validators = new HashMap();

    public ExecuteDelegate() {
	m_validators.put("validFrom", new DateValidator(true, "dd/MM/yyyy"));
	m_validators.put("validTo", new DateValidator(true, "dd/MM/yyyy"));
	m_validators.put("delegateId", new FiscalCodeValidator(true));
    }

    /**
     * Valida i parametri passati nell hashmap.
     * 
     * @param p_params
     * @return una List delle proprieta' non valide.
     */
    public List validate(HashMap p_params) {
	List propertiesNotValid = new ArrayList();

	String propName = "delegateId";
	if (!validate(propName, p_params))
	    propertiesNotValid.add(propName);

	propName = "validFrom";
	if (!validate(propName, p_params))
	    propertiesNotValid.add(propName);

	propName = "validTo";
	if (!validate(propName, p_params))
	    propertiesNotValid.add(propName);

	return propertiesNotValid;
    }

    public ActionForward execute(ActionMapping p_actionMapping,
	    ActionForm p_actionForm, HttpServletRequest p_servletRequest,
	    HttpServletResponse p_servletResponse) throws Exception {
	super.execute(p_actionMapping, p_actionForm, p_servletRequest,
		p_servletResponse);

	try {
	    PeopleContext peopleContext = PeopleContext
		    .create(p_servletRequest);

	    // operazione
	    String operazione = p_servletRequest.getParameter("operazione");
	    boolean isInsert = "insert".equalsIgnoreCase(operazione);
	    boolean isDelete = "delete".equalsIgnoreCase(operazione);
	    boolean isUpdate = "update".equalsIgnoreCase(operazione);

	    // recupero i dati dell'operazione
	    String userId = p_servletRequest.getParameter("userId");
	    String comuneId = peopleContext.getCommune().getOid();
	    String delegateId = p_servletRequest.getParameter("delegateId");
	    String processName = p_servletRequest.getParameter("processName");
	    String validFrom = p_servletRequest.getParameter("validFrom");
	    String validTo = p_servletRequest.getParameter("validTo");

	    // Eseguo l'operazione
	    if (isInsert || isUpdate) {
		// valido i dati inviati
		HashMap params = new HashMap();
		params.put("delegateId", delegateId);
		params.put("validFrom", validFrom);
		params.put("validTo", validTo);

		List propertiesNotValid = validate(params);
		if (propertiesNotValid.size() > 0) {
		    p_servletRequest.setAttribute("propertiesNotValid",
			    propertiesNotValid);
		    return p_actionMapping.findForward("input");
		}

		// insert
		PplProcessDelegate pplProcessDelegate = new PplProcessDelegate();
		pplProcessDelegate.setUserId(userId);
		pplProcessDelegate.setDelegateId(delegateId);
		pplProcessDelegate.setCommuneId(comuneId);
		// pplProcessDelegate.setProcessClassName(Class.forName(processClassName));
		pplProcessDelegate.setProcessName(processName);

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date dataFrom = formatter.parse(validFrom);
		Date dataTo = formatter.parse(validTo);

		pplProcessDelegate.setValidFrom(new Timestamp(dataFrom
			.getTime()));
		pplProcessDelegate.setValidTo(new Timestamp(dataTo.getTime()));

		if (isUpdate) {
		    String oid = p_servletRequest.getParameter("oid");
		    if (oid != null)
			pplProcessDelegate.setOid(new Long(oid));
		}

		// insert
		try {
		    PplProcessDelegateManager.getInstance().set(
			    pplProcessDelegate);
		} catch (peopleException e) {
		    return p_actionMapping.findForward("failed");
		}
	    } else if (isDelete) {
		// delete
		String oid = p_servletRequest.getParameter("oid");
		if (oid != null)
		    PplProcessDelegateManager.getInstance().delete(
			    new Long(oid));
	    }

	    // svuoto la cache del factory
	    PplDelegateFactory.getInstance().clear();

	    // Se e' tutto ok success
	    return p_actionMapping.findForward("success");

	} catch (Exception ex) {
	    cat.error(ex);
	}

	return p_actionMapping.findForward("failed");
    }

    // Parte di validazione
    public boolean validate(String propertyName, HashMap p_params) {
	try {
	    return parserValidate(propertyName, p_params);
	} catch (ParserException pEx) {
	    cat.error(pEx);
	}
	return false;
    }

    public boolean parserValidate(String propertyName, HashMap p_params)
	    throws ParserException {

	FieldValidator validator = getValidator(propertyName);
	try {
	    if (validator != null)
		return validator.parserValidate(p_params.get(propertyName));
	} catch (ParserException pEx) {
	    cat.error(pEx);
	    pEx.setPropertyName(propertyName);
	    throw pEx;
	} catch (Exception ex) {
	    cat.error(ex);
	}
	return true;
    }

    public FieldValidator getValidator(String propertyName) {
	FieldValidator fv = (FieldValidator) m_validators.get(propertyName);
	if (fv == null) {
	    int indexedPropRightBrack = propertyName.lastIndexOf(']');
	    int indexedPropLeftBrack = propertyName.lastIndexOf('[');

	    if (indexedPropLeftBrack != -1 && indexedPropRightBrack != -1
		    && indexedPropLeftBrack < indexedPropRightBrack) {
		String generalIndexPropertyName = propertyName.substring(0,
			indexedPropLeftBrack + 1)
			+ propertyName.substring(indexedPropRightBrack);
		fv = (FieldValidator) m_validators
			.get(generalIndexPropertyName);
	    }
	}
	return (fv != null ? fv : new OptionalAttribute());
    }

}
