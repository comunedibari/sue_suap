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
package it.people.dbm.popup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import it.people.dbm.model.Utente;
import it.people.dbm.utility.Utility;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author Piergiorgio
 */
public class PopUpImpl {

    public JSONObject actionPopUpInterventi(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        Utente utente = (Utente) session.getAttribute("utente");
        try {
            conn = Utility.getConnection();
            String offset = request.getParameter("start");
            String size = request.getParameter("size");
            String sort = request.getParameter("sort");
            String order = request.getParameter("dir");
            String param = Utility.testoADb(request.getParameter("query"));

            String query = "select count(*) righe from ";
            if (request.getParameter("cod_com") != null) {
                query = query + "(select    a.cod_int, b.tit_int , a.tip_aggregazione "
                        + "from      interventi a "
                        + "join      interventi_testi b "
                        + "on        a.cod_int = b.cod_int "
                        + "and       b.cod_lang='it' "
                        + "join      interventi_comuni c "
                        + "on        a.cod_int = c.cod_int "
                        + "and       c.cod_com = ? "
                        + "and       c.flg = 'S' "
                        + "union   "
                        + "select    a.cod_int, b.tit_int, a.tip_aggregazione "
                        + "from      interventi a "
                        + "join      interventi_testi b "
                        + "on        a.cod_int = b.cod_int "
                        + "and       b.cod_lang='it' "
                        + "left join interventi_comuni c "
                        + "on        a.cod_int = c.cod_int "
                        + "where     c.cod_int is null "
                        + "union "
                        + "select    a.cod_int, b.tit_int,  a.tip_aggregazione "
                        + "from      interventi a "
                        + "join      interventi_testi b "
                        + "on        a.cod_int = b.cod_int "
                        + "and       b.cod_lang='it' "
                        + "left join interventi_comuni c "
                        + "on        a.cod_int = c.cod_int "
                        + "where     c.cod_com != ? "
                        + "and       c.flg = 'N' "
                        + "and       a.cod_int not in "
                        + "      (select cod_int "
                        + "              from   interventi_comuni "
                        + "              where  cod_com = ? "
                        + "              and flg = 'N') "
                        + ") p ";
            } else {
                query = query + "(select a.cod_int, b.tit_int, a.tip_aggregazione "
                        + "from interventi a "
                        + "join interventi_testi b "
                        + "on a.cod_int = b.cod_int "
                        + "and b.cod_lang='it') p ";
            }
            query = query + "where 1=1 ";
            if (utente.getRuolo().equals("A") || utente.getRuolo().equals("B")) {
                query = query + "and p.cod_int in (select distinct cod_int from utenti_interventi "
                        + "where cod_utente='" + utente.getCodUtente() + "') ";
            }

            if ((Boolean) session.getAttribute("territorialitaNew")) {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and (p.tip_aggregazione=? or p.tip_aggregazione is null) ";
                }
            } else {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and p.tip_aggregazione=? ";
                }
            }
            query = query + "and ( convert(p.cod_int,CHAR) like ? or p.tit_int like ? )";
            st = conn.prepareStatement(query);
            int indice = 1;
            if (request.getParameter("cod_com") != null) {
                st.setString(indice, request.getParameter("cod_com"));
                indice++;
                st.setString(indice, request.getParameter("cod_com"));
                indice++;
                st.setString(indice, request.getParameter("cod_com"));
                indice++;
            }
            if (utente.getTipAggregazione() != null) {
                st.setString(indice, utente.getTipAggregazione());
                indice++;
            }
            st.setString(indice, "%" + param + "%");
            indice++;
            st.setString(indice, "%" + param + "%");
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);

            query = "select p.cod_int cod_int, p.tit_int tit_int, p.tip_aggregazione tip_aggregazione from ";
            if (request.getParameter("cod_com") != null) {
                query = query + "(select    a.cod_int, b.tit_int , a.tip_aggregazione "
                        + "from      interventi a "
                        + "join      interventi_testi b "
                        + "on        a.cod_int = b.cod_int "
                        + "and       b.cod_lang='it' "
                        + "join      interventi_comuni c "
                        + "on        a.cod_int = c.cod_int "
                        + "and       c.cod_com = ? "
                        + "and       c.flg = 'S' "
                        + "union   "
                        + "select    a.cod_int, b.tit_int , a.tip_aggregazione "
                        + "from      interventi a "
                        + "join      interventi_testi b "
                        + "on        a.cod_int = b.cod_int "
                        + "and       b.cod_lang='it' "
                        + "left join interventi_comuni c "
                        + "on        a.cod_int = c.cod_int "
                        + "where     c.cod_int is null "
                        + "union "
                        + "select    a.cod_int, b.tit_int , a.tip_aggregazione "
                        + "from      interventi a "
                        + "join      interventi_testi b "
                        + "on        a.cod_int = b.cod_int "
                        + "and       b.cod_lang='it' "
                        + "left join interventi_comuni c "
                        + "on        a.cod_int = c.cod_int "
                        + "where     c.cod_com != ? "
                        + "and       c.flg = 'N' "
                        + "and       a.cod_int not in "
                        + "      (select cod_int "
                        + "              from   interventi_comuni "
                        + "              where  cod_com = ? "
                        + "              and flg = 'N') "
                        + ") p ";
            } else {
                query = query + "(select a.cod_int, b.tit_int, a.tip_aggregazione tip_aggregazione from "
                        + "interventi a "
                        + "join interventi_testi b "
                        + "on a.cod_int = b.cod_int "
                        + "and b.cod_lang='it') p ";
            }
            query = query + "where 1 = 1 ";
            if (utente.getRuolo().equals("A") || utente.getRuolo().equals("B")) {
                query = query + "and p.cod_int in (select distinct cod_int from utenti_interventi "
                        + "where cod_utente='" + utente.getCodUtente() + "') ";
            }
            if ((Boolean) session.getAttribute("territorialitaNew")) {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and (p.tip_aggregazione=? or p.tip_aggregazione is null) ";
                }
            } else {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and p.tip_aggregazione=? ";
                }
            }
            query = query + "and ( convert(p.cod_int,CHAR) like ? or p.tit_int like ? ) "
                    + "order by " + sort + " " + order + " limit ? , ?";

            st = conn.prepareStatement(query);
            indice = 1;
            if (request.getParameter("cod_com") != null) {
                st.setString(indice, request.getParameter("cod_com"));
                indice++;
                st.setString(indice, request.getParameter("cod_com"));
                indice++;
                st.setString(indice, request.getParameter("cod_com"));
                indice++;
            }
            if (utente.getTipAggregazione() != null) {
                st.setString(indice, utente.getTipAggregazione());
                indice++;
            }
            st.setString(indice, "%" + param + "%");
            indice++;
            st.setString(indice, "%" + param + "%");
            indice++;
            st.setInt(indice, Integer.parseInt(offset));
            indice++;
            st.setInt(indice, Integer.parseInt(size));
            rs = st.executeQuery();

            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_int", rs.getInt("cod_int"));
                loopObj.put("tit_int", Utility.testoDaDb(rs.getString("tit_int")));
                riga.add(loopObj);

            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("interventi", riga);
            return ret;
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
    }

    public JSONObject actionPopUpInterventiTotali(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        Utente utente = (Utente) session.getAttribute("utente");
        try {
            conn = Utility.getConnection();
            String offset = request.getParameter("start");
            String size = request.getParameter("size");
            String sort = request.getParameter("sort");
            String order = request.getParameter("dir");
            String param = Utility.testoADb(request.getParameter("query"));

            String query = "select count(*) righe from ";
            if (request.getParameter("cod_com") != null) {
                query = query + "(select    a.cod_int, b.tit_int , a.tip_aggregazione "
                        + "from      interventi a "
                        + "join      interventi_testi b "
                        + "on        a.cod_int = b.cod_int "
                        + "and       b.cod_lang='it' "
                        + "join      interventi_comuni c "
                        + "on        a.cod_int = c.cod_int "
                        + "and       c.cod_com = ? "
                        + "and       c.flg = 'S' "
                        + "union   "
                        + "select    a.cod_int, b.tit_int, a.tip_aggregazione "
                        + "from      interventi a "
                        + "join      interventi_testi b "
                        + "on        a.cod_int = b.cod_int "
                        + "and       b.cod_lang='it' "
                        + "left join interventi_comuni c "
                        + "on        a.cod_int = c.cod_int "
                        + "where     c.cod_int is null "
                        + "union "
                        + "select    a.cod_int, b.tit_int,  a.tip_aggregazione "
                        + "from      interventi a "
                        + "join      interventi_testi b "
                        + "on        a.cod_int = b.cod_int "
                        + "and       b.cod_lang='it' "
                        + "left join interventi_comuni c "
                        + "on        a.cod_int = c.cod_int "
                        + "where     c.cod_com != ? "
                        + "and       c.flg = 'N' "
                        + "and       a.cod_int not in "
                        + "      (select cod_int "
                        + "              from   interventi_comuni "
                        + "              where  cod_com = ? "
                        + "              and flg = 'N') "
                        + ") p ";
            } else {
                query = query + "(select a.cod_int, b.tit_int, a.tip_aggregazione "
                        + "from interventi a "
                        + "join interventi_testi b "
                        + "on a.cod_int = b.cod_int "
                        + "and b.cod_lang='it') p ";
            }
            query = query + "where 1=1 ";
            if (utente.getRuolo().equals("A") || utente.getRuolo().equals("B")) {
                query = query + "and p.cod_int in (select distinct cod_int from utenti_interventi "
                        + "where cod_utente='" + utente.getCodUtente() + "') ";
            }

            query = query + "and ( convert(p.cod_int,CHAR) like ? or p.tit_int like ? )";
            st = conn.prepareStatement(query);
            int indice = 1;
            if (request.getParameter("cod_com") != null) {
                st.setString(indice, request.getParameter("cod_com"));
                indice++;
                st.setString(indice, request.getParameter("cod_com"));
                indice++;
                st.setString(indice, request.getParameter("cod_com"));
                indice++;
            }

            st.setString(indice, "%" + param + "%");
            indice++;
            st.setString(indice, "%" + param + "%");
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);

            query = "select p.cod_int cod_int, p.tit_int tit_int, p.tip_aggregazione tip_aggregazione from ";
            if (request.getParameter("cod_com") != null) {
                query = query + "(select    a.cod_int, b.tit_int , a.tip_aggregazione "
                        + "from      interventi a "
                        + "join      interventi_testi b "
                        + "on        a.cod_int = b.cod_int "
                        + "and       b.cod_lang='it' "
                        + "join      interventi_comuni c "
                        + "on        a.cod_int = c.cod_int "
                        + "and       c.cod_com = ? "
                        + "and       c.flg = 'S' "
                        + "union   "
                        + "select    a.cod_int, b.tit_int , a.tip_aggregazione "
                        + "from      interventi a "
                        + "join      interventi_testi b "
                        + "on        a.cod_int = b.cod_int "
                        + "and       b.cod_lang='it' "
                        + "left join interventi_comuni c "
                        + "on        a.cod_int = c.cod_int "
                        + "where     c.cod_int is null "
                        + "union "
                        + "select    a.cod_int, b.tit_int , a.tip_aggregazione "
                        + "from      interventi a "
                        + "join      interventi_testi b "
                        + "on        a.cod_int = b.cod_int "
                        + "and       b.cod_lang='it' "
                        + "left join interventi_comuni c "
                        + "on        a.cod_int = c.cod_int "
                        + "where     c.cod_com != ? "
                        + "and       c.flg = 'N' "
                        + "and       a.cod_int not in "
                        + "      (select cod_int "
                        + "              from   interventi_comuni "
                        + "              where  cod_com = ? "
                        + "              and flg = 'N') "
                        + ") p ";
            } else {
                query = query + "(select a.cod_int, b.tit_int, a.tip_aggregazione tip_aggregazione from "
                        + "interventi a "
                        + "join interventi_testi b "
                        + "on a.cod_int = b.cod_int "
                        + "and b.cod_lang='it') p ";
            }
            query = query + "where 1 = 1 ";
            if (utente.getRuolo().equals("A") || utente.getRuolo().equals("B")) {
                query = query + "and p.cod_int in (select distinct cod_int from utenti_interventi "
                        + "where cod_utente='" + utente.getCodUtente() + "') ";
            }

            query = query + "and ( convert(p.cod_int,CHAR) like ? or p.tit_int like ? ) "
                    + "order by " + sort + " " + order + " limit ? , ?";

            st = conn.prepareStatement(query);
            indice = 1;
            if (request.getParameter("cod_com") != null) {
                st.setString(indice, request.getParameter("cod_com"));
                indice++;
                st.setString(indice, request.getParameter("cod_com"));
                indice++;
                st.setString(indice, request.getParameter("cod_com"));
                indice++;
            }

            st.setString(indice, "%" + param + "%");
            indice++;
            st.setString(indice, "%" + param + "%");
            indice++;
            st.setInt(indice, Integer.parseInt(offset));
            indice++;
            st.setInt(indice, Integer.parseInt(size));
            rs = st.executeQuery();

            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_int", rs.getInt("cod_int"));
                loopObj.put("tit_int", Utility.testoDaDb(rs.getString("tit_int")));
                riga.add(loopObj);

            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("interventi_totali", riga);
            return ret;
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
    }
    
    public JSONObject actionPopUpHref(HttpServletRequest request) throws Exception {
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
                        + "select a.href,b.tit_href "
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
            if ((Boolean) session.getAttribute("territorialitaNew")) {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and (p.tip_aggregazione=? or p.tip_aggregazione is null) ";
                }
            } else {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and p.tip_aggregazione=? ";
                }
            }
            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");

            if (utente.getTipAggregazione() != null) {
                st.setString(3, utente.getTipAggregazione());
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
                        + "select a.href,b.tit_href "
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
            if ((Boolean) session.getAttribute("territorialitaNew")) {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and (p.tip_aggregazione=? or p.tip_aggregazione is null) ";
                }
            } else {
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
            if (utente.getTipAggregazione() != null) {
                st.setString(qsi, utente.getTipAggregazione());
                qsi++;
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

    public JSONObject actionPopUpClassiEnti(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
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
                    + "from classi_enti a "
                    + "join classi_enti_testi b "
                    + "on a.cod_classe_ente = b.cod_classe_ente "
                    + "and b.cod_lang='it' "
                    + "where a.cod_classe_ente like ? or b.des_classe_ente like ? ";
            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);

            query = "select a.cod_classe_ente cod_classe_ente, b.des_classe_ente des_classe_ente "
                    + "from classi_enti a "
                    + "join classi_enti_testi b "
                    + "on a.cod_classe_ente = b.cod_classe_ente "
                    + "and b.cod_lang='it' "
                    + "where a.cod_classe_ente like ? or b.des_classe_ente like ? "
                    + "order by " + sort + " " + order + " limit ? , ?";

            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            st.setInt(3, Integer.parseInt(offset));
            st.setInt(4, Integer.parseInt(size));
            rs = st.executeQuery();

            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_classe_ente", rs.getInt("cod_classe_ente"));
                loopObj.put("des_classe_ente", Utility.testoDaDb(rs.getString("des_classe_ente")));
                riga.add(loopObj);

            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("classi_enti", riga);
            return ret;
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
    }

    public JSONObject actionPopUpEntiComuni(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
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
                    + "on a.cod_ente = b.cod_ente "
                    + "and b.cod_lang='it' "
                    + "where a.cod_ente like ? or b.des_ente like ? ";
            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);

            query = "select a.cod_ente cod_ente, b.des_ente des_ente "
                    + "from enti_comuni a "
                    + "join enti_comuni_testi b "
                    + "on a.cod_ente = b.cod_ente "
                    + "and b.cod_lang='it' "
                    + "where a.cod_ente like ? or b.des_ente like ? "
                    + "order by " + sort + " " + order + " limit ? , ?";

            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            st.setInt(3, Integer.parseInt(offset));
            st.setInt(4, Integer.parseInt(size));
            rs = st.executeQuery();

            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_ente", rs.getString("cod_ente"));
                loopObj.put("des_ente", Utility.testoDaDb(rs.getString("des_ente")));
                riga.add(loopObj);

            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("enti_comuni", riga);
            return ret;
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
    }

    public JSONObject actionPopUpSettoriAttivita(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
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
                    + "from settori_attivita "
                    + "where cod_lang = 'it' and (cod_sett like ? or des_sett like ? )";
            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);

            query = "select cod_sett, des_sett "
                    + "from settori_attivita "
                    + "where cod_lang = 'it' and (cod_sett like ? or des_sett like ? )"
                    + "order by " + sort + " " + order + " limit ? , ?";

            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            st.setInt(3, Integer.parseInt(offset));
            st.setInt(4, Integer.parseInt(size));
            rs = st.executeQuery();

            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_sett", rs.getString("cod_sett"));
                loopObj.put("des_sett", Utility.testoDaDb(rs.getString("des_sett")));
                riga.add(loopObj);

            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("settori_attivita", riga);
            return ret;
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
    }

    public JSONObject actionPopUpOperazioni(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
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
                    + "from operazioni "
                    + "where cod_lang = 'it' and (cod_ope like ? or des_ope like ? )";
            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);

            query = "select cod_ope, des_ope "
                    + "from operazioni "
                    + "where cod_lang = 'it' and (cod_ope like ? or des_ope like ? )"
                    + "order by " + sort + " " + order + " limit ? , ?";

            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            st.setInt(3, Integer.parseInt(offset));
            st.setInt(4, Integer.parseInt(size));
            rs = st.executeQuery();

            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_ope", rs.getString("cod_ope"));
                loopObj.put("des_ope", Utility.testoDaDb(rs.getString("des_ope")));
                riga.add(loopObj);

            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("operazioni", riga);
            return ret;
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
    }

    public JSONObject actionPopUpNormative(HttpServletRequest request) throws Exception {
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

            String query = "select count(*) righe from ( "
                    + "select a.cod_rif cod_rif,a.nome_rif nome_rif, "
                    + "a.cod_tipo_rif cod_tipo_rif, b.tit_rif tit_rif, "
                    + "c.tipo_rif tipo_rif,d.tip_doc tip_doc,d.nome_file nome_file , b.cod_lang cod_lang ,a.tip_aggregazione "
                    + "from normative a join normative_testi b "
                    + "on a.cod_rif=b.cod_rif join tipi_rif c "
                    + "on a.cod_tipo_rif=c.cod_tipo_rif "
                    + "and b.cod_lang = c.cod_lang "
                    + "left join normative_documenti d on a.cod_rif=d.cod_rif and b.cod_lang=d.cod_lang ) p "
                    + "where p.cod_lang ='it' and "
                    + "(p.tit_rif like ? or p.cod_rif like ? )";
            if ((Boolean) session.getAttribute("territorialitaNew")) {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and (p.tip_aggregazione=? or p.tip_aggregazione is null) ";
                }
            } else {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and p.tip_aggregazione=? ";
                }
            }
            st = conn.prepareStatement(query);
            st.setString(qsi, "%" + param + "%");
            qsi++;
            st.setString(qsi, "%" + param + "%");
            qsi++;
            if (utente.getTipAggregazione() != null) {
                st.setString(qsi, utente.getTipAggregazione());
            }
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);

            query = "select p.cod_rif cod_rif,p.tit_rif tit_rif "
                    + "from ( "
                    + "select a.cod_rif cod_rif,a.nome_rif nome_rif, "
                    + "a.cod_tipo_rif cod_tipo_rif, b.tit_rif tit_rif, "
                    + "c.tipo_rif tipo_rif,d.tip_doc tip_doc,d.nome_file nome_file , b.cod_lang cod_lang , a.tip_aggregazione "
                    + "from normative a join normative_testi b "
                    + "on a.cod_rif=b.cod_rif join tipi_rif c "
                    + "on a.cod_tipo_rif=c.cod_tipo_rif "
                    + "and b.cod_lang = c.cod_lang "
                    + "left join normative_documenti d on a.cod_rif=d.cod_rif and b.cod_lang=d.cod_lang ) p "
                    + "where p.cod_lang ='it' and "
                    + "(p.tit_rif like ? or p.cod_rif like ? ) ";
            if ((Boolean) session.getAttribute("territorialitaNew")) {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and (p.tip_aggregazione=? or p.tip_aggregazione is null) ";
                }
            } else {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and p.tip_aggregazione=? ";
                }
            }
            query = query + "order by " + sort + " " + order + " limit ? , ?";
            qsi = 1;
            st = conn.prepareStatement(query);
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

            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_rif", rs.getString("cod_rif"));
                loopObj.put("tit_rif", Utility.testoDaDb(rs.getString("tit_rif")));
                riga.add(loopObj);

            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("normative", riga);
            return ret;
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
    }

    public JSONObject actionPopUpAggregazioni(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
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
                    + "from tipi_aggregazione "
                    + "where tip_aggregazione like ? or des_aggregazione like ? ";
            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);

            query = "select tip_aggregazione, des_aggregazione "
                    + "from tipi_aggregazione "
                    + "where tip_aggregazione like ? or des_aggregazione like ? "
                    + "order by " + sort + " " + order + " limit ? , ?";

            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            st.setInt(3, Integer.parseInt(offset));
            st.setInt(4, Integer.parseInt(size));
            rs = st.executeQuery();

            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("tip_aggregazione", rs.getString("tip_aggregazione"));
                loopObj.put("des_aggregazione", Utility.testoDaDb(rs.getString("des_aggregazione")));
                riga.add(loopObj);

            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("aggregazioni", riga);
            return ret;
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
    }

    public JSONObject actionPopUpProcedimenti(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        int qsi = 1;
        try {
            conn = Utility.getConnection();
            String offset = request.getParameter("start");
            String size = request.getParameter("size");
            String sort = request.getParameter("sort");
            String order = request.getParameter("dir");
            String param = Utility.testoADb(request.getParameter("query"));

            String query = "select count(*) righe "
                    + "from procedimenti a "
                    + "join procedimenti_testi b "
                    + "on a.cod_proc = b.cod_proc "
                    + "and b.cod_lang='it' "
                    + "where (a.cod_proc like ? or b.tit_proc like ?) ";
            if ((Boolean) session.getAttribute("territorialitaNew")) {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and (a.tip_aggregazione=? or a.tip_aggregazione is null) ";
                }
            } else {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and a.tip_aggregazione=? ";
                }
            }
            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            if (utente.getTipAggregazione() != null) {
                st.setString(3, utente.getTipAggregazione());
            }
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);

            query = "select a.cod_proc cod_proc, b.tit_proc tit_proc "
                    + "from procedimenti a "
                    + "join procedimenti_testi b "
                    + "on a.cod_proc = b.cod_proc "
                    + "and b.cod_lang='it' "
                    + "where (a.cod_proc like ? or b.tit_proc like ?) ";
            if ((Boolean) session.getAttribute("territorialitaNew")) {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and (a.tip_aggregazione=? or a.tip_aggregazione is null) ";
                }
            } else {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and a.tip_aggregazione=? ";
                }
            }

            query = query + "order by " + sort + " " + order + " limit ? , ?";

            st = conn.prepareStatement(query);
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

            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_proc", rs.getString("cod_proc"));
                loopObj.put("tit_proc", Utility.testoDaDb(rs.getString("tit_proc")));
                riga.add(loopObj);

            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("procedimenti", riga);
            return ret;
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
    }

    public JSONObject actionPopUpCud(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
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
                    + "from cud a "
                    + "join cud_testi b "
                    + "on a.cod_cud = b.cod_cud "
                    + "and b.cod_lang='it' "
                    + "where a.cod_cud like ? or b.des_cud like ? ";
            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);

            query = "select a.cod_cud cod_cud, b.des_cud des_cud "
                    + "from cud a "
                    + "join cud_testi b "
                    + "on a.cod_cud = b.cod_cud "
                    + "and b.cod_lang='it' "
                    + "where a.cod_cud like ? or b.des_cud like ? "
                    + "order by " + sort + " " + order + " limit ? , ?";

            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            st.setInt(3, Integer.parseInt(offset));
            st.setInt(4, Integer.parseInt(size));
            rs = st.executeQuery();

            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_cud", rs.getString("cod_cud"));
                loopObj.put("des_cud", Utility.testoDaDb(rs.getString("des_cud")));
                riga.add(loopObj);

            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("cud", riga);
            return ret;
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
    }

    public JSONObject actionPopUpOneriGerarchia(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        int qsi = 1;
        try {
            conn = Utility.getConnection();
            String offset = request.getParameter("start");
            String size = request.getParameter("size");
            String sort = request.getParameter("sort");
            String order = request.getParameter("dir");
            String param = Utility.testoADb(request.getParameter("query"));

            String query = "select count(*) righe "
                    + "from oneri_gerarchia a "
                    + "join oneri_gerarchia_testi b "
                    + "on a.cod_figlio=b.cod_figlio "
                    + "where b.cod_lang='it' and a.cod_padre=a.cod_figlio "
                    + "and (convert(a.cod_padre,CHAR) like ? or b.des_gerarchia like  ? ) ";
            if (utente.getTipAggregazione() != null) {
                query = query + " and a.tip_aggregazione=? ";
            }
            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            if (utente.getTipAggregazione() != null) {
                st.setString(3, utente.getTipAggregazione());
            }
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);

            query = "select distinct a.cod_padre cod_padre,a.cod_figlio cod_figlio ,b.des_gerarchia des_gerarchia "
                    + "from oneri_gerarchia a "
                    + "join oneri_gerarchia_testi b "
                    + "on a.cod_figlio=b.cod_figlio "
                    + "where b.cod_lang='it' and a.cod_padre=a.cod_figlio "
                    + "and (convert(a.cod_padre,CHAR) like ? or b.des_gerarchia like  ? ) ";
            if (utente.getTipAggregazione() != null) {
                query = query + "and a.tip_aggregazione=? ";
            }
            query = query + "order by " + sort + " " + order + " limit ? , ?";

            st = conn.prepareStatement(query);
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

            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_padre", rs.getInt("cod_padre"));
                loopObj.put("cod_figlio", rs.getInt("cod_figlio"));
                loopObj.put("des_gerarchia", Utility.testoDaDb(rs.getString("des_gerarchia")));
                riga.add(loopObj);

            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("oneri_gerarchia", riga);
            return ret;
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
    }

    public JSONObject actionPopUpOneriDocumenti(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        int qsi = 1;
        try {
            conn = Utility.getConnection();
            String offset = request.getParameter("start");
            String size = request.getParameter("size");
            String sort = request.getParameter("sort");
            String order = request.getParameter("dir");
            String param = Utility.testoADb(request.getParameter("query"));

            String query = "select count(*) righe "
                    + "from oneri_documenti a "
                    + "join oneri_documenti_testi b "
                    + "on a.cod_doc_onere=b.cod_doc_onere "
                    + "where b.cod_lang='it' "
                    + "and (a.cod_doc_onere like ? or  b.des_doc_onere like  ? ) ";
            if ((Boolean) session.getAttribute("territorialitaNew")) {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and (a.tip_aggregazione=? or a.tip_aggregazione is null) ";
                }
            } else {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and a.tip_aggregazione=? ";
                }
            }
            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            if (utente.getTipAggregazione() != null) {
                st.setString(3, utente.getTipAggregazione());
            }
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);

            query = "select distinct a.cod_doc_onere cod_doc_onere,b.des_doc_onere des_doc_onere "
                    + "from oneri_documenti a "
                    + "join oneri_documenti_testi b "
                    + "on a.cod_doc_onere=b.cod_doc_onere "
                    + "where b.cod_lang='it' "
                    + "and (a.cod_doc_onere like ? or  b.des_doc_onere like  ? ) ";
            if ((Boolean) session.getAttribute("territorialitaNew")) {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and (a.tip_aggregazione=? or a.tip_aggregazione is null) ";
                }
            } else {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and a.tip_aggregazione=? ";
                }
            }
            query = query + "order by " + sort + " " + order + " limit ? , ?";

            st = conn.prepareStatement(query);
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

            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            rs = st.executeQuery();
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_doc_onere", rs.getString("cod_doc_onere"));
                loopObj.put("des_doc_onere", Utility.testoDaDb(rs.getString("des_doc_onere")));
                riga.add(loopObj);

            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("oneri_documenti", riga);
            return ret;
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
    }

    public JSONObject actionPopUpComuni(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
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
                    + "join classi_enti d "
                    + "on a.cod_classe_ente=d.cod_classe_ente "
                    + "where b.cod_lang='it' and d.flg_com = 'S' "
                    + "and (a.cod_ente like ? or b.des_ente like ?) ";
            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);

            query = "select a.cod_ente cod_com, b.des_ente "
                    + "from enti_comuni a "
                    + "join enti_comuni_testi b "
                    + "on a.cod_ente=b.cod_ente "
                    + "join classi_enti d "
                    + "on a.cod_classe_ente=d.cod_classe_ente "
                    + "where b.cod_lang='it' and d.flg_com = 'S' "
                    + "and (a.cod_ente like ? or b.des_ente like ?) "
                    + "order by " + sort + " " + order + " limit ? , ?";

            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            st.setInt(3, Integer.parseInt(offset));
            st.setInt(4, Integer.parseInt(size));
            rs = st.executeQuery();

            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_com", rs.getString("cod_com"));
                loopObj.put("des_ente", Utility.testoDaDb(rs.getString("des_ente")));
                riga.add(loopObj);

            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("comuni", riga);
            return ret;
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
    }

    public JSONObject actionPopUpOneriFormula(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        int qsi = 1;
        try {
            conn = Utility.getConnection();
            String offset = request.getParameter("start");
            String size = request.getParameter("size");
            String sort = request.getParameter("sort");
            String order = request.getParameter("dir");
            String param = Utility.testoADb(request.getParameter("query"));

            String query = "select count(*) righe "
                    + "from oneri_formula "
                    + "where (cod_onere_formula like ? or des_formula like ?) ";
            if ((Boolean) session.getAttribute("territorialitaNew")) {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and (tip_aggregazione=? or tip_aggregazione is null) ";
                }
            } else {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and tip_aggregazione=? ";
                }
            }
            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            if (utente.getTipAggregazione() != null) {
                st.setString(3, utente.getTipAggregazione());
            }
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);

            query = "select cod_onere_formula, des_formula "
                    + "from oneri_formula "
                    + "where (cod_onere_formula like ? or des_formula like ?) ";
            if ((Boolean) session.getAttribute("territorialitaNew")) {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and (tip_aggregazione=? or tip_aggregazione is null) ";
                }
            } else {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and tip_aggregazione=? ";
                }
            }
            query = query + "order by " + sort + " " + order + " limit ? , ?";

            st = conn.prepareStatement(query);
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

            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_onere_formula", rs.getString("cod_onere_formula"));
                loopObj.put("des_formula", Utility.testoDaDb(rs.getString("des_formula")));
                riga.add(loopObj);

            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("oneri_formula", riga);
            return ret;
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
    }

    public JSONObject actionPopUpOneriCampi(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        int qsi = 1;
        try {
            conn = Utility.getConnection();
            String offset = request.getParameter("start");
            String size = request.getParameter("size");
            String sort = request.getParameter("sort");
            String order = request.getParameter("dir");
            String param = Utility.testoADb(request.getParameter("query"));

            String query = "select count(*) righe "
                    + "from oneri_campi a "
                    + "join oneri_campi_testi b "
                    + "on a.cod_onere_campo=b.cod_onere_campo "
                    + "where b.cod_lang='it' "
                    + "and (a.cod_onere_campo like ? or b.testo_campo like ?) ";
            if ((Boolean) session.getAttribute("territorialitaNew")) {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and (a.tip_aggregazione=? or a.tip_aggregazione is null) ";
                }
            } else {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and a.tip_aggregazione=? ";
                }
            }
            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            if (utente.getTipAggregazione() != null) {
                st.setString(3, utente.getTipAggregazione());
            }
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);

            query = "select distinct a.cod_onere_campo, b.testo_campo "
                    + "from oneri_campi a "
                    + "join oneri_campi_testi b "
                    + "on a.cod_onere_campo=b.cod_onere_campo "
                    + "where b.cod_lang='it' "
                    + "and (a.cod_onere_campo like ? or b.testo_campo like ?) ";
            if ((Boolean) session.getAttribute("territorialitaNew")) {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and (a.tip_aggregazione=? or a.tip_aggregazione is null) ";
                }
            } else {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and a.tip_aggregazione=? ";
                }
            }
            query = query + "order by " + sort + " " + order + " limit ? , ?";

            st = conn.prepareStatement(query);
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

            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_onere_campo", rs.getString("cod_onere_campo"));
                loopObj.put("testo_campo", Utility.testoDaDb(rs.getString("testo_campo")));
                riga.add(loopObj);

            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("oneri_campi", riga);
            return ret;
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
    }

    public JSONObject actionPopUpOneri(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        int qsi = 1;
        try {
            conn = Utility.getConnection();
            String offset = request.getParameter("start");
            String size = request.getParameter("size");
            String sort = request.getParameter("sort");
            String order = request.getParameter("dir");
            String param = Utility.testoADb(request.getParameter("query"));

            String query = "select count(*) righe "
                    + "from oneri a "
                    + "join oneri_testi b "
                    + "on a.cod_oneri=b.cod_oneri "
                    + "where b.cod_lang='it' "
                    + "and (a.cod_oneri like ? or b.des_oneri like ?) ";
            if ((Boolean) session.getAttribute("territorialitaNew")) {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and (a.tip_aggregazione=? or a.tip_aggregazione is null) ";
                }
            } else {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and a.tip_aggregazione=? ";
                }
            }
            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            if (utente.getTipAggregazione() != null) {
                st.setString(3, utente.getTipAggregazione());
            }
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);

            query = "select distinct a.cod_oneri, b.des_oneri "
                    + "from oneri a "
                    + "join oneri_testi b "
                    + "on a.cod_oneri=b.cod_oneri "
                    + "where b.cod_lang='it' "
                    + "and (a.cod_oneri like ? or b.des_oneri like ?) ";
            if ((Boolean) session.getAttribute("territorialitaNew")) {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and (a.tip_aggregazione=? or a.tip_aggregazione is null) ";
                }
            } else {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and a.tip_aggregazione=? ";
                }
            }
            query = query + "order by " + sort + " " + order + " limit ? , ?";

            st = conn.prepareStatement(query);
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

            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_oneri", rs.getString("cod_oneri"));
                loopObj.put("des_oneri", Utility.testoDaDb(rs.getString("des_oneri")));
                riga.add(loopObj);

            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("oneri", riga);
            return ret;
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
    }

    public JSONObject actionPopUpDocumenti(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        Utente utente = (Utente) session.getAttribute("utente");
        int qsi = 1;
        try {
            conn = Utility.getConnection();
            String offset = request.getParameter("start");
            String size = request.getParameter("size");
            String sort = request.getParameter("sort");
            String order = request.getParameter("dir");
            String param = Utility.testoADb(request.getParameter("query"));

            String query = "Select count(*) righe "
                    + "from (select a.cod_doc, a.href, b.tit_doc, d.tit_href, e.nome_file, b.des_doc, b.cod_lang, a.tip_aggregazione "
                    + "from documenti a "
                    + "join documenti_testi b "
                    + "on a.cod_doc=b.cod_doc "
                    + "left join href c "
                    + "on a.href=c.href "
                    + "left join href_testi d "
                    + "on a.href=d.href "
                    + "and b.cod_lang=d.cod_lang "
                    + "left join documenti_documenti e "
                    + "on a.cod_doc=e.cod_doc "
                    + "and b.cod_lang=e.cod_lang ) p "
                    + "where p.cod_lang = 'it' and ( p.cod_doc like ? or p.tit_doc like ? or p.href like ? or p.tit_href like ? ) ";
            if ((Boolean) session.getAttribute("territorialitaNew")) {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and (p.tip_aggregazione=? or p.tip_aggregazione is null) ";
                }
            } else {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and p.tip_aggregazione=? ";
                }
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

            query = "select p.cod_doc cod_doc, p.tit_doc tit_doc, p.href href, p.tit_href tit_href, p.tip_aggregazione, p.des_aggregazione "
                    + "from (select a.cod_doc, a.href, b.tit_doc, d.tit_href, e.nome_file, b.des_doc, b.cod_lang, a.tip_aggregazione, h.des_aggregazione "
                    + "from documenti a "
                    + "join documenti_testi b "
                    + "on a.cod_doc=b.cod_doc "
                    + "left join href c "
                    + "on a.href=c.href "
                    + "left join href_testi d "
                    + "on a.href=d.href "
                    + "and b.cod_lang=d.cod_lang "
                    + "left join documenti_documenti e "
                    + "on a.cod_doc=e.cod_doc "
                    + "and b.cod_lang=e.cod_lang "
                    + "left join tipi_aggregazione h "
                    + "on a.tip_aggregazione = h.tip_aggregazione ) p "
                    + "where p.cod_lang = 'it' and ( p.cod_doc like ? or p.tit_doc like ? or p.href like ? or p.tit_href like ? ) ";
            if ((Boolean) session.getAttribute("territorialitaNew")) {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and (p.tip_aggregazione=? or p.tip_aggregazione is null) ";
                }
            } else {
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

            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_doc", rs.getString("cod_doc"));
                loopObj.put("tit_doc", Utility.testoDaDb(rs.getString("tit_doc")));
                loopObj.put("href", rs.getString("href"));
                loopObj.put("tit_href", Utility.testoDaDb(rs.getString("tit_href")));
                riga.add(loopObj);

            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("documenti", riga);
            return ret;
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
    }

    public JSONObject actionPopUpSportelli(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
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
                    + "from sportelli a "
                    + "join sportelli_testi b "
                    + "on a.cod_sport=b.cod_sport "
                    + "where b.cod_lang='it' and (a.cod_sport like ? or b.des_sport like ?)  ";
            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");

            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);

            query = "select a.cod_sport cod_sport, b.des_sport des_sport "
                    + "from sportelli a "
                    + "join sportelli_testi b "
                    + "on a.cod_sport=b.cod_sport "
                    + "where b.cod_lang='it' and (a.cod_sport like ? or b.des_sport like ?)  "
                    + "order by " + sort + " " + order + " limit ? , ?";

            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            st.setInt(3, Integer.parseInt(offset));
            st.setInt(4, Integer.parseInt(size));
            rs = st.executeQuery();

            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_sport", rs.getString("cod_sport"));
                loopObj.put("des_sport", Utility.testoDaDb(rs.getString("des_sport")));
                riga.add(loopObj);

            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("sportelli", riga);
            return ret;
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
    }

    public JSONObject actionPopUpDestinatari(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
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
                    + "from destinatari a "
                    + "join destinatari_testi b "
                    + "on a.cod_dest=b.cod_dest "
                    + "where b.cod_lang='it' and (a.cod_dest like ? or b.nome_dest like ? or b.intestazione like ?) ";
            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            st.setString(3, "%" + param + "%");
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);

            query = "select a.cod_dest, b.nome_dest, b.intestazione "
                    + "from destinatari a "
                    + "join destinatari_testi b "
                    + "on a.cod_dest=b.cod_dest "
                    + "where b.cod_lang='it' and (a.cod_dest like ? or b.nome_dest like ? or b.intestazione like ?)  "
                    + "order by " + sort + " " + order + " limit ? , ?";

            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            st.setString(3, "%" + param + "%");
            st.setInt(4, Integer.parseInt(offset));
            st.setInt(5, Integer.parseInt(size));
            rs = st.executeQuery();

            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_dest", rs.getString("cod_dest"));
                loopObj.put("nome_dest", Utility.testoDaDb(rs.getString("nome_dest")));
                loopObj.put("intestazione", Utility.testoDaDb(rs.getString("intestazione")));
                riga.add(loopObj);

            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("destinatari", riga);
            return ret;
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
    }

    public JSONObject actionPopUpServizi(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
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
                    + "from servizi a "
                    + "join servizi_testi b "
                    + "on a.cod_servizio=b.cod_servizio "
                    + "and a.cod_com=b.cod_com "
                    + "and a.cod_eve_vita=b.cod_eve_vita "
                    + "where b.cod_lang='it' and (convert(a.cod_servizio,CHAR) like ? or b.nome_servizio like ?)  ";
            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");

            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);

            query = "select  a.cod_servizio cod_servizio,a.cod_com cod_com,a.cod_eve_vita cod_eve_vita, "
                    + "b.nome_servizio nome_servizio,b.des_servizio des_servizio "
                    + "from servizi a "
                    + "join servizi_testi b "
                    + "on a.cod_servizio=b.cod_servizio "
                    + "and a.cod_com=b.cod_com "
                    + "and a.cod_eve_vita=b.cod_eve_vita "
                    + "where b.cod_lang='it' and (convert(a.cod_servizio,CHAR) like ? or b.nome_servizio like ?)  "
                    + "order by " + sort + " " + order + " limit ? , ?";

            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            st.setInt(3, Integer.parseInt(offset));
            st.setInt(4, Integer.parseInt(size));
            rs = st.executeQuery();

            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_servizio", rs.getInt("cod_servizio"));
                loopObj.put("cod_com", rs.getInt("cod_com"));
                loopObj.put("cod_eve_vita", rs.getInt("cod_eve_vita"));
                loopObj.put("nome_servizio", Utility.testoDaDb(rs.getString("nome_servizio")));
                loopObj.put("des_servizio", Utility.testoDaDb(rs.getString("des_servizio")));
                riga.add(loopObj);

            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("servizi", riga);
            return ret;
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
    }

    public JSONObject actionPopUpTipiRif(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
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
                    + "from tipi_rif "
                    + "where cod_lang = 'it'  and (cod_tipo_rif like ? or tipo_rif like ?)";
            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);

            query = "select cod_tipo_rif , tipo_rif "
                    + "from tipi_rif "
                    + "where cod_lang = 'it'  and (cod_tipo_rif like ? or tipo_rif like ?)"
                    + "order by " + sort + " " + order + " limit ? , ?";

            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            st.setInt(3, Integer.parseInt(offset));
            st.setInt(4, Integer.parseInt(size));
            rs = st.executeQuery();

            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_tipo_rif", rs.getString("cod_tipo_rif"));
                loopObj.put("tipo_rif", Utility.testoDaDb(rs.getString("tipo_rif")));
                riga.add(loopObj);

            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("tipi_rif", riga);
            return ret;
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
    }

    public JSONObject actionPopUpTestoCondizioni(HttpServletRequest request) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        int qsi = 1;
        try {
            conn = Utility.getConnection();
            String offset = request.getParameter("start");
            String size = request.getParameter("size");
            String sort = request.getParameter("sort");
            String order = request.getParameter("dir");
            String param = Utility.testoADb(request.getParameter("query"));

            String query = "select count(*) righe from"
                    + "( select a.cod_cond, a.testo_cond, a.cod_lang,a.tip_aggregazione from testo_condizioni a ) p "
                    + "where p.cod_lang='it' and (p.testo_cond like ? or p.cod_cond like ? ) ";

            if ((Boolean) session.getAttribute("territorialitaNew")) {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and (p.tip_aggregazione=? or p.tip_aggregazione is null) ";
                }
            } else {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and p.tip_aggregazione=? ";
                }
            }
            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            if (utente.getTipAggregazione() != null) {
                st.setString(3, utente.getTipAggregazione());
            }
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);

            query = "select  p.cod_cond cod_cond, p.testo_cond testo_cond from"
                    + "( select a.cod_cond, a.testo_cond, a.cod_lang,a.tip_aggregazione from testo_condizioni a ) p "
                    + "where p.cod_lang='it' and (p.testo_cond like ? or p.cod_cond like ? ) ";
            if ((Boolean) session.getAttribute("territorialitaNew")) {
                if (utente.getTipAggregazione() != null) {
                    query = query + "and (p.tip_aggregazione=? or p.tip_aggregazione is null) ";
                }
            } else {
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
            if (utente.getTipAggregazione() != null) {
                st.setString(qsi, utente.getTipAggregazione());
                qsi++;
            }
            st.setInt(qsi, Integer.parseInt(offset));
            qsi++;
            st.setInt(qsi, Integer.parseInt(size));
            rs = st.executeQuery();

            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_cond", rs.getString("cod_cond"));
                loopObj.put("testo_cond", Utility.testoDaDb(rs.getString("testo_cond")));
                riga.add(loopObj);

            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("testo_condizioni", riga);
            return ret;
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
    }

    public JSONObject actionPopUpInterventiCollegati(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        Utente utente = (Utente) session.getAttribute("utente");
        int qsi = 1;
        try {
            conn = Utility.getConnection();
            String offset = request.getParameter("start");
            String size = request.getParameter("size");
            String sort = request.getParameter("sort");
            String order = request.getParameter("dir");
            String param = Utility.testoADb(request.getParameter("query"));

            String query = "select count(*) righe "
                    + "from interventi a "
                    + "join interventi_testi b "
                    + "on a.cod_int = b.cod_int "
                    + "and b.cod_lang='it' "
                    + "where ( convert(a.cod_int,CHAR) like ? or b.tit_int like ? )";
            if (utente.getTipAggregazione() != null) {
                query = query + "and a.tip_aggregazione=? ";
            }
            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            if (utente.getTipAggregazione() != null) {
                st.setString(3, utente.getTipAggregazione());
            }
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);

            query = "select a.cod_int cod_int, b.tit_int tit_int "
                    + "from interventi a "
                    + "join interventi_testi b "
                    + "on a.cod_int = b.cod_int "
                    + "and b.cod_lang='it' "
                    + "where ( convert(a.cod_int,CHAR) like ? or b.tit_int like ? ) ";
            if (utente.getTipAggregazione() != null) {
                query = query + "and a.tip_aggregazione=? ";
            }
            query = query + "order by " + sort + " " + order + " limit ? , ?";

            st = conn.prepareStatement(query);
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

            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_int", rs.getInt("cod_int"));
                loopObj.put("tit_int", Utility.testoDaDb(rs.getString("tit_int")));
                riga.add(loopObj);

            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("interventi_collegati", riga);
            return ret;
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
    }

    public JSONObject actionPopUpFormularioAE(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
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
                    + "from formulario_ae fae "
                    + "where fae.codice_ente like ? or fae.tipologia_ufficio like ? or fae.codice_ufficio like ? "
                    + "or fae.tipologia_ente like ? or fae.denominazione like ? or fae.codice_catastale_comune like ? or fae.ufficio_statale like ?";
            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
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

            query = "select fae.id, fae.data_validita, fae.codice_ente, fae.tipologia_ufficio, fae.codice_ufficio, fae.tipologia_ente, fae.denominazione, fae.codice_catastale_comune, fae.data_decorrenza, fae.ufficio_statale "
                    + "from formulario_ae fae "
                    + "where fae.codice_ente like ? or fae.tipologia_ufficio like ? or fae.codice_ufficio like ? "
                    + "or fae.tipologia_ente like ? or fae.denominazione like ? or fae.codice_catastale_comune like ? or fae.ufficio_statale like ? "
                    + "order by " + sort + " " + order + " limit ? , ?";

            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            st.setString(3, "%" + param + "%");
            st.setString(4, "%" + param + "%");
            st.setString(5, "%" + param + "%");
            st.setString(6, "%" + param + "%");
            st.setString(7, "%" + param + "%");
            st.setInt(8, Integer.parseInt(offset));
            st.setInt(9, Integer.parseInt(size));
            rs = st.executeQuery();

            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("id", rs.getInt("id"));
                loopObj.put("data_validita", Utility.testoDaDb(rs.getString("data_validita")));
                loopObj.put("codice_ente", Utility.testoDaDb(rs.getString("codice_ente")));
                loopObj.put("tipologia_ufficio", Utility.testoDaDb(rs.getString("tipologia_ufficio")));
                loopObj.put("codice_ufficio", Utility.testoDaDb(rs.getString("codice_ufficio")));
                loopObj.put("tipologia_ente", Utility.testoDaDb(rs.getString("tipologia_ente")));
                loopObj.put("denominazione", Utility.testoDaDb(rs.getString("denominazione")));
                loopObj.put("codice_catastale_comune", Utility.testoDaDb(rs.getString("codice_catastale_comune")));
                loopObj.put("data_decorrenza", Utility.testoDaDb(rs.getString("data_decorrenza")));
                loopObj.put("ufficio_statale", Utility.testoDaDb(rs.getString("ufficio_statale")));
                riga.add(loopObj);

            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("formulario_ae", riga);
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }
}
