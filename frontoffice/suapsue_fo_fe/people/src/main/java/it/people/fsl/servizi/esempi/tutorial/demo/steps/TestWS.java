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
 * Created on 31-mag-2005
 *
 */
package it.people.fsl.servizi.esempi.tutorial.demo.steps;

import it.people.Step;
import it.people.core.exception.ServiceException;
import it.people.fsl.servizi.esempi.tutorial.demo.model.ProcessData;
import it.people.process.AbstractPplProcess;
import it.people.util.MessageBundleHelper;
import it.people.util.ServiceParameters;
import it.people.vsl.exception.SendException;
import it.people.wrappers.IRequestWrapper;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

import javax.servlet.ServletException;

/**
 * @author fabmi
 *
 */
public class TestWS extends Step {

	/* (non-Javadoc)
	 * @see it.people.IStep#service(it.people.process.AbstractPplProcess, it.people.wrappers.IRequestWrapper)
	 */
	public void service(AbstractPplProcess process, IRequestWrapper request)
			throws IOException, ServletException 
	{
		process.debug("Test di invocazione web-service");
		
	    ServiceParameters spar = (ServiceParameters) request.getSessionAttribute("serviceParameters");
	    String xmlPath = spar.get("xmlPath");
		
		try	{
		    String xmlMessage = readXml(xmlPath);		    
			String risposta = process.callService("BACKEND_TEST", xmlMessage);

			ProcessData data = (ProcessData) process.getData(); 
						
			data.setRispostaWebService(risposta);
			process.debug("Invocazione BACKEND_TEST completata");			
			//throw new ServiceException("prova di registrazione errore"); 
	    } 
		catch (ServiceException svEx) {
	        setError(process, "error.wsError", svEx);
	    } catch (SendException snEx) {
	        setError(process, "error.wsError", snEx);
	    } catch (FileNotFoundException fnfEx) {
	        setError(process, "error.fileNotFound", fnfEx, new String[] {xmlPath});
	    } catch (IOException ioEx) {
	        setError(process, "error.io", ioEx);
	    } catch (Exception ex) {
	        setError(process, "error.genericError", ex);
	    }
	}
	
	protected void setError(AbstractPplProcess process, String errorKey, Throwable ex) {
    	process.cleanErrors();
        process.getServiceError().add(errorKey);
        process.error(ex.getMessage());	    
	}

	protected void setError(AbstractPplProcess process, String errorKey, Throwable ex, String[] params) {
    	process.cleanErrors();
    	String errorMessage = MessageBundleHelper.message(errorKey, params, process.getProcessName(), process.getCommune().getKey(), Locale.getDefault());
        process.getServiceError().add(errorMessage);
        process.error(ex.getMessage());	    
	}
	
	protected String readXml(String filePath) 
	throws FileNotFoundException, IOException {
	    String xmlMessage = "";
	    BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
        String line = "";
        while((line = bf.readLine()) != null) {
            xmlMessage += line;
        }
	    return xmlMessage;
	}
}
