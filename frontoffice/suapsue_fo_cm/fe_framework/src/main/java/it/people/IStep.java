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
 * Created on 29-apr-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.people;

import it.people.core.PplACE;
import it.people.parser.exception.ParserException;
import it.people.process.AbstractPplProcess;
import it.people.util.frontend.WorkflowController;
import it.people.wrappers.IRequestWrapper;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * @author thweb4
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface IStep {

    public static final String READ_ON_LINE_HELP_FROM_DB = "db";

    public void service(AbstractPplProcess process, IRequestWrapper request)
	    throws IOException, ServletException;

    public void loopBack(AbstractPplProcess process, IRequestWrapper request,
	    String propertyName, int index) throws IOException,
	    ServletException;

    public void defineControl(AbstractPplProcess process,
	    IRequestWrapper request);

    /**
     * Informazione della classe (e/o riferimento) del dto associata allo step
     * 
     * @return
     */
    public String getDto();

    public void setDto(String p);

    public StepState getState();

    public void setState(StepState p_state);

    public String getJspPath();

    public void setJspPath(String m_jspPath);

    public String getHelpUrl();

    public void setHelpUrl(String m_helpUrl);

    public WorkflowController getAccessController();

    public void setAccessController(WorkflowController p_accessController);

    /*
     * public PplBean getProcessData() { return m_processData; }
     * 
     * public void setProcessData(PplBean p_processData) { m_processData =
     * p_processData; }
     */
    public boolean validate(AbstractPplProcess process,
	    ServletContext application, HttpServletRequest request,
	    IValidationErrors errors) throws ParserException;

    public boolean logicalValidate(AbstractPplProcess process,
	    IRequestWrapper request, IValidationErrors errors)
	    throws ParserException;

    public String getName();

    public void setName(String name);

    public String getId();

    public void setId(String id);

    public IView getParentView();

    public void setParentView(IView view);

    public String getFullIdentifier();

    /* Aggiunta per visualizzazione help contestuale dello step */

    public boolean isContextualHelpActive();

    public void setContextualHelpActive(boolean contextualHelpActive);

    public String getContextualHelpText();

    public void setContextualHelpText(String contextualHelpText);

    public void onLineHelpLoopBack(AbstractPplProcess process,
	    IRequestWrapper request);

    public void onLineHelpManagementLoopBack(AbstractPplProcess process,
	    IRequestWrapper request, String propertyName);

    /* Aggiunta per visualizzazione del pannello user functions */

    public boolean isUserPanelActive();

    public void setUserPanelActive(boolean userFunctionsPanelActive);

    public void setUserPanelViewActive(String userFunctionsPanelViewActive);

    public String getUserPanelViewActive();

    public void userPanelLoopBack(AbstractPplProcess process,
	    IRequestWrapper request, String parameter);

    // Mantenute per compatibilit�
    public PplACE[] getACL();

}
