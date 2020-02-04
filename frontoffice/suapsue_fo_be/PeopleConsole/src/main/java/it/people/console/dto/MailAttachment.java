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
package it.people.console.dto;

import java.io.ByteArrayInputStream;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A. - Sede di Genova
 *
 */
public class MailAttachment implements IMailAttachment {

	private ByteArrayInputStream attachment;
	
	private String mimeType;
	
	private String name;
	
	public MailAttachment(final ByteArrayInputStream attachment, final String mimeType, final String name) {
		this.setAttachment(attachment);
		this.setMimeType(mimeType);
		this.setName(name);
	}

	/**
	 * @param attachment the attachment to set
	 */
	private void setAttachment(ByteArrayInputStream attachment) {
		this.attachment = attachment;
	}

	/**
	 * @param mimeType the mimeType to set
	 */
	private void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	/**
	 * @param name the name to set
	 */
	private void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the attachment
	 */
	public final ByteArrayInputStream getAttachment() {
		return attachment;
	}

	/**
	 * @return the mimeType
	 */
	public final String getMimeType() {
		return mimeType;
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

}
