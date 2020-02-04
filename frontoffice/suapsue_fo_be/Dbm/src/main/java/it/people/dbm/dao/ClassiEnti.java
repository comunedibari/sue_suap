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
import java.util.Set;

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
public class ClassiEnti {

    private static Logger log = LoggerFactory.getLogger(ClassiEnti.class);

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
                    + "from classi_enti a "
                    + "join classi_enti_testi b "
                    + "on a.cod_classe_ente=b.cod_classe_ente "
                    + "where b.cod_lang='it' and (a.cod_classe_ente like ? or b.des_classe_ente like ?)";
            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);

            query = "select a.cod_classe_ente cod_classe_ente, b.des_classe_ente des_classe_ente, a.flg_com flg_com "
                    + "from classi_enti a "
                    + "join classi_enti_testi b "
                    + "on a.cod_classe_ente=b.cod_classe_ente "
                    + "where b.cod_lang='it' and (a.cod_classe_ente like ? or b.des_classe_ente like ? ) "
                    + "order by " + sort + " " + order + " limit ? , ?";

            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            st.setInt(3, Integer.parseInt(offset));
            st.setInt(4, Integer.parseInt(size));
            rs = st.executeQuery();

            String queryLang = "select des_classe_ente, cod_lang from classi_enti_testi where cod_classe_ente = ?";

            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_classe_ente", rs.getString("cod_classe_ente"));
                loopObj.put("flg_com", Utility.testoDaDb(rs.getString("flg_com")));
                stLang = conn.prepareStatement(queryLang);
                stLang.setString(1, rs.getString("cod_classe_ente"));
                rsLang = stLang.executeQuery();
                while (rsLang.next()) {
                    loopObj.put("des_classe_ente_" + rsLang.getString("cod_lang"), Utility.testoDaDb(rsLang.getString("des_classe_ente")));
                }
                Utility.chiusuraJdbc(rsLang);
                Utility.chiusuraJdbc(stLang);
                riga.add(loopObj);

            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("classi_enti", riga);
        } catch (Exception e) {
            log.error("Errore select ", e);
            ret = new JSONObject();
            ret.put("failure", e.getMessage());            
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
            String query = "insert into classi_enti (cod_classe_ente,flg_com) "
                    + "values (?,? )";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("cod_classe_ente"));
            st.setString(2, request.getParameter("flg_com_hidden"));
            st.executeUpdate();
            st.close();

            query = "insert into classi_enti_testi (cod_classe_ente,cod_lang,des_classe_ente) "
                    + "values (?,?,? )";

            Set<String> parmKey = request.getParameterMap().keySet();
            for (String key : parmKey) {
                String[] parSplit = key.split("des_classe_ente_");
                if (parSplit.length > 1 && key.contains("des_classe_ente_")) {
                    String cod_lang = parSplit[1];
                    if (cod_lang != null) {
                        st = conn.prepareStatement(query);
                        st.setString(1, request.getParameter("cod_classe_ente"));
                        st.setString(2, cod_lang);
                        st.setString(3, Utility.testoADb(request.getParameter("des_classe_ente_" + cod_lang)));
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
            String query = "update classi_enti "
                    + "set flg_com=? "
                    + "where cod_classe_ente =?";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("flg_com_hidden"));
            st.setString(2, request.getParameter("cod_classe_ente"));
            st.executeUpdate();
            Utility.chiusuraJdbc(st);

            String querySelect = "select * from classi_enti_testi where cod_classe_ente = ? and cod_lang = ?";
            String queryInsert = "insert into classi_enti_testi (cod_classe_ente,cod_lang,des_classe_ente) values (?,?,? )";

            query = "update classi_enti_testi "
                    + "set des_classe_ente=? "
                    + "where cod_classe_ente =? and cod_lang=?";
            Set<String> parmKey = request.getParameterMap().keySet();
            for (String key : parmKey) {
                String[] parSplit = key.split("des_classe_ente_");
                if (parSplit.length > 1 && key.contains("des_classe_ente_")) {
                    String cod_lang = parSplit[1];
                    if (cod_lang != null) {
                        stRead = conn.prepareStatement(querySelect);
                        stRead.setString(1, request.getParameter("cod_classe_ente"));
                        stRead.setString(2, cod_lang);
                        rsRead = stRead.executeQuery();
                        if (rsRead.next()) {
                            st = conn.prepareStatement(query);
                            st.setString(1, Utility.testoADb(request.getParameter(key)));
                            st.setString(2, request.getParameter("cod_classe_ente"));
                            st.setString(3, cod_lang);
                            st.executeUpdate();
                        } else {
                            st = conn.prepareStatement(queryInsert);
                            st.setString(1, request.getParameter("cod_classe_ente"));
                            st.setString(2, cod_lang);
                            st.setString(3, Utility.testoADb(request.getParameter(key)));
                            st.execute();
                        }
                        Utility.chiusuraJdbc(rs);
                        Utility.chiusuraJdbc(st);
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
            String query = "select count(*) righe from enti_comuni where cod_classe_ente=? ";
            st = conn.prepareStatement(query);
            st.setString(1, key);
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
            controllo = controlloCancella(request.getParameter("cod_classe_ente"), conn);
            if (controllo) {
                String query = "delete from classi_enti where cod_classe_ente= ?";
                st = conn.prepareStatement(query);
                st.setString(1, request.getParameter("cod_classe_ente"));
                st.executeUpdate();
                st.close();

                query = "delete from classi_enti_testi where cod_classe_ente= ?";
                st = conn.prepareStatement(query);
                st.setString(1, request.getParameter("cod_classe_ente"));
                st.executeUpdate();
                ret.put("success", "Cancellazione effettuata");
            } else {
                ret.put("failure", "Classe ente in uso");
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
