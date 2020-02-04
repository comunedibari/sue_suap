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
package it.people.util;

import it.people.Activity;
import it.people.ActivityState;
import it.people.IActivity;
import it.people.IProcess;
import it.people.IStep;
import it.people.PeopleConstants;
import it.people.Step;
import it.people.StepState;
import it.people.core.PeopleContext;
import it.people.core.ProcessManager;
import it.people.core.persistence.exception.peopleException;
import it.people.fsl.servizi.oggetticondivisi.DomicilioFiscaleNonCA;
import it.people.fsl.servizi.oggetticondivisi.profili.Session2AbstractDataProfileHelper;
import it.people.process.AbstractPplProcess;
import it.people.process.data.AbstractData;
import it.people.process.data.PersistentActivity;
import it.people.process.data.PersistentStep;
import it.people.process.data.PplPersistentData;
import it.people.process.view.ConcreteView;
import it.people.process.workflow.IWorkFlow;
import it.people.process.workflow.IWorkFlowFactory;
import it.people.process.workflow.WorkFlowFactory;
import it.people.propertymgr.PropertyFormatException;
import it.people.vsl.PipelineDataHolder;
import it.people.vsl.PipelineDataImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA. User: sergio Date: Sep 17, 2003 Time: 7:23:15 PM To
 * change this template use Options | File Templates.
 */

public class ProcessUtils {

    private static Logger logger = LogManager
	    .getLogger("it.people.util.ProcessUtils");

    /**
     * @param context
     * @param pplProc
     * @return
     */
    public static PplPersistentData pplProcess2ProcessPersistentData(
	    PeopleContext context, AbstractPplProcess pplProc) {
	if (pplProc == null)
	    return null;

	AbstractData data = (AbstractData) pplProc.getData();

	// Stato Attivita' e step
	ConcreteView currentView = pplProc.getView();
	getActivityState(data, currentView);

	PplPersistentData ppd = new PplPersistentData();
	ppd.setProcessName(pplProc.getProcessName());
	ppd.setOid(pplProc.getOid());
	ppd.setUserID(pplProc.getOwner()); // setto l'owner del processo
	ppd.setContentName(pplProc.getCurrentContent()); // contentName
	ppd.setContentID(pplProc.getCurrentContentID()); // contentID
	ppd.setCommune(pplProc.getCommune());
	ppd.setProcessClass(pplProc.getClass());
	ppd.setProcessData(pplProc.getData().marshall());
	ppd.setCreationTime(pplProc.getCreationTime());
	ppd.setSent(pplProc.getSent());
	ppd.setStatus(pplProc.getStatus());
	ppd.setPrincipal(pplProc.getPrincipal());
	ppd.setDelegate(pplProc.getDelegate());
	// Added for off line sign
	ppd.setOffLineSignDocumentHash(pplProc
		.getOffLineSignDownloadedDocumentHash());
	ppd.setOffLineSignWaiting(pplProc.isWaitingForOffLineSignedDocument());

	try {
	    ppd.setProcessDataID(data.getIdentificatorePeople()
		    .getIdentificatoreProcedimento());
	} catch (Exception ex) {
	    return null;
	}

	return ppd;
    }

    /**
     * @param pplProc
     * @param context
     * @param ppd
     * @return
     * @throws peopleException
     */
    public static AbstractPplProcess processPersistentData2pplProcess(
	    AbstractPplProcess pplProc, PeopleContext context,
	    PplPersistentData ppd) throws peopleException {
	return processPersistentData2pplProcess(pplProc, context, ppd, true);
    }

    /**
     * @param pplProc
     * @param context
     * @param ppd
     * @param deserialize
     * @return
     * @throws peopleException
     */
    public static AbstractPplProcess processPersistentData2pplProcess(
	    AbstractPplProcess pplProc, PeopleContext context,
	    PplPersistentData ppd, boolean deserialize) throws peopleException {
	if (ppd == null)
	    return null;

	try {
	    if (pplProc == null)
		pplProc = ProcessManager.getInstance().create(context,
			ppd.getProcessClass());

	    IWorkFlowFactory factory = new WorkFlowFactory();
	    IWorkFlow wFlow = factory.createWorkFlow();

	    // IProcess process = wFlow.getProcess(ppd.getProcessName(),
	    // ppd.getCommune());
	    IProcess process = wFlow.getProcess(context.getSession(),
		    ppd.getProcessName(), ppd.getCommune());
	    pplProc.setProcessWorkflow(process);

	    pplProc.setProcessName(ppd.getProcessName());
	    pplProc.setOid(ppd.getOid());
	    pplProc.setOwner(ppd.getUserID()); // recupero l'owner del processo
	    pplProc.setCurrentContentID(ppd.getContentID()); // contentID
	    pplProc.setCurrentContent(ppd.getContentName());
	    pplProc.setCommune(ppd.getCommune());
	    pplProc.initialize(context, ppd.getCommune(), Device.HTML);
	    pplProc.setCreationTime(ppd.getCreationTime());
	    pplProc.setLastModifiedTime(ppd.getLastModifiedTime());
	    pplProc.setSent(ppd.getSent());
	    pplProc.setStatus(ppd.getStatus());
	    pplProc.setPrincipal(ppd.getPrincipal());
	    pplProc.setDelegate(ppd.getDelegate());
	    // Added for off line sign
	    pplProc.setOffLineSignDownloadedDocumentHash(ppd
		    .getOffLineSignDocumentHash());
	    pplProc.setWaitingForOffLineSignedDocument(ppd
		    .isOffLineSignWaiting());

	    if (deserialize) {
		// il metodo di deserializzazione imposta anche l'identificatore
		// del procedimento
		pplProc.setMarshalledData(ppd.getProcessData());

		// *******************************************************
		// Ripristina il workflow del servizio
		// *******************************************************
		AbstractData theData = (AbstractData) pplProc.getData();
		ConcreteView currentView = pplProc.getView();

		/*
		 * FIX 2007-12-19 by CEFRIEL: lettura da AbstractData delle
		 * informazioni relative a richiedente e titolare memorizzate
		 * all'atto della creazione del servizio. Tali informazioni sono
		 * utilizzate per ripristinare richiedente e titolare in
		 * sessione
		 */
		Session2AbstractDataProfileHelper
			.storeProfilesInSession(
				theData.getProfiloRichiedente(),
				theData.getProfiloTitolare(),
				theData.getDomicilioElettronico(),
				context.getSession());
		/* END FIX 2007-12-19 */

		setActivityState(theData, currentView);

		// Attita' e step corrente
		currentView.setCurrentActivityIndex(theData
			.getLastActivityIdx());
		currentView.getCurrentActivity().setCurrentStepIndex(
			theData.getLastStepIdx());

		// Stato riepilogo e firma
		pplProc.setSignEnabled(theData.isSignEnabled());
		pplProc.setSummaryState(theData.getSummaryState());

	    } else {
		// Anche se non deserializzo l'xml devo valorizzare
		// l'identificatore del procedimento
		// perch� utilizzato negli elenchi (es. la pagine le mie
		// pratiche)
		AbstractData theData = (AbstractData) pplProc.getData();
		theData.getIdentificatorePeople()
			.setIdentificatoreProcedimento(ppd.getProcessDataID());

		/*
		 * FIX 2007-12-19 by CEFRIEL: lettura da AbstractData delle
		 * informazioni relative a richiedente e titolare memorizzate
		 * all'atto della creazione del servizio. Tali informazioni sono
		 * utilizzate per ripristinare richiedente e titolare in
		 * sessione
		 */
		Session2AbstractDataProfileHelper
			.storeProfilesInSession(
				theData.getProfiloRichiedente(),
				theData.getProfiloTitolare(),
				theData.getDomicilioElettronico(),
				context.getSession());
		/* END FIX 2007-12-19 */
	    }

	    return pplProc;
	} catch (Exception ex) {
	    logger.error(ex);
	}

	return null;
    }

    /**
     * @param context
     * @param ppdCollection
     * @return
     * @throws peopleException
     */
    public static Collection processPersistentData2pplProcess(
	    PeopleContext context, Collection ppdCollection)
	    throws peopleException {
	return processPersistentData2pplProcess(context, ppdCollection, true);
    }

    /**
     * @param context
     * @param ppdCollection
     * @param deserialize
     * @return
     * @throws peopleException
     */
    public static Collection processPersistentData2pplProcess(
	    PeopleContext context, Collection ppdCollection, boolean deserialize)
	    throws peopleException {
	if (ppdCollection == null)
	    return null;

	Vector result = new Vector();
	Iterator iter = ppdCollection.iterator();
	while (iter.hasNext()) {
	    try {
		PplPersistentData ppd = (PplPersistentData) iter.next();
		AbstractPplProcess pplProc = processPersistentData2pplProcess(
			null, context, ppd, deserialize);
		if (pplProc != null)
		    result.add(pplProc);

	    } catch (Exception ex) {
		logger.error(ex);
	    }
	}
	return result;
    }

    /**
     * @param processes
     * @return
     */
    public static Collection distinct(Collection processes) {
	if (processes != null && !processes.isEmpty()) {
	    LinkedHashMap map = new LinkedHashMap();
	    Iterator iter = processes.iterator();
	    while (iter.hasNext()) {
		AbstractPplProcess pplProc = (AbstractPplProcess) iter.next();
		map.put(pplProc.getOid(), pplProc);
	    }
	    return processes = map.values();
	}
	return null;
    }

    /**
     * @param data
     * @param currentView
     */
    protected static void getActivityState(AbstractData data,
	    ConcreteView currentView) {
	// e' necessario azzerare i precedenti salvataggi
	data.setPersistentActivities(new ArrayList());
	Activity[] activities = currentView.getActivities();
	for (int i = 0; i < activities.length; i++) {
	    PersistentActivity persistentActivity = new PersistentActivity();
	    persistentActivity.setId(activities[i].getId());
	    persistentActivity.setStateCode(activities[i].getState().getCode());

	    ArrayList steps = activities[i].getStepList();
	    for (Iterator iter = steps.iterator(); iter.hasNext();) {
		Step step = (Step) iter.next();
		PersistentStep persistentStep = new PersistentStep();
		persistentStep.setId(step.getId());
		persistentStep.setStateCode(step.getState().getCode());
		persistentActivity.addPersistentStep(persistentStep);
	    }

	    data.addPersistentActivity(persistentActivity);
	}
    }

    /**
     * Ripristina lo stato delle attivita' e degli step
     * 
     * @param data
     * @param currentView
     */
    protected static void setActivityState(AbstractData data,
	    ConcreteView currentView) {
	ArrayList persistentActivities = data.getPersistentActivities();
	if (persistentActivities.size() > 0) {
	    for (Iterator iter = persistentActivities.iterator(); iter
		    .hasNext();) {
		PersistentActivity persistentActivity = (PersistentActivity) iter
			.next();
		IActivity activity = currentView
			.getActivityById(persistentActivity.getId());

		if (activity != null) {
		    ActivityState activityState = ActivityState
			    .getState(persistentActivity.getStateCode());
		    if (activityState != null) {
			activity.setState(activityState);

			ArrayList persistentSteps = persistentActivity
				.getPersistentSteps();
			for (Iterator iterator = persistentSteps.iterator(); iterator
				.hasNext();) {
			    PersistentStep persistentStep = (PersistentStep) iterator
				    .next();
			    IStep step = activity.getStepById(persistentStep
				    .getId());

			    if (step != null) {
				StepState stepState = StepState
					.getState(persistentStep.getStateCode());
				if (stepState != null)
				    step.setState(stepState);
				else
				    logger.error("Lo stato per lo step caricato non � valido.");
			    } else {
				logger.error("Lo step per cui ripristinare lo stato non esiste.");
			    }
			}
		    } else {
			logger.error("Lo stato per l'attivit� caricato non � valido.");
		    }
		} else {
		    logger.error("L'attivit� per cui ripristinare lo stato non esiste.");
		}
	    }
	}
    }

    /**
     * @param pplProc
     * @param codiceFiscale
     * @param activity
     * @return
     * @throws peopleException
     */
    public static DomicilioFiscaleNonCA readDomicilioFiscaleNonCA(
	    AbstractPplProcess pplProc, String codiceFiscale, String activity)
	    throws peopleException {

	DomicilioFiscaleNonCA result = null;

	try {
	    String nonCAUserDataWSAddress = PeopleProperties.getInstance()
		    .getProperty("datiutentenonca.service.address")
		    .getValueString(pplProc.getCommune().getKey());
	    if (!isValidNonCAUserDataWSAddress(nonCAUserDataWSAddress)) {
		nonCAUserDataWSAddress = PeopleProperties.getInstance()
			.getProperty("datiutentenonca.service.address")
			.getValueString();
	    }
	    if (!isValidNonCAUserDataWSAddress(nonCAUserDataWSAddress)) {
		logger.error("Indirizzo del ws per il recupero del domicilio fiscale non CA non corretto.");
		throw new peopleException(
			"Indirizzo del ws per il recupero del domicilio fiscale non CA non corretto.");
	    }

	} catch (PropertyFormatException e) {
	    logger.error("Errore nel recupero del domicilio fiscale non CA.", e);
	    throw new peopleException(
		    "Errore nel recupero del domicilio fiscale non CA.");
	}

	return result;

    }

    /**
     * @param nonCAUserDataWSAddress
     * @return
     */
    private static boolean isValidNonCAUserDataWSAddress(
	    String nonCAUserDataWSAddress) {

	if (nonCAUserDataWSAddress != null) {
	    if (nonCAUserDataWSAddress.length() > 0) {
		return true;
	    } else {
		return false;
	    }
	} else {
	    return false;
	}

    }

    /**
     * <p>
     * Clear process clearable session data.
     * 
     * @param request
     */
    public final static void clearProcessSessionClearableData(
	    HttpServletRequest request) {

	HttpSession session = request.getSession();
	if (session != null) {
	    Enumeration sessionAttributes = request.getSession()
		    .getAttributeNames();
	    while (sessionAttributes.hasMoreElements()) {
		String sessionAttribute = String.valueOf(sessionAttributes
			.nextElement());
		if (sessionAttribute
			.startsWith(PeopleConstants.CLEARABLE_SESSION_DATA)) {
		    session.removeAttribute(sessionAttribute);
		}
	    }
	}

    }

    /**
     * @param request
     * @return
     */
    public final static Boolean isDelegate(HttpServletRequest request) {

	Boolean isDelegate = Boolean.FALSE;
	if (request != null) {
	    HttpSession session = request.getSession();
	    if (session != null) {
		try {
		    String descQualifica = String
			    .valueOf(session
				    .getAttribute(PeopleProperties.SIRAC_ACCR_TIPO_QUALIFICA_CORRENTE_ATTRIBUTE_NAME
					    .getValueString()));
		    isDelegate = new Boolean(descQualifica != null
			    && !descQualifica.trim().equalsIgnoreCase("")
			    && !descQualifica.trim().equalsIgnoreCase("utente"));
		} catch (PropertyFormatException e) {
		    logger.error("", e);
		}
	    }
	}

	return isDelegate;

    }

    /**
     * @param pipelineDataHolder
     * @return
     */
    public static String dumpProcessDataXml(
	    final PipelineDataHolder pipelineDataHolder) {

	StringBuilder dumpFileName = new StringBuilder();
	try {

	    AbstractPplProcess process = (AbstractPplProcess) pipelineDataHolder
		    .getPlineData().getAttribute(
			    PipelineDataImpl.EDITABLEPROCESS_PARAMNAME);

	    StringBuilder finalXml = new StringBuilder(
		    (String) pipelineDataHolder.getPlineData().getAttribute(
			    PipelineDataImpl.XML_PROCESSDATA_PARAMNAME));

	    if (!StringUtils
		    .isEmpty(PeopleProperties.PEOPLE_TMP_XML_DUMP_FOLDER_VALUE
			    .getValueString())) {

		File tmpXmlDumpFolder = new File(
			PeopleProperties.PEOPLE_TMP_XML_DUMP_FOLDER_VALUE
				.getValueString());
		if (tmpXmlDumpFolder.exists() && tmpXmlDumpFolder.canWrite()) {
		    dumpFileName.append(File.separator
			    + getDirNameFromPackage(process.getProcessName()));
		    (new File(dumpFileName.toString())).mkdir();
		    dumpFileName
			    .append("-")
			    .append(process.getCurrentOperationId())
			    .append("-")
			    .append((new SimpleDateFormat("yyyyMMddHHmmssSSS"))
				    .format(Calendar.getInstance().getTime()))
			    .append("-toBE.xml");
		    FileOutputStream fos = new FileOutputStream(
			    dumpFileName.toString());
		    PrintStream os = new PrintStream(fos);
		    os.print(finalXml.toString());
		    os.flush();
		    fos.flush();
		    fos.close();
		    os.close();
		} else {
		    StringBuilder tmpXmlDumpFolderError = new StringBuilder("");
		    if (!tmpXmlDumpFolder.exists()) {
			tmpXmlDumpFolderError
				.append("Tmp folder for XML dump doesn't exists.");
		    } else if (!tmpXmlDumpFolder.canWrite()) {
			tmpXmlDumpFolderError
				.append("Tmp folder for XML dump is not writable.");
		    }
		    logger.warn(tmpXmlDumpFolderError.toString());
		    dumpFileName.setLength(0);
		}
	    }
	} catch (PropertyFormatException e) {
	    logger.warn("Invalid property specified for tmp folder XML dump.",
		    e);
	    dumpFileName.setLength(0);
	} catch (FileNotFoundException e) {
	    logger.warn("Unable to open file for tmp folder XML dump.", e);
	    dumpFileName.setLength(0);
	} catch (IOException e) {
	    logger.warn("Unable to open file for tmp folder XML dump.", e);
	    dumpFileName.setLength(0);
	}

	return dumpFileName.toString();

    }

    /**
     * @param dumpFile
     * @return
     */
    public static final boolean clearProcessDataXmlDump(
	    final String dumpFileName) {
	File dumpFile = new File(dumpFileName);
	if (dumpFile.exists() && dumpFile.canWrite()) {
	    return dumpFile.delete();
	}
	return false;
    }

    /**
     * @param _package
     * @return
     */
    public final static String getDirNameFromPackage(String _package) {
	String names[] = _package.split("\\.");
	String dirName = "";
	for (int i = names.length - 3; i < names.length; i++)
	    dirName += getFirstUpperCaseString(names[i]);
	return dirName;
    }

    /**
     * @param string
     * @return
     */
    private static String getFirstUpperCaseString(String string) {
	return string.substring(0, 1).toUpperCase()
		+ string.substring(1, string.length()).toLowerCase();
    }

}
