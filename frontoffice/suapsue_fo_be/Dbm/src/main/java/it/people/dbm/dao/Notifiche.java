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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;

import it.people.dbm.model.Utente;
import it.people.dbm.utility.Utility;
import it.people.dbm.xsd.sqlNotifiche.DocumentRoot;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.LoggerFactory;
/**
 *
 * @author Piergiorgio
 */
public class Notifiche {

    private static Logger log = LoggerFactory.getLogger(Notifiche.class);

    public JSONObject action(HttpServletRequest request) throws Exception {
        JSONObject ret = null;
        if (request.getParameter("interventi") != null && request.getParameter("interventi").equals("yes")) {
            ret = actionInterventi(request);
        } else {
            ret = actionNotifiche(request);
        }
        return ret;
    }

    public JSONObject actionInterventi(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        try {
            conn = Utility.getConnection();
            Integer cnt = Integer.parseInt(request.getParameter("cnt"));

            String query = "select a.cod_int,c.tit_int from notifiche_utenti_interventi a "
                    + "join interventi b "
                    + "on a.cod_int=b.cod_int "
                    + "join interventi_testi c "
                    + "on a.cod_int=c.cod_int "
                    + "where c.cod_lang='it' "
                    + "and a.cnt= ? and a.cod_utente = ?";

            st = conn.prepareStatement(query);
            st.setInt(1, cnt);
            st.setString(2, utente.getCodUtente());
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
            ret.put("interventi", riga);
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject actionNotifiche(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
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

            String query = "select count(distinct a.cnt) righe "
                    + "from notifiche a "
                    + "join utenti b "
                    + "on a.cod_utente_origine=b.cod_utente "
                    + "join notifiche_utenti_interventi c "
                    + "on a.cnt = c.cnt "
                    + "left join utenti f  "
                    + "on a.cod_utente_carico=f.cod_utente "
                    + "where stato_notifica != 'C' "
                    + "and c.cod_utente= ? "
                    + "and ( a.cod_utente_origine like ? or a.testo_notifica like ? or b.cognome_nome like ?) ";
            st = conn.prepareStatement(query);
            st.setString(1, utente.getCodUtente());
            st.setString(2, "%" + param + "%");
            st.setString(3, "%" + param + "%");
            st.setString(4, "%" + param + "%");

            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);

            query = "select distinct a.cnt cnt,a.cod_utente_carico cod_utente_carico,a.cod_utente_origine cod_utente_origine, "
                    + "a.testo_notifica testo_notifica,a.data_notifica data_notifica,a.stato_notifica stato_notifica,a.nome_file nome_file, "
                    + "b.cognome_nome cognome_nome_origine, f.cognome_nome cognome_nome_carico "
                    + "from notifiche a "
                    + "join utenti b "
                    + "on a.cod_utente_origine=b.cod_utente "
                    + "join notifiche_utenti_interventi c "
                    + "on a.cnt = c.cnt "
                    + "left join utenti f  "
                    + "on a.cod_utente_carico=f.cod_utente "
                    + "where stato_notifica != 'C' "
                    + "and c.cod_utente= ? "
                    + "and ( a.cod_utente_origine like ? or a.testo_notifica like ? or b.cognome_nome like ? ) "
                    + "order by " + sort + " " + order + " limit ? , ?";

            st = conn.prepareStatement(query);
            st.setString(1, utente.getCodUtente());
            st.setString(2, "%" + param + "%");
            st.setString(3, "%" + param + "%");
            st.setString(4, "%" + param + "%");
            st.setInt(5, Integer.parseInt(offset));
            st.setInt(6, Integer.parseInt(size));
            rs = st.executeQuery();

            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cnt", rs.getInt("cnt"));
                loopObj.put("cod_utente_carico", Utility.testoDaDb(rs.getString("cod_utente_carico")));
                loopObj.put("cod_utente_origine", rs.getString("cod_utente_origine"));
                loopObj.put("testo_notifica", Utility.testoDaDb(rs.getString("testo_notifica")));
                loopObj.put("data_notifica", Utility.testoDaDb(rs.getString("data_notifica")));
                loopObj.put("stato_notifica", Utility.testoDaDb(rs.getString("stato_notifica")));
                loopObj.put("nome_file", Utility.testoDaDb(rs.getString("nome_file")));
                loopObj.put("cognome_nome_origine", Utility.testoDaDb(rs.getString("cognome_nome_origine")));
                loopObj.put("cognome_nome_carico", Utility.testoDaDb(rs.getString("cognome_nome_carico")));
                riga.add(loopObj);

            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("notifiche", riga);
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject aggiorna(HttpServletRequest request) throws Exception {
        JSONObject ret = null;
        if (request.getParameter("azione") != null && request.getParameter("azione").equals("applica")) {
            ret = aggiornaBancaDati(request);
        } else {
            ret = aggiornaStato(request);
        }
        return ret;
    }

    public JSONObject aggiornaStato(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            String query = "update notifiche "
                    + "set stato_notifica=?, "
                    + "cod_utente_carico=? "
                    + "where cnt=?";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("stato_notifica_hidden"));
            st.setString(2, utente.getCodUtente());
            st.setInt(3, Integer.parseInt(request.getParameter("cnt")));
            st.executeUpdate();
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            if (request.getParameter("stato_notifica_hidden").equals("C")) {
                query = "delete from notifiche_sql where cnt = ?";
                st = conn.prepareStatement(query);
                st.setInt(1, Integer.parseInt(request.getParameter("cnt")));
                st.executeUpdate();
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);

                query = "delete from notifiche_documenti where cnt = ?";
                st = conn.prepareStatement(query);
                st.setInt(1, Integer.parseInt(request.getParameter("cnt")));
                st.executeUpdate();
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);
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

    public JSONObject aggiornaBancaDati(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            String query = "select sql_text from notifiche_sql where cnt=?";
            st = conn.prepareStatement(query);
            st.setInt(1, Integer.parseInt(request.getParameter("cnt")));
            rs = st.executeQuery();
            String xml = null;
            while (rs.next()) {
                xml = rs.getString("sql_text");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            // trasformo l'xml in oggetto
            if (xml != null) {
                JAXBContext jc = JAXBContext.newInstance(DocumentRoot.class);
                Unmarshaller um = jc.createUnmarshaller();
                InputStream is = new ByteArrayInputStream(xml.getBytes("UTF-8"));
                DocumentRoot doc = (DocumentRoot) um.unmarshal(is);
                Iterator it = doc.getSqlString().iterator();
                while (it.hasNext()) {
                    try {
                        query = new String((byte[]) it.next());
                        st = conn.prepareStatement(query);
                        st.executeUpdate();
                        Utility.chiusuraJdbc(rs);
                        Utility.chiusuraJdbc(st);
                    } catch (SQLException e) {
                        Utility.chiusuraJdbc(rs);
                        Utility.chiusuraJdbc(st);
                    }
                }
                query = "delete from notifiche_sql where cnt = ?";
                st = conn.prepareStatement(query);
                st.setInt(1, Integer.parseInt(request.getParameter("cnt")));
                st.executeUpdate();
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);

                query = "delete from notifiche_documenti where cnt = ?";
                st = conn.prepareStatement(query);
                st.setInt(1, Integer.parseInt(request.getParameter("cnt")));
                st.executeUpdate();
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);
            }
            // aggiorna a chiusa la notifica
            query = "update notifiche "
                    + "set stato_notifica=?, "
                    + "cod_utente_carico=? "
                    + "where cnt=?";
            st = conn.prepareStatement(query);
            st.setString(1, "C");
            st.setString(2, utente.getCodUtente());
            st.setInt(3, Integer.parseInt(request.getParameter("cnt")));
            st.executeUpdate();

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
}
