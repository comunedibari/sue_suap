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
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;

import it.people.dbm.utility.Utility;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.slf4j.LoggerFactory;
/**
 * @author Riccardo Foraf� - Engineering Ingegneria Informatica - Genova
 * 20/set/2011 22.59.09
 */
public class Protocollo {

    private static Logger log = LoggerFactory.getLogger(Protocollo.class);

    public JSONObject action(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        int righe = 0;
        try {
        	
        	String ref_sport = request.getParameter("ref_sport");
        	
            conn = Utility.getConnection();

            String query = "select name, value "
                    + "from sportelli_param_prot_ws "
                    + "where ref_sport = ?";
            st = conn.prepareStatement(query);
            st.setString(1, ref_sport);
            rs = st.executeQuery();

            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("name", rs.getString("name"));
                loopObj.put("value", rs.getString("value"));
                riga.add(loopObj);
            }
            ret = new JSONObject();
            ret.put("dati_protocollo_ws", riga);
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
        JSONObject ret = new JSONObject();

    	JSONArray riga = (JSONArray)JSONSerializer.toJSON(request.getParameter("store"));
    	String ref_sport = request.getParameter("ref_sport");
    	
    	if (!riga.isEmpty()) {
        
	        try {
		        	
	            conn = Utility.getConnection();
	
	            String query = "delete from sportelli_param_prot_ws where ref_sport = ?";
	            st = conn.prepareStatement(query);
	            st.setString(1, ref_sport);
	            st.executeUpdate();
	            st.close();
	            
	            query = "insert into sportelli_param_prot_ws(ref_sport, name, value) values(?, ?, ?)";
	            
	            st = conn.prepareStatement(query);
	            
	        	Iterator rigaIterator = riga.iterator();
	        	while(rigaIterator.hasNext()) {
	        		JSONObject parametro = (JSONObject)rigaIterator.next();
	        		st.setString(1, ref_sport);
	        		st.setString(2, String.valueOf(parametro.get("name")));
	        		st.setString(3, String.valueOf(parametro.get("value")));
	        		st.addBatch();
	        	}

	        	st.executeBatch();
	        	
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
    	}
    	
        return ret;
    }

}
