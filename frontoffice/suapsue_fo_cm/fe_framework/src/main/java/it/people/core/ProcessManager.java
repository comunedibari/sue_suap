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
package it.people.core;

import it.people.core.persistence.PersistenceManager;
import it.people.core.persistence.PersistenceManagerFactory;
import it.people.core.persistence.exception.peopleException;
import it.people.dao.ExtendSaveDAO;
import it.people.fsl.servizi.oggetticondivisi.Titolare;
import it.people.process.AbstractPplProcess;
import it.people.process.data.AbstractData;
import it.people.process.data.PplPersistentData;
import it.people.process.view.ConcreteView;
import it.people.util.ProcessUtils;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.apache.log4j.Category;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;

/**
 * User: sergio Date: Sep 16, 2003 Time: 4:08:04 PM <br>
 * <br>
 * Classe per la gestione dei processi
 */
public class ProcessManager {

    private Category cat = Category.getInstance(ProcessManager.class.getName());

    private static ProcessManager ourInstance;

    /**
     * 
     * @return Restituisce l'istanza ProcessManager
     */
    public synchronized static ProcessManager getInstance() {
	if (ourInstance == null) {
	    ourInstance = new ProcessManager();
	}
	return ourInstance;
    }

    /**
     * 
     * @param context
     *            PeopleContext
     * @param processClazz
     *            Classe del processo
     * @return Restituisce un oggetto della classe specificata settandone la
     *         data di creazione
     */
    public AbstractPplProcess create(PeopleContext context, Class processClazz) {
	if (processClazz == null)
	    throw new NullPointerException(
		    "Unable to create an Process starting from a null sample!");
	try {
	    AbstractPplProcess newObject = (AbstractPplProcess) processClazz
		    .newInstance();

	    newObject.setCreationTime(new Timestamp(new Date().getTime()));

	    // Modificato per la gestione dei delegati
	    // newObject.addPrincipal(new
	    // PplPrincipal(context.getUser().getUserID(),
	    // PplRole.RICHIEDENTE));

	    return newObject;
	} catch (Exception ex) {
	    cat.error(ex);
	    return null;
	}
    }

    /**
     * Salva sulla base dati il processo
     * 
     * @param context
     *            PeopleContext
     * @param p_obj_process
     *            Oggetto AbstractPplProcess
     * @throws peopleException
     * 
     */
    public void set(PeopleContext context, AbstractPplProcess process)
	    throws peopleException {
	if (process == null) {
	    return;
	}
	PersistenceManager persMgr = PersistenceManagerFactory.getInstance()
		.get(PplPersistentData.class, PersistenceManager.Mode.WRITE);
	try {
	    process.setLastModifiedTime(new Timestamp(new Date().getTime()));

	    // ******************************************************************
	    // Memorizza lo stato del workflow che puo' cambiare dinamicamente
	    // e richiede un successivo ripristino
	    // ******************************************************************
	    AbstractData theData = (AbstractData) process.getData();
	    ConcreteView currentView = process.getView();

	    // memorizza l'ultimo step e l'ultima attivit� in cui il servizio
	    theData.setLastActivityIdx(currentView.getCurrentActivityIndex());
	    theData.setLastStepIdx(currentView.getCurrentActivity()
		    .getCurrentStepIndex());

	    // memorizza lo stato del riepilogo e della firma
	    theData.setSignEnabled(process.isSignEnabled());
	    theData.setSummaryState(process.getSummaryState());

	    PplPersistentData ppd = ProcessUtils
		    .pplProcess2ProcessPersistentData(context, process);

	    persMgr.definePrimaryKey(ppd);
	    persMgr.set(ppd);

	    // modifica by Mirko Calandrini
	    setExtraInfo(process);

	    process.setOid(ppd.getOid());
	} catch (Exception ex) {
	    cat.error(ex);
	    throw new peopleException(ex.getMessage());
	} finally {
	    if (persMgr != null) {
		persMgr.close();
	    }
	}
    }

    /**
     * Questo metodo cerca solo tra i processi non inviati
     * 
     * @param context
     *            PeopleContext
     * @param oid
     *            Id del processo da recuperare
     * @return Restituisce il processo cercato
     * @throws peopleException
     */
    public AbstractPplProcess load(AbstractPplProcess formBean,
	    PeopleContext context, Long oid) throws peopleException {
	// return load(formBean,context, oid, new Boolean(false));
	return load(formBean, context, oid, null);
    }

    /**
     * @param context
     *            PeopleContext
     * @param oid
     *            Id del processo da recuperare
     * @param sent
     *            Indica se cercare solo tra i processi in stato inviato
     * @return Restituisce il processo cercato
     * @throws peopleException
     * 
     */
    public AbstractPplProcess load(AbstractPplProcess formBean,
	    PeopleContext context, Long oid, Boolean sent)
	    throws peopleException {
	if (oid == null) {
	    return null;
	}

	PersistenceManager persMgr = PersistenceManagerFactory.getInstance()
		.get(PplPersistentData.class, PersistenceManager.Mode.READ);

	try {
	    Collection process = null;

	    Criteria crtr = new Criteria();
	    Criteria crtrD = new Criteria();
	    crtr.addEqualTo("oid", oid);
	    crtrD.addEqualTo("oid", oid);

	    if (sent != null) {
		Criteria crtrSent = new Criteria();
		crtrSent.addEqualTo("sent", sent);
		crtr.addAndCriteria(crtrSent);
		crtrD.addEqualTo("sent", sent);
	    }

	    Criteria crtrUser = new Criteria();
	    crtrUser.addEqualTo("principal.userID", context.getUser()
		    .getUserID());

	    // Delegati
	    Criteria crtrDelegate = new Criteria();
	    crtrDelegate.addEqualTo("delegate.delegateID", context.getUser()
		    .getUserID());

	    crtr.addAndCriteria(crtrUser);
	    crtrD.addAndCriteria(crtrDelegate);

	    process = persMgr.get(QueryFactory.newQuery(
		    PplPersistentData.class, crtr));
	    process.addAll(persMgr.get(QueryFactory.newQuery(
		    PplPersistentData.class, crtrD)));

	    if (process != null && !process.isEmpty()) {
		Iterator iter = process.iterator();
		return ProcessUtils.processPersistentData2pplProcess(formBean,
			context, (PplPersistentData) iter.next());
	    }
	    return null;
	} finally {
	    if (persMgr != null) {
		persMgr.close();
	    }
	}

    }

    /**
     * @param context
     *            PeopleContext
     * @param PeopleID
     *            del processo da recuperare
     * @return Restituisce il processo cercato
     * @throws peopleException
     * 
     */
    public AbstractPplProcess load(AbstractPplProcess formBean,
	    PeopleContext context, String identificatoreProcedimento)
	    throws peopleException {

	if (identificatoreProcedimento == null)
	    return null;

	PersistenceManager persMgr = PersistenceManagerFactory.getInstance()
		.get(PplPersistentData.class, PersistenceManager.Mode.READ);

	try {
	    Collection process = null;

	    Criteria crtr = new Criteria();
	    crtr.addEqualTo("processDataID", identificatoreProcedimento);

	    process = persMgr.get(QueryFactory.newQuery(
		    PplPersistentData.class, crtr));

	    if (process != null && !process.isEmpty()) {
		Iterator iter = process.iterator();
		return ProcessUtils.processPersistentData2pplProcess(formBean,
			context, (PplPersistentData) iter.next());
	    }
	} finally {
	    if (persMgr != null) {
		persMgr.close();
	    }
	}

	return null;
    }

    /**
     * Cancella il processo, per essere rimosso il procedimento deve essere
     * nello stato "S_EDIT", non inviato ed appartenere all'utente
     * 
     * @param context
     * @param oid
     */
    public void delete(PeopleContext context, Long oid) {

	if (oid == null)
	    return;

	// Creazione query di ricerca
	Criteria criteria = new Criteria();

	// filtro sulla chiave
	criteria.addEqualTo("oid", oid);

	// filtro sull'utente
	criteria.addEqualTo("principal.userID", context.getUser().getUserID());

	// solo procedimenti non inviati
	criteria.addEqualTo("sent", Boolean.FALSE);

	// solo procedimenti in stato di edit
	criteria.addEqualTo("status", "S_EDIT");

	PersistenceManager persistenceManagerReader = null;
	PersistenceManager persistenceManagerWriter = null;
	try {
	    // Carica il procedimento
	    persistenceManagerReader = PersistenceManagerFactory.getInstance()
		    .get(PplPersistentData.class, PersistenceManager.Mode.READ);

	    Collection processes = persistenceManagerReader.get(QueryFactory
		    .newQuery(PplPersistentData.class, criteria));

	    // Rimuove il procedimento
	    persistenceManagerWriter = PersistenceManagerFactory
		    .getInstance()
		    .get(PplPersistentData.class, PersistenceManager.Mode.WRITE);

	    persistenceManagerWriter.delete(processes);

	    deleteExtraInfo(processes);
	    // non e' possibile utilizzare direttamente il deleteByQuery()
	    // perche' questo non tiene conto delle dipendenze e non
	    // pulisce la cache.

	} catch (peopleException ex) {
	    // l'interfaccia non consente il ritorno dell'eccezione
	    cat.error(ex);
	} finally {
	    if (persistenceManagerReader != null) {
		persistenceManagerReader.close();
	    }
	    if (persistenceManagerWriter != null) {
		persistenceManagerWriter.close();
	    }
	}
    }

    private void deleteExtraInfo(Collection processes) {
	if (processes != null) {
	    ExtendSaveDAO esd = new ExtendSaveDAO("java:comp/env/jdbc/LegacyDB");
	    esd.deleteExtedData(processes);
	}
    }

    private void setExtraInfo(AbstractPplProcess process) {
	ExtendSaveDAO esd = new ExtendSaveDAO("java:comp/env/jdbc/LegacyDB");
	String descrizione = null;
	String oggetto = null;
	AbstractData data = (AbstractData) process.getData();
	if (data.getDescrizionePratica() != null
		&& !data.getDescrizionePratica().equalsIgnoreCase("")) {
	    descrizione = data.getDescrizionePratica();
	}
	if (data.getOggettoPratica() != null
		&& !data.getOggettoPratica().equalsIgnoreCase("")) {
	    oggetto = data.getOggettoPratica();
	}
	try {
	    if (descrizione != null || oggetto != null) {

		if (data.getIstatEnte() == null
			|| (data.getIstatEnte() != null && data.getIstatEnte()
				.equalsIgnoreCase(""))) {
		    data.setIstatEnte(process.getCommune().getOid());
		}
		if (data.getNomeEnte() == null
			|| (data.getNomeEnte() != null && data.getNomeEnte()
				.equalsIgnoreCase(""))) {
		    data.setNomeEnte(process.getCommune().getName());
		}

		esd.setDetail(oggetto, descrizione, data
			.getIdentificatorePeople()
			.getIdentificatoreProcedimento(), data.getIstatEnte(),
			data.getNomeEnte());
	    }
	    esd.setAltriTitolari(data.getAltriDichiaranti(), data
		    .getIdentificatorePeople().getIdentificatoreProcedimento());
	} catch (Exception ee) {
	    cat.error("ProcessManager - Impossibile salvare le informazioni aggiuntive");
	}
    }
}
