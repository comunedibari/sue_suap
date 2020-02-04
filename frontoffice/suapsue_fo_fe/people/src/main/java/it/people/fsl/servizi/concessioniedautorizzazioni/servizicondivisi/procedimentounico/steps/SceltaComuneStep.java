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

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;

import it.gruppoinit.commons.Utilities;
import it.people.IValidationErrors;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.dao.ProcedimentoUnicoDAO;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ComuneBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ProcedimentoUnicoException;
import it.people.parser.exception.ParserException;
import it.people.process.AbstractPplProcess;
import it.people.wrappers.IRequestWrapper;

public class SceltaComuneStep extends BaseStep {

    public void service(AbstractPplProcess process, IRequestWrapper request) {
        try {
            if (initialise(process,request)) {
                logger.debug("SceltaComuneStep - service method");
                checkRecoveryBookmark(process, request);
                ProcessData dataForm = (ProcessData) process.getData();
                resetError(dataForm);
                ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(db, language);
// PC - accesso comune
//              ArrayList listaComuni = (ArrayList) delegate.getListaComuni();
                ArrayList listaComuni = (ArrayList) delegate.getListaComuni(process.getCommune().getKey());
// PC - accesso comune
                request.setAttribute("listaComuni", listaComuni);
            } else {
                throw new ProcedimentoUnicoException("Sessione scaduta");
            }
            logger.debug("SceltaComuneStep - service method END");
        } catch (Exception e) {
            gestioneEccezioni(process, 1, e);
        }

    }

    public void loopBack(AbstractPplProcess process, IRequestWrapper request, String propertyName, int index) throws IOException, ServletException {
        logger.debug("SceltaComuneStep - loopBack method");
        logger.debug("SceltaComuneStep - loopBack method END");
    }

    public boolean logicalValidate(AbstractPplProcess process, IRequestWrapper request, IValidationErrors errors) throws ParserException {
        ProcessData data = (ProcessData) process.getData();
        try {
            ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(db, language);
            if (Utilities.NVL(data.getComuneSelezionato().getCodEnte(), null) == null) {
// PC - accesso comune
//              ArrayList listaComuni = (ArrayList) delegate.getListaComuni();
                ArrayList listaComuni = (ArrayList) delegate.getListaComuni(process.getCommune().getKey());
// PC - accesso comune
                request.setAttribute("listaComuni", listaComuni);
                errors.add("error.sceltaComune");
                return false;
            } else {
                ComuneBean comune = delegate.getDettaglioComune(data.getComuneSelezionato().getCodEnte());
                data.setComuneSelezionato(comune);
                if (comune != null) {
                	data.setIstatEnte(comune.getCodIstat());
                	data.setNomeEnte(comune.getDescrizione());
                }
                // request.setAttribute("forward", "true");
                return true;
            }
        } catch (Exception e) {
            errors.add("error.generic", "Errore interno");
            gestioneEccezioni(process, 1, e);
            data.setInternalError(true);
            return false;
        }
    }
}
