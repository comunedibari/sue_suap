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

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.ojb.broker.util.Base64;
import org.springframework.util.Assert;

import it.people.console.utils.StringUtils;
import it.people.console.web.servlet.tags.TagsConstants;
import it.people.console.web.utils.WebUtils;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 04/gen/2011 09.07.25
 *
 */
public class CommandsTag extends TagSupport {

	private static final long serialVersionUID = -8691336341577362616L;

	private String cssClass;
	
	private String name;
	
	private String id = "";
	
	private Object detailsMap;
	
	private String label;
	
	private String visibleDetailsImage;

	private String hiddenDetailsImage;
	
	//For saving state
	
	private String image;
	
	private int doEval;
	
	public final void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final void setId(String id) {
		this.id = id;
	}

	public final void setDetailsMap(Object detailsMap) {
		Assert.notNull(detailsMap, "'detailsMap' must not be null");
		Assert.isInstanceOf(Map.class, detailsMap, "'detailsMap' must be of Map type class");
		this.detailsMap = detailsMap;
	}

	public final void setLabel(String label) {
		this.label = label;
	}
	
	public final void setVisibleDetailsImage(String visibleDetailsImage) {
		this.visibleDetailsImage = visibleDetailsImage;
	}

	public final void setHiddenDetailsImage(String hiddenDetailsImage) {
		this.hiddenDetailsImage = hiddenDetailsImage;
	}

	private String getCssClass() {
		return cssClass;
	}

	private String getName() {
		return name;
	}

	public String getId() {
		if (StringUtils.isEmptyString(this.id)) {
			this.setId(generateId());
		}
		return id;
	}

	private Object getDetailsMap() {
		return detailsMap;
	}

	private String getLabel() {
		return label;
	}

	private String getVisibleDetailsImage() {
		return visibleDetailsImage;
	}

	private String getHiddenDetailsImage() {
		return hiddenDetailsImage;
	}

	public int doStartTag() throws JspException {
		doEval = SKIP_BODY;
		String cssClassName = " ";
		if (!StringUtils.isEmptyString(this.getCssClass())) {
			cssClassName = " class=\"" + getCssClass() + "\"";
		}
		try {
			
			HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
			
			
			String inputTag = "";
			
			inputTag += "<fieldset" + cssClassName + ">";
			inputTag += "<legend>";
			inputTag += "<input type=\"image\" name=\"" + this.getName() + "\" id=\"" + 
				this.getId() + "\" ";
			
			JspWriter writer = pageContext.getOut();
			image = "";
			if (!alreadyParsed() || WebUtils.isParamInRequest(request, this.getName())) {
				if (detailsEnabled()) {
					doEval = EVAL_BODY_INCLUDE;
					image = WebUtils.sanitizeImagePath(this.getVisibleDetailsImage());
				}
				else {
					doEval = SKIP_BODY;
					image = WebUtils.sanitizeImagePath(this.getHiddenDetailsImage());
				}
			}
			else {
				restoreState(request);
			}
			inputTag += " src=\"" + request.getContextPath() + image + "\" />";
			
			saveState();

			inputTag += StringUtils.nullToEmpty(this.getLabel());
			inputTag += "</legend>";
			inputTag += "</fieldset>";
			
			writer.write("<div>");
			writer.write(inputTag);
			writer.write("</div>");
		}
		catch (Exception e) {
			throw new JspException();
		}
		return doEval;
	}
	
	@SuppressWarnings("unchecked")
	private boolean detailsEnabled() {
		
		boolean result = false;
		
		Map<String, String> detailsMapBuffer = (Map<String, String>)this.getDetailsMap();
		if (!detailsMapBuffer.containsKey(this.getId())) {
			detailsMapBuffer.put(this.getId(), TagsConstants.HIDE_DETAIL);
		}
		else {
			String detailStatus = (String)detailsMapBuffer.get(this.getId());
			result = detailStatus.equalsIgnoreCase(TagsConstants.HIDE_DETAIL);
			detailsMapBuffer.put(this.getId(), result ? TagsConstants.SHOW_DETAIL : TagsConstants.HIDE_DETAIL);
		}
		
		return result;
		
	}
	
	private String generateId() {
		return UUID.randomUUID().toString();
	}
	
	
	private void saveState() throws JspException {
		Object state[] = new Object[2];
		state[0] = doEval;
		state[1] = image;
		writeHiddenField(this.getName() + TagsConstants.STATE_SUFFIX, Base64.encodeObject(state), 
				pageContext.getOut());
	}
	
	private void restoreState(HttpServletRequest request) {
		String savedState = WebUtils.getRequestParamValue(request, this.getName() + TagsConstants.STATE_SUFFIX);
		
		if (!StringUtils.isEmptyString(savedState)) {
			Object state[] = (Object[])Base64.decodeToObject(savedState);
			doEval = (Integer)state[0];
			image = (String)state[1];
		}
		else {
			doEval = SKIP_BODY;
			image = WebUtils.sanitizeImagePath(this.getHiddenDetailsImage());
		}
		
	}
	
	private void writeHiddenField(String name, String value, JspWriter writer) throws JspException {
		String hiddenTag = "<input type=\"hidden\" name=\"" +name + "\" value=\"" + value + "\" />";
		try {
			writer.write(hiddenTag);
		} catch (IOException e) {
			throw new JspException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	private boolean alreadyParsed() {
		Map<String, String> detailsMapBuffer = (Map<String, String>)this.getDetailsMap();
		return detailsMapBuffer.containsKey(this.getId());
	}
	
}
