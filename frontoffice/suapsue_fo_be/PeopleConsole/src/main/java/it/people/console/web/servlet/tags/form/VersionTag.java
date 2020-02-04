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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import it.people.console.config.ConsoleVersion;
import it.people.console.utils.StringUtils;
import it.people.console.web.servlet.tags.TagsConstants;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 04/gen/2011 09.07.25
 *
 */
public class VersionTag extends TagSupport {
	
	private static final long serialVersionUID = 2941106859093907761L;

	private String showBuildNumber;
	
	private String numbersSeparator;
	
	private String suffix;
	
	/**
	 * @return the showBuildNumber
	 */
	public final String getShowBuildNumber() {
		return showBuildNumber;
	}

	/**
	 * @param showBuildNumber the showBuildNumber to set
	 */
	public final void setShowBuildNumber(String showBuildNumber) {
		this.showBuildNumber = showBuildNumber;
	}

	/**
	 * @return the numbersSeparator
	 */
	public final String getNumbersSeparator() {
		return (this.numbersSeparator == null) ? TagsConstants.DEFAULT_VERSION_NUMBER_SEPARATOR : this.numbersSeparator;
	}

	/**
	 * @param numbersSeparator the numbersSeparator to set
	 */
	public final void setNumbersSeparator(String numbersSeparator) {
		this.numbersSeparator = numbersSeparator;
	}

	/**
	 * @return the suffix
	 */
	public final String getSuffix() {
		return (this.suffix != null) ? " " + this.suffix : "";
	}

	/**
	 * @param suffix the suffix to set
	 */
	public final void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public int doStartTag() throws JspException {
		
		JspWriter writer = pageContext.getOut();
		ConsoleVersion consoleVersion = (ConsoleVersion)pageContext.getServletContext().getAttribute(ConsoleVersion.VERSION_KEY);
		try {
			String version = consoleVersion.getMajorNumber() + 
				this.getNumbersSeparator() + splitMinorNumber(consoleVersion.getMinorNumber()) 
				+ this.getSuffix();
			if (Boolean.parseBoolean(StringUtils.nullToEmpty(this.getShowBuildNumber()))) {
				version += " (build " + consoleVersion.getBuildNumber() + ")";
			}
			writer.write(version);
		} catch (IOException e) {
			throw new JspException(e);
		}
		
		return SKIP_BODY;
		
	}
	
	private String splitMinorNumber(String minorNumber) {
		
		String result = "";
		
		for(int index = 0; index < minorNumber.length(); index++) {
			if ((index > 0) && (index % 2) == 0) {
				result += "." + minorNumber.charAt(index);
			} else {
				result += minorNumber.charAt(index);
			}
		}
		
		return result;
		
	}
	
}
