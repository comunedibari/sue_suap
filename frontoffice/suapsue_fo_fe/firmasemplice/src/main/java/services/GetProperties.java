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
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
//import org.mortbay.servlet.MultiPartRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Restituice al client uno stream di bytes rappresentante il file di properties
 * rappresentante il modello richiesto tramite richiesta http multipart
 *
 * @author Giuseppe
 */
public class GetProperties extends HttpServlet {

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

	private static Logger logger = LoggerFactory.getLogger(GetProperties.class);

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
		String serverConfFiles = getServletContext().getRealPath("") + "/ConfFiles";
		File modelsDir = new File(serverConfFiles + "/modelli/");
		String selectedModel = null;

		try {

			if (ServletFileUpload.isMultipartContent(request)) {
				File fileToSend = null;

				// leggo il nome del file da inviare
				ServletFileUpload sfu = new ServletFileUpload(new DiskFileItemFactory());
				List fileItems = sfu.parseRequest(request);
				FileItem item = (FileItem) fileItems.get(0);
				if (item.isFormField() && item.getFieldName().equals("modelName")) { // file da inviare al client
					selectedModel = item.getString();
					if (logger.isDebugEnabled()) {
						logger.debug("file richiesto : \"" + item.getString() + "\"");
					}
				}
			}
		} catch (FileUploadException ex) {
			logger.error("", ex);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}

		try {
			// MultiPartResponse mresp = new MultiPartResponse(response);
			// mresp.startPart("binary/octet-stream");
			OutputStream outresp = response.getOutputStream();
			FileInputStream fis = new FileInputStream(modelsDir.getAbsolutePath() + "/" + selectedModel);
			byte[] readData = new byte[1024];
			int i = fis.read(readData);
			while (i != -1) {
				outresp.write(readData, 0, i);
				i = fis.read(readData);
			}
			fis.close();
			outresp.close();
		} catch (IOException ex) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
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
