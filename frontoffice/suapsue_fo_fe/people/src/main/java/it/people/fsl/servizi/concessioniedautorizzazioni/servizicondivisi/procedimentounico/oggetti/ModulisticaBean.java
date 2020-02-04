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

import java.io.Serializable;

public class ModulisticaBean implements Serializable {

	private static final long serialVersionUID = 1084333224886501227L;
	
	private String codiceDoc;
	private String tip_doc;
	private String nomeFile;
	private String titolo;
	private byte[] content;
	
	public ModulisticaBean(){
		this.codiceDoc="";
		this.tip_doc="";
		this.nomeFile="";
		this.titolo="";
		this.content=new byte[0];
	}
	
	public String getCodiceDoc() {
		return codiceDoc;
	}
	public void setCodiceDoc(String codiceDoc) {
		this.codiceDoc = codiceDoc;
	}
	public String getTip_doc() {
		return tip_doc;
	}
	public void setTip_doc(String tip_doc) {
		this.tip_doc = tip_doc;
	}
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	
    public void addContent(byte content) {

        int size = this.content.length + 1;
        byte[] temp = new byte[size];

        try {
            for (int i = 0; i < this.content.length; i++) {
                temp[i] = this.content[i];
            }
            temp[size - 1] = content;
            this.content = temp;
            // opsVec=vec;
        } catch (Exception e) {

        }
    }
}
