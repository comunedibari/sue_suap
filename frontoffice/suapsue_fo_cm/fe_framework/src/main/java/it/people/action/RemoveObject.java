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

import it.people.process.AbstractPplProcess;
import it.people.process.common.entity.Attachment;
import it.people.util.PeopleProperties;
import it.people.util.ProcessUtils;

import java.io.File;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Category;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 
 * User: sergio Date: Sep 29, 2003 Time: 7:33:46 AM <br>
 * <br>
 * Rimuove un oggetto di tipo 'propertyName' dal processo.
 */
public class RemoveObject extends Action {

    private Category cat = Category.getInstance(RemoveObject.class.getName());

    /**
     * 
     * @param mapping
     *            Oggetto contenente la "mappatura" delle azioni e dei forward
     * @param form
     *            Form inviata
     * @param request
     *            Request
     * @param response
     *            Respose
     * @return Restituisce il forward da eseguire a seconda dell'esito
     *         dell'azione
     * @throws Exception
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws Exception {
	try {
	    String propertyName = request.getParameter("propertyName");
	    String indexValue = request.getParameter("index");
	    AbstractPplProcess pplProcess = (AbstractPplProcess) form;
	    int lastPos = propertyName.lastIndexOf('.');
	    if (lastPos != -1 && lastPos < propertyName.length() - 1) {
		String beanProperty = propertyName.substring(0, lastPos);
		String methodProperty = propertyName.substring(lastPos + 1);

		Object bean = PropertyUtils.getProperty(form, beanProperty);
		if (bean != null) {
		    Method mthd = null;
		    // cerco metodo con un parametro indice (per lavorare sulle
		    // collezioni)
		    try {
			// String nuovaGestioneFile =
			// request.getSession().getServletContext().getInitParameter("remoteAttachFile");
			// if (nuovaGestioneFile!=null &&
			// nuovaGestioneFile.equalsIgnoreCase("true")){
			if (!pplProcess.isEmbedAttachmentInXml()) {
			    try {
				mthd = bean.getClass().getMethod(
					"get" + capitalize(methodProperty),
					new Class[] { int.class });
				Attachment tmp = (Attachment) mthd.invoke(bean,
					new Object[] { Integer
						.valueOf(indexValue) });
				File f = new File(tmp.getPath());
				if (f.exists()) {
				    f.delete();
				}
			    } catch (Exception e) {
				cat.error("Non è stato possibile rimuovere il file nel File System!!");
			    }
			}
			mthd = bean.getClass().getMethod(
				"remove" + capitalize(methodProperty),
				new Class[] { int.class });
			mthd.invoke(bean,
				new Object[] { Integer.valueOf(indexValue) });
			request.setAttribute(
				RequestProcessor.IGNORE_POPULATEBEAN, "Ignore");
			cat.debug("success");
			return mapping.findForward("success");
		    } catch (Exception ex) {
			if (ex instanceof NoSuchMethodException
				|| ex instanceof NumberFormatException) {
			    cat.info(ex);
			    // cerco metodo senza parametri (per lavorare su
			    // singoli attributi)
			    try {
				mthd = bean.getClass().getMethod(
					"remove" + capitalize(methodProperty),
					new Class[0]);
				mthd.invoke(bean, new Object[0]);
				request.setAttribute(
					RequestProcessor.IGNORE_POPULATEBEAN,
					"Ignore");
				cat.debug("success");
				return mapping.findForward("success");
			    } catch (NoSuchMethodException nsme) {
				cat.error(nsme);
			    }
			} else
			    throw ex;
		    }
		}
	    }
	} catch (Exception ex) {
	    cat.error(ex);
	}
	request.setAttribute(RequestProcessor.IGNORE_POPULATEBEAN, "Ignore");
	cat.debug("failed");
	return mapping.findForward("failed");
    }

    private static String capitalize(String s) {
	if (s.length() == 0) {
	    return s;
	}

	char chars[] = s.toCharArray();
	chars[0] = Character.toUpperCase(chars[0]);
	return new String(chars);
    }
}
