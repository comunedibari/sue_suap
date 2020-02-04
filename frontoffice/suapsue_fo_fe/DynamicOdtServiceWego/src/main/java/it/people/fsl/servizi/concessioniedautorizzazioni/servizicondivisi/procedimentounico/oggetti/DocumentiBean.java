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
package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti;

/**
 * @author massimof
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DocumentiBean extends BaseBean{
    private String codiceDoc;
    private String codiceRif;
    private String nomeFile;
    private String tipoDocumento;
    private String nomeRiferimento;
    /**
     * @return Returns the nomeRiferimento.
     */
    public String getNomeRiferimento() {
        return nomeRiferimento;
    }
    /**
     * @param nomeRiferimento The nomeRiferimento to set.
     */
    public void setNomeRiferimento(String nomeRiferimento) {
        this.nomeRiferimento = nomeRiferimento;
    }
    /**
     * @return Returns the nomeFile.
     */
    public String getNomeFile() {
        return nomeFile;
    }
    /**
     * @param nomeFile The nomeFile to set.
     */
    public void setNomeFile(String nomeFile) {
        this.nomeFile = nomeFile;
    }
    /**
     * @return Returns the tipoDocumento.
     */
    public String getTipoDocumento() {
        return tipoDocumento;
    }
    /**
     * @param tipoDocumento The tipoDocumento to set.
     */
    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
    
    /**
     * @return Returns the codiceDoc.
     */
    public String getCodiceDoc() {
        return codiceDoc;
    }
    /**
     * @param codiceDoc The codiceDoc to set.
     */
    public void setCodiceDoc(String codiceDoc) {
        this.codiceDoc = codiceDoc;
    }
    /**
     * @return Returns the codiceRif.
     */
    public String getCodiceRif() {
        return codiceRif;
    }
    /**
     * @param codiceRif The codiceRif to set.
     */
    public void setCodiceRif(String codiceRif) {
        this.codiceRif = codiceRif;
    }
}
