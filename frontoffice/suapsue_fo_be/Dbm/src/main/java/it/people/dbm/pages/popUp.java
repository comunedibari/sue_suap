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

import it.people.dbm.popup.PopUpImpl;
import net.sf.json.JSONObject;

/**
 *
 * @author Piergiorgio
 */
public class popUp extends HttpServlet {
   
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
        PopUpImpl popUpImpl = new PopUpImpl();
        try {
            String table_name = request.getParameter("table_name");
            if (table_name.equals("interventi")) {
                rootObj=popUpImpl.actionPopUpInterventi(request);
            }
            if (table_name.equals("interventi_totali")) {
                rootObj=popUpImpl.actionPopUpInterventiTotali(request);
            }            
            if (table_name.equals("href")) {
                rootObj=popUpImpl.actionPopUpHref(request);
            }
            if (table_name.equals("classi_enti")) {
                rootObj=popUpImpl.actionPopUpClassiEnti(request);
            }
            if (table_name.equals("enti_comuni")) {
                rootObj=popUpImpl.actionPopUpEntiComuni(request);
            }
            if (table_name.equals("settori_attivita")) {
                rootObj=popUpImpl.actionPopUpSettoriAttivita(request);
            }
            if (table_name.equals("operazioni")) {
                rootObj=popUpImpl.actionPopUpOperazioni(request);
            }
            if (table_name.equals("normative")) {
                rootObj=popUpImpl.actionPopUpNormative(request);
            }
            if (table_name.equals("aggregazioni")) {
                rootObj=popUpImpl.actionPopUpAggregazioni(request);
            }
            if (table_name.equals("procedimenti")) {
                rootObj=popUpImpl.actionPopUpProcedimenti(request);
            }
            if (table_name.equals("cud")) {
                rootObj=popUpImpl.actionPopUpCud(request);
            }
            if (table_name.equals("oneri_gerarchia")) {
                rootObj=popUpImpl.actionPopUpOneriGerarchia(request);
            }
            if (table_name.equals("oneri_documenti")) {
                rootObj=popUpImpl.actionPopUpOneriDocumenti(request);
            }
            if (table_name.equals("comuni")) {
                rootObj=popUpImpl.actionPopUpComuni(request);
            }
            if (table_name.equals("oneri_formula")) {
                rootObj=popUpImpl.actionPopUpOneriFormula(request);
            }
            if (table_name.equals("oneri_campi")) {
                rootObj=popUpImpl.actionPopUpOneriCampi(request);
            }
            if (table_name.equals("oneri")) {
                rootObj=popUpImpl.actionPopUpOneri(request);
            }
            if (table_name.equals("documenti")) {
                rootObj=popUpImpl.actionPopUpDocumenti(request);
            }
            if (table_name.equals("sportelli")) {
                rootObj=popUpImpl.actionPopUpSportelli(request);
            }
            if (table_name.equals("destinatari")) {
                rootObj=popUpImpl.actionPopUpDestinatari(request);
            }
            if (table_name.equals("servizi")) {
                rootObj=popUpImpl.actionPopUpServizi(request);
            }
            if (table_name.equals("tipi_rif")) {
                rootObj=popUpImpl.actionPopUpTipiRif(request);
            }
            if (table_name.equals("testo_condizioni")) {
                rootObj=popUpImpl.actionPopUpTestoCondizioni(request);
            }
            if (table_name.equals("interventi_collegati")) {
                rootObj=popUpImpl.actionPopUpInterventiCollegati(request);
            }
            if (table_name.equals("formulario_ae")) {
                rootObj=popUpImpl.actionPopUpFormularioAE(request);
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
