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
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.people.dbm.utility.Utility;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author Piergiorgio
 */
public class Anagrafica {

	private static Logger log = LoggerFactory.getLogger(Anagrafica.class);

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
                    + "from anagrafica a join anagrafica_testi b "
                    + "on a.contatore=b.contatore "
                    + "and a.nome=b.nome "
                    + "where b.cod_lang='it' "
                    + "and (a.nome like ? or b.des_campo like ?)";
            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);

            query = "select a.contatore contatore,a.nome nome,b.des_campo des_campo,a.riga riga,"
                    + "a.posizione posizione,a.tp_riga tp_riga,a.tipo tipo,a.valore valore,"
                    + "a.controllo controllo,a.tp_controllo tp_controllo,a.lunghezza lunghezza,"
                    + "a.decimali decimali,a.edit edit,a.raggruppamento_check raggruppamento_check,"
                    + "a.campo_collegato campo_collegato,a.val_campo_collegato val_campo_collegato,"
                    + "a.livello livello,a.precompilazione precompilazione,a.web_serv web_serv,"
                    + "a.campo_dati campo_dati,a.campo_key campo_key,a.nome_xsd nome_xsd,"
                    + "a.campo_xml_mod campo_xml_mod, a.flg_precompilazione flg_precompilazione, a.azione azione, b.err_msg err_msg, a.pattern pattern, a.marcatore_incrociato, a.campo_titolare "
                    + "from anagrafica a join anagrafica_testi b "
                    + "on a.contatore=b.contatore "
                    + "and a.nome=b.nome "
                    + "where b.cod_lang='it' "
                    + "and (a.nome like ? or b.des_campo like ?) "
                    + "order by " + sort + " " + order + " limit ? , ?";

            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            st.setInt(3, Integer.parseInt(offset));
            st.setInt(4, Integer.parseInt(size));
            rs = st.executeQuery();

            String queryLang = "select des_campo, err_msg, cod_lang from anagrafica_testi where contatore = ? and nome = ?";

            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("nome", rs.getString("nome"));
                loopObj.put("contatore", rs.getInt("contatore"));
                loopObj.put("riga", rs.getInt("riga"));
                loopObj.put("posizione", rs.getInt("posizione"));
                loopObj.put("tp_riga", rs.getString("tp_riga"));
                loopObj.put("tipo", rs.getString("tipo"));
                loopObj.put("valore", rs.getString("valore"));
                loopObj.put("controllo", rs.getString("controllo"));
                loopObj.put("tp_controllo", rs.getString("tp_controllo"));
                loopObj.put("lunghezza", rs.getInt("lunghezza"));
                loopObj.put("decimali", rs.getInt("decimali"));
                loopObj.put("edit", rs.getString("edit"));
                loopObj.put("raggruppamento_check", rs.getString("raggruppamento_check"));
                loopObj.put("campo_collegato", rs.getString("campo_collegato"));
                loopObj.put("val_campo_collegato", rs.getString("val_campo_collegato"));
                loopObj.put("livello", rs.getInt("livello"));
                loopObj.put("precompilazione", rs.getString("precompilazione"));
                loopObj.put("web_serv", rs.getString("web_serv"));
                loopObj.put("campo_dati", rs.getString("campo_dati"));
                loopObj.put("campo_key", rs.getString("campo_key"));
                loopObj.put("nome_xsd", rs.getString("nome_xsd"));
                loopObj.put("campo_xml_mod", rs.getString("campo_xml_mod"));
                loopObj.put("flg_precompilazione", rs.getString("flg_precompilazione"));
                loopObj.put("azione", rs.getString("azione"));
                loopObj.put("pattern", rs.getString("pattern"));
                loopObj.put("marcatore_incrociato", rs.getString("marcatore_incrociato"));
                loopObj.put("campo_titolare", rs.getString("campo_titolare"));
                stLang = conn.prepareStatement(queryLang);
                stLang.setInt(1, rs.getInt("contatore"));
                stLang.setString(2, rs.getString("nome"));
                rsLang = stLang.executeQuery();
                while (rsLang.next()) {
                    loopObj.put("des_campo_" + rsLang.getString("cod_lang"), Utility.testoDaDb(rsLang.getString("des_campo")));
                    loopObj.put("err_msg_" + rsLang.getString("cod_lang"), Utility.testoDaDb(rsLang.getString("err_msg")));
                }
                Utility.chiusuraJdbc(rsLang);
                Utility.chiusuraJdbc(stLang);
                riga.add(loopObj);

            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("anagrafica", riga);
        } catch (SQLException e) {
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
        HttpSession session = request.getSession();
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();

            String query = "insert into anagrafica ( nome, riga , posizione, tp_riga, tipo, valore, controllo, "
                    + "tp_controllo, lunghezza, decimali, edit, raggruppamento_check, campo_collegato, "
                    + "val_campo_collegato, livello, web_serv, nome_xsd, campo_key, campo_dati, "
                    + "campo_xml_mod, precompilazione, flg_precompilazione, azione, pattern, marcatore_incrociato, campo_titolare ) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            st = conn.prepareStatement(query);
            st.setString(1, Utility.testoADb(request.getParameter("nome")));
            st.setInt(2, Integer.parseInt(request.getParameter("riga")));
            st.setInt(3, Integer.parseInt(request.getParameter("posizione")));
            st.setString(4, request.getParameter("tp_riga_hidden"));
            st.setString(5, request.getParameter("tipo_hidden"));
            st.setString(6, (request.getParameter("valore") == null || request.getParameter("valore").equals("") ? null : Utility.testoADb(request.getParameter("valore"))));
            st.setString(7, request.getParameter("controllo_hidden"));
            st.setString(8, (request.getParameter("tp_controllo_hidden") == null || request.getParameter("tp_controllo_hidden").equals("") ? "T" : request.getParameter("tp_controllo_hidden")));
            st.setInt(9, (request.getParameter("lunghezza") == null || request.getParameter("lunghezza").equals("") ? 30 : Integer.parseInt(request.getParameter("lunghezza"))));
            st.setInt(10, (request.getParameter("decimali") == null || request.getParameter("decimali").equals("") ? 0 : Integer.parseInt(request.getParameter("decimali"))));
            st.setString(11, request.getParameter("edit_hidden"));
            st.setString(12, (request.getParameter("raggruppamento_check") == null || request.getParameter("raggruppamento_check").equals("") ? null : Utility.testoADb(request.getParameter("raggruppamento_check"))));
            st.setString(13, (request.getParameter("campo_collegato") == null || request.getParameter("campo_collegato").equals("") ? null : Utility.testoADb(request.getParameter("campo_collegato"))));
            st.setString(14, (request.getParameter("val_campo_collegato") == null || request.getParameter("val_campo_collegato").equals("") ? null : Utility.testoADb(request.getParameter("val_campo_collegato"))));
            st.setInt(15, Integer.parseInt(request.getParameter("livello")));
            st.setString(16, (request.getParameter("web_serv") == null || request.getParameter("web_serv").equals("") ? null : request.getParameter("web_serv")));
            st.setString(17, (request.getParameter("nome_xsd") == null || request.getParameter("nome_xsd").equals("") ? null : request.getParameter("nome_xsd")));
            st.setString(18, (request.getParameter("campo_key") == null || request.getParameter("campo_key").equals("") ? null : request.getParameter("campo_key")));
            st.setString(19, (request.getParameter("campo_dati") == null || request.getParameter("campo_dati").equals("") ? null : request.getParameter("campo_dati")));
            st.setString(20, (request.getParameter("campo_xml_mod") == null || request.getParameter("campo_xml_mod").equals("") ? null : request.getParameter("campo_xml_mod")));
            st.setString(21, (request.getParameter("precompilazione") == null || request.getParameter("precompilazione").equals("") ? "" : request.getParameter("precompilazione")));
            st.setString(22, request.getParameter("flg_precompilazione_hidden"));
            st.setString(23, (request.getParameter("azione") != null && !request.getParameter("azione").equals("") ? request.getParameter("azione") : null));
            st.setString(24, (request.getParameter("pattern") == null || request.getParameter("pattern").equals("") ? null : request.getParameter("pattern")));
            st.setString(25, (request.getParameter("marcatore_incrociato") == null || request.getParameter("marcatore_incrociato").equals("") ? null : request.getParameter("marcatore_incrociato")));
            st.setString(26, (request.getParameter("campo_titolare_hidden") == null || request.getParameter("campo_titolare_hidden").equals("") ? null : request.getParameter("campo_titolare_hidden")));
            st.executeUpdate();
            st.close();

            int contatore = 0;
            String queryLastInsert = "select max(last_insert_id()) contatore from anagrafica";
            st = conn.prepareStatement(queryLastInsert);
            rs = st.executeQuery();
            if (rs.next()) {
                contatore = rs.getInt("contatore");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);

            query = "insert into anagrafica_testi (contatore, nome, cod_lang, des_campo, err_msg) values (?,?, ?, ?, ?)";

            Set<String> parmKey = request.getParameterMap().keySet();
            for (String key : parmKey) {
                String[] parSplit = key.split("des_campo_");
                if (parSplit.length > 1) {
                    String cod_lang = parSplit[1];
                    if (cod_lang != null) {
                        st = conn.prepareStatement(query);
                        st.setInt(1, contatore);
                        st.setString(2, Utility.testoADb(request.getParameter("nome")));
                        st.setString(3, cod_lang);
                        st.setString(4, Utility.testoADb(request.getParameter("des_campo_" + cod_lang)));
                        st.setString(5, (request.getParameter("err_msg_" + cod_lang) == null || request.getParameter("err_msg_" + cod_lang).equals("") ? null : Utility.testoADb(request.getParameter("err_msg_" + cod_lang))));
                        st.executeUpdate();
                        Utility.chiusuraJdbc(rs);
                        Utility.chiusuraJdbc(st);
                    }
                }
            }
            JSONArray jsa = new JSONArray();
            Set<String> keyVal = null;

            if (request.getParameter("tipo_hidden").equals("L")) {
                if (session.getAttribute("anagrafica_new") != null) {
                    jsa = (JSONArray) session.getAttribute("anagrafica_new");
                    JSONObject el = null;
                    query = "insert into anagrafica_campi_valori (nome,val_select) values (?,?)";
                    String query_testi = "insert into anagrafica_campi_valori_testi (nome,val_select,cod_lang,des_valore) values (?,?,?,?)";
                    for (int i = 0; i < jsa.size(); i++) {
                        el = (JSONObject) jsa.get(i);
                        st = conn.prepareStatement(query);
                        st.setString(1, request.getParameter("nome"));
                        st.setString(2, el.get("val_select").toString());
                        st.executeUpdate();
                        Utility.chiusuraJdbc(st);
                        keyVal = el.keySet();
                        for (String key : keyVal) {
                            String[] parSplit = key.split("des_valore_");
                            if (parSplit.length > 1 && key.contains("des_valore_")) {
                                String cod_lang = parSplit[1];
                                if (cod_lang != null) {
                                    st = conn.prepareStatement(query_testi);
                                    st.setString(1, request.getParameter("nome"));
                                    st.setString(2, el.get("val_select").toString());
                                    st.setString(3, cod_lang);
                                    st.setString(4, el.get("des_valore_" + cod_lang).toString());
                                    st.executeUpdate();
                                    Utility.chiusuraJdbc(rs);
                                    Utility.chiusuraJdbc(st);
                                }
                            }
                        }

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
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject aggiorna(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        PreparedStatement stRead = null;
        ResultSet rsRead = null;
        JSONObject ret = null;
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();

            String query = "update anagrafica "
                    + "set riga = ?, "
                    + "posizione = ?, "
                    + "tp_riga=?, "
                    + "tipo = ?, "
                    + "valore = ?, "
                    + "controllo=?, "
                    + "tp_controllo=?, "
                    + "lunghezza=?, "
                    + "decimali=?, "
                    + "edit=?, "
                    + "raggruppamento_check=?, "
                    + "campo_collegato=?, "
                    + "val_campo_collegato=?, "
                    + "livello=?, "
                    + "web_serv=? , "
                    + "nome_xsd=? , "
                    + "campo_key=? , "
                    + "campo_dati=? ,"
                    + "campo_xml_mod=? , "
                    + "precompilazione=?,  "
                    + "flg_precompilazione= ?, "
                    + "azione=?, "
                    + "pattern=?, "
                    + "marcatore_incrociato=?, "
                    + "campo_titolare=? "
                    + "where nome=? "
                    + "and contatore=?";
            st = conn.prepareStatement(query);
            st.setInt(1, Integer.parseInt(request.getParameter("riga")));
            st.setInt(2, Integer.parseInt(request.getParameter("posizione")));
            st.setString(3, request.getParameter("tp_riga_hidden"));
            st.setString(4, request.getParameter("tipo_hidden"));
            st.setString(5, (request.getParameter("valore") == null || request.getParameter("valore").equals("") ? null : Utility.testoADb(request.getParameter("valore"))));
            st.setString(6, request.getParameter("controllo_hidden"));
            st.setString(7, (request.getParameter("tp_controllo_hidden") == null || request.getParameter("tp_controllo_hidden").equals("") ? "T" : request.getParameter("tp_controllo_hidden")));
            st.setInt(8, (request.getParameter("lunghezza") == null || request.getParameter("lunghezza").equals("") ? 30 : Integer.parseInt(request.getParameter("lunghezza"))));
            st.setInt(9, (request.getParameter("decimali") == null || request.getParameter("decimali").equals("") ? 0 : Integer.parseInt(request.getParameter("decimali"))));
            st.setString(10, request.getParameter("edit_hidden"));
            st.setString(11, (request.getParameter("raggruppamento_check") == null || request.getParameter("raggruppamento_check").equals("") ? null : Utility.testoADb(request.getParameter("raggruppamento_check"))));
            st.setString(12, (request.getParameter("campo_collegato") == null || request.getParameter("campo_collegato").equals("") ? null : Utility.testoADb(request.getParameter("campo_collegato"))));
            st.setString(13, (request.getParameter("val_campo_collegato") == null || request.getParameter("val_campo_collegato").equals("") ? null : Utility.testoADb(request.getParameter("val_campo_collegato"))));
            st.setInt(14, Integer.parseInt(request.getParameter("livello")));
            st.setString(15, (request.getParameter("web_serv") == null || request.getParameter("web_serv").equals("") ? null : request.getParameter("web_serv")));
            st.setString(16, (request.getParameter("nome_xsd") == null || request.getParameter("nome_xsd").equals("") ? null : request.getParameter("nome_xsd")));
            st.setString(17, (request.getParameter("campo_key") == null || request.getParameter("campo_key").equals("") ? null : request.getParameter("campo_key")));
            st.setString(18, (request.getParameter("campo_dati") == null || request.getParameter("campo_dati").equals("") ? null : request.getParameter("campo_dati")));
            st.setString(19, (request.getParameter("campo_xml_mod") == null || request.getParameter("campo_xml_mod").equals("") ? null : request.getParameter("campo_xml_mod")));
            st.setString(20, (request.getParameter("precompilazione") == null || request.getParameter("precompilazione").equals("") ? "" : request.getParameter("precompilazione")));
            st.setString(21, request.getParameter("flg_precompilazione_hidden"));
            st.setString(22, (request.getParameter("azione") != null && !request.getParameter("azione").equals("") ? request.getParameter("azione") : null));
            st.setString(23, (request.getParameter("pattern") != null && !request.getParameter("pattern").equals("") ? request.getParameter("pattern") : null));
            st.setString(24, (request.getParameter("marcatore_incrociato") != null && !request.getParameter("marcatore_incrociato").equals("") ? request.getParameter("marcatore_incrociato") : null));
            st.setString(25, (request.getParameter("campo_titolare_hidden") != null && !request.getParameter("campo_titolare_hidden").equals("") ? request.getParameter("campo_titolare_hidden") : null));
            st.setString(26, request.getParameter("nome"));
            st.setInt(27, Integer.parseInt(request.getParameter("contatore")));
            st.executeUpdate();
            st.close();

            String querySelect = "select * from anagrafica_testi where contatore = ? and nome=? and cod_lang = ?";
            String queryInsert = "insert into anagrafica_testi (contatore,nome,cod_lang,des_campo,err_msg) values (?,?,?,?,? )";

            query = "update anagrafica_testi "
                    + "set des_campo=?, "
                    + "err_msg=? "
                    + "where nome=? "
                    + "and cod_lang = ?"
                    + "and contatore=?";
            Set<String> parmKey = request.getParameterMap().keySet();
            for (String key : parmKey) {
                String[] parSplit = key.split("des_campo_");
                if (parSplit.length > 1 && key.contains("des_campo_")) {
                    String cod_lang = parSplit[1];
                    if (cod_lang != null) {
                        stRead = conn.prepareStatement(querySelect);
                        stRead.setInt(1, Integer.parseInt(request.getParameter("contatore")));
                        stRead.setString(2, request.getParameter("nome"));
                        stRead.setString(3, cod_lang);
                        rsRead = stRead.executeQuery();
                        if (rsRead.next()) {
                            st = conn.prepareStatement(query);
                            st.setString(1, Utility.testoADb(request.getParameter(key)));
                            st.setString(2, ((request.getParameter("err_msg_" + cod_lang)) == null || request.getParameter("err_msg_" + cod_lang).equals("") ? null : Utility.testoADb(request.getParameter("err_msg_" + cod_lang))));
                            st.setString(3, request.getParameter("nome"));
                            st.setString(4, cod_lang);
                            st.setInt(5, Integer.parseInt(request.getParameter("contatore")));
                            st.executeUpdate();
                            Utility.chiusuraJdbc(rs);
                            Utility.chiusuraJdbc(st);
                        } else {
                            st = conn.prepareStatement(queryInsert);
                            st.setInt(1, Integer.parseInt(request.getParameter("contatore")));
                            st.setString(2, request.getParameter("nome"));
                            st.setString(3, cod_lang);
                            st.setString(4, Utility.testoADb(request.getParameter(key)));
                            st.setString(5, ((request.getParameter("err_msg_" + cod_lang)) == null || request.getParameter("err_msg_" + cod_lang).equals("") ? null : Utility.testoADb(request.getParameter("err_msg_" + cod_lang))));
                            st.execute();
                            Utility.chiusuraJdbc(rs);
                            Utility.chiusuraJdbc(st);
                        }
                        Utility.chiusuraJdbc(rsRead);
                        Utility.chiusuraJdbc(stRead);
                    }
                }
            }

            query = "delete from anagrafica_campi_valori where nome= ?";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("nome"));
            st.executeUpdate();
            Utility.chiusuraJdbc(st);
            query = "delete from anagrafica_campi_valori_testi where nome= ?";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("nome"));
            st.executeUpdate();
            Utility.chiusuraJdbc(st);

            Set<String> keyVal = null;
            JSONArray jsa = new JSONArray();
            if (request.getParameter("tipo_hidden").equals("L")) {
                if (session.getAttribute("anagrafica_new") != null) {
                    jsa = (JSONArray) session.getAttribute("anagrafica_new");
                    JSONObject el = null;
                    query = "insert into anagrafica_campi_valori (nome,val_select) values (?,?)";
                    String query_testi = "insert into anagrafica_campi_valori_testi (nome,val_select,cod_lang,des_valore) values (?,?,?,?)";
                    for (int i = 0; i < jsa.size(); i++) {
                        el = (JSONObject) jsa.get(i);
                        st = conn.prepareStatement(query);
                        st.setString(1, request.getParameter("nome"));
                        st.setString(2, el.get("val_select").toString());
                        st.executeUpdate();
                        Utility.chiusuraJdbc(st);
                        keyVal = el.keySet();
                        for (String key : keyVal) {
                            String[] parSplit = key.split("des_valore_");
                            if (parSplit.length > 1 && key.contains("des_valore_")) {
                                String cod_lang = parSplit[1];
                                if (cod_lang != null) {
                                    st = conn.prepareStatement(query_testi);
                                    st.setString(1, request.getParameter("nome"));
                                    st.setString(2, el.get("val_select").toString());
                                    st.setString(3, cod_lang);
                                    st.setString(4, el.get("des_valore_" + cod_lang).toString());
                                    st.executeUpdate();
                                    Utility.chiusuraJdbc(rs);
                                    Utility.chiusuraJdbc(st);
                                }
                            }
                        }

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

    public JSONObject cancella(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();

            String query = "delete from anagrafica_testi where nome= ? and contatore = ?";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("nome"));
            st.setInt(2, Integer.parseInt(request.getParameter("contatore")));
            st.executeUpdate();
            st.close();
            query = "delete from anagrafica where nome= ? and contatore = ?";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("nome"));
            st.setInt(2, Integer.parseInt(request.getParameter("contatore")));
            st.executeUpdate();
            st.close();
            query = "delete from anagrafica_campi_valori where nome= ?";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("nome"));
            st.executeUpdate();
            st.close();
            query = "delete from anagrafica_campi_valori_testi where nome= ?";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("nome"));
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

    public JSONObject pulisciCampoSelectSession(HttpServletRequest request) throws Exception {
        JSONObject ret = null;

        try {
            HttpSession session = request.getSession();
            if (session.getAttribute("anagrafica_new") != null) {
                session.removeAttribute("anagrafica_new");
            }
            ret = new JSONObject();

            ret.put("success", "Aggiornamento effettuato");
        } catch (Exception e) {
            ret.put("failure", e.getMessage());
        } finally {
        }
        return ret;
    }

    public JSONObject aggiungiCampoSelectSession(HttpServletRequest request) throws Exception {
        JSONObject ret = new JSONObject();
        JSONArray jsa = null;
        try {
            HttpSession session = request.getSession();
            if (session.getAttribute("anagrafica_new") != null) {
                jsa = (JSONArray) session.getAttribute("anagrafica_new");
            } else {
                jsa = new JSONArray();
            }
            boolean trovato = false;
            JSONObject el = null;
            for (int i = 0; i < jsa.size(); i++) {
                el = (JSONObject) jsa.get(i);
                if (el.get("val_select").equals(request.getParameter("val_select"))) {
                    trovato = true;
                    break;
                }
            }

            if (!trovato) {
                el = new JSONObject();
                el.put("val_select", request.getParameter("val_select"));
                Set<String> keyVal = request.getParameterMap().keySet();
                for (String key : keyVal) {
                    String[] parSplit = key.split("des_valore_");
                    if (parSplit.length > 1 && key.contains("des_valore_")) {
                        String cod_lang = parSplit[1];
                        if (cod_lang != null) {
                            el.put("des_valore_" + cod_lang, Utility.testoADb(request.getParameter("des_valore_" + cod_lang)));
                        }
                    }
                }
                jsa.add(el);
                session.setAttribute("anagrafica_new", jsa);
                ret.put("success", "Aggiornamento effettuato");
            } else {
                ret.put("failure", "Record giÃ  presente");
            }
        } catch (Exception e) {
            ret.put("failure", e.getMessage());
        } finally {
        }
        return ret;
    }

    public JSONObject cancellaCampoSelectSession(HttpServletRequest request) throws Exception {
        JSONObject ret = new JSONObject();
        JSONArray jsa = null;
        try {
            HttpSession session = request.getSession();
            if (session.getAttribute("anagrafica_new") != null) {
                jsa = (JSONArray) session.getAttribute("anagrafica_new");
            } else {
                jsa = new JSONArray();
            }
            JSONObject el = null;
            for (int i = 0; i < jsa.size(); i++) {
                el = (JSONObject) jsa.get(i);
                if (el.get("val_select").equals(request.getParameter("val_select"))) {
                    jsa.remove(i);
                    break;
                }
            }
            session.setAttribute("anagrafica_new", jsa);
            ret.put("success", "Aggiornamento effettuato");
        } catch (Exception e) {
            ret.put("failure", e.getMessage());
        } finally {
        }
        return ret;
    }

    public JSONObject actionAnagraficaSelect(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        PreparedStatement stLang = null;
        ResultSet rsLang = null;
        JSONObject ret = null;
        int righe = 0;
        HttpSession session = request.getSession();
        try {
            conn = Utility.getConnection();
            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            if (request.getParameter("session") != null && request.getParameter("session").equals("yes")) {
                if (session.getAttribute("anagrafica_new") != null) {
                    riga = (JSONArray) session.getAttribute("anagrafica_new");
                }
            } else {
                String queryLang = "select des_valore, cod_lang from anagrafica_campi_valori_testi where nome = ? and val_select=?";
                String query = "Select a.val_select, b.des_valore "
                        + "from anagrafica_campi_valori a "
                        + "join anagrafica_campi_valori_testi b "
                        + "on a.val_select=b.val_select "
                        + "and a.nome=b.nome "
                        + "where a.nome= ? and b.cod_lang='it'";
                st = conn.prepareStatement(query);
                st.setString(1, request.getParameter("nome"));
                rs = st.executeQuery();
                while (rs.next()) {
                    loopObj = new JSONObject();
                    loopObj.put("val_select", rs.getString("val_select"));
                    loopObj.put("des_valore", rs.getString("des_valore"));
                    stLang = conn.prepareStatement(queryLang);
                    stLang.setString(1, request.getParameter("nome"));
                    stLang.setString(2, rs.getString("val_select"));
                    rsLang = stLang.executeQuery();
                    while (rsLang.next()) {
                        loopObj.put("des_valore_" + rsLang.getString("cod_lang"), Utility.testoDaDb(rsLang.getString("des_valore")));
                    }
                    Utility.chiusuraJdbc(rsLang);
                    Utility.chiusuraJdbc(stLang);
                    riga.add(loopObj);
                }
                session.setAttribute("anagrafica_new", riga);
            }

            ret = new JSONObject();
            ret.put("totalCount", riga.size());
            ret.put("anagrafica_select", riga);
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(rsLang);
            Utility.chiusuraJdbc(stLang);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }
}
