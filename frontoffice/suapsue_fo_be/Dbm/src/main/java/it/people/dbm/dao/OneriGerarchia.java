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
public class OneriGerarchia {

    private static Logger log = LoggerFactory.getLogger(OneriGerarchia.class);
    private JSONArray jArrayGerarchia;

    public void actionLoop(String cod_padre, JSONArray jo, Connection conn, Utente utente, HttpSession session) throws Exception {

        PreparedStatement st = null;
        ResultSet rs = null;
        PreparedStatement stLang = null;
        ResultSet rsLang = null;

        try {
            String query = "select a.cod_figlio text,a.cod_figlio, a.cod_padre, a.cod_onere_formula, "
                    + "b.des_gerarchia, d.des_gerarchia des_padre, e.des_formula, a.tip_aggregazione, f.des_aggregazione "
                    + "from oneri_gerarchia a "
                    + "join oneri_gerarchia_testi b "
                    + "on a.cod_figlio=b.cod_figlio "
                    + "and b.cod_lang='it' "
                    + "left join oneri_gerarchia c "
                    + "on a.cod_padre = c.cod_figlio "
                    + "left join oneri_gerarchia_testi d "
                    + "on c.cod_figlio=d.cod_figlio "
                    + "and d.cod_lang='it' "
                    + "left join oneri_formula e "
                    + "on a.cod_onere_formula=e.cod_onere_formula "
                    + "left join tipi_aggregazione f "
                    + "on a.tip_aggregazione=f.tip_aggregazione "
                    + "where a.cod_padre "
                    + (cod_padre.equals("") ? " = a.cod_figlio " : "= '" + cod_padre + "' and a.cod_padre <> a.cod_figlio ");
            if (!(Boolean) session.getAttribute("territorialitaNew")) {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and a.tip_aggregazione=? ";
                }
            }
            query = query + "  order by a.cod_figlio";
            st = conn.prepareStatement(query);
            if (!(Boolean) session.getAttribute("territorialitaNew")) {
                if (utente.getTipAggregazione() != null) {
                    st.setString(1, utente.getTipAggregazione());
                }
            }
            rs = st.executeQuery();
            String queryLang = "select des_gerarchia, cod_lang from oneri_gerarchia_testi where cod_figlio = ?";
            JSONObject ele;
            while (rs.next()) {
                ele = new JSONObject();
                ele.put("iconCls", "task-folder");
                ele.put("uiProvider", "col");
                ele.put("children", new JSONArray());
                ele.put("text", Utility.testoDaDb(rs.getString("text")));
                ele.put("cod_figlio", rs.getString("cod_figlio"));
                ele.put("cod_padre", rs.getString("cod_padre"));
                ele.put("cod_onere_formula", (rs.getObject("cod_onere_formula") == null ? "" : rs.getString("cod_onere_formula")));
                ele.put("des_padre", (rs.getObject("des_padre") == null ? "" : Utility.testoDaDb(rs.getString("des_padre"))));
                ele.put("des_formula", (rs.getObject("des_formula") == null ? "" : Utility.testoDaDb(rs.getString("des_formula"))));
                ele.put("tip_aggregazione", rs.getString("tip_aggregazione"));
                ele.put("des_aggregazione", rs.getString("des_aggregazione"));
                stLang = conn.prepareStatement(queryLang);
                stLang.setInt(1, rs.getInt("cod_figlio"));
                rsLang = stLang.executeQuery();
                while (rsLang.next()) {
                    ele.put("des_gerarchia_" + rsLang.getString("cod_lang"), (rsLang.getObject("des_gerarchia") == null ? "" : Utility.testoDaDb(rsLang.getString("des_gerarchia"))));
                }
                Utility.chiusuraJdbc(rsLang);
                Utility.chiusuraJdbc(stLang);
                if (rs.getObject("cod_onere_formula") == null) {
                    ele.put("iconCls", "task-folder");
                    actionLoop(rs.getString("cod_figlio"), ele.getJSONArray("children"), conn, utente, session);
                } else {
                    ele.put("leaf", "false");
                    ele.put("cls", "task");
                }
                jo.add(ele);
            }
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(rsLang);
            Utility.chiusuraJdbc(stLang);
        }
    }

    public JSONArray action(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
//        JSONArray ret = new JSONArray();
        try {
            jArrayGerarchia = new JSONArray();
            conn = Utility.getConnection();
            actionLoop("", jArrayGerarchia, conn, utente, session);
            return jArrayGerarchia;
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
    }

    public JSONObject inserisci(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;

        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            String query = "insert into oneri_gerarchia (cod_figlio,cod_padre,cod_onere_formula,tip_aggregazione) "
                    + "values (?,?,?,?)";
            st = conn.prepareStatement(query);
            st.setInt(1, Integer.parseInt(request.getParameter("cod_figlio")));
            st.setInt(2, (request.getParameter("cod_padre") != null && !request.getParameter("cod_padre").trim().equals("") ? Integer.parseInt(request.getParameter("cod_padre")) : Integer.parseInt(request.getParameter("cod_figlio"))));
            st.setString(3, (request.getParameter("cod_onere_formula") == null || request.getParameter("cod_onere_formula").equals("") ? null : request.getParameter("cod_onere_formula")));
            st.setString(4, request.getParameter("tip_aggregazione"));
            st.executeUpdate();
            st.close();

            query = "insert into oneri_gerarchia_testi (cod_figlio,cod_lang,des_gerarchia) "
                    + "values (?,?,? )";
            Set<String> parmKey = request.getParameterMap().keySet();
            for (String key : parmKey) {
                String[] parSplit = key.split("des_gerarchia_");
                if (parSplit.length > 0 && key.contains("des_gerarchia_")) {
                    String cod_lang = parSplit[1];
                    if (cod_lang != null) {
                        st = conn.prepareStatement(query);
                        st.setInt(1, Integer.parseInt(request.getParameter("cod_figlio")));
                        st.setString(2, cod_lang);
                        st.setString(3, Utility.testoADb(request.getParameter( "des_gerarchia_" + cod_lang)));
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
            String query = "update oneri_gerarchia "
                    + "set cod_onere_formula=? , "
                    + "cod_padre=? "
                    + "where cod_figlio=?";
            st = conn.prepareStatement(query);
            st.setString(1, (request.getParameter("cod_onere_formula") == null || request.getParameter("cod_onere_formula").equals("") ? null : request.getParameter("cod_onere_formula")));
            st.setInt(2, Integer.parseInt(request.getParameter("cod_padre")));
            st.setInt(3, Integer.parseInt(request.getParameter("cod_figlio")));
            st.executeUpdate();
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            if ((Boolean) session.getAttribute("territorialitaNew")) {
//                if (utente.getTipAggregazione() != null) {
                    if (request.getParameter("tip_aggregazione") != null) {
                        Integer root=cercaRoot(Integer.parseInt(request.getParameter("cod_figlio")), conn);
                        aggiornaLoopDown(root, conn, request.getParameter("tip_aggregazione"));
                    }
//                }
            }
            String querySelect = "select * from oneri_gerarchia_testi where cod_figlio = ? and cod_lang = ?";
            String queryInsert = "insert into oneri_gerarchia_testi (cod_figlio,des_gerarchia,cod_lang) values (?,?,? )";
            query = "update oneri_gerarchia_testi "
                    + "set des_gerarchia=? "
                    + "where cod_figlio=? and cod_lang=?";
            Set<String> parmKey = request.getParameterMap().keySet();
            for (String key : parmKey) {
                String[] parSplit = key.split("des_gerarchia_");
                if (parSplit.length > 0 && key.contains("des_gerarchia_")) {
                    String cod_lang = parSplit[1];
                    if (cod_lang != null) {
                        stRead = conn.prepareStatement(querySelect);
                        stRead.setInt(1, Integer.parseInt(request.getParameter("cod_figlio")));
                        stRead.setString(2, cod_lang);
                        rsRead = stRead.executeQuery();
                        if (rsRead.next()) {
                            st = conn.prepareStatement(query);
                            st.setString(1, Utility.testoADb(request.getParameter(key)));
                            st.setInt(2, Integer.parseInt(request.getParameter("cod_figlio")));
                            st.setString(3, cod_lang);
                            st.executeUpdate();
                            Utility.chiusuraJdbc(rs);
                            Utility.chiusuraJdbc(st);
                        } else {
                            st = conn.prepareStatement(queryInsert);
                            st.setInt(1, Integer.parseInt(request.getParameter("cod_figlio")));
                            st.setString(2, Utility.testoADb(request.getParameter(key)));
                            st.setString(3, cod_lang);
                            st.execute();
                            Utility.chiusuraJdbc(rs);
                            Utility.chiusuraJdbc(st);
                        }
                        Utility.chiusuraJdbc(rsRead);
                        Utility.chiusuraJdbc(stRead);
                    }
                }
            }
            ret.put("success", "Aggiornamento effettuato");
        } catch (SQLException e) {
            log.error("Errore update ", e);
            ret = new JSONObject();
            ret.put("failure", e.getMessage());
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public void cancellaLoop(Integer codRamo, Connection conn) throws Exception {

        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        try {
            String query = "select * from oneri_gerarchia where cod_padre= ? and cod_padre <> cod_figlio";
            st = conn.prepareStatement(query);
            st.setInt(1, codRamo);
            rs = st.executeQuery();
            while (rs.next()) {
                cancellaLoop(rs.getInt("cod_figlio"), conn);
            }
            rs.close();
            st.close();
            query = "delete from oneri_gerarchia where cod_figlio = ?";
            st = conn.prepareStatement(query);
            st.setInt(1, codRamo);
            st.executeUpdate();
            st.close();
            query = "delete from oneri_gerarchia_testi where cod_figlio = ?";
            st = conn.prepareStatement(query);
            st.setInt(1, codRamo);
            st.executeUpdate();
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
    }

    public JSONObject cancella(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            cancellaLoop(Integer.parseInt(request.getParameter("cod_figlio")), conn);
            ret.put("success", "Cancellazione effettuata");
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

    public void aggiornaLoopDown(Integer codRamo, Connection conn, String tip_aggregazione) throws Exception {

        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String query = "select * from oneri_gerarchia where cod_padre= ? and cod_padre <> cod_figlio";
            st = conn.prepareStatement(query);
            st.setInt(1, codRamo);
            rs = st.executeQuery();
            while (rs.next()) {
                aggiornaLoopDown(rs.getInt("cod_figlio"), conn, tip_aggregazione);
            }
            rs.close();
            st.close();
            query = "update oneri_gerarchia "
                    + "set tip_aggregazione = ?"
                    + "where cod_figlio =? and tip_aggregazione is null";
            st = conn.prepareStatement(query);
            st.setString(1, tip_aggregazione);
            st.setInt(2, codRamo);
            st.executeUpdate();
        } catch (SQLException e) {
            log.error("Errore update ", e);
            throw e;
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
    }

    public Integer cercaRoot(Integer codRamo, Connection conn) throws Exception {

        PreparedStatement st = null;
        ResultSet rs = null;
        Integer ret=null;
        try {
            String query = "select * from oneri_gerarchia where cod_figlio= ?";
            st = conn.prepareStatement(query);
            st.setInt(1, codRamo);
            rs = st.executeQuery();
            while (rs.next()) {
                if (rs.getInt("cod_padre") != rs.getInt("cod_figlio")) {
                    ret=cercaRoot(rs.getInt("cod_padre"), conn);
                } else {
                    ret=rs.getInt("cod_figlio");
                }
            }
            return ret;
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
    }

    public JSONObject leggiRamo(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        try {
            conn = Utility.getConnection();
            String query = "select a.cod_figlio, "
                    + "(select count(*) from oneri_gerarchia where cod_padre = ?) figli, a.tip_aggregazione, e.des_aggregazione "
                    + "from oneri_gerarchia a "
                    + "join oneri_gerarchia_testi b "
                    + "on a.cod_figlio=b.cod_figlio "
                    + "and b.cod_lang='it' "
                    + "left join tipi_aggregazione e "
                    + "on a.tip_aggregazione=e.tip_aggregazione "
                    + "where a.cod_figlio  = ?";

            st = conn.prepareStatement(query);
            st.setInt(1, Integer.parseInt(request.getParameter("cod_figlio")));
            st.setInt(2, Integer.parseInt(request.getParameter("cod_figlio")));
            rs = st.executeQuery();

            JSONObject loopObj = new JSONObject();
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_figlio", rs.getInt("cod_figlio"));
                loopObj.put("figli", rs.getInt("figli"));
                loopObj.put("tip_aggregazione", rs.getString("tip_aggregazione"));
                loopObj.put("des_aggregazione", rs.getString("des_aggregazione"));
            }
            ret = new JSONObject();
            ret.put("success", loopObj);
        } catch (Exception e) {
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
