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

/**
 *
 * @author Piergiorgio
 */
import it.people.dbm.utility.Utility;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.LoggerFactory;
public class RelazioniEnti {

    private static Logger log = LoggerFactory.getLogger(RelazioniEnti.class);
    
    private static final String NOT_VALID_DEST_MESSAGE = "Per attivare il riversamento automatico e' necessario che il destinatario selezionato abbia valorizzati i campi Codice utente e Codice ente.";

    public JSONObject action(HttpServletRequest request) throws Exception {
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

            query = "select count(*) righe "
                    + "from relazioni_enti a "
                    + "join enti_comuni b "
                    + "on a.cod_com=b.cod_ente "
                    + "join enti_comuni_testi c "
                    + "on a.cod_com=c.cod_ente "
                    + "join destinatari d "
                    + "on a.cod_dest=d.cod_dest "
                    + "join destinatari_testi dt "
                    + "on dt.cod_dest=d.cod_dest "
                    + "and dt.cod_lang=c.cod_lang "
                    + "join cud e "
                    + "on a.cod_cud=e.cod_cud "
                    + "join cud_testi f "
                    + "on a.cod_cud = f.cod_cud "
                    + "and c.cod_lang=f.cod_lang "
                    + "join sportelli g "
                    + "on a.cod_sport=g.cod_sport "
                    + "join sportelli_testi h "
                    + "on a.cod_sport=h.cod_sport "
                    + "and c.cod_lang=h.cod_lang "
                    + "where c.cod_lang='it' "
                    + "and ( a.cod_com like ? or  a.cod_dest like ? or a.cod_cud like ? or a.cod_sport like ? "
                    + "or c.des_ente like ? or dt.intestazione like ? or f.des_cud like ? or h.des_sport like ?) ";
            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            st.setString(3, "%" + param + "%");
            st.setString(4, "%" + param + "%");
            st.setString(5, "%" + param + "%");
            st.setString(6, "%" + param + "%");
            st.setString(7, "%" + param + "%");
            st.setString(8, "%" + param + "%");
            rs = st.executeQuery();
            if (rs.next()) {
                righe = rs.getInt("righe");
            }
            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            query = "select a.cod_com cod_com,a.cod_dest cod_dest,a.cod_cud cod_cud,a.cod_sport cod_sport, "
                    + "c.des_ente des_ente,dt.intestazione intestazione,f.des_cud des_cud,h.des_sport des_sport, a.riversamento_automatico "
                    + "from relazioni_enti a "
                    + "join enti_comuni b "
                    + "on a.cod_com=b.cod_ente "
                    + "join enti_comuni_testi c "
                    + "on a.cod_com=c.cod_ente "
                    + "join destinatari d "
                    + "on a.cod_dest=d.cod_dest "
                    + "join destinatari_testi dt "
                    + "on dt.cod_dest=d.cod_dest "
                    + "and dt.cod_lang=c.cod_lang "
                    + "join cud e "
                    + "on a.cod_cud=e.cod_cud "
                    + "join cud_testi f "
                    + "on a.cod_cud = f.cod_cud "
                    + "and c.cod_lang=f.cod_lang "
                    + "join sportelli g "
                    + "on a.cod_sport=g.cod_sport "
                    + "join sportelli_testi h "
                    + "on a.cod_sport=h.cod_sport "
                    + "and c.cod_lang=h.cod_lang "
                    + "where c.cod_lang='it' "
                    + "and ( a.cod_com like ? or  a.cod_dest like ? or a.cod_cud like ? or a.cod_sport like ? "
                    + "or c.des_ente like ? or dt.intestazione like ? or f.des_cud like ? or h.des_sport like ?) " + "order by " + sort + " " + order + " limit ? , ?";

            st = conn.prepareStatement(query);
            st.setString(1, "%" + param + "%");
            st.setString(2, "%" + param + "%");
            st.setString(3, "%" + param + "%");
            st.setString(4, "%" + param + "%");
            st.setString(5, "%" + param + "%");
            st.setString(6, "%" + param + "%");
            st.setString(7, "%" + param + "%");
            st.setString(8, "%" + param + "%");
            st.setInt(9, Integer.parseInt(offset));
            st.setInt(10, Integer.parseInt(size));
            rs = st.executeQuery();

            while (rs.next()) {
                loopObj = new JSONObject();
                loopObj.put("cod_com", rs.getString("cod_com"));
                loopObj.put("des_ente", Utility.testoDaDb(rs.getString("des_ente")));
                loopObj.put("cod_dest", rs.getString("cod_dest"));
                loopObj.put("intestazione", Utility.testoDaDb(rs.getString("intestazione")));
                loopObj.put("cod_cud", rs.getString("cod_cud"));
                loopObj.put("des_cud", Utility.testoDaDb(rs.getString("des_cud")));
                loopObj.put("cod_sport", rs.getString("cod_sport"));
                loopObj.put("des_sport", Utility.testoDaDb(rs.getString("des_sport")));
                loopObj.put("riversamento_automatico", Utility.testoDaDb(rs.getString("riversamento_automatico")).equalsIgnoreCase("s") ? "true" : "false");
                riga.add(loopObj);

            }
            ret = new JSONObject();
            ret.put("totalCount", righe);
            ret.put("relazioni_enti", riga);
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
        if (isValidDest(request.getParameter("riversamento_automatico"), request.getParameter("cod_dest"))) {
	        try {
	            conn = Utility.getConnection();
	            ret = new JSONObject();
	            query = "insert into relazioni_enti (cod_com,cod_cud,cod_dest,cod_sport,riversamento_automatico) "
	                    + "values (?,?,?,?,?)";
	            st = conn.prepareStatement(query);
	            st.setString(1, request.getParameter("cod_com"));
	            st.setString(2, request.getParameter("cod_cud"));
	            st.setString(3, request.getParameter("cod_dest"));
	            st.setString(4, request.getParameter("cod_sport"));
	            st.setString(5, (request.getParameter("riversamento_automatico") == null
	                    || request.getParameter("riversamento_automatico").equals("") ? "N"
	                    : "S"));
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
        } else {
            ret = new JSONObject();
            ret.put("failure", NOT_VALID_DEST_MESSAGE);
        }
        return ret;
    }

    public JSONObject aggiorna(HttpServletRequest request) throws Exception {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        JSONObject ret = null;
        String query;
        if (isValidDest(request.getParameter("riversamento_automatico"), request.getParameter("cod_dest"))) {
	        try {
	            conn = Utility.getConnection();
	            ret = new JSONObject();
	            if (request.getParameter("az_modifica").equals("cancella")) {
	                query = "insert into relazioni_enti (cod_com,cod_cud) "
	                        + "values (?,?)";
	                st = conn.prepareStatement(query);
	                st.setString(1, request.getParameter("cod_com"));
	                st.setString(2, request.getParameter("cod_cud"));
	
	                st.executeUpdate();
	                Utility.chiusuraJdbc(rs);
	                Utility.chiusuraJdbc(st);
	                query = "delete from relazioni_enti where cod_com=? and cod_cud=? and cod_dest=? and cod_sport=?";
	                st = conn.prepareStatement(query);
	                st.setString(1, request.getParameter("cod_com_old"));
	                st.setString(2, request.getParameter("cod_cud_old"));
	                st.setString(3, request.getParameter("cod_dest_old"));
	                st.setString(4, request.getParameter("cod_sport_old"));
	                st.executeUpdate();
	                Utility.chiusuraJdbc(rs);
	                Utility.chiusuraJdbc(st);
	            }
	            if (request.getParameter("az_modifica").equals("mantieni")) {
	                query = "insert into relazioni_enti (cod_com,cod_cud,cod_dest,cod_sport,riversamento_automatico) "
	                        + "values (?,?,?,?)";
	                st = conn.prepareStatement(query);
	                st.setString(1, request.getParameter("cod_com"));
	                st.setString(2, request.getParameter("cod_cud"));
	                st.setString(3, request.getParameter("cod_dest"));
	                st.setString(4, request.getParameter("cod_sport"));
	                st.setString(5, (request.getParameter("riversamento_automatico") == null
	                        || request.getParameter("riversamento_automatico").equals("") ? "N"
	                        : "S"));
	                st.executeUpdate();
	                Utility.chiusuraJdbc(rs);
	                Utility.chiusuraJdbc(st);
	            }
	            if (request.getParameter("az_modifica").equals("")) {
	                query = "update relazioni_enti set cod_dest=?, cod_sport=?, riversamento_automatico = ? where cod_com=? and cod_cud=?";
	                st = conn.prepareStatement(query);
	                st.setString(1, request.getParameter("cod_dest"));
	                st.setString(2, request.getParameter("cod_sport"));
	                st.setString(3, (request.getParameter("riversamento_automatico") == null
	                        || request.getParameter("riversamento_automatico").equals("") ? "N"
	                        : "S"));
	                st.setString(4, request.getParameter("cod_com"));
	                st.setString(5, request.getParameter("cod_cud"));
	                st.executeUpdate();
	            }
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
        } else {
            ret = new JSONObject();
            ret.put("failure", NOT_VALID_DEST_MESSAGE);
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
            query = "delete from relazioni_enti where cod_com=? and cod_cud=?";
            st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("cod_com"));
            st.setString(2, request.getParameter("cod_cud"));
            st.executeUpdate();

            ret.put("success", "Cancellazione effettuata");
        } catch (SQLException e) {
            log.error("Errore cancellazione ", e);
            ret = new JSONObject();
            ret.put("failure", e.getMessage());
        } finally {

            Utility.chiusuraJdbc(rs);
            Utility.chiusuraJdbc(st);
            Utility.chiusuraJdbc(conn);
        }
        return ret;
    }

    private boolean isValidDest(String riversamentoAutomatico, String codDest) throws Exception {
    	
    	boolean result = true;
    	
    	if (riversamentoAutomatico != null && !riversamentoAutomatico.trim().equals("")) {
	    	
	        Connection conn = null;
	        PreparedStatement st = null;
	        ResultSet rs = null;
	        String query;
	        try {
	            conn = Utility.getConnection();
	            query = "select ae_codice_utente, ae_codice_ente from destinatari where cod_dest = ?";
	            st = conn.prepareStatement(query);
	            st.setString(1, codDest);
	            rs = st.executeQuery();
	            if (rs.next()) {
	            	String aeCodiceUtente = rs.getString("ae_codice_utente");
	            	String aeCodiceEnte = rs.getString("ae_codice_ente");
	            	result = (aeCodiceUtente != null && !aeCodiceUtente.trim().equalsIgnoreCase("")) && 
	            			(aeCodiceEnte != null && !aeCodiceEnte.trim().equalsIgnoreCase(""));
	            }
	        } catch (SQLException e) {
	            log.error("Errore cancellazione ", e);
	        } finally {
	
	            Utility.chiusuraJdbc(rs);
	            Utility.chiusuraJdbc(st);
	            Utility.chiusuraJdbc(conn);
	        }
    	}
    	
    	return result;
    	
    }
    
}
