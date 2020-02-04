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
public class TipiAggregazione {

    private static Logger log = LoggerFactory.getLogger(TipiAggregazione.class);

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
                    + "from tipi_aggregazione a "
                    + "left join href b "
                    + "on a.href=b.href "
                    + "left join href_testi c "
                    + "on b.href=c.href "
                    + "and c.cod_lang='it' "
                    + "where a.tip_aggregazione like ? or a.des_aggregazione like ? or a.href like ? or c.tit_href like ?";
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

            query = "select a.tip_aggregazione tip_aggregazione, a.des_aggregazione des_aggregazione, b.href href, c.tit_href tit_href "
                    + "from tipi_aggregazione a "
                    + "left join href b "
                    + "on a.href=b.href "
                    + "left join href_testi c "
                    + "on b.href=c.href "
                    + "and c.cod_lang='it' "
                    + "where a.tip_aggregazione like ? or a.des_aggregazione like ? or a.href like ? or c.tit_href like ?"
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
                loopObj.put("des_aggregazione", Utility.testoDaDb(rs.getString("des_aggregazione")));
                loopObj.put("href", rs.getString("href"));
                loopObj.put("tit_href", Utility.testoDaDb(rs.getString("tit_href")));
                riga.add(loopObj);

            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("tipi_aggregazione", riga);
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
            String query = "insert into tipi_aggregazione (tip_aggregazione,des_aggregazione,href) "
                    + "values (? , ?, ?)";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("tip_aggregazione"));
            st.setString(2, Utility.testoADb(request.getParameter("des_aggregazione")));
            if (request.getParameter("href") != null && !request.getParameter("href").equals("")) {
                st.setString(3, request.getParameter("href"));
            } else {
                st.setString(3, null);
            }
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

    public JSONObject aggiorna(HttpServletRequest request) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;

        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            String query = "update tipi_aggregazione "
                    + "set des_aggregazione=? , "
                    + "href=? "
                    + "where tip_aggregazione =?";
            st = conn.prepareStatement(query);
            st.setString(1, Utility.testoADb(request.getParameter("des_aggregazione")));
            if (request.getParameter("href") != null && !request.getParameter("href").equals("")) {
                st.setString(2, request.getParameter("href"));
            } else {
                st.setString(2, null);
            }
            st.setString(3, request.getParameter("tip_aggregazione"));

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

    public boolean controlloCancella(String key, Connection conn) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        boolean controllo = true;
        int righe = 0;
        try {
            String query = "select count(*) righe from condizioni_di_attivazione where tip_aggregazione=? "
                    + "union "
                    + "select count(*) righe from gerarchia_operazioni where tip_aggregazione=? "
                    + "union "
                    + "select count(*) righe from lingue_aggregazioni where tip_aggregazione=?";
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
            controllo = controlloCancella(request.getParameter("tip_aggregazione"), conn);
            if (controllo) {
                String query = "delete from tipi_aggregazione where tip_aggregazione= ?";
                st = conn.prepareStatement(query);
                st.setString(1, request.getParameter("tip_aggregazione"));
                st.executeUpdate();

                ret.put("success", "Cancellazione effettuata");
            } else {
                ret.put("failure", "Aggregazione in uso");
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
