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
 * ProcessData.java
 *
 * Created on 26 dicembre 2004, 14.37
 */

package it.people.fsl.servizi.esempi.tutorial.pagamentoAnonimo.model;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import it.people.core.PeopleContext;
import it.people.process.AbstractPplProcess;
import it.people.process.data.AbstractData;
import it.people.vsl.PipelineData;

/**
 *
 * @author zero
 */
public class ProcessData  extends AbstractData {
    private String email;
    private double importo;

    // Risultato Pagamento
	private double importoPagato;
	private double importoCommissioni;
	private boolean pagamentoEffettuato;

	/**
	 * @return Returns the importoCommissioni.
	 */
	public double getImportoCommissioni() {
		return importoCommissioni;
	}
	/**
	 * @param importoCommissioni The importoCommissioni to set.
	 */
	public void setImportoCommissioni(double importoCommissioni) {
		this.importoCommissioni = importoCommissioni;
	}
	/**
	 * @return Returns the importoPagato.
	 */
	public double getImportoPagato() {
		return importoPagato;
	}
	/**
	 * @param importoPagato The importoPagato to set.
	 */
	public void setImportoPagato(double importoPagato) {
		this.importoPagato = importoPagato;
	}
	/**
	 * @return Returns the pagamentoEffettuato.
	 */
	public boolean isPagamentoEffettuato() {
		return pagamentoEffettuato;
	}
	/**
	 * @param pagamentoEffettuato The pagamentoEffettuato to set.
	 */
	public void setPagamentoEffettuato(boolean pagamentoEffettuato) {
		this.pagamentoEffettuato = pagamentoEffettuato;
	}
	/* (non-Javadoc)
	 * @see it.people.process.data.AbstractData#doDefineValidators()
	 */
	protected void doDefineValidators() {
	}

	/* (non-Javadoc)
	 * @see it.people.process.PplData#initialize(it.people.core.PeopleContext, it.people.process.AbstractPplProcess)
	 */
	public void initialize(PeopleContext context, AbstractPplProcess pplProcess) {
		if (!context.getUser().isAnonymous())
			this.email = context.getUser().getEMail();
	}
	
	/* (non-Javadoc)
	 * @see it.people.process.PplData#exportToPipeline(it.people.vsl.PipelineData)
	 */
	public void exportToPipeline(PipelineData pd) {
	}

	/** Creates a new instance of ProcessData */
    public ProcessData() {
    	this.m_clazz = ProcessData.class;
    }    
    
    // ACCESSORS

	/**
	 * @return Returns the email.
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email The email to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * @return Returns the importo.
	 */
	public double getImporto() {
		return importo;
	}
	/**
	 * @param importo The importo to set.
	 */
	public void setImporto(double importo) {
		this.importo = importo;
	}
   
	/**
	 * @return Returns the importoString.
	 */
	public String getImportoString() {
        return NumberFormat.getNumberInstance(Locale.ITALY).format(this.importo);
	}
	/**
	 * @param importoString The importoString to set.
	 */
	public void setImportoString(String importoString) {
		try {
			Number number = NumberFormat.getNumberInstance(Locale.ITALY).parse(importoString);
			this.importo = number.doubleValue();
		} catch(ParseException ex) {
			this.importo = 0;
		}
	}
}
