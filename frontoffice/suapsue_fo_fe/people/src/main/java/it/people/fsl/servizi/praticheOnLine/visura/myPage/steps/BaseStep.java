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
package it.people.fsl.servizi.praticheOnLine.visura.myPage.steps;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.people.IValidationErrors;
import it.people.Step;
import it.people.fsl.servizi.praticheOnLine.visura.myPage.model.ProcessData;
import it.people.parser.exception.ParserException;
import it.people.process.AbstractPplProcess;
import it.people.util.ServiceParameters;
import it.people.wrappers.IRequestWrapper;

public class BaseStep extends Step {

	public final Log logger = LogFactory.getLog(this.getClass());
	protected HttpSession session;
	
	
	
    public BaseStep() {
    	super();
    }
    
    
    protected boolean initialise(IRequestWrapper request) {
    	if (request==null || request.getUnwrappedRequest()==null  || request.getUnwrappedRequest().getSession()==null) {
    		return false;
    	} else {
    		return true;
    	}
    }
    
    protected void gestioneEccezioni(AbstractPplProcess process, int codiceErrore, Exception e) {

    	ProcessData dataForm = (ProcessData) process.getData();
    	// ...TODO
    }
    
    public boolean logicalValidate(AbstractPplProcess process, IRequestWrapper request, IValidationErrors errors) throws ParserException {
        return super.logicalValidate(process, request, errors);
    }
	
    public void loopBack(AbstractPplProcess process, IRequestWrapper request, String propertyName, int index) throws IOException, ServletException {
        logger.debug("loopBack BaseStep.java");
        logger.debug("propertyName=" + propertyName);
    }
    
    protected void showJsp(AbstractPplProcess process, String path, boolean completePath) {
    	logger.debug("JSP --> "+process.getView().getCurrentActivity().getCurrentStep().getJspPath());
    	String htmlPath = "/servizi/praticheOnLine/visura/myPage/view/default/html/";
        process.getView().getCurrentActivity().getCurrentStep().setJspPath((completePath ? "" : htmlPath)+ path);
        logger.debug("JSP --> "+process.getView().getCurrentActivity().getCurrentStep().getJspPath());
        logger.debug("you are going here --> "+ process.getView().getCurrentActivity().getCurrentStep().getJspPath());
    }
    
    protected boolean goToStep(AbstractPplProcess process, IRequestWrapper request, int offset) {
        logger.debug("Inside goToStep with offset " + offset);
        int index = getCurrentStepIndex(process);
        logger.debug("Current index is " + index);
        logger.debug("Next index is " + (index + offset));
        setStep(process, (index + offset));

        try {
            process.getView().getCurrentActivity().getCurrentStep().service(process, request);
        } catch (IOException e) {
            logger.error(e);
            return false;
        } catch (ServletException ex) {
            // 
            logger.error("", ex);
            return false;
        }
        return true;
    }
    
    protected int getCurrentStepIndex(AbstractPplProcess process) {
        return process.getView().getCurrentActivity().getCurrentStepIndex();
    }
    
    public void setStep(AbstractPplProcess pplProcess, int index) {
        pplProcess.getView().getCurrentActivity().setCurrentStepIndex(index);
    }
    
}
