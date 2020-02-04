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

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;

import it.people.dbm.dao.ImportExportLocal;
import net.sf.json.JSONObject;
import org.slf4j.LoggerFactory;
/**
 *
 * @author Piergiorgio
 */
public class duplicazioneInterventi extends HttpServlet {

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
    private static Logger log = LoggerFactory.getLogger(duplicazioneInterventi.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
//        PrintWriter out = response.getWriter();
        ServletOutputStream out = response.getOutputStream();
        JSONObject rootObj = null;
        String action = null;
        try {
            String scelta = null;
            String codInt = null;
            String codIntNew = null;
            String codCom = null;
            String xml = null;
            boolean collegati = false;
            if (request.getParameter("rb") != null && request.getParameter("rb").equals("2")) {
                codInt = request.getParameter("cod_int");
                codCom = request.getParameter("cod_com");
                action = "exportFile";
                scelta = request.getParameter("rb");
                if (request.getParameter("int_coll") != null) {
                    collegati = request.getParameter("int_coll").equalsIgnoreCase("collegati");
                }
            } else {
                DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
                fileItemFactory.setSizeThreshold(1 * 1024 * 1024);
                ServletFileUpload upload = new ServletFileUpload(fileItemFactory);
                upload.setSizeMax(1 * 1024 * 1024);
                List items = upload.parseRequest(request);
                Iterator iter = items.iterator();
                while (iter.hasNext()) {
                    FileItem item = (FileItem) iter.next();
                    String name = item.getFieldName();
                    if (item.isFormField()) {
                        // field non file
                        if (name.equals("rb")) {
                            scelta = item.getString();
                            if (item.getString().equals("1")) {
                                action = "copiaLocale";
                            }
                            if (item.getString().equals("3")) {
                                action = "importFile";
                            }
                            if (item.getString().equals("4")) {
                                action = "nessunaAttivita";
                            }
                        }
                        if (name.equals("cod_int")) {
                            codInt = item.getString();
                        }
                        if (name.equals("cod_int_new")) {
                            codIntNew = item.getString();
                        }
                        if (name.equals("cod_com")) {
                            codCom = item.getString();
                        }
                        if (name.equals("int_coll")) {
                            collegati = item.getString().equalsIgnoreCase("collegati");
                        }
                    } else {
                        // field file
                        StringBuilder buffer = new StringBuilder();
                        InputStreamReader isr = new InputStreamReader(item.getInputStream(), "UTF8");
                        Reader in = new BufferedReader(isr);
                        int ch;
                        while ((ch = in.read()) > -1) {
                            buffer.append((char) ch);
                        }
                        in.close();
                        xml = buffer.toString();
                        request.getSession().setAttribute("UPXML", xml);
                    }
                }
            }
            if (action.equals("nessunaAttivita")) {
                rootObj = new JSONObject();
                rootObj.put("failure", "Operazione non attiva");
            }
            if (action.equals("exportFile")) {
                int length = 0;

                ImportExportLocal classe = new ImportExportLocal();
                log.info("Prima della chiamata alla banca dati");
                xml = classe.caricaDocumento(codInt, scelta, codCom, collegati, true);
                log.info(xml);
                response.setHeader("Expires", "0");
                response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
                response.setHeader("Pragma", "public");
                response.setHeader("Content-Disposition", "attachment; filename=export_" + codInt + ".xml");
                response.setContentType("text/xml");
                response.setContentLength((int) xml.getBytes("UTF-8").length);
                byte[] bbuf = new byte[1024];
                InputStream in = new ByteArrayInputStream(xml.getBytes("UTF-8"));

                while ((in != null) && ((length = in.read(bbuf)) != -1)) {
                    out.write(bbuf, 0, length);
                }

                in.close();

            }
            if (action.equals("copiaLocale")) {
                ImportExportLocal classe = new ImportExportLocal();
                classe.caricaDocumento(codInt, scelta, codIntNew, codCom, request, collegati, true);
                rootObj = new JSONObject();
                rootObj.put("success", "Operazione effettuata");
            }
            if (action.equals("importFile")) {
                ImportExportLocal classe = new ImportExportLocal();
                if (request.getParameter("carica") != null && request.getParameter("carica").equals("yes")) {
                    HashMap<String, HashMap<String, String>> importXMLVariables = (HashMap<String, HashMap<String, String>>) request.getSession().getAttribute("newVariables");
                    HashMap<String, ArrayList<String>> importExcludes = (HashMap<String, ArrayList<String>>) request.getSession().getAttribute("excludes");                    
                    if (checkMapIsValid(importXMLVariables)) {
                        classe.scriviDati(xml, scelta, importXMLVariables, request, collegati, importExcludes);
                        rootObj = new JSONObject();
                        rootObj.put("success", "Operazione effettuata");
                        request.getSession().removeAttribute("newVariables");
                        request.getSession().removeAttribute("excludes");
                        request.getSession().removeAttribute("existences");
                        request.getSession().removeAttribute("UPXML");
                    } else {
                        rootObj = new JSONObject();
                        rootObj.put("failure", "Risolvi i problemi di incompatibilità");
                    }
                } else {
                    rootObj = classe.verificaDati(request, xml, collegati);
                }
            }
        } catch (Exception e) {
            rootObj = new JSONObject();
            rootObj.put("failure", e.getMessage());
            log.error("Errore sulla duplicazione interventi", e);
        } finally {
            if (action != null && !action.equals("exportFile")) {
                String res = rootObj.toString();
                response.setContentLength(res.length());
                out.print(res);
            }
            out.close();
           // out.flush();
        }
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
        processRequest(request, response);
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
        processRequest(request, response);
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

    private boolean checkMapIsValid(HashMap<String, HashMap<String, String>> importXMLVariables) {
        boolean valid = true;
        HashMap<String, String> interventiDaModificare = importXMLVariables.get("interventi");
        if (!isValid(interventiDaModificare)) {
            valid = false;
        }
        if (valid) {
            HashMap<String, String> documentiDaModificare = importXMLVariables.get("documenti");
            if (!isValid(documentiDaModificare)) {
                valid = false;
            }
        }
        if (valid) {
            HashMap<String, String> dichiarazioniDaModificare = importXMLVariables.get("dichiarazioni");
            if (!isValid(dichiarazioniDaModificare)) {
                valid = false;
            }
        }
        if (valid) {
            HashMap<String, String> normativeDaModificare = importXMLVariables.get("normative");
            if (!isValid(normativeDaModificare)) {
                valid = false;
            }
        }
        if (valid) {
            HashMap<String, String> cudDaModificare = importXMLVariables.get("cud");
            if (!isValid(cudDaModificare)) {
                valid = false;
            }
        }
        if (valid) {
            HashMap<String, String> procedimentoDaModificare = importXMLVariables.get("procedimenti");
            if (!isValid(procedimentoDaModificare)) {
                valid = false;
            }
        }
        if (valid) {
            HashMap<String, String> condizioniDaModificare = importXMLVariables.get("testo_condizioni");
            if (!isValid(condizioniDaModificare)) {
                valid = false;
            }
        }
        if (valid) {
            HashMap<String, String> tipoNormativaDaModificare = importXMLVariables.get("tipi_rif");
            if (!isValid(tipoNormativaDaModificare)) {
                valid = false;
            }
        }

        return valid;
    }

    private boolean isValid(HashMap<String, String> valori) {
        boolean valid = true;
        Iterator<String> iterator = valori.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = valori.get(key);
            if (isMoreThanOne(value, valori)) {
                //..errore ho più di un valore
                valid = false;
            } else {
                valid = true;
            }
        }
        return valid;
    }

    private boolean isMoreThanOne(String value, HashMap<String, String> valori) {
        Iterator<String> iterator = valori.keySet().iterator();
        int count = 0;
        while (iterator.hasNext()) {
            String key = iterator.next();
            String val = valori.get(key);
            if (val.equals(value)) {
                count++;
            }
        }
        if (count > 1) {
            return true;
        }
        return false;
    }
}
