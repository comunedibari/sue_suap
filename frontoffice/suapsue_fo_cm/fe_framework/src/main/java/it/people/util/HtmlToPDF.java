/**
 * 
 */
package it.people.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.UUID;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import it.people.propertymgr.PropertyFormatException;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 *         19/giu/2012 17:38:35
 */
public class HtmlToPDF {

    private static final Logger logger = LogManager.getLogger(HtmlToPDF.class);

    private static final String TEMPORARY_HTML_FILE_NAME = "tmpHtmlToConvert_847763";
    private static final String TEMPORARY_HTML_FILE_EXT = ".html";
    private static final String TEMPORARY_PDF_FILE_NAME = "tmpPdfOutput_847763";
    private static final String TEMPORARY_PDF_FILE_EXT = ".pdf";

    /**
	 * 
	 */
    private HtmlToPDF() {
    }

    /**
     * @param wkHtmlToPDFPath
     * @param temporaryPath
     * @param htmlDocument
     * @return
     * @throws IOException
     * @throws PropertyFormatException
     */
    public static synchronized byte[] convertHtmlToPDF(
	    final byte[] htmlDocument, final String encoding)
	    throws IOException, PropertyFormatException {

	byte[] result = null;

	String temporaryPath = PeopleProperties.UPLOAD_DIRECTORY
		.getValueString();
	String wkHtmlToPDFPath = PeopleProperties.WKHTMLTOPDF_SYSTEM_PATH
		.getValueString();

	if (!isValidProperty(temporaryPath)
		|| !isValidProperty(wkHtmlToPDFPath)) {
	    throw new PropertyFormatException(
		    "People properties upload.directory or wkhtmltopdf.system.path have not valid value.");
	}

	String pseudoRandomIdentifier = UUID.randomUUID().toString()
		+ String.valueOf(Calendar.getInstance().getTimeInMillis());
	String finalTemporaryPath = sanitizePath(temporaryPath);
	String completeHtmlFilePath = finalTemporaryPath
		+ TEMPORARY_HTML_FILE_NAME + pseudoRandomIdentifier
		+ TEMPORARY_HTML_FILE_EXT;
	String completePDFFilePath = finalTemporaryPath
		+ TEMPORARY_PDF_FILE_NAME + pseudoRandomIdentifier
		+ TEMPORARY_PDF_FILE_EXT;

	File htmlTemporaryFile = new File(completeHtmlFilePath);
	FileOutputStream fileOutputStream = new FileOutputStream(
		htmlTemporaryFile);
	OutputStreamWriter osw = new OutputStreamWriter(fileOutputStream,
		encoding);
	// fileOutputStream.write(htmlDocument);
	// fileOutputStream.flush();
	// fileOutputStream.close();
	osw.write(new String(htmlDocument, encoding));
	osw.flush();
	osw.close();

	ProcessBuilder processBuilder = new ProcessBuilder(wkHtmlToPDFPath,
		completeHtmlFilePath, completePDFFilePath);
	processBuilder.redirectErrorStream(true);

	Process process = processBuilder.start();
	BufferedReader inStreamReader = new BufferedReader(
		new InputStreamReader(process.getInputStream(), encoding));

	String line = inStreamReader.readLine();
	boolean logOutputMessages = logger.isDebugEnabled();

	while (line != null) {
	    if (logOutputMessages) {
		logger.debug(line);
	    }
	    line = inStreamReader.readLine();
	}

	FileInputStream pdfInputStream = new FileInputStream(
		completePDFFilePath);
	result = new byte[pdfInputStream.available()];
	pdfInputStream.read(result);
	pdfInputStream.close();

	if (!logger.isDebugEnabled()) {
	    doTemporaryFilesHouseKeeping(completeHtmlFilePath,
		    completePDFFilePath);
	}

	return result;

    }

    /**
     * @param path
     * @return
     */
    private static String sanitizePath(String path) {

	String fileSeparator = System.getProperty("file.separator");
	if (!path.endsWith(fileSeparator)) {
	    return path + fileSeparator;
	} else {
	    return path;
	}

    }

    /**
     * @param completeHtmlFilePath
     * @param completePDFFilePath
     */
    private static void doTemporaryFilesHouseKeeping(
	    final String completeHtmlFilePath, final String completePDFFilePath) {

	File completeHtmlFile = new File(completeHtmlFilePath);
	File completePDFFile = new File(completePDFFilePath);

	if (completeHtmlFile.exists()) {
	    if (!completeHtmlFile.delete()) {
		logger.error("Error while cleaning html temporary file '"
			+ completeHtmlFile + "'.");
	    }
	}

	if (completePDFFile.exists()) {
	    if (!completePDFFile.delete()) {
		logger.error("Error while cleaning PDF temporary file '"
			+ completeHtmlFile + "'.");
	    }
	}

    }

    /**
     * @param property
     * @return
     */
    private static boolean isValidProperty(final String property) {
	return (property != null && !property.trim().equalsIgnoreCase(""));
    }

}
