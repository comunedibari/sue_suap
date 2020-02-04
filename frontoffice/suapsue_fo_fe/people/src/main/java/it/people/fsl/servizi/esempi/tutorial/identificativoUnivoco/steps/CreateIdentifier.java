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
 * Created on 27-apr-2006
 *
 */
package it.people.fsl.servizi.esempi.tutorial.identificativoUnivoco.steps;

import it.people.Step;
import it.people.fsl.servizi.esempi.tutorial.identificativoUnivoco.model.ProcessData;
import it.people.process.AbstractPplProcess;
import it.people.process.data.AbstractData;
import it.people.util.IDGenerator;
import it.people.util.IdentificatoreUnivoco;
import it.people.wrappers.IRequestWrapper;

import java.io.IOException;

import javax.servlet.ServletException;

/**
 * @author Fabbri Michele - Cedaf s.r.l.
 *
 */
public class CreateIdentifier extends Step {
    
    public void service( AbstractPplProcess process, IRequestWrapper request)
    throws IOException, ServletException {       
	}
		

    public void loopBack(AbstractPplProcess process, IRequestWrapper request,
            String propertyName, int index) throws IOException,
            ServletException {
        
        ProcessData processData = ((ProcessData) process.getData());   
        
        // crea un identificativo temporaneo utilizzando 
        // direttamente il metodo previsto dal framework 
        // versioni del framework maggiori o uguali a 2.0.1
        processData.setIdentificativoOperazioneTemporaneo(
                process.getNewIdentificativoOperazione());
        
        // crea un identificativo temporaneo con un meccanismo a livello 
        // pi� basso � da utilizzare solo con la versione 2.0.0
        processData.setIdentificativoOperazioneTemporaneoOldStyle(
                generaIdentificativoOperazione(process));
    }
    
    /**
     * @deprecated questo meccanismo di generazione dell'identificativo temporaneo � sostituito da AbstractPplProcess.getNewIdentificativoOperazione();
     * @param process
     * @return
     */
	protected String generaIdentificativoOperazione(AbstractPplProcess process)	{	   
	    AbstractData processData = (AbstractData)process.getData();
	    IdentificatoreUnivoco identificatoreUnivoco = processData.getIdentificatoreUnivoco();	    	    
	    String userIdPrefix = identificatoreUnivoco.getCodiceSistema().getCodiceIdentificativoOperazione().substring(0, 16);
		return IDGenerator.generateID(userIdPrefix + "-" + process.getCommune().getAooPrefix()); 
	}
	
}
