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

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;

import it.people.dbm.model.Modifica;
import it.people.dbm.model.Utente;
import it.people.dbm.utility.Comparatore;
import it.people.dbm.utility.ComparatoreValSelect;
import it.people.dbm.utility.Configuration;
import it.people.dbm.utility.RisaliGerarchiaUtenti;
import it.people.dbm.utility.Utility;
import it.people.dbm.xsd.sqlNotifiche.DocumentRoot;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.slf4j.LoggerFactory;
/**
 *
 * @author Piergiorgio
 */
public class Href {

    private static Logger log = LoggerFactory.getLogger(Href.class);

    public JSONObject action(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        PreparedStatement stLang = null;
        ResultSet rsLang = null;
        JSONObject ret = null;
        int righe = 0;
        int qsi = 1;
        Utente utente = (Utente) session.getAttribute("utente");
        try {
            conn = Utility.getConnection();
            String offset = request.getParameter("start");
            String size = request.getParameter("size");
            String sort = request.getParameter("sort");
            String order = request.getParameter("dir");
            String param = Utility.testoADb(request.getParameter("query"));

            String query = "select count(*) righe "
                    + "from ( select a.href,b.tit_href,b.piede_href, a.tip_aggregazione "
                    + "from href a "
                    + "join href_testi b "
                    + "on a.href = b.href "
                    + "and b.cod_lang='it' ";
            if (utente.getRuolo().equals("A") || utente.getRuolo().equals("B")) {
                query = query + "join (select b.href from allegati a "
                        + "join documenti b "
                        + "on a.cod_doc = b.cod_doc "
                        + "join utenti_interventi c "
                        + "on a.cod_int = c.cod_int "
                        + "where c.cod_utente = '" + utente.getCodUtente() + "' and b.href is not null) x on a.href=x.href  "
                        + "union "
                        + "select a.href, b.tit_href, b.piede_href, a.tip_aggregazione "
                        + "from href a "
                        + "join href_testi b "
                        + "on a.href = b.href "
                        + "and b.cod_lang='it' "
                        + "left join documenti c "
                        + "on a.href = c.href "
                        + "where c.href is null and a.href not in (select href from tipi_aggregazione where href is not null) ";
            }
            query = query + ") p "
                    + "where p.href like ? or p.tit_href like ? ";
            if (!(Boolean) session.getAttribute("territorialitaNew")) {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and p.tip_aggregazione=? ";
                }
            }
            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            if (!(Boolean) session.getAttribute("territorialitaNew")) {
                if (utente.getTipAggregazione() != null) {
                    st.setString(3, utente.getTipAggregazione());
                }
            }
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);

            query = "select p.href href, p.tit_href tit_href, p.piede_href piede_href, p.tip_aggregazione, p.des_aggregazione "
                    + "from ( select a.href,b.tit_href, b.piede_href, a.tip_aggregazione, e.des_aggregazione "
                    + "from href a "
                    + "join href_testi b "
                    + "on a.href = b.href "
                    + "and b.cod_lang='it' "
                    + "left join tipi_aggregazione e "
                    + "on a.tip_aggregazione = e.tip_aggregazione ";
            if (utente.getRuolo().equals("A") || utente.getRuolo().equals("B")) {
                query = query + "join (select b.href from allegati a "
                        + "join documenti b "
                        + "on a.cod_doc = b.cod_doc "
                        + "join utenti_interventi c "
                        + "on a.cod_int = c.cod_int "
                        + "where c.cod_utente = '" + utente.getCodUtente() + "' and b.href is not null) x on a.href=x.href  "
                        + "union "
                        + "select a.href, b.tit_href, b.piede_href, a.tip_aggregazione, e.des_aggregazione "
                        + "from href a "
                        + "left join tipi_aggregazione e "
                        + "on a.tip_aggregazione = e.tip_aggregazione "
                        + "join href_testi b "
                        + "on a.href = b.href "
                        + "and b.cod_lang='it' "
                        + "left join documenti c "
                        + "on a.href = c.href "
                        + "where c.href is null and a.href not in (select href from tipi_aggregazione where href is not null) ";
            }
            query = query + ") p "
                    + "where (p.href like ? or p.tit_href like ?) ";
            if (!(Boolean) session.getAttribute("territorialitaNew")) {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and p.tip_aggregazione=? ";
                }
            }
            query = query + "order by " + sort + " " + order + " limit ? , ?";

            st = conn.prepareStatement(query);
            st.setString(qsi, "%" + param + "%");
            qsi++;
            st.setString(qsi, "%" + param + "%");
            qsi++;
            if (!(Boolean) session.getAttribute("territorialitaNew")) {
                if (utente.getTipAggregazione() != null) {
                    st.setString(qsi, utente.getTipAggregazione());
                    qsi++;
                }
            }
            st.setInt(qsi, Integer.parseInt(offset));
            qsi++;
            st.setInt(qsi, Integer.parseInt(size));
            rs = st.executeQuery();

            String queryLang = "select tit_href,piede_href, cod_lang from href_testi where href = ?";
            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("href", rs.getString("href"));
                loopObj.put("tip_aggregazione", rs.getString("tip_aggregazione"));
                loopObj.put("des_aggregazione", rs.getString("des_aggregazione"));
                stLang = conn.prepareStatement(queryLang);
                stLang.setString(1, rs.getString("href"));
                rsLang = stLang.executeQuery();
                while (rsLang.next()) {
                    loopObj.put("tit_href_" + rsLang.getString("cod_lang"), Utility.testoDaDb(rsLang.getString("tit_href")));
                    loopObj.put("piede_href_" + rsLang.getString("cod_lang"), Utility.testoDaDb(rsLang.getString("piede_href")));
                }
                Utility.chiusuraJdbc(rsLang);
                Utility.chiusuraJdbc(stLang);
                riga.add(loopObj);

            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("href", riga);
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(rsLang);
            Utility.chiusuraJdbc(stLang);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    
    public JSONObject actionTotali(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        int qsi = 1;
        Utente utente = (Utente) session.getAttribute("utente");
        try {
            conn = Utility.getConnection();
            String offset = request.getParameter("start");
            String size = request.getParameter("size");
            String sort = request.getParameter("sort");
            String order = request.getParameter("dir");
            String param = Utility.testoADb(request.getParameter("query"));

            String query = "select count(*) righe "
                    + "from ( select a.href,b.tit_href ,a.tip_aggregazione "
                    + "from href a "
                    + "join href_testi b "
                    + "on a.href = b.href "
                    + "and b.cod_lang='it' ";
            if (utente.getRuolo().equals("A") || utente.getRuolo().equals("B")) {
                query = query + "join (select b.href from allegati a "
                        + "join documenti b "
                        + "on a.cod_doc = b.cod_doc "
                        + "join utenti_interventi c "
                        + "on a.cod_int = c.cod_int "
                        + "where c.cod_utente = '" + utente.getCodUtente() + "' and b.href is not null) x on a.href=x.href  "
                        + "union "
                        + "select a.href,b.tit_href,a.tip_aggregazione "
                        + "from href a "
                        + "join href_testi b "
                        + "on a.href = b.href "
                        + "and b.cod_lang='it' "
                        + "left join documenti c "
                        + "on a.href = c.href "
                        + "where c.href is null and a.href not in (select href from tipi_aggregazione where href is not null) ";
            }
            query = query + ") p "
                    + "where p.href like ? or p.tit_href like ? ";
            if (!(Boolean) session.getAttribute("territorialitaNew")) {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and (p.tip_aggregazione=?) ";
                }
            }
            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            if (!(Boolean) session.getAttribute("territorialitaNew")) {
                if (utente.getTipAggregazione() != null) {
                    st.setString(3, utente.getTipAggregazione());
                }
            }
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            query = "select p.href href, p.tit_href tit_href  "
                    + "from ( select a.href,b.tit_href,a.tip_aggregazione "
                    + "from href a "
                    + "join href_testi b "
                    + "on a.href = b.href "
                    + "and b.cod_lang='it' ";
            if (utente.getRuolo().equals("A") || utente.getRuolo().equals("B")) {
                query = query + "join (select b.href from allegati a "
                        + "join documenti b "
                        + "on a.cod_doc = b.cod_doc "
                        + "join utenti_interventi c "
                        + "on a.cod_int = c.cod_int "
                        + "where c.cod_utente = '" + utente.getCodUtente() + "' and b.href is not null) x on a.href=x.href  "
                        + "union "
                        + "select a.href,b.tit_href,a.tip_aggregazione "
                        + "from href a "
                        + "join href_testi b "
                        + "on a.href = b.href "
                        + "and b.cod_lang='it' "
                        + "left join documenti c "
                        + "on a.href = c.href "
                        + "where c.href is null and a.href not in (select href from tipi_aggregazione where href is not null) ";
            }
            query = query + ") p "
                    + "where (p.href like ? or p.tit_href like ?) ";
            if (!(Boolean) session.getAttribute("territorialitaNew")) {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and (p.tip_aggregazione=?) ";
                }
            }
            query = query + "order by " + sort + " " + order + " limit ? , ?";

            st = conn.prepareStatement(query);
            st.setString(qsi, "%" + param + "%");
            qsi++;
            st.setString(qsi, "%" + param + "%");
            qsi++;
            if (!(Boolean) session.getAttribute("territorialitaNew")) {
                if (utente.getTipAggregazione() != null) {
                    st.setString(qsi, utente.getTipAggregazione());
                    qsi++;
                }
            }
            st.setInt(qsi, Integer.parseInt(offset));
            qsi++;
            st.setInt(qsi, Integer.parseInt(size));
            rs = st.executeQuery();
            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("href", rs.getString("href"));
                loopObj.put("tit_href", Utility.testoDaDb(rs.getString("tit_href")));
                riga.add(loopObj);

            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("href", riga);
            return ret;
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
    }

    
    
    public JSONObject actionHrefCampi(HttpServletRequest request) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        PreparedStatement st1 = null;
        PreparedStatement stLang = null;
        ResultSet rsLang = null;
        ResultSet rs1 = null;

        JSONObject ret = null;
        int righe = 0;
        try {
            conn = Utility.getConnection();
            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            JSONArray rigaVal = new JSONArray();
            JSONObject valObj;
            ret = new JSONObject();
            String query = "Select count(*) righe "
                    + "from href_campi a "
                    + "join href_campi_testi b "
                    + "on a.href=b.href "
                    + "and a.contatore=b.contatore "
                    + "and a.nome=b.nome "
                    + "where a.href=? "
                    + "and b.cod_lang='it'";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("href"));
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);

            query = "select a.contatore contatore,a.href href, a.nome nome, b.des_campo des_campo,a.riga riga,"
                    + "a.posizione posizione,a.tp_riga tp_riga,a.tipo tipo,a.valore valore,"
                    + "a.controllo controllo,a.tp_controllo tp_controllo,a.lunghezza lunghezza,"
                    + "a.decimali decimali,a.edit edit,a.raggruppamento_check raggruppamento_check,"
                    + "a.campo_collegato campo_collegato,a.val_campo_collegato val_campo_collegato,"
                    + "a.precompilazione precompilazione,a.web_serv web_serv,"
                    + "a.campo_dati campo_dati,a.campo_key campo_key,a.nome_xsd nome_xsd,"
                    + "a.campo_xml_mod campo_xml_mod, a.marcatore_incrociato marcatore_incrociato, b.err_msg err_msg, a.pattern pattern "
                    + "from href_campi a "
                    + "join href_campi_testi b "
                    + "on a.href=b.href "
                    + "and a.contatore=b.contatore "
                    + "and a.nome=b.nome "
                    + "where a.href=? "
                    + "and b.cod_lang='it' "
                    + "order by riga, posizione, nome";

            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("href"));
            rs = st.executeQuery();
            String queryLang = "select des_campo,err_msg, cod_lang from href_campi_testi where href = ? and contatore=? and nome=?";
            String queryLangValue = "select des_valore, cod_lang from href_campi_valori_testi where href = ? and nome=? and val_select=?";

            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("href", rs.getString("href"));
                loopObj.put("nome", rs.getString("nome"));
                loopObj.put("contatore", Integer.toString(rs.getInt("contatore")));
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
                loopObj.put("precompilazione", rs.getString("precompilazione"));
                loopObj.put("web_serv", rs.getString("web_serv"));
                loopObj.put("campo_dati", rs.getString("campo_dati"));
                loopObj.put("campo_key", rs.getString("campo_key"));
                loopObj.put("nome_xsd", rs.getString("nome_xsd"));
                loopObj.put("campo_xml_mod", rs.getString("campo_xml_mod"));
                loopObj.put("marcatore_incrociato", rs.getString("marcatore_incrociato"));
                loopObj.put("pattern", rs.getString("pattern"));
                stLang = conn.prepareStatement(queryLang);
                stLang.setString(1, rs.getString("href"));
                stLang.setInt(2, rs.getInt("contatore"));
                stLang.setString(3, rs.getString("nome"));
                rsLang = stLang.executeQuery();
                while (rsLang.next()) {
                    loopObj.put("des_campo_" + rsLang.getString("cod_lang"), Utility.testoDaDb(rsLang.getString("des_campo")));
                    loopObj.put("err_msg_" + rsLang.getString("cod_lang"), Utility.testoDaDb(rsLang.getString("err_msg")));
                }
                Utility.chiusuraJdbc(rsLang);
                Utility.chiusuraJdbc(stLang);

                rigaVal = new JSONArray();
                query = "select a.href,a.nome,a.val_select, b.des_valore "
                        + "from href_campi_valori a "
                        + "join href_campi_valori_testi b "
                        + "on a.href=b.href "
                        + "and a.nome=b.nome "
                        + "and a.val_select=b.val_select "
                        + "where a.href=? "
                        + "and a.nome=? "
                        + "and b.cod_lang='it'";
                st1 = conn.prepareStatement(query);
                st1.setString(1, request.getParameter("href"));
                st1.setString(2, rs.getString("nome"));
                rs1 = st1.executeQuery();

                while (rs1.next()) {
                    valObj = new JSONObject();
                    valObj.put("href", rs1.getString("href"));
                    valObj.put("nome", rs1.getString("nome"));
                    valObj.put("val_select", rs1.getString("val_select"));
                    stLang = conn.prepareStatement(queryLangValue);
                    stLang.setString(1, rs1.getString("href"));
                    stLang.setString(2, rs1.getString("nome"));
                    stLang.setString(3, rs1.getString("val_select"));
                    rsLang = stLang.executeQuery();
                    while (rsLang.next()) {
                        valObj.put("des_valore_" + rsLang.getString("cod_lang"), Utility.testoDaDb(rsLang.getString("des_valore")));
                    }
                    Utility.chiusuraJdbc(rsLang);
                    Utility.chiusuraJdbc(stLang);
                    rigaVal.add(valObj);
                }
                loopObj.put("combobox", rigaVal);

                Utility.chiusuraJdbc(rs1);
                Utility.chiusuraJdbc(st1);

                riga.add(loopObj);

                if (ret == null) {
                    ret = new JSONObject();
                }

            }
            ret.put("totalCount", righe);
            ret.put("href_campi", riga);
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(rs1);
            Utility.chiusuraJdbc(st1);
            Utility.chiusuraJdbc(rsLang);
            Utility.chiusuraJdbc(stLang);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject inserisci(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        String query;
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            JSONArray hrefCampi = (JSONArray) JSONSerializer.toJSON(request.getParameter("hrefCampi"));
            query = "insert into href (href,tp_href,tip_aggregazione) "
                    + "values (?,'S',?)";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("href"));
            st.setString(2, request.getParameter("tip_aggregazione"));
            st.executeUpdate();
            Utility.chiusuraJdbc(st);
            query = "insert into href_testi (href,tit_href,piede_href,cod_lang) "
                    + "values (?,?,?,?)";
            Set<String> parmKey = request.getParameterMap().keySet();
            for (String key : parmKey) {
                String[] parSplit = key.split("tit_href_");
                if (parSplit.length > 1 && key.contains("tit_href_")) {
                    String cod_lang = parSplit[1];
                    if (cod_lang != null) {
                        st = conn.prepareStatement(query);
                        st.setString(1, request.getParameter("href"));
                        st.setString(2, Utility.testoADb(request.getParameter("tit_href_" + cod_lang)));
                        st.setString(3, (request.getParameter("piede_href") == null || request.getParameter("piede_href").equals("") ? null : Utility.testoADb(request.getParameter("piede_href"))));
                        st.setString(4, cod_lang);
                        st.executeUpdate();
                        Utility.chiusuraJdbc(rs);
                        Utility.chiusuraJdbc(st);
                    }
                }
            }

            JSONObject jo = null;
            for (int i = 0; i < hrefCampi.size(); i++) {
                jo = (JSONObject) hrefCampi.get(i);
                query = "select IFNULL(MAX(contatore),0)+1 contatore from href_campi where href=? and nome = ?";
                st = conn.prepareStatement(query);
                st.setString(1, request.getParameter("href"));
                st.setString(2, Utility.testoADb((String) jo.get("nome")));
                rs = st.executeQuery();
                int contatore = 0;
                while (rs.next()) {
                    contatore = rs.getInt("contatore");
                }
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);
                query = "insert into href_campi (href, nome, riga, posizione, tp_riga, tipo, valore, controllo,"
                        + " tp_controllo, lunghezza, decimali, edit, web_serv, nome_xsd, campo_key,"
                        + " campo_dati, campo_xml_mod, raggruppamento_check, campo_collegato, val_campo_collegato,"
                        + " marcatore_incrociato, precompilazione, contatore, pattern) values (?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                st = conn.prepareStatement(query);
                st.setString(1, request.getParameter("href"));
                st.setString(2, Utility.testoADb((String) jo.getString("nome")));
                st.setInt(3, (Integer) jo.getInt("riga"));
                st.setInt(4, (Integer) jo.getInt("posizione"));
                st.setString(5, (String) jo.getString("tp_riga"));
                st.setString(6, (String) jo.getString("tipo"));
                st.setString(7, (((String) jo.getString("valore")) == null || ((String) jo.getString("valore")).equals("") ? null : Utility.testoADb((String) jo.getString("valore"))));
                st.setString(8, ((String) jo.getString("controllo") == null || ((String) jo.getString("controllo")).equals("") ? "" : (String) jo.getString("controllo")));
                st.setString(9, ((String) jo.getString("tp_controllo") == null || ((String) jo.getString("tp_controllo")).equals("") ? "T" : (String) jo.getString("tp_controllo")));
                Object ob = (Object) jo.get("lunghezza");
                String type = ob.getClass().getName();
                if (type.equals(String.class.getName())) {
                    st.setInt(10, 30);
                }
                if (type.equals(Integer.class.getName())) {
                    st.setInt(10, (Integer) jo.getInt("lunghezza"));
                }
                ob = (Object) jo.get("decimali");
                type = ob.getClass().getName();
                if (type.equals(String.class.getName())) {
                    st.setInt(11, 0);
                }
                if (type.equals(Integer.class.getName())) {
                    st.setInt(11, (Integer) jo.getInt("decimali"));
                }

                st.setString(12, (String) jo.getString("edit"));
                st.setString(13, ((String) jo.getString("web_serv") == null || ((String) jo.getString("web_serv")).equals("") ? null : (String) jo.getString("web_serv")));
                st.setString(14, ((String) jo.getString("nome_xsd") == null || ((String) jo.getString("nome_xsd")).equals("") ? null : (String) jo.getString("nome_xsd")));
                st.setString(15, ((String) jo.getString("campo_key") == null || ((String) jo.getString("campo_key")).equals("") ? null : (String) jo.getString("campo_key")));
                st.setString(16, ((String) jo.getString("campo_dati") == null || ((String) jo.getString("campo_dati")).equals("") ? null : (String) jo.getString("campo_dati")));
                st.setString(17, ((String) jo.getString("campo_xml_mod") == null || ((String) jo.getString("campo_xml_mod")).equals("") ? null : (String) jo.getString("campo_xml_mod")));
                st.setString(18, ((String) jo.getString("raggruppamento_check") == null || ((String) jo.getString("raggruppamento_check")).equals("") ? null : Utility.testoADb((String) jo.getString("raggruppamento_check"))));
                st.setString(19, ((String) jo.getString("campo_collegato") == null || ((String) jo.getString("campo_collegato")).equals("") ? null : Utility.testoADb((String) jo.getString("campo_collegato"))));
                st.setString(20, ((String) jo.getString("val_campo_collegato") == null || ((String) jo.getString("val_campo_collegato")).equals("") ? null : Utility.testoADb((String) jo.getString("val_campo_collegato"))));
                st.setString(21, ((String) jo.getString("marcatore_incrociato") == null || ((String) jo.getString("marcatore_incrociato")).equals("") ? "" : (String) jo.getString("marcatore_incrociato")));
                st.setString(22, ((String) jo.getString("precompilazione") == null || ((String) jo.getString("precompilazione")).equals("") ? "" : (String) jo.getString("precompilazione")));
                st.setInt(23, contatore);
                st.setString(24, ((String) jo.getString("pattern") == null || ((String) jo.getString("pattern")).equals("") ? null : (String) jo.getString("pattern")));
                st.executeUpdate();
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);

                query = "insert into href_campi_testi (href, nome, cod_lang, des_campo, contatore, err_msg) "
                        + "values (? ,?, ?, ?, ?, ?)";

                parmKey = jo.keySet();
                for (String key : parmKey) {
                    String[] parSplit = key.split("des_campo_");
                    if (parSplit.length > 1 && key.contains("des_campo_")) {
                        String cod_lang = parSplit[1];
                        if (cod_lang != null) {
                            st = conn.prepareStatement(query);
                            st.setString(1, request.getParameter("href"));
                            st.setString(2, (String) jo.get("nome"));
                            st.setString(3, cod_lang);
                            st.setString(4, Utility.testoADb((String) jo.getString("des_campo_" + cod_lang)));
                            st.setInt(5, contatore);
                            st.setString(6, ((String) jo.getString("err_msg_" + cod_lang) == null || ((String) jo.getString("err_msg_" + cod_lang)).equals("") ? null : Utility.testoADb((String) jo.getString("err_msg_" + cod_lang))));
                            st.executeUpdate();
                            Utility.chiusuraJdbc(rs);
                            Utility.chiusuraJdbc(st);
                        }
                    }
                }

                if (((String) jo.get("tipo")).equals("L")) {
                    JSONArray ja = (JSONArray) jo.get("combobox");
                    JSONObject jobox = null;
                    for (int j = 0; j < ja.size(); j++) {
                        jobox = (JSONObject) ja.get(j);
                        query = "insert into href_campi_valori (href, nome, val_select) "
                                + "values (?, ?, ?)";

                        st = conn.prepareStatement(query);
                        st.setString(1, request.getParameter("href"));
                        st.setString(2, (String) jo.getString("nome"));
                        st.setString(3, (String) jobox.getString("val_select"));
                        st.executeUpdate();
                        Utility.chiusuraJdbc(rs);
                        Utility.chiusuraJdbc(st);
                        query = "insert into href_campi_valori_testi (href, nome, val_select, des_valore, cod_lang) "
                                + "values (?, ?, ?, ?, ?)";
                        parmKey = jobox.keySet();
                        for (String key : parmKey) {
                            String[] parSplit = key.split("des_valore_");
                            if (parSplit.length > 1 && key.contains("des_valore_")) {
                                String cod_lang = parSplit[1];
                                if (cod_lang != null) {
                                    st = conn.prepareStatement(query);
                                    st.setString(1, request.getParameter("href"));
                                    st.setString(2, (String) jo.get("nome"));
                                    st.setString(3, (String) jobox.getString("val_select"));
                                    st.setString(4, Utility.testoADb((String) jobox.getString("des_valore_" + cod_lang)));
                                    st.setString(5, cod_lang);
                                    st.executeUpdate();
                                    Utility.chiusuraJdbc(rs);
                                    Utility.chiusuraJdbc(st);
                                }
                            }
                        }
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
        String query;
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            JSONArray hrefCampi = (JSONArray) JSONSerializer.toJSON(request.getParameter("hrefCampi"));

            Modifica verifica = verificaNotifica(request.getParameter("href"), hrefCampi, request, conn);
            if (!verifica.isNotifica()) {
                if (verifica.isModificaIntervento()) {
                    String querySelect = "select * from href_testi where href = ? and cod_lang = ?";
                    String queryInsert = "insert into href_testi (href,cod_lang,tit_href,piede_href) values (?,?,?,? )";
                    query = "update href_testi set tit_href=?, piede_href=? where href=? and cod_lang=?";

                    Set<String> parmKey = request.getParameterMap().keySet();
                    for (String key : parmKey) {
                        String[] parSplit = key.split("tit_href_");
                        if (parSplit.length > 1 && key.contains("tit_href_")) {
                            String cod_lang = parSplit[1];
                            if (cod_lang != null) {
                                stRead = conn.prepareStatement(querySelect);
                                stRead.setString(1, request.getParameter("href"));
                                stRead.setString(2, cod_lang);
                                rsRead = stRead.executeQuery();
                                if (rsRead.next()) {
                                    st = conn.prepareStatement(query);
                                    st.setString(1, Utility.testoADb(request.getParameter(key)));
                                    st.setString(2, (request.getParameter("piede_href_" + cod_lang) == null || request.getParameter("piede_href_" + cod_lang).equals("") ? null : Utility.testoADb(request.getParameter("piede_href_" + cod_lang))));
                                    st.setString(3, request.getParameter("href"));
                                    st.setString(4, cod_lang);
                                    st.executeUpdate();

                                } else {
                                    st = conn.prepareStatement(queryInsert);
                                    st.setString(1, request.getParameter("href"));
                                    st.setString(2, cod_lang);
                                    st.setString(3, Utility.testoADb(request.getParameter(key)));
                                    st.setString(4, (request.getParameter("piede_href_" + cod_lang) == null || request.getParameter("piede_href_" + cod_lang).equals("") ? null : Utility.testoADb(request.getParameter("piede_href_" + cod_lang))));
                                    st.execute();

                                }
                                Utility.chiusuraJdbc(rs);
                                Utility.chiusuraJdbc(st);
                                Utility.chiusuraJdbc(rsRead);
                                Utility.chiusuraJdbc(stRead);
                            }
                        }
                    }
                    if ((Boolean) session.getAttribute("territorialitaNew")) {
//                        if (utente.getTipAggregazione() != null) {
                            if (request.getParameter("tip_aggregazione") != null) {
                                query = "update href "
                                        + "set tip_aggregazione = ?"
                                        + "where href =? and tip_aggregazione is null";
                                st = conn.prepareStatement(query);
                                st.setString(1, request.getParameter("tip_aggregazione"));
                                st.setString(2, request.getParameter("href"));
                                st.executeUpdate();
                                Utility.chiusuraJdbc(rs);
                                Utility.chiusuraJdbc(st);
                            }
//                        }
                    }

                    JSONObject jo = null;
                    query = "delete from href_campi_valori_testi where href = ?";
                    st = conn.prepareStatement(query);
                    st.setString(1, request.getParameter("href"));
                    st.executeUpdate();
                    Utility.chiusuraJdbc(st);
                    query = "delete from href_campi_valori where href = ?";
                    st = conn.prepareStatement(query);
                    st.setString(1, request.getParameter("href"));
                    st.executeUpdate();
                    Utility.chiusuraJdbc(st);
                    query = "delete from href_campi_testi where href = ?";
                    st = conn.prepareStatement(query);
                    st.setString(1, request.getParameter("href"));
                    st.executeUpdate();
                    Utility.chiusuraJdbc(st);
                    query = "delete from href_campi where href = ?";
                    st = conn.prepareStatement(query);
                    st.setString(1, request.getParameter("href"));
                    st.executeUpdate();
                    Utility.chiusuraJdbc(st);

                    for (int i = 0; i < hrefCampi.size(); i++) {
                        jo = (JSONObject) hrefCampi.get(i);
                        query = "select IFNULL(MAX(contatore),0)+1 contatore from href_campi where href=? and nome = ?";
                        st = conn.prepareStatement(query);
                        st.setString(1, request.getParameter("href"));
                        st.setString(2, Utility.testoADb((String) jo.get("nome")));
                        rs = st.executeQuery();
                        int contatore = 0;
                        while (rs.next()) {
                            contatore = rs.getInt("contatore");
                        }
                        Utility.chiusuraJdbc(rs);
                        Utility.chiusuraJdbc(st);
                        query = "insert into href_campi ( href, nome, riga, posizione, tp_riga, tipo, valore, controllo,"
                                + " tp_controllo, lunghezza, decimali, edit, web_serv, nome_xsd, campo_key,"
                                + " campo_dati, campo_xml_mod, raggruppamento_check, campo_collegato, val_campo_collegato,"
                                + " marcatore_incrociato, precompilazione, contatore, pattern) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                        st = conn.prepareStatement(query);
                        st.setString(1, request.getParameter("href"));
                        st.setString(2, Utility.testoADb((String) jo.getString("nome")));
                        st.setInt(3, (Integer) jo.getInt("riga"));
                        st.setInt(4, (Integer) jo.getInt("posizione"));
                        st.setString(5, (String) jo.getString("tp_riga"));
                        st.setString(6, (String) jo.getString("tipo"));
                        st.setString(7, (((String) jo.getString("valore")) == null || ((String) jo.getString("valore")).equals("") ? null : Utility.testoADb((String) jo.getString("valore"))));
                        st.setString(8, ((String) jo.getString("controllo") == null || ((String) jo.getString("controllo")).equals("") ? "" : (String) jo.getString("controllo")));
                        st.setString(9, ((String) jo.getString("tp_controllo") == null || ((String) jo.getString("tp_controllo")).equals("") ? "T" : (String) jo.getString("tp_controllo")));
                        Object ob = (Object) jo.get("lunghezza");
                        String type = ob.getClass().getName();
                        if (type.equals(String.class.getName())) {
                            st.setInt(10, 30);
                        }
                        if (type.equals(Integer.class.getName())) {
                            st.setInt(10, (Integer) jo.getInt("lunghezza"));
                        }
                        ob = (Object) jo.get("decimali");
                        type = ob.getClass().getName();
                        if (type.equals(String.class.getName())) {
                            st.setInt(11, 0);
                        }
                        if (type.equals(Integer.class.getName())) {
                            st.setInt(11, (Integer) jo.getInt("decimali"));
                        }

                        st.setString(12, (String) jo.getString("edit"));
                        st.setString(13, ((String) jo.getString("web_serv") == null || ((String) jo.getString("web_serv")).equals("") ? null : (String) jo.getString("web_serv")));
                        st.setString(14, ((String) jo.getString("nome_xsd") == null || ((String) jo.getString("nome_xsd")).equals("") ? null : (String) jo.getString("nome_xsd")));
                        st.setString(15, ((String) jo.getString("campo_key") == null || ((String) jo.getString("campo_key")).equals("") ? null : (String) jo.getString("campo_key")));
                        st.setString(16, ((String) jo.getString("campo_dati") == null || ((String) jo.getString("campo_dati")).equals("") ? null : (String) jo.getString("campo_dati")));
                        st.setString(17, ((String) jo.getString("campo_xml_mod") == null || ((String) jo.getString("campo_xml_mod")).equals("") ? null : (String) jo.getString("campo_xml_mod")));
                        st.setString(18, ((String) jo.getString("raggruppamento_check") == null || ((String) jo.getString("raggruppamento_check")).equals("") ? null : Utility.testoADb((String) jo.getString("raggruppamento_check"))));
                        st.setString(19, ((String) jo.getString("campo_collegato") == null || ((String) jo.getString("campo_collegato")).equals("") ? null : Utility.testoADb((String) jo.getString("campo_collegato"))));
                        st.setString(20, ((String) jo.getString("val_campo_collegato") == null || ((String) jo.getString("val_campo_collegato")).equals("") ? null : Utility.testoADb((String) jo.getString("val_campo_collegato"))));
                        st.setString(21, ((String) jo.getString("marcatore_incrociato") == null || ((String) jo.getString("marcatore_incrociato")).equals("") ? "" : (String) jo.getString("marcatore_incrociato")));
                        st.setString(22, ((String) jo.getString("precompilazione") == null || ((String) jo.getString("precompilazione")).equals("") ? "" : (String) jo.getString("precompilazione")));
                        st.setInt(23, contatore);
                        st.setString(24, (((String) jo.getString("pattern")) == null || ((String) jo.getString("pattern")).equals("") ? null : Utility.testoADb((String) jo.getString("pattern"))));

                        st.executeUpdate();
                        Utility.chiusuraJdbc(rs);
                        Utility.chiusuraJdbc(st);

                        query = "insert into href_campi_testi (href, nome, cod_lang, des_campo, contatore, err_msg) "
                                + "values (? ,?, ?, ?, ?, ?)";

                        parmKey = jo.keySet();
                        for (String key : parmKey) {
                            String[] parSplit = key.split("des_campo_");
                            if (parSplit.length > 1 && key.contains("des_campo_")) {
                                String cod_lang = parSplit[1];
                                if (cod_lang != null) {
                                    st = conn.prepareStatement(query);
                                    st.setString(1, request.getParameter("href"));
                                    st.setString(2, (String) jo.get("nome"));
                                    st.setString(3, cod_lang);
                                    st.setString(4, Utility.testoADb((String) jo.getString("des_campo_" + cod_lang)));
                                    st.setInt(5, contatore);
                                    st.setString(6, ((String) jo.getString("err_msg_" + cod_lang) == null || ((String) jo.getString("err_msg_" + cod_lang)).equals("") ? null : Utility.testoADb((String) jo.getString("err_msg_" + cod_lang))));
                                    st.executeUpdate();
                                    Utility.chiusuraJdbc(rs);
                                    Utility.chiusuraJdbc(st);
                                }
                            }
                        }

                        if (((String) jo.get("tipo")).equals("L")) {
                            JSONArray ja = (JSONArray) jo.get("combobox");
                            JSONObject jobox = null;
                            for (int j = 0; j < ja.size(); j++) {
                                jobox = (JSONObject) ja.get(j);
                                query = "insert into href_campi_valori (href, nome, val_select) "
                                        + "values (?, ?, ?)";

                                st = conn.prepareStatement(query);
                                st.setString(1, request.getParameter("href"));
                                st.setString(2, (String) jo.getString("nome"));
                                st.setString(3, (String) jobox.getString("val_select"));
                                st.executeUpdate();
                                query = "insert into href_campi_valori_testi (href, nome, val_select, des_valore, cod_lang) "
                                        + "values (?, ?, ?, ?, ?)";
                                parmKey = jobox.keySet();
                                for (String key : parmKey) {
                                    String[] parSplit = key.split("des_valore_");
                                    if (parSplit.length > 1 && key.contains("des_valore_")) {
                                        String cod_lang = parSplit[1];
                                        if (cod_lang != null) {
                                            st = conn.prepareStatement(query);
                                            st.setString(1, request.getParameter("href"));
                                            st.setString(2, (String) jo.get("nome"));
                                            st.setString(3, (String) jobox.getString("val_select"));
                                            st.setString(4, Utility.testoADb((String) jobox.getString("des_valore_" + cod_lang)));
                                            st.setString(5, cod_lang);
                                            st.executeUpdate();
                                            Utility.chiusuraJdbc(rs);
                                            Utility.chiusuraJdbc(st);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    scriviTCR(verifica, request.getParameter("href"), conn);
                }
                ret.put("success", "Aggiornamento effettuato");
            } else {
                Utility.scriviSessione(request);
                JSONObject msg = new JSONObject();
                msg.put("notifica", "La risorsa è condivisa(non effettuo la modifica); Vuoi inviare una notifica?");
                ret.put("failure", msg);
            }
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

    public boolean cancellaControllo(String key, Connection conn) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        String query;
        boolean controllo = true;
        int righe = 0;
        try {
            query = "select count(*) righe from documenti where href = ? "
                    + "union "
                    + "select count(*) righe from tipi_aggregazione where href = ? ";
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
        String query;
        boolean controllo = false;
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            controllo = cancellaControllo(request.getParameter("href"), conn);
            if (controllo) {
                query = "delete from href_campi_valori_testi where href=?";
                st = conn.prepareStatement(query);
                st.setString(1, request.getParameter("href"));
                st.executeUpdate();
                Utility.chiusuraJdbc(st);
                query = "delete from href_campi_valori where href=?";
                st = conn.prepareStatement(query);
                st.setString(1, request.getParameter("href"));
                st.executeUpdate();
                Utility.chiusuraJdbc(st);
                query = "delete from href_campi_testi where href=?";
                st = conn.prepareStatement(query);
                st.setString(1, request.getParameter("href"));
                st.executeUpdate();
                Utility.chiusuraJdbc(st);
                query = "delete from href_campi where href=?";
                st = conn.prepareStatement(query);
                st.setString(1, request.getParameter("href"));
                st.executeUpdate();
                Utility.chiusuraJdbc(st);
                query = "delete from href_testi where href=?";
                st = conn.prepareStatement(query);
                st.setString(1, request.getParameter("href"));
                st.executeUpdate();
                Utility.chiusuraJdbc(st);
                query = "delete from href where href=?";
                st = conn.prepareStatement(query);
                st.setString(1, request.getParameter("href"));
                st.executeUpdate();
                ret.put("success", "Cancellazione effettuata");
            } else {
                ret.put("failure", "Dichiarazione in uso");
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

    public JSONObject duplica(HttpServletRequest request) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        String query;
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            query = "insert into href (href,tp_href,tip_aggregazione) select ?,tp_href,tip_aggregazione from href where href = ?";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("new_href"));
            st.setString(2, request.getParameter("old_href"));
            st.executeUpdate();
            Utility.chiusuraJdbc(st);
            query = "insert into href_testi (href,cod_lang,tit_href,piede_href) select ?,cod_lang,tit_href,piede_href from href_testi where href = ?";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("new_href"));
            st.setString(2, request.getParameter("old_href"));
            st.executeUpdate();
            Utility.chiusuraJdbc(st);
            query = "insert into href_campi (contatore, href,  nome,  riga,  posizione,  tp_riga,  tipo,  valore,  controllo,  web_serv,  nome_xsd,  campo_key,  campo_dati,  campo_xml_mod,  tp_controllo,  lunghezza,  decimali,  edit,  raggruppamento_check,  campo_collegato,  val_campo_collegato,  marcatore_incrociato,  precompilazione, pattern) "
                    + "select contatore, ?, nome,  riga,  posizione,  tp_riga,  tipo,  valore,  controllo,  web_serv,  nome_xsd,  campo_key,  campo_dati,  campo_xml_mod,  tp_controllo,  lunghezza,  decimali,  edit,  raggruppamento_check,  campo_collegato,  val_campo_collegato,  marcatore_incrociato,  precompilazione, pattern from href_campi where href = ?";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("new_href"));
            st.setString(2, request.getParameter("old_href"));
            st.executeUpdate();
            Utility.chiusuraJdbc(st);
            query = "insert into href_campi_testi (contatore, href, nome, cod_lang, des_campo, err_msg) "
                    + "select contatore, ?, nome, cod_lang, des_campo, err_msg from href_campi_testi where href = ?";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("new_href"));
            st.setString(2, request.getParameter("old_href"));
            st.executeUpdate();
            Utility.chiusuraJdbc(st);
            query = "insert into href_campi_valori (href, nome, val_select) select  ?, nome, val_select from href_campi_valori where href = ?";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("new_href"));
            st.setString(2, request.getParameter("old_href"));
            st.executeUpdate();
            Utility.chiusuraJdbc(st);
            query = "insert into href_campi_valori_testi (href, nome, val_select, cod_lang, des_valore) select  ?, nome, val_select, cod_lang, des_valore from href_campi_valori_testi where href = ?";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("new_href"));
            st.setString(2, request.getParameter("old_href"));
            st.executeUpdate();
            Utility.chiusuraJdbc(st);

            ret.put("success", "Duplicazione effettuata");
        } catch (SQLException e) {
            log.error("Errore duplicazione ", e);
            ret = new JSONObject();
            ret.put("failure", e.getMessage());
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public Modifica verificaNotifica(String href, JSONArray hrefCampi, HttpServletRequest request, Connection conn) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        Modifica modificato = new Modifica();
        modificato.init();
        int righe = 0;
        try {
            String datiForm = caricaStringaCompare(hrefCampi, request);
            String datiDb = caricaStringaDb(href, conn);
            if (!datiForm.equals(datiDb)) {
                modificato.setModificaIntervento(true);
            }
            if (utente.getRuolo().equals("A") || utente.getRuolo().equals("B")) {
                if (modificato.isModificaIntervento()) {
                    String sql = "select count(*) righe from "
                            + "(select distinct a.cod_int from allegati a "
                            + "join documenti b "
                            + "on a.cod_doc=b.cod_doc "
                            + "where b.href = ? ) b "
                            + "join utenti_interventi x "
                            + "on b.cod_int = x.cod_int "
                            + "where x.cod_utente <> ?";
                    st = conn.prepareStatement(sql);
                    st.setString(1, href);
                    st.setString(2, utente.getCodUtente());
                    rs = st.executeQuery();
                    if (rs.next()) {
                        righe = rs.getInt("righe");
                    }
                    if (righe > 0) {
                        modificato.setNotifica(true);
                    }
                }
            }
        } catch (SQLException e) {
            log.error("Errore verifica per notifica ", e);
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }

        return modificato;
    }

    public JSONObject scriviNotifica(HttpServletRequest request) throws Exception {
        DocumentRoot doc = new DocumentRoot();

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        String sql;
        HashMap<String, String> param = null;
        HashMap<String, Object> funzRequest = null;
        if (session.getAttribute("sessioneTabelle") != null) {
            funzRequest = (HashMap<String, Object>) session.getAttribute("sessioneTabelle");
        } else {
            funzRequest = new HashMap<String, Object>();
        }
        if (funzRequest.containsKey(request.getParameter("table_name"))) {
            param = (HashMap<String, String>) funzRequest.get(request.getParameter("table_name"));
        } else {
            param = new HashMap<String, String>();
        }
        HashMap<String, List<String>> listaUtentiInterventi = new HashMap<String, List<String>>();
        List<String> listaInterventi = new ArrayList<String>();
        List<String> li = null;
        JSONArray hrefCampi = (JSONArray) JSONSerializer.toJSON(param.get("hrefCampi"));
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            String href = param.get("href");
            sql = "select distinct x.cod_utente, x.cod_int from "
                    + "(select distinct a.cod_int from allegati a "
                    + "join documenti b "
                    + "on a.cod_doc=b.cod_doc "
                    + "where b.href = ? ) b "
                    + "join utenti_interventi x "
                    + "on b.cod_int = x.cod_int "
                    + "order by x.cod_utente,x.cod_int";
            st = conn.prepareStatement(sql);
            st.setString(1, href);
            rs = st.executeQuery();
            while (rs.next()) {
                if (!listaInterventi.contains(String.valueOf(rs.getInt("cod_int")))) {
                    listaInterventi.add(String.valueOf(rs.getInt("cod_int")));
                }
                if (listaUtentiInterventi.containsKey(rs.getString("cod_utente"))) {
                    if (!listaUtentiInterventi.get(rs.getString("cod_utente")).contains(String.valueOf(rs.getInt("cod_int")))) {
                        listaUtentiInterventi.get(rs.getString("cod_utente")).add(String.valueOf(rs.getInt("cod_int")));
                    }
                } else {
                    li = new ArrayList<String>();
                    li.add(String.valueOf(rs.getInt("cod_int")));
                    listaUtentiInterventi.put(rs.getString("cod_utente"), li);
                }
                if (listaUtentiInterventi.size() > 0) {
                    RisaliGerarchiaUtenti rgu = new RisaliGerarchiaUtenti();
                    rgu.risaliGerarchiaUtenti(rs.getString("cod_utente"), listaUtentiInterventi);
                }
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            if (listaUtentiInterventi.size() > 0) {
                // componi testo notifica
                String msg = "Id Dichiarazione(Href) = " + href + "\n";
                msg = msg + "Nuovo titolo = " + param.get("tit_href") + "\n";
                msg = msg + request.getParameter("textMsg");
                // componi stringa sql
                String sqlDeferred = "update href_testi set tit_href = '" + Utility.testoADbNotifica(param.get("tit_href_it")) + "',"
                        + " piede_href = "
                        + ((String) param.get("piede_href_it") == null || ((String) param.get("piede_href_it")).equals("") ? null : "'" + Utility.testoADbNotifica((String) param.get("piede_href_it")) + "'")
                        + " where href = '" + href + "' and cod_lang='it';";
                // carica stringa sql nell'xml
                doc.getSqlString().add(sqlDeferred.getBytes());
                sqlDeferred = "delete from href_campi where href='" + href + "'";
                doc.getSqlString().add(sqlDeferred.getBytes());
                sqlDeferred = "delete from href_campi_testi where href='" + href + "'";
                doc.getSqlString().add(sqlDeferred.getBytes());
                sqlDeferred = "delete from href_campi_valori where href='" + href + "'";
                doc.getSqlString().add(sqlDeferred.getBytes());
                sqlDeferred = "delete from href_campi_valori_testi where href = '" + href + "'";
                doc.getSqlString().add(sqlDeferred.getBytes());
                Integer lunghezza = 0;
                Integer decimali = 0;
                JSONObject jo = null;
                for (int i = 0; i < hrefCampi.size(); i++) {

                    jo = (JSONObject) hrefCampi.get(i);
                    Object ob = (Object) jo.get("lunghezza");
                    String type = ob.getClass().getName();
                    if (type.equals(String.class.getName())) {
                        lunghezza = 30;
                    }
                    if (type.equals(Integer.class.getName())) {
                        lunghezza = (Integer) jo.getInt("lunghezza");
                    }
                    ob = (Object) jo.get("decimali");
                    type = ob.getClass().getName();
                    if (type.equals(String.class.getName())) {
                        decimali = 0;
                    }
                    if (type.equals(Integer.class.getName())) {
                        decimali = (Integer) jo.getInt("decimali");
                    }


                    sqlDeferred = "insert into href_campi (contatore, href, nome, riga, posizione, tp_riga, tipo, valore, controllo,"
                            + " tp_controllo, lunghezza, decimali, edit, web_serv, nome_xsd, campo_key,"
                            + " campo_dati, campo_xml_mod, raggruppamento_check, campo_collegato, val_campo_collegato,"
                            + " marcatore_incrociato, precompilazione, pattern ) select IFNULL(MAX(contatore),0)+1, '" + href + "',"
                            + "'" + Utility.testoADb((String) jo.getString("nome")) + "',"
                            + (Integer) jo.getInt("riga") + ","
                            + (Integer) jo.getInt("posizione") + ","
                            + "'" + (String) jo.getString("tp_riga") + "',"
                            + "'" + (String) jo.getString("tipo") + "', "
                            + ((String) jo.getString("valore") == null || ((String) jo.getString("valore")).equals("") ? null : "'" + Utility.testoADbNotifica((String) jo.getString("valore")) + "'") + ","
                            + ((String) jo.getString("controllo") == null || ((String) jo.getString("controllo")).equals("") ? "''" : "'" + (String) jo.getString("controllo") + "'") + ","
                            + "'" + ((String) jo.getString("tp_controllo") == null || ((String) jo.getString("tp_controllo")).equals("") ? "T" : (String) jo.getString("tp_controllo")) + "',"
                            + lunghezza + ","
                            + decimali + ","
                            + "'" + (String) jo.getString("edit") + "',"
                            + ((String) jo.getString("web_serv") == null || ((String) jo.getString("web_serv")).equals("") ? null : "'" + (String) jo.getString("web_serv") + "'") + ","
                            + ((String) jo.getString("nome_xsd") == null || ((String) jo.getString("nome_xsd")).equals("") ? null : "'" + (String) jo.getString("nome_xsd") + "'") + ","
                            + ((String) jo.getString("campo_key") == null || ((String) jo.getString("campo_key")).equals("") ? null : "'" + (String) jo.getString("campo_key") + "'") + ","
                            + ((String) jo.getString("campo_dati") == null || ((String) jo.getString("campo_dati")).equals("") ? null : "'" + (String) jo.getString("campo_dati") + "'") + ","
                            + ((String) jo.getString("campo_xml_mod") == null || ((String) jo.getString("campo_xml_mod")).equals("") ? null : "'" + (String) jo.getString("campo_xml_mod") + "'") + ","
                            + ((String) jo.getString("raggruppamento_check") == null || ((String) jo.getString("raggruppamento_check")).equals("") ? null : "'" + Utility.testoADbNotifica((String) jo.getString("raggruppamento_check")) + "'") + ","
                            + ((String) jo.getString("campo_collegato") == null || ((String) jo.getString("campo_collegato")).equals("") ? null : "'" + Utility.testoADb((String) jo.getString("campo_collegato")) + "'") + ","
                            + ((String) jo.getString("val_campo_collegato") == null || ((String) jo.getString("val_campo_collegato")).equals("") ? null : "'" + Utility.testoADb((String) jo.getString("val_campo_collegato")) + "'") + ","
                            + ((String) jo.getString("marcatore_incrociato") == null || ((String) jo.getString("marcatore_incrociato")).equals("") ? "''" : "'" + (String) jo.getString("marcatore_incrociato") + "'") + ","
                            + ((String) jo.getString("precompilazione") == null || ((String) jo.getString("precompilazione")).equals("") ? "''" : "'" + (String) jo.getString("precompilazione") + "'") + ","
                            + ((String) jo.getString("pattern") == null || ((String) jo.getString("pattern")).equals("") ? null : "'" + Utility.testoADbNotifica((String) jo.getString("pattern")) + "'") + " from href_campi "
                            + "where href='" + href + "' and nome='" + Utility.testoADb((String) jo.getString("nome")) + "'";
                    doc.getSqlString().add(sqlDeferred.getBytes());
                    sqlDeferred = "insert into href_campi_testi (contatore, href, nome, cod_lang, des_campo, err_msg) "
                            + "select max(contatore) ,'" + href + "','" + (String) jo.getString("nome") + "', 'it', '"
                            + Utility.testoADbNotifica((String) jo.getString("des_campo_it")) + "',"
                            + ((String) jo.getString("err_msg_it") == null || ((String) jo.getString("err_msg_it")).equals("") ? null : "'" + Utility.testoADbNotifica((String) jo.getString("err_msg_it")) + "'")
                            + " from href_campi where href='" + href + "' and nome='" + Utility.testoADb((String) jo.getString("nome")) + "'";
                    doc.getSqlString().add(sqlDeferred.getBytes());
                    if (((String) jo.get("tipo")).equals("L")) {
                        JSONArray ja = (JSONArray) jo.get("combobox");
                        JSONObject jobox = null;
                        for (int j = 0; j < ja.size(); j++) {
                            jobox = (JSONObject) ja.get(j);
                            sqlDeferred = "insert into href_campi_valori (href, nome, val_select) "
                                    + "values ('" + href + "','" + (String) jo.getString("nome") + "','" + (String) jobox.getString("val_select") + "')";
                            doc.getSqlString().add(sqlDeferred.getBytes());
                            sqlDeferred = "insert into href_campi_valori_testi (href, nome, val_select, des_valore, cod_lang) "
                                    + "values ('" + href + "','" + (String) jo.getString("nome") + "','" + (String) jobox.getString("val_select") + "','" + Utility.testoADbNotifica((String) jobox.getString("des_valore_it")) + "','it')";
                            doc.getSqlString().add(sqlDeferred.getBytes());
                        }
                    }
                }

                // inserisco in notifica
                sql = "insert into notifiche (cod_utente_origine,testo_notifica,stato_notifica,cod_elemento) values (?,?,?,?)";
                st = conn.prepareStatement(sql);
                st.setString(1, utente.getCodUtente());
                st.setString(2, msg);
                st.setString(3, "N");
                st.setString(4, "Dichiarazioni=" + href);
                st.executeUpdate();
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);
                // recupero in nuovo id
                sql = "select max(last_insert_id()) last_insert from notifiche";
                int contatore = 0;
                st = conn.prepareStatement(sql);
                rs = st.executeQuery();
                while (rs.next()) {
                    contatore = rs.getInt("last_insert");
                }
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);

                // inserisco la lista degli interventi interessati
                Iterator it = listaUtentiInterventi.keySet().iterator();
                while (it.hasNext()) {
                    String key = (String) it.next();
                    if (listaUtentiInterventi.get(key).size() > 0) {
                        Iterator itl = listaUtentiInterventi.get(key).iterator();
                        while (itl.hasNext()) {
                            sql = "insert into notifiche_utenti_interventi (cnt,cod_int,cod_utente) values (?,?,?)";
                            st = conn.prepareStatement(sql);
                            st.setInt(1, contatore);
                            st.setInt(2, Integer.valueOf((String) itl.next()));
                            st.setString(3, key);
                            st.executeUpdate();
                            Utility.chiusuraJdbc(rs);
                            Utility.chiusuraJdbc(st);
                        }
                    }
                }
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);
                // inserisco la riga nella tabella degli sql
                String xml;
                JAXBContext jc = JAXBContext.newInstance(DocumentRoot.class);
                Marshaller m = jc.createMarshaller();
                m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                m.marshal(doc, os);
                xml = os.toString("UTF-8");
                sql = "insert into notifiche_sql (cnt,sql_text) values (?,?)";
                st = conn.prepareStatement(sql);
                st.setInt(1, contatore);
                st.setString(2, xml);
                st.executeUpdate();
            }
            // cancella la session

            if (funzRequest.containsKey(request.getParameter("table_name"))) {
                funzRequest.remove(request.getParameter("table_name"));
            }

            ret.put("success", "Invio notifica effettuata");
        } catch (SQLException e) {
            log.error("Errore invio notifica ", e);
            ret = new JSONObject();
            ret.put("failure", e.getMessage());
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public void scriviTCR(Modifica modificato, String codHref, Connection conn) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        String sql = null;
        try {
            if (modificato.isModificaIntervento()) {
                sql = "update interventi set data_modifica=current_timestamp where cod_int in "
                        + "(select distinct a.cod_int from allegati a join documenti b on a.cod_doc = b.cod_doc where b.href=?)";
                st = conn.prepareStatement(sql);
                st.setString(1, codHref);
                st.executeUpdate();
                Boolean aggiorna = Boolean.parseBoolean(Configuration.getConfigurationParameter("updateTCR").toLowerCase());
                if (aggiorna) {
                    sql = "update interventi set flg_pubblicazione='S' where cod_int in "
                            + "(select distinct a.cod_int from allegati a join documenti b on a.cod_doc = b.cod_doc where b.href=?)";
                    st = conn.prepareStatement(sql);
                    st.setString(1, codHref);
                    st.executeUpdate();
                }
            }
        } catch (SQLException e) {
            log.error("Errore verifica per notifica per TCR", e);
            throw e;
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
    }

    private String caricaStringaCompare(JSONArray ja, HttpServletRequest request) throws Exception {
        List<JSONObject> list = new ArrayList<JSONObject>();
        List<JSONObject> listVs;
        Comparator<JSONObject> customerComparatorValSelect;
        for (int i = 0; i < ja.size(); i++) {
            list.add((JSONObject) ja.get(i));
        }
        Comparator<JSONObject> customerComparator = new Comparatore();
        Collections.sort(list, customerComparator);

        JSONObject jo, jobox;
        StringBuilder s = new StringBuilder();
        Set<String> parmKey = request.getParameterMap().keySet();
        for (String key : parmKey) {
            String[] parSplit = key.split("tit_href_");
            if (parSplit.length > 1 && key.contains("tit_href_")) {
                String cod_lang = parSplit[1];
                if (cod_lang != null) {
                    s.append(request.getParameter("tit_href_" + cod_lang));
                    s.append(request.getParameter("piede_href_" + cod_lang));
                }
            }
        }
        for (int i = 0; i < list.size(); i++) {
            jo = (JSONObject) list.get(i);
            s.append((String) jo.getString("nome"));
            s.append(Integer.toString(jo.getInt("riga")));
            s.append(Integer.toString(jo.getInt("posizione")));
            s.append((String) jo.getString("tp_riga"));
            s.append((String) jo.getString("tipo"));
            s.append((((String) jo.getString("valore")) == null || ((String) jo.getString("valore")).equals("") ? "" : (String) jo.getString("valore")));
            s.append(((String) jo.getString("controllo") == null || ((String) jo.getString("controllo")).equals("") ? "" : (String) jo.getString("controllo")));
            s.append(((String) jo.getString("tp_controllo") == null || ((String) jo.getString("tp_controllo")).equals("") ? "T" : (String) jo.getString("tp_controllo")));
            Object ob = (Object) jo.get("lunghezza");
            String type = ob.getClass().getName();
            if (type.equals(String.class.getName())) {
                s.append("30");
            }
            if (type.equals(Integer.class.getName())) {
                s.append(Integer.toString((Integer) jo.getInt("lunghezza")));
            }
            ob = (Object) jo.get("decimali");
            type = ob.getClass().getName();
            if (type.equals(String.class.getName())) {
                s.append("0");
            }
            if (type.equals(Integer.class.getName())) {
                s.append(Integer.toString((Integer) jo.getInt("decimali")));
            }

            s.append((String) jo.getString("edit"));
            s.append(((String) jo.getString("raggruppamento_check") == null || ((String) jo.getString("raggruppamento_check")).equals("") ? "" : (String) jo.getString("raggruppamento_check")));
            s.append(((String) jo.getString("campo_collegato") == null || ((String) jo.getString("campo_collegato")).equals("") ? "" : (String) jo.getString("campo_collegato")));
            s.append(((String) jo.getString("val_campo_collegato") == null || ((String) jo.getString("val_campo_collegato")).equals("") ? "" : (String) jo.getString("val_campo_collegato")));
            s.append(((String) jo.getString("marcatore_incrociato") == null || ((String) jo.getString("marcatore_incrociato")).equals("") ? "" : (String) jo.getString("marcatore_incrociato")));
            s.append(((String) jo.getString("precompilazione") == null || ((String) jo.getString("precompilazione")).equals("") ? "" : (String) jo.getString("precompilazione")));
            s.append((((String) jo.getString("pattern")) == null || ((String) jo.getString("pattern")).equals("") ? "" : (String) jo.getString("pattern")));
            s.append((((String) jo.getString("err_msg_it")) == null || ((String) jo.getString("err_msg_it")).equals("") ? "" : (String) jo.getString("err_msg_it")));
            parmKey = jo.keySet();
            for (String key : parmKey) {
                String[] parSplit = key.split("des_campo_");
                if (parSplit.length > 1 && key.contains("des_campo_")) {
                    String cod_lang = parSplit[1];
                    if (cod_lang != null) {
                        s.append((String) jo.getString("des_campo_" + cod_lang));
                        s.append((((String) jo.getString("err_msg_" + cod_lang)) == null || ((String) jo.getString("err_msg_" + cod_lang)).equals("") ? "" : (String) jo.getString("err_msg_" + cod_lang)));
                    }
                }
            }
            if (((String) jo.get("tipo")).equals("L")) {
                listVs = new ArrayList<JSONObject>();
                customerComparatorValSelect = new ComparatoreValSelect();
                JSONArray javs = (JSONArray) jo.get("combobox");
                for (int j = 0; j < javs.size(); j++) {
                    listVs.add((JSONObject) javs.get(j));
                }

                Collections.sort(listVs, customerComparatorValSelect);
                for (int j = 0; j < listVs.size(); j++) {
                    jobox = (JSONObject) listVs.get(j);

                    s.append((String) jobox.getString("val_select"));
                    parmKey = jobox.keySet();
                    for (String key : parmKey) {
                        String[] parSplit = key.split("des_valore_");
                        if (parSplit.length > 1 && key.contains("des_valore_")) {
                            String cod_lang = parSplit[1];
                            if (cod_lang != null) {
                                s.append((String) jobox.getString("des_valore_" + cod_lang));
                            }
                        }
                    }
                }
            }
        }
        return s.toString();
    }

    private String caricaStringaDb(String href, Connection conn) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        PreparedStatement st1 = null;
        ResultSet rs1 = null;
        StringBuilder s = new StringBuilder();
        try {
            String query = "select a.href,b.tit_href,b.piede_href from href a join href_testi b on a.href=b.href where a.href = ? and b.cod_lang='it'";
            st = conn.prepareStatement(query);
            st.setString(1, href);
            rs = st.executeQuery();
            while (rs.next()) {
                s.append(rs.getString("tit_href"));
                s.append(rs.getObject("piede_href") == null ? "" : rs.getString("piede_href"));
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            query = "select a.contatore contatore,a.href href, a.nome nome,b.des_campo des_campo,a.riga riga,"
                    + "a.posizione posizione,a.tp_riga tp_riga,a.tipo tipo,a.valore valore,"
                    + "a.controllo controllo,a.tp_controllo tp_controllo,a.lunghezza lunghezza,"
                    + "a.decimali decimali,a.edit edit,a.raggruppamento_check raggruppamento_check,"
                    + "a.campo_collegato campo_collegato,a.val_campo_collegato val_campo_collegato,"
                    + "a.precompilazione precompilazione,a.web_serv web_serv,"
                    + "a.campo_dati campo_dati,a.campo_key campo_key,a.nome_xsd nome_xsd,"
                    + "a.campo_xml_mod campo_xml_mod, a.marcatore_incrociato marcatore_incrociato, b.err_msg err_msg, a.pattern pattern "
                    + "from href_campi a "
                    + "join href_campi_testi b "
                    + "on a.href=b.href "
                    + "and a.contatore=b.contatore "
                    + "and a.nome=b.nome "
                    + "where a.href=? "
                    + "and b.cod_lang='it' "
                    + "order by  nome,riga,posizione";
            st = conn.prepareStatement(query);
            st.setString(1, href);
            rs = st.executeQuery();
            while (rs.next()) {
                s.append(rs.getString("nome"));
                s.append(rs.getString("riga"));
                s.append(rs.getString("posizione"));
                s.append(rs.getString("des_campo"));
                s.append(rs.getString("tp_riga"));
                s.append(rs.getString("tipo"));
                s.append((rs.getObject("valore") == null ? "" : rs.getString("valore")));
                s.append((rs.getObject("controllo") == null ? "" : rs.getString("controllo")));
                s.append(rs.getString("tp_controllo"));
                s.append(rs.getString("lunghezza"));
                s.append(rs.getString("decimali"));
                s.append(rs.getString("edit"));
                s.append((rs.getObject("raggruppamento_check") == null ? "" : rs.getString("raggruppamento_check")));
                s.append((rs.getObject("campo_collegato") == null ? "" : rs.getString("campo_collegato")));
                s.append((rs.getObject("val_campo_collegato") == null ? "" : rs.getString("val_campo_collegato")));
                s.append((rs.getObject("marcatore_incrociato") == null ? "" : rs.getString("marcatore_incrociato")));
                s.append((rs.getObject("precompilazione") == null ? "" : rs.getString("precompilazione")));
                s.append((rs.getObject("err_msg") == null ? "" : rs.getString("err_msg")));
                s.append((rs.getObject("pattern") == null ? "" : rs.getString("pattern")));
                if (rs.getString("tipo").equals("L")) {
                    query = "select a.href,a.nome,a.val_select, b.des_valore "
                            + "from href_campi_valori a "
                            + "join href_campi_valori_testi b "
                            + "on a.href=b.href "
                            + "and a.nome=b.nome "
                            + "and a.val_select=b.val_select "
                            + "where a.href=? "
                            + "and a.nome=? "
                            + "and b.cod_lang='it'";
                    st1 = conn.prepareStatement(query);
                    st1.setString(1, href);
                    st1.setString(2, rs.getString("nome"));
                    rs1 = st1.executeQuery();
                    while (rs1.next()) {
                        s.append(rs1.getString("val_select"));
                        s.append(rs1.getString("des_valore"));
                    }
                    Utility.chiusuraJdbc(rs1);
                    Utility.chiusuraJdbc(st1);
                }
            }
        } catch (Exception e) {
            log.error("Errore verifica per notifica per TCR", e);
            throw e;
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(rs1);
            Utility.chiusuraJdbc(st1);
        }
        return s.toString();
    }
}
