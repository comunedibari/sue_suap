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

import it.people.dbm.dao.DbmDao;
import it.people.dbm.dao.Immagini;
import it.people.dbm.dao.OneriDocumenti;
import it.people.dbm.dao.Template;
import it.people.dbm.dao.TemplatesVari;
import it.people.dbm.model.ImmagineModel;
import it.people.dbm.model.OneriDocumentiModel;
import it.people.dbm.model.TemplateModel;
import it.people.dbm.model.TemplateVariModel;
import net.sf.json.JSONObject;

/**
 *
 * @author Piergiorgio
 */
public class cancellaMultipart extends HttpServlet {

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
        DbmDao dbmDao = new DbmDao();
        JSONObject rootObj = null;
        try {
            DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
            fileItemFactory.setSizeThreshold(1 * 1024 * 1024);
            ServletFileUpload upload = new ServletFileUpload(fileItemFactory);
            List items = upload.parseRequest(request);
            Iterator iter = items.iterator();
            HashMap<String, FileItem> parameters = new HashMap<String, FileItem>();

            String action = "";
            while (iter.hasNext()) {
                //FileItemStream item = iter.next();
                FileItem item = (FileItem) iter.next();
                String name = item.getFieldName();
                parameters.put(name, item);
                if (name.equals("table_name")) {
                    if (item.getString().equals("oneri_documenti")) {
                        action = "oneri_documenti";
                    }
                    if (item.getString().equals("template")) {
                        action = "template";
                    }
                }
                if (name.equals("window_table_name")) {
                    if (item.getString().equals("immagini")) {
                        action = "immagini";
                    }
                }
                if (name.equals("window_table_name")) {
                    if (item.getString().equals("templates_vari_risorse")) {
                        action = "templates_vari_risorse";
                    }
                }

            }
            if (action.equals("oneri_documenti")) {
                OneriDocumentiModel odm = caricaOneriDocumenti(parameters);
                OneriDocumenti classe = new OneriDocumenti();
                rootObj = classe.cancella(odm);
            }
            if (action.equals("template")) {
                TemplateModel tm = caricaTemplate(parameters);
                Template template = new Template();
                rootObj = template.cancella(tm);
            }
            if (action.equals("immagini")) {
                ImmagineModel tm = caricaImmagine(parameters);
                Immagini template = new Immagini();
                rootObj = template.cancella(tm);
            }
            if (action.equals("templates_vari_risorse")) {
                TemplateVariModel tm = caricaTemplateVariRisorse(parameters);
                TemplatesVari template = new TemplatesVari();
                rootObj = template.cancella(tm);
            }

        } catch (Exception e) {
            rootObj = new JSONObject();
            rootObj.put("failure", e.getMessage());
            e.printStackTrace();
        } finally {
            out.print(rootObj);
        }
    }

    private OneriDocumentiModel caricaOneriDocumenti(HashMap<String, FileItem> parameters) throws Exception {
        OneriDocumentiModel odm = new OneriDocumentiModel();

        FileItem parameter;

        parameter = parameters.get("cod_doc_onere");
        odm.setCodDocOnere(parameter.getString());

        parameter = parameters.get("des_doc_onere");
        odm.setDesDocOnere(parameter.getString());

        odm.setFileUpload(null);
        return odm;
    }

    private TemplateModel caricaTemplate(HashMap<String, FileItem> parameters) throws Exception {
        TemplateModel tm = new TemplateModel();

        FileItem parameter;

        parameter = parameters.get("cod_sport");
        tm.setCodSport(parameter.getString());

        parameter = parameters.get("cod_com");
        tm.setCodCom(parameter.getString());

        parameter = parameters.get("cod_proc");
        tm.setCodProc(parameter.getString());

        parameter = parameters.get("cod_servizio");
        tm.setCodServizio(parameter.getString());

        return tm;
    }

    private ImmagineModel caricaImmagine(HashMap<String, FileItem> parameters) throws Exception {
        ImmagineModel tm = new ImmagineModel();

        FileItem parameter;

        parameter = parameters.get("cod_sport");
        tm.setCodSport(parameter.getString());

        parameter = parameters.get("cod_com");
        tm.setCodCom(parameter.getString());
        
        parameter = parameters.get("cod_lang_hidden");
        tm.setCodLang(parameter.getString());
        
        parameter = parameters.get("window_nome_immagine");
        tm.setNomeImmagine(parameter.getString());

        return tm;
    }
        private TemplateVariModel caricaTemplateVariRisorse(HashMap<String, FileItem> parameters) throws Exception {
        TemplateVariModel tm = new TemplateVariModel();

        FileItem parameter;

        parameter = parameters.get("cod_sport");
        tm.setCodSport(parameter.getString());

        parameter = parameters.get("cod_lang_hidden");
        tm.setCodLang(parameter.getString());

        parameter = parameters.get("window_nome_template");
        tm.setNomeTemplate(parameter.getString());

        return tm;
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
            Logger.getLogger(cancellaMultipart.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(cancellaMultipart.class.getName()).log(Level.SEVERE, null, ex);
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
