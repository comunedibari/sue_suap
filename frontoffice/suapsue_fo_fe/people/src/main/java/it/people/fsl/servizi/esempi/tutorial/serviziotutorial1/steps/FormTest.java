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
 * formTest.java
 *
 * Created on 26 dicembre 2004, 14.46
 */

package it.people.fsl.servizi.esempi.tutorial.serviziotutorial1.steps;

import it.people.IValidationErrors;
import it.people.Step;
import it.people.core.PplUserData;
import it.people.fsl.servizi.esempi.tutorial.serviziotutorial1.model.Donna;
import it.people.fsl.servizi.esempi.tutorial.serviziotutorial1.model.ProcessData;
import it.people.fsl.servizi.esempi.tutorial.serviziotutorial1.model.Uomo;
import it.people.fsl.servizi.oggetticondivisi.PersonaFisica;
import it.people.fsl.servizi.oggetticondivisi.Titolare;
import it.people.fsl.servizi.oggetticondivisi.personagiuridica.PersonaGiuridica;
import it.people.fsl.servizi.oggetticondivisi.tipibase.Data;
import it.people.logger.Logger;
import it.people.parser.exception.ParserException;
import it.people.process.AbstractPplProcess;
import it.people.wrappers.IRequestWrapper;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;

/**
 *
 * @author zero
 */
public class FormTest extends Step {
    // L'utilizzo del logger senza il codice comune � deprecato si veda la guida allo sviluppo di un servizio
	// protected Logger logger = Logger.getLogger("it.people.fsl.servizi.esempi.tutorial.serviziotutorial1"); 
    
    /** 
     * Valida la consistenza LOGICA dei parametri inseriti. La validazione
     * Stretta del formato dei parametri � definibile tramite il validation.xml
     * di struts (si faccia riferimento alla Guida allo sviluppo di un servizio).
     */
    public boolean logicalValidate(AbstractPplProcess process,
            IRequestWrapper request,
            IValidationErrors errors) throws ParserException {
            
        boolean correct = true;

        //Per utilizzarlo occorre castarlo al nostro ProcessData.
        ProcessData tutorialData = (ProcessData)process.getData();
        
        tutorialData.setOggettoPratica("Oggetto TEST");
        tutorialData.setDescrizionePratica("Descrizione Test");   
        Titolare titTest= new Titolare();
        PersonaFisica pf = new PersonaFisica();
        pf.setCodiceFiscale("clnmrk77r09e256s");
        pf.setNome("Mirko");
        pf.setCognome("Calandrini");
        titTest.setPersonaFisica(pf);
        tutorialData.addAltriDichiaranti(titTest);
        PersonaGiuridica pg = new PersonaGiuridica();
        pg.setPartitaIVA("00000000000");
        pg.setDenominazione("INIT");
        Titolare tit2 = new Titolare();
        tit2.setPersonaGiuridica(pg);
        tutorialData.addAltriDichiaranti(tit2);
        
        
        int eta = tutorialData.getEta();
        int annoNascita = tutorialData.getAnno();             
        int annoCorrente = (new GregorianCalendar()).get(Calendar.YEAR);
        
        int etaMillesimo = annoCorrente - annoNascita;         
        if(eta != etaMillesimo && eta != (etaMillesimo - 1)) {
             errors.add("error.etaincorretta");
             correct = false;
        }        
        
        // Modo corretto di invocazione del log anche per il Multiente 
        process.debug("Invocato il validator della FormTest.");
        
        // Modo alternativo di invocazione del log da utilizzare
        // tutte le volte che non � disponibile AbstractPplProcess
        // o PaymentProcess
        Logger logger = Logger.getLogger("it.people.fsl.servizi.esempi.tutorial.serviziotutorial1", process.getCommune());
        logger.debug("Invocato il log con l'istanziazione diretta del logger.");
     
        return correct;
    }   
    
    
	/* (non-Javadoc)
	 * @see it.people.IStep#service(it.people.process.AbstractPplProcess, it.people.wrappers.IRequestWrapper)
	 */
	public void service(AbstractPplProcess process, IRequestWrapper request)
			throws IOException, ServletException {	
	    
	    String dateString = ((ProcessData)process.getData()).getIdentificatoreUnivoco().getDataRegistrazione().toString(); 
	    process.debug("Data di registrazione: " + dateString);
	    process.debug("FormTest - invocato service(AbstractPplProcess process, IRequestWrapper request)");
	    
	    ProcessData processData = (ProcessData) process.getData();
	    // Esempio di salvataggio e ripristino di collezioni di 
	    // classi derivate, se i dati sono gi� stati caricati
	    // non sono sovrascritti.
	    if (processData.getFamiliareCollection().isEmpty())
	        this.initFamiliari(processData);
	    

	    // Prepopolamento dei campi per l'utente non anonimo	    
	    if (!request.getPplUser().isAnonymous()) {
		    PplUserData userData = request.getPplUser().getUserData();
		    	    
		    if (isEmpty(processData.getNome()))
		        processData.setNome(userData.getNome());
		    
		    if (isEmpty(processData.getCognome()))
		        processData.setCognome(userData.getCognome());
		    
		    try {
		        Data dataNascita = new Data(userData.getDataNascita());	        
		        int annoDiNascita = Integer.parseInt(dataNascita.getAnno());
		        
		        if (processData.getAnno() == 0)
		            processData.setAnno(annoDiNascita);
		        
		        if (processData.getEta() == 0) {
		            int annoCorrente = (new GregorianCalendar()).get(Calendar.YEAR);
		            processData.setEta(annoCorrente - annoDiNascita);	            
		        }	        
		        
		    } catch(ParseException pEx) {}	    
	    }
	}

    protected void initFamiliari(ProcessData process) {
    	// utilizzato per il test salvataggio/ripristino collezione 
    	// di classi derivate
    	
    	Uomo familiare1 = new Uomo();
    	familiare1.setAnno(1950);
    	familiare1.setNome("Mario");
    	familiare1.setCognome("Rossi");
    	familiare1.setEta(47);
    	familiare1.setMiliteEsente(Boolean.TRUE);
    	
    	process.addFamiliare(familiare1);

    	Donna familiare2 = new Donna();
    	familiare2.setAnno(1960);
    	familiare2.setNome("Luisa");
    	familiare2.setCognome("Rossi");
    	familiare2.setEta(47);
    	familiare2.setInGravidanza(Boolean.FALSE);
    	
    	process.addFamiliare(familiare2);    	
    }
	
	
	protected boolean isEmpty(String value) {
	    return (value == null || value == "");
	}
	
    public void defineControl(AbstractPplProcess process, IRequestWrapper request) {
        process.debug("FormTest - invocato defineControl(AbstractPplProcess process, IRequestWrapper request)");
    }
}
    
