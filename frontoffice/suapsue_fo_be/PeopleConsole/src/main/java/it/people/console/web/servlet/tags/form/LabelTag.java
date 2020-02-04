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
package it.people.console.web.servlet.tags.form;

import javax.servlet.jsp.JspException;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.tags.form.TagWriter;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 24/giu/2011 19.43.22
 *
 */
public class LabelTag extends
		org.springframework.web.servlet.tags.form.AbstractHtmlElementTag {

	private static final long serialVersionUID = -5927835769174465282L;

	private static final String NBSP = "&nbsp;";
	
	private static final String LABEL_TAG = "label";

	private static final String FOR_ATTRIBUTE = "for";

	private TagWriter tagWriter;

	private String forId;
	
	private String required;
	
	private String cssClass;

	public void setFor(String forId) {
		Assert.notNull(forId, "'forId' must not be null");
		this.forId = forId;
	}

	public String getFor() {
		return this.forId;
	}

	public void setRequired(String required) {
		this.required = required;
	}

	public String getRequired() {
		return this.required;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getCssClass() {
		return this.cssClass;
	}
	
	@Override
	protected int writeTagContent(TagWriter tagWriter) throws JspException {
		tagWriter.startTag(LABEL_TAG);
		tagWriter.writeAttribute(FOR_ATTRIBUTE, resolveFor());
		if (this.getCssClass() != null && !this.getCssClass().equalsIgnoreCase("")) {
			tagWriter.writeAttribute(CLASS_ATTRIBUTE, this.getCssClass());
		}
		writeDefaultAttributes(tagWriter);
		tagWriter.forceBlock();
		this.tagWriter = tagWriter;
		return EVAL_BODY_INCLUDE;
	}

	@Override
	protected String getName() throws JspException {
		// This also suppresses the 'id' attribute (which is okay for a <label/>)
		return null;
	}

	protected String resolveFor() throws JspException {
		if (StringUtils.hasText(this.forId)) {
			return getDisplayString(evaluate(FOR_ATTRIBUTE, this.forId));
		}
		else {
			return autogenerateFor();
		}
	}

	protected String autogenerateFor() throws JspException {
		return StringUtils.deleteAny(getPropertyPath(), "[]");
	}

	@Override
	public int doEndTag() throws JspException {
		if (this.getRequired() != null && !this.getRequired().equalsIgnoreCase("")) {
			this.tagWriter.appendValue(this.getRequired() + NBSP);
		}
		this.tagWriter.endTag();
		return EVAL_PAGE;
	}

	@Override
	public void doFinally() {
		super.doFinally();
		this.tagWriter = null;
	}
	
}
