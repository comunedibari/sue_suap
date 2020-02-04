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
 * Created on 27-apr-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.people.process.workflow.xml;

import it.people.Activity;
import it.people.ActivityState;
import it.people.City;
import it.people.IActivity;
import it.people.IProcess;
import it.people.IStep;
import it.people.IView;
import it.people.Process;
import it.people.Step;
import it.people.StepState;
import it.people.SummaryActivity;
import it.people.View;
import it.people.annotations.DeveloperTaskEnd;
import it.people.annotations.DeveloperTaskStart;
import it.people.db.fedb.Service;
import it.people.db.fedb.ServiceFactory;
import it.people.db.steps.StepsFactory;
import it.people.exceptions.PeopleDBException;
import it.people.layout.multicommune.MultiCommunePath;
import it.people.process.SummaryState;
import it.people.process.print.ConcretePrint;
import it.people.process.privacy.PrivacyStep;
import it.people.process.privacy.PrivacyStepHelper;
import it.people.process.sign.ConcreteSign;
import it.people.process.view.ConcreteView;
import it.people.process.workflow.IWorkFlow;
import it.people.process.workflow.exceptions.XMLWorkFlowException;
import it.people.util.MessageBundleHelper;
import it.people.util.MimeType;
import it.people.util.frontend.WorkflowController;
import it.people.util.payment.IPaymentNotificationObserver;
import it.people.util.payment.IPaymentObserver;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Vector;

import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author thweb4
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class XMLWorkFlow implements IWorkFlow {

    private static Logger logger = LogManager.getLogger(XMLWorkFlow.class);

    protected class ProcessConfiguration {

	protected class ProcessSignConfiguration {
	    private boolean _isJspRedefined = false;
	    private boolean _isJspPrintRedefined = false;
	    private boolean _isClassRedefined = false;
	    private String _relPath = null;
	    private String _relPrintPath = null;

	    public boolean isJspRedefined() {
		return _isJspRedefined;
	    }

	    public void setJspRedefined(boolean value) {
		_isJspRedefined = value;
	    }

	    public boolean isJspPrintRedefined() {
		return _isJspPrintRedefined;
	    }

	    public void setJspPrintRedefined(boolean value) {
		_isJspPrintRedefined = value;
	    }

	    public boolean isClassRedefined() {
		return _isClassRedefined;
	    }

	    public void setClassRedefined(boolean value) {
		_isClassRedefined = value;
	    }

	    public String getRelativePath() {
		return _relPath;
	    }

	    public void setRelativePath(String path) {
		_relPath = path;
	    }

	    public String getRelativePrintPath() {
		return _relPrintPath;
	    }

	    public void setRelativePrintPath(String path) {
		_relPrintPath = path;
	    }
	}

	protected class ProcessPrintConfiguration {
	    private boolean _isClassRedefined = false;

	    public boolean isClassRedefined() {
		return _isClassRedefined;
	    }

	    public void setClassRedefined(boolean value) {
		_isClassRedefined = value;
	    }
	}

	protected class ProcessClassConfiguration {
	    private boolean _isClassRedefined = false;

	    public boolean isClassRedefined() {
		return _isClassRedefined;
	    }

	    public void setClassRedefined(boolean value) {
		_isClassRedefined = value;
	    }
	}

	protected class ProcessViewConfiguration {
	    private boolean _isClassRedefined = false;

	    public boolean isClassRedefined() {
		return _isClassRedefined;
	    }

	    public void setClassRedefined(boolean value) {
		_isClassRedefined = value;
	    }

	    private boolean _isBottomSaveBarEnabled = true;
	    private boolean _isBottomNavigationBarEnabled = true;

	    public boolean isBottomNavigationBarEnabled() {
		return _isBottomNavigationBarEnabled;
	    }

	    public boolean isBottomSaveBarEnabled() {
		return _isBottomSaveBarEnabled;
	    }

	    public void setBottomNavigationBarEnabled(boolean value) {
		_isBottomNavigationBarEnabled = value;
	    }

	    public void setBottomSaveBarEnabled(boolean value) {
		_isBottomSaveBarEnabled = value;
	    }

	    private boolean showBackEndError = false;

	    public boolean isShowBackEndError() {
		return showBackEndError;
	    }

	    public void setShowBackEndError(boolean showBackEndError) {
		this.showBackEndError = showBackEndError;
	    }
	}

	private boolean _isSignEnabled = false;
	private SummaryState _summaryState = SummaryState.NONE;

	private String _paymentObserver = null;

	private ProcessSignConfiguration _psConfig = null;
	private ProcessPrintConfiguration _ppConfig = null;
	private ProcessClassConfiguration _pcConfig = null;
	private ProcessViewConfiguration _pvConfig = null;

	public ProcessConfiguration() {
	    _psConfig = new ProcessSignConfiguration();
	    _ppConfig = new ProcessPrintConfiguration();
	    _pcConfig = new ProcessClassConfiguration();
	    _pvConfig = new ProcessViewConfiguration();
	}

	// -- (AZ) Fine Modifiche
	public SummaryState getSummaryState() {
	    return _summaryState;
	}

	public void setSummaryState(SummaryState value) {
	    _summaryState = value;
	}

	public boolean isSignEnabled() {
	    return _isSignEnabled;
	}

	public void setSignEnabled(boolean value) {
	    _isSignEnabled = value;
	}

	public ProcessSignConfiguration getProcessSignConfiguration() {
	    return _psConfig;
	}

	public void setProcessSignConfiguration(ProcessSignConfiguration config) {
	    _psConfig = config;
	}

	public ProcessPrintConfiguration getProcessPrintConfiguration() {
	    return _ppConfig;
	}

	public void setProcessPrintConfiguration(
		ProcessPrintConfiguration config) {
	    _ppConfig = config;
	}

	public ProcessClassConfiguration getProcessClassConfiguration() {
	    return _pcConfig;
	}

	public void setProcessClassConfiguration(
		ProcessClassConfiguration config) {
	    _pcConfig = config;
	}

	public ProcessViewConfiguration getProcessViewConfiguration() {
	    return _pvConfig;
	}

	public void setProcessViewConfiguration(ProcessViewConfiguration config) {
	    _pvConfig = config;
	}

	public void setPaymentObserver(String className) {
	    _paymentObserver = className;
	};
    }

    private static final String PATH_SEP = "/";
    @DeveloperTaskStart(name = "Riccardo Foraf�", date = "22.05.2011", bugDescription = "Nel metodo 'getCompletePath' il percorso restituito poteva contenere un doppio slash.", description = "Aggiunta la costante HTML_FOLDER con il solo slash iniziale.")
    private static final String HTML_FOLDER = "/html";
    @DeveloperTaskEnd(name = "Riccardo Foraf�", date = "22.05.2011")
    private static final String STATE_ACTIVE = "Active";
    private static final String STATE_INACTIVE = "Inactive";
    private static final String STATE_COMPLETED = "Completed";

    private static final String DEFAULT_PROCESS_CLASS = "it.people.process.GenericProcess";
    private static final String DEFAULT_PROCESS_VIEW = "it.people.process.view.GenericView";
    private static final String DEFAULT_PROCESS_SIGN = "it.people.process.sign.GenericSign";
    private static final String DEFAULT_PROCESS_PRINT = "it.people.process.print.GenericPrint";
    private static final String DEFAULT_COMPLETE_SIGNING_HTML_FRAGMENT_PRINT = "it.people.process.print.CompleSigningHtmlFragmentPrint";
    private static final String DEFAULT_PROCESS_SIGN_JSP = "/framework/sign/signPage.jsp";
    private static final String DEFAULT_FRONTEND_PACKAGE = "it.people.fsl";

    private static final String FWK_STEP_JSP_PAGES = "/framework/view/generic/";
    private static final String DEFAULT_REQUIRED_OPERATOR_DATA_STEP_JSP_PAGE = "/framework/view/generic/default/html/testStep.jsp";
    private static final String DEFAULT_PRIVACY_DISCLAIMER_STEP_JSP_PAGE = "/framework/view/generic/default/html/testStep.jsp";

    private static final String PROCESS = "PROCESS";
    private static final String PROCESS_CONFIG = "PROCESS-CONFIG";
    private static final String SIGN_ENABLED = "SIGN-ENABLED";
    private static final String SUMMARY_ENABLED = "SUMMARY-ENABLED";
    private static final String PAYMENT_OBSERVER = "PAYMENT-OBSERVER";
    private static final String SHOW_BACKEND_ERROR = "SHOW-BACKEND-ERROR";
    private static final String SAVE_BAR_ENABLED = "SAVE-BAR-ENABLED";
    private static final String NAVIGATION_BAR_ENABLED = "NAVIGATION-BAR-ENABLED";
    private static final String REDEFINED_SIGN_JSP = "REDEFINED-SIGN-JSP";
    private static final String REDEFINED_SIGN_PRINT_JSP = "REDEFINED-SIGN-PRINT-JSP";
    private static final String REDEFINED_CLASS = "REDEFINED-CLASS";
    private static final String PROCESS_CLASS = "PROCESS-CLASS";
    private static final String PROCESS_COMPONENTS = "PROCESS-COMPONENTS";
    private static final String PROCESS_VIEW = "PROCESS-VIEW";
    private static final String PROCESS_SIGN = "PROCESS-SIGN";
    private static final String PROCESS_PRINT = "PROCESS-PRINT";
    private static final String PROCESS_MODEL = "PROCESS-MODEL";
    private static final String VIEW = "VIEW";
    private static final String DEVICE = "device";
    private static final String CLASS = "class";
    private static final String ID = "id";
    private static final String DEFAULT = "default";
    private static final String NAME = "name";
    private static final String HOME = "home";
    private static final String ACTIVITY_ORDER = "activity-order";
    private static final String COMUNE = "comune";
    private static final String ACTIVITIES = "ACTIVITIES";
    private static final String ACTIVITY = "ACTIVITY";
    private static final String STATE = "state";
    private static final String CHECKMARK = "checkmark";
    private static final String STEP_ORDER = "step-order";
    private static final String STEPS = "STEPS";
    private static final String STEP = "STEP";
    private static final String STEP_VIEW = "view";
    private static final String HELP = "help";
    private static final String CONTROLLER = "controller";
    private static final String CLASSNAME = "classname";
    private static final String DTO = "dto";
    private static final String DTO_CLASS = "dto-class";

    private Node _root = null;
    private String _viewName = null;
    private ProcessConfiguration _processCfg = null;

    /**
     * Restituisce la root del documento xml
     * 
     * @param viewName
     * @return
     * @throws XMLWorkFlowException
     */
    private void getRootNode(String viewName, City commune)
	    throws XMLWorkFlowException {
	Document dom = readFile(viewName, commune);

	if (dom != null) {
	    if (dom.getChildNodes() == null
		    || dom.getChildNodes().getLength() == 0)
		throw new XMLWorkFlowException(
			"XMLWorkFlow.getView( "
				+ viewName
				+ " ) errore parserizzazione dom --> Il documento non ha nodi.");

	    NodeList nl = dom.getChildNodes();
	    for (int i = 0; i < nl.getLength(); i++) {
		Node n = nl.item(i);
		if (n != null && n.getNodeName() != null) {
		    String nodeName = n.getNodeName();
		    if (nodeName.equalsIgnoreCase(PROCESS))
			_root = n;
		}
	    }
	}

	if (_root == null)
	    throw new XMLWorkFlowException(
		    "XMLWorkFlow : cannot find roor node");
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.people.process.workflow.IWorkFlow#getProcess(java.lang.String,
     * it.people.City)
     */
    @Deprecated
    public IProcess getProcess(String name, City commune)
	    throws XMLWorkFlowException {

	return getProcess(null, name, commune);

    }

    /*
     * (non-Javadoc)
     * 
     * @see it.people.process.workflow.IWorkFlow#getProcess(javax.servlet.http.
     * HttpSession, java.lang.String, it.people.City)
     */
    public IProcess getProcess(HttpSession session, String name, City commune)
	    throws XMLWorkFlowException {

	// Alcune informazioni di configurazione devono essere lette da DB
	Service serviceConf = null;
	try {
	    serviceConf = (new ServiceFactory()).getService(name,
		    commune.getKey());
	} catch (PeopleDBException ex) {
	    throw new XMLWorkFlowException(
		    "Impossibile determinare la configurazione del servizio dal DB. Errore: "
			    + ex.getMessage());
	}

	// Altre dal workflow.xml
	Node processConfigNode = null;
	_viewName = name;

	getRootNode(name, commune);
	Process p = new Process();
	p.setName(name);

	NodeList nl = _root.getChildNodes();
	for (int i = 0; i < nl.getLength(); i++) {

	    Node n = nl.item(i);
	    if (n.getNodeName().equalsIgnoreCase(PROCESS_CONFIG)) {
		processConfigNode = n;
	    } else if (n.getNodeName().equalsIgnoreCase(VIEW)) {
		// IView v = getView( name, commune, n , serviceConf );
		IView v = getView(session, name, commune, n, serviceConf);
		p.setView(v);
	    }
	}

	setProcessConfig(processConfigNode, serviceConf, commune);
	setProcessComponentsAttributes(p, commune);

	setProcessSummary(p);
	checkPaymentObserverInterfaces(p);

	PrivacyStepHelper
		.initializePivacyStep(serviceConf, p, session, commune);

	return p;
    }

    /**
     * Verifica che IPaymentNotificationObserver o IPaymentObserver siano
     * istanziabili, se previsti
     * 
     * @param process
     * @throws XMLWorkFlowException
     */
    private void checkPaymentObserverInterfaces(Process process)
	    throws XMLWorkFlowException {
	String observerClassName = process.getPaymentObserverClass();
	if (observerClassName != null && observerClassName != "") {
	    boolean foundInterface = false;
	    try {
		Class observerClass = Class.forName(observerClassName);
		foundInterface = IPaymentObserver.class
			.isAssignableFrom(observerClass);
		if (!foundInterface)
		    foundInterface = IPaymentNotificationObserver.class
			    .isAssignableFrom(observerClass);
	    } catch (Exception e) {
		throw new XMLWorkFlowException(
			"XMLWorkflow : Errore nella dichiarazione del PaymentObserver nel nodo PAYMENT-OBSERVER, impossibile istanziare la classe: "
				+ observerClassName);
	    }
	    if (!foundInterface)
		throw new XMLWorkFlowException(
			"XMLWorkflow : Errore nella dichiarazione del PaymentObserver nel nodo PAYMENT-OBSERVER, impossibile istanziare la classe: "
				+ observerClassName);
	}
    }

    /**
     * Inserisce se necessario l'attivit� e lo step di riepilogo
     * 
     * @param process
     *            process a cui aggiungere l'attivit�
     */
    private void setProcessSummary(IProcess process) {
	IActivity summaryActivity = SummaryActivity.create(
		_processCfg.isSignEnabled(), _processCfg.getSummaryState(),
		process.getView());
	if (summaryActivity != null)
	    process.getView().getActivities().add(summaryActivity);
    }

    private void setProcessConfig(Node cfgNode, Service serviceConf, City comune) {
	_processCfg = new ProcessConfiguration();

	if (cfgNode != null) {
	    NodeList selNodes = null;
	    NodeList nl = cfgNode.getChildNodes();
	    for (int i = 0; i < nl.getLength(); i++) {
		Node child = nl.item(i);
		if (child.getNodeName().equalsIgnoreCase(DEFAULT)) {
		    selNodes = child.getChildNodes();
		} else if (child.getNodeName().equalsIgnoreCase(COMUNE)) {
		    String strComune = child.getAttributes().getNamedItem(ID)
			    .getNodeValue();
		    if (comune.getKey().equals(strComune)) {
			selNodes = child.getChildNodes();
		    }
		} else if (child.getNodeName().equals(PROCESS_SIGN)) {
		    setProcessSignConfig(child, comune);
		} else if (child.getNodeName().equals(PROCESS_PRINT)) {
		    setProcessPrintConfig(child, comune);
		} else if (child.getNodeName().equals(PROCESS_VIEW)) {
		    setProcessViewConfig(child, comune);
		} else if (child.getNodeName().equals(PROCESS_CLASS)) {
		    setProcessClassConfig(child, comune);
		}
	    }

	    // imposto i valori presenti nel nodo di configurazione
	    if (selNodes != null) {
		for (int i = 0; i < selNodes.getLength(); i++) {
		    Node child = selNodes.item(i);
		    if (child.getNodeName().equalsIgnoreCase(SIGN_ENABLED)) {
			String val = getNodeText(child);
			if (val != null && val.equals("true")) {
			    _processCfg.setSignEnabled(true);
			} else {
			    _processCfg.setSignEnabled(false);
			}
		    } else if (child.getNodeName().equalsIgnoreCase(
			    SUMMARY_ENABLED)) {
			String val = getNodeText(child);
			if (val != null
				&& val.equalsIgnoreCase(SummaryState.ALWAYS
					.getLabel())) {
			    _processCfg.setSummaryState(SummaryState.ALWAYS);
			} else if (val != null
				&& val.equalsIgnoreCase(SummaryState.FINALLY
					.getLabel())) {
			    _processCfg.setSummaryState(SummaryState.FINALLY);
			} else {
			    _processCfg.setSummaryState(SummaryState.NONE);
			}
		    } else if (child.getNodeName().equalsIgnoreCase(
			    PAYMENT_OBSERVER)) {
			String val = getNodeText(child);
			if (val != null) {
			    String observerClassName = _viewName
				    + ".controllers." + val;
			    _processCfg.setPaymentObserver(observerClassName);
			} else {
			    _processCfg.setPaymentObserver("");
			}
		    }
		}
	    }
	}

	// Le impostazioni lette da DB hanno la priorit�
	_processCfg.setSignEnabled(serviceConf.isSignEnabled());
    }

    private void setProcessSignConfig(Node n, City comune) {
	NodeList nl = n.getChildNodes();
	NodeList selNodes = null;
	String strComune = null;
	for (int i = 0; i < nl.getLength(); i++) {
	    Node child = nl.item(i);
	    if (child.getNodeName().equalsIgnoreCase(DEFAULT)) {
		selNodes = child.getChildNodes();
	    } else if (child.getNodeName().equalsIgnoreCase(COMUNE)) {
		strComune = child.getAttributes().getNamedItem(ID)
			.getNodeValue();
		if (comune.getKey().equals(strComune)) {
		    selNodes = child.getChildNodes();
		}
	    }
	}

	for (int i = 0; i < selNodes.getLength(); i++) {
	    Node child = selNodes.item(i);
	    if (child.getNodeName().equalsIgnoreCase(REDEFINED_SIGN_JSP)) {
		String val = getNodeText(child);
		if (val != null) {
		    _processCfg.getProcessSignConfiguration().setJspRedefined(
			    val.equals("true"));
		    if (strComune != null) {
			_processCfg.getProcessSignConfiguration()
				.setRelativePath("/" + strComune + "/html");
		    } else {
			_processCfg.getProcessSignConfiguration()
				.setRelativePath("/" + DEFAULT + "/html");
		    }
		}
	    }
	    if (child.getNodeName().equalsIgnoreCase(REDEFINED_SIGN_PRINT_JSP)) {
		String val = getNodeText(child);
		if (val != null) {
		    _processCfg.getProcessSignConfiguration()
			    .setJspPrintRedefined(val.equals("true"));
		    if (strComune != null) {
			_processCfg
				.getProcessSignConfiguration()
				.setRelativePrintPath("/" + strComune + "/html");
		    } else {
			_processCfg.getProcessSignConfiguration()
				.setRelativePrintPath("/" + DEFAULT + "/html");
		    }
		}
	    }
	}
    }

    private void setProcessPrintConfig(Node n, City comune) {
	NodeList nl = n.getChildNodes();
	NodeList selNodes = null;
	String strComune = null;
	for (int i = 0; i < nl.getLength(); i++) {
	    Node child = nl.item(i);
	    if (child.getNodeName().equalsIgnoreCase(DEFAULT)) {
		selNodes = child.getChildNodes();
	    } else if (child.getNodeName().equalsIgnoreCase(COMUNE)) {
		strComune = child.getAttributes().getNamedItem(ID)
			.getNodeValue();
		if (comune.getKey().equals(strComune)) {
		    selNodes = child.getChildNodes();
		}
	    }
	}

	for (int i = 0; i < selNodes.getLength(); i++) {
	    Node child = selNodes.item(i);
	    if (child.getNodeName().equalsIgnoreCase(REDEFINED_CLASS)) {
		String val = getNodeText(child);
		if (val != null) {
		    _processCfg.getProcessPrintConfiguration()
			    .setClassRedefined(val.equals("true"));
		}
	    }
	}
    }

    private void setProcessViewConfig(Node n, City comune) {
	NodeList nl = n.getChildNodes();
	NodeList selNodes = null;
	String strComune = null;
	for (int i = 0; i < nl.getLength(); i++) {
	    Node child = nl.item(i);
	    if (child.getNodeName().equalsIgnoreCase(DEFAULT)) {
		selNodes = child.getChildNodes();
	    } else if (child.getNodeName().equalsIgnoreCase(COMUNE)) {
		strComune = child.getAttributes().getNamedItem(ID)
			.getNodeValue();
		if (comune.getKey().equals(strComune)) {
		    selNodes = child.getChildNodes();
		}
	    }
	}

	for (int i = 0; i < selNodes.getLength(); i++) {
	    Node child = selNodes.item(i);
	    if (child.getNodeName().equalsIgnoreCase(REDEFINED_CLASS)) {
		String val = getNodeText(child);
		if (val != null) {
		    _processCfg.getProcessViewConfiguration()
			    .setClassRedefined(val.equals("true"));
		}
	    } else if (child.getNodeName().equalsIgnoreCase(SAVE_BAR_ENABLED)) {
		String val = getNodeText(child);
		if (val != null && val.equals("true")) {
		    _processCfg.getProcessViewConfiguration()
			    .setBottomSaveBarEnabled(true);
		} else {
		    _processCfg.getProcessViewConfiguration()
			    .setBottomSaveBarEnabled(false);
		}
	    } else if (child.getNodeName().equalsIgnoreCase(
		    NAVIGATION_BAR_ENABLED)) {
		String val = getNodeText(child);
		if (val != null && val.equals("true")) {
		    _processCfg.getProcessViewConfiguration()
			    .setBottomNavigationBarEnabled(true);
		} else {
		    _processCfg.getProcessViewConfiguration()
			    .setBottomNavigationBarEnabled(false);
		}
	    } else if (child.getNodeName().equalsIgnoreCase(SHOW_BACKEND_ERROR)) {
		String val = getNodeText(child);
		_processCfg.getProcessViewConfiguration().setShowBackEndError(
			val != null && val.equals("true"));
	    }
	}
    }

    private void setProcessClassConfig(Node n, City comune) {
	NodeList nl = n.getChildNodes();
	NodeList selNodes = null;
	String strComune = null;
	for (int i = 0; i < nl.getLength(); i++) {
	    Node child = nl.item(i);
	    if (child.getNodeName().equalsIgnoreCase(DEFAULT)) {
		selNodes = child.getChildNodes();
	    } else if (child.getNodeName().equalsIgnoreCase(COMUNE)) {
		strComune = child.getAttributes().getNamedItem(ID)
			.getNodeValue();
		if (comune.getKey().equals(strComune)) {
		    selNodes = child.getChildNodes();
		}
	    }
	}

	for (int i = 0; i < selNodes.getLength(); i++) {
	    Node child = selNodes.item(i);
	    if (child.getNodeName().equalsIgnoreCase(REDEFINED_CLASS)) {
		String val = getNodeText(child);
		if (val != null) {
		    _processCfg.getProcessClassConfiguration()
			    .setClassRedefined(val.equals("true"));
		}
	    }
	}
    }

    /**
     * Imposta i componenti di un processo leggendoli dal dom xml.
     * 
     * @param p
     * @param processComponentsNode
     * @param comune
     * @throws XMLWorkFlowException
     */
    private void setProcessComponentsAttributes(IProcess p, City comune)
	    throws XMLWorkFlowException {

	p.setProcessClass(getDefaultProcessClass());
	p.setProcessView(getDefaultProcessView());

	ConcreteSign className = getDefaultProcessSign();
	if (className != null) {
	    p.setProcessSign(className);
	}

	HashMap m = getDefaultProcessPrint();
	if (m != null) {
	    p.setProcessPrint(m);
	}

	p.setProcessModel(getDefaultProcessData());

	if (this._processCfg._paymentObserver != null) {
	    p.setPaymentObserverClass(this._processCfg._paymentObserver);
	}
    }

    /**
     * 
     * @return
     */
    private String getDefaultProcessClass() throws XMLWorkFlowException {
	String pcClassName = DEFAULT_PROCESS_CLASS;

	if (_processCfg.getProcessClassConfiguration() == null)
	    return pcClassName;

	if (_processCfg.getProcessClassConfiguration().isClassRedefined()) {
	    pcClassName = _viewName + ".Process";
	    try {
		Class c = Class.forName(pcClassName);
	    } catch (ClassNotFoundException e) {
		throw new XMLWorkFlowException(
			"XMLWorkFlow :Impossibile caricare ProcessClass specifica. Path: "
				+ pcClassName);
	    }
	}

	return pcClassName;
    }

    /**
     * @return
     */
    private ConcreteView getDefaultProcessView() throws XMLWorkFlowException {
	String pvClassName = DEFAULT_PROCESS_VIEW;

	ConcreteView concreteView = null;
	if (_processCfg.getProcessViewConfiguration() != null) {
	    if (_processCfg.getProcessViewConfiguration().isClassRedefined()) {
		pvClassName = _viewName + ".ProcessView";
	    }
	}
	try {
	    Class c = Class.forName(pvClassName);
	    concreteView = (ConcreteView) c.newInstance();
	} catch (Exception e) {
	    throw new XMLWorkFlowException(
		    "XMLWorkFlow :Impossibile caricare ProcessView specifica. Path: "
			    + pvClassName);
	}

	concreteView.setBottomNavigationBarEnabled(_processCfg
		.getProcessViewConfiguration().isBottomNavigationBarEnabled());
	concreteView.setBottomSaveBarEnabled(_processCfg
		.getProcessViewConfiguration().isBottomSaveBarEnabled());
	concreteView.setShowBackEndError(_processCfg
		.getProcessViewConfiguration().isShowBackEndError());
	return concreteView;
    }

    /**
     * 
     * @return
     */
    private ConcreteSign getDefaultProcessSign() throws XMLWorkFlowException {
	String psClassName = DEFAULT_PROCESS_SIGN;
	String psJspPage = DEFAULT_PROCESS_SIGN_JSP;
	ConcreteSign sign = null;

	if (!_processCfg.isSignEnabled())
	    return null;

	if (_processCfg.getProcessSignConfiguration() != null) {
	    if (_processCfg.getProcessSignConfiguration().isClassRedefined()) {
		psClassName = _viewName + ".ProcessSign";
	    }

	    if (_processCfg.getProcessSignConfiguration().isJspRedefined()) {
		psJspPage = getDefaultJspFolder()
			+ "/sign"
			+ _processCfg.getProcessSignConfiguration()
				.getRelativePath() + "/signPage.jsp";
	    }

	}

	try {
	    Class c = Class.forName(psClassName);
	    sign = (ConcreteSign) c.newInstance();
	} catch (Exception e) {
	    String message = e.getMessage();
	    throw new XMLWorkFlowException(
		    "XMLWorkFlow :Impossibile instanziare classe specifica "
			    + psClassName + ". Errore: " + message);
	}

	sign.setSignPage(psJspPage);
	return sign;
    }

    /**
     * 
     * @return
     */
    private HashMap getDefaultProcessPrint() {
	HashMap m = new HashMap();
	String ppClassName = DEFAULT_PROCESS_PRINT;
	String cshfpClassName = DEFAULT_COMPLETE_SIGNING_HTML_FRAGMENT_PRINT;
	ConcretePrint print = null;
	ConcretePrint cshfpPrint = null;
	try {

	    if (_processCfg.getProcessSignConfiguration() != null) {
		if (_processCfg.getProcessSignConfiguration()
			.isClassRedefined()) {
		    ppClassName = _viewName + ".ProcessPrint";
		    Class c = Class.forName(ppClassName);
		    print = (ConcretePrint) c.newInstance();
		} else {
		    Class c = Class.forName(ppClassName);
		    print = (ConcretePrint) c.newInstance();
		}
	    }

	    String pPage = getDefaultJspFolder() + "/sign/" + DEFAULT
		    + "/html/signPrintPage.jsp";
	    if (_processCfg.getProcessSignConfiguration() != null) {
		if (_processCfg.getProcessSignConfiguration()
			.isJspPrintRedefined()) {
		    pPage = getDefaultJspFolder()
			    + "/sign"
			    + _processCfg.getProcessSignConfiguration()
				    .getRelativePrintPath()
			    + "/signPrintPage.jsp";
		}
	    }

	    print.setPrintPage(pPage);
	    m.put("html", print);

	    /*
	     * Add default printer to get complete signing data fragment TODO
	     * Add to workflow a new tag for defining specific fragment printer
	     */
	    if (_processCfg.getProcessSignConfiguration() != null) {
		// if (
		// _processCfg.getProcessSignConfiguration().isClassRedefined()
		// ) {
		// ppClassName = _viewName + ".ProcessPrint";
		// Class c = Class.forName( ppClassName );
		// print = (ConcretePrint) c.newInstance();
		// }
		// else {
		Class cCshfp = Class.forName(cshfpClassName);
		cshfpPrint = (ConcretePrint) cCshfp.newInstance();
		// }
	    }

	    cshfpPrint.setPrintPage(pPage);
	    m.put(MimeType.COMPLETE_HTML.getKey(), cshfpPrint);

	} catch (Exception e) {
	    e.printStackTrace();
	    m = null;
	}

	return m;
    }

    /**
     * 
     * @return
     * @throws XMLWorkFlowException
     */
    private String getDefaultProcessData() throws XMLWorkFlowException {
	String pdClassName = _viewName + ".model.ProcessData";
	try {
	    Class c = Class.forName(pdClassName);
	} catch (ClassNotFoundException e) {
	    throw new XMLWorkFlowException("Process : " + _viewName
		    + ". Classe " + pdClassName + " non trovata.");
	}

	return pdClassName;
    }

    /**
     * Ritorna una HashMap contenente i processi di stampa da utilizzare a
     * seconda del tipo di stampa.
     * 
     * @param n
     * @param comune
     * @return
     */
    /*
     * private HashMap getProcessPrintValue( Node n, City comune ) { HashMap map
     * = new HashMap();
     * 
     * NodeList nl = n.getChildNodes(); NodeList selNodes = getSpecificNodeList(
     * nl, comune );
     * 
     * if ( selNodes != null ) { for ( int i = 0; i < selNodes.getLength(); i++
     * ) { Node child = selNodes.item( i ); if (
     * child.getNodeName().equalsIgnoreCase( DEVICE ) ) {
     * 
     * String key = null; String value = null; NamedNodeMap nnm =
     * child.getAttributes(); for ( int j = 0; j < nnm.getLength(); j++ ) { Node
     * node = nnm.item( j ); if ( node != null && node.getNodeName().equals(
     * NAME ) ) { key = node.getNodeValue(); } else if ( node != null &&
     * node.getNodeName().equals( CLASS ) ) { value = node.getNodeValue(); } }
     * 
     * map.put( key, value ); } } }
     * 
     * return map; }
     * 
     * /** Restituisce la classe del processo ( Sign, Model, View ) da
     * utilizzare
     * 
     * @param n
     * 
     * @param comune
     * 
     * @return
     */
    /*
     * private String getProcessComponentValue( Node n, City comune ) { String
     * value = null;
     * 
     * NodeList nl = n.getChildNodes(); NodeList selNodes = getSpecificNodeList(
     * nl, comune );
     * 
     * if ( selNodes != null ) { for ( int i = 0; i < selNodes.getLength(); i++
     * ) { Node child = selNodes.item( i ); if (
     * child.getNodeName().equalsIgnoreCase( NAME ) ) { value = getNodeText(
     * child ); return value; } } }
     * 
     * return value; }
     */

    /**
     * Costruisce un oggetto che estende l'interfaccia IView da un file xml che
     * ne definisce le caratteristiche quali le attivit� e gli step.
     * L'interfaccia IView definisce un modello del workflow di visualizzazione
     * della parte di presentation di un processo.
     * 
     * @param viewName
     *            , comune
     * @return
     * @throws XMLWorkFlowException
     */
    private IView getView(String viewName, City comune, Node rootView,
	    Service serviceConf) throws XMLWorkFlowException {
	return getView(null, viewName, comune, rootView, serviceConf);
    }

    /**
     * Costruisce un oggetto che estende l'interfaccia IView da un file xml che
     * ne definisce le caratteristiche quali le attivit� e gli step.
     * L'interfaccia IView definisce un modello del workflow di visualizzazione
     * della parte di presentation di un processo.
     * 
     * @param viewName
     *            , comune
     * @return
     * @throws XMLWorkFlowException
     */
    // private IView getView( String viewName, City comune, Node rootView )
    private IView getView(HttpSession session, String viewName, City comune,
	    Node rootView, Service serviceConf) throws XMLWorkFlowException {

	IView v = null;

	if (rootView != null && rootView.getNodeName().equalsIgnoreCase(VIEW)) {
	    v = new View();

	    // Leggo id della VIEW
	    NamedNodeMap nnm = rootView.getAttributes();
	    for (int i = 0; i < nnm.getLength(); i++) {
		Node n = nnm.item(i);
		if (n != null && n.getNodeName().equals(ID)) {
		    v.setId(n.getNodeValue());
		}
	    }

	    if (v.getId() == null) {
		System.out.println("Errore id VIEW obbligatorio. file "
			+ viewName);
	    }

	    // Leggo i figli della VIEW
	    NodeList nl = rootView.getChildNodes();
	    Node activities = null;
	    for (int i = 0; i < nl.getLength(); i++) {
		Node n = nl.item(i);
		if (n != null && n.getNodeName() != null) {
		    String nodeName = n.getNodeName();
		    if (nodeName.equals(DEFAULT)) {
			NodeList children = n.getChildNodes();
			setViewAttributes(v, children);
		    } else if (nodeName.equals(COMUNE)) {
			String strComune = n.getAttributes().getNamedItem(ID)
				.getNodeValue();

			if (comune.getKey().equals(strComune)) {
			    NodeList children = n.getChildNodes();
			    setViewAttributes(v, children);
			}
		    } else if (nodeName.equals(ACTIVITIES)) {
			activities = n;
		    }
		}
	    }

	    // Leggo le activity
	    if (activities != null) {
		ArrayList alActivities = new ArrayList();
		NodeList activityList = activities.getChildNodes();

		int lun = activityList.getLength();
		if (lun == 0) {
		    System.out
			    .println("Errore nodo ACTIVITES non contiene nodi ACTIVITY. file "
				    + viewName);
		}

		for (int i = 0; i < lun; i++) {
		    IActivity activity = null;
		    Node activityNode = activityList.item(i);
		    if (activityNode != null
			    && activityNode.getNodeName().equals(ACTIVITY)) {

			// Leggo id della ACTIVITY
			boolean haveToSkip = true;
			NamedNodeMap map = activityNode.getAttributes();
			for (int j = 0; j < map.getLength(); j++) {
			    Node n = map.item(j);
			    if (n != null && n.getNodeName().equals(ID)) {

				String activityId = n.getNodeValue();
				String[] activityOrder = v.getActivityOrder();
				for (int o = 0; o < activityOrder.length; o++) {
				    if (activityOrder[o].equals(activityId)) {
					activity = new Activity();
					activity.setId(activityId);
					haveToSkip = false;
				    }
				}

			    }
			}

			if (!haveToSkip) {
			    if (activity.getId() == null) {
				System.out
					.println("Errore id ACTIVITY obbligatorio. file "
						+ viewName);
			    }

			    // Leggo i figli della ACTIVITY
			    NodeList childrenActivity = activityNode
				    .getChildNodes();
			    Node stepsNode = null;
			    for (int k = 0; k < childrenActivity.getLength(); k++) {
				Node n = childrenActivity.item(k);
				if (n != null && n.getNodeName() != null) {
				    String nodeName = n.getNodeName();
				    if (nodeName.equals(DEFAULT)) {
					NodeList children = n.getChildNodes();
					setActivityAttributes(activity,
						children);
				    } else if (nodeName.equals(COMUNE)) {
					String strComune = n.getAttributes()
						.getNamedItem(ID)
						.getNodeValue();

					if (comune.getKey().equals(strComune)) {
					    NodeList children = n
						    .getChildNodes();
					    setActivityAttributes(activity,
						    children);
					}
				    } else if (nodeName.equals(STEPS)) {
					stepsNode = n;
				    }
				}
			    }

			    // Leggo gli steps
			    if (stepsNode != null) {
				ArrayList alSteps = new ArrayList();
				NodeList stepList = stepsNode.getChildNodes();

				int lunSteps = stepList.getLength();
				if (lunSteps == 0) {
				    System.out
					    .println("Errore nodo STEPS non contiene nodi STEP. Activity "
						    + activity.getName()
						    + " file " + viewName);
				}

				for (int idx = 0; idx < lunSteps; idx++) {
				    IStep step = null;
				    Node stepNode = stepList.item(idx);
				    if (stepNode != null
					    && stepNode.getNodeName().equals(
						    STEP)) {

					// Leggo id dello STEP
					boolean haveToSkipStep = true;
					NamedNodeMap mapStep = stepNode
						.getAttributes();
					for (int j = 0; j < mapStep.getLength(); j++) {
					    Node n = mapStep.item(j);
					    if (n != null
						    && n.getNodeName().equals(
							    ID)) {
						String stepId = n
							.getNodeValue();
						String[] stepOrder = activity
							.getStepOrder();
						for (int o = 0; o < stepOrder.length; o++) {
						    if (stepOrder[o]
							    .equals(stepId)) {
							step = new Step();
							step.setId(stepId);
							step.setParentView(v);
							haveToSkipStep = false;
						    }
						}
					    }
					}

					if (!haveToSkipStep) {
					    // Leggo i figli dello STEP
					    NodeList childrenStep = stepNode
						    .getChildNodes();
					    for (int k = 0; k < childrenStep
						    .getLength(); k++) {
						boolean isFakade = true;
						String stepClass = null;
						Node n = childrenStep.item(k);
						if (n != null
							&& n.getNodeName() != null) {
						    String nodeName = n
							    .getNodeName();
						    if (nodeName
							    .equals(DEFAULT)) {
							NodeList children = n
								.getChildNodes();
							// stepClass =
							// setStepAttributes(
							// viewName, v,
							// step, children, null
							// );
							stepClass = setStepAttributes(
								session,
								viewName,
								v,
								activity.getId(),
								step, children,
								null,
								serviceConf);
							isFakade = false;
						    } else if (nodeName
							    .equals(COMUNE)) {
							String strComune = n
								.getAttributes()
								.getNamedItem(
									ID)
								.getNodeValue();
							if (comune
								.getKey()
								.equals(strComune)) {
							    NodeList children = n
								    .getChildNodes();
							    // stepClass =
							    // setStepAttributes(
							    // viewName, v,
							    // step, children,
							    // strComune);
							    stepClass = setStepAttributes(
								    session,
								    viewName,
								    v,
								    activity.getId(),
								    step,
								    children,
								    strComune,
								    serviceConf);
							    isFakade = false;
							}
						    }
						}

						if (!isFakade) {
						    if (stepClass != null) {
							try {
							    IStep specStep = (IStep) Class
								    .forName(
									    stepClass)
								    .newInstance();
							    specStep.setId(step
								    .getId());
							    specStep.setParentView(step
								    .getParentView());
							    specStep.setName(step
								    .getName());
							    specStep.setJspPath(step
								    .getJspPath());
							    specStep.setState(step
								    .getState());
							    specStep.setHelpUrl(step
								    .getHelpUrl());
							    specStep.setDto(step
								    .getDto());
							    specStep.setAccessController(step
								    .getAccessController());
							    alSteps.add(specStep);
							} catch (Exception e) {
							    throw new XMLWorkFlowException(
								    "XMLWorkflow : Errore instanziazione Step specifico. Nome Step "
									    + step.getName());
							}

						    } else {
							alSteps.add(step);
						    }
						}
					    }
					}
				    }
				}

				activity.setStepList(alSteps);
			    }
			    alActivities.add(activity);
			}
		    }
		}
		v.setActivities(alActivities);
	    } else {
		System.out
			.println("Errore il nodo VIEW non contiene nodo ACTIVITIES file "
				+ viewName);
	    }
	}

	return v;
    }

    /**
     * Legge un file contenente un dom xml.
     * 
     * @param viewName
     * @return
     * @throws XMLWorkFlowException
     */
    private Document readFile(String viewName, City commune)
	    throws XMLWorkFlowException {

	String middlePath = PATH_SEP + viewName.replaceAll("\\.", "/");
	String finalPath = PATH_SEP + "risorse";
	String defaultFileName = PATH_SEP + "workflow.xml";
	String specializedFileName = PATH_SEP + "workflow_" + commune.getKey()
		+ ".xml";

	String path = middlePath + finalPath + specializedFileName;
	InputStream is = this.getClass().getResourceAsStream(path);
	if (is == null) {
	    path = middlePath + finalPath + defaultFileName;
	    is = this.getClass().getResourceAsStream(path);
	    if (is == null)
		throw new XMLWorkFlowException(
			"XMLWorkFlow.getView( "
				+ viewName
				+ " ): errore creazione dom, impossibile trovare il file: \""
				+ path + "\"");
	}

	InputSource in = new InputSource(is);
	DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();

	Document dom = null;
	try {
	    dfactory.setIgnoringComments(true);
	    dom = dfactory.newDocumentBuilder().parse(in);
	} catch (SAXException ex) {
	    throw new XMLWorkFlowException("XMLWorkFlow.getView( " + viewName
		    + " ) errore creazione dom (SAXException) -->"
		    + ex.getMessage());
	} catch (IOException ex) {
	    throw new XMLWorkFlowException("XMLWorkFlow.getView( " + viewName
		    + " ) errore creazione dom (IOException) -->"
		    + ex.getMessage());
	} catch (ParserConfigurationException ex) {
	    throw new XMLWorkFlowException(
		    "XMLWorkFlow.getView( "
			    + viewName
			    + " ) errore creazione dom (ParserConfigurationException) -->"
			    + ex.getMessage());
	} finally {
	    if (is != null)
		try {
		    is.close();
		} catch (Exception ex) {
		}
	    ;
	}

	return dom;
    }

    /**
     * Imposta gli attributi di un oggetto di tipo IView
     * 
     * @param v
     * @param nl
     */
    private void setViewAttributes(IView v, NodeList nl)
	    throws XMLWorkFlowException {
	boolean hasName = false;
	boolean hasHome = false;
	boolean hasOrder = false;

	for (int j = 0; j < nl.getLength(); j++) {
	    Node child = nl.item(j);
	    if (child != null && child.getNodeName() != null) {
		String childName = child.getNodeName();
		if (childName.equals(NAME)) {
		    v.setName(getNodeText(child));
		    hasName = true;
		} else if (childName.equals(HOME)) {
		    v.setHome(getNodeText(child));
		    hasHome = true;
		} else if (childName.equals(ACTIVITY_ORDER)) {
		    v.setActivityOrder(getNodeText(child));
		    hasOrder = true;
		}
	    }
	}

	// Obbligatori
	if (!hasName)
	    throw new XMLWorkFlowException(
		    "Il nodo <name> di una View � obbligatorio.");
	if (!hasOrder)
	    throw new XMLWorkFlowException(
		    "Il nodo <activty-order> della view " + v.getName()
			    + " � obbligatorio.");

	// Facoltativi
	if (!hasHome) {
	    String defaultHome = getDefaultJspFolder() + "/view";
	    v.setHome(defaultHome);
	}
    }

    /**
     * . Il path restituito � composto da
     * 
     * /servizi/area tematica/sottoarea tematica/nome del processo
     * 
     * In questa cartella si trovano le seguenti sottocartelle : /view che
     * contiene le pagine del processo di view /print che contiene le pagina del
     * processo di print /sign che contiene le pagine per il processo di firma
     * 
     * @return
     */
    private String getDefaultJspFolder() {

	String tempHome = _viewName.replaceFirst(DEFAULT_FRONTEND_PACKAGE, "");
	String defaultHome = tempHome.replaceAll("\\.", "/");
	return defaultHome;
    }

    /**
     * Imposta gli attributi di un oggetto di tipo IActivity
     * 
     * @param a
     * @param nl
     */
    private void setActivityAttributes(IActivity a, NodeList nl)
	    throws XMLWorkFlowException {
	boolean hasName = false;
	boolean hasState = false;
	boolean hasCheck = false;
	boolean hasOrder = false;

	for (int j = 0; j < nl.getLength(); j++) {
	    Node child = nl.item(j);
	    if (child != null && child.getNodeName() != null) {
		String childName = child.getNodeName();
		if (childName.equals(NAME)) {
		    a.setName(getNodeText(child));
		    hasName = true;
		} else if (childName.equals(STATE)) {
		    String str = getNodeText(child);
		    if (str.equalsIgnoreCase(STATE_ACTIVE)) {
			a.setState(ActivityState.ACTIVE);
		    } else if (str.equalsIgnoreCase(STATE_INACTIVE)) {
			a.setState(ActivityState.INACTIVE);
		    } else if (str.equalsIgnoreCase(STATE_COMPLETED)) {
			a.setState(ActivityState.COMPLETED);
		    }
		    hasState = true;
		} else if (childName.equals(CHECKMARK)) {
		    String str = getNodeText(child);
		    boolean b = false;
		    if (str.equalsIgnoreCase("true"))
			b = true;
		    a.setShowCheck(b);
		    hasCheck = true;
		} else if (childName.equals(STEP_ORDER)) {
		    a.setStepOrder(getNodeText(child));
		    a.setCurrentStepIndex(0);
		    hasOrder = true;
		}
	    }
	}

	// Obbligatori
	if (!hasName)
	    throw new XMLWorkFlowException(
		    "Il nodo <name> di una Activity � obbligatorio.");
	if (!hasOrder)
	    throw new XMLWorkFlowException(
		    "Il nodo <step-order> dell'activity " + a.getName()
			    + " � obbligatorio.");

	// Opzionali
	if (!hasState)
	    a.setState(ActivityState.ACTIVE);
	if (!hasCheck)
	    a.setShowCheck(true);

    }

    @DeveloperTaskStart(name = "Riccardo Foraf�", date = "23.05.2011", bugDescription = "Il path per l'help era precedentemente calcolato con il metodo 'getCompletePath', "
	    + "ma questo metodo restituisce sempre il path con lo slash iniziale, mentre per l'url "
	    + "delle pagine di help non deve esserci in modo da sfruttare il contesto attuale.", description = "Sostituita la chiamata del metodo 'getCompletePath' con la chiamata al metodo "
	    + "'getUrlCompletePath' il quale richiama il metodo "
	    + " 'getCompletePath e poi elimina lo slash iniziale se presente.'")
    @DeveloperTaskEnd(name = "Riccardo Foraf�", date = "23.05.2011")
    /**
     * Imposta gli attributi di un oggettto IStep
     * @param viewName
     * @param v
     * @param s
     * @param nl
     * @param comune
     * @return
     * @throws XMLWorkFlowException
     */
    // private String setStepAttributes( String viewName, IView v, IStep s,
    // NodeList nl, String comune ) throws XMLWorkFlowException {
    private String setStepAttributes(HttpSession session, String viewName,
	    IView v, String activityId, IStep s, NodeList nl, String comune,
	    Service serviceConf) throws XMLWorkFlowException {
	boolean hasName = false;
	boolean hasView = false;
	boolean hasHelp = false;
	boolean hasState = false;

	if (logger.isDebugEnabled()) {
	    logger.debug("Verifying if step has on line help on db...");
	}
	boolean hasOnLineHelpOnDb = StepsFactory.hasOnLineHelpOnDb(
		serviceConf.getCommuneId(), serviceConf.getProcessName(),
		v.getName(), activityId, s.getId());
	if (logger.isDebugEnabled()) {
	    if (hasOnLineHelpOnDb) {
		logger.debug("Step has on line help on db.");
	    } else {
		logger.debug("Step doesn't have on line help on db.");
	    }
	}

	String className = null;
	for (int j = 0; j < nl.getLength(); j++) {
	    Node child = nl.item(j);
	    if (child != null && child.getNodeName() != null) {
		String childName = child.getNodeName();
		if (childName.equals(NAME)) {
		    s.setName(getNodeText(child));
		    hasName = true;
		} else if (childName.equals(STEP_VIEW)) {
		    hasView = true;
		    String path = getCompletePath(session, v.getHome(), comune,
			    getNodeText(child));
		    s.setJspPath(path);
		} else if (childName.equals(HELP)) {
		    hasHelp = true;
		    String path = getUrlCompletePath(session, v.getHome(),
			    comune, getNodeText(child));
		    /*
		     * If hasOnLineHelpOnDb is true on line help content is
		     * stored on fedb and managed with the People Console, so
		     * teh value must be IStep.READ_ON_LINE_HELP_FROM_DB. Since
		     * we don't want to change how the workflow is read but we
		     * want to allow the management from the People Console, if
		     * the step on line help is defined and active on the fe db
		     * we force the state for the step.
		     */
		    // s.setHelpUrl( path );
		    s.setHelpUrl((hasOnLineHelpOnDb) ? IStep.READ_ON_LINE_HELP_FROM_DB
			    : path);
		} else if (childName.equals(STATE)) {
		    hasState = true;
		    String str = getNodeText(child);
		    if (str.equalsIgnoreCase(STATE_ACTIVE)) {
			s.setState(StepState.ACTIVE);
		    } else if (str.equalsIgnoreCase(STATE_COMPLETED)) {
			s.setState(StepState.COMPLETED);
		    }
		} else if (childName.equals(DTO)) {

		    String dto = getNodeText(child);
		    String dtoClassName = _viewName + ".dto." + dto;
		    s.setDto(dtoClassName);
		} else if (childName.equals(DTO_CLASS)) {
		    String dto = getNodeText(child);
		    s.setDto(dto);
		} else if (childName.equals(CONTROLLER)) {
		    String controller = getNodeText(child);
		    String controllerClassName = _viewName + ".controllers."
			    + controller;
		    try {
			WorkflowController wc = (WorkflowController) Class
				.forName(controllerClassName).newInstance();
			s.setAccessController(wc);
		    } catch (Exception e) {
			throw new XMLWorkFlowException(
				"XMLWorkflow : Errore instanziazione WorkflowController Step "
					+ s.getName());
		    }
		} else if (childName.equals(CLASSNAME)) {
		    className = _viewName + ".steps." + getNodeText(child);
		}
	    }
	}

	/*
	 * If hasOnLineHelpOnDb is true the on line help content is stored on
	 * fedb and managed with the People Console. If the step has no help
	 * configured into the workflow file but hasOnLineHelpOnDb is true,
	 * value for the step help must be IStep.READ_ON_LINE_HELP_FROM_DB.
	 * Since we don't want to change how the workflow is read but we want to
	 * allow the management from the People Console, if the step on line
	 * help is defined and active on the fe db we force the state for the
	 * step.
	 */
	if (logger.isDebugEnabled()) {
	    logger.debug("Verifying if the workflow file has already configured the step help...");
	}
	if (!hasHelp && hasOnLineHelpOnDb) {
	    if (logger.isDebugEnabled()) {
		logger.debug("The workflow file state nothing about the step help state, but the the step has on line help on db, so force the help to the correct value.");
	    }
	    s.setHelpUrl(IStep.READ_ON_LINE_HELP_FROM_DB);
	}
	if (logger.isDebugEnabled()) {
	    logger.debug("The workflow file has already configured the step help or both the workflow file state nothing and the step hasn't on line help on db.");
	}

	// Obbligatori
	if (!hasName)
	    throw new XMLWorkFlowException(
		    "Il nodo <name> di uno Step � obbligatorio.");
	if (!hasView)
	    throw new XMLWorkFlowException("Il nodo <view> dello step "
		    + s.getName() + " � obbligatorio.");

	// Opzionali
	if (!hasHelp && !hasOnLineHelpOnDb)
	    s.setHelpUrl("");
	if (!hasState)
	    s.setState(StepState.ACTIVE);

	return className;
    }

    /**
     * Ottiene il valore da un nodo xml di tipo text <NODO>valore</NODO>
     * 
     * @param n
     * @return
     */
    private String getNodeText(Node n) {
	String val = null;

	NodeList nl = n.getChildNodes();
	for (int i = 0; i < nl.getLength(); i++) {
	    Node child = nl.item(i);
	    if (child.getNodeType() == Node.TEXT_NODE) {
		val = child.getNodeValue();
		break;
	    }
	}

	return val;
    }

    private NodeList getSpecificNodeList(NodeList nl, City comune) {
	NodeList selNodes = null;

	for (int i = 0; i < nl.getLength(); i++) {
	    Node child = nl.item(i);
	    if (child.getNodeName().equalsIgnoreCase(DEFAULT)) {
		selNodes = child.getChildNodes();
	    } else if (child.getNodeName().equalsIgnoreCase(COMUNE)) {
		String strComune = child.getAttributes().getNamedItem(ID)
			.getNodeValue();
		if (comune.getKey().equals(strComune)) {
		    selNodes = child.getChildNodes();
		}
	    }
	}

	return selNodes;
    }

    /**
     * Restituisce un path relativo al contesto per i file di help, controllando
     * il corretto utilizzo degli slash.
     * 
     * @param first
     * @param center
     * @param last
     * @see it.people.process.workflow.xml.XMLWorkFlow#getCompletePath(String,
     *      String, String)
     * @return
     */
    @DeveloperTaskStart(name = "Riccardo Foraf�", date = "23.05.2011", bugDescription = "Il path era precedentemente calcolato con il metodo 'getCompletePath', "
	    + "ma questo metodo restituisce sempre il path con lo slash iniziale, mentre per l'url "
	    + "delle pagine di help non deve esserci in modo da sfruttare il contesto attuale.", description = "Aggiunto il metodo 'getUrlCompletePath' il quale richiama il metodo "
	    + " 'getCompletePath e poi elimina lo slash iniziale se presente.'")
    @DeveloperTaskEnd(name = "Riccardo Foraf�", date = "23.05.2011")
    private String getUrlCompletePath(HttpSession session, String first,
	    String center, String last) {

	String result = "";

	String urlCompletePath = getCompletePath(session, first, center, last);

	if (urlCompletePath != null) {
	    if (urlCompletePath.startsWith(PATH_SEP)) {
		result = urlCompletePath.substring(1);
	    } else {
		result = urlCompletePath;
	    }
	}

	return result;

    }

    /**
     * Restituisce un path completo, controllando il corretto utilizzo degli
     * slash.
     * 
     * @param first
     * @param center
     * @param last
     * @return
     */
    @DeveloperTaskStart(name = "Riccardo Foraf�", date = "22.05.2011", bugDescription = "Nel caso in cui last contenga uno slash non all'inizio, "
	    + "viene messo uno slash all'inizio anche se viene aggiunta la stringa '/html/'.", description = "Aggiunta il controllo sulla presenza o meno dello slash iniziale"
	    + " nella variabile 'last' ed aggiunta la variabile 'htmlFolder'.")
    @DeveloperTaskEnd(name = "Riccardo Foraf�", date = "22.05.2011")
    private String getCompletePath(HttpSession session, String first,
	    String center, String last) {

	String htmlFolder = HTML_FOLDER;

	if (first.lastIndexOf(PATH_SEP) < first.length() - 1) {
	    first += PATH_SEP;
	}

	if (center == null) {
	    center = DEFAULT;
	}

	if (last.indexOf(PATH_SEP) > 0) {
	    last = PATH_SEP + last;
	}

	if (!last.startsWith(PATH_SEP)) {
	    htmlFolder += PATH_SEP;
	}

	if (session != null) {
	    MultiCommunePath multiCommunePath = new MultiCommunePath(session,
		    sanitizeFirst(first));

	    String jspPath = multiCommunePath.getPath("/html", last);

	    return jspPath;

	} else {

	    return first + center + htmlFolder + last;

	}

    }

    private String sanitizeFirst(String first) {

	if (first != null && first.endsWith("/")) {
	    return first.substring(0, first.length() - 1);
	} else {
	    return first;
	}

    }

}
