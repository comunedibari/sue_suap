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
import org.slf4j.LoggerFactory;

import it.people.dbm.model.Modifica;
import it.people.dbm.utility.Configuration;
import it.people.dbm.utility.Utility;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author Piergiorgio
 */
public class Cud {

    private static Logger log = LoggerFactory.getLogger(Cud.class);

    public JSONObject action(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        PreparedStatement stLang = null;
        ResultSet rsLang = null;
        int righe = 0;
        try {
            conn = Utility.getConnection();
            String offset = request.getParameter("start");
            String size = request.getParameter("size");
            String sort = request.getParameter("sort");
            String order = request.getParameter("dir");
            String href = request.getParameter("href");
            String param = Utility.testoADb(request.getParameter("query"));

            String query = "select count(*) righe "
                    + "from cud a "
                    + "join cud_testi b "
                    + "on a.cod_cud=b.cod_cud "
                    + "where b.cod_lang='it' and (a.cod_cud like ? or b.des_cud like ?)";
            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);

            query = "select a.cod_cud cod_cud, b.des_cud des_cud "
                    + "from cud a "
                    + "join cud_testi b "
                    + "on a.cod_cud=b.cod_cud "
                    + "where b.cod_lang='it' and (a.cod_cud like ? or b.des_cud like ? )"
                    + "order by " + sort + " " + order + " limit ? , ?";

            String queryLang = "select des_cud, cod_lang from cud_testi where cod_cud = ?";

            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            st.setInt(3, Integer.parseInt(offset));
            st.setInt(4, Integer.parseInt(size));
            rs = st.executeQuery();

            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_cud", rs.getString("cod_cud"));
                stLang = conn.prepareStatement(queryLang);
                stLang.setString(1, rs.getString("cod_cud"));
                rsLang = stLang.executeQuery();
                while (rsLang.next()) {
                    loopObj.put("des_cud_" + rsLang.getString("cod_lang"), Utility.testoDaDb(rsLang.getString("des_cud")));
                }
                Utility.chiusuraJdbc(rsLang);
                Utility.chiusuraJdbc(stLang);
                riga.add(loopObj);

            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("cud", riga);
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

        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            String query = "insert into cud (cod_cud) "
                    + "values (? )";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("cod_cud"));
            st.executeUpdate();
            st.close();

            query = "insert into cud_testi (cod_cud,cod_lang,des_cud) "
                    + "values (?,?,? )";
            Set<String> parmKey = request.getParameterMap().keySet();
            for (String key : parmKey) {
                String[] parSplit = key.split("des_cud_");
                if (parSplit.length > 1) {
                    String cod_lang = parSplit[1];
                    if (cod_lang != null) {
                        st = conn.prepareStatement(query);
                        st.setString(1, request.getParameter("cod_cud"));
                        st.setString(2, cod_lang);
                        st.setString(3, Utility.testoADb(request.getParameter("des_cud_" + cod_lang)));
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
            Modifica modifica = verificaTCR(request, conn);
            String querySelect = "select * from cud_testi where cod_cud = ? and cod_lang = ?";
            String queryInsert = "insert into cud_testi (cod_cud,cod_lang,des_cud) values (?,?,? )";
            String query = "update cud_testi "
                    + "set des_cud=? "
                    + "where cod_cud =? and cod_lang=?";
            Set<String> parmKey = request.getParameterMap().keySet();
            for (String key : parmKey) {
                String[] parSplit = key.split("des_cud_");
                if (parSplit.length > 1) {
                    String cod_lang = parSplit[1];
                    if (cod_lang != null) {
                        stRead = conn.prepareStatement(querySelect);
                        stRead.setString(1, request.getParameter("cod_cud"));
                        stRead.setString(2, cod_lang);
                        rsRead = stRead.executeQuery();
                        if (rsRead.next()) {
                            st = conn.prepareStatement(query);
                            st.setString(1, Utility.testoADb(request.getParameter(key)));
                            st.setString(2, request.getParameter("cod_cud"));
                            st.setString(3, cod_lang);
                            st.executeUpdate();
                            Utility.chiusuraJdbc(rs);
                            Utility.chiusuraJdbc(st);
                        } else {
                            st = conn.prepareStatement(queryInsert);
                            st.setString(1, request.getParameter("cod_cud"));
                            st.setString(2, cod_lang);
                            st.setString(3, Utility.testoADb(request.getParameter(key)));
                            st.execute();
                            Utility.chiusuraJdbc(rs);
                            Utility.chiusuraJdbc(st);
                        }
                        Utility.chiusuraJdbc(rsRead);
                        Utility.chiusuraJdbc(stRead);
                    }
                }
            }
            scriviTCR(modifica, request, conn);
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

    public boolean controlloCancella(String key, Connection conn) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        boolean controllo = true;
        int righe = 0;
        try {
            String query = "select count(*) righe from procedimenti where cod_cud=? "
                    + "union "
                    + "select count(*) righe from oneri where cod_cud=? "
                    + "union "
                    + "select count(*) righe from relazioni_enti where cod_cud = ?";
            st = conn.prepareStatement(query);
            st.setString(1, key);
            st.setString(2, key);
            st.setString(3, key);
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
            controllo = controlloCancella(request.getParameter("cod_cud"), conn);
            if (controllo) {
                String query = "delete from cud where cod_cud= ?";
                st = conn.prepareStatement(query);
                st.setString(1, request.getParameter("cod_cud"));
                st.executeUpdate();
                st.close();

                query = "delete from cud_testi where cod_cud= ?";
                st = conn.prepareStatement(query);
                st.setString(1, request.getParameter("cod_cud"));
                st.executeUpdate();
                ret.put("success", "Cancellazione effettuata");
            } else {
                ret.put("failure", "Cud in uso");
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

    public Modifica verificaTCR(HttpServletRequest request, Connection conn) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        String sql = null;
        Modifica modifica = new Modifica();
        modifica.init();
        try {
            sql = "select a.cod_cud,b.des_cud from cud a join cud_testi b on a.cod_cud=b.cod_cud where a.cod_cud = ? and b.cod_lang='it'";
            st = conn.prepareStatement(sql);
            st.setString(1, request.getParameter("cod_cud"));
            rs = st.executeQuery();
            if (rs.next()) {
                if (!request.getParameter("des_cud_it").equals(rs.getString("des_cud"))) {
                    modifica.setModificaIntervento(true);
                }
            }
        } catch (SQLException e) {
            log.error("Errore verifica per notifica per TCR", e);
            throw e;
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
        return modifica;
    }

    public void scriviTCR(Modifica modifica, HttpServletRequest request, Connection conn) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        String sql = null;
        try {
            if (modifica.isModificaIntervento()) {
                sql = "update interventi set data_modifica = current_timestamp where cod_proc in (select distinct cod_proc from procedimenti where cod_cud = ?)";
                st = conn.prepareStatement(sql);
                st.setString(1, request.getParameter("cod_cud"));
                st.executeUpdate();
                Boolean aggiorna = Boolean.parseBoolean(Configuration.getConfigurationParameter("updateTCR").toLowerCase());
                if (aggiorna) {
                    sql = "update interventi set flg_pubblicazione = 'S' where cod_proc in (select distinct cod_proc from procedimenti where cod_cud = ?)";
                    st = conn.prepareStatement(sql);
                    st.setString(1, request.getParameter("cod_cud"));
                    st.executeUpdate();
                }
            }
        } catch (SQLException e) {
            log.error("Errore verifica per notifica per TCR", e);
            throw e;
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
    }
}
