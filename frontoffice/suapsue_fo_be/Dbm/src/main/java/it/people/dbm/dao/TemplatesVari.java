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

import it.people.dbm.model.TemplateVariModel;
import it.people.dbm.utility.Utility;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.LoggerFactory;
/**
 *
 * @author Piergiorgio
 */
public class TemplatesVari {

    private static Logger log = LoggerFactory.getLogger(TemplatesVari.class);

    public JSONObject action(HttpServletRequest request) throws Exception {
        JSONObject ret = null;
        if (request.getParameter("table_name").equals("templates_vari")) {
            ret = actionTemplatesVari(request);
        }
        if (request.getParameter("table_name").equals("templates_vari_risorse")) {
            ret = actionTemplatesVariRisorse(request);
        }
        return ret;
    }

    public JSONObject actionTemplatesVari(HttpServletRequest request) throws Exception {
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

            query = " select count(*) righe from templates_vari "
                    + "where nome_template like ? or des_template like ? ";
            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            query = "select nome_template,des_template from templates_vari "
                    + "where nome_template like ? or des_template like ? "
                    + "order by " + sort + " " + order + " limit ? , ?";

            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            st.setInt(3, Integer.parseInt(offset));
            st.setInt(4, Integer.parseInt(size));
            rs = st.executeQuery();

            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("nome_template", rs.getString("nome_template"));
                loopObj.put("des_template", Utility.testoDaDb(rs.getString("des_template")));
                riga.add(loopObj);
            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("templates_vari", riga);
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject actionTemplatesVariRisorse(HttpServletRequest request) throws Exception {
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
                    + "from templates_vari_risorse a "
                    + "left join sportelli b "
                    + "on a.cod_sport=b.cod_sport "
                    + "left join sportelli_testi c "
                    + "on a.cod_sport=c.cod_sport "
                    + "and c.cod_lang='it' "
                    + "where nome_template=? "
                    + "and (a.cod_sport like ? or c.des_sport like ? or a.nome_file like ?)";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("nome_template"));
            st.setString(2, "%" + param + "%");
            st.setString(3, "%" + param + "%");
            st.setString(4, "%" + param + "%");
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            query = "select a.nome_template nome_template,a.nome_file nome_file,a.cod_sport cod_sport, c.des_sport des_sport, a.cod_lang "
                    + "from templates_vari_risorse a "
                    + "left join sportelli b "
                    + "on a.cod_sport=b.cod_sport "
                    + "left join sportelli_testi c "
                    + "on a.cod_sport=c.cod_sport "
                    + "and c.cod_lang='it' "
                    + "where nome_template=? "
                    + "and (a.cod_sport like ? or c.des_sport like ? or a.nome_file like ?) "
                    + "order by " + sort + " " + order + " limit ? , ?";

            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("nome_template"));
            st.setString(2, "%" + param + "%");
            st.setString(3, "%" + param + "%");
            st.setString(4, "%" + param + "%");
            st.setInt(5, Integer.parseInt(offset));
            st.setInt(6, Integer.parseInt(size));
            rs = st.executeQuery();

            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("nome_template", rs.getString("nome_template"));
                loopObj.put("cod_sport", rs.getString("cod_sport"));
                loopObj.put("des_sport", Utility.testoDaDb(rs.getString("des_sport")));
                loopObj.put("cod_lang", Utility.testoDaDb(rs.getString("cod_lang")));
                loopObj.put("nome_file", "<a href=\"ScaricaFile?tipo=templates_vari&nomeTemplate=" + rs.getString("nome_template") + "&codSport=" + rs.getString("cod_sport") + "&codLang=" + rs.getString("cod_lang") + "\" target=\"_blank\" alt=\"" + rs.getString("nome_file") + "\">" + rs.getString("nome_file") + "</a>");
                riga.add(loopObj);
            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("templates_vari_risorse", riga);
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
            query = "insert into templates_vari (nome_template,des_template) "
                    + "values (?,?)";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("nome_template"));
            st.setString(2, Utility.testoADb(request.getParameter("des_template")));
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
            query = "delete from templates_vari_risorse where nome_template=?";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("nome_template"));
            st.executeUpdate();
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);

            query = "delete from templates_vari where nome_template=?";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("nome_template"));
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

    public JSONObject cancella(TemplateVariModel tm) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        String query;
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            query = "delete from templates_vari_risorse where nome_template=? and cod_sport=? and cod_lang=?";
            st = conn.prepareStatement(query);
            st.setString(1, tm.getNomeTemplate());
            st.setString(2, tm.getCodSport());
            st.setString(3, tm.getCodLang());
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
            query = "update templates_vari set des_template=? where nome_template=?";
            st = conn.prepareStatement(query);
            st.setString(1, Utility.testoADb(request.getParameter("des_template")));
            st.setString(2, request.getParameter("nome_template"));
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

    public JSONObject aggiorna(TemplateVariModel im) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        String query;
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            query = "delete from templates_vari_risorse where nome_template=? and cod_sport=? and cod_lang=?";
            st = conn.prepareStatement(query);
            st.setString(1, im.getNomeTemplate());
            st.setString(2, (im.getCodSport() != null && !im.getCodSport().equals("") ? im.getCodSport() : ""));
            st.setString(3, im.getCodLang());
            st.executeUpdate();
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);

            query = "insert into templates_vari_risorse (nome_template,cod_sport,nome_file,doc_blob,cod_lang,tipo_file) "
                    + "values (?,?,?,?,?,?)";
            st = conn.prepareStatement(query);
            st.setString(1, im.getNomeTemplate());
            st.setString(2, (im.getCodSport() != null && !im.getCodSport().equals("") ? im.getCodSport() : ""));
            st.setString(3, im.getNomeFile());
            st.setString(4, new String(im.getTemplateVariRisorse()));
            st.setString(5, im.getCodLang());
            st.setString(6, im.getTipoFile());

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

    public TemplateVariModel leggi(String codSport, String codTemplate, String codLang) throws Exception {
        TemplateVariModel template = null;
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = Utility.getConnection();
            String query = "select * from templates_vari_risorse "
                    + "where cod_sport=? "
                    + "and nome_template=?"
                    + " and cod_lang=?";
            st = conn.prepareStatement(query);
            st.setString(1, (codSport == null ? "" : codSport));
            st.setString(2, codTemplate);
            st.setString(3, codLang);
            rs = st.executeQuery();
            while (rs.next()) {
                template = new TemplateVariModel();
                template.setCodSport(rs.getString("cod_sport"));
                template.setCodLang(rs.getString("cod_lang"));
                template.setTipoFile(rs.getString("tipo_file"));
                template.setNomeTemplate(codTemplate);
                template.setNomeFile(Utility.testoDaDb(rs.getString("nome_file")));
                byte[] bdata = rs.getBlob("doc_blob").getBytes(1, (int) rs.getBlob("doc_blob").length());
                template.setTemplateVariRisorse(Base64.decodeBase64(bdata));
            }
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return template;
    }
}
