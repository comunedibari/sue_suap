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
import org.slf4j.LoggerFactory;

import it.people.dbm.utility.Utility;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject; 
/**
 *
 * @author Piergiorgio
 */
public class ComuniAggregazione {

    private static Logger log = LoggerFactory.getLogger(ComuniAggregazione.class);

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
                    + "from comuni_aggregazione a "
                    + "join tipi_aggregazione b "
                    + "on a.tip_aggregazione=b.tip_aggregazione "
                    + "join enti_comuni c "
                    + "on a.cod_ente=c.cod_ente "
                    + "join enti_comuni_testi d "
                    + "on a.cod_ente=d.cod_ente "
                    + "where d.cod_lang='it' "
                    + "and (a.tip_aggregazione like ? or a.cod_ente like ? or b.des_aggregazione like ? or d.des_ente like ?) ";
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

            query = "select a.tip_aggregazione tip_aggregazione,b.des_aggregazione des_aggregazione, "
                    + "a.cod_ente cod_com,d.des_ente des_ente "
                    + "from comuni_aggregazione a "
                    + "join tipi_aggregazione b "
                    + "on a.tip_aggregazione=b.tip_aggregazione "
                    + "join enti_comuni c "
                    + "on a.cod_ente=c.cod_ente "
                    + "join enti_comuni_testi d "
                    + "on a.cod_ente=d.cod_ente "
                    + "where d.cod_lang='it' "
                    + "and (a.tip_aggregazione like ? or a.cod_ente like ? or b.des_aggregazione like ? or d.des_ente like ?) "
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
                loopObj.put("tip_aggregazione", rs.getString("tip_aggregazione"));
                loopObj.put("cod_com", rs.getString("cod_com"));
                loopObj.put("des_aggregazione", Utility.testoDaDb(rs.getString("des_aggregazione")));
                loopObj.put("des_ente", Utility.testoDaDb(rs.getString("des_ente")));

                riga.add(loopObj);

            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("comuni_aggregazione", riga);
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
            String query = "insert into comuni_aggregazione (cod_ente,tip_aggregazione) "
                    + "values (?,? )";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("cod_com"));
            st.setString(2, request.getParameter("tip_aggregazione"));
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
            String query = "delete from comuni_aggregazione where cod_ente= ? and tip_aggregazione=?";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("cod_com"));
            st.setString(2, request.getParameter("tip_aggregazione"));

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
