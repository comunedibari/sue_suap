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
public class Interventi {

    private static Logger log = LoggerFactory.getLogger(Interventi.class);

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
                    + "from interventi a "
                    + "join interventi_testi b "
                    + "on a.cod_int=b.cod_int "
                    + "join procedimenti c "
                    + "on a.cod_proc=c.cod_proc "
                    + "join procedimenti_testi d "
                    + "on a.cod_proc = d.cod_proc "
                    + "and d.cod_lang=b.cod_lang "
                    + "where b.cod_lang='it' and (convert(a.cod_int,CHAR) like ? or b.tit_int like ? or a.cod_proc like ? or d.tit_proc like ?)";
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

            query = "select a.cod_int cod_int, a.flg_obb flg_obb, a.cod_proc cod_proc, b.tit_int tit_int, a.ordinamento ordinamento, a.tip_aggregazione, "
                    + "d.tit_proc tit_proc, "
                    + "e.des_aggregazione "
                    + "from interventi a "
                    + "join interventi_testi b "
                    + "on a.cod_int=b.cod_int "
                    + "join procedimenti c "
                    + "on a.cod_proc=c.cod_proc "
                    + "join procedimenti_testi d "
                    + "on a.cod_proc = d.cod_proc "
                    + "and d.cod_lang=b.cod_lang "
                    + "left join tipi_aggregazione e "
                    + "on a.tip_aggregazione = e.tip_aggregazione "
                    + "where b.cod_lang='it' and (convert(a.cod_int,CHAR) like ? or b.tit_int like ? or a.cod_proc like ? or d.tit_proc like ?) ";
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


            String queryLang = "select tit_int, cod_lang from interventi_testi where cod_int = ?";

            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_int", rs.getInt("cod_int"));
                loopObj.put("tit_int_it", Utility.testoDaDb(rs.getString("tit_int")));
                loopObj.put("cod_proc", rs.getString("cod_proc"));
                loopObj.put("tit_proc", Utility.testoDaDb(rs.getString("tit_proc")));
                loopObj.put("flg_obb", Utility.testoDaDb(rs.getString("flg_obb")));
                loopObj.put("ordinamento", rs.getInt("ordinamento"));
                loopObj.put("tip_aggregazione", rs.getString("tip_aggregazione"));
                loopObj.put("des_aggregazione", rs.getString("des_aggregazione"));

                stLang = conn.prepareStatement(queryLang);
                stLang.setInt(1, rs.getInt("cod_int"));
                rsLang = stLang.executeQuery();
                while (rsLang.next()) {
                    loopObj.put("tit_int_" + rsLang.getString("cod_lang"), Utility.testoDaDb(rsLang.getString("tit_int")));
                }
                Utility.chiusuraJdbc(rsLang);
                Utility.chiusuraJdbc(stLang);

                riga.add(loopObj);

            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("interventi", riga);
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
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;

        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            String query = "insert into interventi (cod_int,cod_proc,flg_obb,ordinamento,tip_aggregazione) "
                    + "values (?,?,?,?,?)";
            st = conn.prepareStatement(query);
            st.setInt(1, Integer.parseInt(request.getParameter("cod_int")));
            st.setString(2, request.getParameter("cod_proc"));
            st.setString(3, request.getParameter("flg_obb_hidden"));
            st.setInt(4, Integer.parseInt(request.getParameter("ordinamento") == null || request.getParameter("ordinamento").trim().equals("") ? request.getParameter("cod_int") : request.getParameter("ordinamento")));
            st.setString(5, request.getParameter("tip_aggregazione"));
            st.executeUpdate();
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);

            query = "insert into interventi_testi (cod_int,cod_lang,tit_int) "
                    + "values (?,?,? )";
            Set<String> parmKey = request.getParameterMap().keySet();
            for (String key : parmKey) {
                String[] parSplit = key.split("tit_int_");
                if (parSplit.length > 1) {
                    String cod_lang = parSplit[1];
                    if (cod_lang != null) {
                        st = conn.prepareStatement(query);
                        st.setInt(1, Integer.parseInt(request.getParameter("cod_int")));
                        st.setString(2, cod_lang);
                        st.setString(3, Utility.testoADb(request.getParameter("tit_int_" + cod_lang)));
                        st.executeUpdate();
                        Utility.chiusuraJdbc(rs);
                        Utility.chiusuraJdbc(st);
                    }
                }
            }
            Modifica modificato = new Modifica();
            modificato.init();
            scriviTCR(modificato, request, conn);
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
            String query = "update interventi "
                    + "set cod_proc = ?,"
                    + "flg_obb = ?, "
                    + "ordinamento = ? "
                    + "where cod_int =?";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("cod_proc"));
            st.setString(2, request.getParameter("flg_obb_hidden"));
            st.setInt(3, Integer.parseInt(request.getParameter("ordinamento") == null || request.getParameter("ordinamento").trim().equals("") ? request.getParameter("cod_int") : request.getParameter("ordinamento")));
            st.setInt(4, Integer.parseInt(request.getParameter("cod_int")));
            st.executeUpdate();
            st.close();
            if ((Boolean) session.getAttribute("territorialitaNew")) {
//                if (utente.getTipAggregazione() != null) {
                    if (request.getParameter("tip_aggregazione") != null) {
                        query = "update interventi "
                                + "set tip_aggregazione = ?"
                                + "where cod_int =? and tip_aggregazione is null";
                        st = conn.prepareStatement(query);
                        st.setString(1, request.getParameter("tip_aggregazione"));
                        st.setInt(2, Integer.parseInt(request.getParameter("cod_int")));
                        st.executeUpdate();
                        st.close();
//                    }
                }
            }

            String querySelect = "select * from interventi_testi where cod_int = ? and cod_lang = ?";
            String queryInsert = "insert into interventi_testi (cod_int,cod_lang,tit_int) values (?,?,? )";

            query = "update interventi_testi "
                    + "set tit_int=? "
                    + "where cod_int =? and cod_lang=?";
            Set<String> parmKey = request.getParameterMap().keySet();
            for (String key : parmKey) {
                String[] parSplit = key.split("tit_int_");
                if (parSplit.length > 1) {
                    String cod_lang = parSplit[1];
                    if (cod_lang != null) {
                        stRead = conn.prepareStatement(querySelect);
                        stRead.setInt(1, Integer.parseInt(request.getParameter("cod_int")));
                        stRead.setString(2, cod_lang);
                        rsRead = stRead.executeQuery();
                        if (rsRead.next()) {
                            st = conn.prepareStatement(query);
                            st.setString(1, Utility.testoADb(request.getParameter(key)));
                            st.setInt(2, Integer.parseInt(request.getParameter("cod_int")));
                            st.setString(3, cod_lang);
                            st.executeUpdate();

                        } else {
                            st = conn.prepareStatement(queryInsert);
                            st.setInt(1, Integer.parseInt(request.getParameter("cod_int")));
                            st.setString(2, cod_lang);
                            st.setString(3, Utility.testoADb(request.getParameter(key)));
                            st.execute();

                        }
                        Utility.chiusuraJdbc(rs);
                        Utility.chiusuraJdbc(st);
                        Utility.chiusuraJdbc(rsRead);
                        Utility.chiusuraJdbc(stRead);
                    }
                }
            }
            scriviTCR(modificato, request, conn);
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
            String query = "select count(*) righe from interventi_comuni where cod_int=? "
                    + "union "
                    + "select count(*) righe from interventi_collegati where cod_int=? "
                    + "union "
                    + "select count(*) righe from interventi_collegati where cod_int_padre=? "
                    + "union "
                    + "select count(*) righe from interventi_seq where cod_int_sel=? "
                    + "union "
                    + "select count(*) righe from interventi_seq where cod_int_prec=? "
                    + "union "
                    + "select count(*) righe from norme_interventi where cod_int=? "
                    + "union "
                    + "select count(*) righe from condizioni_di_attivazione where cod_int=? "
                    + "union "
                    + "select count(*) righe from allegati where cod_int=? "
                    + "union "
                    + "select count(*) righe from utenti_interventi where cod_int=? ";
            st = conn.prepareStatement(query);
            st.setInt(1, Integer.parseInt(key));
            st.setInt(2, Integer.parseInt(key));
            st.setInt(3, Integer.parseInt(key));
            st.setInt(4, Integer.parseInt(key));
            st.setInt(5, Integer.parseInt(key));
            st.setInt(6, Integer.parseInt(key));
            st.setInt(7, Integer.parseInt(key));
            st.setInt(8, Integer.parseInt(key));
            st.setInt(9, Integer.parseInt(key));

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
            controllo = controlloCancella(request.getParameter("cod_int"), conn);
            if (controllo) {
                String query = "delete from interventi where cod_int= ?";
                st = conn.prepareStatement(query);
                st.setInt(1, Integer.parseInt(request.getParameter("cod_int")));
                st.executeUpdate();
                st.close();

                query = "delete from interventi_testi where cod_int= ?";
                st = conn.prepareStatement(query);
                st.setInt(1, Integer.parseInt(request.getParameter("cod_int")));
                st.executeUpdate();
                ret.put("success", "Cancellazione effettuata");
            } else {
                ret.put("failure", "Intervento in uso");
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
            sql = "select a.cod_int,a.cod_proc from interventi a  where a.cod_int = ? ";
            st = conn.prepareStatement(sql);
            st.setInt(1, Integer.parseInt(request.getParameter("cod_int")));
            rs = st.executeQuery();
            if (rs.next()) {
                if (!request.getParameter("cod_proc").equals(rs.getString("cod_proc"))) {
                    modificato.setModificaIntervento(true);
                }
            }
            if (!modificato.isModificaIntervento()) {
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);
                sql = "select tit_int, cod_lang from interventi_testi where cod_int = ?";
                st = conn.prepareStatement(sql);
                st.setInt(1, Integer.parseInt(request.getParameter("cod_int")));
                rs = st.executeQuery();
                while (rs.next()) {

                    if (request.getParameter("tit_int_" + rs.getString("cod_lang")) != null
                            && !request.getParameter("tit_int_" + rs.getString("cod_lang")).equals(rs.getString("tit_int") + "_" + rs.getString("cod_lang"))) {
                        modificato.setModificaIntervento(true);
                    }
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
        Boolean ret = false;
        String sql = null;
        try {
            if (modificato.isModificaIntervento()) {
                sql = "update interventi set data_modifica=current_timestamp where cod_int = ?";
                st = conn.prepareStatement(sql);
                st.setInt(1, Integer.parseInt(request.getParameter("cod_int")));
                st.executeUpdate();
                Boolean aggiorna = Boolean.parseBoolean(Configuration.getConfigurationParameter("updateTCR").toLowerCase());
                if (aggiorna) {
                    sql = "update interventi set flg_pubblicazione= 'S' where cod_int = ?";
                    st = conn.prepareStatement(sql);
                    st.setInt(1, Integer.parseInt(request.getParameter("cod_int")));
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
