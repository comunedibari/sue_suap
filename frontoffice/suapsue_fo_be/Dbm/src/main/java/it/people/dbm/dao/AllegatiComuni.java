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
import org.slf4j.LoggerFactory;

import it.people.dbm.model.Utente;
import it.people.dbm.utility.Utility;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author Piergiorgio
 */
public class AllegatiComuni {

	private static Logger log = LoggerFactory.getLogger(AllegatiComuni.class);

    public JSONObject action(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        String query = null;
        Utente utente = (Utente) session.getAttribute("utente");
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
                    + "from allegati_comuni a "
                    + "join documenti b "
                    + "on a.cod_doc=b.cod_doc "
                    + "join documenti_testi c "
                    + "on a.cod_doc=c.cod_doc "
                    + "join enti_comuni d "
                    + "on a.cod_com=d.cod_ente "
                    + "join enti_comuni_testi e "
                    + "on a.cod_com=e.cod_ente "
                    + "and c.cod_lang=e.cod_lang "
                    + "where c.cod_lang='it' "
                    + "and (a.cod_doc like ? or a.cod_com like ? or c.tit_doc like ? or e.des_ente like ? ) ";
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
            query = "select a.cod_doc cod_doc,c.tit_doc tit_doc,a.cod_com cod_com,e.des_ente des_ente, a.flg flg "
                    + "from allegati_comuni a "
                    + "join documenti b "
                    + "on a.cod_doc=b.cod_doc "
                    + "join documenti_testi c "
                    + "on a.cod_doc=c.cod_doc "
                    + "join enti_comuni d "
                    + "on a.cod_com=d.cod_ente "
                    + "join enti_comuni_testi e "
                    + "on a.cod_com=e.cod_ente "
                    + "and c.cod_lang=e.cod_lang "
                    + "where c.cod_lang='it' "
                    + "and (a.cod_doc like ? or a.cod_com like ? or c.tit_doc like ? or e.des_ente like ? ) "
                    + "order by " + sort + " " + order + " limit ? , ?";

            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            st.setString(3, "%" + param + "%");
            st.setString(4, "%" + param + "%");
            st.setInt(5, Integer.parseInt(offset));
            st.setInt(6, Integer.parseInt(size));
            rs = st.executeQuery();

            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_doc", rs.getString("cod_doc"));
                loopObj.put("tit_doc", Utility.testoDaDb(rs.getString("tit_doc")));
                loopObj.put("cod_com", rs.getString("cod_com"));
                loopObj.put("des_ente", Utility.testoDaDb(rs.getString("des_ente")));
                loopObj.put("flg", Utility.testoDaDb(rs.getString("flg")));
                riga.add(loopObj);

            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("allegati_comuni", riga);
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject inserisci(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
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
            query = "insert into allegati_comuni (cod_doc,cod_com,flg) "
                    + "values (?,?,?)";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("cod_doc"));
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
        HttpSession session = request.getSession();
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
            query = "update allegati_comuni set flg=? "
                    + "where cod_doc=? and cod_com=?";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("flg_hidden"));
            st.setString(2, request.getParameter("cod_doc"));
            st.setString(3, request.getParameter("cod_com"));
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
        HttpSession session = request.getSession();
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
            query = "delete from allegati_comuni where cod_doc=? and cod_com=?";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("cod_doc"));
            st.setString(2, request.getParameter("cod_com"));
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
