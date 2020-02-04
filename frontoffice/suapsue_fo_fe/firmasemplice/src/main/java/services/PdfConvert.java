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
package services;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.ICC_Profile;
import com.itextpdf.text.pdf.PdfArray;
import com.itextpdf.text.pdf.PdfBoolean;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfCopyFields;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfICCBased;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfString;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.xml.xmp.XmpWriter;
import com.sun.star.beans.PropertyValue;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Random;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.document.DefaultDocumentFormatRegistry;
import org.artofsolving.jodconverter.document.DocumentFamily;
import org.artofsolving.jodconverter.document.DocumentFormat;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeConnectionProtocol;
import org.artofsolving.jodconverter.office.OfficeException;
import org.artofsolving.jodconverter.office.OfficeManager;

import tools.FileDownloader;
import tools.PdfSignField;
import utils.ProxyManager;
import utils.UtilityFunctions;

/**
 * Converte in pdf un file ricevuto tramite richiesta http multipart
 *
 * @author Giuseppe
 */
public class PdfConvert extends HttpServlet {

	private static Logger logger = LoggerFactory.getLogger(PdfConvert.class);

	private final int MAX_OOFFICE_CONNECTION_TRIES = 30;

	private static OfficeManager officeManager = null;
	private static boolean serviceStarted = false;
	private static DefaultDocumentFormatRegistry dfr = null;
	private static DocumentFormat pdf = null;
	private static final Boolean mutex = Boolean.FALSE;
	private static int openOfficeOpenedConnections = 0;
	private static int openOfficeTotalConnectionsNumber = 0;
	private String serverConfFiles;

	// alla terminazione della servlet chiudo le connessioni aperte ad openoffice
	public void destroy() {
		if (logger.isInfoEnabled()) {
			logger.info("termino le istanze di openoffice...");
		}
		try {
			officeManager.stop();
			serviceStarted = false;
		} catch (Exception ex) {
		}
		super.destroy();
	}

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */

	/*
	 * Parametri della richiesta multipart:
	 *
	 * "SignField": indica la posizione in cui sarà collocata al firma nel
	 * documento. la stringa è composta come segue: "numero di pagina (mettere "n"
	 * per indicare l'ultima);largezza;altezza;margine destro;margine superiore.
	 * Esempio: signPosition = n;200;60;15;22; inserisce la firma all'ultima pagina
	 * in un rettangolo largo 200 e alto 60 distanziato dal margine destro 15 e dal
	 * margine superiore 22; E' possibile richiedere l'inserimento di più campi
	 * firma inserendo più "SignField".
	 *
	 * "ModelFileName": nome del modello da allegare. Solo il nome del file pdf, la
	 * cartella sarà sempre quella predefinita dei modelli;
	 *
	 * "FileUrl": URL del file da scaricare e convertire;
	 *
	 * File binario da convertire. Nel caso si passi il parametro "FileUrl" il file
	 * binario non va inserito.
	 *
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		serverConfFiles = getServletContext().getRealPath("") + "/ConfFiles";
		File recivedFile = null;
		File pdfFile = null;
		File tempDir = new File(serverConfFiles + "/temp/");
		File modelsDir = new File(serverConfFiles + "/modelli/");
		File settigsFile = new File(serverConfFiles + "/settings.properties");
		String modelFileName = null;
		URL FileUrl = null;

		DataInputStream datais = null;
		DataOutputStream dataosoutresp = null;

		if (!tempDir.exists())
			tempDir.mkdir();

		if (!modelsDir.exists())
			modelsDir.mkdir();

		try {
			ArrayList signFields = new ArrayList();

			try {
				if (ServletFileUpload.isMultipartContent(request)) {

					ServletFileUpload sfu = new ServletFileUpload(new DiskFileItemFactory(1024 * 1024, tempDir));
					List fileItems = sfu.parseRequest(request);
					// for (FileItem item : fileItems) {
					// leggo i parametri multipart
					for (int elementIndex = 0; elementIndex < fileItems.size(); elementIndex++) {

						FileItem item = (FileItem) fileItems.get(elementIndex);
						if (item.isFormField() && item.getFieldName().equals("SignField")) { // coordinate degli
																								// eventuali campi firma
																								// da aggiungere al pdf
							if (logger.isDebugEnabled()) {
								logger.debug("Coordinate campo firma: " + item.getString());
							}
							try {
								StringTokenizer tok = new StringTokenizer(item.getString(), ";");

								String page;
								float llx, lly, urx, ury;
								String signName;
								// boolean hiddenSignature;

								page = tok.nextToken();
								llx = Float.parseFloat(tok.nextToken());
								lly = Float.parseFloat(tok.nextToken());
								urx = Float.parseFloat(tok.nextToken());
								ury = Float.parseFloat(tok.nextToken());
								signName = tok.nextToken();
								// hiddenSignature = Boolean.parseBoolean(tok.nextToken());
								PdfSignField signField = new PdfSignField(page, llx, lly, urx, ury, signName);
								signFields.add(signField);
							} catch (NumberFormatException nfe) {
								logger.error("Errore nei parametri dei campi firma da inserire", nfe);
								item.delete();
								response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
								recivedFile.delete();
								return;
								// throw new NumberFormatException("Errore nei parametri dei campi firma da
								// inserire");
							} catch (NoSuchElementException nsee) {
								logger.error("Numero di parametri del campo firma errato", nsee);
								item.delete();
								response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
								recivedFile.delete();
								return;
								// throw new NoSuchElementException("Numero di parametri del campo firma
								// errato");
							}
						} else if (item.isFormField() && item.getFieldName().equals("ModelFileName")) { // leggo
																										// l'eventuale
																										// modello da
																										// allegare al
																										// documento
							modelFileName = item.getString().substring(0, item.getString().lastIndexOf(".")) + ".pdf";
							if (logger.isDebugEnabled()) {
								logger.debug("Modello da allegare: " + modelFileName);
							}
						} else if (item.isFormField() && item.getFieldName().equals("FileUrl")) { // leggo l'eventuale
																									// Url del file da
																									// scaricare
							try {
								FileUrl = new URL(item.getString());
							} catch (MalformedURLException ex) {
								logger.error("", ex);
								response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
								return;
							}
							if (logger.isDebugEnabled()) {
								logger.debug("Url del file da scaricare: " + FileUrl);
							}
						}

						else if (!item.isFormField()) { // Il file ricevuto. Lo salvo temporaneamente su disco

							InputStream fis = null;
							FileOutputStream fos = null;
							try {
								fis = item.getInputStream();
								String recivedFilePathAndName = tempDir.getAbsolutePath() + "/"
										+ request.getSession().getId() + item.getName();
								// rimunovo eventuali caratteri "?" che mi possono generare errori nella
								// creazione del file
								recivedFilePathAndName = recivedFilePathAndName.replace("?", "");

								recivedFile = new File(recivedFilePathAndName);

								fos = new FileOutputStream(recivedFile);
								byte[] readData = new byte[1024];
								int i = fis.read(readData);
								while (i != -1) {
									fos.write(readData, 0, i);
									i = fis.read(readData);
								}
							} finally {
								if (fis != null)
									fis.close();
								if (fos != null)
									fos.close();
							}

							if (UtilityFunctions.isPdf(recivedFile)) {
								String recivedFilePathAndName = recivedFile.getPath().replace(".___", ".pdf");
								File newFile = new File(recivedFilePathAndName);
								recivedFile.renameTo(newFile);
							}

						}
						item.delete(); // elimino l'eventuale file temporaneao creato nel parsing della request (N.B.
										// non elimino il file appena creato)
					}
				} else {
					response.getWriter().print("Il servizio supporta solo richieste multipart");
					response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
					return;
				}
			} catch (FileUploadException fuex) {
				logger.error("", fuex);
				if (recivedFile != null)
					recivedFile.delete();
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}

			Properties settings = new Properties();
			FileInputStream settingsInputStream = null;

			try {
				settingsInputStream = new FileInputStream(settigsFile.getAbsolutePath());
				settings.load(settingsInputStream);
				settingsInputStream.close();
			} catch (FileNotFoundException ex) {
				logger.error("", ex);
				response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
				recivedFile.delete();
				return;
			} catch (IOException ex) {
				logger.error("", ex);
				response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
				recivedFile.delete();
				return;
			}

			String filePrefix = request.getSession().getId();

			if (FileUrl != null) {

				// setto il proxy leggendo i parametri dal file di configurazione
				// "settings.properties"
				String proxyHost = null;
				String proxyPort = null;
				String proxyUsername = null;
				String proxyPassword = null;
				try {
					proxyHost = settings.getProperty("proxyHost");
					if (proxyHost.equals("")) {
						proxyHost = null;
					}
					proxyPort = settings.getProperty("proxyPort");
					if (proxyPort.equals("")) {
						proxyPort = null;
					}
					proxyUsername = settings.getProperty("proxyUsername");
					if (proxyUsername.equals("")) {
						proxyUsername = null;
					}
					proxyPassword = settings.getProperty("proxyPassword");
					if (proxyPassword.equals("")) {
						proxyPassword = null;
					}
				} catch (NullPointerException ex) {
				}

				if (proxyHost != null && proxyPort != null) {
					char[] proxyPasswordCharArray = null;
					if (proxyPassword != null)
						proxyPasswordCharArray = proxyPassword.toCharArray();

					ProxyManager proxy = new ProxyManager(proxyHost, proxyPort, proxyUsername, proxyPasswordCharArray);
					proxy.updateSystemProxy();
				}
				try {
					recivedFile = new FileDownloader(false).downloadFileFromUrl(FileUrl,
							tempDir.getAbsolutePath() + "/" + filePrefix, false);
					if (recivedFile == null) {
						response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
						return;
					}
				} catch (FileNotFoundException ex) {
					logger.error("", ex);
					response.setStatus(HttpServletResponse.SC_NO_CONTENT);
					return;
				}

				// // il client di conversione si identifica specificando nell'header
				// "User-agent" la stringa "ConverterClient"
				// if (request.getHeader("User-agent") != null &&
				// request.getHeader("User-agent").equalsIgnoreCase("ConverterClient") &&
				// isPdf(recivedFile)) {
				//
				// // se il file scaricato è un pdf indico uno status di errore perché è inutile
				// convertire un file che è già un pdf
				// response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
				// return;
				// }

				if ((hasModelPage(recivedFile) && modelFileName != null)
						|| (modelFileName != null && UtilityFunctions.isSigned(recivedFile))) {
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					return;
				}
			}

			// se il file è un pdf non faccio niente
			// se mi passano una url di un file da scaricare e il file scaricato è firmato
			// non lo tocco, mentre se mi inviano direttamente un pdf firmato allora lo
			// riconverto anche se così invaliderei la firma (perché vuol dire che
			// probabilmente l'utente vuole trasformarlo in pdf/a)
			if (UtilityFunctions.isP7m(recivedFile) || (FileUrl != null && UtilityFunctions.isSigned(recivedFile))) {
				// if (modelFileName == null && signFields.isEmpty() && FileUrl == null) {
				// response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
				// return;
				// }
				// else
				// pdfFile = recivedFile;
				pdfFile = recivedFile;
			} else { // se il file non è un pdf, lo converto in pdf

				// converto in pdf/a utilizzando openoffice e elimino il file ricevuto creato
				// prima

				// lettura delle impostazioni di openoffice dal file di configurazione
				File openOfficeHome = null;
				try {
					openOfficeHome = new File(settings.getProperty("openOfficeHome"));
				} catch (NullPointerException ex) {
					logger.error("", ex);
					response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
					recivedFile.delete();
					return;
				}
				if (!openOfficeHome.exists()) {
					if (logger.isInfoEnabled()) {
						logger.info("la directory: \"" + openOfficeHome + "\" non esiste");
					}
					response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
					recivedFile.delete();
					return;
				}

				String openOfficeConnectionProtocol = null;
				try {
					openOfficeConnectionProtocol = settings.getProperty("openOfficeConnectionProtocol");
				} catch (NullPointerException ex) {
					logger.error(
							"Errore nella lettura di \"openOfficeConnectionProtocol\" dal file \"settings.properties\"",
							ex);
					response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
					recivedFile.delete();
					return;
				}
				if (!(openOfficeConnectionProtocol.equalsIgnoreCase("pipe")
						|| openOfficeConnectionProtocol.equalsIgnoreCase("socket"))) {
					logger.error(
							"Errore nella lettura di \"openOfficeConnectionProtocol\" dal file \"settings.properties\", i valori accettati sono: \"pipe\" o \"socket\"");
					response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
					recivedFile.delete();
					return;
				}

				int openOfficePipesNumber = 0;
				if (openOfficeConnectionProtocol.equalsIgnoreCase("pipe")) {
					openOfficePipesNumber = 1;
					try {
						openOfficePipesNumber = Integer.parseInt(settings.getProperty("openOfficePipesNumber"));
					} catch (NullPointerException ex) {
					} catch (NumberFormatException ex) {
					}
				}

				// se la modalità di connessione a openoffice è socket leggo i parametri
				// riguardanti le porte
				int openOfficeExtraPort = 0;
				int openOfficeRandomPortsNumbers = 0;
				int[] openOfficePorts = null;
				if (openOfficeConnectionProtocol.equalsIgnoreCase("socket")) {
					try {
						openOfficeRandomPortsNumbers = Integer
								.parseInt(settings.getProperty("openOfficeRandomPortsNumbers"));
					} catch (NullPointerException ex) {
					} catch (NumberFormatException ex) {
					}

					// leggo le porte sulle quali istanziare Openoffice
					String openOfficeFixedPorts = null;
					try {
						openOfficeFixedPorts = settings.getProperty("openOfficeFixedPorts");
						if (openOfficeFixedPorts.equals(""))
							openOfficeFixedPorts = null;
					} catch (NullPointerException ex) {
					}

					if (openOfficeFixedPorts == null && openOfficeRandomPortsNumbers == 0) {
						logger.error(
								"Errore nella lettura del file \"settings.properties\", devi specificare uno tra \"openOfficeFixedPorts\" e \"openOfficeRandomPortsNumbers\"");
						response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
						recivedFile.delete();
						return;
					}
					ArrayList portsList = new ArrayList();
					if (openOfficeFixedPorts != null) {
						try {
							StringTokenizer tok = new StringTokenizer(openOfficeFixedPorts, ",");
							while (tok.hasMoreTokens()) {
								Integer port = Integer.valueOf(tok.nextToken());
								portsList.add(port);
							}
						} catch (Exception ex) {
							logger.error(
									"Errore nella lettura del file \"settings.properties\", il parametro \"openOfficeFixedPorts\" deve essere uan lista di interi separati da \",\"",
									ex);
							response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
							recivedFile.delete();
							return;
						}
					}

					if (portsList.isEmpty()) {
						openOfficePorts = new int[0];
						if (logger.isInfoEnabled()) {
							logger.info("Porte fisse di openoffice non specificate, saranno usate "
									+ openOfficeRandomPortsNumbers + " porte libere causali");
						}
					} else {
						if (logger.isInfoEnabled()) {
							logger.info("Porte fisse di openoffice che saranno usate: " + openOfficeFixedPorts);
						}
						openOfficeRandomPortsNumbers = 0;
						openOfficePorts = new int[portsList.size()];
						for (int i = 0; i < portsList.size(); i++) {
							Integer value = (Integer) portsList.get(i);
							openOfficePorts[i] = value.intValue();
						}
					}

					//
					try {
						openOfficeExtraPort = Integer.parseInt(settings.getProperty("openOfficeExtraPort"));
					} catch (NullPointerException ex) {
					} catch (NumberFormatException ex) {
						// System.out.println("Errore nella lettura del file settings.properties, il
						// dato \"openOfficeExtraPort\" non è un numero intero, uso una porta casuale");
					}
					if (openOfficeExtraPort == 0) {
						if (logger.isInfoEnabled()) {
							logger.info(
									"Porta extra per le connessioni di openoffice non specificata, sarà usata una porta libera casuale");
						}
					} else {
						if (logger.isInfoEnabled()) {
							logger.info("Porta extra per le connessioni di openoffice: " + openOfficeExtraPort);
						}
					}
				} else {

				}

				try {
					pdfFile = convertPDF(recivedFile, openOfficeHome, openOfficeConnectionProtocol,
							openOfficePipesNumber, openOfficeRandomPortsNumbers, openOfficePorts, openOfficeExtraPort);
					if (pdfFile == null || !pdfFile.exists() || pdfFile.length() == 0) {
						if (!response.isCommitted())
							response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
						return;
					}
					// rimuovo i metadati facendone una copia e setto l'output intent
					try {
						String tempFileNoAuthor = tempDir + "/" + filePrefix + "_tempfile_remove_author";
						FileOutputStream tempos = new FileOutputStream(tempFileNoAuthor);
						PdfReader reader = new PdfReader(pdfFile.getAbsolutePath());

						PdfCopyFields copy = new PdfCopyFields(tempos);
						PdfWriter writer = copy.getWriter();

						writer.setPDFXConformance(PdfCopy.PDFA1A); // per rendere il nuovo file PDF/A-1A
						copy.addDocument(reader);

						// per rendere il nuovo file PDF/A
						PdfDictionary outi = new PdfDictionary(PdfName.OUTPUTINTENT);
						outi.put(PdfName.OUTPUTCONDITIONIDENTIFIER, new PdfString("sRGB IEC61966-2.1"));
						outi.put(PdfName.INFO, new PdfString("sRGB IEC61966-2.1"));
						outi.put(PdfName.S, new PdfName("GTS_PDFA1"));
						ICC_Profile icc = ICC_Profile
								.getInstance(new FileInputStream(modelsDir.getAbsolutePath() + "/srgb.profile"));
						PdfICCBased ib = new PdfICCBased(icc);
						ib.remove(PdfName.ALTERNATE);
						outi.put(PdfName.DESTOUTPUTPROFILE, writer.addToBody(ib).getIndirectReference());
						writer.getExtraCatalog().put(PdfName.OUTPUTINTENTS, new PdfArray(outi));

						// Structure Tree Root
						PdfDictionary structureTreeRoot = new PdfDictionary();
						structureTreeRoot.put(PdfName.TYPE, PdfName.STRUCTTREEROOT);
						writer.getExtraCatalog().put(PdfName.STRUCTTREEROOT, structureTreeRoot);
						// MarkInfo
						PdfDictionary markInfo = new PdfDictionary();
						markInfo.put(PdfName.MARKED, new PdfBoolean(true));
						writer.getExtraCatalog().put(PdfName.MARKINFO, markInfo);

						writer.createXmpMetadata();
						writer.close();
						copy.close();
						reader.close();

						pdfFile.delete();
						pdfFile = new File(tempFileNoAuthor);
					} catch (Exception subEx) {
						logger.error("", subEx);
						response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
						return;
					}
				} catch (IndexOutOfBoundsException ioobex) {
					logger.error("", ioobex);
					response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
					return;
				} catch (ConnectException connex) {
					logger.error("", connex);
					response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
					return;
				} catch (Exception ex) {
					logger.error("", ex);
					response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
					return;
				}

				finally {
					try {
						recivedFile.delete();
					} catch (Exception e) {
					}

				}
			}

			String pdfFileName = pdfFile.getName().replace(filePrefix, "").replace("%20", " ");
			if (logger.isDebugEnabled()) {
				logger.debug("pdfFileName: " + pdfFileName);
			}

			// se ho ricevuto un file binario (non una URL) ed è un pdf
			if ((FileUrl == null && UtilityFunctions.isPdf(pdfFile)) ||
			// oppure se ho ricevuto un file tramite una URL e questo file è pdf e non
			// firmato
					((FileUrl != null) && UtilityFunctions.isPdf(pdfFile) && !UtilityFunctions.isSigned(pdfFile))) {

				// aggiungo gli eventuali campi firma letti prima nella request
				if (logger.isDebugEnabled()) {
					logger.debug("aggiungo gli eventuali campi firma...");
				}

				for (int elementIndex = 0; elementIndex < signFields.size(); elementIndex++) {

					PdfSignField signField = (PdfSignField) signFields.get(elementIndex);

					if (logger.isDebugEnabled()) {
						logger.debug("aggiungo: " + signField.toString());
					}
					try {
						signField.insertIntoPdf(pdfFile);
					} catch (NumberFormatException ex) {
						logger.error("", ex);
						response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						return;
					} catch (DocumentException ex) {
						logger.error("", ex);
						response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						return;
					} catch (IOException ex) {
						logger.error("", ex);
						response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						return;
					} catch (Exception ex) {
						logger.error("", ex);
						response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						return;
					}

				}
				if (logger.isDebugEnabled()) {
					logger.debug("aggiunta dei campi firma terminata.");
				}

				// aggiungo la pagina modello con le firme al pdf
				if (modelFileName != null) {
					if (logger.isDebugEnabled()) {
						logger.debug("aggiunta il modello: " + modelFileName + "...");
					}
					PdfReader reader1 = new PdfReader(pdfFile.getPath());
					PdfReader reader2 = new PdfReader(modelsDir.getAbsolutePath() + "/" + modelFileName);

					PdfCopyFields copy = null;
					PdfWriter writer = null;
					PdfReader tempReader = null;
					PdfStamper tempStamper = null;

					try {
						copy = new PdfCopyFields(
								new FileOutputStream(pdfFile.getParent() + "/merged_" + pdfFile.getName()));
						writer = copy.getWriter();

						writer.setPDFXConformance(PdfCopy.PDFA1A); // per rendere il nuovo file PDF/A-1A
						copy.addDocument(reader2);
						copy.addDocument(reader1);

						if (logger.isDebugEnabled()) {
							logger.debug("aggiunta dei metadati e dei dati per rendere il pdf PDF/A...");
						}

						// per rendere il nuovo file PDF/A
						PdfDictionary outi = new PdfDictionary(PdfName.OUTPUTINTENT);
						outi.put(PdfName.OUTPUTCONDITIONIDENTIFIER, new PdfString("sRGB IEC61966-2.1"));
						outi.put(PdfName.INFO, new PdfString("sRGB IEC61966-2.1"));
						outi.put(PdfName.S, new PdfName("GTS_PDFA1"));
						ICC_Profile icc = ICC_Profile
								.getInstance(new FileInputStream(modelsDir.getAbsolutePath() + "/srgb.profile"));
						PdfICCBased ib = new PdfICCBased(icc);
						ib.remove(PdfName.ALTERNATE);
						outi.put(PdfName.DESTOUTPUTPROFILE, writer.addToBody(ib).getIndirectReference());
						writer.getExtraCatalog().put(PdfName.OUTPUTINTENTS, new PdfArray(outi));

						// Structure Tree Root
						PdfDictionary structureTreeRoot = new PdfDictionary();
						structureTreeRoot.put(PdfName.TYPE, PdfName.STRUCTTREEROOT);
						writer.getExtraCatalog().put(PdfName.STRUCTTREEROOT, structureTreeRoot);
						// MarkInfo
						PdfDictionary markInfo = new PdfDictionary();
						markInfo.put(PdfName.MARKED, new PdfBoolean(true));
						writer.getExtraCatalog().put(PdfName.MARKINFO, markInfo);

						writer.createXmpMetadata();
						writer.close();
						copy.close();
						pdfFile.delete();
						pdfFile = new File(pdfFile.getParent() + "/merged_" + pdfFile.getName());

						// aggiungo un'idetificativo che segnala che al documento è stato aggiundo il
						// modello con le firme
						tempReader = new PdfReader(pdfFile.getAbsolutePath());
						tempStamper = new PdfStamper(tempReader,
								new FileOutputStream(pdfFile.getParent() + "/metadated__" + pdfFile.getName()));
						tempReader.getCatalog().putEx(PdfName.USER, new PdfString("PaginaFirme=" + modelFileName));
						tempStamper.close();
						pdfFile.delete();
						pdfFile = new File(pdfFile.getParent() + "/metadated__" + pdfFile.getName());
					} catch (Exception ex) {
						logger.error("", ex);
						if (reader1 != null)
							reader1.close();
						if (reader2 != null)
							reader2.close();
						if (tempReader != null)
							tempReader.close();
						try {
							if (tempStamper != null)
								tempStamper.close();
						} catch (DocumentException internalEx) {
							logger.error("", internalEx);
							response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
						} catch (IOException internalEx) {
							logger.error("", internalEx);
							response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
						}
						if (copy != null)
							copy.close();
						if (writer != null)
							writer.close();
						if (recivedFile != null)
							recivedFile.delete();
						if (pdfFile != null)
							pdfFile.delete();
						response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
						return;
					}
					if (logger.isDebugEnabled()) {
						logger.debug("aggiunta del modello terminata.");
					}
				}
			}

			response.setHeader("Content-Disposition", "attachment; filename=" + pdfFileName);
			OutputStream outresp = response.getOutputStream();

			// invio il file al client
			if (logger.isInfoEnabled()) {
				logger.info("Invio del file al client...");
			}
			// FileInputStream fis = new FileInputStream(pdfFile);
			byte[] readData = new byte[Integer.parseInt((Long.toString(pdfFile.length())))];
			datais = new DataInputStream(new FileInputStream(pdfFile));
			dataosoutresp = new DataOutputStream(outresp);
			datais.readFully(readData);
			dataosoutresp.write(readData);
			datais.close();
			dataosoutresp.close();
			if (logger.isInfoEnabled()) {
				logger.info("Invio del file al client terminato.");
			}

			pdfFile.delete(); // il pdf ora non serve più, lo elimino
		} catch (Exception ex) {
			try {
				datais.close();
			} catch (Exception subEx) {
			}
			try {
				dataosoutresp.close();
			} catch (Exception subEx) {
			}
			if (recivedFile != null)
				recivedFile.delete();
			if (pdfFile != null)
				pdfFile.delete();
			logger.error("", ex);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
	}

	// converte in pdf un file
	private File convertPDF(File inputFile, File openOfficeHome, String openOfficeConnectionProtocol,
			int openOfficePipesNumber, int openOfficeRandomPortsNumbers, int[] openOfficePort, int openOfficeExtraPort)
			throws ConnectException {
		File pdfFile = null;
		String inputFileExt = null;
		String[] openOfficePipesNames = null;

		try {
			pdfFile = new File(inputFile.getParent() + "/"
					+ inputFile.getName().substring(0, inputFile.getName().lastIndexOf('.')) + "_converted.pdf");
			inputFileExt = inputFile.getName().substring(inputFile.getName().lastIndexOf('.') + 1);
		} catch (IndexOutOfBoundsException ioobe) {
			logger.error("Il file non ha estensione, non riesco a capire il suo formato", ioobe);
			throw new IndexOutOfBoundsException("Il file non ha estensione, non riesco a capire il suo formato");
		}

		// OfficeManager officeManager = null;
		// boolean newInstanceRequired = false;
		// synchronized (mutex) {
		// mutexAcquired = true;

		// boolean serviceStarted = false;
		// if (officeManager != null) {
		// serviceStarted = true;
		// }

		// mi connetto a un'istanza di OpenOffice.org e riprovo finché non ci riesco o
		// fino a che non esaurisco il numero di tentativi massimi
		if (!serviceStarted) {
			if (logger.isInfoEnabled()) {
				logger.info("Sto per entrare in mutua esclusione per la creazione delle connessioni a openoffice...");
			}
			synchronized (this) {
				int tries = 0;
				while (!serviceStarted && tries < MAX_OOFFICE_CONNECTION_TRIES) {
					tries++;
					if (logger.isInfoEnabled()) {
						logger.info("tentativo " + tries + " di " + MAX_OOFFICE_CONNECTION_TRIES + "...");
					}
					if (openOfficeConnectionProtocol.equalsIgnoreCase("socket")) {
						if (openOfficeRandomPortsNumbers > 0) {
							openOfficePort = new int[openOfficeRandomPortsNumbers];
						}
						openOfficeTotalConnectionsNumber = openOfficePort.length;
						if (logger.isInfoEnabled()) {
							logger.info("connessione a OpenOffice sulle porte: ");
						}
						for (int i = 0; i < openOfficePort.length; i++) {
							if (openOfficeRandomPortsNumbers > 0) {
								openOfficePort[i] = new Random().nextInt(65534) + 1;
							}
							if (logger.isInfoEnabled()) {
								if (i < openOfficePort.length - 1) {
									logger.info(openOfficePort[i] + ", ");

								} else {
									logger.info("", openOfficePort[i]);
								}
							}
						}
					} else {
						openOfficePipesNames = new String[openOfficePipesNumber];
						for (int i = 0; i < openOfficePipesNumber; i++) {
							openOfficePipesNames[i] = "___openoffice_pipe_" + i;
						}
						openOfficeTotalConnectionsNumber = openOfficePipesNumber;
					}

					try {
						File homeDirectory = new File(System.getProperty("user.home") + "/ootmp");
						// File homeDirectory = new File (System.getProperty("user.home"));
						if (!homeDirectory.getParentFile().canWrite())
							throw new ConnectException();
						if (!homeDirectory.exists())
							homeDirectory.mkdir();
						String saved_properties = System.getProperty("java.io.tmpdir");
						// System.setProperty("java.io.tmpdir", homeDirectory.getAbsolutePath());

						DefaultOfficeManagerConfiguration defaultOfficeManager = new DefaultOfficeManagerConfiguration();
						defaultOfficeManager.setOfficeHome(openOfficeHome);
						// defaultOfficeManager.setMaxTasksPerProcess(openOfficePort.length);
						if (openOfficeConnectionProtocol.equalsIgnoreCase("socket")) {
							defaultOfficeManager.setConnectionProtocol(OfficeConnectionProtocol.SOCKET);
							defaultOfficeManager.setPortNumbers(openOfficePort);
						} else {
							defaultOfficeManager.setConnectionProtocol(OfficeConnectionProtocol.PIPE);
							defaultOfficeManager.setPipeNames(openOfficePipesNames);
						}

						defaultOfficeManager.setTaskExecutionTimeout(Long.MAX_VALUE);
						// defaultOfficeManager.setTemplateProfileDir(new File("c:/temp/"));
						officeManager = defaultOfficeManager.buildOfficeManager();

						// System.setProperty("java.io.tmpdir", saved_properties);
					} catch (Exception ex) {
						logger.error("impossibile connettersi a OpenOffice sulla porta " + openOfficePort + ".", ex);
						throw new ConnectException(
								"impossibile connettersi a OpenOffice sulla porta " + openOfficePort + ".");
					}

					try {
						officeManager.start();
						serviceStarted = true;
						if (logger.isInfoEnabled()) {
							logger.info("connessione riuscita.");
						}

						// setto l'opzione per creare un PDF/A
						if (logger.isInfoEnabled()) {
							logger.info("settaggio delle opzioni di conversione...");
						}

						dfr = new DefaultDocumentFormatRegistry();

						BufferedReader extFileList = null;
						try {
							extFileList = new BufferedReader(
									new FileReader(serverConfFiles + "/openoffice_supported_files.ini"));
						} catch (FileNotFoundException ex) {
							logger.error("", ex);
							return null;
						}

						String line;
						try {
							line = extFileList.readLine();
							while (line != null) {
								StringTokenizer tok = new StringTokenizer(line, ";");
								String fileExt = null;
								String filter = null;
								String family = null;
								try {
									fileExt = tok.nextToken();
									filter = tok.nextToken();
									family = tok.nextToken();
								} catch (NoSuchElementException ex) {
									logger.error("", ex);
									return null;
								}

								DocumentFormat option = new DocumentFormat(fileExt, fileExt, fileExt);
								Map storeOption = new HashMap();
								storeOption.put("FilterName", filter);
								PropertyValue[] aFilterData1 = new PropertyValue[1];
								aFilterData1[0] = new PropertyValue();
								aFilterData1[0].Name = "SelectPdfVersion";
								aFilterData1[0].Value = Integer.valueOf(1);
								option.setStoreProperties(DocumentFamily.valueOf(family), storeOption);

								if (dfr.getFormatByExtension(fileExt) != null) {
									dfr.getFormatByExtension(fileExt).setStoreProperties(DocumentFamily.valueOf(family),
											storeOption);
								} else {
									dfr.addFormat(option);
								}
								line = extFileList.readLine();
							}
						} catch (IOException ex) {
							logger.error("", ex);
							return null;
						}

						Map writerPdfOptions = new HashMap();
						writerPdfOptions.put("FilterName", "writer_pdf_Export");
						PropertyValue[] aFilterData = new PropertyValue[1];
						aFilterData[0] = new PropertyValue();
						aFilterData[0].Name = "SelectPdfVersion";
						aFilterData[0].Value = Integer.valueOf(1);
						writerPdfOptions.put("FilterData", aFilterData);

						Map calcPdfOptions = new HashMap();
						calcPdfOptions.put("FilterName", "calc_pdf_Export");
						aFilterData = new PropertyValue[1];
						aFilterData[0] = new PropertyValue();
						aFilterData[0].Name = "SelectPdfVersion";
						aFilterData[0].Value = Integer.valueOf(1);
						calcPdfOptions.put("FilterData", aFilterData);

						Map impressPdfOptions = new HashMap();
						impressPdfOptions.put("FilterName", "impress_pdf_Export");
						aFilterData = new PropertyValue[1];
						aFilterData[0] = new PropertyValue();
						aFilterData[0].Name = "SelectPdfVersion";
						aFilterData[0].Value = Integer.valueOf(1);
						impressPdfOptions.put("FilterData", aFilterData);

						Map drawPdfOptions = new HashMap();
						drawPdfOptions.put("FilterName", "draw_pdf_Export");
						aFilterData = new PropertyValue[1];
						aFilterData[0] = new PropertyValue();
						aFilterData[0].Name = "SelectPdfVersion";
						aFilterData[0].Value = Integer.valueOf(1);
						drawPdfOptions.put("FilterData", aFilterData);

						// DocumentFormat txt = new DocumentFormat("Plain Text", "txt", "text/plain");
						// txt.setInputFamily(DocumentFamily.TEXT);
						Map txtLoadProperties = new HashMap();
						// txtLoadProperties.put("Hidden", true);
						// txtLoadProperties.put("ReadOnly", true);
						txtLoadProperties.put("FilterName", "Text (encoded)");
						txtLoadProperties.put("FilterOptions", "utf-8");
						// txt.setLoadProperties(txtLoadProperties);
						dfr.getFormatByExtension("txt").setLoadProperties(txtLoadProperties);

						pdf = new DocumentFormat("Portable Document Format", "pdf", "application/pdf");
						pdf.setStoreProperties(DocumentFamily.TEXT, writerPdfOptions);
						pdf.setStoreProperties(DocumentFamily.SPREADSHEET, calcPdfOptions);
						pdf.setStoreProperties(DocumentFamily.PRESENTATION, impressPdfOptions);
						pdf.setStoreProperties(DocumentFamily.DRAWING, drawPdfOptions);
					} catch (Exception ex) {
						logger.error("Errore nella connessione provo su altre porte...", ex);
						try {
							officeManager.stop();
						} catch (Exception officeException) {
						}
					}
				}
			} // fine mutua esclusione per la creazione delle connessioni di openoffice
		}

		if (dfr.getFormatByExtension(inputFileExt) == null) {
			logger.error("formato \"" + inputFileExt + "\" non supportato");
			return null;
		}

		// converto il documento
		if (logger.isInfoEnabled()) {
			logger.info("conversione del documento...");
		}
		OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager, dfr);
		try {

			synchronized (mutex) {
				if (logger.isInfoEnabled()) {
					logger.info("openOfficeOpenedPorts: " + openOfficeOpenedConnections);
				}
				// se c'è uno slot libero lo occupo

				if (openOfficeOpenedConnections < openOfficeTotalConnectionsNumber) {
					if (logger.isInfoEnabled()) {
						logger.info("Slot libero trovato, lo occupo...");
					}

					openOfficeOpenedConnections++;

				} else {
					if (logger.isInfoEnabled()) {
						logger.info("Nessuno slot libero mi metto in coda per l'apertura di una nuova istanza");
					}
					throw new OfficeException("No slots available");
				}
			}
			if (logger.isInfoEnabled()) {
				logger.info("chiamo la converter.convert()...");
			}

			try {
				converter.convert(inputFile, pdfFile, pdf);
			} catch (Exception ex) {
				logger.error("errore nella conversione, rilascio lo slot...", ex);
				return null;
			} finally {
				synchronized (mutex) {
					openOfficeOpenedConnections--;
				}
			}
		}

		// se non riesco a convertire o non ci sono più slot disponibili provo di nuovo
		// con una nuova istanza
		catch (Exception ex) {
			// ex.printStackTrace(System.out);

			if (ex.getMessage().contains("No slots available")
					|| ex.getMessage().contains("no office manager available")) {

				// synchronized (mutex) {
				// // aspetto in caso non è ancora finita l'inizializzazione di openoffice
				// }

				synchronized (this) {

					// if (!ex.getMessage().contains("No slots available")) {
					// openOfficeOpenedConnections --;
					// }

					// newInstanceRequired = false;
					if (logger.isInfoEnabled()) {
						logger.info("Avvio una nuova istanza di openoffice...");
					}
					boolean subServiceStarted = false;
					OfficeManager subOfficeManager = null;
					while (!subServiceStarted) {
						if (openOfficeExtraPort <= 0)
							openOfficeExtraPort = new Random().nextInt(65534) + 1;
						// openOfficePort = 8001;
						if (logger.isInfoEnabled()) {
							logger.info("connessione alla nuova instanza sulla porta " + openOfficePort + "...");
						}
						try {

							File homeDirectory = new File(System.getProperty("user.home") + "/ootmp");
							// File homeDirectory = new File (System.getProperty("user.home"));
							if (!homeDirectory.getParentFile().canWrite())
								throw new ConnectException();
							if (!homeDirectory.exists())
								homeDirectory.mkdir();
							String saved_properties = System.getProperty("java.io.tmpdir");
							// System.setProperty("java.io.tmpdir", homeDirectory.getAbsolutePath());

							DefaultOfficeManagerConfiguration defaultOfficeManager = new DefaultOfficeManagerConfiguration();
							defaultOfficeManager.setOfficeHome(openOfficeHome);
							defaultOfficeManager.setMaxTasksPerProcess(1);

							if (openOfficeConnectionProtocol.equalsIgnoreCase("socket")) {
								defaultOfficeManager.setConnectionProtocol(OfficeConnectionProtocol.SOCKET);
								defaultOfficeManager.setPortNumber(openOfficeExtraPort);
							} else {
								defaultOfficeManager.setConnectionProtocol(OfficeConnectionProtocol.PIPE);
								defaultOfficeManager.setPipeName("___openoffice_pipe_extra");
							}
							defaultOfficeManager.setTaskExecutionTimeout(Long.MAX_VALUE);
							subOfficeManager = defaultOfficeManager.buildOfficeManager();

							// System.setProperty("java.io.tmpdir", saved_properties);
						} catch (Exception subEx) {
							logger.error("impossibile connettersi a OpenOffice sulla porta " + openOfficePort + ".",
									subEx);
							throw new ConnectException(
									"impossibile connettersi a OpenOffice sulla porta " + openOfficePort + ".");
						}

						try {
							subOfficeManager.start();
							subServiceStarted = true;
						} catch (Exception subEx) {
							logger.error("Errore nella connessione alla porta: " + openOfficePort
									+ " provo su un'altra porta...", subEx);
						}
					}
					// converto il documento
					if (logger.isInfoEnabled()) {
						logger.info("connessione riuscita.");
						logger.info("conversione del documento...");
					}

					converter = new OfficeDocumentConverter(subOfficeManager, dfr);
					try {
						// converter.setDefaultLoadProperties(loadProperties);
						converter.convert(inputFile, pdfFile, pdf);
					} catch (Exception subEex) {
						logger.error("", subEex);
						return null;
					} finally {
						try {
							subOfficeManager.stop();
						} catch (Exception subEex) {
						}
					}
				}
			} else
				return null;
		}
		if (logger.isInfoEnabled()) {
			logger.info("Openoffice: conversione effettuata. File creato: " + pdfFile.getAbsolutePath());
		}

		// chiudo la connessione con Openoffice
		// officeManager.stop();

		return pdfFile;
	}

	private boolean hasModelPage(File file) {
		PdfReader tempReader = null;
		// se il file e' un pdf

		try {

			tempReader = new PdfReader(file.getPath());

			// e contiene la pagina delle firme
			if (tempReader.getCatalog().get(PdfName.USER) != null
					&& tempReader.getCatalog().get(PdfName.USER).toString().substring(0, 11).equals("PaginaFirme")) {
				return true;
			} else
				return false;
		} catch (IOException ex) {
			return false;
		} catch (StringIndexOutOfBoundsException ex) {
			return false;
		}
	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the
	// + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 * 
	 * @return a String containing servlet description
	 */
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

}
