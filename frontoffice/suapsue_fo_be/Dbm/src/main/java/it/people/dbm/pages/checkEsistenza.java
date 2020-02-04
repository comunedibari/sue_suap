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

import it.people.dbm.dao.LeggiEsistenza;
import net.sf.json.JSONObject;

/**
 *
 * @author giuseppe
 */
public class checkEsistenza extends HttpServlet {

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
        LeggiEsistenza leggiEsistenza = new LeggiEsistenza();
        try {
            String table_name = request.getParameter("table_name");
            if (table_name.equals("utenti")) {
                rootObj = leggiEsistenza.leggiUtenti(request);
            }
            if (table_name.equals("html_testi")) {
                rootObj = leggiEsistenza.leggiTestiPortale(request);
            }
            if (table_name.equals("help")) {
                rootObj = leggiEsistenza.leggiHelp(request);
            }
            if (table_name.equals("anagrafica")) {
                rootObj = leggiEsistenza.leggiAnagrafica(request);
            }
            if (table_name.equals("tipi_aggregazione")) {
                rootObj = leggiEsistenza.leggiTipiAggregazione(request);
            }
            if (table_name.equals("cud")) {
                rootObj = leggiEsistenza.leggiCud(request);
            }
            if (table_name.equals("classi_enti")) {
                rootObj = leggiEsistenza.leggiClassiEnti(request);
            }
            if (table_name.equals("enti_comuni")) {
                rootObj = leggiEsistenza.leggiEntiComuni(request);
            }
            if (table_name.equals("destinatari")) {
                rootObj = leggiEsistenza.leggiDestinatari(request);
            }
            if (table_name.equals("sportelli")) {
                rootObj = leggiEsistenza.leggiSportelli(request);
            }
            if (table_name.equals("settori_attivita")) {
                rootObj = leggiEsistenza.leggiSettoriAttivita(request);
            }
            if (table_name.equals("operazioni")) {
                rootObj = leggiEsistenza.leggiOperazioni(request);
            }
            if (table_name.equals("interventi")) {
                rootObj = leggiEsistenza.leggiInterventi(request);
            }
            if (table_name.equals("procedimenti")) {
                rootObj = leggiEsistenza.leggiProcedimenti(request);
            }
            if (table_name.equals("tipi_rif")) {
                rootObj = leggiEsistenza.leggiTipiRif(request);
            }
            if (table_name.equals("oneri")) {
                rootObj = leggiEsistenza.leggiOneri(request);
            }
            if (table_name.equals("oneri_gerarchia")) {
                rootObj = leggiEsistenza.leggiOneriGerarchia(request);
            }
            if (table_name.equals("gerarchia_settori")) {
                rootObj = leggiEsistenza.leggiGerarchiaSettori(request);
            }
            if (table_name.equals("gerarchia_operazioni")) {
                rootObj = leggiEsistenza.leggiGerarchiaOperazioni(request);
            }
            if (table_name.equals("oneri_formula")) {
                rootObj = leggiEsistenza.leggiOneriFormula(request);
            }
            if (table_name.equals("oneri_campi")) {
                rootObj = leggiEsistenza.leggiOneriCampi(request);
            }
            if (table_name.equals("oneri_documenti")) {
                rootObj = leggiEsistenza.leggiOneriDocumenti(request);
            }
            if (table_name.equals("immagini_template")) {
                rootObj = leggiEsistenza.leggiImmaginiTemplate(request);
            }
            if (table_name.equals("testo_condizioni")) {
                rootObj = leggiEsistenza.leggiTestoCondizioni(request);
            }
            if (table_name.equals("href")) {
                rootObj = leggiEsistenza.leggiHref(request);
            }
            if (table_name.equals("new_href")) {
                rootObj = leggiEsistenza.leggiHref(request);
            }
            if (table_name.equals("normative")) {
                rootObj = leggiEsistenza.leggiNormative(request);
            }
            if (table_name.equals("documenti")) {
                rootObj = leggiEsistenza.leggiDocumento(request);
            }
            if (table_name.equals("templates_vari")) {
                rootObj = leggiEsistenza.leggiTemplatesVari(request);
            }
        } catch (Exception e) {
            rootObj = new JSONObject();
            rootObj.put("failure", e.getMessage());
        } finally {
            out.print(rootObj);
            out.close();
        }
    }

    private void storeInSession() {
        
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
