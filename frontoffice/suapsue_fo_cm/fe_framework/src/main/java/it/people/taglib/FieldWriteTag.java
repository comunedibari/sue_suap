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

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.bean.WriteTag;
import org.apache.struts.util.ResponseUtils;

public class FieldWriteTag extends WriteTag {

    protected String m_default;

    public FieldWriteTag() {
	super();
	m_default = "..";
    }

    public void setDefault(String p_defaultValue) {
	this.m_default = p_defaultValue;
    }

    public String getDefault() {
	return this.m_default;
    }

    public int doStartTag() throws JspException {

	TagUtils tagUtils = TagUtils.getInstance();

	Object bean = null;
	if (ignore && tagUtils.lookup(pageContext, name, scope) == null)
	    return 0;

	Object value = tagUtils.lookup(pageContext, name, property, scope);
	if (value == null)
	    return 0;
	String output = formatValue(value);
	if (output.equals(""))
	    output = this.m_default;
	if (filter)
	    tagUtils.write(pageContext, ResponseUtils.filter(output));
	else
	    tagUtils.write(pageContext, output);
	return 0;
    }

    public void release() {
	super.release();
	m_default = null;
    }

}
