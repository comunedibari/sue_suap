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
 * Created on 24-feb-2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.people.process;

import org.apache.log4j.Logger;

import it.people.process.GenericProcess;
import it.people.vsl.PipelineHandler;

/**
 * @author Michele Fabbri - Cedaf s.r.l.
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class PipelineLessProcess extends GenericProcess {
    private static Logger log = Logger.getLogger(PipelineLessProcess.class);

    /*
     * Solo la pipeline � gestita correttamente, gli altri metodi di un
     * Process non sono al momento utilizzabili perch� manca l'infrastruttura
     * che li gestisca, una modifica del framework in questa direzione non e' al
     * momento ipotizzabile per mancanza di tempo. Il metodo � spostato nel
     * GenericProcess
     */

    // Non ridefinisco la PipelineImpl perch� riutilizzo quella esistente.

    /**
     * Ritorna un elenco vuoto di pipeline handler
     */
    public static PipelineHandler[] getPipelineHandlers() {
	log.debug("PipelineLessProcess.getPipelineHandlers() creata pipeline vuota");
	return new PipelineHandler[] {};
    }
}
