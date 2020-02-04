package it.cefriel.people.ssl;

import it.people.sirac.core.SiracHelper;
import it.people.sirac.idp.beans.ResRegBean;
import it.people.sirac.idp.registration.RegistrationClientAdapter;
import java.io.IOException;
import java.rmi.RemoteException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class ChangePasswordServlet extends HttpServlet {
	public static final String PASSWORD_MISMATCH = "Le password inserite non coincidono.";
	public static final String PASSWORD_TOO_SHORT = "La password deve essere di almeno 8 caratteri.";
	public static final String OLD_PASSWORD_INCORRECT = "La vecchia password non � corretta.";
	public static final String SAME_PASSWORD = "La nuova password non pu� coincidere con la vecchia.";
	String changePasswordPage = null;
	String changePasswordConfirmationPage = null;
	String wsreg_address = null;
	RegistrationClientAdapter regWS = null;
	private static final Logger logger = LoggerFactory.getLogger(ChangePasswordServlet.class);

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String codiceFiscale = request.getParameter("codiceFiscale");
		String target = request.getParameter("TARGET");
		boolean hasPasswordData = Boolean.valueOf(request.getParameter("hasPasswordData")).booleanValue();
		String path = request.getContextPath();
		String urlPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
				+ "/ChangePasswordService";
		if (!hasPasswordData) {
			request.setAttribute("nextStep", urlPath);
			request.setAttribute("target", target);
			request.setAttribute("codiceFiscale", codiceFiscale);
			RequestDispatcher rd = request.getRequestDispatcher(this.changePasswordPage);
			rd.forward(request, response);
			return;
		}
		String oldPwd = request.getParameter("oldPwd");
		String newPwd = request.getParameter("newPwd");
		String confirmPwd = request.getParameter("confirmPwd");
		if (!newPwd.equals(confirmPwd)) {
			request.setAttribute("codiceFiscale", codiceFiscale);
			request.setAttribute("message", "Le password inserite non coincidono.");
			request.setAttribute("nextStep", urlPath);
			request.setAttribute("target", target);
			RequestDispatcher rd = request.getRequestDispatcher(this.changePasswordPage);
			rd.forward(request, response);
			return;
		}
		if (newPwd.equals(oldPwd)) {
			request.setAttribute("codiceFiscale", codiceFiscale);
			request.setAttribute("message", "La nuova password non pu� coincidere con la vecchia.");
			request.setAttribute("nextStep", urlPath);
			request.setAttribute("target", target);
			RequestDispatcher rd = request.getRequestDispatcher(this.changePasswordPage);
			rd.forward(request, response);
			return;
		}
		if (newPwd.length() < 8) {
			request.setAttribute("codiceFiscale", codiceFiscale);
			request.setAttribute("message", "La password deve essere di almeno 8 caratteri.");
			request.setAttribute("nextStep", urlPath);
			request.setAttribute("target", target);
			RequestDispatcher rd = request.getRequestDispatcher(this.changePasswordPage);
			rd.forward(request, response);
			return;
		}
		try {
			ResRegBean res = this.regWS.changePassword(codiceFiscale, oldPwd, newPwd);
			if ("FAILED".equalsIgnoreCase(res.getEsito())) {
				request.setAttribute("codiceFiscale", codiceFiscale);
				request.setAttribute("message", res.getMessaggio());
				request.setAttribute("nextStep", urlPath);
				request.setAttribute("target", target);
				RequestDispatcher rd = request.getRequestDispatcher(this.changePasswordPage);
				rd.forward(request, response);
				return;
			}
			request.setAttribute("target", target);
			RequestDispatcher rd = request.getRequestDispatcher(this.changePasswordConfirmationPage);
			rd.forward(request, response);
			return;
		} catch (RemoteException ex) {
			logger.error("doPost() - Si � verificato un errore nella comunicazione con il servizio di registrazione.");
			SiracHelper.forwardToErrorPageWithRuntimeException(request, response, ex,
					"Si � verificato un errore nella comunicazione con il servizio di registrazione.");
		}
	}

	public void init(ServletConfig config) throws ServletException {
		if (logger.isDebugEnabled()) {
			logger.debug("init(config) - start");
		}
		this.changePasswordPage = config.getInitParameter("changePasswordPage");
		this.changePasswordConfirmationPage = config.getInitParameter("changePasswordConfirmationPage");

		this.wsreg_address = config.getServletContext().getInitParameter("wsreg_address");
		try {
			this.regWS = new RegistrationClientAdapter(this.wsreg_address);
			if (logger.isDebugEnabled()) {
				logger.debug("init(config) - Registration Web Service Address configurato corretamente ( "
						+ this.wsreg_address + ")");
			}
		} catch (Exception e) {
			throw new ServletException(
					"Impossibile inizializzare indirizzo web service di registrazione: " + this.wsreg_address);
		}
	}
}
