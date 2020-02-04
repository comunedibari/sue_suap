/**
 * Copyright (c) 2011, Regione Emilia-Romagna, Italy
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by
 * the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 *
 * For convenience a plain text copy of the English version of the Licence can
 * be found in the file LICENCE.txt in the top-level directory of this software
 * distribution.
 *
 * You may obtain a copy of the Licence in any of 22 European Languages at:
 *
 * http://www.osor.eu/eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * Licence for the specific language governing permissions and limitations under
 * the Licence.
*
 */
package it.people.dbm.pages;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import it.people.dbm.dao.DbmDao;
import it.people.dbm.dao.OneriDocumenti;
import it.people.dbm.dao.Template;
import it.people.dbm.model.FileUploadModel;
import it.people.dbm.model.OneriDocumentiModel;
import it.people.dbm.model.TemplateModel;
import it.people.dbm.model.TemplateModelFile;
import it.people.dbm.utility.UploadFile;
import it.people.dbm.utility.Utility;
import net.sf.json.JSONObject;

/**
 *
 * @author Piergiorgio
 */
public class inserisciMultipart extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
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
//            FileItemIterator iter = upload.getItemIterator(request);
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
            }
            if (action.equals("oneri_documenti")) {
                OneriDocumentiModel odm = caricaOneriDocumenti(parameters);
                OneriDocumenti classe = new OneriDocumenti();
                rootObj = classe.inserisci(odm, request);
            }
            if (action.equals("template")) {
                TemplateModel tm = caricaTemplate(parameters);
                Template template = new Template();
                rootObj = template.inserisci(tm);
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

        parameter = parameters.get("document_path");
        odm.setFileUpload(UploadFile.upload(parameter));
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


        Set<String> parmKey = parameters.keySet();

        List<TemplateModelFile> lista = new ArrayList<TemplateModelFile>();
        for (String key : parmKey) {
            String[] parSplit = key.split("document_path_");
            if (parSplit.length > 1 && key.contains("document_path_")) {
                String cod_lang = parSplit[1];
                if (cod_lang != null) {
                    TemplateModelFile tmf = new TemplateModelFile();
                    tmf.setLingua(cod_lang);
                    FileUploadModel uf = UploadFile.upload(parameters.get(key));
                    tmf.setNomeFile(uf.getNomeFile());
                    tmf.setTemplate(Base64.encodeBase64(Utility.byteArrayInputStreamToByteArray(uf.getDocBlob())));
                    lista.add(tmf);

                }
            }
        }
        tm.setListaFile(lista);
        return tm;
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
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
            Logger.getLogger(inserisciMultipart.class.getName()).log(Level.SEVERE, null, ex);
        }




    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
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
            Logger.getLogger(inserisciMultipart.class.getName()).log(Level.SEVERE, null, ex);
        }




    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";


    }// </editor-fold>
}
