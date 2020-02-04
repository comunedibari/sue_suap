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
import java.util.ArrayList;
import java.util.List;

/**
 * @author riccardob
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DefinizioneCampoFormula implements Serializable {
	private String tipo ;
	private String label ;
	private String valoreDefault;
	private int parteIntera;
	private int parteDecimale;
	private String formula;
	private List<String> IDs;
	private List<String> descriptions;
	private boolean accettaValoreZero;
	
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		if (!(null==formula)){
			this.formula = formula;
		}else{
			this.formula="";
		}		
	}
	/**
	 * 
	 */
	public DefinizioneCampoFormula() {
		
		 tipo ="";
		 label ="";
		 valoreDefault="";
		 parteIntera=0;
		 parteDecimale=0;	
		 formula="";
		 IDs = new ArrayList<String>();
		 descriptions = new ArrayList<String>();
		 accettaValoreZero = true;
	}

	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public int getParteDecimale() {
		return parteDecimale;
	}
	public void setParteDecimale(int parteDecimale) {
		this.parteDecimale = parteDecimale;
	}
	public int getParteIntera() {
		return parteIntera;
	}
	public void setParteIntera(int parteIntera) {
		this.parteIntera = parteIntera;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getValoreDefault() {
		return valoreDefault;
	}
	public void setValoreDefault(String valoreDefault) {
		this.valoreDefault = valoreDefault;
	}
	public List<String> getIDs() {
		return IDs;
	}
	public void setIDs(List<String> iDs) {
		IDs = iDs;
	}
	public List<String> getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(List<String> descriptions) {
		this.descriptions = descriptions;
	}
	public final boolean isAccettaValoreZero() {
		return accettaValoreZero;
	}
	public final void setAccettaValoreZero(boolean accettaValoreZero) {
		this.accettaValoreZero = accettaValoreZero;
	}

}
