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

import it.people.dbm.dao.Allegati;
import it.people.dbm.dao.Documenti;
import it.people.dbm.dao.Href;
import it.people.dbm.dao.InterventiCollegati;
import it.people.dbm.dao.Normative;
import it.people.dbm.dao.NormeInterventi;
import it.people.dbm.dao.TestoCondizioni;
import net.sf.json.JSONObject;

/**
 *
 * @author Piergiorgio
 */
public class scriviNotifica extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject rootObj = null;
        try {
            String table_name = request.getParameter("table_name");
            if (table_name.equals("testo_condizioni")) {
                TestoCondizioni classe = new TestoCondizioni();
                rootObj = classe.scriviNotifica(request);
            }
            if (table_name.equals("normative")) {
                Normative classe = new Normative();
                rootObj = classe.scriviNotifica(request);
            }
            if (table_name.equals("normative_documenti")) {
                Normative classe = new Normative();
                rootObj = classe.scriviNotifica(request);
            }
            if (table_name.equals("normative_documenti_upload")) {
                Normative classe = new Normative();
                rootObj = classe.scriviNotifica(request);
            }
            if (table_name.equals("documenti")) {
                Documenti classe = new Documenti();
                rootObj = classe.scriviNotifica(request);
            }
            if (table_name.equals("documenti_documenti")) {
                Documenti classe = new Documenti();
                rootObj = classe.scriviNotifica(request);
            }
            if (table_name.equals("documenti_documenti_upload")) {
                Documenti classe = new Documenti();
                rootObj = classe.scriviNotifica(request);
            }
            if (table_name.equals("href")) {
                Href classe = new Href();
                rootObj = classe.scriviNotifica(request);
            }
            if (table_name.equals("allegati")) {
                Allegati classe = new Allegati();
                rootObj = classe.scriviNotifica(request);
            }
            if (table_name.equals("norme_interventi")) {
                NormeInterventi classe = new NormeInterventi();
                rootObj = classe.scriviNotifica(request);
            }
            if (table_name.equals("interventi_collegati")) {
                InterventiCollegati classe = new InterventiCollegati();
                rootObj = classe.scriviNotifica(request);
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
