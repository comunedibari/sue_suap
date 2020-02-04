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

import it.people.dbm.model.Utente;
import it.people.dbm.utility.Utility;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.LoggerFactory;
/**
 *
 * @author Piergiorgio
 */
public class Mappatura {

    private static Logger log = LoggerFactory.getLogger(Mappatura.class);

    public JSONObject action(HttpServletRequest request) throws Exception {
        JSONObject ret = null;
        if (request.getParameter("table_name").equals("table_mappatura_interventi")) {
            ret = actionInterventi(request);
        }
        if (request.getParameter("table_name").equals("table_mappatura_procedimenti")) {
            ret = actionProcedimenti(request);
        }

        return ret;
    }

    public JSONObject actionProcedimenti(HttpServletRequest request) throws Exception {
        Connection conn = null;
        HttpSession session = request.getSession();
		Utente utente = (Utente) session.getAttribute("utente");
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        String query = null;
        try {
            conn = Utility.getConnection();
            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            String sort = request.getParameter("sort");
            String order = request.getParameter("dir");
            query = "select b.cod_proc, b.cod_cud, b.ter_eva, b.flg_bollo, b.flg_tipo_proc, c.tit_proc, "
                    + "e.des_cud, "
                    + "h.cod_com, h.cod_sport, h.cod_dest, "
                    + "dt.intestazione, "
                    + "k.des_sport, "
                    + "m.des_ente, "
                    + "n.cod_int, o.tit_int "
                    + "from procedimenti b "
                    + "join  procedimenti_testi c "
                    + "on  b.cod_proc = c.cod_proc "
                    + "join cud d "
                    + "on  b.cod_cud = d.cod_cud "
                    + "join  cud_testi e "
                    + "on  d.cod_cud = e.cod_cud "
                    + "and e.cod_lang=c.cod_lang "
                    + "join  relazioni_enti h "
                    + "on  d.cod_cud = h.cod_cud "
                    + "join  destinatari i "
                    + "on  h.cod_dest = i.cod_dest "
                    + "join destinatari_testi dt "
                    + "on i.cod_dest=dt.cod_dest "
                    + "and dt.cod_lang=c.cod_lang "
                    + "join  sportelli j "
                    + "on  h.cod_sport = j.cod_sport "
                    + "join  sportelli_testi k "
                    + "on  j.cod_sport = k.cod_sport "
                    + "and  k.cod_lang = c.cod_lang "
                    + "join  enti_comuni l "
                    + "on  h.cod_com = l.cod_ente "
                    + "join  enti_comuni_testi m "
                    + "on  l.cod_ente = m.cod_ente "
                    + "and  m.cod_lang = c.cod_lang "
                    + "join interventi n "
                    + "on n.cod_proc = b.cod_proc "
                    + "join interventi_testi o "
                    + "on o.cod_int = n.cod_int "
                    + "and o.cod_lang=c.cod_lang "
                    + "where  b.cod_proc = ? and c.cod_lang='it' ";
            if (utente.getTipAggregazione() != null) {
                query = query + "and n.tip_aggregazione=? ";
            }
            query = query + "order by " + sort + " " + order ;
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("cod_proc"));
            if (utente.getTipAggregazione() != null) {
                st.setString(2, utente.getTipAggregazione());
            }
            rs = st.executeQuery();

            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_int", rs.getInt("cod_int"));
                loopObj.put("tit_int", Utility.testoDaDb(rs.getString("tit_int")));
                loopObj.put("cod_sport", rs.getString("cod_sport"));
                loopObj.put("des_sport", Utility.testoDaDb(rs.getString("des_sport")));
                loopObj.put("cod_dest", rs.getString("cod_dest"));
                loopObj.put("intestazione", Utility.testoDaDb(rs.getString("intestazione")));
                loopObj.put("cod_com", rs.getString("cod_com"));
                loopObj.put("des_ente", Utility.testoDaDb(rs.getString("des_ente")));
                loopObj.put("cod_cud", rs.getString("cod_cud"));
                loopObj.put("des_cud", Utility.testoDaDb(rs.getString("des_cud")));
                riga.add(loopObj);
            }
            ret = new JSONObject();
            ret.put("table_mappatura_procedimenti", riga);
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject actionInterventi(HttpServletRequest request) throws Exception {
        Connection conn = null;
        HttpSession session = request.getSession();
		Utente utente = (Utente) session.getAttribute("utente");
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        String query = null;
        try {
            conn = Utility.getConnection();
            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            String sort = request.getParameter("sort");
            String order = request.getParameter("dir");
            query = "select b.cod_proc, b.cod_cud, b.ter_eva, b.flg_bollo, b.flg_tipo_proc, c.tit_proc, "
                    + "e.des_cud, "
                    + "h.cod_com, h.cod_sport, h.cod_dest, "
                    + "dt.intestazione, "
                    + "k.des_sport, "
                    + "m.des_ente, "
                    + "n.cod_int, o.tit_int "
                    + "from procedimenti b "
                    + "join  procedimenti_testi c "
                    + "on  b.cod_proc = c.cod_proc "
                    + "join cud d "
                    + "on  b.cod_cud = d.cod_cud "
                    + "join  cud_testi e "
                    + "on  d.cod_cud = e.cod_cud "
                    + "and e.cod_lang=c.cod_lang "
                    + "join  relazioni_enti h "
                    + "on  d.cod_cud = h.cod_cud "
                    + "join  destinatari i "
                    + "on  h.cod_dest = i.cod_dest "
                    + "join destinatari_testi dt "
                    + "on dt.cod_dest=i.cod_dest "
                    + "and dt.cod_lang=c.cod_lang "
                    + "join  sportelli j "
                    + "on  h.cod_sport = j.cod_sport "
                    + "join  sportelli_testi k "
                    + "on  j.cod_sport = k.cod_sport "
                    + "and  k.cod_lang = c.cod_lang "
                    + "join  enti_comuni l "
                    + "on  h.cod_com = l.cod_ente "
                    + "join  enti_comuni_testi m "
                    + "on  l.cod_ente = m.cod_ente "
                    + "and  m.cod_lang = c.cod_lang "
                    + "join interventi n "
                    + "on n.cod_proc = b.cod_proc "
                    + "join interventi_testi o "
                    + "on o.cod_int = n.cod_int "
                    + "and o.cod_lang=c.cod_lang "
                    + "where  o.cod_int = ? and c.cod_lang='it' ";
            if (utente.getTipAggregazione() != null) {
                query = query + "and n.tip_aggregazione=? ";
            }            
            query = query + "order by " + sort + " " + order ;
            st = conn.prepareStatement(query);
            st.setInt(1, Integer.parseInt(request.getParameter("cod_int")));
            if (utente.getTipAggregazione() != null) {
                st.setString(2, utente.getTipAggregazione());
            }
            rs = st.executeQuery();

            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_int", rs.getInt("cod_int"));
                loopObj.put("tit_int", Utility.testoDaDb(rs.getString("tit_int")));
                loopObj.put("cod_proc", rs.getInt("cod_proc"));
                loopObj.put("tit_proc", Utility.testoDaDb(rs.getString("tit_proc")));
                loopObj.put("cod_sport", rs.getString("cod_sport"));
                loopObj.put("des_sport", Utility.testoDaDb(rs.getString("des_sport")));
                loopObj.put("cod_dest", rs.getString("cod_dest"));
                loopObj.put("intestazione", Utility.testoDaDb(rs.getString("intestazione")));
                loopObj.put("cod_com", rs.getString("cod_com"));
                loopObj.put("des_ente", Utility.testoDaDb(rs.getString("des_ente")));
                loopObj.put("cod_cud", rs.getString("cod_cud"));
                loopObj.put("des_cud", Utility.testoDaDb(rs.getString("des_cud")));
                riga.add(loopObj);
            }
            ret = new JSONObject();
            ret.put("table_mappatura_interventi", riga);
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
            query = "insert into allegati (cod_int,cod_doc,cod_cond,flg_obb,flg_autocert,tipologie,num_max_pag , dimensione_max, copie, ordinamento) "
                    + "values (?,?,?,?,?,?,?,?,?,?)";
            st = conn.prepareStatement(query);
            st.setInt(1, Integer.parseInt(request.getParameter("cod_int")));
            st.setString(2, request.getParameter("cod_doc"));
            st.setString(3, (request.getParameter("cod_cond") != null && !request.getParameter("cod_cond").equals("") ? request.getParameter("cod_cond") : null));
            st.setString(4, request.getParameter("flg_obb_hidden"));
            st.setString(5, request.getParameter("flg_autocert_hidden"));
            st.setString(6, (request.getParameter("tipologie") != null && !request.getParameter("tipologie").equals("") ? request.getParameter("tipologie") : null));
            if (request.getParameter("num_max_pag") != null && !request.getParameter("num_max_pag").equals("")) {
                st.setInt(7, Integer.parseInt(request.getParameter("num_max_pag")));
            } else {
                st.setNull(7, java.sql.Types.INTEGER);
            }
            if (request.getParameter("dimensione_max") != null && !request.getParameter("dimensione_max").equals("")) {
                st.setInt(8, Integer.parseInt(request.getParameter("dimensione_max")));
            } else {
                st.setNull(8, java.sql.Types.INTEGER);
            }
            st.setInt(9, (request.getParameter("copie") != null && !request.getParameter("copie").equals("") ? Integer.parseInt(request.getParameter("copie")) : 1));
            st.setInt(10, (request.getParameter("ordinamento") != null && !request.getParameter("ordinamento").equals("") ? Integer.parseInt(request.getParameter("ordinamento")) : 9999));

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
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        String query;
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            query = "update allegati set cod_cond = ?,flg_obb = ?,flg_autocert=?,tipologie=?,num_max_pag=? , dimensione_max=?, copie=?, ordinamento = ? "
                    + "where cod_int=? and cod_doc=?";
            st = conn.prepareStatement(query);
            st.setString(1, (request.getParameter("cod_cond") != null && !request.getParameter("cod_cond").equals("") ? request.getParameter("cod_cond") : null));
            st.setString(2, request.getParameter("flg_obb_hidden"));
            st.setString(3, request.getParameter("flg_autocert_hidden"));
            st.setString(4, (request.getParameter("tipologie") != null && !request.getParameter("tipologie").equals("") ? request.getParameter("tipologie") : null));
            if (request.getParameter("num_max_pag") != null && !request.getParameter("num_max_pag").equals("")) {
                st.setInt(5, Integer.parseInt(request.getParameter("num_max_pag")));
            } else {
                st.setNull(5, java.sql.Types.INTEGER);
            }
            if (request.getParameter("dimensione_max") != null && !request.getParameter("dimensione_max").equals("")) {
                st.setInt(6, Integer.parseInt(request.getParameter("dimensione_max")));
            } else {
                st.setNull(6, java.sql.Types.INTEGER);
            }
            st.setInt(7, (request.getParameter("copie") != null && !request.getParameter("copie").equals("") ? Integer.parseInt(request.getParameter("copie")) : 1));
            st.setInt(8, (request.getParameter("ordinamento") != null && !request.getParameter("ordinamento").equals("") ? Integer.parseInt(request.getParameter("ordinamento")) : 9999));
            st.setInt(9, Integer.parseInt(request.getParameter("cod_int")));
            st.setString(10, request.getParameter("cod_doc"));
            st.executeUpdate();

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

    public JSONObject cancella(HttpServletRequest request) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        String query;
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            query = "delete from allegati where cod_int=? and cod_doc=?";
            st = conn.prepareStatement(query);
            st.setInt(1, Integer.parseInt(request.getParameter("cod_int")));
            st.setString(2, request.getParameter("cod_doc"));
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
