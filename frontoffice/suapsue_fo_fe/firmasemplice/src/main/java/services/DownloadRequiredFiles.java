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

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

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

import tools.Base64Coder;
//import org.mortbay.servlet.MultiPartResponse;

/**
 * Scarica uno o più file necessari per il funzionamento dell'applet di firma
 *
 * @author Giuseppe
 */
public class DownloadRequiredFiles extends HttpServlet {

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
	 * "FileList", solo il nome della "part" senza specificare il valore: invia al
	 * client la Lista dei file da ricevere;
	 *
	 * "FileToSend": indica il nome del file da inviare al client
	 * 
	 *
	 */

	private static Logger logger = LoggerFactory.getLogger(DownloadRequiredFiles.class);

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
		boolean sendFileList = false;
		try {

			if (ServletFileUpload.isMultipartContent(request)) {
				File fileToSend = null;

				// analizzo la richiesta
				ServletFileUpload sfu = new ServletFileUpload(new DiskFileItemFactory());
				List fileItems = sfu.parseRequest(request);
				FileItem item = (FileItem) fileItems.get(0);
				if (item.isFormField() && item.getFieldName().equals("FileToSend")) { // file da inviare al client

					if (logger.isDebugEnabled()) {
						logger.debug("file richiesto : \"" + item.getString() + "\"");
					}
					fileToSend = new File(
							getServletContext().getRealPath("") + "/requiredAppletFiles" + "/" + item.getString());
					if (!fileToSend.exists()) {
						response.setStatus(HttpServletResponse.SC_NO_CONTENT);
						if (logger.isInfoEnabled()) {
							logger.info("Il file richiesto non esiste sul server.");
						}
						return;
					}
				} else if (item.isFormField() && item.getFieldName().equals("FileList")) { // richiesta di invio della
																							// lista dei file

					if (logger.isInfoEnabled()) {
						logger.info("file richiesto : \"lista dei file\"");
					}
					sendFileList = true;
				}

				if (sendFileList) {

					// invio la lista dei file
					response.setContentType("text/html;charset=UTF-8");
					File requiredFileDir = new File(getServletContext().getRealPath("") + "/requiredAppletFiles/");
					File[] requiredFileList = requiredFileDir.listFiles();

					if (logger.isDebugEnabled()) {
						logger.debug("Directory dei file necessari per l'applet: " + requiredFileDir.getAbsolutePath());
						logger.debug("File trovati: " + requiredFileList.length);
					}

					PrintWriter outresp = response.getWriter();
					for (int i = 0; i < requiredFileList.length; i++) {
						DataInputStream datais = new DataInputStream(new FileInputStream(requiredFileList[i]));
						byte[] content = new byte[(int) requiredFileList[i].length()];
						datais.readFully(content);
						byte[] hash = null;

						try {
							hash = MessageDigest.getInstance("SHA").digest(content);
						} catch (NoSuchAlgorithmException ex) {
							logger.error("", ex);
							response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
							return;
						}
						char[] base64hash = Base64Coder.encode(hash);

						// nella lista inserisco nome del file e l'hash codificato in base64
						outresp.println(requiredFileList[i].getName() + ";" + new String(base64hash));
						if (logger.isDebugEnabled()) {
							logger.debug("File trovato: " + requiredFileList[i].getAbsolutePath());
						}
					}
					outresp.close();
				} else {

					// invio il file richiesto
					OutputStream outresp = response.getOutputStream();

					FileInputStream fis = new FileInputStream(fileToSend);
					byte[] readData = new byte[1024];
					int i = fis.read(readData);
					while (i != -1) {
						outresp.write(readData, 0, i);
						i = fis.read(readData);
					}
					fis.close();
					outresp.close();
				}
			} else {
				response.getWriter().print("Il servizio supporta solo richieste multipart");
				response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
				return;
			}
		} catch (FileUploadException ex) {
			logger.error("", ex);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		} catch (IOException ex) {
			logger.error("", ex);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}

		// BufferedReader fileToSend = new BufferedReader(new
		// InputStreamReader(request.getInputStream()));
		//
		// String line = fileToSend.readLine();
		//
		// while (line != null) {
		// System.out.println("letto: " + line);
		// line = fileToSend.readLine();
		// }

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
