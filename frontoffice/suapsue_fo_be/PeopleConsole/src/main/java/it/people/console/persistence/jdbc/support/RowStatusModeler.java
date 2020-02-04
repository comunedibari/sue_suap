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
package it.people.console.persistence.jdbc.support;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 09/feb/2011 11.58.33
 *
 */
public class RowStatusModeler {

	private String imageFile;
	
	private String alt;
	
	private String title;
	
	public RowStatusModeler(final String imageFile, final String alt, final String title) {
		this.setImageFile(imageFile);
		this.setAlt(alt);
		this.setTitle(title);
	}

	/**
	 * @return the imageFile
	 */
	public final String getImageFile() {
		return imageFile;
	}

	/**
	 * @return the alt
	 */
	public final String getAlt() {
		return alt;
	}

	/**
	 * @return the title
	 */
	public final String getTitle() {
		return title;
	}

	/**
	 * @param imageFile the imageFile to set
	 */
	private void setImageFile(String imageFile) {
		this.imageFile = imageFile;
	}

	/**
	 * @param alt the alt to set
	 */
	private void setAlt(String alt) {
		this.alt = alt;
	}

	/**
	 * @param title the title to set
	 */
	private void setTitle(String title) {
		this.title = title;
	}
	
}
