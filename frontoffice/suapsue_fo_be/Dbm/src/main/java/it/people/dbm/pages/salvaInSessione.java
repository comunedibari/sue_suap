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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.people.dbm.model.Utente;
import net.sf.json.JSONObject;

/**
 *
 * @author giuseppe
 */
public class salvaInSessione extends HttpServlet {

    private HashMap<String, HashMap<String, String>> importXMLVariables;
    private String aggregazione;
	private HashMap<String, HashMap<String, String>> importExistences;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject rootObj = new JSONObject();
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        aggregazione = utente.getTipAggregazione();
        try {
            importXMLVariables = (HashMap<String, HashMap<String, String>>) request.getSession().getAttribute("newVariables");
            importExistences = (HashMap<String, HashMap<String, String>>) request.getSession().getAttribute("existences");
            String type = request.getParameter("type");
            String cod_orig = request.getParameter("cod_orig");
            String new_code = request.getParameter("new_code");
            //Ho già elementi di un certo tipo, quindi aggiungo la modifica
            HashMap<String, String> mappavalori = importXMLVariables.get(type);
            HashMap<String, String> existences = importExistences.get(type);
            mappavalori.put(cod_orig, new_code);
            request.getSession().setAttribute("newVariables", importXMLVariables);
         
            HashMap<String, ArrayList<String>> importExcludes = (HashMap<String, ArrayList<String>>) request.getSession().getAttribute("excludes");
            if (importExcludes != null) {
            	ArrayList<String> excludes = importExcludes.get(type);
            	if (excludes != null && excludes.contains(cod_orig)) {
            		excludes.remove(cod_orig);
            		excludes.add(new_code);
            	}
            }
            
            Iterator<String> mappaIteratore = mappavalori.keySet().iterator();
            boolean found = false;
            while (mappaIteratore.hasNext()) {
                String key = mappaIteratore.next();
                String nuovocodice = mappavalori.get(key);
                if (!key.equals(cod_orig) && nuovocodice.equals(new_code)) {
                    found = true;
                    break;
                }
            }
            if (found) {
                //..il codice è già stato utilizzato
                rootObj.put("success", false);
                rootObj.put("msg", "Valore già utilizzato");
                rootObj.put("esistenza", 'S');
            } else {
            	String ret= esisteInDb(type, new_code,request);
            	
                if (ret.equals("X")) {
                    rootObj.put("success", false);
                    rootObj.put("msg", "Valore presente in database");
                    rootObj.put("esistenza", 'X');
                } else if (ret.equals("D")){
                    rootObj.put("success", false);
                    rootObj.put("msg", "Valore inserito correttamente");
                    rootObj.put("esistenza", 'D');
                } else {
                    rootObj.put("success", true);
                    rootObj.put("msg", "Valore inserito correttamente");
                    rootObj.put("esistenza", 'N');
                }
            }
            if (existences != null) {
            	if (existences.containsKey(cod_orig)) {
            		existences.remove(cod_orig);
            	}
            	existences.put(new_code, rootObj.getString("esistenza"));
            }
        } catch (Exception e) {
            rootObj.put("success", false);
            rootObj.put("msg", e.getMessage());
            rootObj.put("esistenza", 'S');
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

    private String esisteInDb(String type, String new_code,HttpServletRequest request) throws Exception {
        importLeggiDati dati = new importLeggiDati();
        if (type.equals("interventi")) {
            return dati.leggiInterventi(new_code,aggregazione,request);
        } else if (type.equals("documenti")) {
            return dati.leggiDocumenti(new_code,aggregazione,request);
        } else if (type.equals("dichiarazioni")) {
            return dati.leggiHref(new_code,aggregazione,request);
        } else if (type.equals("normative")) {
            return dati.leggiNormative(new_code,aggregazione,request);
        } else if (type.equals("cud")) {
            return dati.leggiCud(new_code);
        } else if (type.equals("procedimenti")) {
            return dati.leggiProcedimenti(new_code,aggregazione,request);
        } else if (type.equals("testo_condizioni")) {
            return dati.leggiTestoCondizioni(new_code,aggregazione,request);
        } else if (type.equals("tipi_rif")) {
            return dati.leggiTipiRif(new_code);
        } else {
            return "S";
        }
    }
}
