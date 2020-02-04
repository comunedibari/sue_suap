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
public class SequenzaInterventi {

    private static Logger log = LoggerFactory.getLogger(SequenzaInterventi.class);

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
                    + "from interventi_seq a "
                    + "join interventi b "
                    + "on a.cod_int_prec=b.cod_int "
                    + "join interventi_testi c "
                    + "on a.cod_int_prec=c.cod_int "
                    + "join interventi d "
                    + "on a.cod_int_sel=d.cod_int "
                    + "join interventi_testi e "
                    + "on a.cod_int_sel=e.cod_int "
                    + "and c.cod_lang=e.cod_lang "
                    + "where c.cod_lang='it' "
                    + "and ( convert(a.cod_int_prec,CHAR) like ? or convert(a.cod_int_sel,CHAR) like ? or  c.tit_int like ? or e.tit_int like ? ) ";
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
            query = "select a.cod_int_prec cod_int_prec,c.tit_int tit_int_prec,a.cod_int_sel cod_int_sel, e.tit_int tit_int_sel "
                    + "from interventi_seq a "
                    + "join interventi b "
                    + "on a.cod_int_prec=b.cod_int "
                    + "join interventi_testi c "
                    + "on a.cod_int_prec=c.cod_int "
                    + "join interventi d "
                    + "on a.cod_int_sel=d.cod_int "
                    + "join interventi_testi e "
                    + "on a.cod_int_sel=e.cod_int "
                    + "and c.cod_lang=e.cod_lang "
                    + "where c.cod_lang='it' "
                    + "and ( convert(a.cod_int_prec,CHAR) like ? or convert(a.cod_int_sel,CHAR) like ? or  c.tit_int like ? or e.tit_int like ? ) "
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
                loopObj.put("cod_int_prec", rs.getInt("cod_int_prec"));
                loopObj.put("tit_int_prec", Utility.testoDaDb(rs.getString("tit_int_prec")));
                loopObj.put("cod_int_sel", rs.getInt("cod_int_sel"));
                loopObj.put("tit_int_sel", Utility.testoDaDb(rs.getString("tit_int_sel")));
                riga.add(loopObj);

            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("interventi_seq", riga);
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public boolean controlloCongruenza(HttpServletRequest request) throws Exception {
        boolean ret = true;
        if (request.getParameter("cod_int_sel").equals(request.getParameter("cod_int_prec"))) {
            ret = false;
        }
        return ret;
    }

    public JSONObject inserisci(HttpServletRequest request) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        String query;
        boolean controllo = false;
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            controllo = controlloCongruenza(request);
            if (controllo) {
                query = "insert into interventi_seq (cod_int_prec,cod_int_sel) "
                        + "values (?,?)";
                st = conn.prepareStatement(query);
                st.setInt(1, Integer.parseInt(request.getParameter("cod_int_prec")));
                st.setInt(2, Integer.parseInt(request.getParameter("cod_int_sel")));
                st.executeUpdate();

                ret.put("success", "Inserimento effettuato");
            } else {
                ret.put("failure", "Gli interventi non possono essere gli stessi");
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

    public JSONObject cancella(HttpServletRequest request) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        String query;
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            query = "delete from interventi_seq where cod_int_prec=? and cod_int_sel=?";
            st = conn.prepareStatement(query);
            st.setInt(1, Integer.parseInt(request.getParameter("cod_int_prec")));
            st.setInt(2, Integer.parseInt(request.getParameter("cod_int_sel")));
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
