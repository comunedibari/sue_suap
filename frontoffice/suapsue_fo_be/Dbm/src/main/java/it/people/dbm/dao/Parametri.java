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
public class Parametri {

    private static Logger log = LoggerFactory.getLogger(Parametri.class);

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
            String href = request.getParameter("href");
            String param = Utility.testoADb(request.getParameter("query"));

            String query = "select count(*) righe "
                    + "from configuration "
                    + "where communeid like ? or name like ? or value like ? or note like ?";
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

            query = "select id, communeid, name, value, note "
                    + "from configuration "
                    + "where communeid like ? or name like ? or value like ? or note like ? "
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
                loopObj.put("id", rs.getInt("id"));
                loopObj.put("communeid", rs.getString("communeid"));
                loopObj.put("name", rs.getString("name"));
                loopObj.put("value", Utility.testoDaDb(rs.getString("value")));
                loopObj.put("note", Utility.testoDaDb(rs.getString("note")));
                riga.add(loopObj);

            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("configuration", riga);
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
            String query = "insert into configuration (name,communeid,value,note) "
                    + "values (? , ?, ?, ?)";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("name"));
            if (request.getParameter("communeid") != null && !request.getParameter("communeid").equals("")) {
                st.setString(2, request.getParameter("communeid"));
            } else {
                st.setString(2, null);
            }
            if (request.getParameter("value") != null && !request.getParameter("value").equals("")) {
                st.setString(3, Utility.testoADb(request.getParameter("value")));
            } else {
                st.setString(3, null);
            }
            st.setString(4, Utility.testoADb(request.getParameter("note")));
            st.executeUpdate();
            st.close();

            JSONObject ris = new JSONObject();
            query = "select max(last_insert_id()) id from  configuration";
            st = conn.prepareStatement(query);
            rs = st.executeQuery();
            if (rs.next()) {
                ris.put("id", rs.getInt("id"));
            }
            ris.put("msg", "Inserimento effettuato");

            ret.put("success", ris);
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
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();

            String query = "update configuration "
                    + "set name=? , "
                    + "communeid=?,  "
                    + "value=? , "
                    + "note=? "
                    + "where id =?";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("name"));
            if (request.getParameter("communeid") != null && !request.getParameter("communeid").equals("")) {
                st.setString(2, request.getParameter("communeid"));
            } else {
                st.setString(2, null);
            }
            if (request.getParameter("value") != null && !request.getParameter("value").equals("")) {
                st.setString(3, Utility.testoADb(request.getParameter("value")));
            } else {
                st.setString(3, null);
            }
            st.setString(4, Utility.testoADb(request.getParameter("note")));
            st.setInt(5, Integer.parseInt(request.getParameter("id")));
            st.executeUpdate();

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

    public JSONObject cancella(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();

            String query = "delete from configuration where id= ?";
            st = conn.prepareStatement(query);
            st.setInt(1, Integer.parseInt(request.getParameter("id")));
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
