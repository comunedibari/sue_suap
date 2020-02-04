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
package it.people.process.sign.entity;

import it.people.util.MimeType;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.log4j.Category;

/**
 * 
 * User: sergio Date: Nov 28, 2003 Time: 7:01:06 PM <br>
 * <br>
 */
public class AttachmentSigningData extends SigningData {

    private Category cat = Category.getInstance(AttachmentSigningData.class
	    .getName());

    public AttachmentSigningData() {
	super();
    }

    public AttachmentSigningData(String key, String friendlyName,
	    String filePath) {
	super(key, friendlyName, filePath);
    }

    public byte[] getBytes() {
	String filePath = (String) super.getObject();

	File file = new File(filePath);
	try {
	    FileInputStream fis = new FileInputStream(file);
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();

	    byte[] output = new byte[4096];
	    int read = 0;

	    while ((read = fis.read(output, 0, output.length)) > 0) {
		baos.write(output, 0, read);
	    }
	    fis.close();
	    return baos.toByteArray();
	} catch (IOException ioEx) {
	    cat.error(ioEx);
	}
	return new byte[0];
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.people.process.sign.entity.SigningData#getBytes(it.people.util.MimeType
     * )
     */
    public byte[] getBytes(MimeType mimeType) {
	return this.getBytes();
    }

}
