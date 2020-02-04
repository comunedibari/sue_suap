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
package it.people.dbm.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *
 * @author giuseppe
 */
public class Configuration {
	private static Logger log = LoggerFactory.getLogger(Configuration.class);
    public static String getConfigurationParameter(String parameter) throws Exception{
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        String result = null;
        try {
            conn = Utility.getConnection();
            String query = "select value from configuration where name = ?";
            st = conn.prepareStatement(query);
            st.setString(1, parameter);
            rs = st.executeQuery();
            if (rs.next()) {
                result = rs.getString("value");
            }
            st.close();
        } catch (SQLException e) {
        	log.error("Errore nel reperire il parametro", e);
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return result;
    }

}
