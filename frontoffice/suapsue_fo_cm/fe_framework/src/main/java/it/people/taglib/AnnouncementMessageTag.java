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
package it.people.taglib;

import it.people.City;
import it.people.PeopleConstants;
import it.people.process.AbstractPplProcess;

import java.util.Locale;

import javax.servlet.jsp.JspException;

import org.apache.log4j.Logger;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.Constants;

public class AnnouncementMessageTag extends
	org.apache.struts.taglib.bean.MessageTag {

    private static final long serialVersionUID = 7463641288692135894L;

    private static Logger logger = Logger.getLogger(MessageTag.class);

    private String cssClass = "";

    /**
     * @return the cssClass
     */
    public final String getCssClass() {
	return this.cssClass;
    }

    /**
     * @param cssClass
     *            the cssClass to set
     */
    protected final void setCssClass(String cssClass) {
	this.cssClass = cssClass;
    }

    public AnnouncementMessageTag() {
	super();
    }

    public void release() {
	super.release();

    }

    public int doStartTag() throws JspException {
	try {
	    return this.announcementMessage();
	} catch (JspException jspe) {
	    // in caso di errore non visualizza nulla
	    logger.warn("Problema nella visualizzazione del messaggio:\n"
		    + jspe.getMessage());
	    return SKIP_BODY;
	}
    }

    public int announcementMessage() throws JspException {

	TagUtils tagUtils = TagUtils.getInstance();

	City city = (City) pageContext.getSession().getAttribute("City");

	if (city != null && city.isShowAnnouncement()) {
	    String rendering = "<span";
	    rendering += (this.getCssClass() != null && !this.cssClass
		    .equalsIgnoreCase("")) ? " style=\"" + this.getCssClass()
		    + "\">" : ">";
	    rendering += city.getAnnouncementMessage() + "</span>";
	    tagUtils.write(pageContext, rendering);
	}

	return 0;

    }

    public int doEndTag() throws JspException {
	return super.doEndTag();
    }

}
