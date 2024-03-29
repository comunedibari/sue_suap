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
package it.people.dbm.model;

import java.io.InputStream;

/**
 *
 * @author Piergiorgio
 */
public class FileUploadModel implements java.io.Serializable {

    public String tipDoc;
    public String nomeFile;
    public Long length;
    public InputStream docBlob;
//    public String formNomeCampo;

    public InputStream getDocBlob() {
        return docBlob;
    }

    public void setDocBlob(InputStream docBlob) {
        this.docBlob = docBlob;
    }

//    public String getFormNomeCampo() {
//        return formNomeCampo;
//    }
//
//    public void setFormNomeCampo(String formNomeCampo) {
//        this.formNomeCampo = formNomeCampo;
//    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public String getNomeFile() {
        return nomeFile;
    }

    public void setNomeFile(String nomeFile) {
        this.nomeFile = nomeFile;
    }

    public String getTipDoc() {
        return tipDoc;
    }

    public void setTipDoc(String tipDoc) {
        this.tipDoc = tipDoc;
    }
}
