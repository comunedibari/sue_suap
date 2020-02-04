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
public class GerarchiaSettori {

    private static Logger log = LoggerFactory.getLogger(GerarchiaSettori.class);
    private JSONArray jArrayGerarchia;

    public void actionLoop(String cod_ramo_prec, JSONArray jo, Connection conn) throws Exception {

        PreparedStatement st = null;
        ResultSet rs = null;
        PreparedStatement stLang = null;
        ResultSet rsLang = null;     
        try {
            String query = "select a.cod_ramo text,a.cod_ramo,a.cod_ramo_prec, a.cod_sett, a.cod_rif, "
                    + "b.des_ramo, d.des_ramo des_ramo_prec, e.des_sett, g.tit_rif "
                    + "from gerarchia_settori a "
                    + "join gerarchia_settori_testi b "
                    + "on a.cod_ramo=b.cod_ramo "
                    + "and b.cod_lang='it' "
                    + "left join gerarchia_settori c "
                    + "on a.cod_ramo_prec = c.cod_ramo "
                    + "left join gerarchia_settori_testi d "
                    + "on c.cod_ramo=d.cod_ramo "
                    + "and d.cod_lang='it' "
                    + "left join settori_attivita e "
                    + "on a.cod_sett=e.cod_sett "
                    + "and e.cod_lang='it' "
                    + "left join normative f "
                    + "on a.cod_rif=f.cod_rif "
                    + "left join normative_testi g "
                    + "on f.cod_rif = g.cod_rif "
                    + "and g.cod_lang='it' "
                    + "where a.cod_ramo_prec "
                    + (cod_ramo_prec == null || cod_ramo_prec.equals("") ? "is null" : " = '" + cod_ramo_prec + "'") + " order by a.cod_ramo";
            st = conn.prepareStatement(query);

            String queryLang = "select des_ramo, cod_lang from gerarchia_settori_testi where cod_ramo = ?";             
            
            rs = st.executeQuery();
            JSONObject ele;
            while (rs.next()) {
                ele = new JSONObject();
                ele.put("iconCls", "task-folder");
                ele.put("uiProvider", "col");
                ele.put("children", new JSONArray());
                ele.put("text", Utility.testoDaDb(rs.getString("text")));
                ele.put("cod_ramo", rs.getString("cod_ramo"));
                ele.put("cod_sett", (rs.getObject("cod_sett") == null ? "" : rs.getString("cod_sett")));
                ele.put("cod_ramo_prec", (rs.getObject("cod_ramo_prec") == null ? "" : rs.getString("cod_ramo_prec")));
                ele.put("cod_rif", (rs.getObject("cod_rif") == null ? "" : rs.getString("cod_rif")));
                ele.put("des_ramo_prec", (rs.getObject("des_ramo_prec") == null ? "" : Utility.testoDaDb(rs.getString("des_ramo_prec"))));
                ele.put("des_sett", (rs.getObject("des_sett") == null ? "" : Utility.testoDaDb(rs.getString("des_sett"))));
                ele.put("tit_rif", (rs.getObject("tit_rif") == null ? "" : Utility.testoDaDb(rs.getString("tit_rif"))));
                stLang = conn.prepareStatement(queryLang);
                stLang.setString(1, rs.getString("cod_ramo"));
                rsLang = stLang.executeQuery();
                while (rsLang.next()) {
                    ele.put(rsLang.getString("cod_lang") + "_des_ramo" , Utility.testoDaDb(rsLang.getString("des_ramo")));
                }
                Utility.chiusuraJdbc(rsLang);
                Utility.chiusuraJdbc(stLang);                  
                if (rs.getObject("cod_sett") == null) {
                    ele.put("iconCls", "task-folder");
                    actionLoop(rs.getString("cod_ramo"), ele.getJSONArray("children"), conn);
                } else {
                    ele.put("leaf", "false");
                    ele.put("cls", "task");
                }
                jo.add(ele);
            }
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(rsLang);
            Utility.chiusuraJdbc(stLang);    
        }
    }

    public JSONArray action(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
//        JSONArray ret = new JSONArray();
        try {
            jArrayGerarchia = new JSONArray();
            conn = Utility.getConnection();
            actionLoop("", jArrayGerarchia, conn);
            return jArrayGerarchia;
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
    }

    public JSONObject inserisci(HttpServletRequest request) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            String query = "insert into gerarchia_settori (cod_ramo,cod_ramo_prec,cod_sett,cod_rif) "
                    + "values (?,?,?,? )";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("cod_ramo"));
            st.setString(2, (request.getParameter("cod_ramo_prec") == null || request.getParameter("cod_ramo_prec").equals("") ? null : request.getParameter("cod_ramo_prec")));
            st.setString(3, (request.getParameter("cod_sett") == null || request.getParameter("cod_sett").equals("") ? null : request.getParameter("cod_sett")));
            st.setString(4, (request.getParameter("cod_rif") == null || request.getParameter("cod_rif").equals("") ? null : request.getParameter("cod_rif")));
            st.executeUpdate();
            st.close();

            query = "insert into gerarchia_settori_testi (cod_ramo,des_ramo,cod_lang) "
                    + "values (?,?,? )";
            
            Set<String> parmKey = request.getParameterMap().keySet();
            for (String key : parmKey) {
                String[] parSplit = key.split("_des_ramo");
                if (parSplit.length > 0 && key.contains("_des_ramo")){
                    String cod_lang = parSplit[0];
                    if (cod_lang != null) {
                        st = conn.prepareStatement(query);
                        st.setString(1, request.getParameter("cod_ramo"));
                        st.setString(2, Utility.testoADb(request.getParameter(cod_lang + "_des_ramo")));
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
            String query = "update gerarchia_settori "
                    + "set cod_sett=? , "
                    + "cod_rif=? "
                    + "where cod_ramo=?";
            st = conn.prepareStatement(query);
            st.setString(1, (request.getParameter("cod_sett") == null || request.getParameter("cod_sett").equals("") ? null : request.getParameter("cod_sett")));
            st.setString(2, (request.getParameter("cod_rif") == null || request.getParameter("cod_rif").equals("") ? null : request.getParameter("cod_rif")));
            st.setString(3, request.getParameter("cod_ramo"));

            st.executeUpdate();
            st.close();

            String querySelect = "select * from gerarchia_settori_testi where cod_ramo = ? and cod_lang = ?";
            String queryInsert = "insert into gerarchia_settori_testi (cod_ramo,des_ramo,cod_lang) values (?,?,? )";                
            query = "update gerarchia_settori_testi "
                    + "set des_ramo=? "
                    + "where cod_ramo=? and cod_lang=?";
            
            Set<String> parmKey = request.getParameterMap().keySet();
            for (String key : parmKey) {
                String[] parSplit = key.split("_des_ramo");
                if (parSplit.length > 0 && key.contains("_des_ramo")){
                    String cod_lang = parSplit[0];
                    if (cod_lang != null) {
                        stRead = conn.prepareStatement(querySelect);
                        stRead.setString(1, request.getParameter("cod_ramo"));
                        stRead.setString(2, cod_lang);
                        rsRead = stRead.executeQuery();
                        if (rsRead.next()) {
                            st = conn.prepareStatement(query);
                            st.setString(1, Utility.testoADb(request.getParameter(key)));
                            st.setString(2, request.getParameter("cod_ramo"));
                            st.setString(3, cod_lang);
                            st.executeUpdate();
                            Utility.chiusuraJdbc(rs);
                            Utility.chiusuraJdbc(st);
                        } else {
                            st = conn.prepareStatement(queryInsert);
                            st.setString(1, request.getParameter("cod_ramo"));
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

    public void cancellaLoop(String codRamo, Connection conn) throws Exception {

        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        try {
            String query = "select cod_ramo,cod_ramo_prec from gerarchia_settori "
                    + "where cod_ramo_prec=?";
            st = conn.prepareStatement(query);
            st.setString(1, codRamo);
            rs = st.executeQuery();
            while (rs.next()) {
                cancellaLoop(rs.getString("cod_ramo"), conn);
            }
            rs.close();
            st.close();
            query = "delete from gerarchia_settori where cod_ramo = ?";
            st = conn.prepareStatement(query);
            st.setString(1, codRamo);
            st.executeUpdate();
            st.close();
            query = "delete from gerarchia_settori_testi where cod_ramo = ?";
            st = conn.prepareStatement(query);
            st.setString(1, codRamo);
            st.executeUpdate();
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
    }

    public JSONObject cancella(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            cancellaLoop(request.getParameter("cod_ramo"),conn);
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

    public JSONObject leggiRamo(HttpServletRequest request) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        try {
            conn = Utility.getConnection();
            String param = Utility.testoADb(request.getParameter("cod_ramo"));
            String query = "select a.cod_sett, "
                    + "(select count(*) from gerarchia_settori where cod_ramo_prec = ?) figli "
                    + "from gerarchia_settori a "
                    + "join gerarchia_settori_testi b "
                    + "on a.cod_ramo=b.cod_ramo "
                    + "and b.cod_lang='it' "
                    + "where a.cod_ramo  = ?";
            st = conn.prepareStatement(query);
            st.setString(1, param);
            st.setString(2, param);
            rs = st.executeQuery();

            JSONObject loopObj = new JSONObject();
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_sett", rs.getString("cod_sett"));
                loopObj.put("figli", rs.getInt("figli"));
            }
            ret = new JSONObject();
            ret.put("success", loopObj);
        } catch (Exception e) {
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
