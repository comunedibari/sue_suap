/**
 * Copyright (c) 2011, Regione Emilia-Romagna, Italy
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by
 * the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 *
 * For convenience a plain text copy of the English version of the Licence can
 * be found in the file LICENCE.txt in the top-level directory of this software
 * distribution.
 *
 * You may obtain a copy of the Licence in any of 22 European Languages at:
 *
 * http://www.osor.eu/eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * Licence for the specific language governing permissions and limitations under
 * the Licence.
*
 */
package it.people.dbm.model;

import java.util.List;

/**
 *
 * @author Piergiorgio
 */
public class TemplateModel implements java.io.Serializable {

    private String codSport;
    private String codCom;
    private String tipo;
    private String codProc;
    private String codServizio;
    private List<TemplateModelFile> listaFile;
    private byte[] template;

    public String getCodCom() {
        return codCom;
    }

    public List<TemplateModelFile> getListaFile() {
        return listaFile;
    }

    public void setListaFile(List<TemplateModelFile> listaFile) {
        this.listaFile = listaFile;
    }

    public void setCodCom(String codCom) {
        this.codCom = codCom;
    }

    public String getCodProc() {
        return codProc;
    }

    public void setCodProc(String codProc) {
        this.codProc = codProc;
    }

    public String getCodServizio() {
        return codServizio;
    }

    public void setCodServizio(String codServizio) {
        this.codServizio = codServizio;
    }

    public String getCodSport() {
        return codSport;
    }

    public void setCodSport(String codSport) {
        this.codSport = codSport;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
        this.setCodProc(tipo.replaceAll("(.*?)(<procedimento>)(.*?)(</procedimento>)(.*?$)", "$3"));
        this.setCodServizio(tipo.replaceAll("(.*?)(<bookmark>)(.*?)(</bookmark>)(.*?$)", "$3"));

    }
}
