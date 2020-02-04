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

import it.people.process.sign.entity.SignedData;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.bean.WriteTag;
import org.apache.struts.util.ResponseUtils;

public class FieldHeaderTag extends WriteTag {

    private String m_propertyFile = "it.people.resources.TaglibMessages";
    private ResourceBundle bunlde;

    private String m_headerName;
    private String m_headerSign;
    private String m_footerName;
    private String m_footerSign;
    private String m_nl;

    protected String m_key;

    public void setKey(String p_key) {
	m_key = p_key;
    }

    public String getKey() {
	return m_key;
    }

    public FieldHeaderTag() {
	super();
	m_nl = "\n";
	try {
	    bunlde = PropertyResourceBundle.getBundle(m_propertyFile);
	    m_headerName = bunlde.getString("BEGINATTACHMENT");
	    m_footerName = bunlde.getString("ENDATTACHMENT");
	    m_headerSign = bunlde.getString("BEGINSIGN");
	    m_footerSign = bunlde.getString("ENDSIGN");
	} catch (Exception e) {
	    m_headerName = "--- BEGIN ATTACHMENT NAME ---";
	    m_footerName = "--- END ATTACHMENT NAME ---";
	    m_headerSign = "--- BEGIN SIGN ---";
	    m_footerSign = "--- END SIGN ---";
	}
    }

    public int doStartTag() throws JspException {

	TagUtils tagUtils = TagUtils.getInstance();
	Object bean = null;
	if (ignore && tagUtils.lookup(pageContext, name, scope) == null)
	    return 0;
	Object value = tagUtils.lookup(pageContext, name, property, scope);
	if (value == null)
	    return 0;

	// String output = formatValue(value);
	/**
	 * Dentro value ho un oggetto signed data
	 */

	SignedData sd = (SignedData) value;
	String output = "";
	String frendlyName = sd.getFriendlyName();
	String sign = sd.getSignedContent().getEncodedSing();
	if (m_key == null || m_key.equals("")
		|| sd.getKey().indexOf(m_key) != -1) {
	    output = m_headerName + m_nl + frendlyName + m_nl + m_footerName
		    + m_nl + m_headerSign + m_nl + sign + m_nl + m_footerSign
		    + m_nl;
	}

	if (filter)
	    tagUtils.write(pageContext, ResponseUtils.filter(output));
	else
	    tagUtils.write(pageContext, output);
	return 0;
    }

    public void release() {
	super.release();
	m_headerName = null;
	m_footerName = null;
	m_footerName = null;
	m_footerSign = null;
	m_nl = null;
	bunlde = null;
	m_key = null;
    }

}
