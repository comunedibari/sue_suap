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
import java.util.Set;

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
public class Operazioni {

    private static Logger log = LoggerFactory.getLogger(Operazioni.class);

    public JSONObject action(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        PreparedStatement stLang = null;
        ResultSet rsLang = null;          
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
                    + "from operazioni "
                    + "where cod_lang='it' and (cod_ope like ? or des_ope like ?)";
            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");

            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);

            query = "select des_ope , cod_ope "
                    + "from operazioni "
                    + "where cod_lang='it' and (cod_ope like ? or des_ope like ?) "
                    + "order by " + sort + " " + order + " limit ? , ?";

            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            st.setInt(3, Integer.parseInt(offset));
            st.setInt(4, Integer.parseInt(size));
            rs = st.executeQuery();

            String queryLang = "select des_ope, cod_lang from operazioni where cod_ope = ?";
            
            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_ope", rs.getString("cod_ope"));
                stLang = conn.prepareStatement(queryLang);
                stLang.setString(1, rs.getString("cod_ope"));
                rsLang = stLang.executeQuery();
                while (rsLang.next()) {
                    loopObj.put("des_ope_" + rsLang.getString("cod_lang"), Utility.testoDaDb(rsLang.getString("des_ope")));
                }
                Utility.chiusuraJdbc(rsLang);
                Utility.chiusuraJdbc(stLang);                
                riga.add(loopObj);

            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("operazioni", riga);
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(rsLang);
            Utility.chiusuraJdbc(stLang);              
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
            String query = "insert into operazioni (cod_ope,des_ope,cod_lang) "
                    + "values (?,?,? )";
            Set<String> parmKey = request.getParameterMap().keySet();
            for (String key : parmKey) {
                String[] parSplit = key.split("des_ope_");
                if (parSplit.length > 1) {
                    String cod_lang = parSplit[1];
                    if (cod_lang != null) {
                        st = conn.prepareStatement(query);
                        st.setString(1, request.getParameter("cod_ope"));
                        st.setString(2, Utility.testoADb(request.getParameter("des_ope_" + cod_lang)));
                        st.setString(3, cod_lang);
                        st.executeUpdate();
                        Utility.chiusuraJdbc(rs);
                        Utility.chiusuraJdbc(st);
                    }
                }
            }
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
        PreparedStatement stRead = null;
        ResultSet rsRead = null;            
        JSONObject ret = null;

        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            String querySelect = "select * from operazioni where cod_ope = ? and cod_lang = ?";
            String queryInsert = "insert into operazioni (cod_ope,des_ope,cod_lang) values (?,?,? )";            
            String query = "update operazioni set des_ope=? "
                    + "where cod_ope =? and cod_lang=?";

            Set<String> parmKey = request.getParameterMap().keySet();
            for (String key : parmKey) {
                String[] parSplit = key.split("des_ope_");
                if (parSplit.length > 1) {
                    String cod_lang = parSplit[1];
                    if (cod_lang != null) {
                        stRead = conn.prepareStatement(querySelect);
                        stRead.setString(1, request.getParameter("cod_ope"));
                        stRead.setString(2, cod_lang);
                        rsRead = stRead.executeQuery();
                        if (rsRead.next()) {
                            st = conn.prepareStatement(query);
                            st.setString(1, Utility.testoADb(request.getParameter(key)));
                            st.setString(2, request.getParameter("cod_ope"));
                            st.setString(3, cod_lang);
                            st.executeUpdate();
                            Utility.chiusuraJdbc(rs);
                            Utility.chiusuraJdbc(st);
                        } else {
                            st = conn.prepareStatement(queryInsert);
                            st.setString(1, request.getParameter("cod_ope"));
                            st.setString(2, Utility.testoADb(request.getParameter(key)));
                            st.setString(3, cod_lang);                            
                            st.execute();
                            Utility.chiusuraJdbc(rs);
                            Utility.chiusuraJdbc(st);
                        }
                        Utility.chiusuraJdbc(rsRead);
                        Utility.chiusuraJdbc(stRead);
                    }
                }
            }

            ret.put("success", "Aggiornamento effettuato");
        } catch (SQLException e) {
            log.error("Errore update ", e);
            ret = new JSONObject();
            ret.put("failure", e.getMessage());
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(rsRead);
            Utility.chiusuraJdbc(stRead);               
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
            String query = "select count(*) righe from condizioni_di_attivazione where cod_ope=? "
                    + "union "
                    + "select count(*) righe from gerarchia_operazioni where cod_ope=? ";
            st = conn.prepareStatement(query);
            st.setString(1, key);
            st.setString(2, key);
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
            controllo = controlloCancella(request.getParameter("cod_ope"), conn);
            if (controllo) {
                String query = "delete from operazioni where cod_ope= ?";
                st = conn.prepareStatement(query);
                st.setString(1, request.getParameter("cod_ope"));
                st.executeUpdate();
                ret.put("success", "Cancellazione effettuata");
            } else {
                ret.put("failure", "Operazione in uso");
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
