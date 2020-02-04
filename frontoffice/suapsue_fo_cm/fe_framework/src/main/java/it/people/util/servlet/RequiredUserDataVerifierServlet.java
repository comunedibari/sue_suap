package it.people.util.servlet;

import it.people.core.PplUserData;
import it.people.layout.ButtonKey;
import it.people.process.AbstractPplProcess;
import it.people.process.data.AbstractData;
import it.people.sirac.accr.ProfiliHelper;
import it.people.sirac.accr.beans.Accreditamento;
import it.people.sirac.core.SiracConstants;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.rpc.ServiceException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class RequiredUserDataVerifierServlet extends HttpServlet {

    private static final Logger logger = LogManager
	    .getLogger(RequiredUserDataVerifierServlet.class);

    private static final long serialVersionUID = -5246743783118890676L;

    private static final String ASK_FOR_REQUIRED_USER_DATA_PAGE_PARAMETER = "askForRequiredUserDataPage";

    private static String askForRequiredUserDataPage = null;

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.servlet.http.HttpServlet#doDelete(javax.servlet.http.HttpServletRequest
     * , javax.servlet.http.HttpServletResponse)
     */
    protected void doDelete(HttpServletRequest request,
	    HttpServletResponse response) {
	try {
	    super.doDelete(request, response);
	} catch (ServletException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
     * , javax.servlet.http.HttpServletResponse)
     */
    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {

	if (logger.isDebugEnabled()) {
	    logger.debug("Serving request resource '"
		    + RequiredUserDataVerifierServlet
			    .getAskForRequiredUserDataPage() + "'.");
	}

	try {

	    getServletContext().getRequestDispatcher(
		    RequiredUserDataVerifierServlet
			    .getAskForRequiredUserDataPage()).forward(request,
		    response);

	} catch (ServletException se) {
	    se.printStackTrace();
	} catch (IOException ioe) {
	    ioe.printStackTrace();
	}

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.servlet.http.HttpServlet#doHead(javax.servlet.http.HttpServletRequest
     * , javax.servlet.http.HttpServletResponse)
     */
    protected void doHead(HttpServletRequest request,
	    HttpServletResponse response) {
	try {
	    super.doHead(request, response);
	} catch (ServletException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.http.HttpServlet#doOptions(javax.servlet.http.
     * HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    protected void doOptions(HttpServletRequest request,
	    HttpServletResponse response) {
	try {
	    super.doOptions(request, response);
	} catch (ServletException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
     * , javax.servlet.http.HttpServletResponse)
     */
    protected void doPost(HttpServletRequest request,
	    HttpServletResponse response) {

	if (logger.isDebugEnabled()) {
	    logger.debug("Post event:");
	    logger.debug("");
	    logger.debug("From URI:");
	    logger.debug(request.getRequestURI());
	}
	if (logger.isDebugEnabled()) {
	    Enumeration<String> attributeNames = request.getAttributeNames();
	    while (attributeNames.hasMoreElements()) {
		String attributeName = attributeNames.nextElement();
		Object attributeValue = request.getAttribute(attributeName);
		logger.debug("Attribute name: " + attributeName);
		logger.debug("Attribute value: " + attributeValue);
	    }
	    logger.debug("");
	    Enumeration<String> parameterNames = request.getParameterNames();
	    while (parameterNames.hasMoreElements()) {
		String parameterName = parameterNames.nextElement();
		Object parameterValue = request.getParameter(parameterName);
		logger.debug("Parameter name: " + parameterName);
		logger.debug("Parameter value: " + parameterValue);
	    }
	}

	// boolean servicesButtonKeySelected =
	// request.getParameter(ButtonKey.SERVICE) != null;
	// boolean exitButtonKeySelected =
	// request.getParameter(ButtonKey.LOGOFF) != null;

	String emailAddress = String.valueOf(request
		.getParameter("EMAILADDRESS"));

	try {
	    if (emailAddress != null && !emailAddress.equalsIgnoreCase("")) {

		PplUserData userData = (PplUserData) request.getSession()
			.getAttribute(
				SiracConstants.SIRAC_AUTHENTICATED_USERDATA);
		userData.setEmailaddress(request.getParameter("EMAILADDRESS"));

		Object pplProcessObject = request.getSession().getAttribute(
			"pplProcess");

		try {
		    AbstractPplProcess pplProcess = (AbstractPplProcess) pplProcessObject;
		    if (pplProcessObject != null) {

			AbstractData pplData = (AbstractData) pplProcess
				.getData();

			if (pplData != null) {

			    Accreditamento accrSel = (Accreditamento) request
				    .getSession().getAttribute(
					    SiracConstants.SIRAC_ACCR_ACCRSEL);
			    boolean profiloUtente = accrSel
				    .getQualifica()
				    .getIdQualifica()
				    .equals(ProfiliHelper
					    .getQualificaUtentePeopleRegistrato()
					    .getIdQualifica());
			    boolean accreditamentoAssociazioneCategoria = "OAC"
				    .equals(accrSel.getQualifica()
					    .getIdQualifica())
				    || "RCT".equals(accrSel.getQualifica()
					    .getIdQualifica());

			    if (profiloUtente) {
				pplData.getProfiloOperatore()
					.setDomicilioElettronico(
						userData.getEmailaddress());
				pplData.getProfiloRichiedente()
					.setDomicilioElettronico(
						userData.getEmailaddress());
				pplData.setDomicilioElettronico(userData
					.getEmailaddress());
			    } else {
				if (accreditamentoAssociazioneCategoria) {
				    pplData.getProfiloOperatore()
					    .setDomicilioElettronico(
						    userData.getEmailaddress());
				} else {
				    pplData.getProfiloOperatore()
					    .setDomicilioElettronico(
						    userData.getEmailaddress());
				    pplData.getProfiloRichiedente()
					    .setDomicilioElettronico(
						    userData.getEmailaddress());
				}
			    }

			}

		    }
		} catch (Exception ignore) {
		    // Ignore
		}

		String queryString = request.getQueryString();

		String processName = "";
		String accreditato = "";

		HashMap<String, String> param = new HashMap<String, String>();
		if (queryString != null) {
		    String[] queryStringToken = queryString.split("&");
		    if (queryStringToken != null) {
			for (int i = 0; i < queryStringToken.length; i++) {
			    String[] par = queryStringToken[i].split("=");
			    if (par != null && par.length == 2) {
				param.put(par[0], par[1]);
			    }
			}
		    }
		}

		if (param.get("processName") != null) {
		    processName = (String) param.get("processName");
		}

		if (param.get("accreditato") != null) {
		    accreditato = (String) param.get("accreditato");
		}

		if (logger.isDebugEnabled()) {
		    logger.debug("Serving request resource '"
			    + RequiredUserDataVerifierServlet
				    .getAskForRequiredUserDataPage() + "'.");
		}
		// getServletContext().getRequestDispatcher(RequiredUserDataVerifierServlet.getAskForRequiredUserDataPage())
		// .forward(request, response);

		response.sendRedirect("initProcess.do?processName="
			+ processName + "&accreditato=" + accreditato);

		// getServletContext().getRequestDispatcher("/initProcess.do?processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&accreditato=TRUE")
		// .forward(request, response);

	    } else {
		// if (!servicesButtonKeySelected && !exitButtonKeySelected) {
		getServletContext().getRequestDispatcher(
			RequiredUserDataVerifierServlet
				.getAskForRequiredUserDataPage()).forward(
			request, response);
		// }
	    }
	} catch (ServletException se) {
	    se.printStackTrace();
	} catch (IOException ioe) {
	    ioe.printStackTrace();
	}

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.servlet.http.HttpServlet#doPut(javax.servlet.http.HttpServletRequest
     * , javax.servlet.http.HttpServletResponse)
     */
    protected void doPut(HttpServletRequest request,
	    HttpServletResponse response) {
	try {
	    super.doPut(request, response);
	} catch (ServletException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.servlet.http.HttpServlet#doTrace(javax.servlet.http.HttpServletRequest
     * , javax.servlet.http.HttpServletResponse)
     */
    protected void doTrace(HttpServletRequest request,
	    HttpServletResponse response) {
	try {
	    super.doTrace(request, response);
	} catch (ServletException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest
     * , javax.servlet.http.HttpServletResponse)
     */
    protected void service(HttpServletRequest request,
	    HttpServletResponse response) {
	try {
	    super.service(request, response);
	} catch (ServletException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.http.HttpServlet#service(javax.servlet.ServletRequest,
     * javax.servlet.ServletResponse)
     */
    public void service(ServletRequest request, ServletResponse response) {
	try {
	    super.service(request, response);
	} catch (ServletException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void init() throws ServletException {

	RequiredUserDataVerifierServlet.setAskForRequiredUserDataPage(this
		.getServletConfig().getInitParameter(
			ASK_FOR_REQUIRED_USER_DATA_PAGE_PARAMETER));

    }

    private static String getAskForRequiredUserDataPage() {
	return askForRequiredUserDataPage;
    }

    private static void setAskForRequiredUserDataPage(
	    String askForRequiredUserDataPage) {
	RequiredUserDataVerifierServlet.askForRequiredUserDataPage = askForRequiredUserDataPage;
    }

}
