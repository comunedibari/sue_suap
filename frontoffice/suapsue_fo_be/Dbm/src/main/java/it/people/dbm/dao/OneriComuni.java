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
public class OneriComuni {

    private static Logger log = LoggerFactory.getLogger(OneriComuni.class);

    public JSONObject action(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        String query = null;
        int qsi = 1;
        try {
            conn = Utility.getConnection();
            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            String offset = request.getParameter("start");
            String size = request.getParameter("size");
            String sort = request.getParameter("sort");
            String order = request.getParameter("dir");
            String href = request.getParameter("href");
            String param = Utility.testoADb(request.getParameter("query"));

            query = "select count(*) righe "
                    + "from oneri_comuni a "
                    + "join oneri b "
                    + "on a.cod_oneri=b.cod_oneri "
                    + "join oneri_testi c "
                    + "on a.cod_oneri=c.cod_oneri "
                    + "join enti_comuni d "
                    + "on a.cod_com=d.cod_ente "
                    + "join enti_comuni_testi e "
                    + "on a.cod_com=e.cod_ente "
                    + "and c.cod_lang=e.cod_lang "
                    + "where c.cod_lang='it' "
                    + "and (a.cod_oneri like ? or a.cod_com like ? or c.des_oneri like ? or e.des_ente like ? ) ";
            if (utente.getTipAggregazione() != null) {
                query = query + "and b.tip_aggregazione=? ";
            }
            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            st.setString(3, "%" + param + "%");
            st.setString(4, "%" + param + "%");
            if (utente.getTipAggregazione() != null) {
                st.setString(5, utente.getTipAggregazione());
            }
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            query = "select a.cod_oneri cod_oneri,c.des_oneri des_oneri,a.cod_com cod_com,e.des_ente des_ente, a.flg flg "
                    + "from oneri_comuni a "
                    + "join oneri b "
                    + "on a.cod_oneri=b.cod_oneri "
                    + "join oneri_testi c "
                    + "on a.cod_oneri=c.cod_oneri "
                    + "join enti_comuni d "
                    + "on a.cod_com=d.cod_ente "
                    + "join enti_comuni_testi e "
                    + "on a.cod_com=e.cod_ente "
                    + "and c.cod_lang=e.cod_lang "
                    + "where c.cod_lang='it' "
                    + "and (a.cod_oneri like ? or a.cod_com like ? or c.des_oneri like ? or e.des_ente like ? ) ";
            if (utente.getTipAggregazione() != null) {
                query = query + "and b.tip_aggregazione=? ";
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
            if (utente.getTipAggregazione() != null) {
                st.setString(qsi, utente.getTipAggregazione());
                qsi++;
            }
            st.setInt(qsi, Integer.parseInt(offset));
            qsi++;
            st.setInt(qsi, Integer.parseInt(size));
            rs = st.executeQuery();
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_oneri", rs.getString("cod_oneri"));
                loopObj.put("des_oneri", Utility.testoDaDb(rs.getString("des_oneri")));
                loopObj.put("cod_com", rs.getString("cod_com"));
                loopObj.put("des_ente", Utility.testoDaDb(rs.getString("des_ente")));
                loopObj.put("flg", Utility.testoDaDb(rs.getString("flg")));
                riga.add(loopObj);

            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("oneri_comuni", riga);
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
        String query;
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            query = "insert into oneri_comuni (cod_oneri,cod_com,flg) "
                    + "values (?,?,?)";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("cod_oneri"));
            st.setString(2, request.getParameter("cod_com"));
            st.setString(3, request.getParameter("flg_hidden"));
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
        String query;
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            query = "update oneri_comuni set flg=? "
                    + "where cod_oneri=? and cod_com=?";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("flg_hidden"));
            st.setString(2, request.getParameter("cod_oneri"));
            st.setString(3, request.getParameter("cod_com"));
            st.executeUpdate();

            ret.put("success", "Aggiornamento effettuato");
        } catch (SQLException e) {
            log.error("Errore aggiornamento ", e);
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
        String query;
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            query = "delete from oneri_comuni where cod_oneri=? and cod_com=?";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("cod_oneri"));
            st.setString(2, request.getParameter("cod_com"));
            st.executeUpdate();

            ret.put("success", "Cancellazione effettuata");
        } catch (SQLException e) {
            log.error("Errore cancellazione ", e);
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
