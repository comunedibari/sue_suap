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
public class UtenteInterventi {

    private static Logger log = LoggerFactory.getLogger(UtenteInterventi.class);

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

            String query = "select count(*) righe "
                    + "from utenti a "
                    + "join utenti_interventi b "
                    + "on a.cod_utente = b.cod_utente "
                    + "join interventi c "
                    + "on b.cod_int = c.cod_int "
                    + "join interventi_testi d "
                    + "on c.cod_int = d.cod_int "
                    + "and d.cod_lang='it' "
                    + "where convert(c.cod_int,CHAR) like ? or a.cod_utente like ? or a.cognome_nome like ? or d.tit_int like ? ";
            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            st.setString(3, "%" + param + "%");
            st.setString(4, "%" + param + "%");
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);

            query = "select a.cod_utente cod_utente, a.cognome_nome cognome_nome, c.cod_int cod_int, d.tit_int tit_int "
                    + "from utenti a "
                    + "join utenti_interventi b "
                    + "on a.cod_utente = b.cod_utente "
                    + "join interventi c "
                    + "on b.cod_int = c.cod_int "
                    + "join interventi_testi d "
                    + "on c.cod_int = d.cod_int "
                    + "and d.cod_lang='it' "
                    + "where convert(c.cod_int,CHAR) like ? or a.cod_utente like ? or a.cognome_nome like ? or d.tit_int like ? "
                    + "order by " + sort + " " + order + " limit ? , ?";

            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            st.setString(3, "%" + param + "%");
            st.setString(4, "%" + param + "%");
            st.setInt(5, Integer.parseInt(offset));
            st.setInt(6, Integer.parseInt(size));
            rs = st.executeQuery();

            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_utente", rs.getString("cod_utente"));
                loopObj.put("cognome_nome", Utility.testoDaDb(rs.getString("cognome_nome")));
                loopObj.put("cod_int", rs.getInt("cod_int"));
                loopObj.put("tit_int", rs.getString("tit_int"));
                riga.add(loopObj);

            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("utenti_interventi", riga);
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
            String query = "insert into utenti_interventi (cod_utente,cod_int) "
                    + "values (? , ?)";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("cod_utente"));
            st.setInt(2, Integer.parseInt(request.getParameter("cod_int")));
            st.executeUpdate();

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

    public JSONObject cancella(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();

            String query = "delete from utenti_interventi where cod_utente= ? and cod_int = ?";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("cod_utente"));
            st.setInt(2, Integer.parseInt(request.getParameter("cod_int")));
            st.executeUpdate();

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
}
