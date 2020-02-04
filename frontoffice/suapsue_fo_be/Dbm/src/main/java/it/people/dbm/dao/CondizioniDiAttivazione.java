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
public class CondizioniDiAttivazione {

    private static Logger log = LoggerFactory.getLogger(CondizioniDiAttivazione.class);

    public JSONObject action(HttpServletRequest request) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        String query = null;
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
                    + "from condizioni_di_attivazione a "
                    + "join interventi b "
                    + "on a.cod_int=b.cod_int "
                    + "join interventi_testi c "
                    + "on a.cod_int=c.cod_int "
                    + "join operazioni d "
                    + "on a.cod_ope=d.cod_ope "
                    + "and c.cod_lang=d.cod_lang "
                    + "join settori_attivita e "
                    + "on a.cod_sett=e.cod_sett "
                    + "and c.cod_lang=e.cod_lang "
                    + "where c.cod_lang='it' "
                    + "and a.tip_aggregazione = ?"
                    + "and ( convert(a.cod_int,CHAR) like ? or a.cod_ope like ? or a.cod_sett like ? "
                    + "or c.tit_int like ? or d.des_ope like ? or e.des_sett like ?) ";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("tip_aggregazione"));
            st.setString(2, "%" + param + "%");
            st.setString(3, "%" + param + "%");
            st.setString(4, "%" + param + "%");
            st.setString(5, "%" + param + "%");
            st.setString(6, "%" + param + "%");
            st.setString(7, "%" + param + "%");
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            query = "select a.cod_int cod_int,a.cod_ope cod_ope,a.cod_sett cod_sett,c.tit_int tit_int,d.des_ope des_ope,e.des_sett des_sett "
                    + "from condizioni_di_attivazione a "
                    + "join interventi b "
                    + "on a.cod_int=b.cod_int "
                    + "join interventi_testi c "
                    + "on a.cod_int=c.cod_int "
                    + "join operazioni d "
                    + "on a.cod_ope=d.cod_ope "
                    + "and c.cod_lang=d.cod_lang "
                    + "join settori_attivita e "
                    + "on a.cod_sett=e.cod_sett "
                    + "and c.cod_lang=e.cod_lang "
                    + "where c.cod_lang='it' "
                    + "and a.tip_aggregazione = ?"
                    + "and ( convert(a.cod_int,CHAR) like ? or a.cod_ope like ? or a.cod_sett like ? "
                    + "or c.tit_int like ? or d.des_ope like ? or e.des_sett like ?) "
                    + "order by " + sort + " " + order + " limit ? , ?";

            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("tip_aggregazione"));
            st.setString(2, "%" + param + "%");
            st.setString(3, "%" + param + "%");
            st.setString(4, "%" + param + "%");
            st.setString(5, "%" + param + "%");
            st.setString(6, "%" + param + "%");
            st.setString(7, "%" + param + "%");
            st.setInt(8, Integer.parseInt(offset));
            st.setInt(9, Integer.parseInt(size));
            rs = st.executeQuery();

            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_int", rs.getInt("cod_int"));
                loopObj.put("tit_int", Utility.testoDaDb(rs.getString("tit_int")));
                loopObj.put("cod_ope", rs.getString("cod_ope"));
                loopObj.put("des_ope", Utility.testoDaDb(rs.getString("des_ope")));
                loopObj.put("cod_sett", rs.getString("cod_sett"));
                loopObj.put("des_sett", Utility.testoDaDb(rs.getString("des_sett")));
                riga.add(loopObj);

            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("condizioni_di_attivazione", riga);
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
            if (request.getParameter("az_modifica").equals("cancella")) {
                query = "insert into condizioni_di_attivazione (cod_int,cod_ope,cod_sett,tip_aggregazione) "
                        + "values (?,?,?,?)";
                st = conn.prepareStatement(query);
                st.setInt(1, Integer.parseInt(request.getParameter("cod_int")));
                st.setString(2, request.getParameter("cod_ope"));
                st.setString(3, request.getParameter("cod_sett"));
                st.setString(4, request.getParameter("tip_aggregazione"));
                st.executeUpdate();
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);
                query = "delete from condizioni_di_attivazione where cod_int=? and cod_ope=? and cod_sett=? and tip_aggregazione = ?";
                st = conn.prepareStatement(query);
                st.setInt(1, Integer.parseInt(request.getParameter("cod_int_old")));
                st.setString(2, request.getParameter("cod_ope_old"));
                st.setString(3, request.getParameter("cod_sett_old"));
                st.setString(4, request.getParameter("tip_aggregazione"));
                st.executeUpdate();
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);
            }
            if (request.getParameter("az_modifica").equals("mantieni")) {
                query = "insert into condizioni_di_attivazione (cod_int,cod_ope,cod_sett,tip_aggregazione) "
                        + "values (?,?,?,?)";
                st = conn.prepareStatement(query);
                st.setInt(1, Integer.parseInt(request.getParameter("cod_int")));
                st.setString(2, request.getParameter("cod_ope"));
                st.setString(3, request.getParameter("cod_sett"));
                st.setString(4, request.getParameter("tip_aggregazione"));
                st.executeUpdate();
                st.executeUpdate();
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);
            }
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

    public JSONObject inserisci(HttpServletRequest request) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        String query;
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            query = "insert into condizioni_di_attivazione (cod_int,cod_ope,cod_sett,tip_aggregazione) "
                    + "values (?,?,?,?)";
            st = conn.prepareStatement(query);
            st.setInt(1, Integer.parseInt(request.getParameter("cod_int")));
            st.setString(2, request.getParameter("cod_ope"));
            st.setString(3, request.getParameter("cod_sett"));
            st.setString(4, request.getParameter("tip_aggregazione"));
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
        String query;
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            query = "delete from condizioni_di_attivazione where cod_int=? and cod_ope=? and cod_sett=? and tip_aggregazione = ?";
            st = conn.prepareStatement(query);
            st.setInt(1, Integer.parseInt(request.getParameter("cod_int")));
            st.setString(2, request.getParameter("cod_ope"));
            st.setString(3, request.getParameter("cod_sett"));
            st.setString(4, request.getParameter("tip_aggregazione"));
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
