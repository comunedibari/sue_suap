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
package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.steps;

import it.people.ActivityState;
import it.people.IValidationErrors;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.dao.ProcedimentoUnicoDAO;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ProcedimentoUnicoException;
import it.people.parser.exception.ParserException;
import it.people.process.AbstractPplProcess;
import it.people.process.SurfDirection;
import it.people.wrappers.IRequestWrapper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.servlet.ServletException;

/**
 * Gestisce l'upload degli allegati 
 *
 * @author InIT http://www.gruppoinit.it
 *
 * 16-giu-2006
 */
public class AllegatiStep extends BaseStep {
    /*
     * (non-Javadoc)
     * 
     * @see it.people.IStep#loopBack(it.people.process.AbstractPplProcess,
     *      it.people.wrappers.IRequestWrapper, java.lang.String, int)
     */
    public boolean logicalValidate(AbstractPplProcess process, IRequestWrapper request, IValidationErrors errors) throws ParserException {
        logger.info("...: : : ENTERING VALIDATION : : :...");
        
        ProcessData dataForm = (ProcessData) process.getData();
        //rende attiva l'Activity di firma o di pagamento
        if(dataForm.getDatiTemporanei().isAttivaPagamento())
            process.getView().getActivityById(Integer.toString(2)).setState(ActivityState.ACTIVE);  
        else
            process.getView().getActivityById(Integer.toString(3)).setState(ActivityState.ACTIVE);  
        return true;
    }

    public void service(AbstractPplProcess process, IRequestWrapper request) throws IOException, ServletException {
        // TODO Problemi con la gestione del tasto back
        //	    fixHistory(process);
        if (initialise(request)) {
            String operation = request.getParameter("operation");
            logStep(process);
            // La pagina � stata chiamata dallo step precedente o da quello successivo
            if(operation == null){
                if (saveHistory(process, request)){
                    return;
                }
            }
            else{
                if(getSurfDirection(process)==SurfDirection.forward){
                    removeLastHistory(request, getCurrentActivityIndex(process), getCurrentStepIndex(process));
                    if (saveHistory(process, request)){
                        return;
                    }
                }
                else{
                    removeLastHistory(request, getCurrentActivityIndex(process), getCurrentStepIndex(process));
                    if (saveHistory(process, request, true)){
                        return;
                    }
                }
            }
            /*
             * Modifica Init 28/03/2007 1.3
             * Se il servizio � di livello 2 non viene visualizzata la pagina di upload degli allegati
             */
            if(session.getAttribute("stop") != null){
                process.getView().getActivityById("3").setState(it.people.ActivityState.ACTIVE);
                setStep(process, request, 3, 0);
            }
        } else {
            //gestione errori
            logger.error("Sessione Scaduta");
            gestioneEccezioni(process, new ProcedimentoUnicoException("sessione scaduta"));
        }
    }

    public void loopBack(AbstractPplProcess process, IRequestWrapper request, String propertyName, int index) throws IOException, ServletException {
        if (initialise(request)) {
            if(propertyName == null)
                propertyName = request.getParameter("pagina");
            logger.debug("loopBack ModelloUnicoStep");
            logger.debug("propertyName=" + propertyName);
            logStep(process);
            try {
                //apro il delegate ProcedimentoUnicoDAO
                ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(db, language);
                //visualizza la lista degli href
                if (propertyName.equalsIgnoreCase("allegati.jsp")) {
                    enableBottomNavigationBar(process, true);
                    //enableBottomSaveBar(process, true);
                    logger.debug(process.getView().getCurrentActivity().getCurrentStep().getJspPath());
                    showJsp(process, propertyName, false);

                } else if (propertyName.equalsIgnoreCase("documenti.jsp")) {
                    String tipoDoc = request.getParameter("tipoDoc");
                    enableBottomNavigationBar(process, false);
                    //enableBottomSaveBar(process, false);
                    if (tipoDoc.equalsIgnoreCase("allegati")) {
                        String codDoc = request.getParameter("codDoc");
                        request.setAttribute("listaDocumenti", delegate.getDocumentiAllegati(codDoc));
                        request.setAttribute("AL", "true");
                        showJsp(process, propertyName, false);
                    } else if (tipoDoc.equalsIgnoreCase("normative")) {
                        logger.debug(process.getView().getCurrentActivity().getCurrentStep().getJspPath());
                        String codRif = request.getParameter("codRif");
                        request.setAttribute("listaDocumenti", delegate.getDocumentiNormative(codRif));
                        request.setAttribute("AL", "true");
                        showJsp(process, propertyName, false);
                    }
                }
            } catch (SQLException e) {
                logger.error(e);
                gestioneEccezioni(process, e);
            } catch (Exception e) {
                logger.error(e);
                gestioneEccezioni(process, e);
            }
        }
    }
}
