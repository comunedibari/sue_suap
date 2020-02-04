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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;

import org.springframework.util.Assert;
import org.springframework.web.servlet.tags.form.AbstractHtmlInputElementTag;
import org.springframework.web.servlet.tags.form.TagWriter;

import it.people.console.domain.PairElement;

public class TabbedPaneHeaderTag extends AbstractHtmlInputElementTag {
	
	private static final long serialVersionUID = -4648267943720486675L;

	private String queryStringParam;
	
	private Object tabsParams;
	
	private String selectedTab;
	
	private String selectedTabCssId;

	/**
	 * @param queryStringParam the queryStringParam to set
	 */
	public final void setQueryStringParam(String queryStringParam) {
		this.queryStringParam = queryStringParam;
	}

	/**
	 * @param tabsParams the tabsParams to set
	 */
	public final void setTabsParams(Object tabsParams) {
		Assert.notNull(tabsParams, "'tabsParams' must not be null");
		Assert.isInstanceOf(ArrayList.class, tabsParams, "'pagedListHoldersCache' must be of ArrayList type class");
		this.tabsParams = tabsParams;
	}

	/**
	 * @param selectedTab the selectedTab to set
	 */
	public final void setSelectedTab(String selectedTab) {
		this.selectedTab = selectedTab;
	}

	/**
	 * @param selectedTabCssId the selectedTabCssId to set
	 */
	public final void setSelectedTabCssId(String selectedTabCssId) {
		this.selectedTabCssId = selectedTabCssId;
	}

	/**
	 * @return the queryStringParam
	 */
	private String getQueryStringParam() {
		return this.queryStringParam;
	}

	/**
	 * @return the tabsParams
	 */
	private Object getTabsParams() {
		return this.tabsParams;
	}

	/**
	 * @return the selectedTab
	 */
	private String getSelectedTab() {
		return this.selectedTab;
	}

	/**
	 * @return the selectedTabCssId
	 */
	private String getSelectedTabCssId() {
		return this.selectedTabCssId;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.tags.form.AbstractFormTag#writeTagContent(org.springframework.web.servlet.tags.form.TagWriter)
	 */
	@Override
	protected int writeTagContent(TagWriter tagWriter) throws JspException {

		tagWriter.startTag("ul");
		
		@SuppressWarnings("unchecked")
		List<PairElement<String, String>> tabsParams = (List<PairElement<String, String>>)this.getTabsParams();
		Iterator<PairElement<String, String>> tabsParamIterator = tabsParams.iterator();
		while(tabsParamIterator.hasNext()) {
			PairElement<String, String> tabParam = tabsParamIterator.next();
			tagWriter.startTag("li");
			if (tabParam.getValue().equalsIgnoreCase(this.getSelectedTab())) {
				tagWriter.writeAttribute("id", this.getSelectedTabCssId());
				tagWriter.appendValue(tabParam.getLabel());
			} else {
				tagWriter.startTag("a");
				tagWriter.writeAttribute("href", addTabHref(tabParam.getValue()));
				tagWriter.appendValue(tabParam.getLabel());
				tagWriter.endTag(true);
			}
			tagWriter.endTag(true);
			tagWriter.appendValue("<!-- -->");
		}
		
		tagWriter.endTag(true);
		
		return EVAL_PAGE;
	}

	/**
	 * @param tabParam
	 * @return
	 */
	private String addTabHref(String tabParam) {
		
		String result = tabParam;
		
		String queryString = this.getRequestContext().getQueryString();
		if (queryString != null && !queryString.equalsIgnoreCase("")) {
			result = "?" + sanitizeQueryString(queryString) + "&";
		} else {
			result = "?";
		}
		
		return result + this.getQueryStringParam() + "=" + tabParam;
		
	}
	
	/**
	 * @param queryString
	 * @return
	 */
	private String sanitizeQueryString(String queryString) {
		
		String result = queryString;
		
		if (queryString != null && !queryString.equalsIgnoreCase("")) {
			if (queryString.indexOf(this.getQueryStringParam() + "=") >= 0) {
				
				String searchingValue = this.getQueryStringParam() + "=";
				
				if (queryString.indexOf(searchingValue) == 0) {
					int argumentSeparator = queryString.indexOf('&');
					if (argumentSeparator > 0) {
						result = queryString.substring(argumentSeparator + 1);
					} else {
						result = "";
					}
				}
		
				if (queryString.indexOf("&" + searchingValue) >= 0) {
					int argumentSeparator = queryString.substring(queryString.indexOf("&" + searchingValue) + 1) .indexOf('&');
					if (argumentSeparator > 0) {
						argumentSeparator += queryString.substring(0, (queryString.indexOf("&" + searchingValue) + 1)).length();
						String left = queryString.substring(0, queryString.indexOf("&" + searchingValue) + 1);
						String right = queryString.substring(argumentSeparator + 1);
						result = left + right;
					} else {
						result = queryString.substring(0, queryString.indexOf("&" + searchingValue));
					}
					
				}
				
			}
		}
		
		return result;
		
	}
	
}
