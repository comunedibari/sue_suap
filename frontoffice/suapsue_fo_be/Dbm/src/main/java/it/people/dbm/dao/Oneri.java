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
package it.people.dbm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;

import it.people.dbm.model.Utente;
import it.people.dbm.utility.Utility;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.LoggerFactory;
/**
 *
 * @author Piergiorgio
 */
public class Oneri {

    private static Logger log = LoggerFactory.getLogger(Oneri.class);

    public JSONObject action(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        PreparedStatement stLang = null;
        ResultSet rsLang = null;
        JSONObject ret = null;
        int righe = 0;
        int qsi = 1;
        try {
            conn = Utility.getConnection();
            String offset = request.getParameter("start");
            String size = request.getParameter("size");
            String sort = request.getParameter("sort");
            String order = request.getParameter("dir");
            String href = request.getParameter("href");
            String param = Utility.testoADb(request.getParameter("query"));

            String query = "select count(*) righe "
                    + "from oneri a "
                    + "join oneri_testi b "
                    + "on a.cod_oneri=b.cod_oneri "
                    + "join cud c "
                    + "on a.cod_cud=c.cod_cud "
                    + "join cud_testi d "
                    + "on a.cod_cud=d.cod_cud "
                    + "and b.cod_lang=d.cod_lang "
                    + "left join oneri_documenti e "
                    + "on a.cod_doc_onere = e.cod_doc_onere "
                    + "left join oneri_documenti_testi f "
                    + "on a.cod_doc_onere=f.cod_doc_onere "
                    + "and f.cod_lang=b.cod_lang "
                    + "left join oneri_gerarchia g "
                    + "on a.cod_padre=g.cod_padre "
                    + "and g.cod_padre=g.cod_figlio "
                    + "left join oneri_gerarchia_testi h "
                    + "on g.cod_figlio= h.cod_figlio "
                    + "and h.cod_lang=b.cod_lang "
                    + "where b.cod_lang='it' and "
                    + "(a.cod_oneri like ? or b.des_oneri like ? or a.cod_cud like ? or d.des_cud like ? or b.note like ? or h.des_gerarchia like ? or f.des_doc_onere like ? ) ";
            if (!(Boolean) session.getAttribute("territorialitaNew")) {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and a.tip_aggregazione=? ";
                }
            }
            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            st.setString(3, "%" + param + "%");
            st.setString(4, "%" + param + "%");
            st.setString(5, "%" + param + "%");
            st.setString(6, "%" + param + "%");
            st.setString(7, "%" + param + "%");
            if (!(Boolean) session.getAttribute("territorialitaNew")) {
                if (utente.getTipAggregazione() != null) {
                    st.setString(8, utente.getTipAggregazione());
                }
            }
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);

            query = "select a.cod_oneri cod_oneri,a.cod_cud cod_cud,a.cod_doc_onere cod_doc_onere,a.imp_acc imp_acc, "
                    + "a.cod_padre cod_padre,b.des_oneri des_oneri,b.note note, d.des_cud des_cud, "
                    + "f.des_doc_onere des_doc_onere,h.des_gerarchia des_gerarchia, a.tip_aggregazione, i.des_aggregazione, a.cumulabile "
// PC - Oneri MIP - inizio   
                    + ",a.identificativo_contabile "                    
// PC - Oneri MIP - fine
                    + "from oneri a "
                    + "join oneri_testi b "
                    + "on a.cod_oneri=b.cod_oneri "
                    + "join cud c "
                    + "on a.cod_cud=c.cod_cud "
                    + "join cud_testi d "
                    + "on a.cod_cud=d.cod_cud "
                    + "and b.cod_lang=d.cod_lang "
                    + "left join tipi_aggregazione i "
                    + "on i.tip_aggregazione = a.tip_aggregazione "
                    + "left join oneri_documenti e "
                    + "on a.cod_doc_onere = e.cod_doc_onere "
                    + "left join oneri_documenti_testi f "
                    + "on a.cod_doc_onere=f.cod_doc_onere "
                    + "and f.cod_lang=b.cod_lang "
                    + "left join oneri_gerarchia g "
                    + "on a.cod_padre=g.cod_padre "
                    + "and g.cod_padre=g.cod_figlio "
                    + "left join oneri_gerarchia_testi h "
                    + "on g.cod_figlio= h.cod_figlio "
                    + "and h.cod_lang=b.cod_lang "
                    + "where b.cod_lang='it' and "
                    + "(a.cod_oneri like ? or b.des_oneri like ? or a.cod_cud like ? or d.des_cud like ? or b.note like ? or h.des_gerarchia like ? or f.des_doc_onere like ? ) ";
            if (!(Boolean) session.getAttribute("territorialitaNew")) {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and a.tip_aggregazione=? ";
                }
            }
            query = query + "order by " + sort + " " + order + " limit ? , ?";

            st = conn.prepareStatement(query);
            st.setString(qsi, "%" + param + "%");
            qsi++;
            st.setString(qsi, "%" + param + "%");
            qsi++;
            st.setString(qsi, "%" + param + "%");
            qsi++;
            st.setString(qsi, "%" + param + "%");
            qsi++;
            st.setString(qsi, "%" + param + "%");
            qsi++;
            st.setString(qsi, "%" + param + "%");
            qsi++;
            st.setString(qsi, "%" + param + "%");
            qsi++;
            if (!(Boolean) session.getAttribute("territorialitaNew")) {
                if (utente.getTipAggregazione() != null) {
                    st.setString(qsi, utente.getTipAggregazione());
                    qsi++;
                }
            }
            st.setInt(qsi, Integer.parseInt(offset));
            qsi++;
            st.setInt(qsi, Integer.parseInt(size));
            rs = st.executeQuery();

            String queryLang = "select des_oneri,note, cod_lang from oneri_testi where cod_oneri = ?";
            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_oneri", rs.getString("cod_oneri"));
                loopObj.put("cod_cud", rs.getString("cod_cud"));
                loopObj.put("cod_doc_onere", (rs.getObject("cod_doc_onere") == null ? "" : rs.getString("cod_doc_onere")));
                loopObj.put("imp_acc", (rs.getObject("imp_acc") == null ? "" : rs.getDouble("imp_acc")));
                loopObj.put("cod_padre", (rs.getObject("cod_padre") == null ? "" : rs.getInt("cod_padre")));
                loopObj.put("des_cud", Utility.testoDaDb(rs.getString("des_cud")));
                loopObj.put("des_doc_onere", (rs.getObject("des_doc_onere") == null ? "" : Utility.testoDaDb(rs.getString("des_doc_onere"))));
                loopObj.put("des_gerarchia", (rs.getObject("des_gerarchia") == null ? "" : Utility.testoDaDb(rs.getString("des_gerarchia"))));
                loopObj.put("tip_aggregazione", rs.getString("tip_aggregazione"));
                loopObj.put("des_aggregazione", rs.getString("des_aggregazione"));
                loopObj.put("cumulabile", Utility.testoDaDb(rs.getString("cumulabile")).equalsIgnoreCase("s") ? "true" : "false");
                loopObj.put("cumulabile_grid", Utility.testoDaDb(rs.getString("cumulabile")).equalsIgnoreCase("s") ? "Sì" : "No");
                stLang = conn.prepareStatement(queryLang);
                stLang.setString(1, rs.getString("cod_oneri"));
                rsLang = stLang.executeQuery();
                while (rsLang.next()) {
                    loopObj.put("des_oneri_" + rsLang.getString("cod_lang"), Utility.testoDaDb(rsLang.getString("des_oneri")));
                    loopObj.put("note_" + rsLang.getString("cod_lang"), (rsLang.getObject("note") == null ? "" : Utility.testoDaDb(rsLang.getString("note"))));
                }
// PC - Oneri MIP - inizio   
                loopObj.put("identificativo_contabile", (rs.getObject("identificativo_contabile") == null ? "" : Utility.testoDaDb(rs.getString("identificativo_contabile"))));                    
// PC - Oneri MIP - fine  
                Utility.chiusuraJdbc(rsLang);
                Utility.chiusuraJdbc(stLang);
                riga.add(loopObj);
            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("oneri", riga);
        } catch (Exception e) {
            log.error("Errore select ", e);
            ret = new JSONObject();
            ret.put("failure", e.getMessage());
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(rsLang);
            Utility.chiusuraJdbc(stLang);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject inserisci(HttpServletRequest request) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
// PC - Oneri MIP - inizio             
//            String query = "insert into oneri (cod_oneri,cod_cud,cod_doc_onere,cod_padre,imp_acc,tip_aggregazione) "
//                    + "values (?,?,?,?,?,?,? )";
            String query = "insert into oneri (cod_oneri,cod_cud,cod_doc_onere,cod_padre,imp_acc,tip_aggregazione,cumulabile,identificativo_contabile) "
                    + "values (?,?,?,?,?,?,?,? )"; 
// PC - Oneri MIP - fine 
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("cod_oneri"));
            st.setString(2, request.getParameter("cod_cud"));
            st.setString(3, (request.getParameter("cod_doc_onere") == null || request.getParameter("cod_doc_onere").equals("") ? null : request.getParameter("cod_doc_onere")));
            if (request.getParameter("cod_padre") == null || request.getParameter("cod_padre").equals("")) {
                st.setNull(4, java.sql.Types.INTEGER);
            } else {
                st.setInt(4, Integer.parseInt(request.getParameter("cod_padre")));
            }
            if (request.getParameter("imp_acc") == null || request.getParameter("imp_acc").equals("")) {
                st.setNull(5, java.sql.Types.DOUBLE);
            } else {
                st.setDouble(5, Double.parseDouble(request.getParameter("imp_acc")));
            }
            st.setString(6, request.getParameter("tip_aggregazione"));
            st.setString(7, (request.getParameter("cumulabile") == null
                    || request.getParameter("cumulabile").equals("") ? "N"
                    : "S"));
// PC - Oneri MIP - inizio
            st.setString(8, (request.getParameter("identificativo_contabile") == null || request.getParameter("identificativo_contabile").equals("") ? null : request.getParameter("identificativo_contabile")));            
// PC - Oneri MIP - fine   					
            st.executeUpdate();
            st.close();

            query = "insert into oneri_testi (cod_oneri,cod_lang,des_oneri,note) "
                    + "values (?,?,?,? )";
            Set<String> parmKey = request.getParameterMap().keySet();
            for (String key : parmKey) {
                String[] parSplit = key.split("des_oneri_");
                if (parSplit.length > 1 && key.contains("des_oneri_")) {
                    String cod_lang = parSplit[1];
                    if (cod_lang != null) {
                        st = conn.prepareStatement(query);
                        st.setString(1, request.getParameter("cod_oneri"));
                        st.setString(2, cod_lang);
                        st.setString(3, Utility.testoADb(request.getParameter("des_oneri_" + cod_lang)));
                        st.setString(4, (request.getParameter("note_" + cod_lang) == null || request.getParameter("note_" + cod_lang).equals("") ? null : Utility.testoADb(request.getParameter("note_" + cod_lang))));
                        st.executeUpdate();
                        Utility.chiusuraJdbc(rs);
                        Utility.chiusuraJdbc(st);
                    }
                }
            }
            ret.put("success", "Inserimento effettuato");
        } catch (SQLException e) {
            log.error("Errore insert ", e);
            ret = new JSONObject();
            ret.put("failure", e.getMessage());
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject aggiorna(HttpServletRequest request) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        PreparedStatement stRead = null;
        ResultSet rsRead = null;
        JSONObject ret = null;
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");        

        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
// PC - Oneri MIP - inizio            
//            String query = "update oneri "
//                    + "set cod_cud = ?,"
//                    + "cod_doc_onere= ?, "
//                    + "cod_padre= ?, "
//                    + "imp_acc= ? "
//                    + "cumulabile= ? " 
//                    + "where cod_oneri =?";
            String query = "update oneri "
                    + "set cod_cud = ?,"
                    + "cod_doc_onere= ?, "
                    + "cod_padre= ?, "
                    + "imp_acc= ?, "
                    + "cumulabile= ?, "
                    + "identificativo_contabile = ? "
                    + "where cod_oneri =?";            
// PC - Oneri MIP - fine 
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("cod_cud"));
            st.setString(2, (request.getParameter("cod_doc_onere") == null || request.getParameter("cod_doc_onere").equals("") ? null : request.getParameter("cod_doc_onere")));
            if (request.getParameter("cod_padre") == null || request.getParameter("cod_padre").equals("")) {
                st.setNull(3, java.sql.Types.INTEGER);
            } else {
                st.setInt(3, Integer.parseInt(request.getParameter("cod_padre")));
            }
            if (request.getParameter("imp_acc") == null || request.getParameter("imp_acc").equals("")) {
                st.setNull(4, java.sql.Types.DOUBLE);
            } else {
                st.setDouble(4, Double.parseDouble(request.getParameter("imp_acc")));
            }
            st.setString(5, (request.getParameter("cumulabile") == null
                    || request.getParameter("cumulabile").equals("") ? "N"
                    : "S"));
// PC - Oneri MIP - inizio             
//            st.setString(6, request.getParameter("cod_oneri"));
            st.setString(6, (request.getParameter("identificativo_contabile") == null || request.getParameter("identificativo_contabile").equals("") ? null : request.getParameter("identificativo_contabile")));            
            st.setString(7, request.getParameter("cod_oneri"));            
// PC - Oneri MIP - fine             
            st.executeUpdate();
            st.close();
            if ((Boolean) session.getAttribute("territorialitaNew")) {
//                if (utente.getTipAggregazione() != null) {
                    if (request.getParameter("tip_aggregazione") != null) {
                        query = "update oneri "
                                + "set tip_aggregazione = ?"
                                + "where cod_oneri =? and tip_aggregazione is null";
                        st = conn.prepareStatement(query);
                        st.setString(1, request.getParameter("tip_aggregazione"));
                        st.setString(2, request.getParameter("cod_oneri"));
                        st.executeUpdate();
                        st.close();
                    }
//                }
            }            
            String querySelect = "select * from oneri_testi where cod_oneri = ? and cod_lang = ?";
            String queryInsert = "insert into oneri_testi (cod_oneri,cod_lang,des_oneri,note) values (?,?,?,? )";
            query = "update oneri_testi "
                    + "set des_oneri=?, "
                    + "note=? "
                    + "where cod_oneri =? and cod_lang=?";
            Set<String> parmKey = request.getParameterMap().keySet();
            for (String key : parmKey) {
                String[] parSplit = key.split("des_oneri_");
                if (parSplit.length > 1 && key.contains("des_oneri_")) {
                    String cod_lang = parSplit[1];
                    if (cod_lang != null) {
                        stRead = conn.prepareStatement(querySelect);
                        stRead.setString(1, request.getParameter("cod_oneri"));
                        stRead.setString(2, cod_lang);
                        rsRead = stRead.executeQuery();
                        if (rsRead.next()) {
                            st = conn.prepareStatement(query);
                            st.setString(1, Utility.testoADb(request.getParameter(key)));
                            st.setString(2, (request.getParameter("note_" + cod_lang) == null || request.getParameter("note_" + cod_lang).equals("") ? null : Utility.testoADb(request.getParameter("note_" + cod_lang))));
                            st.setString(3, request.getParameter("cod_oneri"));
                            st.setString(4, cod_lang);
                            st.executeUpdate();

                        } else {
                            st = conn.prepareStatement(queryInsert);
                            st.setString(1, request.getParameter("cod_oneri"));
                            st.setString(2, cod_lang);
                            st.setString(3, Utility.testoADb(request.getParameter(key)));
                            st.setString(4, (request.getParameter("note_" + cod_lang) == null || request.getParameter("note_" + cod_lang).equals("") ? null : Utility.testoADb(request.getParameter("note_" + cod_lang))));
                            st.execute();

                        }
                        Utility.chiusuraJdbc(rs);
                        Utility.chiusuraJdbc(st);
                        Utility.chiusuraJdbc(rsRead);
                        Utility.chiusuraJdbc(stRead);
                    }
                }
            }

            ret.put("success", "Aggiornamento effettuato");
        } catch (SQLException e) {
            log.error("Errore aggiornamento ", e);
            ret = new JSONObject();
            ret.put("failure", e.getMessage());
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(rsRead);
            Utility.chiusuraJdbc(stRead);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public boolean controlloCancella(String key, Connection conn) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        boolean controllo = true;
        int righe = 0;
        try {
            String query = "select count(*) righe from oneri_interventi where cod_oneri=? ";
            st = conn.prepareStatement(query);
            st.setString(1, key);
            rs = st.executeQuery();
            while (rs.next()) {
                righe = righe + rs.getInt("righe");
            }
            if (righe > 0) {
                controllo = false;
            }
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
        return controllo;
    }

    public JSONObject cancella(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        boolean controllo = true;
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            controllo = controlloCancella(request.getParameter("cod_oneri"), conn);
            if (controllo) {
                String query = "delete from oneri where cod_oneri= ?";
                st = conn.prepareStatement(query);
                st.setString(1, request.getParameter("cod_oneri"));
                st.executeUpdate();
                st.close();

                query = "delete from oneri_testi where cod_oneri= ?";
                st = conn.prepareStatement(query);
                st.setString(1, request.getParameter("cod_oneri"));
                st.executeUpdate();
                ret.put("success", "Cancellazione effettuata");
            } else {
                ret.put("failure", "Onere in uso");
            }
        } catch (SQLException e) {
            log.error("Errore delete ", e);
            ret = new JSONObject();
            ret.put("failure", e.getMessage());
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }
}
