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
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;

import it.people.dbm.utility.Utility;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.LoggerFactory;
/**
 *
 * @author Piergiorgio
 */
public class Destinatari {

    private static Logger log = LoggerFactory.getLogger(Destinatari.class);

    public JSONObject action(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        PreparedStatement stLang = null;
        ResultSet rsLang = null;
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
                    + "from destinatari a "
                    + "join destinatari_testi b "
                    + "on a.cod_dest=b.cod_dest "
                    + "join enti_comuni c "
                    + "on a.cod_ente=c.cod_ente "
                    + "join enti_comuni_testi d "
                    + "on c.cod_ente = d.cod_ente "
                    + "and b.cod_lang=d.cod_lang "
                    + "where b.cod_lang='it' and (a.cod_dest like ? or b.nome_dest like ? "
                    + "or b.intestazione like ? or a.cod_ente like ? or d.des_ente like ?)";
            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            st.setString(3, "%" + param + "%");
            st.setString(4, "%" + param + "%");
            st.setString(5, "%" + param + "%");
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);

            String queryLang = "select intestazione, nome_dest, cod_lang from destinatari_testi where cod_dest = ?";

            query = "select a.cod_dest, b.intestazione, b.nome_dest, a.cap, a.indirizzo, a.citta, a.prov, "
                    + "a.tel, a.fax, a.email, a.cod_ente, d.des_ente, a.ae_codice_utente, a.ae_codice_ente, a.ae_tipo_ufficio, a.ae_codice_ufficio "
                    + "from destinatari a "
                    + "join destinatari_testi b "
                    + "on a.cod_dest=b.cod_dest "
                    + "join enti_comuni c "
                    + "on a.cod_ente=c.cod_ente "
                    + "join enti_comuni_testi d "
                    + "on c.cod_ente = d.cod_ente "
                    + "and b.cod_lang=d.cod_lang "
                    + "where b.cod_lang='it' and (a.cod_dest like ? or b.nome_dest like ? "
                    + "or b.intestazione like ? or a.cod_ente like ? or d.des_ente like ?) "
                    + "order by " + sort + " " + order + " limit ? , ?";

            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            st.setString(3, "%" + param + "%");
            st.setString(4, "%" + param + "%");
            st.setString(5, "%" + param + "%");
            st.setInt(6, Integer.parseInt(offset));
            st.setInt(7, Integer.parseInt(size));
            rs = st.executeQuery();

            JSONArray riga = new JSONArray();
            JSONObject loopObj;
            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_dest", rs.getString("cod_dest"));
                loopObj.put("cap", Utility.testoDaDb(rs.getString("cap")));
                loopObj.put("indirizzo", Utility.testoDaDb(rs.getString("indirizzo")));
                loopObj.put("citta", Utility.testoDaDb(rs.getString("citta")));
                loopObj.put("prov", Utility.testoDaDb(rs.getString("prov")));
                loopObj.put("tel", Utility.testoDaDb(rs.getString("tel")));
                loopObj.put("fax", Utility.testoDaDb(rs.getString("fax")));
                loopObj.put("email", Utility.testoDaDb(rs.getString("email")));
                loopObj.put("cod_ente", Utility.testoDaDb(rs.getString("cod_ente")));
                loopObj.put("des_ente", Utility.testoDaDb(rs.getString("des_ente")));
                loopObj.put("ae_codice_utente", Utility.testoDaDb(rs.getString("ae_codice_utente")));
                loopObj.put("ae_codice_ente", Utility.testoDaDb(rs.getString("ae_codice_ente")));
                loopObj.put("ae_tipo_ufficio", Utility.testoDaDb(rs.getString("ae_tipo_ufficio")));
                loopObj.put("ae_codice_ufficio", Utility.testoDaDb(rs.getString("ae_codice_ufficio")));

                stLang = conn.prepareStatement(queryLang);
                stLang.setString(1, rs.getString("cod_dest"));
                rsLang = stLang.executeQuery();
                while (rsLang.next()) {
                    loopObj.put("intestazione_" + rsLang.getString("cod_lang"), Utility.testoDaDb(rsLang.getString("intestazione")));
                    loopObj.put("nome_dest_" + rsLang.getString("cod_lang"), Utility.testoDaDb(rsLang.getString("nome_dest")));
                }
                Utility.chiusuraJdbc(rsLang);
                Utility.chiusuraJdbc(stLang);
                riga.add(loopObj);

            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("destinatari", riga);
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(rsLang);
            Utility.chiusuraJdbc(stLang);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    public JSONObject inserisci(HttpServletRequest request) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;

        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            String query = "insert into destinatari (cod_ente,cod_dest,indirizzo,cap,citta,prov,tel,fax,email,ae_codice_utente,ae_codice_ente,ae_tipo_ufficio,ae_codice_ufficio) "
                    + "values (?,?,?,?,?,?,?,?,?,?,?,?,? )";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("cod_ente"));
            st.setString(2, request.getParameter("cod_dest"));
            st.setString(3, (request.getParameter("indirizzo") == null || request.getParameter("indirizzo").equals("") ? null : Utility.testoADb(request.getParameter("indirizzo"))));
            st.setString(4, (request.getParameter("cap") == null || request.getParameter("cap").equals("") ? null : Utility.testoADb(request.getParameter("cap"))));
            st.setString(5, (request.getParameter("citta") == null || request.getParameter("citta").equals("") ? null : Utility.testoADb(request.getParameter("citta"))));
            st.setString(6, (request.getParameter("prov") == null || request.getParameter("prov").equals("") ? null : Utility.testoADb(request.getParameter("prov"))));
            st.setString(7, (request.getParameter("tel") == null || request.getParameter("tel").equals("") ? null : Utility.testoADb(request.getParameter("tel"))));
            st.setString(8, (request.getParameter("fax") == null || request.getParameter("fax").equals("") ? null : Utility.testoADb(request.getParameter("fax"))));
            st.setString(9, (request.getParameter("email") == null || request.getParameter("email").equals("") ? null : Utility.testoADb(request.getParameter("email"))));
            st.setString(10, (request.getParameter("ae_codice_utente") == null || request.getParameter("ae_codice_utente").equals("") ? null : Utility.testoADb(request.getParameter("ae_codice_utente"))));
            st.setString(11, (request.getParameter("ae_codice_ente") == null || request.getParameter("ae_codice_ente").equals("") ? null : Utility.testoADb(request.getParameter("ae_codice_ente"))));
            st.setString(12, (request.getParameter("ae_tipo_ufficio") == null || request.getParameter("ae_tipo_ufficio").equals("") ? null : Utility.testoADb(request.getParameter("ae_tipo_ufficio"))));
            st.setString(13, (request.getParameter("ae_codice_ufficio") == null || request.getParameter("ae_codice_ufficio").equals("") ? null : Utility.testoADb(request.getParameter("ae_codice_ufficio"))));
            st.executeUpdate();
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);

            query = "insert into destinatari_testi (cod_dest,cod_lang,nome_dest, intestazione) "
                    + "values (?,?,?,? )";
            Set<String> parmKey = request.getParameterMap().keySet();
            for (String key : parmKey) {
                String[] parSplit = key.split("intestazione_");
                if (parSplit.length > 1 && key.contains("intestazione_")) {
                    String cod_lang = parSplit[1];
                    if (cod_lang != null) {
                        st = conn.prepareStatement(query);
                        st.setString(1, request.getParameter("cod_dest"));
                        st.setString(2, cod_lang);
                        st.setString(3, Utility.testoADb(request.getParameter("nome_dest_" + cod_lang)));
                        st.setString(4, (request.getParameter(key) == null || request.getParameter(key).equals("") ? null : Utility.testoADb(request.getParameter(key))));
                        st.executeUpdate();
                        Utility.chiusuraJdbc(rs);
                        Utility.chiusuraJdbc(st);
                    }
                }
            }


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

    public JSONObject aggiorna(HttpServletRequest request) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        PreparedStatement stRead = null;
        ResultSet rsRead = null;
        JSONObject ret = null;

        boolean isDestAEDataClearable = isDestAEDataClearable(request.getParameter("cod_dest"));
        boolean isValidAEData = true;
        if ((request.getParameter("ae_codice_utente") == null || (request.getParameter("ae_codice_utente") != null && 
        		request.getParameter("ae_codice_utente").trim().equalsIgnoreCase(""))) || 
        		(request.getParameter("ae_codice_ente") == null || (request.getParameter("ae_codice_ente") != null && 
        		request.getParameter("ae_codice_ente").trim().equalsIgnoreCase("")))) {
            isValidAEData = false;
        }
        if (isValidAEData && !isDestAEDataClearable) {
            isDestAEDataClearable = true;
        }
        
        if (isDestAEDataClearable) {
	        try {
	            conn = Utility.getConnection();
	            ret = new JSONObject();
	            String query = "update destinatari "
	                    + "set cod_ente = ?,"
	                    + "indirizzo = ?, "
	                    + "cap=?, "
	                    + "citta= ?, "
	                    + "prov=?, "
	                    + "tel=?, "
	                    + "fax=?, "
	                    + "email=?, "
	                    + "ae_codice_utente=?, "
	                    + "ae_codice_ente=?, "
	                    + "ae_tipo_ufficio=?, "
	                    + "ae_codice_ufficio=? "
	                    + "where cod_dest =?";
	            st = conn.prepareStatement(query);
	            st.setString(1, request.getParameter("cod_ente"));
	            st.setString(2, (request.getParameter("indirizzo") == null || request.getParameter("indirizzo").equals("") ? null : Utility.testoADb(request.getParameter("indirizzo"))));
	            st.setString(3, (request.getParameter("cap") == null || request.getParameter("cap").equals("") ? null : Utility.testoADb(request.getParameter("cap"))));
	            st.setString(4, (request.getParameter("citta") == null || request.getParameter("citta").equals("") ? null : Utility.testoADb(request.getParameter("citta"))));
	            st.setString(5, (request.getParameter("prov") == null || request.getParameter("prov").equals("") ? null : Utility.testoADb(request.getParameter("prov"))));
	            st.setString(6, (request.getParameter("tel") == null || request.getParameter("tel").equals("") ? null : Utility.testoADb(request.getParameter("tel"))));
	            st.setString(7, (request.getParameter("fax") == null || request.getParameter("fax").equals("") ? null : Utility.testoADb(request.getParameter("fax"))));
	            st.setString(8, (request.getParameter("email") == null || request.getParameter("email").equals("") ? null : Utility.testoADb(request.getParameter("email"))));
	            st.setString(9, (request.getParameter("ae_codice_utente") == null || request.getParameter("ae_codice_utente").equals("") ? null : Utility.testoADb(request.getParameter("ae_codice_utente"))));
	            st.setString(10, (request.getParameter("ae_codice_ente") == null || request.getParameter("ae_codice_ente").equals("") ? null : Utility.testoADb(request.getParameter("ae_codice_ente"))));
	            st.setString(11, (request.getParameter("ae_tipo_ufficio") == null || request.getParameter("ae_tipo_ufficio").equals("") ? null : Utility.testoADb(request.getParameter("ae_tipo_ufficio"))));
	            st.setString(12, (request.getParameter("ae_codice_ufficio") == null || request.getParameter("ae_codice_ufficio").equals("") ? null : Utility.testoADb(request.getParameter("ae_codice_ufficio"))));
	            st.setString(13, request.getParameter("cod_dest"));
	            st.executeUpdate();
	            Utility.chiusuraJdbc(rs);
	            Utility.chiusuraJdbc(st);
	
	            String querySelect = "select * from destinatari_testi where cod_dest = ? and cod_lang = ?";
	            String queryInsert = "insert into destinatari_testi (cod_dest,cod_lang,intestazione,nome_dest) values (?,?,?,? )";
	
	            query = "update destinatari_testi "
	                    + "set intestazione=?, "
	                    + "nome_dest=? "
	                    + "where cod_dest =? and cod_lang=?";
	            Set<String> parmKey = request.getParameterMap().keySet();
	            for (String key : parmKey) {
	                String[] parSplit = key.split("intestazione_");
	                if (parSplit.length > 1 && key.contains("intestazione_")) {
	                    String cod_lang = parSplit[1];
	                    if (cod_lang != null) {
	                        stRead = conn.prepareStatement(querySelect);
	                        stRead.setString(1, request.getParameter("cod_dest"));
	                        stRead.setString(2, cod_lang);
	                        rsRead = stRead.executeQuery();
	                        if (rsRead.next()) {
	                            st = conn.prepareStatement(query);
	                            st.setString(1, (request.getParameter(key) == null || request.getParameter(key).equals("") ? null : Utility.testoADb(request.getParameter(key))));
	                            st.setString(2, Utility.testoADb(request.getParameter("nome_dest_" + cod_lang)));
	                            st.setString(3, request.getParameter("cod_dest"));
	                            st.setString(4, cod_lang);
	                            st.executeUpdate();
	
	                        } else {
	                            st = conn.prepareStatement(queryInsert);
	                            st.setString(1, request.getParameter("cod_dest"));
	                            st.setString(2, cod_lang);
	                            st.setString(3, (request.getParameter(key) == null || request.getParameter(key).equals("") ? null : Utility.testoADb(request.getParameter(key))));
	                            st.setString(4, Utility.testoADb(request.getParameter("nome_dest_" + cod_lang)));
	                            st.execute();
	
	                        }
	                        Utility.chiusuraJdbc(rs);
	                        Utility.chiusuraJdbc(st);
	                        Utility.chiusuraJdbc(rsRead);
	                        Utility.chiusuraJdbc(stRead);
	                    }
	                }
	            }
	
	            ret.put("success", "Aggiornamento effettuato");
	        } catch (SQLException e) {
	            log.error("Errore update ", e);
	            ret = new JSONObject();
	            ret.put("failure", e.getMessage());
	        } finally {
	
	            Utility.chiusuraJdbc(rs);
	            Utility.chiusuraJdbc(st);
	            Utility.chiusuraJdbc(rsRead);
	            Utility.chiusuraJdbc(stRead);
	            Utility.chiusuraJdbc(conn);
	        }
        } else {
            ret = new JSONObject();
            ret.put("failure", "Il destinatario partecipa ad una relazione in cui e' attivo il riversamento automatico, per cui i campi Codice utente e Codice ente non possono essere vuoti.");
        }
        return ret;
    }

    public boolean controlloCancella(String key, Connection conn) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        boolean controllo = true;
        int righe = 0;
        try {
            String query = "select count(*) righe from relazioni_enti where cod_dest=? ";
            st = conn.prepareStatement(query);
            st.setString(1, key);
            rs = st.executeQuery();
            while (rs.next()) {
                righe = righe + rs.getInt("righe");
            }
            if (righe > 0) {
                controllo = false;
            }
        } finally {
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
        }
        return controllo;
    }

    public JSONObject cancella(HttpServletRequest request) throws Exception {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        boolean controllo = true;
        try {
            conn = Utility.getConnection();
            ret = new JSONObject();
            controllo = controlloCancella(request.getParameter("cod_dest"), conn);
            if (controllo) {
                String query = "delete from destinatari where cod_dest= ?";
                st = conn.prepareStatement(query);
                st.setString(1, request.getParameter("cod_dest"));
                st.executeUpdate();
                Utility.chiusuraJdbc(rs);
                Utility.chiusuraJdbc(st);
                query = "delete from destinatari_testi where cod_dest= ?";
                st = conn.prepareStatement(query);
                st.setString(1, request.getParameter("cod_dest"));
                st.executeUpdate();
                ret.put("success", "Cancellazione effettuata");
            } else {
                ret.put("failure", "Destinatario in uso");
            }
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

    private boolean isDestAEDataClearable(String codDest) throws Exception {
    	
    	boolean result = true;
    	
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        String query;
        try {
            conn = Utility.getConnection();
            query = "select count(cod_dest) from relazioni_enti as re where re.riversamento_automatico = 'S' and re.cod_dest = ?";
            st = conn.prepareStatement(query);
            st.setString(1, codDest);
            rs = st.executeQuery();
            if (rs.next()) {
            	result = rs.getInt(1) <= 0;
            }
        } catch (SQLException e) {
            log.error("Errore cancellazione ", e);
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
    	
    	return result;
    	
    }
    
    
}
