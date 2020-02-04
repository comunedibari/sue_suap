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
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTag;

import it.people.console.utils.StringUtils;
import it.people.console.web.servlet.tags.TagsConstants;
import it.people.console.web.servlet.tags.base.LoggableBodyTagSupport;
import it.people.console.web.utils.WebUtils;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 04/gen/2011 09.07.25
 *
 */
public class TipButtonTag extends LoggableBodyTagSupport {
	
	private static final long serialVersionUID = -6900781849858073384L;

	private String cssClass;
	
	private String name;
	
	private String id = "";
	
	private String label;
	
	private String visibleDetailsImage;

	private String hiddenDetailsImage;
	
	public final void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final void setId(String id) {
		this.id = id;
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
		int doEval = SKIP_BODY;
		String cssClassName = "";
		try {
			
			HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();

			HashMap<String, String> detailsStatusesHashMap = getDetailStatusesHashMap();
			
			boolean detailEnabled = false;
			if (detailsStatusesHashMap.get(this.getId()) == null) {
				detailsStatusesHashMap.put(this.getId(), TagsConstants.HIDE_DETAIL);
			} else {
				if (!isPageChanged() && WebUtils.isParamInRequest(request, this.getId())) {
					detailEnabled = String.valueOf(detailsStatusesHashMap.get(this.getId())).equalsIgnoreCase(TagsConstants.SHOW_DETAIL);
					if (detailEnabled) {
						detailEnabled = false;
						detailsStatusesHashMap.put(this.getId(), TagsConstants.HIDE_DETAIL);
					} else {
						detailEnabled = true;
						detailsStatusesHashMap.put(this.getId(), TagsConstants.SHOW_DETAIL);
					}
					getRequest().getSession().setAttribute(TagsConstants.DETAILS_STATUSES_HASHMAP_SESSION_KEY, detailsStatusesHashMap);
				} else {
					detailEnabled = String.valueOf(detailsStatusesHashMap.get(this.getId())).equalsIgnoreCase(TagsConstants.SHOW_DETAIL);
				}
			}
			
			JspWriter writer = pageContext.getOut();
			String image = "";
			String tipContent = "";
			
			if (detailEnabled) {
				doEval = BodyTag.EVAL_BODY_BUFFERED;
				image = WebUtils.sanitizeImagePath(this.getVisibleDetailsImage());

				cssClassName = " class=\"expandedTip\"";
				
				BodyContent bodyContent = getBodyContent();
				if (bodyContent != null) {
					tipContent = bodyContent.getString();
				}
				
			}
			else {
				doEval = SKIP_BODY;
				image = WebUtils.sanitizeImagePath(this.getHiddenDetailsImage());
				
				cssClassName = " class=\"collapsedTip\"";

			}

			String inputTag = "";
			
			inputTag += "<fieldset" + cssClassName + ">";
			inputTag += "<legend>";
			inputTag += "<input type=\"image\" name=\"" + this.getId() + "\" id=\"" + 
				this.getId() + "\" ";
			
			inputTag += " src=\"" + request.getContextPath() + image + "\" />";
			
			inputTag += StringUtils.nullToEmpty(this.getLabel());
			inputTag += "</legend>";

			if(!StringUtils.isEmptyString(tipContent)) {
				inputTag += tipContent;
			}
			
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

	private String generateId() {
		return UUID.randomUUID().toString();
	}

	private HttpServletRequest getRequest() {
		return (HttpServletRequest)pageContext.getRequest();
	}

	private HashMap<String, String> getDetailStatusesHashMap() {
		
		@SuppressWarnings("unchecked")
		HashMap<String, String> result = (HashMap<String, String>)getRequest().getSession().getAttribute(TagsConstants.DETAILS_STATUSES_HASHMAP_SESSION_KEY);
		if (result == null) {
			result = new HashMap<String, String>();
			getRequest().getSession().setAttribute(TagsConstants.DETAILS_STATUSES_HASHMAP_SESSION_KEY, result);
		}
		
		return result;
		
	}

	private boolean isPageChanged() {

		HttpServletRequest request = getRequest();
		
		String from = WebUtils.getReferer(request);
		String to = request.getRequestURL().toString();

		String fromTemp = "".equalsIgnoreCase(from) ? "" : from.substring(from.indexOf(request.getContextPath()) + request.getContextPath().length() + 1);
		String toTemp = "".equalsIgnoreCase(to) ? "" : to.substring(to.indexOf(request.getContextPath()) + request.getContextPath().length() + 1);
		
		String fromCleaned = fromTemp.indexOf('?') > 0 ? fromTemp.substring(0, fromTemp.indexOf('?')) : fromTemp;
		String toCleaned = toTemp.indexOf('?') > 0 ? toTemp.substring(0, toTemp.indexOf('?')) : toTemp;
		
//		if (logger.isTraceEnabled()) {
//			logger.trace("From: " + from);
//			logger.trace("To: " + to);
//			
//			logger.trace("From cleaned: " + fromCleaned);
//			logger.trace("To cleaned: " + toCleaned);
//		}
		
		return !toCleaned.equalsIgnoreCase(fromCleaned);
		
	}
	
}
