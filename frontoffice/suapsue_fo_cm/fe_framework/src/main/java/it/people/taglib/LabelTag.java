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
/*
 * Creato il 27-lug-2007 da Cedaf s.r.l.
 *
 */
package it.people.taglib;

import it.people.City;
import it.people.process.AbstractPplProcess;
import it.people.util.MessageBundleHelper;

import java.util.Locale;

import javax.servlet.jsp.JspException;

import org.apache.log4j.Logger;
import org.apache.struts.taglib.TagUtils;

/**
 * @author Michele Fabbri - Cedaf s.r.l.
 * 
 */
public class LabelTag extends MessageTag {
    protected String _for = null;

    public String getFor() {
	return (this._for);
    }

    public void setFor(String _for) {
	this._for = _for;
    }

    private static Logger logger = Logger.getLogger(LabelTag.class);

    public int labelToHtml(AbstractPplProcess process, Locale locale)
	    throws JspException {

	TagUtils tagUtils = TagUtils.getInstance();

	String key = this.key;
	// ******** RETROCOMPATIBILITA' ************************
	// Per retrocompatibilit� con le label che sostiuivano
	// {0} con il carattere *
	setArg0("");
	// *****************************************************
	if (key == null) {
	    Object value = tagUtils.lookup(pageContext, name, property, scope);
	    if (value != null && !(value instanceof String)) {
		JspException e = new JspException(messages.getMessage(
			"message.property", key));
		tagUtils.saveException(pageContext, e);
		throw e;
	    }
	    key = (String) value;
	}
	Object args[] = new Object[5];
	args[0] = arg0;
	args[1] = arg1;
	args[2] = arg2;
	args[3] = arg3;
	args[4] = arg4;

	City city = (City) pageContext.getSession().getAttribute("City");
	String comuneKey = null;
	if (city != null)
	    comuneKey = city.getOid();

	if (logger.isDebugEnabled())
	    logger.debug("Localizzazione per comune key [" + comuneKey + "]");

	String message = key;
	if (process == null) {
	    // messaggio relativo al framework
	    message = MessageBundleHelper.message(key, args, comuneKey, locale);
	} else {
	    // messaggio relativo allo svolgimento di una pratica
	    message = MessageBundleHelper.message(key, args,
		    process.getProcessName(), comuneKey, locale);
	}
	if (message == null)
	    message = key;

	String rendering = "<label";

	if (this._for != null) {
	    rendering += " for=\"" + this._for + "\"";
	}
	rendering += ">" + message + "</label>";

	tagUtils.write(pageContext, rendering);
	return 0;
    }
}
