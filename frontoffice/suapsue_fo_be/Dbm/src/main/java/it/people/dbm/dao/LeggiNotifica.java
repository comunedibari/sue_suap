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

import it.people.dbm.utility.Utility;
import net.sf.json.JSONObject;
import org.slf4j.LoggerFactory;
/**
 *
 * @author Piergiorgio
 */
public class LeggiNotifica {

    private static Logger log = LoggerFactory.getLogger(LeggiNotifica.class);

    public JSONObject leggiTestoCondizioni(HttpServletRequest request) throws Exception {
        String key = "TestoCondizione=" + request.getParameter("cod_cond");
        JSONObject ret = leggiNotifica(key);
        return ret;
    }

    public JSONObject leggiNormative(HttpServletRequest request) throws Exception {
        String key = "Normative=" + request.getParameter("cod_rif");
        JSONObject ret = leggiNotifica(key);
        return ret;
    }

    public JSONObject leggiDocumenti(HttpServletRequest request) throws Exception {
        String key = "Documenti=" + request.getParameter("cod_doc");
        JSONObject ret = leggiNotifica(key);
        return ret;
    }

    public JSONObject leggiHref(HttpServletRequest request) throws Exception {
        String key = "Dichiarazioni=" + request.getParameter("href");
        JSONObject ret = leggiNotifica(key);
        return ret;
    }

    public JSONObject leggiAllegati(HttpServletRequest request) throws Exception {
        String key = "Allegati=" + Integer.parseInt(request.getParameter("cod_int")) + "|" + request.getParameter("cod_doc");
        JSONObject ret = leggiNotifica(key);
        return ret;
    }

    public JSONObject leggiNormeInterventi(HttpServletRequest request) throws Exception {
        String key = "NormeInterventi=" + Integer.parseInt(request.getParameter("cod_int")) + "|" + request.getParameter("cod_rif");
        JSONObject ret = leggiNotifica(key);
        return ret;
    }
    public JSONObject leggiInterventiCollegati(HttpServletRequest request) throws Exception {
        String key = "InterventiCollegati=" + Integer.parseInt(request.getParameter("cod_int")) + "|" + Integer.parseInt(request.getParameter("cod_int_padre"));
        JSONObject ret = leggiNotifica(key);
        return ret;
    }
    public JSONObject leggiNotifica(String key) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = new JSONObject();
        try {
            conn = Utility.getConnection();
            String query = "select max(data_notifica) data from notifiche where cod_elemento = ? and stato_notifica <> 'C' ";
            st = conn.prepareStatement(query);
            st.setString(1, key);
            rs = st.executeQuery();
            Boolean trovato = false;
            while (rs.next()) {
                if (rs.getObject("data") != null) {
                    trovato = true;
                }
            }
            if (trovato) {
                ret.put("failure", "Notifica pendente(non è possibile effettuare modifiche)");
            } else {
                ret.put("success", "Possibiltà di operare");
            }
        } catch (SQLException e) {
            log.error("Errore verifica notifica attiva", e);
            ret.put("failure", e.getMessage());
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }
}
