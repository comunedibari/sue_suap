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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.people.dbm.dao.FormularioAgenziaEntrate;
import it.people.dbm.dao.ImportExportLocal;
import it.people.dbm.dao.Protocollo;
import it.people.dbm.dao.ProtocolloSegnatura;
import it.people.dbm.dao.TipiCampoTitolare;
import it.people.dbm.utility.SortFieldCleanInterceptor;
import net.sf.json.JSONObject;

/**
 * @author Riccardo Foraf� - Engineering Ingegneria Informatica - Genova
 * 20/set/2011 22.59.36
 */
public class actions_CCD extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	// TODO: Interceptor per risolvere il problema del sort e il multi-lingue, da rimuovere una volta risolto
    	request = new SortFieldCleanInterceptor().doSortFieldCleaning(request);

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject rootObj = new JSONObject();
        try {
            String table_name = request.getParameter("table_name");
            String action = request.getParameter("action");
            if (table_name.equals("sportelli_param_prot_pec") && action == null) {
            	ProtocolloSegnatura classe = new ProtocolloSegnatura();
                rootObj = classe.action(request);
            }
            if (table_name.equals("sportelli_param_prot_pec") && action != null && action.equalsIgnoreCase("update")) {
            	ProtocolloSegnatura classe = new ProtocolloSegnatura();
                rootObj = classe.aggiorna(request);
            }
            if (table_name.equals("sportelli_param_prot_ws") && action == null) {
            	Protocollo classe = new Protocollo();
                rootObj = classe.action(request);
            }
            if (table_name.equals("sportelli_param_prot_ws") && action != null && action.equalsIgnoreCase("update")) {
            	Protocollo classe = new Protocollo();
                rootObj = classe.aggiorna(request);
            }
            if (table_name.equals("formulario_ae") && action == null) {
            	FormularioAgenziaEntrate classe = new FormularioAgenziaEntrate();
                rootObj = classe.action(request);
            }
            if (table_name.equals("tipi_campo_titolare") && action == null) {
            	TipiCampoTitolare classe = new TipiCampoTitolare();
                rootObj = classe.action(request);
            }
            if (table_name.equals("import_intervento") && action != null && action.equalsIgnoreCase("exclude")) {
            	ImportExportLocal importExportLocal = new ImportExportLocal();
            	rootObj = importExportLocal.excludeEntity(request);
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
        processRequest(request, response);
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
        processRequest(request, response);
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
