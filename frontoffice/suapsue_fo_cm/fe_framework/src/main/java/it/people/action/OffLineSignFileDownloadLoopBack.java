/**
 * 
 */
package it.people.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import it.infocamere.util.signature.impl.bouncyCastle.FirmaUtils;
import it.people.Activity;
import it.people.core.PeopleContext;
import it.people.core.ProcessManager;
import it.people.core.persistence.exception.peopleException;
import it.people.offlinesignservice.web.servlet.OffLineSignFileDownloadServlet;
import it.people.process.AbstractPplProcess;
import it.people.process.sign.ConcreteSign;
import it.people.process.sign.entity.PDFBytesSigningData;
import it.people.process.sign.entity.SigningData;
import it.people.process.sign.IPdfSignStep;
import it.people.util.HtmlToPDF;
import it.people.util.MimeType;
import it.people.wrappers.HttpServletRequestDelegateWrapper;
import it.people.wrappers.IRequestWrapper;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 *         21/mag/2012 22:19:53
 */
public class OffLineSignFileDownloadLoopBack extends Action {

    private static Logger logger = LogManager
	    .getLogger(OffLineSignFileDownloadLoopBack.class);

    public ActionForward execute(ActionMapping p_actionMapping,
	    ActionForm p_actionForm, HttpServletRequest p_httpServletRequest,
	    HttpServletResponse p_httpServletResponse) throws Exception {

	super.execute(p_actionMapping, p_actionForm, p_httpServletRequest,
		p_httpServletResponse);

	AbstractPplProcess pplProcess = (AbstractPplProcess) p_actionForm;

	if (pplProcess.isInizialized()) {

	    ConcreteSign concreteSign = pplProcess.getSign();

	    String documentToSign = null;

	    String signedDocumentIndex = (p_httpServletRequest
		    .getParameter("signedDataIndex") != null) ? p_httpServletRequest
		    .getParameter("signedDataIndex") : "";

	    // This case should only identify A&C service while the else is for
	    // all others services.
	    if ((concreteSign == null || (concreteSign != null && !concreteSign
		    .isInitialized()))
		    && !pplProcess.getProcessName().equalsIgnoreCase(
			    "it.people.fsl.servizi.admin.sirac.accreditamento")) {

		try {
		    Activity m_activity = pplProcess.getView()
			    .getCurrentActivity();
		    SigningData signingData = ((IPdfSignStep) (m_activity
			    .getStep(m_activity.getCurrentStepIndex())))
			    .getSigningData(pplProcess, p_httpServletRequest,
				    p_httpServletResponse);

		    if (signingData instanceof PDFBytesSigningData) {

			// PDF Bytes in Base64 format.
			byte[] pdfToSign = signingData.getBytes();

			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
				getDocumentToSignBase64Decoded(pdfToSign));
			String documentHash = FirmaUtils.getDigestBase64(
				byteArrayInputStream,
				FirmaUtils.DIGEST_ALGORITHM_SHA256);
			byteArrayInputStream.close();

			documentToSign = new String(pdfToSign);

			pplProcess
				.setOffLineSignDownloadedDocumentHash(documentHash);
			pplProcess.setWaitingForOffLineSignedDocument(true);

			String documentName = "riepilogo"
				+ pplProcess.getIdentificatoreProcedimento()
				+ signedDocumentIndex + ".pdf";
			redirectToServlet(p_httpServletRequest,
				p_httpServletResponse, pplProcess,
				documentToSign, documentName);

			PeopleContext context = PeopleContext
				.create(p_httpServletRequest);
			IRequestWrapper requestWrapper = new HttpServletRequestDelegateWrapper(
				p_httpServletRequest);

			pplProcess.getView().loopBack(pplProcess,
				requestWrapper, null, 0);
			pplProcess.getView().draw(context,
				p_httpServletRequest, p_httpServletResponse);
			ProcessManager.getInstance().set(context, pplProcess);

			return null;

		    }
		} catch (Exception e) {
		    return p_actionMapping.findForward("failed");
		} catch (peopleException e) {
		    return p_actionMapping.findForward("failed");
		}

	    } else if (pplProcess.getProcessName().equalsIgnoreCase(
		    "it.people.fsl.servizi.admin.sirac.accreditamento")) {

		try {

		    Enumeration<String> params = p_httpServletRequest
			    .getParameterNames();
		    boolean foundSigningData = false;
		    while (params.hasMoreElements() && !foundSigningData) {
			String paramName = params.nextElement();
			if (paramName != null
				&& paramName.equalsIgnoreCase("signingData")) {
			    foundSigningData = true;
			}
		    }
		    if (!foundSigningData) {
			return p_actionMapping.findForward("failed");
		    }
		    String signingData = new String(
			    (p_httpServletRequest.getParameter("signingData")).getBytes(p_httpServletRequest
				    .getCharacterEncoding()),
			    p_httpServletRequest.getCharacterEncoding());
		    if (signingData != null
			    && !signingData.equalsIgnoreCase("empty")
			    && !signingData.equalsIgnoreCase("")) {
			signingData = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">"
				+ "<html><head>"
				+ "<meta http-equiv=\"Expires\" content=\"0\" />"
				+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset="
				+ p_httpServletRequest.getCharacterEncoding()
				+ "\" />"
				+ "</head><body>"
				+ signingData
				+ "</body></html>";
		    } else {
			return p_actionMapping.findForward("failed");
		    }

		    byte[] pdfDocument = HtmlToPDF.convertHtmlToPDF(signingData
			    .getBytes(p_httpServletRequest
				    .getCharacterEncoding()),
			    p_httpServletRequest.getCharacterEncoding());

		    documentToSign = getDocumentToSignBase64Encoded(
			    pdfDocument, p_httpServletRequest);

		    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
			    getDocumentToSignBase64Decoded(documentToSign
				    .getBytes()));
		    String documentHash = FirmaUtils.getDigestBase64(
			    byteArrayInputStream,
			    FirmaUtils.DIGEST_ALGORITHM_SHA256);
		    byteArrayInputStream.close();

		    pplProcess
			    .setOffLineSignDownloadedDocumentHash(documentHash);
		    pplProcess.setWaitingForOffLineSignedDocument(true);

		    String documentName = "autocertificazione.pdf";
		    redirectToServlet(p_httpServletRequest,
			    p_httpServletResponse, pplProcess, documentToSign,
			    documentName);

		    if (pplProcess.isInizialized()) {

			PeopleContext context = PeopleContext
				.create(p_httpServletRequest);
			ProcessManager.getInstance().set(context, pplProcess);
			IRequestWrapper requestWrapper = new HttpServletRequestDelegateWrapper(
				p_httpServletRequest);

			pplProcess.getView().loopBack(pplProcess,
				requestWrapper, "", 0);
			pplProcess.getView().draw(context,
				p_httpServletRequest, p_httpServletResponse);

			return null;

		    } else {
			return p_actionMapping.findForward("failed");
		    }
		} catch (peopleException e) {
		    return p_actionMapping.findForward("failed");
		}

	    } else {

		try {
		    SigningData signingData = pplProcess.getSign()
			    .getSigningData(p_httpServletRequest,
				    p_httpServletResponse);

		    byte[] pdfDocument = HtmlToPDF.convertHtmlToPDF(
			    signingData.getBytes(MimeType.COMPLETE_HTML),
			    p_httpServletRequest.getCharacterEncoding());

		    documentToSign = getDocumentToSignBase64Encoded(
			    pdfDocument, p_httpServletRequest);

		    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
			    getDocumentToSignBase64Decoded(documentToSign
				    .getBytes()));
		    String documentHash = FirmaUtils.getDigestBase64(
			    byteArrayInputStream,
			    FirmaUtils.DIGEST_ALGORITHM_SHA256);
		    byteArrayInputStream.close();

		    pplProcess
			    .setOffLineSignDownloadedDocumentHash(documentHash);
		    pplProcess.setWaitingForOffLineSignedDocument(true);

		    String documentName = "riepilogo"
			    + pplProcess.getIdentificatoreProcedimento()
			    + signedDocumentIndex + ".pdf";
		    redirectToServlet(p_httpServletRequest,
			    p_httpServletResponse, pplProcess, documentToSign,
			    documentName);

		    if (pplProcess.isInizialized()) {

			PeopleContext context = PeopleContext
				.create(p_httpServletRequest);
			ProcessManager.getInstance().set(context, pplProcess);
			IRequestWrapper requestWrapper = new HttpServletRequestDelegateWrapper(
				p_httpServletRequest);

			pplProcess.getView().loopBack(pplProcess,
				requestWrapper, null, 0);
			pplProcess.getView().draw(context,
				p_httpServletRequest, p_httpServletResponse);

			return null;

		    } else {
			return p_actionMapping.findForward("failed");
		    }
		} catch (peopleException e) {
		    return p_actionMapping.findForward("failed");
		}

	    }

	}

	if (logger.isDebugEnabled()) {
	    logger.debug("failed ");
	}

	return p_actionMapping.findForward("failed");

    }

    private void redirectToServlet(HttpServletRequest p_httpServletRequest,
	    HttpServletResponse p_httpServletResponse,
	    AbstractPplProcess pplProcess, String documentToSign,
	    String documentName) throws IOException {

	String documentSessionId = UUID.randomUUID().toString();
	p_httpServletRequest.getSession().setAttribute(documentSessionId,
		documentToSign);
	p_httpServletResponse.sendRedirect(OffLineSignFileDownloadServlet
		.getFixedURLPattern()
		+ "/"
		+ documentName
		+ "?"
		+ OffLineSignFileDownloadServlet.QUERY_STRING_PREFIX
		+ documentSessionId);

    }

    private String getDocumentToSignBase64Encoded(byte[] documentToSign,
	    HttpServletRequest p_httpServletRequest) throws IOException {

	String result = null;
	Base64 base64 = new Base64();
	byte[] encodedBytes = base64.encode(documentToSign);
	ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	byteArrayOutputStream.write(encodedBytes, 0, encodedBytes.length);
	byteArrayOutputStream.flush();

	result = new String(byteArrayOutputStream.toByteArray(),
		p_httpServletRequest.getCharacterEncoding());

	byteArrayOutputStream.close();

	try {
	    File fileTemp = new File("/Users/Ricky/fileTemp");
	    FileOutputStream fos = new FileOutputStream(fileTemp);
	    fos.write(documentToSign);
	    fos.flush();
	    fos.close();
	} catch (Exception e) {

	}

	return result;

    }

    private byte[] getDocumentToSignBase64Decoded(byte[] documentToSign)
	    throws IOException {

	Base64 base64 = new Base64();
	return base64.decode(documentToSign);

    }

}
