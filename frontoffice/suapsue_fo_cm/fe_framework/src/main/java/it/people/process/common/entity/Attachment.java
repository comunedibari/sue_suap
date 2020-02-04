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
package it.people.process.common.entity;

import java.io.File;

/**
 * Created by IntelliJ IDEA. User: sergio Date: Oct 11, 2003 Time: 4:30:47 PM To
 * change this template use Options | File Templates.
 */
public class Attachment implements java.io.Serializable {

    private static final long serialVersionUID = 8354381888780236529L;

    private String path;
    private String name;
    private String descrizione;
    private String data;
    private int fileLenght;

    public Attachment() {
	this.path = null;
	this.name = null;
    }

    public Attachment(String name, String path) {
	this.path = path;
	this.name = name;
    }

    public Attachment(String name, File file) {
	this.name = name;
	this.path = file.getAbsolutePath();
    }

    public String getPath() {
	return this.path;
    }

    public void setPath(String path) {
	this.path = path;
    }

    public String getName() {
	return this.name;
    }

    public void setName(String name) {
	this.name = name;
    }

    /**
     * @return Returns the descrizione.
     */
    public String getDescrizione() {
	return descrizione;
    }

    /**
     * @param descrizione
     *            The descrizione to set.
     */
    public void setDescrizione(String descrizione) {
	this.descrizione = descrizione;
    }

    public String getData() {
	return data;
    }

    public void setData(String data) {
	this.data = data;
    }

    /**
     * @return Ritorna la dimensione originale del documento prima della sua
     *         codifica in base 64
     */
    public int getFileLenght() {
	return this.fileLenght;
    }

    public void setFileLenght(int originalLenght) {
	this.fileLenght = originalLenght;
    }

    public enum Types {

	html("", ""), pdf("", "");

	private String type;

	private String extension;

	private Types(final String type, final String extension) {
	    this.setType(type);
	    this.setExtension(extension);
	}

	/**
	 * @param type
	 *            the type to set
	 */
	private void setType(String type) {
	    this.type = type;
	}

	/**
	 * @param extension
	 *            the extension to set
	 */
	private void setExtension(String extension) {
	    this.extension = extension;
	}

	/**
	 * @return the type
	 */
	public final String getType() {
	    return this.type;
	}

	/**
	 * @return the extension
	 */
	public final String getExtension() {
	    return this.extension;
	}

    }

}
