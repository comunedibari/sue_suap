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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *
 * @author Piergiorgio
 */
public class RisaliGerarchiaUtenti {

    private Connection conn = null;
    private static Logger log = LoggerFactory.getLogger(RisaliGerarchiaUtenti.class);

    public void risaliGerarchiaUtenti(String codUtente, HashMap<String, List<String>> lui) throws Exception {
        try {
            conn = Utility.getConnection();
            risaliGerarchiaUtentiLoop(codUtente, lui);
        } finally {
            Utility.chiusuraJdbc(conn);
        }
    }

    public void risaliGerarchiaUtentiLoop(String codUtente, HashMap<String, List<String>> lui) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        String sql;
        try {
            sql = "select * from utenti where cod_utente=?";
            st = conn.prepareStatement(sql);
            st.setString(1, codUtente);
            rs = st.executeQuery();
            while (rs.next()) {
                if (rs.getObject("cod_utente_padre") != null) {
                    if (lui.containsKey(rs.getString("cod_utente_padre"))) {
                        if (lui.containsKey(rs.getString("cod_utente"))) {
                            for (int i = 0; i < lui.get(rs.getString("cod_utente")).size(); i++) {
                                String codInt = lui.get(rs.getString("cod_utente")).get(i);
                                if (!lui.get(rs.getString("cod_utente_padre")).contains(codInt)) {
                                    lui.get(rs.getString("cod_utente_padre")).add(codInt);
                                }
                            }
                        }
                    } else {
                        lui.put(rs.getString("cod_utente_padre"), new ArrayList<String>());
                        if (lui.containsKey(rs.getString("cod_utente"))) {
                            for (int i = 0; i < lui.get(rs.getString("cod_utente")).size(); i++) {
                                String codInt = lui.get(rs.getString("cod_utente")).get(i);
                                if (!lui.get(rs.getString("cod_utente_padre")).contains(codInt)) {
                                    lui.get(rs.getString("cod_utente_padre")).add(codInt);
                                }
                            }
                        }
                    }
                    risaliGerarchiaUtentiLoop(rs.getString("cod_utente_padre"), lui);
                }
            }
        } catch (SQLException e) {
            log.error("Errore risalita utenti ", e);
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }

    }
}
