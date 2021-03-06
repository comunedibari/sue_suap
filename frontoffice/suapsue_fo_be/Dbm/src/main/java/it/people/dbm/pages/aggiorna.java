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
import it.people.dbm.dao.AllegatiComuni;
import it.people.dbm.dao.Anagrafica;
import it.people.dbm.dao.ClassiEnti;
import it.people.dbm.dao.CondizioniDiAttivazione;
import it.people.dbm.dao.Cud;
import it.people.dbm.dao.DatiIntegrati;
import it.people.dbm.dao.Destinatari;
import it.people.dbm.dao.Documenti;
import it.people.dbm.dao.EntiComuni;
import it.people.dbm.dao.GerarchiaOperazioni;
import it.people.dbm.dao.GerarchiaSettori;
import it.people.dbm.dao.Help;
import it.people.dbm.dao.Href;
import it.people.dbm.dao.ImmaginiTemplate;
import it.people.dbm.dao.Interventi;
import it.people.dbm.dao.InterventiCollegati;
import it.people.dbm.dao.InterventiComuni;
import it.people.dbm.dao.Normative;
import it.people.dbm.dao.NormeComuni;
import it.people.dbm.dao.NormeInterventi;
import it.people.dbm.dao.Notifiche;
import it.people.dbm.dao.Oneri;
import it.people.dbm.dao.OneriCampi;
import it.people.dbm.dao.OneriComuni;
import it.people.dbm.dao.OneriFormula;
import it.people.dbm.dao.OneriGerarchia;
import it.people.dbm.dao.Operazioni;
import it.people.dbm.dao.Parametri;
import it.people.dbm.dao.Procedimenti;
import it.people.dbm.dao.Pubblicazione;
import it.people.dbm.dao.RelazioniEnti;
import it.people.dbm.dao.SettoriAttivita;
import it.people.dbm.dao.SettoriAttivitaComuni;
import it.people.dbm.dao.Sportelli;
import it.people.dbm.dao.TemplatesVari;
import it.people.dbm.dao.TestiPortale;
import it.people.dbm.dao.TestoCondizioni;
import it.people.dbm.dao.TipiAggregazione;
import it.people.dbm.dao.TipiRif;
import it.people.dbm.dao.Utenti;
import net.sf.json.JSONObject;

/**
 *
 * @author Piergiorgio
 */
public class aggiorna extends HttpServlet {

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
            if (table_name.equals("utenti")) {
                Utenti classe = new Utenti();
                rootObj = classe.aggiorna(request);
            }
            if (table_name.equals("html_testi")) {
                TestiPortale classe = new TestiPortale();
                rootObj = classe.aggiorna(request);
            }
            if (table_name.equals("help")) {
                Help classe = new Help();
                rootObj = classe.aggiorna(request);
            }
            if (table_name.equals("dati_integrati")) {
                DatiIntegrati classe = new DatiIntegrati();
                rootObj = classe.aggiorna(request);
            }
            if (table_name.equals("anagrafica")) {
                Anagrafica classe = new Anagrafica();
                rootObj = classe.aggiorna(request);
            }
            if (table_name.equals("configuration")) {
                Parametri classe = new Parametri();
                rootObj = classe.aggiorna(request);
            }
            if (table_name.equals("tipi_aggregazione")) {
                TipiAggregazione classe = new TipiAggregazione();
                rootObj = classe.aggiorna(request);
            }
            if (table_name.equals("cud")) {
                Cud classe = new Cud();
                rootObj = classe.aggiorna(request);
            }
            if (table_name.equals("classi_enti")) {
                ClassiEnti classe = new ClassiEnti();
                rootObj = classe.aggiorna(request);
            }
            if (table_name.equals("enti_comuni")) {
                EntiComuni classe = new EntiComuni();
                rootObj = classe.aggiorna(request);
            }
            if (table_name.equals("destinatari")) {
                Destinatari classe = new Destinatari();
                rootObj = classe.aggiorna(request);
            }
            if (table_name.equals("sportelli")) {
                Sportelli classe = new Sportelli();
                rootObj = classe.aggiorna(request);
            }
            if (table_name.equals("settori_attivita")) {
                SettoriAttivita classe = new SettoriAttivita();
                rootObj = classe.aggiorna(request);
            }
            if (table_name.equals("operazioni")) {
                Operazioni classe = new Operazioni();
                rootObj = classe.aggiorna(request);
            }
            if (table_name.equals("gerarchia_settori")) {
                GerarchiaSettori classe = new GerarchiaSettori();
                rootObj = classe.aggiorna(request);
            }
            if (table_name.equals("gerarchia_operazioni")) {
                GerarchiaOperazioni classe = new GerarchiaOperazioni();
                rootObj = classe.aggiorna(request);
            }
            if (table_name.equals("interventi")) {
                Interventi classe = new Interventi();
                rootObj = classe.aggiorna(request);
            }
            if (table_name.equals("procedimenti")) {
                Procedimenti classe = new Procedimenti();
                rootObj = classe.aggiorna(request);
            }
            if (table_name.equals("tipi_rif")) {
                TipiRif classe = new TipiRif();
                rootObj = classe.aggiorna(request);
            }
            if (table_name.equals("oneri")) {
                Oneri classe = new Oneri();
                rootObj = classe.aggiorna(request);
            }
            if (table_name.equals("oneri_gerarchia")) {
                OneriGerarchia classe = new OneriGerarchia();
                rootObj = classe.aggiorna(request);
            }
            if (table_name.equals("oneri_formula")) {
                OneriFormula classe = new OneriFormula();
                rootObj = classe.aggiorna(request);
            }
            if (table_name.equals("oneri_campi")) {
                OneriCampi classe = new OneriCampi();
                rootObj = classe.aggiorna(request);
            }
            if (table_name.equals("allegati_comuni")) {
                AllegatiComuni classe = new AllegatiComuni();
                rootObj = classe.aggiorna(request);
            }
            if (table_name.equals("interventi_comuni")) {
                InterventiComuni classe = new InterventiComuni();
                rootObj = classe.aggiorna(request);
            }
            if (table_name.equals("oneri_comuni")) {
                OneriComuni classe = new OneriComuni();
                rootObj = classe.aggiorna(request);
            }
            if (table_name.equals("settori_attivita_comuni")) {
                SettoriAttivitaComuni classe = new SettoriAttivitaComuni();
                rootObj = classe.aggiorna(request);
            }
            if (table_name.equals("norme_comuni")) {
                NormeComuni classe = new NormeComuni();
                rootObj = classe.aggiorna(request);
            }
            if (table_name.equals("immagini_template")) {
                ImmaginiTemplate classe = new ImmaginiTemplate();
                rootObj = classe.aggiorna(request);
            }
            if (table_name.equals("href")) {
                Href classe = new Href();
                rootObj = classe.aggiorna(request);
            }
            if (table_name.equals("normative")) {
                Normative classe = new Normative();
                rootObj = classe.aggiorna(request);
            }
            if (table_name.equals("documenti")) {
                Documenti classe = new Documenti();
                rootObj = classe.aggiorna(request);
            }
            if (table_name.equals("norme_interventi")) {
                NormeInterventi classe = new NormeInterventi();
                rootObj = classe.aggiorna(request);
            }
            if (table_name.equals("allegati")) {
                Allegati classe = new Allegati();
                rootObj = classe.aggiorna(request);
            }
            if (table_name.equals("interventi_collegati")) {
                InterventiCollegati classe = new InterventiCollegati();
                rootObj = classe.aggiorna(request);
            }
            if (table_name.equals("notifiche")) {
                Notifiche classe = new Notifiche();
                rootObj = classe.aggiorna(request);
            }
            if (table_name.equals("testo_condizioni")) {
                TestoCondizioni classe = new TestoCondizioni();
                rootObj = classe.aggiorna(request);
            }
            if (table_name.equals("templates_vari")) {
                TemplatesVari classe = new TemplatesVari();
                rootObj = classe.aggiorna(request);
            }
            if (table_name.equals("relazioni_enti")) {
                RelazioniEnti classe = new RelazioniEnti();
                rootObj = classe.aggiorna(request);
            }
            if (table_name.equals("condizioni_di_attivazione")) {
                CondizioniDiAttivazione classe = new CondizioniDiAttivazione();
                rootObj = classe.aggiorna(request);
            }
            if (table_name.equals("pubblicazione")) {
                Pubblicazione classe = new Pubblicazione();
                rootObj = classe.aggiorna(request);
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
