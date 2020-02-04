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
package it.people.util;

import it.people.process.common.entity.Attachment;
import it.people.process.common.entity.SignedAttachment;
import it.people.process.sign.entity.SignedInfo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Category;
import org.apache.xerces.utils.Base64;

/**
 * 
 * User: Luigi Corollo Date: 3-feb-2004 Time: 14.15.39 <br>
 * <br>
 * Classe d'utilit� per i signed attachment.
 */
public class SignedAttachmentUtils {

    // Log
    private static Category cat = Category
	    .getInstance(SignedAttachmentUtils.class.getName());

    /**
     * Controlla l'attachment passato, se e' un file firmato ritorna l'istanza
     * di un SignedAttachment.
     * 
     * @param p_attachment
     *            l'attachment da controllare
     * @return
     */
    public static Attachment returnSignedAttachment(Attachment attachment) {
	if (attachment instanceof SignedAttachment) {
	    return attachment;
	} else if ("".equals(attachment.getPath())
		|| "".equals(attachment.getName())) {
	    return attachment;
	} else {
	    // Controllo se e' un signed attachment
	    // try {
	    // File attachFile = new File(attachment.getPath());
	    //
	    // InputStream stream = new FileInputStream(attachFile);
	    // ByteArrayOutputStream boas = new ByteArrayOutputStream();
	    // int bytesRead = 0;
	    // byte[] buffer = new byte[8192];
	    // while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
	    // boas.write(buffer, 0, bytesRead);
	    // }
	    // stream.close();
	    //
	    // //Controllo se il file � firmato
	    // byte[] fileContent = boas.toByteArray();
	    // PKCS7Parser p7mParser = new PKCS7Parser(fileContent);
	    //
	    // if (p7mParser.isP7mFile()) {
	    // //E' firmato -> return new SignedAttachment
	    // p7mParser.decode(new SignedInfo());
	    // Attachment newAttachment = new SignedAttachment(
	    // attachment.getName(),
	    // attachFile,
	    // new String(Base64.encode((byte[]) p7mParser.getSigner().get(0)))
	    // );
	    //
	    // newAttachment.setData(attachment.getData());
	    // newAttachment.setDescrizione(attachment.getDescrizione());
	    // newAttachment.setFileLenght(attachment.getFileLenght());
	    //
	    // return newAttachment;
	    // }
	    // } catch (FileNotFoundException fnfe) {
	    // cat.error(fnfe);
	    // } catch (IOException ioe) {
	    // cat.error(ioe);
	    // }

	    // NON E' firmato -> return Attachment
	    return attachment;
	}
    }
}
