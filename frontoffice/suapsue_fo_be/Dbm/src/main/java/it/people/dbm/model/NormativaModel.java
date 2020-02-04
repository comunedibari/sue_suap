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
public class NormativaModel implements java.io.Serializable {
public String codRif;
public String titRif;
public TipoRifModel tipoRif;
public FileUploadModel fileUpload;
public String nomeRif;

    public String getNomeRif() {
        return nomeRif;
    }

    public void setNomeRif(String nomeRif) {
        this.nomeRif = nomeRif;
    }

    public TipoRifModel getTipoRif() {
        return tipoRif;
    }

    public void setTipoRif(TipoRifModel tipoRif) {
        this.tipoRif = tipoRif;
    }

    public String getTitRif() {
        return titRif;
    }

    public void setTitRif(String titRif) {
        this.titRif = titRif;
    }

    public String getCodRif() {
        return codRif;
    }

    public void setCodRif(String codRif) {
        this.codRif = codRif;
    }

    public FileUploadModel getFileUpload() {
        return fileUpload;
    }

    public void setFileUplad(FileUploadModel fileUpload) {
        this.fileUpload = fileUpload;
    }

}
