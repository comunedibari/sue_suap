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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.people.dbm.utility.Utility;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author Piergiorgio
 */
public class DatiIntegrati {

    private static Logger log = LoggerFactory.getLogger(DatiIntegrati.class);

    public JSONObject action(HttpServletRequest request) throws Exception {

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
            String href = request.getParameter("href");
            String param = Utility.testoADb(request.getParameter("query"));

            String query = "select count(*) righe "
                    + "from href_campi a join href_campi_testi b "
                    + "on a.href = b.href "
                    + "and a.contatore=b.contatore "
                    + "and a.nome=b.nome "
                    + "join href c "
                    + "on a.href=c.href "
                    + "join href_testi d "
                    + "on a.href=d.href "
                    + "and b.cod_lang=d.cod_lang "
                    + "where b.cod_lang='it' "
                    + "and a.href=? and (a.nome like ? or b.des_campo like ?)";
            st = conn.prepareStatement(query);
            st.setString(1, href);
            st.setString(2, "%" + param + "%");
            st.setString(3, "%" + param + "%");
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);

            query = "select a.nome nome,a.contatore contatore,a.href href,a.web_serv web_serv, "
                    + "a.nome_xsd nome_xsd,a.campo_dati campo_dati,a.campo_key campo_key, "
                    + "a.campo_xml_mod campo_xml_mod, a.marcatore_incrociato marcatore_incrociato, "
                    + "a.precompilazione precompilazione,b.des_campo des_campo, d.tit_href tit_href "
                    + "from href_campi a join href_campi_testi b "
                    + "on a.href = b.href "
                    + "and a.contatore=b.contatore "
                    + "and a.nome=b.nome "
                    + "join href c "
                    + "on a.href=c.href "
                    + "join href_testi d "
                    + "on a.href=d.href "
                    + "and b.cod_lang=d.cod_lang "
                    + "where b.cod_lang='it' "
                    + "and a.href=? and (a.nome like ? or b.des_campo like ? ) "
                    + "order by " + sort + " " + order + " limit ? , ?";

            st = conn.prepareStatement(query);
            st.setString(1, href);
            st.setString(2, "%" + param + "%");
            st.setString(3, "%" + param + "%");
            st.setInt(4, Integer.parseInt(offset));
            st.setInt(5, Integer.parseInt(size));
            rs = st.executeQuery();

            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("nome", rs.getString("nome"));
                loopObj.put("contatore", rs.getInt("contatore"));
                loopObj.put("href", rs.getString("href"));
                loopObj.put("web_serv", rs.getString("web_serv"));
                loopObj.put("nome_xsd", rs.getString("nome_xsd"));
                loopObj.put("campo_dati", rs.getString("campo_dati"));
                loopObj.put("campo_key", rs.getString("campo_key"));
                loopObj.put("campo_xml_mod", rs.getString("campo_xml_mod"));
                loopObj.put("marcatore_incrociato", rs.getString("marcatore_incrociato"));
                loopObj.put("precompilazione", rs.getString("precompilazione"));
                loopObj.put("des_campo", Utility.testoDaDb(rs.getString("des_campo")));
                loopObj.put("tit_href", Utility.testoDaDb(rs.getString("tit_href")));
                riga.add(loopObj);

            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("dati_integrati", riga);
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

            String query = "update href_campi "
                    + "set web_serv=? , "
                    + "nome_xsd=? , "
                    + "campo_key=? , "
                    + "campo_dati=? ,"
                    + "campo_xml_mod=? , "
                    + "marcatore_incrociato= ? , "
                    + "precompilazione=?  "
                    + "where href=? "
                    + "and nome=? "
                    + "and contatore=?";
            st = conn.prepareStatement(query);
            st.setString(1, (request.getParameter("web_serv") == null || request.getParameter("web_serv").equals("") ? null : request.getParameter("web_serv")));
            st.setString(2, (request.getParameter("nome_xsd") == null || request.getParameter("nome_xsd").equals("") ? null : request.getParameter("nome_xsd")));
            st.setString(3, (request.getParameter("campo_key") == null || request.getParameter("campo_key").equals("") ? null : request.getParameter("campo_key")));
            st.setString(4, (request.getParameter("campo_dati") == null || request.getParameter("campo_dati").equals("") ? null : request.getParameter("campo_dati")));
            st.setString(5, (request.getParameter("campo_xml_mod") == null || request.getParameter("campo_xml_mod").equals("") ? null : request.getParameter("campo_xml_mod")));
            st.setString(6, (request.getParameter("marcatore_incrociato") == null || request.getParameter("marcatore_incrociato").equals("") ? "" : request.getParameter("marcatore_incrociato")));
            st.setString(7, (request.getParameter("precompilazione") == null || request.getParameter("precompilazione").equals("") ? "" : request.getParameter("precompilazione")));
            st.setString(8, request.getParameter("href"));
            st.setString(9, request.getParameter("nome"));
            st.setInt(10, Integer.parseInt(request.getParameter("contatore")));
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
