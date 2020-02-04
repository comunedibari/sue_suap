/**
 * 
 */
package it.people.offlinesignservice.web.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 *         21/mag/2012 22:34:09
 */
public class OffLineSignFileDownloadServlet extends HttpServlet {

    private Logger logger = Logger
	    .getLogger(OffLineSignFileDownloadServlet.class);

    private static final long serialVersionUID = -9048477523487852409L;

    private static final String APPLICATION_CONTENT_TYPE = "application/octet-stream";
    private static final String CONTENT_DISPOSITION_HEADER_NAME = "Content-Disposition";
    private static final String CONTENT_DISPOSITION_HEADER_PREFIX = "attachment;filename=";
    private static final String REQUEST_URI_PDF_FILE_EXTENSION = ".pdf";
    private static final char REQUEST_URI_CHAR_PATH_SEPARATOR = '/';

    private static final String FIXED_URL_PATTERN = "it.people.offlinesignservice.web.servlet.FIXED_URL_PATTERN";

    public static final String DEFAULT_URL_PATTERN = "olsfds";

    public static final String QUERY_STRING_PREFIX = "id=";

    private static String fixedURLPattern = null;

    public void init(ServletConfig config) throws ServletException {
	logger.info("Initializing OffLineSignFileDownloadServlet...");
	super.init(config);
	String fixedURLPattern = config.getInitParameter(FIXED_URL_PATTERN);
	if (fixedURLPattern == null || fixedURLPattern.length() == 0) {
	    throw new ServletException(FIXED_URL_PATTERN
		    + " cannot be null or an empty string.");
	}

	logger.info("OffLineSignFileDownloadServlet fixedURLPattern = "
		+ fixedURLPattern);
	setFixedURLPattern(fixedURLPattern);

	logger.info("OffLineSignFileDownloadServlet succesfully initialized.");
    }

    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {

	if (logger.isDebugEnabled()) {
	    logger.debug("Serving request resource: " + request.getRequestURI());
	    logger.debug("Request resource query string: "
		    + request.getQueryString());
	}

	String pdfFileName = this.getPdfFileName(request.getRequestURI());
	String documentSessionId = this.getDocumentSessionId(request
		.getQueryString());

	if (logger.isDebugEnabled()) {
	    logger.debug("Pdf file name: '" + pdfFileName + "'.");
	    logger.debug("Document session id: '" + documentSessionId + "'.");
	}

	String documentObject = String.valueOf(request.getSession()
		.getAttribute(documentSessionId));

	String fileName = pdfFileName.replace("%20", " ");
	response.setContentType(APPLICATION_CONTENT_TYPE);
	response.setHeader(CONTENT_DISPOSITION_HEADER_NAME,
		CONTENT_DISPOSITION_HEADER_PREFIX + "\"" + fileName + "\"");

	try {
	    byte[] documentToSign = getDocumentToSignBase64Decoded(documentObject
		    .getBytes());
	    response.setContentLength(documentToSign.length);
	    response.setCharacterEncoding(request.getCharacterEncoding());
	    response.getOutputStream().write(documentToSign);
	    response.getOutputStream().flush();
	} finally {
	    logger.info("Cleaning session document object.");
	    request.getSession().removeAttribute(documentSessionId);
	}

    }

    /**
     * @param requestURI
     * @return
     */
    protected String getPdfFileName(String requestURI) {

	String result = "";

	if (requestURI.endsWith(REQUEST_URI_PDF_FILE_EXTENSION)
		&& requestURI.lastIndexOf(REQUEST_URI_CHAR_PATH_SEPARATOR) > 0) {
	    result = requestURI.substring(requestURI
		    .lastIndexOf(REQUEST_URI_CHAR_PATH_SEPARATOR) + 1);
	}

	return result;

    }

    /**
     * @param requestQueryString
     * @return
     */
    protected String getDocumentSessionId(String requestQueryString) {

	String result = "";

	if (requestQueryString.indexOf(QUERY_STRING_PREFIX) >= 0) {
	    result = requestQueryString.substring(requestQueryString
		    .indexOf(QUERY_STRING_PREFIX)
		    + QUERY_STRING_PREFIX.length());
	}

	return result;

    }

    /**
     * @return the fixedURLPattern
     */
    public static String getFixedURLPattern() {
	return fixedURLPattern;
    }

    /**
     * @param fixedURLPattern
     *            the fixedURLPattern to set
     */
    private static void setFixedURLPattern(String fixedURLPattern) {
	OffLineSignFileDownloadServlet.fixedURLPattern = fixedURLPattern;
    }

    private byte[] getDocumentToSignBase64Decoded(byte[] documentToSign)
	    throws IOException {

	Base64 base64 = new Base64();
	return base64.decode(documentToSign);

    }

}
