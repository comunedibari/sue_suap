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
 * http://www.osor.eu/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
**/
package it.people.dbm.pages;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
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

import it.people.dbm.utility.RevenueAgencyFormularyImporter;
import it.people.dbm.utility.Utility;
import net.sf.json.JSONObject;
 
public class uploadDocument_CCD extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3824831742483088524L;
	private static Logger logger = LoggerFactory.getLogger(uploadDocument_CCD.class);
	
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, FileUploadException {

        Connection dbConnection = null;
    	
    	if (logger.isDebugEnabled()) {
    		logger.debug("uploadDocument_CCD: processing request...");
    	}
    	
        response.setContentType("text/html;charset=UTF-8");
        
//        ServletOutputStream out = response.getOutputStream();
        PrintWriter out = response.getWriter();
        JSONObject rootObj = null;
        
        try {

        	dbConnection = Utility.getConnection();
        	
            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
            diskFileItemFactory.setSizeThreshold(1 * 1024 * 1024);
            
            ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
            
            List items = servletFileUpload.parseRequest(request);
            HashMap<String, FileItem> parameters = new HashMap<String, FileItem>();
            
            Iterator iter = items.iterator();

            while(iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                String name = item.getFieldName();
            	if (logger.isDebugEnabled()) {
            		logger.debug("uploadDocument_CCD: uploaded item name = '" + name + "'.");
            	}
            	parameters.put(name, item);
            }
            
            FileItem uploadedFile = parameters.get("document_path");

            boolean formularyImported = RevenueAgencyFormularyImporter.importFormulary(uploadedFile.getInputStream(), dbConnection);
            
            rootObj = new JSONObject();
            if (formularyImported) {
            	rootObj.put("success", "Operazione effettuata");
            } else {
            	rootObj.put("failure", "Importazione del formulario non effettuata.");
            }
            
        } catch (Exception e) {
            rootObj = new JSONObject();
            rootObj.put("failure", "Assicurarsi aver selezionato un file Excel.");
            e.printStackTrace();
        } finally {
            out.print(rootObj);
            out.close();
        }
        
    }

    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (FileUploadException ex) {
            logger.warn(null, ex);
        }
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (FileUploadException ex) {
            logger.warn(null, ex);
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
    
}
