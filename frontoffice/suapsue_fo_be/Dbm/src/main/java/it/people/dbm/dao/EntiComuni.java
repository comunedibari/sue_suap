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

import it.people.dbm.utility.Utility;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.LoggerFactory;
/**
 *
 * @author Piergiorgio
 */
public class EntiComuni {

    private static Logger log = LoggerFactory.getLogger(EntiComuni.class);

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
            String param = Utility.testoADb(request.getParameter("query"));

            String query = "select count(*) righe "
                    + "from enti_comuni a "
                    + "join enti_comuni_testi b "
                    + "on a.cod_ente=b.cod_ente "
                    + "join classi_enti c "
                    + "on a.cod_classe_ente=c.cod_classe_ente "
                    + "join classi_enti_testi d "
                    + "on a.cod_classe_ente = d.cod_classe_ente "
                    + "and d.cod_lang=b.cod_lang "
                    + "where b.cod_lang='it' and (a.cod_ente like ? or b.des_ente like ? or a.cod_classe_ente like ? or d.des_classe_ente like ?)";
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

            query = "select a.cod_ente, a.cod_classe_ente, b.des_ente, d.des_classe_ente, "
                    + "a.indirizzo, a.cap, a.citta, a.prov, a.tel, a.fax, a.cod_istat, a.cod_bf, a.src_pyth, a.email, a.estensione_firma, a.cod_nodo "
                    + "from enti_comuni a "
                    + "join enti_comuni_testi b "
                    + "on a.cod_ente=b.cod_ente "
                    + "join classi_enti c "
                    + "on a.cod_classe_ente=c.cod_classe_ente "
                    + "join classi_enti_testi d "
                    + "on a.cod_classe_ente = d.cod_classe_ente "
                    + "and d.cod_lang=b.cod_lang "
                    + "where b.cod_lang='it' and (a.cod_ente like ? or b.des_ente like ? or a.cod_classe_ente like ? or d.des_classe_ente like ?) "
                    + "order by " + sort + " " + order + " limit ? , ?";

            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            st.setString(3, "%" + param + "%");
            st.setString(4, "%" + param + "%");
            st.setInt(5, Integer.parseInt(offset));
            st.setInt(6, Integer.parseInt(size));
            rs = st.executeQuery();

            String queryLang = "select des_ente, cod_lang from enti_comuni_testi where cod_ente = ?";

            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_ente", rs.getString("cod_ente"));
                loopObj.put("cod_classe_ente", rs.getString("cod_classe_ente"));
                loopObj.put("des_classe_ente", Utility.testoDaDb(rs.getString("des_classe_ente")));
                loopObj.put("indirizzo", Utility.testoDaDb(rs.getString("indirizzo")));
                loopObj.put("cap", Utility.testoDaDb(rs.getString("cap")));
                loopObj.put("citta", Utility.testoDaDb(rs.getString("citta")));
                loopObj.put("prov", Utility.testoDaDb(rs.getString("prov")));
                loopObj.put("tel", Utility.testoDaDb(rs.getString("tel")));
                loopObj.put("fax", Utility.testoDaDb(rs.getString("fax")));
                loopObj.put("cod_istat", Utility.testoDaDb(rs.getString("cod_istat")));
                loopObj.put("cod_bf", Utility.testoDaDb(rs.getString("cod_bf")));
                loopObj.put("email", Utility.testoDaDb(rs.getString("email")));
                loopObj.put("src_pyth", Utility.testoDaDb(rs.getString("src_pyth")));
                loopObj.put("estensione_firma", Utility.testoDaDb(rs.getString("estensione_firma")));
                loopObj.put("cod_nodo", Utility.testoDaDb(rs.getString("cod_nodo")));
                stLang = conn.prepareStatement(queryLang);
                stLang.setString(1, rs.getString("cod_ente"));
                rsLang = stLang.executeQuery();
                while (rsLang.next()) {
                    loopObj.put("des_ente_" + rsLang.getString("cod_lang"), Utility.testoDaDb(rsLang.getString("des_ente")));
                }
                Utility.chiusuraJdbc(rsLang);
                Utility.chiusuraJdbc(stLang);
                riga.add(loopObj);

            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("enti_comuni", riga);
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
            String query = "insert into enti_comuni (cod_ente,cod_classe_ente,indirizzo,cap,citta,prov,tel,fax,email,cod_istat,cod_bf,src_pyth,estensione_firma, cod_nodo) "
                    + "values (?,?,?,?,?,?,?,?,?,?,?,?,?,? )";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("cod_ente"));
            st.setString(2, request.getParameter("cod_classe_ente"));
            st.setString(3, (request.getParameter("indirizzo") == null || request.getParameter("indirizzo").equals("") ? null : Utility.testoADb(request.getParameter("indirizzo"))));
            st.setString(4, (request.getParameter("cap") == null || request.getParameter("cap").equals("") ? null : Utility.testoADb(request.getParameter("cap"))));
            st.setString(5, (request.getParameter("citta") == null || request.getParameter("citta").equals("") ? null : Utility.testoADb(request.getParameter("citta"))));
            st.setString(6, (request.getParameter("prov") == null || request.getParameter("prov").equals("") ? null : Utility.testoADb(request.getParameter("prov"))));
            st.setString(7, (request.getParameter("tel") == null || request.getParameter("tel").equals("") ? null : Utility.testoADb(request.getParameter("tel"))));
            st.setString(8, (request.getParameter("fax") == null || request.getParameter("fax").equals("") ? null : Utility.testoADb(request.getParameter("fax"))));
            st.setString(9, (request.getParameter("email") == null || request.getParameter("email").equals("") ? null : Utility.testoADb(request.getParameter("email"))));
            st.setString(10, (request.getParameter("cod_istat") == null || request.getParameter("cod_istat").equals("") ? null : Utility.testoADb(request.getParameter("cod_istat"))));
            st.setString(11, (request.getParameter("cod_bf") == null || request.getParameter("cod_bf").equals("") ? null : Utility.testoADb(request.getParameter("cod_bf"))));
            st.setString(12, (request.getParameter("src_pyth") == null || request.getParameter("src_pyth").equals("") ? null : Utility.testoADb(request.getParameter("src_pyth"))));
            st.setString(13, (request.getParameter("estensione_firma") == null || request.getParameter("estensione_firma").equals("") ? null : Utility.testoADb(request.getParameter("estensione_firma"))));
            st.setString(14, (request.getParameter("cod_nodo") == null || request.getParameter("cod_nodo").equals("") ? null : Utility.testoADb(request.getParameter("cod_nodo"))));
            st.executeUpdate();
            st.close();

            query = "insert into enti_comuni_testi (cod_ente,cod_lang,des_ente) "
                    + "values (?,?,? )";
            Set<String> parmKey = request.getParameterMap().keySet();
            for (String key : parmKey) {
                String[] parSplit = key.split("des_ente_");
                if (parSplit.length > 1 && key.contains("des_ente_")) {
                    String cod_lang = parSplit[1];
                    if (cod_lang != null) {
                        st = conn.prepareStatement(query);
                        st.setString(1, request.getParameter("cod_ente"));
                        st.setString(2, cod_lang);
                        st.setString(3, Utility.testoADb(request.getParameter("des_ente_" + cod_lang)));
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
            String query = "update enti_comuni "
                    + "set cod_classe_ente = ?,"
                    + "indirizzo = ?, "
                    + "cap=?, "
                    + "citta= ?, "
                    + "prov=?, "
                    + "tel=?, "
                    + "fax=?, "
                    + "email=?, "
                    + "cod_istat=?, "
                    + "cod_bf=?, "
                    + "src_pyth=?, "
                    + "estensione_firma=?,"
                    + "cod_nodo=? "
                    + "where cod_ente =?";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("cod_classe_ente"));
            st.setString(2, (request.getParameter("indirizzo") == null || request.getParameter("indirizzo").equals("") ? null : Utility.testoADb(request.getParameter("indirizzo"))));
            st.setString(3, (request.getParameter("cap") == null || request.getParameter("cap").equals("") ? null : Utility.testoADb(request.getParameter("cap"))));
            st.setString(4, (request.getParameter("citta") == null || request.getParameter("citta").equals("") ? null : Utility.testoADb(request.getParameter("citta"))));
            st.setString(5, (request.getParameter("prov") == null || request.getParameter("prov").equals("") ? null : Utility.testoADb(request.getParameter("prov"))));
            st.setString(6, (request.getParameter("tel") == null || request.getParameter("tel").equals("") ? null : Utility.testoADb(request.getParameter("tel"))));
            st.setString(7, (request.getParameter("fax") == null || request.getParameter("fax").equals("") ? null : Utility.testoADb(request.getParameter("fax"))));
            st.setString(8, (request.getParameter("email") == null || request.getParameter("email").equals("") ? null : Utility.testoADb(request.getParameter("email"))));
            st.setString(9, (request.getParameter("cod_istat") == null || request.getParameter("cod_istat").equals("") ? null : Utility.testoADb(request.getParameter("cod_istat"))));
            st.setString(10, (request.getParameter("cod_bf") == null || request.getParameter("cod_bf").equals("") ? null : Utility.testoADb(request.getParameter("cod_bf"))));
            st.setString(11, (request.getParameter("src_pyth") == null || request.getParameter("src_pyth").equals("") ? null : Utility.testoADb(request.getParameter("src_pyth"))));
            st.setString(12, (request.getParameter("estensione_firma") == null || request.getParameter("estensione_firma").equals("") ? null : Utility.testoADb(request.getParameter("estensione_firma"))));
            st.setString(13, (request.getParameter("cod_nodo") == null || request.getParameter("cod_nodo").equals("") ? null : Utility.testoADb(request.getParameter("cod_nodo"))));
            st.setString(14, request.getParameter("cod_ente"));
            st.executeUpdate();
            Utility.chiusuraJdbc(st);

            String querySelect = "select * from enti_comuni_testi where cod_ente = ? and cod_lang = ?";
            String queryInsert = "insert into enti_comuni_testi (cod_ente,cod_lang,des_ente) values (?,?,? )";

            query = "update enti_comuni_testi "
                    + "set des_ente=? "
                    + "where cod_ente =? and cod_lang=?";
            Set<String> parmKey = request.getParameterMap().keySet();
            for (String key : parmKey) {
                String[] parSplit = key.split("des_ente_");
                if (parSplit.length > 1 && key.contains("des_ente_")) {
                    String cod_lang = parSplit[1];
                    if (cod_lang != null) {
                        stRead = conn.prepareStatement(querySelect);
                        stRead.setString(1, request.getParameter("cod_ente"));
                        stRead.setString(2, cod_lang);
                        rsRead = stRead.executeQuery();
                        if (rsRead.next()) {
                            st = conn.prepareStatement(query);
                            st.setString(1, Utility.testoADb(request.getParameter(key)));
                            st.setString(2, request.getParameter("cod_ente"));
                            st.setString(3, cod_lang);
                            st.executeUpdate();
                        } else {
                            st = conn.prepareStatement(queryInsert);
                            st.setString(1, request.getParameter("cod_ente"));
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
            String query = "select count(*) righe from destinatari where cod_ente=? "
                    + "union "
                    + "select count(*) righe from allegati_comuni where cod_com=? "
                    + "union "
                    + "select count(*) righe from comuni_aggregazione where cod_ente=? "
                    + "union "
                    + "select count(*) righe from interventi_comuni where cod_com=? "
                    + "union "
                    + "select count(*) righe from oneri_comuni where cod_com=? "
                    + "union "
                    + "select count(*) righe from relazioni_enti where cod_com=? "
                    + "union "
                    + "select count(*) righe from templates where cod_com=? "
                    + "union "
                    + "select count(*) righe from templates_immagini_immagini where cod_com=? "
                    + "union "
                    + "select count(*) righe from norme_comuni where cod_com=? ";

            st = conn.prepareStatement(query);
            st.setString(1, key);
            st.setString(2, key);
            st.setString(3, key);
            st.setString(4, key);
            st.setString(5, key);
            st.setString(6, key);
            st.setString(7, key);
            st.setString(8, key);
            st.setString(9, key);
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
            controllo = controlloCancella(request.getParameter("cod_ente"), conn);
            if (controllo) {
                String query = "delete from enti_comuni where cod_ente= ?";
                st = conn.prepareStatement(query);
                st.setString(1, request.getParameter("cod_ente"));
                st.executeUpdate();
                st.close();

                query = "delete from enti_comuni_testi where cod_ente= ?";
                st = conn.prepareStatement(query);
                st.setString(1, request.getParameter("cod_ente"));
                st.executeUpdate();
                ret.put("success", "Cancellazione effettuata");
            } else {
                ret.put("failure", "Ente in uso");
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
