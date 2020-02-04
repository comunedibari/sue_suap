/**
 * 
 */
package it.people.util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import it.people.City;
import it.people.PeopleConstants;
import it.people.core.persistence.PersistenceManager;
import it.people.core.persistence.UserSignalledBugsPersistenceMgr;
import it.people.core.persistence.UserSuggestionsPersistenceMgr;
import it.people.core.persistence.exception.peopleException;
import it.people.fsl.servizi.oggetticondivisi.UserSignalledBug;
import it.people.fsl.servizi.oggetticondivisi.UserSuggestion;
import it.people.process.AbstractPplProcess;
import it.people.process.data.AbstractData;
import it.people.vsl.PipelineData;
import it.people.vsl.PipelineDataHolder;
import it.people.vsl.PipelineDataImpl;
import it.people.vsl.exception.SendException;
import it.people.vsl.transport.PplMailType;
import it.people.vsl.transport.SendPplAdminMail;
import it.people.wrappers.IRequestWrapper;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 *         29/ott/2012 14:10:18
 */
public class UserPanel implements IUserPanel {

    private AbstractPplProcess parent;

    public UserPanel(AbstractPplProcess parent) {
	this.setParent(parent);
    }

    /**
     * @return the parent
     */
    private AbstractPplProcess getParent() {
	return this.parent;
    }

    /**
     * @param parent
     *            the parent to set
     */
    private void setParent(AbstractPplProcess parent) {
	this.parent = parent;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.people.util.IUserPanel#processPanel(it.people.wrappers.IRequestWrapper
     * , java.lang.String)
     */
    public void processPanel(IRequestWrapper request, String property) {

	String view = request.getParameter("view");
	if (property.equalsIgnoreCase(IUserPanel.ActionTypes.submit
		.getActionName())) {

	    if (view.equalsIgnoreCase(IUserPanel.ViewTypes.bugSignalling
		    .getViewName())) {

		PipelineDataHolder pipelineDataHolder = new PipelineDataHolder();
		PipelineData pipelineData = new PipelineDataImpl();
		pipelineData.setAttribute(
			PipelineDataImpl.EDITABLEPROCESS_PARAMNAME,
			this.getParent());
		pipelineDataHolder.setPlineData(pipelineData);

		HashMap inputParameters = new HashMap();
		HashMap outputParameters = new HashMap();
		
		StringBuffer corpoEmailErr = new StringBuffer(request.getParameter("signallingContent"));
		corpoEmailErr.append("<br/>").append("<br/>")
				.append("Email Segnalatore: ").append(request.getParameter("emailSegnalatore"))
				.append("<br/>").append("Ente: ").append(getNomeEnte(request));

		inputParameters.put("subject",
			request.getParameter("signallingSubject"));
//		inputParameters.put("message",
//			request.getParameter("signallingContent"));
		inputParameters.put("message", corpoEmailErr);

		UserSignalledBugsPersistenceMgr userSignalledBugsPersistenceMgr = null;
		try {
		    SendPplAdminMail pplAdminMail = new SendPplAdminMail(
			    PplMailType.userSignalledBug, this.getParent()
				    .getCommune(), pipelineDataHolder);
		    pplAdminMail.send(inputParameters, outputParameters);

		    if (isReceiptRequired(request)) {
			SendPplAdminMail pplUserReceiptMail = new SendPplAdminMail(
				PplMailType.userSignalledBugUserReceipt, this
					.getParent().getCommune(),
				pipelineDataHolder);
			pplUserReceiptMail.send(inputParameters,
				outputParameters);
		    }

		    AbstractData data = (AbstractData) this.getParent()
			    .getData();

		    UserSignalledBug userSignalledBug = new UserSignalledBug();
		    userSignalledBug.setSubject(request
			    .getParameter("signallingSubject"));
		    userSignalledBug.setDescription(request
			    .getParameter("signallingContent"));
		    userSignalledBug.setUserId(this.getParent().getContext()
			    .getUser().getUserID());
		    userSignalledBug.setFirstName(data.getProfiloOperatore()
			    .getNome());
		    userSignalledBug.setLastName(data.getProfiloOperatore()
			    .getCognome());
		    userSignalledBug.seteMail(data.getProfiloOperatore()
			    .getDomicilioElettronico());
		    userSignalledBug.setCommune(this.getParent().getCommune());
		    userSignalledBug.setReceivedDate(new Timestamp(Calendar
			    .getInstance().getTimeInMillis()));

		    userSignalledBugsPersistenceMgr = new UserSignalledBugsPersistenceMgr(
			    PersistenceManager.Mode.WRITE);
		    userSignalledBugsPersistenceMgr.set(userSignalledBug);

		} catch (SendException e) {
		    e.printStackTrace();
		} catch (peopleException e) {
		    e.printStackTrace();
		} finally {
		    if (userSignalledBugsPersistenceMgr != null) {
			userSignalledBugsPersistenceMgr.close();
		    }
		}

	    }

	    if (view.equalsIgnoreCase(IUserPanel.ViewTypes.sendSuggestion
		    .getViewName())) {

		PipelineDataHolder pipelineDataHolder = new PipelineDataHolder();
		PipelineData pipelineData = new PipelineDataImpl();
		pipelineData.setAttribute(
			PipelineDataImpl.EDITABLEPROCESS_PARAMNAME,
			this.getParent());
		pipelineDataHolder.setPlineData(pipelineData);

		HashMap inputParameters = new HashMap();
		HashMap outputParameters = new HashMap();
		
		
		
		StringBuffer corpoEmailSugg = new StringBuffer(request.getParameter("suggestionContent"));
		corpoEmailSugg.append("<br/>").append("<br/>")
					.append("Email Segnalatore: ").append(request.getParameter("emailSegnalatore"))
					.append("<br/>").append("Ente: ").append(getNomeEnte(request));
		

		inputParameters.put("subject",
			request.getParameter("suggestionSubject"));
		//inputParameters.put("message", request.getParameter("suggestionContent"));
		inputParameters.put("message", corpoEmailSugg);

		UserSuggestionsPersistenceMgr userSuggestionsPersistenceMgr = null;
		try {
		    SendPplAdminMail pplAdminMail = new SendPplAdminMail(
			    PplMailType.userSuggestion, this.getParent()
				    .getCommune(), pipelineDataHolder);
		    pplAdminMail.send(inputParameters, outputParameters);

		    if (isReceiptRequired(request)) {
			SendPplAdminMail pplUserReceiptMail = new SendPplAdminMail(
				PplMailType.userSuggestionUserReceipt, this
					.getParent().getCommune(),
				pipelineDataHolder);
			pplUserReceiptMail.send(inputParameters,
				outputParameters);
		    }

		    AbstractData data = (AbstractData) this.getParent()
			    .getData();

		    UserSuggestion userSuggestion = new UserSuggestion();
		    userSuggestion.setSubject(request
			    .getParameter("suggestionSubject"));
		    userSuggestion.setDescription(request
			    .getParameter("suggestionContent"));
		    userSuggestion.setUserId(this.getParent().getContext()
			    .getUser().getUserID());
		    userSuggestion.setFirstName(data.getProfiloOperatore()
			    .getNome());
		    userSuggestion.setLastName(data.getProfiloOperatore()
			    .getCognome());
		    userSuggestion.seteMail(data.getProfiloOperatore()
			    .getDomicilioElettronico());
		    userSuggestion.setCommune(this.getParent().getCommune());
		    userSuggestion.setReceivedDate(new Timestamp(Calendar
			    .getInstance().getTimeInMillis()));

		    userSuggestionsPersistenceMgr = new UserSuggestionsPersistenceMgr(
			    PersistenceManager.Mode.WRITE);
		    userSuggestionsPersistenceMgr.set(userSuggestion);

		} catch (SendException e) {
		    e.printStackTrace();
		} catch (peopleException e) {
		    e.printStackTrace();
		} finally {
		    if (userSuggestionsPersistenceMgr != null) {
			userSuggestionsPersistenceMgr.close();
		    }
		}

	    }

	}

    }
    
    private String getNomeEnte(IRequestWrapper request){
    	
    	HttpSession session = ((HttpServletRequest) request).getSession();
		City city = (City) session.getAttribute(PeopleConstants.SESSION_NAME_COMMUNE);
		return city.getLabel();
    }

    /**
     * @param request
     * @return
     */
    private boolean isReceiptRequired(IRequestWrapper request) {

	String signallingRequestReceipt = request
		.getParameter("requestReceipt");
	if (signallingRequestReceipt != null
		&& signallingRequestReceipt.equalsIgnoreCase("on")) {
	    return true;
	} else {
	    return false;
	}

    }

}
