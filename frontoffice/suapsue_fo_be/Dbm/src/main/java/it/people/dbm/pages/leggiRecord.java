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

import it.people.dbm.dao.LeggiRecords;
import net.sf.json.JSONObject;

/**
 *
 * @author Piergiorgio
 */
public class leggiRecord extends HttpServlet {

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
        LeggiRecords lr = new LeggiRecords();
        try {
            String table_name = request.getParameter("table_name");
            if (table_name.equals("classi_enti")) {
                rootObj = lr.leggiClassiEnti(request.getParameter("cod_classe_ente"));
            }
            if (table_name.equals("cud")) {
                rootObj = lr.leggiCud(request.getParameter("cod_cud"));
            }
            if (table_name.equals("href")) {
                rootObj = lr.leggiHref(request.getParameter("href"));
            }
            if (table_name.equals("destinatari")) {
                rootObj = lr.leggiDestinatari(request.getParameter("cod_dest"));
            }
            if (table_name.equals("enti_comuni")) {
                rootObj = lr.leggiEntiComuni(request.getParameter("cod_ente"));
            }
            if (table_name.equals("interventi")) {
                rootObj = lr.leggiInterventi(request.getParameter("cod_int"));
            }
            if (table_name.equals("documenti")) {
                rootObj = lr.leggiDocumenti(request.getParameter("cod_doc"));
            }
            if (table_name.equals("normative")) {
                rootObj = lr.leggiNormative(request.getParameter("cod_rif"));
            }
            if (table_name.equals("oneri")) {
                rootObj = lr.leggiOneri(request.getParameter("cod_oneri"));
            }
            if (table_name.equals("oneri_campi")) {
                rootObj = lr.leggiOneriCampi(request.getParameter("cod_onere_campo"));
            }
            if (table_name.equals("oneri_documenti")) {
                rootObj = lr.leggiOneriDocumenti(request.getParameter("cod_doc_onere"));
            }
            if (table_name.equals("oneri_formula")) {
                rootObj = lr.leggiOneriFormula(request.getParameter("cod_onere_formula"));
            }
            if (table_name.equals("operazioni")) {
                rootObj = lr.leggiOperazioni(request.getParameter("cod_ope"));
            }
            if (table_name.equals("procedimenti")) {
                rootObj = lr.leggiProcedimenti(request.getParameter("cod_proc"));
            }
            if (table_name.equals("settori_attivita")) {
                rootObj = lr.leggiSettoriAttivita(request.getParameter("cod_sett"));
            }
            if (table_name.equals("sportelli")) {
                rootObj = lr.leggiSportelli(request.getParameter("cod_sport"));
            }
            if (table_name.equals("testo_condizioni")) {
                rootObj = lr.leggiTestoCondizioni(request.getParameter("cod_cond"));
            }
            if (table_name.equals("tipi_aggregazione")) {
                rootObj = lr.leggiTipiAggregazione(request.getParameter("tip_aggregazione"));
            }
            if (table_name.equals("tipi_rif")) {
                rootObj = lr.leggiTipiRif(request.getParameter("cod_tipo_rif"));
            }
            if (table_name.equals("utenti")) {
                rootObj = lr.leggiUtenti(request.getParameter("cod_utente"));
            }
            if (table_name.equals("allegati")) {
                rootObj = lr.leggiAllegati(request.getParameter("cod_int"),request.getParameter("cod_doc"));
            }
            if (table_name.equals("oneri_interventi")) {
                rootObj = lr.leggiOneriIntervento(request.getParameter("cod_int"),request.getParameter("cod_oneri"));
            }
            if (table_name.equals("norme_interventi")) {
                rootObj = lr.leggiNormeInterventi(request.getParameter("cod_int"),request.getParameter("cod_rif"));
            }
            if (table_name.equals("interventi_collegati")) {
                rootObj = lr.leggiInterventiCollegati(request.getParameter("cod_int"),request.getParameter("cod_int_padre"));
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
