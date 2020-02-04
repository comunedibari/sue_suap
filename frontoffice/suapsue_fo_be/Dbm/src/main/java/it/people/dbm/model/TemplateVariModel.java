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

/**
 *
 * @author Piergiorgio
 */
public class TemplateVariModel implements java.io.Serializable {
public String nomeTemplate;
public String codLang;
public String nomeFile;
public String codSport;
public String tipoFile;
public byte[] templateVariRisorse;

    public String getCodLang() {
        return codLang;
    }

    public void setCodLang(String codLang) {
        this.codLang = codLang;
    }

    public String getCodSport() {
        return codSport;
    }

    public void setCodSport(String codSport) {
        this.codSport = codSport;
    }

    public String getNomeFile() {
        return nomeFile;
    }

    public void setNomeFile(String nomeFile) {
        this.nomeFile = nomeFile;
    }

    public String getNomeTemplate() {
        return nomeTemplate;
    }

    public void setNomeTemplate(String nomeTemplate) {
        this.nomeTemplate = nomeTemplate;
    }

    public byte[] getTemplateVariRisorse() {
        return templateVariRisorse;
    }

    public void setTemplateVariRisorse(byte[] templateVariRisorse) {
        this.templateVariRisorse = templateVariRisorse;
    }

    public String getTipoFile() {
        return tipoFile;
    }

    public void setTipoFile(String tipoFile) {
        this.tipoFile = tipoFile;
    }

}
