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
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.springframework.util.Assert;
import org.springframework.web.servlet.tags.form.AbstractHtmlInputElementTag;
import org.springframework.web.servlet.tags.form.TagWriter;

import it.people.console.security.Command;
import it.people.console.utils.StringUtils;
import it.people.console.web.utils.WebUtils;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 04/gen/2011 09.07.25
 *
 */
public class RowColumnsTag extends AbstractHtmlInputElementTag {

	private static final long serialVersionUID = 4595410347040834781L;

	private Object row;
	
	private Object rowActions = null;
	
	public final void setRow(Object row) {
		Assert.notNull(row, "'row' must not be null");
		Assert.isInstanceOf(Map.class, row, "'row' must be of Map type class");
		this.row = row;
	}

	public final void setRowActions(Object rowActions) {
		Assert.isInstanceOf(List.class, rowActions, "'rowActions' must be of List type class");
		this.rowActions = rowActions;
	}
	
	private Object getRow() {
		return row;
	}

	private Object getRowActions() {
		return this.rowActions;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.tags.form.AbstractFormTag#writeTagContent(org.springframework.web.servlet.tags.form.TagWriter)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected int writeTagContent(TagWriter tagWriter) throws JspException {
		
		Map<String, Object> mappedRow = (Map<String, Object>)this.getRow();
		
		for(Object columnValue : mappedRow.values()) {
			tagWriter.startTag("td");
			if (!StringUtils.isEmptyString(this.getCssClass())) {
				tagWriter.writeAttribute("class", this.getCssClass());
			}
			tagWriter.appendValue(String.valueOf(columnValue));
			tagWriter.endTag(true);
		}
		
		if (this.getRowActions() != null) {
			String actions = "";
			List<Command> rowActions = (List<Command>)this.getRowActions();
			tagWriter.startTag("td");
			if (!StringUtils.isEmptyString(this.getCssClass())) {
				tagWriter.writeAttribute("class", this.getCssClass());
			}
			for(Command command : rowActions) {
				actions += WebUtils.writeAction(command, (HttpServletRequest)pageContext.getRequest());
			}
			tagWriter.appendValue(String.valueOf(actions));
			tagWriter.endTag(true);
		}
		
		return EVAL_PAGE;
		
	}
	
	
}
