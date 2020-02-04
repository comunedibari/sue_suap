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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.people.dbm.dao.LeggiRecords;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author Piergiorgio
 */
public class leggiSessione extends HttpServlet {

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
        HashMap<String, Object> param = null;
        JSONObject retLr = null;
        try {
            rootObj = new JSONObject();
            HashMap<String, Object> funzRequest = null;
            HttpSession session = request.getSession();
            if (session.getAttribute("sessioneTabelle") != null) {
                funzRequest = (HashMap<String, Object>) session.getAttribute("sessioneTabelle");
            } else {
                funzRequest = new HashMap<String, Object>();
            }
            if (funzRequest.containsKey(request.getParameter("table_name"))) {
                param = (HashMap<String, Object>) funzRequest.get(request.getParameter("table_name"));
            } else {
                param = new HashMap<String, Object>();
            }

            if (request.getParameter("table_name").equals("allegati")) {
                if (param.containsKey("cod_int") && !param.get("cod_int").equals("")) {
                    LeggiRecords lr = new LeggiRecords();
                    retLr = lr.leggiInterventi((String) param.get("cod_int"));
                    param = caricaDatoLingue(retLr, param, "interventi", "tit_int","tit_int_it");
                }
                if (param.containsKey("cod_doc") && !param.get("cod_doc").equals("")) {
                    LeggiRecords lr = new LeggiRecords();
                    retLr = lr.leggiDocumenti((String) param.get("cod_doc"));
                    param = caricaDatoLingue(retLr, param, "documenti", "tit_doc","tit_doc_it");
                }
                if (param.containsKey("cod_cond") && !param.get("cod_cond").equals("")) {
                    LeggiRecords lr = new LeggiRecords();
                    retLr = lr.leggiTestoCondizioni((String) param.get("cod_cond"));
                    param = caricaDatoLingue(retLr, param, "testo_condizioni", "testo_cond","testo_cond_it");
                }
            }
            if (request.getParameter("table_name").equals("allegati_comuni")) {
                if (param.containsKey("cod_doc") && !param.get("cod_doc").equals("")) {
                    LeggiRecords lr = new LeggiRecords();
                    retLr = lr.leggiDocumenti((String) param.get("cod_doc"));
                    param = caricaDatoLingue(retLr, param, "documenti", "tit_doc","tit_doc_it");
                }
                if (param.containsKey("cod_com") && !param.get("cod_com").equals("")) {
                    LeggiRecords lr = new LeggiRecords();
                    retLr = lr.leggiEntiComuni((String) param.get("cod_com"));
                    param = caricaDatoLingue(retLr, param, "enti_comuni", "des_ente","des_ente_it");
                }
            }
            if (request.getParameter("table_name").equals("documenti")) {
                if (param.containsKey("href") && !param.get("href").equals("")) {
                    LeggiRecords lr = new LeggiRecords();
                    retLr = lr.leggiHref((String) param.get("href"));
                    param = caricaDatoLingue(retLr, param, "href", "tit_href","tit_href_it");
                }
            }
            if (request.getParameter("table_name").equals("tipi_aggregazione")) {
                if (param.containsKey("href") && !param.get("href").equals("")) {
                    LeggiRecords lr = new LeggiRecords();
                    retLr = lr.leggiHref((String) param.get("href"));
                    param = caricaDatoLingue(retLr, param, "href", "tit_href","tit_href_it");
                }
            }
            if (request.getParameter("table_name").equals("interventi")) {
                if (param.containsKey("cod_proc") && !param.get("cod_proc").equals("")) {
                    LeggiRecords lr = new LeggiRecords();
                    retLr = lr.leggiProcedimenti((String) param.get("cod_proc"));
                    param = caricaDatoLingue(retLr, param, "procedimenti", "tit_proc","tit_proc_it");
                }
            }
            if (request.getParameter("table_name").equals("interventi_comuni")) {
                if (param.containsKey("cod_int") && !param.get("cod_int").equals("")) {
                    LeggiRecords lr = new LeggiRecords();
                    retLr = lr.leggiInterventi((String) param.get("cod_int"));
                    param = caricaDatoLingue(retLr, param, "interventi", "tit_int","tit_int_it");
                }
                if (param.containsKey("cod_com") && !param.get("cod_com").equals("")) {
                    LeggiRecords lr = new LeggiRecords();
                    retLr = lr.leggiEntiComuni((String) param.get("cod_com"));
                    param = caricaDatoLingue(retLr, param, "enti_comuni", "des_ente","des_ente_it");
                }
            }
            if (request.getParameter("table_name").equals("procedimenti")) {
                if (param.containsKey("cod_cud") && !param.get("cod_cud").equals("")) {
                    LeggiRecords lr = new LeggiRecords();
                    retLr = lr.leggiCud((String) param.get("cod_cud"));
                    param = caricaDatoLingue(retLr, param, "cud", "des_cud","des_cud_it");
                }
            }
            if (request.getParameter("table_name").equals("oneri")) {
                if (param.containsKey("cod_cud") && !param.get("cod_cud").equals("")) {
                    LeggiRecords lr = new LeggiRecords();
                    retLr = lr.leggiCud((String) param.get("cod_cud"));
                    param = caricaDatoLingue(retLr, param, "cud", "des_cud","des_cud_it");
                }
                if (param.containsKey("cod_doc_onere") && !param.get("cod_doc_onere").equals("")) {
                    LeggiRecords lr = new LeggiRecords();
                    retLr = lr.leggiOneriDocumenti((String) param.get("cod_doc_onere"));
                    param = caricaDatoLingue(retLr, param, "oneri_documenti", "des_doc_onere","des_doc_onere_it");
                }
            }
            if (request.getParameter("table_name").equals("oneri_comuni")) {
                if (param.containsKey("cod_oneri") && !param.get("cod_oneri").equals("")) {
                    LeggiRecords lr = new LeggiRecords();
                    retLr = lr.leggiOneri((String) param.get("cod_oneri"));
                    param = caricaDatoLingue(retLr, param, "oneri", "des_oneri","des_oneri_it");
                }
                if (param.containsKey("cod_com") && !param.get("cod_com").equals("")) {
                    LeggiRecords lr = new LeggiRecords();
                    retLr = lr.leggiEntiComuni((String) param.get("cod_com"));
                    param = caricaDatoLingue(retLr, param, "enti_comuni", "des_ente","des_ente_it");
                }
            }

            if (request.getParameter("table_name").equals("enti_comuni")) {
                if (param.containsKey("cod_classe_ente") && !param.get("cod_classe_ente").equals("")) {
                    LeggiRecords lr = new LeggiRecords();
                    retLr = lr.leggiClassiEnti((String) param.get("cod_classe_ente"));
                    param = caricaDatoLingue(retLr, param, "classi_enti", "des_classe_ente", "des_classe_ente_it");
                }
            }
            if (request.getParameter("table_name").equals("relazioni_enti")) {
                if (param.containsKey("cod_com") && !param.get("cod_com").equals("")) {
                    LeggiRecords lr = new LeggiRecords();
                    retLr = lr.leggiEntiComuni((String) param.get("cod_com"));
                    param = caricaDatoLingue(retLr, param, "enti_comuni", "des_ente", "des_ente_it");
                }
                if (param.containsKey("cod_cud") && !param.get("cod_cud").equals("")) {
                    LeggiRecords lr = new LeggiRecords();
                    retLr = lr.leggiCud((String) param.get("cod_cud"));
                    param = caricaDatoLingue(retLr, param, "cud", "des_cud", "des_cud_it");
                }
                if (param.containsKey("cod_dest") && !param.get("cod_dest").equals("")) {
                    LeggiRecords lr = new LeggiRecords();
                    retLr = lr.leggiDestinatari((String) param.get("cod_dest"));
                    param = caricaDatoLingue(retLr, param, "destinatari", "intestazione", "intestazione_it");
                }
                if (param.containsKey("cod_sport") && !param.get("cod_sport").equals("")) {
                    LeggiRecords lr = new LeggiRecords();
                    retLr = lr.leggiSportelli((String) param.get("cod_sport"));
                    param = caricaDatoLingue(retLr, param, "sportelli", "des_sport", "des_sport_it");
                }
            }
            if (request.getParameter("table_name").equals("condizioni_di_attivazione")) {
                if (param.containsKey("cod_int") && !param.get("cod_int").equals("")) {
                    LeggiRecords lr = new LeggiRecords();
                    retLr = lr.leggiInterventi((String) param.get("cod_int"));
                    param = caricaDatoLingue(retLr, param, "interventi", "tit_int","tit_int_it");
                }
                if (param.containsKey("cod_ope") && !param.get("cod_ope").equals("")) {
                    LeggiRecords lr = new LeggiRecords();
                    retLr = lr.leggiOperazioni((String) param.get("cod_ope"));
                    param = caricaDatoLingue(retLr, param, "operazioni", "des_ope","des_ope_it");
                }
                if (param.containsKey("cod_sett") && !param.get("cod_sett").equals("")) {
                    LeggiRecords lr = new LeggiRecords();
                    retLr = lr.leggiSettoriAttivita((String) param.get("cod_sett"));
                    param = caricaDatoLingue(retLr, param, "settori_attivita", "des_sett","des_sett_it");
                }
                if (param.containsKey("tip_aggregazione") && !param.get("tip_aggregazione").equals("")) {
                    LeggiRecords lr = new LeggiRecords();
                    retLr = lr.leggiTipiAggregazione((String) param.get("tip_aggregazione"));
                    param = caricaDato(retLr, param, "tipi_aggregazione", "des_aggregazione");
                }
            }
            if (request.getParameter("table_name").equals("destinatari")) {
                if (param.containsKey("cod_ente") && !param.get("cod_ente").equals("")) {
                    LeggiRecords lr = new LeggiRecords();
                    retLr = lr.leggiEntiComuni((String) param.get("cod_ente"));
                    param = caricaDatoLingue(retLr, param, "enti_comuni", "des_ente", "des_ente_it");
                }
            }
            if (request.getParameter("table_name").equals("comuni_aggregazione")) {
                if (param.containsKey("cod_com") && !param.get("cod_com").equals("")) {
                    LeggiRecords lr = new LeggiRecords();
                    retLr = lr.leggiEntiComuni((String) param.get("cod_com"));
                    param = caricaDatoLingue(retLr, param, "enti_comuni", "des_ente","des_ente_it");
                }
                if (param.containsKey("tip_aggregazione") && !param.get("tip_aggregazione").equals("")) {
                    LeggiRecords lr = new LeggiRecords();
                    retLr = lr.leggiTipiAggregazione((String) param.get("tip_aggregazione"));
                    param = caricaDato(retLr, param, "tipi_aggregazione", "des_aggregazione");
                }
            }
            if (request.getParameter("table_name").equals("oneri_interventi")) {
                if (param.containsKey("cod_oneri") && !param.get("cod_oneri").equals("")) {
                    LeggiRecords lr = new LeggiRecords();
                    retLr = lr.leggiOneri((String) param.get("cod_oneri"));
                    param = caricaDatoLingue(retLr, param, "oneri", "des_oneri","des_oneri_it");
                }
                if (param.containsKey("cod_int") && !param.get("cod_int").equals("")) {
                    LeggiRecords lr = new LeggiRecords();
                    retLr = lr.leggiInterventi((String) param.get("cod_int"));
                    param = caricaDatoLingue(retLr, param, "interventi", "tit_int","tit_int_it");
                }
            }
            if (request.getParameter("table_name").equals("settori_attivita_comuni")) {
                if (param.containsKey("cod_sett") && !param.get("cod_sett").equals("")) {
                    LeggiRecords lr = new LeggiRecords();
                    retLr = lr.leggiSettoriAttivita((String) param.get("cod_sett"));
                    param = caricaDatoLingue(retLr, param, "settori_attivita", "des_sett","des_sett_it");
                }
                if (param.containsKey("cod_com") && !param.get("cod_com").equals("")) {
                    LeggiRecords lr = new LeggiRecords();
                    retLr = lr.leggiEntiComuni((String) param.get("cod_com"));
                    param = caricaDatoLingue(retLr, param, "enti_comuni", "des_ente","des_ente_it");
                }
            }
            if (request.getParameter("table_name").equals("normative")) {
                if (param.containsKey("cod_tipo_rif") && !param.get("cod_tipo_rif").equals("")) {
                    LeggiRecords lr = new LeggiRecords();
                    retLr = lr.leggiTipiRif((String) param.get("cod_tipo_rif"));
                    param = caricaDatoLingue(retLr, param, "tipi_rif", "tipo_rif","tipo_rif_it");
                }
            }

            if (request.getParameter("table_name").equals("norme_comuni")) {
                if (param.containsKey("cod_rif") && !param.get("cod_rif").equals("")) {
                    LeggiRecords lr = new LeggiRecords();
                    retLr = lr.leggiNormative((String) param.get("cod_rif"));
                    param = caricaDatoLingue(retLr, param, "normative", "tit_rif","tit_rif_it");
                }
                if (param.containsKey("cod_com") && !param.get("cod_com").equals("")) {
                    LeggiRecords lr = new LeggiRecords();
                    retLr = lr.leggiEntiComuni((String) param.get("cod_com"));
                    param = caricaDatoLingue(retLr, param, "enti_comuni", "des_ente","des_ente_it");
                }
            }
            if (request.getParameter("table_name").equals("norme_interventi")) {
                if (param.containsKey("cod_rif") && !param.get("cod_rif").equals("")) {
                    LeggiRecords lr = new LeggiRecords();
                    retLr = lr.leggiNormative((String) param.get("cod_rif"));
                    param = caricaDatoLingue(retLr, param, "normative", "tit_rif","tit_rif_it");
                }
                if (param.containsKey("cod_int") && !param.get("cod_int").equals("")) {
                    LeggiRecords lr = new LeggiRecords();
                    retLr = lr.leggiInterventi((String) param.get("cod_int"));
                    param = caricaDatoLingue(retLr, param, "interventi", "tit_int","tit_int_it");
                }
            }
            if (request.getParameter("table_name").equals("interventi_collegati")) {
                if (param.containsKey("cod_int_padre") && !param.get("cod_int_padre").equals("")) {
                    LeggiRecords lr = new LeggiRecords();
                    retLr = lr.leggiInterventi((String) param.get("cod_int_padre"));
                    param = caricaDatoLingue(retLr, param, "interventi", "tit_int_padre","tit_int_it");
                }
                if (param.containsKey("cod_int") && !param.get("cod_int").equals("")) {
                    LeggiRecords lr = new LeggiRecords();
                    retLr = lr.leggiInterventi((String) param.get("cod_int"));
                    param = caricaDatoLingue(retLr, param, "interventi", "tit_int","tit_int_it");
                }
                if (param.containsKey("cod_cond") && !param.get("cod_cond").equals("")) {
                    LeggiRecords lr = new LeggiRecords();
                    retLr = lr.leggiTestoCondizioni((String) param.get("cod_cond"));
                    param = caricaDatoLingue(retLr, param, "testo_condizioni", "testo_cond","testo_cond_it");
                }
            }
            if (request.getParameter("table_name").equals("utenti")) {
                if (param.containsKey("cod_utente_padre") && !param.get("cod_utente_padre").equals("")) {
                    LeggiRecords lr = new LeggiRecords();
                    retLr = lr.leggiUtenti((String) param.get("cod_utente_padre"));
                    param = caricaDato(retLr, param, "utenti", "nome_cognome_padre");
                }
            }
            if (request.getParameter("table_name").equals("utenti_interventi")) {
                if (param.containsKey("cod_utente") && !param.get("cod_utente").equals("")) {
                    LeggiRecords lr = new LeggiRecords();
                    retLr = lr.leggiUtenti((String) param.get("cod_utente"));
                    param = caricaDato(retLr, param, "utenti", "nome_cognome");
                }
                if (param.containsKey("cod_int") && !param.get("cod_int").equals("")) {
                    LeggiRecords lr = new LeggiRecords();
                    retLr = lr.leggiInterventi((String) param.get("cod_int"));
                    param = caricaDatoLingue(retLr, param, "interventi", "tit_int","tit_int_it");
                }
            }
            if (request.getParameter("table_name").equals("condizioni_normative")) {
                if (param.containsKey("cod_cond") && !param.get("cod_cond").equals("")) {
                    LeggiRecords lr = new LeggiRecords();
                    retLr = lr.leggiTestoCondizioni((String) param.get("cod_cond"));
                    param = caricaDatoLingue(retLr, param, "testo_condizioni", "testo_cond","test_cond_it");
                }
                if (param.containsKey("cod_rif") && !param.get("cod_rif").equals("")) {
                    LeggiRecords lr = new LeggiRecords();
                    retLr = lr.leggiNormative((String) param.get("cod_rif"));
                    param = caricaDatoLingue(retLr, param, "normative", "tit_rif","tit_rif_it");
                }
            }

            Iterator iterator = param.keySet().iterator();
            JSONObject success = new JSONObject();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                Object ob = param.get(key);
                Class cls = ob.getClass();
                if (cls.getName().equals("java.lang.String")) {
                    if (key.indexOf("_hidden") > -1) {
                        success.put(key.substring(0, key.length() - 7), (String) param.get(key));
                    } else {
                        success.put(key, (String) param.get(key));
                    }
                }
            }

            rootObj.put("success", success);

        } catch (Exception e) {
            rootObj = new JSONObject();
            rootObj.put("failure", e.getMessage());
            e.printStackTrace();
        } finally {
            out.print(rootObj);
        }
    }

    private HashMap<String, Object> caricaDato(JSONObject retLr, HashMap<String, Object> param, String tab, String campo) {
        JSONArray riga = null;
        JSONObject j = retLr.getJSONObject("success");
        if (j != null) {
            riga = j.getJSONArray(tab);
            if (riga != null && riga.size() > 0) {
                JSONObject jo = riga.getJSONObject(0);
                param.put(campo, jo.get(campo));
            }
        }
        return param;
    }
    
        private HashMap<String, Object> caricaDatoLingue(JSONObject retLr, HashMap<String, Object> param, String tab, String campoOut, String campoIn) {
        JSONArray riga = null;
        JSONObject j = retLr.getJSONObject("success");
        if (j != null) {
            riga = j.getJSONArray(tab);
            if (riga != null && riga.size() > 0) {
                JSONObject jo = riga.getJSONObject(0);
                param.put(campoOut, jo.get(campoIn));
            }
        }
        return param;
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
