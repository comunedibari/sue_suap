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

import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.util.Assert;
import org.springframework.web.servlet.tags.form.AbstractHtmlInputElementTag;
import org.springframework.web.servlet.tags.form.TagWriter;

import it.people.console.beans.Option;
import it.people.console.beans.support.AbstractFilter;
import it.people.console.beans.support.IFilter;
import it.people.console.beans.support.ListableFilter;
import it.people.console.enumerations.IOperatorsEnum;
import it.people.console.persistence.beans.support.FilterProperties;
import it.people.console.utils.Constants;
import it.people.console.utils.StringUtils;
import static it.people.console.web.servlet.tags.TagsConstants.*;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 04/gen/2011 15.00.49
 *
 */
public class FilterTag extends AbstractHtmlInputElementTag {

	private static Logger logger = LoggerFactory.getLogger(FilterTag.class);
	
	private static final long serialVersionUID = 5271107927822795868L;

	private Object filter;
	
	private final IFilter getFilter() {
		return (IFilter)filter;
	}

	public void setFilter(Object filter) {
		Assert.notNull(filter, "'filter' must not be null");
		Assert.isTrue(isFilter(filter), "'filter' must extends AbstractFilter class.");
		this.filter = filter;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.tags.form.AbstractFormTag#writeTagContent(org.springframework.web.servlet.tags.form.TagWriter)
	 */
	@Override
	protected int writeTagContent(TagWriter tagWriter) throws JspException {

		tagWriter.startTag("div");
		tagWriter.writeAttribute("class", FILTER_ROW_WIDTH_CLASS);
		tagWriter.startTag("div");
		tagWriter.writeAttribute("class", FILTER_ROW_CLEARFIX_CLASS);
		
		if (this.getFilter().isListable()) {
			if (logger.isDebugEnabled()) {
				logger.debug("Writing listable filter " + this.getFilter().getClass().getName() + ".");
			}
			writeListableFilterTagContent(tagWriter);
		}
		else {
			if (logger.isDebugEnabled()) {
				logger.debug("Writing not listable filter " + this.getFilter().getClass().getName() + ".");
			}
			writeNotListableFilterTagContent(tagWriter);
		}
		tagWriter.endTag(true);
		tagWriter.endTag(true);
		
		return EVAL_PAGE;
		
	}

	private void writeListableFilterTagContent(TagWriter tagWriter) throws JspException {
		IFilter filter = (IFilter)this.getFilter();
		
		writeFilterCheckbox(filter, tagWriter);
		
		
		tagWriter.startTag("div");
		
		tagWriter.writeAttribute("class", FILTER_ROW_TWO_COLUMNS_CLASS);
		
		writeFilterLogicalOperators(filter, tagWriter);
		
		writeSelectValues(filter, tagWriter);

		tagWriter.endTag(true);
		
		
		
		
		
	}

	private void writeNotListableFilterTagContent(TagWriter tagWriter) throws JspException {
		IFilter filter = (IFilter)this.getFilter();

		writeFilterCheckbox(filter, tagWriter);
		
		
		tagWriter.startTag("div");
		
		tagWriter.writeAttribute("class", FILTER_ROW_TWO_COLUMNS_CLASS);
		
		writeFilterLogicalOperators(filter, tagWriter);
		
		writeTextControl(filter, tagWriter);

		tagWriter.endTag(true);
		
		
		
	}
	
	private void writeFilterLogicalOperators(IFilter filter, TagWriter tagWriter) throws JspException {
		tagWriter.startTag("div");
		tagWriter.writeAttribute("class", FILTER_ROW_MAIN_COLUMN_CLASS);
		
		tagWriter.startTag("select");
		tagWriter.writeAttribute("id", filter.getName() + FILTER_TAG_LOGICAL_OPERATORS_SUFFIX);
		tagWriter.writeAttribute("name", FILTER_VALUE_PREFIX + filter.getName() + FILTER_TAG_LOGICAL_OPERATORS_SUFFIX);
		writeLogicalOperatorsOptions(filter, tagWriter);
		tagWriter.endTag(true);
		
		tagWriter.endTag(true);
	}

	private void writeLogicalOperatorsOptions(IFilter filter, TagWriter tagWriter) throws JspException {
		Vector<IOperatorsEnum> logicalOperators = filter.getFilterAllowedOperators();
		for(IOperatorsEnum operatorsEnum : logicalOperators) {
			tagWriter.startTag("option");
			tagWriter.writeAttribute("value", operatorsEnum.getValue());
			if (operatorsEnum.getValue().trim().equalsIgnoreCase(filter.getOperator())) {
				tagWriter.writeAttribute("selected", "selected");
			}
			tagWriter.appendValue(operatorsEnum.getLabel());
			tagWriter.endTag(true);
		}
	}
	
	private void writeSelectValues(IFilter filter, TagWriter tagWriter) throws JspException {
		tagWriter.startTag("div");
		tagWriter.writeAttribute("class", FILTER_ROW_RIGHT_COLUMN_CLASS);
		
		tagWriter.startTag("select");
		tagWriter.writeAttribute("id", filter.getName() + LISTABLE_FILTER_TAG_SELECT_SUFFIX);
		tagWriter.writeAttribute("name", FILTER_VALUE_PREFIX + filter.getName() + LISTABLE_FILTER_TAG_SELECT_SUFFIX);
		writeSelectOptions(filter, tagWriter);
		tagWriter.endTag(true);
		
		tagWriter.endTag(true);
	}

	private void writeSelectOptions(IFilter filter, TagWriter tagWriter) throws JspException {
		
		ListableFilter listableFilter = (ListableFilter)filter;
		Vector<Option> selecOptions = listableFilter.getFilterAllowedValues();
		
		for(Option selecOption : selecOptions) {
			tagWriter.startTag("option");
			tagWriter.writeAttribute("value", selecOption.getValue());
			if (selecOption.getLabel().trim().equalsIgnoreCase(filter.getValue())) {
				tagWriter.writeAttribute("selected", "selected");
			}
			tagWriter.appendValue(selecOption.getLabel());
			tagWriter.endTag(true);
		}
	}
	
	private void writeTextControl(IFilter filter, TagWriter tagWriter) throws JspException {
		
		String value = filter.getValue();
		HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
		@SuppressWarnings("unchecked")
		List<FilterProperties> appliedFilters = (List<FilterProperties>)request.getSession().getAttribute(Constants.ControllerUtils.APPLIED_FILTERS_KEY);
		if (appliedFilters != null) {
			for(FilterProperties appliedFilter : appliedFilters) {
				if (appliedFilter.getColumn().equalsIgnoreCase(filter.getName())) {
					value = appliedFilter.getValue();
				}
			}
		}
		
		tagWriter.startTag("div");
		tagWriter.writeAttribute("class", FILTER_ROW_RIGHT_COLUMN_CLASS);
		
		tagWriter.startTag("input");
		tagWriter.writeAttribute("type", "text");
		tagWriter.writeAttribute("id", filter.getName() + NOT_LISTABLE_FILTER_TAG_TEXT_SUFFIX);
		tagWriter.writeAttribute("name", FILTER_VALUE_PREFIX + filter.getName() + NOT_LISTABLE_FILTER_TAG_TEXT_SUFFIX);
		tagWriter.writeAttribute("value", value);
		tagWriter.endTag(true);
		
		tagWriter.endTag(true);
	}
	
	private void writeFilterCheckbox(IFilter filter, TagWriter tagWriter) throws JspException {

		boolean isActive = filter.isActive();
		HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
		@SuppressWarnings("unchecked")
		List<FilterProperties> appliedFilters = (List<FilterProperties>)request.getSession().getAttribute(Constants.ControllerUtils.APPLIED_FILTERS_KEY);
		if (appliedFilters != null) {
			for(FilterProperties appliedFilter : appliedFilters) {
				if (appliedFilter.getColumn().equalsIgnoreCase(filter.getName())) {
					isActive = true;
				}
			}
		}
		
		tagWriter.startTag("div");
		tagWriter.writeAttribute("class", FILTER_ROW_LEFT_COLUMN_CLASS);
		
		tagWriter.startTag("input");
		tagWriter.writeAttribute("type", "checkbox");
		tagWriter.writeAttribute("id", filter.getName() + FILTER_TAG_CHECKBOX_SUFFIX);
		tagWriter.writeAttribute("name", FILTER_VALUE_PREFIX + filter.getName() + FILTER_TAG_CHECKBOX_SUFFIX);
		if (isActive) {
			tagWriter.writeAttribute("checked", "checked");
		}
		tagWriter.endTag(true);

		tagWriter.startTag("label");
		tagWriter.writeAttribute("for", filter.getName() + FILTER_TAG_CHECKBOX_SUFFIX);
		tagWriter.appendValue(filter.getLabel());
		tagWriter.endTag(true);

		tagWriter.endTag(true);
		
	}
	
	private boolean isFilter(Object filter) {
		
		Class<?> superClass = filter.getClass().getSuperclass();
		if (superClass != null) {
			return StringUtils.nullToEmpty(superClass.getCanonicalName())
				.equalsIgnoreCase(AbstractFilter.class.getCanonicalName());
		}
		
		return false;
		
	}
	
}
