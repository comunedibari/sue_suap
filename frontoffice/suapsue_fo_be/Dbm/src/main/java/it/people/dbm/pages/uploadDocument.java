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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import it.people.dbm.dao.Documenti;
import it.people.dbm.dao.Normative;
import it.people.dbm.model.DocumentoModel;
import it.people.dbm.model.NormativaModel;
import it.people.dbm.utility.UploadFile;
import net.sf.json.JSONObject;

/**
 *
 * @author Piergiorgio
 */
public class uploadDocument extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, FileUploadException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject rootObj = null;
        try {
            DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
            fileItemFactory.setSizeThreshold(1 * 1024 * 1024);
            ServletFileUpload upload = new ServletFileUpload(fileItemFactory);
            List items = upload.parseRequest(request);
            Iterator iter = items.iterator();
            HashMap<String, FileItem> parameters = new HashMap<String, FileItem>();

            String action = "";
            action = request.getParameter("table_name");
            while (iter.hasNext()) {
                //FileItemStream item = iter.next();
                FileItem item = (FileItem) iter.next();
                String name = item.getFieldName();
                parameters.put(name, item);
            }
            if (action.equals("normative")) {
                NormativaModel nm = caricaNormativa(parameters);
                Normative normative = new Normative();
                rootObj = normative.inserisciDocumento(nm,request);
            }
            if (action.equals("documenti")) {
                DocumentoModel nm = caricaDocumento(parameters);
                Documenti documenti = new Documenti();
                rootObj = documenti.inserisciDocumento(nm,request);
            }

        } catch (Exception e) {
            rootObj = new JSONObject();
            rootObj.put("failure", e.getMessage());
            e.printStackTrace();
        } finally {
            out.print(rootObj);
            out.close();
        }
    }

    private NormativaModel caricaNormativa(HashMap<String, FileItem> parameters) throws Exception {
        NormativaModel nm = new NormativaModel();

        FileItem parameter;
        parameter = parameters.get("window_cod_rif");
        nm.setCodRif(parameter.getString());

        parameter = parameters.get("document_path");
        nm.setFileUplad(UploadFile.upload(parameter));

        return nm;
    }
    private DocumentoModel caricaDocumento(HashMap<String, FileItem> parameters) throws Exception {
        DocumentoModel nm = new DocumentoModel();

        FileItem parameter;
        parameter = parameters.get("window_cod_doc");
        nm.setCodDoc(parameter.getString());

        parameter = parameters.get("document_path");
        nm.setFileUpload(UploadFile.upload(parameter));

        return nm;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
            Logger.getLogger(uploadDocument.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(uploadDocument.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
