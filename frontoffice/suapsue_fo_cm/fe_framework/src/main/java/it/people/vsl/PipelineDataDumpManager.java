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
/**
 * 
 */
package it.people.vsl;

import java.util.Collection;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;

import it.people.core.persistence.PersistenceManager;
import it.people.core.persistence.UnsentProcessPipelineDataPersistenceMgr;
import it.people.core.persistence.exception.peopleException;
import it.people.vsl.transport.TransportLayer;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 *         17/ott/2012 15:40:37
 */
public class PipelineDataDumpManager {

    private static final Logger logger = LogManager
	    .getLogger(PipelineDataDumpManager.class);

    /**
     * @param specializedSender
     * @param data
     * @return
     */
    public static final boolean dumpPipelineData(
	    TransportLayer specializedSender, UnsentProcessPipelineData data) {

	boolean result = true;

	if (!isPipelineDataDumpedBySubmittedProcessOid(data
		.getSubmittedProcessOid())) {
	    UnsentProcessPipelineDataPersistenceMgr unsentProcessPipelineDataPersistenceMgr = new UnsentProcessPipelineDataPersistenceMgr(
		    PersistenceManager.Mode.WRITE);
	    try {
		unsentProcessPipelineDataPersistenceMgr.set(data);
	    } catch (peopleException e) {
		logger.error(
			"Unable to dump pipeline data for unsent process.", e);
		result = false;
	    } finally {
		unsentProcessPipelineDataPersistenceMgr.close();
	    }
	}

	return result;

    }

    /**
     * @param submittedProcessOid
     * @return
     */
    public static final UnsentProcessPipelineData getPipelineDataDump(
	    final long submittedProcessOid) {

	UnsentProcessPipelineData result = null;

	UnsentProcessPipelineDataPersistenceMgr unsentProcessPipelineDataPersistenceMgr = new UnsentProcessPipelineDataPersistenceMgr(
		PersistenceManager.Mode.READ);

	try {
	    Criteria criteria = new Criteria();
	    criteria.addEqualTo("submitted_process_oid", submittedProcessOid);

	    Query query = QueryFactory.newQuery(
		    UnsentProcessPipelineData.class, criteria);

	    Collection<?> unsentProcessPipelineDataCollection = null;
	    try {
		unsentProcessPipelineDataCollection = unsentProcessPipelineDataPersistenceMgr
			.get(query);
		if (unsentProcessPipelineDataCollection != null) {
		    if (unsentProcessPipelineDataCollection.size() == 1) {
			result = (UnsentProcessPipelineData) unsentProcessPipelineDataCollection
				.iterator().next();
		    } else {
			throw new peopleException(
				"Pipeline data dump integrity exception: more than one dump found for submitted process id '"
					+ String.valueOf(submittedProcessOid)
					+ "'.");
		    }
		} else {
		    throw new peopleException(
			    "Pipeline data dump integrity exception: no dump found for submitted process id '"
				    + String.valueOf(submittedProcessOid)
				    + "'.");
		}
	    } catch (peopleException e) {
		logger.error(
			"Unable to retrieve dumped data for submitted process id '"
				+ String.valueOf(submittedProcessOid) + "'.", e);
	    }

	    return result;
	} finally {
	    unsentProcessPipelineDataPersistenceMgr.close();
	}

    }

    /**
     * <p>
     * Remove the backup data for the submitted process by dump oid.
     * 
     * @param dumpOid
     *            the OID of the pipeline data dump
     */
    public static final void clearPipelineDataDumpByDumpOid(final long dumpOid) {

	UnsentProcessPipelineDataPersistenceMgr unsentProcessPipelineDataPersistenceMgr = new UnsentProcessPipelineDataPersistenceMgr(
		PersistenceManager.Mode.READ);

	try {
	    Criteria criteria = new Criteria();
	    criteria.addEqualTo("oid", dumpOid);

	    Query query = QueryFactory.newQuery(
		    UnsentProcessPipelineData.class, criteria);

	    unsentProcessPipelineDataPersistenceMgr.delete(query);
	} finally {
	    unsentProcessPipelineDataPersistenceMgr.close();
	}

    }

    /**
     * <p>
     * Remove the backup data for the submitted process by submitted process
     * oid.
     * 
     * @param submittedProcessOid
     *            the OID of the submitted process
     */
    public static final void clearPipelineDataDumpBySubmittedProcessOid(
	    final long submittedProcessOid) {

	UnsentProcessPipelineDataPersistenceMgr unsentProcessPipelineDataPersistenceMgr = new UnsentProcessPipelineDataPersistenceMgr(
		PersistenceManager.Mode.READWRITE);

	try {
	    Criteria criteria = new Criteria();
	    criteria.addEqualTo("submitted_process_oid", submittedProcessOid);

	    Query query = QueryFactory.newQuery(
		    UnsentProcessPipelineData.class, criteria);

	    unsentProcessPipelineDataPersistenceMgr.delete(query);
	} finally {
	    unsentProcessPipelineDataPersistenceMgr.close();
	}

    }

    /**
     * @param dumpOid
     * @return
     */
    public static final boolean isPipelineDataDumpedByDumpOid(final long dumpOid) {

	boolean result = false;

	UnsentProcessPipelineDataPersistenceMgr unsentProcessPipelineDataPersistenceMgr = new UnsentProcessPipelineDataPersistenceMgr(
		PersistenceManager.Mode.READ);

	try {
	    Criteria criteria = new Criteria();
	    criteria.addEqualTo("oid", dumpOid);

	    Query query = QueryFactory.newQuery(
		    UnsentProcessPipelineData.class, criteria);

	    Collection<?> unsentProcessPipelineDataCollection = null;

	    try {
		unsentProcessPipelineDataCollection = unsentProcessPipelineDataPersistenceMgr
			.get(query);
		result = unsentProcessPipelineDataCollection != null
			&& unsentProcessPipelineDataCollection.size() > 0;
	    } catch (peopleException e) {
	    }

	    return result;
	} finally {
	    unsentProcessPipelineDataPersistenceMgr.close();
	}

    }

    /**
     * @param submittedProcessOid
     * @return
     */
    public static final boolean isPipelineDataDumpedBySubmittedProcessOid(
	    final long submittedProcessOid) {

	boolean result = false;

	UnsentProcessPipelineDataPersistenceMgr unsentProcessPipelineDataPersistenceMgr = new UnsentProcessPipelineDataPersistenceMgr(
		PersistenceManager.Mode.READWRITE);

	try {
	    Criteria criteria = new Criteria();
	    criteria.addEqualTo("submitted_process_oid", submittedProcessOid);

	    Query query = QueryFactory.newQuery(
		    UnsentProcessPipelineData.class, criteria);

	    Collection<?> unsentProcessPipelineDataCollection = null;

	    try {
		unsentProcessPipelineDataCollection = unsentProcessPipelineDataPersistenceMgr
			.get(query);
		result = unsentProcessPipelineDataCollection != null
			&& unsentProcessPipelineDataCollection.size() > 0;
	    } catch (peopleException e) {
	    }

	    return result;
	} finally {
	    unsentProcessPipelineDataPersistenceMgr.close();
	}

    }

}
