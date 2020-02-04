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

import org.slf4j.Logger;

import it.people.dbm.utility.Utility;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.LoggerFactory;
/**
 *
 * @author Piergiorgio
 */
public class GerarchiaOperazioni {

    private static Logger log = LoggerFactory.getLogger(GerarchiaOperazioni.class);
    private JSONArray jArrayGerarchia;

    public void actionLoop(String tip_aggregazione, String cod_ramo_prec, JSONArray jo, Connection conn) throws Exception {

        PreparedStatement st = null;
        ResultSet rs = null;
        PreparedStatement stLang = null;
        ResultSet rsLang = null;

        try {
            String query = "select a.tip_aggregazione, a.cod_ramo text,a.cod_ramo,a.cod_ramo_prec, a.cod_ope, a.cod_rif, "
                    + "b.des_ramo, d.des_ramo des_ramo_prec, e.des_ope, g.tit_rif, a.flg_sino, a.raggruppamento_check "
                    + "from gerarchia_operazioni a "
                    + "join gerarchia_operazioni_testi b "
                    + "on a.cod_ramo=b.cod_ramo "
                    + "and a.tip_aggregazione=b.tip_aggregazione "
                    + "and b.cod_lang='it' "
                    + "left join gerarchia_operazioni c "
                    + "on a.cod_ramo_prec = c.cod_ramo "
                    + "and a.tip_aggregazione = c.tip_aggregazione "
                    + "left join gerarchia_operazioni_testi d "
                    + "on c.cod_ramo=d.cod_ramo "
                    + "and c.tip_aggregazione = d.tip_aggregazione "
                    + "and d.cod_lang='it' "
                    + "left join operazioni e "
                    + "on a.cod_ope=e.cod_ope "
                    + "and e.cod_lang='it' "
                    + "left join normative f "
                    + "on a.cod_rif=f.cod_rif "
                    + "left join normative_testi g "
                    + "on f.cod_rif = g.cod_rif "
                    + "and g.cod_lang='it' "
                    + "where a.tip_aggregazione = ? "
                    + "and a.cod_ramo_prec "
                    + (cod_ramo_prec == null || cod_ramo_prec.equals("") ? "is null" : " = '" + cod_ramo_prec + "'") + " order by a.cod_ramo";
            String queryLang = "select des_ramo, cod_lang from gerarchia_operazioni_testi where tip_aggregazione = ? and cod_ramo = ?";
            st = conn.prepareStatement(query);
            st.setString(1, tip_aggregazione);
            rs = st.executeQuery();
            JSONObject ele;
            while (rs.next()) {
                ele = new JSONObject();
                ele.put("iconCls", "task-folder");
                ele.put("uiProvider", "col");
                ele.put("children", new JSONArray());
                ele.put("text", Utility.testoDaDb(rs.getString("text")));
                ele.put("tip_aggregazione", Utility.testoDaDb(rs.getString("tip_aggregazione")));
                ele.put("cod_ramo", rs.getString("cod_ramo"));
                ele.put("cod_ope", (rs.getObject("cod_ope") == null ? "" : rs.getString("cod_ope")));
                ele.put("cod_ramo_prec", (rs.getObject("cod_ramo_prec") == null ? "" : rs.getString("cod_ramo_prec")));
                ele.put("cod_rif", (rs.getObject("cod_rif") == null ? "" : rs.getString("cod_rif")));
                ele.put("des_ramo_prec", (rs.getObject("des_ramo_prec") == null ? "" : Utility.testoDaDb(rs.getString("des_ramo_prec"))));
                ele.put("des_ope", (rs.getObject("des_ope") == null ? "" : Utility.testoDaDb(rs.getString("des_ope"))));
                ele.put("tit_rif", (rs.getObject("tit_rif") == null ? "" : Utility.testoDaDb(rs.getString("tit_rif"))));
                ele.put("des_ramo", Utility.testoDaDb(rs.getString("des_ramo")));
                ele.put("flg_sino", Utility.testoDaDb(rs.getString("flg_sino")));
                stLang = conn.prepareStatement(queryLang);
                stLang.setString(1, rs.getString("tip_aggregazione"));
                stLang.setString(2, rs.getString("cod_ramo"));
                rsLang = stLang.executeQuery();
                while (rsLang.next()) {
                    ele.put(rsLang.getString("cod_lang") + "_des_ramo", Utility.testoDaDb(rsLang.getString("des_ramo")));
                }
                Utility.chiusuraJdbc(rsLang);
                Utility.chiusuraJdbc(stLang);
                ele.put("raggruppamento_check", (rs.getObject("raggruppamento_check") == null ? "" : Utility.testoDaDb(rs.getString("raggruppamento_check"))));
                if (rs.getObject("cod_ope") == null) {
                    ele.put("iconCls", "task-folder");
                    actionLoop(tip_aggregazione, rs.getString("cod_ramo"), ele.getJSONArray("children"), conn);
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

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
//        JSONArray ret = new JSONArray();
        try {
            jArrayGerarchia = new JSONArray();
            conn = Utility.getConnection();
            actionLoop(request.getParameter("tip_aggregazione"), "", jArrayGerarchia, conn);
            return jArrayGerarchia;
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
    }

    public JSONObject inserisci(HttpServletRequest request) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;

        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            String query = "insert into gerarchia_operazioni (tip_aggregazione,cod_ramo,cod_ramo_prec,cod_ope,cod_rif,raggruppamento_check,flg_sino) "
                    + "values (?,?,?,?,?,?,?)";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("tip_aggregazione"));
            st.setString(2, request.getParameter("cod_ramo"));
            st.setString(3, (request.getParameter("cod_ramo_prec") == null || request.getParameter("cod_ramo_prec").equals("") ? null : request.getParameter("cod_ramo_prec")));
            st.setString(4, (request.getParameter("cod_ope") == null || request.getParameter("cod_ope").equals("") ? null : request.getParameter("cod_ope")));
            st.setString(5, (request.getParameter("cod_rif") == null || request.getParameter("cod_rif").equals("") ? null : request.getParameter("cod_rif")));
            st.setString(6, (request.getParameter("raggruppamento_check") == null || request.getParameter("raggruppamento_check").equals("") ? null : request.getParameter("raggruppamento_check")));
            st.setString(7, request.getParameter("flg_sino_hidden"));
            st.executeUpdate();
            st.close();

            query = "insert into gerarchia_operazioni_testi (tip_aggregazione,cod_ramo,des_ramo,cod_lang) "
                    + "values (?,?,?,? )";
            Set<String> parmKey = request.getParameterMap().keySet();
            for (String key : parmKey) {
                String[] parSplit = key.split("_des_ramo");
                if (parSplit.length > 0 && key.contains("_des_ramo")) {
                    String cod_lang = parSplit[0];
                    if (cod_lang != null) {
                        st = conn.prepareStatement(query);
                        st.setString(1, request.getParameter("tip_aggregazione"));
                        st.setString(2, request.getParameter("cod_ramo"));
                        st.setString(3, Utility.testoADb(request.getParameter(cod_lang + "_des_ramo")));
                        st.setString(4, cod_lang);
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

        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            String query = "update gerarchia_operazioni "
                    + "set cod_ope=? , "
                    + "cod_rif=?, "
                    + "flg_sino=?, "
                    + "raggruppamento_check=? "
                    + "where tip_aggregazione=? and cod_ramo=?";
            st = conn.prepareStatement(query);
            st.setString(1, (request.getParameter("cod_ope") == null || request.getParameter("cod_ope").equals("") ? null : request.getParameter("cod_ope")));
            st.setString(2, (request.getParameter("cod_rif") == null || request.getParameter("cod_rif").equals("") ? null : request.getParameter("cod_rif")));
            st.setString(3, request.getParameter("flg_sino_hidden"));
            st.setString(4, (request.getParameter("raggruppamento_check") == null || request.getParameter("raggruppamento_check").equals("") ? null : request.getParameter("raggruppamento_check")));
            st.setString(5, request.getParameter("tip_aggregazione"));
            st.setString(6, request.getParameter("cod_ramo"));

            st.executeUpdate();
            st.close();

            String querySelect = "select * from gerarchia_operazioni_testi where tip_aggregazione = ? and cod_ramo = ? and cod_lang = ?";
            String queryInsert = "insert into gerarchia_operazioni_testi (tip_aggregazione,cod_ramo,des_ramo,cod_lang) values (?,?,?,? )";

            query = "update gerarchia_operazioni_testi "
                    + "set des_ramo=? "
                    + "where tip_aggregazione=? and cod_ramo=? and cod_lang=?";
            Set<String> parmKey = request.getParameterMap().keySet();
            for (String key : parmKey) {
                String[] parSplit = key.split("_des_ramo");
                if (parSplit.length > 0 && key.contains("_des_ramo")) {
                    String cod_lang = parSplit[0];
                    if (cod_lang != null) {
                        stRead = conn.prepareStatement(querySelect);
                        stRead.setString(1, request.getParameter("tip_aggregazione"));
                        stRead.setString(2, request.getParameter("cod_ramo"));
                        stRead.setString(3, cod_lang);
                        rsRead = stRead.executeQuery();
                        if (rsRead.next()) {
                            st = conn.prepareStatement(query);
                            st.setString(1, Utility.testoADb(request.getParameter(key)));
                            st.setString(2, request.getParameter("tip_aggregazione"));
                            st.setString(3, request.getParameter("cod_ramo"));
                            st.setString(4, cod_lang);
                            st.executeUpdate();
                            Utility.chiusuraJdbc(rs);
                            Utility.chiusuraJdbc(st);
                        } else {
                            st = conn.prepareStatement(queryInsert);
                            st.setString(1, request.getParameter("tip_aggregazione"));
                            st.setString(2, request.getParameter("cod_ramo"));
                            st.setString(3, Utility.testoADb(request.getParameter(key)));
                            st.setString(4, cod_lang);
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
            Utility.chiusuraJdbc(rsRead);
            Utility.chiusuraJdbc(stRead);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public void cancellaLoop(String tipAggregazione, String codRamo, Connection conn) throws Exception {

        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        try {
            String query = "select cod_ramo,cod_ramo_prec from gerarchia_operazioni "
                    + "where cod_ramo_prec=? and tip_aggregazione=?";
            st = conn.prepareStatement(query);
            st.setString(1, codRamo);
            st.setString(2, tipAggregazione);
            rs = st.executeQuery();
            while (rs.next()) {
                cancellaLoop(tipAggregazione, rs.getString("cod_ramo"), conn);
            }
            rs.close();
            st.close();
            query = "delete from gerarchia_operazioni where tip_aggregazione= ? and cod_ramo = ?";
            st = conn.prepareStatement(query);
            st.setString(1, tipAggregazione);
            st.setString(2, codRamo);
            st.executeUpdate();
            st.close();
            query = "delete from gerarchia_operazioni_testi where tip_aggregazione= ? and cod_ramo = ?";
            st = conn.prepareStatement(query);
            st.setString(1, tipAggregazione);
            st.setString(2, codRamo);
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
            ret = new JSONObject();
            conn = Utility.getConnection();
            cancellaLoop(request.getParameter("tip_aggregazione"), request.getParameter("cod_ramo"), conn);
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

    public JSONObject leggiRamo(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        try {
            conn = Utility.getConnection();
            String query = "select a.cod_ope, "
                    + "(select count(*) from gerarchia_operazioni where cod_ramo_prec = ? and tip_aggregazione=?) figli "
                    + "from gerarchia_operazioni a "
                    + "join gerarchia_operazioni_testi b "
                    + "on a.cod_ramo=b.cod_ramo "
                    + "and b.cod_lang='it' "
                    + "where a.cod_ramo  = ? and a.tip_aggregazione=?";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("cod_ramo"));
            st.setString(2, request.getParameter("tip_aggregazione"));
            st.setString(3, request.getParameter("cod_ramo"));
            st.setString(4, request.getParameter("tip_aggregazione"));
            rs = st.executeQuery();

            JSONObject loopObj = new JSONObject();
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_ope", rs.getString("cod_ope"));
                loopObj.put("figli", rs.getInt("figli"));
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
