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
package it.people.console.security;

import it.people.console.utils.StringUtils;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 16/gen/2011 17.59.59
 *
 */
public class LinkCommand extends AbstractCommand {

	/**
	 * <p>Add new link type command.
	 * <p>If <code>src</code> is not null or empty then a image is rendered as link, 
	 * otherwise the <code>hrefTitle</code> is rendered.
	 * 
	 * @param id
	 * @param href
	 * @param hrefTitle
	 */
	public LinkCommand(final String id, final String href, final String hrefTitle, final String src, 
			final String disabledSrc, final CommandActions commandAction) {
		this.setId(id);
		this.setHref(StringUtils.nullToEmpty(href));
		this.setSrc(StringUtils.nullToEmpty(src));
		this.setDisabledStateSrc(StringUtils.nullToEmpty(disabledSrc));
		this.setHrefTitle(hrefTitle);
		this.setType(Types.link);
		this.setCommandAction(commandAction);
	}
	
}
