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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;

import it.people.dbm.model.Utente;
import it.people.dbm.utility.Utility;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.slf4j.LoggerFactory;
/**
 *
 * @author Piergiorgio
 */
public class Pubblicazione {

    private static Logger log = LoggerFactory.getLogger(Pubblicazione.class);

    public JSONObject action(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        try {
            String offset = request.getParameter("start");
            String size = request.getParameter("size");
            String sort = request.getParameter("sort");
            String order = request.getParameter("dir");
            String param = Utility.testoADb(request.getParameter("query"));

            conn = Utility.getConnection();

            String query = "select count(*) righe from interventi a "
                    + "join interventi_testi b "
                    + "on a.cod_int=b.cod_int "
                    + "where b.cod_lang='it' "
                    + "and (flg_pubblicazione is null or flg_pubblicazione != 'S') "
                    + "and (a.data_modifica is not null and a.data_pubblicazione is null "
                    + "or a.data_modifica is not null and a.data_pubblicazione is not null and a.data_modifica > a.data_pubblicazione) "
                    + "and ( b.tit_int like ? or  convert(a.cod_int,CHAR) like ?) ";
            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            rs = st.executeQuery();
            while (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            query = "select a.cod_int,b.tit_int,a.data_modifica from interventi a "
                    + "join interventi_testi b "
                    + "on a.cod_int=b.cod_int "
                    + "where b.cod_lang='it' "
                    + "and (flg_pubblicazione is null or flg_pubblicazione != 'S') "
                    + "and (a.data_modifica is not null and a.data_pubblicazione is null "
                    + "or a.data_modifica is not null and a.data_pubblicazione is not null and a.data_modifica > a.data_pubblicazione) "
                    + "and (convert(a.cod_int,CHAR) like ? or b.tit_int like ?) "
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
                loopObj.put("cod_int", rs.getInt("cod_int"));
                loopObj.put("tit_int", Utility.testoDaDb(rs.getString("tit_int")));
                loopObj.put("data_modifica", (rs.getObject("data_modifica") == null ? null : rs.getTimestamp("data_modifica").toString()));
                loopObj.put("note_pubblicazione", "");
                riga.add(loopObj);

            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("pubblicazione", riga);
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

        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            JSONObject dati = (JSONObject) JSONSerializer.toJSON(request.getParameter("dati"));
            JSONArray interventi = dati.getJSONArray("interventi");
            JSONObject jo = null;
            String noteGenerali = dati.getString("noteGenerali");
            String query = "update interventi "
                    + "set flg_pubblicazione = 'S', note_pubblicazione = ?, note_generali_pubblicazione = ? "
                    + "where cod_int =?";
            st = conn.prepareStatement(query);
            for (int i = 0; i < interventi.size(); i++) {
                jo = (JSONObject) interventi.get(i);
                st = conn.prepareStatement(query);
                st.setString(1, jo.getString("notePubblicazione"));
                st.setString(2, noteGenerali);
                st.setInt(3, (Integer) jo.get("codInt"));
                st.executeUpdate();
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);
            }
            ret.put("success", "Aggiornamento effettuato");
        } catch (Exception e) {
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
}
