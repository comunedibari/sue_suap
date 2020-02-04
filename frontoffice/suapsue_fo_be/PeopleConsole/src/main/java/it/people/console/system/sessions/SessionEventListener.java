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
package it.people.console.system.sessions;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import it.people.console.system.MessageSourceAwareClass;
import it.people.console.utils.Constants;
import it.people.console.utils.FoldersUtils;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 21/lug/2011 22.11.26
 *
 */
public class SessionEventListener extends MessageSourceAwareClass implements HttpSessionListener {

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionCreated(HttpSessionEvent sessionEvent) {

		if (logger.isDebugEnabled()) {
			logger.debug("Session created event for session " + sessionEvent.getSession().getId());
		}
		
		try {
			checkTemporaryRepository(sessionEvent);
			this.buildSessionTemporaryRepository(sessionEvent);
			sessionEvent.getSession().getServletContext().setAttribute(
					Constants.System.SERVLET_CONTEXT_TEMPORARY_PATH_EXISTS, new Boolean(true));
		} catch (IOException e) {
			logger.error("Unable to initialize temporary path.", e);
			sessionEvent.getSession().getServletContext().setAttribute(
					Constants.System.SERVLET_CONTEXT_TEMPORARY_PATH_EXISTS, new Boolean(false));
		}
				
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionDestroyed(HttpSessionEvent sessionEvent) {

		if (logger.isDebugEnabled()) {
			logger.debug("Session destroyed event for session " + sessionEvent.getSession().getId());
			logger.debug("Removing session temporary path for session " + sessionEvent.getSession().getId());
		}
		
		FoldersUtils folderUtils = FoldersUtils.instance();
		
		String sessionTemporaryRepositoryPath = folderUtils.getSessionTemporaryRepositoryPath(sessionEvent);

		File sessionTemporaryRepositoryPathFile = new File(sessionTemporaryRepositoryPath);
		if (sessionTemporaryRepositoryPathFile.exists()) {
			folderUtils.cleanUp(sessionTemporaryRepositoryPathFile, sessionTemporaryRepositoryPathFile);
		}
		
	}

	/**
	 * @param sessionEvent
	 */
	private void buildSessionTemporaryRepository(HttpSessionEvent sessionEvent) {
		
		if (logger.isDebugEnabled()) {
			logger.debug("Creating session temporary repository...");
		}
		
		FoldersUtils folderUtils = FoldersUtils.instance();
		
		String sessionTemporaryRepositoryPath = folderUtils.getSessionTemporaryRepositoryPath(sessionEvent);
		File sessionTemporaryRepositoryPathFile = new File(sessionTemporaryRepositoryPath);
		boolean folderBuilt = false;
		if (!sessionTemporaryRepositoryPathFile.exists()) {
			folderBuilt = sessionTemporaryRepositoryPathFile.mkdir();
		}

		if (!folderBuilt) {
			logger.error("Unable to build temporary path for session " + sessionEvent.getSession().getId());
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("Created session temporary repository " + sessionTemporaryRepositoryPath);
			}
		}
		
	}
	
	/**
	 * @param sessionEvent
	 * @throws IOException
	 */
	private void checkTemporaryRepository(HttpSessionEvent sessionEvent) throws IOException {

		FoldersUtils folderUtils = FoldersUtils.instance();
		
		File file = folderUtils.getTemporaryRepositoryPath(sessionEvent);
		File tmpFolder = new File(file, this.getStaticProperty("session.temporary.path"));
		if (!tmpFolder.exists()) {
			tmpFolder.mkdir();
		}
		
	}
	
}
