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
package it.people.dbm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
public class Utenti {

    private static Logger log = LoggerFactory.getLogger(Utenti.class);
    List<String> lu;

    public int controlloCongruenzaUtentiLoop(String codUtente, Connection conn) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        int ret = 0;
        try {
            for (int i = 0; i < lu.size(); i++) {
                if (lu.get(i).equals(codUtente)) {
                    ret = 1;
                    break;
                }
            }
            if (ret == 0) {
                String query = "select cod_utente_padre from utenti where cod_utente = ?";
                st = conn.prepareStatement(query);
                st.setString(1, codUtente);
                rs = st.executeQuery();
                if (rs.next()) {
                    if (rs.getString("cod_utente_padre") != null && !rs.getString("cod_utente_padre").equals("")) {
                        ret = controlloCongruenzaUtentiLoop(rs.getString("cod_utente_padre"), conn);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Errore controllo congruenza utenti ", e);
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
        return ret;
    }

    public int controlloCongruenzaUtenti(String codUtente, String codUtentePadre, Connection conn) throws Exception {
        lu = new ArrayList<String>();
        lu.add(codUtente);
        int ret = 0;
        if (codUtentePadre != null && !codUtente.equals("")) {
            ret = controlloCongruenzaUtentiLoop(codUtentePadre, conn);
        }
        return ret;
    }

    public JSONObject action(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        try {
            conn = Utility.getConnection();
            String offset = request.getParameter("start");
            String size = request.getParameter("size");
            String sort = request.getParameter("sort");
            String order = request.getParameter("dir");
            String param = Utility.testoADb(request.getParameter("query"));

            String query = "Select count(*) righe"
                    + " from utenti as a left join utenti as b on a.cod_utente_padre = b.cod_utente" 
                    + " left join tipi_aggregazione c on c.tip_aggregazione = a.tip_aggregazione"
                    + " where a.cod_utente like ? or a.cognome_nome like ? or c.des_aggregazione like ?";
            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            st.setString(3, "%" + param + "%");
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);

            query = "Select a.cod_utente cod_utente,a.cognome_nome cognome_nome ,a.email email,a.ruolo ruolo,a.cod_utente_padre cod_utente_padre ,"
            		+ " b.cognome_nome cognome_nome_padre, c.tip_aggregazione tip_aggregazione, c.des_aggregazione des_aggregazione"
                    + " from utenti as a left join utenti as b on a.cod_utente_padre = b.cod_utente"
                    + " left join tipi_aggregazione c on c.tip_aggregazione = a.tip_aggregazione"
                    + " where a.cod_utente like ? or a.cognome_nome like ? or c.des_aggregazione like ? "
                    + " order by " + sort + " " + order + " limit ? , ?";

            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            st.setString(3, "%" + param + "%");
            st.setInt(4, Integer.parseInt(offset));
            st.setInt(5, Integer.parseInt(size));
            rs = st.executeQuery();

            JSONArray utenti = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_utente", rs.getString("cod_utente"));
                loopObj.put("cognome_nome", Utility.testoDaDb(rs.getString("cognome_nome")));
                loopObj.put("email", rs.getString("email"));
                loopObj.put("ruolo", rs.getString("ruolo"));
                loopObj.put("cod_utente_padre", rs.getString("cod_utente_padre"));
                loopObj.put("cognome_nome_padre", Utility.testoDaDb(rs.getString("cognome_nome_padre")));
                loopObj.put("tip_aggregazione", Utility.testoDaDb(rs.getString("tip_aggregazione")));
                loopObj.put("des_aggregazione", Utility.testoDaDb(rs.getString("des_aggregazione")));
                utenti.add(loopObj);
            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("utenti", utenti);
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
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
            int controllo = controlloCongruenzaUtenti(request.getParameter("cod_utente"), request.getParameter("cod_utente_padre"), conn);
            if (controllo == 0) {
                String query = "insert into utenti (cod_utente,cognome_nome,email,cod_utente_padre,ruolo,tip_aggregazione) "
                        + "values (? , ?, ?, ?, ?, ?)";
                st = conn.prepareStatement(query);
                st.setString(1, request.getParameter("cod_utente"));
                st.setString(2, Utility.testoADb(request.getParameter("cognome_nome")));
                st.setString(3, request.getParameter("email"));
                if (request.getParameter("cod_utente_padre").equals("")) {
                    st.setString(4, null);
                } else {
                    st.setString(4, request.getParameter("cod_utente_padre"));
                }
                st.setString(5, request.getParameter("ruolo_hidden"));
                st.setString(6, request.getParameter("tip_aggregazione"));
                st.executeUpdate();

                ret.put("success", "Inserimento effettuato");
            } else {
                ret.put("failure", "Collegamento ciclico sugli utenti");
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

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            int controllo = controlloCongruenzaUtenti(request.getParameter("cod_utente"), request.getParameter("cod_utente_padre"), conn);
            if (controllo == 0) {

                String query = "update utenti set cognome_nome= ?, "
                        + "email= ?, "
                        + "cod_utente_padre=?, "
                        + "ruolo=?, "
                        + "tip_aggregazione=? "
                        + "where cod_utente = ?";
                st = conn.prepareStatement(query);
                st.setString(6, request.getParameter("cod_utente"));
                st.setString(1, Utility.testoADb(request.getParameter("cognome_nome")));
                st.setString(2, request.getParameter("email"));
                if (request.getParameter("cod_utente_padre").equals("")) {
                    st.setString(3, null);
                } else {
                    st.setString(3, request.getParameter("cod_utente_padre"));
                }
                st.setString(4, request.getParameter("ruolo_hidden"));
                if (request.getParameter("tip_aggregazione").equals("")) {
                    st.setString(5, null);
                } else {
                    st.setString(5, request.getParameter("tip_aggregazione"));
                }
                st.executeUpdate();

                ret.put("success", "Aggiornamento effettuato");
            } else {
                ret.put("failure", "Collegamento ciclico sugli utenti");
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

    public boolean cancellaControllo(String codUtente, Connection conn) throws Exception {
        boolean ret = true;
        PreparedStatement st = null;
        ResultSet rs = null;
        int righe = 0;
        try {
            String query = "Select count(*) righe from utenti where cod_utente_padre = ?"
                    + " union "
                    + "Select count(*) righe from utenti_interventi where cod_utente = ?";
            st = conn.prepareStatement(query);
            st.setString(1, codUtente);
            st.setString(2, codUtente);
            rs = st.executeQuery();
            while (rs.next()) {
                righe = righe + rs.getInt("righe");
            }
            if (righe > 0) {
                ret = false;
            }
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
        return ret;
    }

    public JSONObject cancella(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();

            boolean controllo = cancellaControllo(request.getParameter("cod_utente"), conn);
            if (controllo) {
                String query = "delete from utenti where cod_utente= ?";
                st = conn.prepareStatement(query);
                st.setString(1, request.getParameter("cod_utente"));
                st.executeUpdate();

                ret.put("success", "Cancellazione effettuata");
            } else {
                ret.put("failure", "Utente referenziato, non è possibile cancellarlo");
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

    public JSONObject actionUtentiPadre(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        try {
            conn = Utility.getConnection();
            String offset = request.getParameter("start");
            String size = request.getParameter("size");
            String sort = request.getParameter("sort");
            String order = request.getParameter("dir");
            String param = Utility.testoADb(request.getParameter("query"));

            String query = "Select count(*) righe"
                    + " from utenti as a left join utenti as b on a.cod_utente_padre = b.cod_utente"
                    + " where a.cod_utente like ? or a.cognome_nome like ?";
            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);

            query = "select a.cod_utente cod_utente_padre, a.cognome_nome cognome_nome_padre"
                    + " from utenti as a "
                    + " where a.cod_utente like ? or a.cognome_nome like ? "
                    + "order by " + sort + " " + order + " limit ? , ?";

            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            st.setInt(3, Integer.parseInt(offset));
            st.setInt(4, Integer.parseInt(size));
            rs = st.executeQuery();

            JSONArray utenti = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_utente_padre", rs.getString("cod_utente_padre"));
                loopObj.put("cognome_nome_padre", Utility.testoDaDb(rs.getString("cognome_nome_padre")));
                utenti.add(loopObj);

            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("utenti_padre", utenti);
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }
}
