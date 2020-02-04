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
/*
 * Created on 18-apr-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.people.fsl.servizi.esempi.tutorial.impostazionestatoprocedimento.model;

import it.people.core.PeopleContext;
import it.people.process.AbstractPplProcess;
import it.people.process.data.AbstractData;
import it.people.util.XmlObjectWrapper;
import it.people.vsl.PipelineData;
import it.people.vsl.PipelineDataImpl;

/**
 * @author fabmi
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ProcessData extends AbstractData {
	String nome;
	String cognome;
	String rispostaWebService;
	
	public ProcessData() {
        super();
    }

    public boolean validate() {
    	return false;
    }
    
	protected void doDefineValidators() {}
	
	public void exportToPipeline(PipelineData pd) {
		// Il modo corretto per generare l'xml � attraverso l'uso di xmlbeans
		String message = "<test>\n"
			+ "\t<nome>" + this.nome + "</nome>\n"
			+ "\t<cognome>" + this.cognome + "</cognome>\n"
			+ "</test>\n";
		
        pd.setAttribute(
        		PipelineDataImpl.XML_PROCESSDATA_PARAMNAME,
                message);
	}
	
	public void initialize(PeopleContext context, AbstractPplProcess pplProcess) {
	}
	
	/**
	 * @return Returns the cognome.
	 */
	public String getCognome() {
		return cognome;
	}
	/**
	 * @param cognome The cognome to set.
	 */
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	/**
	 * @return Returns the nome.
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * @param nome The nome to set.
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	/**
	 * @return Returns the rispostaWebService.
	 */
	public String getRispostaWebService() {
		return rispostaWebService;
	}
	/**
	 * @param rispostaWebService The rispostaWebService to set.
	 */
	public void setRispostaWebService(String rispostaWebService) {
		this.rispostaWebService = rispostaWebService;
	}
}
