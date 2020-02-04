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
 * http://www.osor.eu/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
**/
package it.people.dbm.utility;

import org.apache.commons.fileupload.FileItem;

import it.people.dbm.model.FileUploadModel;


/**
 *
 * @author Piergiorgio
 */
public class UploadFile {

    public static FileUploadModel upload(FileItem parameter) throws Exception {
        FileUploadModel fileUpload = null;
        try {
            fileUpload = new FileUploadModel();
            
//            fileUpload.setFormNomeCampo(parameter.getFieldName());
            fileUpload.setNomeFile(parameter.getName());
            fileUpload.setTipDoc(parameter.getContentType().replaceAll("\"", ""));
            fileUpload.setLength(parameter.getSize());
            fileUpload.setDocBlob(parameter.getInputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileUpload;
    }
}
