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
import java.util.ArrayList;
import java.util.List;
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
public class OneriCampi {

    private static Logger log = LoggerFactory.getLogger(OneriCampi.class);

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
                    + "from oneri_campi a "
                    + "join oneri_campi_testi b "
                    + "on a.cod_onere_campo=b.cod_onere_campo "
                    + "where b.cod_lang='it' and (a.cod_onere_campo like ? or b.testo_campo like ? ) ";
            if (!(Boolean) session.getAttribute("territorialitaNew")) {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and a.tip_aggregazione=? ";
                }
            }
            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            if (!(Boolean) session.getAttribute("territorialitaNew")) {
                if (utente.getTipAggregazione() != null) {
                    st.setString(3, utente.getTipAggregazione());
                }
            }
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);

            query = "select a.cod_onere_campo,a.tp_campo,a.lng_campo,a.lng_dec,b.testo_campo, a.tip_aggregazione, e.des_aggregazione, "
                    + "a.accetta_valore_zero "
                    + "from oneri_campi a "
                    + "join oneri_campi_testi b "
                    + "on a.cod_onere_campo=b.cod_onere_campo "
                    + "left join tipi_aggregazione e "
                    + "on a.tip_aggregazione = e.tip_aggregazione "
                    + "where b.cod_lang='it' and (a.cod_onere_campo like ? or b.testo_campo like ? ) ";
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
            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            String queryLang = "select testo_campo, cod_lang from oneri_campi_testi where cod_onere_campo = ?";
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_onere_campo", rs.getString("cod_onere_campo"));
                loopObj.put("testo_campo", Utility.testoDaDb(rs.getString("testo_campo")));
                loopObj.put("tp_campo", rs.getString("tp_campo"));
                loopObj.put("lng_campo", rs.getInt("lng_campo"));
                loopObj.put("lng_dec", rs.getInt("lng_dec"));
                loopObj.put("tip_aggregazione", rs.getString("tip_aggregazione"));
                loopObj.put("des_aggregazione", rs.getString("des_aggregazione"));
                loopObj.put("accetta_valore_zero", Utility.testoDaDb(rs.getString("accetta_valore_zero")).equalsIgnoreCase("s") ? "true" : "false");

                stLang = conn.prepareStatement(queryLang);
                stLang.setString(1, rs.getString("cod_onere_campo"));
                rsLang = stLang.executeQuery();
                while (rsLang.next()) {
                    loopObj.put("testo_campo_" + rsLang.getString("cod_lang"), Utility.testoDaDb(rsLang.getString("testo_campo")));
                }
                Utility.chiusuraJdbc(rsLang);
                Utility.chiusuraJdbc(stLang);
                riga.add(loopObj);

            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("oneri_campi", riga);
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(rsLang);
            Utility.chiusuraJdbc(stLang);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject pulisciOnereCampoSelectSession(HttpServletRequest request) throws Exception {
        JSONObject ret = null;

        try {
            HttpSession session = request.getSession();
            if (session.getAttribute("oneri_campi_select_testi") != null) {
                session.removeAttribute("oneri_campi_select_testi");
            }
            ret = new JSONObject();

            ret.put("success", "Aggiornamento effettuato");
        } catch (Exception e) {
            ret.put("failure", e.getMessage());
        } finally {
        }
        return ret;
    }

    public JSONObject actionOneriCampiSelectTesti(HttpServletRequest request) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        PreparedStatement stLang = null;
        ResultSet rsLang = null;
        JSONObject ret = null;
        int righe = 0;
        HttpSession session = request.getSession();
        List<JSONObject> lcf = null;
        String query = null;
        try {
            conn = Utility.getConnection();
            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            if (request.getParameter("session") != null && request.getParameter("session").equals("yes")) {
                if (session.getAttribute("oneri_campi_select_testi") != null) {
                    lcf = (List<JSONObject>) session.getAttribute("oneri_campi_select_testi");
                    for (int i = 0; i < lcf.size(); i++) {
                        riga.add(lcf.get(i));
                    }
                }
            } else {
                lcf = new ArrayList<JSONObject>();

                query = "select val_select, des_val_select "
                        + "from oneri_campi_select_testi "
                        + "where cod_onere_campo=? "
                        + "and cod_lang='it' order by val_select";
                st = conn.prepareStatement(query);
                st.setString(1, request.getParameter("cod_onere_campo"));
                rs = st.executeQuery();
                String queryLang = "select des_val_select, cod_lang from oneri_campi_select_testi where cod_onere_campo = ? and val_select=?";
                while (rs.next()) {
                    loopObj = new JSONObject();
                    loopObj.put("val_select", rs.getString("val_select"));
                    stLang = conn.prepareStatement(queryLang);
                    stLang.setString(1, request.getParameter("cod_onere_campo"));
                    stLang.setString(2, rs.getString("val_select"));
                    rsLang = stLang.executeQuery();
                    while (rsLang.next()) {
                        loopObj.put("des_val_select_" + rsLang.getString("cod_lang"), Utility.testoDaDb(rsLang.getString("des_val_select")));
                    }
                    Utility.chiusuraJdbc(rsLang);
                    Utility.chiusuraJdbc(stLang);
                    riga.add(loopObj);
                    lcf.add(loopObj);
                }
                session.setAttribute("oneri_campi_select_testi", lcf);
            }

            ret = new JSONObject();
            ret.put("oneri_campi_select_testi", riga);
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(rsLang);
            Utility.chiusuraJdbc(stLang);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject aggiungiOnereCampoSelectSession(HttpServletRequest request) throws Exception {
        JSONObject ret = null;

        try {
            HttpSession session = request.getSession();
            if (session.getAttribute("oneri_campi_select_testi") != null) {
                List<JSONObject> lcf = (List<JSONObject>) session.getAttribute("oneri_campi_select_testi");
                JSONObject ele = new JSONObject();
                ele.put("val_select", request.getParameter("val_select"));
                Set<String> parmKey = request.getParameterMap().keySet();
                for (String key : parmKey) {
                    String[] parSplit = key.split("des_val_select_");
                    if (parSplit.length > 1 && key.contains("des_val_select_")) {
                        String cod_lang = parSplit[1];
                        if (cod_lang != null) {
                            ele.put("des_val_select_" + cod_lang, request.getParameter("des_val_select_" + cod_lang));
                        }
                    }
                }

                lcf.remove(ele);
                lcf.add(ele);
                session.setAttribute("oneri_campi_select_testi", lcf);
            } else {
                session.setAttribute("oneri_campi_select_testi", new ArrayList<JSONObject>());
            }
            ret = new JSONObject();
            ret.put("success", "Aggiornamento effettuato");
        } catch (Exception e) {
            ret.put("failure", e.getMessage());
        } finally {
        }
        return ret;
    }

    public JSONObject cancellaOnereCampoSelectSession(HttpServletRequest request) throws Exception {
        JSONObject ret = null;
        JSONObject jo = null;
        try {
            HttpSession session = request.getSession();
            if (session.getAttribute("oneri_campi_select_testi") != null) {
                List<JSONObject> lcf = (List<JSONObject>) session.getAttribute("oneri_campi_select_testi");
                for (int i = 0; i < lcf.size(); i++) {
                    jo = lcf.get(i);
                    if (jo.get("val_select").equals(request.getParameter("val_select"))) {
                        lcf.remove(i);
                        break;
                    }
                }
                session.setAttribute("oneri_campi_select_testi", lcf);
            } else {
                session.setAttribute("oneri_campi_select_testi", new ArrayList<JSONObject>());
            }
            ret = new JSONObject();
            ret.put("success", "Aggiornamento effettuato");
        } catch (Exception e) {
            ret.put("failure", e.getMessage());
        } finally {
        }
        return ret;
    }

    public String controlloCongruenzaOneriCampi(HttpServletRequest request, Connection conn) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        String controllo = "";
        int lngd = 0;
        try {
            if (!request.getParameter("lng_dec").equals("")) {
                lngd = Integer.parseInt(request.getParameter("lng_dec"));
            }
            if (!request.getParameter("tp_campo_hidden").equals("L")) {
                if (Integer.parseInt(request.getParameter("lng_campo")) <= 1) {
                    controllo = "lunghezza campo errata";
                }
                if (request.getParameter("tp_campo_hidden").equals("D")) {
                    if (lngd == 0) {
                        controllo = controllo + "\nnumero decimali errato";
                    }
                    if (Integer.parseInt(request.getParameter("lng_campo")) <= lngd) {
                        controllo = controllo + "\nnumero decimali errato";
                    }
                }
                if (request.getParameter("tp_campo_hidden").equals("I")) {
                    if (lngd > 0) {
                        controllo = controllo + "\nnumero decimali errato";
                    }
                }
            } else {
                if (lngd > 0) {
                    controllo = controllo + "\nnumero decimali errato";
                }
            }

        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
        return controllo;
    }

    public JSONObject inserisci(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        String controllo = "";
        String query;
        JSONObject jo = null;
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            controllo = controlloCongruenzaOneriCampi(request, conn);
            if (controllo.equals("")) {
                query = "insert into oneri_campi (cod_onere_campo,tp_campo,lng_campo,lng_dec,tip_aggregazione,accetta_valore_zero) "
                        + "values (?,?,?,?,?,?)";
                st = conn.prepareStatement(query);
                st.setString(1, request.getParameter("cod_onere_campo"));
                st.setString(2, request.getParameter("tp_campo_hidden"));
                st.setInt(3, Integer.parseInt(request.getParameter("lng_campo")));
                if (request.getParameter("lng_dec") == null || request.getParameter("lng_dec").equals("")) {
                    st.setNull(4, java.sql.Types.INTEGER);
                } else {
                    st.setInt(4, Integer.parseInt(request.getParameter("lng_dec")));
                }
                st.setString(5, request.getParameter("tip_aggregazione"));
                st.setString(6, (request.getParameter("accetta_valore_zero") == null
                        || request.getParameter("accetta_valore_zero").equals("") ? "N"
                        : "S"));
                st.executeUpdate();
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);

                query = "insert into oneri_campi_testi (cod_onere_campo,testo_campo,cod_lang) "
                        + "values (?,?,?)";
                Set<String> parmKey = request.getParameterMap().keySet();
                for (String key : parmKey) {
                    String[] parSplit = key.split("testo_campo_");
                    if (parSplit.length > 1 && key.contains("testo_campo_")) {
                        String cod_lang = parSplit[1];
                        if (cod_lang != null) {
                            st = conn.prepareStatement(query);
                            st.setString(1, request.getParameter("cod_onere_campo"));
                            st.setString(2, Utility.testoADb(request.getParameter("testo_campo_" + cod_lang)));
                            st.setString(3, cod_lang);
                            st.executeUpdate();
                            Utility.chiusuraJdbc(rs);
                            Utility.chiusuraJdbc(st);
                        }
                    }
                }

                query = "delete from oneri_campi_select_testi where cod_onere_campo=?";
                st = conn.prepareStatement(query);
                st.setString(1, request.getParameter("cod_onere_campo"));
                st.executeUpdate();
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);
                if (request.getParameter("tp_campo_hidden").equals("L")) {
                    query = "insert into oneri_campi_select_testi (cod_onere_campo,val_select,des_val_select,cod_lang) "
                            + "values (?,?,?,?)";
                    List<JSONObject> lcf = (List<JSONObject>) session.getAttribute("oneri_campi_select_testi");
                    for (int i = 0; i < lcf.size(); i++) {
                        jo = lcf.get(i);

                        parmKey = jo.keySet();
                        for (String key : parmKey) {
                            String[] parSplit = key.split("des_val_select_");
                            if (parSplit.length > 1 && key.contains("des_val_select_")) {
                                String cod_lang = parSplit[1];
                                if (cod_lang != null) {
                                    st = conn.prepareStatement(query);
                                    st.setString(1, request.getParameter("cod_onere_campo"));
                                    st.setString(2, (String) jo.get("val_select"));
                                    st.setString(3, Utility.testoADb((String) jo.get("des_val_select_" + cod_lang)));
                                    st.setString(4, cod_lang);
                                    st.executeUpdate();
                                    Utility.chiusuraJdbc(rs);
                                    Utility.chiusuraJdbc(st);
                                }
                            }
                        }

                    }
                }
                ret.put("success", "Aggiornamento effettuato");
            } else {
                ret.put("failure", controllo);
            }
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
        HttpSession session = request.getSession();
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        PreparedStatement stRead = null;
        ResultSet rsRead = null;
        JSONObject ret = null;
        String query;
        JSONObject jo = null;
        String controllo = "";
        Utente utente = (Utente) session.getAttribute("utente");
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            controllo = controlloCongruenzaOneriCampi(request, conn);
            if (controllo.equals("")) {
                query = "update oneri_campi "
                        + "set tp_campo=?, "
                        + "lng_campo=?, "
                        + "lng_dec=?, "
                        + "accetta_valore_zero=? "
                        + "where cod_onere_campo=?";
                st = conn.prepareStatement(query);
                st.setString(1, request.getParameter("tp_campo_hidden"));
                st.setInt(2, Integer.parseInt(request.getParameter("lng_campo")));
                if (request.getParameter("lng_dec") == null || request.getParameter("lng_dec").equals("")) {
                    st.setNull(3, java.sql.Types.INTEGER);
                } else {
                    st.setInt(3, Integer.parseInt(request.getParameter("lng_dec")));
                }
                st.setString(4, (request.getParameter("accetta_valore_zero") == null
                        || request.getParameter("accetta_valore_zero").equals("") ? "N"
                        : "S"));
                st.setString(5, request.getParameter("cod_onere_campo"));
                st.executeUpdate();
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);
                if ((Boolean) session.getAttribute("territorialitaNew")) {
//                    if (utente.getTipAggregazione() != null) {
                        if (request.getParameter("tip_aggregazione") != null) {
                            query = "update oneri_campi "
                                    + "set tip_aggregazione = ?"
                                    + "where cod_onere_campo =? and tip_aggregazione is null";
                            st = conn.prepareStatement(query);
                            st.setString(1, request.getParameter("tip_aggregazione"));
                            st.setString(2, request.getParameter("cod_onere_campo"));
                            st.executeUpdate();
                            Utility.chiusuraJdbc(rs);
                            Utility.chiusuraJdbc(st);
                        }
//                    }
                }
                String querySelect = "select * from oneri_campi_testi where cod_onere_campo = ? and cod_lang = ?";
                String queryInsert = "insert into oneri_campi_testi (cod_onere_campo,cod_lang,testo_campo) values (?,?,? )";
                query = "update oneri_campi_testi "
                        + "set testo_campo=? "
                        + "where cod_onere_campo=?  and cod_lang=?";
                Set<String> parmKey = request.getParameterMap().keySet();
                for (String key : parmKey) {
                    String[] parSplit = key.split("testo_campo_");
                    if (parSplit.length > 1 && key.contains("testo_campo_")) {
                        String cod_lang = parSplit[1];
                        if (cod_lang != null) {
                            stRead = conn.prepareStatement(querySelect);
                            stRead.setString(1, request.getParameter("cod_onere_campo"));
                            stRead.setString(2, cod_lang);
                            rsRead = stRead.executeQuery();
                            if (rsRead.next()) {
                                st = conn.prepareStatement(query);
                                st.setString(1, Utility.testoADb(request.getParameter(key)));
                                st.setString(2, request.getParameter("cod_onere_campo"));
                                st.setString(3, cod_lang);
                                st.executeUpdate();

                            } else {
                                st = conn.prepareStatement(queryInsert);
                                st.setString(1, request.getParameter("cod_onere_campo"));
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

                query = "delete from oneri_campi_select_testi where cod_onere_campo=?";
                st = conn.prepareStatement(query);
                st.setString(1, request.getParameter("cod_onere_campo"));
                st.executeUpdate();
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);
                if (request.getParameter("tp_campo_hidden").equals("L")) {
                    query = "insert into oneri_campi_select_testi (cod_onere_campo,val_select,des_val_select,cod_lang) "
                            + "values (?,?,?,?)";
                    List<JSONObject> lcf = (List<JSONObject>) session.getAttribute("oneri_campi_select_testi");
                    for (int i = 0; i < lcf.size(); i++) {
                        jo = lcf.get(i);

                        parmKey = jo.keySet();
                        for (String key : parmKey) {
                            String[] parSplit = key.split("des_val_select_");
                            if (parSplit.length > 1 && key.contains("des_val_select_")) {
                                String cod_lang = parSplit[1];
                                if (cod_lang != null) {
                                    st = conn.prepareStatement(query);
                                    st.setString(1, request.getParameter("cod_onere_campo"));
                                    st.setString(2, (String) jo.get("val_select"));
                                    st.setString(3, Utility.testoADb((String) jo.get("des_val_select_" + cod_lang)));
                                    st.setString(4, cod_lang);
                                    st.executeUpdate();
                                    Utility.chiusuraJdbc(rs);
                                    Utility.chiusuraJdbc(st);
                                }
                            }
                        }

                    }
                }
                ret.put("success", "Aggiornamento effettuato");
            } else {
                ret.put("failure", controllo);
            }
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
            String query = "select count(*) righe, 'oneri_campi_formula' tabella from oneri_campi_formula where cod_onere_campo=?";
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
        HttpSession session = request.getSession();
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        boolean controllo = true;
        String query;
        JSONObject jo = null;
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            controllo = controlloCancella(request.getParameter("cod_onere_campo"), conn);
            if (controllo) {
                query = "delete from oneri_campi where cod_onere_campo=?";
                st = conn.prepareStatement(query);
                st.setString(1, request.getParameter("cod_onere_campo"));
                st.executeUpdate();
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);

                query = "delete from oneri_campi_testi where cod_onere_campo=?";
                st = conn.prepareStatement(query);
                st.setString(1, request.getParameter("cod_onere_campo"));
                st.executeUpdate();
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);

                query = "delete from oneri_campi_select_testi where cod_onere_campo=?";
                st = conn.prepareStatement(query);
                st.setString(1, request.getParameter("cod_onere_campo"));
                st.executeUpdate();

                ret.put("success", "Cancellazione effettuato");
            } else {
                ret.put("failure", "Campo oneri in uso");
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
