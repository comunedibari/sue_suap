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
package it.people.console.system;

import java.util.Map;

import javax.sql.DataSource;

import it.people.console.persistence.PersistenceBroker;
import it.people.console.utils.Constants;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 22/ott/2011 12.01.29
 *
 */
public class UserPreferencesManager extends AbstractLogger {

	private PersistenceBroker persistenceBroker;
	
	public UserPreferencesManager(DataSource dataSource) {
		this.persistenceBroker = new PersistenceBroker();
		this.persistenceBroker.setDataSource(dataSource);
	}
	
	public void loadPreferences(int userId) {
		
		if (logger.isInfoEnabled()) {
			logger.info("Loading user preferences...");
		}
		
		if (logger.isInfoEnabled()) {
			logger.info("Loading paged list holders user preferences...");
		}
		Map<String, Integer> pagedListHoldersPref = persistenceBroker.loadPagedListHoldersPreferences(userId);
		if (pagedListHoldersPref.isEmpty()) {
			if (logger.isDebugEnabled()) {
				logger.debug("Initializing paged list holders user preferences with defaults settings...");
			}
			initDefaultPagedListHoldersPref(pagedListHoldersPref);
		}
		
	}

	public void updatePreferences(int userId) {
		
		
	}
	
	private void initDefaultPagedListHoldersPref(Map<String, Integer> pagedListHoldersPref) {
		
		pagedListHoldersPref.put(Constants.PagedListHoldersIds.ACCOUNTS_LIST, new Integer(10));
		pagedListHoldersPref.put(Constants.PagedListHoldersIds.ACCOUNTS_WITHOUT_CERTIFICATES_LIST, new Integer(10));
		pagedListHoldersPref.put(Constants.PagedListHoldersIds.BE_REFERENCES_LIST, new Integer(10));
		pagedListHoldersPref.put(Constants.PagedListHoldersIds.BE_SERVICES_LIST, new Integer(10));
		pagedListHoldersPref.put(Constants.PagedListHoldersIds.CERTIFICATE_ACCOUNTS_LIST, new Integer(10));
		pagedListHoldersPref.put(Constants.PagedListHoldersIds.FE_SERVICES_LIST, new Integer(10));
		pagedListHoldersPref.put(Constants.PagedListHoldersIds.FRAMEWORK_LABELS_LIST, new Integer(10));
		pagedListHoldersPref.put(Constants.PagedListHoldersIds.MAIL_SETTINGS, new Integer(10));
		pagedListHoldersPref.put(Constants.PagedListHoldersIds.NODES_LIST, new Integer(10));
		pagedListHoldersPref.put(Constants.PagedListHoldersIds.PARAMETERS_LIST, new Integer(10));
		pagedListHoldersPref.put(Constants.PagedListHoldersIds.SECURITY_SETTINGS, new Integer(10));
		pagedListHoldersPref.put(Constants.PagedListHoldersIds.SERVICE_LABELS_LIST, new Integer(10));
		
	}
	
}
