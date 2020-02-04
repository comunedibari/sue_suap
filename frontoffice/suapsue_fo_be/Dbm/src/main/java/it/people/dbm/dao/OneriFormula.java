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
public class OneriFormula {

    private List<String> listaCampiInFormula;
    private static Logger log = LoggerFactory.getLogger(OneriFormula.class);

    public JSONObject action(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
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
                    + "from oneri_formula "
                    + "where (cod_onere_formula like ? or des_formula like ? or formula like ?) ";
            if (!(Boolean) session.getAttribute("territorialitaNew")) {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and tip_aggregazione=? ";
                }
            }
            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            st.setString(3, "%" + param + "%");
            if (!(Boolean) session.getAttribute("territorialitaNew")) {
                if (utente.getTipAggregazione() != null) {
                    st.setString(4, utente.getTipAggregazione());
                }
            }
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);

            query = "select a.cod_onere_formula,a.des_formula,a.formula, a.tip_aggregazione, b.des_aggregazione "
                    + "from oneri_formula a "
                    + "left join tipi_aggregazione b "
                    + "on a.tip_aggregazione=b.tip_aggregazione "
                    + "where (a.cod_onere_formula like ? or a.des_formula like ? or a.formula like ?) ";
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
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_onere_formula", rs.getString("cod_onere_formula"));
                loopObj.put("des_formula", Utility.testoDaDb(rs.getString("des_formula")));
                loopObj.put("formula", Utility.testoDaDb(rs.getString("formula")));
                loopObj.put("tip_aggregazione", rs.getString("tip_aggregazione"));
                loopObj.put("des_aggregazione", rs.getString("des_aggregazione"));
                riga.add(loopObj);
            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("oneri_formula", riga);
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
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
            String query = "select count(*) numero, 'oneri_gerarchia' tabella from oneri_gerarchia where cod_onere_formula=? ";
            st = conn.prepareStatement(query);
            st.setString(1, key);

            rs = st.executeQuery();
            while (rs.next()) {
                righe = righe + rs.getInt("numero");
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
            controllo = controlloCancella(request.getParameter("cod_onere_formula"), conn);
            if (controllo) {
                String query = "delete from oneri_formula where cod_onere_formula = ?";
                st = conn.prepareStatement(query);
                st.setString(1, request.getParameter("cod_onere_formula"));
                st.executeUpdate();
                st.close();

                query = "delete from oneri_campi_formula where cod_onere_formula= ?";
                st = conn.prepareStatement(query);
                st.setString(1, request.getParameter("cod_onere_formula"));
                st.executeUpdate();
                ret.put("success", "Cancellazione effettuata");
            } else {
                ret.put("failure", "Formula oneri in uso");
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

    public String cercaCampiInFormula(String formula) throws Exception {
        String newFormula = "";
        int point_start = formula.indexOf("$");
        String ret = "";
        if (point_start > -1) {
            int point_end = formula.indexOf("$", point_start + 1);
            if (point_end > -1) {
                listaCampiInFormula.add(formula.substring(point_start + 1, point_end));
                newFormula = formula.substring(point_end + 1);
                ret = cercaCampiInFormula(newFormula);
            } else {
                ret = "Formato formula errato";
            }
        }
        return ret;
    }

    public String controlloCongruenza(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        String formula = request.getParameter("formula") + " ";
        listaCampiInFormula = new ArrayList<String>();
        String ret = cercaCampiInFormula(formula);
        boolean trovato = false;
        if (ret.equals("")) {
            if (formula.indexOf("#ValOnere#") == -1) {
                ret = "Identificativo '#ValOnere#' non presente nella formula";
            } else {
                List<String> lcf = (List<String>) session.getAttribute("oneri_campi_formula");
                if (lcf != null) {
                    for (int i = 0; i < lcf.size(); i++) {
                        trovato = false;
                        for (int y = 0; y < listaCampiInFormula.size(); y++) {
                            if (listaCampiInFormula.get(y).equals(lcf.get(i))) {
                                trovato = true;
                                break;
                            }
                        }
                        if (!trovato) {
                            ret = ret + "campo formula (" + lcf.get(i) + ") Non presente nella formula.\n";
                        }
                    }
                }
                for (int i = 0; i < listaCampiInFormula.size(); i++) {
                    trovato = false;
                    if (lcf != null) {
                        for (int y = 0; y < lcf.size(); y++) {
                            if (lcf != null) {
                                if (lcf.get(y).equals(listaCampiInFormula.get(i))) {
                                    trovato = true;
                                    break;
                                }
                            }
                        }
                    }
                    if (!trovato) {
                        ret = ret + "campo formula (" + listaCampiInFormula.get(i) + ") Non presente nella lista campi.\n";
                    }
                }
            }
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
        int righe = 0;
        String controllo = "";
        String query;
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            controllo = controlloCongruenza(request);
            if (controllo.equals("")) {

                query = "insert into oneri_formula (cod_onere_formula,des_formula,formula,tip_aggregazione) "
                        + "values (?,?,?,?)";
                st = conn.prepareStatement(query);
                st.setString(1, request.getParameter("cod_onere_formula"));
                st.setString(2, Utility.testoADb(request.getParameter("des_formula")));
                st.setString(3, request.getParameter("formula"));
                st.setString(4, request.getParameter("tip_aggregazione"));
                st.executeUpdate();
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);

                query = "delete from oneri_campi_formula where cod_onere_formula=?";
                st = conn.prepareStatement(query);
                st.setString(1, request.getParameter("cod_onere_formula"));
                st.executeUpdate();
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);

                query = "insert into oneri_campi_formula (cod_onere_formula,cod_onere_campo) "
                        + "values (?,?)";
                List<String> lcf = (List<String>) session.getAttribute("oneri_campi_formula");
                if (lcf != null) {
                    for (int i = 0; i < lcf.size(); i++) {
                        st = conn.prepareStatement(query);
                        st.setString(1, request.getParameter("cod_onere_formula"));
                        st.setString(2, lcf.get(i));
                        st.executeUpdate();
                        Utility.chiusuraJdbc(rs);
                        Utility.chiusuraJdbc(st);
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
        Utente utente = (Utente) session.getAttribute("utente");
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        String controllo = "";
        String query;
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            controllo = controlloCongruenza(request);
            if (controllo.equals("")) {

                query = "update oneri_formula "
                        + "set des_formula=?, "
                        + "formula=? "
                        + "where cod_onere_formula=?";
                st = conn.prepareStatement(query);
                st.setString(1, Utility.testoADb(request.getParameter("des_formula")));
                st.setString(2, request.getParameter("formula"));
                st.setString(3, request.getParameter("cod_onere_formula"));
                st.executeUpdate();
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);
                if ((Boolean) session.getAttribute("territorialitaNew")) {
//                    if (utente.getTipAggregazione() != null) {
                        if (request.getParameter("tip_aggregazione") != null) {
                            query = "update oneri_formula "
                                    + "set tip_aggregazione = ?"
                                    + "where cod_onere_formula =? and tip_aggregazione is null";
                            st = conn.prepareStatement(query);
                            st.setString(1, request.getParameter("tip_aggregazione"));
                            st.setString(2, request.getParameter("cod_onere_formula"));
                            st.executeUpdate();
                            Utility.chiusuraJdbc(rs);
                            Utility.chiusuraJdbc(st);
                        }
//                    }
                }
                query = "delete from oneri_campi_formula where cod_onere_formula=?";
                st = conn.prepareStatement(query);
                st.setString(1, request.getParameter("cod_onere_formula"));
                st.executeUpdate();
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);

                query = "insert into oneri_campi_formula (cod_onere_formula,cod_onere_campo) "
                        + "values (?,?)";
                List<String> lcf = (List<String>) session.getAttribute("oneri_campi_formula");
                for (int i = 0; i < lcf.size(); i++) {
                    st = conn.prepareStatement(query);
                    st.setString(1, request.getParameter("cod_onere_formula"));
                    st.setString(2, lcf.get(i));
                    st.executeUpdate();
                    Utility.chiusuraJdbc(rs);
                    Utility.chiusuraJdbc(st);
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
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject pulisciOnereCampoFormulaSession(HttpServletRequest request) throws Exception {
        JSONObject ret = null;

        try {
            HttpSession session = request.getSession();
            if (session.getAttribute("oneri_campi_formula") != null) {
                session.removeAttribute("oneri_campi_formula");
            }
            ret = new JSONObject();

            ret.put("success", "Aggiornamento effettuato");
        } catch (Exception e) {
            ret.put("failure", e.getMessage());
        } finally {
        }
        return ret;
    }

    public JSONObject aggiungiOnereCampoFormulaSession(HttpServletRequest request) throws Exception {
        JSONObject ret = null;

        try {
            HttpSession session = request.getSession();
            if (session.getAttribute("oneri_campi_formula") != null) {
                List<String> lcf = (List<String>) session.getAttribute("oneri_campi_formula");
                lcf.remove(request.getParameter("cod_onere_campo"));
                lcf.add(request.getParameter("cod_onere_campo"));
                session.setAttribute("oneri_campi_formula", lcf);
            } else {
                session.setAttribute("oneri_campi_formula", new ArrayList<String>());
            }
            ret = new JSONObject();
            ret.put("success", "Aggiornamento effettuato");
        } catch (Exception e) {
            ret.put("failure", e.getMessage());
        } finally {
        }
        return ret;
    }

    public JSONObject cancellaOnereCampoFormulaSession(HttpServletRequest request) throws Exception {
        JSONObject ret = null;

        try {
            HttpSession session = request.getSession();
            if (session.getAttribute("oneri_campi_formula") != null) {
                List<String> lcf = (List<String>) session.getAttribute("oneri_campi_formula");
                lcf.remove(request.getParameter("cod_onere_campo"));
                session.setAttribute("oneri_campi_formula", lcf);
            } else {
                session.setAttribute("oneri_campi_formula", new ArrayList<String>());
            }
            ret = new JSONObject();
            ret.put("success", "Aggiornamento effettuato");
        } catch (Exception e) {
            ret.put("failure", e.getMessage());
        } finally {
        }
        return ret;
    }

    public JSONObject actionOneriCampiFormula(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        HttpSession session = request.getSession();
        List<String> lcf = null;
        String query = null;
        try {
            conn = Utility.getConnection();
            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            if (request.getParameter("session") != null && request.getParameter("session").equals("yes")) {
                if (session.getAttribute("oneri_campi_formula") != null) {
                    lcf = (List<String>) session.getAttribute("oneri_campi_formula");
                    query = "select a.cod_onere_campo,b.testo_campo "
                            + "from oneri_campi a "
                            + "join oneri_campi_testi b "
                            + "on a.cod_onere_campo=b.cod_onere_campo "
                            + "where b.cod_lang='it' and a.cod_onere_campo=?";
                    for (int i = 0; i < lcf.size(); i++) {
                        st = conn.prepareStatement(query);
                        st.setString(1, lcf.get(i));
                        rs = st.executeQuery();
                        while (rs.next()) {
                            loopObj = new JSONObject();
                            loopObj.put("cod_onere_campo", rs.getString("cod_onere_campo"));
                            loopObj.put("testo_campo", Utility.testoDaDb(rs.getString("testo_campo")));
                            riga.add(loopObj);
                        }
                        Utility.chiusuraJdbc(rs);
                        Utility.chiusuraJdbc(st);
                    }
                }
            } else {
                lcf = new ArrayList<String>();

                query = "select a.cod_onere_campo,c.testo_campo "
                        + "from oneri_campi_formula a "
                        + "join oneri_campi b "
                        + "on a.cod_onere_campo=b.cod_onere_campo "
                        + "join oneri_campi_testi c "
                        + "on a.cod_onere_campo=c.cod_onere_campo "
                        + "where a.cod_onere_formula=? "
                        + "and c.cod_lang='it' order by a.cod_onere_campo";
                st = conn.prepareStatement(query);
                st.setString(1, request.getParameter("cod_onere_formula"));
                rs = st.executeQuery();
                while (rs.next()) {
                    loopObj = new JSONObject();
                    loopObj.put("cod_onere_campo", rs.getString("cod_onere_campo"));
                    loopObj.put("testo_campo", Utility.testoDaDb(rs.getString("testo_campo")));
                    riga.add(loopObj);
                    lcf.add(rs.getString("cod_onere_campo"));
                }
                session.setAttribute("oneri_campi_formula", lcf);
            }

            ret = new JSONObject();
            ret.put("oneri_campi_formula", riga);
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }
}
