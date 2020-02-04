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

import it.people.dbm.model.Modifica;
import it.people.dbm.model.Utente;
import it.people.dbm.utility.Configuration;
import it.people.dbm.utility.Utility;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.LoggerFactory;
/**
 *
 * @author Piergiorgio
 */
public class Procedimenti {

    private static Logger log = LoggerFactory.getLogger(Procedimenti.class);

    public JSONObject action(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        PreparedStatement stLang = null;
        ResultSet rsLang = null;
        int righe = 0;
        int qsi = 1;
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        try {
            conn = Utility.getConnection();
            String offset = request.getParameter("start");
            String size = request.getParameter("size");
            String sort = request.getParameter("sort");
            String order = request.getParameter("dir");
            String href = request.getParameter("href");
            String param = Utility.testoADb(request.getParameter("query"));

            String query = "select count(*) righe "
                    + "from procedimenti a "
                    + "join procedimenti_testi b "
                    + "on a.cod_proc=b.cod_proc "
                    + "join cud c "
                    + "on a.cod_cud=c.cod_cud "
                    + "join cud_testi d "
                    + "on a.cod_cud = d.cod_cud "
                    + "and d.cod_lang=b.cod_lang "
                    + "where b.cod_lang='it' and (a.cod_proc like ? or b.tit_proc like ? or a.cod_cud like ? or d.des_cud like ?) ";
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
            if (!(Boolean) session.getAttribute("territorialitaNew")) {
                if (utente.getTipAggregazione() != null) {
                    st.setString(5, utente.getTipAggregazione());
                }
            }
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);

            query = "select a.cod_proc cod_proc, a.flg_bollo flg_bollo, a.flg_tipo_proc flg_tipo_proc, a.cod_cud cod_cud, "
                    + "b.tit_proc tit_proc, d.des_cud, a.ter_eva ter_eva, a.tip_aggregazione, e.des_aggregazione "
                    + "from procedimenti a "
                    + "join procedimenti_testi b "
                    + "on a.cod_proc=b.cod_proc "
                    + "join cud c "
                    + "on a.cod_cud=c.cod_cud "
                    + "join cud_testi d "
                    + "on a.cod_cud = d.cod_cud "
                    + "and d.cod_lang=b.cod_lang "
                    + "left join tipi_aggregazione e "
                    + "on a.tip_aggregazione = e.tip_aggregazione "
                    + "where b.cod_lang='it' and (a.cod_proc like ? or b.tit_proc like ? or a.cod_cud like ? or d.des_cud like ?) ";
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

            String queryLang = "select tit_proc, cod_lang from procedimenti_testi where cod_proc = ?";

            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_proc", rs.getString("cod_proc"));
                loopObj.put("cod_cud", rs.getString("cod_cud"));
                loopObj.put("flg_bollo", rs.getString("flg_bollo"));
                loopObj.put("des_cud", Utility.testoDaDb(rs.getString("des_cud")));
                loopObj.put("flg_tipo_proc", rs.getString("flg_tipo_proc"));
                loopObj.put("ter_eva", rs.getInt("ter_eva"));
                loopObj.put("tip_aggregazione", rs.getString("tip_aggregazione"));
                loopObj.put("des_aggregazione", rs.getString("des_aggregazione"));
                stLang = conn.prepareStatement(queryLang);
                stLang.setString(1, rs.getString("cod_proc"));
                rsLang = stLang.executeQuery();
                while (rsLang.next()) {
                    loopObj.put("tit_proc_" + rsLang.getString("cod_lang"), Utility.testoDaDb(rsLang.getString("tit_proc")));
                }
                Utility.chiusuraJdbc(rsLang);
                Utility.chiusuraJdbc(stLang);
                riga.add(loopObj);

            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("procedimenti", riga);
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
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;

        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            String query = "insert into procedimenti (cod_proc,cod_cud,flg_bollo,flg_tipo_proc,ter_eva,tip_aggregazione) "
                    + "values (?,?,?,?,?,? )";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("cod_proc"));
            st.setString(2, request.getParameter("cod_cud"));
            st.setString(3, request.getParameter("flg_bollo_hidden"));
            st.setString(4, request.getParameter("flg_tipo_proc_hidden"));
            st.setInt(5, (request.getParameter("ter_eva") == null || request.getParameter("ter_eva").equals("") ? 0 : Integer.parseInt(request.getParameter("ter_eva"))));
            st.setString(6, request.getParameter("tip_aggregazione"));
            st.executeUpdate();
            st.close();

            query = "insert into procedimenti_testi (cod_proc,cod_lang,tit_proc)  "
                    + "values (?,?,? )";
            Set<String> parmKey = request.getParameterMap().keySet();
            for (String key : parmKey) {
                String[] parSplit = key.split("tit_proc_");
                if (parSplit.length > 1) {
                    String cod_lang = parSplit[1];
                    if (cod_lang != null) {
                        st = conn.prepareStatement(query);
                        st.setString(1, request.getParameter("cod_proc"));
                        st.setString(2, cod_lang);
                        st.setString(3, Utility.testoADb(request.getParameter("tit_proc_" + cod_lang)));
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
            Modifica modificato = verificaTCR(request, conn);
            String query = "update procedimenti "
                    + "set cod_cud = ?,"
                    + "flg_bollo= ?, "
                    + "flg_tipo_proc= ?, "
                    + "ter_eva= ? "
                    + "where cod_proc =?";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("cod_cud"));
            st.setString(2, request.getParameter("flg_bollo_hidden"));
            st.setString(3, request.getParameter("flg_tipo_proc_hidden"));
            st.setInt(4, (request.getParameter("ter_eva") == null || request.getParameter("ter_eva").equals("") ? 0 : Integer.parseInt(request.getParameter("ter_eva"))));
            st.setString(5, request.getParameter("cod_proc"));
            st.executeUpdate();
            st.close();
            if ((Boolean) session.getAttribute("territorialitaNew")) {
//                if (utente.getTipAggregazione() != null) {
                    if (request.getParameter("tip_aggregazione") != null) {
                        query = "update procedimenti "
                                + "set tip_aggregazione = ?"
                                + "where cod_proc =? and tip_aggregazione is null";
                        st = conn.prepareStatement(query);
                        st.setString(1, request.getParameter("tip_aggregazione"));
                        st.setString(2, request.getParameter("cod_proc"));
                        st.executeUpdate();
                        st.close();
//                    }
                }
            }
            String querySelect = "select * from procedimenti_testi where cod_proc = ? and cod_lang = ?";
            String queryInsert = "insert into procedimenti_testi (cod_proc,cod_lang,tit_proc) values (?,?,? )";

            query = "update procedimenti_testi "
                    + "set tit_proc=? "
                    + "where cod_proc =? and cod_lang=?";

            Set<String> parmKey = request.getParameterMap().keySet();
            for (String key : parmKey) {
                String[] parSplit = key.split("tit_proc_");
                if (parSplit.length > 1) {
                    String cod_lang = parSplit[1];
                    if (cod_lang != null) {
                        stRead = conn.prepareStatement(querySelect);
                        stRead.setString(1, request.getParameter("cod_proc"));
                        stRead.setString(2, cod_lang);
                        rsRead = stRead.executeQuery();
                        if (rsRead.next()) {
                            st = conn.prepareStatement(query);
                            st.setString(1, Utility.testoADb(request.getParameter(key)));
                            st.setString(2, request.getParameter("cod_proc"));
                            st.setString(3, cod_lang);
                            st.executeUpdate();
                            Utility.chiusuraJdbc(rs);
                            Utility.chiusuraJdbc(st);
                        } else {
                            st = conn.prepareStatement(queryInsert);
                            st.setString(1, request.getParameter("cod_proc"));
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



            scriviTCR(modificato, request, conn);
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
            String query = "select count(*) righe from interventi where cod_proc=? ";
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
            controllo = controlloCancella(request.getParameter("cod_proc"), conn);
            if (controllo) {
                String query = "delete from procedimenti where cod_proc= ?";
                st = conn.prepareStatement(query);
                st.setString(1, request.getParameter("cod_proc"));
                st.executeUpdate();
                st.close();

                query = "delete from procedimenti_testi where cod_proc= ?";
                st = conn.prepareStatement(query);
                st.setString(1, request.getParameter("cod_proc"));
                st.executeUpdate();
                ret.put("success", "Cancellazione effettuata");
            } else {
                ret.put("failure", "Procedimento in uso");
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
        Modifica modificato = new Modifica();
        modificato.init();
        try {
            sql = "select a.cod_proc,a.cod_cud,b.tit_proc, a.ter_eva,a.flg_bollo,a.flg_tipo_proc from procedimenti a join procedimenti_testi b on a.cod_proc=b.cod_proc where a.cod_proc = ? and b.cod_lang='it'";
            st = conn.prepareStatement(sql);
            st.setString(1, request.getParameter("cod_proc"));
            rs = st.executeQuery();
            int terEva, terEvaIn;
            if (rs.next()) {
                if (rs.getObject("ter_eva") != null) {
                    terEva = rs.getInt("ter_eva");
                } else {
                    terEva = 0;
                }
                if (request.getParameter("ter_eva") == null || request.getParameter("ter_eva").equals("")) {
                    terEvaIn = 0;
                } else {
                    terEvaIn = Integer.parseInt(request.getParameter("ter_eva"));
                }
                if (!request.getParameter("tit_proc_it").equals(rs.getString("tit_proc"))
                        || !request.getParameter("cod_cud").equals(rs.getString("cod_cud"))
                        || !request.getParameter("flg_bollo_hidden").equals(rs.getString("flg_bollo"))
                        || !request.getParameter("flg_tipo_proc_hidden").equals(rs.getString("flg_tipo_proc"))
                        || terEvaIn != terEva) {
                    modificato.setModificaIntervento(true);
                }
            }
        } catch (SQLException e) {
            log.error("Errore verifica per notifica per TCR", e);
            throw e;
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
        return modificato;
    }

    public void scriviTCR(Modifica modificato, HttpServletRequest request, Connection conn) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        String sql = null;
        try {
            if (modificato.isModificaIntervento()) {
                sql = "update interventi set data_modifica = current_timestamp where cod_proc = ?";
                st = conn.prepareStatement(sql);
                st.setString(1, request.getParameter("cod_proc"));
                st.executeUpdate();
                Boolean aggiorna = Boolean.parseBoolean(Configuration.getConfigurationParameter("updateTCR").toLowerCase());
                if (aggiorna) {
                    sql = "update interventi set flg_pubblicazione='S' where cod_proc = ?";
                    st = conn.prepareStatement(sql);
                    st.setString(1, request.getParameter("cod_proc"));
                    st.executeUpdate();
                }
            }
        } catch (SQLException e) {
            log.error("Errore aggiornamento per notifica per TCR", e);
            throw e;
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
    }
}
