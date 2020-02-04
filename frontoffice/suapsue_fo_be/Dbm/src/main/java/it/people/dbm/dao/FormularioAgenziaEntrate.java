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

import org.slf4j.Logger;

import it.people.dbm.utility.Utility;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.LoggerFactory;
public class FormularioAgenziaEntrate {

    private static Logger log = LoggerFactory.getLogger(FormularioAgenziaEntrate.class);

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

            query = "select fae.data_validita, fae.codice_ente, fae.tipologia_ufficio, fae.codice_ufficio, fae.tipologia_ente, fae.denominazione, fae.codice_catastale_comune, fae.data_decorrenza, fae.ufficio_statale "
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
