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
 * Creato il 27-dic-2006 da Cedaf s.r.l.
 *
 */
package it.people.db.fedb;

import it.people.exceptions.PeopleDBException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;

/**
 * @author Michele Fabbri - Cedaf s.r.l.
 * 
 */
public class ServiceFactory extends AbstractFactory {

    private static Logger logger = Logger.getLogger(ServiceFactory.class);

    /**
     * 
     */
    public ServiceFactory() {
    }

    public Collection getEnabledServices(String communeId)
	    throws PeopleDBException {
	try {
	    // Eng-28072011->Add sendmailtoowner column
	    // CCD - 2012.03.18 - Added showprivacydisclaimer and
	    // privacydisclaimerrequireacceptance properties
	    String query = "SELECT nome, id, process, communeid, "
		    + " package, logid, receiptmailattachment, signenabled, sendmailtoowner, embedattachmentinxml, showprivacydisclaimer, privacydisclaimerrequireacceptance, "
		    + " firmaOnLine, firmaOffLine FROM service "
		    + " WHERE communeId = '" + communeId + "' "
		    + " AND statusid = 1" + " ORDER BY nome";

	    Collection services = getCollection(query);

	    return services;

	} catch (Exception ex) {
	    logger.error(ex);
	    throw new PeopleDBException(
		    "Impossibile caricare l'elenco dei servizi");
	}
    }

    public Collection getEnabledServices(String communeId, String area)
	    throws PeopleDBException {
	try {
	    // Eng-28072011->Add sendmailtoowner column
	    // CCD - 2012.03.18 - Added showprivacydisclaimer and
	    // privacydisclaimerrequireacceptance properties
	    String query = "SELECT nome, id, process, communeid, "
		    + " package, logid, receiptmailattachment, signenabled, sendmailtoowner, embedattachmentinxml, showprivacydisclaimer, privacydisclaimerrequireacceptance, "
		    + " firmaOnLine, firmaOffLine FROM service "
		    + " WHERE communeId = '" + communeId + "' "
		    + " AND statusid = 1" + " AND attivita = '" + area + "'"
		    + " ORDER BY nome";

	    Collection services = getCollection(query);
	    return services;

	} catch (Exception ex) {
	    logger.error(ex);
	    throw new PeopleDBException(
		    "Impossibile caricare l'elenco dei servizi");
	}
    }

    public Service getService(String processName, String communeId)
	    throws PeopleDBException {
	Service service = null;

	try {
	    // Eng-28072011->Add sendmailtoowner column
	    // CCD - 2012.03.18 - Added showprivacydisclaimer and
	    // privacydisclaimerrequireacceptance properties
	    String query = "SELECT nome, id, process, communeid, "
		    + " package, logid, receiptmailattachment, signenabled, sendmailtoowner, embedattachmentinxml, showprivacydisclaimer, privacydisclaimerrequireacceptance, "
		    + " firmaOnLine, firmaOffLine FROM service "
		    + " WHERE package = '" + processName + "' "
		    + " AND communeId = '" + communeId + "'";

	    Collection services = getCollection(query);
	    Iterator iter = services.iterator();
	    if (iter.hasNext()) {
		service = (Service) iter.next();

		// < CCD - 2012.03.18
		if (logger.isDebugEnabled()) {
		    logger.debug("Searching for service required user data...");
		}
		query = "select userdata from service_required_user_data where serviceid = "
			+ service.getId();
		Collection requiredUserData = this.getDynamicCollection(query,
			new RequiredUserDataDynamicCollection());
		if (requiredUserData != null && !requiredUserData.isEmpty()) {
		    if (logger.isDebugEnabled()) {
			logger.debug("Found required user data for service "
				+ service.getProcessName() + ".");
		    }
		    Iterator<String> requiredDataIterator = requiredUserData
			    .iterator();
		    while (requiredDataIterator.hasNext()) {
			String requiredData = requiredDataIterator.next();
			if (logger.isDebugEnabled()) {
			    logger.debug("Adding required user data "
				    + requiredData + " for service "
				    + service.getProcessName() + ".");
			}
			service.getRequiredOperatorData().add(requiredData);
		    }
		} else {
		    if (logger.isDebugEnabled()) {
			logger.debug("No required user data for service "
				+ service.getProcessName() + ".");
		    }
		}
		// CCD - 2012.03.18 >

		if (logger.isDebugEnabled()) {
		    logger.debug("Searching for service enabled audit processors...");
		}
		query = "select auditProcessor from service_audit_processors where active = 1 and serviceId = "
			+ service.getId();
		Collection enabledAuditProcessors = this.getDynamicCollection(
			query, new EnabledAuditProcessorsDynamicCollection());
		if (enabledAuditProcessors != null
			&& !enabledAuditProcessors.isEmpty()) {
		    if (logger.isDebugEnabled()) {
			logger.debug("Found enabled audit processors for service "
				+ service.getProcessName() + ".");
		    }
		    Iterator<String> enabledAuditProcessorsIterator = enabledAuditProcessors
			    .iterator();
		    while (enabledAuditProcessorsIterator.hasNext()) {
			String enabledAuditProcessor = enabledAuditProcessorsIterator
				.next();
			if (logger.isDebugEnabled()) {
			    logger.debug("Adding enabled audit processor "
				    + enabledAuditProcessor + " for service "
				    + service.getProcessName() + ".");
			}
			service.addEnabledProcessors(enabledAuditProcessor);
		    }
		} else {
		    if (logger.isDebugEnabled()) {
			logger.debug("No audit processor(s) enabled for service "
				+ service.getProcessName() + ".");
		    }
		}

		return service;
	    }

	} catch (Exception ex) {
	    logger.error(ex);
	    throw new PeopleDBException(
		    "Impossibile determinare la configurazione del procedimento");
	}

	return service;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.people.db.fedb.AbstractFactory#getFromResultSet(java.sql.ResultSet,
     * java.util.ArrayList)
     */
    protected Object getFromResultSet(ResultSet resultSet, ArrayList list)
	    throws SQLException {
	Service service = new Service();
	service.setDescription(resultSet.getString("nome"));
	service.setId(resultSet.getInt("id"));
	service.setProcessType(resultSet.getString("process"));
	service.setCommuneId(resultSet.getString("communeId"));
	service.setProcessName(resultSet.getString("package"));
	service.setLogId(resultSet.getInt("logid"));
	service.setReceiptMailAttachment(resultSet
		.getInt("receiptmailattachment") == 1);
	service.setSignEnabled(resultSet.getInt("signenabled") == 1);
	// Eng-28072011->Add sendmailtoowner column
	service.setSendMailToOwner(resultSet.getInt("sendmailtoowner") == 1);
	// CCD - 2012.03.18 - Added showprivacydisclaimer and
	// privacydisclaimerrequireacceptance properties
	service.setEmbedAttachmentInXml(resultSet
		.getInt("embedattachmentinxml") == 1);
	service.setShowPrivacyDisclaimer(resultSet
		.getInt("showprivacydisclaimer") == 1);
	service.setPrivacyDisclaimerRequireAcceptance(resultSet
		.getInt("privacydisclaimerrequireacceptance") == 1);
	service.setOnLineSign(resultSet.getInt("firmaOnLine") == 1);
	service.setOffLineSign(resultSet.getInt("firmaOffLine") == 1);
	return service;
    }

    private Collection getRequiredUserDataCollection(ResultSet resultSet) {

	Collection result = new ArrayList();

	if (resultSet != null) {
	    try {
		while (resultSet.next()) {
		    result.add(resultSet.getString(1));
		}
	    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    } finally {

	    }
	}

	return result;

    }

}
