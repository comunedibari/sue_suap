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
import java.sql.Blob;
/**
 *
 * @author Piergiorgio
 */
public class Tariffario implements java.io.Serializable {
public String cod_doc_onere;
public String cod_lang;
public String tip_doc;
public String nome_file;
public String des_doc_onere;
public Blob doc_blob;

    public String getCod_doc_onere() {
        return cod_doc_onere;
    }

    public void setCod_doc_onere(String cod_doc_onere) {
        this.cod_doc_onere = cod_doc_onere;
    }

    public String getCod_lang() {
        return cod_lang;
    }

    public void setCod_lang(String cod_lang) {
        this.cod_lang = cod_lang;
    }

    public String getDes_doc_onere() {
        return des_doc_onere;
    }

    public void setDes_doc_onere(String des_doc_onere) {
        this.des_doc_onere = des_doc_onere;
    }

    public Blob getDoc_blob() {
        return doc_blob;
    }

    public void setDoc_blob(Blob doc_blob) {
        this.doc_blob = doc_blob;
    }

    public String getNome_file() {
        return nome_file;
    }

    public void setNome_file(String nome_file) {
        this.nome_file = nome_file;
    }

    public String getTip_doc() {
        return tip_doc;
    }

    public void setTip_doc(String tip_doc) {
        this.tip_doc = tip_doc;
    }

}
