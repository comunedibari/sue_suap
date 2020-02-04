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
package it.people.process.view;

import it.people.Activity;
import it.people.ActivityState;
import it.people.City;
import it.people.IActivity;
import it.people.IProcess;
import it.people.IStep;
import it.people.IView;
import it.people.Step;
import it.people.StepState;
import it.people.SummaryActivity;
import it.people.core.Logger;
import it.people.core.PeopleContext;
import it.people.core.PplPermission;
import it.people.core.PplPrincipal;
import it.people.core.PplSecurityManager;
import it.people.exceptions.PeopleFrameworkException;
import it.people.process.AbstractPplProcess;
import it.people.process.SummaryState;
import it.people.process.SurfDirection;
import it.people.process.workflow.exceptions.WorkFlowException;
import it.people.util.CachedMap;
import it.people.util.Device;
import it.people.util.PplProcessCoords;
import it.people.util.RequestUtils;
import it.people.util.frontend.PplPropertyChangeListener;
import it.people.wrappers.IRequestWrapper;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Category;

/**
 * 
 * User: sergio Date: Sep 3, 2003 Time: 1:53:47 PM <br>
 * <br>
 * Questa classe descrive il comportamento e le caratteristiche delle classi che
 * (estendendola) realizzano la parte di visualizzazione dei processi.
 * 
 */
public abstract class ConcreteView implements Serializable {

    private static final long serialVersionUID = 736960188446219284L;

    protected CachedMap cssMap = new CachedMap(CachedMap.SECOND, 30);
    private Category cat = Category.getInstance(ConcreteView.class.getName());
    protected AbstractPplProcess m_parent;
    private String m_viewName;
    private int m_currentActivity;

    protected ArrayList m_activities;
    protected String m_frameJspPage;

    // Path dei fogli di stile
    protected String cssPathCategoty;
    protected String cssPathSubcategory;
    protected String cssPathService;
    protected String cssPathCategotyCommune;
    protected String cssPathSubcategoryCommune;
    protected String cssPathServiceCommune;

    // Path dell'header
    protected String htmlPathHeader;
    protected String htmlPathFooter;

    protected boolean _isBottomSaveBarEnabled = true;
    protected boolean _isBottomNavigationBarEnabled = true;

    // La variabile indica la direzione di navigazione degli step
    private SurfDirection surfDirection = SurfDirection.other;

    // Indica se deve essere mostrato un messaggio di errore, in
    // caso di eccezione nel collegamento al web-service di back-end.
    private boolean showBackEndError = false;

    /**
     * Ricerca se presente l'attivit� di riepilogo.
     * 
     * @return attivita' di riepilogo se � presente, null altrimenti
     */
    public SummaryActivity findSummaryActivity() {
	// ricerca se presente l'attivit� di riepilogo
	for (Iterator iterator = m_activities.iterator(); iterator.hasNext();) {
	    Activity activity = (Activity) iterator.next();
	    if (activity instanceof SummaryActivity)
		return (SummaryActivity) activity;
	}
	return null;
    }

    /**
     * Imposta la direzione di navigazione degli step
     * 
     * @param surfDirection
     */
    protected void setSurfDirection(SurfDirection surfDirection) {
	this.surfDirection = surfDirection;
    }

    /**
     * Ritorna la direzione di navigazione
     * 
     * @return
     */
    public SurfDirection getSurfDirection() {
	return this.surfDirection;
    }

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

    // -- (AZ) Fine Modifiche

    protected HashMap m_propertyChangeListeners;

    /**
     * Costruttore.
     */
    public ConcreteView() {
	m_activities = new ArrayList();
	m_propertyChangeListeners = new HashMap();
    }

    /**
     * Costruttore.
     * 
     * @param parent
     *            Processo padre
     */
    public ConcreteView(AbstractPplProcess parent) {
	this();
	m_parent = parent;
    }

    public void setViewName(String viewName) {
	m_viewName = viewName;
    }

    public String getViewName() {
	return this.m_viewName;
    }

    public void setPplProcess(AbstractPplProcess parent) {
	m_parent = parent;
    }

    /**
     * Inizializza le classi per la visualizzazione.
     * 
     * @param comune
     *            Comune
     * @param device
     *            Device
     */
    public void initialize(City comune, Device device, IProcess process)
	    throws WorkFlowException {
	doDefineActivities(process);
	doDefineFramePage();
	doDefineCss();
	// doDefinePropertyChangeListener();
    }

    /**
     * Classe astratta da sovrascrivere per poter definire le attivit? che
     * costituiscono un processo
     */
    protected void doDefineActivities(IProcess process)
	    throws WorkFlowException {

	IView view = process.getView();
	m_activities = view.getActivities();
    }

    public void service(AbstractPplProcess process, IRequestWrapper request)
	    throws IOException, ServletException {
	this.getCurrentActivity().getCurrentStep().service(process, request);
    }

    public void loopBack(AbstractPplProcess process, IRequestWrapper request,
	    String propertyName, int index) throws IOException,
	    ServletException {
	this.getCurrentActivity().getCurrentStep()
		.loopBack(process, request, propertyName, index);
    }

    /**
     * @param process
     * @param request
     * @throws IOException
     * @throws ServletException
     */
    public void onLineHelploopBack(AbstractPplProcess process,
	    IRequestWrapper request) throws IOException, ServletException {
	this.getCurrentActivity().getCurrentStep()
		.onLineHelpLoopBack(process, request);
    }

    /**
     * @param process
     * @param request
     * @param propertyName
     * @throws IOException
     * @throws ServletException
     */
    public void onLineHelpManagementLoopBack(AbstractPplProcess process,
	    IRequestWrapper request, String propertyName) throws IOException,
	    ServletException {
	this.getCurrentActivity().getCurrentStep()
		.onLineHelpManagementLoopBack(process, request, propertyName);
    }

    /**
     * @param process
     * @param request
     * @param parameter
     * @throws IOException
     * @throws ServletException
     */
    public void userPanelLoopBack(AbstractPplProcess process,
	    IRequestWrapper request, String parameter) throws IOException,
	    ServletException {
	this.getCurrentActivity().getCurrentStep()
		.userPanelLoopBack(process, request, parameter);
    }

    protected void doDefineCss() {
	String rootPath = getProcessName();
	rootPath = rootPath.replaceFirst("it.people.fsl.servizi.", "");
	String token[] = rootPath.split("\\.");
	String area = token[0];
	String sottoArea = token[1];
	String servizio = token[2];
	String communeCode = this.m_parent.getCommune().getKey();

	// Css di default
	this.cssPathCategoty = "/servizi/" + area + "/css/" + area + ".css";

	this.cssPathSubcategory = "/servizi/" + area + "/" + sottoArea
		+ "/css/" + sottoArea + ".css";

	this.cssPathService = "/servizi/" + area + "/" + sottoArea + "/"
		+ servizio + "/view/default/css/" + servizio + ".css";

	// Css specifici per comune
	this.cssPathCategotyCommune = "/servizi/" + area + "/css/" + area + "_"
		+ communeCode + ".css";

	this.cssPathSubcategoryCommune = "/servizi/" + area + "/" + sottoArea
		+ "/css/" + sottoArea + "_" + communeCode + ".css";

	this.cssPathServiceCommune = "/servizi/" + area + "/" + sottoArea + "/"
		+ servizio + "/view/" + communeCode + "/css/" + servizio
		+ ".css";
    }

    /**
     * @return Returns the cssPathCategoty.
     */
    public String getCssPathCategory(HttpServletRequest request) {
	return this.cssPathCategoty;
    }

    /**
     * @return Returns the cssPathSubcategory.
     */
    public String getCssPathSubcategory(HttpServletRequest request) {
	return this.cssPathSubcategory;
    }

    /**
     * @return Returns the cssPathService.
     */
    public String getCssPathService(HttpServletRequest request) {
	return this.cssPathService;
    }

    /**
     * @return Ritorna il Css Path Categoty per il comune corrente.
     */
    public String getCssPathCategoryCommune(HttpServletRequest request) {
	return this.cssPathCategotyCommune;
    }

    /**
     * @return ritorna il Css Path Subcategory per il comune corrente.
     */
    public String getCssPathSubcategoryCommune(HttpServletRequest request) {
	return this.cssPathSubcategoryCommune;
    }

    /**
     * @return ritorna il Css Path Service per il comune corrente.
     */
    public String getCssPathServiceCommune(HttpServletRequest request) {
	return this.cssPathServiceCommune;
    }

    protected void doDefineFramePage() {
	m_frameJspPage = "/framework/view/generic/default/html/main.jsp";
    }

    public Activity getCurrentActivity() {
	return (Activity) m_activities.get(m_currentActivity);
    }

    public Activity[] getActivities() {
	return (Activity[]) m_activities.toArray(new Activity[0]);
    }

    public IActivity getActivityById(String id) {
	for (int i = 0; i < m_activities.size(); i++) {
	    IActivity a = (IActivity) m_activities.get(i);
	    if (a.getId().equalsIgnoreCase(id))
		return a;
	}

	return null;
    }

    public int getCurrentActivityIndex() {
	return m_currentActivity;
    }

    /**
     * Imposta l'attivita' corrente, azzerando SurfDirection.
     * 
     * @param m_currentActivity
     *            nuova attivita' corrente.
     */
    public void setCurrentActivityIndex(int currentActivity) {
	setCurrentActivityIndex(currentActivity, true);
    }

    /**
     * Imposta l'attivita' corrente, azzerando SurfDirection.
     * 
     * @param currentActivity
     *            nuova attivita' corrente.
     * @param resetSurfDirection
     *            se true imposta a other la propriet� SurfDirection, se false
     *            lascia invariato il valore
     */
    public void setCurrentActivityIndex(int currentActivity,
	    boolean resetSurfDirection) {
	this.m_currentActivity = currentActivity;
	this.setActivitySummaryState();
	if (resetSurfDirection)
	    this.setSurfDirection(SurfDirection.other);
    }

    public String getProcessName() {
	return m_parent.getProcessName();
    }

    /**
     * Verifica se lo stato impostato � quello dell'attivit� di riepilogo
     * 
     */
    private void setActivitySummaryState() {
	// ricerca SummaryActivity
	Activity[] activities = getActivities();
	SummaryActivity summaryActivity = null;
	for (int i = 0; i < activities.length && summaryActivity == null; i++)
	    if (activities[i] instanceof SummaryActivity)
		summaryActivity = (SummaryActivity) activities[i];

	if (summaryActivity != null
		&& summaryActivity.getSummaryState().equals(
			SummaryState.FINALLY)) {
	    if (getCurrentActivity() instanceof SummaryActivity)
		summaryActivity.setState(ActivityState.ACTIVE);
	    else
		summaryActivity.setState(ActivityState.INACTIVE);
	}
    }

    /**
     * Aggiunge un'attivita' alla 'view'.
     * 
     * @param name
     * @param state
     * @param p_showCheckmark
     * @param steps
     * @param currentStepIndex
     */
    protected void createActivity(String name, ActivityState state,
	    boolean p_showCheckmark, Step[] steps, int currentStepIndex) {
	// Activity activity = new Activity(p_activityProp);
	Activity activity = new Activity(name, steps);
	activity.setState(state);
	activity.setShowCheck(p_showCheckmark);
	activity.setCurrentStepIndex(currentStepIndex);
	m_activities.add(activity);
    }

    /**
     * Metodo che visualizza l'attivit? e lo step correnti
     * 
     * @param context
     *            Contesto
     * @param request
     *            request
     * @param response
     *            response
     * @throws IOException
     * @throws ServletException
     */
    public void draw(PeopleContext context, HttpServletRequest request,
	    HttpServletResponse response) throws IOException, ServletException {

	request.setAttribute("CurrentActivity", getCurrentActivity());
	request.setAttribute("CurrentStep", getCurrentActivity()
		.getCurrentStep());
	request.setAttribute("ConcreteView", this);

	try {
	    PplPrincipal[] princs = m_parent.findPrincipalWithDelegate(context
		    .getUser());

	    request.setAttribute(
		    "PagePermission",
		    PplSecurityManager.getInstance().getPermission(princs,
			    getCurrentActivity().getCurrentStep().getACL()));

	} catch (Exception ex) {
	    cat.error(ex);
	}
	Logger.debug("ConcreteView::debug contextPath["
		+ request.getContextPath() + "]");
	Logger.debug("ConcreteView::debug includoPagina[" + m_frameJspPage
		+ "]");
	request.getRequestDispatcher(m_frameJspPage).include(request, response);
    }

    /**
     * Registra i PropertyChangeListener.
     * 
     * @param propertyName
     * @param pcl
     */
    public final void registerPropertyChangeListener(String propertyName,
	    PplPropertyChangeListener pcl) {
	m_propertyChangeListeners.put(propertyName, pcl);
    }

    /**
     * 
     * @param context
     *            Contesto
     * @param activityStepRef
     *            Coordinate attivit?/step da esaminare
     * @return Restituisce vero se l'utente pu? entrare nello step in esame
     */
    public final boolean canEnter(PeopleContext context,
	    PplProcessCoords activityStepRef) {
	if (activityStepRef.check(this)) {

	    Activity tmpActivity = (Activity) m_activities.get(activityStepRef
		    .getActivityIndex());

	    if ((!tmpActivity.isInactive() && !tmpActivity.isHidden())
		    || tmpActivity instanceof SummaryActivity) {
		Step tmpStep = tmpActivity.getStep(activityStepRef
			.getStepIndex());
		if (tmpStep.isActive()
			|| tmpActivity instanceof SummaryActivity) {

		    // Verifica se l'utente ha i permessi per poter accedere
		    // alla pagina
		    PplPrincipal[] princs = m_parent
			    .findPrincipalWithDelegate(context.getUser());
		    if (!PplSecurityManager.getInstance().canDo(
			    PplPermission.READ, princs, tmpStep.getACL()))
			return false;
		    if (tmpStep.getAccessController() != null
			    && !tmpStep.getAccessController().canEnter(
				    m_parent.getData()))
			return false;
		    return true;
		}
	    }
	}
	return false;
    }

    /**
     * 
     * @param p_coords
     *            Coordinate attivit?/step da esaminare
     * @return Restituisce le coordinate attivit?/step successive a quelle in
     *         esame
     */
    protected PplProcessCoords calculateNextCoords(PplProcessCoords p_coords) {
	if (p_coords != null && p_coords.check(this)) {
	    int activityIndex = p_coords.getActivityIndex();
	    int stepIndex = p_coords.getStepIndex();

	    int lastActIndex = this.getActivities().length - 1;
	    PplProcessCoords newCoords = new PplProcessCoords(p_coords);

	    int lastStepIndex = this.getActivities()[activityIndex]
		    .getStepCount() - 1;
	    if (stepIndex < lastStepIndex) {
		newCoords.setStepIndex(stepIndex + 1);
	    } else {
		if (activityIndex < lastActIndex) {
		    newCoords.setStepIndex(0);
		    newCoords.setActivityIndex(activityIndex + 1);
		}
	    }
	    if (newCoords.check(this))
		return newCoords;
	}
	return null;
    }

    /**
     * 
     * @param p_coords
     *            Coordinate attivit?/step da esaminare
     * @return Restituisce le coordinate attivit?/step precedenti a quelle in
     *         esame
     */
    protected PplProcessCoords calculatePrevCoords(PplProcessCoords p_coords) {
	if (p_coords != null && p_coords.check(this)) {
	    int activityIndex = p_coords.getActivityIndex();
	    int stepIndex = p_coords.getStepIndex();

	    PplProcessCoords newCoords = new PplProcessCoords(p_coords);
	    // int lastStepIndex =
	    // this.getActivities()[activityIndex].getStepCount() - 1;
	    if (stepIndex > 0) {
		newCoords.setStepIndex(stepIndex - 1);
	    } else {
		if (activityIndex > 0) {
		    newCoords.setActivityIndex(activityIndex - 1);
		    newCoords
			    .setStepIndex(this.getActivities()[activityIndex - 1]
				    .getStepCount() - 1);
		}
	    }
	    if (newCoords.check(this))
		return newCoords;
	}
	return null;
    }

    /**
     * A partire dalle coordinate attivita'/step correnti determina quelle
     * succesive in cui l'utente e' autorizzato ad entrare e vi accede.
     * 
     * @param context
     *            Contesto
     */
    public void nextStep(PeopleContext context) {
	PplProcessCoords currCoords = new PplProcessCoords(this);
	boolean foundNextStep = false;
	boolean moveNext = true;
	do {
	    PplProcessCoords nextCoords = calculateNextCoords(currCoords);

	    if (!nextCoords.equals(currCoords)) {
		currCoords = nextCoords;
		if (canEnter(context, nextCoords)) {
		    doEnter(nextCoords);
		    moveNext = false;
		    foundNextStep = true;
		} else
		    moveNext = true;
	    } else
		moveNext = false;

	} while (moveNext);
	if (foundNextStep)
	    setSurfDirection(SurfDirection.forward);
    }

    public void goActivity(PeopleContext context, int destActivityIndex) {
	// coordinate di partenza
	PplProcessCoords sourceCoords = new PplProcessCoords(this);

	// la prima coordinata da provare � il primo step dell'attivit�
	// selezionata
	PplProcessCoords nextCoords = new PplProcessCoords(this);
	nextCoords.setActivityIndex(destActivityIndex);
	nextCoords.setStepIndex(0);

	boolean foundNextStep = false;
	boolean moveNext = true;

	do {
	    if (canEnter(context, nextCoords)) {
		doEnter(nextCoords);
		moveNext = false;
		foundNextStep = true;
	    } else {
		PplProcessCoords tempCoords = nextCoords;
		nextCoords = calculateNextCoords(nextCoords);

		// calculateNextCoords() ritorna la coordinata
		// passata se non � possibile procedere oltre.
		if (tempCoords.equals(nextCoords))
		    moveNext = false;
	    }
	} while (moveNext);

	if (foundNextStep) {
	    // Determina la direzione di navigazione
	    if (nextCoords.getActivityIndex() > sourceCoords.getActivityIndex())
		setSurfDirection(SurfDirection.forward);
	    else if (nextCoords.getActivityIndex() < sourceCoords
		    .getActivityIndex())
		setSurfDirection(SurfDirection.backward);
	    else if (nextCoords.getStepIndex() > sourceCoords.getStepIndex())
		setSurfDirection(SurfDirection.forward);
	    else if (nextCoords.getStepIndex() < sourceCoords.getStepIndex())
		setSurfDirection(SurfDirection.backward);
	    else
		setSurfDirection(SurfDirection.other);
	}
    }

    /**
     * A partire dalle coordinate attivit?/step correnti determina quelle
     * precedenti in cui l'utente ? autorizzato ad entrare e vi accede.
     * 
     * @param context
     *            Contesto
     */
    public void prevStep(PeopleContext context) {
	PplProcessCoords currCoords = new PplProcessCoords(this);
	boolean foundPrevStep = false;
	boolean movePrev = true;
	do {
	    PplProcessCoords prevCoords = calculatePrevCoords(currCoords);

	    if (!prevCoords.equals(currCoords)) {
		currCoords = prevCoords;
		if (canEnter(context, prevCoords)) {
		    doEnter(prevCoords);
		    movePrev = false;
		    foundPrevStep = true;
		} else
		    movePrev = true;
	    } else
		movePrev = false;

	} while (movePrev);
	if (foundPrevStep)
	    setSurfDirection(SurfDirection.backward);
    }

    /**
     * 
     * @param p_coords
     *            Coordinate attivit?/step in cui entrare
     */
    private void doEnter(PplProcessCoords p_coords) {
	setCurrentActivityIndex(p_coords.getActivityIndex());
	getCurrentActivity().setCurrentStepIndex(p_coords.getStepIndex());
    }

    /**
     *
     */
    public final void propertyUpdate() {
	// Compatibilit? con la versione precedente
	Hashtable pcls = new Hashtable();
	pcls.putAll(m_propertyChangeListeners);

	HashMap properties = RequestUtils.getChangedProperties(
		m_parent.getData(), pcls.keys(), null);
	propertyUpdate(properties);
    }

    /**
     *
     */
    public final void propertyUpdate(HashMap properties) {

	Iterator iter = properties.keySet().iterator();
	while (iter.hasNext()) {
	    String propName = (String) iter.next();
	    PplPropertyChangeListener pcl = (PplPropertyChangeListener) m_propertyChangeListeners
		    .get(propName);
	    if (pcl != null)
		pcl.propertyUpdate(properties.get(propName));
	}
    }

    /**
     * 
     * 
     * public final void propertyUpdate(HashMap properties) { // Compatibilit?
     * con la versione precedente if (m_propertyChangeListeners.isEmpty()) {
     * Iterator iter = properties.keySet().iterator(); while (iter.hasNext()) {
     * String propName = (String) iter.next(); propertyUpdate(propName,
     * properties.get(propName)); } } else { Iterator iter =
     * properties.keySet().iterator(); while (iter.hasNext()) { String propName
     * = (String) iter.next(); PplPropertyChangeListener pcl =
     * (PplPropertyChangeListener) m_propertyChangeListeners.get(propName); if
     * (pcl != null) pcl.propertyUpdate(properties.get(propName)); } } }
     * 
     * /**
     * 
     */
    // protected abstract void propertyUpdate(String property, Object value);

    /**
     * Imposta come nodo corrente il risultato di un pagamento
     */
    public void setActivePaymentResultStep() throws PeopleFrameworkException {
	boolean found = false;
	int stepId = -1;
	int activityId = -1;

	// Scorre tutte le attivit� alla ricerca
	// dello step con i risultati del pagamento
	Object[] activities = m_activities.toArray();
	for (int i = 0; i < activities.length; i++) {
	    Activity activity = (Activity) activities[i];

	    Object[] steps = activity.getStepList().toArray();
	    for (int j = 0; j < steps.length; j++) {
		IStep step = (IStep) steps[j];
		if (step.getId().equalsIgnoreCase("EPAG")) {
		    this.setCurrentActivityIndex(i);
		    activity.setCurrentStepIndex(j);
		    stepId = j;
		    activityId = i;
		    found = true;
		}
	    }
	}

	if (!found)
	    throw new PeopleFrameworkException(
		    "Step di risultato pagamento inesistente");

	// Disabilita le attivit� precedenti a quella contenente lo step di
	// esito Pagamento
	// e abilita tutte le altre.
	for (int i = 0; i < activities.length; i++) {
	    Activity activity = (Activity) activities[i];

	    if (i < activityId)
		activity.setState(ActivityState.INACTIVE);
	    else {
		activity.setState(ActivityState.ACTIVE);

		// imposto come completati gli step precedenti a quello di
		// esito,
		// in modo che non siano pi� accessibili
		if (i == activityId) {
		    Object[] steps = activity.getStepList().toArray();
		    for (int j = 0; (j < steps.length) && (j < stepId); j++) {
			IStep step = (IStep) steps[j];
			// step.setState(StepState.COMPLETED);
			step.setState(StepState.INACTIVE);
		    }
		}
	    }
	}
    }

    /**
     * Indica se gli errori di collegamento ai web-service di back-end devono
     * essere mostrati dal framework: - se true il framework aggiunge ai
     * messaggi di errore da mostrare un generico messaggio che indica un
     * problema nel collegamento. - se false il framework non mostra nulla, il
     * messaggio dovr� essere mostrato direttamente dal servizio.
     * 
     * @return
     */
    public boolean isShowBackEndError() {
	return showBackEndError;
    }

    /**
     * Indica se gli errori di collegamento ai web-service di back-end devono
     * essere mostrati dal framework: - se true il framework aggiunge ai
     * messaggi di errore da mostrare un generico messaggio che indica un
     * problema nel collegamento. - se false il framework non mostra nulla, il
     * messaggio dovr� essere mostrato direttamente dal servizio.
     * 
     * @return
     */
    public void setShowBackEndError(boolean showBackEndError) {
	this.showBackEndError = showBackEndError;
    }
}
