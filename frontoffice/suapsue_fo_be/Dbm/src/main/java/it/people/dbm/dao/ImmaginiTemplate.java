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

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;

import it.people.dbm.model.ImmagineModel;
import it.people.dbm.utility.Utility;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.LoggerFactory;
/**
 *
 * @author Piergiorgio
 */
public class ImmaginiTemplate {

    private static Logger log = LoggerFactory.getLogger(ImmaginiTemplate.class);

    public JSONObject action(HttpServletRequest request) throws Exception {
        JSONObject ret = null;
        if (request.getParameter("table_name").equals("immagini_template")) {
            ret = actionImmaginiTemplate(request);
        }
        if (request.getParameter("table_name").equals("immagini")) {
            ret = actionImmagini(request);
        }
        return ret;
    }

    public JSONObject actionImmaginiTemplate(HttpServletRequest request) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        String query = null;
        try {
            conn = Utility.getConnection();
            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            String offset = request.getParameter("start");
            String size = request.getParameter("size");
            String sort = request.getParameter("sort");
            String order = request.getParameter("dir");
            String href = request.getParameter("href");
            String param = Utility.testoADb(request.getParameter("query"));

            query = " select count(*) righe from templates_immagini "
                    + "where nome_immagine like ? or des_immagine like ? ";
            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            query = "select nome_immagine,des_immagine from templates_immagini "
                    + "where nome_immagine like ? or des_immagine like ? "
                    + "order by " + sort + " " + order + " limit ? , ?";

            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            st.setInt(3, Integer.parseInt(offset));
            st.setInt(4, Integer.parseInt(size));
            rs = st.executeQuery();

            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("nome_immagine", rs.getString("nome_immagine"));
                loopObj.put("des_immagine", Utility.testoDaDb(rs.getString("des_immagine")));
                riga.add(loopObj);
            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("immagini_template", riga);
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject actionImmagini(HttpServletRequest request) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        String query = null;
        try {
            conn = Utility.getConnection();
            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            String offset = request.getParameter("start");
            String size = request.getParameter("size");
            String sort = request.getParameter("sort");
            String order = request.getParameter("dir");
            String href = request.getParameter("href");
            String param = Utility.testoADb(request.getParameter("query"));

            query = " select count(*) righe "
                    + "from templates_immagini_immagini a "
                    + "left join sportelli b "
                    + "on a.cod_sport=b.cod_sport "
                    + "left join sportelli_testi c "
                    + "on a.cod_sport=c.cod_sport "
                    + "and c.cod_lang='it' "
                    + "left join enti_comuni d "
                    + "on a.cod_com=d.cod_ente "
                    + "left join enti_comuni_testi e "
                    + "on a.cod_com=e.cod_ente "
                    + "and e.cod_lang='it' "
                    + "where nome_immagine=? "
                    + "and (a.cod_sport like ? or a.cod_com like ? or c.des_sport like ? or e.des_ente like ? or a.nome_file like ?)";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("nome_immagine"));
            st.setString(2, "%" + param + "%");
            st.setString(3, "%" + param + "%");
            st.setString(4, "%" + param + "%");
            st.setString(5, "%" + param + "%");
            st.setString(6, "%" + param + "%");
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            query = "select a.nome_immagine nome_immagine,a.nome_file nome_file,a.cod_sport cod_sport, "
                    + "a.cod_com cod_com,c.des_sport des_sport,e.des_ente des_ente, a.cod_lang "
                    + "from templates_immagini_immagini a "
                    + "left join sportelli b "
                    + "on a.cod_sport=b.cod_sport "
                    + "left join sportelli_testi c "
                    + "on a.cod_sport=c.cod_sport "
                    + "and c.cod_lang='it' "
                    + "left join enti_comuni d "
                    + "on a.cod_com=d.cod_ente "
                    + "left join enti_comuni_testi e "
                    + "on a.cod_com=e.cod_ente "
                    + "and e.cod_lang='it' "
                    + "where nome_immagine=? "
                    + "and (a.cod_sport like ? or a.cod_com like ? or c.des_sport like ? or e.des_ente like ? or a.nome_file like ?) "
                    + "order by " + sort + " " + order + " limit ? , ?";

            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("nome_immagine"));
            st.setString(2, "%" + param + "%");
            st.setString(3, "%" + param + "%");
            st.setString(4, "%" + param + "%");
            st.setString(5, "%" + param + "%");
            st.setString(6, "%" + param + "%");
            st.setInt(7, Integer.parseInt(offset));
            st.setInt(8, Integer.parseInt(size));
            rs = st.executeQuery();

            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("nome_immagine", rs.getString("nome_immagine"));
                loopObj.put("cod_sport", rs.getString("cod_sport"));
                loopObj.put("cod_com", rs.getString("cod_com"));
                loopObj.put("cod_lang", rs.getString("cod_lang"));
                loopObj.put("des_sport", Utility.testoDaDb(rs.getString("des_sport")));
                loopObj.put("des_ente", Utility.testoDaDb(rs.getString("des_ente")));
                loopObj.put("nome_file", "<a href=\"ScaricaFile?tipo=immagine&nomeImmagine=" + rs.getString("nome_immagine") + "&codSport=" + rs.getString("cod_sport") + "&codCom=" + rs.getString("cod_com") +"&codLang=" + rs.getString("cod_lang") + "\" target=\"_blank\" alt=\"" + rs.getString("nome_file") + "\">" + rs.getString("nome_file") + "</a>");
                riga.add(loopObj);
            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("immagini", riga);
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
            query = "insert into templates_immagini (nome_immagine,des_immagine) "
                    + "values (?,?)";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("nome_immagine"));
            st.setString(2, Utility.testoADb(request.getParameter("des_immagine")));
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

    public JSONObject cancella(HttpServletRequest request) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        String query;
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            query = "delete from templates_immagini_immagini where nome_immagine=?";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("nome_immagine"));
            st.executeUpdate();
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);

            query = "delete from templates_immagini where nome_immagine=?";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("nome_immagine"));
            st.executeUpdate();
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);

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

    public JSONObject aggiorna(HttpServletRequest request) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        String query;
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            query = "update templates_immagini set des_immagine=? where nome_immagine=?";
            st = conn.prepareStatement(query);
            st.setString(1, Utility.testoADb(request.getParameter("des_immagine")));
            st.setString(2, request.getParameter("nome_immagine"));
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

    public ImmagineModel leggi(String codSport, String codCom, String codImmagine, String codLang) throws Exception {
        ImmagineModel immagine = null;
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = Utility.getConnection();
            String query = "select * from templates_immagini_immagini "
                    + "where cod_sport=? "
                    + "and cod_com=? "
                    + "and nome_immagine=?"
                    + " and cod_lang=?";
            st = conn.prepareStatement(query);
            st.setString(1, (codSport == null ? "" : codSport));
            st.setString(2, (codCom == null ? "" : codCom));
            st.setString(3, codImmagine);
            st.setString(4, codLang);
            rs = st.executeQuery();
            while (rs.next()) {
                immagine = new ImmagineModel();
                immagine.setCodCom(rs.getString("cod_com"));
                immagine.setCodSport(rs.getString("cod_sport"));
                immagine.setCodLang(rs.getString("cod_lang"));
                immagine.setNomeImmagine(codImmagine);
                immagine.setNomeFile(Utility.testoDaDb(rs.getString("nome_file")));
                byte[] bdata = rs.getBlob("immagine").getBytes(1, (int) rs.getBlob("immagine").length());
                immagine.setImmagine(Base64.decodeBase64(bdata));
            }
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return immagine;
    }
}
